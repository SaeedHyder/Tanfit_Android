package com.ingic.tanfit.fragments;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.FavoriteDataEnt;
import com.ingic.tanfit.entities.UserAllDataEnt;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.global.WebServiceConstants;
import com.ingic.tanfit.ui.adapters.TabViewPagerAdapter;
import com.ingic.tanfit.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by saeedhyder on 11/22/2017.
 */
public class FavoriteFragment extends BaseFragment {
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.pager)
    ViewPager pager;
    Unbinder unbinder;


    private TabViewPagerAdapter adapter;

    public static FavoriteFragment newInstance() {
        Bundle args = new Bundle();
        FavoriteFragment fragment = new FavoriteFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new TabViewPagerAdapter(getChildFragmentManager());
        if (getArguments() != null) {

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        serviceHelper.enqueueCall(headerWebService.getFavoriteData(prefHelper.getUser().getUserId()), WebServiceConstants.favoriteData);

       // setViewPager();
        setViewInTabLayout();


    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {

            case WebServiceConstants.favoriteData:
                FavoriteDataEnt entity = (FavoriteDataEnt) result;
                prefHelper.putFavoriteData(entity);
                setViewPager();
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

        pager.setCurrentItem(1);

        if (adapter.getCount() > 0) {
            adapter.clearList();
        }

        adapter.addFragment(new FitnessClassesFragment(), getDockActivity().getResources().getString(R.string.fitness_classes));
        adapter.addFragment(new StudiosFragment(), getDockActivity().getResources().getString(R.string.studios));
        pager.setAdapter(adapter);
        pager.getAdapter().notifyDataSetChanged();
        tabLayout.setupWithViewPager(pager);
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getDockActivity().getResources().getString(R.string.favorite));
    }


}
