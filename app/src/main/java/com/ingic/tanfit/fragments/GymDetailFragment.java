package com.ingic.tanfit.fragments;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.GymFeatureEnt;
import com.ingic.tanfit.entities.SpecialFeatureEnt;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.global.AppConstants;
import com.ingic.tanfit.helpers.DialogHelper;
import com.ingic.tanfit.interfaces.RecyclerViewItemListener;
import com.ingic.tanfit.ui.adapters.ArrayListAdapter;
import com.ingic.tanfit.ui.binders.GymGalleryItemBinder;
import com.ingic.tanfit.ui.binders.speacialFeatureItemBinder;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.CustomRecyclerView;
import com.ingic.tanfit.ui.views.ExpandableGridView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created on 11/27/2017.
 */
public class GymDetailFragment extends BaseFragment implements RecyclerViewItemListener {
    @BindView(R.id.iv_profileImage)
    CircleImageView ivProfileImage;
    @BindView(R.id.btn_subscribe_detail)
    ImageView btnSubscribeDetail;
    @BindView(R.id.btn_subscribe)
    Button btnSubscribe;
    @BindView(R.id.txt_title)
    AnyTextView txtTitle;
    @BindView(R.id.txt_address)
    AnyTextView txtAddress;
    Unbinder unbinder;
    @BindView(R.id.gv_gym)
    ExpandableGridView gvGym;
    @BindView(R.id.gv_yoga)
    ExpandableGridView gvYoga;
    @BindView(R.id.rv_gallery)
    CustomRecyclerView rvGallery;
    @BindView(R.id.btn_group)
    Button btnGroup;
    @BindView(R.id.btn_express)
    Button btnExpress;
    @BindView(R.id.btn_fitness)
    Button btnFitness;
    @BindView(R.id.btn_personal)
    Button btnPersonal;
    private ArrayListAdapter<SpecialFeatureEnt> adaptergym;
    private ArrayList<SpecialFeatureEnt> userCollectiongym = new ArrayList<>();
    private ArrayListAdapter<SpecialFeatureEnt> adapteryoga;
    private ArrayList<SpecialFeatureEnt> userCollectionyoga = new ArrayList<>();
    private ArrayList<GymFeatureEnt> userCollectionsGallery;

    public static GymDetailFragment newInstance() {
        Bundle args = new Bundle();

        GymDetailFragment fragment = new GymDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adaptergym = new ArrayListAdapter<SpecialFeatureEnt>(getDockActivity(), new speacialFeatureItemBinder(getDockActivity(), prefHelper));
        adapteryoga = new ArrayListAdapter<SpecialFeatureEnt>(getDockActivity(), new speacialFeatureItemBinder(getDockActivity(), prefHelper));
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gym_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setyogaData();
        setgymData();
        setRecyclerViewData();
    }

    private void setyogaData() {

        userCollectionyoga = new ArrayList<>();

        userCollectionyoga.add(new SpecialFeatureEnt(AppConstants.DRAWABLE_PATH + R.drawable.changing_room, getDockActivity().getResources().getString(R.string.changing_room)));
        userCollectionyoga.add(new SpecialFeatureEnt(AppConstants.DRAWABLE_PATH + R.drawable.treadmil, getDockActivity().getResources().getString(R.string.treadmil)));
        userCollectionyoga.add(new SpecialFeatureEnt(AppConstants.DRAWABLE_PATH + R.drawable.music, getDockActivity().getResources().getString(R.string.music)));
        userCollectionyoga.add(new SpecialFeatureEnt(AppConstants.DRAWABLE_PATH + R.drawable.trainer, getDockActivity().getResources().getString(R.string.trainer)));


        adapteryoga.clearList();
        adapteryoga.addAll(userCollectionyoga);
        gvYoga.setAdapter(adapteryoga);
        adapteryoga.notifyDataSetChanged();

    }

    private void setRecyclerViewData() {

        userCollectionsGallery = new ArrayList<>();
        userCollectionsGallery.add(new GymFeatureEnt(R.drawable.image8, "Troh Gym"));
        userCollectionsGallery.add(new GymFeatureEnt(R.drawable.image9, "Troh Gym"));
        userCollectionsGallery.add(new GymFeatureEnt(R.drawable.image8, "Troh Gym"));

        rvGallery.BindRecyclerView(new GymGalleryItemBinder(this), userCollectionsGallery,
                new LinearLayoutManager(getDockActivity(), LinearLayoutManager.HORIZONTAL, false)
                , new DefaultItemAnimator());

    }

    private void setgymData() {

        userCollectiongym = new ArrayList<>();

        userCollectiongym.add(new SpecialFeatureEnt(AppConstants.DRAWABLE_PATH + R.drawable.changing_room, getDockActivity().getResources().getString(R.string.changing_room)));
        userCollectiongym.add(new SpecialFeatureEnt(AppConstants.DRAWABLE_PATH + R.drawable.treadmil, getDockActivity().getResources().getString(R.string.treadmil)));
        userCollectiongym.add(new SpecialFeatureEnt(AppConstants.DRAWABLE_PATH + R.drawable.music, getDockActivity().getResources().getString(R.string.music)));
        userCollectiongym.add(new SpecialFeatureEnt(AppConstants.DRAWABLE_PATH + R.drawable.trainer, getDockActivity().getResources().getString(R.string.trainer)));


        adaptergym.clearList();
        adaptergym.addAll(userCollectiongym);
        gvGym.setAdapter(adaptergym);
        adaptergym.notifyDataSetChanged();
    }


    @Override
    public void onRecyclerItemClicked(Object Ent, int position) {
        DialogHelper dialogHelper = new DialogHelper(getDockActivity());
        dialogHelper.openImageinBig(R.layout.dialog_image_viewer, ((GymFeatureEnt) Ent).getFeatureRes());
        dialogHelper.showDialog();
    }


    @OnClick({R.id.btn_subscribe_detail, R.id.btn_subscribe, R.id.btn_group, R.id.btn_express, R.id.btn_fitness, R.id.btn_personal})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_subscribe_detail:
                getDockActivity().addAndShowDialogFragment(GymDetailTimingFragment.newInstance());
                break;
            case R.id.btn_subscribe:
                getDockActivity().popBackStackTillEntry(0);
                MainFragment mainFragment = MainFragment.newInstance();
                mainFragment.setStartWithTab(AppConstants.TAB_SUBSCRIBE);
                getDockActivity().replaceDockableFragment(mainFragment);
                break;
            case R.id.btn_group:
                willbeimplementedinBeta();
                break;
            case R.id.btn_express:
                willbeimplementedinBeta();
                break;
            case R.id.btn_fitness:
                willbeimplementedinBeta();
                break;
            case R.id.btn_personal:
                willbeimplementedinBeta();
                break;
        }
    }
}