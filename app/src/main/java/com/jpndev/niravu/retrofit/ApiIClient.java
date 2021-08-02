package com.jpndev.niravu.retrofit;




import com.jpndev.niravu.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiIClient {
  public static final String BASE_URL = BuildConfig.BASE_URL;
    //public static final String BASE_URL =GLOBALURL;
    private static Retrofit retrofit = null;

    private ApiIClient() {

    }
    private static ApiInterface repoService;

    public static ApiInterface getInstance() {
        if (repoService != null) {
            return repoService;
        }
        if (retrofit == null) {
            getClient();
        }
        repoService = retrofit.create(ApiInterface.class);
        return repoService;
    }
    //https://api.suresh.beeone.co.uk/v1/getPaymentGateways
  //  {"email":"jithish@ccrb.io","userid":"858c5d2cad5cc84d9c0a7eb8f76d6d6b8d5607c7","packageType":"Upgrade","packageId":23,"device_type":"android"}
    final static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build();
    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                   /* .client(
                            new OkHttpClient.Builder().addInterceptor(new WatchTowerInterceptor())
                                    .build()
                    )*/
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}