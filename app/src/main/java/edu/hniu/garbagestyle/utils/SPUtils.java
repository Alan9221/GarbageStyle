package edu.hniu.garbagestyle.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * SharedPreferences工具类
 *
 * @author llw
 */
public class SPUtils {
    private static final String NAME = "config";

    public static void putBoolean(String key, boolean value, Context context) {
        SharedPreferences sp = context.getSharedPreferences(NAME,
                Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(String key, boolean defValue, Context context) {
        SharedPreferences sp = context.getSharedPreferences(NAME,
                Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    public static void putString(String key, String value, Context context) {
        SharedPreferences sp = context.getSharedPreferences(NAME,
                Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    public static String getString(String key, String defValue, Context context) {
        if (context != null) {
            SharedPreferences sp = context.getSharedPreferences(NAME,
                    Context.MODE_PRIVATE);
            return sp.getString(key, defValue);
        }
        return "";

    }

    public static void putInt(String key, int value, Context context) {
        SharedPreferences sp = context.getSharedPreferences(NAME,
                Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).commit();
    }


    public static int getInt(String key, int defValue, Context context) {
        SharedPreferences sp = context.getSharedPreferences(NAME,
                Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }

    public static void putLong(String key, long value, Context context) {
        SharedPreferences sp = context.getSharedPreferences(NAME,
                Context.MODE_PRIVATE);
        sp.edit().putLong(key, value).commit();
    }


    public static long getLong(String key, long defValue, Context context) {
        SharedPreferences sp = context.getSharedPreferences(NAME,
                Context.MODE_PRIVATE);
        return sp.getLong(key, defValue);
    }

    public static void remove(String key, Context context) {
        SharedPreferences sp = context.getSharedPreferences(NAME,
                Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }

    //创建存储用户名 密码至sp文件
    public static void saveToSP(Context mContext,String sp_loginname,String sp_loginpwd){
        //获得一个sp存储对象  (sp名字
        SharedPreferences sp=mContext.getSharedPreferences("sp_UserInfo",Context.MODE_PRIVATE);
        //获得sp文件编辑器
        SharedPreferences.Editor editor=sp.edit();
        //往sp文件中存储数据
        editor.putString("sp_name",sp_loginname);
        editor.putString("sp_pwd",sp_loginpwd);
        editor.putBoolean("isLogin",true);
        //提交保存
        editor.commit();
    }

    public static void saveToNcSp(Context mContext,String sp_nc){
        //获得一个sp存储对象  (sp名字
        SharedPreferences sp=mContext.getSharedPreferences("sp_UserInfo",Context.MODE_PRIVATE);
        //获得sp文件编辑器
        SharedPreferences.Editor editor=sp.edit();
        //往sp文件中存储数据
        editor.putString("sp_nc",sp_nc);
        //提交保存
        editor.commit();
    }

    /*创建读取sp文件中的用户名 密码,HashMap  Object是对象类型*/
    public static HashMap<String,Object> readToSP(Context mContext){
        //初始化map对象
        HashMap<String ,Object> map=new HashMap<>();
        //获得一个sp存储对象  (sp名字
        SharedPreferences sp=mContext.getSharedPreferences("sp_UserInfo",Context.MODE_PRIVATE);
        //读取数据
        String hs_name=sp.getString("sp_name","");
        String hs_pwd=sp.getString("sp_pwd","");
        String hs_nc=sp.getString("sp_nc","");
        boolean isLogin=(Boolean)sp.getBoolean("isLogin",true);
        //在将读取出来的用户名 密码保存至map
        map.put("mp_name",hs_name);
        map.put("mp_pwd",hs_pwd);
        map.put("isLogin",isLogin);
        map.put("mp_nc",hs_nc);
        //再将map返回
        return  map;

    }

    /**
     * 从SharedPreferences中读取登录用户名
     */
    public static String readLoginUserName(Context context){
        SharedPreferences sp=context.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String userName=sp.getString("loginUserName", "");
        return userName;
    }

}
