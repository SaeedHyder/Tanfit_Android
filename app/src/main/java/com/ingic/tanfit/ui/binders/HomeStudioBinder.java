package com.ingic.tanfit.ui.binders;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;

import com.ingic.tanfit.R;
import com.ingic.tanfit.activities.DockActivity;
import com.ingic.tanfit.entities.fitnessEnt;
import com.ingic.tanfit.helpers.BasePreferenceHelper;
import com.ingic.tanfit.helpers.UIHelper;
import com.ingic.tanfit.ui.viewbinders.abstracts.ViewBinder;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created on 11/25/2017.
 */

public class HomeStudioBinder extends ViewBinder<fitnessEnt> {
    private DockActivity dockActivity;
    private BasePreferenceHelper prefHelper;
    private ImageLoader imageLoader;

    public HomeStudioBinder(DockActivity dockActivity, BasePreferenceHelper prefHelper) {
        super(R.layout.row_item_home_studio);
        this.dockActivity = dockActivity;
        this.prefHelper = prefHelper;
        imageLoader= ImageLoader.getInstance();
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(fitnessEnt entity, int position, int grpPosition, View view, Activity activity) {
        final ViewHolder viewHolder = (ViewHolder) view.getTag();

        imageLoader.displayImage(entity.getImage(), viewHolder.ivProfileImage);
        viewHolder.txtTitle.setText(entity.getTitle() + "");
        viewHolder.txtAddress.setText(entity.getAddress() + "");
        viewHolder.txtTime.setText(entity.getTime() + "");


      /*  viewHolder.ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.showShortToastInCenter(dockActivity, "Will be implemented in beta");
            }
        });*/
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

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
