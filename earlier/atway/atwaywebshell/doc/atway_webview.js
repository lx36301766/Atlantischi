/**
 * webview接口
 */

(function(Atway){

Atway._invokeCallback = function(name, args, returnIdx) {
    var func = this._callbacks[name];
    var ret = null;
    try {
        var invokeArgs = [];
        for( var idx in args ) {
            var item = args[idx];
            switch(item.type) {
                case "number":
                    invokeArgs.push(Number(item.value));
                    break;
                case "string":
                    invokeArgs.push(String(item.value));
                    break;
                case "boolean":
                    invokeArgs.push("true".toLowerCase() == item.value);
                    break;
            }
        }
        ret = func.apply(this, invokeArgs);
    } catch (e) {}
    Atway._setCallbackResult(returnIdx, ret);
};
Atway._addCallback = function(callback) {
    var callbackIdx = String(Atway._callback_counter++);
    this._callbacks[callbackIdx] = callback;
    return callbackIdx;
};
Atway._callback_counter = 0;
Atway._callbacks = {};

Atway.exec = function() {
    var args = [];
    for(var idx=0;idx<arguments.length;++idx) {
        var val = arguments[idx];
        switch (typeof val) {
            case "number":
                args.push({"type":"number", "value":val});
                break;
            case "string":
                args.push({"type":"string", "value":val});
                break;
            case "bool":
                args.push({"type":"boolean", "value":val});
                break;
            case "object":
                args.push({"type":"object", "value":val});
                break;
            case "function":
                args.push({"type": "callback", "value": this._addCallback(val)});
                break;
        }
    }
    var cmd = args.shift();
    if(typeof this._execAndroidFunc === "undefined"){
        return null;
    }
    return this._execAndroidFunc(cmd.value, JSON.stringify(args));
};

//调用支付接口
Atway.bellmannPay = function(appParams, thridParams) {
    return this.exec("bellmannPay", appParams, thridParams);
};

//加密用户信息
Atway.encodeInfo = function(userInfo, key) {
    return String(this.exec("encodeInfo", userInfo, key));
};

//解密用户信息
Atway.decodeInfo = function(userInfo, key) {
    return String(this.exec("decodeInfo", userInfo, key));
};

//获取加密key
Atway.getKey = function() {
    return String(this.exec("getKey"));
};

//在网页中打开播放器
Atway.openPlayerInWeb = function(player_info) {
    return this.exec("openPlayerInWeb", player_info);
};

//关闭并隐藏网页播放器
Atway.closePlayerInWeb = function() {
    return this.exec("closePlayerInWeb");
};

//打开全屏播放器
Atway.startPlayer = function(player_info) {
    return this.exec("startPlayer", player_info);
};

// 返回用户信息
Atway.getUser = function() {
    return String(this.exec("getUser"));
};

// 关闭浏览器
Atway.closeBrowser = function() {
    return this.exec("closeBrowser");
};

// 输出日志信息, info可为一个或多个, 多个输入参数以空格分隔
Atway.log = function(tag, info) {
    var logMsg = [];
    for(var idx=1;idx<arguments.length;++idx) {
        var item = arguments[idx];
        if (item instanceof Array || typeof(item) == "object") {
            logMsg.push(JSON.stringfy(item));
        } else {
            logMsg.push(item);
        }
    }
    this.exec("log", tag, logMsg.join(" "));
};

Atway.getScreenSize = function() {
    var posInfo = this.exec("getScreenSize");
    posInfo = posInfo.split(",");
    return {
        width:posInfo[0],
        height:posInfo[1]
    };
};

// 设置事件回调
// handlerType 回调类型, KeyEvent, Message
// handler 回调函数, 其形式由handlerType决定
//-----------------------------------------------------------------------------
// handler为空表示取消对事件的监听
// handler如果处理了事件, 返回true, 否则事件交由浏览器处理
Atway.setHandler = function(handlerType, handler) {
    return this.exec("setHandler", handlerType, handler);
};
// 获取焦点
// webview获取焦点后，所有按键事件都将转发给页面处理
Atway.requestFocus = function() {
    return this.exec("requestFocus");
};
// 释放焦点
// direction: 将焦点释放给当前webview在某个方向上的相邻元素，如无相邻元素，则焦点不变化
Atway.releaseFocus = function(direction) {
    return this.exec("releaseFocus", direction);
};
// 是否有焦点
Atway.hasFocus = function() {
    return this.exec("hasFocus");
};
// 读取系统属性
Atway.readSystemProp = function(propName) {
    return String(this.exec("readSystemProp", propName));
};
// 设置按键事件回调, handler形如function(action, keyCode, keyName, metaState)
// action可以为keyUp, keyDown
// keyName为按键名称, 如: up, down, left, right, a, b, c, d, 1, 2, 3, 4, enter, return, UNKNOWN
// keyCode为按键对应的键值
// metaState为控制建对应状态，ctrl, alt, shift等
Atway.setKeyEventHandler = function(handler) {
    return this.setHandler("KeyEvent", handler);
};
// 设置消息回调，handler形如function(msg, extInfo)
// msg:
//      onFocusChanged, true/false  -   webview焦点变化，true表示获取焦点，false表示失去焦点
Atway.setMessageHandler = function(handler) {
    return this.setHandler("Message", handler);
};
// 发送消息给应用
// msg:
//      onLogin,     extInfo: {name:"user name", id:"user id", token:"login token"}
//      onPurchases, extInfo: {id:"item id", result:"success"/"failed", msg:"err message"}
Atway.sendMessage = function(msg, extInfo) {
    return this.exec("sendMessage", msg, extInfo);
};
// 发送Android Intent
// mode:
//      sendBroadcast   发送广播
//      startActivity   打开应用
//      startService    启动服务
Atway.sendIntent = function(mode, intent) {
    return this.exec("sendIntent", intent);
};
// 打开子浏览器（弹出方式）
// 同时只能打开一个子浏览器
Atway.openBrowser = function(url) {
    return this.exec("openBrowser", url);
};


window.Webview = Atway;

})(typeof Atway === "undefined" ? {}:Atway); //Atway是webview提供的对象
