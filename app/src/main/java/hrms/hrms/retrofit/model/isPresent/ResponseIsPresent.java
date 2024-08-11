package hrms.hrms.retrofit.model.isPresent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ResponseIsPresent{

	@SerializedName("Attendance")
	@Expose
	private AttendancePresent attendance;

	@SerializedName("IsPresent")
	@Expose
	private boolean isPresent;

	public void setAttendance(AttendancePresent attendance){
		this.attendance = attendance;
	}

	public AttendancePresent getAttendance(){
		return attendance;
	}

	public void setIsPresent(boolean isPresent){
		this.isPresent = isPresent;
	}

	public boolean isIsPresent(){
		return isPresent;
	}
}