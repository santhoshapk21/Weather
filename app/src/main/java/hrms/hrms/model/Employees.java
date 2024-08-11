package hrms.hrms.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yudiz on 14/10/16.
 */
public class Employees {

    private String index;
    private String name;
    private String designation;
    private String imgPAth;

    public Employees(String index, String name) {
        this.index = index;
        this.name = name;
    }


    public Employees(String index, String designation, String name) {
        this.index = index;
        this.designation = designation;
        this.name = name;
    }

    public void setIndex(String index) {

        this.index = index;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public static List<Employees> getEnglishemployees() {
        List<Employees> employeess = new ArrayList<>();
        employeess.add(new Employees("A", "Abbey"));
        employeess.add(new Employees("A", "Alex"));
        employeess.add(new Employees("A", "Amy"));
        employeess.add(new Employees("A", "Anne"));
        employeess.add(new Employees("B", "Betty"));
        employeess.add(new Employees("B", "Bob"));
        employeess.add(new Employees("B", "Brian"));
        employeess.add(new Employees("C", "Carl"));
        employeess.add(new Employees("C", "Candy"));
        employeess.add(new Employees("C", "Carlos"));
        employeess.add(new Employees("C", "Charles"));
        employeess.add(new Employees("C", "Christina"));
        employeess.add(new Employees("D", "David"));
        employeess.add(new Employees("D", "Daniel"));
        employeess.add(new Employees("E", "Elizabeth"));
        employeess.add(new Employees("E", "Eric"));
        employeess.add(new Employees("E", "Eva"));
        employeess.add(new Employees("F", "Frances"));
        employeess.add(new Employees("F", "Frank"));
        employeess.add(new Employees("I", "Ivy"));
        employeess.add(new Employees("J", "James"));
        employeess.add(new Employees("J", "John"));
        employeess.add(new Employees("J", "Jessica"));
        employeess.add(new Employees("K", "Karen"));
        employeess.add(new Employees("K", "Karl"));
        employeess.add(new Employees("K", "Kim"));
        employeess.add(new Employees("L", "Leon"));
        employeess.add(new Employees("L", "Lisa"));
        employeess.add(new Employees("P", "Paul"));
        employeess.add(new Employees("P", "Peter"));
        employeess.add(new Employees("S", "Sarah"));
        employeess.add(new Employees("S", "Steven"));
        employeess.add(new Employees("R", "Robert"));
        employeess.add(new Employees("R", "Ryan"));
        employeess.add(new Employees("T", "Tom"));
        employeess.add(new Employees("T", "Tony"));
        employeess.add(new Employees("W", "Wendy"));
        employeess.add(new Employees("W", "Will"));
        employeess.add(new Employees("W", "William"));
        employeess.add(new Employees("Z", "Zoe"));
        return employeess;
    }
}
