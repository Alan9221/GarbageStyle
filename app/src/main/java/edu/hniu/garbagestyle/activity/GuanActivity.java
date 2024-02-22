package edu.hniu.garbagestyle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;



import edu.hniu.garbagestyle.R;
import edu.hniu.mvplibrary.base.BaseActivity;

public class GuanActivity extends BaseActivity {
    //工具栏
    private Toolbar toolbar;
    @Override
    public void initData(Bundle savedInstanceState) {
        init();
    }
    private void init() {
        toolbar = findViewById(R.id.toolbar);
        //设置页面状态栏
        setStatubar(this, R.color.white, true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GuanActivity.this,MyselfActivity.class);
                //启动跳转
                startActivity(intent);
                //关闭登录页
                GuanActivity.this.finish();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_guan;
    }
}
