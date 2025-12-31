package vn.edu.stu.boihangngay;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Map;

import vn.edu.stu.boihangngay.model.EphemerisDay;
import vn.edu.stu.boihangngay.model.NatalChart;
import vn.edu.stu.boihangngay.services.EphemerisLoader;
import vn.edu.stu.boihangngay.services.HoroscopeEngine;
import vn.edu.stu.boihangngay.util.ZodiacUtil;

public class MainActivity extends AppCompatActivity {

    Map<String, EphemerisDay> ephemerisMap;

    int birthDay, birthMonth, birthYear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ephemerisMap = EphemerisLoader.load(this);

        if (ephemerisMap == null) {
            Log.e("EPHEMERIS", "Không load được ephemeris");
        }

        String today = java.time.LocalDate.now().toString();
        EphemerisDay todayData = ephemerisMap.get(today);

        Log.d("EPHEMERIS", "Moon: " + todayData.moon);
        onControls();

    }


    private void onControls() {
        Button btnPickDate = findViewById(R.id.btnPickDate);
        TextView txtBirthday = findViewById(R.id.txtBirthday);
        TextView txtResult = findViewById(R.id.txtResult);
        Button btnBoi = findViewById(R.id.btnBoi);
        final int[] birthDay = new int[1];
        final int[] birthMonth = new int[1];
        final int[] birthYear = new int[1];

        btnPickDate.setOnClickListener(v -> {

            Calendar calendar = Calendar.getInstance();

            DatePickerDialog dialog = new DatePickerDialog(
                    this,
                    (view, year, month, dayOfMonth) -> {
                        birthDay[0] = dayOfMonth;
                        birthMonth[0] = month + 1; // month bắt đầu từ 0
                        birthYear[0] = year;

                        txtBirthday.setText(
                                dayOfMonth + "/" + (month + 1) + "/" + year
                        );
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );

            dialog.show();
        });

        // ⭐ NÚT BÓI ⭐
        btnBoi.setOnClickListener(v -> {

            if (birthDay[0] == 0) {
                txtResult.setText("⚠️ Vui lòng chọn ngày sinh trước");
                return;
            }

            String birthDate = birthYear[0] + "-"
                    + String.format("%02d", birthMonth[0])
                    + "-"
                    + String.format("%02d", birthDay[0]);
            EphemerisDay birthData = ephemerisMap.get(birthDate);
            if (birthData == null) {
                txtResult.setText("❌ Không có dữ liệu chiêm tinh cho ngày sinh");
                return;
            }

            NatalChart natal = new NatalChart();
// ☀ Sun: từ ZodiacUtil
            natal.sun = ZodiacUtil.getZodiac(birthDay[0], birthMonth[0]);
// 🌙 Moon + các hành tinh: từ ephemeris ngày sinh
            natal.moon = birthData.moon;
            natal.mercury = birthData.mercury;
            natal.venus = birthData.venus;
            natal.mars = birthData.mars;
            natal.jupiter = birthData.jupiter;
            natal.saturn = birthData.saturn;
            String today = java.time.LocalDate.now().toString();
            EphemerisDay todayData = ephemerisMap.get(today);

            if (todayData == null) {
                txtResult.setText("❌ Không có dữ liệu ephemeris hôm nay");
                return;
            }

            String horoscope =
                    HoroscopeEngine.generate(natal, todayData);

            txtResult.setText(horoscope);
        });

    }


}