package ziaetaiba.com.zia_e_magazine.Connection;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ziaetaiba.com.zia_e_magazine.API.Api_URLS;

/**
 * Created by HAMI on 17/07/2018.
 */

public class Connect_Server {

    private static Retrofit retrofit = null;

    public static Retrofit getApiClient() {
        if (retrofit == null) {

            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(180, TimeUnit.SECONDS)
                    .readTimeout(180, TimeUnit.SECONDS)
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .writeTimeout(180, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder().baseUrl(Api_URLS.BASE_API)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
