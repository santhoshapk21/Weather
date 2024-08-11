
package hrms.hrms.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModeOfTravel {

    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("details")
    @Expose
    private List<ModelOfTravelDetail> details = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ModelOfTravelDetail> getDetails() {
        return details;
    }

    public void setDetails(List<ModelOfTravelDetail> details) {
        this.details = details;
    }

}
