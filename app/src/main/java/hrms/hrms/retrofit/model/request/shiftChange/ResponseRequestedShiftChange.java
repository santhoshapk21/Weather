package hrms.hrms.retrofit.model.request.shiftChange;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseRequestedShiftChange {

    @SerializedName("Status")
    @Expose
    private String status;

    @SerializedName("EmaployeeId")
    @Expose
    private String emaployeeId;

    @SerializedName("Reporting1Status")
    @Expose
    private String reporting1Status;

    @SerializedName("Name")
    @Expose
    private String name;

    @SerializedName("ShiftChangeId")
    @Expose
    private String shiftChangeId;

    @SerializedName("ShiftId")
    @Expose
    private String shiftId;

    @SerializedName("FromDate")
    @Expose
    private String fromDate;

    @SerializedName("ToDate")
    @Expose
    private String toDate;

    @SerializedName("Reason")
    @Expose
    private String reason;

    @SerializedName("Reporting2Status")
    @Expose
    private String reporting2Status;


    @SerializedName("ShiftName")
    @Expose
    private String ShiftName;

    @SerializedName("StartTime")
    @Expose
    private String startTime;

    @SerializedName("EndTime")
    @Expose
    private String endTime;

    @SerializedName("ManagerReason")
    @Expose
    private String comment;

    public String ReportPerson1Id;
    public String ReportPerson1Name;
    public String ReportPerson2Id;
    public String ReportPerson2Name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStartTime() {
        return startTime + "";
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime + "";
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getShiftName() {
        return ShiftName;
    }

    public void setShiftName(String shiftName) {
        ShiftName = shiftName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setEmaployeeId(String emaployeeId) {
        this.emaployeeId = emaployeeId;
    }

    public String getEmaployeeId() {
        return emaployeeId;
    }

    public void setReporting1Status(String reporting1Status) {
        this.reporting1Status = reporting1Status;
    }

    public String getReporting1Status() {
        return reporting1Status;
    }

    public void setShiftChangeId(String shiftChangeId) {
        this.shiftChangeId = shiftChangeId;
    }

    public String getShiftChangeId() {
        return shiftChangeId;
    }

    public void setShiftId(String shiftId) {
        this.shiftId = shiftId;
    }

    public String getShiftId() {
        return shiftId;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReporting2Status(String reporting2Status) {
        this.reporting2Status = reporting2Status;
    }

    public String getReporting2Status() {
        return reporting2Status;
    }
}