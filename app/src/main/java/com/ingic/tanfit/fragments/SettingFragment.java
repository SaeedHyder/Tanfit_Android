package com.ingic.tanfit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.ingic.tanfit.R;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.helpers.UIHelper;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by saeedhyder on 11/25/2017.
 */
public class SettingFragment extends BaseFragment {
    @BindView(R.id.txt_deleteAccount)
    AnyTextView txtDeleteAccount;
    @BindView(R.id.toggleNotification)
    ToggleButton toggleNotification;
    @BindView(R.id.txt_englishLanguage)
    AnyTextView txtEnglishLanguage;
    @BindView(R.id.txt_persianLanguage)
    AnyTextView txtPersianLanguage;
    Unbinder unbinder;

    public static SettingFragment newInstance() {
        Bundle args = new Bundle();

        SettingFragment fragment = new SettingFragment();
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
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toggleListner();

    }

    private void toggleListner() {

        toggleNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.implemented_beta));
            }
        });
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.settings));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.txt_englishLanguage, R.id.txt_persianLanguage,R.id.txt_deleteAccount})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_englishLanguage:
                UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.implemented_beta));
                break;
            case R.id.txt_persianLanguage:
                UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.implemented_beta));
                break;
            case R.id.txt_deleteAccount:
                UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.implemented_beta));
                break;
        }
    }
}
