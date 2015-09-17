package com.easy.manager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;

/**
 * ActivityManager
 * 
 * @Description
 * @author Joke Huang
 * @createDate 2014年7月9日
 * @version 1.0.0
 */

public class EasyActivityManager {
	private static EasyActivityManager instance;
	private List<Activity> allActivitys = new LinkedList<Activity>();

	private EasyActivityManager() {
	}

	/**
	 * 单例
	 * @return
	 */
	public static EasyActivityManager getInstance() {
		if (instance == null) {
			synchronized (EasyActivityManager.class) {
				if (instance == null) {
					instance = new EasyActivityManager();
				}
			}
		}
		return instance;
	}

	/**
	 * 添加一个活动中的activity，在activity的onCreate方法调用
	 * @param newActivity
	 */
	public void add(Activity newActivity) {
		synchronized (EasyActivityManager.class) {
			allActivitys.add(newActivity);
		}
	}

	/**
	 * 移除一个已经被结束的activity，在activity的onDestroy方法调用
	 * @param newActivity
	 */
	public void remove(Activity activity) {
		synchronized (EasyActivityManager.class) {
			allActivitys.remove(activity);
		}
	}

	/**
	 * 结束某一类activity，一般是在A activity里想结束B activity的时候使用
	 * @param activityClass
	 */
	public void finish(Class<? extends Activity> activityClass) {
		synchronized (EasyActivityManager.class) {
			List<Activity> matchActivity = new ArrayList<Activity>();
			for (Activity activity : allActivitys) {
				if (activity.getClass().equals(activityClass)) {
					matchActivity.add(activity);
				}
			}
			for (Activity activity : matchActivity) {
				if (!activity.isFinishing()) {
					activity.finish();
				}
				allActivitys.remove(activity);
			}
		}
	}

	/**
	 * 结束所有activity
	 */
	public void finishAll() {
		synchronized (EasyActivityManager.class) {
			for (Activity activity : allActivitys) {
				if (!activity.isFinishing()) {
					activity.finish();
				}
			}
			allActivitys.clear();
		}
	}

	/**
	 * 统计某类activity的个数
	 * @param activityClass
	 * @return
	 */
	public int count(Class<? extends Activity> activityClass) {
		synchronized (EasyActivityManager.class) {
			int count = 0;
			for (Activity activity : allActivitys) {
				if (activity.getClass().equals(activityClass)) {
					count++;
				}
			}
			return count;
		}
	}

	/**
	 * 判断是否存在某类activity
	 * @param activityClass
	 * @return
	 */
	public boolean has(Class<? extends Activity> activityClass) {
		return count(activityClass) > 0;
	}

	/**
	 * activity总数
	 * @return
	 */
	public int size() {
		return allActivitys.size();
	}

	/**
	 * 返回全部activity
	 * @return
	 */
	public List<Activity> getAllActivitys() {
		return allActivitys;
	}
}
