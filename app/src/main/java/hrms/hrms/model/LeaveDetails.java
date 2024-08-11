package hrms.hrms.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Yudiz on 23/08/16.
 */
public class LeaveDetails implements Serializable {
    private String LeaveCodeID;
    private String LeaveCode;
    private String FromDate;
    private String ToDate;
    private String ReasonforLeave;
    private transient long fromDateInMillis;
    private transient long toDateInMillis;
    private List<Data> dates;

    public long getToDateInMillis() {
        return toDateInMillis;
    }

    public void setToDateInMillis(long toDateInMillis) {
        this.toDateInMillis = toDateInMillis;
    }

    public long getFromDateInMillis() {
        return fromDateInMillis;
    }

    public void setFromDateInMillis(long fromDateInMillis) {
        this.fromDateInMillis = fromDateInMillis;
    }

    public String getLeaveCodeID() {
        return LeaveCodeID;
    }

    public void setLeaveCodeID(String leaveCodeID) {
        LeaveCodeID = leaveCodeID;
    }

    public String getLeaveCode() {
        return LeaveCode;
    }

    public void setLeaveCode(String leaveCode) {
        LeaveCode = leaveCode;
    }

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String fromDate) {
        FromDate = fromDate;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }

    public String getReasonforLeave() {
        return ReasonforLeave;
    }

    public void setReasonforLeave(String reasonforLeave) {
        ReasonforLeave = reasonforLeave;
    }

    public List<Data> getDates() {
        return dates;
    }

    public void setDates(List<Data> dates) {
        this.dates = dates;
    }

}
