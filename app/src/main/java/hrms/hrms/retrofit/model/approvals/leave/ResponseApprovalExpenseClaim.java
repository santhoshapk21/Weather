package hrms.hrms.retrofit.model.approvals.leave;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yudizsolutions on 12/09/18.
 */

public class ResponseApprovalExpenseClaim implements Serializable {

    @SerializedName("PageNo")
    @Expose
    public Integer pageNo;
    @SerializedName("TotalRecord")
    @Expose
    public String totalRecord;
    @SerializedName("IsNextPage")
    @Expose
    public Boolean isNextPage;
    @SerializedName("Message")
    @Expose
    public String message;
    @SerializedName("details")
    @Expose
    public List<Detail> details = null;


    public class Detail implements Serializable {

        @SerializedName("TravelApplicationHeaderID")
        @Expose
        public String travelApplicationHeaderID;
        @SerializedName("EmployeeID")
        @Expose
        public String employeeID;
        @SerializedName("TravelApplicationDate")
        @Expose
        public String travelApplicationDate;
        @SerializedName("FromPlace")
        @Expose
        public String fromPlace;
        @SerializedName("ToPlace")
        @Expose
        public String toPlace;
        @SerializedName("PurposeOfVisit")
        @Expose
        public String purposeOfVisit;
        @SerializedName("TravelModeID")
        @Expose
        public String travelModeID;
        @SerializedName("FromDate")
        @Expose
        public String fromDate;
        @SerializedName("ToDate")
        @Expose
        public String toDate;
        @SerializedName("TravelDuration")
        @Expose
        public String travelDuration;
        @SerializedName("TotalExpenseAmount")
        @Expose
        public String totalExpenseAmount;
        @SerializedName("TotalApprovedAmount")
        @Expose
        public String totalApprovedAmount;
        @SerializedName("FinalStatus")
        @Expose
        public String finalStatus;
        @SerializedName("ReportingPerson1Status")
        @Expose
        public String reportingPerson1Status;
        @SerializedName("ReportingPerson2Status")
        @Expose
        public String reportingPerson2Status;
        @SerializedName("ClaimType")
        @Expose
        public String claimType;
        @SerializedName("CityType")
        @Expose
        public String cityType;
        @SerializedName("OtherExpenceMonth")
        @Expose
        public String otherExpenceMonth;
        @SerializedName("OtherExpenceYear")
        @Expose
        public String otherExpenceYear;
        @SerializedName("ReportingPerson")
        @Expose
        public String reportingPerson;
        @SerializedName("ReportingPerson1")
        @Expose
        public String reportingPerson1;
        @SerializedName("EmployeeName")
        @Expose
        public String employeeName;
        @SerializedName("EFirstName")
        @Expose
        public String eFirstName;
        @SerializedName("EMiddleName")
        @Expose
        public String eMiddleName;
        @SerializedName("ELastName")
        @Expose
        public String eLastName;
        @SerializedName("expenses")
        @Expose
        public List<Expense> expenses = null;

        public boolean isSelected = false;

    }

    public class Expense implements Serializable {
        @SerializedName("TravelApplicationLineID")
        @Expose
        public String travelApplicationLineID;
        @SerializedName("TravelApplicationHeaderID")
        @Expose
        public String travelApplicationHeaderID;
        @SerializedName("Amount")
        @Expose
        public String amount;
        @SerializedName("ApprovedAmount")
        @Expose
        public String approvedAmount;
        @SerializedName("Description")
        @Expose
        public String description;
        @SerializedName("ReceiptNumber")
        @Expose
        public String receiptNumber;
        @SerializedName("ExpenceName")
        @Expose
        public String expenceName;
        @SerializedName("AttachmentPath")
        @Expose
        public String attachmentPath;


    }
}
