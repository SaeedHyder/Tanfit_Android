package com.ingic.tanfit.fragments;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.LocationModel;
import com.ingic.tanfit.entities.MapScreenItem;
import com.ingic.tanfit.entities.SearchRecyclerEnt;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.global.AppConstants;
import com.ingic.tanfit.helpers.UIHelper;
import com.ingic.tanfit.interfaces.RecyclerViewItemListener;
import com.ingic.tanfit.interfaces.SetChildTitlebar;
import com.ingic.tanfit.map.abstracts.GoogleMapOptions;
import com.ingic.tanfit.map.abstracts.MapMarkerItemBinder;
import com.ingic.tanfit.ui.binders.SearchItemBinder;
import com.ingic.tanfit.ui.views.AutoCompleteLocation;
import com.ingic.tanfit.ui.views.CustomRecyclerView;
import com.ingic.tanfit.ui.views.TitleBar;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.bendik.simplerangeview.SimpleRangeView;

/**
 * Created by saeedhyder on 11/23/2017.
 */
public class SearchFragment extends BaseFragment implements OnMapReadyCallback, RecyclerViewItemListener {


    SupportMapFragment mapFragment;
    @BindView(R.id.btn_apply)
    Button btnApply;
    @BindView(R.id.lv_companies)
    CustomRecyclerView lv_companies;
    @BindView(R.id.rangeview)
    SimpleRangeView rangeView;
    @BindView(R.id.autoComplete)
    AutoCompleteLocation autoComplete;
    @BindView(R.id.img_gps)
    ImageView imgGps;
    @BindView(R.id.ll_filters)
    LinearLayout llFilters;
    @BindView(R.id.btn_showFilters)
    Button btnShowFilters;

    private SetChildTitlebar childTitlebar;
    private ArrayList<MapScreenItem> mapCollection = new ArrayList<>();
    private GoogleMap mMap;
    private View viewParent;
    private ArrayList<SearchRecyclerEnt> userCollections;

    public static SearchFragment newInstance() {
        Bundle args = new Bundle();

        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setChildTitlebar(SetChildTitlebar childTitlebar) {
        this.childTitlebar = childTitlebar;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
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

        lv_companies.setVisibility(View.GONE);

        childTitlebar = (MainFragment) getParentFragment();
        if (childTitlebar != null) {
            childTitlebar.setChildTitlebar(null, AppConstants.SEARCH_FRAGMENT_TAG);
        }
        setRecyclerViewData();
        setRangeBar();
        setGpsIcon();


    }

    private void setGpsIcon() {

        autoComplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    imgGps.setVisibility(View.VISIBLE);
                } else {
                    imgGps.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setRangeBar() {
        rangeView.setOnRangeLabelsListener(new SimpleRangeView.OnRangeLabelsListener() {
            @Nullable
            @Override
            public String getLabelTextForPosition(@NotNull SimpleRangeView rangeView, int pos, @NotNull SimpleRangeView.State state) {
                if (pos < 11) {
                    return String.valueOf((pos + 1) + " am");
                } else if (pos + 1 == 12) {

                    return String.valueOf((pos + 1) + " pm");
                } else if (pos + 1 == 24) {

                    return String.valueOf((pos + 1) - 12 + " am");

                } else {
                    return String.valueOf((pos + 1) - 12 + " pm");
                }

            }
        });
    }

    private void setRecyclerViewData() {

        userCollections = new ArrayList<>();
        userCollections.add(new SearchRecyclerEnt(AppConstants.DRAWABLE_PATH + R.drawable.image8, "Troh Gym", "Meimi Beach FL USA"));
        userCollections.add(new SearchRecyclerEnt(AppConstants.DRAWABLE_PATH + R.drawable.image9, "Troh Gym", "Meimi Beach FL USA"));
        userCollections.add(new SearchRecyclerEnt(AppConstants.DRAWABLE_PATH + R.drawable.images10, "Troh Gym", "Meimi Beach FL USA"));

        lv_companies.BindRecyclerView(new SearchItemBinder(this), userCollections,
                new LinearLayoutManager(getDockActivity(), LinearLayoutManager.HORIZONTAL, false)
                , new DefaultItemAnimator());

    }

    private void initMap() {
        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void getLocation(AutoCompleteTextView textView) {
        if (getMainActivity().statusCheck()) {
            LocationModel locationModel = getMainActivity().getMyCurrentLocation();
            if (locationModel != null) {
                textView.setText(locationModel.getAddress());
            } else {
                getLocation(autoComplete);
            }
        }
    }

    private void bindview() {

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.valueOf(25.204849), Double.valueOf(55.270783)), AppConstants.zoomIn));

        mapCollection = new ArrayList<>();

        mapCollection.add(new MapScreenItem("25.204849", "55.270783", R.drawable.circle2marker));
        mapCollection.add(new MapScreenItem("25.209740", "55.274330", R.drawable.circle1marker));
        mapCollection.add(new MapScreenItem("25.218322", "55.309210", R.drawable.circle2marker));
        mapCollection.add(new MapScreenItem("25.259935", "55.292387", R.drawable.circle1marker));
        mapCollection.add(new MapScreenItem("25.276391", "55.362768", R.drawable.circle2marker));
        mapCollection.add(new MapScreenItem("25.208397", "55.271852", R.drawable.circle1marker));
       /* try {
            for (UserProfile user : resultuser) {
                if (!user.getGym_latitude().isEmpty()) {

                    userCollection.add(new MapScreenItem(user.getGym_latitude(),
                            user.getGym_longitude(), user.getProfile_image(), user.getId(), latitude, longitude));
                }
            }
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }*/

        addMarker(mapCollection);

    }

    void addMarker(final ArrayList<MapScreenItem> mapCollection) {

        GoogleMapOptions<MapScreenItem> googleMapOptions = new GoogleMapOptions<>(getDockActivity(),
                mMap,
                mapCollection,
                null,
                new MapMarkerItemBinder(getMainActivity(), getDockActivity())
        );

        // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.valueOf(userCollection.get(userCollection.size() - 1).getLat()), Double.valueOf(userCollection.get(userCollection.size() - 1).getLng())), AppConstants.zoomIn));
        //  mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.valueOf(language), Double.valueOf(longitude)), AppConstants.zoomIn));

        googleMapOptions.addMarkers();

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (mapCollection != null) {
                    for (final MapScreenItem item : mapCollection) {
                      /*  if (String.valueOf(item.getUserId()).equals(marker.getTag().toString())) {
                               getDockActivity().addDockableFragment(TrainerProfileFragment.newInstance(item.getUserId()), "TrainerProfileFragment");
                        }*/
                    }
                }
                return true;
            }
        });

        //  mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.valueOf(userCollection.get(userCollection.size() - 1).getLat()), Double.valueOf(userCollection.get(userCollection.size() - 1).getLng())), AppConstants.zoomInToTrainer));


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }


    @OnClick({R.id.btn_apply, R.id.img_gps, R.id.btn_showFilters})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_apply:
                UIHelper.hideSoftKeyboard(getDockActivity(), view);
                llFilters.setVisibility(View.GONE);
                lv_companies.setVisibility(View.VISIBLE);
                btnShowFilters.setVisibility(View.VISIBLE);
                bindview();
                break;
            case R.id.img_gps:
                UIHelper.hideSoftKeyboard(getDockActivity(), view);
                getLocation(autoComplete);
                break;

            case R.id.btn_showFilters:
                UIHelper.hideSoftKeyboard(getDockActivity(), view);
                btnShowFilters.setVisibility(View.GONE);
                llFilters.setVisibility(View.VISIBLE);
                break;


        }

    }

    @Override
    public void onPause() {
        super.onPause();
        UIHelper.hideSoftKeyboard(getDockActivity(), getMainActivity().getWindow().getDecorView());
    }

    @Override
    public void onResume() {
        super.onResume();
        UIHelper.hideSoftKeyboard(getDockActivity(), getMainActivity().getWindow().getDecorView());
    }

    @Override
    public void onRecyclerItemClicked(Object Ent, int position) {

        getDockActivity().replaceDockableFragment(ClassDetailFragment.newInstance(), "ClassDetailFragment");
    }
}
