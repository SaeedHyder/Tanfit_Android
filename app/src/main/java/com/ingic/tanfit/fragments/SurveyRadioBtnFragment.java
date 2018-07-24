package com.ingic.tanfit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.SurveyQuestionsEnt;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.global.WebServiceConstants;
import com.ingic.tanfit.helpers.UIHelper;
import com.ingic.tanfit.interfaces.SurveyChangeListener;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.TitleBar;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by saeedhyder on 6/11/2018.
 */
public class SurveyRadioBtnFragment extends BaseFragment implements SurveyChangeListener {
    @BindView(R.id.txt_title)
    AnyTextView txtTitle;
    @BindView(R.id.txt_question)
    AnyTextView txtQuestion;
    Unbinder unbinder;
    @BindView(R.id.rb_0)
    RadioButton rb0;
    @BindView(R.id.rb_1)
    RadioButton rb1;
    @BindView(R.id.rb_2)
    RadioButton rb2;
    @BindView(R.id.rb_3)
    RadioButton rb3;
    @BindView(R.id.rb_4)
    RadioButton rb4;
    @BindView(R.id.rb_5)
    RadioButton rb5;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    String value;

    SurveyQuestionsEnt entitiy;

    public static SurveyRadioBtnFragment newInstance() {
        Bundle args = new Bundle();

        SurveyRadioBtnFragment fragment = new SurveyRadioBtnFragment();
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
        View view = inflater.inflate(R.layout.fragment_survey_radiobtn, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (entitiy != null) {
            txtTitle.setText(entitiy.getHeading());
            txtQuestion.setText(entitiy.getQuestion());
        }
        radioBtnOnClick();

    }

    public void setData(SurveyQuestionsEnt data) {
        this.entitiy = data;
    }

    public boolean isValidate() {
        if (rb0.isChecked()) {
            value="0";
            return true;
        } else if (rb1.isChecked()) {
            value="1";
            return true;
        } else if (rb2.isChecked()) {
            value="2";
            return true;
        } else if (rb3.isChecked()) {
            value="3";
            return true;
        } else if (rb4.isChecked()) {
            value="4";
            return true;
        } else if (rb5.isChecked()) {
            value="5";
            return true;
        } else {
            return false;
        }
    }


    private void radioBtnOnClick() {


        rb0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb0.setTextColor(getDockActivity().getResources().getColor(R.color.white));
                rb1.setTextColor(getDockActivity().getResources().getColor(R.color.gray_dark));
                rb2.setTextColor(getDockActivity().getResources().getColor(R.color.gray_dark));
                rb3.setTextColor(getDockActivity().getResources().getColor(R.color.gray_dark));
                rb4.setTextColor(getDockActivity().getResources().getColor(R.color.gray_dark));
                rb5.setTextColor(getDockActivity().getResources().getColor(R.color.gray_dark));
            }
        });

        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb0.setTextColor(getDockActivity().getResources().getColor(R.color.gray_dark));
                rb1.setTextColor(getDockActivity().getResources().getColor(R.color.white));
                rb2.setTextColor(getDockActivity().getResources().getColor(R.color.gray_dark));
                rb3.setTextColor(getDockActivity().getResources().getColor(R.color.gray_dark));
                rb4.setTextColor(getDockActivity().getResources().getColor(R.color.gray_dark));
                rb5.setTextColor(getDockActivity().getResources().getColor(R.color.gray_dark));
            }
        });


        rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb0.setTextColor(getDockActivity().getResources().getColor(R.color.gray_dark));
                rb1.setTextColor(getDockActivity().getResources().getColor(R.color.gray_dark));
                rb2.setTextColor(getDockActivity().getResources().getColor(R.color.white));
                rb3.setTextColor(getDockActivity().getResources().getColor(R.color.gray_dark));
                rb4.setTextColor(getDockActivity().getResources().getColor(R.color.gray_dark));
                rb5.setTextColor(getDockActivity().getResources().getColor(R.color.gray_dark));
            }
        });

        rb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb0.setTextColor(getDockActivity().getResources().getColor(R.color.gray_dark));
                rb1.setTextColor(getDockActivity().getResources().getColor(R.color.gray_dark));
                rb2.setTextColor(getDockActivity().getResources().getColor(R.color.gray_dark));
                rb3.setTextColor(getDockActivity().getResources().getColor(R.color.white));
                rb4.setTextColor(getDockActivity().getResources().getColor(R.color.gray_dark));
                rb5.setTextColor(getDockActivity().getResources().getColor(R.color.gray_dark));
            }
        });

        rb4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb0.setTextColor(getDockActivity().getResources().getColor(R.color.gray_dark));
                rb1.setTextColor(getDockActivity().getResources().getColor(R.color.gray_dark));
                rb2.setTextColor(getDockActivity().getResources().getColor(R.color.gray_dark));
                rb3.setTextColor(getDockActivity().getResources().getColor(R.color.gray_dark));
                rb4.setTextColor(getDockActivity().getResources().getColor(R.color.white));
                rb5.setTextColor(getDockActivity().getResources().getColor(R.color.gray_dark));
            }
        });

        rb5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb0.setTextColor(getDockActivity().getResources().getColor(R.color.gray_dark));
                rb1.setTextColor(getDockActivity().getResources().getColor(R.color.gray_dark));
                rb2.setTextColor(getDockActivity().getResources().getColor(R.color.gray_dark));
                rb3.setTextColor(getDockActivity().getResources().getColor(R.color.gray_dark));
                rb4.setTextColor(getDockActivity().getResources().getColor(R.color.gray_dark));
                rb5.setTextColor(getDockActivity().getResources().getColor(R.color.white));
            }
        });
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }


    @Override
    public void onSurveyNextFragmentListener() {

        if(isValidate()){
            ((CancelSurveyFragment)getParentFragment()).changeToNextQuestion(value,entitiy);
        }else{
            UIHelper.showShortToastInCenter(getDockActivity(),"Select any number");
        }

    }
}
