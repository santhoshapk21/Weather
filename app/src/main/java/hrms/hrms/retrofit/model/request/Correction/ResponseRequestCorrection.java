package hrms.hrms.retrofit.model.request.Correction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseRequestCorrection {

    @SerializedName("Type")
    @Expose
    private String type;

    @SerializedName("RequestDate")
    @Expose
    private String requestDate;

    @SerializedName("EmaployeeId")
    @Expose
    private String emaployeeId;

    @SerializedName("CorrectionId")
    @Expose
    private String correctionId;

    @SerializedName("RequestTime")
    @Expose
    private String requestTime;

    @SerializedName("NextDayOut")
    @Expose
    private boolean nextDayOut;

    @SerializedName("FinalStatus")
    @Expose
    private String finalStatus;

    @SerializedName("Reason")
    @Expose
    private String reason;

    @SerializedName("ManagerReason")
    @Expose
    private String comment;

    public String ReportPerson1Id;
    public String ReportPerson1Name;
    public String ReportPerson2Id;
    public String ReportPerson2Name;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getEmaployeeId() {
        return emaployeeId;
    }

    public void setEmaployeeId(String emaployeeId) {
        this.emaployeeId = emaployeeId;
    }

    public String getCorrectionId() {
        return correctionId;
    }

    public void setCorrectionId(String correctionId) {
        this.correctionId = correctionId;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public boolean isNextDayOut() {
        return nextDayOut;
    }

    public void setNextDayOut(boolean nextDayOut) {
        this.nextDayOut = nextDayOut;
    }

    public String getFinalStatus() {
        return finalStatus;
    }

    public void setFinalStatus(String finalStatus) {
        this.finalStatus = finalStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}