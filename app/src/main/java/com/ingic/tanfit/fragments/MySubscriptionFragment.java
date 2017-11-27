package com.ingic.tanfit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ingic.tanfit.R;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.helpers.DialogHelper;
import com.ingic.tanfit.ui.views.AnyEditTextView;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by saeedhyder on 11/22/2017.
 */
public class MySubscriptionFragment extends BaseFragment {

    @BindView(R.id.txt_subscription_package)
    AnyTextView txtSubscriptionPackage;
    @BindView(R.id.edit_days)
    AnyEditTextView editDays;
    @BindView(R.id.btn_visit_website)
    Button btnCancel;
    @BindView(R.id.txt_days_left)
    AnyTextView txtDaysLeft;
    Unbinder unbinder;
    @BindView(R.id.btn_pause)
    Button btnPause;

    public static MySubscriptionFragment newInstance() {
        Bundle args = new Bundle();

        MySubscriptionFragment fragment = new MySubscriptionFragment();
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
        View view = inflater.inflate(R.layout.fragment_my_subscription, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.my_subscriptions));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.btn_pause, R.id.btn_visit_website})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_pause:
                final DialogHelper dialogHelper = new DialogHelper(getDockActivity());
                dialogHelper.subcriptionDialoge(R.layout.pause_subscription_dialoge, getString(R.string.pauseSubscription), getString(R.string.are_you_sure_you_want_to_pause_subcription), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnPause.setText(R.string.resume);
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
            case R.id.btn_visit_website:
                final DialogHelper cancel = new DialogHelper(getDockActivity());
                cancel.subcriptionDialoge(R.layout.pause_subscription_dialoge, getString(R.string.cancel_subscription), getString(R.string.are_you_sure_you_want_to_cancel_subcription), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        cancel.hideDialog();

                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cancel.hideDialog();
                    }
                });
                cancel.showDialog();

                break;
        }
    }
}
