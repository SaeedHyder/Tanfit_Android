package com.ingic.tanfit.ui.binders;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.ingic.tanfit.R;
import com.ingic.tanfit.activities.DockActivity;
import com.ingic.tanfit.entities.CurrentBookingEnt;
import com.ingic.tanfit.helpers.BasePreferenceHelper;
import com.ingic.tanfit.ui.viewbinders.abstracts.ExpandableListViewBinder;
import com.ingic.tanfit.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by saeedhyder on 11/25/2017.
 */

public class CurrentBookingItemBinder extends ExpandableListViewBinder<String, CurrentBookingEnt> {

    DockActivity context;

    private BasePreferenceHelper preferenceHelper;

    public CurrentBookingItemBinder(DockActivity context, BasePreferenceHelper prefHelper) {

        super(R.layout.row_item_booking_header, R.layout.row_item_booking_history);
        this.context = context;
        this.preferenceHelper = prefHelper;

    }

    @Override
    public BaseGroupViewHolder createGroupViewHolder(View view) {
        return new parentViewHolder(view);
    }

    @Override
    public childViewHolder createChildViewHolder(View view) {
        return new childViewHolder(view);
    }

    @Override
    public void bindGroupView(String entity, int groupPosition, int position, int grpPosition, View view, Activity activity, boolean isExpanded) {
        parentViewHolder parentViewHolder = (CurrentBookingItemBinder.parentViewHolder) view.getTag();

        parentViewHolder.header.setText(entity);

        if (isExpanded) {
            parentViewHolder.indicator.setImageResource(R.drawable.up_arrow);
            parentViewHolder.view.setVisibility(View.GONE);
        } else {
            parentViewHolder.indicator.setImageResource(R.drawable.down_arrow);
            parentViewHolder.view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void bindChildView(CurrentBookingEnt entity, int position, int grpPosition, View view, Activity activity) {


    }

    @Override
    public void bindGroupView(String entity, int position, int grpPosition, int childCount, View view, Activity activity) {

    }

    @Override
    public void bindChildView(CurrentBookingEnt entity, int position, int grpPosition, int size, View view, Activity activity) {
        childViewHolder childViewHolder = (CurrentBookingItemBinder.childViewHolder) view.getTag();
        childViewHolder.txtDate.setText(entity.getDate());
        childViewHolder.txtCenter.setText(entity.getCenter());
        childViewHolder.txtAddress.setText(entity.getAddress());
    }

    static class parentViewHolder extends BaseGroupViewHolder {
        @BindView(R.id.header)
        AnyTextView header;
        @BindView(R.id.indicator)
        ImageView indicator;
        @BindView(R.id.view)
        View view;


        parentViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class childViewHolder extends BaseGroupViewHolder {
        @BindView(R.id.txt_date)
        AnyTextView txtDate;
        @BindView(R.id.txt_center)
        AnyTextView txtCenter;
        @BindView(R.id.txt_address)
        AnyTextView txtAddress;

        childViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
