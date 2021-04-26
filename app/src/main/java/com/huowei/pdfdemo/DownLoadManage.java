package com.huowei.pdfdemo;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.webkit.MimeTypeMap;

import java.io.File;

/**
 * @Author: huowei
 * @CreateDate: 2021/3/22 18:48
 * @Describe: 下载相关
 * @Version: 1.0.0
 */
public class DownLoadManage {

    private static DownloadManager mDownloadManage;

    public static DownloadManager getInstance(Context context) {
        if (mDownloadManage == null) {
            synchronized (DownLoadManage.class) {
                if (mDownloadManage == null) {
                    mDownloadManage = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                }
            }
        }
        return mDownloadManage;
    }

    public static long downFile(String url, String name, BroadcastReceiver receiver, Context context) {
        String rootUrl =  url;

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(rootUrl));
        request.setAllowedOverRoaming(true);
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        String mimiString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(name));
        request.setMimeType(mimiString);
        String folder = File.separator + "ceshiDownload" + File.separator;
        // 解决Android28 DownloadManager对保存文件的路径进行切换
        try { //V28 Below
            request.setDestinationInExternalPublicDir(folder, name);//v 28 allow to create and it deprecated method(v28+)
        } catch (Exception e) {
            //For Android  28+
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name);//(Environment.DIRECTORY_PICTURES,"picname.jpeg")
        }
//        request.setDestinationInExternalPublicDir(File.separator + "ceshiDownload" + File.separator, name);

        long mTaskId = getInstance(context).enqueue(request);
        context.registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        return mTaskId;
    }

}
