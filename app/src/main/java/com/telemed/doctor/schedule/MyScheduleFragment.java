package com.telemed.doctor.schedule;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.telemed.doctor.R;
import com.telemed.doctor.home.HomeActivity;
import com.telemed.doctor.util.NonSwipeViewPager;


public class MyScheduleFragment extends Fragment {

    private NonSwipeViewPager mViewPager;
    private ImageButton ibtnBackward,ibtnForward;
    private MyScheduleViewPagerAdapter mViewPagerAdapter;
    private RecyclerView rvWeeklySchedule;
    private WeeklyScheduleAdapter mAdapter;
    private static int currentVisibleItem=0;
    private Button btnSynchronizeSchedule;

    public static MyScheduleFragment newInstance() {
        return new MyScheduleFragment() ;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);



        ibtnBackward = v.findViewById(R.id.ibtn_backward);
        ibtnForward = v.findViewById(R.id.ibtn_forward);
        ibtnBackward.setOnClickListener(v1 -> {
//                int index = mViewPager.getCurrentItem();
//                mViewPager.setCurrentItem(--index);
            rvWeeklySchedule.smoothScrollToPosition(--currentVisibleItem);
        });

        ibtnForward.setOnClickListener(v12 -> {
//                int index = mViewPager.getCurrentItem();
//                mViewPager.setCurrentItem(++index);

            rvWeeklySchedule.smoothScrollToPosition(++currentVisibleItem);
        });

        //
        ImageButton ibtnClose = v.findViewById(R.id.ibtn_close);
        ibtnClose.setOnClickListener(v13 -> {
            if(getActivity()!=null){
                ((HomeActivity)getActivity()).popTopMostFragment();
            }

        });
        initRecyclerView(v);


        btnSynchronizeSchedule = v.findViewById(R.id.btn_synchronize_schedule);
        btnSynchronizeSchedule.setOnClickListener(v14 -> {

            if(getActivity()!=null){
                ((HomeActivity)getActivity()).showScheduleSychronizeFragment();
            }
        });
    }




    private void initRecyclerView(View v) {
        rvWeeklySchedule =v.findViewById(R.id.rv_weekly_schedule);
        rvWeeklySchedule.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager=new LinearLayoutManager(getActivity());
//      mLinearLayoutManager.setScrolllEnabled(true); no call
//      mLinearLayoutManager.getClass().getMethod("setScrollEnabled").invoke(mLinearLayoutManager);


        mLinearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rvWeeklySchedule.setLayoutManager(mLinearLayoutManager);

         mAdapter=new WeeklyScheduleAdapter();
        rvWeeklySchedule.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(position -> {
//                Toast.makeText(getActivity(), "hii", Toast.LENGTH_SHORT).show();
//            if(getActivity() !=null){
//                ((HomeActivity)getActivity()).showMedicalRecordFragment();
//
//            }
        });

         PagerSnapHelper snapHelper = new PagerSnapHelper();
         snapHelper.attachToRecyclerView(rvWeeklySchedule);



        //... initialization
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
                return super.getSwipeThreshold(viewHolder);
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                Toast.makeText(getActivity(), ""+swipeDir, Toast.LENGTH_SHORT).show();

            }


        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rvWeeklySchedule);
    }


}
