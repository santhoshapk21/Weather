package hrms.hrms.fragment.leave.ApplyLeave;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.hris365.R;
import com.hris365.databinding.FragmentLeaveFormStepFirstBinding;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import hrms.hrms.activity.HomeActivity;
import hrms.hrms.baseclass.BaseAppCompactActivity;
import hrms.hrms.baseclass.BaseFragment;
import hrms.hrms.interfaces.OnDateDialogListener;
import hrms.hrms.model.LeaveDetails;
import hrms.hrms.retrofit.OnApiResponseListner;
import hrms.hrms.retrofit.RequestCode;
import hrms.hrms.retrofit.model.Leave.LeaveDetails.ResponseLeaveDetails;
import hrms.hrms.utility.Constants;
import hrms.hrms.utility.Utility;
import hrms.hrms.widget.SpinnerAdapter;

/**
 * Created by Yudiz on 07/10/16.
 */

public class LeaveFormFragmentStepFirst extends BaseFragment implements OnApiResponseListner {

    public static final String LEAVE_DETAILS = "leave_details";
    private FragmentLeaveFormStepFirstBinding mBinding;
    private ResponseLeaveDetails leaveDetailsModel;
    private LeaveDetails mLeaveDetails;
    private SpinnerAdapter leaveDetailsAdapter;
    private Date newDate = new Date();
    private Date tempDate = new Date();
    private long dateFrom, dateTo;
    private Calendar cal = Calendar.getInstance();
    private Calendar tempCal = Calendar.getInstance();
    private String balance;
    private String leaveCode, leaveId;
    String date = null;
    String todate = null;
    private Calendar calendar;
    private int tempbalance;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_leave_form_step_first, container, false);
        ButterKnife.bind(this, mBinding.getRoot());

        mBinding.fragmentLeaveTvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!leaveCode.equals("Leave Without Pay") && !mBinding.frgLeaveFormTvBalance.getText().equals("0.00")) {
                    LeaveFormFragmentStepTwo leaveFormFragmentStepTwo = new LeaveFormFragmentStepTwo();
                    Bundle bundle = new Bundle();
                    mLeaveDetails.setFromDate(
                            getFormatedDate(mBinding.fragmentLeaveFormTvFormDate.getText().toString())
                    );

                    mLeaveDetails.setToDate(
                            getFormatedDate(mBinding.fragmentLeaveFormTvToDate.getText().toString())
                    );

                    mLeaveDetails.setFromDateInMillis(dateFrom);
                    mLeaveDetails.setToDateInMillis(dateTo);
                    mLeaveDetails.setLeaveCode(leaveCode);
                    mLeaveDetails.setLeaveCodeID(leaveId);
                    bundle.putSerializable(LEAVE_DETAILS, mLeaveDetails);
                    leaveFormFragmentStepTwo.setArguments(bundle);
                    addFragment(leaveFormFragmentStepTwo, getString(R.string.str_apply_leave));
                } else if (leaveCode.equals("Leave Without Pay")) {
                    LeaveFormFragmentStepTwo leaveFormFragmentStepTwo = new LeaveFormFragmentStepTwo();
                    Bundle bundle = new Bundle();
                    mLeaveDetails.setFromDate(
                            getFormatedDate(mBinding.fragmentLeaveFormTvFormDate.getText().toString())
                    );

                    mLeaveDetails.setToDate(
                            getFormatedDate(mBinding.fragmentLeaveFormTvToDate.getText().toString())
                    );

                    mLeaveDetails.setFromDateInMillis(dateFrom);
                    mLeaveDetails.setToDateInMillis(dateTo);
                    mLeaveDetails.setLeaveCode(leaveCode);
                    mLeaveDetails.setLeaveCodeID(leaveId);
                    bundle.putSerializable(LEAVE_DETAILS, mLeaveDetails);
                    leaveFormFragmentStepTwo.setArguments(bundle);
                    addFragment(leaveFormFragmentStepTwo, getString(R.string.str_apply_leave));
                } else {
                    Toast.makeText(getContext(), "No more leave credit is available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mBinding.fragmentLeaveFormRlFormDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newDate.setTime(dateTo);
                cal.setTime(newDate);

                tempDate.setTime(dateFrom);
                tempCal.setTime(tempDate);

//                Utility.showDateDialog(getFragmentManager(), Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
//                        Calendar.getInstance().get(Calendar.MONTH),
//                        Calendar.getInstance().get(Calendar.YEAR) - 100,
//                        cal.get(Calendar.DAY_OF_MONTH),
//                        cal.get(Calendar.MONTH),
//                        cal.get(Calendar.YEAR) + 5, new OnDateDialogListener() {
//                            @Override
//                            public void onPositiveActionClicked(DatePickerDialog dialog) {
//                                SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT1);
//                                String date1 = dialog.getFormattedDate(format);
//                                mBinding.fragmentLeaveFormTvFormDate.setText(date1);
//                                dateFrom = dialog.getDate();
//                                dateTo = dialog.getDate();
//                                mBinding.fragmentLeaveFormTvToDate.setText(date1);
//                            }
//                        }, tempCal.get(Calendar.YEAR), tempCal.get(Calendar.MONTH), tempCal.get(Calendar.DAY_OF_MONTH));

                Utility.showDateDialogNew(getActivity(), Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.YEAR) - 100,
                        cal.get(Calendar.DAY_OF_MONTH),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.YEAR) + 5,
                        tempCal.get(Calendar.DAY_OF_MONTH), tempCal.get(Calendar.MONTH), tempCal.get(Calendar.YEAR),
                        new android.app.DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                Calendar mCalendar = Calendar.getInstance();
                                mCalendar.set(Calendar.YEAR, year);
                                mCalendar.set(Calendar.MONTH, month);
                                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                //String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(mCalendar.getTime());
                                //Wednesday, 24 July, 2024
                                mBinding.fragmentLeaveFormTvFormDate.setText(getFormattedDate(mCalendar));
                                mBinding.fragmentLeaveFormTvToDate.setText(getFormattedDate(mCalendar));
                                dateFrom = mCalendar.getTimeInMillis();
                                dateTo = mCalendar.getTimeInMillis();

                            }
                        });

            }
        });
        mBinding.fragmentLeaveFormRlToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newDate.setTime(dateFrom);
                cal.setTime(newDate);
                tempbalance = Integer.parseInt(balance.substring(0, 1));
                Log.v("OkHttp", balance.substring(2, 3));
                if (balance.substring(2, 3).equals("5")) {
                    tempbalance = tempbalance + 1;
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(cal.getTimeInMillis());
                calendar.add(Calendar.DAY_OF_MONTH, tempbalance);

                tempDate.setTime(dateTo);
                tempCal.setTime(tempDate);

//                Utility.showDateDialog(getFragmentManager(), (cal.get(Calendar.DAY_OF_MONTH)),
//                        cal.get(Calendar.MONTH), cal.get(Calendar.YEAR),
//                        calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH),
//                        calendar.get(Calendar.YEAR) + 50, new OnDateDialogListener() {
//                            @Override
//                            public void onPositiveActionClicked(DatePickerDialog dialog) {
//                                SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT1);
//                                String date1 = dialog.getFormattedDate(format);
//                                mBinding.fragmentLeaveFormTvToDate.setText(date1);
//                                dateTo = dialog.getDate();
//                            }
//                        }, tempCal.get(Calendar.YEAR), tempCal.get(Calendar.MONTH), tempCal.get(Calendar.DAY_OF_MONTH));


                Utility.showDateDialogNew(getActivity(),
                        cal.get(Calendar.DAY_OF_MONTH),
                        cal.get(Calendar.MONTH), cal.get(Calendar.YEAR),
                        calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.YEAR) + 50,
                        tempCal.get(Calendar.DAY_OF_MONTH), tempCal.get(Calendar.MONTH), tempCal.get(Calendar.YEAR),
                        new android.app.DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                Calendar mCalendar = Calendar.getInstance();
                                mCalendar.set(Calendar.YEAR, year);
                                mCalendar.set(Calendar.MONTH, month);
                                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                //String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(mCalendar.getTime());
                                //Wednesday, 24 July, 2024
                                mBinding.fragmentLeaveFormTvToDate.setText(getFormattedDate(mCalendar));
                                dateTo = mCalendar.getTimeInMillis();
                            }
                        });
            }
        });
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        calendar = Calendar.getInstance();
        onLeaveDetails();
        mLeaveDetails = new LeaveDetails();
        mBinding.leaveFormSpnLeaveCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                leaveId = leaveDetailsModel.details.get(position).LeaveTypeId;
                Log.v("OkHttp1", leaveId + "  ID");
                leaveCode = leaveDetailsModel.details.get(position).LeaveType;
                Log.v("OkHttp1", leaveCode + "  LeaveCode");
                mBinding.frgLeaveFormTvPending
                        .setText(leaveDetailsModel.details.get(position).Credit);
                mBinding.frgLeaveFormTvTaken
                        .setText(leaveDetailsModel.details.get(position).Debit);
                mBinding.frgLeaveFormTvBalance
                        .setText(leaveDetailsModel.details.get(position).Total);
                balance = leaveDetailsModel.details.get(position).Credit;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dateFrom = calendar.getTimeInMillis();

        //From
        newDate.setTime(dateFrom);
        cal.setTime(newDate);
        getFromDate(cal);
        mBinding.fragmentLeaveFormTvFormDate.setText(date);

        //To
        dateTo = cal.getTime().getTime();
        getToDate(cal);
        mBinding.fragmentLeaveFormTvToDate.setText(todate);

        //Set Correct
        cal.setTime(newDate);
    }

    private String getFromDate(Calendar cal) {
        try {
            date = getFormatDate(
                    "dd-MM-yyyy",
                    "dd MMM yyyy",
                    String.format("%02d", this.cal.get(Calendar.DAY_OF_MONTH))
                            + "-" + String.format("%02d", ((this.cal.get(Calendar.MONTH) + 1))) + "-"
                            + this.cal.get(Calendar.YEAR));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (date == null) ? "" : date;
    }

    private String getToDate(Calendar cal) {
        try {
            todate = getFormatDate(
                    "dd-MM-yyyy",
                    "dd MMM yyyy",
                    String.format("%02d", this.cal.get(Calendar.DAY_OF_MONTH))
                            + "-" + String.format("%02d", ((this.cal.get(Calendar.MONTH) + 1))) + "-"
                            + this.cal.get(Calendar.YEAR));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (todate == null) ? "" : todate;
    }

    private void onLeaveDetails() {
        showDialog();
        ((HomeActivity) getActivity()).getApiTask().doGetLeaveDetailsCount(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
                calendar.get(Calendar.YEAR),
                this
        );
    }

    @Override
    public void onResponseComplete(Object clsGson, int requestCode, int responseCode) {
        dismissDialog();
        if (requestCode == RequestCode.LEAVEDETAILSCOUNT) {
            ((HomeActivity) getActivity()).dismissDialog();
            leaveDetailsModel = (ResponseLeaveDetails) clsGson;
            if (leaveDetailsModel != null && leaveDetailsModel.details.size() > 0)
                setAdapter(leaveDetailsModel.details);
            else {
            }
        }
    }

    @Override
    public void onResponseError(String errorMessage, int requestCode) {

    }

    private void setAdapter(List<ResponseLeaveDetails.Details> leaveDetailsModel) {

        ArrayList<String> categoryList = new ArrayList<>();

        for (ResponseLeaveDetails.Details leveDetails : leaveDetailsModel) {
            categoryList.add(leveDetails.LeaveType);
        }

        leaveDetailsAdapter = new SpinnerAdapter(
                getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                categoryList
        );
        mBinding.leaveFormSpnLeaveCode.setAdapter(leaveDetailsAdapter);
    }

    public String getFormatedDate(@NonNull String date) {
        return getFormatDate("dd MMM yyyy", "MM-dd-yyyy", date);
    }
}
