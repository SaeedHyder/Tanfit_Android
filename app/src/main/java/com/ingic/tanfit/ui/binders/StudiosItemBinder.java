package com.ingic.tanfit.ui.binders;

import android.app.Activity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ingic.tanfit.R;
import com.ingic.tanfit.activities.DockActivity;
import com.ingic.tanfit.entities.Studio;
import com.ingic.tanfit.helpers.BasePreferenceHelper;
import com.ingic.tanfit.helpers.DateHelper;
import com.ingic.tanfit.interfaces.RecyclerViewItemListener;
import com.ingic.tanfit.ui.viewbinders.abstracts.ViewBinder;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.CustomRecyclerView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by saeedhyder on 11/22/2017.
 */

public class StudiosItemBinder extends ViewBinder<Studio> implements RecyclerViewItemListener {

    private DockActivity dockActivity;
    private BasePreferenceHelper prefHelper;
    private ImageLoader imageLoader;
    private RecyclerViewItemListener clickListner;


    public StudiosItemBinder(DockActivity dockActivity, BasePreferenceHelper prefHelper, RecyclerViewItemListener clickListner) {
        super(R.layout.row_item_studios);
        this.dockActivity = dockActivity;
        this.prefHelper = prefHelper;
        this.imageLoader = ImageLoader.getInstance();
        this.clickListner = clickListner;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(final Studio entity, final int position, int grpPosition, View view, Activity activity) {

        final ViewHolder viewHolder = (ViewHolder) view.getTag();

      /*  imageLoader.displayImage(entity.getImage(), viewHolder.ivProfileImage);
        viewHolder.txtTitle.setText(entity.getTitle() + "");
        viewHolder.txtAddress.setText(entity.getAddress() + "");
        viewHolder.txtTime.setText(entity.getTime() + "");*/

        // imageLoader.displayImage(entity.getStudioLogo(), viewHolder.ivProfileImage);


        Picasso.with(dockActivity)
                .load(entity.getStudioLogo())
                .placeholder(R.drawable.placeholder3)
                .into(viewHolder.ivProfileImage);

        if (prefHelper.isLanguagePersian()) {
            viewHolder.txtTitle.setText(entity.getStudioNamePer() + "");
            if (entity.getAddressPer() != null) {
                viewHolder.txtAddress.setText(entity.getAddressPer() + "");
            } else {
                viewHolder.txtAddress.setText("-");
            }

            //  viewHolder.txtTime.setText(entity.getOpeningTime() + "-" + entity.getClosingTime());
            viewHolder.txtTime.setText(DateHelper.getFormatedDate("HH:mm:ss", "HH:mm", entity.getOpeningTimePr()) + "-" +
                    DateHelper.getFormatedDate("HH:mm:ss", "HH:mm", entity.getClosingTimePr()));

        } else {
            viewHolder.txtTitle.setText(entity.getStudioNameEng() + "");
            if (entity.getAddressEng() != null) {
                viewHolder.txtAddress.setText(entity.getAddressEng() + "");
            } else {
                viewHolder.txtAddress.setText("-");
            }
            //  viewHolder.txtTime.setText(entity.getOpeningTime() + "-" + entity.getClosingTime());
            viewHolder.txtTime.setText(DateHelper.getFormatedDate("HH:mm:ss", "HH:mm", entity.getOpeningTime()) + "-" +
                    DateHelper.getFormatedDate("HH:mm:ss", "HH:mm", entity.getClosingTime()));
        }


        viewHolder.rv_features.BindRecyclerView(new StudioFeaturesBinder(this, prefHelper,dockActivity), entity.getStudioFeatures(),
                new LinearLayoutManager(dockActivity, LinearLayoutManager.HORIZONTAL, false)
                , new DefaultItemAnimator());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListner.onRecyclerItemClicked(entity, position);
            }
        });


    /*    viewHolder.ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.showShortToastInCenter(dockActivity, "Will be implemented in beta");
            }
        });*/

    }

    @Override
    public void onRecyclerItemClicked(Object Ent, int position) {

    }


    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_profileImage)
        CircleImageView ivProfileImage;
        @BindView(R.id.txt_title)
        AnyTextView txtTitle;
        @BindView(R.id.iv_favorite)
        ImageView ivFavorite;
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

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
