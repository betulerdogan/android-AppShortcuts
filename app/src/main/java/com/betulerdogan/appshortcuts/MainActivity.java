package com.betulerdogan.appshortcuts;

import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);

        ShortcutInfo browserShortcut = new ShortcutInfo.Builder(this,"shortcut_browser")
                .setShortLabel("google.com")
                .setLongLabel("open google.com")
                .setDisabledMessage("dynamic shortcut disable")
                .setIcon(Icon.createWithResource(this,R.drawable.ic_open_in_browser))
                .setIntent(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com")))
                .setRank(0)
                .build();

        ShortcutInfo dynamicShortcut = new ShortcutInfo.Builder(this,"dynamic_shortcut")
                .setShortLabel("Dynamic")
                .setLongLabel("Open dynamic shortcut")
                .setIcon(Icon.createWithResource(this,R.drawable.ic_dynamic))
                .setIntents(
                        new Intent[]{
                                new Intent(Intent.ACTION_MAIN, Uri.EMPTY,this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK),
                                new Intent(DynamicShortcutActivity.ACTION)
                        })
                .setRank(1)
                .build();

        shortcutManager.setDynamicShortcuts(Arrays.asList(browserShortcut,dynamicShortcut));

        Button updateShortcutsBtn = findViewById(R.id.update_shortcuts);
        updateShortcutsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(android.R.color.holo_red_light,getTheme()));
                String label = "open google.com";

                SpannableStringBuilder colouredLabel = new SpannableStringBuilder(label);
                colouredLabel.setSpan(colorSpan,0,label.length(),Spanned.SPAN_INCLUSIVE_INCLUSIVE);

                ShortcutInfo browserShortcut = new ShortcutInfo.Builder(MainActivity.this,"shortcut_browser")
                        .setShortLabel(colouredLabel)
                        .setRank(1)
                        .build();

                ShortcutInfo dynamicShortcut = new ShortcutInfo.Builder(MainActivity.this,"dynamic shortcut")
                        .setRank(0)
                        .build();

                shortcutManager.updateShortcuts(Arrays.asList(browserShortcut,dynamicShortcut));

                Toast.makeText(MainActivity.this,"Kısayollar Güncellendi :",Toast.LENGTH_SHORT).show();
            }
        });

        Button disableShortcutBtn = findViewById(R.id.disable_shortcut);
        disableShortcutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shortcutManager.disableShortcuts(Arrays.asList("dynamic shortcut"));
                Toast.makeText(MainActivity.this,"Dinamik Kısayol Devre dışı!!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
