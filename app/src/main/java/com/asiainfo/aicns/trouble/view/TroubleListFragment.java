package com.asiainfo.aicns.trouble.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asiainfo.aicns.R;
import com.asiainfo.aicns.bean.TroubleDetailBean;
import com.asiainfo.aicns.common.view.AIRecyclerView;
import com.asiainfo.aicns.trouble.event.TroubleLevelChangeEvent;
import com.asiainfo.aicns.trouble.presenter.TroubleListPresenter;
import com.asiainfo.aicns.trouble.presenter.TroubleListPresenterImpl;
import com.asiainfo.aicns.trouble.view.adapter.TroubleListAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.List;


public class TroubleListFragment extends Fragment implements TroubleListView{

    private static final String TROUBLE_TYPE_KEY = "com.asiainfo.aicns.trouble.view.TroubleListFragment.trouble_type_key";
    private Integer troubleLevel;

    private TroubleListPresenter troubleListPresenter;
    private TroubleListAdapter mTroubleListAdapter;

    AIRecyclerView mAIRecyclerView;

    public static TroubleListFragment newInstance(Integer troubleLevel){
        TroubleListFragment troubleChartFragment = new TroubleListFragment();
        Bundle args = new Bundle();
        args.putSerializable("troubleLevel", troubleLevel);
        troubleChartFragment.setArguments(args);
        return troubleChartFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Serializable troubleLevel = this.getArguments().getSerializable("troubleLevel");
        if (troubleLevel != null){
            this.troubleLevel = (Integer) troubleLevel;
        }
        EventBus.getDefault().register(this);
        Log.d("TroubleListFragment", "onCreate-注册");
        troubleListPresenter = new TroubleListPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trouble_list, container, false);

        mAIRecyclerView = (AIRecyclerView) view.findViewById(R.id.troubleList);
        mTroubleListAdapter = new TroubleListAdapter(this.getActivity());
        mTroubleListAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TroubleDetailBean tdb = (TroubleDetailBean) v.getTag();
                Intent intent = new Intent(TroubleListFragment.this.getActivity(), TroubleDetailActivity.class);
                intent.putExtra("faultId", tdb.getTroubleId());
                TroubleListFragment.this.getActivity().startActivity(intent);
            }
        });
        mAIRecyclerView.setAdapter(mTroubleListAdapter);
        mAIRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mAIRecyclerView.setItemDecoration(new HorizontalDividerItemDecoration.Builder(this.getActivity()).build());
        mAIRecyclerView.setSwipeRefreshLayoutColor(this.getActivity().getResources().getColor(R.color.md_blue_500));
        mAIRecyclerView.setOnScrollBottomListener(new AIRecyclerView.OnScrollBottomListener() {
            @Override
            public void onScrollBottomListener(int currentPage) {
                troubleListPresenter.addTroubleListData(currentPage, troubleLevel);
            }
        });
        mAIRecyclerView.setOnRefreshListener(new AIRecyclerView.OnRefreshListener() {
            @Override
            public void onRefreshHelper(SwipeRefreshLayout refreshLayout) {
                troubleListPresenter.refreshTroubleListData(troubleLevel);
            }
        });

        troubleListPresenter.refreshTroubleListData(troubleLevel);

        return view;
    }


    @Override
    public void showRefreshLayout() {
        mAIRecyclerView.getSwipeRefreshLayout().setRefreshing(true);
    }

    @Override
    public void hideRefreshLayout() {
        mAIRecyclerView.getSwipeRefreshLayout().setRefreshing(false);
    }

    @Override
    public void setData2RecyclerView(List<TroubleDetailBean> datas) {
        mTroubleListAdapter.setDataList(datas);
    }

    @Override
    public void addData2RecyclerView(List<TroubleDetailBean> datas) {
        mTroubleListAdapter.addDataList(datas);
    }

    @Override
    public void setCurrentPage(int page) {
        mAIRecyclerView.setCurrentPage(page);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTroubleLevelChangeEvent(TroubleLevelChangeEvent event){
        this.troubleLevel = event.troubleLevel;
        troubleListPresenter.refreshTroubleListData(event.troubleLevel);
    }
}
