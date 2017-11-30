package com.ingic.tanfit.fragments;


import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ingic.tanfit.R;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.global.AppConstants;
import com.ingic.tanfit.interfaces.SetChildTitlebar;
import com.ingic.tanfit.ui.adapters.TabViewPagerAdapter;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeFragment extends BaseFragment {


    @BindView(R.id.txt_gym_type)
    AnyTextView txtGymType;
    @BindView(R.id.txt_gym_time)
    AnyTextView txtGymTime;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    private TabViewPagerAdapter adapter;
    private SetChildTitlebar childTitlebar;
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new TabViewPagerAdapter(getChildFragmentManager());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        childTitlebar = (MainFragment)getParentFragment();

        setViewPager();
        setViewInTabLayout();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (childTitlebar != null) {
            childTitlebar.setChildTitlebar(getString(R.string.home), AppConstants.HOME_FRAGMENT_TAG);
        }
    }

    private void setViewInTabLayout() {

        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.GRAY);
        drawable.setSize(1, 1);
//        linearLayout.setDividerPadding(10);
        linearLayout.setDividerDrawable(drawable);
    }
    private void setViewPager() {



        if (adapter.getCount() > 0) {
            adapter.clearList();
        }

        adapter.addFragment(new HomeFitnessClassFragment(), getString(R.string.fitness_classes));
        adapter.addFragment(new HomeStudioFragment(), getString(R.string.studios));
        viewpager.setAdapter(adapter);
        viewpager.getAdapter().notifyDataSetChanged();
        tabLayout.setupWithViewPager(viewpager);
    }
    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
      /*  titleBar.hideButtons();
        titleBar.showMenuButton();
        titleBar.setSubHeading("Home");
        titleBar.showNotificationButton(0);*/

    }


}

