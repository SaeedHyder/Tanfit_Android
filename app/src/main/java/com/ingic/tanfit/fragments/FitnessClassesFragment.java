package com.ingic.tanfit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.fitnessEnt;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.global.AppConstants;
import com.ingic.tanfit.ui.adapters.ArrayListAdapter;
import com.ingic.tanfit.ui.binders.FItnessItemBinder;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by saeedhyder on 11/22/2017.
 */
public class FitnessClassesFragment extends BaseFragment {
    @BindView(R.id.txt_noresult)
    AnyTextView txtNoresult;
    @BindView(R.id.lv_fitnessClasses)
    ListView lvFitnessClasses;
    Unbinder unbinder;

    private ArrayListAdapter<fitnessEnt> adapter;
    private ArrayList<fitnessEnt> userCollection;

    public static FitnessClassesFragment newInstance() {
        Bundle args = new Bundle();

        FitnessClassesFragment fragment = new FitnessClassesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ArrayListAdapter<fitnessEnt>(getDockActivity(), new FItnessItemBinder(getDockActivity(),prefHelper));
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fitness_classes, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setFitnessData();
        itemListner();

    }

    private void itemListner() {
        lvFitnessClasses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getDockActivity().replaceDockableFragment(ClassDetailFragment.newInstance(),"ClassDetailFragment");
            }
        });
    }

    private void setFitnessData() {

        userCollection = new ArrayList<>();

        userCollection.add(new fitnessEnt(AppConstants.DRAWABLE_PATH+R.drawable.group_training,"Group Personal Training","Bespoke Ride","Al Quoz","08:00","60 min"));
        userCollection.add(new fitnessEnt(AppConstants.DRAWABLE_PATH+R.drawable.group_training,"Group Personal Training","Bespoke Ride","Al Quoz","08:00","60 min"));
        userCollection.add(new fitnessEnt(AppConstants.DRAWABLE_PATH+R.drawable.group_training,"Group Personal Training","Bespoke Ride","Al Quoz","08:00","60 min"));
        userCollection.add(new fitnessEnt(AppConstants.DRAWABLE_PATH+R.drawable.group_training,"Group Personal Training","Bespoke Ride","Al Quoz","08:00","60 min"));
        userCollection.add(new fitnessEnt(AppConstants.DRAWABLE_PATH+R.drawable.group_training,"Group Personal Training","Bespoke Ride","Al Quoz","08:00","60 min"));

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
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading("");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
