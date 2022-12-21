package live.midreamsheep.editor.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import live.midreamsheep.editor.R;
import live.midreamsheep.editor.data.AndroidConfig;
import live.midreamsheep.editor.tool.file.FileController;
import live.midreamsheep.hexo.netapi.hand.net.ConnectorConfig;


public class Loading extends AppCompatActivity {

    ProgressBar processBar;
    TextView processText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        //获取进度条
        processBar = findViewById(R.id.progressBar);
        processText = findViewById(R.id.progress);
        requestPermission();
        processBar.setProgress(25);
        //加载配置文件
        SharedPreferences config = getSharedPreferences("config", MODE_PRIVATE);
        processBar.setProgress(50);
        processText.setText("正在解析配置文件");
        AndroidConfig.isConfig = config.getBoolean("isConfig", false);
        if(AndroidConfig.isConfig) {
            ConnectorConfig.toIp= config.getString("ip", "");
            ConnectorConfig.toPort = config.getInt("port", 52088);
            ConnectorConfig.password = config.getString("password", "");
        }
        FileController.file = new File(config.getString("file", Environment.getExternalStorageDirectory()+"/Documents"));
        processBar.setProgress(100);
        //加载主界面
        Intent intent = new Intent(Loading.this, HomePage.class);
        startActivity(intent);
        finish();
    }
    private void requestPermission() {
        int permission_write= ContextCompat.checkSelfPermission(Loading.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission_read=ContextCompat.checkSelfPermission(Loading.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permission_write!= PackageManager.PERMISSION_GRANTED
                || permission_read!=PackageManager.PERMISSION_GRANTED){
            //申请权限，特征码自定义为1，可在回调时进行相关判断
            ActivityCompat.requestPermissions(Loading.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    //权限已成功申请
                }else{
                    //用户拒绝授权
                    Toast.makeText(this, "无法获取SD卡读写权限", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}