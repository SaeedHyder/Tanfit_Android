package com.ingic.tanfit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.CancelBody;
import com.ingic.tanfit.entities.UserEnt;
import com.ingic.tanfit.entities.UserSurveyAnswerLists;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.global.AppConstants;
import com.ingic.tanfit.global.WebServiceConstants;
import com.ingic.tanfit.helpers.TokenUpdater;
import com.ingic.tanfit.helpers.UIHelper;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.TitleBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by saeedhyder on 6/11/2018.
 */
public class CancelConformationFragment extends BaseFragment {
    @BindView(R.id.txt_title)
    AnyTextView txtTitle;
    @BindView(R.id.txt_question)
    AnyTextView txtQuestion;
    @BindView(R.id.btn_yes)
    Button btnYes;
    @BindView(R.id.btn_pause)
    AnyTextView btnPause;
    Unbinder unbinder;
    private boolean isSubscribed=false;
    private int itemPosition;

    public static CancelConformationFragment newInstance() {
        Bundle args = new Bundle();

        CancelConformationFragment fragment = new CancelConformationFragment();
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
        View view = inflater.inflate(R.layout.fragment_cancel_conformation, container, false);
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
        titleBar.hideTitleBar();
    }


    @OnClick({R.id.btn_yes, R.id.btn_pause})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_yes:

               ArrayList<UserSurveyAnswerLists> result=((CancelSurveyFragment) getParentFragment()).allAnswers();
                CancelBody body = new CancelBody();
                body.setUserSurveyAnswerLists(result);
              //  String jsonAllAnswersString=new Gson().toJson(body);
                serviceHelper.enqueueCall(headerWebService.surveyAnswers(body), WebServiceConstants.AllAnswers);
                break;
            case R.id.btn_pause:
                if (prefHelper.getUserAllData().getUserSubscription().size() > 0) {

                    for (int i=0;i<prefHelper.getUserAllData().getUserSubscription().size();i++) {
                        if (prefHelper.getUserAllData().getUserSubscription().get(i).getUserSubscriptionStatusId() == 1) {
                            isSubscribed=true;
                            itemPosition=i;
                        }
                    }

                    if (isSubscribed) {
                        getMainActivity().popFragment();
                        getDockActivity().replaceDockableFragment(MySubscriptionFragment.newInstance(itemPosition), "MySubscriptionFragment");
                    } else {
                        MainFragment mainFragment = MainFragment.newInstance();
                        mainFragment.setStartWithTab(AppConstants.TAB_SUBSCRIBE);
                        getDockActivity().replaceDockableFragment(mainFragment);
                        break;
                    }

                } else {
                    MainFragment mainFragment = MainFragment.newInstance();
                    mainFragment.setStartWithTab(AppConstants.TAB_SUBSCRIBE);
                    getDockActivity().replaceDockableFragment(mainFragment);
//                    getDockActivity().replaceDockableFragment(MainFragment.newInstance("SubscriptionScreen"), "MainFragment");
                }
                break;
        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {

            case WebServiceConstants.cancelUserClass:

                UIHelper.showShortToastInCenter(getDockActivity(), message);
                getMainActivity().popFragment();
                getDockActivity().replaceDockableFragment(MainFragment.newInstance(), "MainFragment");
             //   deleteEvent(EventIdGlobal);
               // btnBookNow.setVisibility(View.VISIBLE);
              //  btnCancelBooking.setVisibility(View.GONE);
                break;

            case WebServiceConstants.AllAnswers:

                  serviceHelper.enqueueCall(headerWebService.cancelUserClass(prefHelper.getUserAllData().getId(),((CancelSurveyFragment) getParentFragment()).getClassId()), WebServiceConstants.cancelUserClass);

                break;

        }}



}
