package hrms.hrms.fragment.claims;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hris365.R;
import com.hris365.databinding.RowClaimBinding;

import hrms.hrms.model.Claim;
import hrms.hrms.model.ClaimDetails;
import hrms.hrms.retrofit.model.claim.ResponseClaimList;
import hrms.hrms.activity.HomeActivity;
import hrms.hrms.utility.Utility;

import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by Yudiz on 20/06/16.
 */
public class ClaimAdapter extends RecyclerView.Adapter<ClaimAdapter.ViewHolder> {

    private ClaimsFragment claimsFragment;
    Context context;
    private List<ResponseClaimList.Details> claimList;
    private View.OnClickListener onClickListener;

    public ClaimAdapter(ClaimsFragment claimsFragment, List<ResponseClaimList.Details> claimList, View.OnClickListener onClickListener, Context context) {
        this.claimsFragment = claimsFragment;
        this.claimList = claimList;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.row_claim, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mBinding.rowClaimTvStatus.setText(claimList.get(position).Status);
        holder.mBinding.rowClaimTvType.setText(claimList.get(position).ClaimType);
        holder.mBinding.rowClaimTvDate.setText(Utility.getFormattedDateTimeString(claimList.get(position).ApplicationDate, HomeActivity.serverFormat, "dd MMM yyyy"));
        holder.mBinding.rowClaimTvApproved.setText(claimList.get(position).TotalExpenseAmount);
        holder.mBinding.rowClaimTvSubmitted.setText(claimList.get(position).TotalExpenseAmount);

    }


    @Override
    public int getItemCount() {
        return claimList.size();
    }

    public void add(List<ClaimDetails> claimDetails, Claim claim) {
/*
        int lastPos = claimList.size() - 1;
        for (int i = 0; i < claimDetails.size(); i++) {
            Claim tmpClaim = new Claim();
            tmpClaim.setSubmitted(ExpenseClaim.getSubmitted());
            tmpClaim.setApproval(ExpenseClaim.getApproval());
            tmpClaim.setStatus(ExpenseClaim.getStatus());
            tmpClaim.setDate(ExpenseClaim.getDate());
            tmpClaim.setType(claimDetails.get(i).getType());
            claimList.add(tmpClaim);
        }
        notifyItemRangeChanged(lastPos, claimList.size() - 1);
*/
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private RowClaimBinding mBinding;

        public ViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }


}
