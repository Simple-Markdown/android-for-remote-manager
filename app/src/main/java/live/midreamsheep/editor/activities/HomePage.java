package live.midreamsheep.editor.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.ui.AppBarConfiguration;

import live.midreamsheep.editor.R;
import live.midreamsheep.editor.databinding.ActivityHomePageBinding;

public class HomePage extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityHomePageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    }


}