package hrms.hrms.adapter.request;

import static hrms.hrms.activity.HomeActivity.serverFormat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hris365.R;
import com.hris365.databinding.RowRequestMarkAttendanceScreenBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

import hrms.hrms.fragment.request.RequestFragment;
import hrms.hrms.retrofit.model.request.attendance.ResponseRequestAttendance;
import hrms.hrms.utility.Utility;


/**
 * Created by yudiz on 06/03/17.
 */

public class RequestMarkAttendanceAdapter
        extends RecyclerView.Adapter<RequestMarkAttendanceAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private RequestFragment requestFragment;
    private List<ResponseRequestAttendance> responseRequestAttendances;
    private int lastAnimationPosition;
    private Animation animation;
    private Context context;
    private View.OnClickListener onclick;

    public RequestMarkAttendanceAdapter(
            @NonNull Context context,
            List<ResponseRequestAttendance> responseRequestAttendances,
            RequestFragment requestFragment, View.OnClickListener onclick) {
        inflater = LayoutInflater.from(context);
        this.onclick = onclick;
        this.requestFragment = requestFragment;
        this.responseRequestAttendances = responseRequestAttendances;
        lastAnimationPosition = -1;
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_request_mark_attendance_screen, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setData(position);
        setAnimation(holder.mBinding.frgRequestMarkAttendanceRlMain, position);
    }

    @Override
    public void onViewDetachedFromWindow(MyViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public int getItemCount() {
        return responseRequestAttendances.size();
    }


    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastAnimationPosition) {
            animation = AnimationUtils.loadAnimation(context, R.anim.scale_with_alpha_animation);
            viewToAnimate.startAnimation(animation);
            lastAnimationPosition = position;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private RowRequestMarkAttendanceScreenBinding mBinding;

        public MyViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }

        public void setData(int position) {
            mBinding.frgRowRequestMarkAttendanceTvDate.setText(Utility.getFormattedDateTimeString(responseRequestAttendances.get(position).getDate(), serverFormat, "dd MMM yyyy"));

            if (responseRequestAttendances.get(position).getDate() != null)
                mBinding.frgRowRequestMarkAttendanceTvPunchIn
                        .setText(Utility.getFormattedDateTimeString(responseRequestAttendances.get(position).getDate(), serverFormat, "HH:mm"));
            else
                mBinding.frgRowRequestMarkAttendanceLlPunchIn.setVisibility(View.GONE);

            if (responseRequestAttendances.get(position).getOutTime() != null)
                mBinding.frgRowRequestMarkAttendanceTvPunchOut
                        .setText(responseRequestAttendances.get(position).getOutTime());
            else
                mBinding.frgRowRequestMarkAttendanceLlPunchOut.setVisibility(View.GONE);

            mBinding.frgRequestMarkAttendanceLlMain.setTag(responseRequestAttendances.get(position).getAttendanceId());
            mBinding.frgRequestMarkAttendanceLlMain.setOnClickListener(onclick);
            if (responseRequestAttendances != null && responseRequestAttendances.size() > 0) {
                if (responseRequestAttendances.get(position).getImagePath()!=null && !responseRequestAttendances.get(position).getImagePath().equals("")) {
                    Picasso.get().load(responseRequestAttendances.get(position).getImagePath()).placeholder(R.drawable.ic_no_image).into(mBinding.frgApprovalAttendanceIvImage);
                }
            }
            setTextColor(position);
        }

        public void setTextColor(int position) {
            if (responseRequestAttendances.get(position).getStatus().equals(RequestFragment.leaveStatus.Approve.toString())) {
//
//                mBinding.frgRowRequestMarkAttendanceTvStatus
//                        .setText(RequestFragment.leaveStatus.Approve.getValue());
//                mBinding.frgRowRequestMarkAttendanceTvStatus
//                        .setTextColor(context.getResources().getColor(R.color.present_green));

                mBinding.frgRowRequestMarkAttendanceIvStatus.setImageResource(R.drawable.ic_done);
                mBinding.frgRowRequestMarkAttendanceIvStatus.setColorFilter(ContextCompat.getColor(context, R.color.approve_green), android.graphics.PorterDuff.Mode.MULTIPLY);


            } else if (responseRequestAttendances.get(position).getStatus().equals(RequestFragment.leaveStatus.Pending.toString())) {
                mBinding.frgRowRequestMarkAttendanceIvStatus.setImageResource(R.drawable.ic_clock);
                mBinding.frgRowRequestMarkAttendanceIvStatus.setColorFilter(ContextCompat.getColor(context, R.color.pending_yellow), android.graphics.PorterDuff.Mode.MULTIPLY);


//                mBinding.frgRowRequestMarkAttendanceTvStatus
//                        .setText(RequestFragment.leaveStatus.Pending.getValue());
//                mBinding.frgRowRequestMarkAttendanceTvStatus
//                        .setTextColor(context.getResources().getColor(R.color.blue));

            } else if (responseRequestAttendances.get(position).getStatus()
                    .equals(RequestFragment.leaveStatus.Reject.toString())) {
                mBinding.frgRowRequestMarkAttendanceIvStatus.setImageResource(R.drawable.ic_close);
                mBinding.frgRowRequestMarkAttendanceIvStatus.setColorFilter(ContextCompat.getColor(context, R.color.reject_red), android.graphics.PorterDuff.Mode.MULTIPLY);

//                mBinding.frgRowRequestMarkAttendanceTvStatus
//                        .setText(RequestFragment.leaveStatus.Reject.getValue());
//                mBinding.frgRowRequestMarkAttendanceTvStatus
//                        .setTextColor(context.getResources().getColor(R.color.abpresent_red));

            } else if (responseRequestAttendances.get(position).getStatus()
                    .equals(RequestFragment.leaveStatus.Cancel.toString())) {
                mBinding.frgRowRequestMarkAttendanceIvStatus.setImageResource(R.drawable.ic_close);
                mBinding.frgRowRequestMarkAttendanceIvStatus.setColorFilter(ContextCompat.getColor(context, R.color.cancelled), android.graphics.PorterDuff.Mode.MULTIPLY);

//
//                mBinding.frgRowRequestMarkAttendanceTvStatus
//                        .setText(RequestFragment.leaveStatus.Cancel.getValue());
//                mBinding.frgRowRequestMarkAttendanceTvStatus
//                        .setTextColor(context.getResources().getColor(R.color.abpresent_red));
            }

            mBinding.frgRowRequestMarkAttendanceIvStatus.setVisibility(View.VISIBLE);
        }
    }
}
