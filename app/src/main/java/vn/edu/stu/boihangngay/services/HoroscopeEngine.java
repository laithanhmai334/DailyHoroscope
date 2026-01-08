package vn.edu.stu.boihangngay.services;

import vn.edu.stu.boihangngay.model.EphemerisDay;
import vn.edu.stu.boihangngay.model.HoroscopeResult;
import vn.edu.stu.boihangngay.model.NatalChart;

import java.util.Arrays;
import java.util.List;

public class HoroscopeEngine {
    public enum Aspect {
        CONJUNCTION, // Trùng
        SEXTILE,     // Lục hợp (cách 2 cung)
        SQUARE,      // Vuông (cách 3 cung)
        TRINE,       // Tam hợp (cách 4 cung)
        OPPOSITION,  // Đối đỉnh (cách 6 cung)
        NONE
    }
    private static final List<String> ZODIAC_ORDER = Arrays.asList(
            "Aries", "Taurus", "Gemini", "Cancer",
            "Leo", "Virgo", "Libra", "Scorpio",
            "Sagittarius", "Capricorn", "Aquarius", "Pisces"
    );
    private static Aspect getAspect(String natal, String transit) {
        int i1 = ZODIAC_ORDER.indexOf(natal);
        int i2 = ZODIAC_ORDER.indexOf(transit);

        if (i1 == -1 || i2 == -1) return Aspect.NONE;

        int diff = Math.abs(i1 - i2);
        diff = Math.min(diff, 12 - diff);

        switch (diff) {
            case 0: return Aspect.CONJUNCTION;
            case 2: return Aspect.SEXTILE;
            case 3: return Aspect.SQUARE;
            case 4: return Aspect.TRINE;
            case 6: return Aspect.OPPOSITION;
            default: return Aspect.NONE;
        }
    }
    private static String aspectMeaning(
            String planet,
            Aspect aspect,
            String good,
            String bad
    ) {
        switch (aspect) {
            case CONJUNCTION:
                return "• " + planet + " trùng góc: tác động rất mạnh, dễ bị kích hoạt rõ rệt.\n";

            case SEXTILE:
                return "• " + planet + " lục hợp: " + good + "\n";

            case TRINE:
                return "• " + planet + " tam hợp: " + good + "\n";

            case SQUARE:
                return "• " + planet + " vuông góc: " + bad + "\n";

            case OPPOSITION:
                return "• " + planet + " đối đỉnh: mâu thuẫn nội tâm, cần cân bằng.\n";

            default:
                return "• " + planet + ": ảnh hưởng nhẹ hoặc trung tính.\n";
        }
    }
    private static String handleSun(String natalSun, EphemerisDay astro) {
        Aspect a = getAspect(natalSun, astro.sun);

        return aspectMeaning(
                "Bản ngã",
                a,
                "bạn tự tin hơn, dễ thể hiện bản thân",
                "dễ mệt mỏi, nghi ngờ chính mình"
        );
    }
    private static String handleMoon(String natalMoon, EphemerisDay astro) {
        Aspect a = getAspect(natalMoon, astro.moon);

        return aspectMeaning(
                "Cảm xúc",
                a,
                "cảm xúc ổn định, dễ chịu",
                "dễ căng thẳng, nhạy cảm, mood thất thường"
        );
    }

    // ====== NHÓM CUNG ======

    public static HoroscopeResult generate(NatalChart natal, EphemerisDay astro) {

        HoroscopeResult r = new HoroscopeResult();

        // 🌞 Bản ngã
        r.banNga = handleSun(natal.sun, astro);

        // 🌙 Cảm xúc
        r.camXuc = handleMoon(natal.moon, astro);

        // ♀ Tình cảm
        r.tinhCam = handleVenus(natal.venus, astro);

        // ♂ Động lực
        r.dongLuc = handleMars(natal.mars, astro);

        // ☿ Học tập / tư duy
        r.hocTap = handleMercury(natal.mercury, astro);

        // ♃ May mắn
        r.mayMan = handleJupiter(natal.jupiter, astro);

        // ♄ Kỷ luật
        r.kyLuat = handleSaturn(natal.saturn, astro);

        return r;
    }



    // ====== MOON ======

    // ====== VENUS ======
    private static String handleMercury(String natalMercury, EphemerisDay astro) {
        Aspect a = getAspect(natalMercury, astro.mercury);

        String base;

        switch (a) {
            case CONJUNCTION:
                base = "• Đầu óc hoạt động mạnh, suy nghĩ liên tục.\n";
                break;
            case SEXTILE:
                base = "• Tư duy linh hoạt, giao tiếp trôi chảy.\n";
                break;
            case TRINE:
                base = "• Đầu óc minh mẫn, học nhanh, nói chuyện dễ hiểu.\n";
                break;
            case SQUARE:
                base = "• Dễ rối suy nghĩ, hiểu lầm khi giao tiếp.\n";
                break;
            case OPPOSITION:
                base = "• Mâu thuẫn giữa lý trí và ý kiến người khác.\n";
                break;
            default:
                base = "• Tư duy hôm nay ở mức trung bình.\n";
        }

        // 🔁 chồng hiệu ứng retrograde
        if (astro.mercury_retrograde) {
            base += "  ⚠ Nên kiểm tra kỹ thông tin, tránh vội kết luận.\n";
        }

        return base;
    }

    private static String handleVenus(String natalVenus, EphemerisDay astro) {
        Aspect a = getAspect(natalVenus, astro.venus);

        return aspectMeaning(
                "Venus",
                a,
                "dễ hòa hợp, tình cảm tích cực",
                "dễ thất vọng, kỳ vọng lệch"
        );
    }
    private static String handleMars(String natalMars, EphemerisDay astro) {
        Aspect a = getAspect(natalMars, astro.mars);

        return aspectMeaning(
                "Động lực",
                a,
                "nhiều năng lượng, làm việc hiệu quả",
                "dễ nóng nảy, hành động vội"
        );
    }
    private static String handleJupiter(String natalJupiter, EphemerisDay astro) {
        Aspect a = getAspect(natalJupiter, astro.jupiter);

        return aspectMeaning(
                "May mắn",
                a,
                "dễ gặp cơ hội, tư duy tích cực",
                "kỳ vọng quá cao, chủ quan"
        );
    }
    private static String handleSaturn(String natalSaturn, EphemerisDay astro) {
        Aspect a = getAspect(natalSaturn, astro.saturn);

        return aspectMeaning(
                "Kỷ luật",
                a,
                "kỷ luật tốt, làm việc nghiêm túc",
                "cảm giác áp lực, bị giới hạn"
        );
    }




}
