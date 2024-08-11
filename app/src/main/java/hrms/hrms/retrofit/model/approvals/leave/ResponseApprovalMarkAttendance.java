package hrms.hrms.retrofit.model.approvals.leave;

import java.util.List;

/**
 * Created by yudiz on 06/03/18.
 */

public class ResponseApprovalMarkAttendance {

    public int PageNo;
    public String TotalRecord;
    public boolean IsNextPage;
    public List<Details> details;

    public class Details {
        public String ReportingPerson;
        public String ReportingPerson1;
        public String EmployeeName;
        public String EFirstName;
        public String EMiddleName;
        public String ELastName;
        public String EmployeeID;
        public String GPSAttendanceID;
        public String INLongitude;
        public String INLatitude;
        public String InLocation;
        public String INLocationRadiusInMeters;
        public String OutLocationName;
        public String InReason;
        public String OutLatitude;
        public String OutLongitude;
        public String OutLocationRadiusInMeters;
        public String OutReason;
        public String Reporting1Status;
        public String Reporting2Status;
        public String FinalStatus;
        public String CreatedBy;
        public String CreatedDate;
        public String Modifiedby;
        public String ModifedDate;
        public String Device;
        public String ImageURL;
        private boolean isSelected;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }    }
}
