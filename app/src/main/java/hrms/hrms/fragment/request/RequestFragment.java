package hrms.hrms.fragment.request;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.hris365.R;
import com.hris365.databinding.FragmentRequestScreenBinding;

import java.util.Calendar;

import butterknife.ButterKnife;
import hrms.hris365.pickerview.popwindow.DatePickerPopWin;
import hrms.hrms.activity.HomeActivity;
import hrms.hrms.adapter.request.RequestAttendanceCorrectionRequestAdapter;
import hrms.hrms.adapter.request.RequestDataLeaveAdapter;
import hrms.hrms.adapter.request.RequestHeaderAdapter;
import hrms.hrms.adapter.request.RequestMarkAttendanceAdapter;
import hrms.hrms.adapter.request.RequestReqularizeAttendanceAdapter;
import hrms.hrms.adapter.request.RequestShiftChangeAdapter;
import hrms.hrms.baseclass.BaseAppCompactActivity;
import hrms.hrms.baseclass.BaseFragment;
import hrms.hrms.fragment.DashBoaredFragment;
import hrms.hrms.retrofit.OnApiResponseListner;
import hrms.hrms.retrofit.RequestCode;
import hrms.hrms.retrofit.model.request.ResponseRequest;
import hrms.hrms.retrofit.model.request.ResponseRequestCount;
import hrms.hrms.retrofit.model.request.leave.ResponseRequestLeave;
import retrofit2.Call;

/**
 * Created by yudiz on 03/03/17.
 */

public class RequestFragment extends BaseFragment
        implements View.OnClickListener, OnApiResponseListner, CancelLeaveAttanceFragment.OnLeaveCancelSuccessListener {

    private LinearLayoutManager linearLayoutManager;
    private FragmentRequestScreenBinding mBinding;
    private RequestHeaderAdapter requestHeaderAdapter;
    private LinearLayoutManager horizontalHeaderlayoutManager;
    public Calendar calendar;
    private RequestDataLeaveAdapter mLeaveAdapter;
    private RequestAttendanceCorrectionRequestAdapter mrequestAttendanceCorrectionRequestAdapter;
    private RequestReqularizeAttendanceAdapter mrequestReqularizeAttendanceAdapter;
    private RequestShiftChangeAdapter mshiftChangeAdapter;
    private RequestMarkAttendanceAdapter mrequestMarkAttendanceAdapter;
    private ResponseRequest mResponseRequestList;
    private Call<?> call;
    ResponseRequestLeave response;
    private int lastSelectedPosition = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_request_screen, container, false);

        ButterKnife.bind(this, mBinding.getRoot());
mBinding.frgRequestLlFilterMain.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


        DatePickerPopWin pickerPopWin = new DatePickerPopWin.Builder(getContext(), new DatePickerPopWin.OnDatePickedListener() {
            @Override
            public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
                calendar.set(Calendar.MONTH, month - 1);
                calendar.set(Calendar.YEAR, year);
                onGetAllRequestApi(calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
                lastSelectedPosition = -1;
            }
        }).textConfirm(getResources().getString(R.string.str_confirm)) //text of confirm button
                .colorConfirm(getContext().getResources().getColor(R.color.black))
                .textCancel(getResources().getString(R.string.str_cancel)) //text of cancel button
                .minYear(calendar.get(Calendar.YEAR) - 100) //min year in loop
                .maxYear(calendar.get(Calendar.YEAR) + 100) // max year in loop
                .showDayMonthYear(true) // shows like dd mm yyyy (default is false)
                .dateChose(calendar.get(Calendar.YEAR) + "-" +
                        (calendar.get(Calendar.MONTH) + 1) + "-"
                        + calendar.get(Calendar.DAY_OF_MONTH))
                // date chose when init popwindow
                .build();

        pickerPopWin.showPopWin(getActivity());
    }
});
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        setHeaderAdapter();
    }

    private void init() {
        calendar = Calendar.getInstance();
        onGetAllRequestApi(calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
        mBinding.frgRequestTvFilterMonth.setText(getDate());
        linearLayoutManager = new LinearLayoutManager(getContext());
        mBinding.frgRequestRvListRequests.setLayoutManager(linearLayoutManager);
    }

    private void setHeaderAdapter() {
        requestHeaderAdapter = new RequestHeaderAdapter(getContext(), RequestFragment.this);
        horizontalHeaderlayoutManager = new LinearLayoutManager(getContext());
        horizontalHeaderlayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mBinding.frgRequestRvHeader.setLayoutManager(horizontalHeaderlayoutManager);
        mBinding.frgRequestRvHeader.setAdapter(requestHeaderAdapter);
    }

    @Override
    public void onClick(View v) {

        if (lastSelectedPosition == (Integer) v.getTag())
            return;

        if (mResponseRequestList == null)
            return;
        else
            setDataView();

        switch ((Integer) v.getTag()) {
            case 0:
                if (mResponseRequestList.getLeave() == null)
                    setNoDataView();
                else
                    setLeaveAdapter();
                break;

            case 1:
                if (mResponseRequestList.getShift() == null)
                    setNoDataView();
                else
                    setShiftChangeAdapter();
                break;

            case 2:
                if (mResponseRequestList.getCorrection() == null)
                    setNoDataView();
                else
                    setRequestAttendanceCorrectionRequestAdapter();
                break;

            case 3:
                if (mResponseRequestList.getRegularization() == null)
                    setNoDataView();
                else
                    setRequestReqularizeAttendanceAdapter();
                break;

            case 4:
                if (mResponseRequestList.getAttendance() == null)
                    setNoDataView();
                else
                    setMarkAttendanceAdapter();
                break;
        }
        mBinding.frgRequestTvFilterMonth.setText(getDate());
        lastSelectedPosition = (Integer) v.getTag();
    }

    private String getDate() {
        String date = null;
        try {

            date = getFormatDate("MM-yyyy", "MMMM yyyy",
                    String.format("%02d", calendar.get(Calendar.MONTH) + 1) + "-" +
                            calendar.get(Calendar.YEAR));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return (date == null) ? "" : date;
    }

    @Override
    public void onResponseComplete(Object clsGson, int requestCode, int responseCode) {
        if (clsGson != null) {

            if (requestCode == RequestCode.REQUESTALL) {
                mResponseRequestList = (ResponseRequest) clsGson;
                View view = new View(getContext());
                view.setTag(requestHeaderAdapter.getSelectedPosition());
                lastSelectedPosition = -1;
                onClick(view);
            }

            if (requestCode == RequestCode.REQUESTMONTHLYCOUNT) {
                ResponseRequestCount model = (ResponseRequestCount) clsGson;

                requestHeaderAdapter.setCounts(model.details.get(0).Leave, model.details.get(0).Shift, model.details.get(0).Correction, model.details.get(0).Regularisation, model.details.get(0).Attendance);
            }
        }
    }

    private void setNoDataView() {
        mBinding.frgLlNoData.setVisibility(View.VISIBLE);
        mBinding.frgRequestRvListRequests.setVisibility(View.GONE);
    }

    private void setDataView() {
        mBinding.frgLlNoData.setVisibility(View.GONE);
        mBinding.frgRequestRvListRequests.setVisibility(View.VISIBLE);
    }

    private void setMarkAttendanceAdapter() {
        mrequestMarkAttendanceAdapter = new RequestMarkAttendanceAdapter(requireContext(), mResponseRequestList.getAttendance(), this, v -> {
            Log.i("TAG1", v.getTag() + "");
            Bundle bundle = new Bundle();
            bundle.putString(RequestMarkAttendanceDetailFragment.GPSATTENDANCEID, v.getTag().toString());
//                addFragment(new AttendanceFragment(), getContext().getString(R.string.attendance));

            RequestMarkAttendanceDetailFragment dFragment = new RequestMarkAttendanceDetailFragment(RequestFragment.this);
            dFragment.setArguments(bundle);
            addFragment(dFragment, getContext().getString(R.string.str_mark_attandance));
        });
        mBinding.frgRequestRvListRequests.setAdapter(mrequestMarkAttendanceAdapter);
    }

    private void setLeaveAdapter() {
        if (mLeaveAdapter == null) {
            mLeaveAdapter = new RequestDataLeaveAdapter(getContext(),
                    mResponseRequestList.getLeave(), this, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                Log.i("TAG1",v.getTag().toString()+"");
                    Bundle bundle = new Bundle();
                    bundle.putString(RequestLeaveDetailsFragment.LEAVEAPPID, v.getTag().toString());


//                addFragment(new AttendanceFragment(), getContext().getString(R.string.attendance));

                    RequestLeaveDetailsFragment dFragment = new RequestLeaveDetailsFragment(RequestFragment.this);
                    dFragment.setArguments(bundle);
                    addFragment(dFragment, getContext().getString(R.string.request_details));
                }
            });
            mBinding.frgRequestRvListRequests.setAdapter(mLeaveAdapter);
        } else {
            mLeaveAdapter.changeData(mResponseRequestList.getLeave());
        }
    }

    private void setRequestAttendanceCorrectionRequestAdapter() {
        mrequestAttendanceCorrectionRequestAdapter = new RequestAttendanceCorrectionRequestAdapter(
                getContext(),
                mResponseRequestList.getCorrection(),
                this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG1", v.getTag() + "");
                Bundle bundle = new Bundle();
                bundle.putString(RequestAttendanceCorectionDetailFragmen.ATTENDANCECORRECTIONID, v.getTag().toString());
//                addFragment(new AttendanceFragment(), getContext().getString(R.string.attendance));

                RequestAttendanceCorectionDetailFragmen dFragment = new RequestAttendanceCorectionDetailFragmen(RequestFragment.this);
                dFragment.setArguments(bundle);
                addFragment(dFragment, getContext().getString(R.string.str_attendance_correction));
            }
        });
        mBinding.frgRequestRvListRequests.setAdapter(mrequestAttendanceCorrectionRequestAdapter);
    }

    private void setRequestReqularizeAttendanceAdapter() {
        mrequestReqularizeAttendanceAdapter = new RequestReqularizeAttendanceAdapter(
                getContext(),
                mResponseRequestList.getRegularization(),
                this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG1", v.getTag() + "");
                Bundle bundle = new Bundle();
                bundle.putString(RequestRegularizeAttendanceDetailFragment.REGULARISATIONID, v.getTag().toString());
//                addFragment(new AttendanceFragment(), getContext().getString(R.string.attendance));

                RequestRegularizeAttendanceDetailFragment dFragment = new RequestRegularizeAttendanceDetailFragment(RequestFragment.this);
                dFragment.setArguments(bundle);
                addFragment(dFragment, getContext().getString(R.string.str_regularization_attendance));
            }
        });
        mBinding.frgRequestRvListRequests.setAdapter(mrequestReqularizeAttendanceAdapter);
    }

    private void setShiftChangeAdapter() {
        mshiftChangeAdapter = new RequestShiftChangeAdapter(
                getContext(),
                mResponseRequestList.getShift(),
                this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG1", v.getTag() + "");
                Bundle bundle = new Bundle();
                bundle.putString(RequestShiftChangeDetailsFragment.SHIFTCHANGEID, v.getTag().toString());
//                addFragment(new AttendanceFragment(), getContext().getString(R.string.attendance));

                RequestShiftChangeDetailsFragment dFragment = new RequestShiftChangeDetailsFragment(RequestFragment.this);
                dFragment.setArguments(bundle);
                addFragment(dFragment, getContext().getString(R.string.shift_change));
            }
        });
        mBinding.frgRequestRvListRequests.setAdapter(mshiftChangeAdapter);
    }

    @Override
    public void onResponseError(String errorMessage, int requestCode) {
        setNoDataView();
        assert errorMessage != null;
        if (!errorMessage.equals("Socket closed") && !errorMessage.equals("Canceled")) {
            if (getActivity() != null)
                ((HomeActivity) getActivity()).showSnackBar(
                        mBinding.getRoot(), errorMessage, Snackbar.LENGTH_SHORT);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (call != null)
            call.cancel();
        Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.fl_container);
        if (currentFragment instanceof DashBoaredFragment) {
            ((DashBoaredFragment) currentFragment).requestCountApi();
            ((DashBoaredFragment) currentFragment).approvalCountApi();
        }
    }

    public void onGetAllRequestApi(int month, int year) {
        call = ((HomeActivity) getActivity()).getApiTask().doGetAllRequest(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
                (month + 1),
                year,
                this
        );
        requestMonthlyCountApi();
    }

    public void requestMonthlyCountApi() {
        ((HomeActivity) getActivity()).getApiTask().getRequestMonthlyCount(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.YEAR),
                this
        );
    }

    @Override
    public void onLeaveCancelSuccess() {
        getActivity().onBackPressed();
        getActivity().onBackPressed();
        onGetAllRequestApi(calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
    }

    public enum leaveStatus {
        Pending("In Progress"), Approve("Approved"), Reject("Rejected"), Cancel("Cancelled");
        private String value;

        leaveStatus(String s) {
            this.value = s;
        }

        public String getValue() {
            return value;
        }
    }
}
