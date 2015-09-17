package com.easy.util;

/**
 * @description
 * @author Joke
 * @email 113979462@qq.com
 * @create 2015年8月12日
 * @version 1.0.0
 */

public class RunDuration {
	private long startTime = -1;
	private long endTime = -1;

	public RunDuration start() {
		startTime = System.currentTimeMillis();
		return this;
	}

	public RunDuration end() {
		endTime = System.currentTimeMillis();
		return this;
	}

	public RunDuration log() {
		LogUtil.d("RunDuration", "RunDuration: " + (endTime - startTime));
		return this;
	}

	public RunDuration log(String tag) {
		LogUtil.d(tag, "RunDuration: " + (endTime - startTime));
		return this;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

}
