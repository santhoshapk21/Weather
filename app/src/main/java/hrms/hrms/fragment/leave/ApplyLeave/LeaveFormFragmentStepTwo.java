package hrms.hrms.fragment.leave.ApplyLeave;

import static hrms.hrms.fragment.leave.ApplyLeave.LeaveFormFragmentStepFirst.LEAVE_DETAILS;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.hris365.R;
import com.hris365.databinding.FragmentLeaveFormStepTwoBinding;

import butterknife.ButterKnife;
import hrms.hrms.adapter.leave.LeaveFormFragmentTwoAdapter;
import hrms.hrms.baseclass.BaseFragment;
import hrms.hrms.model.LeaveDetails;

/**
 * Created by yudiz on 24/03/17.
 */

public class LeaveFormFragmentStepTwo extends BaseFragment {

    private FragmentLeaveFormStepTwoBinding mBinding;
    private LeaveFormFragmentTwoAdapter mAdapter;
    private LeaveDetails mLeaveDetails;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_leave_form_step_two, container, false);
        ButterKnife.bind(this, mBinding.getRoot());
        mBinding.fragmentLeaveFormTowTvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LeaveFormFragmentStepThird leaveFormFragmentStepThird = new LeaveFormFragmentStepThird();
                Bundle bundle = new Bundle();
                bundle.putSerializable(LEAVE_DETAILS, mLeaveDetails);
                leaveFormFragmentStepThird.setArguments(bundle);
                addFragment(leaveFormFragmentStepThird, getString(R.string.str_apply_leave) + " ");
            }
        });
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLeaveDetails = (LeaveDetails) getArguments().getSerializable(LEAVE_DETAILS);
        setAdapter();
    }

    private void setAdapter() {
        mAdapter = new LeaveFormFragmentTwoAdapter(getContext(), mLeaveDetails);
        mBinding.frgLeaveFormRvList.setAdapter(mAdapter);
    }
}
