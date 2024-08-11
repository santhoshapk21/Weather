package hrms.hrms.adapter.request;

import static hrms.hrms.activity.HomeActivity.serverFormat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hris365.R;
import com.hris365.databinding.RowRequestLeaveScreenBinding;

import java.util.List;

import hrms.hrms.activity.HomeActivity;
import hrms.hrms.fragment.request.RequestFragment;
import hrms.hrms.retrofit.model.request.leave.ResponseRequestLeave;
import hrms.hrms.utility.Utility;


/**
 * Created by yudiz on 03/03/17.
 */

public class RequestDataLeaveAdapter
        extends RecyclerView.Adapter<RequestDataLeaveAdapter.MyViewHolder> {

    private LinearLayout mfrgrequestleavellmain;
    private LayoutInflater inflater;
    private List<ResponseRequestLeave> responseRequestLeaves;
    private RequestFragment requestFragment;
    private Context context;
    private int lastAnimationPosition;
    private Animation animation;
    private HomeActivity homeActivity;
    private View.OnClickListener onclick;

    public RequestDataLeaveAdapter(@NonNull Context context,
                                   List<ResponseRequestLeave> responseRequestLeaves,
                                   RequestFragment requestFragment, View.OnClickListener onclick) {
        this.onclick = onclick;
        this.responseRequestLeaves = responseRequestLeaves;
        inflater = LayoutInflater.from(context);
        this.requestFragment = requestFragment;
        this.context = context;
        lastAnimationPosition = -1;
        homeActivity = (HomeActivity) context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_request_leave_screen, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setData(position);
        setAnimation(holder.mBinding.frgRequestLeaveLlMain, position);

    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastAnimationPosition) {
            animation = AnimationUtils.loadAnimation(context, R.anim.scale_with_alpha_animation);
            viewToAnimate.startAnimation(animation);
            lastAnimationPosition = position;
        }
    }


    @Override
    public int getItemCount() {
        return responseRequestLeaves.size();
    }

    public void changeData(List<ResponseRequestLeave> responseRequestLeaves) {
        this.responseRequestLeaves=responseRequestLeaves;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private RowRequestLeaveScreenBinding mBinding;

        public MyViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
            mfrgrequestleavellmain = (LinearLayout) itemView.findViewById(R.id.frg_request_leave_ll_main);


//            mBinding.frgRequestLeaveLlMain.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    RequestLeaveDetailsFragment requestDetailsFragment = new RequestLeaveDetailsFragment();
//                    homeActivity.addFragment(requestDetailsFragment,
//                            context.getResources().getString(R.string.request_details));
//                }
//            });
        }

        public void setData(int position) {
            mBinding.frgRowRequestLeaveTvTitle.setText(
                    responseRequestLeaves.get(position).getLeaveCode());

            mBinding.frgRowRequestLeaveTvFromDate.setText(Utility.getFormattedDateTimeString(responseRequestLeaves.get(position).getFromDate(),serverFormat, "dd MMM yyyy"));

            mBinding.frgRowRequestLeaveTvToDate.setText(Utility.getFormattedDateTimeString(responseRequestLeaves.get(position).getToDate(),serverFormat, "dd MMM yyyy"));

            mBinding.frgRowRequestLeaveTvType.setText("Leave Duration : " +
                    responseRequestLeaves.get(position).getLeaveDuration()
            );

//            mBinding.frgRequestLeaveLlMain.setTag(responseRequestLeaves.get(position).getEmployeeID());
            mBinding.frgRequestLeaveLlMain.setTag(responseRequestLeaves.get(position).getLeaveAppId());
            mBinding.frgRequestLeaveLlMain.setOnClickListener(onclick);

            setTextColor(position);
        }

        public void setTextColor(int position) {
            if (responseRequestLeaves.get(position).getLeaveStatus()
                    .equals(RequestFragment.leaveStatus.Approve.toString())) {
                mBinding.frgRowRequestLeaveTvStatus
                        .setText(RequestFragment.leaveStatus.Approve.getValue());
                mBinding.frgRowRequestLeaveTvStatus
                        .setTextColor(context.getResources().getColor(R.color.present_green));

            } else if (responseRequestLeaves.get(position).getLeaveStatus()
                    .equals(RequestFragment.leaveStatus.Pending.toString())) {
                mBinding.frgRowRequestLeaveTvStatus
                        .setText(RequestFragment.leaveStatus.Pending.getValue());
                mBinding.frgRowRequestLeaveTvStatus
                        .setTextColor(context.getResources().getColor(R.color.blue));

            } else if (responseRequestLeaves.get(position).getLeaveStatus()
                    .equals(RequestFragment.leaveStatus.Reject.toString())) {
                mBinding.frgRowRequestLeaveTvStatus
                        .setText(RequestFragment.leaveStatus.Reject.getValue().toString());
                mBinding.frgRowRequestLeaveTvStatus.
                        setTextColor(context.getResources().getColor(R.color.abpresent_red));
            } else if (responseRequestLeaves.get(position).getLeaveStatus()
                    .equals(RequestFragment.leaveStatus.Cancel.toString())) {
                mBinding.frgRowRequestLeaveTvStatus
                        .setText(RequestFragment.leaveStatus.Cancel.getValue().toString());
                mBinding.frgRowRequestLeaveTvStatus.
                        setTextColor(context.getResources().getColor(R.color.abpresent_red));
            }
        }

    }

}
