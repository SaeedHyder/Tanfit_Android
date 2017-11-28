package com.ingic.tanfit.fragments;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.TimingEnt;
import com.ingic.tanfit.entities.TimingTypeEnt;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.interfaces.RecyclerViewTimingClickListner;
import com.ingic.tanfit.ui.adapters.ArrayListExpandableAdapter;
import com.ingic.tanfit.ui.binders.GymTimingBinder;
import com.ingic.tanfit.ui.binders.TimingTypeBinder;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.CustomRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created on 11/28/2017.
 */
public class GymDetailTimingFragment extends BaseFragment implements RecyclerViewTimingClickListner {
    @BindView(R.id.txt_title)
    AnyTextView txtTitle;
    @BindView(R.id.btn_close)
    ImageView btnClose;
    @BindView(R.id.rv_gallery)
    CustomRecyclerView rvGallery;
    @BindView(R.id.elv_booking_history)
    ExpandableListView elvBookingHistory;
    Unbinder unbinder;
    private ArrayList<TimingTypeEnt> userCollectionsTime;
    private ArrayListExpandableAdapter<String, TimingEnt> adapter;
    private ArrayList<String> collectionGroup;
    private ArrayList<TimingEnt> collectionChild;
    private HashMap<String, ArrayList<TimingEnt>> listDataChild;

    public static GymDetailTimingFragment newInstance() {
        Bundle args = new Bundle();

        GymDetailTimingFragment fragment = new GymDetailTimingFragment();
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
        View view = inflater.inflate(R.layout.fragment_gym_detail_timing, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRecyclerViewData();
        setBookingHistoryData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void setBookingHistoryData() {
        collectionGroup = new ArrayList<>();
        collectionChild = new ArrayList<>();
        listDataChild = new HashMap<>();

        collectionGroup.add("Monday");
        collectionGroup.add("Tuesday");
        collectionGroup.add("Wednesday");
        collectionGroup.add("Thursday");
        collectionGroup.add("Friday");
        collectionGroup.add("Saturday");

        collectionChild.add(new TimingEnt("4 am to 12 pm ", "12 am to 4 pm"));
        collectionChild.add(new TimingEnt("4 am to 12 pm ", "12 am to 4 pm"));
        collectionChild.add(new TimingEnt("4 am to 12 pm ", "12 am to 4 pm"));

        listDataChild.put(collectionGroup.get(0), collectionChild);
        listDataChild.put(collectionGroup.get(1), collectionChild);
        listDataChild.put(collectionGroup.get(2), collectionChild);
        listDataChild.put(collectionGroup.get(3), collectionChild);
        listDataChild.put(collectionGroup.get(4), collectionChild);
        listDataChild.put(collectionGroup.get(5), collectionChild);


        adapter = new ArrayListExpandableAdapter<>(getDockActivity(), collectionGroup, listDataChild,
                new GymTimingBinder(getDockActivity(), prefHelper), null);
        elvBookingHistory.setAdapter(adapter);
        for (int i = 0; i < listDataChild.size(); i++) {
            elvBookingHistory.expandGroup(i);
        }

        adapter.notifyDataSetChanged();
    }

    private void setRecyclerViewData() {

        userCollectionsTime = new ArrayList<>();
        userCollectionsTime.add(new TimingTypeEnt(R.drawable.gym_grey, R.drawable.gym2, true, "Gym"));
        userCollectionsTime.add(new TimingTypeEnt(R.drawable.yoga_grey, R.drawable.yoga2, false, "Yoga"));
        userCollectionsTime.add(new TimingTypeEnt(R.drawable.boot_camp, R.drawable.boot_camp2, false, "Boot Camp"));

        rvGallery.BindRecyclerView(new TimingTypeBinder(this), userCollectionsTime,
                new LinearLayoutManager(getDockActivity(), LinearLayoutManager.HORIZONTAL, false)
                , new DefaultItemAnimator());

    }

    @OnClick(R.id.btn_close)
    public void onViewClicked() {
    }

    @Override
    public void OnTypeItemClickedListener(int currentPostion, int previousPosition) {
        rvGallery.notifyItemChanged(currentPostion);
        rvGallery.notifyItemChanged(previousPosition);
    }
}