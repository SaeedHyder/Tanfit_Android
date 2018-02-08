package com.ingic.tanfit.fragments;


import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.GetNearestStudiosEnt;
import com.ingic.tanfit.entities.UserAllDataEnt;
import com.ingic.tanfit.entities.UserSubscription;
import com.ingic.tanfit.entities.remindingClassEnt;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.global.AppConstants;
import com.ingic.tanfit.global.WebServiceConstants;
import com.ingic.tanfit.helpers.DateHelper;
import com.ingic.tanfit.interfaces.SetChildTitlebar;
import com.ingic.tanfit.ui.adapters.TabViewPagerAdapter;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeFragment extends BaseFragment  {


    @BindView(R.id.txt_gym_type)
    AnyTextView txtGymType;
    @BindView(R.id.txt_gym_time)
    AnyTextView txtGymTime;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.rl_remindingClass)
    RelativeLayout rlRemindingClass;


    private TabViewPagerAdapter adapter;
    private SetChildTitlebar childTitlebar;

    GetNearestStudiosEnt entity;


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new TabViewPagerAdapter(getChildFragmentManager());
    }

    public void setContent(GetNearestStudiosEnt data) {
        this.entity = data;
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
        childTitlebar = (MainFragment) getParentFragment();

       /* swipeContainer.setOnRefreshListener(this);

        swipeContainer.setColorSchemeColors(getResources().getColor(R.color.app_blue));*/

        serviceHelper.enqueueCall(headerWebService.RemindingFitnessClass(prefHelper.getUser().getUserId() + ""), WebServiceConstants.RemindingCLass);
        serviceHelper.enqueueCall(headerWebService.getSubsccriptionData(prefHelper.getUser().getUserId() + ""), WebServiceConstants.getSubscription);


        setViewPager();
        setViewInTabLayout();

        if (prefHelper.isStudio()) {
            TabLayout.Tab tab = tabLayout.getTabAt(1);
            tab.select();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (childTitlebar != null) {
            childTitlebar.setChildTitlebar(getString(R.string.home), AppConstants.HOME_FRAGMENT_TAG);
        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {

            case WebServiceConstants.RemindingCLass:
                remindingClassEnt remindingClass = (remindingClassEnt) result;
                if (remindingClass != null && remindingClass.getFitnessClass() != null) {
                    rlRemindingClass.setVisibility(View.VISIBLE);
                    txtGymType.setText(remindingClass.getFitnessClass().getClassNameEng() + "");
                    txtGymTime.setText(DateHelper.getFormatedDate("HH:mm:ss", "hh:mm aa", remindingClass.getSeletedTime().getTimeIn()));
                } else {
                    rlRemindingClass.setVisibility(View.GONE);
                }
                break;

            case WebServiceConstants.getSubscription:
                UserAllDataEnt userAppData = prefHelper.getUserAllData();

                ArrayList<UserSubscription> userSubscriptions = (ArrayList<UserSubscription>) result;

                userAppData.setUserSubscription(userSubscriptions);

                prefHelper.putUserAllData(userAppData);


                break;


        }
    }

    private void setViewInTabLayout() {

        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.GRAY);
        drawable.setSize(1, 1);
        linearLayout.setDividerPadding(0);
        linearLayout.setDividerDrawable(drawable);
    }

    private void setViewPager() {


        if (adapter.getCount() > 0) {
            adapter.clearList();
        }

        HomeFitnessClassFragment homeFitnessClass = new HomeFitnessClassFragment();
        homeFitnessClass.setContent(entity.getFitnessClassess());
        adapter.addFragment(homeFitnessClass, getString(R.string.fitness_classes));

        HomeStudioFragment homeStudiosClass = new HomeStudioFragment();
        homeStudiosClass.setContent(entity.getStudios());
        adapter.addFragment(homeStudiosClass, getString(R.string.studios));
        viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager);
        //viewpager.getAdapter().notifyDataSetChanged();

       /* if(swipeContainer!=null && swipeContainer.isRefreshing()){
            swipeContainer.setRefreshing(false);}*/
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

/*
    @Override
    public void onRefresh() {
        setViewPager();
    }*/
}

