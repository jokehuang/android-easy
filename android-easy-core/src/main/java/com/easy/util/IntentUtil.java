package com.easy.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	 * 启动activity
	 *
	 * @param context
	 * @param clazz
	 */
	public static boolean startActivity(Context context, Class<?> clazz) {
		return startActivity(context, new Intent(context, clazz), false);
	}

	public static boolean startActivity(Context context, Intent intent, boolean isNewTask) {
		if (isNewTask) intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		try {
			context.startActivity(intent);
			return true;
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean startActivityForResult(Activity activity, Intent intent, int
			requestCode) {
		try {
			activity.startActivityForResult(intent, requestCode);
			return true;
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 打开文件管理器
	 *
	 * @param activity
	 */
	public static boolean startFileManager(Activity activity, int requestCode) {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "file/*");
		return startActivityForResult(activity, intent, requestCode);
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

		i.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android" + "" +
				".package-archive");
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
		return true;
	}

	/**
	 * 检查是否安装了某个应用
	 *
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static boolean isInstalled(Context context, String packageName) {
		PackageManager packageManager = context.getPackageManager();
		List<PackageInfo> pinfos = packageManager.getInstalledPackages(0);
		for (PackageInfo info : pinfos) {
			if (info.packageName.equalsIgnoreCase(packageName)) return true;
		}
		return false;
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
	 * 拍照后储存到媒体库
	 *
	 * @param activity
	 * @param requestCode
	 */
	public static boolean startPhoto(Activity activity, int requestCode) {
		return startActivityForResult(activity, new Intent(MediaStore.ACTION_IMAGE_CAPTURE),
				requestCode);
	}

	/**
	 * 拍照后储存到指定路径
	 *
	 * @param activity
	 * @param requestCode
	 * @param path
	 */
	public static boolean startPhoto(Activity activity, int requestCode, String path) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path)));
		return startActivityForResult(activity, intent, requestCode);
	}

	/**
	 * 打开相册
	 *
	 * @param activity
	 * @param requestCode
	 */
	public static boolean startAlbum(Activity activity, int requestCode) {
		return startActivityForResult(activity, new Intent(Intent.ACTION_PICK, MediaStore.Images
				.Media.EXTERNAL_CONTENT_URI), requestCode);
	}

	/**
	 * 分享文字
	 *
	 * @param context
	 * @param content
	 * @return
	 */
	public static boolean startShareText(Context context, String content) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT, content);
		return startActivity(context, intent, true);
	}

	/**
	 * 分享图片
	 *
	 * @param context
	 * @param imgPath
	 * @return
	 */
	public static boolean startShareImage(Context context, String imgPath) {
		Uri imgUri = Uri.fromFile(new File(imgPath));
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("image/*");
		intent.putExtra(Intent.EXTRA_STREAM, imgUri);
		return startActivity(context, intent, true);
	}

	/**
	 * 分享图片
	 *
	 * @param context
	 * @param imgPaths
	 * @return
	 */
	public static boolean startShareImage(Context context, List<String> imgPaths) {
		ArrayList<Uri> imgUris = new ArrayList<>();
		for (String path : imgPaths) {
			imgUris.add(Uri.fromFile(new File(path)));
		}
		Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
		intent.setType("image/*");
		intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imgUris);
		return startActivity(context, intent, true);
	}

	/**
	 * 打开电话面板
	 *
	 * @param context
	 * @param tel
	 */
	public static boolean startTelephone(Context context, String tel) {
		return startActivity(context, new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel)),
				true);
	}

	/**
	 * 打开地图显示指定位置
	 *
	 * @param context
	 * @param latitude
	 * @param longitude
	 * @return
	 */
	public static boolean startMap(Context context, double latitude, double longitude, String
			poiName) {
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + latitude + "," +
				longitude + "?q=" + poiName));
		return startActivity(context, intent, true);
	}

	/**
	 * 打开某个网站
	 *
	 * @param context
	 * @param url
	 * @return
	 */
	public static boolean startWeb(Context context, String url) {
		if (!url.matches("^http[s]?://.*")) {
			url = "http://" + url;
		}
		Uri uri = Uri.parse(url);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		return startActivity(context, intent, true);
	}

	/**
	 * 搜索
	 *
	 * @param context
	 * @param keyword
	 * @return
	 */
	public static boolean startSearch(Context context, String keyword) {
		Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
		intent.putExtra(SearchManager.QUERY, keyword);
		return startActivity(context, intent, true);
	}

	/**
	 * 跳到市场
	 *
	 * @param context
	 */
	public static boolean startMarket(Context context) {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_APP_MARKET);
		return startActivity(context, intent, true);
	}

	/**
	 * 跳到本应用的市场详细页
	 *
	 * @param context
	 */
	public static boolean startMarket(Context context, String packageName) {
		Uri uri = Uri.parse("market://details?id=" + packageName);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		return startActivity(context, intent, true);
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
	public static Map<String, String> getInfo(final Context context, final Uri uri) {

		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

		// DocumentProvider
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
			// ExternalStorageProvider
			if (isExternalStorageDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				if ("primary".equalsIgnoreCase(type)) {
					Map<String, String> info = new HashMap<>();
					info.put("path", Environment.getExternalStorageDirectory() + "/" + split[1]);
					return info;
				}

				// TODO handle non-primary volumes
			}
			// DownloadsProvider
			else if (isDownloadsDocument(uri)) {

				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(Uri.parse
						("content://downloads/public_downloads"), Long.valueOf(id));
				return getColumns(context, contentUri, null, null);
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

				return getColumns(context, contentUri, selection, selectionArgs);
			}
		}
		// MediaStore (and general)
		else if ("content".equalsIgnoreCase(uri.getScheme())) {

			// Return the remote address
			if (isGooglePhotosUri(uri)) {
				Map<String, String> info = new HashMap<>();
				info.put("path", uri.getLastPathSegment());
				return info;
			}

			return getColumns(context, uri, null, null);
		}
		// File
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			Map<String, String> info = new HashMap<>();
			info.put("path", uri.getPath());
			return info;
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
	//	public static String getDataColumn(Context context, Uri uri, String selection, String[]
	//			selectionArgs) {
	//
	//		Map<String, String> columns = getColumns(context, uri, selection, selectionArgs);
	//		if (columns != null) return columns.get("_data");
	//		return null;
	//	}
	public static Map<String, String> getColumns(Context context, Uri uri, String selection,
	                                             String[] selectionArgs) {
		Cursor cursor = null;

		try {
			cursor = context.getContentResolver().query(uri, null, selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				Map<String, String> result = new HashMap<>();
				for (int i = 0; i < cursor.getColumnCount(); i++) {
					String key = cursor.getColumnName(i);
					String value = cursor.getString(i);
					result.put(key, value);
				}
				String path = result.get("_data");
				if (EmptyUtil.notEmpty(path)) result.put("path", path);
				return result;
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
