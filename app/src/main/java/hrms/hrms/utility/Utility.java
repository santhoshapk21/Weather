package hrms.hrms.utility;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.DialogFragment;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import hrms.hrms.activity.HomeActivity;
import hrms.hrms.interfaces.OnDateDialogListener;

/**
 * Created by Yudiz on 11/10/16.
 */
public class Utility {

    private static DialogFragment dateDialogFragment;
    private static int todayDate;

    public static void showDateDialogNew(Activity context, int minDay, int minMonth,
                                         int minYear, int maxDay, int maxMonth, int maxYear,
                                         int selectedDay, int selectedMonth, int selectedYear,
                                         final android.app.DatePickerDialog.OnDateSetListener listener) {
        android.app.DatePickerDialog pickerDialog = new android.app.DatePickerDialog(context);
        // Set minDate
        Calendar minCalendar = Calendar.getInstance();
        minCalendar.set(minYear, minMonth, minDay);
        pickerDialog.getDatePicker().setMinDate(minCalendar.getTimeInMillis());

        // Set maxDate
        Calendar maxCalendar = Calendar.getInstance();
        maxCalendar.set(maxYear, maxMonth, maxDay);
        pickerDialog.getDatePicker().setMaxDate(maxCalendar.getTimeInMillis());
        // Set selectedDate
        pickerDialog.getDatePicker().updateDate(
                selectedYear,
                selectedMonth,
                selectedDay);
        pickerDialog.setOnDateSetListener(listener);
        pickerDialog.show();
    }

    public static void showDateDialog(FragmentManager fm, int minDay, int minMonth, int minYear, int maxDay, int maxMonth, int maxYear, final OnDateDialogListener listener, int selectedYear, int selectedMonth, int selectedDay) {
        if (dateDialogFragment == null || !dateDialogFragment.isVisible()) {
            DatePickerDialog.Builder builder = new DatePickerDialog.Builder(minDay, minMonth, minYear, maxDay, maxMonth, maxYear, selectedDay, selectedMonth, selectedYear) {
                @Override
                public void onPositiveActionClicked(DialogFragment fragment) {
                    DatePickerDialog dialog = (DatePickerDialog) fragment.getDialog();
                    listener.onPositiveActionClicked(dialog);
                    super.onPositiveActionClicked(fragment);
                }

                @Override
                public void onNegativeActionClicked(DialogFragment fragment) {
                    super.onNegativeActionClicked(fragment);
                }
            };
            builder.positiveAction("OK").negativeAction("CANCEL");
            dateDialogFragment = DialogFragment.newInstance(builder);
            dateDialogFragment.show(fm, "datePicker");
        }
    }

    public static Calendar getFormatedDate(String dateString, String dateFormat) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        try {
            calendar.setTime(sdf.parse(dateString));
            return calendar;

        } catch (Exception e) {
//            e.printStackTrace();
            return calendar;
        }
    }

    public static String getTodayDate() {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT1);
        return sdf.format(calendar.getTime());
    }

    public static String getTodayDateinFormat2() {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
        return sdf.format(calendar.getTime());
    }

    public static void log(String msg) {
        Log.i("HRMS", msg);
    }

    public static void toast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void loadImage(Context context, String path, ImageView imageView) {
        if (!TextUtils.isEmpty(path)) {
            Picasso.get().load(path).into(imageView);
        }
    }

    public static String getFormattedDateTimeString(String date, String oldFormat, String newDateFormat) {
        return getFormatDate(oldFormat, newDateFormat, getLocalTimeFromUTC(date, HomeActivity.serverFormat));
    }

    /*new method to convert timezone from severtimezone to localtimezone provided by serverside */
    public static String getLocalTimeFromUTC(String utcTime, String dateFormat) {
        if (Constants.SERVER_TIMEZONE.equals(Constants.LOCAL_TIMEZONE)) {
            return utcTime;
        }
        try {
            SimpleDateFormat utcformat = new SimpleDateFormat(dateFormat, Locale.getDefault());
            utcformat.setTimeZone(TimeZone.getTimeZone(Constants.SERVER_TIMEZONE));
            Date date = utcformat.parse(utcTime);
            TimeZone defaultTimezone = TimeZone.getTimeZone(Constants.LOCAL_TIMEZONE);
            utcformat.setTimeZone(defaultTimezone);
            return utcformat.format(date);
        } catch (Exception e) {
            Log.d("UTCTOLOCALEXCEPTION", e.getMessage());
            return "00:00";
        }
    }

    public static String getFormatDate(String oldFormat, String newFormat, String currentDate) {
        String outputDateStr = "";
        if (!TextUtils.isEmpty(currentDate)) {
            DateFormat inputFormat = new SimpleDateFormat(oldFormat, Locale.getDefault());
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


}
