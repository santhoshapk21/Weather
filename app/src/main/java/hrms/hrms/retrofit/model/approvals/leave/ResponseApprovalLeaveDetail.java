package hrms.hrms.retrofit.model.approvals.leave;

import java.util.List;

/**
 * Created by yudiz on 15/03/18.
 */

public class ResponseApprovalLeaveDetail {

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
