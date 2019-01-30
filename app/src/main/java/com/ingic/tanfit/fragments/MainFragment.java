package com.ingic.tanfit.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.GetNearestStudiosEnt;
import com.ingic.tanfit.entities.LocationModel;
import com.ingic.tanfit.entities.UserAllDataEnt;
import com.ingic.tanfit.entities.UserLocationModel;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.global.AppConstants;
import com.ingic.tanfit.global.WebServiceConstants;
import com.ingic.tanfit.helpers.ISO8601TimeStampHelper;
import com.ingic.tanfit.helpers.UIHelper;
import com.ingic.tanfit.interfaces.LocationUpdateListner;
import com.ingic.tanfit.interfaces.SetChildTitlebar;
import com.ingic.tanfit.interfaces.locationInterface;
import com.ingic.tanfit.ui.adapters.TabViewPagerAdapter;
import com.ingic.tanfit.ui.views.TitleBar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created on gym_image_11/24/2017.
 */
public class MainFragment extends BaseFragment implements SetChildTitlebar, locationInterface, LocationUpdateListner {
    @BindView(R.id.viewpager)
    FrameLayout viewpager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    Unbinder unbinder;
    @BindView(R.id.progressBarHome)
    ProgressBar progressBarHome;
    private TabViewPagerAdapter adapter;
    private TitleBar titleBar;
    private int[] tabIcons = {R.drawable.home, R.drawable.search, R.drawable.subscription};
    private int startWithTab = 0;
    private int tabTag = 0;
    private LocationModel locationModel;
    private Handler mHandler;
    private Runnable mUpdateResults;
    ISO8601TimeStampHelper timeStamp = new ISO8601TimeStampHelper();
    public boolean loading;


    public static MainFragment newInstance() {
//        Bundle args = new Bundle();

//        MainFragment fragment =  new MainFragment();
//        fragment.setArguments(args);
        return new MainFragment();
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

        getMainActivity().setLocationListner(this);

        if (titleBar != null && prefHelper.getUserAllData().getUserThumbnailImage() != null) {
            titleBar.setMenuButtonImage(getDockActivity(), prefHelper.getUserAllData().getUserThumbnailImage());
        }


        setViewInTabLayout();

        homeServicesCalling();


        getDockActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // UIHelper.showLongToastInCenter(getDockActivity(),"asdasdasdasdasdasd");
                LoadingStarted();
            }
        });


    }


    protected void homeServicesCalling() {

        // Fire off a thread to do some work that we shouldn't do directly in the UI thread
        Thread t = new Thread() {
            public void run() {

                requestLocationPermission();

            }
        };
        t.start();
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
                            updateResultsInUi();
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
                       /* UIHelper.showShortToastInCenter(getDockActivity(), "Grant Location Permission to processed");
                        openSettings();*/
                        updateResultsInUi();
                    }
                })

                .onSameThread()
                .check();


    }

    public TitleBar getTitleBarInstance() {

        return titleBar;

    }

    private void updateResultsInUi() {

        if (getMainActivity().statusCheckMainThread()) {
            //  serviceHelper.enqueueCallHome(headerWebService.getAllStudios(0,10),WebServiceConstants.getAllStudios);
            if (!prefHelper.getIsUserGetData()) {
                locationModel = getMainActivity().getMyCurrentLocation();
                prefHelper.setIsGetUser(true);
                serviceHelper.enqueueCallHome(headerWebService.userAllData(prefHelper.getUser().getUserId()), WebServiceConstants.userAllDAta);
            } /*else if (prefHelper.getNearestStuidos() != null && prefHelper.getNearestStuidos().getStudios().size() <= 0) {
                locationModel = getMainActivity().getMyCurrentLocation();
                serviceHelper.enqueueCallHome(headerWebService.userAllData(prefHelper.getUser().getUserId()), WebServiceConstants.userAllDAta);
            }else if (prefHelper.getNearestStuidos() != null && prefHelper.getNearestStuidos().getStudios().size() > 0) {*/ else if (prefHelper.getNearestStuidos() != null) {
                getDockActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setViewPager(prefHelper.getNearestStuidos());
                    }
                });
            }
        } else {
            getDockActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //    getDockActivity().onLoadingFinished();
                    GetNearestStudiosEnt getNearestStudiosEnt = new GetNearestStudiosEnt();
                    setViewPager(getNearestStudiosEnt);
                }
            });
        }


      /*  if (getMainActivity().statusCheck() && !prefHelper.getIsUserGetData()) {
            locationModel = getMainActivity().getMyCurrentLocation();
            prefHelper.setIsGetUser(true);
            serviceHelper.enqueueCallHome(headerWebService.userAllData(prefHelper.getUser().getUserId()), WebServiceConstants.userAllDAta);
        } else if (prefHelper.getNearestStuidos() != null && prefHelper.getNearestStuidos().getStudios().size() <= 0) {
            locationModel = getMainActivity().getMyCurrentLocation();
            serviceHelper.enqueueCallHome(headerWebService.userAllData(prefHelper.getUser().getUserId()), WebServiceConstants.userAllDAta);
        } else if (prefHelper.getNearestStuidos() != null && prefHelper.getNearestStuidos().getStudios().size() > 0) {
            getDockActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setViewPager(prefHelper.getNearestStuidos());
                }
            });
        }*/
    }


    @Override
    public void ResponseSuccess(final Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {

            case WebServiceConstants.userAllDAta:


                UserAllDataEnt entity = (UserAllDataEnt) result;
                prefHelper.putUserAllData(entity);
                prefHelper.setUserId(entity.getId());

                if (entity.getUserLocationModel().size() == 0) {
                    if (locationModel != null && String.valueOf(locationModel.getLat()) != null)
                        serviceHelper.enqueueCallHome(headerWebService.addUserLocation(entity.getId(), String.valueOf(locationModel.getLat()), String.valueOf(locationModel.getLng()), locationModel.getAddress()), WebServiceConstants.addUserLocation);
                    else {
                        serviceHelper.enqueueCallHome(headerWebService.addUserLocation(entity.getId(), "35.705240", "51.435577", "Tehran Province, Iran"), WebServiceConstants.addUserLocation);
                    }

                } else {
                   /* if (locationModel != null) {
                        serviceHelper.enqueueCallHome(headerWebService.addUserLocation(entity.getId(), String.valueOf(locationModel.getLat()), String.valueOf(locationModel.getLng()), locationModel.getAddress()), WebServiceConstants.addUserLocation);
                    } else {*/
                    //  serviceHelper.enqueueCallHome(headerWebService.getNearestStudios(entity.getId(), String.valueOf(entity.getUserLocationModel().get(entity.getUserLocationModel().size() - 1).getLatitude()), String.valueOf(entity.getUserLocationModel().get(entity.getUserLocationModel().size() - 1).getLongitude()), 5000), WebServiceConstants.getNearestStudios);

                    //serviceHelper.enqueueCallHome(headerWebService.getNearestStudiosLite(entity.getId(), String.valueOf(entity.getUserLocationModel().get(entity.getUserLocationModel().size() - 1).getLatitude()), String.valueOf(entity.getUserLocationModel().get(entity.getUserLocationModel().size() - 1).getLongitude()), timeStamp.getISO8601StringForCurrentDate()), WebServiceConstants.getNearestStudios);
                    //requestLocationPermission(entity);

                    LocationModel locationModel = getMainActivity().getMyCurrentLocation();

                    if (getMainActivity().statusCheck()) {
                        double latitude = 0.0;
                        double longitude = 0.0;

                        if (locationModel != null && String.valueOf(locationModel.getLat()) != null) {
                            latitude = locationModel.getLat();
                            longitude = locationModel.getLng();
                        }

                        if (latitude != 0.0)
                            serviceHelper.enqueueCallHome(headerWebService.getNearestStudiosLite(entity.getId(), String.valueOf(latitude), String.valueOf(longitude), timeStamp.getISO8601StringForCurrentDate()), WebServiceConstants.getNearestStudios);
                        else
                            serviceHelper.enqueueCallHome(headerWebService.getNearestStudiosLite(entity.getId(), String.valueOf(entity.getUserLocationModel().get(entity.getUserLocationModel().size() - 1).getLatitude()), String.valueOf(entity.getUserLocationModel().get(entity.getUserLocationModel().size() - 1).getLongitude()), timeStamp.getISO8601StringForCurrentDate()), WebServiceConstants.getNearestStudios);

                    } else {
                        serviceHelper.enqueueCallHome(headerWebService.getNearestStudiosLite(entity.getId(), String.valueOf(entity.getUserLocationModel().get(entity.getUserLocationModel().size() - 1).getLatitude()), String.valueOf(entity.getUserLocationModel().get(entity.getUserLocationModel().size() - 1).getLongitude()), timeStamp.getISO8601StringForCurrentDate()), WebServiceConstants.getNearestStudios);

                    }
                }
                break;

            case WebServiceConstants.addUserLocation:
                UserLocationModel locationModel = (UserLocationModel) result;

                // serviceHelper.enqueueCallHome(headerWebService.getNearestStudios(prefHelper.getUserAllData().getId(), locationModel.getLatitude() + "", locationModel.getLongitude() + "", 5000), WebServiceConstants.getNearestStudios);
                serviceHelper.enqueueCallHome(headerWebService.getNearestStudiosLite(prefHelper.getUserAllData().getId(), locationModel.getLatitude() + "", locationModel.getLongitude() + "", timeStamp.getISO8601StringForCurrentDate()), WebServiceConstants.getNearestStudios);


                break;


            case WebServiceConstants.getNearestStudios:
                getDockActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        GetNearestStudiosEnt data = (GetNearestStudiosEnt) result;

                        prefHelper.putNearestStudios(data);
                        if (data != null) {
                            setViewPager(data);
                        } else {
                            setViewPager(prefHelper.getNearestStuidos());
                        }
                    }
                });

                break;


        }
    }

    private void requestLocationPermission(final UserAllDataEnt entity) {
        Dexter.withActivity(getDockActivity())
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {

                        LocationModel locationModel = getMainActivity().getMyCurrentLocation();


                        if (report.areAllPermissionsGranted()) {
                            if (getMainActivity().statusCheck()) {
                                double latitude = locationModel.getLat();
                                double longitude = locationModel.getLng();

                                if (latitude != 0.0)
                                    serviceHelper.enqueueCallHome(headerWebService.getNearestStudiosLite(entity.getId(), String.valueOf(latitude), String.valueOf(longitude), timeStamp.getISO8601StringForCurrentDate()), WebServiceConstants.getNearestStudios);
                                else
                                    serviceHelper.enqueueCallHome(headerWebService.getNearestStudiosLite(entity.getId(), String.valueOf(entity.getUserLocationModel().get(entity.getUserLocationModel().size() - 1).getLatitude()), String.valueOf(entity.getUserLocationModel().get(entity.getUserLocationModel().size() - 1).getLongitude()), timeStamp.getISO8601StringForCurrentDate()), WebServiceConstants.getNearestStudios);

                            } else {
                                serviceHelper.enqueueCallHome(headerWebService.getNearestStudiosLite(entity.getId(), String.valueOf(entity.getUserLocationModel().get(entity.getUserLocationModel().size() - 1).getLatitude()), String.valueOf(entity.getUserLocationModel().get(entity.getUserLocationModel().size() - 1).getLongitude()), timeStamp.getISO8601StringForCurrentDate()), WebServiceConstants.getNearestStudios);

                            }
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            serviceHelper.enqueueCallHome(headerWebService.getNearestStudiosLite(entity.getId(), String.valueOf(entity.getUserLocationModel().get(entity.getUserLocationModel().size() - 1).getLatitude()), String.valueOf(entity.getUserLocationModel().get(entity.getUserLocationModel().size() - 1).getLongitude()), timeStamp.getISO8601StringForCurrentDate()), WebServiceConstants.getNearestStudios);

                        } else if (report.getDeniedPermissionResponses().size() > 0) {
                            serviceHelper.enqueueCallHome(headerWebService.getNearestStudiosLite(entity.getId(), String.valueOf(entity.getUserLocationModel().get(entity.getUserLocationModel().size() - 1).getLatitude()), String.valueOf(entity.getUserLocationModel().get(entity.getUserLocationModel().size() - 1).getLongitude()), timeStamp.getISO8601StringForCurrentDate()), WebServiceConstants.getNearestStudios);

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        //    serviceHelper.enqueueCallHome(headerWebService.getNearestStudiosLite(entity.getId(), String.valueOf(entity.getUserLocationModel().get(entity.getUserLocationModel().size() - 1).getLatitude()), String.valueOf(entity.getUserLocationModel().get(entity.getUserLocationModel().size() - 1).getLongitude()), timeStamp.getISO8601StringForCurrentDate()), WebServiceConstants.getNearestStudios);
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        serviceHelper.enqueueCallHome(headerWebService.getNearestStudiosLite(entity.getId(), String.valueOf(entity.getUserLocationModel().get(entity.getUserLocationModel().size() - 1).getLatitude()), String.valueOf(entity.getUserLocationModel().get(entity.getUserLocationModel().size() - 1).getLongitude()), timeStamp.getISO8601StringForCurrentDate()), WebServiceConstants.getNearestStudios);

                    }
                })
                .onSameThread()
                .check();
    }

    @Override
    public void ResponseFailure(String tag) {
        super.ResponseFailure(tag);
        LoadingFinished();
        //  UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.network_issue));

        if (getMainActivity().statusCheckMainThread()) {
            if (prefHelper.getNearestStuidos() != null) {
                getDockActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setViewPager(prefHelper.getNearestStuidos());
                    }
                });
            }
        } else {
            getDockActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    GetNearestStudiosEnt getNearestStudiosEnt = new GetNearestStudiosEnt();
                    setViewPager(getNearestStudiosEnt);
                }
            });
        }
    }

    private void setViewInTabLayout() {

        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.GRAY);
        drawable.setSize(1, 1);
        //  linearLayout.setDividerPadding(10);
        linearLayout.setDividerDrawable(drawable);
    }

    private void ReplaceTab(int position) {

        if (adapter.getItem(position) != null) {

            FragmentTransaction transaction = getChildFragmentManager()
                    .beginTransaction();
            transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

            transaction.replace(R.id.viewpager, adapter.getItem(position));
            // transaction.commit();
            transaction.commitAllowingStateLoss();
        }
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

    private void setViewPager(GetNearestStudiosEnt data) {

        System.gc();

        if (adapter.getCount() > 0) {
            adapter.clearList();
        }

        HomeFragment home = new HomeFragment();
        home.setContent(data);
        adapter.addFragment(home, getDockActivity().getResources().getString(R.string.home));
        adapter.addFragment(new SearchFragment(), getDockActivity().getResources().getString(R.string.search));
        adapter.addFragment(new SubscriptionFragment(), getDockActivity().getResources().getString(R.string.subscription_plans));

        LoadingFinished();
//        viewpager.setAdapter(adapter);
//        viewpager.setPageMargin(0);
//        viewpager.getAdapter().notifyDataSetChanged();
        //    tabLayout.setupWithViewPager(viewpager);

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
        if (tabLayout != null) {
            tabLayout.removeAllTabs();
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
    }


    @Override
    public void setChildTitlebar(String heading, int Tag) {
        tabTag = Tag;
        if (titleBar != null) {
            switch (Tag) {
                case AppConstants.HOME_FRAGMENT_TAG:
                    titleBar.showTitleBar();
                    titleBar.hideButtons();
                    titleBar.showMenuButton(getDockActivity(), prefHelper.getUser().getUserThumbnailImage());
                   /* titleBar.showFilterButton(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MainFragment mainFragment = MainFragment.newInstance();
                            mainFragment.setStartWithTab(AppConstants.SEARCH_FRAGMENT_TAG);
                            getDockActivity().replaceDockableFragment(mainFragment);

                        }
                    });*/
                    titleBar.setSubHeading(getDockActivity().getResources().getString(R.string.home));
                    break;
                case AppConstants.SEARCH_FRAGMENT_TAG:
                    titleBar.hideTitleBar();
                    break;
                case AppConstants.SUBSCRIPTION_FRAGMENT_TAG:
                    titleBar.showTitleBar();
                    titleBar.hideButtons();
                    titleBar.showMenuButton(getDockActivity(), prefHelper.getUser().getUserThumbnailImage());
                    titleBar.setSubHeading(getDockActivity().getResources().getString(R.string.subscription_plans));
                    break;

            }
        }

    }

    @Override
    public void getGoogleLocation(double latitude, double longitude) {


        //  locationModel=new LocationModel("",latitude,longitude);
    }


    @Override
    public void updateLocationFragment() {


        if (getMainActivity().statusCheckMainThread()) {
            //  serviceHelper.enqueueCallHome(headerWebService.getAllStudios(0,10),WebServiceConstants.getAllStudios);
            if (!prefHelper.getIsUserGetData()) {
                locationModel = getMainActivity().getMyCurrentLocation();
                prefHelper.setIsGetUser(true);
                serviceHelper.enqueueCallHome(headerWebService.userAllData(prefHelper.getUser().getUserId()), WebServiceConstants.userAllDAta);
            } /*else if (prefHelper.getNearestStuidos() != null && prefHelper.getNearestStuidos().getStudios().size() <= 0) {
                locationModel = getMainActivity().getMyCurrentLocation();
                serviceHelper.enqueueCallHome(headerWebService.userAllData(prefHelper.getUser().getUserId()), WebServiceConstants.userAllDAta);
            }else if (prefHelper.getNearestStuidos() != null && prefHelper.getNearestStuidos().getStudios().size() > 0) {*/ else if (prefHelper.getNearestStuidos() != null) {
                getDockActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setViewPager(prefHelper.getNearestStuidos());
                    }
                });
            }
        } else {
            getDockActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //    getDockActivity().onLoadingFinished();
                    GetNearestStudiosEnt getNearestStudiosEnt = new GetNearestStudiosEnt();
                    setViewPager(getNearestStudiosEnt);
                }
            });
        }

      /*  if (getMainActivity().statusCheckMainThread()) {
            if (!prefHelper.getIsUserGetData()) {
                locationModel = getMainActivity().getMyCurrentLocation();
                prefHelper.setIsGetUser(true);
                serviceHelper.enqueueCallHome(headerWebService.userAllData(prefHelper.getUser().getUserId()), WebServiceConstants.userAllDAta);
            } else if (prefHelper.getNearestStuidos() != null && prefHelper.getNearestStuidos().getStudios().size() <= 0) {
                locationModel = getMainActivity().getMyCurrentLocation();
                serviceHelper.enqueueCallHome(headerWebService.userAllData(prefHelper.getUser().getUserId()), WebServiceConstants.userAllDAta);
            } else if (prefHelper.getNearestStuidos() != null && prefHelper.getNearestStuidos().getStudios().size() > 0) {
                getDockActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setViewPager(prefHelper.getNearestStuidos());
                    }
                });
            }
        } else {
            getDockActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //   getDockActivity().onLoadingFinished();
                    GetNearestStudiosEnt getNearestStudiosEnt = new GetNearestStudiosEnt();
                    setViewPager(getNearestStudiosEnt);
                }
            });

        }*/

    }


    public void LoadingStarted() {

        if (viewpager != null) {
            getMainActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            viewpager.setVisibility(View.VISIBLE);
            if (progressBarHome != null) {
                progressBarHome.setVisibility(View.VISIBLE);
            }
            loading = true;
        }
    }


    public void LoadingFinished() {
        viewpager.setVisibility(View.VISIBLE);
        getMainActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        if (progressBarHome != null) {

            progressBarHome.setVisibility(View.INVISIBLE);
        }
        loading = false;

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

    @Override
    public void onResume() {
        super.onResume();

    }


}