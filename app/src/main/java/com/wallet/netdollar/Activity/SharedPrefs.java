package com.wallet.netdollar.Activity;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {
    final static String fileName="login";
   static Context context;

    SharedPrefs(Context context) {
        this.context = context;
    }


    public static String readShared(Context ctx, String setname, String defaultvalue) {
        SharedPreferences sharedPref=ctx.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        return sharedPref.getString(setname,defaultvalue);
    }

    public static void saveObjectShared(Context ctx, String setname, Object setvalue) {
        SharedPreferences sharedPre=ctx.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPre.edit();
        editor.putString(setname,setvalue.toString());
        editor.apply();
    }
    public static void saveShared(Context ctx, String setname, String setvalue) {
        SharedPreferences sharedPre=ctx.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPre.edit();
        editor.putString(setname,setvalue);
        editor.apply();
    }
}
