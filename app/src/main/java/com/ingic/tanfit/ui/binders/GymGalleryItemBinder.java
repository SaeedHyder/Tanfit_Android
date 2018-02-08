package com.ingic.tanfit.ui.binders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.GymFeatureEnt;
import com.ingic.tanfit.interfaces.RecyclerViewItemListener;
import com.ingic.tanfit.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 11/27/2017.
 */

public class GymGalleryItemBinder extends RecyclerViewBinder<GymFeatureEnt> {
    private ImageLoader imageLoader;
    private RecyclerViewItemListener recyclerViewItemListener;

    public GymGalleryItemBinder(RecyclerViewItemListener recyclerViewItemListener) {
        super(R.layout.row_item_gym_images);
        imageLoader = ImageLoader.getInstance();
        this.recyclerViewItemListener = recyclerViewItemListener;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(final GymFeatureEnt entity, final int position, final Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.imgPic.setImageResource(entity.getFeatureRes());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewItemListener.onRecyclerItemClicked(entity, position);
            }
        });

    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.img_pic)
        ImageView imgPic;

        ViewHolder(View view) {
             super(view);
            ButterKnife.bind(this, view);
        }

    }
}
