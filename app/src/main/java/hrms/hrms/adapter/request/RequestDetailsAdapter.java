package hrms.hrms.adapter.request;

import static hrms.hrms.activity.HomeActivity.context;
import static hrms.hrms.activity.HomeActivity.serverFormat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hris365.R;
import com.hris365.databinding.RowRequestDetailsScreenBinding;

import java.util.List;

import hrms.hrms.fragment.request.RequestFragment;
import hrms.hrms.fragment.request.RequestLeaveDetailsFragment;
import hrms.hrms.retrofit.model.request.leave.ResponseLeaveDetail;
import hrms.hrms.utility.Utility;


/**
 * Created by yudiz on 05/04/17.
 */

public class RequestDetailsAdapter extends
        RecyclerView.Adapter<RequestDetailsAdapter.RequestDetailsViewHolder> {

    RequestLeaveDetailsFragment requestLeaveDetailsFragment;
    private LayoutInflater mInflater;
    private List<ResponseLeaveDetail.Details> detail;

//        this.requestLeaveDetailsFragment = requestLeaveDetailsFragment;

    public RequestDetailsAdapter(@NonNull Context context, List<ResponseLeaveDetail.Details> detail) {
        mInflater = LayoutInflater.from(context);
        this.detail = detail;
        Log.i("TAG1", detail + "");
    }

    @Override
    public RequestDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.row_request_details_screen, parent, false);
        return new RequestDetailsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RequestDetailsViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return detail.size();
    }

/*
    public String getDate(int position) {
            return requestLeaveDetailsFragment.getFormatDate(serverFormat, "dd MMM yyyy", detail.get(position).getLeaveDate());
    }
*/

    public class RequestDetailsViewHolder extends RecyclerView.ViewHolder {

        private RowRequestDetailsScreenBinding mBinding;

        public RequestDetailsViewHolder(View itemView) {
            super(itemView);

            mBinding = DataBindingUtil.bind(itemView);

        }

        public void setData(int position) {
            mBinding.frgRowRequestDetailsDate.setText(Utility.getFormattedDateTimeString(detail.get(position).Date,serverFormat, "dd MMM yyyy"));
//            mBinding.frgRowRequestDetailsDate.setText();
            mBinding.frgRowRequestDetailsType.setText(detail.get(position).Duration);
            mBinding.frgRowRequestDetailsStatus.setText(detail.get(position).Status);

            setTextColor(position);
        }

        public void setTextColor(int position) {

            if (detail.get(position).Status
                    .equals(RequestFragment.leaveStatus.Approve.toString())) {
                mBinding.frgRowRequestDetailsStatus
                        .setText(RequestFragment.leaveStatus.Approve.getValue());
                mBinding.frgRowRequestDetailsStatus
                        .setTextColor(context.getResources().getColor(R.color.present_green));

            } else if (detail.get(position).Status
                    .equals(RequestFragment.leaveStatus.Pending.toString())) {
                mBinding.frgRowRequestDetailsStatus
                        .setText(RequestFragment.leaveStatus.Pending.getValue());
                mBinding.frgRowRequestDetailsStatus
                        .setTextColor(context.getResources().getColor(R.color.blue));

            } else if (detail.get(position).Status
                    .equals(RequestFragment.leaveStatus.Reject.toString())) {
                mBinding.frgRowRequestDetailsStatus
                        .setText(RequestFragment.leaveStatus.Reject.getValue().toString());
                mBinding.frgRowRequestDetailsStatus.
                        setTextColor(context.getResources().getColor(R.color.abpresent_red));
            } else if (detail.get(position).Status
                    .equals(RequestFragment.leaveStatus.Cancel.toString())) {
                mBinding.frgRowRequestDetailsStatus
                        .setText(RequestFragment.leaveStatus.Cancel.getValue().toString());
                mBinding.frgRowRequestDetailsStatus.
                        setTextColor(context.getResources().getColor(R.color.abpresent_red));
            }
        }
    }
}
