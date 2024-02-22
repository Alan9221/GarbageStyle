package edu.hniu.garbagestyle.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.imageview.ShapeableImageView;


import java.util.HashMap;

import edu.hniu.garbagestyle.R;
import edu.hniu.garbagestyle.utils.SPUtils;
import edu.hniu.mvplibrary.base.BaseActivity;

public class MyselfActivity extends BaseActivity {
    //工具栏
    private Toolbar toolbar;
    private ShapeableImageView iv_head;//头像
    private TextView tv_name;//昵称
    private RelativeLayout rl_password,rl_guan,rl_quit;//修改密码 关于我们 退出登录

    @Override
    public void initData(Bundle savedInstanceState) {
        //初始化
       init();
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        iv_head=findViewById(R.id.iv_head);
        tv_name=findViewById(R.id.tv_name);
        rl_guan=findViewById(R.id.rl_guan);
        rl_password=findViewById(R.id.rl_password);
        rl_quit=findViewById(R.id.rl_quit);

        //用户是否可以跳转至登录页面 首先要判断用户进来时是否已经登录 如果未登录则跳转到登录页 如果已经登录则无需跳转
        setLoginParams(readLoginStatus());

        //设置页面状态栏
        setStatubar(this, R.color.white, true);
        back(toolbar, false);


     /*   //设置昵称监听
        tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setnc(readLoginStatus());
            }
        });*/
        //设置头像监听
        iv_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(readLoginStatus()){
                    Toast.makeText(MyselfActivity.this, "已经登录", Toast.LENGTH_SHORT).show();
                }
                else{
                    //未登录则挑战至登录页面
                    Intent intent=new Intent(MyselfActivity.this, LoginActivity.class);
                    //启动意图
                    startActivity(intent);
                    //关闭注册页面
                    MyselfActivity.this.finish();
                }

            }
        });
        //设置修改密码监听
        rl_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MyselfActivity.this,EditPwdActivity.class);
                //启动跳转
                startActivity(intent);
                //关闭登录页
                MyselfActivity.this.finish();
            }
        });
        //设置关于我们监听
        rl_guan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MyselfActivity.this,GuanActivity.class);
                //启动跳转
                startActivity(intent);
                //关闭登录页
                MyselfActivity.this.finish();
            }
        });

        //设置退出登录监听
        rl_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //弹消息退出登录
                Toast.makeText(MyselfActivity.this, "退出登录成功！", Toast.LENGTH_SHORT).show();
                //调用清空文件方法
                clearLoginStatus();
                //退出登录后页面直接退到上一页面
                Intent intent=new Intent();
                //保存登录状态的值到intent身上
                intent.putExtra("isLogin",false);
                setResult(1,intent);
                //关闭当前页面
                MyselfActivity.this.finish();
            }
        });
    }

   /*
    //用来设置昵称状态 已经登录就跳转修改 未登录则弹窗请先登录
    public void setnc(boolean readLoginStatus) {
        if(readLoginStatus){
            Intent intent=new Intent(MyselfActivity.this,NcActivity.class);
            //启动跳转
            startActivity(intent);
            //关闭登录页
            MyselfActivity.this.finish();
        }else {
            Toast.makeText(MyselfActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
        }
    }
*/
    //用来设置头向下到底显示用户名还是点击登录的
    public void setLoginParams(boolean readLoginStatus) {
        if(readLoginStatus){
            //读取保存在sp文件中的用户名
            HashMap<String, Object> map = SPUtils.readToSP(MyselfActivity.this);
            //读取保存在map中的用户名
            String username=(String)map.get("mp_name");
            //将设置的
            tv_name.setText("用户名："+username);
        }else {
            Toast.makeText(MyselfActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
        }
    }


    //读取用户存在sp文件中的登录状态
    private boolean readLoginStatus(){
        //获取sp文件
        SharedPreferences sp=MyselfActivity.this.getSharedPreferences("sp_UserInfo", Context.MODE_PRIVATE);
        //读取sp文件中保存的登录状态值
        boolean isLogin=sp.getBoolean("isLogin",false);
        //返回登录状态
        return isLogin;
    }

    //清空文件方法
    public void clearLoginStatus(){
        //获得sp文件
        SharedPreferences sp=getSharedPreferences("sp_UserInfo",MODE_PRIVATE);
        //获得sp文本编辑器
        SharedPreferences.Editor editor=sp.edit();
        //给用户名 密码设置空值
        editor.putString("mp_name","");
        editor.putString("mp_pwd","");
        editor.putBoolean("isLogin",false);
        editor.commit();
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_myself;
    }



}
