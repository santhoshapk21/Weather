package hrms.hrms.fragment.claims.details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hris365.R;
import com.hris365.databinding.RowClaimsDetailsBinding;

import java.util.List;

import hrms.hrms.model.ClaimDetails;

/**
 * Created by Yudiz on 20/06/16.
 */
public class ClaimDetailsAdapter extends RecyclerView.Adapter<ClaimDetailsAdapter.ViewHolder> {

    private Context context;
    private List<ClaimDetails> claimDetailsList;
    private View.OnClickListener onClickListener;

    public ClaimDetailsAdapter(Context context, List<ClaimDetails> claimDetailsList, View.OnClickListener onClickListener) {
        this.context = context;
        this.claimDetailsList = claimDetailsList;
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.row_claims_details, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mBinding.rowClaimTvAmount.setText(claimDetailsList.get(position).getAmount());
        holder.mBinding.rowClaimTvDesc.setText(claimDetailsList.get(position).getDesc());
        holder.mBinding.rowTvClaimType.setText(claimDetailsList.get(position).getType());
        holder.mBinding.rowClaimIvEdit.setTag(claimDetailsList.get(position));
        holder.mBinding.rowClaimIvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null)
                    onClickListener.onClick(view);
            }
        });
    }


    @Override
    public int getItemCount() {
        return claimDetailsList.size();
    }

    public void add(ClaimDetails claimDetails) {
        boolean isUpdate = false;
        for (int i = 0; i < claimDetailsList.size(); i++) {
            if (claimDetails.getId().equals(claimDetailsList.get(i).getId())) {
                changeData(i, claimDetails);
                notifyItemChanged(i);
                isUpdate = true;
                break;
            }
        }
        if (!isUpdate) {
            claimDetailsList.add(claimDetails);
            notifyItemInserted(claimDetailsList.size() - 1);
        }
    }

    private void changeData(int i, ClaimDetails claimDetails) {
        claimDetailsList.get(i).setId(claimDetails.getId());
        claimDetailsList.get(i).setDesc(claimDetails.getDesc());
        claimDetailsList.get(i).setAttachment(claimDetails.getAttachment());
        claimDetailsList.get(i).setType(claimDetails.getType());
        claimDetailsList.get(i).setAmount(claimDetails.getAmount());

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private RowClaimsDetailsBinding mBinding;

        public ViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }

    public List<ClaimDetails> getClaimExpensesList() {
        if (!claimDetailsList.isEmpty()) {
            return claimDetailsList;
        }
        return null;
    }
}
