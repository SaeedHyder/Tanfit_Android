package com.ingic.tanfit.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ingic.tanfit.R;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.helpers.UIHelper;
import com.ingic.tanfit.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created on 11/21/2017.
 */
public class ForgotPasswordEmailSend extends BaseFragment {
    @BindView(R.id.btn_email)
    Button btnEmail;
    Unbinder unbinder;

    public static ForgotPasswordEmailSend newInstance() {
        Bundle args = new Bundle();

        ForgotPasswordEmailSend fragment = new ForgotPasswordEmailSend();
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
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading(getString(R.string.forgot_password));
        titleBar.showBackButton();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_email_send, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void sendEmail() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"tanfit@sampleemail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Test Subject");
        i.putExtra(Intent.EXTRA_TEXT, "Test Body");
        try {
            startActivity(Intent.createChooser(i, getString(R.string.forgot_password_send_mail)));
        } catch (android.content.ActivityNotFoundException ex) {
            UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.email_app_error));
        }
    }

    @OnClick(R.id.btn_email)
    public void onViewClicked() {
        sendEmail();
    }
}