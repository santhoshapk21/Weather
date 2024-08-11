package hrms.hrms.fragment.payslip;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hris365.R;
import hrms.hrms.baseclass.BaseFragment;
import hrms.hrms.widget.SpinnerAdapter;

import com.hris365.databinding.FragmentPayslipBinding;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;


/**
 * Created by Yudiz on 14/10/16.
 */
public class PaySlipFragment extends BaseFragment implements View.OnClickListener, Spinner.OnItemSelectedListener {


    private FragmentPayslipBinding mBinding;
    private List<Calendar> calendars;
    private PaySlipAdapter paySlipAdapter;
    private Calendar calendar1, calendar2, calendar3;
    private SpinnerAdapter yearAdapter, monthAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_payslip, container, false);
        setUpSpinner();
        setUpPaySlip();
        return mBinding.getRoot();
    }

    private void setUpSpinner() {
        String[] months = new String[13];
        int i = 0;
        months[i] = "MONTH";
        for (String month :
                new DateFormatSymbols().getMonths()) {
            i++;
            months[i] = month;
        }
        monthAdapter = new SpinnerAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, months);
        mBinding.fragmentPayslipSpnMonth.setAdapter(monthAdapter);
        ArrayList<String> year = new ArrayList<>();
        year.add("YEAR");
        for (int k = 0; k < 5; k++) {
            year.add((Calendar.getInstance().get(Calendar.YEAR) - k) + "");
        }
        yearAdapter = new SpinnerAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, year);
        mBinding.fragmentPayslipSpnYear.setAdapter(yearAdapter);
        mBinding.fragmentPayslipSpnYear.setOnItemSelectedListener(this);
        mBinding.fragmentPayslipSpnMonth.setOnItemSelectedListener(this);
    }

    private void setUpPaySlip() {

        calendar1 = Calendar.getInstance(Locale.getDefault());
        calendar2 = Calendar.getInstance(Locale.getDefault());
        calendar3 = Calendar.getInstance(Locale.getDefault());
        calendars = new ArrayList<>();
        calendars.add(calendar1);
        calendar2.add(Calendar.MONTH, -1);
        calendars.add(calendar2);
        calendar3.add(Calendar.YEAR, -1);
        calendars.add(calendar3);
        mBinding.fragmentPayslipRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        paySlipAdapter = new PaySlipAdapter(calendars, this);
        mBinding.fragmentPayslipRv.setAdapter( new ScaleInAnimationAdapter(paySlipAdapter));


    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



        if (!mBinding.fragmentPayslipSpnMonth.getSelectedItem().toString().equals("MONTH") && !mBinding.fragmentPayslipSpnYear.getSelectedItem().equals("YEAR")) {
            List<Calendar> tmpCalender = new ArrayList<>();
            for (int i = 0; i < calendars.size(); i++) {

                if (calendars.get(i).getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()).toLowerCase().equals(mBinding.fragmentPayslipSpnMonth.getSelectedItem().toString().toLowerCase())
                        && calendars.get(i).get(Calendar.YEAR) == Integer.parseInt(mBinding.fragmentPayslipSpnYear.getSelectedItem().toString())
                ) {
                    tmpCalender.add(calendars.get(i));

                }

            }
            paySlipAdapter = new PaySlipAdapter(tmpCalender, this);
            mBinding.fragmentPayslipRv.setAdapter( new ScaleInAnimationAdapter(paySlipAdapter));
        } else if (!mBinding.fragmentPayslipSpnMonth.getSelectedItem().toString().equals("MONTH")) {
            List<Calendar> tmpCalender = new ArrayList<>();
            for (int i = 0; i < calendars.size(); i++) {

                if (calendars.get(i).getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()).toLowerCase().equals(mBinding.fragmentPayslipSpnMonth.getSelectedItem().toString().toLowerCase())) {
                    tmpCalender.add(calendars.get(i));

                }


            }
            paySlipAdapter = new PaySlipAdapter(tmpCalender, this);
            mBinding.fragmentPayslipRv.setAdapter( new ScaleInAnimationAdapter(paySlipAdapter));
        } else if (!mBinding.fragmentPayslipSpnYear.getSelectedItem().equals("YEAR")) {
            List<Calendar> tmpCalender = new ArrayList<>();
            for (int i = 0; i < calendars.size(); i++) {

                if (calendars.get(i).get(Calendar.YEAR) == Integer.parseInt(mBinding.fragmentPayslipSpnYear.getSelectedItem().toString())
                ) {
                    tmpCalender.add(calendars.get(i));

                }


            }
            paySlipAdapter = new PaySlipAdapter(tmpCalender, this);
            mBinding.fragmentPayslipRv.setAdapter( new ScaleInAnimationAdapter(paySlipAdapter));

        } else {
            paySlipAdapter = new PaySlipAdapter(calendars, this);
            mBinding.fragmentPayslipRv.setAdapter( new ScaleInAnimationAdapter(paySlipAdapter));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
