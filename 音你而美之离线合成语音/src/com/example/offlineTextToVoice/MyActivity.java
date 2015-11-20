package com.example.offlineTextToVoice;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.util.ResourceUtil;

public class MyActivity extends Activity  implements View.OnClickListener{
    /**
     * Called when the activity is first created.
     */
    SpeechSynthesizer mTts;
    EditText text;
    Toast mToast;
    String resPath ="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mTts = SpeechSynthesizer.createSynthesizer(this,null);
        setInit();
        resPath=   ResourceUtil.generateResourcePath(this,
                ResourceUtil.RESOURCE_TYPE.res, String.valueOf(R.raw.xiaofeng));
        showTips(resPath);
    }

    private void setInit() {
        mTts.setParameter(SpeechConstant.ENGINE_TYPE,SpeechConstant.TYPE_LOCAL);
        mTts.setParameter(SpeechConstant.VOICE_NAME,"xiaoyan");  //设置本地发音人 在/res/tts/ *.jet 文件
        mTts.setParameter(ResourceUtil.TTS_RES_PATH,resPath);//加载本地资源，可以是内存卡，或者集成在程序包中
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory().toString());

        findViewById(R.id.hecheng).setOnClickListener(this);
        text = (EditText)this.findViewById(R.id.text);
        mToast = Toast.makeText(this,"",Toast.LENGTH_SHORT);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.hecheng:
                mTts.startSpeaking(text.getText().toString(),mSynListener);
                break;
        }
    }

    SynthesizerListener mSynListener =new SynthesizerListener() {
        @Override
        public void onSpeakBegin() {
            showTips("开始讲话");
        }

        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {

        }

        @Override
        public void onSpeakPaused() {
            showTips("暂停讲话");
        }

        @Override
        public void onSpeakResumed() {
            showTips("继续讲话");
        }

        @Override
        public void onSpeakProgress(int i, int i1, int i2) {
        }

        @Override
        public void onCompleted(SpeechError speechError) {
            showTips("结束讲话");
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {
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
