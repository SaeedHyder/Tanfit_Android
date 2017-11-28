package com.ingic.tanfit.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ingic.tanfit.R;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.helpers.CameraHelper;
import com.ingic.tanfit.helpers.UIHelper;
import com.ingic.tanfit.interfaces.ImageSetter;
import com.ingic.tanfit.ui.views.AnyEditTextView;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.TitleBar;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created on 11/21/2017.
 */
public class SignUpFragment extends BaseFragment implements ImageSetter, CompoundButton.OnCheckedChangeListener {
    @BindView(R.id.img_profile)
    CircleImageView imgProfile;
    @BindView(R.id.btn_camera)
    ImageView btnCamera;
    @BindView(R.id.ll_ProfileImage)
    RelativeLayout llProfileImage;
    @BindView(R.id.edt_fullname)
    AnyEditTextView edtFullname;
    @BindView(R.id.spn_gender)
    Spinner spnGender;
    @BindView(R.id.edt_email)
    AnyEditTextView edtEmail;
    @BindView(R.id.edt_password)
    AnyEditTextView edtPassword;
    @BindView(R.id.checkbox_show_password)
    CheckBox checkboxShowPassword;
    @BindView(R.id.edt_confirm_password)
    AnyEditTextView edtConfirmPassword;
    @BindView(R.id.checkbox_show_confirm_password)
    CheckBox checkboxShowConfirmPassword;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.btn_fbSignup)
    Button btnFbSignup;
    @BindView(R.id.btn_gSingup)
    Button btnGSingup;
    @BindView(R.id.check_term_condition)
    CheckBox checkboxTermCondition;
    @BindView(R.id.txt_term_condition_text)
    AnyTextView termCondtion;
    private File profilePic;
    private String profilePath;


    public static SignUpFragment newInstance() {
        Bundle args = new Bundle();

        SignUpFragment fragment = new SignUpFragment();
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
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.signup_head));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTermsClickListeners();
        setGenderSpinner();
        getMainActivity().setImageSetter(this);


    }

    private void setGenderSpinner() {
        ArrayList<String> genderCollection = new ArrayList<>(3);
        genderCollection.add(getString(R.string.gender_hint));
        genderCollection.add(getString(R.string.gender_male));
        genderCollection.add(getString(R.string.gender_female));
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(getDockActivity()
                , R.layout.spinner_item, genderCollection) {
            @Override
            public boolean isEnabled(int position) {
                return !(position == 0);
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTextColor(position == 0 ? Color.GRAY : Color.BLACK);
                return view;
            }
        };

        // ArrayAdapter<String> carWashType = new ArrayAdapter<String>(getDockActivity(), android.R.layout.simple_spinner_item, CarWashTypeArray);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGender.setAdapter(genderAdapter);

    }

    private void setTermsClickListeners() {
        checkboxShowPassword.setOnCheckedChangeListener(this);
        checkboxShowConfirmPassword.setOnCheckedChangeListener(this);
        SpannableString stringBuilder = new SpannableString(getString(R.string.term_condition_agree));
        setClickableSpan(stringBuilder, getString(R.string.term_condition_agree), getString(R.string.term_condition),
                new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        getDockActivity().replaceDockableFragment(TermsandConditionsFragment.newInstance(), "TermsandConditionsFragment");
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        ds.setColor(getResources().getColor(R.color.black));
                        ds.setUnderlineText(true);
                    }
                });
        setForegroundColorSpan(stringBuilder, getString(R.string.term_condition_agree),
                getString(R.string.term_condition), getResources().getColor(R.color.black));

        termCondtion.setText(stringBuilder);
        termCondtion.setMovementMethod(LinkMovementMethod.getInstance());
        termCondtion.setHighlightColor(Color.TRANSPARENT);
    }

    private void setForegroundColorSpan(SpannableString stringBuilder, String text, String secondaryText, int color) {

        stringBuilder.setSpan(
                new ForegroundColorSpan(color),
                text.indexOf(secondaryText),
                text.indexOf(secondaryText) + String.valueOf(secondaryText).length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );
    }

    private void setClickableSpan(SpannableString stringBuilder, String text, String secondaryText, ClickableSpan span) {
        stringBuilder.setSpan(
                span,
                text.indexOf(secondaryText),
                text.indexOf(secondaryText) + String.valueOf(secondaryText).length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );
    }

    @OnClick({R.id.btn_submit, R.id.btn_fbSignup, R.id.btn_gSingup, R.id.btn_camera})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                if (isValidated()) {
                   getDockActivity().replaceDockableFragment(VerificationEmailFragment.newInstance(),"VerificationEmailFragment");
                }
                break;
            case R.id.btn_fbSignup:
                willbeimplementedinBeta();
                break;
            case R.id.btn_gSingup:
                willbeimplementedinBeta();
                break;
            case R.id.btn_camera:
                CameraHelper.uploadPhotoDialog(getMainActivity());
                break;
        }
    }

    private boolean isValidated() {
        if (edtFullname.getText() == null || (edtFullname.getText().toString().isEmpty())) {
            if (edtFullname.requestFocus()) {
                setEditTextFocus(edtFullname);
            }
            edtFullname.setError(getString(R.string.enter_FullName));
            return false;
        } else if (edtEmail.getText() == null || (edtEmail.getText().toString().isEmpty()) ||
                (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches())) {
            if (edtEmail.requestFocus()) {
                setEditTextFocus(edtEmail);
            }
            edtEmail.setError(getString(R.string.enter_email));
            return false;
        } else if (edtPassword.getText().toString().isEmpty()) {
            edtPassword.setError(getString(R.string.enter_password));
            if (edtPassword.requestFocus()) {
                setEditTextFocus(edtPassword);
            }
            return false;
        } else if (edtPassword.getText().toString().length() < 6) {
            edtPassword.setError(getString(R.string.enter_valid_password));
            if (edtPassword.requestFocus()) {
                setEditTextFocus(edtPassword);
            }
            return false;
        } else if (!edtConfirmPassword.getText().toString().equals(edtPassword.getText().toString())) {
            edtConfirmPassword.setError(getString(R.string.confirm_password_error));
            if (edtConfirmPassword.requestFocus()) {
                setEditTextFocus(edtConfirmPassword);
            }
            return false;
        } else if (spnGender.getSelectedItemPosition() == 0) {
            UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.select_gender_error));
            return false;
        } else if (!checkboxTermCondition.isChecked()) {
            UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.select_term_condition_error));
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void setImage(String imagePath) {
        if (imagePath != null) {
            //profilePic = new File(imagePath);
            profilePic = new File(imagePath);
            profilePath = imagePath;
            Picasso.with(getDockActivity())
                    .load("file:///" + imagePath)
                    .into(imgProfile);
        }
    }

    @Override
    public void setFilePath(String filePath) {

    }

    @Override
    public void setVideo(String videoPath, String VideoThumbail) {

    }

    private void setPasswordTransformation(boolean isChecked, AnyEditTextView textView) {
        if (isChecked) {
            textView.setTransformationMethod(null);
        } else {
            textView.setTransformationMethod(new PasswordTransformationMethod());
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.checkbox_show_password:
                setPasswordTransformation(isChecked, edtPassword);
                break;
            case R.id.checkbox_show_confirm_password:
                setPasswordTransformation(isChecked, edtConfirmPassword);
                break;

        }
    }
}