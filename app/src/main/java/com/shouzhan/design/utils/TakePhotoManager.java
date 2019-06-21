package com.shouzhan.design.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.multidex.BuildConfig;
import android.support.v4.content.FileProvider;
import com.fshows.android.stark.utils.FileUtil;
import com.fshows.android.stark.utils.PermissionHelper;
import com.google.common.base.Strings;
import com.shouzhan.design.callback.OnTakePhotoListener;
import com.tbruyelle.rxpermissions.RxPermissions;
import rx.functions.Action1;

import java.io.File;


/**
 * 拍照管理的类
 *
 * @author cjy on 2017/4/03.
 */
public class TakePhotoManager {

    private static final String TAG = TakePhotoManager.class.getSimpleName();

    private static final String CAMERA_PHOTO_PATH = "temp_camera_image.jpg";
    private static final String CLIP_PHOTO_PATH = "temp_clip_image.jpg";

    private static final String PRIMARY = "primary";
    private static final String IMAGE = "image";
    private static final String VIDEO = "video";
    private static final String AUDIO = "audio";
    private static final String CONTENT = "content";
    private static final String FILE = "file";

    /**
     * 上下文
     */
    private Activity activity;

    /**
     * 图片的存储地址-父路径
     */
    private String mRootPath;

    /**
     * 原图保存地址
     */
    private Uri imageUri = null;
    /**
     * 设置裁剪参数
     */
    public TakePhotoValue takePhotoValue = null;

    /**
     * 回调图片监听
     */
    private OnTakePhotoListener listener;

    /**
     * 权限
     */
    private RxPermissions mRxPermissions;

    public TakePhotoManager(Activity activity, OnTakePhotoListener listener) {
        this.activity = activity;
        this.mRootPath = FileUtil.getRootPath(activity) + File.separator + "photo";
        this.listener = listener;
        this.mRxPermissions = new RxPermissions(activity);
    }

    /**
     * 设置是否裁剪
     *
     * @param width
     * @param height
     */
    public void setTakePhotoValue(int width, int height, int cutting) {
        this.takePhotoValue = new TakePhotoValue(width, height, cutting);
    }

    /**
     * 请求相机拍照
     */
    public void requestTakePhoto() {
        mRxPermissions.request(Manifest.permission.CAMERA)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            if (PermissionHelper.isCameraEnable()) {
                                takePhoto();
                            }
                        }
                    }
                });
    }

    /**
     * 打开相机拍照
     */
    public void takePhoto() {
        if (Strings.isNullOrEmpty(mRootPath)) {
            return;
        }
        File outDir = new File(mRootPath);
        if (!outDir.exists()) {
            outDir.mkdirs();
        }
        File outFile = new File(mRootPath, CAMERA_PHOTO_PATH);
        if (outFile.exists()) {
            outFile.delete();
        }
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intentFromCapture.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            imageUri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".fileprovider", outFile);
        } else {
            imageUri = Uri.fromFile(outFile);
        }
        intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        activity.startActivityForResult(intentFromCapture, Constants.TAKE_PHOTO_REQUEST_CODE);
    }

    /**
     * 请求获取本地图片
     */
    public void requestPickPhoto() {
        mRxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            pickPhoto();
                        }
                    }
                });
    }

    /**
     * 获取本地图片
     */
    public void pickPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(intent, Constants.PICK_PHOTO_REQUEST_CODE);
    }

    /**
     * 裁剪本地图片、拍照
     *
     * @param context
     * @param uri
     */
    private void requestClipPhoto(Context context, Uri uri) {
        if (uri == null || context == null) {
            return;
        }
        File outDir = new File(mRootPath);
        if (!outDir.exists()) {
            outDir.mkdirs();
        }
        File outFile = new File(mRootPath, CLIP_PHOTO_PATH);
        if (outFile.exists()) {
            outFile.delete();
        }
        String imagePath = getImagePath(activity, uri);
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".fileprovider", new File(imagePath));
            intent.setDataAndType(contentUri, "image/*");
        } else {
            intent.setDataAndType(uri, "image/*");
        }
        intent.putExtra(Constants.EXTRA_FILE_PATH, imagePath);
        setCropBorderSize(intent);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outFile));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("return-data", false);
        activity.startActivityForResult(intent, Constants.CLIP_PHOTO_REQUEST_CODE);
    }

    /**
     * 设置裁剪区域大小
     */
    private void setCropBorderSize(Intent intent) {
        int clipWidth = takePhotoValue.width;
        int clipHeight = takePhotoValue.height;
        if (clipWidth == 0 || clipHeight == 0) {
            return;
        }
        intent.putExtra("aspectX", clipWidth);
        intent.putExtra("aspectY", clipHeight);
        intent.putExtra("outputX", clipWidth);
        intent.putExtra("outputY", clipHeight);
    }

    /**
     * 返回处理
     */
    public void onActivityResult(Context context, int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }
        switch (requestCode) {
            case Constants.PICK_PHOTO_REQUEST_CODE:
                Uri imageUri = data.getData();
                if (takePhotoValue != null && takePhotoValue.cutting == 1) {
                    requestClipPhoto(context, imageUri);
                } else {
                    getPhotoPath(context, imageUri);
                }
                break;
            case Constants.TAKE_PHOTO_REQUEST_CODE:
                File tempFile = new File(mRootPath, CAMERA_PHOTO_PATH);
                if (takePhotoValue != null && takePhotoValue.cutting == 1) {
                    requestClipPhoto(context, Uri.fromFile(tempFile));
                } else {
                    getPhotoPath(context, tempFile.getAbsolutePath());
                }
                break;
            case Constants.CLIP_PHOTO_REQUEST_CODE:
                takePhotoValue = null;
                File clipFile = new File(mRootPath, CLIP_PHOTO_PATH);
                if (clipFile == null || !clipFile.exists()) {
                    return;
                }
                getPhotoPath(context, clipFile.getAbsolutePath());
                break;
            default:
                break;
        }
    }

    /**
     * 获取图片地址
     *
     * @param context
     * @param imageUri
     */
    public void getPhotoPath(Context context, Uri imageUri) {
        if (imageUri == null) {
            return;
        }
        String imagePath = getImagePath(context, imageUri);
        if (Strings.isNullOrEmpty(imagePath)) {
            return;
        }
        getPhotoPath(context, imagePath);
    }

    /**
     * 获取图片地址
     *
     * @param context
     * @param filePath
     */
    public void getPhotoPath(Context context, String filePath) {
        if (listener != null && context != null) {
            listener.onTakePath(filePath);
        }
    }

    /**
     * 获取图片地址
     *
     * @param context
     * @param imageUri
     */
    @TargetApi(19)
    public static String getImagePath(Context context, Uri imageUri) {
        if (context == null || imageUri == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, imageUri)) {
            if (isExternalStorageDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                if (PRIMARY.equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(imageUri)) {
                String id = DocumentsContract.getDocumentId(imageUri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if (IMAGE.equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if (VIDEO.equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if (AUDIO.equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if (CONTENT.equalsIgnoreCase(imageUri.getScheme())) {
            if (isGooglePhotosUri(imageUri)) {
                return imageUri.getLastPathSegment();
            }
            return getDataColumn(context, imageUri, null, null);
        } else if (FILE.equalsIgnoreCase(imageUri.getScheme())) {
            return imageUri.getPath();
        }
        return null;
    }

    /**
     * 获取文件路径
     *
     * @param context
     * @param uri
     * @param selection
     * @param selectionArgs
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    /**
     * 裁剪图片参数类
     */
    public static class TakePhotoValue {
        /**
         * 裁剪图片宽度
         */
        public int width;
        /**
         * 裁剪图片高度
         */
        public int height;
        /**
         * 是否裁剪, 0不裁剪, 1裁剪
         */
        public int cutting;

        public TakePhotoValue(int width, int height, int cutting) {
            this.width = width;
            this.height = height;
            this.cutting = cutting;
        }


    }

}