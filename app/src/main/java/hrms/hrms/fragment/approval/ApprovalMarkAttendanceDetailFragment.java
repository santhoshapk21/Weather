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

import com.hris365.databinding.FragmentApprovalMarkAttendanceDetailBinding;
import com.squareup.picasso.Picasso;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class ApprovalMarkAttendanceDetailFragment extends BaseFragment implements OnApiResponseListner {


    private FragmentApprovalMarkAttendanceDetailBinding mBinding;
    private String employeeID;
    private String employeeName;
    private String createdDate;
    private String createdTime;
    private String inLocation;
    private String imageURL;
    private String inReason;
    private String gpsAttendanceID;
    private Call<?> approvalCall;

    public ApprovalMarkAttendanceDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_approval_mark_attendance_detail, container, false);
        // Inflate the layout for this fragment

        gpsAttendanceID = getArguments().getString("GPSAttendanceID");
        employeeID = getArguments().getString("EmployeeID");
        employeeName = getArguments().getString("EmployeeName");
        createdDate = getArguments().getString("CreatedDate");
        createdTime = getArguments().getString("CreatedTime");
        inLocation = getArguments().getString("InLocation");
        imageURL = getArguments().getString("ImageURL");
        inReason = getArguments().getString("InReason");

        if (!TextUtils.isEmpty(employeeName))
            mBinding.frgApprovalAttendanceDetailsTvName.setText(employeeName);
        if (!TextUtils.isEmpty(createdDate))
            mBinding.frgApprovalMarkAttendanceDetailsTvFromDate.setText(createdDate);
        if (!TextUtils.isEmpty(createdTime))
            mBinding.frgApprovalMarkAttendanceDetailsTvFromTime.setText(createdTime);
        if (!TextUtils.isEmpty(inReason))
            mBinding.frgApprovalMarkAttendanceDetailsTvReason.setText(inReason);
        if (!TextUtils.isEmpty(inLocation))
            mBinding.frgApprovalMarkAttendanceDetailsTvLocation.setText(inLocation);
        else
            mBinding.frgApprovalMarkAttendanceDetailsLlLocation.setVisibility(View.INVISIBLE);
        if (!TextUtils.isEmpty(imageURL))
            Picasso.get().load(imageURL).placeholder(R.drawable.ic_no_image).into(mBinding.frgApprovalAttendanceIvImage);
        else
            mBinding.frgApprovalAttendanceIvImage.setImageResource(R.drawable.ic_no_image);

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
        approvalCall = ((HomeActivity) getActivity()).getApiTask().getMarkAttendanceActionApproval(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
                mBinding.frgApprovalsEtMessage.getText().toString(),
                gpsAttendanceID,
                status,
                status,
                this
        );
    }

    @Override
    public void onResponseComplete(Object clsGson, int requestCode, int responseCode) {
        dismissDialog();
        if (requestCode == RequestCode.ACTIONAPPROVALMARKATTENDANCE) {
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