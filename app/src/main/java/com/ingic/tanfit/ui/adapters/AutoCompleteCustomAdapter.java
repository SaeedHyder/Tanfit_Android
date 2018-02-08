package com.ingic.tanfit.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.ActivityAutoCompleteEnt;
import com.ingic.tanfit.entities.GetActivitiesEnt;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by saeedhyder on 8/23/2017.
 */

public class AutoCompleteCustomAdapter extends ArrayAdapter<GetActivitiesEnt> {

        ArrayList<GetActivitiesEnt> customers, tempCustomer, suggestions;
        ImageLoader imageLoader;
        private int activityId;

        public AutoCompleteCustomAdapter(Context context, ArrayList<GetActivitiesEnt> objects) {
            super(context, android.R.layout.simple_list_item_1, objects);
            this.customers = objects;
            this.tempCustomer = new ArrayList<GetActivitiesEnt>(objects);
            this.suggestions = new ArrayList<GetActivitiesEnt>(objects);
            imageLoader=ImageLoader.getInstance();


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
                CircleImageView ivCustomerImage = (CircleImageView) convertView.findViewById(R.id.image);
                txtCustomer.setText(activity.getName());
                ivCustomerImage.setImageResource(R.drawable.power_yoga);
                if (ivCustomerImage != null && activity.getIcon() != null ){
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
                return activity.getName();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if (constraint != null) {
                    suggestions.clear();
                    for (GetActivitiesEnt people : tempCustomer) {
                        if (people.getName().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
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
