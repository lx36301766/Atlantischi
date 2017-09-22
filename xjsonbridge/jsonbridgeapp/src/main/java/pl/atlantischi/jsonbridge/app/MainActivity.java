package pl.atlantischi.jsonbridge.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.alibaba.fastjson.parser.deserializer.ExtraTypeProvider;
import com.alibaba.fastjson.parser.deserializer.FieldTypeResolver;
import com.alibaba.fastjson.parser.deserializer.ParseProcess;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StringBuilder builder = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open("test.json"), "UTF-8"));
            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                builder.append(mLine);
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }

        String jsonString = builder.toString();

        Gson gson = new GsonBuilder().setFieldNamingStrategy(new FieldNamingStrategy() {
                    @Override
                    public String translateName(Field f) {
                        if (f.isAnnotationPresent(XJsonBridgeRename.class)) {
                            XJsonBridgeRename xJsonBridgeRename = f.getAnnotation(XJsonBridgeRename.class);
                            return xJsonBridgeRename.value();
                        }
                        return f.getName();
                    }
                })
                .create();
        PayCenterBean gsonBean = gson.fromJson(jsonString, PayCenterBean.class);

        ParseProcess processor = new ParseProcessImpl();
        PayCenterBean fastjsonBean = JSON.parseObject(jsonString, PayCenterBean.class, processor, new Feature[0]);

        Moshi moshi = new Moshi.Builder().build();
        final JsonAdapter<PayCenterBean> jsonAdapter = moshi.adapter(PayCenterBean.class);
        try {
            PayCenterBean moshiBean = jsonAdapter.fromJson(jsonString);
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ParseProcessImpl implements ExtraProcessor {

        @Override
        public void processExtra(Object object, String key, Object value) {
            System.out.println();
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(XJsonBridgeRename.class)) {
                    XJsonBridgeRename xJsonBridgeRename = field.getAnnotation(XJsonBridgeRename.class);
                    if (xJsonBridgeRename.value().equals(key)) {
                        try {
                            field.set(object, value);
                            break;
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

}
