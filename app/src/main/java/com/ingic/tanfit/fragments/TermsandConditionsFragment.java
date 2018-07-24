package com.ingic.tanfit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ingic.tanfit.R;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created on 11/23/2017.
 */
public class TermsandConditionsFragment extends BaseFragment {
    @BindView(R.id.txt_term_condition)
    TextView txtTermCondition;
    Unbinder unbinder;

    public static TermsandConditionsFragment newInstance() {
        Bundle args = new Bundle();

        TermsandConditionsFragment fragment = new TermsandConditionsFragment();
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
        View view = inflater.inflate(R.layout.fragment_term_condition, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(prefHelper.isLanguagePersian()){
            txtTermCondition.setText(prefHelper.getAppDefaultSetting().getTermsAndConditionsPer());
        }else{
            txtTermCondition.setText(prefHelper.getAppDefaultSetting().getTermsAndConditionsEng());
        }

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getDockActivity().getResources().getString(R.string.term_condition));
    }
}