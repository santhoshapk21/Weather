
package hrms.hrms.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExpenseListDetail {

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("EligibleAmount")
    @Expose
    private String eligibleAmount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEligibleAmount() {
        return eligibleAmount;
    }

    public void setEligibleAmount(String eligibleAmount) {
        this.eligibleAmount = eligibleAmount;
    }

}
