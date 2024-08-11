package hrms.hrms.fragment.approval;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.hris365.R;
import com.hris365.databinding.FragmentApprovalExpenseClaimDetailsScreenBinding;

import hrms.hrms.activity.HomeActivity;
import hrms.hrms.adapter.approvals.ApprovalsClaimDetailExpensesAdapter;
import hrms.hrms.baseclass.BaseFragment;
import hrms.hrms.retrofit.OnApiResponseListner;
import hrms.hrms.retrofit.RequestCode;
import hrms.hrms.retrofit.model.approvals.leave.ResponseActionApproval;
import hrms.hrms.retrofit.model.approvals.leave.ResponseApprovalExpenseClaim;

import hrms.hrms.utility.Utility;
import retrofit2.Call;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class ApprovalExpenseClaimDetailsScreen extends BaseFragment implements OnApiResponseListner, ApprovalsClaimDetailExpensesAdapter.ApprovalClickListener {


    private FragmentApprovalExpenseClaimDetailsScreenBinding mBinding;
    private ResponseApprovalExpenseClaim.Detail detailList;
    private Call<?> approvalCall;
    private ApprovalsClaimDetailExpensesAdapter expenseAdapter;


    public ApprovalExpenseClaimDetailsScreen() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_approval_expense_claim_details_screen, container, false);
        getBundleData();


        mBinding.frgApprovalsTvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getactionexpenseclaim("Approve");
            }
        });

        mBinding.frgApprovalsTvReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        return mBinding.getRoot();
    }

    private void getBundleData() {
        if (getArguments().getSerializable(getString(R.string.approval_claim_list)) != null) {
            detailList = (ResponseApprovalExpenseClaim.Detail) getArguments().getSerializable(getString(R.string.approval_claim_list));
            setData();
            if (detailList.expenses != null) {
                setUpRecyclerView();
            }
        } else {
            showNoDataAvailable();
        }
    }

    private void setUpRecyclerView() {
        expenseAdapter = new ApprovalsClaimDetailExpensesAdapter(getContext(), detailList.expenses, this);
        mBinding.frgRowApprovalsClaimDetailsRvExpenses.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.frgRowApprovalsClaimDetailsRvExpenses.setAdapter(expenseAdapter);
    }

    private void setData() {
        mBinding.frgApprovalClaimDetailsTvName.setText(detailList.employeeID + "-" + detailList.eFirstName);
        mBinding.frgRowApprovalsClaimDetailsTvClaimType.setText(detailList.claimType);
        mBinding.frgRowApprovalsClaimDetailsTvAmount.setText(detailList.totalExpenseAmount);
        if (detailList.claimType.equalsIgnoreCase(getString(R.string.claim_type_other))) {
            mBinding.frgApprovalClaimDetailsLlDateMain.setVisibility(View.GONE);
            mBinding.frgApprovalClaimDetailsLlPlaceMain.setVisibility(View.GONE);
        } else {
            mBinding.frgRowApprovalsClaimDetailsTvFromPlace.setText(detailList.fromPlace);
            mBinding.frgRowApprovalsClaimDetailsTvToPlace.setText(detailList.toPlace);
            mBinding.frgRowApprovalsClaimDetailsTvFromDate.setText(
                    Utility.getFormattedDateTimeString( detailList.fromDate, HomeActivity.serverFormat, "dd MMM yyyy")
                   );
            mBinding.frgRowApprovalsClaimDetailsTvToDate.setText(Utility.getFormattedDateTimeString( detailList.toDate, HomeActivity.serverFormat, "dd MMM yyyy"));
        }

    }

    private void showNoDataAvailable() {
    }

    private void getactionexpenseclaim(String status) {
        showDialog();

//        approvalCall = ((HomeActivity) getActivity()).getApiTask().getactionexpenseclaim(
//                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
//                mBinding.frgApprovalsEtMessage.getText().toString(),
//                shiftRequestId,
//                status,
//                "Shift",
//                this
//        );
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

    @Override
    public void onApprovalClicked(String type) {

    }
}
