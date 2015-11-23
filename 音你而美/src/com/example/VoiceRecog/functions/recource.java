package com.example.VoiceRecog.functions;

import android.content.Context;
import com.example.VoiceRecog.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ¿×ÐùÖ¾ on 2015/11/23.
 */
public class recource{
    Context context;


    private Map<String,String> browers;

    public recource(Context context) {
        this.context = context;
        browers = new HashMap<String, String>();
        load();
    }
    public Map<String, String> getBrowers() {

        return browers;
    }

    private void load() {
        String[] browsers =  context.getResources().getStringArray(R.array.browsers);
        String[] values =  context.getResources().getStringArray(R.array.value);
        for(int i=0;i<browsers.length;i++){
            browers.put(browsers[i],values[i]);
        }
    }
}
