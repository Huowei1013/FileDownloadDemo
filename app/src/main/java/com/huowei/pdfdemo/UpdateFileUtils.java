package com.huowei.pdfdemo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import java.io.File;

/**
 * @Author: huowei
 * @CreateDate: 2021/4/25 18:22
 * @Describe:
 * @Version: 1.0.0
 */
public class UpdateFileUtils {
    /**
     * 打开文件
     * @param context
     * @param file
     */
    public static Intent openFile(Context context, File file){
        //Uri uri = Uri.parse("file://"+file.getAbsolutePath());
        Intent intent = new Intent();
        String type = getMIMEType(file);
        //判断Andorid版本 文件访问适配
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            Uri contentUri = FileProvider.getUriForFile(context, "com.huowei.pdfdemo.file.provider", file);
            Uri contentUri = FileProvider.getUriForFile(context, "com.huowei.pdfdemo.file.provider", file);
            intent.setDataAndType(contentUri, type);
        } else {
            intent.setDataAndType(Uri.fromFile(file), type);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //设置intent的Action属性
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory("android.intent.category.DEFAULT");
        //获取文件file的MIME类型
//        String type = getMIMEType(file);
        //设置intent的data和Type属性。
//        intent.setDataAndType(/*uri*/Uri.fromFile(file), type);
        //跳转
//        startActivity(intent);
        return intent;
    }
    /**
     * 递归创建文件夹
     *
     * @param dirPath
     * @return 创建失败返回""
     */
    public static String createDir(String dirPath) {
        try {
            File file = new File(dirPath);
            if (file.getParentFile().exists()) {
                file.mkdir();
                return file.getAbsolutePath();
            } else {
                createDir(file.getParentFile().getAbsolutePath());
                file.mkdir();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dirPath;
    }

    /**
     * 根据文件后缀名获得对应的MIME类型。
     * @param file
     */
    public static String getMIMEType(File file)
    {
        String type="*/*";
        String fName=file.getName();
        //获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if(dotIndex < 0){
            return type;
        }
        /* 获取文件的后缀名 */
        String end=fName.substring(dotIndex,fName.length()).toLowerCase();
        if(end.equals(""))return type;
        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        for(int i=0;i<MIME_MapTable.length;i++){
            if(end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }

    private static final String[][] MIME_MapTable={
            //{后缀名，    MIME类型}
//            {".3gp",    "video/3gpp"},
//            {".apk",    "application/vnd.android.package-archive"},
//            {".asf",    "video/x-ms-asf"},
//            {".avi",    "video/x-msvideo"},
//            {".bin",    "application/octet-stream"},
//            {".bmp",      "image/bmp"},
//            {".c",        "text/plain"},
//            {".class",    "application/octet-stream"},
//            {".conf",    "text/plain"},
//            {".cpp",    "text/plain"},
            {".doc",    "application/msword"},
            {".docx",   "application/msword"},
//            {".exe",    "application/octet-stream"},
//            {".gif",    "image/gif"},
//            {".gtar",    "application/x-gtar"},
//            {".gz",        "application/x-gzip"},
//            {".h",        "text/plain"},
//            {".htm",    "text/html"},
//            {".html",    "text/html"},
//            {".jar",    "application/java-archive"},
//            {".java",    "text/plain"},
            {".jpeg",    "image/jpeg"},
            {".jpg",    "image/jpeg"},
//            {".js",        "application/x-javascript"},
//            {".log",    "text/plain"},
//            {".m3u",    "audio/x-mpegurl"},
//            {".m4a",    "audio/mp4a-latm"},
//            {".m4b",    "audio/mp4a-latm"},
//            {".m4p",    "audio/mp4a-latm"},
//            {".m4u",    "video/vnd.mpegurl"},
//            {".m4v",    "video/x-m4v"},
//            {".mov",    "video/quicktime"},
//            {".mp2",    "audio/x-mpeg"},
//            {".mp3",    "audio/x-mpeg"},
//            {".mp4",    "video/mp4"},
//            {".mpc",    "application/vnd.mpohun.certificate"},
//            {".mpe",    "video/mpeg"},
//            {".mpeg",    "video/mpeg"},
//            {".mpg",    "video/mpeg"},
//            {".mpg4",    "video/mp4"},
//            {".mpga",    "audio/mpeg"},
//            {".msg",    "application/vnd.ms-outlook"},
//            {".ogg",    "audio/ogg"},
            {".pdf",    "application/pdf"},
            {".png",    "image/png"},
//            {".pps",    "application/vnd.ms-powerpoint"},
            {".ppt",    "application/vnd.ms-powerpoint"},
            {".xls",    "application/vnd.ms-excel"},
            {".xlsx",    "application/vnd.ms-excel"},
//            {".prop",    "text/plain"},
//            {".rar",    "application/x-rar-compressed"},
//            {".rc",        "text/plain"},
//            {".rmvb",    "audio/x-pn-realaudio"},
//            {".rtf",    "application/rtf"},
//            {".sh",        "text/plain"},
//            {".tar",    "application/x-tar"},
//            {".tgz",    "application/x-compressed"},
//            {".txt",    "text/plain"},
//            {".wav",    "audio/x-wav"},
//            {".wma",    "audio/x-ms-wma"},
//            {".wmv",    "audio/x-ms-wmv"},
            {".wps",    "application/vnd.ms-works"},
            //{".xml",    "text/xml"},
//            {".xml",    "text/plain"},
//            {".z",        "application/x-compress"},
//            {".zip",    "application/zip"},
//            {"",        "*/*"}
    };
}
