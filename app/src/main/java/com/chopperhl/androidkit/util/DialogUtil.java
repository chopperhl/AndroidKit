package com.chopperhl.androidkit.util;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chopperhl.androidkit.R;
import com.chopperhl.androidkit.base.BaseActivity;
import com.chopperhl.androidkit.common.Constants;
import com.chopperhl.androidkit.widget.BottomSheetDialog;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.ImageEngine;
import io.reactivex.functions.Consumer;

import java.io.File;
import java.util.List;


/**
 * Description:
 * Author chopperhl
 * Date 6/30/18
 *
 * Copyright ©2015-20018 chopperhl All Rights Reserved.
 */
public class DialogUtil {

    public interface DateCallback {
        void onCheck(String date);
    }

    /**
     * 显示默认的时间选择器
     *
     * @param context  上下文，必须是act
     * @param time     初始化时选中的时间
     * @param callback 选中结果回调
     */
    public static void showDatePicker(Context context, final String time, final DateCallback callback) {
        String[] date = time.split("-");
        int year = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]) - 1;
        int day = Integer.parseInt(date[2]);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, (datePicker, i, i1, i2) -> {
            String days;
            if (i1 + 1 < 10) {
                if (i2 < 10) {
                    days = i + "-" + "0" + (i1 + 1) + "-" + "0" + i2;
                } else {
                    days = i + "-" + "0" + (i1 + 1) + "-" + i2;
                }
            } else {
                if (i2 < 10) {
                    days = i + "-" + (i1 + 1) + "-" + "0" + i2;
                } else {
                    days = i + "-" + (i1 + 1) + "-" + i2;
                }
            }
            callback.onCheck(days);
        }, year, month, day) {
            @Override
            protected void onStop() {

            }
        };
        datePickerDialog.show();
    }

    /**
     * 显示图片选择器
     *
     * @param activity
     * @param callback 选择结果回调
     */
    public static void showImageSelector(BaseActivity activity, Consumer<String> callback) {
        List<String> list = Lists.newArrayList(activity.getString(R.string.take_photo)
                , activity.getString(R.string.choose_pic)
                , activity.getString(R.string.cancel)
        );
        BottomSheetDialog dialog = new BottomSheetDialog.Builder(activity)
                .setSheetData(list)
                .setOnItemClickListener((index, obj) ->
                        activity.requestPermissions(new String[]{
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        }, () -> {
                            if (index == 0) {
                                takePhoto(activity, callback);
                            } else if (index == 1) {
                                choosePhoto(activity, callback);
                            }
                        }))
                .create();
        dialog.show();
    }

    /**
     * 选择相机
     */
    private static void takePhoto(BaseActivity activity, Consumer<String> pathCallback) {
        // 跳转到系统照相机
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(activity.getPackageManager()) != null) {
            // 设置系统相机拍照后的输出路径
            File cacheDir = new File(activity.getCacheDir().getAbsolutePath() + "/picture/");
            if (!cacheDir.exists()) {
                if (!cacheDir.mkdir()) {
                    Util.showToast(R.string.path_make_fail);
                    return;
                }
            }
            File tempFile = new File(cacheDir, TimeUtil.getCurrentTime(TimeUtil.TIME_STAMP) + ".jpg");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri uri = FileProvider.getUriForFile(activity, activity.getApplicationInfo().packageName + ".FileProvider", tempFile);
                cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            } else {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
            }
            activity.registerLifecycleListener((requestCode, resultCode, data) -> {
                if (requestCode == Constants.REQUEST_CAMERA) {
                    activity.registerLifecycleListener(null);
                    if (resultCode == Activity.RESULT_OK && tempFile.exists()) {
                        try {
                            pathCallback.accept(tempFile.getAbsolutePath());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            activity.startActivityForResult(cameraIntent, Constants.REQUEST_CAMERA);
        }
    }


    /**
     * 调用知乎的图片选择工具
     *
     * @param activity
     * @param pathCallback
     */
    private static void choosePhoto(BaseActivity activity, Consumer<String> pathCallback) {
        Matisse.from(activity)
                .choose(MimeType.allOf()) // 选择 mime 的类型
                .countable(true)
                .maxSelectable(1) // 图片选择的最多数量
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .theme(R.style.Matisse_Dracula)
                .thumbnailScale(0.85f) // 缩略图的比例
                .imageEngine(new ImageEngine() {
                    @Override
                    public void loadThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {
                        Glide.with(context)
                                .load(uri)
                                .apply(RequestOptions.centerCropTransform())
                                .apply(RequestOptions.overrideOf(resize))
                                .apply(RequestOptions.placeholderOf(placeholder))
                                .into(imageView);
                    }

                    @Override
                    public void loadAnimatedGifThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {
                        Glide.with(context)
                                .load(uri)
                                .apply(RequestOptions.centerCropTransform())
                                .apply(RequestOptions.overrideOf(resize))
                                .apply(RequestOptions.placeholderOf(placeholder))
                                .into(imageView);
                    }

                    @Override
                    public void loadImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
                        Glide.with(context)
                                .load(uri)
                                .apply(RequestOptions.centerCropTransform())
                                .apply(RequestOptions.overrideOf(resizeX, resizeY))
                                .into(imageView);
                    }

                    @Override
                    public void loadAnimatedGifImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
                        Glide.with(context)
                                .load(uri)
                                .apply(RequestOptions.centerCropTransform())
                                .apply(RequestOptions.overrideOf(resizeX, resizeY))
                                .into(imageView);
                    }

                    @Override
                    public boolean supportAnimatedGif() {
                        return false;
                    }
                }) // 使用的图片加载引擎
                .forResult(Constants.REQUEST_GALLERY); // 设置作为标记的请求码
        activity.registerLifecycleListener((requestCode, resultCode, data) -> {
            if (requestCode == Constants.REQUEST_GALLERY) {
                activity.registerLifecycleListener(null);
                if (resultCode != Activity.RESULT_OK) return;
                List<Uri> uri = Matisse.obtainResult(data);
                if (Lists.isEmpty(uri)) return;
                Cursor cursor = activity.getContentResolver().query(uri.get(0), new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                if (cursor == null) return;
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    String path = cursor.getString(columnIndex);
                    cursor.close();
                    File file = new File(path);
                    if (TextUtils.isEmpty(path) || !file.exists()) return;
                    try {
                        pathCallback.accept(path);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    cursor.close();
                }

            }
        });
    }


}
