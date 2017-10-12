package com.atway.webshell.widget;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import com.atway.webshell.App;
import com.atway.webshell.AtwayWebActivity;
import com.atway.webshell.PlayerActivity;
import com.atway.webshell.WebReceiverActivity;
import com.atway.webshell.utils.UserTools;
import com.atway.webshell.utils.VersionUtils;
import com.atway.webshell.utils.Xlog;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("all")
public abstract class AtwayWebView extends WebView {

    private String jsExposeName = "Atway";

    static final KeyCharacterMap charMap = KeyCharacterMap.load(0);
    private static final String TAG = AtwayWebView.class.getSimpleName();
    private final AtomicInteger evalJsIndex = new AtomicInteger(0);
    private final Map<Integer, String> jsReturnValues = new HashMap<Integer, String>();
    jsExposeObjectWrapper _wrapper = new jsExposeObjectWrapper();

    private Rect _rect = new Rect();
    private Context mContext;
    private Handler viewHandler = new Handler();
    private JsExtObject _extObj;
    private Class<?> _extObjClass;

    private JSCallback _keyCallback;
    private JSCallback _msgCallback;

    public AtwayWebView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public AtwayWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public AtwayWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    @Override
    protected void onDetachedFromWindow() {
        try {
            addJavascriptInterface(new Object(), jsExposeName);
        } catch (Exception e) {
        }
        jsReturnValues.clear();
        _wrapper.bind(null);
        super.onDetachedFromWindow();
    }

    @Override
    protected void onAttachedToWindow() {
        Xlog.d(TAG, "onAttachedToWindow");
        if (_wrapper == null || _wrapper.isNull()) {
            init();
        }
        super.onAttachedToWindow();
    }

    private void init() {
        getSettings().setUseWideViewPort(true);
        getSettings().setLoadWithOverviewMode(true);
        getSettings().setJavaScriptEnabled(true);
        setWebChromeClient(new WebChromeClient());
        addJavascriptInterface(_wrapper, jsExposeName);
        _wrapper.bind(new JsExposeObjectImpl());
        bindExtObject(createExtObject());
    }

    protected JsExtObject createExtObject() {
        return new JsExtObject();
    }

    public void loadJsCallback(String methodName, String args) {
        args = "'" + args + "'";
        String jsCallback = String.format("javascript:%s(%s)",  methodName, args);
        Xlog.d(TAG, "loadJsCallback, jsCallback=%s", jsCallback);
        loadUrl(jsCallback);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        _rect.left = l;
        _rect.right = r;
        _rect.top = t;
        _rect.bottom = b;
    }

    private void bindExtObject(JsExtObject obj) {
        _extObj = obj;
        if (_extObj != null) {
            _extObjClass = _extObj.getClass();
        } else {
            _extObjClass = null;
        }
    }

    private String _waitForJsReturnValue(int index, int waitMs) {
        synchronized (this) {
            try {
                synchronized (jsReturnValues) {
                    return jsReturnValues.remove(index);
                }
            } catch (Exception e) {
            }
        }
        return "";
    }

    private static class jsExposeObjectWrapper {
        private JsExposeObjectImpl _impl;

        public void bind(JsExposeObjectImpl impl) {
            _impl = impl;
        }

        public Object _execAndroidFunc(String name, String json) {
            if (_impl == null) {
                return null;
            }
            return _impl._execAndroidFunc(name, json);
        }

        public void _setCallbackResult(int index, String value) {
            if (_impl == null) {
                return;
            }
            _impl._setCallbackResult(index, value);
        }

        public boolean isNull() {
            return _impl == null;
        }
    }

    private class JsExposeObjectImpl {
        public Object _execAndroidFunc(String name, String json) {
            if (_extObjClass == null) {
                return null;
            }
            try {
                ArrayList<Class<?>> paraTypes = new ArrayList<Class<?>>();
                ArrayList<Object> paraValues = new ArrayList<Object>();
                JSONArray jsonArgs = new JSONArray(json);
                for (int i = 0; i < jsonArgs.length(); i++) {
                    JSONObject arg = jsonArgs.getJSONObject(i);
                    String type = arg.getString("type");
                    String value = arg.getString("value");
//                    Xlog.i(TAG, "callback:" + type + "::" + value);
                    if ("boolean".equalsIgnoreCase(type)) {
                        paraTypes.add(boolean.class);
                        paraValues.add(Boolean.parseBoolean(value));
                    } else if ("number".equalsIgnoreCase(type)) {
                        paraTypes.add(double.class);
                        paraValues.add(Double.parseDouble(value));
                    } else if ("string".equalsIgnoreCase(type)) {
                        paraTypes.add(String.class);
                        paraValues.add(value);
                    } else if ("callback".equalsIgnoreCase(type)) {
                        JSCallback JSCallback = new JSCallback();
                        JSCallback.methodName = value;
                        paraTypes.add(JSCallback.class);
                        paraValues.add(JSCallback);
                    } else if ("object".equalsIgnoreCase(type)) {
                        paraTypes.add(JSONObject.class);
                        paraValues.add(arg.getJSONObject("value"));
                    } else {
                        throw new IllegalArgumentException("error type : " + type);
                    }
                }
//                Xlog.d(TAG, "name=%s, type=%s, val=%s", name, paraTypes, paraValues);
                Class<?>[] pt = paraTypes.toArray(new Class[paraTypes.size()]);
                Method method = _extObjClass.getMethod(name, pt);
                return method.invoke(_extObj, paraValues.toArray());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        public void _setCallbackResult(int index, String value) {
            synchronized (AtwayWebView.this) {
                synchronized (jsReturnValues) {
                    if (jsReturnValues.containsKey(index)) {
                        jsReturnValues.put(index, value);
                    }
                }
                AtwayWebView.this.notifyAll();
            }
        }
    }

    private boolean _hasFocus = false;
    private KeyEvent _lastEatEvent;

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        _hasFocus = focused;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return handleKey(event) || super.dispatchKeyEvent(event);
    }

    public boolean handleKey(KeyEvent event) {
        if (_keyCallback != null && _hasFocus) {
            if (event.getAction() == KeyEvent.ACTION_UP) {
                if (_lastEatEvent != null && _lastEatEvent.getKeyCode() == event.getKeyCode()) {
                    // eat matched up event
                    _lastEatEvent = null;
                    return true;
                } else {
                    _lastEatEvent = null;
                    return false;
                }
            }
            String action = "";
            switch (event.getAction()) {
                case KeyEvent.ACTION_DOWN:
                    action = "keyDown";
                    break;
                case KeyEvent.ACTION_UP:
                    action = "keyUp";
                    break;
                default:
                    return false;
            }
            JSONArray metaState = new JSONArray();
            if ((event.getMetaState() & KeyEvent.META_ALT_ON) != 0) {
                metaState.put("alt");
            }
            if ((event.getMetaState() & KeyEvent.META_SHIFT_ON) != 0) {
                metaState.put("shift");
            }
            String val = "";
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    val = "LEFT";
                    break;
                case KeyEvent.KEYCODE_DPAD_UP:
                    val = "UP";
                    break;
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    val = "DOWN";
                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    val = "RIGHT";
                    break;
                case KeyEvent.KEYCODE_DPAD_CENTER:
                case KeyEvent.KEYCODE_ENTER:
                    val = "ENTER";
                    break;
                case KeyEvent.KEYCODE_BACK:
                    val = "BACK";
                    break;
                case KeyEvent.KEYCODE_DEL:
                    if ( (event.getMetaState()&KeyEvent.META_SHIFT_ON) != 0) {
                        val = "DELETE";
                    } else {
                        val = "BACKSPACE";
                    }
                    break;
                case KeyEvent.KEYCODE_MEDIA_FAST_FORWARD:
                    val = "MEDIA_FAST_FORWARD";
                    break;
                case KeyEvent.KEYCODE_MEDIA_REWIND:
                    val = "MEDIA_REWIND";
                    break;
                case KeyEvent.KEYCODE_MEDIA_NEXT:
                    val = "MEDIA_NEXT";
                    break;
                case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                    val = "MEDIA_PREVIOUS";
                    break;
                case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
                    val = "MEDIA_PLAY_PAUSE";
                    break;
                case KeyEvent.KEYCODE_MEDIA_STOP:
                    val = "MEDIA_STOP";
                    break;
                case KeyEvent.KEYCODE_MENU:
                    val = "MENU";
                    break;
                default:
                    int ch = charMap.get(event.getKeyCode(), event.getMetaState());
                    if (ch != 0) {
                        val = Character.valueOf((char) ch).toString();
                    }
                    break;
            }
            if ("true".equals(_keyCallback.run(action, event.getKeyCode(), val, metaState.toString()))) {
                _lastEatEvent = event;
                return true;
            } else {
                _lastEatEvent = null;
            }
            return true;
        }
        return false;
    }

    BroadcastReceiver callbackReceiver;

    public class JsExtObject {

        private final String TAG = JsExtObject.class.getSimpleName();

        /**
         * 调用电信的支付接口
         * @param appParams 包含启动第三方apk包名，类名等
         * @param thridParams 透传给第三方apk的参数
         * @param jsCallback 支付完成的回调
         */
        public void bellmannPay(JSONObject appParams, JSONObject thridParams) {
            Xlog.d(TAG, "bellmannPay, appParams=%s\n thridParams=%s", appParams, thridParams);
            if (callbackReceiver != null) {
                ((Activity) getContext()).unregisterReceiver(callbackReceiver);
            }
            final String callbackName = appParams.optString("callback_name");
            callbackReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Xlog.d(TAG, "callbackReceiver onReceive");
                    if (intent != null) {
                        String json = intent.getStringExtra("jsonData");
                        loadJsCallback(callbackName, json);
                    }
                }
            };
            IntentFilter filter = new IntentFilter();
            filter.addAction(WebReceiverActivity.DELIVER_DATA);
            ((Activity) getContext()).registerReceiver(callbackReceiver, filter);

            String pkgName = appParams.optString("package_name");
            String className = appParams.optString("class_name");
            Intent intent = new Intent();
            intent.setClassName(pkgName, className);
            intent.putExtra("jsonData", thridParams.toString());
            getContext().startActivity(intent);
        }

        public String encodeInfo(String userInfo, String key) {
            Xlog.d(TAG, "encodeInfo, userInfo=%s, key=%s", userInfo, key);
            return UserTools.encodeInfo(userInfo, key);
        }

        public String decodeInfo(String userInfo, String key) {
            Xlog.d(TAG, "decodeInfo, userInfo=%s, key=%s", userInfo, key);
            return UserTools.getOriginalInfo(key, userInfo);
        }

        public String getKey() {
            String key = UserTools.getKey(getUserInfo("user_id"));
            Xlog.d(TAG, "getKey, key=%s", key);
            return key;
        }

        public void startPlayer(JSONObject jsonObject) {
            Xlog.d(TAG, "startPlayer, jsonObject=%s", jsonObject);
            Intent intent = new Intent();
            intent.setClass(getContext(), PlayerActivity.class);
            intent.putExtra("jsonData", jsonObject.toString());
            ((Activity) getContext()).startActivityForResult(intent, AtwayWebActivity.CODE_REQ_PLAYER);
        }

        public String getUser() {
            String userId = getUserInfo("user_id");
            String userToken = getUserInfo("user_token");
            //Xlog.d(TAG, "getUser, userId=%s, userToken=%s", userId, userToken);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("user_id", userId);
                jsonObject.put("user_token", userToken);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Xlog.d(TAG, "getUser, " + jsonObject.toString());
            return jsonObject.toString();
        }

        private String getUserInfo(String key) {
            Uri uri = Uri.parse("content://com.hunantv.operator.mango.hndxiptv/userinfo");
            Cursor mCursor = mContext.getContentResolver().query(uri, null, null, null, null);
            if (mCursor != null) {
                while (mCursor.moveToNext()) {
                    String name = mCursor.getString(mCursor.getColumnIndex("name"));
                    if(key.equals(name)){
                        return mCursor.getString(mCursor.getColumnIndex("value"));
                    }
                }
                mCursor.close();
            }
            return "";
        }

        public void log(String tag, String info) {
            Xlog.i(tag, info);
        }

        // 获取焦点
        // webview获取焦点后，所有按键事件都将转发给页面处理
        public void requestFocus() {
            viewHandler.post(new Runnable() {
                @Override
                public void run() {
                    AtwayWebView.this.setFocusable(true);
                    AtwayWebView.this.requestFocus();
                }
            });
        }

        // 释放焦点
        // direction: 将焦点释放给当前webview在某个方向上的相邻元素，如无相邻元素，则焦点不变化
        public void releaseFocus(String direction) {
            viewHandler.post(new Runnable() {
                @Override
                public void run() {
                    AtwayWebView.this.clearFocus();
                }
            });
        }

        // 是否有焦点
        public boolean hasFocus() {
            return _hasFocus;
        }

        public String getScreenSize() {
            return App.SCREEN_WIDTH + "," + App.SCREEN_HEIGHT;
        }

        public String readSystemProp(String propName) {
            if ("version_code".equals(propName)) {
                return VersionUtils.getVersionCode(mContext);
            } else if ("version_name".equals(propName)) {
                return VersionUtils.getVersionName(mContext);
            } else {
                return "unsupport";
            }
        }

        public void sendMessage(final String msg, final JSONObject extInfo) {
            viewHandler.post(new Runnable() {
                @Override
                public void run() {
                    Xlog.d(TAG, "sendMessage, msg=%s, extInfo=%s", msg, extInfo);
                }
            });
        }
        public void setHandler(String handlerName, JSCallback callback) {
            if ("KeyEvent".equals(handlerName)) {
                _keyCallback = callback;
                return;
            }
            if ("Message".equals(handlerName)) {
                _msgCallback = callback;
                return;
            }
        }

        public void sendIntent(String mode, JSONObject intent) {

        }
    }

    @Deprecated
    public class JSCallback {

        String methodName;

        public String run(Object... args) {
            if (Looper.getMainLooper().getThread().getId() != Thread.currentThread().getId()) {
                throw new IllegalThreadStateException("JSCallback must invoked in UI Thread!!!");
            }
            ArrayList<Pair<String, Object>> argsList = new ArrayList<Pair<String, Object>>();
            for (Object arg : args) {
                if (arg instanceof Integer
                        || arg instanceof Double
                        || arg instanceof Long
                        || arg instanceof Float
                        || arg instanceof Short
                        || arg instanceof Byte) {
                    argsList.add(Pair.create("number", arg));
                } else if (arg instanceof Boolean) {
                    argsList.add(Pair.create("boolean", arg));
                } else if (arg instanceof CharSequence
                        || arg instanceof Character) {
                    argsList.add(Pair.create("string", arg));
                } else if (arg instanceof JSONObject) {
                    argsList.add(Pair.create("object", arg));
                }
            }
            String argsJson = buildJson(argsList.toArray(new Pair[argsList.size()]));
            Xlog.d(TAG, "invokeCallback argsJson= " + argsJson);
            return invokeJSCallback(methodName, argsJson);
        }

        private String invokeJSCallback(final String methodName, final String args) {
            final int index = evalJsIndex.incrementAndGet();
            synchronized (jsReturnValues) {
                jsReturnValues.put(index, null);
            }
            String jsCallback = String.format("javascript:%s._invokeCallback(\"%s\", %s, %d)", jsExposeName, methodName, args, index);
            Xlog.d(TAG, "jsCallback, method name = " + methodName);
            Xlog.d(TAG, "jsCallback = " + jsCallback);
            loadUrl(jsCallback);
            return _waitForJsReturnValue(index, 1000);
        }

        private String buildJson(Pair<String, Object>... jsonArgs) {
            JSONStringer jsonStringer = new JSONStringer();
            try {
                jsonStringer.array();
                for (Pair<String, Object> arg : jsonArgs) {
                    jsonStringer.object()
                            .key("type").value(arg.first)
                            .key("value").value(arg.second)
                            .endObject();
                }
                jsonStringer.endArray();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonStringer.toString();
        }
    }
}
