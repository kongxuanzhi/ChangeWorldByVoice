package com.example.useVoice2;
import android.app.Application;
import android.os.Environment;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.util.ResourceUtil;

/**
 * Created by ����־ on 2015/11/15.
 */
public class SpeechApp extends Application {
    @Override
    public void onCreate() {

   //     String resPath =  ResourceUtil.generateResourcePath(this, ResourceUtil.RESOURCE_TYPE.path, Environment.getExternalStorageDirectory()+"/msc/asr/common.jet")+ ";" +
     //           ResourceUtil.generateResourcePath(this, ResourceUtil.RESOURCE_TYPE.path,Environment.getExternalStorageDirectory()+"/msc/asr/sms_16k.jet");
        StringBuffer param = new StringBuffer();
//����ʶ�𱾵���Դ�� resPathΪ����ʶ����Դ·��

       // param.append(","+ ResourceUtil.ASR_RES_PATH+"="+resPath);
       // param.append(","+ResourceUtil.ENGINE_START+"="+SpeechConstant.ENG_ASR);
        param.append(SpeechConstant.APPID + "=" + getResources().getString(R.string.appid));
        SpeechUtility.createUtility(this, param.toString());
        super.onCreate();
    }
}
