package hrms.hrms.fragment.claims;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.hris365.R;
import com.hris365.databinding.FragmentClaimRequestTypeBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClaimRequestType extends DialogFragment implements View.OnClickListener {
    public static int TRAVELSELECTED = 1;
    public static int OTHERSELECTED = 2;

    private FragmentClaimRequestTypeBinding mBinding;
    private int selectedItem = -1;
    private View.OnClickListener onClickListener;
    private boolean isValidCorrection;
    private CompoundButton.OnCheckedChangeListener compoundButton = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                if (buttonView == mBinding.rbTravelClaim) {
                    mBinding.rbTravelClaim.setChecked(true);
                    mBinding.rbOtherClaim.setChecked(false);
                    selectedItem = TRAVELSELECTED;
                } else if (buttonView == mBinding.rbOtherClaim) {
                    mBinding.rbOtherClaim.setChecked(true);
                    mBinding.rbTravelClaim.setChecked(false);
                    selectedItem = OTHERSELECTED;
                }
            }
        }
    };

    public ClaimRequestType() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_claim_request_type, container, false);
        setStatusBarColorIfPossible(getResources().getColor(R.color.colorPrimary));
        return mBinding.getRoot();// Inflate the layout for this fragment
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onClickListener = (View.OnClickListener) getTargetFragment();
        isValidCorrection = true;
        setCheckBox();
        setOnClickListner();
        mBinding.requestTypeIvClose.setOnClickListener(this);
    }

    private void setOnClickListner() {
        mBinding.dialogTvOkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedItem == -1) {
                    Toast.makeText(getContext(), "Please select valid option.", Toast.LENGTH_SHORT).show();
                } else {
                    v.setTag(selectedItem);
                    onClickListener.onClick(v);
                    dismiss();
                }
            }
        });
    }

    private void setCheckBox() {
        if (isValidCorrection) {
            mBinding.rbTravelClaim.setOnCheckedChangeListener(compoundButton);
            mBinding.tvCorrectionRequest.setOnClickListener(this);
        } else {
            mBinding.tvCorrectionRequest.setTextColor(getContext().getResources().getColor(R.color.login_tv_hint));
            mBinding.rbTravelClaim.setEnabled(false);
            mBinding.rbTravelClaim.getButtonDrawable().getCurrent().setColorFilter(Color.parseColor("#B9B9B9"), PorterDuff.Mode.ADD);
            mBinding.rbOtherClaim.setChecked(true);
            selectedItem = 2;

        }
        mBinding.rbOtherClaim.setOnCheckedChangeListener(compoundButton);
        mBinding.tvRegularizeAttendance.setOnClickListener(this);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        Dialog dialog = new Dialog(getActivity(), R.style.Transparent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation_style;
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return dialog;
    }

    @Override
    public void onClick(View view) {
        if (view == mBinding.requestTypeIvClose)
            dismiss();
        else if (view == mBinding.tvCorrectionRequest)
            mBinding.rbTravelClaim.setChecked(true);
        else if (view == mBinding.tvRegularizeAttendance)
            mBinding.rbOtherClaim.setChecked(true);
    }

    public void setStatusBarColorIfPossible(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getDialog().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getDialog().getWindow().setStatusBarColor(color);
        }
    }
}
