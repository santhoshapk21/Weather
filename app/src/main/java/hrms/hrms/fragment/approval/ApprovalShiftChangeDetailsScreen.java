package hrms.hrms.fragment.approval;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;
import com.hris365.R;
import com.hris365.databinding.FragmentApprovalShiftChangeDetailsScreenBinding;

import hrms.hrms.activity.HomeActivity;
import hrms.hrms.baseclass.BaseAppCompactActivity;
import hrms.hrms.baseclass.BaseFragment;
import hrms.hrms.retrofit.OnApiResponseListner;
import hrms.hrms.retrofit.RequestCode;
import hrms.hrms.retrofit.model.approvals.leave.ResponseActionApproval;
import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class ApprovalShiftChangeDetailsScreen extends BaseFragment implements OnApiResponseListner {


    private FragmentApprovalShiftChangeDetailsScreenBinding mBinding;
    private String shiftRequestId;
    private String shiftName;
    private String employeeId;
    private String createdFromDate;
    private String employeeName;
    private String createdFromTime;
    private String createdToDate;
    private String createdToTime;
    private String reason;
    private Call<?> approvalCall;

    public ApprovalShiftChangeDetailsScreen() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_approval_shift_change_details_screen, container, false);
        // Inflate the layout for this fragment

        shiftRequestId = getArguments().getString("ShiftRequestId");
        shiftName = getArguments().getString("ShiftName");
        employeeId = getArguments().getString("EmployeeId");
        employeeName = getArguments().getString("EmployeeName");
        createdFromDate = getArguments().getString("CreatedFromDate");
        createdFromTime = getArguments().getString("CreatedFromTime");
        createdToDate = getArguments().getString("CreatedToDate");
        createdToTime = getArguments().getString("CreatedToTime");
        reason = getArguments().getString("Reason");

        if (!TextUtils.isEmpty(employeeName))
            mBinding.frgApprovalShiftChangeDetailsTvName.setText(employeeName);
        if (!TextUtils.isEmpty(createdFromDate))
            mBinding.frgApprovalShiftChangeDetailsTvFromDate.setText(createdFromDate);
        if (!TextUtils.isEmpty(createdFromTime))
            mBinding.frgApprovalShiftChangeDetailsTvFromTime.setText(createdFromTime);
        if (!TextUtils.isEmpty(createdToDate))
            mBinding.frgApprovalShiftChangeDetailsTvToDate.setText(createdToDate);
        if (!TextUtils.isEmpty(createdToTime))
            mBinding.frgApprovalShiftChangeDetailsTvToTime.setText(createdToTime);
        if (!TextUtils.isEmpty(reason))
            mBinding.frgApprovalShiftChangeDetailsTvReson.setText(reason);
        if (!TextUtils.isEmpty(shiftName))
            mBinding.frgApprovalShiftChangeDetailsTvTitle.setText(shiftName);

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
                shiftRequestId,
                status,
                "Shift",
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
