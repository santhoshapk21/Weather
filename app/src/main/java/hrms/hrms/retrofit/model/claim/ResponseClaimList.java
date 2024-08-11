package hrms.hrms.retrofit.model.claim;


import java.util.List;

/**
 * Created by yudiz on 08/03/18.
 */

public class ResponseClaimList {

    public String Message;
    public List<Details> details;

    public List<Details> getDetails() {
        return details;
    }

    public void setDetails(List<Details> details) {
        this.details = details;
    }

    public class Details {
        public String Id;
        public String EmployeeID;
        public String ClaimType;
        public String CityType;
        public String ApplicationDate;
        public String FromPlace;
        public String ToPlace;
        public String PurposeOfVisit;
        public String FromDate;
        public String ToDate;
        public String TravelDuration;
        public String TotalExpenseAmount;
        public String Status;
        public String OtherExpenceMonth;
        public String OtherExpenceYear;
    }
}
