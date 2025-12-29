package vn.edu.stu.boihangngay.model;

public class AstroData {
    public String date;
    public String moon;
    public Mercury mercury;
    public String venus;
    public String mars;

    public static class Mercury {
        public String sign;
        public boolean retrograde;
    }
}
