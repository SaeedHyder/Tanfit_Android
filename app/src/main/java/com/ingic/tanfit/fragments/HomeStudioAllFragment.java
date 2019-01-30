package com.ingic.tanfit.fragments;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;
import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.Studio;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.global.WebServiceConstants;
import com.ingic.tanfit.interfaces.LoadMoreListener;
import com.ingic.tanfit.interfaces.RecyclerViewItemListener;
import com.ingic.tanfit.ui.adapters.ArrayListAdapter;
import com.ingic.tanfit.ui.binders.HomeStudioBinder;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.CustomRecyclerView;
import com.ingic.tanfit.ui.views.TitleBar;

import org.joda.time.DateTime;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created on gym_image_11/25/2017.
 */
public class HomeStudioAllFragment extends BaseFragment implements DatePickerListener, RecyclerViewItemListener {
    @BindView(R.id.datePicker)
    HorizontalPicker datePicker;
    @BindView(R.id.txt_noresult)
    AnyTextView txtNoresult;
    @BindView(R.id.lv_fitnessClasses)
    CustomRecyclerView lvStudios;
    Unbinder unbinder;
    private ArrayListAdapter<Studio> adapter;
    private ArrayList<Studio> userCollection;
    ArrayList<Studio> entity;

    boolean canCallForMore = true;
    boolean isOnCall;
    int currentPageNumber = 1;
    int totalCount = 10;
    private LinearLayoutManager linearLayoutManager;


    public static HomeStudioAllFragment newInstance() {
        Bundle args = new Bundle();

        HomeStudioAllFragment fragment = new HomeStudioAllFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //    adapter = new ArrayListAdapter<Studio>(getDockActivity(), new HomeStudioBinder(getDockActivity(),prefHelper,this));
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_studio, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (prefHelper.isLanguagePersian()) {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        } else {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }

        if (!prefHelper.isStudio())
            if (((HomeFragment) getParentFragment()) != null)
                ((HomeFragment) getParentFragment()).hideSearchBar();

        currentPageNumber = 1;
        serviceHelper.enqueueCall(headerWebService.getAllStudios(currentPageNumber, totalCount), WebServiceConstants.getAllStudios);



    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (((HomeFragment) getParentFragment()) != null) {
                ((HomeFragment) getParentFragment()).hideSearchBar();
            }
            if (prefHelper != null) {
                prefHelper.setIsFromStudio(false);
            }
        }

    }


    @Override
    public void ResponseSuccess(final Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {
            case WebServiceConstants.getAllStudios:

                ArrayList<Studio> allStudios = (ArrayList<Studio>) result;
                entity = allStudios;
                setStudiosData(entity);

                break;

            case WebServiceConstants.getAllStudiosPaging:

                isOnCall = false;
                bindPagedSearchList((ArrayList<Studio>) result);


                break;
        }
    }

    private void setStudiosData(ArrayList<Studio> entity) {


        if (entity.size() <= 0) {
            txtNoresult.setText(getDockActivity().getResources().getString(R.string.no_studios_found));
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

        lvStudios.getAdapter().setOnLoadMoreListener(new LoadMoreListener() {
            @Override
            public void onLoadMoreItem(int position) {
                getPagedData();
            }
        });




        /*adapter.clearList();
        lvStudios.setAdapter(adapter);
        adapter.addAll(entity);
        adapter.notifyDataSetChanged();*/

    }

    private void getPagedData() {


        if (canCallForMore) {
            if (!isOnCall) {
                currentPageNumber = currentPageNumber + 1;
                //  progressBar.setVisibility(View.VISIBLE);
                isOnCall = true;
                serviceHelper.enqueueCall(headerWebService.getAllStudios(currentPageNumber, totalCount), WebServiceConstants.getAllStudiosPaging);
            }
        }

    }

    private void bindPagedSearchList(ArrayList<Studio> result) {
        if (result.size() <= 0) {
            canCallForMore = false;
        } else {
            entity.addAll(result);
            lvStudios.notifyItemRangeChanged(linearLayoutManager.findLastVisibleItemPosition(), result.size());
        }
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();

    }

    @Override
    public void onDateSelected(DateTime dateSelected) {

    }

    @Override
    public void onRecyclerItemClicked(Object ent, int position) {

        Studio studioData = (Studio) ent;
        getDockActivity().replaceDockableFragment(GymDetailFragment.newInstance(studioData), "GymDetailFragment");
    }
}