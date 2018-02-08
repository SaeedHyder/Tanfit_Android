package com.ingic.tanfit.ui.binders;

import android.app.Activity;
import android.view.View;

import com.ingic.tanfit.R;
import com.ingic.tanfit.activities.DockActivity;
import com.ingic.tanfit.entities.TimingEnt;
import com.ingic.tanfit.helpers.BasePreferenceHelper;
import com.ingic.tanfit.ui.viewbinders.abstracts.ExpandableListViewBinder;
import com.ingic.tanfit.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 11/28/2017.
 */

public class GymTimingBinder extends ExpandableListViewBinder<String, TimingEnt> {
    public GymTimingBinder() {
        super(R.layout.row_item_timing_head, R.layout.row_item_timing_child);
    }

    @Override
    public BaseGroupViewHolder createGroupViewHolder(View view) {
        return new GroupViewHolder(view);
    }

    @Override
    public BaseGroupViewHolder createChildViewHolder(View view) {
        return new ChildViewholder(view);
    }

    @Override
    public void bindGroupView(String entity, int groupPosition, int position, int grpPosition, View view, Activity activity, boolean isExpanded) {
        GroupViewHolder holder = (GroupViewHolder) view.getTag();
        holder.txtDayTitle.setText(entity);
    }

    @Override
    public void bindChildView(TimingEnt entity, int position, int grpPosition, View view, Activity activity) {

    }

    @Override
    public void bindGroupView(String entity, int position, int grpPosition, int childCount, View view, Activity activity) {

    }

    @Override
    public void bindChildView(TimingEnt entity, int position, int grpPosition, int size, View view, Activity activity) {

        ChildViewholder childViewholder = (ChildViewholder) view.getTag();
        childViewholder.txtMenTime.setText(entity.getMenTime());
        childViewholder.txtWomenTime.setText(entity.getWomenTime());
    }

    static class GroupViewHolder extends BaseGroupViewHolder {
        @BindView(R.id.txt_day_title)
        AnyTextView txtDayTitle;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ChildViewholder extends BaseGroupViewHolder {
        @BindView(R.id.txt_men_time)
        AnyTextView txtMenTime;
        @BindView(R.id.txt_women_time)
        AnyTextView txtWomenTime;

        ChildViewholder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
