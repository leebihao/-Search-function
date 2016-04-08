package com.itheiam.search.utils;

import android.util.Log;

public class LogUtil {
	public static boolean DEBUG = true;// 上线会改成false
	public static void i(String Tag, Object obj) {
		if (DEBUG) {
			Log.i(Tag, obj + "");
		}

	}
}
