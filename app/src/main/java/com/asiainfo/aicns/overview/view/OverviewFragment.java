package com.asiainfo.aicns.overview.view;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allen.library.SuperTextView;
import com.asiainfo.aicns.R;
import com.asiainfo.aicns.bean.OverviewBean;
import com.asiainfo.aicns.common.view.UsageRateProgress;
import com.asiainfo.aicns.overview.presenter.OverviewPresenter;
import com.asiainfo.aicns.overview.presenter.OverviewPresenterImpl;
import com.github.lazylibrary.util.DensityUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class OverviewFragment extends Fragment implements OverviewView {

    UsageRateProgress urp1;
    UsageRateProgress urp2;
    UsageRateProgress urp3;
    SuperTextView st_allLink;
    SuperTextView st_abnormalLink;
    SuperTextView st_allDev;
    SuperTextView st_abnormalDev;

    FloatingActionButton fab_button;

    private OverviewPresenter overviewPresenter;

    public OverviewFragment() {
        // Required empty public construgit ctor
        overviewPresenter = new OverviewPresenterImpl(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        urp1 = (UsageRateProgress) view.findViewById(R.id.urp1);
        urp2 = (UsageRateProgress) view.findViewById(R.id.urp2);
        urp3 = (UsageRateProgress) view.findViewById(R.id.urp3);
        fab_button = (FloatingActionButton) view.findViewById(R.id.fab_button);
        st_allLink = (SuperTextView) view.findViewById(R.id.st_allLink);
        st_abnormalLink = (SuperTextView) view.findViewById(R.id.st_abnormalLink);
        st_allDev = (SuperTextView) view.findViewById(R.id.st_allDev);
        st_abnormalDev = (SuperTextView) view.findViewById(R.id.st_abnormalDev);

        setWidth(urp1);
        setWidth(urp2);
        setWidth(urp3);

        fab_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overviewPresenter.refreshOverview();
            }
        });

        overviewPresenter.refreshOverview();

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

    @Override
    public void isRefreshing(boolean b) {

    }

    @Override
    public void updateOverviewData(OverviewBean ob) {

        int a = ob.getAbnormalDev()/ob.getAllDev();
        urp1.setProgress(a<1?1:a);
        urp1.setProgressColor(getProgressColor(a));
        urp1.setVisibility(View.VISIBLE);
        st_allLink.setRightString(ob.getAllLink()+"");
        st_abnormalLink.setRightString(ob.getAbnormalLink()+"");

        int b = ob.getAbnormalLink()/ob.getAllLink();
        urp2.setProgress(b<1?1:b);
        urp2.setProgressColor(getProgressColor(b));
        urp2.setVisibility(View.VISIBLE);
        st_allDev.setRightString(ob.getAllDev()+"");
        st_abnormalDev.setRightString(ob.getAbnormalDev()+"");

        //计费
        urp3.setProgress(20);
        urp3.setProgressColor(getResources().getColor(R.color.md_amber_300));
        urp3.setVisibility(View.VISIBLE);
    }

    private int getProgressColor(int value){
        int result = getResources().getColor(R.color.md_red_100);
        if (value >= 60){
            result = getResources().getColor(R.color.md_red_600);
        }else if (value >= 40){
            result = getResources().getColor(R.color.md_amber_400);
        }else if(value >= 20){
            result = getResources().getColor(R.color.md_amber_200);
        }
        return result;
    }
}
