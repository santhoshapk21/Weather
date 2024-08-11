package hrms.hrms.fragment.request;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;
import com.hris365.R;
import com.hris365.databinding.FragmentRequestMarkAttendanceDetailsScreenBinding;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import hrms.hrms.baseclass.BaseAppCompactActivity;
import hrms.hrms.baseclass.BaseFragment;
import hrms.hrms.retrofit.OnApiResponseListner;
import hrms.hrms.retrofit.model.correctionRequest.ResponseRequestPunch;
import hrms.hrms.activity.HomeActivity;
import hrms.hrms.utility.Utility;
import retrofit2.Call;


/**
 * Created by yudiz on 11/10/17.
 */

@SuppressLint("ValidFragment")
public class RequestMarkAttendanceDetailFragment extends BaseFragment implements OnApiResponseListner {

    public static final String GPSATTENDANCEID = "gpsattendanceid";
    ResponseRequestPunch responseRequestPunch;
    private FragmentRequestMarkAttendanceDetailsScreenBinding mBinding;
    private String gpsattendanceid;
    private Call<?> getRequestMarkAttendanceDetail;
    private CancelLeaveAttanceFragment.OnLeaveCancelSuccessListener leaveCancelSuccessListener;

    public RequestMarkAttendanceDetailFragment(CancelLeaveAttanceFragment.OnLeaveCancelSuccessListener leaveCancelSuccessListener) {
        this.leaveCancelSuccessListener = leaveCancelSuccessListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_request_mark_attendance_details_screen, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this, mBinding.getRoot());
        gpsattendanceid = getArguments().getString(GPSATTENDANCEID);
        getRequestMarkAttendanceDetail();
        clickListeners();
        return mBinding.getRoot();
    }

    private void clickListeners() {
        mBinding.frgRequestRegularizeDetailsTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelLeaveAttanceFragment cancelLeaveAttanceFragment = new CancelLeaveAttanceFragment(leaveCancelSuccessListener);
                Bundle bundle = new Bundle();
                bundle.putString("LeaveAppID", gpsattendanceid);
                bundle.putString("Type", "MarkAttendance");
                cancelLeaveAttanceFragment.setArguments(bundle);
                addFragment(cancelLeaveAttanceFragment, getString(R.string.str_cancel_attendance));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getRequestMarkAttendanceDetail != null) {
            getRequestMarkAttendanceDetail.cancel();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        setAdapter();
    }

    private void getRequestMarkAttendanceDetail() {
        showDialog();
        getRequestMarkAttendanceDetail = ((HomeActivity) getActivity()).getApiTask().doRequestMarkAttendanceDetail(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
                gpsattendanceid,
                this
        );
    }

    @Override
    public void onResponseComplete(Object clsGson, int requestCode, int responseCode) {
        dismissDialog();
        if (clsGson != null) {
            ResponseRequestPunch responserequestpunch = (ResponseRequestPunch) clsGson;
            setData(responserequestpunch);
            responseRequestPunch = responserequestpunch;
        }
    }

    @Override
    public void onResponseError(String errorMessage, int requestCode) {
        dismissDialog();
        if (errorMessage != null
                && errorMessage.equals("Socket closed") || errorMessage.equals("Canceled")) {

        } else if (errorMessage != null) {
            ((HomeActivity) getActivity()).showSnackBar(mBinding.getRoot(), errorMessage, Snackbar.LENGTH_SHORT);
            Log.i("TAG1", errorMessage + "");
        }
    }

    private void setData(ResponseRequestPunch responserequestpunch) {
        mBinding.frgRequestRegularizeDetailsTvName.setText(getFullName());
//        mBinding.frgRequestMarkAttendanceDetailsTvFromDate.setText(getDate(responserequestpunch.getDate()));
        mBinding.frgRequestMarkAttendanceDetailsTvFromDate.setText(Utility.getFormattedDateTimeString(responserequestpunch.getDate(), HomeActivity.serverFormat,"dd MMM yyyy"));
        mBinding.frgRequestMarkAttendanceDetailsTvFromTime.setText(Utility.getFormattedDateTimeString(responserequestpunch.getDate(), HomeActivity.serverFormat, "HH:mm"));
        mBinding.frgRequestMarkAttendanceDetailsTvReson.setText(responserequestpunch.getInReason());
//        mBinding.frgRequestMarkAttendanceDetailsOutTvFromDate.setText(getDate(responserequestpunch.getDate()));
        mBinding.frgRequestMarkAttendanceDetailsOutTvFromDate.setText(responserequestpunch.getDate());
        mBinding.frgRequestMarkAttendanceDetailsOutTvFromTime.setText(responserequestpunch.getOutTime());
        mBinding.frgRequestMarkAttendanceDetailsOutTvReson.setText(responserequestpunch.getOutReason());
        mBinding.frgRequestMarkAttendaceDetailsTvManagerName.setText(responserequestpunch.ReportPerson1Name);
        mBinding.frgRequestMarkAttendanceDetailsTvManagerReson.setText(responserequestpunch.getReporting1Status());
        if (responserequestpunch.getInLocation().length() > 0) {
            mBinding.frgRequestMarkAttendanceDetailsTvLocation.setText(responserequestpunch.getInLocation());
        } else {
            mBinding.frgRequestMarkAttendanceDetailsLlLocation.setVisibility(View.GONE);
        }
/*
        if (responserequestpunch.getStatus().toLowerCase().equals("cancel")) {
            mBinding.frgRequestRegularizeDetailsTvCancel.setVisibility(View.GONE);
        } else {
            mBinding.frgRequestRegularizeDetailsTvCancel.setVisibility(View.VISIBLE);
        }
*/
        if (responserequestpunch !=null && responserequestpunch.ImageURL !=null && !responserequestpunch.ImageURL.equals("")) {
            Picasso.get().load(responserequestpunch.ImageURL).placeholder(R.drawable.ic_no_image).into(mBinding.frgAttendanceIvImage);
        } else {
            mBinding.frgAttendanceIvImage.setImageResource(R.drawable.ic_no_image);
        }
//        frg_request_shift_chnage_details_tv_manager_reson
    }

    //    Fabric credentials:-Username:pravin@hris365.com
    //    Password:hris365
}
