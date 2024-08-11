package hrms.hrms.adapter.approvals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hris365.R;
import com.hris365.databinding.RowRequestHeaderScreenBinding;

/**
 * Created by yudiz on 03/03/17.
 */

public class ApprovalHeaderAdapter extends RecyclerView.Adapter<ApprovalHeaderAdapter.MyViewHolder> {

    private int menuIcon[] = {
            R.drawable.ic_leave,
            R.drawable.ic_shift_change,
            R.drawable.ic_attendance,
            R.drawable.ic_regularize,
            R.drawable.ic_mark_attendance_selected,
//            R.drawable.ic_claims
    };
    private int menudeselect[] = {
            R.drawable.ic_leave_deselected,
            R.drawable.ic_shift_change_deselected,
            R.drawable.ic_attendance_deselected,
            R.drawable.ic_regularize_deselected,
            R.drawable.ic_mark_attendance_deselected,
//            R.drawable.ic_claims_unselected
    };
    private int menuName[] = {R.string.leave,
            R.string.shift_change,
            R.string.str_attendance_correction,
            R.string.str_regularization_attendance,
            R.string.str_mark_attandance,
//            R.string.str_claim

    };

    private LayoutInflater inflater;
    private View.OnClickListener onClickListener;
    private Context context;
    private int selectedPosition = 0;
    public String attendance;
    private String leave;
    private String shift;
    private String regularization;
    private String correction;
    private String expenseClaim;


    public ApprovalHeaderAdapter(@NonNull Context context, View.OnClickListener onClickListener) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.onClickListener = onClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_request_header_screen, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mBinding.rowRequestLlHeaderMain.setTag(position);
        holder.initView(position);
    }

    @Override
    public int getItemCount() {
        return menuIcon.length;
    }

    public void selecter(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setCounts(String leave, String shift, String correction, String regularization, String attendance, String claim) {
        this.leave = leave;
        this.shift = shift;
        this.correction = correction;
        this.regularization = regularization;
        this.attendance = attendance;
        this.expenseClaim = claim;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RowRequestHeaderScreenBinding mBinding;

        public MyViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
            mBinding.rowRequestLlHeaderMain.setOnClickListener(this);
        }

        public void initView(int position) {
            if (selectedPosition == position) {
                mBinding.rowRequestIvHeaderImage.setImageResource(menuIcon[position]);
                mBinding.rowRequestTvHeaderTitle.setText(menuName[position]);
                mBinding.rowRequestTvHeaderTitle.setTextColor(
                        context.getResources().getColor(R.color.request_header_text_seleted));
            } else {
                mBinding.rowRequestIvHeaderImage.setImageResource(menudeselect[position]);
                mBinding.rowRequestTvHeaderTitle.setText(menuName[position]);
                mBinding.rowRequestTvHeaderTitle.setTextColor(
                        context.getResources().getColor(R.color.request_header_text_deseleted));
            }
            if (position == 0) {
                mBinding.rowHomeTvNotificationCount.setText(leave);
            } else if (position == 1) {
                mBinding.rowHomeTvNotificationCount.setText(shift);
            } else if (position == 2) {
                mBinding.rowHomeTvNotificationCount.setText(correction);
            } else if (position == 3) {
                mBinding.rowHomeTvNotificationCount.setText(regularization);
            } else if (position == 4) {
                mBinding.rowHomeTvNotificationCount.setText(attendance);
            } else if (position == 5) {
                mBinding.rowHomeTvNotificationCount.setText(expenseClaim);
            }
        }

        @Override
        public void onClick(View v) {
            selecter((Integer) v.getTag());
            onClickListener.onClick(v);
        }
    }

}
