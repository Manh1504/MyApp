package com.example.music;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;

import java.util.Locale;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        // Lắng nghe khi người dùng chọn ngôn ngữ
        ListPreference languagePref = findPreference("language");
        if (languagePref != null) {
            languagePref.setOnPreferenceChangeListener((preference, newValue) -> {
                // Lưu ngôn ngữ vào SharedPreferences
                SharedPreferences prefs = requireContext()
                        .getSharedPreferences("music_prefs", Context.MODE_PRIVATE);
                prefs.edit().putString("language", newValue.toString()).apply();

                // Áp dụng ngôn ngữ và restart Activity
                applyLanguage(newValue.toString());
                return true;
            });
        }
    }

    private void applyLanguage(String langCode) {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);

        Resources resources = requireContext().getResources();
        Configuration config = new Configuration(resources.getConfiguration());
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());

        // Restart SettingsActivity để giao diện cập nhật
        requireActivity().recreate();
    }
}