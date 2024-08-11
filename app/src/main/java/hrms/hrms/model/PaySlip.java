package hrms.hrms.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yudiz on 14/10/16.
 */
public class PaySlip {

    private String year;
    private String date;
    private String month;

    public PaySlip(String year, String date, String month) {
        this.year = year;
        this.date = date;
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
