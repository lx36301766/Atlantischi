package com.atway.webshell.domain;

public class DoaminGetMainUrls {

    public DoaminResult result;
    public String main_page_url;
    public Urls urls;

    public static class Urls {
        public String a1;
        public String a2;
        public String a3;

        @Override
        public String toString() {
            return "Urls{" +
                    "a1='" + a1 + '\'' +
                    ", a2='" + a2 + '\'' +
                    ", a3='" + a3 + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DoaminGetMainUrls{" +
                "result=" + result +
                ", main_page_url=" + main_page_url +
                ", urls=" + urls +
                '}';
    }
}
