package com.example.offlineTextToVoice;

import android.app.Application;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.util.ResourceUtil;

/**
 * Created by ����־ on 2015/11/20.
 */
public class base extends Application {

    String resPath= "";
    @Override
    public void onCreate() {
        super.onCreate();
        resPath =   ResourceUtil.generateResourcePath(this,
                ResourceUtil.RESOURCE_TYPE.res, String.valueOf(R.raw.xiaofeng));

        StringBuffer param = new StringBuffer();
//����ʶ�𱾵���Դ�� resPathΪ����ʶ����Դ·��
        param.append(","+ ResourceUtil.ASR_RES_PATH+"="+resPath);
        param.append(","+ResourceUtil.ENGINE_START+"="+SpeechConstant.ENG_ASR);
        param.append(";"+ SpeechConstant.APPID+"=564201e5");
        SpeechUtility.createUtility(this, param.toString());
    }
}
