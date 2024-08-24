package hrms.hrms.baseclass;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.hris365.R;
import com.nostra13.universalimageloader.BuildConfig;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import hrms.hrms.retrofit.HttpClientProvider;
import hrms.hrms.retrofit.HttpLoggingInterceptor;
import hrms.hrms.retrofit.WebAPI;
import hrms.hrms.retrofit.task.ApiTask;
import hrms.hrms.utility.Utility;
import hrms.hrms.widget.ProgressBarDialog;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Yudiz on 12/10/16.
 */
public abstract class BaseAppCompactActivity extends AppCompatActivity {

    public static boolean isInternet;
    private static Retrofit retrofit;
    private static Retrofit retrofit2;
    private ProgressBarDialog progressBarDialog;
    private ApiTask apiTask;
    private SharedPreferences preference;
    private MobileDataStateChangedReceiver receiver;
    static OkHttpClient okHttpClient;
    static OkHttpClient okHttpClient2;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            httpClient.readTimeout(2, TimeUnit.MINUTES);
            httpClient.connectTimeout(2, TimeUnit.MINUTES);
            httpClient.writeTimeout(2, TimeUnit.MINUTES);

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request.Builder builder = original.newBuilder();
                    builder.method(original.method(), original.body());

                    return chain.proceed(builder.build());
                }
            });

            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                httpClient.addInterceptor(interceptor);
            } else {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
                httpClient.addInterceptor(interceptor);
            }

            retrofit = new Retrofit.Builder()
                    .baseUrl(WebAPI.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getRetrofitInstance2() {
        if (retrofit2 == null) {

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            httpClient.readTimeout(2, TimeUnit.MINUTES);
            httpClient.connectTimeout(2, TimeUnit.MINUTES);
            httpClient.writeTimeout(2, TimeUnit.MINUTES);

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request.Builder builder = original.newBuilder();
                    builder.method(original.method(), original.body());

                    return chain.proceed(builder.build());
                }
            });

            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                httpClient.addInterceptor(interceptor);
            } else {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
                httpClient.addInterceptor(interceptor);
            }

            retrofit2 = new Retrofit.Builder()
                    .baseUrl(WebAPI.BASE_URL_2)
                    .client(okHttpClient2)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit2;
    }

    public static void resetRetrofitInstance() {
        retrofit = null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressBarDialog = new ProgressBarDialog(this);
        okHttpClient = HttpClientProvider.getOkHttpClient(BaseAppCompactActivity.this);
        okHttpClient2 = HttpClientProvider.getOkHttpClient2(BaseAppCompactActivity.this);

    }

    protected void log(String msg) {
        Utility.log(msg);
    }

    protected void toast(String msg) {
        Utility.toast(getApplicationContext(), msg);
    }

    public void showDialog() {
        progressBarDialog.showDialog();
    }

    public void dismissDialog() {
        if (progressBarDialog != null)
            progressBarDialog.dismissDialog();
    }

    protected void replaceFragment(Fragment mFragment, String title) {
    }

    protected void addFragment(Fragment mFragment, String title) {
    }

    protected void replaceSharedFragment(Fragment first, Fragment second, String title, String[] transiton, View... view) {
    }

    public void showSnackBar(View rootView, String text, int duration) {
        try {
            final Snackbar sb = Snackbar.make(rootView, text, duration);
            sb.setActionTextColor(getResources().getColor(R.color.white));
            View sbView = sb.getView();
            sbView.setBackgroundColor(getResources().getColor(R.color.red));
            // TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            // textView.setTextColor(getResources().getColor(R.color.white));
            sb.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ApiTask getApiTask() {
        return (apiTask == null) ? apiTask = new ApiTask() : apiTask;
    }

    public void putString(TYPE name, String value) {
        if (preference == null)
            preference = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString(name.toString(), value);
        editor.commit();
    }

    public void putBoolean(TYPE name, boolean value) {
        if (preference == null)
            preference = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putBoolean(name.toString(), value);
        editor.commit();
    }

    public SharedPreferences getPrefrence() {
        return preference;
    }

    public String getString(TYPE fieldName) {
        if (preference == null)
            preference = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);

        return preference.getString(fieldName.toString(), "");
    }

    public boolean getBoolean(TYPE fieldName) {
        if (preference == null)
            preference = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);

        return preference.getBoolean(fieldName.toString(), false);
    }

    public void setImage(@NonNull final Context context, @NonNull final String url, @NonNull final ImageView imageView) {
        Picasso.get()
                .load(url)
                .placeholder(getResources().getDrawable(R.drawable.ic_no_image))
                .error(getResources().getDrawable(R.drawable.ic_no_image))
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(Exception e) {
                        try {
                            ImageLoader.getInstance().loadImage(url, new ImageLoadingListener() {
                                @Override
                                public void onLoadingStarted(String s, View view) {

                                }

                                @Override
                                public void onLoadingFailed(String s, View view, FailReason failReason) {

                                    if (failReason != null && failReason.getCause() != null)
                                        failReason.getCause().printStackTrace();
                                }

                                @Override
                                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                                    imageView.setImageBitmap(bitmap);
                                }

                                @Override
                                public void onLoadingCancelled(String s, View view) {

                                }
                            });
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                });
    }

    public void navigationIntentWithTransitionEffetView(Context context, Class<?> classname, View view1) {
        Intent intent = new Intent(context, classname);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Pair<View, String> pair1 = Pair.create(view1, view1.getTransitionName());

//            ActivityOptionsCompat options = ActivityOptionsCompat.
//                    makeSceneTransitionAnimation((Activity) context, pair1);
            context.startActivity(intent);
        } else {
            context.startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (receiver == null)
            receiver = new MobileDataStateChangedReceiver();
        registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    public enum TYPE {ACCESSTOKEN, PROFILEURL, COMPANYURL, DEPT, USERFULLNAME, Android, ISGPSENABLE, ISPRESENT, COMPANYNAME, COMPANYID, ISMANAGER}

    public boolean internetConnection() {
        try {
            NetworkUtil networkUtil = new NetworkUtil();
            int state = networkUtil.getConnectivityStatus(this);
            if (state == networkUtil.TYPE_WIFI || state == networkUtil.TYPE_MOBILE) {
                isInternet = true;
            } else if (state == networkUtil.TYPE_NOT_CONNECTED) {
                isInternet = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isInternet;
    }

    public class MobileDataStateChangedReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            internetConnection();
        }
    }

    class NetworkUtil {

        static final int TYPE_WIFI = 1;
        static final int TYPE_MOBILE = 2;
        static final int TYPE_NOT_CONNECTED = 0;

        public NetworkUtil() {

        }

        public int getConnectivityStatus(Context context) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (null != activeNetwork) {
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                    return TYPE_WIFI;

                if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                    return TYPE_MOBILE;
            }
            return TYPE_NOT_CONNECTED;
        }
    }
}
