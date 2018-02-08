package com.ingic.tanfit.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ingic.tanfit.entities.GetSubscriptionEnt;
import com.ingic.tanfit.fragments.SubscriptionPagerItem;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private String[] titles = new String[]{""};
    ArrayList<GetSubscriptionEnt> pagesArray;
    ArrayList<SubscriptionPagerItem> fragmentsArray = new ArrayList<>();


    public ViewPagerAdapter(FragmentManager fm,ArrayList<SubscriptionPagerItem>fragmentsArray) {
        super(fm);
        this.fragmentsArray = fragmentsArray;
        this.pagesArray = pagesArray;
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return fragmentsArray.size();
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
       /* SubscriptionPagerItem item = fragmentsArray.get(position);
        item.setContent(pagesArray.get(position), position);
        return item;*/
       return fragmentsArray.get(position);
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
