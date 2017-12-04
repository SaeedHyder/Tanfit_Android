package com.ingic.tanfit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;
import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.fitnessEnt;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.global.AppConstants;
import com.ingic.tanfit.interfaces.SetChildTitlebar;
import com.ingic.tanfit.ui.adapters.ArrayListAdapter;
import com.ingic.tanfit.ui.binders.HomeFitnessBinder;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.TitleBar;

import org.joda.time.DateTime;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created on 11/25/2017.
 */
public class HomeFitnessClassFragment extends BaseFragment implements DatePickerListener {
    @BindView(R.id.datePicker)
    HorizontalPicker datePicker;
    @BindView(R.id.txt_noresult)
    AnyTextView txtNoresult;
    @BindView(R.id.lv_fitnessClasses)
    ListView lvFitnessClasses;
    Unbinder unbinder;
    private ArrayListAdapter<fitnessEnt> adapter;
    private ArrayList<fitnessEnt> userCollection;


    public static HomeFitnessClassFragment newInstance() {
        Bundle args = new Bundle();

        HomeFitnessClassFragment fragment = new HomeFitnessClassFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ArrayListAdapter<fitnessEnt>(getDockActivity(), new HomeFitnessBinder(getDockActivity(), prefHelper));
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

        datePicker
                .setListener(this)
                .setDays(120)
                .setOffset(7)
                .init();
        datePicker.setDate(new DateTime());
        setFitnessData();
    }

    private void setFitnessData() {

        userCollection = new ArrayList<>();

        userCollection.add(new fitnessEnt(AppConstants.DRAWABLE_PATH + R.drawable.group_training, "Group Personal Training", "Bespoke Ride", "Al Quoz", "08:00", "60 min"));
        userCollection.add(new fitnessEnt(AppConstants.DRAWABLE_PATH + R.drawable.power_yoga, "Power Yoga", "136.1 Yoga Studio", "Al Quoz", "08:00", "60 min"));
        userCollection.add(new fitnessEnt(AppConstants.DRAWABLE_PATH + R.drawable.pelton_biking, "Pelton Biking", "Quantum Health Club", "Trade Center Area", "08:00", "60 min"));
      //  userCollection.add(new fitnessEnt(AppConstants.DRAWABLE_PATH + R.drawable.spinninghome, "Spinning", "136.1 Yoga Studio", "Al Quoz", "08:00", "60 min"));
        userCollection.add(new fitnessEnt(AppConstants.DRAWABLE_PATH + R.drawable.group_training, "Group Personal Training", "Bespoke Ride", "Al Quoz", "08:00", "60 min"));
        userCollection.add(new fitnessEnt(AppConstants.DRAWABLE_PATH + R.drawable.power_yoga, "Power Yoga", "136.1 Yoga Studio", "Al Quoz", "08:00", "60 min"));
        userCollection.add(new fitnessEnt(AppConstants.DRAWABLE_PATH + R.drawable.pelton_biking, "Pelton Biking", "Quantum Health Club", "Trade Center Area", "08:00", "60 min"));


        if (userCollection.size() <= 0) {
            txtNoresult.setVisibility(View.VISIBLE);
            lvFitnessClasses.setVisibility(View.GONE);
        } else {
            txtNoresult.setVisibility(View.GONE);
            lvFitnessClasses.setVisibility(View.VISIBLE);
        }

        adapter.clearList();
        lvFitnessClasses.setAdapter(adapter);
        adapter.addAll(userCollection);
        adapter.notifyDataSetChanged();
        lvFitnessClasses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getDockActivity().replaceDockableFragment(ClassDetailFragment.newInstance(),"ClassDetailFragment");
            }
        });
    }

    @Override
    public void onDateSelected(DateTime dateSelected) {

    }
}