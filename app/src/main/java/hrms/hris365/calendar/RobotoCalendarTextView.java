/*
 * Copyright (C) 2015 Marco Hernaiz Cao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package hrms.hris365.calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hris365.R;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * The roboto calendar view
 *
 * @author Marco Hernaiz Cao
 */
public class RobotoCalendarTextView extends LinearLayout {

    // ************************************************************************************************************************************************************************
    // * Attributes
    // ************************************************************************************************************************************************************************

    public static final int RED_COLOR = R.color.roboto_calendar_red;
    public static final int GREEN_COLOR = R.color.roboto_calendar_green;
    public static final int BLUE_COLOR = R.color.roboto_calendar_blue;
    private static final String DAY_OF_WEEK = "dayOfWeek";
    private static final String DAY_OF_MONTH_TEXT = "dayOfMonthText";
    private static final String DAY_OF_MONTH_BACKGROUND = "dayOfMonthBackground";
    private static final String DAY_OF_MONTH_CONTAINER = "dayOfMonthContainer";
    private static final String FIRST_UNDERLINE = "firstUnderlineView";
    private static final String SECOND_UNDERLINE = "secondUnderlineView";
    private static final String NEXT = "nxt";
    private static final String PREV = "prev";
    TextView next, prv;
    // View
    private Context context;
    private TextView dateTitle, dateTitleFull;
    private ImageView leftButton;
    private ImageView rightButton;
    private View view;
    // Class
    private RobotoCalendarListener robotoCalendarListener;
    private Calendar currentCalendar;
    private Locale locale;
    private Date lastCurrentDay;
    private Date lastSelectedDay;
    private ArrayList<String> weekOfDay;
    private Date lastSelectedDate = null;
    // ************************************************************************************************************************************************************************
    // * Initialization methods
    // ************************************************************************************************************************************************************************
    private OnClickListener onDayOfMonthClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            // Extract day selected
            ViewGroup dayOfMonthContainer = (ViewGroup) view;
            String tagId = (String) dayOfMonthContainer.getTag();
            tagId = tagId.substring(DAY_OF_MONTH_CONTAINER.length(), tagId.length());
            TextView dayOfMonthText = (TextView) view.findViewWithTag(DAY_OF_MONTH_TEXT + tagId);

            // Fire event
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentCalendar.getTime());
            calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(dayOfMonthText.getText().toString()));

            if (robotoCalendarListener == null) {
                throw new IllegalStateException("You must assing a valid RobotoCalendarListener first!");
            } else {
                robotoCalendarListener.onDateSelected(calendar.getTime());
            }
        }
    };

    public RobotoCalendarTextView(Context context) {
        super(context);
        this.context = context;
        onCreateView();
    }

    public RobotoCalendarTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        if (isInEditMode()) {
            return;
        }
        onCreateView();
    }

    public Date getLastSelectedDate() {
        return lastSelectedDate;
    }

    public void setLastSelectedDate(Date lastSelectedDate) {
        this.lastSelectedDate = lastSelectedDate;
    }

    public View onCreateView() {

        LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflate.inflate(R.layout.roboto_calendar_picker_layout_text, this, true);

        findViewsById(view);

        initializeEventListeners();

        initializeComponentBehavior();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        return view;
    }

    private void findViewsById(View view) {
        next = (TextView) view.findViewWithTag(NEXT);
        prv = (TextView) view.findViewWithTag(PREV);
        leftButton = (ImageView) view.findViewById(R.id.leftButton);
        rightButton = (ImageView) view.findViewById(R.id.rightButton);
        dateTitle = (TextView) view.findViewById(R.id.dateTitle);
        dateTitleFull = (TextView) view.findViewById(R.id.fullDateTitle);
    }

    private void initializeEventListeners() {
        next.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                robotoCalendarListener.onRightButtonClick();
            }
        });

        prv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                robotoCalendarListener.onLeftButtonClick();
            }
        });

        leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (robotoCalendarListener == null) {
                    throw new IllegalStateException("You must assign a valid RobotoCalendarListener first!");
                }
                robotoCalendarListener.onLeftButtonClick();
            }
        });

        rightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (robotoCalendarListener == null) {
                    throw new IllegalStateException("You must assign a valid RobotoCalendarListener first!");
                }
                robotoCalendarListener.onRightButtonClick();
            }
        });

        dateTitleFull.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                robotoCalendarListener.onTitleClick();
            }
        });
    }

    // ************************************************************************************************************************************************************************
    // * Initialization UI methods
    // ************************************************************************************************************************************************************************

    private void initializeComponentBehavior() {
        // Initialize calendar for current month
        Locale locale = context.getResources().getConfiguration().locale;
        Calendar currentCalendar = Calendar.getInstance(locale);
        initializeCalendar(currentCalendar);
    }

    @SuppressLint("DefaultLocale")
    private void initializeTitleLayout(Calendar currentCalendarDate) {

        if (currentCalendarDate == null) {
            String datePrevText = "";
            String dateNextText = "";

            String dateText = new DateFormatSymbols(locale).getShortMonths()[currentCalendar.get(Calendar.MONTH)];
            if (currentCalendar.get(Calendar.MONTH) != 0) {
                datePrevText = new DateFormatSymbols(locale).getShortMonths()[currentCalendar.get(Calendar.MONTH) - 1];
                if (currentCalendar.get(Calendar.MONTH) == 11) {
                    dateNextText = new DateFormatSymbols(locale).getShortMonths()[0] + " " + (currentCalendar.get(Calendar.YEAR) + 1);
                } else
                    dateNextText = new DateFormatSymbols(locale).getShortMonths()[currentCalendar.get(Calendar.MONTH) + 1];
            } else {
                datePrevText = new DateFormatSymbols(locale).getShortMonths()[11] + " " + (currentCalendar.get(Calendar.YEAR) - 1);
                dateNextText = new DateFormatSymbols(locale).getShortMonths()[0];
            }

            next.setText(dateNextText);
            prv.setText(datePrevText);
            String weekText = new DateFormatSymbols(locale).getWeekdays()[currentCalendar.get(Calendar.DAY_OF_WEEK)];
//            dateText = dateText.substring(0, 1).toUpperCase() + dateText.subSequence(1, dateText.length());
            if (Calendar.getInstance().get(Calendar.MONTH) == currentCalendar.get(Calendar.MONTH) && Calendar.getInstance().get(Calendar.YEAR) == currentCalendar.get(Calendar.YEAR))
                dateTitleFull.setText(String.format("%s %s %s", currentCalendar.get(Calendar.DAY_OF_MONTH), weekText, currentCalendar.get(Calendar.YEAR), weekText));
            else
                dateTitleFull.setText(String.format("%s", currentCalendar.get(Calendar.YEAR)));
            dateTitle.setText(currentCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));
        } else {
            String dateText = new DateFormatSymbols(locale).getShortMonths()[currentCalendarDate.get(Calendar.MONTH)];
            String weekText = new DateFormatSymbols(locale).getWeekdays()[currentCalendarDate.get(Calendar.DAY_OF_WEEK)];
//            dateText = dateText.substring(0, 1).toUpperCase() + dateText.subSequence(1, dateText.length());

            dateTitleFull.setText(String.format("%s %s %s", currentCalendarDate.get(Calendar.DAY_OF_MONTH), weekText, currentCalendarDate.get(Calendar.YEAR), weekText));
            dateTitle.setText(currentCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));

            if (dateTitle.getText().equals("January")) {
                next.setText("Feb");
            }
        }

        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
            String date = format.format(cal.getTime()) + "";
            dateTitleFull.setText(date + "");
        } catch (Exception e) {
        }
    }

    @SuppressLint("DefaultLocale")
    private void initializeWeekDaysLayout() {

        TextView dayOfWeek;
        String dayOfTheWeekString;
        String[] weekDaysArray = new DateFormatSymbols(locale).getWeekdays();
        for (int i = 1; i < weekDaysArray.length; i++) {
            dayOfWeek = (TextView) view.findViewWithTag(DAY_OF_WEEK + getWeekIndex(i, currentCalendar));
            dayOfTheWeekString = weekDaysArray[i];
            dayOfTheWeekString = checkSpecificLocales(dayOfTheWeekString, i);

            dayOfWeek.setText(dayOfTheWeekString);
        }
    }

    @SuppressLint("DefaultLocale")
    private String checkSpecificLocales(String dayOfTheWeekString, int i) {
        // Set Wednesday as "X" in Spanish locale
        if (i == 4 && locale.getCountry().equals("ES")) {
            dayOfTheWeekString = "X";
        } else {
            dayOfTheWeekString = dayOfTheWeekString.substring(0, 3).toUpperCase();
        }
        return dayOfTheWeekString;
    }

    private void initializeDaysOfMonthLayout() {

        TextView dayOfMonthText;
        TextView firstUnderline;
        View secondUnderline;
        ViewGroup dayOfMonthContainer;
        ViewGroup dayOfMonthBackground;
        ArrayList<String> daysOfWeek = new ArrayList<>();

        for (int i = 1; i < 43; i++) {

            dayOfMonthContainer = (ViewGroup) view.findViewWithTag(DAY_OF_MONTH_CONTAINER + i);
            dayOfMonthBackground = (ViewGroup) view.findViewWithTag(DAY_OF_MONTH_BACKGROUND + i);
            dayOfMonthText = (TextView) view.findViewWithTag(DAY_OF_MONTH_TEXT + i);
            firstUnderline = (TextView) view.findViewWithTag(FIRST_UNDERLINE + i);
            firstUnderline.setTextColor(Color.TRANSPARENT);

//            if (daysOfWeek.contains(dayOfMonthText.getText().toString()))
//                dayOfMonthText.setTextColor(Color.parseColor("#d7d7d7"));
//            else
//                dayOfMonthText.setTextColor(Color.BLACK);


//            secondUnderline = view.findViewWithTag(SECOND_UNDERLINE + i);

            dayOfMonthText.setVisibility(View.INVISIBLE);
            firstUnderline.setVisibility(View.VISIBLE);
//            secondUnderline.setVisibility(View.GONE);

            // Apply styles
            dayOfMonthText.setBackgroundResource(android.R.color.transparent);
            dayOfMonthContainer.setBackgroundResource(android.R.color.transparent);
            dayOfMonthContainer.setOnClickListener(null);
            dayOfMonthBackground.setBackgroundResource(android.R.color.transparent);
        }
    }

    public void countWeekendDays() {
        Calendar calendar = Calendar.getInstance();
        // Note that month is 0-based in calendar, bizarrely.
        calendar.setTime(currentCalendar.getTime());
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int day = 1; day <= daysInMonth; day++) {
            calendar.set(Calendar.DAY_OF_MONTH, day);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == 1) {
                weekOfDay.add(day + "");
                // Or do whatever you need to with the result.
            }
        }
    }

    public void visibleLine(Calendar calendar) {
        TextView firstUnderline;
        Calendar current = Calendar.getInstance();
        Calendar auxCalendar = Calendar.getInstance(locale);
        auxCalendar.setTime(currentCalendar.getTime());
        auxCalendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfMonth = auxCalendar.get(Calendar.DAY_OF_WEEK);
        TextView dayOfMonthText;
        ViewGroup dayOfMonthContainer;

        // Calculate dayOfMonthIndex
        int dayOfMonthIndex = getWeekIndex(firstDayOfMonth, auxCalendar);

        for (int i = 1; i <= auxCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++, dayOfMonthIndex++) {
            dayOfMonthContainer = (ViewGroup) view.findViewWithTag(DAY_OF_MONTH_CONTAINER + dayOfMonthIndex);
            dayOfMonthText = (TextView) view.findViewWithTag(DAY_OF_MONTH_TEXT + dayOfMonthIndex);
            firstUnderline = (TextView) view.findViewWithTag(FIRST_UNDERLINE + dayOfMonthIndex);
            if (dayOfMonthText == null) {
                break;
            }
            try {
                if (calendar.get(Calendar.DAY_OF_MONTH) == Integer.parseInt(dayOfMonthText.getText().toString()))
                    firstUnderline.setVisibility(VISIBLE);
            } catch (Exception a) {
                a.printStackTrace();
            }
        }

    }

    public void setDaysInCalendar() {

        Calendar auxCalendar = Calendar.getInstance(locale);
        auxCalendar.setTime(currentCalendar.getTime());
        auxCalendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfMonth = auxCalendar.get(Calendar.DAY_OF_WEEK);
        TextView dayOfMonthText;
        ViewGroup dayOfMonthContainer;

        // Calculate dayOfMonthIndex
        int dayOfMonthIndex = getWeekIndex(firstDayOfMonth, auxCalendar);

        for (int i = 1; i <= auxCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++, dayOfMonthIndex++) {
            dayOfMonthContainer = (ViewGroup) view.findViewWithTag(DAY_OF_MONTH_CONTAINER + dayOfMonthIndex);
            dayOfMonthText = (TextView) view.findViewWithTag(DAY_OF_MONTH_TEXT + dayOfMonthIndex);
            if (dayOfMonthText == null) {
                break;
            }

            dayOfMonthContainer.setOnClickListener(onDayOfMonthClickListener);
            dayOfMonthText.setVisibility(View.VISIBLE);

            dayOfMonthText.setText(String.valueOf(i));

            if (weekOfDay.contains(String.valueOf(i))) {
                dayOfMonthText.setTextColor(getContext().getResources().getColor(R.color.holiday_text_color));
            } else {
                dayOfMonthText.setTextColor(getContext().getResources().getColor(R.color.text_color));
            }
        }

        // If the last week row has no visible days, hide it or show it in case
        ViewGroup weekRow = (ViewGroup) view.findViewWithTag("weekRow6");
        dayOfMonthText = (TextView) view.findViewWithTag("dayOfMonthText36");
        if (dayOfMonthText.getVisibility() == INVISIBLE) {
            weekRow.setVisibility(GONE);
        } else {
            weekRow.setVisibility(VISIBLE);
        }
    }

    // ************************************************************************************************************************************************************************
    // * Getter methods
    // ************************************************************************************************************************************************************************

    private void clearDayOfTheMonthStyle(Date currentDate) {

        if (currentDate != null) {
            Calendar calendar = getCurrentCalendar();
            calendar.setTime(currentDate);
            ViewGroup dayOfMonthBackground = getDayOfMonthBackground(calendar);
            dayOfMonthBackground.setBackgroundResource(android.R.color.transparent);

            TextView dayOfMonthText = (TextView) view.findViewWithTag(DAY_OF_MONTH_TEXT + currentDate.getDay());
            dayOfMonthText.setBackgroundResource(android.R.color.transparent);

            if (weekOfDay.contains(currentDate.getDay()))
                getDayOfMonthText(calendar).setTextColor(getResources().getColor(R.color.holiday_text_color));
            else
                getDayOfMonthText(calendar).setTextColor(getResources().getColor(R.color.text_color));
        }
    }

    private ViewGroup getDayOfMonthBackground(Calendar currentCalendar) {
        return (ViewGroup) getView(DAY_OF_MONTH_BACKGROUND, currentCalendar);
    }

    private TextView getDayOfMonthText(Calendar currentCalendar) {
        return (TextView) getView(DAY_OF_MONTH_TEXT, currentCalendar);
    }


//    private View getFirstUnderline(Calendar currentCalendar) {
//        return getView(FIRST_UNDERLINE, currentCalendar);
//    }

//    private View getSecondUnderline(Calendar currentCalendar) {
//        return getView(SECOND_UNDERLINE, currentCalendar);
//    }

    public void visibleFont(Calendar calendar, String text, int resource) {

        Calendar auxCalendar = Calendar.getInstance(locale);
        auxCalendar.setTime(currentCalendar.getTime());
        auxCalendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfMonth = auxCalendar.get(Calendar.DAY_OF_WEEK);
        TextView dayOfMonthText;
        TextView firstUnderline;

        // Calculate dayOfMonthIndex
        int dayOfMonthIndex = getWeekIndex(firstDayOfMonth, auxCalendar);

        for (int i = 1; i <= auxCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++, dayOfMonthIndex++) {

            dayOfMonthText = (TextView) view.findViewWithTag(DAY_OF_MONTH_TEXT + dayOfMonthIndex);
            firstUnderline = (TextView) view.findViewWithTag(FIRST_UNDERLINE + dayOfMonthIndex);
            if (dayOfMonthText == null) {
                break;
            }
            try {
                if (calendar.get(Calendar.DAY_OF_MONTH) == Integer.parseInt(dayOfMonthText.getText().toString())) {
                    firstUnderline.setVisibility(VISIBLE);
                    firstUnderline.setText(text);
                    firstUnderline.setTextColor(resource);
                }
            } catch (Exception a) {
                a.printStackTrace();
            }
        }

    }

    private int getDayIndexByDate(Calendar currentCalendar) {
        int monthOffset = getMonthOffset(currentCalendar);
        int currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH);
        return currentDay + monthOffset;
    }

    private int getMonthOffset(Calendar currentCalendar) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentCalendar.getTime());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayWeekPosition = calendar.getFirstDayOfWeek();
        int dayPosition = calendar.get(Calendar.DAY_OF_WEEK);

        if (firstDayWeekPosition == 1) {
            return dayPosition - 1;
        } else {

            if (dayPosition == 1) {
                return 6;
            } else {
                return dayPosition - 2;
            }
        }
    }

    private int getWeekIndex(int weekIndex, Calendar currentCalendar) {
        int firstDayWeekPosition = currentCalendar.getFirstDayOfWeek();

        if (firstDayWeekPosition == 1) {
            return weekIndex;
        } else {

            if (weekIndex == 1) {
                return 7;
            } else {
                return weekIndex - 1;
            }
        }
    }

    private View getView(String key, Calendar currentCalendar) {
        int index = getDayIndexByDate(currentCalendar);
        return view.findViewWithTag(key + index);
    }

    // ************************************************************************************************************************************************************************
    // * Public calendar methods
    // ************************************************************************************************************************************************************************

    private Calendar getCurrentCalendar() {
        return Calendar.getInstance(context.getResources().getConfiguration().locale);
    }

    @SuppressLint("DefaultLocale")
    public void initializeCalendar(Calendar currentCalendar) {

        this.currentCalendar = currentCalendar;
        weekOfDay = new ArrayList<>();
        weekOfDay.clear();
        lastSelectedDay = null;
//        countWeekendDays();

        locale = context.getResources().getConfiguration().locale;

        // Set date title
        initializeTitleLayout(null);

        // Set weeks days titles
        initializeWeekDaysLayout();

        // Initialize days of the month
        initializeDaysOfMonthLayout();

        // Set days in calendar
        setDaysInCalendar();
    }

    public void markDayAsCurrentDay(Date currentDate) {
        if (currentDate != null) {
            lastCurrentDay = currentDate;
            Calendar currentCalendar = getCurrentCalendar();
            currentCalendar.setTime(currentDate);
            TextView dayOfMonth = getDayOfMonthText(currentCalendar);
            dayOfMonth.setTextColor(context.getResources().getColor(R.color.text_color));
            dayOfMonth.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_current_date_gray));
        }
    }

    public void removeMarkDayAsCurrentDay(Date currentDate) {
        if (currentDate != null) {
            lastCurrentDay = currentDate;
            Calendar currentCalendar = getCurrentCalendar();
            currentCalendar.setTime(currentDate);
            TextView dayOfMonth = getDayOfMonthText(currentCalendar);

            dayOfMonth.setTextColor(context.getResources().getColor(R.color.roboto_calendar_day_of_month));

        }

    }

    public void markDayAsSelectedDay(Date currentDate) {

        // Initialize attributes
        Calendar currentCalendar = getCurrentCalendar();
        currentCalendar.setTime(currentDate);

//        if (Calendar.getInstance().get(Calendar.MONTH) == currentCalendar.get(Calendar.MONTH) && Calendar.getInstance().get(Calendar.YEAR) == currentCalendar.get(Calendar.YEAR))
//            markDayAsCurrentDay(lastCurrentDay);

        if (lastSelectedDay != null)
            clearBackgroundDate(lastSelectedDay);

        // Store current values as last values
        storeLastValues(currentDate);

        // Mark current day as selected
        ViewGroup dayOfMonthBackground = getDayOfMonthBackground(currentCalendar);

        if (currentCalendar.get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                && currentCalendar.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH)
                && currentCalendar.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR)) {
            getDayOfMonthText(currentCalendar).setBackgroundResource(R.drawable.circle_current_date_gray);
        } else {
            getDayOfMonthText(currentCalendar).setBackgroundResource(R.drawable.circle);
        }

        initializeTitleLayout(currentCalendar);
    }

    public void clearBackgroundDate(Date currentDate1) {
        Calendar currentCalendar = getCurrentCalendar();
        currentCalendar.setTime(currentDate1);

        ViewGroup dayOfMonthBackground = getDayOfMonthBackground(currentCalendar);
        getDayOfMonthText(currentCalendar).setBackgroundResource(android.R.color.transparent);

        if (weekOfDay.contains(currentCalendar.get(Calendar.DAY_OF_MONTH) + ""))
            getDayOfMonthText(currentCalendar).setTextColor(getResources().getColor(R.color.holiday_text_color));
        else
            getDayOfMonthText(currentCalendar).setTextColor(getResources().getColor(R.color.text_color));

    }

    public Date getCurrentDate() {
        return lastSelectedDay;
    }

    private void storeLastValues(Date currentDate) {
        lastSelectedDay = currentDate;
    }

    public void markFirstUnderlineWithStyle(int style, Date currentDate) {
        Locale locale = context.getResources().getConfiguration().locale;
        Calendar currentCalendar = Calendar.getInstance(locale);
        currentCalendar.setTime(currentDate);
//        View underline = getFirstUnderline(currentCalendar);

        // Draw day with style
//        underline.setVisibility(View.VISIBLE);
//        underline.setBackgroundResource(style);
    }


    // ************************************************************************************************************************************************************************
    // * Public interface
    // ************************************************************************************************************************************************************************

    public void markSecondUnderlineWithStyle(int style, Date currentDate) {
        Locale locale = context.getResources().getConfiguration().locale;
        Calendar currentCalendar = Calendar.getInstance(locale);
        currentCalendar.setTime(currentDate);
//        View underline = getSecondUnderline(currentCalendar);

        // Draw day with style
//        underline.setVisibility(View.VISIBLE);
//        underline.setBackgroundResource(style);
    }

    public void setRobotoCalendarListener(RobotoCalendarListener robotoCalendarListener) {
        this.robotoCalendarListener = robotoCalendarListener;
    }

    // ************************************************************************************************************************************************************************
    // * Event handler methods
    // ************************************************************************************************************************************************************************

    public void addOffDay(String day) {
        int a = day.indexOf("0");

        if (a == 0)
            day = day.replace("0", "");

        weekOfDay.add(day);

    }

    public interface RobotoCalendarListener {

        void onDateSelected(Date date);

        void onRightButtonClick();

        void onLeftButtonClick();

        void onTitleClick();
    }
}
