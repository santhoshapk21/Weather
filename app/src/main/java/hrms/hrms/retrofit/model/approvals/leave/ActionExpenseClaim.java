package hrms.hrms.retrofit.model.approvals.leave;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yudizsolutions on 20/09/18.
 */

public class ActionExpenseClaim implements Serializable {

    @SerializedName("TravelApplicationHeaderID")
    @Expose
    public Integer TravelApplicationHeaderID;
    @SerializedName("ReportingStatus")
    @Expose
    public String ReportingStatus;
    @SerializedName("FinalStatus")
    @Expose
    public Integer FinalStatus;
    @SerializedName("expenses")
    @Expose
    public ArrayList<Expenses> expenses;

    class Expenses {
        public String TravelApplicationLineID;
        public String ApprovedAmount;
    }
}
