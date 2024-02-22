package edu.hniu.garbagestyle.activity;

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



import java.util.HashMap;

import edu.hniu.garbagestyle.R;
import edu.hniu.garbagestyle.utils.MyHelper;
import edu.hniu.garbagestyle.utils.SPUtils;
import edu.hniu.mvplibrary.base.BaseActivity;
/*修改密码的逻辑：用户想要修改自己的密码 首先得凭着原始密码修改 将输入的原始密码和保存在数据库中的密码对比
 * 如果一致才能修改
 * 否则提示用户输入的原始密码不对无法修改*/

public class EditPwdActivity extends BaseActivity implements View.OnClickListener {
    private EditText ed_pwd,ed_newpwd,ed_again_newpwd;
    private Button btn_save;
    private SQLiteDatabase db;
    private MyHelper helper;
    //数据组装类
    private ContentValues values;
    //工具栏
    private Toolbar toolbar;
    @Override
    public void initData(Bundle savedInstanceState) {
        //初始化控件的方法
        initview();
        //初始化数据库帮助对象
        helper=new MyHelper(this);
    }

    private void initview() {
        ed_pwd=findViewById(R.id.ed_pwd);
        ed_newpwd=findViewById(R.id.ed_newpwd);
        ed_again_newpwd=findViewById(R.id.ed_again_newpwd);
        btn_save=findViewById(R.id.btn_save);
        toolbar = findViewById(R.id.toolbar);
        //设置页面状态栏
        setStatubar(this, R.color.white, true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EditPwdActivity.this,MyselfActivity.class);
                //启动跳转
                startActivity(intent);
                //关闭登录页
                EditPwdActivity.this.finish();
            }
        });

        btn_save.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_editpwd;
    }

    @Override
    public void onClick(View view) {
        //先获取用户输入的原始密码
        String inputPwd = ed_pwd.getText().toString().trim();
        //获取输入的新密码
        String inputNewPwd = ed_newpwd.getText().toString().trim();
        //获取输入的新密码确认
        String inputNewPwdAgain = ed_again_newpwd.getText().toString().trim();
        //对原始密码 新密码 确认密码做非空验证
        if (TextUtils.isEmpty(inputPwd)) {
            Toast.makeText(this, "请输入原始密码", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(inputNewPwd)) {
            Toast.makeText(this, "请输入新密码", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(inputNewPwdAgain)) {
            Toast.makeText(this, "请输入确认密码", Toast.LENGTH_SHORT).show();
            return;
        }
        //用户已经输入用户名 密码 确认密码 将输入的原始密码和保存在sp文件中的密码对比
        //调用查询sp文件中密码的方法
        //      boolean isUPdate= UpdateSQLite(inputPwd,inputNewPwd,inputNewPwdAgain);
        //调用sp文件中的读取密码 的方法
        HashMap<String, Object> map = SPUtils.readToSP(this);
        //取出map中的密码
        String spPwd=(String) map.get("mp_pwd");

        if(!(inputPwd.equals(spPwd))){
            Toast.makeText(this, "原始密码不正确", Toast.LENGTH_SHORT).show();
            return;
        }else if(!(inputNewPwd.equals(inputNewPwdAgain))){
            Toast.makeText(this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
            return;
        }else{
            boolean isUPdate= UpdateSQLite(inputPwd,inputNewPwd,inputNewPwdAgain);
            if(isUPdate){
                Intent intent=new Intent(EditPwdActivity.this,LoginActivity.class);
                startActivity(intent);
                Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                EditPwdActivity.this.finish();
            }
                  /*  //获取一份sp文件
                    SharedPreferences sp=getSharedPreferences("sp_UserInfo",MODE_PRIVATE);
                    //获得编辑器
                    SharedPreferences.Editor editor= sp.edit();
                    //保存新密码进入sp
                    editor.putString("sp_pwd",inputNewPwd);
                    //最后提交
                    editor.commit();
                    //关闭当前页面 退出到登录页

                    */
        }
    }

    /*从数据库中修改密码*/
    public boolean UpdateSQLite(String originalPwd,String userPwd,String userPwdAgain){
        //获得一个可写的数据库对象
        db=helper.getWritableDatabase();
        //初始化values
        values=new ContentValues();
        values.put("user_pwd",userPwd);
        values.put("user_pwd_again",userPwdAgain);
        db.update("Person",values,"user_pwd=?",new String[]{originalPwd});
        //关闭数据库
        db.close();
        return true;



    }
}
