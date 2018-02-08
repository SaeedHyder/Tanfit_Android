package com.ingic.tanfit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.AppDefaultSettingEnt;
import com.ingic.tanfit.entities.UserAllDataEnt;
import com.ingic.tanfit.entities.UserEnt;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.global.WebServiceConstants;
import com.ingic.tanfit.helpers.DialogHelper;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.TitleBar;

import java.util.ArrayList;

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

        // serviceHelper.enqueueCall(headerWebService.getUserSetting(prefHelper.getUserAllData().getId()),WebServiceConstants.getUserAppSetting);

        if (prefHelper.getUserAllData().getUserAppSetting().size() > 0) {
            if (prefHelper.getUserAllData().getUserAppSetting().get(0).getNotification()) {
                toggleNotification.setChecked(true);
            } else {
                toggleNotification.setChecked(false);
            }

            if (prefHelper.getUserAllData().getUserAppSetting().get(0).getLangId() == 1) {
                txtEnglishLanguage.setTextColor(getResources().getColor(R.color.app_green));
                txtPersianLanguage.setTextColor(getResources().getColor(R.color.app_text_gray));
            } else {
                txtEnglishLanguage.setTextColor(getResources().getColor(R.color.app_text_gray));
                txtPersianLanguage.setTextColor(getResources().getColor(R.color.app_green));
            }

        }

        toggleListner();

    }

    private void toggleListner() {

        toggleNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //  prefHelper.getUserAllData().getUserAppSetting().get(0).setNotification(isChecked);
                if (prefHelper.getUserAllData() != null && prefHelper.getAppDefaultSetting() != null && serviceHelper != null)
                    if (prefHelper.getUserAllData().getUserAppSetting().size() > 0) {
                        serviceHelper.enqueueCall(headerWebService.updateUserAppSetting(prefHelper.getUserAllData().getId(), isChecked, prefHelper.getUserAllData().getUserAppSetting().get(0).getLangId()), WebServiceConstants.userAppNotification);
                        UserAllDataEnt notification = prefHelper.getUserAllData();
                        notification.getUserAppSetting().get(0).setNotification(isChecked);
                        prefHelper.putUserAllData(notification);
                    } else {
                        serviceHelper.enqueueCall(headerWebService.updateUserAppSetting(prefHelper.getUserAllData().getId(), isChecked, prefHelper.getAppDefaultSetting().getLangId()), WebServiceConstants.userAppNotification);

                        UserAllDataEnt notification = prefHelper.getUserAllData();
                        notification.getUserAppSetting().add(new AppDefaultSettingEnt());
                        notification.getUserAppSetting().get(0).setNotification(isChecked);
                        prefHelper.putUserAllData(notification);
                    }


                // UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.implemented_beta));
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

    @OnClick({R.id.txt_englishLanguage, R.id.txt_persianLanguage, R.id.txt_deleteAccount})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_englishLanguage:
                txtEnglishLanguage.setTextColor(getResources().getColor(R.color.app_green));
                txtPersianLanguage.setTextColor(getResources().getColor(R.color.app_text_gray));

                if (prefHelper.getUserAllData() != null && prefHelper.getAppDefaultSetting() != null &&  serviceHelper != null)
                    if (prefHelper.getUserAllData().getUserAppSetting().size() > 0) {
                        serviceHelper.enqueueCall(headerWebService.updateUserAppSetting(prefHelper.getUserAllData().getId(), prefHelper.getUserAllData().getUserAppSetting().get(0).getNotification(), 1), WebServiceConstants.englishLanguage);
                        UserAllDataEnt englishLanguage = prefHelper.getUserAllData();
                        englishLanguage.getUserAppSetting().get(0).setLangId(1);
                        prefHelper.putUserAllData(englishLanguage);
                    } else {
                        serviceHelper.enqueueCall(headerWebService.updateUserAppSetting(prefHelper.getUserAllData().getId(), prefHelper.getAppDefaultSetting().getNotification(), 1), WebServiceConstants.userAppNotification);

                        UserAllDataEnt englishLanguage = prefHelper.getUserAllData();
                        englishLanguage.getUserAppSetting().add(new AppDefaultSettingEnt());
                        englishLanguage.getUserAppSetting().get(0).setLangId(1);
                        prefHelper.putUserAllData(englishLanguage);
                    }


                break;
            case R.id.txt_persianLanguage:
                txtEnglishLanguage.setTextColor(getResources().getColor(R.color.app_text_gray));
                txtPersianLanguage.setTextColor(getResources().getColor(R.color.app_green));

                if (prefHelper.getUserAllData() != null && prefHelper.getAppDefaultSetting() != null && serviceHelper != null)
                    if (prefHelper.getUserAllData().getUserAppSetting().size() > 0) {
                        serviceHelper.enqueueCall(headerWebService.updateUserAppSetting(prefHelper.getUserAllData().getId(), prefHelper.getUserAllData().getUserAppSetting().get(0).getNotification(), 2), WebServiceConstants.persianlanguage);
                        UserAllDataEnt persianLanguage = prefHelper.getUserAllData();
                        persianLanguage.getUserAppSetting().get(0).setLangId(2);
                        prefHelper.putUserAllData(persianLanguage);
                    } else {
                        serviceHelper.enqueueCall(headerWebService.updateUserAppSetting(prefHelper.getUserAllData().getId(), prefHelper.getAppDefaultSetting().getNotification(), 2), WebServiceConstants.userAppNotification);

                        UserAllDataEnt persianLanguage = prefHelper.getUserAllData();
                        persianLanguage.getUserAppSetting().add(new AppDefaultSettingEnt());
                        persianLanguage.getUserAppSetting().get(0).setLangId(2);
                        prefHelper.putUserAllData(persianLanguage);
                    }


                break;
            case R.id.txt_deleteAccount:
                final DialogHelper deleteAccount = new DialogHelper(getDockActivity());
                deleteAccount.iniDeleteAccount(R.layout.delete_account_dialoge, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        serviceHelper.enqueueCall(headerWebService.deleteAccount(prefHelper.getUserAllData().getId(), prefHelper.getUserAllData().getEmail(), prefHelper.getUserAllData().getFullName(), prefHelper.getUserAllData().getUserThumbnailImage(), prefHelper.getUserAllData().getGenderId() + "", true), WebServiceConstants.deleteAccount);
                        deleteAccount.hideDialog();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteAccount.hideDialog();
                    }
                });
                deleteAccount.showDialog();
                break;
        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {

            case WebServiceConstants.deleteAccount:
                UserEnt entity = prefHelper.getUser();
                entity.setIsDeleted("True");
                prefHelper.putUser(entity);
                getMainActivity().popBackStackTillEntry(0);
                prefHelper.setLoginStatus(false);
                getDockActivity().replaceDockableFragment(LoginFragment.newInstance(), "LoginFragment");
                break;

            case WebServiceConstants.englishLanguage:

                //   prefHelper.getUserAllData().getUserAppSetting().get(0).setLangId(1);

                break;

            case WebServiceConstants.persianlanguage:

                //  prefHelper.getUserAllData().getUserAppSetting().get(0).setLangId(2);

                break;


            case WebServiceConstants.userAppNotification:


                break;

            case WebServiceConstants.getUserAppSetting:
                ArrayList<AppDefaultSettingEnt> userSetting = (ArrayList<AppDefaultSettingEnt>) result;

                if (userSetting.size() > 0) {
                    if (userSetting.get(0).getNotification()) {
                        toggleNotification.setChecked(true);
                    } else {
                        toggleNotification.setChecked(false);
                    }

                    if (userSetting.get(0).getLangId() == 1) {
                        txtEnglishLanguage.setTextColor(getResources().getColor(R.color.app_green));
                        txtPersianLanguage.setTextColor(getResources().getColor(R.color.app_text_gray));
                    } else {
                        txtEnglishLanguage.setTextColor(getResources().getColor(R.color.app_text_gray));
                        txtPersianLanguage.setTextColor(getResources().getColor(R.color.app_green));
                    }

                }

                break;


        }
    }
}
