package com.example.useVoice2;
import android.app.Application;
import com.iflytek.cloud.SpeechUtility;

/**
 * Created by ¿×ÐùÖ¾ on 2015/11/15.
 */
public class SpeechApp extends Application {

    @Override
    public void onCreate() {
        StringBuilder param = new StringBuilder();
        param.append("appid="+getResources().getString(R.string.appid));
        //param.append(",");
        SpeechUtility.createUtility(this,param.toString());
        super.onCreate();
    }
}
