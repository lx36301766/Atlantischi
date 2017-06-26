package com.lx.testandroid.encrypt.impl;

import com.lx.testandroid.encrypt.base.EncryptStrategy;
import com.lx.testandroid.encrypt.tools.EncryptHelper;

public class NoEncryptStrategy implements EncryptStrategy {

    @Override
    public String encrypt(String url, int encryptType, int decryptType) {
        String codec = EncryptHelper.buildCodec(encryptType, decryptType, EncryptHelper.randomKeyCodec());
        return url + "&nns_codec=" + codec;
    }

    @Override
    public byte[] decrypt(String url, byte[] data) {
        return data;
    }
}
