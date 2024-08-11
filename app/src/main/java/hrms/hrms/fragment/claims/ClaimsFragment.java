package hrms.hrms.fragment.claims;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.andexert.library.RippleView;
import com.google.android.material.snackbar.Snackbar;
import com.hris365.R;
import com.hris365.databinding.FragmentClaimsBinding;

import hrms.hrms.activity.HomeActivity;
import hrms.hrms.baseclass.BaseAppCompactActivity;
import hrms.hrms.baseclass.BaseFragment;
import hrms.hrms.fragment.DashBoaredFragment;
import hrms.hrms.fragment.claims.details.NewClaimsFragment;
import hrms.hrms.interfaces.OnBackPressListerner;
import hrms.hrms.retrofit.OnApiResponseListner;
import hrms.hrms.retrofit.RequestCode;
import hrms.hrms.retrofit.model.claim.ResponseClaimList;

import java.util.List;

import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator;
import retrofit2.Call;

/**
 * Created by Yudiz on 07/10/16.
 */
public class ClaimsFragment extends BaseFragment implements View.OnClickListener, RippleView.OnRippleCompleteListener, OnApiResponseListner {

    private FragmentClaimsBinding mBinding;
    private ClaimAdapter claimAdapter;
    private Call<?> call;
    private OnBackPressListerner onBackPressListerner = new OnBackPressListerner() {
        @Override
        public void onBackPressListerner(Object object) {
            onClaimListApiCall();
        }
    };
    private List<ResponseClaimList.Details> responseClaimList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_claims, container, false);
        ButterKnife.bind(this, mBinding.getRoot());
        onClaimListApiCall();

        mBinding.fragmentClaimsTvNewClaims.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClaimRequestType dialog = new ClaimRequestType();
                dialog.setTargetFragment(ClaimsFragment.this, 1);
                dialog.show(getFragmentManager(), "Claim Type Dialog");

//                addFragment(new NewClaimsFragment(), getString(R.string.new_claim));
            }
        });

        return mBinding.getRoot();
    }

    @Override
    public void onClick(View view) {
        final int position = (int) view.getTag();

        final Bundle choosentype = new Bundle();
        choosentype.putInt("choosentype", position);


//        if (position == 1) {
//            new CountDownTimer(300, 300) {
//                @Override
//                public void onTick(long millisUntilFinished) {
//
//                }
//
//                @Override
//                public void onFinish() {
//NewClaimsFragment newClaimsFragment = new NewClaimsFragment();
//        addFragment(newClaimsFragment, getString(R.string.str_travel_claim));
//                }
//            }.start();
//        } else {
        new CountDownTimer(300, 300) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                NewClaimsFragment newClaimsFragment = new NewClaimsFragment(onBackPressListerner);
                newClaimsFragment.setArguments(choosentype);
//                Toast.makeText(getContext(), choosentype + "", Toast.LENGTH_SHORT).show();
                if (position == ClaimRequestType.TRAVELSELECTED) {
                    addFragment(newClaimsFragment, getString(R.string.str_travel_claim));
                } else if (position == ClaimRequestType.OTHERSELECTED) {
                    addFragment(newClaimsFragment, getString(R.string.str_other_claim));
                }

            }
        }.start();
//        }
    }

    private void onClaimListApiCall() {
        showDialog();
        call = ((HomeActivity) getActivity()).getApiTask().doGetClaimList(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN)
                , this
        );
    }

    private void setUpView() {
        setDataView();
        claimAdapter = new ClaimAdapter(ClaimsFragment.this, responseClaimList, this, getActivity());
        mBinding.fragmentClaimsRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.fragmentClaimsRv.setAdapter(new ScaleInAnimationAdapter(claimAdapter));
        mBinding.fragmentClaimsRv.setItemAnimator(new FadeInUpAnimator());
        mBinding.fragmentClaimsRvNewClaims.setOnRippleCompleteListener(this);
    }

    @Override
    public void onComplete(RippleView rippleView) {
    }

//    @Override
//    public void onBackPressListerner(Object object) {
////        Claim ExpenseClaim = (Claim) object;
////        claimAdapter.add(ExpenseClaim.getClaimDetails(), ExpenseClaim);
//
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.fl_container);
        if (currentFragment instanceof DashBoaredFragment) {
            ((DashBoaredFragment) currentFragment).requestCountApi();
            ((DashBoaredFragment) currentFragment).approvalCountApi();
        }
    }

    @Override
    public void onResponseComplete(Object clsGson, int requestCode, int responseCode) {
        dismissDialog();
        if (requestCode == RequestCode.CLAIMLIST) {
            responseClaimList = ((ResponseClaimList) clsGson).getDetails();
            if (responseClaimList != null && responseClaimList.size() > 0) {
                setUpView();
            } else {
                setNoDataView();
            }
        }
    }

    @Override
    public void onResponseError(String errorMessage, int requestCode) {
        dismissDialog();
        setNoDataView();
        if (errorMessage != null && errorMessage.equals("Socket closed") || errorMessage.equals("Canceled")) {

        } else if (errorMessage != null && ((HomeActivity) getActivity()) != null)
            ((HomeActivity) getActivity()).showSnackBar(mBinding.getRoot(), errorMessage, Snackbar.LENGTH_SHORT);
    }

    private void setNoDataView() {
        mBinding.frgLlNoDataClaimlist.setVisibility(View.VISIBLE);
        mBinding.fragmentClaimsRv.setVisibility(View.GONE);
    }

    private void setDataView() {
        mBinding.frgLlNoDataClaimlist.setVisibility(View.GONE);
        mBinding.fragmentClaimsRv.setVisibility(View.VISIBLE);
    }

}
