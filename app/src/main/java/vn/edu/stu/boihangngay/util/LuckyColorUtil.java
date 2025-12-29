package vn.edu.stu.boihangngay.util;

import java.util.Arrays;
import java.util.List;

public class LuckyColorUtil {

    private static final List<String> FIRE = Arrays.asList("Aries", "Leo", "Sagittarius");
    private static final List<String> EARTH = Arrays.asList("Taurus", "Virgo", "Capricorn");
    private static final List<String> AIR = Arrays.asList("Gemini", "Libra", "Aquarius");
    private static final List<String> WATER = Arrays.asList("Cancer", "Scorpio", "Pisces");

    public static String getLuckyColor(String userZodiac, String moonSign) {

        // Ưu tiên Moon (năng lượng trong ngày)
        if (WATER.contains(moonSign)) {
            return "Xanh dương đậm / Tím";
        }

        if (FIRE.contains(moonSign)) {
            return "Đỏ / Cam";
        }

        if (EARTH.contains(moonSign)) {
            return "Xanh lá / Nâu";
        }

        // Moon thuộc Air
        return "Trắng / Xanh nhạt";
    }
}
