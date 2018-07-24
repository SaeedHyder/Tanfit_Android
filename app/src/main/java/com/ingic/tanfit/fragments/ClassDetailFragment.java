package com.ingic.tanfit.fragments;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.BookClassEnt;
import com.ingic.tanfit.entities.CalanderReminderEnt;
import com.ingic.tanfit.entities.FitnessClassSelectedDay;
import com.ingic.tanfit.entities.FitnessClassTime;
import com.ingic.tanfit.entities.FitnessClassess;
import com.ingic.tanfit.entities.IsFavoriteEnt;
import com.ingic.tanfit.entities.SlotsEnt;
import com.ingic.tanfit.entities.StudioFeature;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.global.WebServiceConstants;
import com.ingic.tanfit.helpers.DateHelper;
import com.ingic.tanfit.helpers.DialogHelper;
import com.ingic.tanfit.helpers.UIHelper;
import com.ingic.tanfit.ui.adapters.ArrayListAdapter;
import com.ingic.tanfit.ui.binders.speacialFeatureClassItemBinder;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.ExpandableGridView;
import com.ingic.tanfit.ui.views.TitleBar;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * Created by saeedhyder on gym_image_11/21/2017.
 */
public class ClassDetailFragment extends BaseFragment implements OnMapReadyCallback {

    @BindView(R.id.iv_profileImage)
    ImageView ivProfileImage;
    @BindView(R.id.txt_title)
    AnyTextView txtTitle;
    @BindView(R.id.txt_address)
    AnyTextView txtAddress;
    @BindView(R.id.txt_time)
    AnyTextView txtTime;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.txt_duration)
    AnyTextView txtDuration;
    @BindView(R.id.txt_date)
    AnyTextView txtDate;
    @BindView(R.id.txt_description)
    AnyTextView txt_description;
    @BindView(R.id.btn_book_now)
    Button btnBookNow;
    @BindView(R.id.btn_cancel_booking)
    Button btnCancelBooking;
    @BindView(R.id.gv_special_features)
    ExpandableGridView gvSpecialFeatures;
    @BindView(R.id.btn_viewStudioPage)
    Button btnViewStudioPage;
    @BindView(R.id.gv_amelities)
    ExpandableGridView gvAmelities;
    @BindView(R.id.gridViewLine)
    View gridViewLine;
    @BindView(R.id.txt_cancel_hours)
    AnyTextView txt_cancel_hours;
    @BindView(R.id.rl_bookBtn)
    RelativeLayout RlBookBtn;
    @BindView(R.id.mainFrameLayout)
    LinearLayout mainFrameLayout;


    //    @BindView(R.id.map)
//    ImageView map;
    ImageLoader imageLoader;
    @BindView(R.id.ll_description)
    LinearLayout llDescription;
    private GoogleMap mMap;
    private View viewParent;
    private SupportMapFragment mapFragment;
    private ArrayListAdapter<StudioFeature> adapter;
    private ArrayList<StudioFeature> userCollectionSpecialities = new ArrayList<>();

    private ArrayListAdapter<StudioFeature> adapterAmenities;
    private ArrayList<StudioFeature> userCollectionAmenities = new ArrayList<>();

    private SlotsEnt selectedSlot;
    private ArrayList<SlotsEnt> slotsArray = new ArrayList<>();

    long EventIdGlobal = 0;
    private static String FitnessEnt = "FitnessEnt";
    private static String IsFavoriteClassKey = "IsFavoriteClass";
    private boolean isfavorite = false;
    private static String Day = "Day";
    private static String DayId = "DayId";
    private static String DateKey = "Date";
    private String jsonString;
    private FitnessClassess entiity;
    private CalanderReminderEnt calanderReminderEnt;
    private Date startDate;
    private boolean radioChecked = false;

    public static ClassDetailFragment newInstance() {
        Bundle args = new Bundle();

        ClassDetailFragment fragment = new ClassDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ClassDetailFragment newInstance(FitnessClassess fitnessClassess, boolean IsFavoriteClass) {
        Bundle args = new Bundle();
        args.putString(FitnessEnt, new Gson().toJson(fitnessClassess));
        args.putBoolean(IsFavoriteClassKey, IsFavoriteClass);
        ClassDetailFragment fragment = new ClassDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ClassDetailFragment newInstance(FitnessClassess fitnessClassess, String DayKey, String Date, String DayID) {
        Bundle args = new Bundle();
        args.putString(FitnessEnt, new Gson().toJson(fitnessClassess));
        args.putString(Day, DayKey);
        args.putString(DateKey, Date);
        args.putString(DayId, DayID);
        ClassDetailFragment fragment = new ClassDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<StudioFeature>(getDockActivity(), new speacialFeatureClassItemBinder(getDockActivity(), prefHelper));
        adapterAmenities = new ArrayListAdapter<StudioFeature>(getDockActivity(), new speacialFeatureClassItemBinder(getDockActivity(), prefHelper));
        imageLoader = ImageLoader.getInstance();
        if (getArguments() != null) {
            jsonString = getArguments().getString(FitnessEnt);
            Day = getArguments().getString(Day);
            DateKey = getArguments().getString(DateKey);
            DayId = getArguments().getString(DayId);
            isfavorite = getArguments().getBoolean(IsFavoriteClassKey);
        }
        if (jsonString != null) {
            entiity = new Gson().fromJson(jsonString, FitnessClassess.class);
        }

    }

    @Override
    public void setTitleBar(final TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading("");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
     /*   View view = inflater.inflate(R.layout.fragment_class_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;*/
        if (viewParent != null) {
            ViewGroup parent = (ViewGroup) viewParent.getParent();
            if (parent != null)
                parent.removeView(viewParent);
        }
        try {
            viewParent = inflater.inflate(R.layout.fragment_class_detail, container, false);

        } catch (InflateException e) {
            e.printStackTrace();
        }
        ButterKnife.bind(this, viewParent);
        return viewParent;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (prefHelper.isLanguagePersian()) {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        } else {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }

        if (getTitleBar() != null) {
            getTitleBar().hideHeartButton();
        }


        if (isfavorite) {
            RlBookBtn.setVisibility(View.GONE);
            serviceHelper.enqueueCall(headerWebService.isFavoriteClass(prefHelper.getUserAllData().getId(), entiity.getId()), WebServiceConstants.isFavoriteClass);
        } else {
            RlBookBtn.setVisibility(View.GONE);
/*          serviceHelper.enqueueCall(headerWebService.isFavoriteClass(prefHelper.getUserAllData().getId(), entiity.getId()), WebServiceConstants.isFavoriteClass);
            serviceHelper.enqueueCall(headerWebService.isClassBooked(prefHelper.getUserAllData().getId(), entiity.getId(), DayId), WebServiceConstants.isCLassBooked);*/
            serviceHelper.enqueueCall(headerWebService.isBooked(prefHelper.getUserAllData().getId(), entiity.getId(), DayId), WebServiceConstants.isCLassBooked);
        }


        prefHelper.setIsFromStudio(false);

        if ((entiity.getFitnessClassFeatures().size() % 2) == 0) {
            gridViewLine.setVisibility(View.GONE);
        } else {
            gridViewLine.setVisibility(View.VISIBLE);
        }

        if (mapFragment == null) {
            initMap();
        }

        getSelectedSlots();
        setFitnessClassData(entiity);
        setSpecialFeatures(entiity.getFitnessClassFeatures());
        setAmenitiesData();

    }

    private void getSelectedSlots() {


        try {

            for (FitnessClassSelectedDay item : entiity.getFitnessClassSelectedDays()) {

                if (prefHelper.getUserAllData().getGenderId().equals(item.getGenderId())) {

                    for (FitnessClassTime item2 : item.getFitnessClassTimes()) {

                        if (Day != null) {
                            if (Day.equals(item.getDayNameEn())) {
                                slotsArray.add(new SlotsEnt(item2.getFitnessClassSelectedDayId(), item2.getId(), item.getFitnessClassId(), item2.getTimeIn(), item2.getTimeOut()));
                            }
                        } else {
                            slotsArray.add(new SlotsEnt(item2.getFitnessClassSelectedDayId(), item2.getId(), item.getFitnessClassId(), item2.getTimeIn(), item2.getTimeOut()));
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (slotsArray.size() > 0) {
            txtTime.setText(DateHelper.getFormatedDate("HH:mm:ss", "HH:mm", slotsArray.get(0).getTimeIn()) + " - " + DateHelper.getFormatedDate("HH:mm:ss", "HH:mm", slotsArray.get(slotsArray.size() - 1).getTimeOut()));
        } else {
            txtTime.setText(R.string.no_class);
        }
    }

    private void setFitnessClassData(FitnessClassess enitity) {


        if (prefHelper.getUserAllData().getGenderId() == 1) {
          /*  Picasso.with(getDockActivity())
                    .load(enitity.getActivity().getMaleIcon())
                    .placeholder(R.drawable.placeholder3)
                    .into(ivProfileImage);*/
            Glide.with(getDockActivity()).asGif()
                    .load(enitity.getActivity().getMaleIcon())
                    .apply(bitmapTransform(new CircleCrop()))
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.placeholder3))
                    .into(ivProfileImage);
        } else {
         /*   Picasso.with(getDockActivity())
                    .load(enitity.getActivity().getFemaleIcon())
                    .placeholder(R.drawable.placeholder3)
                    .into(ivProfileImage);*/
            Glide.with(getDockActivity()).asGif()
                    .load(enitity.getActivity().getFemaleIcon())
                    .apply(bitmapTransform(new CircleCrop()))
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.placeholder3))
                    .into(ivProfileImage);
        }

        if (prefHelper.isLanguagePersian()) {
            txtTitle.setText(enitity.getClassNamePer() + "");

            if (enitity.getStudioAddressPer() != null && !enitity.getStudioAddressPer().equals("")) {
                txtAddress.setText(enitity.getStudioAddressPer() + "");
            } else {
                txtAddress.setText("-");
            }
            // txtTime.setText(DateHelper.getFormatedDate("HH:mm:ss", "HH:mm", enitity.getFitnessClassSelectedDays().get(0).getFitnessClassTimes().get(0).getTimeIn()) + " - " + DateHelper.getFormatedDate("HH:mm:ss", "HH:mm", enitity.getFitnessClassSelectedDays().get(0).getFitnessClassTimes().get(0).getTimeOut()));
            txtDuration.setText(enitity.getClassDurationMinPr() + " Min");
            txtDate.setText(DateHelper.getFormatedDate("yyyy-MM-dd'T'HH:mm:ss", "MMM dd,yyyy", enitity.getFromDate()));
            if (enitity.getClassDescriptionPer() != null && !enitity.getClassDescriptionPer().equals("null")) {
                txt_description.setText(enitity.getClassDescriptionPer() + "");
                llDescription.setVisibility(View.VISIBLE);
            } else {
                llDescription.setVisibility(View.GONE);
            }
            txt_cancel_hours.setText(getDockActivity().getResources().getString(R.string.you_can_cacel_before) + " " + enitity.getClassCancellationDurationHrsPr() + " " + getDockActivity().getResources().getString(R.string.hours_to_avoid_charges));

        } else {
            txtTitle.setText(enitity.getClassNameEng() + "");

            if (enitity.getStudioAddressEng() != null && !enitity.getStudioAddressEng().equals("")) {
                txtAddress.setText(enitity.getStudioAddressEng() + "");
            } else {
                txtAddress.setText("-");
            }

            // txtTime.setText(DateHelper.getFormatedDate("HH:mm:ss", "HH:mm", enitity.getFitnessClassSelectedDays().get(0).getFitnessClassTimes().get(0).getTimeIn()) + " - " + DateHelper.getFormatedDate("HH:mm:ss", "HH:mm", enitity.getFitnessClassSelectedDays().get(0).getFitnessClassTimes().get(0).getTimeOut()));
            txtDuration.setText(enitity.getClassDurationMin() + " Min");
            txtDate.setText(DateHelper.getFormatedDate("yyyy-MM-dd'T'HH:mm:ss", "MMM dd,yyyy", enitity.getFromDate()));
            if (enitity.getClassDescriptionEng() != null && !enitity.getClassDescriptionEng().equals("null")) {
                txt_description.setText(enitity.getClassDescriptionEng() + "");
                llDescription.setVisibility(View.VISIBLE);
            } else {
                llDescription.setVisibility(View.GONE);
            }
            txt_cancel_hours.setText(getDockActivity().getResources().getString(R.string.you_can_cacel_before) + " " + enitity.getClassCancellationDurationHrs() + " " + getDockActivity().getResources().getString(R.string.hours_to_avoid_charges));
        }


    }


    private void initMap() {
        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void setMapLocation(FitnessClassess enitity) {

        Double lat = enitity.getLatitude();
        Double lng = enitity.getLongitude();
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.location2);
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).icon(icon));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 15));

//        String mapURL="https://maps.googleapis.com/maps/api/staticmap?center="+lat+","+lng+"&zoom=14&" +
//                "&scale=2&size=500x300&maptype=roadmap&markers=color:blue|"+lat+","+lng+"&key=AIzaSyCDylplefNyWlLDoBL_n2VFjwlMWvq3sBg";
//        imageLoader.displayImage(mapURL, map);
    }


    private void setSpecialFeatures(ArrayList<StudioFeature> fitnessClassFeatures) {

        userCollectionSpecialities = new ArrayList<>();
        userCollectionSpecialities.addAll(fitnessClassFeatures);

        adapter.clearList();
        adapter.addAll(userCollectionSpecialities);
        gvSpecialFeatures.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setAmenitiesData() {

        userCollectionAmenities = new ArrayList<>();

      /*  userCollectionAmenities.add(new SpecialFeatureEnt(AppConstants.DRAWABLE_PATH + R.drawable.changing_room, getDockActivity().getResources().getString(R.string.changing_room)));
        userCollectionAmenities.add(new SpecialFeatureEnt(AppConstants.DRAWABLE_PATH + R.drawable.treadmil, getDockActivity().getResources().getString(R.string.treadmil)));
        userCollectionAmenities.add(new SpecialFeatureEnt(AppConstants.DRAWABLE_PATH + R.drawable.music, getDockActivity().getResources().getString(R.string.music)));
        userCollectionAmenities.add(new SpecialFeatureEnt(AppConstants.DRAWABLE_PATH + R.drawable.trainer, getDockActivity().getResources().getString(R.string.trainer)));*/


        adapterAmenities.clearList();
        adapterAmenities.addAll(userCollectionAmenities);
        gvAmelities.setAdapter(adapter);
        adapterAmenities.notifyDataSetChanged();

    }


    @OnClick({R.id.btn_book_now, R.id.btn_viewStudioPage, R.id.btn_cancel_booking})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_book_now:

               /* final ArrayList<SlotsEnt> slotsArray = new ArrayList<>();

                try {

                    for (FitnessClassSelectedDay item : entiity.getFitnessClassSelectedDays()) {

                        if (prefHelper.getUserAllData().getGenderId().equals(item.getGenderId())) {

                            for (FitnessClassTime item2 : item.getFitnessClassTimes()) {

                                if (Day != null) {
                                    if (Day.equals(item.getDayName())) {
                                        slotsArray.add(new SlotsEnt(item2.getFitnessClassSelectedDayId(), item2.getId(), item.getFitnessClassId(), item2.getTimeIn(), item2.getTimeOut()));
                                    }
                                }else{
                                    slotsArray.add(new SlotsEnt(item2.getFitnessClassSelectedDayId(), item2.getId(), item.getFitnessClassId(), item2.getTimeIn(), item2.getTimeOut()));
                                }
                            }
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }*/

                if (slotsArray.size() > 0) {

                    final DialogHelper classSlotsDialoge = new DialogHelper(getDockActivity());
                    classSlotsDialoge.initSlotDialoge(R.layout.class_slots_dialoge, new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            View radioButton = group.findViewById(checkedId);
                            int idx = group.indexOfChild(radioButton);
                            selectedSlot = slotsArray.get(idx);
                        }
                    }, new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                radioChecked = true;
                            }
                        }
                    }, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (radioChecked) {
                                bookingNowDialog(selectedSlot);
                                classSlotsDialoge.hideDialog();
                            } else {
                                UIHelper.showShortToastInCenter(getDockActivity(), getDockActivity().getResources().getString(R.string.please_select_any_slot));
                            }


                        }
                    }, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            classSlotsDialoge.hideDialog();
                        }
                    }, getDockActivity(), slotsArray);

                    classSlotsDialoge.showDialog();
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), getDockActivity().getResources().getString(R.string.no_class_is_scheduled_for_today));
                }

                break;
            case R.id.btn_viewStudioPage:
                getDockActivity().replaceDockableFragment(GymDetailFragment.newInstance(entiity.getStudioId()), "GymDetailFragment");
                break;
            case R.id.btn_cancel_booking:
                final DialogHelper cancelBookingDialoge = new DialogHelper(getDockActivity());
                cancelBookingDialoge.initCancel(R.layout.cancel_class_dialoge, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //  getDockActivity().replaceDockableFragment(CancelSurveyFragment.newInstance(entiity.getId()+""),"CancelSurveyFragment");

                        serviceHelper.enqueueCall(headerWebService.cancelClassCheck(prefHelper.getUserAllData().getId(), entiity.getId(), DayId), WebServiceConstants.CheckCancelClass);

                        cancelBookingDialoge.hideDialog();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cancelBookingDialoge.hideDialog();
                    }
                });
                cancelBookingDialoge.showDialog();
                break;


        }
    }

    private void bookingNowDialog(final SlotsEnt selectedSlot) {
        final DialogHelper bookNowDialoge = new DialogHelper(getDockActivity());
        bookNowDialoge.initbooknow(R.layout.book_class_dialog, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                serviceHelper.enqueueCall(headerWebService.bookUserClass(prefHelper.getUserAllData().getId(), selectedSlot.getFitnessClassId(), selectedSlot.getDayId(), selectedSlot.getId()), WebServiceConstants.bookUserClass);
                bookNowDialoge.hideDialog();

            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookNowDialoge.hideDialog();
            }
        });

        bookNowDialoge.showDialog();
    }

    private void conformDialoge() {
        final DialogHelper conformDialoge = new DialogHelper(getDockActivity());
        conformDialoge.initConformBooking(R.layout.conform_booking_dialoge, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                conformDialoge.hideDialog();
            }
        });
        conformDialoge.showDialog();
    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {
            case WebServiceConstants.bookUserClass:


                BookClassEnt bookClassEntity = (BookClassEnt) result;

                conformDialoge();
                btnBookNow.setVisibility(View.GONE);
                btnCancelBooking.setVisibility(View.VISIBLE);
                txt_cancel_hours.setVisibility(View.VISIBLE);

                Calendar cal = Calendar.getInstance();


                if (DateKey != null) {
                    DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        startDate = dateFormater.parse(DateKey);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    cal.setTime(startDate);
                }


                long startTime = cal.getTimeInMillis();
                long endTime = cal.getTimeInMillis() + 60 * 60 * 1000;
                // onAddEventClicked(v);

                //  pushAppointmentsToCalender(getDockActivity(), "TItle", "AddInfo", "Dubai", 1, startTime, true, true, false);
                pushAppointmentsToCalender(getDockActivity(), bookClassEntity.getFitnessClass().getClassNameEng(), bookClassEntity.getFitnessClass().getClassDescriptionEng(), bookClassEntity.getFitnessClass().getStudioAddressEng(), 1, startTime, true, true, false);

                break;

            case WebServiceConstants.cancelUserClass:

                UIHelper.showShortToastInCenter(getDockActivity(), message);
                deleteEvent(EventIdGlobal);
                btnBookNow.setVisibility(View.VISIBLE);
                btnCancelBooking.setVisibility(View.GONE);
                break;

            case WebServiceConstants.CheckCancelClass:

                getDockActivity().replaceDockableFragment(CancelSurveyFragment.newInstance(entiity.getId() + ""), "CancelSurveyFragment");

                break;


            case WebServiceConstants.isCLassBooked:

                RlBookBtn.setVisibility(View.VISIBLE);
                if (getTitleBar() != null) {
                    getTitleBar().showHeartButton(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            serviceHelper.enqueueCall(headerWebService.addFavoriteClass(prefHelper.getUserAllData().getId(), entiity.getId(), isChecked ? false : true), WebServiceConstants.addFavoriteClass);

                        }
                    });

                    if (((IsFavoriteEnt) result).isFavorite()) {

                        getTitleBar().getHearCheckBox(R.id.cb_heart).setChecked(true);

                    } else {
                        getTitleBar().getHearCheckBox(R.id.cb_heart).setChecked(false);
                    }
                }

                if (((IsFavoriteEnt) result).isBooked()) {
                    btnBookNow.setVisibility(View.GONE);
                    btnCancelBooking.setVisibility(View.VISIBLE);
                    txt_cancel_hours.setVisibility(View.VISIBLE);
                } else {
                    btnBookNow.setVisibility(View.VISIBLE);
                    btnCancelBooking.setVisibility(View.GONE);
                    txt_cancel_hours.setVisibility(View.GONE);
                }


                break;

            case WebServiceConstants.isFavoriteClass:

                if (getTitleBar() != null) {
                    getTitleBar().showHeartButton(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            serviceHelper.enqueueCall(headerWebService.addFavoriteClass(prefHelper.getUserAllData().getId(), entiity.getId(), isChecked ? false : true), WebServiceConstants.addFavoriteClass);

                        }
                    });
                    if (((IsFavoriteEnt) result).isFavorite()) {

                        getTitleBar().getHearCheckBox(R.id.cb_heart).setChecked(true);

                    } else {
                        getTitleBar().getHearCheckBox(R.id.cb_heart).setChecked(false);
                    }
                }
                break;


            case WebServiceConstants.addFavoriteClass:
                //  UIHelper.showShortToastInCenter(getDockActivity(),message);
                break;


        }
    }

    public void onAddEventClicked(View view) {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType("vnd.android.cursor.item/event");

        Calendar cal = Calendar.getInstance();
        long startTime = cal.getTimeInMillis();
        long endTime = cal.getTimeInMillis() + 60 * 60 * 1000;

        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime);
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime);
        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);

        intent.putExtra(CalendarContract.Events.TITLE, "Siddharth Birthday");
        intent.putExtra(CalendarContract.Events.DESCRIPTION, "This is a description");
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "My Guest House");
        intent.putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY");

        startActivity(intent);
    }

    public long pushAppointmentsToCalender(Activity curActivity, String title, String addInfo, String place, int status, long startDate, boolean needReminder, boolean needMailService, boolean deleteEvent) {

        String eventUriString = "content://com.android.calendar/events";
        ContentValues eventValues = new ContentValues();

        eventValues.put("calendar_id", 1); // id, We need to choose from
        // our mobile for primary
        // its 1
        eventValues.put("title", title);
        eventValues.put("description", addInfo);
        eventValues.put("eventLocation", place);

        long endDate = startDate + 1000 * 60 * 60; // For next 1hr

        eventValues.put("dtstart", startDate);
        eventValues.put("dtend", endDate);

        // values.put("allDay", 1); //If it is bithday alarm or such
        // kind (which should remind me for whole day) 0 for false, 1
        // for true
        eventValues.put("eventStatus", status); // This information is
        // sufficient for most
        // entries tentative (0),
        // confirmed (1) or canceled
        // (2):
        eventValues.put("eventTimezone", "UTC/GMT +2:00");
   /*Comment below visibility and transparency  column to avoid java.lang.IllegalArgumentException column visibility is invalid error */

    /*eventValues.put("visibility", 3); // visibility to default (0),
                                        // confidential (1), private
                                        // (2), or public (3):
    eventValues.put("transparency", 0); // You can control whether
                                        // an event consumes time
                                        // opaque (0) or transparent
                                        // (1).
      */
        eventValues.put("hasAlarm", 1); // 0 for false, 1 for true

        Uri eventUri = curActivity.getApplicationContext().getContentResolver().insert(Uri.parse(eventUriString), eventValues);
        long eventID = Long.parseLong(eventUri.getLastPathSegment());

        if (needReminder) {
            /***************** Event: Reminder(with alert) Adding reminder to event *******************/

            String reminderUriString = "content://com.android.calendar/reminders";

            ContentValues reminderValues = new ContentValues();

            reminderValues.put("event_id", eventID);
            reminderValues.put("minutes", 5); // Default value of the
            // system. Minutes is a
            // integer
            reminderValues.put("method", 1); // Alert Methods: Default(0),
            // Alert(1), Email(2),
            // SMS(3)

            Uri reminderUri = curActivity.getApplicationContext().getContentResolver().insert(Uri.parse(reminderUriString), reminderValues);
        }

        /***************** Event: Meeting(without alert) Adding Attendies to the meeting *******************/

        if (needMailService) {
            String attendeuesesUriString = "content://com.android.calendar/attendees";

            /********
             * To add multiple attendees need to insert ContentValues multiple
             * times
             ***********/
            ContentValues attendeesValues = new ContentValues();

            attendeesValues.put("event_id", eventID);
            attendeesValues.put("attendeeName", "xxxxx"); // Attendees name
            attendeesValues.put("attendeeEmail", "yyyy@gmail.com");// Attendee
            // E
            // mail
            // id
            attendeesValues.put("attendeeRelationship", 0); // Relationship_Attendee(1),
            // Relationship_None(0),
            // Organizer(2),
            // Performer(3),
            // Speaker(4)
            attendeesValues.put("attendeeType", 0); // None(0), Optional(1),
            // Required(2), Resource(3)
            attendeesValues.put("attendeeStatus", 0); // NOne(0), Accepted(1),
            // Decline(2),
            // Invited(3),
            // Tentative(4)

            Uri attendeuesesUri = curActivity.getApplicationContext().getContentResolver().insert(Uri.parse(attendeuesesUriString), attendeesValues);
        }

        EventIdGlobal = eventID;

        return eventID;

    }

    void deleteEvent(long eventID) {

        if (eventID != 0) {
            Uri deleteUri = null;

            deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, Long.parseLong(String.valueOf(eventID)));
            int rows = getDockActivity().getContentResolver().delete(deleteUri, null, null);
            Toast.makeText(getDockActivity(), "Event deleted", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setMapLocation(entiity);
    }


}
