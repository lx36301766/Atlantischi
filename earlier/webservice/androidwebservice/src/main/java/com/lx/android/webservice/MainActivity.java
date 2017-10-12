package com.lx.android.webservice;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.lx.android.webservice.bean.ListAttachBean;
import com.lx.android.webservice.ws.*;
import org.ksoap2.serialization.SoapObject;

import java.util.List;

public class MainActivity extends Activity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.text);
        textView.setTextSize(12);
        execTask();
    }

    private void execTask() {
        AsyncTask<Void, Void, WSResult> task = new AsyncTask<Void, Void, WSResult>() {

            @Override
            protected WSResult doInBackground(Void... params) {
                WSConfig config = buildListAttach();
                return WSUtils.callWebService(config);
            }

            @Override
            protected void onPostExecute(WSResult result) {
                super.onPostExecute(result);
                if (result == null) {
                    textView.setText("null");
                    Log.d("lx", "onPostExecute, result is null");
                    return;
                }
                if (result.getFault() != null) {
                    Log.w("lx", "onPostExecute, get fault : \n" + result.getResultString());
                    textView.setText(result.getFault().toString());
                } else {
                    SoapObject soapObject = result.getResultObject();
//                    LoginFromMobileBean bean1 = WSSoapParser.parseLoginFromMobile(soapObject);
//                    List<ListNoticeTitleBean> beans = WSSoapParser.parseListNoticeTitle(soapObject);
//                    ListNoticeDetailBean bean2 = WSSoapParser.parseListNoticeDetail(soapObject);
                    List<ListAttachBean> beans = WSSoapParser.parseListAttach(soapObject);
                    textView.setText(buildListString(beans));
                    System.out.println();
                }
            }
        };
        task.execute((Void) null);
    }

    private String buildListString(List<?> list) {
        StringBuilder builder = new StringBuilder();
        builder.append("\n[");
        for (Object bean : list) {
            builder.append(bean);
            builder.append("\n");
        }
        builder.append("]");
        return builder.toString();
    }


    /**
     * test data*
     */

    public static WSConfig buildLoginFromMobile() {
        return WSConfigBuilder.buildLoginFromMobile("张刚伟", "654321", "13978666622");
    }

    public static WSConfig buildListNoticeTitle() {
        return WSConfigBuilder.buildListNoticeTitle("186");
    }

    public static WSConfig buildListNoticeDetail() {
        return WSConfigBuilder.buildListNoticeDetail("10000971");
    }

    public static WSConfig buildListAttach() {
        return WSConfigBuilder.buildListAttach("605824@1401_950179949");
    }

}
