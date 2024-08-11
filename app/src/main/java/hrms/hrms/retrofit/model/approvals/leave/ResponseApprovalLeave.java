package hrms.hrms.retrofit.model.approvals.leave;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseApprovalLeave {

    private int PageNo;

    private String TotalRecord;

    private boolean IsNextPage;

    public List<Details> details;

    public int getPageNo() {
        return PageNo;
    }

    public void setPageNo(int pageNo) {
        PageNo = pageNo;
    }

    public String getTotalRecord() {
        return TotalRecord;
    }

    public void setTotalRecord(String totalRecord) {
        TotalRecord = totalRecord;
    }

    public boolean getIsNextPage() {
        return IsNextPage;
    }

    public void setIsNextPage(boolean isNextPage) {
        IsNextPage = isNextPage;
    }

    public List<Details> getDetails() {
        return details;
    }

    public void setDetails(List<Details> details) {
        this.details = details;
    }

    public class Details {

        @SerializedName("LeaveAppLineStatus")
        @Expose
        private String leaveAppLineStatus;

        @SerializedName("LastDayHalf")
        @Expose
        private String lastDayHalf;

        @SerializedName("LeaveCancelType")
        @Expose
        private String leaveCancelType;

        @SerializedName("LeaveAppId")
        @Expose
        private String leaveAppId;

        @SerializedName("LeaveType")
        @Expose
        private String leaveType;

        @SerializedName("ReasonForLeave")
        @Expose
        private String reasonForLeave;

        @SerializedName("LeaveCancelReqDate")
        @Expose
        private String leaveCancelReqDate;

        @SerializedName("LeaveCode")
        @Expose
        private String leaveCode;

        @SerializedName("FirstDayHalf")
        @Expose
        private String firstDayHalf;

        @SerializedName("LeaveTypeID")
        @Expose
        private String leaveTypeID;

        @SerializedName("ReasonForCancel")
        @Expose
        private String reasonForCancel;

        @SerializedName("LeaveAppDate")
        @Expose
        private String leaveAppDate;

        @SerializedName("NoOfDays")
        @Expose
        private String noOfDays;

        @SerializedName("LeaveDuration")
        @Expose
        private String leaveDuration;

        @SerializedName("LeaveApprovedDate")
        @Expose
        private String leaveApprovedDate;

        @SerializedName("FromDate")
        @Expose
        private String fromDate;

        @SerializedName("ToDate")
        @Expose
        private String toDate;

        @SerializedName("EmployeeId")
        @Expose
        private String employeeId;

        @SerializedName("EmployeeName")
        @Expose
        private String employeeName;

        @SerializedName("LeaveCancelDate")
        @Expose
        private String leaveCancelDate;

        private boolean isSelected;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public String getEmployeeName() {
            return employeeName;
        }

        public void setEmployeeName(String employeeName) {
            this.employeeName = employeeName;
        }

        public void setLeaveAppLineStatus(String leaveAppLineStatus) {
            this.leaveAppLineStatus = leaveAppLineStatus;
        }

        public String getLeaveAppLineStatus() {
            return leaveAppLineStatus;
        }

        public void setLastDayHalf(String lastDayHalf) {
            this.lastDayHalf = lastDayHalf;
        }

        public String getLastDayHalf() {
            return lastDayHalf;
        }

        public void setLeaveCancelType(String leaveCancelType) {
            this.leaveCancelType = leaveCancelType;
        }

        public String getLeaveCancelType() {
            return leaveCancelType;
        }

        public void setLeaveAppId(String leaveAppId) {
            this.leaveAppId = leaveAppId;
        }

        public String getLeaveAppId() {
            return leaveAppId;
        }

        public void setLeaveType(String leaveType) {
            this.leaveType = leaveType;
        }

        public String getLeaveType() {
            return leaveType;
        }

        public void setReasonForLeave(String reasonForLeave) {
            this.reasonForLeave = reasonForLeave;
        }

        public String getReasonForLeave() {
            return reasonForLeave;
        }

        public void setLeaveCancelReqDate(String leaveCancelReqDate) {
            this.leaveCancelReqDate = leaveCancelReqDate;
        }

        public String getLeaveCancelReqDate() {
            return leaveCancelReqDate;
        }

        public void setLeaveCode(String leaveCode) {
            this.leaveCode = leaveCode;
        }

        public String getLeaveCode() {
            return leaveCode;
        }

        public void setFirstDayHalf(String firstDayHalf) {
            this.firstDayHalf = firstDayHalf;
        }

        public String getFirstDayHalf() {
            return firstDayHalf;
        }

        public void setLeaveTypeID(String leaveTypeID) {
            this.leaveTypeID = leaveTypeID;
        }

        public String getLeaveTypeID() {
            return leaveTypeID;
        }

        public void setReasonForCancel(String reasonForCancel) {
            this.reasonForCancel = reasonForCancel;
        }

        public String getReasonForCancel() {
            return reasonForCancel;
        }

        public void setLeaveAppDate(String leaveAppDate) {
            this.leaveAppDate = leaveAppDate;
        }

        public String getLeaveAppDate() {
            return leaveAppDate;
        }

        public void setNoOfDays(String noOfDays) {
            this.noOfDays = noOfDays;
        }

        public String getNoOfDays() {
            return noOfDays;
        }

        public void setLeaveDuration(String leaveDuration) {
            this.leaveDuration = leaveDuration;
        }

        public String getLeaveDuration() {
            return leaveDuration;
        }

        public void setLeaveApprovedDate(String leaveApprovedDate) {
            this.leaveApprovedDate = leaveApprovedDate;
        }

        public String getLeaveApprovedDate() {
            return leaveApprovedDate;
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

        public void setEmployeeId(String employeeId) {
            this.employeeId = employeeId;
        }

        public String getEmployeeId() {
            return employeeId;
        }

        public void setLeaveCancelDate(String leaveCancelDate) {
            this.leaveCancelDate = leaveCancelDate;
        }

        public String getLeaveCancelDate() {
            return leaveCancelDate;
        }


    }
}