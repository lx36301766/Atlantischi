package com.lx.phonerecycle.volley.ext;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-7-26 上午11:42 <br>
 * description:
 */

public class MultipartRequest<T> extends GsonRequest<T> {

    private MultipartEntity entity = new MultipartEntity();

    private static final String FILE_PART_NAME = "nns_header";

    public MultipartRequest(String url, Class<T> clazz, Response.Listener<T> listener,
                            Response.ErrorListener errorListener, File file) {
        super(Request.Method.POST, url, clazz, listener, errorListener);
        entity.addPart(FILE_PART_NAME, new FileBody(file));
    }

    @Override
    public String getBodyContentType() {
        return entity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            entity.writeTo(bos);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }

}