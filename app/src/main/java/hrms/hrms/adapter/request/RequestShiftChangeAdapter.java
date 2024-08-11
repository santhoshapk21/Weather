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
import com.hris365.databinding.RowRequestShiftChangeScreenBinding;

import java.util.List;

import hrms.hrms.fragment.request.RequestFragment;
import hrms.hrms.retrofit.model.request.shiftChange.ResponseRequestedShiftChange;
import hrms.hrms.utility.Utility;


/**
 * Created by yudiz on 06/03/17.
 */

public class RequestShiftChangeAdapter extends
        RecyclerView.Adapter<RequestShiftChangeAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private RequestFragment requestFragment;
    private List<ResponseRequestedShiftChange> responseRequestShiftChange;
    private int lastAnimationPosition;
    private Animation animation;
    private Context context;
    private View.OnClickListener onclick;

    public RequestShiftChangeAdapter(@NonNull Context context, List<ResponseRequestedShiftChange>
            responseRequestShiftChange, RequestFragment requestFragment, View.OnClickListener onclick) {
        inflater = LayoutInflater.from(context);
        this.onclick = onclick;
        this.requestFragment = requestFragment;
        this.responseRequestShiftChange = responseRequestShiftChange;
        animation = AnimationUtils.loadAnimation(context, R.anim.scale_with_alpha_animation);
        lastAnimationPosition = -1;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_request_shift_change_screen, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setData(position);
        setAnimation(holder.mBinding.frgRequestShiftChangeLlMain, position);
    }

    @Override
    public void onViewDetachedFromWindow(MyViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.mBinding.frgRequestShiftChangeLlMain.clearAnimation();
    }

    @Override
    public int getItemCount() {
        return responseRequestShiftChange.size();
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

        private RowRequestShiftChangeScreenBinding mBinding;

        public MyViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }

        public void setData(int position) {
            mBinding.frgRowRequestShiftChangeTvTitle.setText(responseRequestShiftChange.get(position).getShiftName());

            mBinding.frgRowRequestShiftChangeTvFromDate.setText(Utility.getFormattedDateTimeString(responseRequestShiftChange.get(position).getFromDate(),serverFormat, "dd MMM yyyy"));
            mBinding.frgRowRequestChiftChangeTvFromTime.setText(Utility.getFormattedDateTimeString(responseRequestShiftChange.get(position).getStartTime(),"hh:mm:ss", "HH:mm"));

            mBinding.frgRowRequestShiftChangeTvToDate.setText(Utility.getFormattedDateTimeString(responseRequestShiftChange.get(position).getToDate(),serverFormat, "dd MMM yyyy"));
            mBinding.frgRowRequestShiftChangeTvToTime.setText(Utility.getFormattedDateTimeString(responseRequestShiftChange.get(position).getEndTime(),"hh:mm:ss", "HH:mm"));

            mBinding.frgRequestShiftChangeLlMain.setTag(responseRequestShiftChange.get(position).getShiftChangeId());
            mBinding.frgRequestShiftChangeLlMain.setOnClickListener(onclick);

            setTextColor(position);

        }

        public void setTextColor(int position) {
            if (responseRequestShiftChange.get(position).getStatus()
                    .equals(RequestFragment.leaveStatus.Approve.toString())) {

                mBinding.frgRowRequestShiftChangeTvStatus.setText(RequestFragment.leaveStatus.Approve.getValue());
                mBinding.frgRowRequestShiftChangeTvStatus.setTextColor(context.getResources().getColor(R.color.present_green));

            } else if (responseRequestShiftChange.get(position).getStatus()
                    .equals(RequestFragment.leaveStatus.Pending.toString())) {

                mBinding.frgRowRequestShiftChangeTvStatus.setText(RequestFragment.leaveStatus.Pending.getValue());
                mBinding.frgRowRequestShiftChangeTvStatus.setTextColor(context.getResources().getColor(R.color.blue));

            } else if (responseRequestShiftChange.get(position).getStatus()
                    .equals(RequestFragment.leaveStatus.Reject.toString())) {

                mBinding.frgRowRequestShiftChangeTvStatus.setText(RequestFragment.leaveStatus.Reject.getValue());
                mBinding.frgRowRequestShiftChangeTvStatus.setTextColor(context.getResources().getColor(R.color.abpresent_red));

            } else if (responseRequestShiftChange.get(position).getStatus()
                    .equals(RequestFragment.leaveStatus.Cancel.toString())) {

                mBinding.frgRowRequestShiftChangeTvStatus.setText(RequestFragment.leaveStatus.Cancel.getValue());
                mBinding.frgRowRequestShiftChangeTvStatus.setTextColor(context.getResources().getColor(R.color.abpresent_red));
            }
        }

    }

}
