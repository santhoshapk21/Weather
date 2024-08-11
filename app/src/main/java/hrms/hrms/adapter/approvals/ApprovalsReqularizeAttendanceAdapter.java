package hrms.hrms.adapter.approvals;

import static hrms.hrms.activity.HomeActivity.serverFormat;
import static hrms.hrms.utility.Utility.getFormattedDateTimeString;

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
import com.hris365.databinding.RowApprovalsRegularizeAttendanceScreenBinding;

import java.util.List;

import hrms.hrms.activity.HomeActivity;
import hrms.hrms.fragment.approval.ApprovalAttendanceRegularizationFragment;
import hrms.hrms.fragment.approval.ApprovalRequestFragment;
import hrms.hrms.retrofit.model.approvals.leave.ResponseApprovalRegularizationAttendance;

/**
 * Created by yudiz on 06/03/17.
 */

public class ApprovalsReqularizeAttendanceAdapter extends
        RecyclerView.Adapter<ApprovalsReqularizeAttendanceAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ApprovalRequestFragment approvalRequestFragment;
    private List<ResponseApprovalRegularizationAttendance.Details> responseApprovalRegularizationAttendances;
    ApprovalClickListener approvalClickListener;
    HomeActivity homeActivity;
    private Context context;
    private int lastAnimationPosition;
    private Animation animation;
    private boolean isBind;

    public ApprovalsReqularizeAttendanceAdapter(@NonNull Context context,
                                                List<ResponseApprovalRegularizationAttendance.Details>
                                                        responseApprovalRegularizationAttendances,
                                                ApprovalRequestFragment approvalRequestFragment,
                                                HomeActivity homeActivity,
                                                ApprovalClickListener approvalClickListener) {
        inflater = LayoutInflater.from(context);
        this.approvalRequestFragment = approvalRequestFragment;
        this.responseApprovalRegularizationAttendances = responseApprovalRegularizationAttendances;
        this.context = context;
        this.homeActivity = homeActivity;
        lastAnimationPosition = -1;
        this.approvalClickListener = approvalClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_approvals_regularize_attendance_screen, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        isBind = false;
        holder.mBinding.frgApprovalsCbSelect.setTag(position);
        holder.mBinding.frgApprovalsRegularizeLlMain.setTag(position);
        holder.setData(position);
        setAnimation(holder.mBinding.frgApprovalsRegularizeLlMain, position);
        isBind = true;
    }


    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastAnimationPosition) {
            animation = AnimationUtils.loadAnimation(context, R.anim.scale_with_alpha_animation);
            viewToAnimate.startAnimation(animation);
            lastAnimationPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return responseApprovalRegularizationAttendances.size();
    }


    public void selectAll() {
        for (int i = 0; i < responseApprovalRegularizationAttendances.size(); i++) {
            responseApprovalRegularizationAttendances.get(i).setSelected(true);
        }
        notifyDataSetChanged();
    }

    public void deSelectAll() {
        for (int i = 0; i < responseApprovalRegularizationAttendances.size(); i++) {
            responseApprovalRegularizationAttendances.get(i).setSelected(false);
        }
        notifyDataSetChanged();
    }

    public void set(List<ResponseApprovalRegularizationAttendance.Details> responseApprovalRegularizationAttendances) {
        this.responseApprovalRegularizationAttendances=responseApprovalRegularizationAttendances;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private RowApprovalsRegularizeAttendanceScreenBinding mBinding;

        public MyViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);

            mBinding.frgApprovalsCbSelect.setOnCheckedChangeListener(
                    new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                responseApprovalRegularizationAttendances.
                                        get((Integer) mBinding.frgApprovalsCbSelect.getTag())
                                        .setSelected(true);
                                setData((Integer) mBinding.frgApprovalsCbSelect.getTag());
                            } else {
                                responseApprovalRegularizationAttendances
                                        .get((Integer) mBinding.frgApprovalsCbSelect.getTag())
                                        .setSelected(false);
                                setData((Integer) mBinding.frgApprovalsCbSelect.getTag());
                            }
                            approvalClickListener.onApprovalClicked("Regularisation");
                        }
                    });


        }

        public void setData(final int position) {
            if (responseApprovalRegularizationAttendances.get(position).isSelected()) {
                mBinding.frgRowApprovalsLeaveTvName.setText(responseApprovalRegularizationAttendances.get(position).EmployeeId
                        + " - " +
                        responseApprovalRegularizationAttendances.get(position).EmployeeName);
                mBinding.frgRowApprovalsLeaveTvName.setTextColor(
                        context.getResources().getColor(R.color.blue));
                mBinding.frgApprovalsCbSelect.setChecked(true);
            } else {
                mBinding.frgRowApprovalsLeaveTvName.setText(responseApprovalRegularizationAttendances.get(position).EmployeeId
                        + " - " +
                        responseApprovalRegularizationAttendances.get(position).EmployeeName);
                mBinding.frgRowApprovalsLeaveTvName.setTextColor(
                        context.getResources().getColor(R.color.request_leave_heading_text));
                mBinding.frgApprovalsCbSelect.setChecked(false);
            }

            mBinding.frgApprovalsRegularizeLlMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ApprovalAttendanceRegularizationFragment approvalAttendanceRegularizationFragment = new ApprovalAttendanceRegularizationFragment();

                    Bundle bundle = new Bundle();
                    bundle.putString("RegularisationRequestId", responseApprovalRegularizationAttendances.get(position).RegularisationRequestId);
                    bundle.putString("CategoryName", responseApprovalRegularizationAttendances.get(position).Category);
                    bundle.putString("EmployeeId", responseApprovalRegularizationAttendances.get(position).EmployeeId);
                    bundle.putString("EmployeeName", responseApprovalRegularizationAttendances.get(position).EmployeeName);
                    bundle.putString("CreatedFromDate",getFormattedDateTimeString(responseApprovalRegularizationAttendances.get(position).FromDate,serverFormat, "dd MMM yyyy"));
                    bundle.putString("CreatedToDate", getFormattedDateTimeString(responseApprovalRegularizationAttendances.get(position).ToDate,serverFormat, "dd MMM yyyy"));
                    bundle.putString("Reason", responseApprovalRegularizationAttendances.get(position).Description);
                    approvalAttendanceRegularizationFragment.setArguments(bundle);
                    homeActivity.addFragment(approvalAttendanceRegularizationFragment,
                            context.getResources().getString(R.string.str_approval_details));
                }
            });

            mBinding.frgRowApprovalsLeaveTvTitle.setText(
                    responseApprovalRegularizationAttendances.get(position).Description);
            mBinding.frgRowApprovalsLeaveTvFromDate.setText(getFormattedDateTimeString(responseApprovalRegularizationAttendances.get(position).FromDate,serverFormat, "dd MMM yyyy"));
            mBinding.frgRowApprovalsLeaveTvToDate.setText(getFormattedDateTimeString(responseApprovalRegularizationAttendances.get(position).ToDate,serverFormat, "dd MMM yyyy"));
        }
    }

    public interface ApprovalClickListener {
        void onApprovalClicked(String type);
    }
}
