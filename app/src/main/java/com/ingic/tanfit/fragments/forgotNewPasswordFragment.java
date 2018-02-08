package com.ingic.tanfit.fragments;

import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.ingic.tanfit.R;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.global.WebServiceConstants;
import com.ingic.tanfit.helpers.UIHelper;
import com.ingic.tanfit.ui.views.AnyEditTextView;
import com.ingic.tanfit.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by saeedhyder on 1/9/2018.
 */
public class forgotNewPasswordFragment extends BaseFragment {

    @BindView(R.id.edt_password)
    AnyEditTextView edtPassword;
    @BindView(R.id.btn_show_password)
    CheckBox btnShowPassword;
    @BindView(R.id.edt_conformPassword)
    AnyEditTextView edtConformPassword;
    @BindView(R.id.btn_show_conformpassword)
    CheckBox btnShowConformpassword;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    Unbinder unbinder;

    private static String email = "emailKey";
    private static String code = "code";

    public static forgotNewPasswordFragment newInstance() {
        Bundle args = new Bundle();

        forgotNewPasswordFragment fragment = new forgotNewPasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static forgotNewPasswordFragment newInstance(String emailKey, String codeKey) {
        Bundle args = new Bundle();
        args.putString(email, emailKey);
        args.putString(code, codeKey);
        forgotNewPasswordFragment fragment = new forgotNewPasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            email = getArguments().getString(email);
            code = getArguments().getString(code);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fotgot_changepassword, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListners();
    }

    private void setListners() {
        btnShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    edtPassword.setTransformationMethod(null);
                } else {
                    edtPassword.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });
        btnShowConformpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    edtConformPassword.setTransformationMethod(null);
                } else {
                    edtConformPassword.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });
    }

    private boolean isValidated() {
        if (edtPassword.getText() == null || (edtPassword.getText().toString().isEmpty())) {
            edtPassword.setError(getString(R.string.enter_password));
            return false;
        } else if (edtPassword.getText().toString().length() < 6) {
            edtPassword.setError(getString(R.string.passwordLength));
            return false;
        } else if (edtConformPassword.getText() == null || (edtConformPassword.getText().toString().isEmpty()) || edtConformPassword.getText().toString().length() < 6) {
            edtConformPassword.setError(getString(R.string.enter_password));
            return false;
        } else if (!edtConformPassword.getText().toString().equals(edtPassword.getText().toString())) {
            edtConformPassword.setError(getString(R.string.conform_password_error));
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading("Forgot Password");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        if (isValidated()) {
            serviceHelper.enqueueCall(headerWebService.updateForgotPassword(email,code,edtPassword.getText().toString(), edtConformPassword.getText().toString()), WebServiceConstants.forgotCodeVerification);
        }

    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {

            case WebServiceConstants.forgotCodeVerification:
                UIHelper.showShortToastInCenter(getDockActivity(), message);
           //     getDockActivity().popBackStackTillEntry(0);
                getDockActivity().replaceDockableFragment(LoginFragment.newInstance(), "LoginFragment");

        }
    }
}
