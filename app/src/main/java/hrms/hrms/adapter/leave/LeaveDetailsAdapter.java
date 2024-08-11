package hrms.hrms.adapter.leave;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hris365.R;
import com.hris365.databinding.RowLeaveDetailsBinding;

import java.util.List;

import hrms.hrms.retrofit.model.Leave.LeaveDetails.ResponseLeaveDetails;


/**
 * Created by Yudiz on 20/06/16.
 */
public class LeaveDetailsAdapter extends RecyclerView.Adapter<LeaveDetailsAdapter.ViewHolder> {

    private Context context;
    private List<ResponseLeaveDetails.Details> leaveDetailses;

    public LeaveDetailsAdapter(Context context, List<ResponseLeaveDetails.Details> leaveDetailses) {
        this.context = context;
        this.leaveDetailses = leaveDetailses;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.row_leave_details, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(position);
    }


    @Override
    public int getItemCount() {
        return leaveDetailses.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private RowLeaveDetailsBinding mBinding;

        public ViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }

        private void setData(int position) {

            if(leaveDetailses.get(position).IsPaid) {
                mBinding.rowLeaveTvBalance.setText(leaveDetailses.get(position).Total);
                mBinding.rowLeaveTvCarryForward.setText(
                        (leaveDetailses.get(position).IsCarryForward) ? "Yes" : "No"
                );

                mBinding.rowLeaveTvTaken.setText(leaveDetailses.get(position).Debit);
                mBinding.rowLeaveDetailsTvLeaveType.setText(
                        leaveDetailses.get(position).LeaveType);
            }else{
                mBinding.rowLeaveTvBalance.setText("NA");
                mBinding.rowLeaveTvCarryForward.setText("NA");

                mBinding.rowLeaveTvTaken.setText(leaveDetailses.get(position).Debit);
                mBinding.rowLeaveDetailsTvLeaveType.setText(
                        leaveDetailses.get(position).LeaveType);
            }
        }
    }

}
