package com.ingic.tanfit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.CurrentBookingEnt;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.ui.adapters.ArrayListExpandableAdapter;
import com.ingic.tanfit.ui.binders.CurrentBookingItemBinder;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.TitleBar;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by saeedhyder on 11/25/2017.
 */
public class BookingHistoryFragment extends BaseFragment {
    @BindView(R.id.txt_noresult)
    AnyTextView txtNoresult;
    @BindView(R.id.elv_booking_history)
    ExpandableListView elvBookingHistory;
    Unbinder unbinder;

    private ArrayListExpandableAdapter<String, CurrentBookingEnt> adapter;
    private ArrayList<String> collectionGroup;
    private ArrayList<CurrentBookingEnt> collectionChild ;
    private HashMap<String, ArrayList<CurrentBookingEnt>> listDataChild;

    public static BookingHistoryFragment newInstance() {
        Bundle args = new Bundle();

        BookingHistoryFragment fragment = new BookingHistoryFragment();
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
        View view = inflater.inflate(R.layout.fragment_bookinghistory, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setBookingHistoryData();
    }

    private void setBookingHistoryData() {
        collectionGroup = new ArrayList<>();
        collectionChild = new ArrayList<>();
        listDataChild = new HashMap<>();

        collectionGroup.add("Spinning Class");
        collectionGroup.add("Yoga Class");
        collectionGroup.add("Spinning Class");

        collectionChild.add(new CurrentBookingEnt("December 13,2018","Fitness Center 55","IR IRAN Tehran Vali Asr St Something House 2554 Apartment Theus"));

        listDataChild.put(collectionGroup.get(0),collectionChild);
        listDataChild.put(collectionGroup.get(1),collectionChild);
        listDataChild.put(collectionGroup.get(2),collectionChild);


        adapter = new ArrayListExpandableAdapter<>(getDockActivity(), collectionGroup, listDataChild, new CurrentBookingItemBinder(getDockActivity(),prefHelper), elvBookingHistory);
        elvBookingHistory.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
