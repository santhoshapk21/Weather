package hrms.hrms.model;

/**
 * Created by Yudiz on 23/08/16.
 */
public class RequestDetails {
    private String id = "";
    private String fromDate = "";
    private String toDate = "";
    private String reqType = "";
    private String status = "";
    private String duration = "";
    private String desc = "";

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public RequestDetails(String fromDate, String toDate, String status, String reqType, String desc) {
        this.id =  Math.random()+"";
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.status = status;

        this.reqType = reqType;
        this.desc = desc;
    }

    public RequestDetails() {
        this.id = System.currentTimeMillis() + "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public String getFromDate() {

        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getReqType() {
        return reqType;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
