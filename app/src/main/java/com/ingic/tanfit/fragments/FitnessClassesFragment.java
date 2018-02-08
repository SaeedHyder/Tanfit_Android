package com.ingic.tanfit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.FavoriteClassesEnt;
import com.ingic.tanfit.entities.FitnessClass;
import com.ingic.tanfit.entities.FitnessClassess;
import com.ingic.tanfit.entities.Studio;
import com.ingic.tanfit.entities.UserFitnessClasses;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.global.WebServiceConstants;
import com.ingic.tanfit.interfaces.RecyclerViewItemListener;
import com.ingic.tanfit.ui.adapters.ArrayListAdapter;
import com.ingic.tanfit.ui.binders.FItnessItemBinder;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by saeedhyder on gym_image_11/22/2017.
 */
public class FitnessClassesFragment extends BaseFragment implements RecyclerViewItemListener{
    @BindView(R.id.txt_noresult)
    AnyTextView txtNoresult;
    @BindView(R.id.lv_fitnessClasses)
    ListView lvFitnessClasses;
    Unbinder unbinder;

    private ArrayListAdapter<FitnessClassess> adapter;
    private ArrayList<FitnessClassess> fitnessClassesCollection;

    public static FitnessClassesFragment newInstance() {
        Bundle args = new Bundle();

        FitnessClassesFragment fragment = new FitnessClassesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ArrayListAdapter<FitnessClassess>(getDockActivity(), new FItnessItemBinder(getDockActivity(), prefHelper,this));
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

        setFitnessData(prefHelper.getFavoriteData().getFitnessClasses());
        itemListner();

    }

    private void itemListner() {
        lvFitnessClasses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getDockActivity().replaceDockableFragment(ClassDetailFragment.newInstance(), "ClassDetailFragment");
            }
        });
    }

    private void setFitnessData(ArrayList<FitnessClassess> allfavoriteClasses) {

        ArrayList<FitnessClassess> favoriteClasses=new ArrayList<>();

        for(FitnessClassess item: allfavoriteClasses){

            if(!item.getIsDeleted()){
                favoriteClasses.add(item);
            }

        }

     //  fitnessClassesCollection=new ArrayList<>();

        /*for(FitnessClassess item:favoriteClasses){
            serviceHelper.enqueueCall(headerWebService.getFitnessClass(item.getId()+""), WebServiceConstants.getFitnessClassDetail);
        }*/

        if (favoriteClasses.size() <= 0) {
            txtNoresult.setVisibility(View.VISIBLE);
            lvFitnessClasses.setVisibility(View.GONE);
        } else {
            txtNoresult.setVisibility(View.GONE);
            lvFitnessClasses.setVisibility(View.VISIBLE);
        }

        adapter.clearList();
        lvFitnessClasses.setAdapter(adapter);
        adapter.addAll(favoriteClasses);
        adapter.notifyDataSetChanged();
    }


    /*@Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {

            case WebServiceConstants.getFitnessClassDetail:
                fitnessClassesCollection.add((FitnessClassess)result);

                if(fitnessClassesCollection.size()==prefHelper.getUserAllData().getUserFavouriteFitnessClasses().size()){
                    if (fitnessClassesCollection.size() <= 0) {
                        txtNoresult.setVisibility(View.VISIBLE);
                        lvFitnessClasses.setVisibility(View.GONE);
                    } else {
                        txtNoresult.setVisibility(View.GONE);
                        lvFitnessClasses.setVisibility(View.VISIBLE);
                    }

                    adapter.clearList();
                    lvFitnessClasses.setAdapter(adapter);
                    adapter.addAll(fitnessClassesCollection);
                    adapter.notifyDataSetChanged();

                }

                break;

        }
    }*/

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

    @Override
    public void onRecyclerItemClicked(Object Ent, int position) {
        FitnessClassess fitnessClassData = (FitnessClassess) Ent;
        getDockActivity().replaceDockableFragment(ClassDetailFragment.newInstance(fitnessClassData,true), "ClassDetailFragment");
    }
}
