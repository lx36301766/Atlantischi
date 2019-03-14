package com.lx.testandroid.googleauth;

import com.lx.testandroid.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class GoogleAuthActivity extends AppCompatActivity {

    TextView authCodeTv;
    EditText deviceIdEt;
    Button refreshBtn;

    GoogleAuthenticator authenticator = new GoogleAuthenticator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_auth);
        authCodeTv = (TextView) findViewById(R.id.auth_code);
        deviceIdEt = (EditText) findViewById(R.id.device_id_edit);
        deviceIdEt.setText("");
        refreshBtn = (Button) findViewById(R.id.refresh_btn);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String deviceId = deviceIdEt.getText().toString();
                    long code = authenticator.get_code(deviceId, System.currentTimeMillis());
                    authCodeTv.setText("" + code);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        authenticator.setWindowSize(5);
    }

}
