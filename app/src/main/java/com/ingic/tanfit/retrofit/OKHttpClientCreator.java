package com.ingic.tanfit.retrofit;


import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.ingic.tanfit.global.AppConstants;
import com.ingic.tanfit.helpers.BasePreferenceHelper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OKHttpClientCreator {

    private static NotificationManager mNotifyManager;
    private static NotificationCompat.Builder mBuilder;
    private static BasePreferenceHelper preferenceHelper;

    public static OkHttpClient createCustomInterceptorClientwithHeader(Context context) {

        preferenceHelper = new BasePreferenceHelper(context);

        Log.e("Header Token", AppConstants.HeaderToken);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()

                .addNetworkInterceptor(new CustomInterceptor(progressListener))
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                                    @Override
                                    public Response intercept(Chain chain) throws IOException {
                                        Request request = chain.request().newBuilder()
                                                .addHeader("Accept-Encoding", "identity")
                                                .addHeader("Authorization", "Bearer " +  preferenceHelper.getAccess_Token())
                                                .build();
                                        return chain.proceed(request);
                                    }

                                }
                )

                .build();


        return client;


    }

    public static OkHttpClient createCustomInterceptorClient(Context context) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new CustomInterceptor(progressListener))
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                                    @Override
                                    public Response intercept(Chain chain) throws IOException {
                                        Request request = chain.request().newBuilder().addHeader("Accept-Encoding", "identity").build();
                                        return chain.proceed(request);
                                    }

                                }

                )
                .build();


        return client;


    }

    public static OkHttpClient createDefaultInterceptorClient(Context context) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response originalResponse = chain.proceed(chain.request());
                        return originalResponse.newBuilder()
                                .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                                .build();
                    }
                })
                .addInterceptor(loggingInterceptor)
                .build();


        return client;

    }

    final static ProgressResponseBody.ProgressListener progressListener = new ProgressResponseBody.ProgressListener() {
        @Override
        public void update(long bytesRead, long contentLength, boolean done) {
            int percent = (int) ((100 * bytesRead) / contentLength);
        }
    };

}
