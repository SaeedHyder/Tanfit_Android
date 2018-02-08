package com.ingic.tanfit.ui.binders;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ingic.tanfit.R;
import com.ingic.tanfit.activities.DockActivity;
import com.ingic.tanfit.entities.FavoriteClassesEnt;
import com.ingic.tanfit.entities.FitnessClassess;
import com.ingic.tanfit.entities.fitnessEnt;
import com.ingic.tanfit.helpers.BasePreferenceHelper;
import com.ingic.tanfit.helpers.DateHelper;
import com.ingic.tanfit.helpers.UIHelper;
import com.ingic.tanfit.interfaces.RecyclerViewItemListener;
import com.ingic.tanfit.ui.viewbinders.abstracts.ViewBinder;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by saeedhyder on 11/22/2017.
 */

public class FItnessItemBinder extends ViewBinder<FitnessClassess> {

    private DockActivity dockActivity;
    private BasePreferenceHelper prefHelper;
    private ImageLoader imageLoader;
    private RecyclerViewItemListener clickListner;


    public FItnessItemBinder(DockActivity dockActivity, BasePreferenceHelper prefHelper,RecyclerViewItemListener clickListner) {
        super(R.layout.row_item_fitness);
        this.dockActivity = dockActivity;
        this.prefHelper = prefHelper;
        this.imageLoader = ImageLoader.getInstance();
        this.clickListner=clickListner;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(final FitnessClassess entity, final int position, int grpPosition, View view, Activity activity) {

        final ViewHolder viewHolder = (ViewHolder) view.getTag();

        imageLoader.displayImage(entity.getFitnessClassActivityIcon(),viewHolder.ivProfileImage);
        viewHolder.txtTitle.setText(entity.getClassNameEng()+"");
        viewHolder.description.setText(entity.getClassDescriptionEng()+"");
        viewHolder.txtAddress.setText(entity.getStudioAddressEng()+"");
        // viewHolder.txtTime.setText(entity.getFitnessClassSelectedDays().get(0).getFitnessClassTimes().get(0).getTimeIn()+"");
//        viewHolder.txtTime.setText(DateHelper.getFormatedDate("HH:mm:ss","HH:mm",entity.getFitnessClassSelectedDays().get(0).getFitnessClassTimes().get(0).getTimeIn()+""));
        viewHolder.txtDuration.setText(entity.getClassDurationMin()+" Min");

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListner.onRecyclerItemClicked(entity,position);
            }
        });

     /*   viewHolder.ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.showShortToastInCenter(dockActivity,"Will be implemented in beta");
            }
        });*/

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
        @BindView(R.id.description)
        AnyTextView description;
        @BindView(R.id.txt_address)
        AnyTextView txtAddress;
        @BindView(R.id.txt_time)
        AnyTextView txtTime;
        @BindView(R.id.view)
        View view;
        @BindView(R.id.txt_duration)
        AnyTextView txtDuration;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
