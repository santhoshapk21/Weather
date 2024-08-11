package hrms.hrms.fragment.empdir;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;
import com.hris365.R;
import com.hris365.databinding.FragmentEmpDetailsBinding;

import hrms.hrms.activity.HomeActivity;
import hrms.hrms.baseclass.BaseAppCompactActivity;
import hrms.hrms.baseclass.BaseFragment;
import hrms.hrms.retrofit.OnApiResponseListner;
import hrms.hrms.retrofit.model.profile.ResponseProfile;


/**
 * Created by Yudiz on 14/10/16.
 */
public class EmployeeDetailsFragment extends BaseFragment implements OnApiResponseListner {


    private FragmentEmpDetailsBinding mBinding;
    private String empId, isPresent;
    private Animation alphaAnimation;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_emp_details, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        alphaAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.alpha_animation);
        empId = getArguments().getString("empId");
        isPresent = getArguments().getString("isPresent");
        getProfileApi();
    }

    private void getProfileApi() {
        showDialog();
        ((HomeActivity) getActivity()).getApiTask().doGetProfileOther(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
                empId,
                this
        );
    }

    @Override
    public void onResponseComplete(Object clsGson, int requestCode,int responseCode) {
        dismissDialog();
        if (clsGson != null) {
            ResponseProfile profile = (ResponseProfile) clsGson;
            setData(profile);
        }
    }

    @Override
    public void onResponseError(String errorMessage, int requestCode) {
        dismissDialog();
        if (errorMessage != null)
            ((HomeActivity) getActivity()).showSnackBar(mBinding.getRoot(), errorMessage, Snackbar.LENGTH_SHORT);
    }

    private void setData(ResponseProfile profile) {
        mBinding.frgTvOfficeName.setText(profile.getFirstName() + " " + profile.getLastName());
        mBinding.frgTvDesignation.setText(profile.getDesignation());
        mBinding.frgTvOfficeNumber.setText(profile.getPhone());
        mBinding.frgTvMobileNumber.setText(profile.getMobile());
        mBinding.frgTvEmail.setText(profile.getEmail());
        mBinding.frgTvEmpId.setText(profile.getEmployeeID());
        mBinding.frgTvOfficeDepartment.setText(profile.getDepartment());

        if (profile.getCountry().equals(""))
            mBinding.fragmentEmpDetailsTvAddress.setText(profile.getCity());
        else
            mBinding.fragmentEmpDetailsTvAddress.setText(profile.getCity() + "," + profile.getCountry());

        if (!profile.getProfilePicture().equals("")) {
            ((HomeActivity) getActivity()).setImage(getContext(), profile.getProfilePicture(), mBinding.frgIvProfileImage);
        } else {
            mBinding.frgIvProfileImage.setImageResource(R.drawable.ic_no_image);
        }

        mBinding.rlMain.setVisibility(View.VISIBLE);

        if (!isPresent.equals("Absent")) {
            mBinding.frgIvOnline.startAnimation(alphaAnimation);
        } else
            mBinding.frgIvOnline.setVisibility(View.GONE);
    }
}
