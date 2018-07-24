package com.ingic.tanfit.ui.binders;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.ingic.tanfit.R;
import com.ingic.tanfit.activities.DockActivity;
import com.ingic.tanfit.entities.Studio;
import com.ingic.tanfit.helpers.BasePreferenceHelper;
import com.ingic.tanfit.helpers.DateHelper;
import com.ingic.tanfit.interfaces.RecyclerViewItemListener;
import com.ingic.tanfit.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.CustomRecyclerView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created on 11/25/2017.
 */

public class HomeStudioBinder extends RecyclerViewBinder<Studio> implements RecyclerViewItemListener {
    private DockActivity dockActivity;
    private BasePreferenceHelper prefHelper;
    private RecyclerViewItemListener clickListner;
    private ImageLoader imageLoader;

    public HomeStudioBinder(DockActivity dockActivity, BasePreferenceHelper prefHelper, RecyclerViewItemListener clickListner) {
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

    public void bindView(final Studio entity, final int position, Object viewHolder, Context context) {

        final ViewHolder holder = (ViewHolder) viewHolder;


        Picasso.with(dockActivity)
                .load(entity.getStudioLogo())
                .placeholder(R.drawable.placeholder3)
                .into(holder.ivProfileImage);

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


        //  viewHolder.txtTime.setText(entity.getOpeningTime() + "-" + entity.getClosingTime());

        //Features
        holder.rv_features.BindRecyclerView(new StudioFeaturesBinder(this, prefHelper, dockActivity), entity.getStudioFeatures(),
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
        CircleImageView ivProfileImage;
        @BindView(R.id.txt_title)
        AnyTextView txtTitle;
        @BindView(R.id.ll_title)
        LinearLayout llTitle;
        @BindView(R.id.txt_address)
        AnyTextView txtAddress;
        @BindView(R.id.txt_time)
        AnyTextView txtTime;
        @BindView(R.id.txt_pilates)
        AnyTextView txtPilates;
        @BindView(R.id.txt_TRX)
        AnyTextView txtTRX;
        @BindView(R.id.txt_yoga)
        AnyTextView txtYoga;
        @BindView(R.id.txt_spinning)
        AnyTextView txtSpinning;
        @BindView(R.id.rv_features)
        CustomRecyclerView rv_features;
        @BindView(R.id.ll_mainframe)
        LinearLayout llMainframe;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}

