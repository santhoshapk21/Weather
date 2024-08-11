package hrms.hrms.adapter.approvals;

import static hrms.hrms.activity.HomeActivity.context;
import static hrms.hrms.activity.HomeActivity.serverFormat;
import static hrms.hrms.utility.Utility.getFormattedDateTimeString;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hris365.R;
import com.hris365.databinding.AcceptRejectViewBinding;
import com.hris365.databinding.RowRequestDetailsScreenBinding;
import com.rey.material.app.BottomSheetDialog;

import java.util.List;

import hrms.hrms.fragment.request.RequestFragment;
import hrms.hrms.retrofit.model.approvals.leave.ResponseApprovalLeaveDetail;


/**
 * Created by yudiz on 14/03/18.
 */

public class ApprovalLeaveDetailAdapter extends
        RecyclerView.Adapter<ApprovalLeaveDetailAdapter.RequestDetailsViewHolder> {

    private LayoutInflater mInflater;
    private List<ResponseApprovalLeaveDetail.Details> detail;
    private Button btn_Cancel;

//        this.requestLeaveDetailsFragment = requestLeaveDetailsFragment;

    public ApprovalLeaveDetailAdapter(@NonNull Context context, List<ResponseApprovalLeaveDetail.Details> detail) {
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

        public void setData(final int position) {
            mBinding.frgRowRequestDetailsDate.setText(getFormattedDateTimeString(detail.get(position).Date,serverFormat,"dd MMM yyyy"));
//            mBinding.frgRowRequestDetailsDate.setText();
            mBinding.frgRowRequestDetailsType.setText(detail.get(position).Duration);
            detail.get(position).Status = "Accept";
            mBinding.frgRowRequestDetailsStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
                    final AcceptRejectViewBinding acceptRejectViewBinding = DataBindingUtil.inflate(mInflater, R.layout.accept_reject_view, null, false);
                    bottomSheetDialog.setContentView(acceptRejectViewBinding.getRoot());

                    acceptRejectViewBinding.acceptRejectViewCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            bottomSheetDialog.dismiss();
                        }
                    });

                    acceptRejectViewBinding.acceptRejectViewConfirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            detail.get(position).Status = acceptRejectViewBinding.acceptRejectViewFinal.getText().toString();
                            setTextColor(position);
                            bottomSheetDialog.dismiss();
                        }
                    });

                    acceptRejectViewBinding.acceptRejectViewAccept.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            acceptRejectViewBinding.acceptRejectViewAccept.setTextColor(Color.parseColor("#49c885"));
                            acceptRejectViewBinding.acceptRejectViewReject.setTextColor(Color.parseColor("#6a6a6a"));
                            acceptRejectViewBinding.acceptRejectViewFinal.setText("Accept");
                            return false;
                        }
                    });

                    acceptRejectViewBinding.acceptRejectViewReject.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            acceptRejectViewBinding.acceptRejectViewAccept.setTextColor(Color.parseColor("#6a6a6a"));
                            acceptRejectViewBinding.acceptRejectViewReject.setTextColor(Color.parseColor("#ff0202"));
                            acceptRejectViewBinding.acceptRejectViewFinal.setText("Reject");
                            return false;
                        }
                    });

                    bottomSheetDialog.show();
                }
            });

            setTextColor(position);
//            mBinding.frgRowRequestDetailsStatus.setText(detail.get(position).Status);
        }

        public void setTextColor(int position) {

            if (detail.get(position).Status
                    .equals("Accept")) {
                mBinding.frgRowRequestDetailsStatus
                        .setText("Accept");
                mBinding.frgRowRequestDetailsStatus
                        .setTextColor(context.getResources().getColor(R.color.present_green));

            } else if (detail.get(position).Status
                    .equals(RequestFragment.leaveStatus.Reject.toString())) {
                mBinding.frgRowRequestDetailsStatus
                        .setText("Reject");
                mBinding.frgRowRequestDetailsStatus.
                        setTextColor(context.getResources().getColor(R.color.abpresent_red));
            }
        }
    }

}

