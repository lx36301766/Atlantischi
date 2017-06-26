package com.lx.testandroid.encrypt.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GetSecretKeysInfo implements Serializable {

    private static final long serialVersionUID = 118526816881161377L;

    public List<SecretKeys> secretKeys = new ArrayList<>();
    public List<ApiEncrypt> apiEncrypts = new ArrayList<>();

    public String validKeyGroup = "";

    public static class SecretKeys {
        public String sign;
        public String requestEncryptKey;
        public String responseEncryptKey;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            SecretKeys that = (SecretKeys) o;
            return sign != null ? sign.equals(that.sign) : that.sign == null;
        }

        @Override
        public int hashCode() {
            return sign != null ? sign.hashCode() : 0;
        }
    }

    public static class ApiEncrypt {
        public String sign;
        public String requestEncryptMode;
        public String responseEncryptMode;
    }

}
