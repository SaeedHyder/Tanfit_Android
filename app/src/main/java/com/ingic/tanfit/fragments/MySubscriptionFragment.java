package com.ingic.tanfit.fragments;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.UserAllDataEnt;
import com.ingic.tanfit.entities.UserSubscription;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.global.WebServiceConstants;
import com.ingic.tanfit.helpers.DialogHelper;
import com.ingic.tanfit.helpers.ISO8601TimeStampHelper;
import com.ingic.tanfit.helpers.UIHelper;
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
    @BindView(R.id.ll_cancelSubScription)
    LinearLayout llCancelSubScription;
    @BindView(R.id.mainFrame)
    LinearLayout mainFrame;


    private static String itemPosition = "itemPosition";
    private int positionItem;
    private UserSubscription userSubscription;

    public static MySubscriptionFragment newInstance() {
        Bundle args = new Bundle();

        MySubscriptionFragment fragment = new MySubscriptionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static MySubscriptionFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(itemPosition, position);
        MySubscriptionFragment fragment = new MySubscriptionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            positionItem = getArguments().getInt(itemPosition);
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

        userSubscription = prefHelper.getUserAllData().getUserSubscription().get(positionItem);


        txtSubscriptionPackage.setText(userSubscription.getSubscriptionType() + "");
        if (userSubscription.getIsPaused()) {
            txtDaysLeft.setVisibility(View.VISIBLE);
            txtDaysLeft.setText("You stil have " + userSubscription.getTotalRemainingDays() + " more days left once you resume");
        } else {
            txtDaysLeft.setVisibility(View.GONE);
        }


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
                if (!userSubscription.getIsPaused()) {
                    if (isValidate()) {
                        final DialogHelper dialogHelper = new DialogHelper(getDockActivity());
                        dialogHelper.subcriptionDialoge(R.layout.pause_subscription_dialoge, getString(R.string.pauseSubscription), getString(R.string.are_you_sure_you_want_to_pause_subcription), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // btnPause.setText(R.string.resume);
                                ISO8601TimeStampHelper timeStamp = new ISO8601TimeStampHelper();
                                serviceHelper.enqueueCall(headerWebService.setPauseSubscription(prefHelper.getUserId(), editDays.getText().toString(), timeStamp.getISO8601StringForCurrentDate()), WebServiceConstants.setPauseSubscription);
                                dialogHelper.hideDialog();

                            }
                        }, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogHelper.hideDialog();
                            }
                        });
                        dialogHelper.showDialog();
                    }
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.paused_subscrition));
                }

                break;
            case R.id.btn_visit_website:
                final DialogHelper cancel = new DialogHelper(getDockActivity());
                cancel.subcriptionDialoge(R.layout.pause_subscription_dialoge, getString(R.string.cancel_subscription), getString(R.string.are_you_sure_you_want_to_cancel_subcription), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        serviceHelper.enqueueCall(headerWebService.cancelUserSubscription(prefHelper.getUserAllData().getId() + "", prefHelper.getUserAllData().getUserSubscription().get(prefHelper.getUserAllData().getUserSubscription().size() - 1).getId() + ""), WebServiceConstants.cancelSubScription);
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

    private boolean isValidate() {

        if (editDays.getText() == null || (editDays.getText().toString().isEmpty())) {
            if (editDays.requestFocus()) {
                setEditTextFocus(editDays);
            }
            UIHelper.showShortToastInCenter(getDockActivity(),"Enter days field");
            return false;
        } else {
            return true;
        }
    }

    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {

            case WebServiceConstants.setPauseSubscription:

                UserAllDataEnt isPuased = prefHelper.getUserAllData();
                isPuased.getUserSubscription().get(0).setIsPaused(true);
                prefHelper.putUserAllData(isPuased);
                UIHelper.showShortToastInCenter(getDockActivity(), "Subscription paused successfully");
                getDockActivity().replaceDockableFragment(MainFragment.newInstance(), "MainFragment");
                // UIHelper.showShortToastInCenter(getDockActivity(), message);
                //   editDays.setVisibility(View.INVISIBLE);
                break;

            case WebServiceConstants.cancelSubScription:
                UIHelper.showShortToastInCenter(getDockActivity(), "Subscription cancelled successfully");
                getDockActivity().replaceDockableFragment(MainFragment.newInstance(), "MainFragment");
                //   editDays.setVisibility(View.INVISIBLE);
                break;


        }
    }
}
