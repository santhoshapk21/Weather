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
import com.hris365.databinding.FragmentAddCorrectionAttendanceRequestBinding;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.TimePickerDialog;

import java.text.ParseException;
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
import hrms.hrms.retrofit.model.category.ResponseCategory;
import hrms.hrms.retrofit.model.resetPassword.ResponseResetPassword;
import hrms.hrms.utility.Constants;
import hrms.hrms.utility.Utility;


/**
 * Created by Yudiz on 17/10/16.
 */
@SuppressLint("ValidFragment")
public class AddCorrectionAttendanceRequest extends BaseFragment implements OnApiResponseListner {

    int hour, min;
    private FragmentAddCorrectionAttendanceRequestBinding mBinding;
    private SpinnerAdapter spinnerAdapter;
    private long date;
    private Calendar cal = Calendar.getInstance();
    private Date newDate = new Date();
    private ArrayList<String> categoryList;
    String date1 = null;
    private String userdatetime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_add_correction_attendance_request,
                container,
                false
        );
        ButterKnife.bind(this, mBinding.getRoot());
        getCategoryApi();
        mBinding.fragmentAttendanceRequestRlFormDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newDate.setTime(date);
                cal.setTime(newDate);

//                Utility.showDateDialog(getFragmentManager(), 1, 1, 1900,
//                        cal.get(Calendar.DAY_OF_MONTH),
//                        cal.get(Calendar.MONTH), cal.get(Calendar.YEAR), new OnDateDialogListener() {
//                            @Override
//                            public void onPositiveActionClicked(DatePickerDialog dialog) {
//                                SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT1);
//                                String date1 = dialog.getFormattedDate(format);
//                                mBinding.fragmentAttendanceRequestTvFormDate.setText(date1);
//                                date = dialog.getDate();
//                            }
//                        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

                Utility.showDateDialogNew(getActivity(), 1, 1, 1900, cal.get(Calendar.DAY_OF_MONTH)
                        , cal.get(Calendar.MONTH), cal.get(Calendar.YEAR),
                        cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR), new android.app.DatePickerDialog.OnDateSetListener() {
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
        mBinding.fragmentAttendanceRequestTvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogTimer();
            }
        });
        mBinding.fragmentAttendanceRequestTvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userdatetime = mBinding.fragmentAttendanceRequestTvFormDate.getText() + " " + mBinding.fragmentAttendanceRequestTvTime.getText();
                Date currentTime = Calendar.getInstance().getTime();
                System.out.println(currentTime + "current");

                SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy HH:mm");
                Date comparedate = null;
                try {
                    comparedate = format.parse(userdatetime);
                    System.out.println(comparedate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (currentTime.after(comparedate)) {
                    if (mBinding.fragmentAttendanceRequestEtDesc.getText().toString().length() > 0) {
                        mBinding.fragmentAttendanceRequestTvSubmit.setClickable(false);
                        if (isInternet)
                            correctionRequestApi();
                    } else {
                        ((HomeActivity) getActivity()).showSnackBar(mBinding.getRoot(), getString(R.string.str_error_desc), Snackbar.LENGTH_SHORT);
                    }
                } else {
                    ((HomeActivity) getActivity()).showSnackBar(mBinding.getRoot(), "You can't able to select future time", Snackbar.LENGTH_SHORT);
                }
            }
        });
        return mBinding.getRoot();
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hour = newDate.getHours();
        min = newDate.getMinutes();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String time1 = sdf.format(newDate);
        mBinding.fragmentAttendanceRequestTvTime.setText(time1);
/*
        try {
            if (time1.toLowerCase().contains("a")) {
                time1 = time1.replace(".", "").substring(0, 5);
                mBinding.fragmentAttendanceRequestTvTime.setText(time1);
                mBinding.fragmentAttendanceRequestTvTime.setText(time1 + " " + " AM");
            } else {
                time1 = time1.replace(".", "").substring(0, 5);
                mBinding.fragmentAttendanceRequestTvTime.setText(time1);
                mBinding.fragmentAttendanceRequestTvTime.setText(time1 + " " + " PM");
            }
        } catch (Exception e) {
        }
*/

        date = getArguments().getLong("date");
        newDate.setTime(date);
        cal.setTime(newDate);
        getDate(cal);
        mBinding.fragmentAttendanceRequestTvFormDate.setText(date1);
    }

    private String getDate(Calendar cal) {
        try {
            date1 = getFormatDate(
                    "dd-MM-yyyy",
                    "dd MMM yyyy",
                    String.format("%02d", this.cal.get(Calendar.DAY_OF_MONTH))
                            + "-" + String.format("%02d", ((this.cal.get(Calendar.MONTH) + 1))) + "-"
                            + this.cal.get(Calendar.YEAR));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (date1 == null) ? "" : date1;
    }

    private void setUpShiftChange() {
        spinnerAdapter = new hrms.hrms.widget.SpinnerAdapter(
                getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                categoryList
        );
        mBinding.attendanceRequestSpnName.setAdapter(spinnerAdapter);
    }

    public void showDialogTimer() {
        final TimePickerDialog.Builder builder = new TimePickerDialog.Builder(hour, min) {

            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                TimePickerDialog dialog = (TimePickerDialog) fragment.getDialog();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                userdatetime = mBinding.fragmentAttendanceRequestTvFormDate.getText().toString() + " " + ((TimePickerDialog) fragment.getDialog()).getFormattedTime(format);

                hour = ((TimePickerDialog) fragment.getDialog()).getHour();
                min = ((TimePickerDialog) fragment.getDialog()).getMinute();

                mBinding.fragmentAttendanceRequestTvTime.setText(((TimePickerDialog) fragment.getDialog()).getFormattedTime(format));

                /*
                try {

                    if (dialog.getFormattedTime(format).toString().toLowerCase().contains("a")) {
                        mBinding.fragmentAttendanceRequestTvTime.setText(dialog.getFormattedTime(format).replace(".", "").substring(0, 5));
                        mBinding.fragmentAttendanceRequestTvTime.setText(mBinding.fragmentAttendanceRequestTvTime.getText() + " " + " AM");
                    } else {
                        mBinding.fragmentAttendanceRequestTvTime.setText(dialog.getFormattedTime(format).replace(".", "").substring(0, 5));
                        mBinding.fragmentAttendanceRequestTvTime.setText(mBinding.fragmentAttendanceRequestTvTime.getText() + " " + " PM");
                    }
                } catch (Exception e) {
                }
*/
                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
            }
        };
        builder.positiveAction("OK").negativeAction("CANCEL");
        final DialogFragment fragmentTimeDialog = DialogFragment.newInstance(builder);
        fragmentTimeDialog.show(getActivity().getSupportFragmentManager(), "");
    }

    private void getCategoryApi() {
        showDialog();
        ((HomeActivity) getActivity()).getApiTask().doGetCorrectionCategory(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN)
                , this
        );
    }

    @Override
    public void onResponseComplete(Object clsGson, int requestCode, int responseCode) {
        dismissDialog();
        mBinding.fragmentAttendanceRequestTvSubmit.setClickable(true);

        if ((clsGson != null)) {
            if (requestCode == RequestCode.CORRECTIONREQUEST) {
                ResponseResetPassword message = (ResponseResetPassword) clsGson;
                Toast.makeText(getContext(), message.getMessage() + "", Toast.LENGTH_SHORT).show();
                if (isVisible() && getActivity() != null)
                    ((HomeActivity) getActivity()).onBackPressed();
            } else {
                List<ResponseCategory> category = (List<ResponseCategory>) clsGson;
                categoryList = new ArrayList<>();
                for (int i = 0; i < category.size(); i++) {
                    categoryList.add(category.get(i).getType());
                }
                if (categoryList.size() > 0)
                    setUpShiftChange();
            }
        }
    }

    @Override
    public void onResponseError(String errorMessage, int requestCode) {
        dismissDialog();
        mBinding.fragmentAttendanceRequestTvSubmit.setClickable(true);
        if (errorMessage != null && getActivity() != null)
            ((HomeActivity) getActivity()).showSnackBar(mBinding.getRoot(), errorMessage, Snackbar.LENGTH_SHORT);
    }

    private void correctionRequestApi() {
        showDialog();
        ((HomeActivity) getActivity()).getApiTask().doCorrectionRequest(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
                mBinding.fragmentAttendanceRequestTvTime.getText().toString(),
                getFormatDate("dd MMM yyyy", "MM-dd-yyyy", mBinding.fragmentAttendanceRequestTvFormDate.getText().toString()),
                (mBinding.fragmentAttendanceRequestCbNextDayOut.isChecked() == true) ? 1 : 0,
                mBinding.fragmentAttendanceRequestEtDesc.getText().toString(),
                mBinding.attendanceRequestSpnName.getSelectedItem().toString(),
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
