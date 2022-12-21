package live.midreamsheep.editor.activities;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import live.midreamsheep.editor.R;
import live.midreamsheep.editor.tool.file.FileController;

public class HomePage extends AppCompatActivity {

    private FileTreeApadar fileTreeApadar;
    private RecyclerView recyclerView;
    private List<File> files = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        recyclerView = findViewById(R.id.fileList);
        FileController.currentFileDir = FileController.file;
        if(!FileController.file.exists()){
            FileController.file.mkdirs();
        }
        File[] files = FileController.currentFileDir.listFiles();
        this.files = Arrays.asList(files == null||files.length==0 ? new File[0] : files);
        System.out.println(FileController.file.getAbsoluteFile());

        fileTreeApadar = new FileTreeApadar();
        recyclerView.setAdapter(fileTreeApadar);
        LinearLayoutManager layoutManager = new LinearLayoutManager(HomePage.this);
        recyclerView.setLayoutManager(layoutManager);
    }

    class FileTreeApadar extends RecyclerView.Adapter<MyViewHoder> {

        @NonNull
        @Override
        public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(HomePage.this, R.layout.item_file, null);
            return new MyViewHoder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {
            String fileName = files.get(position).getName();
            holder.fileName.setText(fileName);
        }

        @Override
        public int getItemCount() {
            return files.size();
        }
    }

    static class MyViewHoder extends RecyclerView.ViewHolder {
        TextView fileName;

        public MyViewHoder(@NonNull View itemView) {
            super(itemView);
            fileName = itemView.findViewById(R.id.file_name);
        }
    }


}