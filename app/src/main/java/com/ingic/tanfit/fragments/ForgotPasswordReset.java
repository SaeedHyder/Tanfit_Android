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
import com.ingic.tanfit.retrofit.WebService;
import com.ingic.tanfit.ui.views.AnyEditTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created on 11/21/2017.
 */
public class ForgotPasswordReset extends BaseFragment {
    @BindView(R.id.edt_password)
    AnyEditTextView edtPassword;
    @BindView(R.id.btn_show_password)
    CheckBox btnShowPassword;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    Unbinder unbinder;

    private static String emailKey="emailKey";

    public static ForgotPasswordReset newInstance(String Email) {
        Bundle args = new Bundle();
        args.putString(emailKey,Email);
        ForgotPasswordReset fragment = new ForgotPasswordReset();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            emailKey=getArguments().getString(emailKey);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_password_reset, container, false);
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
        setListener();
    }



    private void setListener() {
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
    }

    private boolean isValidated() {
        if (edtPassword.getText().toString().isEmpty()) {
            edtPassword.setError(getDockActivity().getResources().getString(R.string.enter_password));
            return false;
        } /*else if (edtPassword.getText().toString().length() < 6) {
            edtPassword.setError(getString(R.string.passwordLength));
            return false;
        }*/ else {
            return true;
        }
    }

    @OnClick({R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                if (isValidated()) {
                    serviceHelper.enqueueCall(webService.forgotCodeVerification(emailKey,edtPassword.getText().toString()), WebServiceConstants.forgotCodeVerification);
                }
                break;
        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {

            case WebServiceConstants.forgotCodeVerification:
                UIHelper.showShortToastInCenter(getDockActivity(), message);
           //     getDockActivity().popBackStackTillEntry(0);
                getDockActivity().replaceDockableFragment(forgotNewPasswordFragment.newInstance(emailKey,edtPassword.getText().toString()), "forgotNewPasswordFragment");

        }
    }
}