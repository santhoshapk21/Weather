package hrms.hrms.model;

/**
 * Created by yudizsolutions on 18/04/18.
 */
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class ExpenseListDetailResponse {

    @SerializedName("TravelApplicationHeaderID")
    @Expose
    private String travelApplicationHeaderID;
    @SerializedName("EmployeeID")
    @Expose
    private String employeeID;
    @SerializedName("ClaimType")
    @Expose
    private String claimType;
    @SerializedName("CityType")
    @Expose
    private String cityType;
    @SerializedName("ApplicationDate")
    @Expose
    private String applicationDate;
    @SerializedName("FromPlace")
    @Expose
    private String fromPlace;
    @SerializedName("ToPlace")
    @Expose
    private String toPlace;
    @SerializedName("PurposeOfVisit")
    @Expose
    private String purposeOfVisit;
    @SerializedName("FromDate")
    @Expose
    private String fromDate;
    @SerializedName("ToDate")
    @Expose
    private String toDate;
    @SerializedName("TravelDuration")
    @Expose
    private String travelDuration;
    @SerializedName("TotalExpenseAmount")
    @Expose
    private String totalExpenseAmount;
    @SerializedName("TotalApprovedAmount")
    @Expose
    private String totalApprovedAmount;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("TravelModeID")
    @Expose
    private String travelModeID;
    @SerializedName("OtherExpenceMonth")
    @Expose
    private String otherExpenceMonth;
    @SerializedName("OtherExpenceYear")
    @Expose
    private String otherExpenceYear;
    @SerializedName("expenses")
    @Expose
    private List<ExpenseListResponse> expenses = null;

    public String getTravelApplicationHeaderID() {
        return travelApplicationHeaderID;
    }

    public void setTravelApplicationHeaderID(String travelApplicationHeaderID) {
        this.travelApplicationHeaderID = travelApplicationHeaderID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getClaimType() {
        return claimType;
    }

    public void setClaimType(String claimType) {
        this.claimType = claimType;
    }

    public String getCityType() {
        return cityType;
    }

    public void setCityType(String cityType) {
        this.cityType = cityType;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getFromPlace() {
        return fromPlace;
    }

    public void setFromPlace(String fromPlace) {
        this.fromPlace = fromPlace;
    }

    public String getToPlace() {
        return toPlace;
    }

    public void setToPlace(String toPlace) {
        this.toPlace = toPlace;
    }

    public String getPurposeOfVisit() {
        return purposeOfVisit;
    }

    public void setPurposeOfVisit(String purposeOfVisit) {
        this.purposeOfVisit = purposeOfVisit;
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

    public String getTravelDuration() {
        return travelDuration;
    }

    public void setTravelDuration(String travelDuration) {
        this.travelDuration = travelDuration;
    }

    public String getTotalExpenseAmount() {
        return totalExpenseAmount;
    }

    public void setTotalExpenseAmount(String totalExpenseAmount) {
        this.totalExpenseAmount = totalExpenseAmount;
    }

    public String getTotalApprovedAmount() {
        return totalApprovedAmount;
    }

    public void setTotalApprovedAmount(String totalApprovedAmount) {
        this.totalApprovedAmount = totalApprovedAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTravelModeID() {
        return travelModeID;
    }

    public void setTravelModeID(String travelModeID) {
        this.travelModeID = travelModeID;
    }

    public String getOtherExpenceMonth() {
        return otherExpenceMonth;
    }

    public void setOtherExpenceMonth(String otherExpenceMonth) {
        this.otherExpenceMonth = otherExpenceMonth;
    }

    public String getOtherExpenceYear() {
        return otherExpenceYear;
    }

    public void setOtherExpenceYear(String otherExpenceYear) {
        this.otherExpenceYear = otherExpenceYear;
    }

    public List<ExpenseListResponse> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<ExpenseListResponse> expenses) {
        this.expenses = expenses;
    }
}
