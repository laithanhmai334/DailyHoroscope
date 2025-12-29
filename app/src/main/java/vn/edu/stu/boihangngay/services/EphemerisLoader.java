package vn.edu.stu.boihangngay.services;

import android.content.Context;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Map;

import vn.edu.stu.boihangngay.model.EphemerisDay;

public class EphemerisLoader {
    public static Map<String, EphemerisDay> load(Context context) {
        try {
            InputStream is = context.getAssets().open("ephemeris_2025.json");
            InputStreamReader reader = new InputStreamReader(is);

            Type type = new TypeToken<Map<String, EphemerisDay>>() {}.getType();
            return new Gson().fromJson(reader, type);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
