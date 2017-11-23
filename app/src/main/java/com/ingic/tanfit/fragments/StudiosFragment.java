package com.ingic.tanfit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.fitnessEnt;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.global.AppConstants;
import com.ingic.tanfit.ui.adapters.ArrayListAdapter;
import com.ingic.tanfit.ui.binders.FItnessItemBinder;
import com.ingic.tanfit.ui.binders.StudiosItemBinder;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by saeedhyder on 11/22/2017.
 */
public class StudiosFragment extends BaseFragment {
    @BindView(R.id.txt_noresult)
    AnyTextView txtNoresult;
    @BindView(R.id.lv_studios)
    ListView lvStudios;
    Unbinder unbinder;

    private ArrayListAdapter<fitnessEnt> adapter;
    private ArrayList<fitnessEnt> userCollection;

    public static StudiosFragment newInstance() {
        Bundle args = new Bundle();

        StudiosFragment fragment = new StudiosFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ArrayListAdapter<fitnessEnt>(getDockActivity(), new StudiosItemBinder(getDockActivity(),prefHelper));
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_studios, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setStudiosData();
    }

    private void setStudiosData() {

        userCollection = new ArrayList<>();

        userCollection.add(new fitnessEnt(AppConstants.DRAWABLE_PATH+R.drawable.image,"Group Personal Training","Bespoke Ride","Al Quoz","08:00","60 min"));
        userCollection.add(new fitnessEnt(AppConstants.DRAWABLE_PATH+R.drawable.image,"Group Personal Training","Bespoke Ride","Al Quoz","08:00","60 min"));
        userCollection.add(new fitnessEnt(AppConstants.DRAWABLE_PATH+R.drawable.image,"Group Personal Training","Bespoke Ride","Al Quoz","08:00","60 min"));
        userCollection.add(new fitnessEnt(AppConstants.DRAWABLE_PATH+R.drawable.image,"Group Personal Training","Bespoke Ride","Al Quoz","08:00","60 min"));
        userCollection.add(new fitnessEnt(AppConstants.DRAWABLE_PATH+R.drawable.image,"Group Personal Training","Bespoke Ride","Al Quoz","08:00","60 min"));

        if (userCollection.size() <= 0) {
            txtNoresult.setVisibility(View.VISIBLE);
            lvStudios.setVisibility(View.GONE);
        } else {
            txtNoresult.setVisibility(View.GONE);
            lvStudios.setVisibility(View.VISIBLE);
        }

        adapter.clearList();
        lvStudios.setAdapter(adapter);
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
