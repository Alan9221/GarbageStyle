package edu.hniu.garbagestyle.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import edu.hniu.garbagestyle.R;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        jump();
    }

    private void jump() {
        //定义一个计时器
        Timer timer=new Timer();
        //开启子线程任务
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        };
        //设置延时实践 2000毫秒
        timer.schedule(task,2000);
    }
}
