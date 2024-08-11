package hrms.hrms.adapter.approvals;

import static hrms.hrms.activity.HomeActivity.serverFormat;
import static hrms.hrms.utility.Utility.getFormattedDateTimeString;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hris365.R;
import com.hris365.databinding.RowApprovalExpenseClaimBinding;

import java.util.List;

import hrms.hrms.activity.HomeActivity;
import hrms.hrms.fragment.approval.ApprovalExpenseClaimDetailsScreen;
import hrms.hrms.fragment.approval.ApprovalRequestFragment;
import hrms.hrms.retrofit.model.approvals.leave.ResponseApprovalExpenseClaim;


public class ApprovalsExpenseClaimAdapter extends RecyclerView.Adapter<ApprovalsExpenseClaimAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ApprovalRequestFragment approvalRequestFragment;
    public List<ResponseApprovalExpenseClaim.Detail> responseApprovalExpenseClaimList;
    private int lastAnimationPosition;
    private Animation animation;
    ApprovalClickListener approvalClickListener;
    HomeActivity homeActivity;
    private Context context;

    public ApprovalsExpenseClaimAdapter(@NonNull Context context,
                                        List<ResponseApprovalExpenseClaim.Detail> responseApprovalExpenseClaimList,
                                        ApprovalRequestFragment approvalRequestFragment,
                                        HomeActivity homeActivity,
                                        ApprovalClickListener approvalClickListener) {
        inflater = LayoutInflater.from(context);
        this.approvalRequestFragment = approvalRequestFragment;
        this.responseApprovalExpenseClaimList = responseApprovalExpenseClaimList;
        animation = AnimationUtils.loadAnimation(context, R.anim.scale_with_alpha_animation);
        lastAnimationPosition = -1;
        this.context = context;
        this.homeActivity = homeActivity;
        this.approvalClickListener = approvalClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_approval_expense_claim, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.mBinding.frgApprovalsCbSelect.setTag(position);
        holder.mBinding.frgRequestShiftChangeLlMain.setTag(position);
        holder.setData(position);
        setAnimation(holder.mBinding.frgRequestShiftChangeLlMain, position);
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastAnimationPosition) {
            animation = AnimationUtils.loadAnimation(context, R.anim.scale_with_alpha_animation);
            viewToAnimate.startAnimation(animation);
            lastAnimationPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return responseApprovalExpenseClaimList.size();
    }

    public void selectAll() {
        for (int i = 0; i < responseApprovalExpenseClaimList.size(); i++) {
            responseApprovalExpenseClaimList.get(i).isSelected = true;
        }
        notifyDataSetChanged();
    }

    public void deSelectAll() {
        for (int i = 0; i < responseApprovalExpenseClaimList.size(); i++) {
            responseApprovalExpenseClaimList.get(i).isSelected = false;
        }
        notifyDataSetChanged();
    }

    public void set(List<ResponseApprovalExpenseClaim.Detail> responseApprovalExpenseClaim) {
        this.responseApprovalExpenseClaimList = responseApprovalExpenseClaim;
        notifyDataSetChanged();
    }

    public void addList(List<ResponseApprovalExpenseClaim.Detail> responseApprovalExpenseClaim) {
        this.responseApprovalExpenseClaimList.addAll(responseApprovalExpenseClaim);
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private RowApprovalExpenseClaimBinding mBinding;

        public MyViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);

            mBinding.frgApprovalsCbSelect.setOnCheckedChangeListener(
                    new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                responseApprovalExpenseClaimList.
                                        get((Integer) mBinding.frgApprovalsCbSelect.getTag())
                                        .isSelected = true;
                                setData((Integer) mBinding.frgApprovalsCbSelect.getTag());
                            } else {
                                responseApprovalExpenseClaimList
                                        .get((Integer) mBinding.frgApprovalsCbSelect.getTag())
                                        .isSelected = false;
                                setData((Integer) mBinding.frgApprovalsCbSelect.getTag());
                            }
                            approvalClickListener.onApprovalClicked(homeActivity.getString(R.string.approval_type_expense_claim));
                        }
                    });
        }

        @SuppressLint("SetTextI18n")
        public void setData(final int position) {
            mBinding.frgRowApprovalsClaimTvName.setText(responseApprovalExpenseClaimList.get(position).employeeID
                    + " - " +
                    responseApprovalExpenseClaimList.get(position).eFirstName);
            if (responseApprovalExpenseClaimList.get(position).isSelected) {
                mBinding.frgRowApprovalsClaimTvName.setTextColor(
                        context.getResources().getColor(R.color.blue));
                mBinding.frgApprovalsCbSelect.setChecked(true);
            } else {
                mBinding.frgRowApprovalsClaimTvName.setTextColor(
                        context.getResources().getColor(R.color.request_leave_heading_text));
                mBinding.frgApprovalsCbSelect.setChecked(false);
            }

            if (responseApprovalExpenseClaimList.get(position).claimType.equalsIgnoreCase(homeActivity.getString(R.string.claim_type_other))) {
                mBinding.frgRowApprovalClaimLlDateMain.setVisibility(View.GONE);
                mBinding.frgRowApprovalClaimLlPlaceMain.setVisibility(View.GONE);
            } else {
                mBinding.frgRowApprovalClaimLlDateMain.setVisibility(View.VISIBLE);
                mBinding.frgRowApprovalClaimLlPlaceMain.setVisibility(View.VISIBLE);
                mBinding.frgRowApprovalsClaimTvFromDate.setText(getFormattedDateTimeString(responseApprovalExpenseClaimList.get(position).fromDate,serverFormat, "dd MMM yyyy"));
                mBinding.frgRowApprovalsClaimTvToDate.setText(getFormattedDateTimeString(responseApprovalExpenseClaimList.get(position).toDate,serverFormat, "dd MMM yyyy"));
                mBinding.frgRowApprovalsClaimTvFromPlace.setText(responseApprovalExpenseClaimList.get(position).fromPlace);
                mBinding.frgRowApprovalsClaimTvToPlace.setText(responseApprovalExpenseClaimList.get(position).toPlace);
            }

            mBinding.frgRowApprovalsClaimTvClaimType.setText(responseApprovalExpenseClaimList.get(position).claimType);
            mBinding.frgRowApprovalsClaimTvAmount.setText(responseApprovalExpenseClaimList.get(position).totalExpenseAmount);
            mBinding.frgRequestShiftChangeLlMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ApprovalExpenseClaimDetailsScreen approvalExpenseClaimDetailsScreen = new ApprovalExpenseClaimDetailsScreen();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(context.getResources().getString(R.string.approval_claim_list), responseApprovalExpenseClaimList.get(position));
                    approvalExpenseClaimDetailsScreen.setArguments(bundle);
                    homeActivity.addFragment(approvalExpenseClaimDetailsScreen,
                            context.getResources().getString(R.string.str_expense_claim_details));
                }
            });

        }

    }

    public interface ApprovalClickListener {
        void onApprovalClicked(String type);
    }
}
