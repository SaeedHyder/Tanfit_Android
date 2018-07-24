package com.ingic.tanfit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.SurveyQuestionsEnt;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.global.WebServiceConstants;
import com.ingic.tanfit.helpers.UIHelper;
import com.ingic.tanfit.interfaces.SurveyChangeListener;
import com.ingic.tanfit.ui.views.AnyEditTextView;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.TitleBar;
import com.xw.repo.XEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by saeedhyder on 6/11/2018.
 */
public class SurveyTextBoxFragment extends BaseFragment implements SurveyChangeListener {
    @BindView(R.id.txt_title)
    AnyTextView txtTitle;
    @BindView(R.id.txt_question)
    AnyTextView txtQuestion;
    @BindView(R.id.txt_textBox)
    AnyEditTextView txtTextBox;
    Unbinder unbinder;

    SurveyQuestionsEnt entitiy;

    public static SurveyTextBoxFragment newInstance() {
        Bundle args = new Bundle();

        SurveyTextBoxFragment fragment = new SurveyTextBoxFragment();
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
        View view = inflater.inflate(R.layout.fragment_survey_text_box, container, false);
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

        txtTextBox.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });

    }

    public void setData(SurveyQuestionsEnt data) {
        this.entitiy = data;
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }


    @Override
    public void onSurveyNextFragmentListener() {
        if (txtTextBox.getText().toString().isEmpty() || txtTextBox.getText().toString().trim().equals("")) {
            UIHelper.showShortToastInCenter(getDockActivity(), getDockActivity().getResources().getString(R.string.feedback_cannot_empty));
        } else {
            ((CancelSurveyFragment) getParentFragment()).changeToNextQuestion(txtTextBox.getText().toString(),entitiy);
        }
    }
}
