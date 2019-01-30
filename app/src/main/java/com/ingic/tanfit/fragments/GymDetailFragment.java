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
import android.widget.LinearLayout;

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
import com.squareup.picasso.Picasso;

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
    @BindView(R.id.ll_gallery)
    LinearLayout llGallery;
    @BindView(R.id.ll_mainframe)
    LinearLayout llMainframe;
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
    private static String tagKey = "tagKey";
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

       /* if (enitity == null && prefHelper.getNearestStuidos().getStudios().size() > 0) {
            for (Studio item : prefHelper.getNearestStuidos().getStudios()) {
                if (item.getId() == StudioId) {
                    enitity = item;
                }
            }
        }*/
        if (enitity != null) {
            serviceHelper.enqueueCall(headerWebService.getStudio(enitity.getId() + ""), WebServiceConstants.StudioDetail);
        } else {
            serviceHelper.enqueueCall(headerWebService.getStudio(StudioId + ""), WebServiceConstants.StudioDetail);


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

        if (prefHelper.isLanguagePersian()) {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        } else {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }

        if (getTitleBar() != null) {
            getTitleBar().hideHeartButton();
        }

        llMainframe.setVisibility(View.GONE);

        if (enitity != null) {
        //    serviceHelper.enqueueCall(headerWebService.isFavoriteStudio(prefHelper.getUserAllData().getId(), enitity.getId()), WebServiceConstants.isFavoriteStudio);

            prefHelper.setIsFromStudio(true);

        }

    }

    private void setFeatureList() {

        featuresList = new ArrayList<>();
        featuresList.add(new GymDetailListEnt(getDockActivity().getResources().getString(R.string.gym), enitity.getStudioFeatures()));

        if (enitity.getFitnessClasses().size() > 0) {
            for (int i = 0; i < enitity.getFitnessClasses().size(); i++) {
                if (prefHelper.isLanguagePersian()) {
                    featuresList.add(new GymDetailListEnt(enitity.getFitnessClasses().get(i).getClassNamePer(), enitity.getFitnessClasses().get(i).getFitnessClassFeatures()));
                } else {
                    featuresList.add(new GymDetailListEnt(enitity.getFitnessClasses().get(i).getClassNameEng(), enitity.getFitnessClasses().get(i).getFitnessClassFeatures()));
                }

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

        Picasso.with(getDockActivity())
                .load(enitity.getStudioLogo())
                .placeholder(R.drawable.placeholder3)
                .into(ivProfileImage);

        if (prefHelper.isLanguagePersian()) {
            txtTitle.setText(enitity.getStudioNamePer() + "");
            txtAddress.setText(enitity.getAddressPer() + "");
        } else {
            txtTitle.setText(enitity.getStudioNameEng() + "");
            txtAddress.setText(enitity.getAddressEng() + "");
        }


    }

    private void setgymData() {

        userCollectiongym = new ArrayList<>();

        userCollectiongym.addAll(enitity.getStudioFeatures());

        adaptergym.clearList();
        adaptergym.addAll(userCollectiongym);
        gvGym.setAdapter(adaptergym);

    }


    private void setRecyclerViewData() {

        userCollectionsGallery = new ArrayList<>();
        userCollectionsGallery.addAll(enitity.getStudioImages());

        if (userCollectionsGallery.size() > 0) {
            llGallery.setVisibility(View.VISIBLE);


            rvGallery.BindRecyclerView(new GymGalleryItemBinder(this), userCollectionsGallery,
                    new LinearLayoutManager(getDockActivity(), LinearLayoutManager.HORIZONTAL, false)
                    , new DefaultItemAnimator());
        } else {
            llGallery.setVisibility(View.GONE);
        }

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
                    UIHelper.showShortToastInCenter(getDockActivity(), getDockActivity().getResources().getString(R.string.no_class_found));
                break;
            case R.id.btn_subscribe:
                getDockActivity().popBackStackTillEntry(0);
                MainFragment mainFragment = MainFragment.newInstance();
                mainFragment.setStartWithTab(AppConstants.TAB_SUBSCRIBE);
                getDockActivity().replaceDockableFragment(mainFragment);
                break;
         /*   case R.id.btn_group:
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
                break;*/
        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {

            case WebServiceConstants.isFavoriteStudio:

                if (getTitleBar() != null) {
                    getTitleBar().showHeartButton(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            serviceHelper.enqueueCall(headerWebService.addFavoriteStudio(prefHelper.getUserAllData().getId(), enitity.getId(), isChecked ? false : true), WebServiceConstants.addFavoriteStudio);

                        }
                    });
                    if (((IsFavoriteEnt) result).isFavorite()) {
                        getTitleBar().getHearCheckBox(R.id.cb_heart).setChecked(true);
                    } else {
                        getTitleBar().getHearCheckBox(R.id.cb_heart).setChecked(false);
                    }
                }
                break;

            case WebServiceConstants.addFavoriteStudio:
                //   UIHelper.showShortToastInCenter(getDockActivity(), message);

                break;

            case WebServiceConstants.StudioDetail:

                enitity = (Studio) result;

                serviceHelper.enqueueCall(headerWebService.isFavoriteStudio(prefHelper.getUserAllData().getId(), enitity.getId()), WebServiceConstants.isFavoriteStudio);

                llMainframe.setVisibility(View.VISIBLE);
                setStudioData();

                if (isAdded()) {
                    setFeatureList();
                }

                //setyogaData();
                setgymData();
                setRecyclerViewData();


                break;


        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading("");

    }

    @Override
    public void onResume() {
        super.onResume();
        if (getTitleBar() != null) {
            getTitleBar().hideButtons();
            getTitleBar().showBackButton();
            getTitleBar().setSubHeading("");
            getTitleBar().showHeartButton(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    serviceHelper.enqueueCall(headerWebService.addFavoriteStudio(prefHelper.getUserAllData().getId(), enitity.getId(), isChecked ? false : true), WebServiceConstants.addFavoriteStudio);

                }
            });
        }
    }
}