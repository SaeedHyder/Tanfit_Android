package com.ingic.tanfit.fragments;

import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ingic.tanfit.R;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.helpers.UIHelper;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.PinEntryEditText;
import com.ingic.tanfit.ui.views.TitleBar;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by saeedhyder on 11/21/2017.
 */
public class VerificationEmailFragment extends BaseFragment {
    @BindView(R.id.txt_pin_entry)
    PinEntryEditText txtPinEntry;
    @BindView(R.id.tv_counter)
    AnyTextView tvCounter;
    @BindView(R.id.txt_resened_code)
    AnyTextView txtResenedCode;
    Unbinder unbinder;
    CountDownTimer timer;
    @BindView(R.id.btn_submit)
    Button btnSubmit;


    public static VerificationEmailFragment newInstance() {
        Bundle args = new Bundle();

        VerificationEmailFragment fragment = new VerificationEmailFragment();
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
        View view = inflater.inflate(R.layout.fragment_verification_email, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        counter();
        txtResenedCode.setPaintFlags(txtResenedCode.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

    }


    public void counter() {
        timer = new CountDownTimer(225000, 1000) {

            public void onTick(long millisUntilFinished) {

                String text = String.format(Locale.getDefault(), "%2d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                if (tvCounter != null) {
                    tvCounter.setText(text + "");

                }
            }

            public void onFinish() {
                if (tvCounter != null)
                    tvCounter.setText("0");
            }
        }.start();

    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.verify_email));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



    @OnClick({R.id.btn_submit, R.id.txt_resened_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), "HomeFragment");
                break;
            case R.id.txt_resened_code:
                timer.cancel();
                counter();
                UIHelper.showShortToastInCenter(getDockActivity(),"Resend Code");
                break;
        }
    }
}
