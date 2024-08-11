package hrms.hrms.adapter.approvals;

import static hrms.hrms.activity.HomeActivity.serverFormat;
import static hrms.hrms.utility.Utility.getFormattedDateTimeString;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hris365.R;
import com.hris365.databinding.RowApprovalsLeaveScreenBinding;

import java.util.List;

import hrms.hrms.activity.HomeActivity;
import hrms.hrms.fragment.approval.ApprovalRequestFragment;
import hrms.hrms.retrofit.model.approvals.leave.ResponseApprovalLeave;

/**
 * Created by yudiz on 03/03/17.
 */

public class ApprovalsDataLeaveAdapter extends
        RecyclerView.Adapter<ApprovalsDataLeaveAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private List<ResponseApprovalLeave.Details> responseApprovalLeaves;
    private ApprovalRequestFragment approvalRequestFragment;
    private Context context;
    private int lastAnimationPosition;
    private Animation animation;
    private boolean isBind;
    ApprovalClickListener approvalClickListener;
    private HomeActivity homeActivity;
    public String leaveId = null;

    public ApprovalsDataLeaveAdapter(@NonNull Context context,
                                     List<ResponseApprovalLeave.Details> responseRequestLeaves,
                                     ApprovalRequestFragment approvalRequestFragment,
                                     HomeActivity homeActivity,
                                     ApprovalClickListener approvalClickListener) {
        this.responseApprovalLeaves = responseRequestLeaves;
        inflater = LayoutInflater.from(context);
        this.approvalRequestFragment = approvalRequestFragment;
        this.context = context;
        lastAnimationPosition = -1;
        this.homeActivity = homeActivity;
        this.approvalClickListener = approvalClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_approvals_leave_screen, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        isBind = false;
        holder.mBinding.frgApprovalsCbSelect.setTag(position);
        holder.mBinding.frgApprovalsLeaveLlMain.setTag(position);
        holder.setData(position);
        setAnimation(holder.mBinding.frgApprovalsLeaveLlMain, position);
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
        return responseApprovalLeaves.size();
    }

    public void selectAll() {
        leaveId = null;
        for (int i = 0; i < responseApprovalLeaves.size(); i++) {
            responseApprovalLeaves.get(i).setSelected(true);
        }
        notifyDataSetChanged();
    }

    public void deSelectAll() {
        for (int i = 0; i < responseApprovalLeaves.size(); i++) {
            responseApprovalLeaves.get(i).setSelected(false);
        }
        notifyDataSetChanged();
    }

    public void set(List<ResponseApprovalLeave.Details> responseApprovalLeaves) {
        this.responseApprovalLeaves=responseApprovalLeaves;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private RowApprovalsLeaveScreenBinding mBinding;

        public MyViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);

            mBinding.frgApprovalsCbSelect.setOnCheckedChangeListener(
                    new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                responseApprovalLeaves.get((Integer) mBinding.frgApprovalsCbSelect.getTag()).setSelected(true);
                                setData((Integer) mBinding.frgApprovalsCbSelect.getTag());
                            } else {
                                responseApprovalLeaves.get((Integer) mBinding.frgApprovalsCbSelect.getTag()).setSelected(false);
                                setData((Integer) mBinding.frgApprovalsCbSelect.getTag());
                            }

                            approvalClickListener.onApprovalClicked("Leave");
                        }
                    });
        }

        public void setData(final int position) {
            if (responseApprovalLeaves.get(position).isSelected()) {
                mBinding.frgRowApprovalsLeaveTvName.setText(responseApprovalLeaves.get(position).getEmployeeId()
                        +" - "+
                        responseApprovalLeaves.get(position).getEmployeeName());
                mBinding.frgRowApprovalsLeaveTvName.setTextColor(
                        context.getResources().getColor(R.color.blue));
                mBinding.frgApprovalsCbSelect.setChecked(true);
            } else {
                mBinding.frgRowApprovalsLeaveTvName.setText(responseApprovalLeaves.get(position).getEmployeeId()
                        +" - "+
                        responseApprovalLeaves.get(position).getEmployeeName());
                mBinding.frgRowApprovalsLeaveTvName.setTextColor(
                        context.getResources().getColor(R.color.request_leave_heading_text));
                mBinding.frgApprovalsCbSelect.setChecked(false);
            }

            mBinding.frgApprovalsLeaveLlMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
/*
                    ApprovalDetailsScreen approvalDetailsScreen = new ApprovalDetailsScreen();
                    Bundle bundle = new Bundle();
                    bundle.putString("LeaveAppId", responseApprovalLeaves.get(position).getLeaveAppId());
                    approvalDetailsScreen.setArguments(bundle);
                    homeActivity.addFragment(approvalDetailsScreen,
                            context.getResources().getString(R.string.str_approval_details));
*/
                    //Toast.makeText(context, "Under Development", Toast.LENGTH_SHORT).show();
                }
            });

            mBinding.frgRowApprovalsLeaveTvTitle.setText(
                    responseApprovalLeaves.get(position).getReasonForLeave());
            mBinding.frgRowApprovalsLeaveTvFromDate.setText( getFormattedDateTimeString(responseApprovalLeaves.get(position).getFromDate(), serverFormat,"dd MMM yyyy"));
            mBinding.frgRowApprovalsLeaveTvToDate.setText(getFormattedDateTimeString(responseApprovalLeaves.get(position).getToDate(), serverFormat,"dd MMM yyyy"));
            if (responseApprovalLeaves.get(position).getLeaveDuration().equals("Full"))
                mBinding.frgRowApprovalsLeaveTvType.setText("Full Day Leave");
            else if (responseApprovalLeaves.get(position).getLeaveDuration().equals("Half"))
                mBinding.frgRowApprovalsLeaveTvType.setText("Half Day Leave");
        }
    }

    public interface ApprovalClickListener {
        void onApprovalClicked(String type);
    }
}
