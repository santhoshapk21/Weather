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
import com.hris365.databinding.RowApprovalsMarkAttendanceScreenBinding;

import java.util.List;

import hrms.hrms.activity.HomeActivity;
import hrms.hrms.fragment.approval.ApprovalMarkAttendanceDetailFragment;
import hrms.hrms.fragment.approval.ApprovalRequestFragment;
import hrms.hrms.retrofit.model.approvals.leave.ResponseApprovalMarkAttendance;


/**
 * Created by yudiz on 06/03/17.
 */

public class ApprovalsMarkAttendanceAdapter extends
        RecyclerView.Adapter<ApprovalsMarkAttendanceAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ApprovalRequestFragment approvalRequestFragment;
    private List<ResponseApprovalMarkAttendance.Details> responseApprovalMarkAttendances;
    private int lastAnimationPosition;
    private Animation animation;
    private Context context;
    private boolean isBind;
    ApprovalClickListener approvalClickListener;
    private HomeActivity homeActivity;

    public ApprovalsMarkAttendanceAdapter(@NonNull Context context,
                                          List<ResponseApprovalMarkAttendance.Details> responseApprovalMarkAttendances,
                                          ApprovalRequestFragment approvalRequestFragment,
                                          HomeActivity homeActivity,
                                          ApprovalClickListener approvalClickListener) {
        inflater = LayoutInflater.from(context);
        this.approvalRequestFragment = approvalRequestFragment;
        this.responseApprovalMarkAttendances = responseApprovalMarkAttendances;
        lastAnimationPosition = -1;
        this.approvalClickListener = approvalClickListener;
        this.homeActivity = homeActivity;
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_approvals_mark_attendance_screen, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        isBind = false;
        holder.mBinding.frgApprovalsCbSelect.setTag(position);
        holder.mBinding.frgApprovalsMarkAttendanceLlMain.setTag(position);
        holder.setData(position);
        setAnimation(holder.mBinding.frgApprovalsMarkAttendanceLlMain, position);
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
        return responseApprovalMarkAttendances.size();
    }

    public void selectAll() {
        for (int i = 0; i < responseApprovalMarkAttendances.size(); i++) {
            responseApprovalMarkAttendances.get(i).setSelected(true);
        }
        notifyDataSetChanged();
    }

    public void deSelectAll() {
        for (int i = 0; i < responseApprovalMarkAttendances.size(); i++) {
            responseApprovalMarkAttendances.get(i).setSelected(false);
        }
        notifyDataSetChanged();
    }

    public void set(List<ResponseApprovalMarkAttendance.Details> responseApprovalMarkAttendance) {
        this.responseApprovalMarkAttendances = responseApprovalMarkAttendance;
        notifyDataSetChanged();
    }

    public void addList(List<ResponseApprovalMarkAttendance.Details> responseApprovalMarkAttendance) {
        this.responseApprovalMarkAttendances.addAll(responseApprovalMarkAttendance);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private RowApprovalsMarkAttendanceScreenBinding mBinding;

        public MyViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);

            mBinding.frgApprovalsCbSelect.setOnCheckedChangeListener(
                    (buttonView, isChecked) -> {
                        if (isChecked) {
                            responseApprovalMarkAttendances.
                                    get((Integer) mBinding.frgApprovalsCbSelect.getTag())
                                    .setSelected(true);
                            setData((Integer) mBinding.frgApprovalsCbSelect.getTag());
                        } else {
                            responseApprovalMarkAttendances
                                    .get((Integer) mBinding.frgApprovalsCbSelect.getTag())
                                    .setSelected(false);
                            setData((Integer) mBinding.frgApprovalsCbSelect.getTag());
                        }
                        approvalClickListener.onApprovalClicked("MarkAttendance");
                    });


        }

        public void setData(final int position) {
            if (responseApprovalMarkAttendances.get(position).isSelected()) {
                mBinding.frgRowApprovalsLeaveTvName.setText(responseApprovalMarkAttendances.get(position).EmployeeID
                        + " - " +
                        responseApprovalMarkAttendances.get(position).EmployeeName);
                mBinding.frgRowApprovalsLeaveTvName.setTextColor(
                        context.getResources().getColor(R.color.blue));
                mBinding.frgApprovalsCbSelect.setChecked(true);
            } else {
                mBinding.frgRowApprovalsLeaveTvName.setText(responseApprovalMarkAttendances.get(position).EmployeeID
                        + " - " +
                        responseApprovalMarkAttendances.get(position).EmployeeName);
                mBinding.frgRowApprovalsLeaveTvName.setTextColor(
                        context.getResources().getColor(R.color.request_leave_heading_text));
                mBinding.frgApprovalsCbSelect.setChecked(false);
            }

            mBinding.frgApprovalsMarkAttendanceLlMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ApprovalMarkAttendanceDetailFragment approvalMarkAttendanceDetailFragment = new ApprovalMarkAttendanceDetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("GPSAttendanceID", responseApprovalMarkAttendances.get(position).GPSAttendanceID);
                    bundle.putString("EmployeeID", responseApprovalMarkAttendances.get(position).EmployeeID);
                    bundle.putString("EmployeeName", responseApprovalMarkAttendances.get(position).EmployeeName);
                    bundle.putString("CreatedDate", getFormattedDateTimeString(responseApprovalMarkAttendances.get(position).CreatedDate, serverFormat,"dd MMM yyyy"));
                    bundle.putString("CreatedTime", getFormattedDateTimeString(responseApprovalMarkAttendances.get(position).CreatedDate, serverFormat,"HH:mm"));
                    bundle.putString("InLocation", responseApprovalMarkAttendances.get(position).InLocation);
                    bundle.putString("ImageURL", responseApprovalMarkAttendances.get(position).ImageURL);
                    bundle.putString("InReason", responseApprovalMarkAttendances.get(position).InReason);
                    approvalMarkAttendanceDetailFragment.setArguments(bundle);
                    homeActivity.addFragment(approvalMarkAttendanceDetailFragment,
                            context.getResources().getString(R.string.str_approval_details));
                }
            });

            mBinding.frgApprovalsMarkAttendanceTvDate.setText( getFormattedDateTimeString(responseApprovalMarkAttendances.get(position).CreatedDate, serverFormat,"dd MMM yyyy"));
            mBinding.frgApprovalsMarkAttendanceTvPunchIn.setText(getFormattedDateTimeString(responseApprovalMarkAttendances.get(position).CreatedDate, serverFormat,"HH:mm"));
        }
    }

    public interface ApprovalClickListener {
        void onApprovalClicked(String type);
    }
}
