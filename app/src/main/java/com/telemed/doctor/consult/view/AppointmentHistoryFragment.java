package com.telemed.doctor.consult.view;

import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.telemed.doctor.R;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.consult.model.AppointmentRequest;
import com.telemed.doctor.consult.model.PastAppointment;
import com.telemed.doctor.consult.model.PastAppointmentResponse;
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
import com.telemed.doctor.consult.viewmodel.AppointmentHistoryViewModel;
import com.telemed.doctor.signup.model.UserInfoWrapper;
import com.telemed.doctor.util.CustomAlertTextView;
import com.telemed.doctor.util.CustomTypingEditText;
import com.telemed.doctor.util.DividerItemDecoration;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class AppointmentHistoryFragment extends Fragment {
    private final String TAG=AppointmentHistoryFragment.class.getSimpleName();
    private static final int PAGE_SIZE = 5;
    private AppointmentHistoryViewModel mViewModel;
    private RecyclerView rvAppointmentsHistory;
    private AppointmentHistoryAdapter mAdapter;
    private HomeFragmentSelectedListener mFragmentListener;
    private String mAccessToken;
    private HashMap<String, String> mHeaderMap;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CustomAlertTextView tvAlertView;
    private ImageButton ibtnClose,ibtnBack;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean isLastPage = false;
    private boolean isListLoading = false;
    private int currentPage = 1;
    private CustomTypingEditText edtSearchView;
    private String mSearchQuery="";

    public static AppointmentHistoryFragment newInstance(Object payload) {
        return new AppointmentHistoryFragment();
    }
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (HomeFragmentSelectedListener) context;
        SharedPrefHelper mHelper = ((TeleMedApplication) context.getApplicationContext()).getSharedPrefInstance();
        mAccessToken = mHelper.read(SharedPrefHelper.KEY_ACCESS_TOKEN, "");
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new AppointmentHistoryAdapter();
        mHeaderMap = new HashMap<>();
        mHeaderMap.put("content-type", "application/json");
        mHeaderMap.put("Authorization","Bearer "+mAccessToken);
        mViewModel = ViewModelProviders.of(this).get(AppointmentHistoryViewModel.class);
        AppointmentRequest in=new AppointmentRequest();
        in.setPageNumber(currentPage);
        in.setPageSize(PAGE_SIZE);
        in.setSearchQuery(mSearchQuery);
        in.setFilterBy(""); // no need there
        mViewModel.fetchPastAppointments(mHeaderMap,in);
       // Toast.makeText(requireActivity(), "onCreate", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(requireActivity(), R.style.FragmentThemeOne);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        return localInflater.inflate(R.layout.fragment_appointment_history, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        initView(v);
        initHistoryRecyclerView(v);
        initObserver();
    }

    private void initView(View v) {
        tvAlertView = v.findViewById(R.id.tv_alert_view);
        ibtnClose=v.findViewById(R.id.ibtn_close);
        ibtnClose.setOnClickListener(v1 -> {
            if (mFragmentListener != null)
                mFragmentListener.popTillFragment("HomeFragment",0);
        });

        ibtnBack=v.findViewById(R.id.ibtn_back);
        ibtnBack.setOnClickListener(v1 -> {
            if (mFragmentListener != null)
                mFragmentListener.popTopMostFragment();
        });

        edtSearchView = v.findViewById(R.id.edt_search_view);
        edtSearchView.setOnTypingModified((view, isTyping) -> {
            currentPage=1;isLastPage = false;isListLoading = false;  // re-initialize !!!

            if (!isTyping) {
                //  Log.e(TAG, "User stopped typing.");
                mSearchQuery=view.getText().toString().trim();
                AppointmentRequest in=new AppointmentRequest();
                in.setPageNumber(currentPage);
                in.setPageSize(PAGE_SIZE);
                in.setSearchQuery(mSearchQuery); // no need there
                in.setFilterBy(""); // no need there
                mViewModel.fetchPastAppointments(mHeaderMap,in);

            }
        });


        swipeRefreshLayout = v.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorBlue);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            currentPage=1;isLastPage = false;isListLoading = false; mSearchQuery=""; // re-initialize !!!
            AppointmentRequest in=new AppointmentRequest();
            in.setPageNumber(currentPage);
            in.setPageSize(PAGE_SIZE);
            in.setSearchQuery(mSearchQuery);
            in.setFilterBy(""); // no need there
            mViewModel.fetchPastAppointments(mHeaderMap,in);
        });

    }

    private void initHistoryRecyclerView(View v) {
        rvAppointmentsHistory = v.findViewById(R.id.rv_last_appointment);
        mLinearLayoutManager = new LinearLayoutManager(requireActivity());
        rvAppointmentsHistory.setLayoutManager(mLinearLayoutManager);
        rvAppointmentsHistory.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(mOnItemClickListener);
        mAdapter.setOnReloadClickListener(mOnReloadClickListener);
        rvAppointmentsHistory.setItemAnimator(new DefaultItemAnimator());
        rvAppointmentsHistory.addOnScrollListener(mOnScrollListener);

        Drawable dividerDrawable = ContextCompat.getDrawable(requireActivity(), R.drawable.custom_divider_white);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
        rvAppointmentsHistory.addItemDecoration(dividerItemDecoration);

    }

    private void initObserver() {
        mViewModel.getProgress()
                .observe(getViewLifecycleOwner(), isLoading -> swipeRefreshLayout.setRefreshing(isLoading));

        mViewModel.isLastPage()
                .observe(getViewLifecycleOwner(), status -> {
                    this.isLastPage=status;
                });

        mViewModel.getListLoadingStatus()
                .observe(getViewLifecycleOwner(), isLoading -> {
                    this.isListLoading=isLoading;
                });

        mViewModel.isLastPage()
                .observe(getViewLifecycleOwner(), status -> {
                    this.isLastPage=status;
                });

        mViewModel.getPastAppointments()
                .observe(getViewLifecycleOwner(), lstOfAppointments -> {
                    mAdapter.addAll(lstOfAppointments);

                    if (lstOfAppointments.size() >= PAGE_SIZE) {
                        mAdapter.addFooter();
                    } else {
                        mViewModel.isLastPage(true);
                    }
                });


        mViewModel.getResultPastAppointment().observe(getViewLifecycleOwner(), response -> {

            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        PastAppointmentResponse.Data infoObj = response.getData().getData(); // adding Additional Info
                        if(infoObj.getDataList()!=null && (!infoObj.getDataList().isEmpty())){
                            mAdapter.clearAll(); // just to make sure refreshing works ok ....
                            mViewModel.setPastAppointmentList(infoObj.getDataList());
                        }else {
                            mAdapter.clearAll();
                        }
                    }
                    break;

                case FAILURE:
                    if (response.getErrorMsg() != null) {
                        tvAlertView.showTopAlert(response.getErrorMsg());
                    }
                    break;
            }

        });


        mViewModel.getResultPastNextAppointment().observe(getViewLifecycleOwner(), response -> {
            switch (response.getStatus()) {
                case SUCCESS:
                    mAdapter.removeFooter();

                    if (response.getData() != null) {
                        PastAppointmentResponse.Data infoObj = response.getData().getData(); // adding Additional Info
                        if(infoObj.getDataList()!=null && (!infoObj.getDataList().isEmpty())){
                            mViewModel.setPastAppointmentList(infoObj.getDataList());
                        }
                    }
                    break;

                case FAILURE:
                    if (response.getErrorMsg() != null) {
                        mAdapter.updateFooter(AppointmentHistoryAdapter.FooterType.ERROR);
                    }
                    break;
            }
        });

    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NotNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = mLinearLayoutManager.getChildCount();
            int totalItemCount = mLinearLayoutManager.getItemCount();
            int firstVisibleItemPosition = mLinearLayoutManager.findFirstVisibleItemPosition();

            if (!isListLoading && !isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZE) {
                    loadMoreItems();
                }
            }
        }
    };

    private AppointmentHistoryAdapter.OnItemClickListener mOnItemClickListener=new AppointmentHistoryAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position, PastAppointment model) {

        }

        @Override
        public void onItemMoreClick(PastAppointment model, MenuItem item) {
            if (item.getItemId()==1){
                if (mFragmentListener != null ) {
                    UserInfoWrapper in = new UserInfoWrapper();
                    in.setPatientId(model.getUserId());
                    in.setAppointmentId(model.getAppointmentId());
                    mFragmentListener.showFragment("DoctorDocumentFragment", in);
                }
            }
            if (item.getItemId()==2){
                if (mFragmentListener != null ) {
                    mFragmentListener.showFragment("PatientRatingFragment", null);
                }
            }

            if (item.getItemId()==3){
                if (mFragmentListener != null ) {
                    mFragmentListener.showFragment("MedicalRecordFragment", null);
                }
            }
        }


    };

    private void loadMoreItems() {
        mViewModel.setListLoading(true);
        currentPage += 1;
        AppointmentRequest in=new AppointmentRequest();
        in.setPageNumber(currentPage);
        in.setPageSize(PAGE_SIZE);
        in.setSearchQuery(""); // no need there
        in.setFilterBy("");  // no need there
        mViewModel.fetchPastNextAppointments(mHeaderMap,in);
    }

    private AppointmentHistoryAdapter.OnReloadClickListener mOnReloadClickListener= () -> {
        mAdapter.updateFooter(AppointmentHistoryAdapter.FooterType.LOAD_MORE);

        AppointmentRequest in=new AppointmentRequest();
        in.setPageNumber(currentPage);
        in.setPageSize(PAGE_SIZE);
        in.setSearchQuery(mSearchQuery); // no need there
        in.setFilterBy("");  // no need there
        mViewModel.fetchPastNextAppointments(mHeaderMap,in);

    };

    @Override
    public void onDestroyView() {
        rvAppointmentsHistory.removeOnScrollListener(mOnScrollListener);
        super.onDestroyView();

    }


}
