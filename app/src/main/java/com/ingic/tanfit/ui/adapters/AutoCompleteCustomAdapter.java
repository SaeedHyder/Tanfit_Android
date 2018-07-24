package com.ingic.tanfit.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.ingic.tanfit.R;
import com.ingic.tanfit.activities.DockActivity;
import com.ingic.tanfit.entities.ActivityAutoCompleteEnt;
import com.ingic.tanfit.entities.GetActivitiesEnt;
import com.ingic.tanfit.helpers.BasePreferenceHelper;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * Created by saeedhyder on 8/23/2017.
 */

public class AutoCompleteCustomAdapter extends ArrayAdapter<GetActivitiesEnt> {

        ArrayList<GetActivitiesEnt> customers, tempCustomer, suggestions;
        ImageLoader imageLoader;
        private int activityId;
        private BasePreferenceHelper prefHelper;
        private DockActivity dockActivity;

        public AutoCompleteCustomAdapter(DockActivity context, ArrayList<GetActivitiesEnt> objects, BasePreferenceHelper prefHelper) {
            super(context, android.R.layout.simple_list_item_1, objects);
            this.customers = objects;
            this.tempCustomer = new ArrayList<GetActivitiesEnt>(objects);
            this.suggestions = new ArrayList<GetActivitiesEnt>(objects);
            imageLoader=ImageLoader.getInstance();
            this.prefHelper=prefHelper;
            this.dockActivity=context;


        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            GetActivitiesEnt activity = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_item_autocomplete, parent, false);
            }
            TextView txtCustomer = (TextView) convertView.findViewById(R.id.tvCustomer);
            TextView txtDistance = (TextView) convertView.findViewById(R.id.distance);

            if (activity != null) {
                ImageView ivCustomerImage = (ImageView) convertView.findViewById(R.id.image);
                txtCustomer.setText(activity.getNameEn());
               // ivCustomerImage.setImageResource(R.drawable.power_yoga);
                if (ivCustomerImage != null && activity.getMaleIcon() != null ){
                    if (prefHelper.getUserAllData().getGenderId() == 1) {
                        Glide.with(dockActivity).asGif()
                                .load(activity.getMaleIcon())
                                .apply(bitmapTransform(new CircleCrop()))
                                .apply(new RequestOptions()
                                        .placeholder(R.drawable.placeholder3))
                                .into(ivCustomerImage);
                    } else {
                        Glide.with(dockActivity).asGif()
                                .load(activity.getFemaleIcon())
                                .apply(bitmapTransform(new CircleCrop()))
                                .apply(new RequestOptions()
                                        .placeholder(R.drawable.placeholder3))
                                .into(ivCustomerImage);
                    }
                  //  imageLoader.displayImage(activity.getIcon(),ivCustomerImage);
                   // Picasso.with(getContext()).load(activity.getActivityImage()).into(ivCustomerImage);
                }
            }

            return convertView;
        }

        public int getActivityId(){
            return activityId;
        }


        @Override
        public Filter getFilter() {
            return myFilter;
        }

        Filter myFilter = new Filter() {
            @Override
            public CharSequence convertResultToString(Object resultValue) {
                GetActivitiesEnt activity = (GetActivitiesEnt) resultValue;
                return activity.getNameEn();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if (constraint != null) {
                    suggestions.clear();
                    for (GetActivitiesEnt people : tempCustomer) {
                        if (people.getNameEn().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                            suggestions.add(people);
                        }
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = suggestions;
                    filterResults.count = suggestions.size();
                    return filterResults;
                } else {
                    return new FilterResults();
                }
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                ArrayList<GetActivitiesEnt> c = (ArrayList<GetActivitiesEnt>) results.values;
                if (results != null && results.count > 0) {
                    clear();
                    for (GetActivitiesEnt cust : c) {
                        add(cust);
                        notifyDataSetChanged();
                    }
                }
            }
        };
}
