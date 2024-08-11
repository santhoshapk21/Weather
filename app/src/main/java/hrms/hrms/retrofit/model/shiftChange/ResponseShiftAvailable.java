package hrms.hrms.retrofit.model.shiftChange;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseShiftAvailable{

	@SerializedName("ShiftStartTime")
	@Expose
	private String shiftStartTime;

	@SerializedName("ShiftDuration")
	@Expose
	private String shiftDuration;

	@SerializedName("ShiftName")
	@Expose
	private String shiftName;

	@SerializedName("ShiftId")
	@Expose
	private String shiftId;

	@SerializedName("BreakDuration")
	@Expose
	private String breakDuration;

	@SerializedName("BreakEndTime")
	@Expose
	private String breakEndTime;

	@SerializedName("BreakStartTime")
	@Expose
	private String breakStartTime;

	@SerializedName("ShiftEndTime")
	@Expose
	private String shiftEndTime;

	public void setShiftStartTime(String shiftStartTime){
		this.shiftStartTime = shiftStartTime;
	}

	public String getShiftStartTime(){
		return shiftStartTime;
	}

	public void setShiftDuration(String shiftDuration){
		this.shiftDuration = shiftDuration;
	}

	public String getShiftDuration(){
		return shiftDuration;
	}

	public void setShiftName(String shiftName){
		this.shiftName = shiftName;
	}

	public String getShiftName(){
		return shiftName;
	}

	public void setShiftId(String shiftId){
		this.shiftId = shiftId;
	}

	public String getShiftId(){
		return shiftId;
	}

	public void setBreakDuration(String breakDuration){
		this.breakDuration = breakDuration;
	}

	public String getBreakDuration(){
		return breakDuration;
	}

	public void setBreakEndTime(String breakEndTime){
		this.breakEndTime = breakEndTime;
	}

	public String getBreakEndTime(){
		return breakEndTime;
	}

	public void setBreakStartTime(String breakStartTime){
		this.breakStartTime = breakStartTime;
	}

	public String getBreakStartTime(){
		return breakStartTime;
	}

	public void setShiftEndTime(String shiftEndTime){
		this.shiftEndTime = shiftEndTime;
	}

	public String getShiftEndTime(){
		return shiftEndTime;
	}
}