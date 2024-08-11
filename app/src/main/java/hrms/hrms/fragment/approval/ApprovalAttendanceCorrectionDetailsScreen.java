package hrms.hrms.fragment.approval;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.hris365.R;

import hrms.hrms.activity.HomeActivity;
import hrms.hrms.baseclass.BaseAppCompactActivity;
import hrms.hrms.baseclass.BaseFragment;
import hrms.hrms.retrofit.OnApiResponseListner;
import hrms.hrms.retrofit.RequestCode;
import hrms.hrms.retrofit.model.approvals.leave.ResponseActionApproval;

import com.hris365.databinding.FragmentApprovalAttendanceCorrectionDetailsScreenBinding;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class ApprovalAttendanceCorrectionDetailsScreen extends BaseFragment implements OnApiResponseListner {


    private FragmentApprovalAttendanceCorrectionDetailsScreenBinding mBinding;
    private String correctionReqId;
    private String requestType;
    private String employeeName;
    private String createdFromDate;
    private String employeeId;
    private String reason;
    private String createdFromTime;
    private Call<?> approvalCall;

    public ApprovalAttendanceCorrectionDetailsScreen() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_approval_attendance_correction_details_screen, container, false);
        // Inflate the layout for this fragment

        correctionReqId = getArguments().getString("CorrectionReqId");
        requestType = getArguments().getString("RequestType");
        employeeId = getArguments().getString("EmployeeId");
        employeeName = getArguments().getString("EmployeeName");
        createdFromDate = getArguments().getString("CreatedFromDate");
        createdFromTime = getArguments().getString("CreatedFromTime");
        reason = getArguments().getString("Reason");

        if (!TextUtils.isEmpty(employeeName))
            mBinding.frgApprovalAttendanceCorrectionDetailsTvName.setText(employeeName);
        if (!TextUtils.isEmpty(createdFromDate))
            mBinding.frgApprovalAttendanceCorrectionDetailsTvFromDate.setText(createdFromDate);
        if (!TextUtils.isEmpty(createdFromTime))
            mBinding.frgApprovalAttendanceCorrectionDetailsTvFromTime.setText(createdFromTime);
        if (!TextUtils.isEmpty(reason))
            mBinding.frgApprovalAttendanceCorrectionDetailsTvReson.setText(reason);
        if (!TextUtils.isEmpty(requestType))
            mBinding.frgApprovalAttendanceCorrectionDetailsTvTitle.setText(requestType);

        mBinding.frgApprovalsTvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActionApproval("Approve");
            }
        });

        mBinding.frgApprovalsTvReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mBinding.frgApprovalsEtMessage.getText().toString()))
                    getActionApproval("Reject");
                else {
                    ((HomeActivity) getActivity()).showSnackBar(mBinding.getRoot(), "Please enter rejection reason", Snackbar.LENGTH_SHORT);
                }
            }
        });

        return mBinding.getRoot();
    }

    private void getActionApproval(String status) {
        showDialog();
        approvalCall = ((HomeActivity) getActivity()).getApiTask().getActionApproval(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
                mBinding.frgApprovalsEtMessage.getText().toString(),
                correctionReqId,
                status,
                "Correction",
                this
        );
    }

    @Override
    public void onResponseComplete(Object clsGson, int requestCode, int responseCode) {
        dismissDialog();
        if (requestCode == RequestCode.ACTIONAPPROVAL) {
            ResponseActionApproval user = new ResponseActionApproval();
            if (responseCode == 200) {
                getActivity().onBackPressed();
            }
        }
    }

    @Override
    public void onResponseError(String errorMessage, int requestCode) {
        dismissDialog();
        if (errorMessage != null && errorMessage.equals("Socket closed") || errorMessage.equals("Canceled")) {

        } else if (errorMessage != null && ((HomeActivity) getActivity()) != null)
            ((HomeActivity) getActivity()).showSnackBar(mBinding.getRoot(), errorMessage, Snackbar.LENGTH_SHORT);
    }
}
