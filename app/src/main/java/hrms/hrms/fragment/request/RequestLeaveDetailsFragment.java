package hrms.hrms.fragment.request;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.hris365.R;
import com.hris365.databinding.FragmentRequestLeaveDetailsScreenBinding;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import hrms.hrms.activity.HomeActivity;
import hrms.hrms.adapter.request.RequestDetailsAdapter;
import hrms.hrms.baseclass.BaseAppCompactActivity;
import hrms.hrms.baseclass.BaseFragment;
import hrms.hrms.retrofit.OnApiResponseListner;
import hrms.hrms.retrofit.model.request.leave.ResponseLeaveDetail;
import hrms.hrms.widget.TextView;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import retrofit2.Call;

/**
 * Created by yudiz on 17/03/17.
 */

@SuppressLint("ValidFragment")
public class RequestLeaveDetailsFragment extends BaseFragment implements OnApiResponseListner {

    public static final String LEAVEAPPID = "leaveappid";
    private FragmentRequestLeaveDetailsScreenBinding mBinding;
    private RequestDetailsAdapter mRequestDetailsAdapter;
    private Call<?> getRequestLeaveDetail;
    ResponseLeaveDetail responserequestleave;
    private String leaveid;
    private LinearLayoutManager linearLayout;
    private List<ResponseLeaveDetail.Details> leaveDetailList, tmpLeaveDetailList;
    private TextView frg_request_leave_details_tv_reson;
    private CancelLeaveAttanceFragment.OnLeaveCancelSuccessListener leaveCancelSuccessListener;

    public RequestLeaveDetailsFragment(CancelLeaveAttanceFragment.OnLeaveCancelSuccessListener leaveCancelSuccessListener) {
        this.leaveCancelSuccessListener = leaveCancelSuccessListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_request_leave_details_screen, container, false);

        // Inflate the layout for this fragment
        ButterKnife.bind(this, mBinding.getRoot());
        leaveid = getArguments().getString(LEAVEAPPID);

        mBinding.frgRequestLeaveDetailsTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onDestroy();
            }
        });

        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        getRequestLeaveDetailApi();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void getRequestLeaveDetailApi() {
        showDialog();
        getRequestLeaveDetail = ((HomeActivity) getActivity()).getApiTask().doRequestLeaveDetails(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
                leaveid,
                this
        );
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        setAdapter();

    }

    private void setRequestLeaveDetailAdapter() {
        tmpLeaveDetailList = new ArrayList<>();
        linearLayout = new LinearLayoutManager(getActivity());
        mBinding.frgRequestRvList.setLayoutManager(linearLayout);
        mRequestDetailsAdapter = new RequestDetailsAdapter(getActivity(), leaveDetailList);
        mBinding.frgRequestRvList.setAdapter(new ScaleInAnimationAdapter(mRequestDetailsAdapter));
    }

    @Override
    public void onResponseComplete(Object clsGson, int requestCode,int responseCode) {
        dismissDialog();
        if (clsGson != null) {
            ResponseLeaveDetail responserequestlev = (ResponseLeaveDetail) clsGson;
            setData(responserequestlev);
            responserequestleave = responserequestlev;

            List<ResponseLeaveDetail.Details> directories = responserequestlev.Details;

            if (leaveDetailList == null) {
                leaveDetailList = directories;
                setRequestLeaveDetailAdapter();
            } else {
                leaveDetailList.addAll(directories);
                mRequestDetailsAdapter.notifyDataSetChanged();
            }
        }
    }

    private void setData(ResponseLeaveDetail responserequestlev) {
        mBinding.frgRequestLeaveDetailsTvReson.setText(responserequestlev.ReasonForLeave);
        mBinding.frgRequestLeaveDetailsTvManagerName.setText(responserequestlev.ReportPerson1Name);
        mBinding.frgRequestLeaveDetailsTvManagerReson.setText(responserequestlev.ManagerReason);
        if (responserequestlev.LeaveStatus.toLowerCase().equals("cancel")){
            mBinding.frgRequestLeaveDetailsTvCancel.setVisibility(View.INVISIBLE);
        }else{
            mBinding.frgRequestLeaveDetailsTvCancel.setVisibility(View.VISIBLE);
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



}
