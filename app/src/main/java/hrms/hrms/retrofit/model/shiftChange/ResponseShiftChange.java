package hrms.hrms.retrofit.model.shiftChange;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResponseShiftChange implements Serializable {

	@SerializedName("ShiftStartTime")
	@Expose
	private String shiftStartTime;

	@SerializedName("ShiftName")
	@Expose
	private String shiftName;

	@SerializedName("FromDate")
	@Expose
	private String fromDate;

	@SerializedName("ToDate")
	@Expose
	private String toDate;

	@SerializedName("ShiftEndTime")
	@Expose
	private String shiftEndTime;

	public void setShiftStartTime(String shiftStartTime){
		this.shiftStartTime = shiftStartTime;
	}

	public String getShiftStartTime(){
		return shiftStartTime;
	}

	public void setShiftName(String shiftName){
		this.shiftName = shiftName;
	}

	public String getShiftName(){
		return shiftName;
	}

	public void setFromDate(String fromDate){
		this.fromDate = fromDate;
	}

	public String getFromDate(){
		return fromDate;
	}

	public void setToDate(String toDate){
		this.toDate = toDate;
	}

	public String getToDate(){
		return toDate;
	}

	public void setShiftEndTime(String shiftEndTime){
		this.shiftEndTime = shiftEndTime;
	}

	public String getShiftEndTime(){
		return shiftEndTime;
	}
}