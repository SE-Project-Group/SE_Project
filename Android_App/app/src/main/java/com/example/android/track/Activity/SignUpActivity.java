package com.example.android.track.Activity;

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

import com.example.android.track.Application.MyApplication;
import com.example.android.track.R;

import cn.jiguang.analytics.android.api.JAnalyticsInterface;
import cn.jiguang.analytics.android.api.RegisterEvent;

import static com.adobe.creativesdk.aviary.internal.AdobeAccountUserStatus.Type.SIGNUP;


public class SignUpActivity extends AppCompatActivity {
    private static final int SIGNUP_OK = 0;
    private static final int EXIST_PHONE = 1;
    private static final int EXIST_USER_NAME = 2;
    private static final int SIGNUP_FAILED = 3;
    private Button sign_up_btn;

    //view
    private EditText et_userName;
    private EditText et_password;
    private EditText et_password_confirm;

    private String phone;  // get from last activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        // set ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.signUpToolBar);
        toolbar.setTitleTextColor(SignUpActivity.this.getResources().getColor(R.color.gray));
        setSupportActionBar(toolbar);

        setCheckListener();

        // get EditText et_password_confirmform
        et_userName = (EditText) findViewById(R.id.et_userName);
        et_password = (EditText) findViewById(R.id.et_password);
        et_password_confirm = (EditText) findViewById(R.id.et_password_confirm);
        
        sign_up_btn = (Button) findViewById(R.id.sign_up_btn); 
        
        sign_up_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String user_name_ed = et_userName.getText().toString();
                String password_ed = et_password.getText().toString();
                String phone_ed = phone;
                String password_confirm_ed = et_password_confirm.getText().toString();

                if(user_name_ed.equals("") || password_ed.equals("") || !password_ed.equals(password_confirm_ed)) {
                    Toast.makeText(SignUpActivity.this, "表单信息有误", Toast.LENGTH_SHORT).show();
                }else {
                    // create a new Thread to send sign up request
                    signUp(user_name_ed, password_ed, phone_ed);
                }
            }
        });
    }
    
    
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SIGNUP_OK:
                    // upload analytics data
                    RegisterEvent rEvent = new RegisterEvent();
                    rEvent.addKeyValue("user_name", et_userName.getText().toString());
                    rEvent.addKeyValue("phone", phone);
                    JAnalyticsInterface.onEvent(SignUpActivity.this, rEvent);

                    // sign up Jmessage client
                    Toast.makeText(MyApplication.getContext(), "注册成功", Toast.LENGTH_SHORT).show();
                    // go back to home activity
                    Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                    startActivity(intent);
                    break;
                case EXIST_PHONE:
                    Toast.makeText(SignUpActivity.this, "手机号已经被注册", Toast.LENGTH_SHORT).show();
                    break;
                case EXIST_USER_NAME:
                    Toast.makeText(SignUpActivity.this, "用户名已存在", Toast.LENGTH_SHORT).show();
                    break;
                case SIGNUP_FAILED:
                    Toast.makeText(SignUpActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    
    private void signUp(final String name, final String pwd, final String phone){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String response_data = MyApplication.signUp(name, pwd, phone);

                Message message = new Message();
                if(response_data.equals("existing phone"))
                    message.what = EXIST_PHONE;
                else if(response_data.equals("existing user name"))
                    message.what = EXIST_USER_NAME;
                else if(response_data.equals("success"))
                    message.what = SIGNUP_OK;
                else
                    message.what = SIGNUP_FAILED;
                handler.sendMessage(message);
            }
        }).start();
        
        
    }


    // check the form
    private void setCheckListener(){
        final TextInputLayout textInputLayout3 = (TextInputLayout) findViewById(R.id.user_name);
        EditText user_name = textInputLayout3.getEditText();

        user_name.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.length() == 0) {
                    textInputLayout3.setError("用户名不可为空");
                    textInputLayout3.setErrorEnabled(true);
                    sign_up_btn.setEnabled(false);
                }
                else if(s.length()>15){
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
                } else if(s.length()>15){
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
        final EditText password_com = textInputLayout5.getEditText();
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
    }
}
