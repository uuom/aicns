package com.asiainfo.aicns;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.asiainfo.aicns.common.api.Api;
import com.asiainfo.aicns.common.constant.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FirstActivity extends AppCompatActivity {

    private static final int OBJ_SHOW_LOGIN_MESSAGE = 2;
    private static final int OBJ_LOGIN_SUCCESS = 3;

    private static final int OBJ_WAIT= 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        App.addActivity(this);

        //check update
        PackageManager manager = this.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(this.getPackageName(), 0);
            int version = info.versionCode;
            checkVersion(version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        //check login
        SharedPreferences spf = getSharedPreferences(Constant.SHARED_PREFERENCES_KEY, MODE_PRIVATE);
        final String oldUsername = spf.getString(Constant.SHARED_PREFERENCES_NAME_USERNAME, null);
        final String oldPassword = spf.getString(Constant.SHARED_PREFERENCES_NAME_PASSWORD, null);
        Log.d("LoginActivity", "oldUsername=" + oldUsername + ",oldPassword=" + oldPassword);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);

                    Message msg = new Message();
                    msg.what = OBJ_WAIT;
                    msg.obj = new String[]{oldUsername, oldPassword};
                    handler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void checkVersion(int version) {
        String url = Api.REQUEST_BASE_URL + Api.CHECK_APP_UPDATE_URL;

    }

    private void login(final String username, final String password){
        final String url = Api.REQUEST_BASE_URL + Api.LOGIN_URL;
        App.getOkHttpUtil().get(url + "&user="+username+"&passwd="+password, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = new Message();
                msg.what = OBJ_SHOW_LOGIN_MESSAGE;
                msg.obj = "服务器忙";
                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resp = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(resp);
                    int code = jsonObject.getInt("code");

                    if (code == 1){
                        SharedPreferences spf = getSharedPreferences(Constant.SHARED_PREFERENCES_KEY, MODE_PRIVATE);
                        SharedPreferences.Editor editor = spf.edit();
                        editor.putString(Constant.SHARED_PREFERENCES_NAME_JSESSIONID, jsonObject.getString("sessionId")+"");
                        editor.putString(Constant.SHARED_PREFERENCES_NAME_USERNAME, username);
                        editor.putString(Constant.SHARED_PREFERENCES_NAME_PASSWORD, password);
                        editor.commit();
                        Message msg1 = new Message();
                        msg1.what = OBJ_LOGIN_SUCCESS;
                        handler.sendMessage(msg1);
                    }
                } catch (JSONException e) {
                    Message msg = new Message();
                    msg.what = OBJ_SHOW_LOGIN_MESSAGE;
                    msg.obj = "服务器忙";
                    handler.sendMessage(msg);
                }
            }
        });
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case OBJ_SHOW_LOGIN_MESSAGE:
                    Toast.makeText(FirstActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case OBJ_LOGIN_SUCCESS:
                    FirstActivity.this.startActivity(new Intent(FirstActivity.this, MainActivity.class));
                    break;
                case OBJ_WAIT:
                    String[] arr = (String[]) msg.obj;
                    String oldUsername = arr[0];
                    String oldPassword = arr[1];
                    //登录
                    if(oldUsername != null && oldPassword != null){
                        Log.d("MainActivity", "使用已存在的用户名密码登录");
                        login(oldUsername, oldPassword);
                    }else{
                        //open loginView
                        FirstActivity.this.startActivity(new Intent(FirstActivity.this, LoginActivity.class));
                    }
                    break;
            }
        }
    };
}
