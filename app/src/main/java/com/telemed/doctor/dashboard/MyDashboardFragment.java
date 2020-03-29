package com.telemed.doctor.dashboard;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.telemed.doctor.R;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyDashboardFragment extends Fragment {


    private LineChart mLineChart;
    private HomeFragmentSelectedListener mFragmentListener;

    public static MyDashboardFragment  newInstance() {
       return new MyDashboardFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (HomeFragmentSelectedListener) context;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        v.findViewById(R.id.ibtn_close).setOnClickListener(v1 -> {
            if(mFragmentListener!=null)
                mFragmentListener.popTopMostFragment();
        });
        initLineChart(v);
    }

    private void initLineChart(View v) {
        mLineChart = v.findViewById(R.id.line_chart);
        // apply styling
        // holder.chart.setValueTypeface(mTf);
        mLineChart.getDescription().setEnabled(false);
        mLineChart.setDrawGridBackground(false);

        XAxis xAxis =mLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//      xAxis.setTypeface(mTf);                                   // for now !!!
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);

        YAxis leftAxis = mLineChart.getAxisLeft();                   // for now !!!
//        leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(5, false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = mLineChart.getAxisRight();
//        rightAxis.setTypeface(mTf);                                 // for now !!!
        rightAxis.setLabelCount(5, false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        // set data
        LineData mChartData = generateDataLine(1);
        mLineChart.setData((LineData) mChartData);

        // do not forget to refresh the chart
        // holder.chart.invalidate();
        mLineChart.animateX(750);
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return Line data
     */
    private LineData generateDataLine(int cnt) {

        ArrayList<Entry> values1 = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            values1.add(new Entry(i, (int) (Math.random() * 65) + 40));
        }

        LineDataSet d1 = new LineDataSet(values1, "Mr Miguel ");
        d1.setLineWidth(2.5f);
        d1.setCircleRadius(4.5f);
        d1.setDrawCircleHole(false);
        d1.setCircleHoleRadius(0.5f);
        d1.setHighLightColor(getResources().getColor(R.color.colorRed));
        d1.setColor(getResources().getColor(R.color.colorRed));
        d1.setCircleColor(getResources().getColor(R.color.colorRed));
        d1.setDrawValues(false);

        ArrayList<Entry> values2 = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            values2.add(new Entry(i, values1.get(i).getY() - 30));
        }

        LineDataSet d2 = new LineDataSet(values2, "Average ");
        d2.setLineWidth(2.5f);
        d2.setCircleRadius(4.5f);
        d2.setDrawCircleHole(false);
        d2.setHighLightColor(getResources().getColor(R.color.colorBlue));
        d2.setColor(getResources().getColor(R.color.colorBlue));
        d2.setCircleColor(getResources().getColor(R.color.colorBlue));
        d2.setDrawValues(false);

        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(d1);
        sets.add(d2);

        return new LineData(sets);
    }

}
