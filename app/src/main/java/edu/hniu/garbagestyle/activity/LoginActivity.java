package edu.hniu.garbagestyle.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;



import java.util.HashMap;

import edu.hniu.garbagestyle.R;
import edu.hniu.garbagestyle.utils.MyHelper;
import edu.hniu.garbagestyle.utils.SPUtils;
import edu.hniu.mvplibrary.base.BaseActivity;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText ed_username,ed_pwd;//用户名 密码
    private Button btn_reg,btn_login;//注册 登录
    private SQLiteDatabase db;
    private MyHelper helper;
    //工具栏
    private Toolbar toolbar;

    @Override
    public void initData(Bundle savedInstanceState) {
        initview();
        helper=new MyHelper(LoginActivity.this);

     /*   //读取sp文件中保存的用户名 密码
        HashMap<String,Object> map= SPUtils.readToSP(LoginActivity.this);
        //取出map中保存的用户名 密码
        String name=(String) map.get("userName");
        String pwd=(String) map.get("userPwd");
        //将取出来的用户名 密码绑定到两个输入框里
        ed_username.setText(name);
        ed_pwd.setText(pwd);*/
    }

    private void initview() {
        ed_username=findViewById(R.id.ed_username);
        ed_pwd=findViewById(R.id.ed_pwd);
        btn_reg=findViewById(R.id.btn_reg);
        btn_login=findViewById(R.id.btn_login);
        toolbar = findViewById(R.id.toolbar);
        //设置页面状态栏
        setStatubar(this, R.color.white, true);

        btn_login.setOnClickListener(this);
        btn_reg.setOnClickListener(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,MyselfActivity.class);
                //启动跳转
                startActivity(intent);
                //关闭登录页
                LoginActivity.this.finish();
            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_login;
    }

    @Override
    public void onClick(View view) {
        String logname=ed_username.getText().toString().trim();
        String logpwd=ed_pwd.getText().toString().trim();
        switch (view.getId()){
            case R.id.btn_login://用户点击的是登录按钮
                //判断用户是否已经输入了用户名 密码
                if(TextUtils.isEmpty(logname)){
                    Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(logpwd)){
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                //当点击登录时候将用户名密码存储到sp文件中
                SPUtils.saveToSP(this,logname,logpwd);
                //取出map中的密码

                boolean isScuess=userLogin(logname,logpwd);
                if(isScuess){
                    //跳转主页面
                    Intent intent=new Intent(LoginActivity.this,MyselfActivity.class);
                    //当用户登录成功进入主页时将用户的登录状态 登录名保存在意图中
                    HashMap<String ,Object> map=SPUtils.readToSP(this);
                    //取出登录状态
                    String username=(String)map.get("mp_name");
                    boolean isLogin=(boolean)map.get("isLogin");
                    //存放到意图身上
                    intent.putExtra("mp_name",username);
                    intent.putExtra("isLogin",isLogin);
                    //保存至意图身上 1是结果码
                    setResult(1,intent);
                    //开启意图
                    startActivity(intent);
                    //关闭登录页
                    LoginActivity.this.finish();

                }else {
                    Toast.makeText(LoginActivity.this, "用户名或密码不正确！", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btn_reg://用户点击的注册按钮
                Intent intent=new Intent(LoginActivity.this,RegActivity.class);
                //启动跳转
                startActivity(intent);
                //关闭登录页
                LoginActivity.this.finish();
                break;
        }
    }
    /*创建用户登录方法*/
    public boolean  userLogin(String loginname,String loginPwd){
        //获得一个可读的数据库对象
        db=helper.getReadableDatabase();
        //
        String sql="select * from Person where user_name=? and user_pwd=?";
        Cursor cursor=db.rawQuery(sql,new String[]{loginname,loginPwd});
        //判断游标结果集
        if(cursor.moveToFirst()==true){
            //关闭游标
            cursor.close();
            return true;
        }
        return false;

    }
}
