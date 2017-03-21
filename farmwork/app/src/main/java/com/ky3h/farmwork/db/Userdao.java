package com.ky3h.farmwork.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ky3h.farmwork.bean.User;
import com.yolanda.nohttp.db.Where;

/**
 * Created by lipengcheng on 2017/3/14.
 */

public class Userdao {
    private Userdao userdao;

    public Userdao getUerDao() {
        if (userdao == null) {
            userdao = new Userdao();
            return userdao;
        } else {
            return userdao;
        }
    }

    public boolean addUser(User user, Context context) {
        DbHelper helper = new DbHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", user.getId());//memberId
        values.put("userName", user.getUserName());//手机号码
        values.put("gender", user.getGender());//个人资料性别
        values.put("birthDate", user.getBirthDate());//个人资料生日
        values.put("name", user.getName());//用户昵称
        values.put("status", user.getStatus());
        values.put("identityType", user.getIdentityType());//证件类型
        values.put("idNumber", user.getIdNumber());//身份证
        values.put("address", user.getAddress());//地址
        values.put("phone", user.getPhone());//固定电话
        values.put("memberChildId", user.getMemberChildId());
        values.put("memberImage", user.getMemberImage());//个人头像
        values.put("isMedicare", user.getIsMedicare());//是否有医保
        values.put("JSESSIONID", user.getJSESSIONID());
        values.put("token", user.getToken());
        long result = sqLiteDatabase.insert("user", null, values);
        if (result != -1) {
            return true;
        }
        return false;
    }

    public boolean deleteUser(User user, Context context) {
        DbHelper helper = new DbHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        int result = sqLiteDatabase.delete("user", "userName=?", new String[]{user.getUserName()});
        if (result == 0) {
            return false;
        }
        return true;
    }

    public boolean updateUser(User user, Context context) {
        DbHelper helper = new DbHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", user.getId());//memberId
        values.put("userName", user.getUserName());//手机号码
        values.put("gender", user.getGender());//个人资料性别
        values.put("birthDate", user.getBirthDate());//个人资料生日
        values.put("name", user.getName());//用户昵称
        values.put("status", user.getStatus());
        values.put("identityType", user.getIdentityType());//证件类型
        values.put("idNumber", user.getIdNumber());//身份证
        values.put("address", user.getAddress());//地址
        values.put("phone", user.getPhone());//固定电话
        values.put("memberChildId", user.getMemberChildId());
        values.put("memberImage", user.getMemberImage());//个人头像
        values.put("isMedicare", user.getIsMedicare());//是否有医保
        values.put("JSESSIONID", user.getJSESSIONID());
        values.put("token", user.getToken());
        int result = sqLiteDatabase.update("user", values, "userName=?", new String[]{user.getUserName()});
        if (result <= 0) {
            return false;
        }
        return true;
    }

    public User queryUser(String userName, Context context) {
        DbHelper helper = new DbHelper(context);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        User user = null;
        Cursor cursor = sqLiteDatabase.rawQuery("select * from user where userName=?", new String[]{userName}, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String username = cursor.getString(cursor.getColumnIndex("userName"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String gender = cursor.getString(cursor.getColumnIndex("gender"));
                String birthDate = cursor.getString(cursor.getColumnIndex("birthDate"));//个人资料生日
                int status = cursor.getInt(cursor.getColumnIndex("status"));
                String identityType = cursor.getString(cursor.getColumnIndex("identityType"));//证件类型
                String idNumber = cursor.getString(cursor.getColumnIndex("idNumber"));//身份证
                String address = cursor.getString(cursor.getColumnIndex("address"));//地址
                String phone = cursor.getString(cursor.getColumnIndex("phone"));//固定电话
                int memberChildId = cursor.getInt(cursor.getColumnIndex("memberChildId"));
                String memberImage = cursor.getString(cursor.getColumnIndex("memberImage"));//个人头像
                String isMedicare = cursor.getString(cursor.getColumnIndex("isMedicare"));//是否有医保
                String JSESSIONID = cursor.getString(cursor.getColumnIndex("JSESSIONID"));
                String token = cursor.getString(cursor.getColumnIndex("token"));
                user = new User(id, username, name, gender, birthDate, status, identityType, idNumber, address, phone, memberChildId, memberImage, isMedicare, JSESSIONID, token);

            }
        }

        return user;
    }
}
