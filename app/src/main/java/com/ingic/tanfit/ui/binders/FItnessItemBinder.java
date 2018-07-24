package com.ingic.tanfit.ui.binders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.ingic.tanfit.R;
import com.ingic.tanfit.activities.DockActivity;
import com.ingic.tanfit.entities.FitnessClassess;
import com.ingic.tanfit.helpers.BasePreferenceHelper;
import com.ingic.tanfit.interfaces.RecyclerViewItemListener;
import com.ingic.tanfit.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * Created by saeedhyder on 11/22/2017.
 */

public class FItnessItemBinder extends RecyclerViewBinder<FitnessClassess> {

    private DockActivity dockActivity;
    private BasePreferenceHelper prefHelper;
    private ImageLoader imageLoader;
    private RecyclerViewItemListener clickListner;


    public FItnessItemBinder(DockActivity dockActivity, BasePreferenceHelper prefHelper, RecyclerViewItemListener clickListner) {
        super(R.layout.row_item_fitness);
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
    public void bindView(final FitnessClassess entity, final int position, Object viewHolder, Context context) {

        final ViewHolder holder = (ViewHolder) viewHolder;

        // imageLoader.displayImage(entity.getFitnessClassActivityIcon(),holder.ivProfileImage);

        if (prefHelper.getUserAllData().getGenderId() == 1) {
            Glide.with(dockActivity).asGif()
                    .load(entity.getActivity().getMaleIcon())
                    .apply(bitmapTransform(new CircleCrop()))
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.placeholder3))
                    .into(holder.ivProfileImage);
        } else {
            Glide.with(dockActivity).asGif()
                    .load(entity.getActivity().getFemaleIcon())
                    .apply(bitmapTransform(new CircleCrop()))
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.placeholder3))
                    .into(holder.ivProfileImage);
        }

        if(prefHelper.isLanguagePersian()){
            holder.txtTitle.setText(entity.getClassNamePer() + "");
            if (entity.getClassDescriptionPer() != null && !entity.getClassDescriptionPer().equals("")) {
                holder.description.setText(entity.getClassDescriptionPer() + "");
                holder.description.setVisibility(View.VISIBLE);
            } else {
                //  holder.description.setVisibility(View.GONE);
                holder.description.setText("-");
            }
            if (entity.getStudioAddressPer() != null && !entity.getStudioAddressPer().equals("")) {
                holder.txtAddress.setText(entity.getStudioAddressPer() + "");
            } else {
                holder.txtAddress.setText("-");
            }

            // viewHolder.txtTime.setText(entity.getFitnessClassSelectedDays().get(0).getFitnessClassTimes().get(0).getTimeIn()+"");
//        viewHolder.txtTime.setText(DateHelper.getFormatedDate("HH:mm:ss","HH:mm",entity.getFitnessClassSelectedDays().get(0).getFitnessClassTimes().get(0).getTimeIn()+""));
            holder.txtDuration.setText(entity.getClassDurationMinPr() + " "+dockActivity.getString(R.string.min));
        }
        else{
            holder.txtTitle.setText(entity.getClassNameEng() + "");
            if (entity.getClassDescriptionEng() != null && !entity.getClassDescriptionEng().equals("")) {
                holder.description.setText(entity.getClassDescriptionEng() + "");
                holder.description.setVisibility(View.VISIBLE);
            } else {
                //  holder.description.setVisibility(View.GONE);
                holder.description.setText("-");
            }
            if (entity.getStudioAddressEng() != null && !entity.getStudioAddressEng().equals("")) {
                holder.txtAddress.setText(entity.getStudioAddressEng() + "");
            } else {
                holder.txtAddress.setText("-");
            }
            // viewHolder.txtTime.setText(entity.getFitnessClassSelectedDays().get(0).getFitnessClassTimes().get(0).getTimeIn()+"");
//        viewHolder.txtTime.setText(DateHelper.getFormatedDate("HH:mm:ss","HH:mm",entity.getFitnessClassSelectedDays().get(0).getFitnessClassTimes().get(0).getTimeIn()+""));
            holder.txtDuration.setText(entity.getClassDurationMin() + dockActivity.getString(R.string.min));

        }


        holder.llMainframe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListner.onRecyclerItemClicked(entity, position);
            }
        });
    }


    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_profileImage)
        ImageView ivProfileImage;
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
        @BindView(R.id.ll_mainframe)
        LinearLayout llMainframe;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
