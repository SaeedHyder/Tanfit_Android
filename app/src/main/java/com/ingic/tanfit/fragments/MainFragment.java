package com.ingic.tanfit.fragments;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.ingic.tanfit.R;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.global.AppConstants;
import com.ingic.tanfit.interfaces.SetChildTitlebar;
import com.ingic.tanfit.ui.adapters.TabViewPagerAdapter;
import com.ingic.tanfit.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created on 11/24/2017.
 */
public class MainFragment extends BaseFragment implements SetChildTitlebar {
    @BindView(R.id.viewpager)
    FrameLayout viewpager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    Unbinder unbinder;
    private TabViewPagerAdapter adapter;
    private TitleBar titleBar;
    private int[] tabIcons = {R.drawable.home, R.drawable.search, R.drawable.subscription};
    private int startWithTab = 0;
    private int tabTag = 0;

    public static MainFragment newInstance() {
        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setStartWithTab(int startWithTab) {
        this.startWithTab = startWithTab;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new TabViewPagerAdapter(getChildFragmentManager());
        if (getArguments() != null) {
        }

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        if (this.titleBar == null) {
            this.titleBar = titleBar;
            setChildTitlebar("", tabTag);
        } else {
            this.titleBar = titleBar;
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setViewPager();
        // setViewInTabLayout();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void setViewInTabLayout() {

        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.GRAY);
        drawable.setSize(1, 1);
        linearLayout.setDividerPadding(10);
        linearLayout.setDividerDrawable(drawable);
    }

    private void ReplaceTab(int position) {
        android.support.v4.app.FragmentTransaction transaction = getChildFragmentManager()
                .beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        transaction.replace(R.id.viewpager, adapter.getItem(position));
        transaction.commit();


    }

    private void setTabIcon(TabLayout.Tab tab) {
        try {
            if (tab != null) {
                switch ((Integer) tab.getTag()) {
                    case 0:
                        tab.setIcon(R.drawable.home2);
                        ReplaceTab(0);
                        break;
                    case 1:
                        tab.setIcon(R.drawable.search2);
                        ReplaceTab(1);
                        break;
                    case 2:
                        tab.setIcon(R.drawable.subscription2);
                        ReplaceTab(2);
                        break;
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    private void setUnTabIcon(TabLayout.Tab tab) {
        try {
            if (tab != null) {
                switch ((Integer) tab.getTag()) {
                    case 0:
                        tab.setIcon(R.drawable.home);
                        break;
                    case 1:
                        tab.setIcon(R.drawable.search);
                        break;
                    case 2:
                        tab.setIcon(R.drawable.subscription);
                        break;
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    private void setViewPager() {


        if (adapter.getCount() > 0) {
            adapter.clearList();
        }

        adapter.addFragment(new HomeFragment(), getString(R.string.home));
        adapter.addFragment(new SearchFragment(), getString(R.string.search));
        adapter.addFragment(new SubscriptionFragment(), getString(R.string.subscription_plans));
//        viewpager.setAdapter(adapter);
//        viewpager.setPageMargin(0);
//        viewpager.getAdapter().notifyDataSetChanged();
//        tabLayout.setupWithViewPager(viewpager);
        setTabLayout();
    }

    private void setTabLayout() {
       /* for (int i = 0; i < tabLayout.getTabCount(); i++) {

            if (tabLayout.getTabAt(i) != null) {
                tabLayout.getTabAt(i).setIcon(tabIcons[i]);
                tabLayout.getTabAt(i).setTag(i);
                tabLayout.getTabAt(i).setText("");
            }
        }*/
        tabLayout.addTab(tabLayout.newTab().setIcon(tabIcons[0]).setTag(0), 0, true);
        tabLayout.addTab(tabLayout.newTab().setIcon(tabIcons[1]).setTag(1), 1, false);
        tabLayout.addTab(tabLayout.newTab().setIcon(tabIcons[2]).setTag(2), 2, false);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                setTabIcon(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                setUnTabIcon(tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        // viewpager.setCurrentItem(0);
        setTabIcon(tabLayout.getTabAt(startWithTab));
        TabLayout.Tab tab = tabLayout.getTabAt(startWithTab);
        tab.select();
        startWithTab = 0;
    }

    @Override
    public void setChildTitlebar(String heading, int Tag) {
        tabTag = Tag;
        if (titleBar != null) {
            switch (Tag) {
                case AppConstants.HOME_FRAGMENT_TAG:
                    titleBar.showTitleBar();
                    titleBar.hideButtons();
                    titleBar.showFilterButton(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            willbeimplementedinBeta();
                        }
                    });
                    titleBar.showMenuButton();
                    titleBar.setSubHeading(getString(R.string.home));
                    break;
                case AppConstants.SEARCH_FRAGMENT_TAG:
                    titleBar.hideTitleBar();
                    break;
                case AppConstants.SUBSCRIPTION_FRAGMENT_TAG:
                    titleBar.showTitleBar();
                    titleBar.hideButtons();
                    titleBar.showMenuButton();
                    titleBar.setSubHeading(getString(R.string.subscription_plans));
                    break;

            }
        }

    }
}