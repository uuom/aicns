package com.asiainfo.aicns;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.asiainfo.aicns.common.api.Api;
import com.asiainfo.aicns.common.constant.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private static final int OBJ_SHOW_LOGIN_MESSAGE = 2;
    private static final int OBJ_LOGIN_SUCCESS = 3;

    AutoCompleteTextView username;
    EditText password;
    Button btn_signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        App.addActivity(this);

        username = (AutoCompleteTextView) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        btn_signIn = (Button) findViewById(R.id.btn_signIn);
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String usernameStr = username.getText().toString();
                final String passwordStr = password.getText().toString();
                Log.d("LoginActivity", "点击登录按钮");
                boolean isChecked = checkData(usernameStr, passwordStr);
                if (isChecked){
                    Log.d("LoginActivity", "isChecked="+isChecked+",usernameStr="+usernameStr + ",passwordStr="+passwordStr);
                    login(usernameStr, passwordStr);
                }
            }
        });
    }

    private void login(final String username, final String password){
        final String url = Api.REQUEST_BASE_URL + Api.LOGIN_URL;
        App.getOkHttpUtil().get(url + "&user="+username+"&passwd="+password, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("e" , e.toString());
                Message msg = new Message();
                msg.what = OBJ_SHOW_LOGIN_MESSAGE;
                msg.obj = "网络异常";
                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resp = response.body().string();
                Log.d("LoginActivity", "resp : "+ resp);
                try {
                    JSONObject jsonObject = new JSONObject(resp);
                    int code = jsonObject.getInt("code");

                    Log.d("LoginActivity", "code="+code);

                    Message msg = new Message();
                    msg.what = OBJ_SHOW_LOGIN_MESSAGE;
                    msg.obj = jsonObject.getString("message");
                    handler.sendMessage(msg);

                    if (code == 1){
                        Log.d("LoginActivity", "登录成功,sessionId="+jsonObject.getString("sessionId"));
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
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean checkData(String username, String password) {
        boolean result = true;
        if ("".equals(username) || "".equals(password)){
            result = false;
        }
        // 别的校验
        return result;
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case OBJ_SHOW_LOGIN_MESSAGE:
                    Toast.makeText(LoginActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case OBJ_LOGIN_SUCCESS:
                    LoginActivity.this.startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    break;

            }
        }
    };

    long currentTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (currentTime != 0){
                long waitTime = System.currentTimeMillis() - currentTime;
                if (waitTime < 2000){
                    App.clear();
                }else {
                    currentTime = 0;
                    Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                    currentTime = System.currentTimeMillis();
                }
            }else{
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                currentTime = System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
