package com.ingic.tanfit.ui.binders;

import android.content.Context;
import android.view.View;

import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.ProfileEnt;
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


    public ProfileItemBinder() {
        super(R.layout.row_item_profile_gyms);
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(ProfileEnt entity, int position, Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;

    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_gymImage)
        CircleImageView ivGymImage;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
