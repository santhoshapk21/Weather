package hrms.hrms.utility;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yudiz on 11/10/16.
 */
public class Constants {
    public static String DATE_FORMAT = "MM-dd-yyyy";
    public static String DATE_FORMAT1 = "dd MMM yyyy";
    public static String SERVER_TIMEZONE = "Asia/Calcutta";
    public static String LOCAL_TIMEZONE = "Asia/Calcutta";
    public static Boolean ISTIMEZONECHANGE = false;

    public static List<String> getDateTextYear() {
        List<String> list = new ArrayList<>();
        list.add(0, "This Year");
        list.add(1, "Last Year");
        list.add(2, "Before Two Year");
        return list;
    }

    public static List<String> getDateText() {
        List<String> list = new ArrayList<>();
        list.add(0, "Today");
        list.add(1, "Yesterday");
        list.add(2, "Before Two Day");
        return list;
    }

    public static void setTimeZones(String serverTime, String locationTime) {
        if (!TextUtils.isEmpty(serverTime) && !TextUtils.isEmpty(locationTime)) {
            SERVER_TIMEZONE = serverTime;
            LOCAL_TIMEZONE = locationTime;
        }
    }
}
