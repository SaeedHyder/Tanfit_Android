package com.ingic.tanfit.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ingic.tanfit.R;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.helpers.UIHelper;
import com.ingic.tanfit.ui.views.AnyTextView;
import com.ingic.tanfit.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by saeedhyder on 11/22/2017.
 */
public class ContactUsFragment extends BaseFragment {
    @BindView(R.id.txt_address)
    AnyTextView txtAddress;
    @BindView(R.id.txt_question)
    AnyTextView txtQuestion;
    @BindView(R.id.btn_visit_website)
    Button btnVisitWebsite;
    @BindView(R.id.txt_aboutUs)
    AnyTextView txtAboutUs;
    Unbinder unbinder;

    public static ContactUsFragment newInstance() {
        Bundle args = new Bundle();

        ContactUsFragment fragment = new ContactUsFragment();
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
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtAboutUs.setText(prefHelper.getAppDefaultSetting().getContactUsEng() + "");
        txtAddress.setText(prefHelper.getAppDefaultSetting().getContactUsAddressEng() + "");

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.contact));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_visit_website)
    public void onViewClicked() {
        if (prefHelper.getAppDefaultSetting().getContactUsUrl().contains("http")) {
            Uri uri = Uri.parse(prefHelper.getAppDefaultSetting().getContactUsUrl()); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } else {
            UIHelper.showShortToastInCenter(getDockActivity(), "URL missing");
        }

    }
}
