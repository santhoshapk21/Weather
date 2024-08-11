package hrms.hrms.enums;

/**
 * Created by Yudiz on 07/10/16.
 */
public enum CalenderRequest {
    INPROGRESS("inprogress"), REJECTED("rejected"), ACCEPTED("accepted");
    public final String value;

    CalenderRequest(String value) {
        this.value = value;
    }


}

