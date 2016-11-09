package com.asiainfo.aicns.trouble.view;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

import com.asiainfo.aicns.R;
import com.asiainfo.aicns.bean.TroubleMoreDetailBean;
import com.asiainfo.aicns.trouble.presenter.TroubleDetailPresenter;
import com.asiainfo.aicns.trouble.presenter.TroubleDetailPresenterImpl;

public class TroubleDetailActivity extends AppCompatActivity implements TroubleDetailView {

    Toolbar toolbar;
    SwipeRefreshLayout refreshLayout;

    private String faultId;
    private TroubleDetailPresenter troubleDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trouble_detail);
        faultId = getIntent().getStringExtra("faultId");
        troubleDetailPresenter = new TroubleDetailPresenterImpl(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("故障明细");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setColorSchemeColors(this.getResources().getColor(R.color.md_blue_500));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                troubleDetailPresenter.refreshTroubleDetail(faultId);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        troubleDetailPresenter.refreshTroubleDetail(faultId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setRefreshLayout(boolean b) {
        refreshLayout.setRefreshing(b);
    }

    @Override
    public void setTroubleDetail2View(TroubleMoreDetailBean troubleMoreDetailBean) {
        ((EditText)findViewById(R.id.equipid)).setText(troubleMoreDetailBean.getEquipid());
        ((EditText)findViewById(R.id.equipname)).setText(troubleMoreDetailBean.getEquipname());
        ((EditText)findViewById(R.id.domainid)).setText(troubleMoreDetailBean.getDomainid());
        ((EditText)findViewById(R.id.faultid)).setText(troubleMoreDetailBean.getFaultid());
        ((EditText)findViewById(R.id.faultip)).setText(troubleMoreDetailBean.getFaultip());
        ((EditText)findViewById(R.id.severity)).setText(troubleMoreDetailBean.getSeverity());
        ((EditText)findViewById(R.id.faulttype)).setText(troubleMoreDetailBean.getFaulttype());
        ((EditText)findViewById(R.id.faultdesc)).setText(troubleMoreDetailBean.getFaultdesc());
        ((EditText)findViewById(R.id.firsttime)).setText(troubleMoreDetailBean.getFirsttime());
        ((EditText)findViewById(R.id.lasttime)).setText(troubleMoreDetailBean.getLasttime());
        ((EditText)findViewById(R.id.agent)).setText(troubleMoreDetailBean.getAgent());
        ((EditText)findViewById(R.id.faultkey)).setText(troubleMoreDetailBean.getFaultkey());
        ((EditText)findViewById(R.id.ifname)).setText(troubleMoreDetailBean.getIfname());
        ((EditText)findViewById(R.id.parent)).setText(troubleMoreDetailBean.getParent());
        ((EditText)findViewById(R.id.oseverity)).setText(troubleMoreDetailBean.getOseverity());
        ((EditText)findViewById(R.id.ofaulttype)).setText(troubleMoreDetailBean.getOfaulttype());
        ((EditText)findViewById(R.id.faultstatus)).setText(troubleMoreDetailBean.getFaultstatus());
        ((EditText)findViewById(R.id.count)).setText(troubleMoreDetailBean.getCount());
        ((EditText)findViewById(R.id.inserttime)).setText(troubleMoreDetailBean.getInserttime());
        ((EditText)findViewById(R.id.cleartime)).setText(troubleMoreDetailBean.getCleartime());
        ((EditText)findViewById(R.id.confirmflag)).setText(troubleMoreDetailBean.getConfirmflag()=="1"?"是":"否");
        ((EditText)findViewById(R.id.confirmtime)).setText(troubleMoreDetailBean.getConfirmtime());
        ((EditText)findViewById(R.id.confirmuserid)).setText(troubleMoreDetailBean.getConfirmuserid());
        ((EditText)findViewById(R.id.missionflag)).setText(troubleMoreDetailBean.getMissionflag()=="1"?"是":"否");
        ((EditText)findViewById(R.id.missiontime)).setText(troubleMoreDetailBean.getMissiontime());
        ((EditText)findViewById(R.id.missionuserid)).setText(troubleMoreDetailBean.getMissionuserid());
    }
}
