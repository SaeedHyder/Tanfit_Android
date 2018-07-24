package com.ingic.tanfit.fragments;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.UserEnt;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.global.WebServiceConstants;
import com.ingic.tanfit.helpers.InternetHelper;
import com.ingic.tanfit.helpers.UIHelper;
import com.ingic.tanfit.ui.views.AnyEditTextView;
import com.ingic.tanfit.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by saeedhyder on 11/25/2017.
 */
public class ChangePasswordFragment extends BaseFragment {
    @BindView(R.id.edtcurrentPassword)
    AnyEditTextView edtcurrentPassword;
    @BindView(R.id.ll_currentPassword)
    LinearLayout llCurrentPassword;
    @BindView(R.id.editNewPassword)
    AnyEditTextView editNewPassword;
    @BindView(R.id.togglePassword)
    ToggleButton togglePassword;
    @BindView(R.id.rl_eye_new_password)
    RelativeLayout rlEyeNewPassword;
    @BindView(R.id.ll_NewPassword)
    LinearLayout llNewPassword;
    @BindView(R.id.editConfirmPassword)
    AnyEditTextView editConfirmPassword;
    @BindView(R.id.togglePassword1)
    ToggleButton togglePassword1;
    @BindView(R.id.rl_eye_confirm_password)
    RelativeLayout rlEyeConfirmPassword;
    @BindView(R.id.ll_ConfirmPassword)
    LinearLayout llConfirmPassword;
    @BindView(R.id.SubmitButton)
    Button SubmitButton;
    Unbinder unbinder;

    public static ChangePasswordFragment newInstance() {
        Bundle args = new Bundle();

        ChangePasswordFragment fragment = new ChangePasswordFragment();
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
        View view = inflater.inflate(R.layout.fragment_changepassword, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (prefHelper.isLanguagePersian()) {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        } else {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }


    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getDockActivity().getResources().getString(R.string.change_password));
    }


    private boolean isvalidate() {
        if (edtcurrentPassword.getText() == null || (edtcurrentPassword.getText().toString().isEmpty())) {
            edtcurrentPassword.setError(getDockActivity().getResources().getString(R.string.enter_password));
            return false;
        } else if (editNewPassword.getText() == null || (editNewPassword.getText().toString().isEmpty())) {
            editNewPassword.setError(getDockActivity().getResources().getString(R.string.enter_password));
            return false;
        } else if (editNewPassword.getText().toString().equals(edtcurrentPassword.getText().toString())) {
            editNewPassword.setError(getDockActivity().getResources().getString(R.string.samePassword));
            return false;
        } else if (editNewPassword.getText().toString().length() < 6) {
            editNewPassword.setError(getDockActivity().getResources().getString(R.string.passwordLength));
            return false;
        } else if (editConfirmPassword.getText() == null || (editConfirmPassword.getText().toString().isEmpty()) || editConfirmPassword.getText().toString().length() < 6) {
            editConfirmPassword.setError(getDockActivity().getResources().getString(R.string.enter_password));
            return false;
        } else if (!editConfirmPassword.getText().toString().equals(editNewPassword.getText().toString())) {
            editConfirmPassword.setError(getDockActivity().getResources().getString(R.string.conform_password_error));
            return false;
        } else {
            return true;
        }
    }


    @OnClick({R.id.rl_eye_new_password, R.id.rl_eye_confirm_password, R.id.SubmitButton, R.id.togglePassword1, R.id.togglePassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.togglePassword:
                boolean isCheck = togglePassword.isChecked();

                if (isCheck) {
                    editNewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    editNewPassword.setSelection(editNewPassword.getText().length());
                } else {
                    editNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    editNewPassword.setSelection(editNewPassword.getText().length());
                }
                break;
            case R.id.togglePassword1:
                boolean isCheck1 = togglePassword1.isChecked();

                if (isCheck1) {
                    editConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    editConfirmPassword.setSelection(editConfirmPassword.getText().length());
                } else {
                    editConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    editConfirmPassword.setSelection(editConfirmPassword.getText().length());
                }
                break;
            case R.id.SubmitButton:
                if (isvalidate()) {
                    if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity()))
                        serviceHelper.enqueueCall(headerWebService.changePassword(edtcurrentPassword.getText().toString(), editNewPassword.getText().toString(), editConfirmPassword.getText().toString()), WebServiceConstants.changePassword);
                }
                break;
        }
    }

     @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {

            case WebServiceConstants.changePassword:
                UIHelper.showShortToastInCenter(getDockActivity(),message);
                getDockActivity().replaceDockableFragment(MainFragment.newInstance(), "MainFragment");

                break;
        }
    }
}
