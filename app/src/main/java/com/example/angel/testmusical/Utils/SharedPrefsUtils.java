package com.example.angel.testmusical.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPrefsUtils {


    private static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    private static SharedPreferences.Editor getSharedPreferencesEditor(Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.edit();
    }

    public static String getString(Context context, int id_string_key, String defaultValue) {
        String key = context.getResources().getString(id_string_key);

        SharedPreferences sp = getSharedPreferences(context);

        return sp.getString(key, defaultValue);
    }


    public static void saveString(Context context, int id_string_key, String value) {
        String key = context.getResources().getString(id_string_key);
        SharedPreferences.Editor editor = getSharedPreferencesEditor(context);

        editor.putString(key, value);

        editor.commit();
    }


    public static Integer getInt(Context context, int id_int_key, Integer defaultValue) {
        String key = context.getResources().getString(id_int_key);
        SharedPreferences sp = getSharedPreferences(context);

        return sp.getInt(key, defaultValue);

    }


    public static void saveInt(Context context, int id_int_key, Integer value) {
        String key = context.getResources().getString(id_int_key);
        SharedPreferences.Editor editor = getSharedPreferencesEditor(context);
        editor.putInt(key, value);
        editor.commit();
    }


}
