package hrms.hrms.retrofit.model.attendance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseAttendance {

    @SerializedName("TimeIn")
    @Expose
    private String timeIn;

    @SerializedName("LateTimeIn")
    @Expose
    private String lateTimeIn;

    @SerializedName("HoursWorked")
    @Expose
    private String hoursWorked;

    @SerializedName("WorkingType")
    @Expose
    private String workingType;

    @SerializedName("TimeOut")
    @Expose
    private String timeOut;

    @SerializedName("LeaveStatus")
    @Expose
    private String leaveStatus;

    @SerializedName("LeaveType")
    @Expose
    private String leaveType;

    @SerializedName("IsAppliedForLeave")
    @Expose
    private Boolean isAppliedForLeave;

    @SerializedName("IsPresent")
    @Expose
    private boolean isPresent;

    @SerializedName("EarlyTimeOut")

    @Expose
    private String earlyTimeOut;

    @SerializedName("Date")
    @Expose
    private String date;

    @SerializedName("HolidayType")
    @Expose
    private String holidayType;

    @SerializedName("LeaveCode")
    @Expose
    private String leaveCode;

    public String getLeaveCode() {
        return (leaveCode == null) ? "" : leaveCode;
    }

    public void setLeaveCode(String leaveCode) {
        this.leaveCode = leaveCode;
    }

    public String getHolidayType() {
        return holidayType;
    }

    public void setHolidayType(String holidayType) {
        this.holidayType = holidayType;
    }

    public String getTimeIn() {
        return (timeIn == null) ? "" : timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public String getLateTimeIn() {
        return (lateTimeIn == null) ? "" : lateTimeIn;
    }

    public void setLateTimeIn(String lateTimeIn) {
        this.lateTimeIn = lateTimeIn;
    }

    public String getHoursWorked() {
        return (hoursWorked == null) ? "" : hoursWorked;
    }

    public void setHoursWorked(String hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public String getWorkingType() {
        return (workingType == null) ? "" : workingType;
    }

    public void setWorkingType(String workingType) {
        this.workingType = workingType;
    }

    public String getTimeOut() {
        return (timeOut == null) ? "" : timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public String getLeaveStatus() {
        return (leaveStatus == null) ? "" : leaveStatus;
    }

    public void setLeaveStatus(String leaveStatus) {
        this.leaveStatus = leaveStatus;
    }

    public String getLeaveType() {
        return (leaveType == null) ? "" : leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public boolean isIsAppliedForLeave() {
        return (isAppliedForLeave == null) ? false : isAppliedForLeave;
    }

    public void setIsAppliedForLeave(boolean isAppliedForLeave) {
        this.isAppliedForLeave = isAppliedForLeave;
    }

    public boolean isIsPresent() {
        return isPresent;
    }

    public void setIsPresent(boolean isPresent) {
        this.isPresent = isPresent;
    }

    public String getEarlyTimeOut() {
        return (earlyTimeOut == null) ? "" : earlyTimeOut;
    }

    public void setEarlyTimeOut(String earlyTimeOut) {
        this.earlyTimeOut = earlyTimeOut;
    }

    public String getDate() {
        return (date == null) ? "" : date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}