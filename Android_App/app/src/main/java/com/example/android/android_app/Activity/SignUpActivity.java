package com.example.android.android_app.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.android_app.Class.RequestServer;
import com.example.android.android_app.Class.RequestServerInterface;
import com.example.android.android_app.R;

public class SignUpActivity extends AppCompatActivity {
    private static final int SIGNUP_OK = 0;
    private static final int SIGNUP_FAILED = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // set ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.signUpToolBar);
        setSupportActionBar(toolbar);
        final Button sign_up_btn = (Button)findViewById(R.id.sign_up_btn);

        final TextInputLayout textInputLayout = (TextInputLayout) findViewById(R.id.phone_num);
        EditText phone_num = textInputLayout.getEditText();

        phone_num.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.length()==0) {
                    textInputLayout.setError("手机号码不可为空");
                    textInputLayout.setErrorEnabled(true);
                    sign_up_btn.setEnabled(false);
                } else {
                    textInputLayout.setErrorEnabled(false);
                    sign_up_btn.setEnabled(true);
                }
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()==0) {
                    textInputLayout.setError("手机号码不可为空");
                    textInputLayout.setErrorEnabled(true);
                    sign_up_btn.setEnabled(false);
                } else {
                    textInputLayout.setErrorEnabled(false);
                    sign_up_btn.setEnabled(true);
                }
            }

            public void afterTextChanged(Editable s) {
                if (s.length()==0) {
                    textInputLayout.setError("手机号码不可为空");
                    textInputLayout.setErrorEnabled(true);
                    sign_up_btn.setEnabled(false);
                } else {
                    textInputLayout.setErrorEnabled(false);
                    sign_up_btn.setEnabled(true);
                }
            }

        });

        final TextInputLayout textInputLayout2 = (TextInputLayout) findViewById(R.id.security_code);
        EditText security_code = textInputLayout2.getEditText();

        security_code.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.length() == 0) {
                    textInputLayout2.setError("验证码不可为空");
                    textInputLayout2.setErrorEnabled(true);
                    sign_up_btn.setEnabled(false);
                } else {
                    textInputLayout2.setErrorEnabled(false);
                    sign_up_btn.setEnabled(true);
                }
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()==0) {
                    textInputLayout2.setError("验证码不可为空");
                    sign_up_btn.setEnabled(false);
                    textInputLayout2.setErrorEnabled(true);
                } else {
                    textInputLayout2.setErrorEnabled(false);
                    sign_up_btn.setEnabled(true);
                }
            }

            public void afterTextChanged(Editable s) {
                if (s.length()==0) {
                    textInputLayout2.setError("验证码不可为空");
                    textInputLayout2.setErrorEnabled(true);
                    sign_up_btn.setEnabled(false);
                } else {
                    textInputLayout2.setErrorEnabled(false);
                    sign_up_btn.setEnabled(true);
                }
            }
        });

        final TextInputLayout textInputLayout3 = (TextInputLayout) findViewById(R.id.user_name);
        EditText user_name = textInputLayout3.getEditText();

        user_name.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.length() == 0) {
                    textInputLayout3.setError("用户名不可为空");
                    textInputLayout3.setErrorEnabled(true);
                    sign_up_btn.setEnabled(false);
                }else if(s.length()<5){
                    textInputLayout3.setError("用户名不可少于5个字符");
                    textInputLayout3.setErrorEnabled(true);
                    sign_up_btn.setEnabled(false);
                }else if(s.length()>15){
                    textInputLayout3.setError("用户名不可多于15个字符");
                    textInputLayout3.setErrorEnabled(true);
                    sign_up_btn.setEnabled(false);
                }else if(s.toString().contains(" ")){
                    textInputLayout3.setError("用户名不可包含空格");
                    textInputLayout3.setErrorEnabled(true);
                    sign_up_btn.setEnabled(false);
                }
                else {
                    textInputLayout3.setErrorEnabled(false);
                    sign_up_btn.setEnabled(true);
                }
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()==0) {
                    textInputLayout3.setError("用户名不可为空");
                    textInputLayout3.setErrorEnabled(true);
                    sign_up_btn.setEnabled(false);
                } else if(s.length()<5){
                    textInputLayout3.setError("用户名不可少于5个字符");
                    textInputLayout3.setErrorEnabled(true);
                    sign_up_btn.setEnabled(false);
                }else if(s.length()>15){
                    textInputLayout3.setError("用户名不可多于15个字符");
                    textInputLayout3.setErrorEnabled(true);
                    sign_up_btn.setEnabled(false);
                }else if(s.toString().contains(" ")){
                    textInputLayout3.setError("用户名不可包含空格");
                    textInputLayout3.setErrorEnabled(true);
                    sign_up_btn.setEnabled(false);
                }
                else {
                    textInputLayout3.setErrorEnabled(false);
                    sign_up_btn.setEnabled(true);
                }
            }

            public void afterTextChanged(Editable s) {
                if (s.length()==0) {
                    textInputLayout3.setError("用户名不可为空");
                    textInputLayout3.setErrorEnabled(true);
                    sign_up_btn.setEnabled(false);
                } else if(s.length()<5){
                    textInputLayout3.setError("用户名不可少于5个字符");
                    textInputLayout3.setErrorEnabled(true);
                    sign_up_btn.setEnabled(false);
                }else if(s.length()>15){
                    textInputLayout3.setError("用户名不可多于15个字符");
                    textInputLayout3.setErrorEnabled(true);
                    sign_up_btn.setEnabled(false);
                }else if(s.toString().contains(" ")){
                    textInputLayout3.setError("用户名不可包含空格");
                    textInputLayout3.setErrorEnabled(true);
                    sign_up_btn.setEnabled(false);
                }
                else {
                    textInputLayout3.setErrorEnabled(false);
                    sign_up_btn.setEnabled(true);
                }
            }
        });

        final TextInputLayout textInputLayout4 = (TextInputLayout) findViewById(R.id.password);
        final EditText password = textInputLayout4.getEditText();

        password.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.length() == 0) {
                    textInputLayout4.setError("密码不可为空");
                    textInputLayout4.setErrorEnabled(true);
                    sign_up_btn.setEnabled(false);
                }else if(s.length()<5){
                    textInputLayout4.setError("密码不可少于5个字符");
                    textInputLayout4.setErrorEnabled(true);
                    sign_up_btn.setEnabled(false);
                }else if(s.length()>15){
                    textInputLayout4.setError("密码不可多于15个字符");
                    textInputLayout4.setErrorEnabled(true);
                    sign_up_btn.setEnabled(false);
                }else if(s.toString().contains(" ")){
                    textInputLayout4.setError("密码不可包含空格");
                    textInputLayout4.setErrorEnabled(true);
                    sign_up_btn.setEnabled(false);
                }
                else {
                    textInputLayout4.setErrorEnabled(false);
                    sign_up_btn.setEnabled(true);
                }
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()==0) {
                    textInputLayout4.setError("密码不可为空");
                    textInputLayout4.setErrorEnabled(true);
                    sign_up_btn.setEnabled(false);
                } else if(s.length()<5){
                    textInputLayout4.setError("密码不可少于5个字符");
                    textInputLayout4.setErrorEnabled(true);
                    sign_up_btn.setEnabled(false);
                }else if(s.length()>15){
                    textInputLayout4.setError("密码不可多于15个字符");
                    textInputLayout4.setErrorEnabled(true);
                    sign_up_btn.setEnabled(false);
                }else if(s.toString().contains(" ")){
                    textInputLayout4.setError("密码不可包含空格");
                    textInputLayout4.setErrorEnabled(true);
                    sign_up_btn.setEnabled(false);
                }else {
                    textInputLayout4.setErrorEnabled(false);
                    sign_up_btn.setEnabled(true);
                }
            }

            public void afterTextChanged(Editable s) {
                if (s.length()==0) {
                    textInputLayout4.setError("密码不可为空");
                    textInputLayout4.setErrorEnabled(true);
                    sign_up_btn.setEnabled(false);
                } else if(s.length()<5){
                    textInputLayout4.setError("密码不可少于5个字符");
                    textInputLayout4.setErrorEnabled(true);
                    sign_up_btn.setEnabled(false);
                }else if(s.length()>15){
                    textInputLayout4.setError("密码不可多于15个字符");
                    textInputLayout4.setErrorEnabled(true);
                    sign_up_btn.setEnabled(false);
                }else if(s.toString().contains(" ")){
                    textInputLayout4.setError("密码不可包含空格");
                    textInputLayout4.setErrorEnabled(true);
                    sign_up_btn.setEnabled(false);
                }else {
                    textInputLayout4.setErrorEnabled(false);
                    sign_up_btn.setEnabled(true);
                }
            }
        });

        // Password Confirm
        final TextInputLayout textInputLayout5 = (TextInputLayout) findViewById(R.id.password_com);
        EditText password_com = textInputLayout5.getEditText();
        final TextInputLayout textInputLayout6 = (TextInputLayout) findViewById(R.id.password);
        final EditText password_final = textInputLayout6.getEditText();
        password_com.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                EditText et_password = (EditText) findViewById(R.id.et_password);
                final String fin_password = et_password.getText().toString();
                if (!(s.toString().equals(fin_password))) {
                    textInputLayout5.setError("两次输入密码不一致");
                    textInputLayout5.setErrorEnabled(true);
                    sign_up_btn.setEnabled(false);
                }
                else {
                    textInputLayout5.setErrorEnabled(false);
                    sign_up_btn.setEnabled(true);
                }
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EditText et_password = (EditText) findViewById(R.id.et_password);
                final String fin_password = et_password.getText().toString();
                if (!(s.toString().equals(fin_password))) {
                    textInputLayout5.setError("两次输入密码不一致");
                    textInputLayout5.setErrorEnabled(true);
                    sign_up_btn.setEnabled(false);
                }
                else {
                    textInputLayout5.setErrorEnabled(false);
                    sign_up_btn.setEnabled(true);
                }
            }

            public void afterTextChanged(Editable s) {
                EditText et_password = (EditText) findViewById(R.id.et_password);
                final String fin_password = et_password.getText().toString();
                if (!(s.toString().equals(fin_password))) {
                    textInputLayout5.setError("两次输入密码不一致");
                    textInputLayout5.setErrorEnabled(true);
                    sign_up_btn.setEnabled(false);
                }
                else {
                    textInputLayout5.setErrorEnabled(false);
                    sign_up_btn.setEnabled(true);
                }
            }
        });


        final RequestServerInterface requestServer = new RequestServer(handler, SIGNUP_OK, SIGNUP_FAILED, this);
        sign_up_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                    requestServer.signUp();
            }
        });
    }



    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String token = "";
            int user_id = 0;
            switch (msg.what){
                case SIGNUP_OK:
                    Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                    // go back to home activity
                    Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };

}