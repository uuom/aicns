package com.asiainfo.aicns.overview.view;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asiainfo.aicns.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OverviewFragment extends Fragment {

    PieChart mPieChart;

    public OverviewFragment() {
        // Required empty public construgit ctor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_overview, container, false);
        mPieChart = (PieChart) view.findViewById(R.id.chart);

        mPieChart.setUsePercentValues(false);
        mPieChart.getDescription().setEnabled(false);

        mPieChart.setHoleRadius(50f); //内圆半径
        mPieChart.setTransparentCircleRadius(60f);

        mPieChart.setDrawCenterText(true);
        mPieChart.setCenterText("链路概况");  //饼状图中间的文字
        mPieChart.setNoDataText("暂无数据");
        mPieChart.animateXY(1000, 1000);  //设置动画
        mPieChart.setDrawEntryLabels(false); //是否显示值名称
        mPieChart.setRotationEnabled(false); // 可以手动旋转
        mPieChart.getLegend().setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);

        List<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add(new PieEntry(119, "正常"));
        entries.add(new PieEntry(5, "故障"));
        PieDataSet dataSet = new PieDataSet(entries, " 链路状态");
        ArrayList<Integer> colors = new ArrayList<Integer>();
        // 饼图颜色
        colors.add(Color.rgb(0, 230, 50));
        colors.add(Color.rgb(255, 0, 0));
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        mPieChart.setData(data);
//        mPieChart.invalidate();

        return view;
    }

}
