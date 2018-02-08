package com.ingic.tanfit.ui.binders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.StudioFeature;
import com.ingic.tanfit.interfaces.RecyclerViewItemListener;
import com.ingic.tanfit.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by saeedhyder on 12/28/2017.
 */

public class StudioFeaturesBinder extends RecyclerViewBinder<StudioFeature> {

    private ImageLoader imageLoader;
    private RecyclerViewItemListener recyclerViewItemListener;

    public StudioFeaturesBinder(RecyclerViewItemListener recyclerViewItemListener) {
        super(R.layout.row_item_studio_features);
        imageLoader = ImageLoader.getInstance();
        this.recyclerViewItemListener = recyclerViewItemListener;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(StudioFeature entity, int position, Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;

        imageLoader.displayImage(entity.getIcon(),holder.ivFeaturelogo);
       holder.txtFeature.setText(entity.getFeatureName());


    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_featurelogo)
        ImageView ivFeaturelogo;
        @BindView(R.id.txt_feature)
        AnyTextView txtFeature;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
