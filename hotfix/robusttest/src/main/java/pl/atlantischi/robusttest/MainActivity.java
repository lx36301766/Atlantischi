package pl.atlantischi.robusttest;

import java.util.List;

import com.meituan.robust.Patch;
import com.meituan.robust.PatchExecutor;
import com.meituan.robust.RobustCallBack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import pl.atlantischi.robusttest.patch.PatchManipulateImpl;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PatchExecutor(getApplication(), new PatchManipulateImpl(), new RobustCallBackImpl()).start();
            }
        });

        findViewById(R.id.jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RobustActivity.class));
            }
        });
    }

    static class RobustCallBackImpl implements RobustCallBack {

        @Override
        public void onPatchListFetched(boolean result, boolean isNet, List<Patch> patches) {
            Log.d(TAG, "onPatchListFetched() called with: result = [" + result + "], isNet = [" + isNet + "]");
        }

        @Override
        public void onPatchFetched(boolean result, boolean isNet, Patch patch) {
            Log.d(TAG, "onPatchFetched() called with: result = [" + result + "], isNet = [" + isNet + "], patch = [" + patch + "]");
        }

        @Override
        public void onPatchApplied(boolean result, Patch patch) {
            Log.d(TAG, "onPatchApplied() called with: result = [" + result + "], patch = [" + patch + "]");
//            mResultTxt.setText("result-->"+result);
        }

        @Override
        public void logNotify(String log, String where) {
            Log.d(TAG, "logNotify() called with: log = [" + log + "], where = [" + where + "]");
        }

        @Override
        public void exceptionNotify(Throwable throwable, String where) {
            Log.d(TAG, "exceptionNotify() called with: throwable = [" + throwable + "], where = [" + where + "]");
        }
    }

}
