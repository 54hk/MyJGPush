package com.yjy.myjgpush;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MyActivity extends Activity {
    ImageView imageView;
    AlertDialog dialog;
    View dview;
    TextView myd_save, myd_cancel;

    private FileOutputStream out;
    private Context mContext;
    String img_url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508238237643&di=23843862172a00ab51aa43635bf7fbbc&imgtype=0&src=http%3A%2F%2Fwww.taopic.com%2Fuploads%2Fallimg%2F140507%2F240381-14050H2025093.jpg";
    private Bitmap mySave_Bit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        mContext = MyActivity.this;
        initDialog();
        imageView = findViewById(R.id.my_img);
        SimpleTarget target = new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                mySave_Bit = bitmap;
                Log.e("MyActivity", "bitmap:" + bitmap);
                imageView.setImageBitmap(bitmap);
            }
        };
        Glide.with(mContext).load(img_url).asBitmap().into(target);
        //asBitmap


        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (dialog != null && !dialog.isShowing()) {
                    dialog.show();
                    dialog.setContentView(dview);
                }

                return false;
            }
        });

        myd_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mySave_Bit != null) {
                    try {
                        saveFile(mySave_Bit);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        myd_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }

    public void saveFile(Bitmap bitmap) throws IOException {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) // 判断是否可以对SDcard进行操作
        {    // 获取SDCard指定目录下
            String sdCardDir = Environment.getExternalStorageDirectory() + "/jyydyl/myphoto";
            File dirFile = new File(sdCardDir);  //目录转化成文件夹
            if (!dirFile.exists()) {//如果不存在，那就建立这个文件夹
                dirFile.mkdirs();
            }

            File file = new File(sdCardDir, System.currentTimeMillis() + ".jpg");// 在SDcard的目录下创建图片文,以当前时间为其命名

            try {
                out = new FileOutputStream(file);
                Log.e("MyActivity", "zou");
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            savePhoto(file);
            Toast.makeText(mContext, "已经保存在本地，请在相册中查看", Toast.LENGTH_SHORT).show();
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } else {
            Toast.makeText(mContext, "保存出错", Toast.LENGTH_SHORT).show();
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }


    //
//    //通知相册更新图片
    private void savePhoto(File file) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        mContext.sendBroadcast(intent);//这个广播的目的就是更新图库，发了这个广播进入相册就可以找到你保存的图片了！
    }

    private void initDialog() {
        dview = LayoutInflater.from(mContext).inflate(R.layout.mydialog, null);
        dialog = new AlertDialog.Builder(mContext).create();
        myd_save = dview.findViewById(R.id.my_save);
        myd_cancel = dview.findViewById(R.id.my_cancel);
    }

}
