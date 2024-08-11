package hrms.hrms.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yudiz on 23/08/16.
 */
public class Claim {

    private String type, date, status, approval, submitted;
    private List<ClaimDetails> claimDetailses;

    public String getApproval() {
        return approval;
    }

    public Claim(String type, String date, String status, String approval, String submitted) {
        claimDetailses = new ArrayList<>();
        this.type = type;
        this.date = date;
        this.status = status;
        this.approval = approval;
        this.submitted = submitted;
    }

    public List<ClaimDetails> getClaimDetails() {
        return claimDetailses;
    }

    public void setClaimDetails(List<ClaimDetails> claimDetailses) {
        this.claimDetailses = claimDetailses;
    }

    public Claim() {
        claimDetailses = new ArrayList<>();
    }

    public void setApproval(String approval) {
        this.approval = approval;
    }

    public String getSubmitted() {
        return submitted;
    }

    public void setSubmitted(String submitted) {
        this.submitted = submitted;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
