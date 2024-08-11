package hrms.hrms.fragment.shift_change;

import static hrms.hrms.baseclass.BaseAppCompactActivity.isInternet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;
import com.hris365.R;
import com.hris365.databinding.FragmentEditShiftChangeBinding;
import com.rey.material.app.DatePickerDialog;

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
import hrms.hrms.retrofit.model.resetPassword.ResponseResetPassword;
import hrms.hrms.retrofit.model.shiftChange.ResponseShiftAvailable;
import hrms.hrms.retrofit.model.shiftChange.ResponseShiftChange;
import hrms.hrms.utility.Constants;
import hrms.hrms.utility.Utility;
import hrms.hrms.widget.SpinnerAdapter;

/**
 * Created by Yudiz on 17/10/16.
 */
public class EditShiftChangeFragment extends BaseFragment implements OnApiResponseListner {

    private FragmentEditShiftChangeBinding mBinding;
    private ResponseShiftChange shift;
    private List<ResponseShiftAvailable> shiftAvailables;
    private SpinnerAdapter spinnerAdapter;
    private Calendar fromDate;
    private long dateFrom, dateTo;
    private Calendar cal = Calendar.getInstance();
    private Calendar tempCal = Calendar.getInstance();
    private Date newDate = new Date();
    private Date tempDate = new Date();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_edit_shift_change,
                container,
                false);

        ButterKnife.bind(this, mBinding.getRoot());
        if (getArguments().containsKey(ShiftChangeFragment.shiftChange))
            shift = (ResponseShiftChange)
                    getArguments().getSerializable(ShiftChangeFragment.shiftChange);
        mBinding.fragmentEditShiftTvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isInternet) {
                    if (isValid())
                        doUpdateShift();
                } else {
                    ((HomeActivity) getActivity()).showSnackBar(
                            mBinding.getRoot(), getString(R.string.nointernet), Snackbar.LENGTH_SHORT
                    );
                }
            }
        });
        mBinding.fragmentEditShiftTvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBinding.fragmentEditShiftFormTvDesc.setText("");
                init();
                if (shiftAvailables != null && shiftAvailables.size() > 0)
                    mBinding.editShiftSpnName.setSelection(0);
            }
        });
        mBinding.fragmentEditShiftFormTvFormDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newDate.setTime(dateTo);
                cal.setTime(newDate);

                tempDate.setTime(dateFrom);
                tempCal.setTime(tempDate);

//                Utility.showDateDialog(getFragmentManager(),
//                        1, 1, 1900, cal.get(Calendar.DAY_OF_MONTH),
//                        cal.get(Calendar.MONTH), cal.get(Calendar.YEAR), new OnDateDialogListener() {
//                            @Override
//                            public void onPositiveActionClicked(DatePickerDialog dialog) {
//                                SimpleDateFormat format =
//                                        new SimpleDateFormat(Constants.DATE_FORMAT1);
//                                String date1 = dialog.getFormattedDate(format);
//                                mBinding.fragmentEditShiftFormTvFormDate.setText(date1);
//                                dateFrom = dialog.getDate();
//                            }
//                        },
//                        tempCal.get(Calendar.YEAR),
//                        tempCal.get(Calendar.MONTH),
//                        tempCal.get(Calendar.DAY_OF_MONTH));


                Utility.showDateDialogNew(getActivity(),
                        1, 1, 1900, cal.get(Calendar.DAY_OF_MONTH),
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
                                mBinding.fragmentEditShiftFormTvFormDate.setText(getFormattedDate(mCalendar));
                                dateFrom = mCalendar.getTimeInMillis();
                            }
                        });
            }
        });
        mBinding.fragmentEditShiftFormRlToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newDate.setTime(dateFrom);
                cal.setTime(newDate);

                tempDate.setTime(dateTo);
                tempCal.setTime(tempDate);

//                Utility.showDateDialog(getFragmentManager(),
//                        cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH),
//                        cal.get(Calendar.YEAR), 1, 1, cal.get(Calendar.YEAR) + 100,
//                        new OnDateDialogListener() {
//                            @Override
//                            public void onPositiveActionClicked(DatePickerDialog dialog) {
//                                SimpleDateFormat format =
//                                        new SimpleDateFormat(Constants.DATE_FORMAT1);
//                                String date1 = dialog.getFormattedDate(format);
//                                mBinding.fragmentEditShiftFormTvToDate.setText(date1);
//                                dateTo = dialog.getDate();
//                            }
//                        },
//                        tempCal.get(Calendar.YEAR),
//                        tempCal.get(Calendar.MONTH),
//                        tempCal.get(Calendar.DAY_OF_MONTH));

                Utility.showDateDialogNew(getActivity(),
                        cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH),
                        cal.get(Calendar.YEAR), 1, 1, cal.get(Calendar.YEAR) + 100,
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
                                mBinding.fragmentEditShiftFormTvToDate.setText(getFormattedDate(mCalendar));
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
        doGetShiftAvailableApi();
        init();
    }

    private void init() {
        mBinding.fragmentEditShiftFormTvFormDate.setText(Utility.getFormattedDateTimeString(shift.getFromDate(), HomeActivity.serverFormat, "dd MMM yyyy"));
        mBinding.fragmentEditShiftFormTvToDate.setText(Utility.getFormattedDateTimeString(shift.getToDate(), HomeActivity.serverFormat, "dd MMM yyyy"));
        SimpleDateFormat simpledate = new SimpleDateFormat(HomeActivity.serverFormat);
        try {
            tempDate = simpledate.parse(shift.getFromDate());
            dateFrom = tempDate.getTime();
            tempDate = simpledate.parse(shift.getToDate());
            dateTo = tempDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void doGetShiftAvailableApi() {
        showDialog();
        ((HomeActivity) getActivity()).getApiTask().doGetShiftAvailable(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
                this
        );
    }

    @Override
    public void onResponseComplete(Object clsGson, int requestCode,int responseCode) {
        dismissDialog();
        if (requestCode == RequestCode.SHIFTAVAILABLE) {
            shiftAvailables = (List<ResponseShiftAvailable>) clsGson;
            if (shiftAvailables != null && shiftAvailables.size() > 0)
                setAdapter();
        } else if (requestCode == RequestCode.SHIFTUPDATE) {
            ResponseResetPassword responseResetPassword = (ResponseResetPassword) clsGson;
            if (responseResetPassword != null)
                Toast.makeText(getContext(),
                        responseResetPassword.getMessage() + "",
                        Toast.LENGTH_SHORT).show();
            if (getActivity() != null)
                ((HomeActivity) getActivity()).onBackPressed();
        }
    }

    @Override
    public void onResponseError(String errorMessage, int requestCode) {
        dismissDialog();
        if (errorMessage != null)
            ((HomeActivity) getActivity()).showSnackBar(
                    mBinding.getRoot(),
                    errorMessage,
                    Snackbar.LENGTH_SHORT
            );
    }

    private void setAdapter() {
        ArrayList<String> shiftTitleList = new ArrayList<>();
        for (ResponseShiftAvailable shiftAvailable : shiftAvailables) {
            shiftTitleList.add(shiftAvailable.getShiftName());
        }
        spinnerAdapter = new SpinnerAdapter(
                getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                shiftTitleList
        );
        mBinding.editShiftSpnName.setAdapter(spinnerAdapter);
    }

    private void doUpdateShift() {
        showDialog();

        String shiftId = shiftAvailables.get(
                mBinding.editShiftSpnName.getSelectedItemPosition()).
                getShiftId();

        ((HomeActivity) getActivity()).getApiTask().doGetShiftUpdate(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
                shiftId,
                getFormatDate("dd MMM yyyy", "MM-dd-yyyy", mBinding.fragmentEditShiftFormTvFormDate.getText().toString()),
                getFormatDate("dd MMM yyyy", "MM-dd-yyyy", mBinding.fragmentEditShiftFormTvToDate.getText().toString()),
                mBinding.fragmentEditShiftFormTvDesc.getText().toString(),
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.COMPANYID),
                this
        );
    }

    public boolean isValid() {
        if (mBinding.editShiftSpnName.getSelectedItem() == null) {
            ((HomeActivity) getActivity()).showSnackBar(
                    mBinding.getRoot(),
                    getString(R.string.error_no_item), Snackbar.LENGTH_SHORT);
            return false;
        } else if (mBinding.fragmentEditShiftFormTvDesc.getText().toString().isEmpty()) {
            ((HomeActivity) getActivity()).showSnackBar(
                    mBinding.getRoot(),
                    getString(R.string.str_error_desc), Snackbar.LENGTH_SHORT);
            return false;
        }
        return true;
    }

}
