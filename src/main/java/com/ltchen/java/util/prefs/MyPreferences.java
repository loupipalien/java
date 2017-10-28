package com.ltchen.java.util.prefs;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * @file : MyPreferences.java
 * @date : 2017年7月8日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : Preferences存取信息.Preferences会利用合适的系统资源完成存取,并且这些资源随系统的不同而不同,
 * 例如在windows中就使用注册表(注册表有键值对这样的存储结构)
 * 
 * 报错：
 * 七月 08, 2017 4:42:01 下午 java.util.prefs.WindowsPreferences <init>
 * WARNING: Could not open/create prefs root node Software\JavaSoft\Prefs at root 0x80000002. Windows RegCreateKeyEx(...) returned error code 5.
 * 解决：
 * 1.打开注册表--在开始搜索栏 regedit
 * 2.找到目录HKEY_LOCAL_MACHINE\Software\JavaSoft
 * 3.新建文件夹 Prefs
 */
public class MyPreferences {

	public static void main(String[] args) throws BackingStoreException {
		Preferences preferences  = Preferences.userNodeForPackage(MyPreferences.class);
		preferences.put("Location", "Oz");
		preferences.put("Footwear", "Ruby Slippers");
		preferences.putInt("Companious", 4);
		preferences.putBoolean("Are there witches", true);
		int usageCount = preferences.getInt("UsageCount", 0);
		usageCount++;
		preferences.putInt("UsageCount", usageCount);
		for (String key : preferences.keys()) {
			System.out.println("key:" + preferences.get(key, null));
		}
//		preferences.removeNode();
		System.out.println("How many Companious does Dorothy have?");
		System.out.println(preferences.getInt("Companious", 0));
	}
}
