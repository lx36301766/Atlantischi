package pl.atlantischi.robusttest;

import com.meituan.robust.patch.annotaion.Modify;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class RobustActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robust);
        TextView tvTest = (TextView) findViewById(R.id.test);
        tvTest.setText("before hotfix");

    }

//    @Modify
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_robust);
//        TextView tvTest = (TextView) findViewById(R.id.test);
//        tvTest.setText("after hotfix");
//    }

}
