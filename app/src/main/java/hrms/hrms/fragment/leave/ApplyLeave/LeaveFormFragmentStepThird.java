package hrms.hrms.fragment.leave.ApplyLeave;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;
import com.hris365.R;
import com.hris365.databinding.FragmentLeaveFormStepThirdBinding;

import butterknife.ButterKnife;
import butterknife.OnClick;
import hrms.hrms.baseclass.BaseAppCompactActivity;
import hrms.hrms.baseclass.BaseFragment;
import hrms.hrms.model.LeaveDetails;
import hrms.hrms.retrofit.OnApiResponseListner;
import hrms.hrms.retrofit.model.resetPassword.ResponseResetPassword;
import hrms.hrms.activity.HomeActivity;
import retrofit2.Call;


/**
 * Created by yudiz on 24/03/17.
 */

public class LeaveFormFragmentStepThird extends BaseFragment implements OnApiResponseListner {

    private FragmentLeaveFormStepThirdBinding mBinding;
    private LeaveDetails mLeaveDetails;
    private Call<?> call;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_leave_form_step_third, container, false);

        ButterKnife.bind(this, mBinding.getRoot());
        mBinding.fragmentLeaveFormThirdTvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(mBinding.getRoot().findFocus());
                if (mBinding.fragmentLeaveFormThirdEtDesc.getText().toString().trim().isEmpty())
                    ((HomeActivity) getActivity()).showSnackBar(mBinding.getRoot(),
                            getString(R.string.str_error_desc),
                            Snackbar.LENGTH_SHORT
                    );
                else if (!BaseAppCompactActivity.isInternet)
                    ((HomeActivity) getActivity()).showSnackBar(mBinding.getRoot(),
                            getString(R.string.nointernet),
                            Snackbar.LENGTH_SHORT
                    );
                else {
                    mLeaveDetails.setReasonforLeave(mBinding.fragmentLeaveFormThirdEtDesc.getText().toString());
                    onApplyLeaveApi();
                }
            }
        });
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLeaveDetails = (LeaveDetails) getArguments().getSerializable(LeaveFormFragmentStepFirst.LEAVE_DETAILS);
    }


    private void onApplyLeaveApi() {
        showDialog();
        ((HomeActivity) getActivity()).getApiTask().doApplyLeave(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
                mLeaveDetails,
                this
        );
    }

    @Override
    public void onResponseComplete(Object clsGson, int requestCode,int responseCode) {
        dismissDialog();
        if (clsGson != null) {
            ResponseResetPassword model = (ResponseResetPassword) clsGson;
            if (getContext() != null)
                Toast.makeText(getContext(), model.getMessage() + "", Toast.LENGTH_SHORT).show();
            ((HomeActivity) getActivity()).showLeaveFragment();
        }
    }

    @Override
    public void onResponseError(String errorMessage, int requestCode) {
        dismissDialog();
        if (errorMessage != null)
            ((HomeActivity) getActivity()).showSnackBar(mBinding.getRoot(),
                    errorMessage,
                    Snackbar.LENGTH_SHORT
            );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hideSoftKeyboard(mBinding.getRoot().findFocus());
    }
}
