package hrms.hrms.retrofit.model.request;

import hrms.hrms.retrofit.model.request.Correction.ResponseRequestCorrection;
import hrms.hrms.retrofit.model.request.attendance.ResponseRequestAttendance;
import hrms.hrms.retrofit.model.request.leave.ResponseRequestLeave;
import hrms.hrms.retrofit.model.request.regularisation.ResponseRequestRegularisation;
import hrms.hrms.retrofit.model.request.shiftChange.ResponseRequestedShiftChange;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yudiz on 16/03/17.
 */

public class ResponseRequest {

    @SerializedName("Shift")
    @Expose
    private List<ResponseRequestedShiftChange> shift;

    @SerializedName("Attendance")
    @Expose
    private List<ResponseRequestAttendance> attendance;

    @SerializedName("Leave")
    @Expose
    private List<ResponseRequestLeave> leave;

    @SerializedName("Regularization")
    @Expose
    private List<ResponseRequestRegularisation> regularization;

    @SerializedName("Correction")
    @Expose
    private List<ResponseRequestCorrection> correction;

    public List<ResponseRequestedShiftChange> getShift() {
        return shift;
    }

    public void setShift(List<ResponseRequestedShiftChange> shift) {
        this.shift = shift;
    }

    public List<ResponseRequestAttendance> getAttendance() {
        return attendance;
    }

    public void setAttendance(List<ResponseRequestAttendance> attendance) {
        this.attendance = attendance;
    }

    public List<ResponseRequestLeave> getLeave() {
        return leave;
    }

    public void setLeave(List<ResponseRequestLeave> leave) {
        this.leave = leave;
    }

    public List<ResponseRequestRegularisation> getRegularization() {
        return regularization;
    }

    public void setRegularization(List<ResponseRequestRegularisation> regularization) {
        this.regularization = regularization;
    }

    public List<ResponseRequestCorrection> getCorrection() {
        return correction;
    }

    public void setCorrection(List<ResponseRequestCorrection> correction) {
        this.correction = correction;
    }

}
