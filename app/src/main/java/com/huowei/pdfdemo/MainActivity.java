package com.huowei.pdfdemo;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yanzhenjie.permission.AndPermission;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private boolean isDownload;
    private long queryDownloadId;
    private String fileName2;
    private String fileName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndPermission.with(this)
                .requestCode(100)
                .permission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .callback(this)
                .start();
        textView = findViewById(R.id.myTextView);
        final String str = "https://apaas-datashop.wodcloud.com/apaas/static/docs/image/file/817014cc-752c-4f99-b494-bd3ed5ff9528/工作区域申请文件模板.xlsx";
        String[] split = str.split("/");
        fileName = split[split.length-1];
        textView.setText(fileName);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sdcard = Environment.getExternalStorageDirectory().getPath();
                String filePath = UpdateFileUtils.createDir(sdcard + File.separator + "ceshiDownload") + File.separator;
                File file = new File(filePath + fileName);
                if (file.exists()) {
                    Intent intent = null;
                    try {
                        intent = UpdateFileUtils.openFile(MainActivity.this,file);
                        if (intent != null) {
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "无法打开附件，请安装WPS相应软件",Toast.LENGTH_SHORT);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "正在下载，可在通知栏查看进度",Toast.LENGTH_LONG);
                    isDownload = true;
                    fileName2 = filePath + fileName;
                    queryDownloadId = DownLoadManage.downFile(str, fileName, broadcastReceiver, MainActivity.this);
                }
            }
        });
    }
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(queryDownloadId);
            Cursor c = DownLoadManage.getInstance(context).query(query);
            if (c.moveToFirst()) {
                int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                switch (status) {
                    case DownloadManager.STATUS_PAUSED:
                        break;
                    case DownloadManager.STATUS_PENDING:
                        break;
                    case DownloadManager.STATUS_RUNNING:
                        break;
                    case DownloadManager.STATUS_SUCCESSFUL:
                        Toast.makeText(context, "附件下载完成",Toast.LENGTH_SHORT);
                        if (!fileName2.equals("") && isDownload) {
                            isDownload = false;
                            Intent intent1 = UpdateFileUtils.openFile(MainActivity.this, new File(fileName2));
                            if (intent1 != null) {
                                startActivity(intent1);
                            } else {
                                Toast.makeText(context, "无法打开附件，请安装WPS相应软件",Toast.LENGTH_SHORT);
                            }
                        }
                        break;
                    case DownloadManager.STATUS_FAILED:
                        Toast.makeText(context, "附件下载失败，请重新下载",Toast.LENGTH_SHORT);

                        break;
                }
            }
        }
    };
}
