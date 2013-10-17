package com.lihu.myscreenengine.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.lihu.myscreenengine.R;
import com.lihu.myscreenengine.enums.SCREEN_TYPE;

public class TopActivity extends BaseScreen
{
	public static final String TAG = TopActivity.class.getCanonicalName();

	public TopActivity()
	{
		super(SCREEN_TYPE.TOP, TAG);
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.activity_top,container);
	}
}
