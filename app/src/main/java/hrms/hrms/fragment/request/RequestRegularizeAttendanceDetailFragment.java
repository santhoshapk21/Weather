package hrms.hrms.fragment.request;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.hris365.R;
import com.hris365.databinding.FragmentRequestRegularizeDetailsScreenBinding;

import hrms.hrms.baseclass.BaseAppCompactActivity;
import hrms.hrms.baseclass.BaseFragment;
import hrms.hrms.retrofit.OnApiResponseListner;
import hrms.hrms.retrofit.model.request.regularisation.ResponseRequestRegularisation;

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
public class RequestRegularizeAttendanceDetailFragment extends BaseFragment implements OnApiResponseListner {

    public static final String REGULARISATIONID = "regularisationid";
    private FragmentRequestRegularizeDetailsScreenBinding mBinding;
    private String regularisationid;
    ResponseRequestRegularisation responseRequestRegularisationDetail;
    private Call<?> getRequestRegularisationDetail;
    private CancelLeaveAttanceFragment.OnLeaveCancelSuccessListener leaveCancelSuccessListener;

    public RequestRegularizeAttendanceDetailFragment(CancelLeaveAttanceFragment.OnLeaveCancelSuccessListener leaveCancelSuccessListener) {
        super();
        this.leaveCancelSuccessListener = leaveCancelSuccessListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_request_regularize_details_screen, container, false);

        // Inflate the layout for this fragment
        ButterKnife.bind(this, mBinding.getRoot());
        regularisationid = getArguments().getString(REGULARISATIONID);

        mBinding.frgRequestRegularizeDetailsTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelLeaveAttanceFragment cancelLeaveAttanceFragment = new CancelLeaveAttanceFragment(leaveCancelSuccessListener);
                Bundle bundle = new Bundle();
                bundle.putString("LeaveAppID", regularisationid);
                bundle.putString("Type", "Regularisation");
                cancelLeaveAttanceFragment.setArguments(bundle);
                addFragment(cancelLeaveAttanceFragment, getString(R.string.str_cancel_attendance));
            }
        });
        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        getRequestRegularisationDetailApi();
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

    private void getRequestRegularisationDetailApi() {
        showDialog();
        getRequestRegularisationDetail = ((HomeActivity) getActivity()).getApiTask().doRequestRegularisationDetail(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
                regularisationid,
                this
        );
    }

    @Override
    public void onResponseComplete(Object clsGson, int requestCode,int responseCode) {
        dismissDialog();
        if (clsGson != null) {
            ResponseRequestRegularisation responserequestregularisation = (ResponseRequestRegularisation) clsGson;
            setData(responserequestregularisation);
            responseRequestRegularisationDetail = responserequestregularisation;
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

    private void setData(ResponseRequestRegularisation responserequestregularisation) {
        mBinding.frgRequestRegularizeDetailsTvName.setText(getFullName());
        mBinding.frgRequestRegularizeDetailsTvTitle.setText(responserequestregularisation.getCategoryName());
        mBinding.frgRequestRegularizeDetailsTvFromDate.setText(Utility.getFormattedDateTimeString(responserequestregularisation.getFromDate(), HomeActivity.serverFormat, "dd MMM yyyy"));
        mBinding.frgRequestRegularizeDetailsTvToDate.setText(Utility.getFormattedDateTimeString(responserequestregularisation.getToDate(), HomeActivity.serverFormat, "dd MMM yyyy"));
        mBinding.frgRequestRegularizeDetailsTvReson.setText(responserequestregularisation.getDescription());
        mBinding.frgRequestRegularizeDetailsTvManagerName.setText(responserequestregularisation.ReportPerson1Name);
        mBinding.frgRequestShiftChnageDetailsTvManagerReson.setText(responserequestregularisation.getComment());
/*
        if (responserequestregularisation.getStatus().toLowerCase().equals("cancel")){
            mBinding.frgRequestRegularizeDetailsTvCancel.setVisibility(View.GONE);
        }else{
            mBinding.frgRequestRegularizeDetailsTvCancel.setVisibility(View.VISIBLE);
        }
*/
//        frg_request_shift_chnage_details_tv_manager_reson
    }
}
