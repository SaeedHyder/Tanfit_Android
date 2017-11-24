package com.ingic.tanfit.ui.binders;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.SearchRecyclerEnt;
import com.ingic.tanfit.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.ingic.tanfit.ui.viewbinders.abstracts.ViewBinder;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by saeedhyder on 11/23/2017.
 */

public class SearchItemBinder extends RecyclerViewBinder<SearchRecyclerEnt> {

    private ImageLoader imageLoader;

    public SearchItemBinder() {
        super(R.layout.row_item_search_categories);
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(SearchRecyclerEnt entity, int position, Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;

    }


    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.txt_gym_name)
        AnyTextView txtGymName;
        @BindView(R.id.txt_gym_address)
        AnyTextView txtGymAddress;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
