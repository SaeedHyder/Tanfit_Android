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
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.GetActivitiesEnt;
import com.ingic.tanfit.entities.LocationModel;
import com.ingic.tanfit.entities.MapScreenItem;
import com.ingic.tanfit.entities.SearchRecyclerEnt;
import com.ingic.tanfit.entities.Studio;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.global.AppConstants;
import com.ingic.tanfit.global.WebServiceConstants;
import com.ingic.tanfit.helpers.UIHelper;
import com.ingic.tanfit.interfaces.RecyclerViewItemListener;
import com.ingic.tanfit.interfaces.SetChildTitlebar;
import com.ingic.tanfit.map.abstracts.GoogleMapOptions;
import com.ingic.tanfit.map.abstracts.MapMarkerItemBinder;
import com.ingic.tanfit.range_bar.SimpleRangeView;
import com.ingic.tanfit.ui.adapters.AutoCompleteCustomAdapter;
import com.ingic.tanfit.ui.binders.SearchItemBinder;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.AutoCompleteLocation;
import com.ingic.tanfit.ui.views.CustomRecyclerView;
import com.ingic.tanfit.ui.views.RangeSeekBar;
import com.ingic.tanfit.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by saeedhyder on gym_image_11/23/2017.
 */
public class SearchFragment extends BaseFragment implements OnMapReadyCallback, RecyclerViewItemListener {


    SupportMapFragment mapFragment;
    @BindView(R.id.btn_apply)
    Button btnApply;
    @BindView(R.id.lv_companies)
    CustomRecyclerView lv_companies;

    @BindView(R.id.autoComplete)
    AutoCompleteLocation autoComplete;
    @BindView(R.id.img_gps)
    ImageView imgGps;
    @BindView(R.id.ll_filters)
    LinearLayout llFilters;
    @BindView(R.id.btn_showFilters)
    Button btnShowFilters;
    @BindView(R.id.rangeview)
    SimpleRangeView rangeview;
    @BindView(R.id.hourRangeBar)
    RangeSeekBar hourRangeBar;
    @BindView(R.id.txt_starttext)
    AnyTextView txtStarttext;
    @BindView(R.id.txt_endtext)
    AnyTextView txtEndtext;
    @BindView(R.id.edit_search)
    AutoCompleteTextView editSearch;

    private SetChildTitlebar childTitlebar;
    private ArrayList<MapScreenItem> mapCollection = new ArrayList<>();
    private GoogleMap mMap;
    private View viewParent;
    private ArrayList<SearchRecyclerEnt> userCollections;
    private AutoCompleteCustomAdapter adapter = null;
    private double locationLat;
    private double locationLng;
    private String minTime = "0:00:00";
    private String maxTime = "23:00:00";
    private int ActivityId = 0;
    private ArrayList<Studio> entity;


    ArrayList<GetActivitiesEnt> activityList;

    public static SearchFragment newInstance() {
        Bundle args = new Bundle();

        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s).replace(' ', '*');
    }

    public static String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s).replace(' ', '*');
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
    public void onResume() {
        super.onResume();
        UIHelper.hideSoftKeyboard(getDockActivity(), getMainActivity().getWindow().getDecorView());
    }

    @Override
    public void onPause() {
        super.onPause();
        UIHelper.hideSoftKeyboard(getDockActivity(), getMainActivity().getWindow().getDecorView());
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


        // setRangeBar();
        setGpsIcon();
        setLatLngOnAutoComplete();
        setRangeSeekBar();
        serviceHelper.enqueueCall(headerWebService.getActivities(), WebServiceConstants.getActivities);


    }

    private void setLatLngOnAutoComplete() {

        autoComplete.setAutoCompleteTextListener(new AutoCompleteLocation.AutoCompleteLocationListener() {
            @Override
            public void onTextClear() {

            }

            @Override
            public void onItemSelected(Place selectedPlace) {
                locationLat = selectedPlace.getLatLng().latitude;
                locationLng = selectedPlace.getLatLng().longitude;

            }
        });
    }


    private void setActivityAutoComplete(final ArrayList<GetActivitiesEnt> result) {
        activityList = new ArrayList<>();
      /*  activityList.add(new ActivityAutoCompleteEnt(AppConstants.DRAWABLE_PATH + R.drawable.group_training, "Group Personal Training", R.drawable.group_training));
        activityList.add(new ActivityAutoCompleteEnt(AppConstants.DRAWABLE_PATH + R.drawable.power_yoga, "Power Yoga", R.drawable.power_yoga));
        activityList.add(new ActivityAutoCompleteEnt(AppConstants.DRAWABLE_PATH + R.drawable.pelton_biking, "Pelton Biking", R.drawable.pelton_biking));
        activityList.add(new ActivityAutoCompleteEnt(AppConstants.DRAWABLE_PATH + R.drawable.group_training, "Group Personal Training", R.drawable.group_training));
*/
        adapter = new AutoCompleteCustomAdapter(getDockActivity(), result);
        editSearch.setAdapter(adapter);

        editSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ActivityId = result.get(position).getId();
            }
        });
    }

    private void setRangeSeekBar() {
        hourRangeBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Number minValue, Number maxValue) {

                int startvalue = (int) minValue - 1;
                int endvalue = (int) maxValue - 1;

                minTime = startvalue + ":00:00";
                maxTime = endvalue + ":00:00";

                if ((maxValue.intValue() - minValue.intValue() <= 1)) {
                    if (maxValue.intValue() == 24) {
                        bar.setSelectedMinValue(minValue.intValue() - 1);
                    } else if (minValue.intValue() == 1) {
                        bar.setSelectedMaxValue(maxValue.intValue() + 1);
                    } else {
                        bar.setSelectedMaxValue(maxValue.intValue() + 1);
                    }
                }
            }
        });

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


    private void setRecyclerViewData(ArrayList<Studio> entity) {

        userCollections = new ArrayList<>();

        for (Studio item : entity) {
            userCollections.add(new SearchRecyclerEnt(item.getStudioLogo(), item.getStudioNameEng() + "", item.getAddressEng() + ""));
        }

       /* userCollections.add(new SearchRecyclerEnt(AppConstants.DRAWABLE_PATH + R.drawable.search_image_1, "Troh Gym", "Meimi Beach, FL, USA"));
        userCollections.add(new SearchRecyclerEnt(AppConstants.DRAWABLE_PATH + R.drawable.search_image_2, "15 Mintues Body", "Meimi Beach, FL, USA"));
        userCollections.add(new SearchRecyclerEnt(AppConstants.DRAWABLE_PATH + R.drawable.search_image_1, "Troh Gym", "Meimi Beach, FL, USA"));
*/
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
                locationLat = locationModel.getLat();
                locationLng = locationModel.getLng();
            } else {
                getLocation(autoComplete);
            }
        }
    }

    private void bindview(ArrayList<Studio> entity) {

        if (entity.size() > 0) {
            //  mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.valueOf(25.204849), Double.valueOf(55.270783)), AppConstants.zoomIn));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.valueOf(entity.get(0).getLatitude()), Double.valueOf(entity.get(0).getLongitude())), AppConstants.zoomIn));

            mapCollection = new ArrayList<>();

            for (Studio item : entity) {

                mapCollection.add(new MapScreenItem(item.getLatitude() + "", item.getLongitude() + "", R.drawable.circle2marker));
            }

       /* mapCollection.add(new MapScreenItem("25.204849", "55.270783", R.drawable.circle2marker));
        mapCollection.add(new MapScreenItem("25.209740", "55.274330", R.drawable.circle1marker));
        mapCollection.add(new MapScreenItem("25.218322", "55.309210", R.drawable.circle2marker));
        mapCollection.add(new MapScreenItem("25.259935", "55.292387", R.drawable.circle1marker));
        mapCollection.add(new MapScreenItem("25.276391", "55.362768", R.drawable.circle2marker));
        mapCollection.add(new MapScreenItem("25.208397", "55.271852", R.drawable.circle1marker));*/
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

    private boolean isValidated() {
        if (autoComplete.getText() == null || (autoComplete.getText().toString().isEmpty())) {
            // autoComplete.setError(getString(R.string.enter_Address));
            UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.enter_Address));
            return false;
        } else if (editSearch.getText() == null || (editSearch.getText().toString().isEmpty())) {
            editSearch.setError(getString(R.string.enter_activity));
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @OnClick({R.id.btn_apply, R.id.img_gps, R.id.btn_showFilters})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_apply:
               /* if (isValidated()) {
                    UIHelper.hideSoftKeyboard(getDockActivity(), view);
                    serviceHelper.enqueueCall(headerWebService.searchNearestStudios(prefHelper.getUserAllData().getId(), locationLat + "", locationLng + "", ActivityId + "", prefHelper.getUserAllData().getGenderId() + "", minTime + "", maxTime + "", 5000), WebServiceConstants.searchNearestStudios);
                    //serviceHelper.enqueueCall(headerWebService.searchNearestStudios(prefHelper.getUserAllData().getId(), "24.814783829601804", "67.07380771636963", "2", prefHelper.getUserAllData().getGenderId() + "", "16:00:00", "19:00:00", 5000), WebServiceConstants.searchNearestStudios);
                }*/
                if (autoComplete.getText() != null && !(autoComplete.getText().toString().isEmpty())) {
                    if (editSearch.getText() == null || (editSearch.getText().toString().isEmpty())) {
                        ActivityId = 0;
                    }
                    serviceHelper.enqueueCall(headerWebService.searchNearestStudios(prefHelper.getUserAllData().getId(), locationLat + "", locationLng + "", ActivityId + "", prefHelper.getUserAllData().getGenderId() + "", minTime + "", maxTime + "", 5000), WebServiceConstants.searchNearestStudios);
                } else if (editSearch.getText() != null && !(editSearch.getText().toString().isEmpty())) {
                    locationLat = prefHelper.getUserAllData().getUserLocationModel().get(prefHelper.getUserAllData().getUserLocationModel().size() - 1).getLatitude();
                    locationLng = prefHelper.getUserAllData().getUserLocationModel().get(prefHelper.getUserAllData().getUserLocationModel().size() - 1).getLongitude();
                    serviceHelper.enqueueCall(headerWebService.searchNearestStudios(prefHelper.getUserAllData().getId(), locationLat + "", locationLng + "", ActivityId + "", prefHelper.getUserAllData().getGenderId() + "", minTime + "", maxTime + "", 5000), WebServiceConstants.searchNearestStudios);
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.address_activity));

                }
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
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {

            case WebServiceConstants.getActivities:
                setActivityAutoComplete((ArrayList<GetActivitiesEnt>) result);
                break;

            case WebServiceConstants.searchNearestStudios:
                ArrayList<Studio> entResult = (ArrayList<Studio>) result;
                entity = entResult;

                if (entResult.size() > 0) {
                    llFilters.setVisibility(View.GONE);
                    lv_companies.setVisibility(View.VISIBLE);
                    btnShowFilters.setVisibility(View.VISIBLE);
                    setRecyclerViewData(entResult);
                    bindview(entResult);
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), "No Result Found");
                }
                break;

        }
    }


    @Override
    public void onRecyclerItemClicked(Object Ent, int position) {
        if (entity != null)
            getDockActivity().replaceDockableFragment(GymDetailFragment.newInstance(entity.get(position).getId()), "GymDetailFragment");
    }
}
