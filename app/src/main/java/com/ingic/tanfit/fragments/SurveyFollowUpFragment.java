package com.ingic.tanfit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.SurveyQuestionsEnt;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.global.WebServiceConstants;
import com.ingic.tanfit.helpers.UIHelper;
import com.ingic.tanfit.interfaces.SurveyChangeListener;
import com.ingic.tanfit.interfaces.SurveyInterface;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by saeedhyder on 6/11/2018.
 */
public class SurveyFollowUpFragment extends BaseFragment implements SurveyChangeListener {
    @BindView(R.id.txt_title)
    AnyTextView txtTitle;
    @BindView(R.id.txt_question)
    AnyTextView txtQuestion;
    @BindView(R.id.btn_yes)
    Button btnYes;
    @BindView(R.id.btn_No)
    Button btnNo;
    Unbinder unbinder;

    SurveyQuestionsEnt entitiy;

    private SurveyInterface surveyInterface;

    public static SurveyFollowUpFragment newInstance() {
        Bundle args = new Bundle();
        SurveyFollowUpFragment fragment = new SurveyFollowUpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setInterfaceRef(SurveyInterface surveyInterface){
        this.surveyInterface=surveyInterface;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_survey_followup, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    public void setData(SurveyQuestionsEnt data){
        this.entitiy=data;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(entitiy!=null){
            txtTitle.setText(entitiy.getHeading());
            txtQuestion.setText(entitiy.getQuestion());
        }

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }


    @OnClick({R.id.btn_yes, R.id.btn_No})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_yes:
                ((CancelSurveyFragment)getParentFragment()).changeToNextQuestion("yes" ,entitiy);
              surveyInterface.btnFollowUpYes();
                break;
            case R.id.btn_No:
                ((CancelSurveyFragment)getParentFragment()).changeToNextQuestion("no",entitiy);
                surveyInterface.btnFollowUpNo();
                break;
        }
    }

    @Override
    public void onSurveyNextFragmentListener() {


    }
}
