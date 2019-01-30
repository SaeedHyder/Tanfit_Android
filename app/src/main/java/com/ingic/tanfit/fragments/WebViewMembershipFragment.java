package com.ingic.tanfit.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;
import com.ingic.tanfit.R;
import com.ingic.tanfit.fragments.abstracts.BaseFragment;
import com.ingic.tanfit.global.AppConstants;
import com.ingic.tanfit.helpers.UIHelper;
import com.ingic.tanfit.ui.views.TitleBar;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class WebViewMembershipFragment extends BaseFragment {
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private static String Amount;
    private static String UniqueId;
    Unbinder unbinder;


    public static WebViewMembershipFragment newInstance() {
        Bundle args = new Bundle();
        WebViewMembershipFragment fragment = new WebViewMembershipFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static WebViewMembershipFragment newInstance(String id,String price) {
        Bundle args = new Bundle();
        Amount=price;
        UniqueId=id;
        WebViewMembershipFragment fragment = new WebViewMembershipFragment();
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
        View view = inflater.inflate(R.layout.fragment_web_view, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                WebAction();
            }
        });
        WebAction();
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading(getDockActivity().getResources().getString(R.string.payment_method));
        titleBar.showBackButton();
    }

    public void WebAction() {

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.loadUrl("https://www.tan-fit.com/sampardakh-bank-payment-gateway?Amount="+Amount+"&ResNum="+UniqueId);
        swipe.setRefreshing(true);
        webView.setWebViewClient(new WebViewClient() {

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                UIHelper.showShortToastInCenter(getDockActivity(), "Error! Please try again later.");
            }

            public void onPageFinished(WebView view, String url) {
                swipe.setRefreshing(false);
            }

            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
             //   if (url.equals("http://cavalli.stagingic.com/membership-success?id=" + Amount)) {
                if (url.equals("https://www.tan-fit.com/thanks?StateCode=0&State=OK")) {
                    getDockActivity().popBackStackTillEntry(0);
                    UIHelper.showLongToastInCenter(getDockActivity(), "Payment successful.");
                    MainFragment mainFragment = MainFragment.newInstance();
                    mainFragment.setStartWithTab(AppConstants.TAB_SUBSCRIBE);
                    getDockActivity().replaceDockableFragment(mainFragment);

                }
                else if(url.contains("https://www.tan-fit.com/thanks?StateCode=-1&State=Canceled")){
                    getDockActivity().popBackStackTillEntry(0);
                    MainFragment mainFragment = MainFragment.newInstance();
                    mainFragment.setStartWithTab(AppConstants.TAB_SUBSCRIBE);
                    getDockActivity().replaceDockableFragment(mainFragment);
                }
                else if (url.contains("https://www.tan-fit.com/thanks")){
                    getDockActivity().popBackStackTillEntry(0);
                    UIHelper.showLongToastInCenter(getDockActivity(), "Payment cancelled.");
                    MainFragment mainFragment = MainFragment.newInstance();
                    mainFragment.setStartWithTab(AppConstants.TAB_SUBSCRIBE);
                    getDockActivity().replaceDockableFragment(mainFragment);
                }
                // }

            }
        });
    }


}
