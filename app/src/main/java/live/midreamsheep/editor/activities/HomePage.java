package live.midreamsheep.editor.activities;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import live.midreamsheep.editor.R;

public class HomePage extends AppCompatActivity {

    private FileTreeApadar fileTreeApadar;
    private RecyclerView recyclerView;
    private List<String> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        recyclerView = findViewById(R.id.fileList);
        fileTreeApadar = new FileTreeApadar();
        recyclerView.setAdapter(fileTreeApadar);
        list.add("asdda.md");
        list.add("asdasd");
        list.add("asddwad.pl");
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
            String fileName = list.get(position);
            holder.fileName.setText(fileName);
        }

        @Override
        public int getItemCount() {
            return list.size();
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