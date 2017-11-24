package com.ingic.tanfit.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ingic.tanfit.R;
import com.ingic.tanfit.activities.DockActivity;
import com.ingic.tanfit.activities.MainActivity;
import com.ingic.tanfit.fragments.SubscriptionPagerItem;
import com.ingic.tanfit.fragments.VerificationEmailFragment;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private String[] titles= new String[]{""};
    ArrayList<SubscriptionPagerItem> pagesArray;


    public ViewPagerAdapter(FragmentManager fm, ArrayList<SubscriptionPagerItem> pagesArray) {
        super(fm);
        this.pagesArray=pagesArray;
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return  pagesArray.size() ;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {

        return pagesArray.get(position);
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return  titles[position];
    }


}
