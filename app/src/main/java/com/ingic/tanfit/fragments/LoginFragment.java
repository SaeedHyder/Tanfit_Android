package com.ingic.tanfit.fragments;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SeekBar;

import com.ingic.tanfit.R;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.ui.views.AnyEditTextView;
import com.ingic.tanfit.ui.views.AnyTextView;
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
    SeekBar slidelogin;
    @BindView(R.id.forgot_password)
    AnyTextView forgotPassword;
    @BindView(R.id.card_container)
    CardView cardContainer;
    @BindView(R.id.btn_signup)
    AnyTextView btnSignup;
    @BindView(R.id.scrollview_bigdaddy)
    ScrollView scrollviewBigdaddy;
    @BindView(R.id.img_password)
    ImageView passwordTick;
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
        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 5 && !hasImageSpan()) {
                    passwordTick.setVisibility(View.VISIBLE);
                } else if (s.toString().length() < 5 && hasImageSpan()) {
                    passwordTick.setVisibility(View.GONE);
                }
            }
        });

        slidelogin.setOnTouchListener(new View.OnTouchListener() {
            private boolean isInvalidMove;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        return isInvalidMove = motionEvent.getX() > slidelogin.getThumb().getIntrinsicWidth();
                    case MotionEvent.ACTION_MOVE:
                        return isInvalidMove;
                    case MotionEvent.ACTION_UP:
                        return isInvalidMove;
                }
                return false;
            }
        });

        slidelogin.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
                //label.setAlpha(1f - progress * 0.02f);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() < 100) {
                    animateSeekbarToZero(seekBar);
                } else {
                    if (isValidated()) {
                        prefHelper.setLoginStatus(true);
                        getDockActivity().popBackStackTillEntry(0);
                        getDockActivity().replaceDockableFragment(MainFragment.newInstance(), "MainFragment");
                    } else
                        animateSeekbarToZero(seekBar);
                }
            }
        });
       /* slidelogin.setOnUnlockListener(new SlideToUnlock.OnUnlockListener() {
            @Override
            public void onUnlock() {
                if (isValidated()) {
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(ClassDetailFragment.newInstance(), "ClassDetailFragment");
                } else
                    slidelogin.reset();
            }
        });*/
    }

    private void animateSeekbarToZero(SeekBar seekBar) {
        ObjectAnimator anim = ObjectAnimator.ofInt(seekBar, "progress", 0);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime));
        anim.start();
    }

    public boolean hasImageSpan() {
        return passwordTick.getVisibility() == View.VISIBLE;
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
                getDockActivity().replaceDockableFragment(ForgotPasswordEmailEnter.newInstance(), "ForgotPasswordEmailEnter");
                break;
            case R.id.btn_signup:
                getDockActivity().replaceDockableFragment(SignUpFragment.newInstance(), "SignUpFragment");
                break;
        }
    }
}
