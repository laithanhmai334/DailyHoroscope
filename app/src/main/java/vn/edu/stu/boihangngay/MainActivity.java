package vn.edu.stu.boihangngay;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.Calendar;
import java.util.Map;
import vn.edu.stu.boihangngay.model.EphemerisDay;
import vn.edu.stu.boihangngay.model.HoroscopeResult;
import vn.edu.stu.boihangngay.model.NatalChart;
import vn.edu.stu.boihangngay.services.EphemerisLoader;
import vn.edu.stu.boihangngay.services.HoroscopeEngine;
import vn.edu.stu.boihangngay.util.ZodiacUtil;

public class MainActivity extends AppCompatActivity {

    Map<String, EphemerisDay> ephemerisMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Đã sửa lỗi R.id.main ở đây bằng cách thêm ID vào XML hoặc kiểm tra kỹ
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ephemerisMap = EphemerisLoader.load(this);
        onControls();
    }

    private void onControls() {
        Button btnPickDate = findViewById(R.id.btnPickDate);
        Button btnBoi = findViewById(R.id.btnBoi);
        TextView txtBirthday = findViewById(R.id.txtBirthday);

        // Khai báo các TextView trong Card
        TextView txtDongLuc = findViewById(R.id.txtDongLuc);
        TextView txtTinhCam = findViewById(R.id.txtTinhCam);
        TextView txtHocTap = findViewById(R.id.txtHocTap);
        TextView txtKyLuat = findViewById(R.id.txtKiLuat);
        TextView txtBanNga = findViewById(R.id.txtBanNga);
        TextView txtMayMan = findViewById(R.id.txtMayMan);
        TextView txtCamXuc = findViewById(R.id.txtCamXuc);
        // Nếu bạn đã thêm card Cam Xuc và Ki Luat vào XML thì khai báo thêm ở đây

        final int[] birthDay = new int[1];
        final int[] birthMonth = new int[1];
        final int[] birthYear = new int[1];

        btnPickDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(
                    this,
                    (view, year, month, dayOfMonth) -> {
                        birthDay[0] = dayOfMonth;
                        birthMonth[0] = month + 1;
                        birthYear[0] = year;
                        txtBirthday.setText("Ngày đã chọn: " + dayOfMonth + "/" + (month + 1) + "/" + year);
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            dialog.show();
        });

        btnBoi.setOnClickListener(v -> {
            if (birthDay[0] == 0) {
                txtDongLuc.setText("⚠️ Hãy chọn ngày sinh!");
                return;
            }

            String birthDateStr = birthYear[0] + "-" + String.format("%02d", birthMonth[0]) + "-" + String.format("%02d", birthDay[0]);
            EphemerisDay birthData = ephemerisMap.get(birthDateStr);

            if (birthData == null) {
                txtDongLuc.setText("❌ Không có dữ liệu chiêm tinh");
                return;
            }

            // Tạo biểu đồ ngày sinh
            NatalChart natal = new NatalChart();
            natal.sun = ZodiacUtil.getZodiac(birthDay[0], birthMonth[0]);
            natal.moon = birthData.moon;
            natal.mercury = birthData.mercury;
            natal.venus = birthData.venus;
            natal.mars = birthData.mars;
            natal.jupiter = birthData.jupiter;
            natal.saturn = birthData.saturn;

            String today = java.time.LocalDate.now().toString();
            EphemerisDay todayData = ephemerisMap.get(today);

            if (todayData != null) {
                // Giả sử HoroscopeEngine.generate trả về một chuỗi dài
                // Để chia về các Card, bạn có thể chỉnh lại Engine để trả về Object
                // Hoặc tạm thời gán các giá trị mẫu để xem giao diện:

                HoroscopeResult result = HoroscopeEngine.generate(natal, todayData);

                txtBanNga.setText(result.banNga);
                txtCamXuc.setText(result.camXuc);
                txtTinhCam.setText(result.tinhCam);
                txtDongLuc.setText(result.dongLuc);
                txtHocTap.setText(result.hocTap);
                txtMayMan.setText(result.mayMan);
                txtKyLuat.setText(result.kyLuat);



            }
        });
    }
}