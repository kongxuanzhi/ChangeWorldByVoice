package com.example.VoiceRecog;
import android.app.Application;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

/**
 * Created by ¿×ÐùÖ¾ on 2015/11/15.
 */
public class SpeechApp extends Application {
    @Override
    public void onCreate() {
        StringBuffer param = new StringBuffer();
        param.append(SpeechConstant.APPID + "=" + getResources().getString(R.string.appid));
        SpeechUtility.createUtility(this, param.toString());
        super.onCreate();
    }
}
