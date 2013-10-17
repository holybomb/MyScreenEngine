package com.lihu.myscreenengine.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.lihu.myscreenengine.R;
import com.lihu.myscreenengine.enums.SCREEN_TYPE;

public class HomeActivity extends BaseScreen
{
	public static final String TAG = HomeActivity.class.getCanonicalName();

	public HomeActivity()
	{
		super(SCREEN_TYPE.HOME, TAG);
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.activity_home, container);
		TextView txt = (TextView) view.findViewById(R.id.home_txt);
		txt.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// mScreenService.show(SettingActivity.class);
			}
		});
		return view;
	}
	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// getMenuInflater().inflate(R.menu.activity_home, menu);
	// return true;
	// }
}
