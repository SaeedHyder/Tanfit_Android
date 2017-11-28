package com.ingic.tanfit.ui.binders;


import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.TimingTypeEnt;
import com.ingic.tanfit.interfaces.RecyclerViewTimingClickListner;
import com.ingic.tanfit.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 11/28/2017.
 */

public class TimingTypeBinder extends RecyclerViewBinder<TimingTypeEnt> implements View.OnClickListener {
    private ImageLoader imageLoader;
    private RecyclerViewTimingClickListner recyclerViewItemListener;
    private int previousSelectedPosition = -1;

    public TimingTypeBinder(RecyclerViewTimingClickListner recyclerViewItemListener) {
        super(R.layout.row_item_timing_type);
        imageLoader = ImageLoader.getInstance();
        this.recyclerViewItemListener = recyclerViewItemListener;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(TimingTypeEnt entity, int position, Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (entity.isSelected()) {
            previousSelectedPosition = position;
            holder.frameImg.setBackground(context.getResources().getDrawable(R.drawable.square_timing_background_green));
            holder.imgGym.setImageResource(entity.getImageWhiteRes());
            holder.txtType.setTextColor(context.getResources().getColor(R.color.app_font_green));
            entity.setSelected(true);
        } else {
            holder.frameImg.setBackground(context.getResources().getDrawable(R.drawable.square_background_timing_transparent));
            holder.imgGym.setImageResource(entity.getImageGreyRes());
            holder.txtType.setTextColor(context.getResources().getColor(R.color.app_font_black));
        }
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (recyclerViewItemListener != null) {
            recyclerViewItemListener.OnTypeItemClickedListener((int) v.getTag(), previousSelectedPosition);
        }
    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.img_gym)
        ImageView imgGym;
        @BindView(R.id.frame_img)
        FrameLayout frameImg;
        @BindView(R.id.txt_type)
        AnyTextView txtType;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
