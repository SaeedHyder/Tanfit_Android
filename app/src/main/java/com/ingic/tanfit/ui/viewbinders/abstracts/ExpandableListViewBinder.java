package com.ingic.tanfit.ui.viewbinders.abstracts;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.ingic.tanfit.R;
import com.ingic.tanfit.global.AppConstants;

public abstract class ExpandableListViewBinder<T, E> {

    int mLayoutGroupId;
    int mLayoutChildId;
    int mLayoutChildId2;

    public ExpandableListViewBinder() {

    }

    public ExpandableListViewBinder(int layoutGroupId, int layoutChildId) {
        mLayoutGroupId = layoutGroupId;
        mLayoutChildId = layoutChildId;
    }

    public ExpandableListViewBinder(int layoutGroupId, int layoutChildId, int LayoutChildId2) {
        mLayoutGroupId = layoutGroupId;
        mLayoutChildId = layoutChildId;
        mLayoutChildId2 = LayoutChildId2;
    }

    public View createGroupView(Activity activity) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(mLayoutGroupId, null);
        view.setTag(createGroupViewHolder(view));
        return view;

    }

    public View createChildView(Activity activity) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(mLayoutChildId, null);
        view.setTag(createChildViewHolder(view));
        return view;

    }

    public View createChildView(Activity mContext, int childType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view;
        if (childType == 0) {
            view = inflater.inflate(mLayoutChildId, null);
            view.setTag(R.integer.type_feature, AppConstants.CHILD_TYPE_LIST);
        } else {
            view = inflater.inflate(mLayoutChildId2, null);
//            view.setTag(1, AppConstants.CHILD_TYPE_GALLERY);
        }

        view.setTag(createChildViewHolder(view));
        return view;
    }

    public abstract BaseGroupViewHolder createGroupViewHolder(View view);

    public abstract BaseGroupViewHolder createChildViewHolder(View view);

    /**
     * @param entity
     * @param groupPosition
     * @param position
     * @param grpPosition   In cases applicable, for e.g in expandable listview
     * @param view
     * @param activity
     * @param isExpanded
     */
    public abstract void bindGroupView(T entity, int groupPosition, int position, int grpPosition, View view, Activity activity, boolean isExpanded);

    public abstract void bindChildView(E entity, int position, int grpPosition, View view, Activity activity);

    public abstract void bindGroupView(T entity, int position, int grpPosition, int childCount, View view, Activity activity);

    public abstract void bindChildView(E entity, int position, int grpPosition, int size, View view, Activity activity);


    protected static class BaseGroupViewHolder {

    }

    protected static class BaseChildViewHolder {

    }
}

