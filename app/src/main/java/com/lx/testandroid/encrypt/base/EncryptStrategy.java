package com.lx.testandroid.encrypt.base;

public interface EncryptStrategy {

    /**
     *
     * @param url
     * @param encryptType
     * @param decryptType
     * @return
     */
    String encrypt(String url, int encryptType, int decryptType);

    /**
     *
     * @param url
     * @param data
     * @return
     */
    byte[] decrypt(String url, byte[] data);

}
