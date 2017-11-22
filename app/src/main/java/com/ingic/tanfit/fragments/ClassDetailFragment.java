package com.ingic.tanfit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.SpecialFeatureEnt;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.global.AppConstants;
import com.ingic.tanfit.helpers.UIHelper;
import com.ingic.tanfit.ui.adapters.ArrayListAdapter;
import com.ingic.tanfit.ui.binders.speacialFeatureItemBinder;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.ExpandableGridView;
import com.ingic.tanfit.ui.views.TitleBar;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by saeedhyder on 11/21/2017.
 */
public class ClassDetailFragment extends BaseFragment {
    @BindView(R.id.iv_profileImage)
    CircleImageView ivProfileImage;
    @BindView(R.id.txt_title)
    AnyTextView txtTitle;
    @BindView(R.id.txt_address)
    AnyTextView txtAddress;
    @BindView(R.id.txt_time)
    AnyTextView txtTime;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.txt_duration)
    AnyTextView txtDuration;
    @BindView(R.id.txt_date)
    AnyTextView txtDate;
    @BindView(R.id.btn_book_now)
    Button btnBookNow;
    @BindView(R.id.gv_special_features)
    ExpandableGridView gvSpecialFeatures;
    @BindView(R.id.btn_viewStudioPage)
    Button btnViewStudioPage;
    Unbinder unbinder;
    @BindView(R.id.gv_amelities)
    ExpandableGridView gvAmelities;
    @BindView(R.id.map)
    ImageView map;
    ImageLoader imageLoader;

    private ArrayListAdapter<SpecialFeatureEnt> adapter;
    private ArrayList<SpecialFeatureEnt> userCollectionSpecialities = new ArrayList<>();

    private ArrayListAdapter<SpecialFeatureEnt> adapterAmenities;
    private ArrayList<SpecialFeatureEnt> userCollectionAmenities = new ArrayList<>();

    public static ClassDetailFragment newInstance() {
        Bundle args = new Bundle();

        ClassDetailFragment fragment = new ClassDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ArrayListAdapter<SpecialFeatureEnt>(getDockActivity(), new speacialFeatureItemBinder(getDockActivity(), prefHelper));
        adapterAmenities = new ArrayListAdapter<SpecialFeatureEnt>(getDockActivity(), new speacialFeatureItemBinder(getDockActivity(), prefHelper));
        imageLoader=ImageLoader.getInstance();
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setSpecialFeatures();
        setAmenitiesData();
        setMapLocation();

    }

    private void setMapLocation() {

        String lat="25.212706";
        String lng="55.283592";

        String mapURL="https://maps.googleapis.com/maps/api/staticmap?center="+lat+","+lng+"&zoom=14&" +
                "&scale=2&size=500x300&maptype=roadmap&markers=color:blue|"+lat+","+lng+"&key=AIzaSyCDylplefNyWlLDoBL_n2VFjwlMWvq3sBg";
        imageLoader.displayImage(mapURL,map);
    }

    private void setAmenitiesData() {

        userCollectionAmenities = new ArrayList<>();

        userCollectionAmenities.add(new SpecialFeatureEnt(AppConstants.DRAWABLE_PATH + R.drawable.changing_room, getDockActivity().getResources().getString(R.string.changing_room)));
        userCollectionAmenities.add(new SpecialFeatureEnt(AppConstants.DRAWABLE_PATH + R.drawable.treadmil, getDockActivity().getResources().getString(R.string.treadmil)));
        userCollectionAmenities.add(new SpecialFeatureEnt(AppConstants.DRAWABLE_PATH + R.drawable.music, getDockActivity().getResources().getString(R.string.music)));
        userCollectionAmenities.add(new SpecialFeatureEnt(AppConstants.DRAWABLE_PATH + R.drawable.trainer, getDockActivity().getResources().getString(R.string.trainer)));


        adapterAmenities.clearList();
        adapterAmenities.addAll(userCollectionAmenities);
        gvAmelities.setAdapter(adapter);
        adapterAmenities.notifyDataSetChanged();

    }

    private void setSpecialFeatures() {

        userCollectionSpecialities = new ArrayList<>();

        userCollectionSpecialities.add(new SpecialFeatureEnt(AppConstants.DRAWABLE_PATH + R.drawable.changing_room, getDockActivity().getResources().getString(R.string.changing_room)));
        userCollectionSpecialities.add(new SpecialFeatureEnt(AppConstants.DRAWABLE_PATH + R.drawable.treadmil, getDockActivity().getResources().getString(R.string.treadmil)));
        userCollectionSpecialities.add(new SpecialFeatureEnt(AppConstants.DRAWABLE_PATH + R.drawable.music, getDockActivity().getResources().getString(R.string.music)));
        userCollectionSpecialities.add(new SpecialFeatureEnt(AppConstants.DRAWABLE_PATH + R.drawable.trainer, getDockActivity().getResources().getString(R.string.trainer)));


        adapter.clearList();
        adapter.addAll(userCollectionSpecialities);
        gvSpecialFeatures.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading("");
        titleBar.showHeartButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.showShortToastInCenter(getDockActivity(), "will be implemented in beta");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_book_now, R.id.btn_viewStudioPage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_book_now:
                break;
            case R.id.btn_viewStudioPage:
                break;
        }
    }
}
