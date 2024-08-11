package hrms.hrms.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yudiz on 14/10/16.
 */
public class Message {

    private String status = "";
    private String name = "";
    private String message = "";

    public Message(String status, String name, String message) {
        this.status = status;
        this.name = name;
        this.message = message;
    }

    public Message() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
