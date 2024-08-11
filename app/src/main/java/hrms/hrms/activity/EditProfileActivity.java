package hrms.hrms.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;
import com.hris365.R;
import com.hris365.databinding.ActivityEditProfileDetailsBinding;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.ButterKnife;
import hrms.com.isseiaoki.simplecropview.CropImageView;
import hrms.hrms.baseclass.BaseAppCompactActivity;
import hrms.hrms.dialog.CropImageDialog;
import hrms.hrms.retrofit.OnApiResponseListner;
import hrms.hrms.retrofit.RequestCode;
import hrms.hrms.retrofit.model.profile.ResponseProfile;
import hrms.hrms.retrofit.model.resetPassword.ResponseResetPassword;
import hrms.hrms.util_lib.UtilLib;
import hrms.hrms.util_lib.imagechooser.ChooseType;
import hrms.hrms.util_lib.imagechooser.OnImageChooserListener;

/**
 * Created by yudiz on 24/02/17.
 */

public class EditProfileActivity extends BaseAppCompactActivity implements OnApiResponseListner {

    private ActivityEditProfileDetailsBinding mBinding;
    private ResponseProfile responseProfile;
    private Animation alphaAnimation;
    private ArrayAdapter<String> blood_group;
    private String profileLink;
    private boolean imageUpdate = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile_details);
        ButterKnife.bind(this, mBinding.getRoot());
        init();
        mBinding.activityEditProfileIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
            }
        });
        mBinding.actEditProfileIvProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilLib.getPhoto(getApplicationContext(), ChooseType.REQUEST_ANY)
                        .enqueue(new OnImageChooserListener() {
                            @Override
                            public void onImageChoose(String path) {
                                if (path.length() > 0) {
                                    mBinding.activityEditProfileIvDone.setClickable(false);
                                    profileLink = path;
                                    CropImageDialog dialog = new CropImageDialog(
                                            EditProfileActivity.this,
                                            profileLink,
                                            new CropImageDialog.CropImage() {
                                                @Override
                                                public void onImageCrop(String imagePath) {
                                                    imageUpdate = true;
                                                    profileLink = imagePath;
                                                    Picasso.get()
                                                            .load(new File(profileLink))
                                                            .into(mBinding.actEditProfileIvProfileImage
                                                            );
                                                }
                                            }, CropImageView.CropMode.CIRCLE);

                                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                        @Override
                                        public void onDismiss(DialogInterface dialog) {
                                            mBinding.activityEditProfileIvDone.setClickable(true);
                                        }
                                    });

                                    dialog.show();
                                }
                            }
                        });

//
//        UtilLib.getPhoto(this, ChooseType.REQUEST_ANY).enqueue(
//                new OnImageChooserListener() {
//                    @Override
//                    public void onImageChoose(String path2) {
//
//                    }
//                }
//        );
            }
        });

        mBinding.activityEditProfileIvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBinding.activityEditProfileEdPersonalNumber.getText().toString().length() > 6) {
                    if (mBinding.activityEditProfileEdOfficeNumber.getText().toString().length() > 0 && mBinding.activityEditProfileEdOfficeNumber.getText().toString().length() < 6) {
                        showSnackBar(mBinding.getRoot(), "Number must be of more than 6 digits", Snackbar.LENGTH_SHORT);
                    } else {
                        if (imageUpdate) {
                            doupdateImage();
                        } else
                            updateProfile();
                    }
                } else {
                    showSnackBar(mBinding.getRoot(), "Number must be of more than 6 digits", Snackbar.LENGTH_SHORT);
                }
            }
        });
    }

    private void init() {
        responseProfile = (ResponseProfile) getIntent().getSerializableExtra("responseProfile");
        alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_animation);
        setBloodGroupAdapter();
        setData(responseProfile);
    }

    private void setBloodGroupAdapter() {
        blood_group = new ArrayAdapter<>(
                this,
                com.rey.material.R.layout.support_simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.blood_group)
        );
        mBinding.updateSpGender.setAdapter(blood_group);
    }

    private void setData(ResponseProfile profile) {
        mBinding.actEditProfileTvName.setText(profile.getName());

        mBinding.actEditProfileTvDesigination.setText(profile.getDesignation());

        if (profile.getCountry().equals(""))
            mBinding.actEditProfileTvAddress.setText(profile.getCity());
        else
            mBinding.actEditProfileTvAddress.setText(profile.getCity() + "," + profile.getCountry());

        mBinding.activityEditProfileEdOfficeNumber.setText(profile.getPhone());
        mBinding.activityEditProfileEdPersonalNumber.setText(profile.getMobile());
        mBinding.activityEditProfileEdEmail.setText(profile.getEmail());
        mBinding.activityEditProfileEdEmpId.setText(profile.getEmployeeID());
        mBinding.activityEditProfileEdDept.setText(profile.getDepartment());
        profileLink = profile.getProfilePicture();

        setBloodDefaultGroupSelection(profile);


        if (!profile.getProfilePicture().equals("")) {
            setImage(
                    this,
                    getString(TYPE.PROFILEURL),
                    mBinding.actEditProfileIvProfileImage
            );
        } else {
            mBinding.actEditProfileIvProfileImage.setImageResource(R.drawable.ic_no_image);
        }

        mBinding.actEditProfileRlMain.setVisibility(View.VISIBLE);

        if (getBoolean(TYPE.ISPRESENT)) {
            mBinding.actEditProfileIvOnline.startAnimation(alphaAnimation);
        } else
            mBinding.actEditProfileIvOnline.setVisibility(View.GONE);
    }

    private void setBloodDefaultGroupSelection(ResponseProfile profile) {
        if (profile.getBloodGroup().equals("O-"))
            mBinding.updateSpGender.setSelection(0);
        else if (profile.getBloodGroup().equals("O+"))
            mBinding.updateSpGender.setSelection(1);
        else if (profile.getBloodGroup().equals("A-"))
            mBinding.updateSpGender.setSelection(2);
        else if (profile.getBloodGroup().equals("A+"))
            mBinding.updateSpGender.setSelection(3);
        else if (profile.getBloodGroup().equals("B-"))
            mBinding.updateSpGender.setSelection(4);
        else if (profile.getBloodGroup().equals("B+"))
            mBinding.updateSpGender.setSelection(5);
        else if (profile.getBloodGroup().equals("AB-"))
            mBinding.updateSpGender.setSelection(6);
        else if (profile.getBloodGroup().equals("AB+"))
            mBinding.updateSpGender.setSelection(7);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mBinding.activityEditProfileIvBack.performClick();
    }

    private void updateProfile() {
        showDialog();
        getApiTask().doUpdateProfile(
                getString(TYPE.ACCESSTOKEN),
                responseProfile.getFirstName(),
                responseProfile.getMiddleName(),
                responseProfile.getLastName(),
                mBinding.activityEditProfileEdPersonalNumber.getText().toString(),
                mBinding.activityEditProfileEdOfficeNumber.getText().toString(),
                responseProfile.getEmail(),
                responseProfile.getGender(),
                mBinding.updateSpGender.getSelectedItem().toString(),
                responseProfile.getCity(),
                responseProfile.getState(),
                responseProfile.getCountry(),
                profileLink,
                this
        );
    }

    @Override
    public void onResponseComplete(Object clsGson, int requestCode, int responseCode) {
        dismissDialog();
        if (clsGson != null) {
            if (requestCode == RequestCode.UPDATEPROFILEIMAGE) {
                ResponseResetPassword model = (ResponseResetPassword) clsGson;
                profileLink = model.getMessage();
                putString(TYPE.PROFILEURL, profileLink);
                updateProfile();
            } else {
                if (responseCode == 200) {
                    Toast.makeText(this, "Profile successfully updated", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            }
        }
    }

    @Override
    public void onResponseError(String errorMessage, int requestCode) {
        dismissDialog();
        if (errorMessage != null)
            showSnackBar(mBinding.getRoot(), errorMessage, Snackbar.LENGTH_SHORT);
    }

    private void doupdateImage() {
        showDialog();
        getApiTask().doUpdateProfileImage(
                getString(TYPE.ACCESSTOKEN),
                profileLink,
                this);

        Log.v("OkHttps EDPROFILE", getString(TYPE.ACCESSTOKEN));

    }
}
