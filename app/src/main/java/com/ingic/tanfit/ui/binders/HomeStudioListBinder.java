package com.ingic.tanfit.ui.binders;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.ingic.tanfit.R;
import com.ingic.tanfit.activities.DockActivity;
import com.ingic.tanfit.entities.Studio;
import com.ingic.tanfit.helpers.BasePreferenceHelper;
import com.ingic.tanfit.helpers.DateHelper;
import com.ingic.tanfit.interfaces.RecyclerViewItemListener;
import com.ingic.tanfit.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.ingic.tanfit.ui.viewbinders.abstracts.ViewBinder;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.CustomRecyclerView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;


public class HomeStudioListBinder extends ViewBinder<Studio> implements RecyclerViewItemListener {
    private DockActivity dockActivity;
    private BasePreferenceHelper prefHelper;
    private RecyclerViewItemListener clickListner;
    private ImageLoader imageLoader;

    public HomeStudioListBinder(DockActivity dockActivity, BasePreferenceHelper prefHelper, RecyclerViewItemListener clickListner) {
        super(R.layout.row_item_home_studio);
        this.dockActivity = dockActivity;
        this.prefHelper = prefHelper;
        imageLoader = ImageLoader.getInstance();
        this.clickListner = clickListner;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }


    public void bindView(final Studio entity, final int position, int grpPosition, View view, Activity activity) {

        final ViewHolder holder = (ViewHolder) view.getTag();

        if (entity.getStudioLogo() != null && !entity.getStudioLogo().equals("")) {
            Glide.with(dockActivity)
                    .load(entity.getStudioLogo())
                    .apply(bitmapTransform(new CircleCrop()))
                    .apply(new RequestOptions().placeholder(R.drawable.placeholder3))
                    .into(holder.ivProfileImage);
        }

        if (prefHelper.isLanguagePersian()) {
            holder.txtTitle.setText(entity.getStudioNamePer() + "");
            if (entity.getAddressPer() != null) {
                holder.txtAddress.setText(entity.getAddressPer() + "");
            } else {
                holder.txtAddress.setText("-");
            }
            holder.txtTime.setText(DateHelper.getFormatedDate("HH:mm:ss", "HH:mm", entity.getOpeningTimePr()) + "-" + DateHelper.getFormatedDate("HH:mm:ss", "HH:mm", entity.getClosingTimePr()));

        } else {
            holder.txtTitle.setText(entity.getStudioNameEng() + "");
            if (entity.getAddressEng() != null) {
                holder.txtAddress.setText(entity.getAddressEng() + "");
            } else {
                holder.txtAddress.setText("-");
            }
            holder.txtTime.setText(DateHelper.getFormatedDate("HH:mm:ss", "HH:mm", entity.getOpeningTime()) + "-" + DateHelper.getFormatedDate("HH:mm:ss", "HH:mm", entity.getClosingTime()));

        }

        holder.rvFeatures.BindRecyclerView(new StudioFeaturesBinder(this, prefHelper, dockActivity), entity.getStudioFeatures(),
                new LinearLayoutManager(dockActivity, LinearLayoutManager.HORIZONTAL, false)
                , new DefaultItemAnimator());

        holder.llMainframe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListner.onRecyclerItemClicked(entity, position);
            }
        });

    }

    @Override
    public void onRecyclerItemClicked(Object Ent, int position) {

    }


    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_profileImage)
        ImageView ivProfileImage;
        @BindView(R.id.txt_title)
        AnyTextView txtTitle;
        @BindView(R.id.ll_title)
        LinearLayout llTitle;
        @BindView(R.id.txt_address)
        AnyTextView txtAddress;
        @BindView(R.id.txt_time)
        AnyTextView txtTime;
        @BindView(R.id.rv_features)
        CustomRecyclerView rvFeatures;
        @BindView(R.id.ll_mainframe)
        LinearLayout llMainframe;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
