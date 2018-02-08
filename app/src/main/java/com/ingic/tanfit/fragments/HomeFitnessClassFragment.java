package com.ingic.tanfit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;
import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.FitnessClassess;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.interfaces.RecyclerViewItemListener;
import com.ingic.tanfit.ui.adapters.ArrayListAdapter;
import com.ingic.tanfit.ui.binders.HomeFitnessBinder;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.TitleBar;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created on gym_image_11/25/2017.
 */
public class HomeFitnessClassFragment extends BaseFragment implements DatePickerListener, RecyclerViewItemListener {
    @BindView(R.id.datePicker)
    HorizontalPicker datePicker;
    @BindView(R.id.txt_noresult)
    AnyTextView txtNoresult;
    @BindView(R.id.lv_fitnessClasses)
    ListView lvFitnessClasses;
    Unbinder unbinder;

    private ArrayListAdapter<FitnessClassess> adapter;
    private ArrayList<FitnessClassess> userCollection;
    private ArrayList<FitnessClassess> entity;
    private ArrayList<FitnessClassess> filterCollection;
    private String Day;
    private String Date;


    public static HomeFitnessClassFragment newInstance() {
        Bundle args = new Bundle();

        HomeFitnessClassFragment fragment = new HomeFitnessClassFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ArrayListAdapter<FitnessClassess>(getDockActivity(), new HomeFitnessBinder(getDockActivity(), prefHelper, this));
        if (getArguments() != null) {
        }

    }

    public void setContent(ArrayList<FitnessClassess> data) {

        this.entity = data;
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



        datePicker
                .setListener(this)
                .setDays(7)
                .setOffset(0)
                .init();
        datePicker.setDate(new DateTime());


    }

    private void setFitnessData(ArrayList<FitnessClassess> entity) {

       /* userCollection = new ArrayList<>();

        userCollection.add(new fitnessEnt(AppConstants.DRAWABLE_PATH + R.drawable.group_training, "Group Personal Training", "Bespoke Ride", "Al Quoz", "gym_image_8:00", "60 min"));
        userCollection.add(new fitnessEnt(AppConstants.DRAWABLE_PATH + R.drawable.power_yoga, "Power Yoga", "136.1 Yoga Studio", "Al Quoz", "gym_image_8:00", "60 min"));
        userCollection.add(new fitnessEnt(AppConstants.DRAWABLE_PATH + R.drawable.pelton_biking, "Pelton Biking", "Quantum Health Club", "Trade Center Area", "gym_image_8:00", "60 min"));
        userCollection.add(new fitnessEnt(AppConstants.DRAWABLE_PATH + R.drawable.group_training, "Group Personal Training", "Bespoke Ride", "Al Quoz", "gym_image_8:00", "60 min"));
        userCollection.add(new fitnessEnt(AppConstants.DRAWABLE_PATH + R.drawable.power_yoga, "Power Yoga", "136.1 Yoga Studio", "Al Quoz", "gym_image_8:00", "60 min"));
        userCollection.add(new fitnessEnt(AppConstants.DRAWABLE_PATH + R.drawable.pelton_biking, "Pelton Biking", "Quantum Health Club", "Trade Center Area", "gym_image_8:00", "60 min"));
*/

        if (entity.size() <= 0) {
            txtNoresult.setVisibility(View.VISIBLE);
            lvFitnessClasses.setVisibility(View.GONE);
        } else {
            txtNoresult.setVisibility(View.GONE);
            lvFitnessClasses.setVisibility(View.VISIBLE);
        }

        adapter.clearList();
        lvFitnessClasses.setAdapter(adapter);
        adapter.addAll(entity);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onDateSelected(DateTime dateSelected) {

        Date todayDate;



        filterCollection = new ArrayList<>();
        try {
            DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'+'HH:mm", Locale.ENGLISH);
            todayDate = dateFormater.parse(String.valueOf(dateSelected));

            SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
            SimpleDateFormat selectedDate=new SimpleDateFormat("yyyy-MM-dd");
            Day = outFormat.format(todayDate);
            Date=selectedDate.format(todayDate);

            for (FitnessClassess item : entity) {
                dateFormater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+'HH:mm",Locale.ENGLISH);
                Date startDate = dateFormater.parse(item.getFromDate());
                Date endDate = dateFormater.parse(item.getToDate());

            if(todayDate.after(startDate) && todayDate.before(endDate)) {
                filterCollection.add(item);
            }
        }

            } catch (ParseException e) {
            e.printStackTrace();
        }

        setFitnessData(filterCollection);

    }

    @Override
    public void onRecyclerItemClicked(Object Ent, int position) {

        FitnessClassess fitnessClassData = (FitnessClassess) Ent;
        getDockActivity().replaceDockableFragment(ClassDetailFragment.newInstance(fitnessClassData,Day,Date), "ClassDetailFragment");
    }
}