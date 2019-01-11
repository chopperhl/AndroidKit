package com.chopperhl.androidkit.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.chopperhl.androidkit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author chopperhl
 * Date 10/9/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public class SheetAdapter extends RecyclerView.Adapter<SheetAdapter.LocalViewHolder> {
    public interface OnItemClickListener {
        void onClick(int index, Object obj);
    }

    private OnItemClickListener mOnItemClickListener;

    private List sheetData = new ArrayList<>();

    public SheetAdapter() {
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setSheetData(List sheetData) {
        this.sheetData.clear();
        this.sheetData.addAll(sheetData);
    }


    @NonNull
    @Override
    public LocalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LocalViewHolder holder = LocalViewHolder.createViewHolder(parent);
        holder.itemView.setOnClickListener(v -> {
            if (mOnItemClickListener != null) {
                Object obj = holder.itemView.getTag();
                int index = sheetData.indexOf(obj);
                mOnItemClickListener.onClick(index, obj);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LocalViewHolder holder, int position) {
        holder.mTvContent.setText(sheetData.get(position).toString());
        holder.itemView.setTag(sheetData.get(position));
    }

    @Override
    public int getItemCount() {
        return sheetData.size();
    }

    public static class LocalViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_content)
        TextView mTvContent;

        private LocalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public static LocalViewHolder createViewHolder(ViewGroup viewGroup) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_sheet, viewGroup, false);
            return new LocalViewHolder(view);
        }
    }
}
