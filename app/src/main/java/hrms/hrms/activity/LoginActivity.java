package hrms.hrms.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;
import com.hris365.R;
import com.hris365.databinding.ActivityLoginBinding;

import hrms.hrms.baseclass.BaseAppCompactActivity;
import hrms.hrms.retrofit.OnApiResponseListner;
import hrms.hrms.retrofit.RequestCode;
import hrms.hrms.retrofit.model.login.ResponseLogin;

public class LoginActivity extends BaseAppCompactActivity implements
        View.OnClickListener, OnApiResponseListner {

    private ActivityLoginBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        mBinding.actTvLogin.setOnClickListener(this);
        mBinding.actTvForgotPassword.setOnClickListener(this);
        mBinding.actTvEmpPassword.setTransformationMethod(new PasswordTransformationMethod());
//        if (BuildConfig.DEBUG) {
//            mBinding.actTvEmpDomainName.setText("Demo");
//            mBinding.actTvEmpId.setText("SW30005");
//            mBinding.actTvEmpPassword.setText("3kb5xb");
//        }
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.act_tv_login) {
            if (checkValidation()) {
                loginApiCall();
                mBinding.actTvLogin.setClickable(false);
            }
        } else if (view.getId() == R.id.act_tv_forgot_password) {
            navigationIntentWithTransitionEffetView(this,
                    ForgotPasswordActivity.class, mBinding.actTvLogin);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        } else {
            finish();
        }
    }

    @Override
    public void onResponseComplete(Object clsGson, int requestCode, int responseCode) {
        mBinding.actTvLogin.setClickable(true);
        if (requestCode == RequestCode.LOGIN) {
            if (clsGson != null) {
                ResponseLogin user = (ResponseLogin) clsGson;
                putString(TYPE.ACCESSTOKEN, "Basic " + user.getUserToken());
                putString(TYPE.COMPANYURL, user.getCompanyLogo());
                putString(TYPE.PROFILEURL, user.getProfilePicture());
                putString(TYPE.DEPT, user.getDesignation());
                putString(TYPE.COMPANYNAME, user.getCompanyName());
                putString(TYPE.COMPANYID, user.getCompanyID());
                putString(TYPE.USERFULLNAME, user.getFullName());
                //putBoolean(TYPE.ISGPSENABLE, user.isIsGpsEnable());
                putBoolean(TYPE.ISGPSENABLE, true);
                putBoolean(TYPE.ISMANAGER, user.isIsManager());
                doRegisterToken(getString(TYPE.ACCESSTOKEN),
//                        FirebaseInstanceId.getInstance().getToken());
                        "ABC-123");
            }
        } else {
            dismissDialog();
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }
    }

    @Override
    public void onResponseError(String errorMessage, int requestCode) {
        dismissDialog();
        mBinding.actTvLogin.setClickable(true);
        if (errorMessage != null && mBinding.getRoot() != null)
            showSnackBar(mBinding.getRoot(), errorMessage, Snackbar.LENGTH_LONG);
    }

    private void loginApiCall() {
        showDialog();
        getApiTask().doLogin(mBinding.actTvEmpDomainName.getText().toString(),
                mBinding.actTvEmpId.getText().toString(),
                mBinding.actTvEmpPassword.getText().toString().trim(),
                this
        );
    }

    private void doRegisterToken(String accessToken, String token) {
        getApiTask().doRegisterToken(accessToken, token, TYPE.Android.toString(), this);
    }

    public boolean checkValidation() {
        if (mBinding.actTvEmpDomainName.getText().toString().equals("")) {
            showSnackBar(mBinding.getRoot(), getString(R.string.str_error_domain_name), Snackbar.LENGTH_SHORT);
            return false;
        } else if (mBinding.actTvEmpId.getText().toString().equals("")) {
            showSnackBar(mBinding.getRoot(), getString(R.string.str_error_emp_id), Snackbar.LENGTH_SHORT);
            return false;
        } else if (mBinding.actTvEmpPassword.getText().toString().equals("")) {
            showSnackBar(mBinding.getRoot(), getString(R.string.str_error_password), Snackbar.LENGTH_SHORT);
            return false;
        }
        return true;
    }

}
