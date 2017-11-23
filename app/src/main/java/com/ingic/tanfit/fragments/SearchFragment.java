package com.ingic.tanfit.fragments;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.SearchRecyclerEnt;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.global.AppConstants;
import com.ingic.tanfit.ui.binders.SearchItemBinder;
import com.ingic.tanfit.ui.views.CustomRecyclerView;
import com.ingic.tanfit.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by saeedhyder on 11/23/2017.
 */
public class SearchFragment extends BaseFragment implements OnMapReadyCallback {


    SupportMapFragment mapFragment;
    @BindView(R.id.btn_apply)
    Button btnApply;
    @BindView(R.id.lv_companies)
    CustomRecyclerView lv_companies;
    private GoogleMap mMap;
    private View viewParent;
    private ArrayList<SearchRecyclerEnt> userCollections;

    public static SearchFragment newInstance() {
        Bundle args = new Bundle();

        SearchFragment fragment = new SearchFragment();
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

        if (viewParent != null) {
            ViewGroup parent = (ViewGroup) viewParent.getParent();
            if (parent != null)
                parent.removeView(viewParent);
        }
        try {
            viewParent = inflater.inflate(R.layout.fragment_search, container, false);

        } catch (InflateException e) {
            e.printStackTrace();
        }
        ButterKnife.bind(this, viewParent);
        return viewParent;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mapFragment == null) {
            initMap();
        }
       setRecyclerViewData();

    }



    private void setRecyclerViewData() {

        userCollections=new ArrayList<>();
        userCollections.add(new SearchRecyclerEnt(AppConstants.DRAWABLE_PATH+R.drawable.image1,"Troh Gym","Meimi Beach FL USA"));
        userCollections.add(new SearchRecyclerEnt(AppConstants.DRAWABLE_PATH+R.drawable.image1,"Troh Gym","Meimi Beach FL USA"));
        userCollections.add(new SearchRecyclerEnt(AppConstants.DRAWABLE_PATH+R.drawable.image1,"Troh Gym","Meimi Beach FL USA"));

        lv_companies.BindRecyclerView(new SearchItemBinder(), userCollections,
                new LinearLayoutManager(getDockActivity(), LinearLayoutManager.HORIZONTAL, false)
                , new DefaultItemAnimator());

    }

    private void initMap() {
        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading("");
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }



    @OnClick(R.id.btn_apply)
    public void onViewClicked() {
    }
}
