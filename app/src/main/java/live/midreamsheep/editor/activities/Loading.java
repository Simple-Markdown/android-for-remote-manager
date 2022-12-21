package live.midreamsheep.editor.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import live.midreamsheep.editor.R;
import live.midreamsheep.editor.data.AndroidConfig;
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
        //加载配置文件
        SharedPreferences config = getSharedPreferences("config", MODE_PRIVATE);
        processBar.setProgress(25);
        processText.setText("正在解析配置文件");
        AndroidConfig.isConfig = config.getBoolean("isConfig", false);
        if(AndroidConfig.isConfig) {
            ConnectorConfig.toIp= config.getString("ip", "");
            ConnectorConfig.toPort = config.getInt("port", 52088);
            ConnectorConfig.password = config.getString("password", "");
        }
        //加载主界面
        Intent intent = new Intent(Loading.this, HomePage.class);
        startActivity(intent);
        finish();
    }
}