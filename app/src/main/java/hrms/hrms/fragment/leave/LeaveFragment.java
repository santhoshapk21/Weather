package hrms.hrms.fragment.leave;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.hris365.R;
import com.hris365.databinding.FragmentLeaveBinding;

import java.util.Calendar;

import butterknife.ButterKnife;
import hrms.hrms.activity.HomeActivity;
import hrms.hrms.adapter.leave.LeaveDetailsAdapter;
import hrms.hrms.baseclass.BaseAppCompactActivity;
import hrms.hrms.baseclass.BaseFragment;
import hrms.hrms.fragment.DashBoaredFragment;
import hrms.hrms.fragment.leave.ApplyLeave.LeaveFormFragmentStepFirst;
import hrms.hrms.fragment.request.RequestFragment;
import hrms.hrms.retrofit.OnApiResponseListner;
import hrms.hrms.retrofit.RequestCode;
import hrms.hrms.retrofit.model.Leave.LeaveDetails.ResponseLeaveDetails;

/**
 * Created by Yudiz on 07/10/16.
 */
public class LeaveFragment extends BaseFragment
        implements OnApiResponseListner {

    private FragmentLeaveBinding mBinding;
    private LeaveDetailsAdapter leaveDetailsAdapter;
    private ResponseLeaveDetails leaveDetailsModel;
    private Calendar calendar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_leave, container, false);
        ButterKnife.bind(this, mBinding.getRoot());
        init();
        mBinding.fragmentLeaveTvApplyLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment(new LeaveFormFragmentStepFirst(), getString(R.string.leave_form));
            }
        });
        mBinding.frgLeaveLlPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment(new RequestFragment(), getString(R.string.request));
            }
        });
        return mBinding.getRoot();
    }

    private void init() {
        calendar = Calendar.getInstance();
        mBinding.frgLeaveRlMain.setVisibility(View.INVISIBLE);
        onLeaveDetails();
//        onAnnualLeaveCount();
    }

/*
    private void onAnnualLeaveCount() {
        ((HomeActivity) getActivity()).showDialog();
        ((HomeActivity) getActivity()).getApiTask().doGetAnnualLeaveCount(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
                this
        );
    }
*/

    private void onLeaveDetails() {
        ((HomeActivity) getActivity()).showDialog();
        ((HomeActivity) getActivity()).getApiTask().doGetLeaveDetailsCount(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
                calendar.get(Calendar.YEAR),
                this
        );
    }

    @Override
    public void onResponseComplete(Object clsGson, int requestCode, int responseCode) {
        ((HomeActivity) getActivity()).dismissDialog();

      /*  if (requestCode == RequestCode.ANNUALLEAVE) {
            ResponseAnnualLeave model = (ResponseAnnualLeave) clsGson;
            if (model != null) {

            }
        } else */
        if (requestCode == RequestCode.LEAVEDETAILSCOUNT) {
            leaveDetailsModel = (ResponseLeaveDetails) clsGson;
            mBinding.frgLeaveTvPending.setText(leaveDetailsModel.PendingApproval + "");
            mBinding.frgLeaveTvTaken.setText(leaveDetailsModel.Taken + "");
            mBinding.frgLeaveTvBalance.setText(leaveDetailsModel.Balance + "");
            mBinding.frgLeaveRlMain.setVisibility(View.VISIBLE);
//            onLeaveDetails();

            if (leaveDetailsModel.details != null && leaveDetailsModel.details.size() > 0) {
                setAdapter();
                mBinding.fragmentLeaveTvApplyLeave.setVisibility(View.VISIBLE);
            } else {
                mBinding.fragmentLeaveRvLeave.setVisibility(View.GONE);
                mBinding.fragmentLeaveTvApplyLeave.setVisibility(View.GONE);
                ((HomeActivity) getActivity()).showSnackBar(mBinding.getRoot(), "No leave codes assigned. Please contact HR." , Snackbar.LENGTH_LONG);
                mBinding.frgLeaveTvNoData.setVisibility(View.VISIBLE);
            }
        }

    }

    private void setAdapter() {
        mBinding.fragmentLeaveRvLeave.setVisibility(View.VISIBLE);
        mBinding.frgLeaveTvNoData.setVisibility(View.GONE);
        leaveDetailsAdapter = new LeaveDetailsAdapter(getContext(), leaveDetailsModel.details);
        mBinding.fragmentLeaveRvLeave.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.fragmentLeaveRvLeave.setAdapter(leaveDetailsAdapter);
    }

    @Override
    public void onResponseError(String errorMessage, int requestCode) {
        if (getActivity() != null)
            ((HomeActivity) getActivity()).dismissDialog();

        if (errorMessage != null && getActivity() != null)
            ((HomeActivity) getActivity()).showSnackBar(mBinding.getRoot(), errorMessage, -1);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.fl_container);
        if (currentFragment instanceof DashBoaredFragment) {
            ((DashBoaredFragment) currentFragment).requestCountApi();
            ((DashBoaredFragment) currentFragment).approvalCountApi();
        }
    }
}
