package live.midreamsheep.editor.activities.homepage;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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

import live.midreamsheep.editor.R;
import live.midreamsheep.editor.tool.file.FileController;

public class HomePage extends AppCompatActivity {

    private FileTreeApadar fileTreeApadar;
    private RecyclerView recyclerView;
    private MaterialToolbar toolbar;
    private List<File> files = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        recyclerView = findViewById(R.id.fileList);
        toolbar = findViewById(R.id.topAppBar);
        setToolBar();
        FileController.currentFileDir = FileController.file;
        if(!FileController.file.exists()){
            FileController.file.mkdirs();
        }
        new File(Environment.getExternalStorageDirectory()+"/Documents/"+"asad").mkdirs();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                Files.createFile(new File(Environment.getExternalStorageDirectory()+"/Documents/"+"asad.txt").toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File[] files = FileController.currentFileDir.listFiles();
        this.files = Arrays.asList(files == null||files.length==0 ? new File[0] : files);
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

            if (file.isDirectory()) {
                holder.imageView.setImageDrawable(getDrawable(R.drawable.folder_48px));
                holder.self.setOnClickListener(v -> {
                    FileController.currentFileDir = file;
                    FileController.parentFiles.add(file);
                    File[] files = file.listFiles();
                    HomePage.this.files = Arrays.asList(files==null||files.length==0 ? new File[0] : files);
                    fileTreeApadar.notifyDataSetChanged();
                });
                return;
            }
            if(file.getAbsoluteFile().toString().endsWith(".md")){
                holder.imageView.setImageDrawable(getDrawable(R.drawable.file_open_48px));
                holder.self.setOnClickListener(v -> {
                    System.out.println("开始编辑");
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