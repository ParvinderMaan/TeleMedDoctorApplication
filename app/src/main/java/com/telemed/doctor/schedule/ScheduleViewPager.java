package com.telemed.doctor.schedule;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.telemed.doctor.R;


public class ScheduleViewPager extends PagerAdapter {
    private final int TOTAL_SLIDES=12;
    private Context context;
    private LayoutInflater layoutInflater;
 //   private Integer[] mImgBackground = {R.drawable.img_checkup_intro, R.drawable.img_payment_intro, R.drawable.img_calendar_intro};


    private OnItemClickListener mOnItemClickListener;

    public ScheduleViewPager(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return TOTAL_SLIDES;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int pos) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = null;
        if (layoutInflater != null ) {
            //inflate this view
            view = layoutInflater.inflate(R.layout.list_item_schedule, null);
//            LinearLayout llChildOne = view.findViewById(R.id.ll_child_one);


//            btnSkip.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    if(mOnItemClickListener!=null)
//                        mOnItemClickListener.onItemSkipClick();
//                }
//            });









            ViewPager vp = (ViewPager) container;
            vp.addView(view,0);

        }

        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//
//        ViewPager vp = (ViewPager) container;
//        View view = (View) object;
//        vp.removeView(view);

    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }


    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener=listener;
    }

    public interface OnItemClickListener{
        void onItemNextClick();
        void onItemSkipClick();

    }
}