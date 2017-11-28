package com.ingic.tanfit.ui.binders;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.ingic.tanfit.R;
import com.ingic.tanfit.activities.DockActivity;
import com.ingic.tanfit.entities.GymFeatureEnt;
import com.ingic.tanfit.helpers.BasePreferenceHelper;
import com.ingic.tanfit.ui.viewbinders.abstracts.ExpandableListViewBinder;
import com.ingic.tanfit.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 11/27/2017.
 */

public class GymDetailBinder extends ExpandableListViewBinder<String, GymFeatureEnt> {
    private DockActivity activity;
    private BasePreferenceHelper preferenceHelper;

    public GymDetailBinder(DockActivity dockActivity, BasePreferenceHelper prefHelper) {
        super(R.layout.row_item_booking_header, R.layout.row_item_special_features, R.layout.row_item_gym_images);
        activity = dockActivity;
        preferenceHelper = prefHelper;
    }

    @Override
    public BaseGroupViewHolder createGroupViewHolder(View view) {
        return new GroupViewHolder(view);
    }

    @Override
    public BaseGroupViewHolder createChildViewHolder(View view) {
        if (view.getTag(R.integer.type_feature) == null) {
            return new ChildPictureViewHolder(view);
        } else {
            return new ChildFeatureViewHolder(view);
        }
    }

    @Override
    public void bindGroupView(String entity, int groupPosition, int position, int grpPosition, View view, Activity activity, boolean isExpanded) {
        GroupViewHolder viewHolder = (GroupViewHolder) view.getTag();
        viewHolder.indicator.setVisibility(View.GONE);
        viewHolder.header.setText(entity);
    }

    @Override
    public void bindChildView(GymFeatureEnt entity, int position, int grpPosition, View view, Activity activity) {


        if (view.getTag(R.integer.type_feature) == null) {
            ChildPictureViewHolder viewHolder = (ChildPictureViewHolder) view.getTag();
            viewHolder.imgPic.setImageResource(entity.getFeatureRes());
        } else {
            ChildFeatureViewHolder featureViewHolder = (ChildFeatureViewHolder) view.getTag();
            featureViewHolder.ivFeature.setImageResource(entity.getFeatureRes());
            featureViewHolder.txtFeature.setText(entity.getFeatureTitle());
        }
    }

    @Override
    public void bindGroupView(String entity, int position, int grpPosition, int childCount, View view, Activity activity) {

    }

    @Override
    public void bindChildView(GymFeatureEnt entity, int position, int grpPosition, int size, View view, Activity activity) {

    }

    static class GroupViewHolder extends BaseGroupViewHolder {
        @BindView(R.id.header)
        AnyTextView header;
        @BindView(R.id.indicator)
        ImageView indicator;
        @BindView(R.id.view)
        View view;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ChildFeatureViewHolder extends BaseGroupViewHolder {
        @BindView(R.id.iv_feature)
        ImageView ivFeature;
        @BindView(R.id.txt_feature)
        AnyTextView txtFeature;

        ChildFeatureViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    static class ChildPictureViewHolder extends BaseGroupViewHolder {
        @BindView(R.id.img_pic)
        ImageView imgPic;

        ChildPictureViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
