package hrms.hrms.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Yudiz on 23/08/16.
 */
public class ClaimDetails {


    @SerializedName("ExpenceName")
    @Expose
    private String ExpenseName;
    @SerializedName("Amount")
    @Expose
    private String amount;
    @SerializedName("ReceiptNumber")
    @Expose
    private String receiptNumber;
    @SerializedName("Description")
    @Expose
    private String desc;
    @SerializedName("AttachmentPath")
    @Expose
    private String attachment;


    private String type = "";
    private String id = "";


    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }


    public String getExpenseName() {
        return ExpenseName;
    }

    public void setExpenseName(String expenseName) {
        ExpenseName = expenseName;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ClaimDetails(String type, String desc, String amount, String attachment) {
        this.id = Math.random() + "";
        this.type = type;
        this.desc = desc;
        this.amount = amount;
        this.attachment = attachment;

    }


    public ClaimDetails() {
        this.id = System.currentTimeMillis() + "";
    }

    public String getDesc() {

        return desc;
    }


    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
