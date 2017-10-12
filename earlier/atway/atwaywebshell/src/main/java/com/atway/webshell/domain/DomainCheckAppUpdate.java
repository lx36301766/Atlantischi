package com.atway.webshell.domain;

public class DomainCheckAppUpdate {

    public DoaminResult result;
    public UpdateInfo update_info;

    public static class UpdateInfo {
        public String update_state;
        public String update_type;
        public String download_url;

        @Override
        public String toString() {
            return "UpdateInfo{" +
                    "update_state='" + update_state + '\'' +
                    ", update_type='" + update_type + '\'' +
                    ", download_url='" + download_url + '\'' +
                    "} ";
        }
    }

    @Override
    public String toString() {
        return "DomainCheckAppUpdate{" +
                "result=" + result +
                ", update_info=" + update_info +
                '}';
    }
}
