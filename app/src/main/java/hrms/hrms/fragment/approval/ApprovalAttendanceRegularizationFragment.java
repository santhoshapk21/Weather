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
import hrms.hrms.baseclass.BaseFragment;
import hrms.hrms.baseclass.BaseAppCompactActivity;
import hrms.hrms.retrofit.OnApiResponseListner;
import hrms.hrms.retrofit.RequestCode;
import hrms.hrms.retrofit.model.approvals.leave.ResponseActionApproval;

import com.hris365.databinding.FragmentApprovalAttendanceRegularizationBinding;

import retrofit2.Call;


/**
 * A simple {@link Fragment} subclass.
 */
public class ApprovalAttendanceRegularizationFragment extends BaseFragment implements OnApiResponseListner {


    private FragmentApprovalAttendanceRegularizationBinding mBinding;
    private String employeeID;
    private String regularisationRequestId;
    private String employeeName;
    private String createdFromDate;
    private String createdToDate;
    private String reason;
    private String categoryName;
    private Call<?> approvalCall;

    public ApprovalAttendanceRegularizationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_approval_attendance_regularization, container, false);
        // Inflate the layout for this fragment

        employeeID = getArguments().getString("EmployeeID");
        regularisationRequestId = getArguments().getString("RegularisationRequestId");
        categoryName = getArguments().getString("CategoryName");
        employeeName = getArguments().getString("EmployeeName");
        createdFromDate = getArguments().getString("CreatedFromDate");
        createdToDate = getArguments().getString("CreatedToDate");
        reason = getArguments().getString("Reason");

        if (!TextUtils.isEmpty(employeeName))
            mBinding.frgApprovalRegularizeDetailsTvName.setText(employeeName);
        if (!TextUtils.isEmpty(categoryName))
            mBinding.frgApprovalRegularizeDetailsTvTitle.setText(categoryName);
        if (!TextUtils.isEmpty(createdFromDate))
            mBinding.frgApprovalRegularizeDetailsTvFromDate.setText(createdFromDate);
        if (!TextUtils.isEmpty(createdToDate))
            mBinding.frgApprovalRegularizeDetailsTvToDate.setText(createdToDate);
        if (!TextUtils.isEmpty(reason))
            mBinding.frgApprovalRegularizeDetailsTvReson.setText(reason);

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
                regularisationRequestId,
                status,
                "Regularisation",
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
