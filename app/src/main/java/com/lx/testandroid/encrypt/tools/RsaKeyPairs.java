package com.lx.testandroid.encrypt.tools;

import java.util.HashMap;
import java.util.Map;

import android.util.Pair;

public class RsaKeyPairs {

    private static final String[][] DEFAULT_KEYS = {
            //0
            {
                    "MIICagIBAAKBhADQhJgt4Wdt93zlF+3qQlxJ1kQMDTNH76KFh+Fz3Nui5D+5iCv+\n" +
                            "yeW2uGMlK/JB429GAd47H16bc8oUC0ZoH67dac6lcS6EZ2Lkn6gOHRzwtAjKZtKu\n" +
                            "TF/zWZ4y6/+Vnu8zxmuG/WHWyQmaY6O31yO7zH4uAsY1+bMpp1lfMOIo0vyR3wID\n" +
                            "AQABAoGEAJxFgzd/YmCLxlJ7aJbUPQ4pQr41dmVHNuoUN+b5crHVldyaLT66WSpj\n" +
                            "bgqBsQGoTdCNvdtZWzHtyVjUSmPmusq9V7dW1gO8Ce3T96nf29OG/1OFJa6C/f+i\n" +
                            "10Qb/A212Lk1O60rQqBozirt/DHX4VDabOLLoUz2Lwu6bKwIabQBY6shAkIOxDfe\n" +
                            "hJchfDsGrGC8qhC1WZw7XhHlZrFkaJ/zoZ29BIFyK+PNHa/YNh3hHryXm7BkV11Y\n" +
                            "+MFTYXE86WeDmSFbem0CQg4e/CgPkHiQPuhR6gvnoxTsYSHwAjSmtqigeonJVqmh\n" +
                            "l80KQSxrULMHmYN4KZq8icbAGy3bNq90mc6elOe+MOQN+wJCCMJHSYxogQpJkOnK\n" +
                            "794Izyl6TJS+FM7XRnHw/vjMsgPsLKbwFwkW9y8ShihPQbat6LcRvea/9g+EtdbU\n" +
                            "+fBpIvP5AkIKfwmblYYjt8Sq10iLNNDJ2rKQkWByVoFrcm3jQV9Dk7PMWxT6uYIw\n" +
                            "sTNwv77dH8uhimYpfcp2PMufA81XPM4GlkUCQgtdlBjQHdbSFbu47U/4KwYnuU6l\n" +
                            "cm9F8lHROr5iggBrLf8/9vfTQkrMwdNvgDhyfGQK2rimomc18foiqZl5z+SoBw=="
                    ,
                    "MIGiMA0GCSqGSIb3DQEBAQUAA4GQADCBjAKBhADRb102jT0Yos/YiUdG+9EJ5Chv\n" +
                            "Z0Va4E2Yt+ojHJurRb0TXXNsaNYjHrMKQPciGajEsW8yrOwxAb5keVuqAkATIkRS\n" +
                            "NVRcYb5SVLUNseNB+TUFOhQfTU5MVh5UQPsKlwYAlaMmpBY3rgFXe19vFnC5P6DH\n" +
                            "bKN+mX8nqk+XX5vVdPtuJQIDAQAB"
            },
    };

    public final static Map<Integer, Pair<String, String>> mKeyMap = new HashMap<>();

    static {
        for (int i = 0; i < DEFAULT_KEYS.length; i++) {
            mKeyMap.put(i, Pair.create(DEFAULT_KEYS[i][0], DEFAULT_KEYS[i][1]));
        }
    }

}
