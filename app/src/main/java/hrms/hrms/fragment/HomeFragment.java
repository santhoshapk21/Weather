package hrms.hrms.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.hris365.R;
import com.hris365.databinding.FragmentHomeBinding;

import hrms.hrms.baseclass.BaseFragment;
import hrms.hris365.calendar.RobotoCalendarView;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Yudiz on 07/10/16.
 */
public class HomeFragment extends BaseFragment implements RobotoCalendarView.RobotoCalendarListener {

    private FragmentHomeBinding mBinding;
    private Calendar currentCalendar;
    private int currentMonthIndex;
    private Calendar test;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        setUpView();
        return mBinding.getRoot();
    }

    private void setUpView() {
        currentMonthIndex = 0;
        mBinding.robotCalnder.initializeCalendar(Calendar.getInstance());
        test = Calendar.getInstance();
        test.add(Calendar.DATE, 1);
        mBinding.robotCalnder.visibleShape(test, R.drawable.circle_holiday);
        test.add(Calendar.DATE, 2);
        mBinding.robotCalnder.visibleShape(test, R.drawable.circle_leave);
        mBinding.robotCalnder.setRobotoCalendarListener(this);
    }


    @Override
    public void onDateSelected(Date date) {
        mBinding.robotCalnder.markDayAsSelectedDay(date);
    }

    @Override
    public void onRightButtonClick() {
        currentMonthIndex++;
        updateCalendar();
    }

    @Override
    public void onLeftButtonClick() {
        currentMonthIndex--;
        updateCalendar();
    }


    private void updateCalendar() {
        currentCalendar = Calendar.getInstance(Locale.getDefault());
        currentCalendar.add(Calendar.MONTH, currentMonthIndex);
        mBinding.robotCalnder.initializeCalendar(currentCalendar);

    }
}
