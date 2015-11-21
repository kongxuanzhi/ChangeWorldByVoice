package com.example.VoiceRecog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.VoiceRecog.functions.Talk;

/**
 * Created by ¿×ÐùÖ¾ on 2015/11/21.
 */
public class startActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.start);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.browser:
                Intent intent = new Intent(this,main.class);
                startActivity(intent);
                break;
            case R.id.reader:
                break;
            case R.id.talk:
                Intent intent1 = new Intent(this,Talk.class);
                startActivity(intent1);
                break;
        }
    }
}
