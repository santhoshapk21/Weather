package hrms.hrms.model;

/**
 * Created by yudizsolutions on 18/04/18.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExpenseListResponse {
    @SerializedName("TravelApplicationLineID")
    @Expose
    private String travelApplicationLineID;
    @SerializedName("TravelApplicationHeaderID")
    @Expose
    private String travelApplicationHeaderID;
    @SerializedName("Amount")
    @Expose
    private String amount;
    @SerializedName("ApprovedAmount")
    @Expose
    private String approvedAmount;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("ReceiptNumber")
    @Expose
    private String receiptNumber;
    @SerializedName("ExpenceName")
    @Expose
    private String expenceName;
    @SerializedName("AttachmentPath")
    @Expose
    private String attachmentPath;

    public String getTravelApplicationLineID() {
        return travelApplicationLineID;
    }

    public void setTravelApplicationLineID(String travelApplicationLineID) {
        this.travelApplicationLineID = travelApplicationLineID;
    }

    public String getTravelApplicationHeaderID() {
        return travelApplicationHeaderID;
    }

    public void setTravelApplicationHeaderID(String travelApplicationHeaderID) {
        this.travelApplicationHeaderID = travelApplicationHeaderID;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getApprovedAmount() {
        return approvedAmount;
    }

    public void setApprovedAmount(String approvedAmount) {
        this.approvedAmount = approvedAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public String getExpenceName() {
        return expenceName;
    }

    public void setExpenceName(String expenceName) {
        this.expenceName = expenceName;
    }

    public String getAttachmentPath() {
        return attachmentPath;
    }

    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }

}
