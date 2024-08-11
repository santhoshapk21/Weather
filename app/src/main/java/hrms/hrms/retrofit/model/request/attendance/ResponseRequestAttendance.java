package hrms.hrms.retrofit.model.request.attendance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseRequestAttendance {

    //	"GPSAttendanceID": "10139",
//			"GPSAOutTime": null,
//			"Date": "2019-01-01T07:41:32",
//			"Message": null,
//			"Comment": "",
//			"FinalStatus": "Pending",
//			"Latitude": "23.00994767219452",
//			"Longitude": "72.50605071918861",
//			"OutReason": "",
//			"GPSAInTime": "07:41",
//			"InReason": "bsbs",
//			"ImagePath": "http:\/\/192.168.11.174:8086\/clients\/demo\/HRMSdocuments2\/ProfileImage\/1546328462016.jpg"
    @SerializedName("OutLongitude")
    @Expose
    private String outLongitude;

    @SerializedName("FinalStatus")
    @Expose
    private String status;

    @SerializedName("Message")
    @Expose
    private Object message;

    @SerializedName("InReason")
    @Expose
    private String inReason;

    @SerializedName("GPSAOutTime")
    @Expose
    private String outTime;

    @SerializedName("InLongitude")
    @Expose
    private String inLongitude;

    @SerializedName("Date")
    @Expose
    private String date;

    @SerializedName("InLatitude")
    @Expose
    private String inLatitude;

    @SerializedName("GPSAttendanceID")
    @Expose
    private String attendanceId;

    @SerializedName("OutLocation")
    @Expose
    private String outLocation;

    @SerializedName("InTime")
    @Expose
    private Object inTime;

    @SerializedName("OutLatitude")
    @Expose
    private String outLatitude;

    @SerializedName("OutReason")
    @Expose
    private String outReason;

    @SerializedName("Reporting1Status")
    @Expose
    private String reporting1Status;

    @SerializedName("InLocation")
    @Expose
    private String inLocation;

    @SerializedName("EmployeeId")
    @Expose
    private String employeeId;

    @SerializedName("Reporting2Status")
    @Expose
    private String reporting2Status;

    @SerializedName("Comment")
    @Expose
    private String comment;


    @SerializedName("ImagePath")
    @Expose
    private String imagePath;


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setOutLongitude(String outLongitude) {
        this.outLongitude = outLongitude;
    }

    public String getOutLongitude() {
        return outLongitude;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Object getMessage() {
        return message;
    }

    public void setInReason(String inReason) {
        this.inReason = inReason;
    }

    public String getInReason() {
        return inReason;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setInLongitude(String inLongitude) {
        this.inLongitude = inLongitude;
    }

    public String getInLongitude() {
        return inLongitude;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setInLatitude(String inLatitude) {
        this.inLatitude = inLatitude;
    }

    public String getInLatitude() {
        return inLatitude;
    }

    public void setAttendanceId(String attendanceId) {
        this.attendanceId = attendanceId;
    }

    public String getAttendanceId() {
        return attendanceId;
    }

    public void setOutLocation(String outLocation) {
        this.outLocation = outLocation;
    }

    public String getOutLocation() {
        return outLocation;
    }

    public void setInTime(Object inTime) {
        this.inTime = inTime;
    }

    public Object getInTime() {
        return inTime;
    }

    public void setOutLatitude(String outLatitude) {
        this.outLatitude = outLatitude;
    }

    public String getOutLatitude() {
        return outLatitude;
    }

    public void setOutReason(String outReason) {
        this.outReason = outReason;
    }

    public String getOutReason() {
        return outReason;
    }

    public void setReporting1Status(String reporting1Status) {
        this.reporting1Status = reporting1Status;
    }

    public String getReporting1Status() {
        return reporting1Status;
    }

    public void setInLocation(String inLocation) {
        this.inLocation = inLocation;
    }

    public String getInLocation() {
        return inLocation;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setReporting2Status(String reporting2Status) {
        this.reporting2Status = reporting2Status;
    }

    public String getReporting2Status() {
        return reporting2Status;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}