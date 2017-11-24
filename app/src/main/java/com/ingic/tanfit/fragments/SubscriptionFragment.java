package com.ingic.tanfit.fragments;

import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ingic.tanfit.R;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.ui.adapters.ViewPagerAdapter;
import com.ingic.tanfit.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by saeedhyder on 11/23/2017.
 */
public class SubscriptionFragment extends BaseFragment {



    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private ViewPagerAdapter adapter;
    private int positionOfPager;
    private ArrayList<SubscriptionPagerItem> pagesArray;


    Unbinder unbinder;

    public static SubscriptionFragment newInstance() {
        Bundle args = new Bundle();

        SubscriptionFragment fragment = new SubscriptionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subcription, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setViewPager();

    }


    private void setViewPager() {

        setPagerSetting();

        pagesArray = new ArrayList<>();
        pagesArray.add(new SubscriptionPagerItem());
        pagesArray.add(new SubscriptionPagerItem());
        pagesArray.add(new SubscriptionPagerItem());


        adapter = new ViewPagerAdapter(getChildFragmentManager(),pagesArray);

        viewpager.setAdapter(adapter);
        positionOfPager = viewpager.getCurrentItem();


    }

    private void setPagerSetting() {
        viewpager.setClipToPadding(false);
        viewpager.setPageMargin(10);
       /* pager.setPadding(20, 8, 20, 8);
        pager.setOffscreenPageLimit(3);*/
        viewpager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                int pageWidth = viewpager.getMeasuredWidth() -
                        viewpager.getPaddingLeft() - viewpager.getPaddingRight();
                int pageHeight = viewpager.getHeight();
                int paddingLeft = viewpager.getPaddingLeft();
                float transformPos = (float) (page.getLeft() -
                        (viewpager.getScrollX() + paddingLeft)) / pageWidth;
                int max = pageHeight / 10;

                if (transformPos < -1) { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    page.setAlpha(0.7f);// to make left transparent
                    page.setScaleY(0.9f);
                } else if (transformPos <= 1) { // [-1,1]
                    page.setAlpha(1f);
                    page.setScaleY(1f);
                } else { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    page.setAlpha(0.7f);// to make right transparent
                    page.setScaleY(0.9f);
                }
            }
        });
    }



    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading(getString(R.string.subscription_plans));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}