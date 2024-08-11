package hrms.hrms.adapter.approvals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hris365.R;
import com.hris365.databinding.RowRequestDetailsScreenBinding;

/**
 * Created by yudiz on 05/04/17.
 */

public class ApprovalsDetailsAdapter extends
        RecyclerView.Adapter<ApprovalsDetailsAdapter.RequestDetailsViewHolder> {

    private LayoutInflater mInflater;


    public ApprovalsDetailsAdapter(@NonNull Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RequestDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.row_approvals_details_screen, parent, false);
        return new RequestDetailsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RequestDetailsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class RequestDetailsViewHolder extends RecyclerView.ViewHolder {

        private RowRequestDetailsScreenBinding mBinding;

        public RequestDetailsViewHolder(View itemView) {
            super(itemView);

            mBinding = DataBindingUtil.bind(itemView);
        }
    }
}
