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
import com.hris365.databinding.RowApprovalsShiftChangeScreenBinding;

import java.util.List;

import hrms.hrms.activity.HomeActivity;
import hrms.hrms.fragment.approval.ApprovalRequestFragment;
import hrms.hrms.fragment.approval.ApprovalShiftChangeDetailsScreen;
import hrms.hrms.retrofit.model.approvals.leave.ResponseApprovalShiftChange;

/**
 * Created by yudiz on 06/03/17.
 */

public class ApprovalsShiftChangeAdapter extends RecyclerView.Adapter<ApprovalsShiftChangeAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ApprovalRequestFragment approvalRequestFragment;
    private List<ResponseApprovalShiftChange.Details> responseRequestShiftChange;
    private int lastAnimationPosition;
    private Animation animation;
    ApprovalClickListener approvalClickListener;
    HomeActivity homeActivity;
    private Context context;
    private boolean isBind;

    public ApprovalsShiftChangeAdapter(@NonNull Context context,
                                       List<ResponseApprovalShiftChange.Details> responseRequestShiftChange,
                                       ApprovalRequestFragment approvalRequestFragment,
                                       HomeActivity homeActivity,
                                       ApprovalClickListener approvalClickListener) {
        inflater = LayoutInflater.from(context);
        this.approvalRequestFragment = approvalRequestFragment;
        this.responseRequestShiftChange = responseRequestShiftChange;
        animation = AnimationUtils.loadAnimation(context, R.anim.scale_with_alpha_animation);
        lastAnimationPosition = -1;
        this.context = context;
        this.homeActivity = homeActivity;
        this.approvalClickListener = approvalClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_approvals_shift_change_screen, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        isBind = false;
        holder.mBinding.frgApprovalsCbSelect.setTag(position);
        holder.mBinding.frgRequestShiftChangeLlMain.setTag(position);
        holder.setData(position);
        setAnimation(holder.mBinding.frgRequestShiftChangeLlMain, position);
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
        return responseRequestShiftChange.size();
    }

    public void selectAll() {
        for (int i = 0; i < responseRequestShiftChange.size(); i++) {
            responseRequestShiftChange.get(i).setSelected(true);
        }
        notifyDataSetChanged();
    }

    public void deSelectAll() {
        for (int i = 0; i < responseRequestShiftChange.size(); i++) {
            responseRequestShiftChange.get(i).setSelected(false);
        }
        notifyDataSetChanged();
    }

    public void set(List<ResponseApprovalShiftChange.Details> responseApprovalShiftChnage) {
        this.responseRequestShiftChange = responseApprovalShiftChnage;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private RowApprovalsShiftChangeScreenBinding mBinding;

        public MyViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);

            mBinding.frgApprovalsCbSelect.setOnCheckedChangeListener(
                    new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                responseRequestShiftChange.
                                        get((Integer) mBinding.frgApprovalsCbSelect.getTag())
                                        .setSelected(true);
                                setData((Integer) mBinding.frgApprovalsCbSelect.getTag());
                            } else {
                                responseRequestShiftChange
                                        .get((Integer) mBinding.frgApprovalsCbSelect.getTag())
                                        .setSelected(false);
                                setData((Integer) mBinding.frgApprovalsCbSelect.getTag());
                            }
                            approvalClickListener.onApprovalClicked("Shift");
                        }
                    });
        }

        public void setData(final int position) {
            if (responseRequestShiftChange.get(position).isSelected()) {
                mBinding.frgRowApprovalsShiftTvName.setText(responseRequestShiftChange.get(position).EmployeeId
                        + " - " +
                        responseRequestShiftChange.get(position).EmployeeName);
                mBinding.frgRowApprovalsShiftTvName.setTextColor(
                        context.getResources().getColor(R.color.blue));
                mBinding.frgApprovalsCbSelect.setChecked(true);
            } else {
                mBinding.frgRowApprovalsShiftTvName.setText(responseRequestShiftChange.get(position).EmployeeId
                        + " - " +
                        responseRequestShiftChange.get(position).EmployeeName);
                mBinding.frgRowApprovalsShiftTvName.setTextColor(
                        context.getResources().getColor(R.color.request_leave_heading_text));
                mBinding.frgApprovalsCbSelect.setChecked(false);
            }

            mBinding.frgRequestShiftChangeLlMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ApprovalShiftChangeDetailsScreen approvalShiftChangeDetailsScreen = new ApprovalShiftChangeDetailsScreen();
                    Bundle bundle = new Bundle();
                    bundle.putString("ShiftRequestId", responseRequestShiftChange.get(position).ShiftRequestId);
                    bundle.putString("EmployeeID", responseRequestShiftChange.get(position).EmployeeId);
                    bundle.putString("EmployeeName", responseRequestShiftChange.get(position).EmployeeName);
                    bundle.putString("CreatedFromDate",getFormattedDateTimeString(responseRequestShiftChange.get(position).FromDate,serverFormat, "dd MMM yyyy") );
                    bundle.putString("CreatedToDate", getFormattedDateTimeString(responseRequestShiftChange.get(position).ToDate,serverFormat, "dd MMM yyyy") );
                    bundle.putString("CreatedFromTime",getFormattedDateTimeString(responseRequestShiftChange.get(position).ShiftStartTime,"hh:mm:ss", "HH:mm") );
                    bundle.putString("CreatedToTime", getFormattedDateTimeString(responseRequestShiftChange.get(position).ShiftEndTime,"hh:mm:ss", "HH:mm") );
                    bundle.putString("ShiftName", responseRequestShiftChange.get(position).ShiftName);
                    bundle.putString("Reason", responseRequestShiftChange.get(position).Reason);
                    approvalShiftChangeDetailsScreen.setArguments(bundle);
                    homeActivity.addFragment(approvalShiftChangeDetailsScreen,
                            context.getResources().getString(R.string.str_approval_details));
                }
            });

            mBinding.frgRowApprovalsShiftChangeTvTitle.setText(
                    responseRequestShiftChange.get(position).Reason);
            mBinding.frgRowApprovalsShiftChangeTvFromDate.setText(getFormattedDateTimeString(responseRequestShiftChange.get(position).FromDate,serverFormat, "dd MMM yyyy") );
            mBinding.frgRowApprovalsShiftChangeTvToDate.setText(getFormattedDateTimeString(responseRequestShiftChange.get(position).ToDate,serverFormat, "dd MMM yyyy") );
            mBinding.frgRowApprovalsChiftChangeTvFromTime.setText(getFormattedDateTimeString(responseRequestShiftChange.get(position).ShiftStartTime,"hh:mm:ss", "HH:mm") );
            mBinding.frgRowQpprovalsShiftChangeTvToTime.setText(getFormattedDateTimeString(responseRequestShiftChange.get(position).ShiftEndTime,"hh:mm:ss", "HH:mm") );
        }

    }

    public interface ApprovalClickListener {
        void onApprovalClicked(String type);
    }
}
