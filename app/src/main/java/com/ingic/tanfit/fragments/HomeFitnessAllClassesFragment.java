package com.ingic.tanfit.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;
import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.BookClassEnt;
import com.ingic.tanfit.entities.BookNowClickListner;
import com.ingic.tanfit.entities.FitnessClassSelectedDay;
import com.ingic.tanfit.entities.FitnessClassTime;
import com.ingic.tanfit.entities.FitnessClassess;
import com.ingic.tanfit.entities.IsFavoriteEnt;
import com.ingic.tanfit.entities.SlotsEnt;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.global.WebServiceConstants;
import com.ingic.tanfit.helpers.BasePreferenceHelper;
import com.ingic.tanfit.helpers.DateHelper;
import com.ingic.tanfit.helpers.DialogHelper;
import com.ingic.tanfit.helpers.UIHelper;
import com.ingic.tanfit.interfaces.LoadMoreListener;
import com.ingic.tanfit.interfaces.RecyclerViewItemListener;
import com.ingic.tanfit.ui.adapters.ArrayListAdapter;
import com.ingic.tanfit.ui.binders.HomeFitnessBinder;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.CustomRecyclerView;
import com.ingic.tanfit.ui.views.TitleBar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by saeedhyder on 7/17/2018.
 */


public class HomeFitnessAllClassesFragment extends BaseFragment implements DatePickerListener, RecyclerViewItemListener, BookNowClickListner {
    @BindView(R.id.datePicker)
    HorizontalPicker datePicker;
    @BindView(R.id.txt_noresult)
    AnyTextView txtNoresult;
    @BindView(R.id.lv_fitnessClasses)
    CustomRecyclerView lvFitnessClasses;
    Unbinder unbinder;
    @BindView(R.id.ll_calendar)
    LinearLayout llCalendar;
    private static String TAG;

    private ArrayListAdapter<FitnessClassess> adapter;
    private ArrayList<FitnessClassess> userCollection;
    private ArrayList<FitnessClassess> filterCollection;
    private String Day;
    private String Date;
    private String DayId;
    private int DayIdKey;
    private ArrayList<SlotsEnt> slotsArray;
    private SlotsEnt selectedSlot;
    private boolean radioChecked = false;
    long EventIdGlobal = 0;
    private java.util.Date startDate;
    private FitnessClassess entityFitnessClassClicked;

    boolean canCallForMore = true;
    boolean isOnCall;
    int currentPageNumber = 1;
    int totalCount = 10;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<FitnessClassess> entity;
    private ArrayList<FitnessClassess> allData = new ArrayList<>();
    private DateTime selectedDate;
    private DateTime previousDate = new DateTime();
    boolean firstTime = true;
    private SimpleDateFormat formatter;


    public static HomeFitnessAllClassesFragment newInstance() {
        Bundle args = new Bundle();

        HomeFitnessAllClassesFragment fragment = new HomeFitnessAllClassesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*   adapter = new ArrayListAdapter<FitnessClassess>(getDockActivity(), new HomeFitnessBinder(getDockActivity(), prefHelper, this));*/
        if (getArguments() != null) {
        }

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fitness_classes_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        currentPageNumber = 1;
        firstTime = true;
        canCallForMore = true;
        String date = formatter.format(new Date());
        selectedDate = new DateTime();
        // serviceHelper.enqueueCallHome(headerWebService.getAllClasses(date, prefHelper.getUserAllData().getGenderId() + "", currentPageNumber, totalCount), WebServiceConstants.getAllClassess);

        if (prefHelper.isLanguagePersian()) {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            datePicker.setPreference("fa");

        } else {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            datePicker.setPreference("en");
        }

        RecyclerView.ItemAnimator animator = lvFitnessClasses.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        if (!prefHelper.isStudio())
            if (((HomeFragment) getParentFragment()) != null)
                ((HomeFragment) getParentFragment()).hideSearchBar();

        datePicker
                .setListener(this)
                .setDays(7)
                .setOffset(0)
                .init();
        datePicker.setDate(new DateTime());

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (((HomeFragment) getParentFragment()) != null)
                ((HomeFragment) getParentFragment()).hideSearchBar();

            if (prefHelper != null) {
                prefHelper.setIsFromStudio(false);
            }

            if (formatter != null && prefHelper != null && entity==null) {
                formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                serviceHelper.enqueueCall(headerWebService.getAllClasses(formatter.format(new Date()), prefHelper.getUserAllData().getGenderId() + "", currentPageNumber, totalCount), WebServiceConstants.getAllClassess);
            }

        }

    }

   /* @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
            // do something nice here
        }
    }*/


    private void getPagedData() {


        if (canCallForMore) {
            if (!isOnCall) {
                currentPageNumber = currentPageNumber + 1;
                //  progressBar.setVisibility(View.VISIBLE);
                isOnCall = true;
                serviceHelper.enqueueCall(headerWebService.getAllClasses(DateHelper.dateFormat(selectedDate.toString(), "yyyy-MM-dd", "yyyy-MM-dd"), prefHelper.getUserAllData().getGenderId() + "", currentPageNumber, totalCount), WebServiceConstants.getAllClassessPaging);
            }
        }

    }

    private void bindPagedSearchList(ArrayList<FitnessClassess> result) {
      /*  if (result.size() <= 0) {
            canCallForMore = false;
        }*/
        if (result.size() > 0) {
            entity = new ArrayList<>();
            entity.addAll(result);
            previousDate = selectedDate;
            onDateSelected(selectedDate);
        } else {
            canCallForMore = false;
        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);

        switch (Tag) {

            case WebServiceConstants.getAllClassess:

                ArrayList<FitnessClassess> allClasses = (ArrayList<FitnessClassess>) result;
                entity = allClasses;
                onDateSelected(selectedDate != null ? selectedDate : new DateTime());
                // setFitnessData(entity);

                break;

            case WebServiceConstants.getAllClassessPaging:

                isOnCall = false;
                bindPagedSearchList((ArrayList<FitnessClassess>) result);

                break;


            case WebServiceConstants.isCLassBooked:

                if (((IsFavoriteEnt) result).isBooked()) {
                    UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.class_booked));
                } else {
                    requestCalendarPermission();
                }


                break;

            case WebServiceConstants.bookUserClass:


                BookClassEnt bookClassEntity = (BookClassEnt) result;

                conformDialoge();

                Calendar cal = Calendar.getInstance();


                if (Date != null) {
                    DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                    try {
                        startDate = dateFormater.parse(Date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    cal.setTime(startDate);
                }


                long startTime = cal.getTimeInMillis();
                long endTime = cal.getTimeInMillis() + 60 * 60 * 1000;
                // onAddEventClicked(v);

                //  pushAppointmentsToCalender(getDockActivity(), "TItle", "AddInfo", "Dubai", 1, startTime, true, true, false);
                if (isWriteCalendarPermissionGranted() && isReadCalendarPermissionGranted()) {
                    pushAppointmentsToCalender(getDockActivity(), bookClassEntity.getFitnessClass().getClassNameEng(), bookClassEntity.getFitnessClass().getClassDescriptionEng(), bookClassEntity.getFitnessClass().getStudioAddressEng(), 1, startTime, true, true, false);
                }
                break;
        }
    }

    public boolean isWriteCalendarPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getDockActivity().checkSelfPermission(Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(getDockActivity(), new String[]{Manifest.permission.WRITE_CALENDAR}, 1);
                return false;

            } else {
                Log.v(TAG, "Permission is granted");
                return true;

            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }
    }

    public boolean isReadCalendarPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getDockActivity().checkSelfPermission(Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(getDockActivity(), new String[]{Manifest.permission.READ_CALENDAR}, 1);
                return false;

            } else {
                Log.v(TAG, "Permission is granted");
                return true;

            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
        }
    }


    @Override
    public void onResume() {
        super.onResume();

    }


    private void setFitnessData(ArrayList<FitnessClassess> entity) {

        if (entity.size() <= 0) {
            txtNoresult.setText(getDockActivity().getResources().getString(R.string.no_class_found));
            txtNoresult.setVisibility(View.VISIBLE);
            lvFitnessClasses.setVisibility(View.GONE);
        } else {
            txtNoresult.setVisibility(View.GONE);
            lvFitnessClasses.setVisibility(View.VISIBLE);
        }

        if (firstTime) {
            linearLayoutManager = new LinearLayoutManager(getDockActivity());
            lvFitnessClasses.clearList();
            lvFitnessClasses.BindRecyclerView(new HomeFitnessBinder(getDockActivity(), prefHelper, this, DayIdKey, this), entity,
                    linearLayoutManager
                    , new DefaultItemAnimator());
            firstTime = false;
        } else {
            lvFitnessClasses.addAll(entity);
            lvFitnessClasses.notifyItemRangeChanged(linearLayoutManager.findFirstVisibleItemPosition(), entity.size());
        }


        if (lvFitnessClasses.getAdapter() != null) {
            lvFitnessClasses.getAdapter().setOnLoadMoreListener(new LoadMoreListener() {
                @Override
                public void onLoadMoreItem(int position) {
                    getPagedData();
                }
            });
        }

    }


    @Override
    public void onDateSelected(DateTime dateSelected) {

        selectedDate = dateSelected;

        try {
            if (!formatter.parse(previousDate.toString()).equals(formatter.parse(selectedDate.toString()))) {
                firstTime = true;
                canCallForMore = true;
                currentPageNumber = 1;
                previousDate = selectedDate;

                serviceHelper.enqueueCall(headerWebService.getAllClasses(DateHelper.dateFormat(selectedDate.toString(), "yyyy-MM-dd", "yyyy-MM-dd"), prefHelper.getUserAllData().getGenderId() + "", currentPageNumber, totalCount), WebServiceConstants.getAllClassess);
            } else {
                previousDate = selectedDate;

                if (entity != null) {

                    Date todayDate;
                    try {
                        DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'+'HH:mm", Locale.ENGLISH);
                        todayDate = dateFormater.parse(String.valueOf(dateSelected));

                        SimpleDateFormat outFormat = new SimpleDateFormat("EEEE", Locale.ENGLISH);
                        SimpleDateFormat selectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                        Day = outFormat.format(todayDate);
                        Date = selectedDate.format(todayDate);

                        if (Day.equalsIgnoreCase("sunday")) {
                            DayIdKey = 1;
                        } else if (Day.equalsIgnoreCase("monday")) {
                            DayIdKey = 2;
                        } else if (Day.equalsIgnoreCase("tuesday")) {
                            DayIdKey = 3;
                        } else if (Day.equalsIgnoreCase("wednesday")) {
                            DayIdKey = 4;
                        } else if (Day.equalsIgnoreCase("thursday")) {
                            DayIdKey = 5;
                        } else if (Day.equalsIgnoreCase("friday")) {
                            DayIdKey = 6;
                        } else if (Day.equalsIgnoreCase("saturday")) {
                            DayIdKey = 7;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    setFitnessData(entity);


                  /*  filterCollection = new ArrayList<>();
                    userCollection = new ArrayList<>();
                        for (FitnessClassess item : entity) {
                            //dateFormater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+'HH:mm", Locale.ENGLISH);
                            dateFormater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
                            Date startDate = dateFormater.parse(item.getFromDate());
                            Date endDate = dateFormater.parse(item.getToDate());


                            if (todayDate.after(startDate) && todayDate.before(endDate)) {
                                filterCollection.add(item);
                            }
                        }




                  /*  for (FitnessClassess item : filterCollection) {
                        for (int i = 0; i < item.getFitnessClassSelectedDays().size(); i++) {
                            if (item.getFitnessClassSelectedDays().get(i).getDayNameEn().equals(Day)) {
                                userCollection.add(item);
                            }
                        }
                    }*/

                }

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRecyclerItemClicked(Object Ent, int position) {

        FitnessClassess fitnessClassData = (FitnessClassess) Ent;

        for (FitnessClassSelectedDay item : fitnessClassData.getFitnessClassSelectedDays()) {
            if (item.getDayNameEn().equals(Day)) {
                if (item.getGenderId() == prefHelper.getUserAllData().getGenderId()) {
                    DayId = item.getId() + "";
                }
            }
        }

        getDockActivity().addDockableFragment(ClassDetailFragment.newInstance(fitnessClassData, Day, Date, DayId), "ClassDetailFragment");
    }

    @Override
    public void onBookNowClick(FitnessClassess entity, int position) {

        entityFitnessClassClicked = entity;

        //   if (DayId == null || DayId.isEmpty()) {
        for (FitnessClassSelectedDay item : entity.getFitnessClassSelectedDays()) {
            if (item.getDayNameEn().equals(Day)) {
                if (item.getGenderId() == prefHelper.getUserAllData().getGenderId()) {
                    DayId = item.getId() + "";
                }
            }
            //   }
        }

        serviceHelper.enqueueCall(headerWebService.isBooked(prefHelper.getUserAllData().getId(), entity.getId(), DayId), WebServiceConstants.isCLassBooked);

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


    private void classBooking() {

        slotsArray = new ArrayList<>();

        try {

            for (FitnessClassSelectedDay item : entityFitnessClassClicked.getFitnessClassSelectedDays()) {

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

        if (eventUri != null) {

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
        }
        return EventIdGlobal;

    }

    private void requestCalendarPermission() {
        Dexter.withActivity(getDockActivity())
                .withPermissions(
                        Manifest.permission.READ_CALENDAR,
                        Manifest.permission.WRITE_CALENDAR,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {

                        if (report.areAllPermissionsGranted()) {
                            classBooking();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            requestCalendarPermission();

                        } else if (report.getDeniedPermissionResponses().size() > 0) {
                            requestCalendarPermission();
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
                        UIHelper.showShortToastInCenter(getDockActivity(), "Grant Calendar Permission to processed");
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
}





