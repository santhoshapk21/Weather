package hrms.hrms.fragment.approval;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.hris365.R;
import com.hris365.databinding.FragmentApprovalsDetailsScreenBinding;

import hrms.hrms.activity.HomeActivity;
import hrms.hrms.adapter.approvals.ApprovalLeaveDetailAdapter;
import hrms.hrms.adapter.approvals.ApprovalsDetailsAdapter;
import hrms.hrms.baseclass.BaseAppCompactActivity;
import hrms.hrms.baseclass.BaseFragment;
import hrms.hrms.retrofit.OnApiResponseListner;
import hrms.hrms.retrofit.model.approvals.leave.ResponseApprovalLeaveDetail;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import retrofit2.Call;

/**
 * Created by yudiz on 05/04/17.
 */

public class ApprovalDetailsScreen extends BaseFragment implements OnApiResponseListner {

    private FragmentApprovalsDetailsScreenBinding mBinding;
    private ApprovalsDetailsAdapter mAdapter;
    private String leaveAppId;
    private String leaveType;
    private String employeeId;
    private String employeeName;
    private String createdFromDate;
    private String reason;
    private String createdToDate;
    private int leaveDuration;
    private Call<?> getRequestLeaveDetail;
    private ResponseApprovalLeaveDetail responserequestleave;
    private List<ResponseApprovalLeaveDetail.Details> leaveDetailList, tmpLeaveDetailList;
    private ApprovalLeaveDetailAdapter mRequestDetailsAdapter;
    private LinearLayoutManager linearLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_approvals_details_screen, container, false);
        ButterKnife.bind(this, mBinding.getRoot());

        leaveAppId = getArguments().getString("LeaveAppId");

        if (!TextUtils.isEmpty(leaveAppId))
            getRequestLeaveDetailApi();

        return mBinding.getRoot();
    }

    private void getRequestLeaveDetailApi() {
        showDialog();
        getRequestLeaveDetail = ((HomeActivity) getActivity()).getApiTask().doRequestLeaveDetail(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
                leaveAppId,
                this
        );
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResponseComplete(Object clsGson, int requestCode, int responseCode) {
        dismissDialog();
        if (clsGson != null) {
            ResponseApprovalLeaveDetail responserequestlev = (ResponseApprovalLeaveDetail) clsGson;
            setData(responserequestlev);
            responserequestleave = responserequestlev;

            List<ResponseApprovalLeaveDetail.Details> directories = responserequestlev.Details;

            if (leaveDetailList == null) {
                leaveDetailList = directories;
                setRequestLeaveDetailAdapter();
            } else {
                leaveDetailList.addAll(directories);
                mRequestDetailsAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onResponseError(String errorMessage, int requestCode) {
        dismissDialog();

        if (errorMessage != null
                && errorMessage.equals("Socket closed") || errorMessage.equals("Canceled")) {

        } else if (errorMessage != null) {
            ((HomeActivity) getActivity()).showSnackBar(mBinding.getRoot(), errorMessage, Snackbar.LENGTH_SHORT);
            Log.i("TAG1", errorMessage + "");
        }
    }

    private void setData(ResponseApprovalLeaveDetail responserequestlev) {
        mBinding.frgApprovalsDetailsTvReson.setText(responserequestlev.ReasonForLeave);
        mBinding.frgApprovalDetailsTvName.setText(responserequestlev.Name);
        mBinding.frgApprovalsDetailsTvTitle.setText(responserequestlev.Name);
    }

    private void setRequestLeaveDetailAdapter() {
        tmpLeaveDetailList = new ArrayList<>();
        linearLayout = new LinearLayoutManager(getActivity());
        mBinding.frgApprovalsRvList.setLayoutManager(linearLayout);
        mRequestDetailsAdapter = new ApprovalLeaveDetailAdapter(getActivity(), leaveDetailList);
        mBinding.frgApprovalsRvList.setAdapter(new ScaleInAnimationAdapter(mRequestDetailsAdapter));
    }
}
