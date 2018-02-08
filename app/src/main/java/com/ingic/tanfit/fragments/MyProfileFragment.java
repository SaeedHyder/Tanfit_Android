package com.ingic.tanfit.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ingic.tanfit.R;
import com.ingic.tanfit.entities.AppDefaultSettingEnt;
import com.ingic.tanfit.entities.ProfileEnt;
import com.ingic.tanfit.entities.StudioLogo;
import com.ingic.tanfit.entities.UserSubscription;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.global.AppConstants;
import com.ingic.tanfit.global.WebServiceConstants;
import com.ingic.tanfit.helpers.DialogHelper;
import com.ingic.tanfit.interfaces.OnSwipeListener;
import com.ingic.tanfit.interfaces.RecyclerViewItemListener;
import com.ingic.tanfit.ui.binders.ProfileItemBinder;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.CustomRecyclerView;
import com.ingic.tanfit.ui.views.TitleBar;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;

/**
 * Created by saeedhyder on gym_image_11/25/2017.
 */
public class MyProfileFragment extends BaseFragment implements RecyclerViewItemListener {

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.btnLogout)
    ImageView btnLogout;
    @BindView(R.id.txt_name)
    AnyTextView txtName;
    @BindView(R.id.txt_email)
    AnyTextView txtEmail;
    @BindView(R.id.rv_gyms)
    CustomRecyclerView rvGyms;
    @BindView(R.id.txt_bookingHistory)
    AnyTextView txtBookingHistory;
    @BindView(R.id.txt_manageSubscription)
    AnyTextView txtManageSubscription;
    @BindView(R.id.txt_MyFavorites)
    AnyTextView txtMyFavorites;
    @BindView(R.id.txt_changePassword)
    AnyTextView txtChangePassword;
    @BindView(R.id.txt_settings)
    AnyTextView txtSettings;
    @BindView(R.id.txt_aboutUs)
    AnyTextView txtAboutUs;
    @BindView(R.id.txt_contactUs)
    AnyTextView txtContactUs;
    @BindView(R.id.txtMyClasses)
    AnyTextView txtMyClasses;

    Unbinder unbinder;
    OverlapDecoration overlapDecoration;
    LinearLayoutManager layoutManager;
    @BindView(R.id.iv_profile)
    ImageView ivProfile;
    @BindView(R.id.btn_edit)
    ImageView btnEdit;
    @BindView(R.id.txt_emergencyNumber)
    AnyTextView txtEmergencyNumber;
    @BindView(R.id.txt_weight)
    AnyTextView txtWeight;
    @BindView(R.id.txt_height)
    AnyTextView txtHeight;
    private ArrayList<ProfileEnt> userCollections;
    private File profileImage;
    private boolean isSubscribed=false;
    private int itemPosition;

    private ImageLoader imageLoader;


    public static MyProfileFragment newInstance() {
        Bundle args = new Bundle();

        MyProfileFragment fragment = new MyProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageLoader = ImageLoader.getInstance();
        if (getArguments() != null) {
        }

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myprofile, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefHelper.setIsFromStudio(false);
        setProfileData();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void setProfileData() {

        userCollections = new ArrayList<>();

        if (prefHelper.getUserAllData() != null) {

            Picasso.with(getDockActivity()).load(prefHelper.getUserAllData().getUserThumbnailImage()).placeholder(getResources().getDrawable(R.drawable.image_place_holder)).into(ivProfile);
            // imageLoader.displayImage(prefHelper.getUserAllData().getUserThumbnailImage(), ivProfile);
            txtName.setText(prefHelper.getUserAllData().getFullName() + "");
            txtEmail.setText(prefHelper.getUserAllData().getEmail() + "");
            txtEmergencyNumber.setText(prefHelper.getUserAllData().getPhoneNumber()+"");

            if(prefHelper.getUserAllData().getHeight()!=null && !prefHelper.getUserAllData().getHeight().equals("")){
                txtHeight.setText(prefHelper.getUserAllData().getHeight()+" ln");
            }
            if(prefHelper.getUserAllData().getWeight()!=null && !prefHelper.getUserAllData().getWeight().equals("")){
                txtWeight.setText(prefHelper.getUserAllData().getWeight()+" KG");
            }
           /* if(prefHelper.getUserAllData().getEmergencyContact()!=null && !prefHelper.getUserAllData().getEmergencyContact().equals("")){

            }*/

            for (StudioLogo item : prefHelper.getUserAllData().getStudioLogos()) {
                userCollections.add(new ProfileEnt(item.getStudioImage()));
            }
        }

        if (userCollections.size() <= 0) {
            rvGyms.setVisibility(View.GONE);
            txtMyClasses.setVisibility(View.GONE);
        } else {
            rvGyms.setVisibility(View.VISIBLE);
            txtMyClasses.setVisibility(View.VISIBLE);
        }


        overlapDecoration = new OverlapDecoration(Math.round(getResources().getDimension(R.dimen.x40_)));

        rvGyms.addItemDecoration(overlapDecoration);

        layoutManager = new LinearLayoutManager(getDockActivity(), LinearLayoutManager.HORIZONTAL, false);


        rvGyms.BindRecyclerView(new ProfileItemBinder(this), userCollections,
                layoutManager, new SlideInRightAnimator());
        //recyclerHorizontlListner();
    }

    @OnClick({R.id.btnBack, R.id.btnLogout, R.id.txt_bookingHistory, R.id.txt_manageSubscription, R.id.txt_MyFavorites, R.id.txt_changePassword, R.id.txt_settings, R.id.txt_aboutUs, R.id.txt_contactUs})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                getDockActivity().popFragment();
                break;
            case R.id.btnLogout:
                final DialogHelper dialogHelper = new DialogHelper(getDockActivity());
                prefHelper.setIsGetUser(false);
                dialogHelper.initlogout(R.layout.logout_dialog, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        serviceHelper.enqueueCall(headerWebService.logout(prefHelper.getUserAllData().getId()), WebServiceConstants.logout);
                       /* prefHelper.setLoginStatus(false);
                        getMainActivity().popBackStackTillEntry(0);
                        getDockActivity().replaceDockableFragment(LoginFragment.newInstance(), "LoginFragment");*/
                        dialogHelper.hideDialog();

                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogHelper.hideDialog();
                    }
                });
                dialogHelper.showDialog();

                break;
            case R.id.txt_bookingHistory:
                getDockActivity().replaceDockableFragment(BookingHistoryTabLayFragment.newInstance(), "BookingHistoryTabLayFragment");
                break;
            case R.id.txt_manageSubscription:

                if (prefHelper.getUserAllData().getUserSubscription().size() > 0) {

                    for (int i=0;i<prefHelper.getUserAllData().getUserSubscription().size();i++) {
                        if (prefHelper.getUserAllData().getUserSubscription().get(i).getUserSubscriptionStatusId() == 1) {
                            isSubscribed=true;
                            itemPosition=i;
                        }
                    }

                    if (isSubscribed) {
                        getDockActivity().replaceDockableFragment(MySubscriptionFragment.newInstance(itemPosition), "MySubscriptionFragment");
                    } else {
                        MainFragment mainFragment = MainFragment.newInstance();
                        mainFragment.setStartWithTab(AppConstants.TAB_SUBSCRIBE);
                        getDockActivity().replaceDockableFragment(mainFragment);
                        break;
                    }

                } else {
                    MainFragment mainFragment = MainFragment.newInstance();
                    mainFragment.setStartWithTab(AppConstants.TAB_SUBSCRIBE);
                    getDockActivity().replaceDockableFragment(mainFragment);
//                    getDockActivity().replaceDockableFragment(MainFragment.newInstance("SubscriptionScreen"), "MainFragment");
                }
                break;
            case R.id.txt_MyFavorites:
                getDockActivity().replaceDockableFragment(FavoriteFragment.newInstance(), "FavoriteFragment");
                break;
            case R.id.txt_changePassword:
                getDockActivity().replaceDockableFragment(ChangePasswordFragment.newInstance(), "ChangePasswordFragment");
                break;
            case R.id.txt_settings:
                getDockActivity().replaceDockableFragment(SettingFragment.newInstance(), "SettingFragment");
                break;
            case R.id.txt_aboutUs:
                getDockActivity().replaceDockableFragment(AboutUsFragment.newInstance(), "AboutUsFragment");
                break;
            case R.id.txt_contactUs:
                getDockActivity().replaceDockableFragment(ContactUsFragment.newInstance(), "ContactUsFragment");
                break;
        }
    }

    @Override
    public void onRecyclerItemClicked(Object Ent, int position) {


    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {

            case WebServiceConstants.logout:
                prefHelper.setLoginStatus(false);
                prefHelper.removeRideSessionPreferences();
                getMainActivity().popBackStackTillEntry(0);
                getDockActivity().replaceDockableFragment(LoginFragment.newInstance(), "LoginFragment");
                break;
        }
    }

    public void recyclerHorizontlListner() {

       /* rvGyms.addOnScrollListener(new RecyclerView.OnScrollListener() {
//           @Override
//           public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//               super.onScrollStateChanged(recyclerView, newState);
//               if (overlapDecoration != null)
//                   rvGyms.removeItemDecoration(overlapDecoration);
//
//               overlapDecoration = new OverlapDecoration(0);
//               rvGyms.addItemDecoration(overlapDecoration);
//               rvGyms.notifyDataSetChanged();
//           }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dx > 0) {
                    if (overlapDecoration != null)
                        rvGyms.removeItemDecoration(overlapDecoration);

                    overlapDecoration = new OverlapDecoration(-110);
                    rvGyms.addItemDecoration(overlapDecoration);
                    rvGyms.notifyDataSetChanged();
                } else if (dx < 0) {
                    if (overlapDecoration != null)
                        rvGyms.removeItemDecoration(overlapDecoration);

                    overlapDecoration = new OverlapDecoration(0);
                    rvGyms.addItemDecoration(overlapDecoration);
                    rvGyms.notifyDataSetChanged();
                }
            }
        });*/

        final GestureDetector detector = new GestureDetector(getDockActivity(), new OnSwipeListener() {

            @Override
            public boolean onSwipe(Direction direction) {
                if (direction == Direction.left) {
                    //do your stuff


                    overlapDecoration.setPadding(Math.round(getResources().getDimension(R.dimen.x5)));
                    rvGyms.invalidateItemDecorations();

                }

                if (direction == Direction.right) {
                    //do your stuff
                    overlapDecoration.setPadding(Math.round(getResources().getDimension(R.dimen.x40_)));
                    rvGyms.invalidateItemDecorations();
                }
                return true;
            }


        });
        rvGyms.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                detector.onTouchEvent(e);
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
       /* rvGyms.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dx > gym_image_10) {
                    overlapDecoration.setPadding(Math.round(getResources().getDimension(R.dimen.x40_)));
                    rvGyms.invalidateItemDecorations();
                } else if (dx < -gym_image_10) {
                    overlapDecoration.setPadding(Math.round(getResources().getDimension(R.dimen.x5)));
                    rvGyms.invalidateItemDecorations();
                }
            }
        });*/


    }

    @OnClick(R.id.btn_edit)
    public void onViewClicked() {
        getDockActivity().replaceDockableFragment(EditProfileFragment.newInstance(),"EditProfileFragment");
    }

    public class OverlapDecoration extends RecyclerView.ItemDecoration {

        private final int vertOverlap = -110;
        int negativePadding = Math.round(getResources().getDimension(R.dimen.x40_));
        int positivePadding = Math.round(getResources().getDimension(R.dimen.x5));
        private int padding = 0;

        public OverlapDecoration(int padding) {
            this.padding = padding;
        }

        public void setPadding(int padding) {
            this.padding = padding;
        }

        @Override
        public void getItemOffsets(final Rect outRect, final View view, RecyclerView parent, RecyclerView.State state) {
            final int itemPosition = parent.getChildAdapterPosition(view);
            if (itemPosition == 0) {
                return;
            }
            outRect.set(padding, 0, 0, 0);

        }

    }

    private Bitmap decodeFromBase64ToBitmap(String encodedImage)

    {

        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);

        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        return decodedByte;

    }
}


