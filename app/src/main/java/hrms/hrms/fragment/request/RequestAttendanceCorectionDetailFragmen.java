package hrms.hrms.fragment.request;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.hris365.R;
import com.hris365.databinding.FragmentRequestAttendanceCorrectionDetailsScreenBinding;

import hrms.hrms.baseclass.BaseAppCompactActivity;
import hrms.hrms.baseclass.BaseFragment;
import hrms.hrms.retrofit.OnApiResponseListner;
import hrms.hrms.retrofit.model.request.Correction.ResponseRequestCorrection;

import butterknife.ButterKnife;
import hrms.hrms.activity.HomeActivity;
import hrms.hrms.utility.Utility;
import retrofit2.Call;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

/**
 * Created by yudiz on 11/10/17.
 */

@SuppressLint("ValidFragment")
public class RequestAttendanceCorectionDetailFragmen extends BaseFragment implements OnApiResponseListner {

    public static final String ATTENDANCECORRECTIONID = "attendancecorrectionid";
    private FragmentRequestAttendanceCorrectionDetailsScreenBinding mBinding;
    private String attendancecorrectionid;
    ResponseRequestCorrection responserequestcorrectiondetail;
    private Call<?> getRequestAttendanceCorrectionDetail;
    private CancelLeaveAttanceFragment.OnLeaveCancelSuccessListener leaveCancelSuccessListener;

    public RequestAttendanceCorectionDetailFragmen(CancelLeaveAttanceFragment.OnLeaveCancelSuccessListener leaveCancelSuccessListener) {
        this.leaveCancelSuccessListener = leaveCancelSuccessListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_request_attendance_correction_details_screen, container, false);

        // Inflate the layout for this fragment
        ButterKnife.bind(this, mBinding.getRoot());
        attendancecorrectionid = getArguments().getString(ATTENDANCECORRECTIONID);

        mBinding.frgRequestAttendanceCorrectionDetailsTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelLeaveAttanceFragment cancelLeaveAttanceFragment = new CancelLeaveAttanceFragment(leaveCancelSuccessListener);
                Bundle bundle = new Bundle();
                bundle.putString("LeaveAppID", attendancecorrectionid);
                bundle.putString("Type", "Correction");
                cancelLeaveAttanceFragment.setArguments(bundle);
                addFragment(cancelLeaveAttanceFragment, getString(R.string.str_cancel_attendance));
            }
        });

        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        getRequestAttendanceCorrectionDetailApi();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        setAdapter();

    }

    private void getRequestAttendanceCorrectionDetailApi() {
        showDialog();
        getRequestAttendanceCorrectionDetail = ((HomeActivity) getActivity()).getApiTask().doRequestAttendanceCorrectionDetail(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
                attendancecorrectionid,
                this
        );
    }

    @Override
    public void onResponseComplete(Object clsGson, int requestCode, int responseCode) {
        dismissDialog();
        if (clsGson != null) {
            ResponseRequestCorrection responserequestcorrection = (ResponseRequestCorrection) clsGson;
            setData(responserequestcorrection);
            responserequestcorrectiondetail = responserequestcorrection;
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

    private void setData(ResponseRequestCorrection responserequestcorrection) {
        mBinding.frgRequestAttendanceCorrectionDetailsTvName.setText(getFullName());
        mBinding.frgRequestAttendanceCorrectionDetailsTvTitle.setText(responserequestcorrection.getType());
/*
        if (responserequestcorrection.getFinalStatus().toLowerCase().equals("cancel")) {
            mBinding.frgRequestAttendanceCorrectionDetailsTvCancel.setVisibility(View.GONE);
        } else {
            mBinding.frgRequestAttendanceCorrectionDetailsTvCancel.setVisibility(View.VISIBLE);
        }
*/
        mBinding.frgRequestAttendanceCorrectionDetailsTvFromTime.setText(responserequestcorrection.getRequestTime());
        mBinding.frgRequestAttendanceCorrectionDetailsTvFromDate.setText(Utility.getFormattedDateTimeString(responserequestcorrection.getRequestDate(), HomeActivity.serverFormat, "dd MMM yyyy"));
        mBinding.frgRequestAttendanceCorrectionDetailsTvReson.setText(responserequestcorrection.getReason());
        mBinding.frgRequestMarkAttendaceDetailsTvManagerName.setText(responserequestcorrection.ReportPerson1Name);
        mBinding.frgRequestAttendanceCorrectionDetailsTvManagerReson.setText(responserequestcorrection.getComment());
//        frg_request_shift_chnage_details_tv_manager_reson
    }

}
