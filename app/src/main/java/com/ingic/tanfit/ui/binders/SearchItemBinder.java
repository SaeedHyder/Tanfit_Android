package com.ingic.tanfit.ui.binders;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.SearchRecyclerEnt;
import com.ingic.tanfit.interfaces.RecyclerViewItemListener;
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
    private RecyclerViewItemListener recyclerViewItemListener;

    public SearchItemBinder(RecyclerViewItemListener recyclerViewItemListener) {
        super(R.layout.row_item_search_categories);
        imageLoader = ImageLoader.getInstance();
        this.recyclerViewItemListener=recyclerViewItemListener;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(SearchRecyclerEnt entity, final int position, final Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;
        imageLoader.displayImage(entity.getImage(),holder.image);
        holder.txtGymName.setText(entity.getGymName()+"");
        holder.txtGymAddress.setText(entity.getGymAddress()+"");

        holder.llCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewItemListener.onRecyclerItemClicked(viewHolder,position);
            }
        });




    }


    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.txt_gym_name)
        AnyTextView txtGymName;
        @BindView(R.id.txt_gym_address)
        AnyTextView txtGymAddress;
        @BindView(R.id.ll_company)
        LinearLayout llCompany;
        @BindView(R.id.image)
        ImageView image;





        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
