package hrms.hrms.baseclass;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Yudiz on 12/10/16.
 */
public class BaseFragment extends Fragment {

    private static String outputDateStr;
    BaseAppCompactActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (BaseAppCompactActivity) getActivity();
    }

    protected void log(String msg) {
        activity.log(msg);
    }

    protected void toast(String msg) {
        activity.toast(msg);
    }

    protected void showDialog() {
        activity.showDialog();
    }

    protected void dismissDialog() {
        activity.dismissDialog();
    }

    public void addFragment(Fragment fragment, String title) {
        activity.addFragment(fragment, title);
    }

    public static String getFormatDate(String oldFormat, String newFormat, String currentDate) {
        if (!TextUtils.isEmpty(currentDate)) {
            DateFormat inputFormat = new SimpleDateFormat(oldFormat,Locale.getDefault());
//        inputFormat.setTimeZone(TimeZone.getTimeZone(ServerTimeZone));
            SimpleDateFormat outputFormat = new SimpleDateFormat(newFormat, Locale.getDefault());

            DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();
            dateFormatSymbols.setAmPmStrings(new String[]{"AM", "PM"});
            outputFormat.setDateFormatSymbols(dateFormatSymbols);

            String inputDateStr = currentDate;
            Date date = null;
            try {
                date = inputFormat.parse(inputDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (date != null) {
                outputDateStr = outputFormat.format(date);
            }
        } else {
            outputDateStr = currentDate;
        }
        return outputDateStr;
    }
    public String getFormattedDate(Calendar cal) {
        String FromDate = cal.getTime().toString();
        try {
            FromDate = getFormatDate(
                    "dd-MM-yyyy",
                    "dd MMM yyyy",
                    String.format("%02d", cal.get(Calendar.DAY_OF_MONTH))
                            + "-" + String.format("%02d", ((cal.get(Calendar.MONTH) + 1))) + "-"
                            + cal.get(Calendar.YEAR));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (FromDate == null) ? "" : FromDate;
    }
    public Calendar getFormatDate(String oldFormat, String newFormat, String currentDate, String timeZone) {
        DateFormat inputFormat = new SimpleDateFormat(oldFormat);
//        inputFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        SimpleDateFormat outputFormat = new SimpleDateFormat(newFormat, Locale.getDefault());

        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();
        dateFormatSymbols.setAmPmStrings(new String[]{"AM", "PM"});
        outputFormat.setDateFormatSymbols(dateFormatSymbols);

        String inputDateStr = currentDate;
        Date date = null;
        try {
            date = inputFormat.parse(inputDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTime(date);
        return calendar;
    }

    public void hideSoftKeyboard(View view) {
        if (view != null && view.getContext() != null) {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /*  old method to change time from UTC to LOCAL mobile timezone
        public String getLocalTimeFromUTC(String utcTime, String dateFormat) {
            try {
                SimpleDateFormat utcformat = new SimpleDateFormat(dateFormat, Locale.getDefault());
                utcformat.setTimeZone(TimeZone.getTimeZone("GMT"));
                Date date = utcformat.parse(utcTime);
                TimeZone defaultTimezone = TimeZone.getDefault();
                utcformat.setTimeZone(defaultTimezone);
                return utcformat.format(date);
            } catch (Exception e) {
                Log.d("UTCTOLOCALEXCEPTION", e.getMessage());
                return "00:00";
            }
        }*/


    public String getFullName() {
        if (activity != null)
            return activity.getString(BaseAppCompactActivity.TYPE.USERFULLNAME);

        return "";
    }


    public String getFormattedDateTimeFromUtc(String oldFormat, String newformat, String datetime) {
        SimpleDateFormat inputFormat = new SimpleDateFormat(oldFormat);
        inputFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        SimpleDateFormat outputFormat = new SimpleDateFormat(newformat, Locale.getDefault());

        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();
        dateFormatSymbols.setAmPmStrings(new String[]{"AM", "PM"});
        outputFormat.setDateFormatSymbols(dateFormatSymbols);
        TimeZone defaultTimezone = TimeZone.getDefault();
        outputFormat.setTimeZone(defaultTimezone);
        String inputDateStr = datetime;
        Date date = null;
        try {
            date = inputFormat.parse(inputDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null) {
            outputDateStr = outputFormat.format(date);
        }
        return outputDateStr;


    }


}
