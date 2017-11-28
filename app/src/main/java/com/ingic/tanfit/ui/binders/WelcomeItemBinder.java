package com.ingic.tanfit.ui.binders;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ingic.tanfit.R;
import com.ingic.tanfit.activities.DockActivity;
import com.ingic.tanfit.helpers.BasePreferenceHelper;
import com.ingic.tanfit.ui.viewbinders.abstracts.ViewBinder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.rd.PageIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by saeedhyder on 11/20/2017.
 */

public class WelcomeItemBinder extends ViewBinder<String> {

    private DockActivity dockActivity;
    private BasePreferenceHelper prefHelper;
    private ImageLoader imageLoader;


    public WelcomeItemBinder(DockActivity dockActivity, BasePreferenceHelper prefHelper) {
        super(R.layout.row_item_card);
        this.dockActivity = dockActivity;
        this.prefHelper = prefHelper;
        this.imageLoader = ImageLoader.getInstance();
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(String entity, int position, int grpPosition, View view, Activity activity) {

        final ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.logo.setImageResource(R.drawable.logo);
        viewHolder.ivMainImage.setImageResource(R.drawable.gym);
        viewHolder.pageIndicatorView.setSelection(position);
        viewHolder.pageIndicatorView.setSelection(position);

        viewHolder.pageIndicatorView.setCount(4);
        viewHolder.pageIndicatorView.setSelectedColor(R.color.app_blue);
        viewHolder.pageIndicatorView.setUnselectedColor(R.color.app_text_gray);
//        imageLoader.displayImage(AppConstants.DRAWABLE_PATH + R.drawable.ic_launcher, viewHolder.logo);
//        imageLoader.displayImage(AppConstants.DRAWABLE_PATH + R.drawable.ic_launcher, viewHolder.ivMainImage);
        viewHolder.txtHeading.setText("Donec ultrices scelerisque nisi");
        viewHolder.txtDescription.setText("Nulla ei orci aliquam,condimentum tellus eaac,cursus tellius,In magna eros luctus non");


    }


    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.logo)
        ImageView logo;
        @BindView(R.id.iv_mainImage)
        ImageView ivMainImage;
        @BindView(R.id.pageIndicatorView)
        PageIndicatorView pageIndicatorView;
        @BindView(R.id.txt_heading)
        TextView txtHeading;
        @BindView(R.id.txt_description)
        TextView txtDescription;
        @BindView(R.id.card_view)
        CardView cardView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
