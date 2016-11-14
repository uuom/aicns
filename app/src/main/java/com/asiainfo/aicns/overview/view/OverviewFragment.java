package com.asiainfo.aicns.overview.view;


import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.asiainfo.aicns.R;
import com.asiainfo.aicns.common.util.BarUtil;
import com.asiainfo.aicns.common.view.UsageRateProgress;
import com.github.lazylibrary.util.DensityUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class OverviewFragment extends Fragment {

    UsageRateProgress urp1;
    UsageRateProgress urp2;
    UsageRateProgress urp3;
    FrameLayout rightFrame;

    public OverviewFragment() {
        // Required empty public construgit ctor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        urp1 = (UsageRateProgress) view.findViewById(R.id.urp1);
        urp2 = (UsageRateProgress) view.findViewById(R.id.urp2);
        urp3 = (UsageRateProgress) view.findViewById(R.id.urp3);
        rightFrame = (FrameLayout) view.findViewById(R.id.rightFrame);

        setWidth(urp1);
        setWidth(urp2);
        setWidth(urp3);

        urp1.setProgress(60);
        urp1.setProgressColor(getResources().getColor(R.color.md_red_500));
        urp2.setProgress(40);
        urp2.setProgressColor(getResources().getColor(R.color.md_orange_500));
        urp3.setProgress(20);
        urp3.setProgressColor(getResources().getColor(R.color.md_amber_300));

        return view;
    }

    private void setWidth(View view){
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int[] widthAndHeight = DensityUtil.getDeviceInfo(OverviewFragment.this.getActivity());
        int resultWidth = widthAndHeight[0]/3 - widthAndHeight[0]/6;
        layoutParams.width = resultWidth;
        layoutParams.height = resultWidth;
        view.setLayoutParams(layoutParams);
    }

}
