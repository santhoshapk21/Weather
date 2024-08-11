package hrms.hrms.fragment.shift_change;

import static hrms.hrms.baseclass.BaseAppCompactActivity.isInternet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.hris365.R;
import com.hris365.databinding.FragmentShiftChangeBinding;

import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;
import hrms.hris365.pickerview.popwindow.DatePickerPopYear;
import hrms.hrms.activity.HomeActivity;
import hrms.hrms.adapter.shiftChange.ShiftChangeAdapter;
import hrms.hrms.baseclass.BaseAppCompactActivity;
import hrms.hrms.baseclass.BaseFragment;
import hrms.hrms.fragment.DashBoaredFragment;
import hrms.hrms.retrofit.OnApiResponseListner;
import hrms.hrms.retrofit.model.shiftChange.ResponseShiftChange;

/**
 * Created by Yudiz on 14/10/16.
 */
public class ShiftChangeFragment extends BaseFragment
        implements View.OnClickListener, OnApiResponseListner {


    private FragmentShiftChangeBinding mBinding;
    private List<ResponseShiftChange> responseShiftChange;
    private ShiftChangeAdapter shiftChangeAdapter;
    private Calendar calendar;
    public static final String shiftChange = "ShiftChange";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_shift_change, container, false);
        ButterKnife.bind(this, mBinding.getRoot());
        mBinding.frgShiftChangeLlFilterMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerPopYear pickerPopWin = new DatePickerPopYear.Builder(getContext(), new DatePickerPopYear.OnDatePickedListener() {
                    @Override
                    public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
                        calendar.set(Calendar.YEAR, year);
                        mBinding.frgShiftChangeTvFilterMonth.setText(year + "");
                        doGetShiftChangeApi(calendar.get(Calendar.YEAR));
                    }
                }).textConfirm(getResources().getString(R.string.str_confirm)) //text of confirm button
                        .colorConfirm(getContext().getResources().getColor(R.color.black))
                        .textCancel(getResources().getString(R.string.str_cancel)) //text of cancel button
                        .minYear(calendar.get(Calendar.YEAR) - 100) //min year in loop
                        .maxYear(calendar.get(Calendar.YEAR) + 100) // max year in loop
                        .showDayMonthYear(true) // shows like dd mm yyyy (default is false)
                        .dateChose(calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH)) // date chose when init popwindow
                        .build();

                pickerPopWin.showPopWin(getActivity());
            }
        });
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (isInternet)
            doGetShiftChangeApi(Calendar.getInstance().get(Calendar.YEAR));
        else
            ((HomeActivity) getActivity()).showSnackBar(
                    mBinding.getRoot(),
                    getString(R.string.nointernet),
                    Snackbar.LENGTH_SHORT
            );
        init();
    }

    private void init() {
        calendar = Calendar.getInstance();
        mBinding.frgShiftChangeTvFilterMonth.setText(calendar.get(Calendar.YEAR) + "");
    }

    @Override
    public void onClick(View view) {
        if (view != null && view.getTag() != null) {

            EditShiftChangeFragment editShiftChangeFragment = new EditShiftChangeFragment();
            ResponseShiftChange shiftChangemodel = responseShiftChange.get((int) view.getTag());
            Bundle bundle = new Bundle();
            bundle.putSerializable(shiftChange, shiftChangemodel);
            editShiftChangeFragment.setArguments(bundle);

            addFragment(
                    editShiftChangeFragment,
                    getString(R.string.edit_shift_change));
        }
    }

    private void doGetShiftChangeApi(int year) {
        showDialog();
        ((HomeActivity) getActivity()).getApiTask().doGetShiftChangeList(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
                year,
                this
        );
    }

    @Override
    public void onResponseComplete(Object clsGson, int requestCode, int responseCode) {
        dismissDialog();
        responseShiftChange = (List<ResponseShiftChange>) clsGson;
        if (responseShiftChange != null) {
            if (responseShiftChange == null || responseShiftChange.size() == 0)
                showNoDataFoundView();
            else
                setAdapter();
        } else {
            showNoDataFoundView();
        }
    }

    private void setAdapter() {
        showDataFoundView();
        shiftChangeAdapter = new ShiftChangeAdapter(getContext(), responseShiftChange, this);
        mBinding.fragmentShiftChangeRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.fragmentShiftChangeRv.setAdapter(shiftChangeAdapter);
    }

    @Override
    public void onResponseError(String errorMessage, int requestCode) {
        dismissDialog();
        if (errorMessage != null)
            ((HomeActivity) getActivity()).showSnackBar(
                    mBinding.getRoot(),
                    errorMessage + "",
                    Snackbar.LENGTH_SHORT);
    }

    private void showNoDataFoundView() {
        mBinding.fragmentShiftChangeRv.setVisibility(View.GONE);
        mBinding.grgShiftChangeTvNoData.setVisibility(View.VISIBLE);
    }

    private void showDataFoundView() {
        mBinding.fragmentShiftChangeRv.setVisibility(View.VISIBLE);
        mBinding.grgShiftChangeTvNoData.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.fl_container);
        if (currentFragment instanceof DashBoaredFragment) {
            ((DashBoaredFragment) currentFragment).requestCountApi();
            ((DashBoaredFragment) currentFragment).approvalCountApi();
        }
    }
}
