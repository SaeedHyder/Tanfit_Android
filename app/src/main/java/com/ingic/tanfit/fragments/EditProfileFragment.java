package com.ingic.tanfit.fragments;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.iceteck.silicompressorr.SiliCompressor;
import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.UpdateUserEnt;
import com.ingic.tanfit.entities.UserAllDataEnt;
import com.ingic.tanfit.entities.UserEnt;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.global.AppConstants;
import com.ingic.tanfit.global.WebServiceConstants;
import com.ingic.tanfit.helpers.CameraHelper;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * Created by saeedhyder on 1/11/2018.
 */
public class EditProfileFragment extends BaseFragment implements ImageSetter {
    @BindView(R.id.CircularImageSharePop1)
    CircleImageView CircularImageSharePop1;
    @BindView(R.id.img_profile)
    CircleImageView imgProfile;
    @BindView(R.id.btn_camera)
    ImageView btnCamera;
    @BindView(R.id.ll_ProfileImage)
    RelativeLayout llProfileImage;
    @BindView(R.id.edt_fullname)
    AnyEditTextView edtFullname;
    @BindView(R.id.edt_email)
    AnyTextView edtEmail;
    @BindView(R.id.edt_phoneNumber)
    AnyTextView edtPhone;
    @BindView(R.id.edt_gender)
    AnyTextView edtGender;
    @BindView(R.id.edt_mobile_number)
    AnyEditTextView edtMobileNumber;
    @BindView(R.id.edt_height)
    AnyEditTextView edtHeight;
    @BindView(R.id.edt_weight)
    AnyEditTextView edtWeight;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    Unbinder unbinder;

    private File profilePic;
    private Bitmap profilePicBitmap;
    private String profilePath;
    private ImageLoader imageLoader;
    private String gender;

    public static EditProfileFragment newInstance() {
        Bundle args = new Bundle();

        EditProfileFragment fragment = new EditProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (prefHelper.isLanguagePersian()) {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
          /*  edtHeight.setImeOptions(EditorInfo.IME_ACTION_DONE);
            edtWeight.setImeOptions(EditorInfo.IME_ACTION_NEXT);*/
        } else {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
           /* edtHeight.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            edtWeight.setImeOptions(EditorInfo.IME_ACTION_DONE);*/
        }

        setUserData();
        getMainActivity().setImageSetter(this);

    }

    private void setUserData() {


      /*  Glide.with(getDockActivity()).asGif()
                .load(prefHelper.getUserAllData().getUserThumbnailImage())
                .apply(bitmapTransform(new CircleCrop()))
                .apply(new RequestOptions()
                        .placeholder(R.drawable.com_facebook_profile_picture_blank_square))
                .into(imgProfile);*/

        imageLoader.displayImage(prefHelper.getUserAllData().getUserThumbnailImage(), imgProfile);

        edtFullname.setText(prefHelper.getUserAllData().getFullName() + "");
        edtEmail.setText(prefHelper.getUserAllData().getEmail() + "");
        edtGender.setText(prefHelper.getUserAllData().getGender() + "");
        edtPhone.setText(prefHelper.getUserAllData().getPhoneNumber() + "");
        if (prefHelper.getUserAllData().getEmergencyContact() != null && !prefHelper.getUserAllData().getEmergencyContact().equals("")) {
            edtMobileNumber.setText(prefHelper.getUserAllData().getEmergencyContact() + "");
        }
        if (prefHelper.getUserAllData().getWeight() != null && !prefHelper.getUserAllData().getWeight().equals("")) {
            edtWeight.setText(prefHelper.getUserAllData().getWeight() + "");
        }
        if (prefHelper.getUserAllData().getHeight() != null && !prefHelper.getUserAllData().getHeight().equals("")) {
            edtHeight.setText(prefHelper.getUserAllData().getHeight() + "");
        }


    }


    private boolean isValidated() {
        if (edtFullname.getText() == null || (edtFullname.getText().toString().isEmpty())) {
            if (edtFullname.requestFocus()) {
                setEditTextFocus(edtFullname);
            }
            edtFullname.setError(getDockActivity().getResources().getString(R.string.enter_FullName));
            return false;
        } else if (edtMobileNumber.getText() == null || (edtMobileNumber.getText().toString().isEmpty())) {
            if (edtMobileNumber.requestFocus()) {
                setEditTextFocus(edtMobileNumber);
            }
            edtMobileNumber.setError(getDockActivity().getResources().getString(R.string.enter_MobileNum));
            return false;
        } else if (edtHeight.getText() == null || (edtHeight.getText().toString().isEmpty())) {
            if (edtHeight.requestFocus()) {
                setEditTextFocus(edtHeight);
            }
            edtHeight.setError(getDockActivity().getResources().getString(R.string.enter_Height));
            return false;
        } else if (edtWeight.getText() == null || (edtWeight.getText().toString().isEmpty())) {
            if (edtWeight.requestFocus()) {
                setEditTextFocus(edtWeight);
            }
            edtWeight.setError(getDockActivity().getResources().getString(R.string.enter_Weight));
            return false;
        } else {
            return true;
        }

    }

    private String convertToBase64(String imagePath)

    {

        Bitmap bm = BitmapFactory.decodeFile(imagePath);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bm.compress(Bitmap.CompressFormat.JPEG, 60, baos);

        byte[] byteArrayImage = baos.toByteArray();

        String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);

        return encodedImage;

    }

    private void UpdateUser() {

        String image = "";
        if (profilePicBitmap != null && !profilePicBitmap.equals("")) {
            image = AppConstants.Base64+convertBitmapToBase64(profilePicBitmap);
        } else {
            image = prefHelper.getUserAllData().getUserThumbnailImage();
        }


        serviceHelper.enqueueCall(headerWebService.updateProfile(
                prefHelper.getUserAllData().getId(),
                edtEmail.getText().toString(),
                edtFullname.getText().toString(),
                prefHelper.getUserAllData().getPhoneNumber() + "",
                image,
                edtHeight.getText().toString(),
                edtWeight.getText().toString(),
                edtMobileNumber.getText().toString(),
                prefHelper.getUserAllData().getGenderId() + "", false),
                WebServiceConstants.updateUser);

    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getDockActivity().getResources().getString(R.string.edit_profile));
    }



    public String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        return encoded;
    }


    @Override
    public void setImage(String imagePath) {
        if (imagePath != null) {

           //  profilePic = new File(imagePath);

           // profilePath = imagePath;
          /*  Picasso.with(getDockActivity())
                    .load("file:///" + imagePath)
                    .into(imgProfile);*/

            imageLoader.displayImage("file:///" + imagePath, imgProfile);
            try {
                profilePicBitmap = SiliCompressor.with(getDockActivity()).getCompressBitmap(imagePath);
            } catch (Exception e) {
                e.printStackTrace();
                UIHelper.showShortToastInCenter(getDockActivity(), getDockActivity().getResources().getString(R.string.internet_issue));
            }
        }
    }

    @Override
    public void setFilePath(String filePath) {

    }

    @Override
    public void setVideo(String videoPath, String VideoThumbail) {

    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {

            case WebServiceConstants.updateUser:
                UpdateUserEnt UserResult = (UpdateUserEnt) result;

                UserAllDataEnt updateUser = prefHelper.getUserAllData();
                UserEnt homeUserData = prefHelper.getUser();
                updateUser.setFullName(edtFullname.getText().toString());
                updateUser.setEmergencyContact(edtMobileNumber.getText().toString());
                updateUser.setHeight(Integer.valueOf(edtHeight.getText().toString()));
                updateUser.setWeight(Integer.valueOf(edtWeight.getText().toString()));


                if (profilePicBitmap != null && !profilePicBitmap.equals("") && UserResult.getUserThumbnailImage() != null) {
                    updateUser.setUserThumbnailImage(UserResult.getUserThumbnailImage());
                    homeUserData.setUserThumbnailImage(UserResult.getUserThumbnailImage());
                } else {
                    updateUser.setUserThumbnailImage(prefHelper.getUserAllData().getUserThumbnailImage());
                    homeUserData.setUserThumbnailImage(prefHelper.getUserAllData().getUserThumbnailImage());
                }
                prefHelper.putUserAllData(updateUser);
                prefHelper.putUser(homeUserData);

                //  getMainActivity().popFragment();
                UIHelper.showShortToastInCenter(getDockActivity(), getDockActivity().getResources().getString(R.string.profile_updated));
                getDockActivity().replaceDockableFragment(MainFragment.newInstance(), "MainFragment");
                //  getDockActivity().replaceDockableFragment(MyProfileFragment.newInstance(), "MyProfileFragment");
                break;
        }
    }


    @OnClick({R.id.btn_camera, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_camera:
                requestCameraPermission();

                break;
            case R.id.btn_submit:
                if (isValidated()) {
                    UpdateUser();
                }
                break;
        }
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
