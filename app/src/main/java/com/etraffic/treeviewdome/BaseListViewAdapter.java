package com.etraffic.treeviewdome;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public abstract class BaseListViewAdapter<VH extends BaseListViewAdapter.BaseListViewHolder> extends BaseAdapter {

    public final void notifyItemChanged(ListView listView, int position) {
        int visiblePosition = listView.getFirstVisiblePosition();
        if (position - visiblePosition >= 0) {
            View view = listView.getChildAt(position - visiblePosition);
            if (view != null) {
                VH viewHolder = (VH) view.getTag();
                onBindViewHolder(viewHolder, position);
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VH holder;
        if (convertView == null) {
            int itemType = getItemViewType(position);
            holder = onCreateViewHolder(parent, itemType);
            holder.mItemViewType = itemType;
            convertView = holder.itemView;
            convertView.setTag(holder);
        } else {
            holder = (VH) convertView.getTag();
        }
        onBindViewHolder(holder, position);
        return convertView;
    }

    protected abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    protected abstract void onBindViewHolder(VH holder, int position);

    public static class BaseListViewHolder {

        public final View itemView;
        int mItemViewType;

        public BaseListViewHolder(View itemView) {
            this.itemView = itemView;
        }
    }
}
