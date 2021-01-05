package com.abc.reak.syllabustracker;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceConfig {

    SharedPreferences sharedPreferences;
    Context context;

    public SharedPreferenceConfig(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.shared_preference),context.MODE_PRIVATE);
    }

    public void writeIsFirstTimeUser(boolean id){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.isFirstTime), String.valueOf(id ? 1 : 0));
        editor.commit();
    }

    public boolean readIsFirstTimeUser(){
        return sharedPreferences.getString(context.getResources().getString(R.string.isFirstTime), "1").equals("1");
    }

}
