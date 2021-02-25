package com.woc.common.common.utils;

import java.util.Arrays;

public class FileUilts {
    private static String[] imgType = {"png","jpg","jpng","bmp"};

    private static String[] audioType = {"mp3","wma","avi",".rm","rmvb","flv","mpg","mov","mkv"};

    private static String[] videoType ={"avi","mov","rmvb","rm","flv","mp4","3gp","ogg"};

    public static String fileType(String fileExt){
        if(Arrays.asList(imgType).contains(fileExt.toLowerCase())){
            return "image";
        }
        if(Arrays.asList(audioType).contains(fileExt.toLowerCase())){
            return "audio";
        }
        if(Arrays.asList(videoType).contains(fileExt.toLowerCase())){
            return "video";
        }
        return "";
    }
}
