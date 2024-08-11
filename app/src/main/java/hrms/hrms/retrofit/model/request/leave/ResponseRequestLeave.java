package hrms.hrms.retrofit.model.request.leave;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseRequestLeave{

	@SerializedName("ReportingStatus2")
	@Expose
	private String reportingStatus2;

	@SerializedName("ReportingStatus1")
	@Expose
	private String reportingStatus1;

	@SerializedName("LeaveDetail")
	@Expose
	private String leaveDetail;

	@SerializedName("LeaveAppId")
	@Expose
	private String leaveAppId;

	@SerializedName("LeaveStatus")
	@Expose
	private String leaveStatus;

	@SerializedName("LeaveDuration")
	@Expose
	private String leaveDuration;

	@SerializedName("FromDate")
	@Expose
	private String fromDate;

	@SerializedName("ToDate")
	@Expose
	private String toDate;

	@SerializedName("EmployeeID")
	@Expose
	private String employeeID;

	@SerializedName("ReasonForLeave")
	@Expose
	private String reasonforLeave;

	@SerializedName("ReasonForCancel")
	@Expose

	private String reasonforCancel;

	@SerializedName("ReasonForApproval")
	@Expose
	private String reasonforApproval;

	@SerializedName("LeaveReason")
	@Expose
	private String leaveReason;

	@SerializedName("ReportingManager1")
	@Expose
	private String reportingManager1;

	@SerializedName("ReportingManager2")
	@Expose
	private String reportingManager2;

	@SerializedName("LeaveLineId")
	@Expose
	private String leaveLineId;

	@SerializedName("LeaveCode")
	@Expose
	private String LeaveCode;

	public String getLeaveCode() {
		return LeaveCode;
	}

	public void setLeaveCode(String leaveCode) {
		LeaveCode = leaveCode;
	}

	public void setReportingStatus2(String reportingStatus2){
		this.reportingStatus2 = reportingStatus2;
	}

	public String getReportingStatus2(){
		return reportingStatus2;
	}

	public void setReportingStatus1(String reportingStatus1){
		this.reportingStatus1 = reportingStatus1;
	}

	public String getReportingStatus1(){
		return reportingStatus1;
	}

	public void setLeaveAppId(String leaveAppId){
		this.leaveAppId = leaveAppId;
	}

	public String getLeaveAppId(){
		return leaveAppId;
	}

	public void setLeaveStatus(String leaveStatus){
		this.leaveStatus = leaveStatus;
	}

	public String getLeaveStatus(){
		return leaveStatus;
	}

	public void setLeaveDuration(String leaveDuration){
		this.leaveDuration = leaveDuration;
	}

	public String getLeaveDuration(){
		return leaveDuration;
	}

	public void setFromDate(String leaveDate){
		this.fromDate = leaveDate;
	}

	public String getFromDate(){
		return fromDate;
	}

	public void setToDate(String leaveDate){
		this.toDate = leaveDate;
	}

	public String getToDate(){
		return toDate;
	}

	public void setEmployeeID(String employeeID){
		this.employeeID = employeeID;
	}

	public String getEmployeeID(){
		return employeeID;
	}

	public void setLeaveReason(String leaveReason){
		this.leaveReason = leaveReason;
	}

	public String getLeaveReason(){
		return leaveReason;
	}

	public void setReportingManager1(String reportingManager1){
		this.reportingManager1 = reportingManager1;
	}

	public String getReportingManager1(){
		return reportingManager1;
	}

	public void setReportingManager2(String reportingManager2){
		this.reportingManager2 = reportingManager2;
	}

	public String getReportingManager2(){
		return reportingManager2;
	}

	public void setLeaveLineId(String leaveLineId){
		this.leaveLineId = leaveLineId;
	}

	public String getLeaveLineId(){
		return leaveLineId;
	}

	public String getReasonforLeave() {
		return reasonforLeave;
	}

	public void setReasonforLeave(String reasonforLeave) {
		this.reasonforLeave = reasonforLeave;
	}

	public String getReasonforCancel() {
		return reasonforCancel;
	}

	public void setReasonforCancel(String reasonforCancel) {
		this.reasonforCancel = reasonforCancel;
	}

	public String getReasonforApproval() {
		return reasonforApproval;
	}

	public void setReasonforApproval(String reasonforApproval) {
		this.reasonforApproval = reasonforApproval;
	}

	public String getLeaveDetail() {
		return leaveDetail;
	}

	public void setLeaveDetail(String leaveDetail) {
		this.leaveDetail = leaveDetail;
	}

}
