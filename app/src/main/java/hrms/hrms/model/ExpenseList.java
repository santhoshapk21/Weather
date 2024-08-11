
package hrms.hrms.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExpenseList {

    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("details")
    @Expose
    private List<ExpenseListDetail> details = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ExpenseListDetail> getDetails() {
        return details;
    }

    public void setDetails(List<ExpenseListDetail> details) {
        this.details = details;
    }

}
