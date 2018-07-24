package com.ingic.tanfit.ui.binders;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.ingic.tanfit.R;
import com.ingic.tanfit.activities.DockActivity;
import com.ingic.tanfit.entities.BookNowClickListner;
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
 * Created on 11/25/2017.
 */

public class HomeFitnessBinder extends RecyclerViewBinder<FitnessClassess> {
    private DockActivity dockActivity;
    private BasePreferenceHelper prefHelper;
    private ImageLoader imageLoader;
    private RecyclerViewItemListener clickListner;
    private BookNowClickListner bookNowClickListner;
    private int dayId;

    public HomeFitnessBinder(DockActivity dockActivity, BasePreferenceHelper prefHelper, RecyclerViewItemListener clickListner, int dayId, BookNowClickListner bookNowClickListner) {
        super(R.layout.row_item_home_fitness);
        this.dockActivity = dockActivity;
        this.prefHelper = prefHelper;
        this.imageLoader = ImageLoader.getInstance();
        this.clickListner = clickListner;
        this.dayId = dayId;
        this.bookNowClickListner = bookNowClickListner;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(final FitnessClassess entity, final int position, Object viewHolder, Context context) {

        final ViewHolder holder = (ViewHolder) viewHolder;


        //    imageLoader.displayImage(entity.getFitnessClassActivityIcon(), viewHolder.ivProfileImage);
        /*Glide.with(dockActivity).load(entity.getFitnessClassActivityIcon())
                .apply(bitmapTransform(new CircleCrop()))
                .into(viewHolder.ivProfileImage);*/


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

        if (prefHelper.isLanguagePersian()) {
            holder.txtTitle.setText(entity.getClassNamePer() + "");

            if (entity.getClassDescriptionEng() != null && !entity.getClassDescriptionEng().equals("")) {
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


            holder.txtDuration.setText(entity.getClassDurationMinPr() + " " + dockActivity.getString(R.string.min));
        } else {
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

            holder.txtDuration.setText(entity.getClassDurationMin() + " " + dockActivity.getString(R.string.min));
        }


        // viewHolder.txtTime.setText(entity.getFitnessClassSelectedDays().get(0).getFitnessClassTimes().get(0).getTimeIn()+"");

       /* if (entity.getFitnessClassSelectedDays() != null && entity.getFitnessClassSelectedDays().get(0).getFitnessClassTimes() != null && entity.getFitnessClassSelectedDays().size() > 0 && entity.getFitnessClassSelectedDays().get(0).getFitnessClassTimes().size() > 0) {
            holder.txtTime.setText(DateHelper.getFormatedDate("HH:mm:ss", "HH:mm", entity.getFitnessClassSelectedDays().get(0).getFitnessClassTimes().get(0).getTimeIn() + ""));
        } else {
            holder.txtTime.setText(R.string.no_class);
        }*/

        /*if (entity.getFitnessClassSelectedDays() != null && entity.getFitnessClassSelectedDays().size() > 0) {
            for (FitnessClassSelectedDay item : entity.getFitnessClassSelectedDays()) {
                if (prefHelper.getUserAllData().getGenderId()==item.getGenderId()) {
                    if (item.getDayId() == dayId) {
                        if (item.getFitnessClassTimes() != null && item.getFitnessClassTimes().size() > 0) {
                            holder.txtTime.setText(DateHelper.getFormatedDate("HH:mm:ss", "HH:mm", item.getFitnessClassTimes().get(0).getTimeIn() + ""));
                        } else {
                            holder.txtTime.setText(R.string.no_class);
                        }
                    } else {
                        holder.txtTime.setText(R.string.no_class);
                    }

                } else {
                    holder.txtTime.setText(R.string.no_class);
                }
            }
        } else {
            holder.txtTime.setText(R.string.no_class);
        }*/


        holder.llMainframe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListner.onRecyclerItemClicked(entity, position);
            }
        });
        holder.btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookNowClickListner.onBookNowClick(entity, position);
            }
        });
    }

   /* @Override
    public void bindView(final FitnessClassess entity, final int position, int grpPosition, View view, Activity activity) {



    }*/

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_profileImage)
        ImageView ivProfileImage;
        @BindView(R.id.txt_title)
        AnyTextView txtTitle;
        //        @BindView(R.id.iv_favorite)
//        ImageView ivFavorite;
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
        @BindView(R.id.btn_book_now)
        Button btnBookNow;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
