package com.ingic.tanfit.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.FitnessClassTime;
import com.ingic.tanfit.entities.FitnessClassess;
import com.ingic.tanfit.entities.GymTimingDialogeEnt;
import com.ingic.tanfit.entities.Studio;
import com.ingic.tanfit.entities.TimingEnt;
import com.ingic.tanfit.entities.TimingTypeEnt;
import com.ingic.tanfit.entities.Timings;
import com.ingic.tanfit.helpers.DateHelper;
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
 * Created on gym_image_11/28/2017.
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

    private static String StudioEnt = "studioEnt";
    private String jsonString;
    private Studio enitity;


    public static GymDetailTimingFragment newInstance() {
        Bundle args = new Bundle();

        GymDetailTimingFragment fragment = new GymDetailTimingFragment();
        fragment.setArguments(args);
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        return fragment;
    }

    public static GymDetailTimingFragment newInstance(Studio entity) {
        Bundle args = new Bundle();
        args.putString(StudioEnt, new Gson().toJson(entity));
        GymDetailTimingFragment fragment = new GymDetailTimingFragment();
        fragment.setArguments(args);
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            jsonString = getArguments().getString(StudioEnt);
        }
        if (jsonString != null) {
            enitity = new Gson().fromJson(jsonString, Studio.class);
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
        setStudioTiming(enitity.getFitnessClasses().get(0));


    }


    private void setBookingHistoryData(ArrayList<GymTimingDialogeEnt> timing) {
        elvBookingHistory.setExpanded(true);
        collectionGroup = new ArrayList<>();

        listDataChild = new HashMap<>();


        for (int i = 0; i < timing.size(); i++) {
            collectionChild = new ArrayList<>();
            String dayname = timing.get(i).getDayName();
            ArrayList<Timings> maleTiming = timing.get(i).getMaleTiming();
            ArrayList<Timings> femaleTiming = timing.get(i).getFemaleTiming();
//            collectionGroup.add(dayname);
//
//
//            collectionChild.addAll(new TimingEnt(item.getTimeIn()+" to "+item.getTimeOut(), ));
//
            if (maleTiming.size() >= femaleTiming.size()) {
                for (int j = 0; j < maleTiming.size(); j++) {
                    // String maleTimein = maleTiming.get(j).getTimeIn();
                    String maleTimein = DateHelper.getFormatedDate("HH:mm:ss", "hh:mm aa", maleTiming.get(j).getTimeIn());
                    // String maleTimeout = maleTiming.get(j).getTimeOut();
                    String maleTimeout = DateHelper.getFormatedDate("HH:mm:ss", "hh:mm aa", maleTiming.get(j).getTimeOut());
                    String femaleTimeIn = "";
                    String femaleTimeOut = "";

                    if (femaleTiming.size() > j) {
                        // femaleTimeIn = femaleTiming.get(j).getTimeIn();
                        femaleTimeIn = DateHelper.getFormatedDate("HH:mm:ss", "hh:mm aa", femaleTiming.get(j).getTimeIn());
                        //femaleTimeOut = femaleTiming.get(j).getTimeOut();
                        femaleTimeOut = DateHelper.getFormatedDate("HH:mm:ss", "hh:mm aa", femaleTiming.get(j).getTimeOut());
                    }
                    collectionChild.add(new TimingEnt(maleTimein + " - " + maleTimeout, femaleTimeIn + " - " + femaleTimeOut));
                }
            } else {
                for (int j = 0; j < femaleTiming.size(); j++) {
                    String maleTimein = "";
                    String maleTimeout = "";
                    //  String femaleTimeIn = femaleTiming.get(j).getTimeIn();
                    String femaleTimeIn = DateHelper.getFormatedDate("HH:mm:ss", "hh aa", femaleTiming.get(j).getTimeIn());
                    //   String femaleTimeOut = femaleTiming.get(j).getTimeOut();
                    String femaleTimeOut = DateHelper.getFormatedDate("HH:mm:ss", "hh aa", femaleTiming.get(j).getTimeOut());

                    if (maleTiming.size() > j) {
                        // maleTimein = maleTiming.get(j).getTimeIn();
                        maleTimein = DateHelper.getFormatedDate("HH:mm:ss", "hh aa", maleTiming.get(j).getTimeIn());
                        //   maleTimeout = maleTiming.get(j).getTimeOut();
                        maleTimeout = DateHelper.getFormatedDate("HH:mm:ss", "hh aa", maleTiming.get(j).getTimeOut());
                    }
                    collectionChild.add(new TimingEnt(maleTimein + " - " + maleTimeout, femaleTimeIn + " - " + femaleTimeOut));
                }
            }

            collectionGroup.add(dayname);
            listDataChild.put(collectionGroup.get(i), collectionChild);
        }

    /*    collectionGroup.add("Monday");
        collectionGroup.add("Tuesday");
        collectionGroup.add("Wednesday");
        collectionGroup.add("Thursday");
        collectionGroup.add("Friday");
        collectionGroup.add("Saturday");

        collectionChild.add(new TimingEnt("8 am to 12 pm ", "12 am to 4 pm"));
        collectionChild.add(new TimingEnt("4 pm to 8 pm ", "8 pm to 9 pm"));
        collectionChild.add(new TimingEnt("4 am to 12 pm ", "12 am to 4 pm"));

        listDataChild.put(collectionGroup.get(0), collectionChild);
        listDataChild.put(collectionGroup.get(1), collectionChild);
        listDataChild.put(collectionGroup.get(2), collectionChild);
        listDataChild.put(collectionGroup.get(3), collectionChild);
        listDataChild.put(collectionGroup.get(4), collectionChild);
        listDataChild.put(collectionGroup.get(5), collectionChild);

*/
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

        for (int i = 0; i < enitity.getFitnessClasses().size(); i++) {
            if (i == 0)
                userCollectionsTime.add(new TimingTypeEnt(R.drawable.gym_grey, R.drawable.gym2, true, enitity.getFitnessClasses().get(i).getClassNameEng(),enitity.getFitnessClasses().get(i).getFitnessClassActivityIcon(),enitity.getFitnessClasses().get(i).getId()));
            else
                userCollectionsTime.add(new TimingTypeEnt(R.drawable.gym_grey, R.drawable.gym2, false, enitity.getFitnessClasses().get(i).getClassNameEng(),enitity.getFitnessClasses().get(i).getFitnessClassActivityIcon(),enitity.getFitnessClasses().get(i).getId()));
        }

       /* userCollectionsTime.add(new TimingTypeEnt(R.drawable.gym_grey, R.drawable.gym2, true, "Gym"));
        userCollectionsTime.add(new TimingTypeEnt(R.drawable.yoga_grey, R.drawable.yoga2, false, "Yoga"));
        userCollectionsTime.add(new TimingTypeEnt(R.drawable.boot_camp, R.drawable.boot_camp2, false, "Boot Camp"));*/
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
        if(currentPostion==previousPosition)
            return;
        rvGallery.notifyItemChanged(currentPostion);
        rvGallery.notifyItemChanged(previousPosition);
    }

    @Override
    public void onclickItem(Object entTiming, int position) {

        setStudioTiming(enitity.getFitnessClasses().get(position));

    }

    private void setStudioTiming(FitnessClassess fitnessClassess) {

        ArrayList<GymTimingDialogeEnt> timing = new ArrayList<>();


        for (int i = 0; i < fitnessClassess.getFitnessClassSelectedDays().size(); i++) {
            int genderId = fitnessClassess.getFitnessClassSelectedDays().get(i).getGenderId();
            int dayId = fitnessClassess.getFitnessClassSelectedDays().get(i).getDayId();
            String dayName = fitnessClassess.getFitnessClassSelectedDays().get(i).getDayName();
            ArrayList<FitnessClassTime> data = fitnessClassess.getFitnessClassSelectedDays().get(i).getFitnessClassTimes();

            GymTimingDialogeEnt gymTimingObject = new GymTimingDialogeEnt();
            gymTimingObject.setDayName(dayName);

            if (genderId == 1) {
                ArrayList<Timings> maleTimings = new ArrayList<>();
                for (FitnessClassTime item : data) {
                    maleTimings.add(new Timings(item.getTimeIn(), item.getTimeOut()));
                }
                gymTimingObject.setMaleTiming(maleTimings);
            } else {
                ArrayList<Timings> femaleTimings = new ArrayList<>();
                for (FitnessClassTime item : data) {
                    femaleTimings.add(new Timings(item.getTimeIn(), item.getTimeOut()));
                }
                gymTimingObject.setFemaleTiming(femaleTimings);
            }
            //loop to the size of timing array
            boolean islastObjNeedToReplace = false;
            for (int j = 0; j < timing.size(); j++) {
                GymTimingDialogeEnt oldObj = timing.get(j);
                if (oldObj.getDayName().equals(dayName)) {
                    islastObjNeedToReplace = true;
                    if (genderId == 1) {// last obj was with female here
                        oldObj.setMaleTiming(gymTimingObject.getMaleTiming());
                    } else {
                        oldObj.setFemaleTiming(gymTimingObject.getFemaleTiming());
                    }
                    gymTimingObject = oldObj;
                }
            }
            if (islastObjNeedToReplace) {
                timing.set(i - 1, gymTimingObject);
                fitnessClassess.getFitnessClassSelectedDays().remove(i);
                --i;
                //    timing.remove(i - 1);//todo in case last object will always with same dayId
            } else
                timing.add(gymTimingObject);
            //    String maletiming=enitity.getFitnessClasses().get(0).getFitnessClassSelectedDays().get(i).getFitnessClassTimes().get
        }

        setBookingHistoryData(timing);
    }
}