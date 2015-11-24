package com.example.VoiceRecog.uilts;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.*;

/**
 * Created by 孔轩志 on 2015/11/17.
 */
public class operatorFile {

    private Context context;

    public operatorFile(Context context){
        this.context = context;
    }
    //外置内存卡根路径
    public String ReadFromSdCard(String fileName) throws IOException{
        File file = new File(Environment.getExternalStorageDirectory(), fileName);
        //获得输入流
        if(file.exists()) {
            FileInputStream inStream = new FileInputStream(file);
            return ReadFile(inStream);
        }else {
            Toast.makeText(context,"文件不存在",Toast.LENGTH_SHORT).show();
            return  null;
        }

    }
    //内置内存/data/data/下面包名
    public String ReadFromInner(String fileName) throws IOException {
        //获得文件的输入流
        File file = context.getFileStreamPath(fileName);
        if(file.exists()){
            FileInputStream inputStream = context.openFileInput(fileName);
            return ReadFile(inputStream);
        }else
        {
            Toast.makeText(context, "文件不存在", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public void WriteToSdCard(String fileName,String Content,boolean append) throws IOException{
        File file = new File(Environment.getExternalStorageDirectory(),fileName);
        FileOutputStream outStream = new FileOutputStream(file.getPath(),append);
        WriteFile(outStream, Content);
    }
    public void WriteToInner(String fileName,String Content,boolean append) throws IOException {

        int mode = Context.MODE_PRIVATE;
        if(append){
            mode = Context.MODE_APPEND;
        }
        FileOutputStream outStream = context.openFileOutput(fileName,mode);
        WriteFile(outStream, Content);
    }

    private void WriteFile( FileOutputStream outStream,String Content) throws IOException {
        outStream.write(Content.getBytes());
        outStream.flush();
        outStream.close();
       // Toast.makeText(context,"写入成功",Toast.LENGTH_SHORT).show();
    }

    private String ReadFile(FileInputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        //之后将从文件中读到的内容输出到输出流中，在转化为字节数组，最后转化为字符串

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        while ((len= inputStream.read(buffer))!=-1){
            outputStream.write(buffer,0,len);
        }
        //得到文件的二进制数据
        byte[] data= outputStream.toByteArray();
        inputStream.close();
        outputStream.close();
       // Toast.makeText(context, "读取成功", Toast.LENGTH_SHORT).show();
        return new String(data);
    }

    public void DeleteFiles(File file){
        if(file.exists()){
            if(file.isFile()){
                file.delete();
            }else{
                File file1[] = file.listFiles();
                for (int i=0;i<file1.length;i++){
                    file1[i].delete();
                }
            }
           // Toast.makeText(context,"删除成功",Toast.LENGTH_SHORT).show();
        }else {
           // Toast.makeText(context,"文件不存在",Toast.LENGTH_SHORT).show();
        }
    }
}
