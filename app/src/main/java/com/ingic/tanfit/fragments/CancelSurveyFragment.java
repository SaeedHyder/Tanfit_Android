package com.ingic.tanfit.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.SurveyQuestionsEnt;
import com.ingic.tanfit.entities.UserSurveyAnswerLists;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.global.WebServiceConstants;
import com.ingic.tanfit.interfaces.SurveyChangeListener;
import com.ingic.tanfit.interfaces.SurveyInterface;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.TitleBar;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.ingic.tanfit.activities.DockActivity.KEY_FRAG_FIRST;
import static com.ingic.tanfit.global.WebServiceConstants.SurveyQuestions;

/**
 * Created by saeedhyder on 6/9/2018.
 */
public class CancelSurveyFragment extends BaseFragment implements SurveyInterface {
    @BindView(R.id.customProgress)
    ProgressBar customProgress;
    @BindView(R.id.txt_progress)
    AnyTextView txtProgress;
    @BindView(R.id.ll_fragments)
    FrameLayout llFragments;
    @BindView(R.id.btn_next)
    Button btnNext;
    Unbinder unbinder;
    @BindView(R.id.mainFrameLayout)
    LinearLayout mainFrameLayout;
    @BindView(R.id.ll_progressbar)
    LinearLayout llProgressbar;
    private long percentageRatio;
    private long percentage;
    private int startPosition = 0;
    private ArrayList<SurveyQuestionsEnt> entity;
    private HashMap<String, String> result = new HashMap<>();
    private ArrayList<UserSurveyAnswerLists> surveyAnswers=new ArrayList<>();
    SurveyChangeListener listener;

    private static String classId = "classId";

    public static CancelSurveyFragment newInstance(String cancelClassId) {
        Bundle args = new Bundle();
        args.putString(classId, cancelClassId);
        CancelSurveyFragment fragment = new CancelSurveyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            classId = getArguments().getString(classId);
        }

    }

    public String getClassId() {
        return classId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cancel_survey, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainFrameLayout.setVisibility(View.GONE);
        serviceHelper.enqueueCall(headerWebService.surveyQusetions(), SurveyQuestions);


    }


    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {

            case WebServiceConstants.SurveyQuestions:

                mainFrameLayout.setVisibility(View.VISIBLE);

                entity = (ArrayList<SurveyQuestionsEnt>) result;

                percentageRatio = Math.round(100.0 * 1 / (entity.size()));
                percentage = 0;

                customProgress.setProgress((int) 0);
                txtProgress.setText(0 + "% Completed");


                if (startPosition < entity.size()) {

                    if (entity.get(startPosition).getQuestionType() == 1) {
                        SurveyTextBoxFragment surveyTextBoxFragment = new SurveyTextBoxFragment();
                        surveyTextBoxFragment.setData(entity.get(startPosition));
                        startPosition = startPosition + 1;
                        btnNext.setVisibility(View.VISIBLE);
                        listener = surveyTextBoxFragment;
                        replaceFragment(surveyTextBoxFragment);

                    } else if (entity.get(startPosition).getQuestionType() == 2) {
                        SurveyRadioBtnFragment surveyRadioBtnFragment = new SurveyRadioBtnFragment();
                        surveyRadioBtnFragment.setData(entity.get(startPosition));
                        startPosition = startPosition + 1;
                        btnNext.setVisibility(View.VISIBLE);
                        listener = surveyRadioBtnFragment;
                        replaceFragment(surveyRadioBtnFragment);

                    } else if (entity.get(startPosition).getQuestionType() == 3) {
                        SurveyFollowUpFragment surveyFollowUpFragment = new SurveyFollowUpFragment();
                        surveyFollowUpFragment.setData(entity.get(startPosition));
                        surveyFollowUpFragment.setInterfaceRef(this);
                        startPosition = startPosition + 1;
                        btnNext.setVisibility(View.GONE);
                        listener = surveyFollowUpFragment;
                        replaceFragment(surveyFollowUpFragment);

                    }
                }


                break;
        }
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading(getDockActivity().getResources().getString(R.string.cancel_booking));
        titleBar.showBackButton();

    }

    @OnClick(R.id.btn_next)
    public void onViewClicked() {

        if (startPosition <= entity.size()) {
            if (listener != null) {
                listener.onSurveyNextFragmentListener();
            }
        }


    }

    public ArrayList<UserSurveyAnswerLists> allAnswers(){
        return surveyAnswers;
    }

    public void changeToNextQuestion(String data, SurveyQuestionsEnt entitiy) {

        percentage = (int) percentage + (int) percentageRatio;
        customProgress.setProgress((int) percentage);
        txtProgress.setText(percentage + "% Completed");

        result.put(entitiy.getId() + "", data);
        surveyAnswers.add(new UserSurveyAnswerLists(prefHelper.getUserAllData().getId(),classId,entitiy.getId()+"",data));

        if (startPosition == entity.size()) {
            llProgressbar.setVisibility(View.GONE);
            btnNext.setVisibility(View.GONE);
            replaceFragment(CancelConformationFragment.newInstance());
            //getDockActivity().replaceDockableFragment(CancelConformationFragment.newInstance(), "CancelConformationFragment");

        } else if (entity.get(startPosition).getQuestionType() == 1) {
            SurveyTextBoxFragment surveyTextBoxFragment = new SurveyTextBoxFragment();
            surveyTextBoxFragment.setData(entity.get(startPosition));
            startPosition = startPosition + 1;
            btnNext.setVisibility(View.VISIBLE);
            listener = surveyTextBoxFragment;
            replaceFragment(surveyTextBoxFragment);

        } else if (entity.get(startPosition).getQuestionType() == 2) {
            SurveyRadioBtnFragment surveyRadioBtnFragment = new SurveyRadioBtnFragment();
            surveyRadioBtnFragment.setData(entity.get(startPosition));
            startPosition = startPosition + 1;
            listener = surveyRadioBtnFragment;
            btnNext.setVisibility(View.VISIBLE);
            replaceFragment(surveyRadioBtnFragment);

        } else if (entity.get(startPosition).getQuestionType() == 3) {
            SurveyFollowUpFragment surveyFollowUpFragment = new SurveyFollowUpFragment();
            surveyFollowUpFragment.setData(entity.get(startPosition));
            surveyFollowUpFragment.setInterfaceRef(this);
            startPosition = startPosition + 1;
            listener = surveyFollowUpFragment;
            btnNext.setVisibility(View.GONE);
            replaceFragment(surveyFollowUpFragment);
        }


    }

    public void replaceFragment(BaseFragment frag) {

        FragmentTransaction transaction = getChildFragmentManager()
                .beginTransaction();

        transaction.replace(R.id.ll_fragments, frag);
        transaction
                .addToBackStack(
                        getChildFragmentManager().getBackStackEntryCount() == 0 ? KEY_FRAG_FIRST
                                : null).commit();


    }

    @Override
    public void btnFollowUpYes() {


        if (startPosition <= entity.size()) {
            if (listener != null) {
                listener.onSurveyNextFragmentListener();
            }
        }

    }

    @Override
    public void btnFollowUpNo() {

        if (startPosition <= entity.size()) {
            if (listener != null) {
                listener.onSurveyNextFragmentListener();
            }
        }
    }

}
