package hrms.hrms.adapter.dashboared;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hris365.R;
import com.hris365.databinding.RowHomeMenuItemScreenTempBinding;

/**
 * Created by yudiz on 17/02/17.
 */

public class MenuListAdapterNotManager extends
        RecyclerView.Adapter<MenuListAdapterNotManager.MyViewHolder> {

    private int menuIcon[] = {R.drawable.ic_attendance,
            R.drawable.ic_leave,
            R.drawable.ic_claims,
            R.drawable.ic_shift_change,
            R.drawable.ic_employe_directory,
            R.drawable.ic_requests
    };

    private int menName[] = {R.string.attendance,
            R.string.leave,
            R.string.claims,
            R.string.shift_change,
            R.string.employee_directory,
            R.string.request
    };

    private LayoutInflater inflater;
    private View.OnClickListener onClickListener;
    private Context context;
    private String count;

    public MenuListAdapterNotManager(@NonNull Context context, String count, @NonNull View.OnClickListener onClickListener) {
        inflater = LayoutInflater.from(context);
        this.onClickListener = onClickListener;
        this.count = count;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_home_menu_item_screen_temp, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.setData(position);
        holder.mBinding.rowHomeLlMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(holder.mBinding.rowHomeLlMain);
            }
        });


        holder.mBinding.rowHomeLlMain.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        holder.mBinding.rowHomeCvMain.setCardElevation(context.getResources().getDimensionPixelSize(com.intuit.sdp.R.dimen._8sdp));
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_OUTSIDE:
                    case MotionEvent.ACTION_CANCEL:
                        holder.mBinding.rowHomeCvMain.setCardElevation(context.getResources().getDimensionPixelSize(com.intuit.sdp.R.dimen._1sdp));
                        break;
                }
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return menName.length;
    }

    public void addRequestCount(String count) {
        this.count=count;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private RowHomeMenuItemScreenTempBinding mBinding;

        public MyViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }

        public void setData(int position) {
            mBinding.rowHomeIvMenu.setImageResource(menuIcon[position]);
            mBinding.rowHomeTvMenuName.setText(menName[position]);

            if (position > 3)
                mBinding.rowHomeLlMain.setTag(position + 1);
            else
                mBinding.rowHomeLlMain.setTag(position);

            if (position == 5) {
                mBinding.rowHomeTvNotificationCount.setVisibility(View.VISIBLE);
                mBinding.rowHomeTvNotificationCount.setText(count);
            } else {
                mBinding.rowHomeTvNotificationCount.setVisibility(View.INVISIBLE);
                mBinding.rowHomeTvNotificationCount.setText(count);
            }
        }
    }

}
