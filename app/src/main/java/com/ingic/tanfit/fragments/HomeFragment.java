package com.ingic.tanfit.fragments;


import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.location.places.Place;
import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.GetNearestStudiosEnt;
import com.ingic.tanfit.entities.LocationModel;
import com.ingic.tanfit.entities.Studio;
import com.ingic.tanfit.entities.UserAllDataEnt;
import com.ingic.tanfit.entities.UserSubscription;
import com.ingic.tanfit.entities.remindingClassEnt;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.global.AppConstants;
import com.ingic.tanfit.global.WebServiceConstants;
import com.ingic.tanfit.helpers.CustomViewPager;
import com.ingic.tanfit.helpers.DateHelper;
import com.ingic.tanfit.helpers.ISO8601TimeStampHelper;
import com.ingic.tanfit.helpers.UIHelper;
import com.ingic.tanfit.interfaces.SetChildTitlebar;
import com.ingic.tanfit.ui.adapters.TabViewPagerAdapter;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.AutoCompleteLocation;
import com.ingic.tanfit.ui.views.TitleBar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeFragment extends BaseFragment {


    @BindView(R.id.txt_gym_type)
    AnyTextView txtGymType;
    @BindView(R.id.txt_gym_time)
    AnyTextView txtGymTime;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.rl_remindingClass)
    RelativeLayout rlRemindingClass;
    @BindView(R.id.autoComplete)
    AutoCompleteLocation autoComplete;
    @BindView(R.id.img_gps)
    ImageView imgGps;
    @BindView(R.id.rl_searchAutoComplete)
    RelativeLayout rlSearchAutoComplete;


    private TabViewPagerAdapter adapter;
    private SetChildTitlebar childTitlebar;

    GetNearestStudiosEnt entity;
    private ArrayList<Studio> data;
    private double locationLat;
    private double locationLng;
    private ISO8601TimeStampHelper timeStamp = new ISO8601TimeStampHelper();


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new TabViewPagerAdapter(getChildFragmentManager());

    }

    public void setContent(GetNearestStudiosEnt data) {
        this.entity = data;
    }

    public void setStudiosContent(ArrayList<Studio> data) {
        this.data = data;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        childTitlebar = (MainFragment) getParentFragment();

       /* swipeContainer.setOnRefreshListener(this);

        swipeContainer.setColorSchemeColors(getResources().getColor(R.color.app_blue));*/
        setGpsIcon();
        setLatLngOnAutoComplete();

        serviceHelper.enqueueCall(headerWebService.RemindingFitnessClass(prefHelper.getUser().getUserId() + ""), WebServiceConstants.RemindingCLass);
        serviceHelper.enqueueCall(headerWebService.getSubsccriptionData(prefHelper.getUser().getUserId() + ""), WebServiceConstants.getSubscription);


        setViewPager();
        setViewInTabLayout();

        getDockActivity().getSupportFragmentManager().addOnBackStackChangedListener(getListener());
        //   onBackStack();

      /*  if (prefHelper.isStudio()) {
            TabLayout.Tab tab = tabLayout.getTabAt(1);
            tab.select();
        }*/

    }

    private FragmentManager.OnBackStackChangedListener getListener() {
        FragmentManager.OnBackStackChangedListener result = new FragmentManager.OnBackStackChangedListener() {
            public void onBackStackChanged() {
                FragmentManager manager = getDockActivity().getSupportFragmentManager();

                if (manager != null) {
                    Fragment currFrag = getDockActivity().getSupportFragmentManager().findFragmentById(getDockActivity().getDockFrameLayoutId());
                    if (currFrag != null) {
                        if (currFrag instanceof MainFragment) {
                            if (childTitlebar != null) {
                                childTitlebar.setChildTitlebar(getDockActivity().getResources().getString(R.string.home), AppConstants.HOME_FRAGMENT_TAG);
                            }
                        }
                        //currFrag.onResume();
                    }
                }
            }
        };

        return result;
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

    private void setLatLngOnAutoComplete() {
        autoComplete.setAutoCompleteTextListener(new AutoCompleteLocation.AutoCompleteLocationListener() {
            @Override
            public void onTextClear() {

            }

            @Override
            public void onItemSelected(Place selectedPlace) {
                locationLat = selectedPlace.getLatLng().latitude;
                locationLng = selectedPlace.getLatLng().longitude;

                serviceHelper.enqueueCall(headerWebService.getNearestStudiosLite(prefHelper.getUserAllData().getId(), String.valueOf(locationLat), String.valueOf(locationLng),
                        timeStamp.getISO8601StringForCurrentDate()), WebServiceConstants.getNearestStudios);

            }
        }, prefHelper);
    }

    public void hideSearchBar() {
        if (rlSearchAutoComplete != null)
            rlSearchAutoComplete.setVisibility(View.GONE);
    }

    public void showSearchBar() {
        if (rlSearchAutoComplete != null)
            rlSearchAutoComplete.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (childTitlebar != null) {
            childTitlebar.setChildTitlebar(getDockActivity().getResources().getString(R.string.home), AppConstants.HOME_FRAGMENT_TAG);
        }


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

        }
    }

    /*   void onBackStack(){
           getDockActivity().getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener()
           {
               public void onBackStackChanged()
               {
                   android.support.v4.app.Fragment currentFragment = getDockActivity().getSupportFragmentManager().findFragmentById(getDockActivity().getDockFrameLayoutId());
    //
                   if (currentFragment instanceof MainFragment) {

                       if(((MainFragment)getParentFragment()).getTitleBarInstance()!=null){
                       TitleBar titleBar=((MainFragment)getParentFragment()).getTitleBarInstance();
                       titleBar.showTitleBar();
                       titleBar.hideButtons();
                       titleBar.showMenuButton(getDockActivity(), prefHelper.getUser().getUserThumbnailImage());
                       titleBar.showFilterButton(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               MainFragment mainFragment = MainFragment.newInstance();
                               mainFragment.setStartWithTab(AppConstants.SEARCH_FRAGMENT_TAG);
                               getDockActivity().replaceDockableFragment(mainFragment);

                           }
                       });
                       titleBar.setSubHeading(getDockActivity().getString(R.string.home));
                   }
               }else{
                       if(((MainFragment)getParentFragment()).getTitleBarInstance()!=null){
                           TitleBar titleBar=((MainFragment)getParentFragment()).getTitleBarInstance();
                           titleBar.showTitleBar();
                           titleBar.hideButtons();
                           titleBar.showBackButton();
                       }
                   }
               }
           });
       }*/
    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {

            case WebServiceConstants.RemindingCLass:
                remindingClassEnt remindingClass = (remindingClassEnt) result;
                if (remindingClass != null && remindingClass.getFitnessClass() != null) {
                    rlRemindingClass.setVisibility(View.VISIBLE);
                    txtGymType.setText(remindingClass.getFitnessClass().getClassNameEng() + "");
                    txtGymTime.setText(DateHelper.getFormatedDate("HH:mm:ss", "hh:mm aa", remindingClass.getSeletedTime().getTimeIn()));
                } else {
                    rlRemindingClass.setVisibility(View.GONE);
                }
                break;

            case WebServiceConstants.getSubscription:
                UserAllDataEnt userAppData = prefHelper.getUserAllData();

                if (userAppData == null) {
                    userAppData = new UserAllDataEnt();
                }

                ArrayList<UserSubscription> userSubscriptions = (ArrayList<UserSubscription>) result;

                if (userSubscriptions.size() > 0) {
                    userAppData.setUserSubscription(userSubscriptions);
                    prefHelper.putUserAllData(userAppData);
                }

                break;

            case WebServiceConstants.getNearestStudios:

                GetNearestStudiosEnt data = (GetNearestStudiosEnt) result;
                prefHelper.putNearestStudios(data);
                setContent(data);
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


        if (adapter.getCount() > 0) {
            adapter.clearList();
        }

        if (entity != null) {

            HomeFitnessClassFragment homeFitnessClass = new HomeFitnessClassFragment();
            homeFitnessClass.setContent(entity.getFitnessClassess());
            adapter.addFragment(homeFitnessClass, getDockActivity().getResources().getString(R.string.fitness_classes));

            HomeStudioFragment homeStudiosClass = new HomeStudioFragment();
            homeStudiosClass.setContent(entity.getStudios());
            adapter.addFragment(homeStudiosClass, getDockActivity().getResources().getString(R.string.studios));

       /* HomeStudioAllFragment homeAllStudioFragment = new HomeStudioAllFragment();
        adapter.addFragment(homeAllStudioFragment, getDockActivity().getResources().getString(R.string.allstudios));*/


            HomeFitnessAllClassesFragment homeFitnessAllClass = new HomeFitnessAllClassesFragment();
            adapter.addFragment(homeFitnessAllClass, getDockActivity().getResources().getString(R.string.all_classes));

        }


        viewpager.setOffscreenPageLimit(0);
        viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager);

        if (prefHelper.isStudio()) {
            TabLayout.Tab tab = tabLayout.getTabAt(1);
            tab.select();
        }
        //viewpager.getAdapter().notifyDataSetChanged();

       /* if(swipeContainer!=null && swipeContainer.isRefreshing()){
            swipeContainer.setRefreshing(false);}*/
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
      /*  titleBar.hideButtons();
        titleBar.showMenuButton();
        titleBar.setSubHeading("Home");
        titleBar.showNotificationButton(0);*/

    }


    @OnClick(R.id.img_gps)
    public void onViewClicked() {
        UIHelper.hideSoftKeyboard(getDockActivity(), imgGps);
        requestLocationPermission();
    }


    private void getLocation(AutoCompleteTextView textView) {
        if (getMainActivity().statusCheck()) {
            LocationModel locationModel = getMainActivity().getMyCurrentLocation();
            if (locationModel != null) {
                textView.setText(locationModel.getAddress());
                locationLat = locationModel.getLat();
                locationLng = locationModel.getLng();

                serviceHelper.enqueueCall(headerWebService.getNearestStudiosLite(prefHelper.getUserAllData().getId(), String.valueOf(locationLat), String.valueOf(locationLng),
                        timeStamp.getISO8601StringForCurrentDate()), WebServiceConstants.getNearestStudios);

            } else {
                getLocation(autoComplete);
            }
        }
    }

    private void requestLocationPermission() {
        Dexter.withActivity(getDockActivity())
                .withPermissions(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {

                        if (report.areAllPermissionsGranted()) {
                            getLocation(autoComplete);
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            requestLocationPermission();

                        } else if (report.getDeniedPermissionResponses().size() > 0) {
                            requestLocationPermission();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        UIHelper.showShortToastInCenter(getDockActivity(), "Grant Location Permission to processed");
                        openSettings();
                    }
                })

                .onSameThread()
                .check();


    }

    private void openSettings() {

        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        Uri uri = Uri.fromParts("package", getDockActivity().getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }






    /*
    @Override
    public void onRefresh() {
        setViewPager();
    }*/
}

