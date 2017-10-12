package com.atway.webshell.domain;

import java.util.List;

public class DomainGetAppImage {

    public DoaminResult result;
    public ImageInfo image_info;

    public static class ImageInfo {
        public String display_time;
        public List<String> urls;

        @Override
        public String toString() {
            return "ImageInfo{" +
                    "display_time='" + display_time + '\'' +
                    ", urls=" + urls +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DomainGetLaunchImage{" +
                "result=" + result +
                ", image_info=" + image_info +
                "} ";
    }

}
