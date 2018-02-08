package com.ingic.tanfit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;
import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.GetNearestStudiosEnt;
import com.ingic.tanfit.entities.Studio;
import com.ingic.tanfit.entities.fitnessEnt;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.global.AppConstants;
import com.ingic.tanfit.interfaces.RecyclerViewItemListener;
import com.ingic.tanfit.interfaces.SetChildTitlebar;
import com.ingic.tanfit.ui.adapters.ArrayListAdapter;
import com.ingic.tanfit.ui.adapters.ArrayListExpandableAdapter;
import com.ingic.tanfit.ui.binders.HomeFitnessBinder;
import com.ingic.tanfit.ui.binders.HomeStudioBinder;
import com.ingic.tanfit.ui.binders.StudiosItemBinder;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.TitleBar;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created on gym_image_11/25/2017.
 */
public class HomeStudioFragment extends BaseFragment implements DatePickerListener,RecyclerViewItemListener {
    @BindView(R.id.datePicker)
    HorizontalPicker datePicker;
    @BindView(R.id.txt_noresult)
    AnyTextView txtNoresult;
    @BindView(R.id.lv_fitnessClasses)
    ListView lvStudios;
    Unbinder unbinder;
    private ArrayListAdapter<Studio> adapter;
    private ArrayList<Studio> userCollection;
    ArrayList<Studio> entity;






    public static HomeStudioFragment newInstance() {
        Bundle args = new Bundle();

        HomeStudioFragment fragment = new HomeStudioFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ArrayListAdapter<Studio>(getDockActivity(), new HomeStudioBinder(getDockActivity(),prefHelper,this));
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_studio, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    public void setContent(ArrayList<Studio> data){
     //   adapter = new ArrayListAdapter<Studio>(getDockActivity(), new HomeStudioBinder(getDockActivity(),prefHelper));
        this.entity=data;

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        datePicker
                .setListener(this)
                .setDays(120)
                .setOffset(1)
                .init();
        datePicker.setDate(new DateTime());
        setStudiosData(entity);

    }
    private void setStudiosData(ArrayList<Studio> entity) {

      /*  userCollection = new ArrayList<>();
        userCollection.addAll(entity);*/
        /*if(this.entity !=null){
            userCollection= entity;
        }*/
/*
        userCollection.add(new fitnessEnt(AppConstants.DRAWABLE_PATH+R.drawable.gym_image_1,"Fitness Center Name 1","Bespoke Ride","Al Quoz","gym_image_8:00","60 min"));
        userCollection.add(new fitnessEnt(AppConstants.DRAWABLE_PATH+R.drawable.gym_image_2,"Fitness Center Name 2","Bespoke Ride","Al Quoz","gym_image_8:00","60 min"));
        userCollection.add(new fitnessEnt(AppConstants.DRAWABLE_PATH+R.drawable.gym_image_3,"Fitness Center Name 3","Bespoke Ride","Al Quoz","gym_image_8:00","60 min"));
        userCollection.add(new fitnessEnt(AppConstants.DRAWABLE_PATH+R.drawable.gym_image_4,"Fitness Center Name 4","Bespoke Ride","Al Quoz","gym_image_8:00","60 min"));
        userCollection.add(new fitnessEnt(AppConstants.DRAWABLE_PATH+R.drawable.gym_image_1,"Fitness Center Name 5","Bespoke Ride","Al Quoz","gym_image_8:00","60 min"));
        userCollection.add(new fitnessEnt(AppConstants.DRAWABLE_PATH+R.drawable.gym_image_2,"Fitness Center Name 6","Bespoke Ride","Al Quoz","gym_image_8:00","60 min"));
        userCollection.add(new fitnessEnt(AppConstants.DRAWABLE_PATH+R.drawable.gym_image_3,"Fitness Center Name 7","Bespoke Ride","Al Quoz","gym_image_8:00","60 min"));
        userCollection.add(new fitnessEnt(AppConstants.DRAWABLE_PATH+R.drawable.gym_image_4,"Fitness Center Name 8","Bespoke Ride","Al Quoz","gym_image_8:00","60 min"));
// userCollection.add(new fitnessEnt(AppConstants.DRAWABLE_PATH+R.drawable.gymstudio3,"Fitness Center Name 5","Bespoke Ride","Al Quoz","gym_image_8:00","60 min"));*/


        if (entity.size() <= 0) {
            txtNoresult.setVisibility(View.VISIBLE);
            lvStudios.setVisibility(View.GONE);
        } else {
            txtNoresult.setVisibility(View.GONE);
            lvStudios.setVisibility(View.VISIBLE);
        }


        adapter.clearList();
        lvStudios.setAdapter(adapter);
        adapter.addAll(entity);
        adapter.notifyDataSetChanged();
      /*  lvStudios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });*/
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
    public void onDateSelected(DateTime dateSelected) {

    }

    @Override
    public void onRecyclerItemClicked(Object ent, int position) {

        Studio studioData=(Studio)ent;
        getDockActivity().replaceDockableFragment(GymDetailFragment.newInstance(studioData),"GymDetailFragment");
    }
}