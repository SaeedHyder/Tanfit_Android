package com.ingic.tanfit.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.ingic.tanfit.R;
import com.ingic.tanfit.activities.DockActivity;
import com.ingic.tanfit.activities.MainActivity;
import com.ingic.tanfit.entities.GetActivitiesEnt;
import com.ingic.tanfit.helpers.BasePreferenceHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * Created by saeedhyder on 6/8/2018.
 */

public class SpinnerAdapter extends ArrayAdapter<GetActivitiesEnt> {

    int groupid;
    DockActivity context;
    ArrayList<GetActivitiesEnt> list;
    LayoutInflater inflater;
    BasePreferenceHelper prefHelper;
    MainActivity mainFragment;
    ImageLoader imageLoader;

    public SpinnerAdapter(DockActivity context, int groupid, int id, ArrayList<GetActivitiesEnt>
            list, BasePreferenceHelper prefHelper, MainActivity mainFragment) {
        super(context, id, list);
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.groupid = groupid;
        this.prefHelper = prefHelper;
        this.mainFragment = mainFragment;
        imageLoader=ImageLoader.getInstance();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = inflater.inflate(groupid, parent, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.img);

       /* if (position == 0) {
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setVisibility(View.VISIBLE);
            if (mainFragment != null) {
                if (prefHelper.getUserAllData().getGenderId() == 1) {
                    Glide.with(mainFragment).asGif()
                            .load(list.get(position).getMaleIcon())
                            .apply(bitmapTransform(new CircleCrop()))
                            .apply(new RequestOptions()
                                    .placeholder(R.drawable.placeholder3))
                            .into(imageView);
                 //  imageLoader.displayImage(list.get(position).getMaleIcon(),imageView);
                } else {
                    Glide.with(mainFragment).asGif()
                            .load(list.get(position).getFemaleIcon())
                            .apply(bitmapTransform(new CircleCrop()))
                            .apply(new RequestOptions()
                                    .placeholder(R.drawable.placeholder3))
                            .into(imageView);
                }
            }
        }*/

        TextView textView = (TextView) itemView.findViewById(R.id.txt);
        if(prefHelper.isLanguagePersian()){
            textView.setText(list.get(position).getNamePr());
        }
        else{
            textView.setText(list.get(position).getNameEn());
        }

        return itemView;
    }

    public View getDropDownView(int position, View convertView, ViewGroup
            parent) {
        return getView(position, convertView, parent);

    }
}

