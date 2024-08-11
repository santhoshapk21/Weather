package hrms.hrms.retrofit.model.request.leave;

import java.util.List;

/**
 * Created by yudiz on 09/10/17.
 */

public class ResponseLeaveDetail {

    public String ReasonForLeave;
    public String ReasonForCancel;
    public String ReasonForApproval;
    public String LeaveAppID;
    public String EmployeeID;
    public String Name;
    public String LeaveStatus;
    public String ReportPerson1Id;
    public String ReportPerson1Name;
    public String ReportPerson2Id;
    public String ReportPerson2Name;
    public String ManagerReason;

    public List<Details> Details;

    public class Details{
        public String LineID;
        public String Date;
        public String Duration;
        public String ManagerApproval;
        public String Status;
    }
}