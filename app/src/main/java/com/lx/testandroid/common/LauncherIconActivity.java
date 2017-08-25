package com.lx.testandroid.common;

import com.lx.testandroid.R;
import com.lx.testandroid.shortcut.shortcut_lib.ShortcutUtils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LauncherIconActivity extends AppCompatActivity {

    public static final String ACTION_PLAY = "action_play";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher_icon);

        TextView addText = (TextView) findViewById(R.id.add);
        addText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent shortcutAction = new Intent(getApplicationContext(), NumberTextActivity.class);
                shortcutAction.setAction(ACTION_PLAY);

                Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
                Paint paint = new Paint();
                paint.setColor(0xFF808080); // gray
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTextSize(50);
                new Canvas(bitmap).drawText("" + 3, 50, 50, paint);

                ShortcutUtils.addShortcut(getApplication(), shortcutAction, "ttt", false, bitmap);
            }
        });
        TextView delText = (TextView) findViewById(R.id.del);
        delText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent shortcutAction = new Intent(getApplicationContext(), NumberTextActivity.class);
                shortcutAction.setAction(ACTION_PLAY);

                ShortcutUtils.removeShortcut(getApplication(), shortcutAction, "ttt");
            }
        });
    }

}
