package pl.atlantischi.squareup.okhttp;

/**
 * Created on 21/04/2017.
 *
 * @author lx
 */

public interface ReqCallBack<T> {
    /**
     * 响应成功
     */
    void onReqSuccess(T result);

    /**
     * 响应失败
     */
    void onReqFailed(String errorMsg);
}
