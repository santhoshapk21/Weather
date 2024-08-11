package hrms.hrms.fragment.attendance.request;

import static hrms.hrms.baseclass.BaseAppCompactActivity.isInternet;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;
import com.hris365.R;
import com.hris365.databinding.FragmentAddAttendanceRequestBinding;
import com.rey.material.app.DatePickerDialog;

import java.text.DateFormat;
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
import hrms.hrms.retrofit.OnApiResponseListner;
import hrms.hrms.retrofit.RequestCode;
import hrms.hrms.retrofit.model.regularisationCategory.ResponseRegularisationCategory;
import hrms.hrms.retrofit.model.resetPassword.ResponseResetPassword;
import hrms.hrms.utility.Constants;
import hrms.hrms.utility.Utility;


/**
 * Created by Yudiz on 17/10/16.
 */
@SuppressLint("ValidFragment")
public class AddRegularisationAttendanceRequest extends BaseFragment implements OnApiResponseListner {

    private FragmentAddAttendanceRequestBinding mBinding;
    private SpinnerAdapter spinnerAdapter;
    private String[] requestName;
    private long dateFrom, dateTo;
    private Calendar cal = Calendar.getInstance();
    private Calendar tempCal = Calendar.getInstance();
    private Date newDate = new Date();
    private Date tempDate = new Date();
    private ArrayList<String> categoryList;
    private ArrayList<Integer> categoryId;
    private String date = null;
    private String todate = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_attendance_request, container, false);
        ButterKnife.bind(this, mBinding.getRoot());
        getCategoryApi();
        mBinding.fragmentAttendanceRequestRlFormDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                newDate.setTime(dateTo);
                cal.setTime(newDate);

                tempDate.setTime(dateFrom);
                tempCal.setTime(tempDate);

//                Utility.showDateDialog(getFragmentManager(), 1, 1, 1900, cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR), new OnDateDialogListener() {
//                    @Override
//                    public void onPositiveActionClicked(DatePickerDialog dialog) {
//                        SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT1);
//                        String date1 = dialog.getFormattedDate(format);
//                        mBinding.fragmentAttendanceRequestTvFormDate.setText(date1);
//                        dateFrom = dialog.getDate();
//                    }
//                }, tempCal.get(Calendar.YEAR), tempCal.get(Calendar.MONTH), tempCal.get(Calendar.DAY_OF_MONTH));

                Utility.showDateDialogNew(getActivity(), 1, 1, 1900, cal.get(Calendar.DAY_OF_MONTH),
                        cal.get(Calendar.MONTH), cal.get(Calendar.YEAR),
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
                                mBinding.fragmentAttendanceRequestTvFormDate.setText(getFromDateNew(mCalendar));
                            }
                        });
            }
        });
        mBinding.fragmentAttendanceRequestRlToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newDate.setTime(dateFrom);
                cal.setTime(newDate);

                tempDate.setTime(dateTo);
                tempCal.setTime(tempDate);

//                Utility.showDateDialog(getFragmentManager(), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR), 1, 1, cal.get(Calendar.YEAR) + 100, new OnDateDialogListener() {
//                    @Override
//                    public void onPositiveActionClicked(DatePickerDialog dialog) {
//                        SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT1);
//                        String date1 = dialog.getFormattedDate(format);
//                        mBinding.fragmentAttendanceRequestTvToDate.setText(date1);
//                        dateTo = dialog.getDate();
//                    }
//                }, tempCal.get(Calendar.YEAR), tempCal.get(Calendar.MONTH), tempCal.get(Calendar.DAY_OF_MONTH));

                Utility.showDateDialogNew(getActivity(), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR),
                        1, 1, cal.get(Calendar.YEAR) + 100,
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
                                mBinding.fragmentAttendanceRequestTvToDate.setText(getToDateNew(mCalendar));
                            }
                        });
            }
        });
        mBinding.fragmentAttendanceRequestTvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBinding.fragmentAttendanceRequestEdDesc.getText().toString().length() > 0) {
                    if (isInternet)
                        getRegularisationRequest();
                } else {
                    ((HomeActivity) getActivity()).showSnackBar(mBinding.getRoot(), getString(R.string.str_error_desc), Snackbar.LENGTH_SHORT);
                }
            }
        });
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dateFrom = getArguments().getLong("date");

        //From
        newDate.setTime(dateFrom);
        cal.setTime(newDate);
        getFromDate(cal);
        mBinding.fragmentAttendanceRequestTvFormDate.setText(date);

        //To
        cal.add(Calendar.DAY_OF_MONTH, 1);
        dateTo = cal.getTime().getTime();
        getToDate(cal);
        mBinding.fragmentAttendanceRequestTvToDate.setText(todate);

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

    private String getFromDateNew(Calendar cal) {
        String FromDate = cal.getTime().toString();
        try {
            FromDate = getFormatDate(
                    "dd-MM-yyyy",
                    "dd MMM yyyy",
                    String.format("%02d", cal.get(Calendar.DAY_OF_MONTH))
                            + "-" + String.format("%02d", ((cal.get(Calendar.MONTH) + 1))) + "-"
                            + cal.get(Calendar.YEAR));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (FromDate == null) ? "" : FromDate;
    }

    private String getToDateNew(Calendar cal) {
        String toDate = cal.getTime().toString();
        try {
            toDate = getFormatDate(
                    "dd-MM-yyyy",
                    "dd MMM yyyy",
                    String.format("%02d", cal.get(Calendar.DAY_OF_MONTH))
                            + "-" + String.format("%02d", ((cal.get(Calendar.MONTH) + 1))) + "-"
                            + cal.get(Calendar.YEAR));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (toDate == null) ? "" : toDate;
    }

    private void setUpShiftChange() {
        spinnerAdapter = new hrms.hrms.widget.SpinnerAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, categoryList);
        mBinding.attendanceRequestSpnName.setAdapter(spinnerAdapter);
    }

    @Override
    public void onResponseComplete(Object clsGson, int requestCode, int responseCode) {
        dismissDialog();
        if (requestCode == RequestCode.REGULARISATIONREQUEST) {
            ResponseResetPassword message = (ResponseResetPassword) clsGson;
            Toast.makeText(getContext(), message.getMessage() + "", Toast.LENGTH_SHORT).show();
            if (isVisible() && getActivity() != null)
                ((HomeActivity) getActivity()).onBackPressed();
        } else {
            List<ResponseRegularisationCategory> category = (List<ResponseRegularisationCategory>) clsGson;
            categoryList = new ArrayList<>();
            categoryId = new ArrayList<>();
            for (int i = 0; i < category.size(); i++) {
                categoryList.add(category.get(i).getRegularisationCategory());
                categoryId.add(Integer.parseInt(category.get(i).getRegularisationId()));
            }
            if (categoryList.size() > 0)
                setUpShiftChange();
        }
    }

    @Override
    public void onResponseError(String errorMessage, int requestCode) {
        dismissDialog();
        mBinding.fragmentAttendanceRequestTvSubmit.setClickable(true);
        if (errorMessage != null && getActivity() != null)
            ((HomeActivity) getActivity()).showSnackBar(mBinding.getRoot(), errorMessage, Snackbar.LENGTH_SHORT);
    }

    private void getCategoryApi() {
        showDialog();
        ((HomeActivity) getActivity()).getApiTask().doGetRegularisationCategory(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
                this
        );
    }

    private void getRegularisationRequest() {
        showDialog();
        ((HomeActivity) getActivity()).getApiTask().doRegularisationRequest(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
                categoryId.get(mBinding.attendanceRequestSpnName.getSelectedItemPosition()),
                getFormatDate("dd MMM yyyy", "dd-MM-yyyy", mBinding.fragmentAttendanceRequestTvFormDate.getText().toString()),
                getFormatDate("dd MMM yyyy", "dd-MM-yyyy", mBinding.fragmentAttendanceRequestTvToDate.getText().toString()),
                mBinding.fragmentAttendanceRequestEdDesc.getText().toString(),
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.COMPANYID),
                this
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hideSoftKeyboard(mBinding.getRoot().findFocus());
    }
}
