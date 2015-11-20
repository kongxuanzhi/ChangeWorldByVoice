package com.example.voiceTotext;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.iflytek.cloud.*;
import com.iflytek.cloud.util.ResourceUtil;

public class MyActivity extends Activity  implements View.OnClickListener{
    /**
     * Called when the activity is first created.
     */
    EditText text;
    Toast mToast;
    String resPath ="";
    SpeechRecognizer mAsr;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mAsr = SpeechRecognizer.createRecognizer(this,null);

        setInit();
        resPath=   ResourceUtil.generateResourcePath(this,
                ResourceUtil.RESOURCE_TYPE.res, String.valueOf(R.raw.sms_16k));
        showTips(resPath);
    }

    private void setInit() {
        mAsr.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
        mAsr.setParameter(SpeechConstant.LOCAL_GRAMMAR,"call");
        mAsr.setParameter(SpeechConstant.MIXED_THRESHOLD,"30");
        mAsr.setParameter(ResourceUtil.GRM_BUILD_PATH,"");
        mAsr.setParameter(ResourceUtil.ASR_RES_PATH,resPath);

        findViewById(R.id.hecheng).setOnClickListener(this);
        text = (EditText)this.findViewById(R.id.text);
        mToast = Toast.makeText(this,"",Toast.LENGTH_SHORT);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.hecheng:
                int ret = mAsr.startListening(mRecognizerListener);
                showTips(ret+"");
                break;
        }
    }
    RecognizerListener mRecognizerListener = new RecognizerListener() {
        @Override
        public void onVolumeChanged(int i) {
            showTips("音量改变为："+i);
        }

        @Override
        public void onBeginOfSpeech() {
            showTips("开始讲话");
        }

        @Override
        public void onEndOfSpeech() {
            showTips("结束讲话");
        }

        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
           text.setText(recognizerResult.getResultString());
            showTips("返回数据");
        }

        @Override
        public void onError(SpeechError speechError) {
            showTips(speechError.getErrorDescription());
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {
            showTips("有某些事件发生了");
        }
    };

    public void showTips(final String str){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mToast.setText(str);
                mToast.show();
            }
        });
    }

}
