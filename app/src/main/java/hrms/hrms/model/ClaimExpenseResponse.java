package hrms.hrms.model;

/**
 * Created by yudizsolutions on 18/04/18.
 */
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class ClaimExpenseResponse {


    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("details")
    @Expose
    private List<ExpenseListDetailResponse> details = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ExpenseListDetailResponse> getDetails() {
        return details;
    }

    public void setDetails(List<ExpenseListDetailResponse> details) {
        this.details = details;
    }

}
