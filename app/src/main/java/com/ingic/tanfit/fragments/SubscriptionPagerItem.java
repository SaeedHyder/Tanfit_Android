package com.ingic.tanfit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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
 * Created by saeedhyder on 11/23/2017.
 */
public class SubscriptionPagerItem extends BaseFragment {
    @BindView(R.id.txt_title)
    AnyTextView txtTitle;
    @BindView(R.id.txt_title1)
    AnyTextView txtTitle1;
    @BindView(R.id.header)
    LinearLayout header;
    @BindView(R.id.txt_description)
    AnyTextView txtDescription;
    @BindView(R.id.txt_description2)
    AnyTextView txtDescription2;
    @BindView(R.id.btn_SubcribeNow)
    Button btnSubcribeNow;
    @BindView(R.id.bottom)
    RelativeLayout bottom;
    Unbinder unbinder;

    public static SubscriptionPagerItem newInstance() {
        Bundle args = new Bundle();

        SubscriptionPagerItem fragment = new SubscriptionPagerItem();
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
        View view = inflater.inflate(R.layout.row_item_subscription, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading("");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_SubcribeNow)
    public void onViewClicked() {
        UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.implemented_beta));
    }
}
