package com.misht.shakepaws.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Heikki Lintulahti on 5/5/2018.
 */

public class PrefManager {
    //shared pref FILE name
    private static final String PREF_NAME = "welcome";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context mContext;
    //shared pref mode
    int PRIVATE_MODE = 0;

    public PrefManager(Context context) {
        this.mContext = context;
        pref = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setIsFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }
}
