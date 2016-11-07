package com.asiainfo.aicns.trouble.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.asiainfo.aicns.R;
import com.asiainfo.aicns.bean.TroubleBean;
import com.asiainfo.aicns.trouble.event.TroubleLevelChangeEvent;
import com.asiainfo.aicns.trouble.presenter.TroublePresenter;
import com.asiainfo.aicns.trouble.presenter.TroublePresenterImpl;
import com.asiainfo.aicns.trouble.view.adapter.TroubleAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by uuom on 16-10-31.
 */
public class TroubleFragment extends Fragment implements TroubleView{

    RecyclerView mRecyclerView;
    SwipeRefreshLayout refreshLayout;
    FrameLayout rightFrame;

    private boolean rightFrameIsChart = true; //默认显示图表页面

    TroubleAdapter mTroubleAdapter;
    TroublePresenter troublePresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        troublePresenter = new TroublePresenterImpl(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_trouble, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_troubleList);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        rightFrame = (FrameLayout) view.findViewById(R.id.rightFrame);

        this.getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.rightFrame, TroubleChartFragment.newInstance(null))
                .commit();

        //设置故障列表数据和事件监听
        setRecyclerView();

        refreshLayout.setColorSchemeColors(this.getResources().getColor(R.color.md_blue_500));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                troublePresenter.refreshTroubleData();
            }
        });

        troublePresenter.refreshTroubleData();
        return view;
    }

    private void setRecyclerView() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this.getActivity()).build());
        //事件监听
        mTroubleAdapter = new TroubleAdapter();
        mTroubleAdapter.setOnClickListener(new TroubleAdapter.OnClickListener() {
            @Override
            public void onClickTroubleType(View view) {
                TroubleBean tb = (TroubleBean) view.getTag();
                Integer troubleLevel = tb.troubleType==null?null:new Integer(tb.troubleType);
                if (rightFrameIsChart){
                    EventBus.getDefault().post(new TroubleLevelChangeEvent(troubleLevel));
                }else{
                    rightFrameIsChart = true;
                    TroubleFragment.this.getChildFragmentManager()
                            .beginTransaction()
                            .replace(R.id.rightFrame, TroubleChartFragment.newInstance(troubleLevel))
                            .commit();
                }
            }

            @Override
            public void onClickTroubleCount(View view) {
                TroubleBean tb = (TroubleBean) view.getTag();
                Integer troubleLevel = tb.troubleType==null?null:new Integer(tb.troubleType);
                if(rightFrameIsChart){
                    rightFrameIsChart = false;
                    TroubleFragment.this.getChildFragmentManager()
                            .beginTransaction()
                            .replace(R.id.rightFrame, TroubleListFragment.newInstance(troubleLevel))
                            .commit();
                }else{
                    EventBus.getDefault().post(new TroubleLevelChangeEvent(troubleLevel));
                }
            }
        });
        mRecyclerView.setAdapter(mTroubleAdapter);
    }

    @Override
    public void showErrorMessage(String msg) {
        Toast.makeText(this.getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setNewData2RecyclerView(List<TroubleBean> dataList) {
        mTroubleAdapter.setDataList(dataList);
    }

    @Override
    public void setRefreshLayoutShow(final boolean b) {
        refreshLayout.setRefreshing(b);
    }
}
