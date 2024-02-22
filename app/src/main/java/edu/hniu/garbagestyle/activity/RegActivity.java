package edu.hniu.garbagestyle.activity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;



import edu.hniu.garbagestyle.R;
import edu.hniu.garbagestyle.utils.MyHelper;
import edu.hniu.mvplibrary.base.BaseActivity;

public class RegActivity extends BaseActivity implements View.OnClickListener {
    private EditText ed_username,ed_pwd,ed_againPwd;
    private Button btn_reg,btn_login;
    //声明数据库帮助对象
    private MyHelper helper;
    //声明数据库对象
    private SQLiteDatabase db;
    //定义数据组装类
    private ContentValues values;
    //声明用户输入的用户名 密码 确认密码
    private String name,pwd,pwd_again;
    //工具栏
    private Toolbar toolbar;

    @Override
    public void initData(Bundle savedInstanceState) {
        //创建初始化控件方法
        initview();
        //初始化数据库帮助对象
        helper=new MyHelper(RegActivity.this);
        //初始化数据组装类
        values=new ContentValues();
    }

    @SuppressLint("WrongViewCast")
    private void initview() {
        ed_username=findViewById(R.id.ed_usename);
        ed_pwd=findViewById(R.id.ed_pwd);
        ed_againPwd=findViewById(R.id.ed_agaginPwd);
        btn_login=findViewById(R.id.btn_login);
        btn_reg=findViewById(R.id.btn_reg);
        toolbar = findViewById(R.id.toolbar);
        //设置页面状态栏
        setStatubar(this, R.color.white, true);

        ed_againPwd.setOnClickListener(this);
        ed_pwd.setOnClickListener(this);
        ed_username.setOnClickListener(this);
        btn_reg.setOnClickListener(this);
        btn_login.setOnClickListener(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegActivity.this,MyselfActivity.class);
                //启动跳转
                startActivity(intent);
                //关闭登录页
                RegActivity.this.finish();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_reg;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login://点击的是返回按钮
                backLoginActivity();
                break;
            case R.id.btn_reg://点击注册按钮
                //获取用户名输入的用户名 密码 确认密码
                name=ed_username.getText().toString();
                pwd=ed_pwd.getText().toString();
                pwd_again=ed_againPwd.getText().toString();
                //一旦获取真正的用户名 密码 确认密码再放入   获取用户输入的用户名 密码 确认密码
                RegUserInfo(name,pwd,pwd_again);
                break;
        }
    }
    //创建跳转方法
    public void backLoginActivity(){
        Intent intent=new Intent(RegActivity.this,LoginActivity.class);
        //启动意图
        startActivity(intent);
        //关闭注册页面
        RegActivity.this.finish();
    }
    //创建具体的注册方法
    public void RegUserInfo(String username,String userpwd,String userpwd_again){
        //判断用户是否输入了注册信息   TextUtil是文本检验工具
        if(TextUtils.isEmpty(name)|TextUtils.isEmpty(pwd)|TextUtils.isEmpty(pwd_again)){
            Toast.makeText(RegActivity.this, "请输入用户名，密码，确认密码！", Toast.LENGTH_SHORT).show();
            return;
        }
        //对两次输入的密码进行校验
        if(!(pwd.equals(pwd_again))){
            Toast.makeText(RegActivity.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
            return;
        }else {
            //获得一个可写的数据库对象
            db = helper.getWritableDatabase();
            //往数据库表中插数据 （列名，控件)
            values.put("user_name", username);
            values.put("user_pwd", userpwd);
            values.put("user_pwd_again", userpwd_again);
            // 获取到数据库表名 (表名 哪些列)
            db.insert("Person", null, values);
            //关闭数据库
            db.close();
            Toast.makeText(RegActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
            backLoginActivity();
        }

    }
}
