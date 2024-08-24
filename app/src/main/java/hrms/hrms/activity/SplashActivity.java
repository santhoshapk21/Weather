package hrms.hrms.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;
import com.hris365.R;
import com.hris365.databinding.ActivitySplashBinding;
import com.nostra13.universalimageloader.BuildConfig;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import hrms.hrms.baseclass.BaseAppCompactActivity;
import hrms.hrms.retrofit.OnApiResponseListner;
import hrms.hrms.retrofit.model.versionUpdate.ResponseVersion;
import retrofit2.Call;

public class SplashActivity extends BaseAppCompactActivity implements OnApiResponseListner {

    private CountDownTimer countDownTimer;
    private Call<?> appVersionApi;
    private ActivitySplashBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        internetConnection();
//        Fabric.with(this, new Crashlytics());
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

     getCertificatePin();
    }

    private void getCertificatePin() {
        InputStream inputStream = getResources().openRawResource(R.raw.kpmanishapi1);
        CertificateFactory certificateFactory = null;
        try {
            certificateFactory = CertificateFactory.getInstance("X.509");
            X509Certificate certificate = (X509Certificate) certificateFactory.generateCertificate(inputStream);
            PublicKey publicKey = certificate.getPublicKey();
            byte[] publicKeyBytes = publicKey.getEncoded();
            Log.e("PublicKey",sha256Base64(publicKey.getEncoded()));
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        }
    }

    private String sha256Base64(byte[] input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input);
            return Base64.encodeToString(hash,0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        AppVersionApiCall();
        countDownTimer = new CountDownTimer(3000, 3000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {

                if (getString(TYPE.ACCESSTOKEN) != null && !getString(TYPE.ACCESSTOKEN).equals(""))
                {
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    finish();
                } else {
                    navigateIntent();
                }
            }
        }.start();
    }

    private void navigateIntent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                            SplashActivity.this,
//                            new Pair<View, String>(findViewById(R.id.iv_logo),
//                            getString(R.string.transition_logo)),
//                    new Pair<View, String>(findViewById(R.id.iv_hris_text),
//                            getString(R.string.transition_logo_hris)));
            appVersionApi.cancel();
            startActivity(
                    new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        } else {
            startActivity(
                    new Intent(SplashActivity.this,
                            LoginActivity.class)
            );
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        countDownTimer.cancel();
        appVersionApi.cancel();
    }

    @Override
    public void onResponseComplete(Object clsGson, int requestCode,int responseCode) {

        if (clsGson != null) {
            ResponseVersion model = (ResponseVersion) clsGson;
            if (model.getStatus() != 0) {
                showDialog(model.getMessage() + "", model.getStatus());
            }
        }
    }

    @Override
    public void onResponseError(String errorMessage, int requestCode) {
        if (errorMessage != null
                && errorMessage.equals("Socket closed") || errorMessage.equals("Canceled")) {

        } else if (errorMessage != null)
            showSnackBar(mBinding.getRoot(), errorMessage, Snackbar.LENGTH_SHORT);
    }

    private void AppVersionApiCall() {
        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;
        appVersionApi = getApiTask().doGetAppVersion(versionName, TYPE.Android.toString(), this);
    }

    public void showDialog(@NonNull String message, final int code) {

        if (countDownTimer != null)
            countDownTimer.cancel();

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (code == 1)
                            navigateIntent();
                        else
                            finish();
                    }
                });
        alertDialog.setTitle("");
        alertDialog.show();
    }

}
