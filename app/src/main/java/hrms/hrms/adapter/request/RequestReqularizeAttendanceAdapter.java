package hrms.hrms.adapter.request;

import static hrms.hrms.activity.HomeActivity.serverFormat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hris365.R;
import com.hris365.databinding.RowRequestRegularizeAttendanceScreenBinding;

import java.util.List;

import hrms.hrms.fragment.request.RequestFragment;
import hrms.hrms.retrofit.model.request.regularisation.ResponseRequestRegularisation;
import hrms.hrms.utility.Utility;


/**
 * Created by yudiz on 06/03/17.
 */

public class RequestReqularizeAttendanceAdapter extends RecyclerView.Adapter<RequestReqularizeAttendanceAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private RequestFragment requestFragment;
    private List<ResponseRequestRegularisation> responseRequestRegularisations;
    private Context context;
    private int lastAnimationPosition;
    private Animation animation;
    private View.OnClickListener onclick;

    public RequestReqularizeAttendanceAdapter(@NonNull Context context, List<ResponseRequestRegularisation> responseRequestRegularisations, RequestFragment requestFragment, View.OnClickListener onclick) {
        inflater = LayoutInflater.from(context);
        this.onclick = onclick;
        this.requestFragment = requestFragment;
        this.responseRequestRegularisations = responseRequestRegularisations;
        this.context = context;
        lastAnimationPosition = -1;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_request_regularize_attendance_screen, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setData(position);
        setAnimation(holder.mBinding.frgRowRequestRegularizeLlMain, position);
    }

    @Override
    public void onViewDetachedFromWindow(MyViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.mBinding.frgRowRequestRegularizeLlMain.clearAnimation();
    }

    @Override
    public int getItemCount() {
        return responseRequestRegularisations.size();
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastAnimationPosition) {
            animation = AnimationUtils.loadAnimation(context, R.anim.scale_with_alpha_animation);
            viewToAnimate.startAnimation(animation);
            lastAnimationPosition = position;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private RowRequestRegularizeAttendanceScreenBinding mBinding;

        public MyViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }

        public void setData(int position) {
            mBinding.frgRowRequestLeaveTvTitle.setText(responseRequestRegularisations.get(position).getCategoryName());
            mBinding.frgRowRequestLeaveTvFromDate.setText(Utility.getFormattedDateTimeString(responseRequestRegularisations.get(position).getFromDate(),serverFormat, "dd MMM yyyy"));
            mBinding.frgRowRequestLeaveTvToDate.setText(Utility.getFormattedDateTimeString(responseRequestRegularisations.get(position).getToDate(),serverFormat, "dd MMM yyyy"));

            mBinding.frgRowRequestRegularizeLlMain.setTag(responseRequestRegularisations.get(position).getRegularisationId());
            mBinding.frgRowRequestRegularizeLlMain.setOnClickListener(onclick);

            setTextColor(position);
        }

        public void setTextColor(int position) {
            if (responseRequestRegularisations.get(position).getStatus()
                    .equals(RequestFragment.leaveStatus.Approve.toString())) {

                mBinding.frgRowRequestLeaveTvStatus.setText(RequestFragment.leaveStatus.Approve.getValue());
                mBinding.frgRowRequestLeaveTvStatus.setTextColor(context.getResources().getColor(R.color.present_green));

            } else if (responseRequestRegularisations.get(position).getStatus()
                    .equals(RequestFragment.leaveStatus.Pending.toString())) {

                mBinding.frgRowRequestLeaveTvStatus.setText(RequestFragment.leaveStatus.Pending.getValue());
                mBinding.frgRowRequestLeaveTvStatus.setTextColor(context.getResources().getColor(R.color.blue));

            } else if (responseRequestRegularisations.get(position).getStatus()
                    .equals(RequestFragment.leaveStatus.Reject.toString())) {

                mBinding.frgRowRequestLeaveTvStatus.setText(RequestFragment.leaveStatus.Reject.getValue());
                mBinding.frgRowRequestLeaveTvStatus.setTextColor(context.getResources().getColor(R.color.abpresent_red));

            } else if (responseRequestRegularisations.get(position).getStatus()
                    .equals(RequestFragment.leaveStatus.Cancel.toString())) {

                mBinding.frgRowRequestLeaveTvStatus.setText(RequestFragment.leaveStatus.Cancel.getValue());
                mBinding.frgRowRequestLeaveTvStatus.setTextColor(context.getResources().getColor(R.color.abpresent_red));
            }
        }

    }
}
