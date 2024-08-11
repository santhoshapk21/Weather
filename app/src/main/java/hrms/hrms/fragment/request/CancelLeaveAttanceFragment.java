package hrms.hrms.fragment.request;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;
import com.hris365.R;
import com.hris365.databinding.FragmentCancelLeaveAttanceBinding;

import hrms.hrms.baseclass.BaseAppCompactActivity;
import hrms.hrms.baseclass.BaseFragment;
import hrms.hrms.retrofit.OnApiResponseListner;
import hrms.hrms.retrofit.RequestCode;
import hrms.hrms.retrofit.model.approvals.leave.ResponseActionApproval;
import hrms.hrms.retrofit.model.request.leave.ResponseLeaveCancel;

import java.util.Calendar;

import hrms.hrms.activity.HomeActivity;

import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class CancelLeaveAttanceFragment extends BaseFragment implements OnApiResponseListner {

    FragmentCancelLeaveAttanceBinding mBinding;
    private String leaveid;
    private Call getRequestLeaveCancel;
    private String type;
    private OnLeaveCancelSuccessListener leaveCancelSuccessListener;
    private Call<?> getRequestMarkAttendanceCancel;
    private Calendar calendar;

    public CancelLeaveAttanceFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public CancelLeaveAttanceFragment(OnLeaveCancelSuccessListener leaveCancelSuccessListener) {

        this.leaveCancelSuccessListener = leaveCancelSuccessListener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cancel_leave_attance, container, false);
        ButterKnife.bind(this, mBinding.getRoot());

        leaveid = getArguments().getString("LeaveAppID");
        type = getArguments().getString("Type");

        mBinding.fragmentAttendanceRequestTvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("MarkAttendance")) {
                    if (mBinding.fragmentAttendanceRequestEtDesc.getText().toString().length() > 0) {
                        mBinding.fragmentAttendanceRequestTvSubmit.setEnabled(false);
                        getMarkAttendanceActionApproval();
                    } else {
                        ((HomeActivity) getActivity()).showSnackBar(mBinding.getRoot(), "Please Enter the Mark Attendance Cancel Reason", Snackbar.LENGTH_SHORT);
                    }
                } else if (type.equals("Leave")) {
                    if (mBinding.fragmentAttendanceRequestEtDesc.getText().toString().length() > 0) {
                        mBinding.fragmentAttendanceRequestTvSubmit.setEnabled(false);
                        getRequestLeaveCancelApi();
                    } else {
                        ((HomeActivity) getActivity()).showSnackBar(mBinding.getRoot(), "Please Enter the Leave Cancel Reason", Snackbar.LENGTH_SHORT);
                    }
                } else if (type.equals("Shift")) {
                    if (mBinding.fragmentAttendanceRequestEtDesc.getText().toString().length() > 0) {
                        mBinding.fragmentAttendanceRequestTvSubmit.setEnabled(false);
//                        getActionApproval();
                    } else {
                        ((HomeActivity) getActivity()).showSnackBar(mBinding.getRoot(), "Please Enter the Shift Change Cancel Reason", Snackbar.LENGTH_SHORT);
                    }
                } else if (type.equals("Correction")) {
                    if (mBinding.fragmentAttendanceRequestEtDesc.getText().toString().length() > 0) {
                        mBinding.fragmentAttendanceRequestTvSubmit.setEnabled(false);
//                        getActionApproval();
                    } else {
                        ((HomeActivity) getActivity()).showSnackBar(mBinding.getRoot(), "Please Enter the Attendance Correction Cancel Reason", Snackbar.LENGTH_SHORT);
                    }
                } else if (type.equals("Regularisation")) {
                    if (mBinding.fragmentAttendanceRequestEtDesc.getText().toString().length() > 0) {
                        mBinding.fragmentAttendanceRequestTvSubmit.setEnabled(false);
//                        getActionApproval();
                    } else {
                        ((HomeActivity) getActivity()).showSnackBar(mBinding.getRoot(), "Please Enter the Attendance Regularisation Cancel Reason", Snackbar.LENGTH_SHORT);
                    }
                } else {
                    mBinding.fragmentAttendanceRequestTvSubmit.setEnabled(false);
                    getRequestLeaveCancelApi();
                }
            }
        });

        mBinding.fragmentAttendanceRequestTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return mBinding.getRoot();
    }


    private void getRequestLeaveCancelApi() {
        showDialog();
        getRequestLeaveCancel = ((HomeActivity) getActivity()).getApiTask().getRequestLeaveCancel(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
                mBinding.fragmentAttendanceRequestEtDesc.getText().toString(),
                leaveid,
                this
        );
    }

    @Override
    public void onResponseComplete(Object clsGson, int requestCode, int responseCode) {
        dismissDialog();
        mBinding.fragmentAttendanceRequestTvSubmit.setEnabled(true);
        calendar = Calendar.getInstance();

        if (requestCode == RequestCode.ACTIONAPPROVAL) {
            if (clsGson != null) {
                ResponseActionApproval responseActionApproval = (ResponseActionApproval) clsGson;

                Toast.makeText(getActivity(), "" + responseActionApproval.Message, Toast.LENGTH_SHORT).show();

                leaveCancelSuccessListener.onLeaveCancelSuccess();
            }
        }

        if (requestCode == RequestCode.REQUESTLEAVECANCEL) {
            if (clsGson != null) {
                ResponseLeaveCancel responseActionApproval = (ResponseLeaveCancel) clsGson;

                Toast.makeText(getActivity(), "" + responseActionApproval.getMessage(), Toast.LENGTH_SHORT).show();

                leaveCancelSuccessListener.onLeaveCancelSuccess();
            }
        }

        if (requestCode == RequestCode.ACTIONAPPROVALMARKATTENDANCE) {
            if (clsGson != null) {
                ResponseActionApproval responseActionApproval = (ResponseActionApproval) clsGson;

                Toast.makeText(getActivity(), "" + responseActionApproval.Message, Toast.LENGTH_SHORT).show();

                leaveCancelSuccessListener.onLeaveCancelSuccess();
            }
        }
    }

    @Override
    public void onResponseError(String errorMessage, int requestCode) {
        dismissDialog();
        mBinding.fragmentAttendanceRequestTvSubmit.setEnabled(true);

        Toast.makeText(getActivity(), "" + errorMessage, Toast.LENGTH_SHORT).show();
    }

/*
    private void getActionApproval() {
        showDialog();
        getRequestLeaveCancel = ((HomeActivity) getActivity()).getApiTask().getActionApproval(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
                mBinding.fragmentAttendanceRequestEtDesc.getText().toString(),
                leaveid,
                "Cancel",
                type,
                this
        );
    }
*/

    private void getMarkAttendanceActionApproval() {
        showDialog();
        getRequestMarkAttendanceCancel = ((HomeActivity) getActivity()).getApiTask().getMarkAttendanceActionApproval(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
                mBinding.fragmentAttendanceRequestEtDesc.getText().toString(),
                leaveid,
                "Cancel",
                "Cancel",
                this
        );
    }

    public interface OnLeaveCancelSuccessListener {

        void onLeaveCancelSuccess();
    }
}
