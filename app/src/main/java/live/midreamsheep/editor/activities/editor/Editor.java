package live.midreamsheep.editor.activities.editor;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.Executors;

import io.noties.markwon.Markwon;
import io.noties.markwon.editor.MarkwonEditor;
import io.noties.markwon.editor.MarkwonEditorTextWatcher;
import live.midreamsheep.editor.R;
import live.midreamsheep.editor.tool.file.FileController;
import live.midreamsheep.editor.tool.io.SIO;
import live.midreamsheep.hexo.netapi.hand.net.ListenerApi;

public class Editor extends AppCompatActivity implements View.OnClickListener {
    EditText editText;
    LinearLayout toolbar;
    FloatingActionButton actionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        String s = SIO.inputString(FileController.currentFile);
        editText = findViewById(R.id.editor);
        editText.setText(s);
        // obtain Markwon instance
        final Markwon markwon = Markwon.create(this);
        // create editor
        final MarkwonEditor editor = MarkwonEditor.create(markwon);
        // set edit listener
        editText.addTextChangedListener(MarkwonEditorTextWatcher.withPreRender(
                editor,
                Executors.newCachedThreadPool(),
                editText));
        actionButton = findViewById(R.id.floating_action_button);
        toolbar = findViewById(R.id.BottomToolbar);
        for (Map.Entry<Integer, String> entry : ToolBarMapper.map.entrySet()) {
            toolbar.findViewById(entry.getKey()).setOnClickListener(this);
        }
        setMenuLinstener();
    }

    private void setMenuLinstener() {
        actionButton.setOnClickListener(v -> {
            // View当前PopupMenu显示的相对View的位置
            PopupMenu popupMenu = new PopupMenu(this, actionButton);
            // menu布局
            popupMenu.getMenuInflater().inflate(R.menu.edit_menu, popupMenu.getMenu());
            // menu的item点击事件
            popupMenu.setOnMenuItemClickListener(item -> {
                hand(item);
                return false;
            });
            popupMenu.show();
        });
    }

    private void hand(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.keep:
                SIO.outPutString(FileController.currentFile, editText.getText().toString());
                ListenerApi.fileChange(FileController.currentFile,false);
                break;
            case R.id.preview:
                break;
            case R.id.ret:
                finish();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        String txt = "";
        txt = ToolBarMapper.map.get(v.getId());
        insertTextToEditText(txt);
    }


    private void insertTextToEditText(String txt) {
        if (TextUtils.isEmpty(txt)) return;
        int start = editText.getSelectionStart();
        int end = editText.getSelectionEnd();
        Editable edit = editText.getEditableText();//获取EditText的文字
        if (start < 0 || start >= edit.length()) {
            edit.append(txt);
        } else {
            edit.replace(start, end, txt);//光标所在位置插入文字
        }
    }


}