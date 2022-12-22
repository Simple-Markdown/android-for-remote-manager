package live.midreamsheep.editor.activities.editor;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import live.midreamsheep.editor.R;
import live.midreamsheep.editor.tool.handler.HandlerInter;

public class ToolBarMapper {
    public static Map<Integer,String> map = new HashMap<>();
    static{
        map.put(R.id.bold,"**");
        map.put(R.id.title,"#");
        map.put(R.id.strikethrough,"__");
        map.put(R.id.list,"-");
        map.put(R.id.italic,">");
    }
}
