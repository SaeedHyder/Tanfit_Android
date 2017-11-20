package com.ingic.tanfit.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ingic.tanfit.R;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.helpers.UIHelper;
import com.ingic.tanfit.ui.views.AutoCompleteLocation;
import com.ingic.tanfit.ui.views.TitleBar;
import com.google.android.gms.location.places.Place;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class HomeFragment extends BaseFragment implements AutoCompleteLocation.AutoCompleteLocationListener {


    @BindView(R.id.ac_location)
    AutoCompleteLocation ac_location;
    Unbinder unbinder;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ac_location.setAutoCompleteTextListener(this);
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showMenuButton();
        titleBar.setSubHeading("Home");
        titleBar.showNotificationButton(0);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onTextClear() {
    }

    @Override
    public void onItemSelected(Place selectedPlace) {
        UIHelper.showShortToastInCenter(getDockActivity(),selectedPlace.getLatLng().latitude + "  :  " + selectedPlace.getLatLng().longitude);
    }


}

