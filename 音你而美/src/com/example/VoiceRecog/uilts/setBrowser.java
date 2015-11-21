package com.example.VoiceRecog.uilts;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.DialogPreference;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.example.VoiceRecog.R;
import com.example.VoiceRecog.main;

import java.io.IOException;
import java.util.*;

/**
 * Created by 孔轩志 on 2015/11/21.
 */
public class setBrowser extends Activity {
    private static  List<Map<String, String>> maps =null;
    ListView listView;
    Toast mToast;
    Dialog alertDialog;
    operatorFile opfile;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set);
        listView = (ListView) this.findViewById(R.id.list);
        opfile = new operatorFile(this);

        maps = loadData();
        SimpleAdapter  simpleAdapter = new SimpleAdapter(this,maps,R.layout.listitems,
                new String[]{"title","detail"},new int[]{R.id.title,R.id.detail}
        );
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        showItems();
                        break;
                }
            }
            public void showItems(){
                final String[] browsers = getResources().getStringArray(R.array.browsers);
                Dialog alertDialog = new AlertDialog.Builder(setBrowser.this).
                        setTitle("搜索引擎").
                        setIcon(R.drawable.ic_launcher)
                        .setItems(browsers, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    opfile.WriteToInner("browser.txt",browsers[which],false);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(setBrowser.this, browsers[which], Toast.LENGTH_SHORT).show();
                                if(browsers[which].equals("必应")){
                                    main.url ="http://cn.bing.com/search?q=";
                                }
                                else{
                                    main.url = "http://www.baidu.com/s?wd=";
                                }
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                            }
                        }).create();
                alertDialog.show();
            }
        });
        mToast = Toast.makeText(this,"",Toast.LENGTH_SHORT);
    }

    private List<Map<String, String>> loadData() {
        List<Map<String, String>> maps = new ArrayList<Map<String, String>>();
        Map<String,String> item = new HashMap<String, String>();
        item.put("title", "默认搜索引擎");
        item.put("detail", "百度，必应");
        maps.add(item);
        return maps;
    }
//    class MyListAdapter extends BaseAdapter {
//
//        View items[];
//        Context context;
//        public MyListAdapter(Context context,Map<?,?> Data){
//            this.context = context;
//            int len = Data.size();
//            items = new View[len];
//            Iterator iter = Data.entrySet().iterator();
//            int i = 0;
//            while (iter.hasNext()){
//                Map.Entry  entry = (Map.Entry)iter.next();
//                items[i] = CreateView(entry);
//                i++;
//            }
//        }
//        public View CreateView(Map.Entry entry){
//            String key = entry.getKey().toString();
//            String value = entry.getValue().toString();
//            LayoutInflater  inflater = (LayoutInflater)context
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View view = null;
//            view = inflater.inflate(R.layout.listitems,null);
//            TextView title = (TextView)view.findViewById(R.id.title);
//            TextView content = (TextView)view.findViewById(R.id.detail);
//            title.setText(key);
//            content.setText(value);
//            return view;
//        }
//        @Override
//        public int getCount() {
//            return items.length;
//        }
//
//        @Override
//        public Object getItem(int i) {
//            return items[i];
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return i;
//        }
//
//        @Override
//        public View getView(int i, View view, ViewGroup viewGroup) {
//            return items[i];
//        }
//    }

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