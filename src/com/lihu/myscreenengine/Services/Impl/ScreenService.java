/* Copyright (C) 2010-2011, Mamadou Diop.
 *  Copyright (C) 2011, Doubango Telecom.
 *
 * Contact: Mamadou Diop <diopmamadou(at)doubango(dot)org>
 *	
 * This file is part of imsdroid Project (http://code.google.com/p/imsdroid)
 *
 * imsdroid is free software: you can redistribute it and/or modify it under the terms of 
 * the GNU General Public License as published by the Free Software Foundation, either version 3 
 * of the License, or (at your option) any later version.
 *	
 * imsdroid is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU General Public License for more details.
 *	
 * You should have received a copy of the GNU General Public License along 
 * with this program; if not, write to the Free Software Foundation, Inc., 
 * 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.lihu.myscreenengine.Services.Impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;

import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

import com.lihu.myscreenengine.Engine;
import com.lihu.myscreenengine.Main;
import com.lihu.myscreenengine.MyScreenApp;
import com.lihu.myscreenengine.screens.HomeActivity;
import com.lihu.myscreenengine.screens.IBaseScreen;

public class ScreenService {
	private final static String TAG = ScreenService.class.getCanonicalName();
	public final static Class HOME_SCREEN_CLASS = HomeActivity.class;
	private int mLastScreensIndex = -1; // ring cursor
	private final String[] mLastScreens = new String[] { // ring
	null, null, null, null };


	
	private RelativeLayout top,bottom;

	
	private ViewFlipper center;

	
	public boolean back() {
		String screen;
//		while(BaseScreen.isRefreshing)
//		{
//			System.out.println("isRefreshing.......");
//		}
		// no screen in the stack
		if (mLastScreensIndex < 0) {
			return true;
		}

		// zero is special case
		if (mLastScreensIndex == 0) {
			if ((screen = mLastScreens[mLastScreens.length - 1]) == null) {
				// goto home
				return show(HOME_SCREEN_CLASS);
			} else {
				return this.show(screen);
			}
		}
		// all other cases
		screen = mLastScreens[mLastScreensIndex - 1];
		mLastScreens[mLastScreensIndex - 1] = null;
		mLastScreensIndex--;
		if (screen == null || !show(screen)) {
			return show(HOME_SCREEN_CLASS);
		}

		return true;
	}

	
	public boolean bringToFront(int action, String[]... args) {
		Intent intent = new Intent(MyScreenApp.getContext(), Main.class);
		try {
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra("action", action);
			for (String[] arg : args) {
				if (arg.length != 2) {
					continue;
				}
				intent.putExtra(arg[0], arg[1]);
			}
			MyScreenApp.getContext().startActivity(intent);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
	public boolean bringToFront(String[]... args) {
		return this.bringToFront(0);
	}

	
	public boolean destroy(String id) {
//		final FragmentManager activityManager = (((Main) Engine.getInstance().getMainActivity())).getSupportFragmentManager()();
//		if (activityManager != null) {
//			activityManager.(id, false);
//			// http://code.google.com/p/android/issues/detail?id=12359
//			// http://www.netmite.com/android/mydroid/frameworks/base/core/java/android/app/LocalActivityManager.java
//			try {
//				final Field mActivitiesField = LocalActivityManager.class.getDeclaredField("mActivities");
//				if (mActivitiesField != null) {
//					mActivitiesField.setAccessible(true);
//					@SuppressWarnings("unchecked")
//					final Map<String, Object> mActivities = (Map<String, Object>) mActivitiesField.get(activityManager);
//					if (mActivities != null) {
//						mActivities.remove(id);
//					}
//					final Field mActivityArrayField = LocalActivityManager.class.getDeclaredField("mActivityArray");
//					if (mActivityArrayField != null) {
//						mActivityArrayField.setAccessible(true);
//						@SuppressWarnings("unchecked")
//						final ArrayList<Object> mActivityArray = (ArrayList<Object>) mActivityArrayField.get(activityManager);
//						if (mActivityArray != null) {
//							for (Object record : mActivityArray) {
//								final Field idField = record.getClass().getDeclaredField("id");
//								if (idField != null) {
//									idField.setAccessible(true);
//									final String _id = (String) idField.get(record);
//									if (id.equals(_id)) {
//										mActivityArray.remove(record);
//										break;
//									}
//								}
//							}
//						}
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			return true;
//		}
		return false;
	}
//	public IBaseScreen getCurrentScreen() {
//		return (IBaseScreen) ((Main) Engine.getInstance().getMainActivity()).getLocalActivityManager().getCurrentActivity();
//	}
//	public IBaseScreen getScreen(String id) {
//		return (IBaseScreen) ((Main) Engine.getInstance().getMainActivity()).getLocalActivityManager().getActivity(id);
//	}
	public void runOnUiThread(Runnable r) {
		if (Engine.getInstance().getMainActivity() != null) {
			Engine.getInstance().getMainActivity().runOnUiThread(r);
		} else {
			Log.e(this.getClass().getCanonicalName(), "No Main activity");
		}
	}
	
	public void setProgressInfoText(String text) {
	}

	
	public void setViewLayout(RelativeLayout top,ViewFlipper center,RelativeLayout bottom)
	{
		this.top = top;
		this.center = center;
		this.bottom = bottom;
	}

	
	public boolean show(Class<? extends Fragment> cls) {
		return show(cls, null, false);
	}

	
	public boolean show(Class<? extends Fragment> cls, String id) {
		return show(cls, id, false);
	}

	
	public boolean show(Class<? extends Fragment> cls, String id, boolean isFullScreen) {
		return show(cls, id, isFullScreen, null);
	}

	
	public boolean show(Class<? extends Fragment> cls, String id, boolean isFullScreen, Intent mIntent) {

		return show(cls, id, isFullScreen, mIntent, null);
	}

	
	public boolean show(Class<? extends Fragment> cls, String id, boolean isFullScreen, Intent mIntent, Bundle bundle) {
		final Main mainActivity = (Main) Engine.getInstance().getMainActivity();
		String screen_id = (id == null) ? cls.getCanonicalName() : id;

		Log.v(TAG, screen_id);

		Intent intent = new Intent(mainActivity, cls);
		if (mIntent != null) {
			intent = mIntent;
			intent.setClass(mainActivity, cls);
		}
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		intent.putExtra("id", screen_id);
//		final Window window = mainActivity.getLocalActivityManager().startActivity(screen_id, intent);
//		if (window != null) {
//			View view = mainActivity.getLocalActivityManager().startActivity(screen_id, intent).getDecorView();
//			InputMethodManager imm = (InputMethodManager)mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
//			boolean isOpen=imm.isActive(view);
//			if(isOpen)
//				imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
//			center.removeAllViews();
//			center.addView(view, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
////			center.showNext();
//			if (isFullScreen) {
//				top.setVisibility(View.GONE);
//				bottom.setVisibility(View.GONE);
//			} else {
//				top.setVisibility(View.VISIBLE);
//				bottom.setVisibility(View.VISIBLE);
//			}
//			this.mLastScreens[(++this.mLastScreensIndex % this.mLastScreens.length)] = screen_id;
//			this.mLastScreensIndex %= this.mLastScreens.length;
//			return true;
//		}
		return false;
	}

	
	public boolean show(String id) {
		final Fragment screen = (Fragment) ((Main) Engine.getInstance().getMainActivity()).getSupportFragmentManager().getFragment(null, id);
		if (screen == null) {
			Log.e(TAG, String.format("Failed to retrieve the Screen with id=%s", id));
			return false;
		} else {
			return this.show(screen.getClass(), id);
		}
	}

	
	public boolean showBottom(Class<? extends Fragment> cls, String id, Intent mIntent, Bundle bundle) {
		final Main mainActivity = (Main) Engine.getInstance().getMainActivity();
		String screen_id = (id == null) ? cls.getCanonicalName() : id;

		Log.v(TAG, screen_id);

		Intent intent = new Intent(mainActivity, cls);
		if (mIntent != null) {
			intent = mIntent;
			intent.setClass(mainActivity, cls);
		}
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		intent.putExtra("bottom_id", screen_id);
//		final Window window = mainActivity.getLocalActivityManager().startActivity(screen_id, intent);
//		if (window != null) {
//			View view = mainActivity.getLocalActivityManager().startActivity(screen_id, intent).getDecorView();
//			InputMethodManager imm = (InputMethodManager)mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
//			boolean isOpen=imm.isActive(view);
//			if(isOpen)
//				imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
//			bottom.removeAllViews();
//			bottom.addView(view, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
//			return true;
//		}
		return false;
	}

	
	public boolean showTop(Class<? extends Fragment> cls, String id, Intent mIntent, Bundle bundle) {
		final Main mainActivity = (Main) Engine.getInstance().getMainActivity();
		String screen_id = (id == null) ? cls.getCanonicalName() : id;

		Log.v(TAG, screen_id);

		Intent intent = new Intent(mainActivity, cls);
		if (mIntent != null) {
			intent = mIntent;
			intent.setClass(mainActivity, cls);
		}
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		intent.putExtra("top_id", screen_id);
//		final Window window = mainActivity.getLocalActivityManager().startActivity(screen_id, intent);
//		if (window != null) {
//			View view = mainActivity.getLocalActivityManager().startActivity(screen_id, intent).getDecorView();
//			InputMethodManager imm = (InputMethodManager)mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
//			boolean isOpen=imm.isActive(view);
//			if(isOpen)
//				imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
//			top.removeAllViews();
//			top.addView(view, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
//			return true;
//		}
		return false;
	}
}
