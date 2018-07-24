package com.ingic.tanfit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.GetSubscriptionEnt;
import com.ingic.tanfit.entities.UserAllDataEnt;
import com.ingic.tanfit.entities.UserSubscription;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.global.WebServiceConstants;
import com.ingic.tanfit.helpers.DialogHelper;
import com.ingic.tanfit.helpers.ISO8601TimeStampHelper;
import com.ingic.tanfit.helpers.UIHelper;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.TitleBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by saeedhyder on 11/23/2017.
 */
public class SubscriptionPagerItem extends BaseFragment {
    @BindView(R.id.txt_title)
    AnyTextView txtTitle;
    @BindView(R.id.txt_title1)
    AnyTextView txtTitle1;
    @BindView(R.id.header)
    LinearLayout header;
    @BindView(R.id.txt_description)
    AnyTextView txtDescription;
    @BindView(R.id.txt_description2)
    AnyTextView txtDescription2;
    @BindView(R.id.txtPrice)
    AnyTextView txtPrice;
    @BindView(R.id.btn_SubcribeNow)
    Button btnSubcribeNow;
    @BindView(R.id.bottom)
    RelativeLayout bottom;
    Unbinder unbinder;

    View mainFrame;

    GetSubscriptionEnt entity;
    int itemPosition;

    public static SubscriptionPagerItem newInstance() {
        Bundle args = new Bundle();

        SubscriptionPagerItem fragment = new SubscriptionPagerItem();
        fragment.setArguments(args);
        return fragment;
    }

    public void setContent(GetSubscriptionEnt item, int position) {
        this.entity = item;
        this.itemPosition = position;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.row_item_subscription, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainFrame=view;
        mainFrame.setVisibility(View.GONE);

        if((entity.getId() %2)==0){
            header.setBackground(getDockActivity().getResources().getDrawable(R.drawable.golden));

        } else{
            header.setBackground(getDockActivity().getResources().getDrawable(R.drawable.brown));
        }

        serviceHelper.enqueueCall(headerWebService.getSubsccriptionData(prefHelper.getUser().getUserId() + ""), WebServiceConstants.getSubscription);

      //  UIHelper.showShortToastInCenter(getDockActivity(), entity.getSubscriptionType());
        if(prefHelper.isLanguagePersian()){
            txtTitle.setText(entity.getTitlePr());
            txtDescription.setText(entity.getSubscriptionTypePr()+" "+getDockActivity().getResources().getString(R.string.classes) + " "+System.getProperty("line.separator") + " "+getDockActivity().getResources().getString(R.string.per)+entity.getNoOfSubscriptionDaysPr()+" "+getDockActivity().getResources().getString(R.string.days));

            if(entity.getSubscriptionTypePr().equals(getDockActivity().getResources().getString(R.string.limited))){
                txtDescription2.setText(getDockActivity().getResources().getString(R.string.total_no_of_subscribes_classes_are)+" "+entity.getTotalNoOfFitnessClassesPr());
            }
            else{
                txtDescription2.setText(getDockActivity().getResources().getString(R.string.maximum)+" "+entity.getNoOfSubscriptionDaysPr()+" "+getDockActivity().getResources().getString(R.string.classes_per_day));

            }

            txtPrice.setText(getDockActivity().getResources().getString(R.string.irr)+" "+entity.getPriceInIranianRiyalPr());
        }
        else{
            txtTitle.setText(entity.getTitleEn());
            txtDescription.setText(entity.getSubscriptionType()+" "+getDockActivity().getResources().getString(R.string.classes) +" "+System.getProperty("line.separator") +" "+ getDockActivity().getResources().getString(R.string.per)+" "+entity.getNoOfSubscriptionDays()+" "+getDockActivity().getResources().getString(R.string.days));

            if(entity.getSubscriptionType().equals(getDockActivity().getResources().getString(R.string.limited))){
                txtDescription2.setText(getDockActivity().getResources().getString(R.string.total_no_of_subscribes_classes_are)+" "+entity.getTotalNoOfFitnessClasses());
            }
            else{
                txtDescription2.setText(getDockActivity().getResources().getString(R.string.maximum)+" "+entity.getNoOfClassesADay()+" "+getDockActivity().getResources().getString(R.string.classes_per_day));

            }

            txtPrice.setText(getDockActivity().getResources().getString(R.string.irr)+" "+entity.getPriceInIranianRiyal());
        }

       // txtTitle1.setText("");




    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading("");
    }


    @OnClick(R.id.btn_SubcribeNow)
    public void onViewClicked() {
        if(btnSubcribeNow.getText().toString().equals(getDockActivity().getResources().getString(R.string.subscribe_now))) {
            ISO8601TimeStampHelper timeStamp = new ISO8601TimeStampHelper();
            serviceHelper.enqueueCall(headerWebService.addUserSubscription(prefHelper.getUserId(), String.valueOf(entity.getId()), timeStamp.getISO8601StringForCurrentDate()), WebServiceConstants.addUserSubscription);
        }else {
            final DialogHelper cancel = new DialogHelper(getDockActivity());
            cancel.subcriptionDialoge(R.layout.pause_subscription_dialoge, getDockActivity().getResources().getString(R.string.cancel_subscription), getDockActivity().getResources().getString(R.string.are_you_sure_you_want_to_cancel_subcription), new View.OnClickListener() {
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
        }
    }

    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {

            case WebServiceConstants.addUserSubscription:
                UIHelper.showShortToastInCenter(getDockActivity(),getDockActivity().getResources().getString(R.string.you_have_subscribed_successfully));
                serviceHelper.enqueueCall(headerWebService.getSubsccriptionData(prefHelper.getUser().getUserId() + ""), WebServiceConstants.getSubscription);
                break;

            case WebServiceConstants.getSubscription:
                UserAllDataEnt userAppData = prefHelper.getUserAllData();

                mainFrame.setVisibility(View.VISIBLE);

                ArrayList<UserSubscription> userSubscriptions = (ArrayList<UserSubscription>) result;

               if(userSubscriptions.size()>0){
                    for(UserSubscription item: userSubscriptions){
                        if(item.getSubscriptionId()==entity.getId() && item.getUserSubscriptionStatusId()==1){
                            btnSubcribeNow.setText(R.string.unsubscribe_now);
                        } else{
                            btnSubcribeNow.setText(R.string.subscribe_now);
                        }
                    }
                }
                userAppData.setUserSubscription(userSubscriptions);
                prefHelper.putUserAllData(userAppData);

                break;

            case WebServiceConstants.cancelSubScription:
                UIHelper.showShortToastInCenter(getDockActivity(), getDockActivity().getResources().getString(R.string.subscription_cancelled_successfully));
                serviceHelper.enqueueCall(headerWebService.getSubsccriptionData(prefHelper.getUser().getUserId() + ""), WebServiceConstants.getSubscription);

                //getDockActivity().replaceDockableFragment(MainFragment.newInstance(), "MainFragment");
                //   editDays.setVisibility(View.INVISIBLE);
                break;

        }
    }
}
