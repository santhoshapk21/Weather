package hrms.hrms.fragment.attendance;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.hris365.R;
import com.hris365.databinding.FragmentAttendanceBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import hrms.hris365.calendar.RobotoCalendarTextView;
import hrms.hrms.activity.HomeActivity;
import hrms.hrms.baseclass.BaseAppCompactActivity;
import hrms.hrms.baseclass.BaseFragment;
import hrms.hrms.dialog.AttendanceRequestType;
import hrms.hrms.fragment.DashBoaredFragment;
import hrms.hrms.fragment.attendance.request.AddCorrectionAttendanceRequest;
import hrms.hrms.fragment.attendance.request.AddRegularisationAttendanceRequest;
import hrms.hrms.retrofit.OnApiResponseListner;
import hrms.hrms.retrofit.model.attendance.ResponseAttendance;
import hrms.hrms.utility.Utility;
import retrofit2.Call;


/**
 * Created by Yudiz on 07/10/16.
 */
public class AttendanceFragment extends BaseFragment implements RobotoCalendarTextView.RobotoCalendarListener, View.OnClickListener,
        OnApiResponseListner {

    private FragmentAttendanceBinding mBinding;
    private Calendar currentCalender;
    private List<ResponseAttendance> responseAttendances;
    private String timeZone = "UTC";
    private Call<?> api;
    private boolean recordFound;
    private String[] workingTitile = {"Off Day", "Full Day Working"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_attendance, container, false);
        ButterKnife.bind(this, mBinding.getRoot());
        init();
        mBinding.frgAttendanceIvCorrection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                if (mBinding.fragmentAttendanceRobotCalnder.getCurrentDate() != null) {
                    boolean value = (mBinding.fragmentAttendanceRobotCalnder.getCurrentDate().getTime() > cal.getTime().getTime()) ? false : true;

                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isCorrectionValid", value);

            AttendanceRequestType dialog = new AttendanceRequestType();
                    dialog.setTargetFragment(AttendanceFragment.this, 1);
            dialog.setArguments(bundle);

            dialog.show(getFragmentManager(), "Correction Dialog");
                }
            }
        });
        return mBinding.getRoot();
    }

    private void init() {
        setData(new Date());
        currentCalender = Calendar.getInstance();

        AttendanceApiCall(currentCalender.get(Calendar.MONTH) + 1, currentCalender.get(Calendar.YEAR));

        mBinding.fragmentAttendanceRobotCalnder.initializeCalendar(Calendar.getInstance());
        mBinding.fragmentAttendanceRobotCalnder.setRobotoCalendarListener(this);
        setVisibilityDialogView(View.GONE);
    }

    private void visibleFont(String date, String h, int present_green) {
        mBinding.fragmentAttendanceRobotCalnder.visibleFont(
                getFormatDate(HomeActivity.serverFormat,
                        "dd-MM-yyyy", date, timeZone),
                h, getContext().getResources().getColor(present_green));
    }

    private void setUpView() {

        for (int i = 0; i < responseAttendances.size(); i++) {

            if (responseAttendances.get(i).getWorkingType().equals(workingType.OffDay.toString()))
                mBinding.fragmentAttendanceRobotCalnder.addOffDay(
                        Utility.getFormattedDateTimeString(responseAttendances.get(i).getDate(), HomeActivity.serverFormat, "dd")
                );

            if (responseAttendances.get(i).getWorkingType().equals(workingType.Holiday.toString())) {
                visibleFont(responseAttendances.get(i).getDate(), "H", R.color.present_green);
            } else if (responseAttendances.get(i).isIsAppliedForLeave()) {
                if (responseAttendances.get(i).getLeaveStatus().toLowerCase().equals(leaveType.Approve.toString().toLowerCase())) {
                    visibleFont(responseAttendances.get(i).getDate(), "L", R.color.present_green);
                } else {
                    visibleFont(responseAttendances.get(i).getDate(), "L", R.color.pending_text_color);
                }
            } else if (responseAttendances.get(i).isIsPresent()) {
                visibleFont(responseAttendances.get(i).getDate(), "P", R.color.present_green);
            } else if (!responseAttendances.get(i).isIsPresent() && !responseAttendances.get(i).getWorkingType().equals(workingType.OffDay.toString())) {
                visibleFont(responseAttendances.get(i).getDate(), "A", R.color.abpresent_red);
            }
        }
        mBinding.fragmentAttendanceRobotCalnder.setDaysInCalendar();

        if (currentCalender.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH)
                && currentCalender.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR)) {
            setData(Calendar.getInstance().getTime());
            mBinding.fragmentAttendanceRobotCalnder.markDayAsSelectedDay(Calendar.getInstance().getTime());
        } else {
            currentCalender.set(Calendar.DAY_OF_MONTH, 1);
            setData(currentCalender.getTime());
            mBinding.fragmentAttendanceRobotCalnder.markDayAsSelectedDay(currentCalender.getTime());
        }
    }

    @Override
    public void onDateSelected(Date date) {
        mBinding.fragmentAttendanceRobotCalnder.markDayAsSelectedDay(date);
        setData(date);
    }

    private void setData(Date date) {
        mBinding.frgAttendanceCvMain.setVisibility(View.GONE);
        recordFound = false;

        if (responseAttendances != null) {
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(date);

            for (int i = 0; i < responseAttendances.size(); i++) {
                Calendar c1 = Calendar.getInstance();
                c1.setTime(date);
                c1.set(Calendar.DAY_OF_MONTH, getFormatDate(HomeActivity.serverFormat, "EEE, dd MMM yyyy",
                        responseAttendances.get(i).getDate(), timeZone).get(Calendar.DAY_OF_MONTH));
                if (calendar1.equals(c1)) {
                    recordFound = true;

                    mBinding.frgAttendanceTvOutTime.setText("Out - " + responseAttendances.get(i).getTimeOut());
                    setVisibilityDialogView(View.VISIBLE);

                    if (responseAttendances.get(i).getWorkingType().equals(""))
                        mBinding.frgAttendanceLlTitle.setVisibility(View.GONE);
                    else if (responseAttendances.get(i).getWorkingType().equals(workingType.FullDay.toString()))
                        mBinding.frgAttendanceTvTitle.setText(workingTitile[1]);
                    else if (responseAttendances.get(i).getWorkingType().equals(workingType.OffDay.toString()))
                        mBinding.frgAttendanceTvTitle.setText(workingTitile[0]);
                    else
                        mBinding.frgAttendanceTvTitle.setText(responseAttendances.get(i).getWorkingType() + " For " + responseAttendances.get(i).getHolidayType());

                    if (responseAttendances.get(i).getLeaveType().equals(""))
                        mBinding.frgAttendanceLlLeaveStatus.setVisibility(View.GONE);
                    else
                        mBinding.frgAttendanceTvLeaveStatus.setText(responseAttendances.get(i).getLeaveType() + " Leave " + "(" + responseAttendances.get(i).getLeaveCode().toUpperCase() + ")");

                    if (responseAttendances.get(i).getTimeIn().equals(""))
                        mBinding.frgAttendanceLlTime.setVisibility(View.GONE);
                    else
                        mBinding.frgAttendanceTvInTime.setText("In - " + responseAttendances.get(i).getTimeIn());

                    if (!responseAttendances.get(i).getEarlyTimeOut().equals(""))
                        mBinding.frgAttendanceTvOutTimeLate.setText("Early : " + responseAttendances.get(i).getEarlyTimeOut());
                    else
                        mBinding.frgAttendanceTvOutTimeLate.setText("Early : NA");

                    if (!responseAttendances.get(i).getLateTimeIn().equals(""))
                        mBinding.frgAttendanceTvInTimeLate.setText("Late : " + responseAttendances.get(i).getLateTimeIn());
                    else
                        mBinding.frgAttendanceTvInTimeLate.setText("Late : NA");

                    break;
                }
            }
            if (!recordFound) {
                setVisibilityDialogView(View.GONE);
            }
        }

        SimpleDateFormat sdfDate = new SimpleDateFormat("EEE, dd MMM yyyy");
        String strDate = sdfDate.format(date);
        mBinding.frgAttendanceTvDate.setText(strDate);
        mBinding.frgAttendanceCvMain.setVisibility(View.VISIBLE);
    }

    private void setVisibilityDialogView(int value) {
        mBinding.frgAttendanceLlTitle.setVisibility(value);
        mBinding.frgAttendanceLlTime.setVisibility(value);
        mBinding.frgAttendanceLlLeaveStatus.setVisibility(value);
    }

    @Override
    public void onRightButtonClick() {
        updateCalendar(true);
    }

    @Override
    public void onLeftButtonClick() {
        updateCalendar(false);
    }

    @Override
    public void onTitleClick() {
        if (api != null && api.isExecuted())
            api.cancel();
        currentCalender = Calendar.getInstance();
        AttendanceApiCall(currentCalender.get(Calendar.MONTH) + 1, currentCalender.get(Calendar.YEAR));
        mBinding.fragmentAttendanceRobotCalnder.initializeCalendar(currentCalender);
    }

    private void updateCalendar(boolean isAdded) {
        if (api != null && api.isExecuted())
            api.cancel();

        if (isAdded)
            currentCalender.add(Calendar.MONTH, 1);
        else
            currentCalender.add(Calendar.MONTH, -1);

        AttendanceApiCall(currentCalender.get(Calendar.MONTH) + 1, currentCalender.get(Calendar.YEAR));
        mBinding.fragmentAttendanceRobotCalnder.initializeCalendar(currentCalender);
    }

    @Override
    public void onClick(View view) {
        int position = (int) view.getTag();

        final Bundle bundle = new Bundle();
        bundle.putLong("date", mBinding.fragmentAttendanceRobotCalnder.getCurrentDate().getTime());

        if (position == 1) {
            new CountDownTimer(300, 300) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    AddCorrectionAttendanceRequest addCorrectionAttendanceRequest = new AddCorrectionAttendanceRequest();
                    addCorrectionAttendanceRequest.setArguments(bundle);
                    addFragment(addCorrectionAttendanceRequest, getString(R.string.str_correction_request));
                }
            }.start();
        } else {
            new CountDownTimer(300, 300) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    AddRegularisationAttendanceRequest addAttendanceRequest = new AddRegularisationAttendanceRequest();
                    addAttendanceRequest.setArguments(bundle);
                    addFragment(addAttendanceRequest, getString(R.string.str_correction_Regularisation_request));
                }
            }.start();
        }
    }

    @Override
    public void onResponseComplete(Object clsGson, int requestCode,int responseCode) {
        dismissDialog();
        if (clsGson != null) {
            responseAttendances = (List<ResponseAttendance>) clsGson;
            if (responseAttendances != null && responseAttendances.size() > 0)
                setUpView();
        }
    }

    @Override
    public void onResponseError(String errorMessage, int requestCode) {
        dismissDialog();
        if (errorMessage != null && errorMessage.equals("Socket closed") || errorMessage.equals("Canceled")) {

        } else if (errorMessage != null && getActivity() != null)
            ((HomeActivity) getActivity()).showSnackBar(mBinding.getRoot(), errorMessage, Snackbar.LENGTH_SHORT);
    }

    private void AttendanceApiCall(int month, int year) {
        showDialog();
        api = ((HomeActivity) getActivity()).getApiTask().doGetAttendance(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
                month,
                year,
                this
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        api.cancel();
        Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.fl_container);
        if (currentFragment instanceof DashBoaredFragment) {
            ((DashBoaredFragment) currentFragment).requestCountApi();
            ((DashBoaredFragment) currentFragment).approvalCountApi();
        }
    }

    public enum workingType {OffDay, Holiday, FullDay}

    public enum leaveType {Approve, Pending}

}
