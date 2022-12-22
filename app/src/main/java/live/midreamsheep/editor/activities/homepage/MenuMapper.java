package live.midreamsheep.editor.activities.homepage;

import android.content.Intent;

import java.util.HashMap;
import java.util.Map;

import live.midreamsheep.editor.R;
import live.midreamsheep.editor.activities.setting.SettingsActivity;
import live.midreamsheep.editor.tool.handler.HandlerInter;

public class MenuMapper {
    public static Map<Integer, HandlerInter> map = new HashMap<>();
    static{
        map.put(R.id.setting,(a)->{
            Intent intent = new Intent(a, SettingsActivity.class);
            a.startActivity(intent);
        });
        map.put(R.id.manager_dpush,(a)->{

        });
        map.put(R.id.manager_pull,(a)->{

        });
        map.put(R.id.manager_push,(a)->{

        });
        map.put(R.id.create_file,(a)->{

        });
        map.put(R.id.create_folder,(a)->{

        });
    }
}
