package com.ingic.tanfit.fragments;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.ProfileEnt;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.helpers.DialogHelper;
import com.ingic.tanfit.interfaces.RecyclerViewItemListener;
import com.ingic.tanfit.ui.binders.ProfileItemBinder;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.CustomRecyclerView;
import com.ingic.tanfit.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by saeedhyder on 11/25/2017.
 */
public class MyProfileFragment extends BaseFragment implements RecyclerViewItemListener {

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.btnLogout)
    ImageView btnLogout;
    @BindView(R.id.txt_name)
    AnyTextView txtName;
    @BindView(R.id.txt_email)
    AnyTextView txtEmail;
    @BindView(R.id.rv_gyms)
    CustomRecyclerView rvGyms;
    @BindView(R.id.txt_bookingHistory)
    AnyTextView txtBookingHistory;
    @BindView(R.id.txt_manageSubscription)
    AnyTextView txtManageSubscription;
    @BindView(R.id.txt_MyFavorites)
    AnyTextView txtMyFavorites;
    @BindView(R.id.txt_changePassword)
    AnyTextView txtChangePassword;
    @BindView(R.id.txt_settings)
    AnyTextView txtSettings;
    @BindView(R.id.txt_aboutUs)
    AnyTextView txtAboutUs;
    @BindView(R.id.txt_contactUs)
    AnyTextView txtContactUs;
    Unbinder unbinder;

    private ArrayList<ProfileEnt> userCollections;
    ;

    public static MyProfileFragment newInstance() {
        Bundle args = new Bundle();

        MyProfileFragment fragment = new MyProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myprofile, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setProfileData();

    }


    private void setProfileData() {

        userCollections = new ArrayList<>();
        userCollections.add(new ProfileEnt("sdfsdfds"));
        userCollections.add(new ProfileEnt("sdfsdfds"));
        userCollections.add(new ProfileEnt("sdfsdfds"));


        rvGyms.addItemDecoration(new OverlapDecoration());
        rvGyms.setLayoutManager(new LinearLayoutManager(getDockActivity()));


        rvGyms.BindRecyclerView(new ProfileItemBinder(this), userCollections,
                new LinearLayoutManager(getDockActivity(), LinearLayoutManager.HORIZONTAL, false)
                , new DefaultItemAnimator());

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnBack, R.id.btnLogout, R.id.txt_bookingHistory, R.id.txt_manageSubscription, R.id.txt_MyFavorites, R.id.txt_changePassword, R.id.txt_settings, R.id.txt_aboutUs, R.id.txt_contactUs})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                getMainActivity().popFragment();
                break;
            case R.id.btnLogout:
                final DialogHelper dialogHelper = new DialogHelper(getDockActivity());
                dialogHelper.initlogout(R.layout.logout_dialog, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        prefHelper.setLoginStatus(false);
                        getDockActivity().replaceDockableFragment(LoginFragment.newInstance(), "LoginFragment");
                        dialogHelper.hideDialog();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogHelper.hideDialog();
                    }
                });
                dialogHelper.showDialog();

                break;
            case R.id.txt_bookingHistory:
                getDockActivity().replaceDockableFragment(BookingHistoryTabLayFragment.newInstance(), "BookingHistoryTabLayFragment");
                break;
            case R.id.txt_manageSubscription:
                getDockActivity().replaceDockableFragment(MySubscriptionFragment.newInstance(), "MySubscriptionFragment");
                break;
            case R.id.txt_MyFavorites:
                getDockActivity().replaceDockableFragment(FavoriteFragment.newInstance(), "FavoriteFragment");
                break;
            case R.id.txt_changePassword:
                getDockActivity().replaceDockableFragment(ChangePasswordFragment.newInstance(), "ChangePasswordFragment");
                break;
            case R.id.txt_settings:
                getDockActivity().replaceDockableFragment(SettingFragment.newInstance(), "SettingFragment");
                break;
            case R.id.txt_aboutUs:
                getDockActivity().replaceDockableFragment(AboutUsFragment.newInstance(), "AboutUsFragment");
                break;
            case R.id.txt_contactUs:
                getDockActivity().replaceDockableFragment(ContactUsFragment.newInstance(), "ContactUsFragment");
                break;
        }
    }

    @Override
    public void onRecyclerItemClicked(Object Ent, int position) {


    }

    public class OverlapDecoration extends RecyclerView.ItemDecoration {

        private final int vertOverlap = -90;

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            final int itemPosition = parent.getChildAdapterPosition(view);
            if (itemPosition == 0) {
                return;
            }
            outRect.set(vertOverlap, 0, 0, 0);


        }
    }
}

