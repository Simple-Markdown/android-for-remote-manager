package live.midreamsheep.editor.activities.homepage;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import live.midreamsheep.editor.R;
import live.midreamsheep.editor.activities.editor.Editor;
import live.midreamsheep.editor.data.AndroidConfig;
import live.midreamsheep.editor.tool.file.FileController;
import live.midreamsheep.hexo.netapi.hand.net.ListenerApi;

public class HomePage extends AppCompatActivity {

    public static FileTreeApadar fileTreeApadar;
    private RecyclerView recyclerView;
    private MaterialToolbar toolbar;
    private LinearLayout parentFile;
    public static CopyOnWriteArrayList<File> files = new CopyOnWriteArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        recyclerView = findViewById(R.id.fileList);
        toolbar = findViewById(R.id.topAppBar);
        parentFile = findViewById(R.id.parentFile);
        parentFile.setOnClickListener(v -> {
            if(FileController.parentFiles.size()==0){
                return;
            }
            File file = FileController.parentFiles.get(FileController.parentFiles.size() - 1);
            FileController.parentFiles.remove(FileController.parentFiles.size()-1);
            FileController.currentFileDir = file;
            File[] files = file.listFiles();
            this.files.clear();
            this.files.addAll(Arrays.asList(files == null || files.length == 0 ? new File[0] : files));
            fileTreeApadar.notifyDataSetChanged();
        });
        //长按监听
        setToolBar();
        FileController.currentFileDir = FileController.file;
        if(!FileController.file.exists()){
            FileController.file.mkdirs();
        }
        File[] files = FileController.currentFileDir.listFiles();
        this.files.clear();
        this.files.addAll(Arrays.asList(files == null || files.length == 0 ? new File[0] : files));
        fileTreeApadar = new FileTreeApadar();
        recyclerView.setAdapter(fileTreeApadar);
        LinearLayoutManager layoutManager = new LinearLayoutManager(HomePage.this);
        recyclerView.setLayoutManager(layoutManager);
    }
    @SuppressLint("NonConstantResourceId")
    private void setToolBar() {
        toolbar.setOnMenuItemClickListener(item -> {
            if(MenuMapper.map.containsKey(item.getItemId())){
                MenuMapper.map.get(item.getItemId()).handle(this);
            }
            return true;
        });
    }

    class FileTreeApadar extends RecyclerView.Adapter<MyViewHoder> {

        @NonNull
        @Override
        public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(HomePage.this, R.layout.item_file, null);
            return new MyViewHoder(view);
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        @Override
        public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {
            File file = files.get(position);

            String fileName = file.getName();
            holder.fileName.setText(fileName);
            holder.self.setOnLongClickListener(v -> {
                AlertDialog dialog =new AlertDialog.Builder(holder.self.getContext())
                        .setTitle("你确定要删除吗")//标题
                        .setPositiveButton("确定", (dialogInterface, i) -> {
                            file.delete();
                            files.remove(file);
                            fileTreeApadar.notifyDataSetChanged();
                            if(AndroidConfig.isConfig) {
                                if (file.isDirectory()) {
                                    deletefile(file);
                                    return;
                                }
                                ListenerApi.fileDelete(file, false);
                            }
                        })//确定按钮
                        .setNegativeButton("取消", (dialogInterface, i) -> {
                        })
                        .create();
                dialog.show();
                return true;
            });
            if (file.isDirectory()) {
                holder.imageView.setImageDrawable(getDrawable(R.drawable.folder_48px));
                holder.self.setOnClickListener(v -> {
                    FileController.parentFiles.add(FileController.currentFileDir);
                    FileController.currentFileDir = file;
                    File[] childFiles = file.listFiles();
                    files.clear();
                    files.addAll(Arrays.asList(childFiles == null || childFiles.length == 0 ? new File[0] : childFiles));
                    fileTreeApadar.notifyDataSetChanged();
                });
                return;
            }
            if(file.getAbsoluteFile().toString().endsWith(".md")){
                holder.imageView.setImageDrawable(getDrawable(R.drawable.file_open_48px));
                holder.self.setOnClickListener(v -> {
                    Intent intent = new Intent(HomePage.this, Editor.class);
                    FileController.currentFile = file;
                    startActivity(intent);
                });
                return;
            }
            holder.imageView.setImageDrawable(getDrawable(R.drawable.normal_file_48px));
            holder.self.setOnClickListener(v -> {
                Toast.makeText(HomePage.this, "不支持的文件类型", Toast.LENGTH_SHORT).show();
            });
        }

        @Override
        public int getItemCount() {
            return files.size();
        }
    }

    private void deletefile(File file) {
        for (File listFile : file.listFiles()) {
            if(listFile.isDirectory()){
                deletefile(file);
            }
            listFile.delete();
        }
        file.delete();
    }

    static class MyViewHoder extends RecyclerView.ViewHolder {
        TextView fileName;
        ImageView imageView;
        LinearLayout self;
        public MyViewHoder(@NonNull View itemView) {
            super(itemView);
            fileName = itemView.findViewById(R.id.file_name);
            imageView = itemView.findViewById(R.id.file_type);
            self = itemView.findViewById(R.id.linearLayout_self);
        }
    }


}