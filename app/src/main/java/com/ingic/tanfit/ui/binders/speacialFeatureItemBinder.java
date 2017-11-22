package com.ingic.tanfit.ui.binders;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.ingic.tanfit.R;
import com.ingic.tanfit.activities.DockActivity;
import com.ingic.tanfit.entities.SpecialFeatureEnt;
import com.ingic.tanfit.helpers.BasePreferenceHelper;
import com.ingic.tanfit.ui.viewbinders.abstracts.ViewBinder;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by saeedhyder on 11/22/2017.
 */

public class speacialFeatureItemBinder extends ViewBinder<SpecialFeatureEnt> {

    private DockActivity dockActivity;
    private BasePreferenceHelper prefHelper;
    private ImageLoader imageLoader;


    public speacialFeatureItemBinder(DockActivity dockActivity, BasePreferenceHelper prefHelper) {
        super(R.layout.row_item_special_features);
        this.dockActivity = dockActivity;
        this.prefHelper = prefHelper;
        this.imageLoader = ImageLoader.getInstance();
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(SpecialFeatureEnt entity, int position, int grpPosition, View view, Activity activity) {

        final speacialFeatureItemBinder.ViewHolder viewHolder = (speacialFeatureItemBinder.ViewHolder) view.getTag();

        imageLoader.displayImage(entity.getImage(),viewHolder.ivFeature);
        viewHolder.txtFeature.setText(entity.getFeature()+"");

    }

    static class ViewHolder extends BaseViewHolder{
        @BindView(R.id.iv_feature)
        ImageView ivFeature;
        @BindView(R.id.txt_feature)
        AnyTextView txtFeature;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
