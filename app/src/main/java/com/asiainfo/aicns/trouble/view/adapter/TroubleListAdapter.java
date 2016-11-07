package com.asiainfo.aicns.trouble.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asiainfo.aicns.R;
import com.asiainfo.aicns.bean.TroubleDetailBean;
import com.asiainfo.aicns.common.view.AIRecyclerView;
import com.asiainfo.aicns.common.view.BorderTextView;

/**
 * Created by uuom on 16-11-3.
 */
public class TroubleListAdapter extends AIRecyclerView.AIAdapter {

    private Context context;
    public TroubleListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateDataItemViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_troublelist_list, parent ,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null){
                    onClickListener.onClick(v);
                }
            }
        });
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    private View.OnClickListener onClickListener;
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public void onBindDataItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        TroubleDetailBean tdb = (TroubleDetailBean) dataList.get(position);
        ViewHolder vh = (ViewHolder) holder;
        vh.tv_troubleLevel.setText(tdb.getTroubleLevel());
        vh.tv_troubleLevel.setTextColor(Color.parseColor(tdb.getTroubleLevelColor()));
        vh.tv_troubleType.setText(tdb.getTroubleType());
        vh.tv_troubleTime.setText(tdb.getTroubleTime());
        vh.tv_domainName.setText(tdb.getDomainName());
        vh.tv_troubleDeviceName.setText(tdb.getTroubleDeviceName());
        vh.tv_troubleDescription.setText(tdb.getTroubleDescription());
        vh.itemView.setTag(tdb);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        BorderTextView tv_troubleLevel;
        BorderTextView tv_troubleType;
        TextView tv_troubleTime;
        TextView tv_domainName;
        TextView tv_troubleDeviceName;
        TextView tv_troubleDescription;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_troubleLevel = (BorderTextView) itemView.findViewById(R.id.btv_troubleLevel);
            tv_troubleType = (BorderTextView) itemView.findViewById(R.id.btv_troubleType);
            tv_troubleTime = (TextView) itemView.findViewById(R.id.tv_troubleTime);
            tv_domainName = (TextView) itemView.findViewById(R.id.tv_domainName);
            tv_troubleDeviceName = (TextView) itemView.findViewById(R.id.tv_troubleDeviceName);
            tv_troubleDescription = (TextView) itemView.findViewById(R.id.tv_troubleDescription);
        }
    }
}
