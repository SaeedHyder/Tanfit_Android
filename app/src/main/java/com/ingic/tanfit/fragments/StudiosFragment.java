package com.ingic.tanfit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.FavoriteClassesEnt;
import com.ingic.tanfit.entities.Studio;
import com.ingic.tanfit.entities.UserFitnessClasses;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.global.WebServiceConstants;
import com.ingic.tanfit.interfaces.RecyclerViewItemListener;
import com.ingic.tanfit.ui.adapters.ArrayListAdapter;
import com.ingic.tanfit.ui.binders.StudiosItemBinder;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by saeedhyder on gym_image_11/22/2017.
 */
public class StudiosFragment extends BaseFragment implements RecyclerViewItemListener {
    @BindView(R.id.txt_noresult)
    AnyTextView txtNoresult;
    @BindView(R.id.lv_studios)
    ListView lvStudios;
    Unbinder unbinder;

    private ArrayListAdapter<Studio> adapter;
    private ArrayList<Studio> studiosCollection;

    public static StudiosFragment newInstance() {
        Bundle args = new Bundle();

        StudiosFragment fragment = new StudiosFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ArrayListAdapter<Studio>(getDockActivity(), new StudiosItemBinder(getDockActivity(), prefHelper, this));
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

        if (prefHelper.isLanguagePersian()) {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        } else {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }

        setStudiosData(prefHelper.getFavoriteData().getStudios());
        itemListner();
    }

    private void itemListner() {
        lvStudios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getDockActivity().replaceDockableFragment(GymDetailFragment.newInstance(), "GymDetailFragment");
            }
        });
    }

    private void setStudiosData(ArrayList<Studio> allfavoriteStudios) {

        ArrayList<Studio> favoriteStudios=new ArrayList<>();

        for(Studio item: allfavoriteStudios){

            if(!item.getIsDeleted()){
                favoriteStudios.add(item);
            }

        }
        /*studiosCollection = new ArrayList<>();

        for (UserFitnessClasses item : favoriteStudios) {
            serviceHelper.enqueueCall(headerWebService.getStudio(item.getStudioId() + ""), WebServiceConstants.getStudioDetail);
        }*/

        if (favoriteStudios.size() <= 0) {
            txtNoresult.setVisibility(View.VISIBLE);
            lvStudios.setVisibility(View.GONE);
        } else {
            txtNoresult.setVisibility(View.GONE);
            lvStudios.setVisibility(View.VISIBLE);
        }

        adapter.clearList();
        lvStudios.setAdapter(adapter);
        adapter.addAll(favoriteStudios);
        adapter.notifyDataSetChanged();
    }

 /*   @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {

            case WebServiceConstants.getStudioDetail:

                studiosCollection.add((Studio) result);

                if (studiosCollection.size() == prefHelper.getUserAllData().getUserFavouriteStudioModel().size()) {
                    if (studiosCollection.size() <= 0) {
                        txtNoresult.setVisibility(View.VISIBLE);
                        lvStudios.setVisibility(View.GONE);
                    } else {
                        txtNoresult.setVisibility(View.GONE);
                        lvStudios.setVisibility(View.VISIBLE);
                    }

                    adapter.clearList();
                    lvStudios.setAdapter(adapter);
                    adapter.addAll(studiosCollection);
                    adapter.notifyDataSetChanged();

                }

                break;

        }
    }
*/
    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading("");
    }



    @Override
    public void onRecyclerItemClicked(Object Ent, int position) {
        Studio studioData = (Studio) Ent;
        getDockActivity().replaceDockableFragment(GymDetailFragment.newInstance(studioData), "GymDetailFragment");
    }
}
