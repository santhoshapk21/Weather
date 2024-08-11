package hrms.hrms.adapter.approvals;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hris365.R;
import com.hris365.databinding.RowRvExpenseDetailsExpensesBinding;

import java.util.List;

import hrms.hrms.retrofit.model.approvals.leave.ResponseApprovalExpenseClaim;
import hrms.hrms.utility.Utility;


public class ApprovalsClaimDetailExpensesAdapter extends RecyclerView.Adapter<ApprovalsClaimDetailExpensesAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    public List<ResponseApprovalExpenseClaim.Expense> expenseList;
    private Animation animation;
    ApprovalClickListener approvalClickListener;
    private Context context;


    public ApprovalsClaimDetailExpensesAdapter(@NonNull Context context,
                                               List<ResponseApprovalExpenseClaim.Expense> expenseList,
                                               ApprovalClickListener approvalClickListener) {
        inflater = LayoutInflater.from(context);
        this.expenseList = expenseList;
        animation = AnimationUtils.loadAnimation(context, R.anim.scale_with_alpha_animation);
        this.context = context;
        this.approvalClickListener = approvalClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_rv_expense_details_expenses, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }


    public void set(List<ResponseApprovalExpenseClaim.Expense> expenseList) {
        this.expenseList = expenseList;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private RowRvExpenseDetailsExpensesBinding mBinding;

        public MyViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);

        }

        @SuppressLint("SetTextI18n")
        public void setData(final int position) {
            mBinding.rowRvExpenseDetailTvExpenseName.setText(expenseList.get(position).expenceName);
            if (TextUtils.isEmpty(expenseList.get(position).description)) {
                mBinding.rowRvExpenseDetailLlDescriptionMain.setVisibility(View.GONE);
            } else {
                mBinding.rowRvExpenseDetailTvDescription.setText(expenseList.get(position).description);
            }
            if (TextUtils.isEmpty(expenseList.get(position).receiptNumber)) {
                mBinding.rowRvExpenseDetailLlReceiptMain.setVisibility(View.GONE);
            } else {
                mBinding.rowRvExpenseDetailTvReceipt.setText(expenseList.get(position).receiptNumber);
            }

            mBinding.rowRvExpenseDetailEtAmount.setText(expenseList.get(position).amount);
            mBinding.rowRvExpenseDetailEtAmount.setText(expenseList.get(position).amount);
            if (TextUtils.isEmpty(expenseList.get(position).attachmentPath)) {
                mBinding.rowRvExpenseDetailLlAttachmentMain.setVisibility(View.GONE);
            } else {
                Utility.loadImage(context, expenseList.get(position).attachmentPath, mBinding.rowRvExpenseDetailIvAttachment);
            }
        }

    }
    public interface ApprovalClickListener {
        void onApprovalClicked(String type);
    }
}
