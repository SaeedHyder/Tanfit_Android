package com.ingic.tanfit.ui.binders;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.ProfileEnt;
import com.ingic.tanfit.interfaces.RecyclerViewItemListener;
import com.ingic.tanfit.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by saeedhyder on 11/25/2017.
 */

public class ProfileItemBinder extends RecyclerViewBinder<ProfileEnt> {

    private ImageLoader imageLoader;
    private RecyclerViewItemListener itemListener;


    public ProfileItemBinder(RecyclerViewItemListener itemListener) {
        super(R.layout.row_item_profile_gyms);
        imageLoader = ImageLoader.getInstance();
        this.itemListener=itemListener;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(final ProfileEnt entity, final int position, Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;

        holder.mainFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemListener.onRecyclerItemClicked(entity,position);
            }
        });

    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_gymImage)
        CircleImageView ivGymImage;

        @BindView(R.id.mainFrame)
        LinearLayout mainFrame;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
