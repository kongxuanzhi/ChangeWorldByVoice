package com.example.offlineTextToVoice;

import android.app.Application;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.util.ResourceUtil;

/**
 * Created by 孔轩志 on 2015/11/20.
 */
public class base extends Application {

    String resPath= "";
    @Override
    public void onCreate() {
        super.onCreate();
        resPath =   ResourceUtil.generateResourcePath(this,
                ResourceUtil.RESOURCE_TYPE.res, String.valueOf(R.raw.xiaofeng));

        StringBuffer param = new StringBuffer();
//加载识别本地资源， resPath为本地识别资源路径
        param.append(","+ ResourceUtil.ASR_RES_PATH+"="+resPath);
        param.append(","+ResourceUtil.ENGINE_START+"="+SpeechConstant.ENG_ASR);
        param.append(";"+ SpeechConstant.APPID+"=564201e5");
        SpeechUtility.createUtility(this, param.toString());
    }
}
