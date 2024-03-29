package com.ingic.tanfit.fragments;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.iid.FirebaseInstanceId;
import com.iceteck.silicompressorr.SiliCompressor;
import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.UserEnt;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.global.AppConstants;
import com.ingic.tanfit.global.WebServiceConstants;
import com.ingic.tanfit.helpers.CameraHelper;
import com.ingic.tanfit.helpers.GoogleHelper;
import com.ingic.tanfit.helpers.TokenUpdater;
import com.ingic.tanfit.helpers.UIHelper;
import com.ingic.tanfit.interfaces.ImageSetter;
import com.ingic.tanfit.ui.views.AnyEditTextView;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.TitleBar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.ingic.tanfit.ui.views.RoundedDrawable.TAG;

/**
 * Created on 11/21/2017.
 */
public class SignUpFragment extends BaseFragment implements ImageSetter, CompoundButton.OnCheckedChangeListener, GoogleHelper.GoogleHelperInterfce {
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
    @BindView(R.id.edt_mobile_number)
    AnyEditTextView edtMobileNumber;
    @BindView(R.id.iv_google)
    ImageView ivGoogle;
    @BindView(R.id.txtgoogle)
    AnyTextView txtgoogle;
    @BindView(R.id.ll_google)
    LinearLayout llGoogle;
    private File profilePic;
    private Bitmap profilePicBitmap;
    private String profilePath;
    private GoogleHelper googleHelper;
    private static final int RC_SIGN_IN = 007;
    private String mSocialMediaPlatform = "";
    private String mSocialMediaID = "";
    private ImageLoader imageLoader;


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
        titleBar.setSubHeading(getDockActivity().getResources().getString(R.string.signup_head));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        ButterKnife.bind(this, view);
        imageLoader = ImageLoader.getInstance();
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
        setTermsClickListeners();
        setGenderSpinner();
        setupGoogleSignup();
        getMainActivity().setImageSetter(this);


    }

    private void setupGoogleSignup() {
        googleHelper = GoogleHelper.getInstance();
        googleHelper.setGoogleHelperInterface(this);
        googleHelper.configGoogleApiClient(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            googleHelper.handleGoogleResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        googleHelper.ConnectGoogleAPi();
        //googleHelper.checkGoogleSeesion();
    }

    @Override
    public void onStop() {
        super.onStop();
        googleHelper.DisconnectGoogleApi();
    }

    @Override
    public void onPause() {
        super.onPause();
        googleHelper.DisconnectGoogleApi();
    }


    private void setGenderSpinner() {
        ArrayList<String> genderCollection = new ArrayList<>(3);
        genderCollection.add(getDockActivity().getResources().getString(R.string.gender_hint));
        genderCollection.add(getDockActivity().getResources().getString(R.string.gender_male));
        genderCollection.add(getDockActivity().getResources().getString(R.string.gender_female));
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
        SpannableString stringBuilder = new SpannableString(getDockActivity().getResources().getString(R.string.term_condition_agree));
        setClickableSpan(stringBuilder, getDockActivity().getResources().getString(R.string.term_condition_agree), getDockActivity().getResources().getString(R.string.term_condition),
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
        setForegroundColorSpan(stringBuilder, getDockActivity().getResources().getString(R.string.term_condition_agree),
                getString(R.string.term_condition), getDockActivity().getResources().getColor(R.color.black));

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

    @OnClick({R.id.btn_submit, R.id.btn_fbSignup, R.id.ll_google, R.id.btn_camera})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                if (isValidated()) {
                    registerUser();

                }
                break;
            case R.id.btn_fbSignup:
                willbeimplementedinBeta();
                break;
            case R.id.ll_google:
                googleHelper.intentGoogleSign();
                break;
            case R.id.btn_camera:
                requestCameraPermission();

                break;
        }
    }

    private void registerUser() {

        getDockActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getMainActivity().onLoadingStarted();
            }
        });

        Thread t = new Thread() {
            public void run() {

                MultipartBody.Part filePart;
                if (profilePic != null) {
                    filePart = MultipartBody.Part.createFormData("profile_picture",
                            profilePic.getName(), RequestBody.create(MediaType.parse("image*//*"), profilePic));
                } else {
                    filePart = MultipartBody.Part.createFormData("profile_picture", "",
                            RequestBody.create(MediaType.parse("**/*//*"), ""));
                }


                String gender;
                if (spnGender.getSelectedItemPosition() == 1) {
                    gender = "1";
                } else {
                    gender = "2";
                }
                String image;
                if (profilePicBitmap != null && !profilePicBitmap.equals("")) {
                    image = AppConstants.Base64+convertBitmapToBase64(profilePicBitmap);
                } else {
                    image = "";
                }

                serviceHelper.enqueueCallHome(webService.registerUser(
                        edtEmail.getText().toString(),
                        edtPassword.getText().toString(),
                        edtConfirmPassword.getText().toString(),
                        edtMobileNumber.getText().toString(),
                        edtFullname.getText().toString(),
                        image,
                        gender), WebServiceConstants.registeruser);
            }
        };
        t.start();






    }

    @Override
    public void ResponseFailure(String tag) {
        super.ResponseFailure(tag);

        getMainActivity().onLoadingFinished();
    }

    private String convertToBase64(String imagePath)

    {

        Bitmap bm = BitmapFactory.decodeFile(imagePath);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] byteArrayImage = baos.toByteArray();

        String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);

        return encodedImage;

    }


    public String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        return encoded;
    }


    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {

            case WebServiceConstants.registeruser:
                getMainActivity().onLoadingFinished();
                googleHelper.googleRevokeAccess();
                googleHelper.googleSignOut();
                UserEnt entity = (UserEnt) result;
                prefHelper.putUser(entity);
                prefHelper.setUserId(entity.getUserId());
                prefHelper.setAccess_Token(entity.getAccessToken());
                // UIHelper.showShortToastInCenter(getDockActivity(), message);
                TokenUpdater.getInstance().UpdateToken(getDockActivity(),
                        entity.getUserId(),
                        AppConstants.Device_Type,
                        FirebaseInstanceId.getInstance().getToken());
                getDockActivity().replaceDockableFragment(VerificationEmailFragment.newInstance(), "VerificationEmailFragment");
                // getDockActivity().replaceDockableFragment(LoginFragment.newInstance(), "LoginFragment");

                break;
        }
    }


    private boolean isValidated() {
        if (edtFullname.getText() == null || (edtFullname.getText().toString().isEmpty())) {
            if (edtFullname.requestFocus()) {
                setEditTextFocus(edtFullname);
            }
            edtFullname.setError(getDockActivity().getResources().getString(R.string.enter_FullName));
            return false;
        } else if (spnGender.getSelectedItemPosition() == 0) {
            UIHelper.showShortToastInCenter(getDockActivity(), getDockActivity().getResources().getString(R.string.select_gender_error));
            return false;
        } else if (edtEmail.getText() == null || (edtEmail.getText().toString().isEmpty()) ||
                (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches())) {
            if (edtEmail.requestFocus()) {
                setEditTextFocus(edtEmail);
            }
            edtEmail.setError(getDockActivity().getResources().getString(R.string.enter_email));
            return false;
        } else if (edtPassword.getText().toString().isEmpty()) {
            edtPassword.setError(getDockActivity().getResources().getString(R.string.enter_password));
            if (edtPassword.requestFocus()) {
                setEditTextFocus(edtPassword);
            }
            return false;
        } else if (edtMobileNumber.getText() == null || (edtMobileNumber.getText().toString().isEmpty())) {
            if (edtMobileNumber.requestFocus()) {
                setEditTextFocus(edtMobileNumber);
            }
            edtMobileNumber.setError(getDockActivity().getResources().getString(R.string.enter_MobileNum));
            return false;
        } else if (edtPassword.getText().toString().length() < 6) {
            edtPassword.setError(getDockActivity().getResources().getString(R.string.enter_valid_password));
            if (edtPassword.requestFocus()) {
                setEditTextFocus(edtPassword);
            }
            return false;
        } else if (!edtConfirmPassword.getText().toString().equals(edtPassword.getText().toString())) {
            edtConfirmPassword.setError(getDockActivity().getResources().getString(R.string.confirm_password_error));
            if (edtConfirmPassword.requestFocus()) {
                setEditTextFocus(edtConfirmPassword);
            }
            return false;
        } else if (!checkboxTermCondition.isChecked()) {
            UIHelper.showShortToastInCenter(getDockActivity(), getDockActivity().getResources().getString(R.string.select_term_condition_error));
            return false;
        } else if (profilePath == null || profilePath.equals("") || profilePath.isEmpty()) {
            UIHelper.showShortToastInCenter(getDockActivity(), getDockActivity().getResources().getString(R.string.Upload_Profile_Pic));
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void setImage(String imagePath) {
        if (imagePath != null) {

            profilePic = new File(imagePath);
            profilePath = imagePath;
           /* Picasso.with(getDockActivity())
                    .load("file:///" + imagePath)
                    .into(imgProfile);*/
            imageLoader.displayImage("file:///" + imagePath, imgProfile);
            try {
                //profilePicBitmap = new Compressor(getDockActivity()).compressToBitmap(new File(imagePath));
                profilePicBitmap = SiliCompressor.with(getDockActivity()).getCompressBitmap(imagePath);
            } catch (Exception e) {
                e.printStackTrace();
            }


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

    private void SetFaceBookImage(String imagePath) {
        if (imagePath != null) {
            //profilePic = new File(imagePath);
           /* profilePic = new File(imagePath);
            profilePath = imagePath;
            Glide.with(getDockActivity())
                    .load(imagePath)
                    .into(CircularImageSharePop);*/
            Picasso.with(getDockActivity()).load(imagePath).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    try {
                        imgProfile.setImageBitmap(bitmap);
                        String root = Environment.getExternalStorageDirectory().toString();
                        profilePic = new File(root + "/tanfit");

                        if (!profilePic.exists()) {
                            profilePic.mkdirs();
                        }

                        String name = new Date().toString() + ".jpg";
                        profilePic = new File(profilePic, name);
                        profilePicBitmap = bitmap;
                        FileOutputStream out = new FileOutputStream(profilePic);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);

                        out.flush();
                        out.close();
                    } catch (Exception e) {
                        // some action
                    }
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
            //  ImageLoader.getInstance().displayImage(
            //     "file:///" +imagePath, CircularImageSharePop);
        }
    }

    private void clearViews() {
        edtFullname.setText("");
        edtEmail.setText("");
        edtMobileNumber.setText("");
        edtPassword.setText("");
        edtConfirmPassword.setText("");
    }

    @Override
    public void onGoogleSignInResult(GoogleSignInAccount result) {

        clearViews();
        Log.e(TAG, "display name: " + result.getDisplayName());

        String personName = result.getDisplayName();
        if (result.getPhotoUrl() != null) {
            String personPhotoUrl = result.getPhotoUrl().toString();
            SetFaceBookImage(personPhotoUrl);
        }
        String email = result.getEmail();

        Log.e(TAG, "Name: " + personName + ", email: " + email
                + ", Image: ");
        mSocialMediaPlatform = AppConstants.SOCIAL_MEDIA_TYPE_GOOGLE;
        mSocialMediaID = result.getId();
        edtFullname.setText(result.getDisplayName() + "");
        edtEmail.setText(result.getEmail() + "");
    }





    private void requestCameraPermission() {
        Dexter.withActivity(getDockActivity())
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {

                        if (report.areAllPermissionsGranted()) {
                            CameraHelper.uploadPhotoDialog(getMainActivity());
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            requestCameraPermission();

                        } else if (report.getDeniedPermissionResponses().size() > 0) {
                            requestCameraPermission();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        UIHelper.showShortToastInCenter(getDockActivity(), "Grant Camera And Storage Permission to processed");
                        openSettings();
                    }
                })

                .onSameThread()
                .check();


    }

    @Override
    public void onResume() {
        super.onResume();
        if (profilePath != null && !profilePath.equals(""))
            imageLoader.displayImage("file:///" + profilePath, imgProfile);
    }


    private void openSettings() {

        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        Uri uri = Uri.fromParts("package", getDockActivity().getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }
}