package me.yusufeka.yusufdicoding1.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppPreference {

    SharedPreferences prefs;
    Context context;

    public AppPreference(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public void setDailyActive(Boolean input) {
        SharedPreferences.Editor editor = prefs.edit();
        String key = "daily";
        editor.putBoolean(key, input);
        editor.commit();
    }

    public Boolean getDailyActive() {
        String key = "daily";
        return prefs.getBoolean(key, true);
    }

    public void setReleaseActive(Boolean input) {
        SharedPreferences.Editor editor = prefs.edit();
        String key = "release";
        editor.putBoolean(key, input);
        editor.commit();
    }

    public Boolean getReleaseActive() {
        String key = "release";
        return prefs.getBoolean(key, true);
    }
}
