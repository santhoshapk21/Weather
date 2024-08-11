package hrms.hrms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;
import com.hris365.R;
import com.hris365.databinding.ActivityProfileDetailsBinding;

import butterknife.ButterKnife;
import hrms.hrms.baseclass.BaseAppCompactActivity;
import hrms.hrms.retrofit.OnApiResponseListner;
import hrms.hrms.retrofit.model.profile.ResponseProfile;
import retrofit2.Call;

public class ProfileActivity extends BaseAppCompactActivity implements OnApiResponseListner {

    private ActivityProfileDetailsBinding mBinding;
    private ResponseProfile responseProfile;
    private Animation alphaAnimation;
    private Call<?> getProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile_details);
        ButterKnife.bind(this);
        alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_animation);
        mBinding.activityProfileIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mBinding.activityProfileIvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBinding.activityProfileIvEdit.setClickable(false);
                Intent iv = new Intent(ProfileActivity.this, EditProfileActivity.class);
                iv.putExtra("responseProfile", responseProfile);
                startActivity(iv);
                overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        profileApi();
        mBinding.activityProfileIvEdit.setClickable(true);
    }

//    @OnClick(R.id.activity_profile_iv_back)
//    public void onBackArrowClick() {
//        super.onBackPressed();
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void profileApi() {
        showDialog();
        getProfile = getApiTask().doGetProfile(
                getString(TYPE.ACCESSTOKEN),
                this
        );
    }

    @Override
    public void onResponseComplete(Object clsGson, int requestCode,int responseCode) {
        dismissDialog();
        if (clsGson != null) {
            ResponseProfile profile = (ResponseProfile) clsGson;
            setData(profile);
            responseProfile = profile;
            mBinding.activityProfileIvEdit.setVisibility(View.VISIBLE);
        }
    }

    private void setData(ResponseProfile profile) {
        mBinding.actProfileTvName.setText(profile.getFirstName() + " " + profile.getLastName());
        mBinding.actProfileTvDesigination.setText(profile.getDesignation());
        if (profile.getCountry().equals(""))
            mBinding.actProfileTvAddress.setText(profile.getCity());
        else
            mBinding.actProfileTvAddress.setText(profile.getCity() + "," + profile.getCountry());

        mBinding.activityProfileEdOfficeNumber.setText(profile.getPhone());
        mBinding.activityProfileEdPersonalNumber.setText(profile.getMobile());
        mBinding.activityProfileEdEmail.setText(profile.getEmail());
        mBinding.activityProfileEdEmpId.setText(profile.getEmployeeID());
        mBinding.activityProfileEdDept.setText(profile.getDepartment());
        mBinding.activityProfileEdBloodGroup.setText(profile.getBloodGroup());

        if (!profile.getProfilePicture().equals("")) {
            setImage(
                    this,
                    getString(TYPE.PROFILEURL),
                    mBinding.actProfileIvProfileImage);
        } else {
            mBinding.actProfileIvProfileImage.setImageResource(R.drawable.ic_no_image);
        }

        mBinding.actProfileRlMain.setVisibility(View.VISIBLE);

        if (getBoolean(TYPE.ISPRESENT)) {
            mBinding.actProfileIvOnline.startAnimation(alphaAnimation);
            mBinding.actProfileIvOnline.setImageDrawable(getResources().getDrawable(R.drawable.circle_green));
        } else
            mBinding.actProfileIvOnline.startAnimation(alphaAnimation);
            mBinding.actProfileIvOnline.setImageDrawable(getResources().getDrawable(R.drawable.circle_leave));
    }

    @Override
    public void onResponseError(String errorMessage, int requestCode) {
        dismissDialog();

        if (errorMessage != null
                && errorMessage.equals("Socket closed") || errorMessage.equals("Canceled")) {

        }else if (errorMessage != null)
            showSnackBar(mBinding.getRoot(), errorMessage, Snackbar.LENGTH_SHORT);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (getProfile != null)
            getProfile.cancel();
    }

}
