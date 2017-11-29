package com.ingic.tanfit.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.ingic.tanfit.R;
import com.ingic.tanfit.activities.DockActivity;
import com.ingic.tanfit.global.AppConstants;
import com.ingic.tanfit.ui.viewbinders.abstracts.ViewBinder;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by saeedhyder on 11/20/2017.
 */

public class SwipeDeckAdapter<T> extends BaseAdapter {

    private List<T> data;
    private DockActivity context;
    private ImageLoader imageLoader;
    protected ViewBinder<T> viewBinder;
    public SwipeDeckAdapter(List<T> data, DockActivity context,ViewBinder<T> viewBinder) {
        this.data = data;
        this.context = context;
        this.imageLoader = ImageLoader.getInstance();
        this.viewBinder = viewBinder;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

      /*  View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // normally use a viewholder
            v = inflater.inflate(R.layout.row_item_card, parent, false);
        }

        ImageView logo = (ImageView) v.findViewById(R.id.logo);
//        Picasso.with(context).load(R.drawable.logo).fit().into(logo);
        imageLoader.displayImage(AppConstants.DRAWABLE_PATH + R.drawable.logo, logo);
        ImageView image = (ImageView) v.findViewById(R.id.iv_mainImage);
//        Picasso.with(context).load(R.drawable.ic_launcher).fit().into(image);
        imageLoader.displayImage(AppConstants.DRAWABLE_PATH + R.drawable.gym, image);
        TextView heading = (TextView) v.findViewById(R.id.txt_heading);
        heading.setText("Donec ultrices scelerisque nisi");
        TextView description = (TextView) v.findViewById(R.id.txt_description);
        description.setText("Nulla ei orci aliquam,condimentum tellus eaac,cursus tellius,In magna eros luctus non");
        String item = (String) getItem(position);
        PagerIndicator indicator = (PagerIndicator) v.findViewById(R.id.custom_indicator);


        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Layer type: ", Integer.toString(v.getLayerType()));
                Log.i("Hardware Accel type:", Integer.toString(View.LAYER_TYPE_HARDWARE));
            }
        });
        return v;*/
        View view = convertView;

        if ( view == null ) {
            view = viewBinder.createView( context );
        }

        viewBinder.bindView( data.get( position ), position, 0,
                view, context );

        return view;
    }

    /**
     * Clears the internal list
     */
    public void clearList() {
        data.clear();
        notifyDataSetChanged();
    }

    /**
     * Adds a entity to the list and calls {@link #notifyDataSetChanged()}.
     * Should not be used if lots of NotificationDummy are added.
     *
     * @see #addAll(List)
     */
    public void add( T entity ) {
        data.add( entity );
        notifyDataSetChanged();
    }

    /**
     * Adds a NotificationDummy to the list and calls
     * {@link #notifyDataSetChanged()}. Can be used {
     * {@link List#subList(int, int)}.
     *
     * @see #addAll(List)
     */
    public void addAll( List<T> entityList ) {
        data.addAll( entityList );
        notifyDataSetChanged();
    }

}
