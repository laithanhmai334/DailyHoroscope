package vn.edu.stu.boihangngay.services;

import vn.edu.stu.boihangngay.model.EphemerisDay;

import java.util.Arrays;
import java.util.List;
import vn.edu.stu.boihangngay.util.LuckyColorUtil;
public class HoroscopeEngine {

    // ====== NHÓM CUNG ======
    private static final List<String> FIRE = Arrays.asList("Aries", "Leo", "Sagittarius");
    private static final List<String> EARTH = Arrays.asList("Taurus", "Virgo", "Capricorn");
    private static final List<String> AIR = Arrays.asList("Gemini", "Libra", "Aquarius");
    private static final List<String> WATER = Arrays.asList("Cancer", "Scorpio", "Pisces");

    public static String generate(String userZodiac, EphemerisDay astro) {
        StringBuilder result = new StringBuilder();

        result.append(" Cung của bạn: ").append(userZodiac).append("\n\n");

        // 1. Mercury – tư duy & giao tiếp
        result.append(handleMercury(astro));

        // 2. Moon – cảm xúc
        result.append(handleMoon(userZodiac, astro));

        // 3. Venus – tình cảm
        result.append(handleVenus(userZodiac, astro));

        // 4. Mars – hành động
        result.append(handleMars(userZodiac, astro));
        String luckyColor =
                LuckyColorUtil.getLuckyColor(userZodiac, astro.moon);

        result.append("\nMàu sắc may mắn hôm nay: ")
                .append(luckyColor);
        return result.toString();
    }

    // ====== MERCURY ======
    private static String handleMercury(EphemerisDay astro) {
        if (astro.mercury_retrograde) {
            return "• Mercury nghịch hành: hôm nay bạn nên cẩn trọng khi giao tiếp, ký kết hoặc đưa ra quyết định quan trọng.\n";
        }
        return "• Giao tiếp hôm nay khá suôn sẻ, dễ trao đổi và học hỏi.\n";
    }

    // ====== MOON ======
    private static String handleMoon(String userZodiac, EphemerisDay astro) {
        if (WATER.contains(astro.moon)) {
            return "• Moon ở cung Nước: cảm xúc của bạn trở nên nhạy cảm hơn, dễ bị tác động bởi môi trường xung quanh.\n";
        }

        if (FIRE.contains(astro.moon)) {
            return "• Moon ở cung Lửa: tâm trạng sôi nổi, dễ có động lực để bắt đầu việc mới.\n";
        }

        if (EARTH.contains(astro.moon)) {
            return "• Moon ở cung Đất: bạn có xu hướng thực tế, muốn ổn định và kiểm soát mọi thứ.\n";
        }

        return "• Moon ở cung Khí: tâm trí hoạt động nhiều, dễ suy nghĩ và trao đổi ý tưởng.\n";
    }

    // ====== VENUS ======
    private static String handleVenus(String userZodiac, EphemerisDay astro) {

        if (astro.venus.equals(userZodiac)) {
            return "• Venus chiếu mạnh vào cung của bạn: tình cảm và các mối quan hệ cá nhân có dấu hiệu tích cực.\n";
        }

        if (sameElement(userZodiac, astro.venus)) {
            return "• Venus ở cung cùng nguyên tố: dễ có sự đồng cảm và hòa hợp trong tình cảm.\n";
        }

        return "• Tình cảm hôm nay ở mức ổn định, nên tránh kỳ vọng quá cao.\n";
    }

    // ====== MARS ======
    private static String handleMars(String userZodiac, EphemerisDay astro) {

        if (astro.mars.equals(userZodiac)) {
            return "• Mars kích hoạt cung của bạn: năng lượng dồi dào, phù hợp để hành động và giải quyết việc tồn đọng.\n";
        }

        if (FIRE.contains(astro.mars)) {
            return "• Mars ở cung Lửa: dễ hành động nhanh, cần tránh nóng vội.\n";
        }

        if (EARTH.contains(astro.mars)) {
            return "• Mars ở cung Đất: hành động chậm nhưng chắc, phù hợp làm việc dài hạn.\n";
        }

        return "• Năng lượng hôm nay ở mức vừa phải, nên cân bằng giữa hành động và nghỉ ngơi.\n";
    }

    // ====== TIỆN ÍCH ======
    private static boolean sameElement(String zodiac1, String zodiac2) {
        return (FIRE.contains(zodiac1) && FIRE.contains(zodiac2)) ||
                (EARTH.contains(zodiac1) && EARTH.contains(zodiac2)) ||
                (AIR.contains(zodiac1) && AIR.contains(zodiac2)) ||
                (WATER.contains(zodiac1) && WATER.contains(zodiac2));
    }
}
