package hrms.hrms.adapter.shiftChange;

import static hrms.hrms.activity.HomeActivity.serverFormat;
import static hrms.hrms.utility.Utility.getFormattedDateTimeString;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hris365.R;
import com.hris365.databinding.RowShiftChangeBinding;

import java.util.List;

import hrms.hrms.retrofit.model.shiftChange.ResponseShiftChange;


/**
 * Created by gjz on 9/4/16.
 */
public class ShiftChangeAdapter extends
        RecyclerView.Adapter<ShiftChangeAdapter.ChangeShiftViewHolder> {

    private List<ResponseShiftChange> shiftList;
    private View.OnClickListener onClickListener;
    private Animation animation;
    private int lastAnimationPosition;
    private Context context;

    public ShiftChangeAdapter(Context context, List<ResponseShiftChange>
            shiftList, View.OnClickListener onClickListener) {
        this.shiftList = shiftList;
        this.onClickListener = onClickListener;
        lastAnimationPosition = -1;
        this.context = context;
    }

    @Override
    public ChangeShiftViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_shift_change, parent, false);
        return new ChangeShiftViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChangeShiftViewHolder holder, int position) {
        holder.setData(position);
        setAnimation(holder.mBinding.rowShiftChangeRlMain, position);
    }

    @Override
    public int getItemCount() {
        return shiftList.size();
    }


    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastAnimationPosition) {
            animation = AnimationUtils.loadAnimation(context, R.anim.scale_with_alpha_animation);
            viewToAnimate.startAnimation(animation);
            lastAnimationPosition = position;
        }
    }

    class ChangeShiftViewHolder extends RecyclerView.ViewHolder {
        private RowShiftChangeBinding mBinding;

        public ChangeShiftViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);

            mBinding.rowShiftChangeRlMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClick(v);
                }
            });
        }

        private void setData(int position) {
            mBinding.rowShiftChangeTvFromDate.setText(
                    getFormattedDateTimeString(shiftList.get(position).getFromDate(), serverFormat, "dd/MM/yyyy"));

            mBinding.rowShiftChangeTvToDate.setText(
                    getFormattedDateTimeString(shiftList.get(position).getToDate(), serverFormat, "dd/MM/yyyy")
            );

            mBinding.rowShiftChangeTvName.setText(shiftList.get(position).getShiftName());
            mBinding.rowShiftChangeTvTime.setText(
                    getFormattedDateTimeString(shiftList.get(position).getShiftStartTime(), "hh:mm:ss", "HH:mm")
                            + "\n " + "To" + "\n " +
                            getFormattedDateTimeString(shiftList.get(position).getShiftEndTime(), "hh:mm:ss", "HH:mm"));

            mBinding.rowShiftChangeRlMain.setTag(position);
        }
    }
}