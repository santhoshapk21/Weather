package hrms.hrms.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yudiz on 14/10/16.
 */
public class Shift {
    private String toDate, fromDate, shiftTime, shiftName, id, desc;

    public String getToDate() {
        return toDate;
    }


    public Shift(String toDate, String fromDate, String shiftTime, String shiftName) {
        id =  Math.random()+"";
        this.toDate = toDate;
        this.fromDate = fromDate;
        this.shiftTime = shiftTime;
        this.shiftName = shiftName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getShiftTime() {
        return shiftTime;
    }

    public void setShiftTime(String shiftTime) {
        this.shiftTime = shiftTime;
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }
}
