package com.ingic.tanfit.fragments;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.Studio;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.interfaces.RecyclerViewItemListener;
import com.ingic.tanfit.ui.adapters.ArrayListAdapter;
import com.ingic.tanfit.ui.binders.HomeStudioBinder;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.CustomRecyclerView;
import com.ingic.tanfit.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created on gym_image_11/25/2017.
 */
public class HomeStudioFragment extends BaseFragment implements RecyclerViewItemListener {

    @BindView(R.id.txt_noresult)
    AnyTextView txtNoresult;
    @BindView(R.id.lv_fitnessClasses)
    CustomRecyclerView lvStudios;
    Unbinder unbinder;
    ArrayList<Studio> entity;

    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false);


    public static HomeStudioFragment newInstance() {
        Bundle args = new Bundle();

        HomeStudioFragment fragment = new HomeStudioFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_studio, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    public void setContent(ArrayList<Studio> data) {
        this.entity = data;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (prefHelper.isLanguagePersian()) {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        } else {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }

        if (entity != null)
            setStudiosData(entity);

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (((HomeFragment) getParentFragment()) != null) {
                ((HomeFragment) getParentFragment()).showSearchBar();
            }
            if (prefHelper != null) {
                prefHelper.setIsFromStudio(true);
            }
        }
    }


    private void setStudiosData(ArrayList<Studio> entity) {

        if (entity.size() <= 0) {
            txtNoresult.setVisibility(View.VISIBLE);
            lvStudios.setVisibility(View.GONE);
        } else {
            txtNoresult.setVisibility(View.GONE);
            lvStudios.setVisibility(View.VISIBLE);
        }

        linearLayoutManager = new LinearLayoutManager(getDockActivity());
        lvStudios.BindRecyclerView(new HomeStudioBinder(getDockActivity(), prefHelper, this), entity,
                linearLayoutManager
                , new DefaultItemAnimator());

    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();

    }


    @Override
    public void onRecyclerItemClicked(Object ent, int position) {

        Studio studioData = (Studio) ent;
        getDockActivity().replaceDockableFragment(GymDetailFragment.newInstance(studioData), "GymDetailFragment");
    }
}