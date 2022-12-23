package live.midreamsheep.editor.activities.preview;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import io.noties.markwon.Markwon;
import live.midreamsheep.editor.R;

public class Preview extends AppCompatActivity {
    TextView textView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        String content = getIntent().getStringExtra("content");
        textView = findViewById(R.id.md_preview);
        // obtain an instance of Markwon
        final Markwon markwon = Markwon.create(this);

// set markdown
        markwon.setMarkdown(textView, content);

    }
}