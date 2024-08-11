package hrms.hrms.fragment.request;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.hris365.R;
import com.hris365.databinding.FragmentRequestShiftChangeDetailsScreenBinding;

import hrms.hrms.baseclass.BaseAppCompactActivity;
import hrms.hrms.baseclass.BaseFragment;
import hrms.hrms.retrofit.OnApiResponseListner;
import hrms.hrms.retrofit.model.request.shiftChange.ResponseRequestedShiftChange;
import hrms.hrms.activity.HomeActivity;
import butterknife.ButterKnife;
import hrms.hrms.utility.Utility;
import retrofit2.Call;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

/**
 * Created by yudiz on 01/05/17.
 */

@SuppressLint("ValidFragment")
public class RequestShiftChangeDetailsFragment extends BaseFragment implements OnApiResponseListner {
    public static final String SHIFTCHANGEID = "shiftchangeid";
    private Call<?> getRequestShiftChangeDetail;
    private FragmentRequestShiftChangeDetailsScreenBinding mBinding;
    private String shiftchangeid;
    private ResponseRequestedShiftChange responserequestshiftchangedetail;
    private CancelLeaveAttanceFragment.OnLeaveCancelSuccessListener leaveCancelSuccessListener;

    public RequestShiftChangeDetailsFragment(CancelLeaveAttanceFragment.OnLeaveCancelSuccessListener leaveCancelSuccessListener) {
        this.leaveCancelSuccessListener = leaveCancelSuccessListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_request_shift_change_details_screen, container, false);

        // Inflate the layout for this fragment
        ButterKnife.bind(this, mBinding.getRoot());
        shiftchangeid = getArguments().getString(SHIFTCHANGEID);

        mBinding.frgRequestShiftChangeDetailsTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelLeaveAttanceFragment cancelLeaveAttanceFragment = new CancelLeaveAttanceFragment(leaveCancelSuccessListener);
                Bundle bundle = new Bundle();
                bundle.putString("LeaveAppID", shiftchangeid);
                bundle.putString("Type", "Shift");
                cancelLeaveAttanceFragment.setArguments(bundle);
                addFragment(cancelLeaveAttanceFragment, getString(R.string.str_cancel_attendance));
            }
        });

        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        getRequestShiftChangeDetailApi();
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

    private void getRequestShiftChangeDetailApi() {
        showDialog();
        getRequestShiftChangeDetail = ((HomeActivity) getActivity()).getApiTask().doRequestShiftChangeDetails(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
                shiftchangeid,
                this
        );
    }

    @Override
    public void onResponseComplete(Object clsGson, int requestCode, int responseCode) {
        dismissDialog();
        if (clsGson != null) {
            ResponseRequestedShiftChange responserequestshiftchange = (ResponseRequestedShiftChange) clsGson;
            setData(responserequestshiftchange);
            responserequestshiftchangedetail = responserequestshiftchange;
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

    private void setData(ResponseRequestedShiftChange responserequestshiftchange) {
        mBinding.frgRequestShiftChangeDetailsTvName.setText(getFullName());
        mBinding.frgRequestShiftChangeDetailsTvTitle.setText(responserequestshiftchange.getShiftName());
        mBinding.frgRequestShiftChangeDetailsTvFromTime.setText(Utility.getFormattedDateTimeString(responserequestshiftchange.getFromDate(), HomeActivity.serverFormat, "HH:mm"));
        mBinding.frgRequestShiftChangeDetailsTvToTime.setText(Utility.getFormattedDateTimeString(responserequestshiftchange.getToDate(), HomeActivity.serverFormat, "HH:mm"));
        mBinding.frgRequestShiftChangeDetailsTvFromDate.setText(Utility.getFormattedDateTimeString(responserequestshiftchange.getFromDate(), HomeActivity.serverFormat, "dd MMM yyyy"));
        mBinding.frgRequestShiftChangeDetailsTvToDate.setText(Utility.getFormattedDateTimeString(responserequestshiftchange.getToDate(), HomeActivity.serverFormat, "dd MMM yyyy"));
        mBinding.frgRequestShiftChangeDetailsTvReson.setText(responserequestshiftchange.getReason());
        mBinding.frgRequestShiftChangeDetailsTvManagerName.setText(responserequestshiftchange.ReportPerson1Name);
        mBinding.frgRequestShiftChnageDetailsTvManagerReson.setText(responserequestshiftchange.getComment());
/*
        if (responserequestshiftchange.getStatus().toLowerCase().equals("cancel")){
            mBinding.frgRequestShiftChangeDetailsTvCancel.setVisibility(View.GONE);
        }else{
            mBinding.frgRequestShiftChangeDetailsTvCancel.setVisibility(View.VISIBLE);
        }
*/
//        frg_request_shift_chnage_details_tv_manager_reson
    }
//
//    public String getDate(String date) {
//        return getFormatDate(serverFormat, "dd MMM yyyy", date);
//    }
//
//    public String getTime(String date) {
//        return getFormatDate(serverFormat, "HH:mm", date);
//    }
}
