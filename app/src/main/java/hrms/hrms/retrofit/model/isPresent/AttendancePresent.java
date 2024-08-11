package hrms.hrms.retrofit.model.isPresent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttendancePresent {

	@SerializedName("OutLongitude")
	@Expose
	private Object outLongitude;

	@SerializedName("Status")
	@Expose
	private Object status;

	@SerializedName("InReason")
	@Expose
	private Object inReason;

	@SerializedName("OutTime")
	@Expose
	private Object outTime;

	@SerializedName("InLongitude")
	@Expose
	private Object inLongitude;

	@SerializedName("Date")
	@Expose
	private Object date;

	@SerializedName("InLatitude")
	@Expose
	private Object inLatitude;

	@SerializedName("AttendanceId")
	@Expose
	private Object attendanceId;

	@SerializedName("OutLocation")
	@Expose
	private Object outLocation;

	@SerializedName("InTime")
	@Expose
	private Object inTime;

	@SerializedName("OutLatitude")
	@Expose
	private Object outLatitude;

	@SerializedName("OutReason")
	@Expose
	private Object outReason;

	@SerializedName("Reporting1Status")
	@Expose
	private Object reporting1Status;

	@SerializedName("InLocation")
	@Expose
	private Object inLocation;

	@SerializedName("EmployeeId")
	@Expose
	private Object employeeId;

	@SerializedName("Reporting2Status")
	@Expose
	private Object reporting2Status;

	public void setOutLongitude(Object outLongitude){
		this.outLongitude = outLongitude;
	}

	public Object getOutLongitude(){
		return outLongitude;
	}

	public void setStatus(Object status){
		this.status = status;
	}

	public Object getStatus(){
		return status;
	}

	public void setInReason(Object inReason){
		this.inReason = inReason;
	}

	public Object getInReason(){
		return inReason;
	}

	public void setOutTime(Object outTime){
		this.outTime = outTime;
	}

	public Object getOutTime(){
		return outTime;
	}

	public void setInLongitude(Object inLongitude){
		this.inLongitude = inLongitude;
	}

	public Object getInLongitude(){
		return inLongitude;
	}

	public void setDate(Object date){
		this.date = date;
	}

	public Object getDate(){
		return date;
	}

	public void setInLatitude(Object inLatitude){
		this.inLatitude = inLatitude;
	}

	public Object getInLatitude(){
		return inLatitude;
	}

	public void setAttendanceId(Object attendanceId){
		this.attendanceId = attendanceId;
	}

	public Object getAttendanceId(){
		return attendanceId;
	}

	public void setOutLocation(Object outLocation){
		this.outLocation = outLocation;
	}

	public Object getOutLocation(){
		return outLocation;
	}

	public void setInTime(Object inTime){
		this.inTime = inTime;
	}

	public Object getInTime(){
		return inTime;
	}

	public void setOutLatitude(Object outLatitude){
		this.outLatitude = outLatitude;
	}

	public Object getOutLatitude(){
		return outLatitude;
	}

	public void setOutReason(Object outReason){
		this.outReason = outReason;
	}

	public Object getOutReason(){
		return outReason;
	}

	public void setReporting1Status(Object reporting1Status){
		this.reporting1Status = reporting1Status;
	}

	public Object getReporting1Status(){
		return reporting1Status;
	}

	public void setInLocation(Object inLocation){
		this.inLocation = inLocation;
	}

	public Object getInLocation(){
		return inLocation;
	}

	public void setEmployeeId(Object employeeId){
		this.employeeId = employeeId;
	}

	public Object getEmployeeId(){
		return employeeId;
	}

	public void setReporting2Status(Object reporting2Status){
		this.reporting2Status = reporting2Status;
	}

	public Object getReporting2Status(){
		return reporting2Status;
	}
}