package com.ingic.tanfit.fragments;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SeekBar;

import com.google.firebase.iid.FirebaseInstanceId;
import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.AppDefaultSettingEnt;
import com.ingic.tanfit.entities.IsVerifiedEnt;
import com.ingic.tanfit.entities.UserEnt;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.global.AppConstants;
import com.ingic.tanfit.global.WebServiceConstants;
import com.ingic.tanfit.helpers.DialogHelper;
import com.ingic.tanfit.helpers.TokenUpdater;
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
        serviceHelper.enqueueCall(headerWebService.getDefaultSetting(), WebServiceConstants.getDefaultSetting);
        setListener();
        animateSeekbarToZero(slidelogin);

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
                        //  loginService();
                        serviceHelper.enqueueCall(headerWebService.loginUser(edtEmail.getText().toString(), edtPassword.getText().toString()), WebServiceConstants.LoginUser);
                        animateSeekbarToZero(seekBar);
                    } else
                        animateSeekbarToZero(seekBar);
                }
            }
        });
    }


    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {

            case WebServiceConstants.LoginUser:

                UserEnt entity = (UserEnt) result;
                prefHelper.putUser(entity);
                prefHelper.setUserId(entity.getUserId());
                prefHelper.setAccess_Token(entity.getAccessToken());
                TokenUpdater.getInstance().UpdateToken(getDockActivity(),
                        entity.getUserId(),
                        AppConstants.Device_Type,
                        FirebaseInstanceId.getInstance().getToken());

                serviceHelper.enqueueCall(headerWebService.isVerified(prefHelper.getUser().getUserId() + ""), WebServiceConstants.iVerified);


                break;

            case WebServiceConstants.iVerified:

                final IsVerifiedEnt data = (IsVerifiedEnt) result;
                prefHelper.putIsVerified(data);
                if ((data.getIsDeleted())) {

                    final DialogHelper activiateAccountDialoge = new DialogHelper(getDockActivity());
                    activiateAccountDialoge.initbooknow(R.layout.activite_account_dialoge, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            serviceHelper.enqueueCall(headerWebService.deleteAccount(prefHelper.getUser().getUserId(), prefHelper.getUser().getEmail(), prefHelper.getUser().getFullName(), prefHelper.getUser().getUserThumbnailImage(), prefHelper.getUser().getGenderId() + "", false), WebServiceConstants.deleteAccount);
                            activiateAccountDialoge.hideDialog();

                        }
                    }, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            activiateAccountDialoge.hideDialog();
                        }
                    });

                    activiateAccountDialoge.showDialog();

                    // UIHelper.showShortToastInCenter(getDockActivity(),"User is deleted");
                } else {
                    if (data.getPhoneNumberConfirmed()) {
                        prefHelper.setLoginStatus(true);
                        getDockActivity().popBackStackTillEntry(0);
                        getDockActivity().replaceDockableFragment(MainFragment.newInstance(), "MainFragment");
                    } else {
                        getDockActivity().replaceDockableFragment(VerificationEmailFragment.newInstance(), "VerificationEmailFragment");
                    }

                }
                break;

            case WebServiceConstants.getDefaultSetting:
                prefHelper.putAppDefaultSetting((AppDefaultSettingEnt) result);
                break;

            case WebServiceConstants.deleteAccount:
                if (prefHelper.getIsVerified().getPhoneNumberConfirmed()) {
                    prefHelper.setLoginStatus(true);
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(MainFragment.newInstance(), "MainFragment");
                } else {
                    getDockActivity().replaceDockableFragment(VerificationEmailFragment.newInstance(), "VerificationEmailFragment");
                }
                break;


        }
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
