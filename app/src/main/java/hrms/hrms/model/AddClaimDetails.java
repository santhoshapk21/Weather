
package hrms.hrms.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddClaimDetails {

    @SerializedName("ClaimType")
    @Expose
    private String claimType;
    @SerializedName("CityType")
    @Expose
    private String cityType;
    @SerializedName("FromPlace")
    @Expose
    private String fromPlace;
    @SerializedName("ToPlace")
    @Expose
    private String toPlace;
    @SerializedName("PurposeOfVisit")
    @Expose
    private String purposeOfVisit;
    @SerializedName("TravelModeID")
    @Expose
    private String travelModeID;
    @SerializedName("FromDate")
    @Expose
    private String fromDate;
    @SerializedName("ToDate")
    @Expose
    private String toDate;
    @SerializedName("Remarks")
    @Expose
    private String remarks;
    @SerializedName("OtherExpenceMonth")
    @Expose
    private String otherExpenceMonth;
    @SerializedName("OtherExpenceYear")
    @Expose
    private String otherExpenceYear;
    @SerializedName("expenses")
    @Expose
    private List<ClaimDetails> expenses = null;

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

    public String getTravelModeID() {
        return travelModeID;
    }

    public void setTravelModeID(String travelModeID) {
        this.travelModeID = travelModeID;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public List<ClaimDetails> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<ClaimDetails> expenses) {
        this.expenses = expenses;
    }

}
