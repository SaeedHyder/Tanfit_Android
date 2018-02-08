package com.ingic.tanfit.fragments;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.GymDetailListEnt;
import com.ingic.tanfit.entities.IsFavoriteEnt;
import com.ingic.tanfit.entities.Studio;
import com.ingic.tanfit.entities.StudioFeature;
import com.ingic.tanfit.entities.StudioImage;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.global.AppConstants;
import com.ingic.tanfit.global.WebServiceConstants;
import com.ingic.tanfit.helpers.DialogHelper;
import com.ingic.tanfit.helpers.UIHelper;
import com.ingic.tanfit.interfaces.RecyclerViewItemListener;
import com.ingic.tanfit.ui.adapters.ArrayListAdapter;
import com.ingic.tanfit.ui.adapters.ArrayListExpandableAdapter;
import com.ingic.tanfit.ui.binders.GymGalleryItemBinder;
import com.ingic.tanfit.ui.binders.StudioFeatureExpandedBinder;
import com.ingic.tanfit.ui.binders.speacialFeatureItemBinder;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.CustomExpandableListView;
import com.ingic.tanfit.ui.views.CustomRecyclerView;
import com.ingic.tanfit.ui.views.ExpandableGridView;
import com.ingic.tanfit.ui.views.TitleBar;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created on gym_image_11/27/2017.
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
    @BindView(R.id.elv_features)
    CustomExpandableListView elvFeatures;
    private ArrayListAdapter<StudioFeature> adaptergym;
    private ArrayList<StudioFeature> userCollectiongym = new ArrayList<>();
    private ArrayListAdapter<StudioFeature> adapteryoga;
    private ArrayList<StudioFeature> userCollectionyoga = new ArrayList<>();
    private ArrayList<StudioImage> userCollectionsGallery;

    private ArrayListExpandableAdapter<String, ArrayList<StudioFeature>> adapter;
    private ArrayList<String> collectionGroup;
    private ArrayList<StudioFeature> collectionChild;
    private HashMap<String, ArrayList<ArrayList<StudioFeature>>> listDataChild;
    private ArrayList<GymDetailListEnt> featuresList;

    private static String StudioEnt = "studioEnt";
    private static String StudioIdKey = "StudioId";
    private int StudioId;
    private String jsonString;
    private Studio enitity;
    private ImageLoader imageLoader;

    public static GymDetailFragment newInstance() {
        Bundle args = new Bundle();

        GymDetailFragment fragment = new GymDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static GymDetailFragment newInstance(Studio studioData) {
        Bundle args = new Bundle();
        args.putString(StudioEnt, new Gson().toJson(studioData));
        GymDetailFragment fragment = new GymDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //FromClassDetail
    public static GymDetailFragment newInstance(int studioId) {
        Bundle args = new Bundle();
        args.putInt(StudioIdKey, studioId);
        GymDetailFragment fragment = new GymDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageLoader = ImageLoader.getInstance();
        adaptergym = new ArrayListAdapter<StudioFeature>(getDockActivity(), new speacialFeatureItemBinder(getDockActivity(), prefHelper));
        adapteryoga = new ArrayListAdapter<StudioFeature>(getDockActivity(), new speacialFeatureItemBinder(getDockActivity(), prefHelper));
        if (getArguments() != null) {
            jsonString = getArguments().getString(StudioEnt);
            StudioId = getArguments().getInt(StudioIdKey);
        }
        if (jsonString != null) {
            enitity = new Gson().fromJson(jsonString, Studio.class);
        }

        if (enitity == null) {
            for (Studio item : prefHelper.getNearestStuidos().getStudios()) {
                if (item.getId() == StudioId) {
                    enitity = item;
                }
            }

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

        serviceHelper.enqueueCall(headerWebService.isFavoriteStudio(prefHelper.getUserAllData().getId(), enitity.getId()), WebServiceConstants.isFavoriteStudio);

        prefHelper.setIsFromStudio(true);

        setStudioData();
        setFeatureList();

        setyogaData();
        setgymData();
        setRecyclerViewData();

    }

    private void setFeatureList() {

        featuresList = new ArrayList<>();
        featuresList.add(new GymDetailListEnt("Gym", enitity.getStudioFeatures()));

        if (enitity.getFitnessClasses().size() > 0) {
            for (int i = 0; i < enitity.getFitnessClasses().size(); i++) {
                featuresList.add(new GymDetailListEnt(enitity.getFitnessClasses().get(i).getClassNameEng(), enitity.getFitnessClasses().get(i).getFitnessClassFeatures()));
            }
        }

        setExpandableListView(featuresList);

    }

    private void setExpandableListView(ArrayList<GymDetailListEnt> featuresList) {
        elvFeatures.setExpanded(true);
        collectionGroup = new ArrayList<>();
        collectionChild = new ArrayList<>();
        listDataChild = new HashMap<>();
        ArrayList<ArrayList<StudioFeature>> childArrayCollection = new ArrayList<>();


        for (int i = 0; i < featuresList.size(); i++) {

            collectionGroup.add(featuresList.get(i).getFeatureHeader());
            collectionChild.addAll(featuresList.get(i).getFeaturesList());
            childArrayCollection.add(collectionChild);

            listDataChild.put(collectionGroup.get(i), childArrayCollection);

            collectionChild = new ArrayList<>();
            childArrayCollection = new ArrayList<>();

        }
      /*  collectionGroup.add("Gym");
        collectionGroup.add("Yoga");
      *//*  collectionGroup.add("Wednesday");
        collectionGroup.add("Thursday");
        collectionGroup.add("Friday");
        collectionGroup.add("Saturday");*//*
        collectionChild.addAll(enitity.getStudioFeatures());

      *//*  collectionChild.add(new TimingEnt("8 am to 12 pm ", "12 am to 4 pm"));
        collectionChild.add(new TimingEnt("4 pm to 8 pm ", "8 pm to 9 pm"));
        collectionChild.add(new TimingEnt("4 am to 12 pm ", "12 am to 4 pm"));*//*
        sa.add(collectionChild);
        listDataChild.put(collectionGroup.get(0), sa);
        listDataChild.put(collectionGroup.get(1), sa);*/
        //  listDataChild.put(collectionGroup.get(2), sa);
        //  listDataChild.put(collectionGroup.get(3), sa);
        //   listDataChild.put(collectionGroup.get(4), sa);
        // listDataChild.put(collectionGroup.get(5), sa);


        adapter = new ArrayListExpandableAdapter<String, ArrayList<StudioFeature>>(getActivity(), collectionGroup,
                listDataChild, new StudioFeatureExpandedBinder(getDockActivity(), prefHelper),
                null);
        elvFeatures.setAdapter(adapter);
        for (int i = 0; i < listDataChild.size(); i++) {
            elvFeatures.expandGroup(i);
        }
        elvFeatures.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return true; // This way the expander cannot be collapsed
            }
        });
        adapter.notifyDataSetChanged();
    }


    private void setStudioData() {
        imageLoader.displayImage(enitity.getStudioLogo(), ivProfileImage);
        txtTitle.setText(enitity.getStudioNameEng() + "");
        txtAddress.setText(enitity.getAddressEng() + "");
    }

    private void setgymData() {

        userCollectiongym = new ArrayList<>();

        userCollectiongym.addAll(enitity.getStudioFeatures());

   /*     userCollectiongym.add(new SpecialFeatureEnt(AppConstants.DRAWABLE_PATH + R.drawable.changing_room, getDockActivity().getResources().getString(R.string.changing_room)));
        userCollectiongym.add(new SpecialFeatureEnt(AppConstants.DRAWABLE_PATH + R.drawable.treadmil, getDockActivity().getResources().getString(R.string.treadmil)));
        userCollectiongym.add(new SpecialFeatureEnt(AppConstants.DRAWABLE_PATH + R.drawable.music, getDockActivity().getResources().getString(R.string.music)));
        userCollectiongym.add(new SpecialFeatureEnt(AppConstants.DRAWABLE_PATH + R.drawable.trainer, getDockActivity().getResources().getString(R.string.trainer)));
*/

        adaptergym.clearList();
        adaptergym.addAll(userCollectiongym);
        gvGym.setAdapter(adaptergym);
        adaptergym.notifyDataSetChanged();
    }

    private void setyogaData() {

        userCollectionyoga = new ArrayList<>();

     /*   userCollectionyoga.add(new SpecialFeatureEnt(AppConstants.DRAWABLE_PATH + R.drawable.changing_room, getDockActivity().getResources().getString(R.string.changing_room)));
        userCollectionyoga.add(new SpecialFeatureEnt(AppConstants.DRAWABLE_PATH + R.drawable.steam_room, getString(R.string.steamroom)));
        userCollectionyoga.add(new SpecialFeatureEnt(AppConstants.DRAWABLE_PATH + R.drawable.music,getString(R.string.music)));
        userCollectionyoga.add(new SpecialFeatureEnt(AppConstants.DRAWABLE_PATH + R.drawable.trainer, getDockActivity().getResources().getString(R.string.trainer)));*/


        adapteryoga.clearList();
        adapteryoga.addAll(userCollectionyoga);
        gvYoga.setAdapter(adapteryoga);
        adapteryoga.notifyDataSetChanged();

    }

    private void setRecyclerViewData() {

        userCollectionsGallery = new ArrayList<>();
        userCollectionsGallery.addAll(enitity.getStudioImages());

       /* userCollectionsGallery.add(new SliderDialogEnt(AppConstants.DRAWABLE_PATH+R.drawable.gym_image_8,R.drawable.gym_image_8));
        userCollectionsGallery.add(new SliderDialogEnt(AppConstants.DRAWABLE_PATH+R.drawable.gym_image_9,R.drawable.gym_image_9));
        userCollectionsGallery.add(new SliderDialogEnt(AppConstants.DRAWABLE_PATH+R.drawable.gym_image_10,R.drawable.gym_image_10));
        userCollectionsGallery.add(new SliderDialogEnt(AppConstants.DRAWABLE_PATH+R.drawable.gym_image_11,R.drawable.gym_image_11));*/


        rvGallery.BindRecyclerView(new GymGalleryItemBinder(this), userCollectionsGallery,
                new LinearLayoutManager(getDockActivity(), LinearLayoutManager.HORIZONTAL, false)
                , new DefaultItemAnimator());

    }


    @Override
    public void onRecyclerItemClicked(Object Ent, int position) {

       /* DialogHelper dialogHelper = new DialogHelper(getDockActivity());
        dialogHelper.openImageinBig(R.layout.dialog_image_viewer, ((GymFeatureEnt) Ent).getFeatureRes());
        dialogHelper.showDialog();*/

        DialogHelper dialogHelper = new DialogHelper(getDockActivity());
        dialogHelper.openSlider(R.layout.dialog_image_viewer, userCollectionsGallery, position, getDockActivity());
        dialogHelper.showDialog();
    }


    @OnClick({R.id.btn_subscribe_detail, R.id.btn_subscribe, R.id.btn_group, R.id.btn_express, R.id.btn_fitness, R.id.btn_personal})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_subscribe_detail:
                if (enitity.getFitnessClasses().size() > 0) {
                    getDockActivity().addAndShowDialogFragment(GymDetailTimingFragment.newInstance(enitity));
                } else
                    UIHelper.showShortToastInCenter(getDockActivity(), getResources().getString(R.string.no_class_found));
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

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {

            case WebServiceConstants.isFavoriteStudio:

                if (((IsFavoriteEnt) result).isFavorite()) {
                    getTitleBar().getHearCheckBox(R.id.cb_heart).setChecked(true);
                } else {
                    getTitleBar().getHearCheckBox(R.id.cb_heart).setChecked(false);
                }
                break;

            case WebServiceConstants.addFavoriteClass:

                break;

        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading("");
        titleBar.showHeartButton(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                serviceHelper.enqueueCall(headerWebService.addFavoriteStudio(prefHelper.getUserAllData().getId(), enitity.getId(), isChecked ? false : true), WebServiceConstants.addFavoriteStudio);

            }
        });
    }
}