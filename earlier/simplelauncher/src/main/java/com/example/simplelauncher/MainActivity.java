package com.example.simplelauncher;

import android.os.Bundle;
import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Intent;
import android.view.Menu;

public class MainActivity extends LauncherActivity {
    @Override
    protected void onCreate(Bundle icicle) {
        // TODO Auto-generated method stub
        super.onCreate(icicle);
    }

    @Override
    protected Intent getTargetIntent() {
        // TODO Auto-generated method stub
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory("android.intent.category.LAUNCHER");
        return intent;
    }

}
