package com.ingic.tanfit.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.iceteck.silicompressorr.SiliCompressor;
import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.UpdateUserEnt;
import com.ingic.tanfit.entities.UserAllDataEnt;
import com.ingic.tanfit.entities.UserEnt;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.global.WebServiceConstants;
import com.ingic.tanfit.helpers.CameraHelper;
import com.ingic.tanfit.helpers.UIHelper;
import com.ingic.tanfit.interfaces.ImageSetter;
import com.ingic.tanfit.ui.views.AnyEditTextView;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.TitleBar;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

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

        setUserData();
        getMainActivity().setImageSetter(this);
    }

    private void setUserData() {

        imageLoader.displayImage(prefHelper.getUserAllData().getUserThumbnailImage(), imgProfile);
        edtFullname.setText(prefHelper.getUserAllData().getFullName() + "");
        edtEmail.setText(prefHelper.getUserAllData().getEmail() + "");
        edtGender.setText(prefHelper.getUserAllData().getGender() + "");
        edtPhone.setText(prefHelper.getUserAllData().getPhoneNumber()+"");
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
            edtFullname.setError(getString(R.string.enter_FullName));
            return false;
        } else if (edtMobileNumber.getText() == null || (edtMobileNumber.getText().toString().isEmpty())) {
            if (edtMobileNumber.requestFocus()) {
                setEditTextFocus(edtMobileNumber);
            }
            edtMobileNumber.setError(getString(R.string.enter_MobileNum));
            return false;
        } else if (edtHeight.getText() == null || (edtHeight.getText().toString().isEmpty())) {
            if (edtHeight.requestFocus()) {
                setEditTextFocus(edtHeight);
            }
            edtHeight.setError(getString(R.string.enter_Height));
            return false;
        } else if (edtWeight.getText() == null || (edtWeight.getText().toString().isEmpty())) {
            if (edtWeight.requestFocus()) {
                setEditTextFocus(edtWeight);
            }
            edtWeight.setError(getString(R.string.enter_Weight));
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


        serviceHelper.enqueueCall(headerWebService.updateProfile(
                prefHelper.getUserAllData().getId(),
                edtEmail.getText().toString(),
                edtFullname.getText().toString(),
                //  profilePath != null ? convertToBase64(profilePath) : prefHelper.getUserAllData().getUserThumbnailImage(),
                profilePicBitmap != null ? convertBitmapToBase64(profilePicBitmap) : prefHelper.getUserAllData().getUserThumbnailImage(),
                //profilePath != null ? convertToBase64(profilePath) : prefHelper.getUserAllData().getUserThumbnailImage(),
                edtHeight.getText().toString(),
                edtWeight.getText().toString(),
                edtMobileNumber.getText().toString(),
                prefHelper.getUserAllData().getGenderId() + "", false), WebServiceConstants.updateUser);

    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.edit_profile));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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

           // profilePic = new File(imagePath);

            //profilePath = imagePath;
            Picasso.with(getDockActivity())
                    .load("file:///" + imagePath)
                    .into(imgProfile);
            try {
              profilePicBitmap = SiliCompressor.with(getDockActivity()).getCompressBitmap(imagePath);
            } catch (Exception e) {
                e.printStackTrace();
                UIHelper.showShortToastInCenter(getDockActivity(), "Internet Issue,Upload Image Again");
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


                if (profilePicBitmap != null && UserResult.getUserThumbnailImage() != null) {
                    updateUser.setUserThumbnailImage(UserResult.getUserThumbnailImage());
                    homeUserData.setUserThumbnailImage(UserResult.getUserThumbnailImage());
                } else {
                    updateUser.setUserThumbnailImage(prefHelper.getUserAllData().getUserThumbnailImage());
                    homeUserData.setUserThumbnailImage(prefHelper.getUserAllData().getUserThumbnailImage());
                }
                prefHelper.putUserAllData(updateUser);
                prefHelper.putUser(homeUserData);

                //  getMainActivity().popFragment();
                UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.profile_updated));
                getDockActivity().replaceDockableFragment(MainFragment.newInstance(), "MainFragment");
                //  getDockActivity().replaceDockableFragment(MyProfileFragment.newInstance(), "MyProfileFragment");
                break;
        }
    }


    @OnClick({R.id.btn_camera, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_camera:
                CameraHelper.uploadPhotoDialog(getMainActivity());
                break;
            case R.id.btn_submit:
                if (isValidated()) {
                    UpdateUser();
                }
                break;
        }
    }
}
