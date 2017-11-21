package com.ingic.tanfit.fragments;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.ingic.tanfit.R;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.ui.views.AnyEditTextView;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.SlideToUnlock;
import com.ingic.tanfit.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class LoginFragment extends BaseFragment {


    @BindView(R.id.edt_email)
    AnyEditTextView edtEmail;
    @BindView(R.id.edt_password)
    AnyEditTextView edtPassword;
    @BindView(R.id.slidelogin)
    SlideToUnlock slidelogin;
    @BindView(R.id.forgot_password)
    AnyTextView forgotPassword;
    @BindView(R.id.card_container)
    CardView cardContainer;
    @BindView(R.id.btn_signup)
    AnyTextView btnSignup;
    @BindView(R.id.scrollview_bigdaddy)
    ScrollView scrollviewBigdaddy;
    Unbinder unbinder;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        // TODO Auto-generated method stub
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        setListener();
    }

    private void setListener() {
        slidelogin.setOnUnlockListener(new SlideToUnlock.OnUnlockListener() {
            @Override
            public void onUnlock() {
                if (isValidated()) {
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), "HomeFragment");
                } else
                    slidelogin.reset();
            }
        });
    }

    private boolean isValidated() {
        if (edtEmail.getText() == null || (edtEmail.getText().toString().isEmpty()) ||
                !(Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches())) {
            edtEmail.setError(getString(R.string.enter_valid_email));
            return false;
        } else if (edtPassword.getText().toString().isEmpty()) {
            edtPassword.setError(getString(R.string.enter_password));
            return false;
        } else if (edtPassword.getText().toString().length() < 6) {
            edtPassword.setError(getString(R.string.passwordLength));
            return false;
        } else {
            return true;
        }
    }

    @OnClick({R.id.forgot_password, R.id.btn_signup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.forgot_password:
                break;
            case R.id.btn_signup:
                break;
        }
    }
}
