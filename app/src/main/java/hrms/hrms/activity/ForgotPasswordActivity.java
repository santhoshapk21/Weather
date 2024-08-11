package hrms.hrms.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;
import com.hris365.R;
import com.hris365.databinding.ActivityForgotPasswordBinding;

import hrms.hrms.baseclass.BaseAppCompactActivity;
import hrms.hrms.retrofit.OnApiResponseListner;
import hrms.hrms.retrofit.model.resetPassword.ResponseResetPassword;

/**
 * Created by yudiz on 21/02/17.
 */

public class ForgotPasswordActivity extends BaseAppCompactActivity
        implements View.OnClickListener, OnApiResponseListner {

    private ActivityForgotPasswordBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password);
        mBinding.actTvLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (checkValidation())
            resetPasswordApiCall();
    }

    private void resetPasswordApiCall() {
        showDialog();
        mBinding.actTvLogin.setClickable(false);
        getApiTask().doResetPassword(mBinding.actTvEmpDomainName.getText().toString(),
                mBinding.actTvEmpId.getText().toString(),
                this
        );
    }

    @Override
    public void onResponseComplete(Object clsGson, int requestCode,int responseCode) {
        dismissDialog();
        mBinding.actTvLogin.setClickable(false);
        if (clsGson != null) {
            ResponseResetPassword model = (ResponseResetPassword) clsGson;
            Toast.makeText(this, model.getMessage() + "", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onResponseError(String errorMessage, int requestCode) {
        dismissDialog();
        mBinding.actTvLogin.setClickable(true);
        if (errorMessage != null)
            showSnackBar(mBinding.getRoot(), errorMessage, Snackbar.LENGTH_LONG);
    }

    public boolean checkValidation() {
        if (mBinding.actTvEmpDomainName.getText().toString().equals("")) {
            showSnackBar(
                    mBinding.getRoot(),
                    getString(R.string.str_error_domain_name),
                    Snackbar.LENGTH_SHORT);
            return false;
        } else if (mBinding.actTvEmpId.getText().toString().equals("")) {
            showSnackBar(
                    mBinding.getRoot(),
                    getString(R.string.str_error_emp_id),
                    Snackbar.LENGTH_SHORT);
            return false;
        }
        return true;
    }
}
