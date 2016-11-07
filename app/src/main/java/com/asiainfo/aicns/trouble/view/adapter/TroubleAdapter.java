package com.asiainfo.aicns.trouble.view.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asiainfo.aicns.R;
import com.asiainfo.aicns.bean.TroubleBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uuom on 16-10-18.
 */
public class TroubleAdapter extends RecyclerView.Adapter<TroubleAdapter.ViewHolder> {

    List<TroubleBean> dataList = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trouble_list,parent, false);
        TextView typeTV = (TextView) view.findViewById(R.id.troubleType);
        LinearLayout troubleCountLayout = (LinearLayout) view.findViewById(R.id.troubleCountLayout);
        typeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClickTroubleType(v);
            }
        });
        troubleCountLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClickTroubleCount(v);
            }
        });
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        TroubleBean tb = dataList.get(position);
        viewHolder.troubleType.setText(tb.troubleTypeZHName);
        viewHolder.troubleCount.setText(tb.troubleCount);
        GradientDrawable myGrad = (GradientDrawable)viewHolder.troubleCount.getBackground();
        myGrad.setColor(Color.parseColor(tb.troubleColor));
        viewHolder.troubleType.setTag(tb);
        viewHolder.troubleCountLayout.setTag(tb);
    }

    OnClickListener onClickListener;
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClickTroubleType(View view);
        void onClickTroubleCount(View view);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setDataList(List<TroubleBean> dataList) {
        this.dataList = dataList;
        this.notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView troubleType;
        TextView troubleCount;

        LinearLayout troubleCountLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            troubleType = (TextView) itemView.findViewById(R.id.troubleType);
            troubleCount = (TextView) itemView.findViewById(R.id.troubleCount);
            troubleCountLayout = (LinearLayout) itemView.findViewById(R.id.troubleCountLayout);
        }
    }
}
