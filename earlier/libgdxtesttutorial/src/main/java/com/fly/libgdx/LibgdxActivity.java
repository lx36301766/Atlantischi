package com.fly.libgdx;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.fly.libgdx.a_first.FirstGame;
import com.fly.libgdx.b_second.SecondGame;
import com.fly.libgdx.c_third.ThirdGame;
import com.fly.libgdx.d_fourth.FourthGame;
import com.fly.libgdx.e_fifth.FifthGame;
import com.fly.libgdx.f_sixth.SixthGame;
import com.fly.libgdx.g_seven.SevenGame;

public class LibgdxActivity extends AndroidApplication {

    int gameId = 7;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initGame();
    }

    private void initGame() {
        ApplicationListener app = null;
        switch (gameId) {
        case 1:
            app = new FirstGame();
            break;
        case 2:
            app = new SecondGame();
            break;
        case 3:
            app = new ThirdGame();
            break;
        case 4:
            app = new FourthGame();
            break;
        case 5:
            app = new FifthGame();
            break;
        case 6:
            app = new SixthGame();
            break;
        case 7:
            app = new SevenGame();
            break;
        default:
            break;
        }
        initialize(app, false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        menu.add(Menu.NONE, 1, 1, "1");
        menu.add(Menu.NONE, 2, 2, "2");
        menu.add(Menu.NONE, 3, 3, "3");
        menu.add(Menu.NONE, 4, 4, "4");
        menu.add(Menu.NONE, 5, 5, "5");
        menu.add(Menu.NONE, 6, 6, "6");
        menu.add(Menu.NONE, 7, 7, "7");
//        getMenuInflater().inflate(R.menu.app_menu, menu);
        return super.onPrepareOptionsMenu(menu);
    }
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        gameId = item.getItemId();
        initGame();
        return super.onMenuItemSelected(featureId, item);
    }
}