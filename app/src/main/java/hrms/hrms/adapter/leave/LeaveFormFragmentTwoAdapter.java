package hrms.hrms.adapter.leave;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hris365.R;
import com.hris365.databinding.RowLeaveFormBinding;
import com.rey.material.widget.Spinner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import hrms.hrms.baseclass.BaseFragment;
import hrms.hrms.model.Data;
import hrms.hrms.model.LeaveDetails;
import hrms.hrms.widget.SpinnerAdapter;

/**
 * Created by yudiz on 24/03/17.
 */

public class LeaveFormFragmentTwoAdapter
        extends RecyclerView.Adapter<LeaveFormFragmentTwoAdapter.ViewHolder> {

    String[] categoryList = new String[]{"Full", "First Half", "Second Half"};
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private LeaveDetails leaveDetails;
    private int size;

    public LeaveFormFragmentTwoAdapter(@NonNull Context context,
                                       @NonNull LeaveDetails leaveDetails) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.leaveDetails = leaveDetails;
        size = (getDiffrence(leaveDetails.getFromDate(), leaveDetails.getToDate()) + 1);

        List<Data> dataList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Data leaveDetails1 = new Data();
            leaveDetails1.setLeaveDate(BaseFragment
                    .getFormatDate("dd-MM-yyyy", "MM-dd-yyyy", getDate(i, "-", 1)));
            leaveDetails1.setLeaveDuration("Full");
            dataList.add(leaveDetails1);
        }
        leaveDetails.setDates(dataList);
    }

    private int getDiffrence(String fromDate, String toDate) {
        DateFormat df = new SimpleDateFormat("MM-dd-yyyy");

        try {
            Date fromDateNew = df.parse(fromDate);
            Date toDateNew = df.parse(toDate);
            long diffrence = toDateNew.getTime() - fromDateNew.getTime();
            return (int) TimeUnit.MILLISECONDS.toDays(diffrence);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.row_leave_form, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        Log.i("TAG1",leaveDetails.getFromDate());
        if (size == 0)
            return (getDiffrence(leaveDetails.getFromDate(), leaveDetails.getToDate()) + 1);
        else
            return size;
    }

    public String getDate(int position, String seprater, int incrementMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(leaveDetails.getFromDateInMillis());
        calendar.add(Calendar.DAY_OF_MONTH, position);


        String date =
                getTwoDigit(calendar.get(Calendar.DAY_OF_MONTH)) + seprater
                        + getTwoDigit(calendar.get(Calendar.MONTH) + incrementMonth) + seprater
                        + calendar.get(Calendar.YEAR);
        return date;
    }

    private String getTwoDigit(int value) {
        return String.format(Locale.getDefault(), "%02d", value);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RowLeaveFormBinding mBinding;

        public ViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
            setAdapter();

            mBinding.leaveFormSpnLeaveCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    leaveDetails.getDates().get((Integer) mBinding.leaveFormSpnLeaveCode.getTag())
                            .setLeaveDuration(categoryList[position]);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        protected void setData(int position) {
            mBinding.leaveFormTvLeaveDate.setText(
                    getDate(position, "/", 1)
            );
            mBinding.leaveFormSpnLeaveCode.setTag(position);
        }

        protected void setAdapter() {
            SpinnerAdapter spinnerAdapter = new SpinnerAdapter(mContext, android.R.layout.simple_dropdown_item_1line, categoryList);
            mBinding.leaveFormSpnLeaveCode.setAdapter(spinnerAdapter);
        }

    }

}
