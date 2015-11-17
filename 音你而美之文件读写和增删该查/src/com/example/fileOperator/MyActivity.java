package com.example.fileOperator;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import com.example.fileOperator.utils.operatorFile;

import java.io.*;

public class MyActivity extends Activity implements View.OnClickListener {
    /**
     * Called when the activity is first created.
     */
    operatorFile fileOperator;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setUpViewInit();
    }
    private void setUpViewInit() {
        fileOperator = new operatorFile(this);

        this.findViewById(R.id.readFromInner).setOnClickListener(this);
        this.findViewById(R.id.readFromSdCard).setOnClickListener(this);
        this.findViewById(R.id.writeToSdCard).setOnClickListener(this);
        this.findViewById(R.id.writeToinner).setOnClickListener(this);
        this.findViewById(R.id.DeleteFileFromInner).setOnClickListener(this);
        this.findViewById(R.id.DeleteFileFromSdCard).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        EditText fileName = (EditText) this.findViewById(R.id.fileName);
        EditText content = (EditText) this.findViewById(R.id.Content);

        EditText fileContents = (EditText) this.findViewById(R.id.ReadResult);
        try {
            switch (view.getId()) {
                case R.id.writeToinner:
                    fileOperator.WriteToInner(fileName.getText().toString(), content.getText().toString(),true);
                    break;
                case R.id.readFromInner:
                    fileContents.setText(fileOperator.ReadFromInner(fileName.getText().toString()));
                    break;
                case R.id.writeToSdCard:
                    fileOperator.WriteToSdCard(fileName.getText().toString(), content.getText().toString(),false);
                    break;
                case R.id.readFromSdCard:
                    fileContents.setText(fileOperator.ReadFromSdCard(fileName.getText().toString()));
                    break;
                case R.id.DeleteFileFromInner:
                    File file = this.getFileStreamPath(fileName.getText().toString());
                    fileOperator.DeleteFiles(file);
                    break;
                case R.id.DeleteFileFromSdCard:
                    File file2 = new File(Environment.getExternalStorageDirectory(),fileName.getText().toString());
                    fileOperator.DeleteFiles(file2);
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}