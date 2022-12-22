package live.midreamsheep.editor.activities.homepage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import live.midreamsheep.editor.R;
import live.midreamsheep.editor.activities.setting.SettingsActivity;
import live.midreamsheep.editor.data.AndroidConfig;
import live.midreamsheep.editor.tool.file.FileController;
import live.midreamsheep.editor.tool.handler.HandlerInter;
import live.midreamsheep.hexo.netapi.hand.HandlerEnum;
import live.midreamsheep.hexo.netapi.hand.HandlerMapper;
import live.midreamsheep.hexo.netapi.hand.net.ListenerApi;
import live.midreamsheep.hexo.netapi.hand.net.NetToolApi;

public class MenuMapper {
    public static Map<Integer, HandlerInter> map = new HashMap<>();
    static{
        map.put(R.id.setting,(a)->{
            Intent intent = new Intent(a, SettingsActivity.class);
            a.startActivity(intent);
        });
        map.put(R.id.manager_dpush,(a)->{
            new Thread(()->{
                HandlerMapper.handlerMap.get(HandlerEnum.DPUSH.getId()).handle(new byte[0]);
                //ui线程发送提示
                a.runOnUiThread(()->{
                    Toast.makeText(a,"dpush成功",Toast.LENGTH_SHORT).show();
                });
            }).start();
        });
        map.put(R.id.manager_pull,(a)->{
            new Thread(()->{
                HandlerMapper.handlerMap.get(HandlerEnum.PULL.getId()).handle(new byte[0]);
                //ui线程发送提示
                a.runOnUiThread(()->{
                    Toast.makeText(a,"拉取成功",Toast.LENGTH_SHORT).show();
                });
            }).start();
        });
        map.put(R.id.manager_push,(a)->{
            new Thread(()->{
                HandlerMapper.handlerMap.get(HandlerEnum.PUSH.getId()).handle(new byte[0]);
                //ui线程发送提示
                a.runOnUiThread(()->{
                    Toast.makeText(a,"push成功",Toast.LENGTH_SHORT).show();
                });
            }).start();
        });
        map.put(R.id.create_file,(a)->{
            a.runOnUiThread(()->{
                final EditText inputServer = new EditText(a);
                //添加取消
                //添加"Yes"按钮
                AlertDialog dialog =new AlertDialog.Builder(a)
                        .setTitle("请输入文件名")//标题
                        .setView(inputServer)//内容
                        .setPositiveButton("确定", (dialogInterface, i) -> {
                            String fileName = inputServer.getText().toString()+".md";
                            File file = new File(FileController.currentFileDir, fileName);
                            //创建文件
                            try {
                                FileOutputStream outputStream = new FileOutputStream(file);
                                outputStream.close();
                            }catch (Exception ignored){
                            }
                            HomePage.files.add(file);
                            HomePage.fileTreeApadar.notifyDataSetChanged();
                            if(!AndroidConfig.isConfig){
                                return;
                            }
                            ListenerApi.fileCreate(file,false);
                        })
                        .setNegativeButton("取消", (dialogInterface, i) -> {
                        })
                        .create();
                dialog.show();

            });
        });
        map.put(R.id.create_folder,(a)->{
            a.runOnUiThread(()->{
                final EditText inputServer = new EditText(a);
                //添加取消
                //添加"Yes"按钮
                AlertDialog dialog =new AlertDialog.Builder(a)
                        .setTitle("请输入文件名")//标题
                        .setView(inputServer)//内容
                        .setPositiveButton("确定", (dialogInterface, i) -> {
                            String fileName = inputServer.getText().toString();
                            File file = new File(FileController.currentFileDir, fileName);
                            //创建文件
                            file.mkdirs();
                            HomePage.files.add(file);
                            HomePage.fileTreeApadar.notifyDataSetChanged();
                            if(!AndroidConfig.isConfig){
                                return;
                            }
                            ListenerApi.directoryCreate(file,false);
                        })
                        .setNegativeButton("取消", (dialogInterface, i) -> {
                        })
                        .create();
                dialog.show();

            });
        });
    }
}
