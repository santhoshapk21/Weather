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
import com.hris365.databinding.RowRequestAttendanceCurrectionScreenBinding;

import java.util.List;

import hrms.hrms.fragment.request.RequestFragment;
import hrms.hrms.retrofit.model.request.Correction.ResponseRequestCorrection;
import hrms.hrms.utility.Utility;


/**
 * Created by yudiz on 06/03/17.
 */

public class RequestAttendanceCorrectionRequestAdapter
        extends RecyclerView.Adapter<RequestAttendanceCorrectionRequestAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private RequestFragment requestFragment;
    private List<ResponseRequestCorrection> responseRequestCorrections;
    private Context context;
    private int lastAnimationPosition;
    private Animation animation;
    private View.OnClickListener onclick;

    public RequestAttendanceCorrectionRequestAdapter(
            @NonNull Context context,
            List<ResponseRequestCorrection> responseRequestCorrections,
            RequestFragment requestFragment,View.OnClickListener onclick) {

        inflater = LayoutInflater.from(context);
        this.onclick=onclick;
        this.requestFragment = requestFragment;
        this.responseRequestCorrections = responseRequestCorrections;
        this.context = context;
        lastAnimationPosition = -1;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(
                R.layout.row_request_attendance_currection_screen,
                parent,
                false
        );
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setData(position);
        setAnimation(holder.mBinding.frgRowRequestAttendanceLlMain, position);
    }

    @Override
    public void onViewDetachedFromWindow(MyViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.mBinding.frgRowRequestAttendanceLlMain.clearAnimation();
    }

    @Override
    public int getItemCount() {
        return responseRequestCorrections.size();
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

        private RowRequestAttendanceCurrectionScreenBinding mBinding;

        public MyViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }

        public void setData(int position) {
            mBinding.frgRowRequestCorrectionTvTitle.setText(
                    responseRequestCorrections.get(position).getType());

            mBinding.frgRowRequestCorrectionDate.setText(Utility.getFormattedDateTimeString(responseRequestCorrections.get(position).getRequestDate(),serverFormat, "dd MMM yyyy"));

            mBinding.frgRowRequestCorrectionTime.setText(
                    responseRequestCorrections.get(position).getRequestTime());

            mBinding.frgRowRequestAttendanceLlMain.setTag(responseRequestCorrections.get(position).getCorrectionId());
            mBinding.frgRowRequestAttendanceLlMain.setOnClickListener(onclick);

            setTextColor(position);
        }

        public void setTextColor(int position) {

            if (responseRequestCorrections.get(position).getFinalStatus()
                    .equals(RequestFragment.leaveStatus.Approve.toString())) {

                mBinding.frgRowRequestCorrectionTvStatus.setText(
                        RequestFragment.leaveStatus.Approve.getValue());

                mBinding.frgRowRequestCorrectionTvStatus.setTextColor(
                        context.getResources().getColor(R.color.present_green));

            } else if (responseRequestCorrections.get(position).getFinalStatus().
                    equals(RequestFragment.leaveStatus.Pending.toString())) {
                mBinding.frgRowRequestCorrectionTvStatus.
                        setText(RequestFragment.leaveStatus.Pending.getValue());

                mBinding.frgRowRequestCorrectionTvStatus.
                        setTextColor(context.getResources().getColor(R.color.blue));

            } else if(responseRequestCorrections.get(position).getFinalStatus().
                    equals(RequestFragment.leaveStatus.Reject.toString())){
                mBinding.frgRowRequestCorrectionTvStatus.
                        setText(RequestFragment.leaveStatus.Reject.getValue());

                mBinding.frgRowRequestCorrectionTvStatus.
                        setTextColor(context.getResources().getColor(R.color.abpresent_red));
            }else if(responseRequestCorrections.get(position).getFinalStatus().
                    equals(RequestFragment.leaveStatus.Cancel.toString())){
                mBinding.frgRowRequestCorrectionTvStatus.
                        setText(RequestFragment.leaveStatus.Cancel.getValue());

                mBinding.frgRowRequestCorrectionTvStatus.
                        setTextColor(context.getResources().getColor(R.color.abpresent_red));
            }
        }

    }

}
