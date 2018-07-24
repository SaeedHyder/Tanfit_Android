package com.ingic.tanfit.fragments;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
 * Created on 11/21/2017.
 */
public class ForgotPasswordEmailEnter extends BaseFragment {
    @BindView(R.id.edt_email)
    AnyEditTextView edtEmail;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    Unbinder unbinder;

    public static ForgotPasswordEmailEnter newInstance() {
        Bundle args = new Bundle();

        ForgotPasswordEmailEnter fragment = new ForgotPasswordEmailEnter();
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
        View view = inflater.inflate(R.layout.fragment_forgot_password_email, container, false);
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

    private boolean isValidated() {
        if (edtEmail.getText() == null || (edtEmail.getText().toString().isEmpty()) ||
                !(Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches())) {
            edtEmail.setError(getDockActivity().getResources().getString(R.string.enter_valid_email));
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading(getDockActivity().getResources().getString(R.string.forgot_password));
        titleBar.showBackButton();
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        if (isValidated()) {
            serviceHelper.enqueueCall(headerWebService.forgotPassword(edtEmail.getText().toString()), WebServiceConstants.forgotPassword);
         }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {

            case WebServiceConstants.forgotPassword:
                UIHelper.showShortToastInCenter(getDockActivity(), message);
               // getDockActivity().popBackStackTillEntry(0);
                getDockActivity().replaceDockableFragment(ForgotPasswordReset.newInstance(edtEmail.getText().toString()), "ForgotPasswordReset");

        }
    }
}