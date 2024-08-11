package hrms.hrms.fragment.payslip;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hris365.R;
import com.hris365.databinding.RowPaySlipBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


/**
 * Created by gjz on 9/4/16.
 */
public class PaySlipAdapter extends RecyclerView.Adapter<PaySlipAdapter.PaySlipViewHolder> {

    private List<Calendar> calendars;
    private View.OnClickListener onClickListener;
    private ArrayList<String> list;
    private int CURRENT_YEAR = 0, PREVIOUS_YEAR = 1, AFTER_THAT = 2;
    private Calendar currCalendar;

    public PaySlipAdapter(List<Calendar> calendars, View.OnClickListener onClickListener) {
        this.calendars = calendars;
        this.onClickListener = onClickListener;
        list = new ArrayList<>();
        list.add(CURRENT_YEAR, "This Year");
        list.add(PREVIOUS_YEAR, "Last Year");
        list.add(AFTER_THAT, "Before Two Year");

    }

    @Override
    public PaySlipViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_pay_slip, parent, false);
        return new PaySlipViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(PaySlipViewHolder holder, int position) {
        Calendar calendar = calendars.get(position);
        currCalendar = Calendar.getInstance(Locale.getDefault());
        if (position == 0 || calendars.get(position - 1).get(Calendar.YEAR) != (calendar.get(Calendar.YEAR))) {
            holder.mBinding.rowPaySlipTvTxtYear.setVisibility(View.VISIBLE);
            if (currCalendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR))
                holder.mBinding.rowPaySlipTvTxtYear.setText(list.get(CURRENT_YEAR) + "");
            currCalendar.add(Calendar.YEAR, -1);
            if (currCalendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR))
                holder.mBinding.rowPaySlipTvTxtYear.setText(list.get(PREVIOUS_YEAR) + "");
            currCalendar.add(Calendar.YEAR, -1);
            if (currCalendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR))
                holder.mBinding.rowPaySlipTvTxtYear.setText(list.get(AFTER_THAT) + "");
        } else {
            holder.mBinding.rowPaySlipTvTxtYear.setVisibility(View.GONE);
        }
        holder.mBinding.rowPaySlipTvDate.setText(calendars.get(position).get(Calendar.DAY_OF_MONTH) + " " + calendars.get(position).getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));
        holder.mBinding.rowPaySlipTvYear.setText(calendars.get(position).get(Calendar.YEAR) + "");

    }

    @Override
    public int getItemCount() {
        return calendars.size();
    }

    class PaySlipViewHolder extends RecyclerView.ViewHolder {
        private RowPaySlipBinding mBinding;

        public PaySlipViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }
}