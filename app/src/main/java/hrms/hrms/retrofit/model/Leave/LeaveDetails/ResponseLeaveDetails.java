package hrms.hrms.retrofit.model.Leave.LeaveDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseLeaveDetails {

    public String Taken;
    public String Balance;
    public String PendingApproval;
    public List<Details> details;

    public class Details {
        public String Debit;
        public String Credit;
        public String LeaveType;
        public String Total;
        public boolean IsPaid;
        public boolean IsCarryForward;
        public String LeaveTypeId;
    }
}