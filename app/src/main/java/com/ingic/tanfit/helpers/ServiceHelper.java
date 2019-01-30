package com.ingic.tanfit.helpers;

import android.util.Log;

import com.ingic.tanfit.R;
import com.ingic.tanfit.activities.DockActivity;
import com.ingic.tanfit.entities.ResponseWrapper;
import com.ingic.tanfit.interfaces.webServiceResponseLisener;
import com.ingic.tanfit.retrofit.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created on 7/17/2017.
 */

public class ServiceHelper<T> {
    private webServiceResponseLisener serviceResponseLisener;
    private DockActivity context;
    private WebService webService;

    public ServiceHelper(webServiceResponseLisener serviceResponseLisener, DockActivity conttext, WebService webService) {
        this.serviceResponseLisener = serviceResponseLisener;
        this.context = conttext;
        this.webService = webService;
    }

    public void enqueueCall(Call<ResponseWrapper<T>> call, final String tag) {
        if (InternetHelper.CheckInternetConectivityandShowToast(context)) {
            context.onLoadingStarted();
            call.enqueue(new Callback<ResponseWrapper<T>>() {
                @Override
                public void onResponse(Call<ResponseWrapper<T>> call, Response<ResponseWrapper<T>> response) {
                    context.onLoadingFinished();
                    if (response != null && response.body() != null) {
                        if (response.body().isSuccess()) {
                            serviceResponseLisener.ResponseSuccess(response.body().getResult(), tag, response.body().getMessageEN());
                        } else {
                                serviceResponseLisener.ResponseFailure(tag);
                            UIHelper.showShortToastInCenter(context, response.body().getMessageEN());
                        }
                    } else {
                        UIHelper.showShortToastInCenter(context, response.message() == null || response.message().equals("") ? context.getResources().getString(R.string.responseerror) : response.message());
                    }

                }

                @Override
                public void onFailure(Call<ResponseWrapper<T>> call, Throwable t) {
                    context.onLoadingFinished();
                    t.printStackTrace();
                    Log.e(ServiceHelper.class.getSimpleName() + " by tag: " + tag, t.toString());
                }
            });
        }
    }

    public void enqueueCallHome(Call<ResponseWrapper<T>> call, final String tag) {
        if (InternetHelper.CheckInternetConectivityandShowToast(context)) {
//            context.onLoadingStarted();
            call.enqueue(new Callback<ResponseWrapper<T>>() {
                @Override
                public void onResponse(Call<ResponseWrapper<T>> call, Response<ResponseWrapper<T>> response) {
                    //   context.onLoadingFinished();
                    if (response != null && response.body() != null) {
                        if (response.body().isSuccess()) {
                            serviceResponseLisener.ResponseSuccess(response.body().getResult(), tag, response.body().getMessageEN());
                        } else {
                            serviceResponseLisener.ResponseFailure(tag);
                            UIHelper.showShortToastInCenter(context, response.body().getMessageEN());
                        }
                    } else {
                        serviceResponseLisener.ResponseFailure(tag);
                        UIHelper.showShortToastInCenter(context, response.message() == null ? context.getString(R.string.responseerror) : response.message());
                    }

                }

                @Override
                public void onFailure(Call<ResponseWrapper<T>> call, Throwable t) {
                    //   context.onLoadingFinished();
                    t.printStackTrace();
                    serviceResponseLisener.ResponseFailure(tag);
                    Log.e(ServiceHelper.class.getSimpleName() + " by tag: " + tag, t.toString());
                }
            });
        }
    }

}
