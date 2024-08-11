package hrms.hrms.fragment.notification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hris365.R;
import com.hris365.databinding.FragmentNotificationBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import hrms.hrms.baseclass.BaseFragment;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;


/**
 * Created by Yudiz on 14/10/16.
 */
public class NotificationFragment extends BaseFragment implements View.OnClickListener {


    private FragmentNotificationBinding mBinding;
    private List<Calendar> calendars;
    private NotificationAdapter notificationAdapter;
    private Calendar calendar1, calendar2, calendar3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_notification, container, false);
        setUpEmp();
        return mBinding.getRoot();
    }

    private void setUpEmp() {
        calendars = new ArrayList<>();
        calendar1 = Calendar.getInstance(Locale.getDefault());
        calendar2 = Calendar.getInstance(Locale.getDefault());
        calendar3 = Calendar.getInstance(Locale.getDefault());
        calendars.add(calendar1);
        calendar2.add(Calendar.MONTH, -1);
        calendars.add(calendar2);
        calendar3.add(Calendar.YEAR, -1);
        calendars.add(calendar3);

        for (int i = 0; i < 10; i++) {
            Calendar calendar = Calendar.getInstance(Locale.getDefault());
            calendar.add(Calendar.MONTH, i);
            calendars.add(calendar);
        }


        Collections.sort(calendars, new Comparator<Calendar>() {
            @Override
            public int compare(Calendar calendar, Calendar t1) {
                return t1.compareTo(calendar);
            }
        });
        mBinding.fragmentNotificationRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        notificationAdapter = new NotificationAdapter(getActivity(), this, calendars, this);

        mBinding.fragmentNotificationRv.setAdapter(new ScaleInAnimationAdapter(notificationAdapter));


    }

    @Override
    public void onClick(View view) {


    }
}
