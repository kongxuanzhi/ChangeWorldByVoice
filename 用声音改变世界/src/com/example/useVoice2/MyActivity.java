package com.example.useVoice2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.provider.Settings;
import android.view.*;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.iflytek.cloud.*;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

public class MyActivity extends Activity  implements View.OnClickListener {
    /**
     * Called when the activity is first created.
     */
    private SpeechRecognizer recognizer;  //无对话框语言识别成文字
    private EditText editText;
    private RecognizerDialog  Recog_Dialog;   //对话框语音识别成文字
    private SpeechSynthesizer TextToVoice;  //语音合成
    private Toast mToast;
    AlertDialog.Builder b;

    //缓冲进度
    private int mPercentForBuffering = 0;
    //播放进度
    private int mPercentForPlaying = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        recognizer = SpeechRecognizer.createRecognizer(this,null);
        Recog_Dialog = new RecognizerDialog(MyActivity.this, new InitListener() {
            @Override
            public void onInit(int code) {
                if (code != ErrorCode.SUCCESS) {
                    showTips("对话框初始化失败:" + code);
                }
            }
        });
        TextToVoice = SpeechSynthesizer.createSynthesizer(this,null);
        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);

        b = new AlertDialog.Builder(this).setTitle("没有可用的网络")
                .setMessage("是否对网络进行设置？");
        setUpandInitView();
    }
    private void setUpandInitView() {
        recognizer.setParameter(SpeechConstant.DOMAIN, "iat");
        recognizer.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        recognizer.setParameter(SpeechConstant.ACCENT, "mandarin ");

        //文字转语音的设置
        TextToVoice.setParameter(SpeechConstant.VOICE_NAME,"xiaoyan");
        TextToVoice.setParameter(SpeechConstant.SPEED,"50");
        TextToVoice.setParameter(SpeechConstant.VOLUME,"80");
        TextToVoice.setParameter(SpeechConstant.TTS_AUDIO_PATH,"./sdcard/iflytek.pcm");
        TextToVoice.setParameter(SpeechConstant.ENGINE_TYPE,SpeechConstant.TYPE_CLOUD);
        //设置播放器音频流类型
        TextToVoice.setParameter(SpeechConstant.STREAM_TYPE,"3");

        this.findViewById(R.id.start).setOnClickListener(this);
        this.findViewById(R.id.start_Diago).setOnClickListener(this);
        this.findViewById(R.id.stop).setOnClickListener(this);
        this.findViewById(R.id.Read_Text).setOnClickListener(this);

        editText = (EditText)this.findViewById(R.id.Rec_result);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.start:
                recognizer.startListening(mRecoListener);
                break;
            case R.id.start_Diago:
                showTips("打开对话框");
                Recog_Dialog.setListener(recognizerDialogListener);
                Recog_Dialog.show();
                break;
            case R.id.stop:
                recognizer.stopListening();
                break;
            case R.id.Read_Text:
                TextToVoice.startSpeaking(editText.getText().toString(),mSynListener);
                break;
        }
    }

    //=============================================监听事件============================================
    //合成音频监听事件
    private SynthesizerListener mSynListener = new SynthesizerListener() {
        @Override
        public void onSpeakBegin() {
            showTips("开始播放");
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
        String info) {
            mToast.setText(String.format(getString(R.string.tts_toast_format),
                    mPercentForBuffering, mPercentForPlaying));

            mToast.show();
        }
        @Override
        public void onSpeakPaused() {
            showTips("播放暂停");
        }

        @Override
        public void onSpeakResumed() {
            showTips("继续播放");
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            mPercentForPlaying = percent;
            showTips(String.format(getString(R.string.tts_toast_format),
                    mPercentForBuffering, mPercentForPlaying));
        }

        @Override
        public void onCompleted(SpeechError error) {
            if(error == null)
            {
                showTips("播放完成");
            }
            else if(error != null)
            {
                showTips(error.getPlainDescription(true));
            }
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };


    //对话框监听事件
    private RecognizerDialogListener  recognizerDialogListener = new RecognizerDialogListener() {
        @Override
        public void onResult(RecognizerResult result, boolean isLast) {
            String text = JsonParser.parseIatResult(result.getResultString());
            editText.append(text);
            editText.setSelection(editText.length());
            if(isLast){

            }
        }
        @Override
        public void onError(SpeechError error) {
            showTips(error.getErrorDescription());
        }
    };

    //无对话框监听事件
    RecognizerListener mRecoListener = new RecognizerListener() {
        @Override
        public void onVolumeChanged(int i) {}
        @Override
        public void onBeginOfSpeech() {
            showTips("开始录音");
        }
        @Override
        public void onEndOfSpeech() {
            showTips("结束录音");
        }
        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            String text = JsonParser.parseIatResult(results.getResultString());
            editText.append(text);
            editText.setSelection(editText.length());
            showTips("识别结果是:" + editText.getText());
            if(isLast) {
                //TODO 最后的结果
            }
        }
        @Override
        public void onError(SpeechError speechError) {
          //  showTips(speechError.getErrorCode()+"");
            showTips(speechError.getErrorDescription());
            //IsNetAccess(b);
        }
        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
           // showTips(eventType + "|" + arg1 + "|" + arg2);
            switch (eventType){
                case 22003:
                   // IsNetAccess(b);
                    break;
            }
        }
    };


   //========================================================工具函数============================================


    public void showTips(final String str){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mToast.setText(str);
                mToast.show();
            }
        });
    }
    //判断是否联网，没有联网，就提醒去联网
    public void IsNetAccess(AlertDialog.Builder b){
//        boolean netSataus = false;
//        ConnectivityManager cwjManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        cwjManager.getActiveNetworkInfo();
//        if (cwjManager.getActiveNetworkInfo() != null) {
//            netSataus = cwjManager.getActiveNetworkInfo().isAvailable();
//        }
        //if(!netSataus){
            b.setPositiveButton("是", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    Intent intent=null;
                    //判断手机系统的版本  即API大于10 就是3.0或以上版本
                    if(android.os.Build.VERSION.SDK_INT>10){
                        intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                    }else{
                        intent = new Intent();
                        ComponentName component = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
                        intent.setComponent(component);
                        intent.setAction("android.intent.action.VIEW");
                    }
                    startActivity(intent);  // 如果在设置完成后需要再次进行操作，可以重写操作代码，在这里不再重写
                }
            }).setNeutralButton("否", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.cancel();
                }
            }).show();
        //}
    }
}
