package edu.hniu.garbagestyle.other;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import org.litepal.LitePal;

import edu.hniu.mvplibrary.BaseApplication;
import edu.hniu.mvplibrary.network.NetworkApi;

/**
 * 自定义Application
 *
 * @author llw
 * @date 2021/3/30 15:19
 */
public class TrashApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化网络框架
        NetworkApi.init(new NetworkRequiredInfo(this));

        //配置讯飞语音SDK
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=fcbc4a87");

        //数据库初始化
        LitePal.initialize(this);
    }
}
