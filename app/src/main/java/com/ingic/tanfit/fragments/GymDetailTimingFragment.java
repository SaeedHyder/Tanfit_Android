package com.ingic.tanfit.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.TimingEnt;
import com.ingic.tanfit.entities.TimingTypeEnt;
import com.ingic.tanfit.interfaces.RecyclerViewTimingClickListner;
import com.ingic.tanfit.ui.adapters.ArrayListExpandableAdapter;
import com.ingic.tanfit.ui.binders.GymTimingBinder;
import com.ingic.tanfit.ui.binders.TimingTypeBinder;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.CustomExpandableListView;
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
public class GymDetailTimingFragment extends DialogFragment implements RecyclerViewTimingClickListner {
    @BindView(R.id.txt_title)
    AnyTextView txtTitle;
    @BindView(R.id.btn_close)
    ImageView btnClose;
    @BindView(R.id.rv_gallery)
    CustomRecyclerView rvGallery;
    @BindView(R.id.elv_booking_history)
    CustomExpandableListView elvBookingHistory;
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
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.fragment_gym_detail_timing, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        int margin = Math.round(getResources().getDimension(R.dimen.x30));
        int widthmargin = Math.round(getResources().getDimension(R.dimen.x10));
        getDialog().getWindow().setLayout(
                getResources().getDisplayMetrics().widthPixels - widthmargin,
                getResources().getDisplayMetrics().heightPixels - margin
        );

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRecyclerViewData();
        setBookingHistoryData();
    }

    private void setBookingHistoryData() {
        elvBookingHistory.setExpanded(true);
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


        adapter = new ArrayListExpandableAdapter<>(getActivity(), collectionGroup, listDataChild,
                new GymTimingBinder(), null);
        elvBookingHistory.setAdapter(adapter);
        for (int i = 0; i < listDataChild.size(); i++) {
            elvBookingHistory.expandGroup(i);
        }
        elvBookingHistory.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return true; // This way the expander cannot be collapsed
            }
        });
        adapter.notifyDataSetChanged();
    }


    private void setRecyclerViewData() {

        userCollectionsTime = new ArrayList<>();
        userCollectionsTime.add(new TimingTypeEnt(R.drawable.gym_grey, R.drawable.gym2, true, "Gym"));
        userCollectionsTime.add(new TimingTypeEnt(R.drawable.yoga_grey, R.drawable.yoga2, false, "Yoga"));
        userCollectionsTime.add(new TimingTypeEnt(R.drawable.boot_camp, R.drawable.boot_camp2, false, "Boot Camp"));
        rvGallery.BindRecyclerView(new TimingTypeBinder(this), userCollectionsTime,
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
                , new DefaultItemAnimator());

    }

    public void dismissDialog() {

        GymDetailTimingFragment.this.dismiss();

    }

    @OnClick(R.id.btn_close)
    public void onViewClicked() {
        dismissDialog();
    }

    @Override
    public void OnTypeItemClickedListener(int currentPostion, int previousPosition) {
        rvGallery.notifyItemChanged(currentPostion);
        rvGallery.notifyItemChanged(previousPosition);
    }
}