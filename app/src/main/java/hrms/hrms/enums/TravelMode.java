package hrms.hrms.enums;

/**
 * Created by Yudiz on 12/10/16.
 */
public enum TravelMode {
    PLANE("0"), BUS("2"), TRAIN("1"), BIKECAR("3"),DEFAULT("-1");
    final String val;

    TravelMode(String pos) {
        val = pos;
    }
}
