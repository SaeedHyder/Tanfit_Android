package com.ingic.tanfit.activities;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.AppDefaultSettingEnt;
import com.ingic.tanfit.entities.LocationModel;
import com.ingic.tanfit.fragments.MainFragment;
import com.ingic.tanfit.fragments.MyProfileFragment;
import com.ingic.tanfit.fragments.NotificationsFragment;
import com.ingic.tanfit.fragments.SideMenuFragment;
import com.ingic.tanfit.fragments.WelcomeFragment;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.global.AppConstants;
import com.ingic.tanfit.global.SideMenuChooser;
import com.ingic.tanfit.global.SideMenuDirection;
import com.ingic.tanfit.global.WebServiceConstants;
import com.ingic.tanfit.helpers.ScreenHelper;
import com.ingic.tanfit.helpers.ServiceHelper;
import com.ingic.tanfit.helpers.UIHelper;
import com.ingic.tanfit.interfaces.ImageSetter;
import com.ingic.tanfit.interfaces.LocationUpdateListner;
import com.ingic.tanfit.interfaces.locationInterface;
import com.ingic.tanfit.interfaces.webServiceResponseLisener;
import com.ingic.tanfit.residemenu.ResideMenu;
import com.ingic.tanfit.retrofit.WebService;
import com.ingic.tanfit.retrofit.WebServiceFactory;
import com.ingic.tanfit.ui.views.TitleBar;
import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ChosenImage;
import com.kbeanie.imagechooser.api.ChosenImages;
import com.kbeanie.imagechooser.api.ImageChooserListener;
import com.kbeanie.imagechooser.api.ImageChooserManager;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends DockActivity implements OnClickListener, ImageChooserListener, webServiceResponseLisener {
    private final static String TAG = "ICA";
    public TitleBar titleBar;
    @BindView(R.id.sideMneuFragmentContainer)
    public FrameLayout sideMneuFragmentContainer;
    @BindView(R.id.header_main)
    TitleBar header_main;
    @BindView(R.id.mainFrameLayout)
    FrameLayout mainFrameLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private MainActivity mContext;
    public boolean loading;
    private ImageSetter imageSetter;
    private ResideMenu resideMenu;
    private float lastTranslate = 0.0f;
    private String sideMenuType;
    private String sideMenuDirection;
    private ImageChooserManager imageChooserManager;
    private int chooserType;
    private String filePath;
    private String originalFilePath;
    private String thumbnailFilePath;
    private String thumbnailSmallFilePath;
    private boolean isActivityResultOver = false;
    protected ServiceHelper serviceHelper;
    protected WebService headerWebService;
    private LocationModel locationModel;
    private LocationManager locationManager;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    public LocationRequest mLocationRequest;
    private locationInterface locationInterface;
    private String address="";
    private String country="";
    private LocationUpdateListner locationUpdateListner;


    public void setImageSetter(ImageSetter imageSetter) {
        this.imageSetter = imageSetter;
    }

    public LocationModel getMyCurrentLocation() {


        String address = "";
        LocationModel locationObj = new LocationModel(address,35.705240,51.435577);
      //  LocationModel locationObj = new LocationModel(address,24.829759,67.073822);


// instantiate the location manager, note you will need to request permissions in your manifest
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


// get the last know location from your location manager.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
// now get the lat/lon from the location and do something with it.
        Location gpslocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location networklocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Location passivelocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        Location locationchangelocation = locationManager.getLastKnownLocation(LocationManager.KEY_LOCATION_CHANGED);


        if (gpslocation != null) {

            Log.d("Location", "GPS::" + gpslocation.getLatitude() + "," + gpslocation.getLongitude());
            address = getCurrentAddress(gpslocation.getLatitude(), gpslocation.getLongitude());
            locationObj = new LocationModel(address, gpslocation.getLatitude(), gpslocation.getLongitude());

            return locationObj;

        } else if (networklocation != null) {

            Log.d("Location", "NETWORK::" + networklocation.getLatitude() + "," + networklocation.getLongitude());
            address = getCurrentAddress(networklocation.getLatitude(), networklocation.getLongitude());
            locationObj = new LocationModel(address, networklocation.getLatitude(), networklocation.getLongitude());

            return locationObj;
        } else if (passivelocation != null) {

            Log.d("Location", "PASSIVE::" + passivelocation.getLatitude() + "," + passivelocation.getLongitude());
            address = getCurrentAddress(passivelocation.getLatitude(), passivelocation.getLongitude());
            locationObj = new LocationModel(address, passivelocation.getLatitude(), passivelocation.getLongitude());

            return locationObj;
        } else if (locationchangelocation != null) {

            Log.d("Location", "CHAGELOCATION::" + locationchangelocation.getLatitude() + "," + locationchangelocation.getLongitude());
            address = getCurrentAddress(locationchangelocation.getLatitude(), locationchangelocation.getLongitude());
            locationObj = new LocationModel(address, locationchangelocation.getLatitude(), locationchangelocation.getLongitude());

            return locationObj;
        }

        return locationObj;


    }

    private String getCurrentAddress(double lat, double lng) {
        try {


            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            addresses = geocoder.getFromLocation(lat, lng, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
           if(addresses.size()>0) {
                address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
           }
// String city = addresses.get(0).getLocality();
// String state = addresses.get(0).getAdminArea();
            if(addresses.size()>0) {
                country = addresses.get(0).getCountryName();
            }
// String postalCode = addresses.get(0).getPostalCode();
// String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
            return address + ", " + country;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void setLocationListner(LocationUpdateListner locationListner){
        this.locationUpdateListner=locationListner;
    }

    public boolean statusCheck() {
        if (isNetworkAvailable()) {
            final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                buildAlertMessageNoGps();
                return false;
            } else {
                return true;
            }
        } else {

            UIHelper.showShortToastInCenter(this, getString(R.string.internet_not_connected));
            return false;
        }
    }

    public boolean statusCheckMainThread() {
        if (isNetworkAvailable()) {
            final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                buildAlertMessageNoGps();
                return false;
            } else {
                return true;
            }
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    UIHelper.showShortToastInCenter(MainActivity.this, getString(R.string.internet_not_connected));
                }
            });

            return false;
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(getString(R.string.gps_question))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.gps_yes), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                      // startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), AppConstants.LocationUpdateListner);
                    }
                })
                .setNegativeButton(getResources().getString(R.string.gps_no), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }


    public View getDrawerView() {
        return getLayoutInflater().inflate(getSideMenuFrameLayoutId(), null);
    }



    private void settingSideMenu(String type, String direction) {

        if (type.equals(SideMenuChooser.DRAWER.getValue())) {

            DrawerLayout.LayoutParams params = new DrawerLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels, (int) DrawerLayout.LayoutParams.MATCH_PARENT);

        /*   int width = getResources().getDisplayMetrics().widthPixels;
            DrawerLayout.LayoutParams params = (android.support.v4.widget.DrawerLayout.LayoutParams) sideMneuFragmentContainer.getLayoutParams();
            params.width = width;*/


            //   DrawerLayout.LayoutParams params = new DrawerLayout.LayoutParams((int) DrawerLayout.LayoutParams.MATCH_PARENT, (int) DrawerLayout.LayoutParams.MATCH_PARENT);
            if (direction.equals(SideMenuDirection.LEFT.getValue())) {
                params.gravity = Gravity.LEFT;
                sideMneuFragmentContainer.setLayoutParams(params);

            } else {
                params.gravity = Gravity.RIGHT;
                sideMneuFragmentContainer.setLayoutParams(params);
            }
            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


            sideMenuFragment = MyProfileFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            transaction.replace(getSideMenuFrameLayoutId(), sideMenuFragment).commit();

            drawerLayout.closeDrawers();
        } else {
            resideMenu = new ResideMenu(this);
            resideMenu.attachToActivity(this);
            resideMenu.setMenuListener(getMenuListener());
            resideMenu.setScaleValue(0.52f);

            setMenuItemDirection(direction);
        }
    }

    private void setMenuItemDirection(String direction) {

        if (direction.equals(SideMenuDirection.LEFT.getValue())) {

            SideMenuFragment leftSideMenuFragment = SideMenuFragment.newInstance();
            resideMenu.addMenuItem(leftSideMenuFragment, "LeftSideMenuFragment", direction);

        } else if (direction.equals(SideMenuDirection.RIGHT.getValue())) {

            SideMenuFragment rightSideMenuFragment = SideMenuFragment.newInstance();
            resideMenu.addMenuItem(rightSideMenuFragment, "RightSideMenuFragment", direction);

        }

    }

    private int getSideMenuFrameLayoutId() {
        return R.id.sideMneuFragmentContainer;

    }

    public void initFragment() {
        getSupportFragmentManager().addOnBackStackChangedListener(getListener());
        prefHelper.setIsFromStudio(false);
        if (prefHelper.isLogin()) {
            replaceDockableFragment(MainFragment.newInstance(), "MainFragment");
        } else {
            replaceDockableFragment(WelcomeFragment.newInstance(), "WelcomeFragment");
        }
    }

    private FragmentManager.OnBackStackChangedListener getListener() {
        FragmentManager.OnBackStackChangedListener result = new FragmentManager.OnBackStackChangedListener() {
            public void onBackStackChanged() {
                FragmentManager manager = getSupportFragmentManager();

                if (manager != null) {
                    BaseFragment currFrag = (BaseFragment) manager.findFragmentById(getDockFrameLayoutId());
                    if (currFrag != null) {
                        currFrag.fragmentResume();
                    }
                }
            }
        };

        return result;
    }

    @Override
    public void onLoadingStarted() {

        if (mainFrameLayout != null) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            mainFrameLayout.setVisibility(View.VISIBLE);
            if (progressBar != null) {
                progressBar.setVisibility(View.VISIBLE);
            }
            loading = true;
        }
    }

    @Override
    public void onLoadingFinished() {
        mainFrameLayout.setVisibility(View.VISIBLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        if (progressBar != null) {

            progressBar.setVisibility(View.INVISIBLE);
        }
        loading = false;

    }

    @Override
    public void onProgressUpdated(int percentLoaded) {

    }

    @Override
    public int getDockFrameLayoutId() {
        return R.id.mainFrameLayout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dock);
        ButterKnife.bind(this);

        setCurrentLocale();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        titleBar = header_main;
        // setBehindContentView(R.layout.fragment_frame);
        mContext = this;
        Log.i("Screen Density", ScreenHelper.getDensity(this) + "");

        sideMenuType = SideMenuChooser.DRAWER.getValue();
        sideMenuDirection = SideMenuDirection.LEFT.getValue();

        settingSideMenu(sideMenuType, sideMenuDirection);
        prefHelper.setIsGetUser(false);
        // FirebaseCrash.log("Activity created");

        headerWebService = WebServiceFactory.getWebServiceInstanceWithCustomInterceptorandheader(this, WebServiceConstants.Local_SERVICE_URL);

        if (serviceHelper == null) {
            serviceHelper = new ServiceHelper(this, this, headerWebService);
        }

        serviceHelper.enqueueCallHome(headerWebService.getDefaultSetting(), WebServiceConstants.getDefaultSetting);


        titleBar.setMenuButtonListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                replaceDockableFragment(MyProfileFragment.newInstance(), "MyProfileFragment", false);
                /*if (sideMenuType.equals(SideMenuChooser.DRAWER.getValue()) && getDrawerLayout() != null) {
                    if (sideMenuDirection.equals(SideMenuDirection.LEFT.getValue())) {
                        drawerLayout.openDrawer(Gravity.LEFT);
                    } else {
                        drawerLayout.openDrawer(Gravity.RIGHT);
                    }
                } else {
                    resideMenu.openMenu(sideMenuDirection);
                }*/

            }
        });

        titleBar.setBackButtonListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (loading) {
                    UIHelper.showLongToastInCenter(getApplicationContext(),
                            R.string.message_wait);
                } else {

                    popFragment();
                    UIHelper.hideSoftKeyboard(getApplicationContext(),
                            titleBar);
                }
            }
        });

        titleBar.setNotificationButtonListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceDockableFragment(NotificationsFragment.newInstance(), "NotificationsFragment");
            }
        });

       // if (savedInstanceState == null)
            initFragment();



    }



    private void setCurrentLocale() {
        if (prefHelper.isLanguagePersian()) {
            Resources resources = getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            Configuration conf = resources.getConfiguration();
            conf.locale = new Locale("fa");
            resources.updateConfiguration(conf, dm);

        } else {
            Resources resources = getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            Configuration conf = resources.getConfiguration();
            conf.locale = new Locale("en");
            resources.updateConfiguration(conf, dm);

        }
    }


    @Override
    public void onBackPressed() {
        if (loading) {
            UIHelper.showLongToastInCenter(getApplicationContext(),
                    R.string.message_wait);
        } else
            super.onBackPressed();

    }

    @Override
    public void onMenuItemActionCalled(int actionId, String data) {

    }

    @Override
    public void setSubHeading(String subHeadText) {

    }

    @Override
    public boolean isLoggedIn() {
        return false;
    }

    @Override
    public void hideHeaderButtons(boolean leftBtn, boolean rightBtn) {
    }

    private void notImplemented() {
        UIHelper.showLongToastInCenter(this, "Coming Soon");
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        setCurrentLocale();
        if(requestCode== AppConstants.LocationUpdateListner){
            locationUpdateListner.updateLocationFragment();
        }

        if (resultCode == RESULT_OK
                && (requestCode == ChooserType.REQUEST_PICK_PICTURE || requestCode == ChooserType.REQUEST_CAPTURE_PICTURE)) {
            if (imageChooserManager == null) {
                reinitializeImageChooser();
            }
            imageChooserManager.submit(requestCode, data);
        } else {
            // progressBar.setVisibility(View.GONE);
        }

    }

    private void reinitializeImageChooser() {
        imageChooserManager = new ImageChooserManager(this, chooserType, true);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Intent.EXTRA_ALLOW_MULTIPLE, true);
        imageChooserManager.setExtras(bundle);
        imageChooserManager.setImageChooserListener(this);
        imageChooserManager.reinitialize(filePath);
    }

    public void chooseImage() {
        chooserType = ChooserType.REQUEST_PICK_PICTURE;
        imageChooserManager = new ImageChooserManager(this,
                ChooserType.REQUEST_PICK_PICTURE, true);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Intent.EXTRA_ALLOW_MULTIPLE, true);
        imageChooserManager.setExtras(bundle);
        imageChooserManager.setImageChooserListener(this);
        imageChooserManager.clearOldFiles();
        try {
            //pbar.setVisibility(View.VISIBLE);
            filePath = imageChooserManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void takePicture() {
        chooserType = ChooserType.REQUEST_CAPTURE_PICTURE;
        imageChooserManager = new ImageChooserManager(this,
                ChooserType.REQUEST_CAPTURE_PICTURE, true);
        imageChooserManager.setImageChooserListener(this);
        try {
            //pbar.setVisibility(View.VISIBLE);
            filePath = imageChooserManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onImageChosen(final ChosenImage image) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Log.i(TAG, "Chosen Image: O - " + image.getFilePathOriginal());
                Log.i(TAG, "Chosen Image: T - " + image.getFileThumbnail());
                Log.i(TAG, "Chosen Image: Ts - " + image.getFileThumbnailSmall());
                isActivityResultOver = true;
                originalFilePath = image.getFilePathOriginal();
                thumbnailFilePath = image.getFileThumbnail();
                thumbnailSmallFilePath = image.getFileThumbnailSmall();
                //pbar.setVisibility(View.GONE);
                if (image != null) {
                    Log.i(TAG, "Chosen Image: Is not null");

                    // Toast.makeText(getApplication(),thumbnailFilePath,Toast.LENGTH_LONG).show();
                    imageSetter.setImage(originalFilePath);

                    //loadImage(imageViewThumbnail, image.getFileThumbnail());
                } else {
                    Log.i(TAG, "Chosen Image: Is null");
                }

            }
        });

    }

    @Override
    public void onError(final String reason) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Log.i(TAG, "OnError: " + reason);
                // pbar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, reason,
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onImagesChosen(final ChosenImages images) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "On Images Chosen: " + images.size());
                onImageChosen(images.getImage(0));
            }
        });

    }


    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {

        switch (Tag) {
            case WebServiceConstants.getDefaultSetting:
                prefHelper.putAppDefaultSetting((AppDefaultSettingEnt) result);
                break;


        }
    }

    @Override
    public void ResponseFailure(String tag) {

    }

    public void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}
