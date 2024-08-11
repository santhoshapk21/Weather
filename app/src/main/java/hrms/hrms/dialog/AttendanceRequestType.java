package hrms.hrms.dialog;

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

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.hris365.R;
import com.hris365.databinding.DialogRequestTypeBinding;

/**
 * Created by Yudiz on 10/10/16.
 */
public class AttendanceRequestType extends DialogFragment implements View.OnClickListener {

    private DialogRequestTypeBinding mBinding;
    private boolean isValidCorrection;
    private View.OnClickListener onClickListener;
    private int selectedItem = -1;
    private CompoundButton.OnCheckedChangeListener compoundButton = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                if (buttonView == mBinding.cbCorrectionRequest) {
                    mBinding.cbCorrectionRequest.setChecked(true);
                    mBinding.cbRegularizeAttendance.setChecked(false);
                    selectedItem = 1;
                } else if (buttonView == mBinding.cbRegularizeAttendance) {
                    mBinding.cbRegularizeAttendance.setChecked(true);
                    mBinding.cbCorrectionRequest.setChecked(false);
                    selectedItem = 2;
                }
            }
        }
    };

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onClickListener = (View.OnClickListener) getTargetFragment();
        isValidCorrection = getArguments().getBoolean("isCorrectionValid");
        setCheckBox();
        setOnClickListner();
        mBinding.requestTypeIvClose.setOnClickListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_request_type, container, false);
        setStatusBarColorIfPossible(getResources().getColor(R.color.colorPrimary));
        return mBinding.getRoot();
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

    private void setCheckBox() {
        if (isValidCorrection) {
            mBinding.cbCorrectionRequest.setOnCheckedChangeListener(compoundButton);
            mBinding.tvCorrectionRequest.setOnClickListener(this);
        } else {
            mBinding.tvCorrectionRequest.setTextColor(getContext().getResources().getColor(R.color.login_tv_hint));
            mBinding.cbCorrectionRequest.setEnabled(false);
            mBinding.cbCorrectionRequest.getButtonDrawable().getCurrent().setColorFilter(Color.parseColor("#B9B9B9"), PorterDuff.Mode.ADD);
            mBinding.cbRegularizeAttendance.setChecked(true);
            selectedItem = 2;

        }
        mBinding.cbRegularizeAttendance.setOnCheckedChangeListener(compoundButton);
        mBinding.tvRegularizeAttendance.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == mBinding.requestTypeIvClose)
            dismiss();
        else if (view == mBinding.tvCorrectionRequest)
            mBinding.cbCorrectionRequest.setChecked(true);
        else if (view == mBinding.tvRegularizeAttendance)
            mBinding.cbRegularizeAttendance.setChecked(true);
    }

    public void setStatusBarColorIfPossible(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getDialog().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getDialog().getWindow().setStatusBarColor(color);
        }
    }
}
