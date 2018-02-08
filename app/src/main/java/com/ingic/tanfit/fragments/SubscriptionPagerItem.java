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
import com.ingic.tanfit.helpers.ISO8601TimeStampHelper;
import com.ingic.tanfit.helpers.UIHelper;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.TitleBar;

import java.util.ArrayList;

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

        if((entity.getId() %2)==0){
            header.setBackground(getResources().getDrawable(R.drawable.golden));

        } else{
            header.setBackground(getResources().getDrawable(R.drawable.brown));
        }

      //  UIHelper.showShortToastInCenter(getDockActivity(), entity.getSubscriptionType());
        txtTitle.setText(entity.getTitle());
       // txtTitle1.setText("");
        txtDescription.setText(entity.getSubscriptionType()+" Classes" + System.getProperty("line.separator") + "PER "+entity.getNoOfSubscriptionDays()+" Days");
        if(entity.getSubscriptionType().equals("Limited")){
            txtDescription2.setText("*Total no of subscribed classes are "+entity.getTotalNoOfFitnessClasses());
        }
        else{
            txtDescription2.setText("*Maximum "+entity.getNoOfClassesADay()+" classes per day");

        }

        txtPrice.setText("IRR "+entity.getPriceInIranianRiyal());
    }

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



    @OnClick(R.id.btn_SubcribeNow)
    public void onViewClicked() {
        ISO8601TimeStampHelper timeStamp = new ISO8601TimeStampHelper();
        serviceHelper.enqueueCall(headerWebService.addUserSubscription(prefHelper.getUserId(), String.valueOf(entity.getId()),timeStamp.getISO8601StringForCurrentDate()), WebServiceConstants.addUserSubscription);

    }

    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {

            case WebServiceConstants.addUserSubscription:
                UIHelper.showShortToastInCenter(getDockActivity(),"You have successfully subscribed");
                serviceHelper.enqueueCall(headerWebService.getSubsccriptionData(prefHelper.getUser().getUserId() + ""), WebServiceConstants.getSubscription);
                break;

            case WebServiceConstants.getSubscription:
                UserAllDataEnt userAppData = prefHelper.getUserAllData();

                ArrayList<UserSubscription> userSubscriptions = (ArrayList<UserSubscription>) result;

                userAppData.setUserSubscription(userSubscriptions);

                prefHelper.putUserAllData(userAppData);


                break;

        }
    }
}
