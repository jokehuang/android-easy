package com.easy.util;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

/**
 * @author Joke Huang
 * @version 1.0.0
 * @Description
 * @createDate 2014年12月8日
 */

public class IntentUtil {

	private IntentUtil() {
	}

	/**
	 * 启动自定义的页面
	 *
	 * @param context
	 * @param clazz
	 */
	public static void startActivity(Context context, Class<?> clazz) {
		context.startActivity(new Intent(context, clazz));
	}

	/**
	 * 打开电话面板
	 *
	 * @param context
	 * @param tel
	 */
	public static void openTelephone(Context context, String tel) {
		context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel)));
	}

	/**
	 * 安装apk
	 *
	 * @param context
	 * @param filePath
	 * @return
	 */
	public static boolean install(Context context, String filePath) {
		Intent i = new Intent(Intent.ACTION_VIEW);
		File file = new File(filePath);
		if (file == null || !file.exists() || !file.isFile() || file.length() <= 0) {
			return false;
		}

		i.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android" +
				".package-archive");
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
		return true;
	}

	/**
	 * 卸载app
	 *
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static boolean uninstall(Context context, String packageName) {
		if (packageName == null || packageName.length() == 0) {
			return false;
		}

		Intent i = new Intent(Intent.ACTION_DELETE, Uri.parse(new StringBuilder(32).append
				("package:").append(packageName).toString()));
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
		return true;
	}

	/**
	 * 跳到本应用的市场详细页
	 * @param context
	 */
	public static void gotoMarket(Context context) {
		Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	/**
	 * 拍照后储存到媒体库
	 *
	 * @param activity
	 * @param requestCode
	 */
	public static void takePhoto(Activity activity, int requestCode) {
		activity.startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), requestCode);
	}

	/**
	 * 拍照后储存到指定路径
	 *
	 * @param activity
	 * @param requestCode
	 * @param path
	 */
	public static void takePhoto(Activity activity, int requestCode, String path) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path)));
		activity.startActivityForResult(intent, requestCode);
	}

	/**
	 * 打开相册
	 *
	 * @param activity
	 * @param requestCode
	 */
	public static void openAlbum(Activity activity, int requestCode) {
		activity.startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media
				.EXTERNAL_CONTENT_URI), requestCode);
	}

	/**
	 * 判断相册里的图片是否为本地图片（则不是同步在云端的图片）
	 *
	 * @param uri
	 * @return
	 */
	public static boolean isLocalUri(Uri uri) {
		return uri.toString().startsWith(MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());
	}

	/**
	 * 根据uri获取对应的路径
	 *
	 * @param context
	 * @param uri
	 * @return
	 */
	@SuppressLint("NewApi")
	public static String getPath(final Context context, final Uri uri) {

		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

		// DocumentProvider
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
			// ExternalStorageProvider
			if (isExternalStorageDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/" + split[1];
				}

				// TODO handle non-primary volumes
			}
			// DownloadsProvider
			else if (isDownloadsDocument(uri)) {

				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(Uri.parse
						("content://downloads/public_downloads"), Long.valueOf(id));

				return getDataColumn(context, contentUri, null, null);
			}
			// MediaProvider
			else if (isMediaDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}

				final String selection = "_id=?";
				final String[] selectionArgs = new String[]{split[1]};

				return getDataColumn(context, contentUri, selection, selectionArgs);
			}
		}
		// MediaStore (and general)
		else if ("content".equalsIgnoreCase(uri.getScheme())) {

			// Return the remote address
			if (isGooglePhotosUri(uri)) return uri.getLastPathSegment();

			return getDataColumn(context, uri, null, null);
		}
		// File
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}

		return null;
	}

	/**
	 * Get the value of the data column for this Uri. This is useful for
	 * MediaStore Uris, and other file-based ContentProviders.
	 *
	 * @param context       The context.
	 * @param uri           The Uri to query.
	 * @param selection     (Optional) Filter used in the query.
	 * @param selectionArgs (Optional) Selection arguments used in the query.
	 * @return The value of the _data column, which is typically a file path.
	 */
	public static String getDataColumn(Context context, Uri uri, String selection, String[]
			selectionArgs) {

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
		} finally {
			if (cursor != null) cursor.close();
		}
		return null;
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is Google Photos.
	 */
	public static boolean isGooglePhotosUri(Uri uri) {
		return "com.google.android.apps.photos.content".equals(uri.getAuthority());
	}
}
