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

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hris365.R;
import com.hris365.databinding.RowApprovalsAttendanceCurrectionScreenBinding;

import java.util.List;

import hrms.hrms.activity.HomeActivity;
import hrms.hrms.fragment.approval.ApprovalAttendanceCorrectionDetailsScreen;
import hrms.hrms.fragment.approval.ApprovalRequestFragment;
import hrms.hrms.retrofit.model.approvals.leave.ResponseApprovalAttendanceCorrection;

/**
 * Created by yudiz on 06/03/17.
 */

public class ApprovalsAttendanceCorrectionRequestAdapter
        extends RecyclerView.Adapter<ApprovalsAttendanceCorrectionRequestAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ApprovalRequestFragment requestFragment;
    private List<ResponseApprovalAttendanceCorrection.Details> responseRequestCorrections;
    HomeActivity     homeActivity;
    private Context context;
    private int lastAnimationPosition;
    private Animation animation;
    ApprovalClickListener approvalClickListener;
    private boolean isBind;

    public ApprovalsAttendanceCorrectionRequestAdapter(@NonNull Context context,
                                                       List<ResponseApprovalAttendanceCorrection.Details> responseRequestCorrections,
                                                       ApprovalRequestFragment requestFragment,
                                                       HomeActivity homeActivity,
                                                       ApprovalClickListener approvalClickListener) {
        inflater = LayoutInflater.from(context);
        this.requestFragment = requestFragment;
        this.responseRequestCorrections = responseRequestCorrections;
        this.context = context;
        this.homeActivity = homeActivity;
        lastAnimationPosition = -1;
        this.approvalClickListener = approvalClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_approvals_attendance_currection_screen, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        isBind = false;
        holder.mBinding.frgApprovalsCbSelect.setTag(position);
        holder.mBinding.frgRowApprovalsAttendanceLlMain.setTag(position);
        holder.setData(position);
        setAnimation(holder.mBinding.frgRowApprovalsAttendanceLlMain, position);
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
        return responseRequestCorrections.size();
    }


    public void selectAll() {
        for (int i = 0; i < responseRequestCorrections.size(); i++) {
            responseRequestCorrections.get(i).setSelected(true);
        }

        notifyDataSetChanged();
    }

    public void deSelectAll() {
        for (int i = 0; i < responseRequestCorrections.size(); i++) {
            responseRequestCorrections.get(i).setSelected(false);
        }
        notifyDataSetChanged();
    }

    public void set(List<ResponseApprovalAttendanceCorrection.Details> responseRequestCorrections) {
        this.responseRequestCorrections=responseRequestCorrections;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private RowApprovalsAttendanceCurrectionScreenBinding mBinding;

        public MyViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);

            mBinding.frgApprovalsCbSelect.setOnCheckedChangeListener(
                    (buttonView, isChecked) -> {
                        if (isChecked) {
                            responseRequestCorrections.
                                    get((Integer) mBinding.frgApprovalsCbSelect.getTag())
                                    .setSelected(true);
                            setData((Integer) mBinding.frgApprovalsCbSelect.getTag());
                        } else {
                            responseRequestCorrections
                                    .get((Integer) mBinding.frgApprovalsCbSelect.getTag())
                                    .setSelected(false);
                            setData((Integer) mBinding.frgApprovalsCbSelect.getTag());
                        }
                        approvalClickListener.onApprovalClicked("Correction");
                    });
        }

        public void setData(final int position) {
            if (responseRequestCorrections.get(position).isSelected()) {
                mBinding.frgRowApprovalsLeaveTvName.setText(responseRequestCorrections.get(position).EmployeeId
                        + " - " +
                        responseRequestCorrections.get(position).EmployeeName);
                mBinding.frgRowApprovalsLeaveTvName.setTextColor(
                        context.getResources().getColor(R.color.blue));
                mBinding.frgApprovalsCbSelect.setChecked(true);
            } else {
                mBinding.frgRowApprovalsLeaveTvName.setText(responseRequestCorrections.get(position).EmployeeId
                        + " - " +
                        responseRequestCorrections.get(position).EmployeeName);
                mBinding.frgRowApprovalsLeaveTvName.setTextColor(
                        context.getResources().getColor(R.color.request_leave_heading_text));
                mBinding.frgApprovalsCbSelect.setChecked(false);
            }

            mBinding.frgRowApprovalsAttendanceLlMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ApprovalAttendanceCorrectionDetailsScreen approvalAttendanceCorrectionDetailsScreen = new ApprovalAttendanceCorrectionDetailsScreen();
                    Bundle bundle = new Bundle();
                    bundle.putString("CorrectionReqId", responseRequestCorrections.get(position).CorrectionReqId);
                    bundle.putString("RequestType", responseRequestCorrections.get(position).RequestType);
                    bundle.putString("EmployeeId", responseRequestCorrections.get(position).EmployeeId);
                    bundle.putString("EmployeeName", responseRequestCorrections.get(position).EmployeeName);
                    bundle.putString("CreatedFromDate", getFormattedDateTimeString(responseRequestCorrections.get(position).RequestDate,serverFormat, "dd MMM yyyy"));
                    bundle.putString("CreatedFromTime",getFormattedDateTimeString(responseRequestCorrections.get(position).RequestTime,"hh:mm:ss", "dd MMM yyyy"));
                    bundle.putString("Reason", responseRequestCorrections.get(position).Reason);
                    approvalAttendanceCorrectionDetailsScreen.setArguments(bundle);
                    homeActivity.addFragment(approvalAttendanceCorrectionDetailsScreen,
                            context.getResources().getString(R.string.str_approval_details));
                }
            });

            mBinding.frgRowApprovalsCorrectionTvTitle.setText(responseRequestCorrections.get(position).Reason);
            mBinding.frgRowApprovalsCorrectionDate.setText(getFormattedDateTimeString(responseRequestCorrections.get(position).RequestDate,serverFormat, "dd MMM yyyy"));
            mBinding.frgRowApprovalsCorrectionTime.setText(getFormattedDateTimeString(responseRequestCorrections.get(position).RequestTime,"hh:mm:ss", "dd MMM yyyy"));
        }

    }

    public interface ApprovalClickListener {
        void onApprovalClicked(String type);
    }
}
