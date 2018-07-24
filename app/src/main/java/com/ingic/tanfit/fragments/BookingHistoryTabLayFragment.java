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
import com.ingic.tanfit.entities.BookingHistoryEnt;
import com.ingic.tanfit.entities.UserFitnessClasses;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.global.WebServiceConstants;
import com.ingic.tanfit.ui.adapters.TabViewPagerAdapter;
import com.ingic.tanfit.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by saeedhyder on gym_image_11/25/2017.
 */
public class BookingHistoryTabLayFragment extends BaseFragment {
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.pager)
    ViewPager pager;
    Unbinder unbinder;
    ArrayList<BookingHistoryEnt> currentBooking;
    ArrayList<BookingHistoryEnt> bookingHistory;

    ArrayList<UserFitnessClasses> userFitnessClasses;

    private TabViewPagerAdapter adapter;

    public static BookingHistoryTabLayFragment newInstance() {
        Bundle args = new Bundle();

        BookingHistoryTabLayFragment fragment = new BookingHistoryTabLayFragment();
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
        View view = inflater.inflate(R.layout.fragment_booking_history_tab, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        serviceHelper.enqueueCall(headerWebService.getUserFitnessClasses(prefHelper.getUserAllData().getId()), WebServiceConstants.getBookingHistory);

        setViewInTabLayout();
    }

    private void setViewInTabLayout() {

        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.GRAY);
        drawable.setSize(1, 1);
//        linearLayout.setDividerPadding(gym_image_10);
        linearLayout.setDividerDrawable(drawable);
    }

    private void setViewPager() {

        pager.setCurrentItem(1);

        if (adapter.getCount() > 0) {
            adapter.clearList();
        }

        CurrentBookingFragment currentBookingFragment = new CurrentBookingFragment();
        currentBookingFragment.setContent(currentBooking);
        adapter.addFragment(currentBookingFragment, getDockActivity().getResources().getString(R.string.current_booking));

        BookingHistoryFragment bookingHistoryFragment = new BookingHistoryFragment();
        bookingHistoryFragment.setContent(bookingHistory);
        adapter.addFragment(bookingHistoryFragment, getDockActivity().getResources().getString(R.string.booking_history));
        pager.setAdapter(adapter);
        //   pager.getAdapter().notifyDataSetChanged();
        tabLayout.setupWithViewPager(pager);
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getDockActivity().getResources().getString(R.string.booking_history));
    }


    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {

            case WebServiceConstants.getBookingHistory:

                ArrayList<BookingHistoryEnt> data = (ArrayList<BookingHistoryEnt>) result;
                currentBooking = new ArrayList<>();
                bookingHistory = new ArrayList<>();

                if (data.size() > 0) {
                    for (BookingHistoryEnt item : data) {
                        if (item.getFitnessClassStatusId() == 1) {
                            currentBooking.add(item);
                        } else {
                            bookingHistory.add(item);

                        }
                    }
                }


              /*  userFitnessClasses = new ArrayList<>();

                userFitnessClasses = (ArrayList<UserFitnessClasses>)result;

                if (userFitnessClasses.size() > 0) {
                    for (UserFitnessClasses item : userFitnessClasses) {
                        if (item.getFitnessClassStatusId()==1) {
                            currentBooking.add(item.getFitnessClassess());
                        } else {
                            bookingHistory.add(item.getFitnessClassess());
                        }
                    }
                }*/

                setViewPager();
                break;
        }
    }
}
