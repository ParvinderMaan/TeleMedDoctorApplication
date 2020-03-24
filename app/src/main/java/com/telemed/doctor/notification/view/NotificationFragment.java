package com.telemed.doctor.notification.view;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.telemed.doctor.R;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.annotation.NotificationType;
import com.telemed.doctor.dialog.AlertDialogFragment;
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
import com.telemed.doctor.notification.model.NotificationRequest;
import com.telemed.doctor.notification.model.NotificationResponse;
import com.telemed.doctor.notification.viewmodel.NotificationViewModel;
import com.telemed.doctor.util.CustomAlertTextView;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;


public class NotificationFragment extends Fragment {
    private static final int PAGE_SIZE = 10;
    private NotificationViewModel mViewModel;
    private HomeFragmentSelectedListener mFragmentListener;
    private String mAccessToken;
    private HashMap<String, String> mHeaderMap;
    private CustomAlertTextView tvAlertView;
    private ImageButton ibtnClose,ibtnBack;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean isLastPage = false;
    private boolean isListLoading = false;
    private int currentPage = 1;
    private RecyclerView rvNotification;
    private FloatingActionButton fbtnDeleteAllNotification;
    private NotificationAdapter mAdapter;
    private ProgressBar progressBar;
    private TextView tvEmptyView;
    private AlertDialogFragment mDeleteDialogFragment;

    public NotificationFragment() {
        // Required empty public constructor
    }


    public static NotificationFragment newInstance() {
        return new NotificationFragment();
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
        mAdapter = new NotificationAdapter();
        mHeaderMap = new HashMap<>();
        mHeaderMap.put("content-type", "application/json");
        mHeaderMap.put("Authorization","Bearer "+mAccessToken);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(requireActivity(), R.style.FragmentThemeOne);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        return localInflater.inflate(R.layout.fragment_notification, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(NotificationViewModel.class);
        super.onViewCreated(v, savedInstanceState);
        initView(v);
        initListener();
        initRecyclerView(v);
        initObserver();

        NotificationRequest in=new NotificationRequest();
        in.setPageNumber(currentPage);
        in.setPageSize(PAGE_SIZE);
        in.setSearchQuery("");
        in.setFilterBy(""); // no need there

        mViewModel.fetchFirstNotifications(mHeaderMap,in);




    }

    private void initListener() {
        ibtnClose.setOnClickListener(v1 -> {

            if (mFragmentListener != null)
                mFragmentListener.popTopMostFragment();
        });

        fbtnDeleteAllNotification.setOnClickListener(v1 -> {
            mDeleteDialogFragment = AlertDialogFragment.newInstance("delete all");
            mDeleteDialogFragment.show(getChildFragmentManager(), "TAG");
            mDeleteDialogFragment.setOnAlertDialogListener(new AlertDialogFragment.AlertDialogListener() {
                @Override
                public void onClickYes() {
                    mDeleteDialogFragment.dismiss();
                    mViewModel.deleteNotifications(mHeaderMap); // delete all notifications
                }

                @Override
                public void onClickNo() { }
            });

        });

    }

    private void initView(View v) {
         ibtnClose = v.findViewById(R.id.ibtn_close);
         fbtnDeleteAllNotification = v.findViewById(R.id.fbtn_delete_all);
         fbtnDeleteAllNotification.hide(); //by default
         tvAlertView = v.findViewById(R.id.tv_alert_view);
         progressBar = v.findViewById(R.id.progress_bar);
         tvEmptyView= v.findViewById(R.id.tv_empty_view);

        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorWhite),
                android.graphics.PorterDuff.Mode.SRC_IN);
    }


    private void initRecyclerView(View v) {
        rvNotification = v.findViewById(R.id.rv_notification);
        mLinearLayoutManager=new LinearLayoutManager(requireActivity());
        rvNotification.setLayoutManager(mLinearLayoutManager);
        rvNotification.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(mOnItemClickListener);
        mAdapter.setOnReloadClickListener(mOnReloadClickListener);
        rvNotification.setItemAnimator(new DefaultItemAnimator());
        rvNotification.addOnScrollListener(mOnScrollListener);

    }

    private void initObserver() {
        mViewModel.getProgress()
                .observe(getViewLifecycleOwner(), isLoading ->
                        progressBar.setVisibility(isLoading?View.VISIBLE:View.INVISIBLE));

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

        mViewModel.getNotifications()
                .observe(getViewLifecycleOwner(), lstOfAppointments -> {
                    mAdapter.addAll(lstOfAppointments);

                    if (lstOfAppointments.size() >= PAGE_SIZE) {
                        mAdapter.addFooter();
                    } else {
                        mViewModel.isLastPage(true);
                    }
                });


        mViewModel.getResultFirstNotification().observe(getViewLifecycleOwner(), response -> {

                    switch (response.getStatus()) {
                        case SUCCESS:
                            if (response.getData() != null) {
                                NotificationResponse.Data infoObj = response.getData().getData(); // adding Additional Info
                                if (infoObj.getDataList() != null && (!infoObj.getDataList().isEmpty())) {
                                    mAdapter.clearAll(); // just to make sure refreshing works ok ....
                                    mViewModel.setNotificationList(infoObj.getDataList());
                                    rvNotification.setVisibility(View.VISIBLE);
                                    tvEmptyView.setVisibility(View.GONE);
                                    fbtnDeleteAllNotification.show();
                                } else {
                                    mAdapter.clearAll();
                                    rvNotification.setVisibility(View.GONE);
                                    tvEmptyView.setVisibility(View.VISIBLE);
                                    fbtnDeleteAllNotification.hide();
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


            mViewModel.getResultDeleteNotification().observe(getViewLifecycleOwner(), response -> {

                switch (response.getStatus()) {
                    case SUCCESS:
                        if (response.getData() != null) {
                            String infoObj = response.getData().getMessage();
                               rvNotification.setVisibility(View.GONE);
                               tvEmptyView.setVisibility(View.VISIBLE);
                               mAdapter.clearAll();
                               fbtnDeleteAllNotification.hide();
                               tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                               tvAlertView.showTopAlert(infoObj);
                        }
                        break;

                    case FAILURE:
                        if (response.getErrorMsg() != null) {
                            tvAlertView.showTopAlert(response.getErrorMsg());
                        }
                        break;
                }

        });


        mViewModel.getResultNextNotification().observe(getViewLifecycleOwner(), response -> {
            switch (response.getStatus()) {
                case SUCCESS:
                    mAdapter.removeFooter();

                    if (response.getData() != null) {
                        NotificationResponse.Data infoObj = response.getData().getData(); // adding Additional Info
                        if(infoObj.getDataList()!=null && (!infoObj.getDataList().isEmpty())){
                            mViewModel.setNotificationList(infoObj.getDataList());
                        }
                    }
                    break;

                case FAILURE:
                    if (response.getErrorMsg() != null) {
                        mAdapter.updateFooter(NotificationAdapter.FooterType.ERROR);
                    }
                    break;
            }
        });


        mViewModel.getResultReadNotification().observe(getViewLifecycleOwner(), response -> {

            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
//                     tvAlertView.showTopAlert(response.getErrorMsg());
                        mAdapter.update(readNotificationPosition);
                    }
                    break;

                case FAILURE:
                    if (response.getErrorMsg() != null) {
//                     tvAlertView.showTopAlert(response.getErrorMsg());
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

    private int readNotificationPosition;
    private NotificationAdapter.OnItemClickListener mOnItemClickListener= (position, model) -> {
        this.readNotificationPosition=position;
        switch (model.getType()){
            case NotificationType.ADMIN_NOTE:
              //  mViewModel.readNotification(mHeaderMap,model.getId());
                break;

            case NotificationType.NEW_APPOINTMENT_REQUEST:
               // if(mFragmentListener!=null) mFragmentListener.showFragment("AppointmentConfirmIFragment",);
                break;

            case NotificationType.CANCELLED_APPOINTMENT:
              //  mViewModel.readNotification(mHeaderMap,model.getId());
                break;


        }
    };

    private void loadMoreItems() {
        mViewModel.setListLoading(true);
        currentPage += 1;
        NotificationRequest in=new NotificationRequest();
        in.setPageNumber(currentPage);
        in.setPageSize(PAGE_SIZE);
        in.setSearchQuery(""); // no need there
        in.setFilterBy("");  // no need there
        mViewModel.fetchNextNotifications(mHeaderMap,in);
    }

    private NotificationAdapter.OnReloadClickListener mOnReloadClickListener= () -> {
        mAdapter.updateFooter(NotificationAdapter.FooterType.LOAD_MORE);
        NotificationRequest in=new NotificationRequest();
        in.setPageNumber(currentPage);
        in.setPageSize(PAGE_SIZE);
        in.setSearchQuery(""); // no need there
        in.setFilterBy("");  // no need there
        mViewModel.fetchNextNotifications(mHeaderMap,in);

    };

    @Override
    public void onDestroyView() {
        rvNotification.removeOnScrollListener(mOnScrollListener);
        super.onDestroyView();

    }
}
