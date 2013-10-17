package com.lihu.myscreenengine;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

import com.lihu.myscreenengine.Services.Impl.ScreenService;
import com.lihu.myscreenengine.screens.BottomActivity;
import com.lihu.myscreenengine.screens.HomeActivity;
import com.lihu.myscreenengine.screens.TopActivity;

public class Main extends FragmentActivity {
	public final ScreenService mScreenService;
	public Main() {
		Engine.initialize();
		Engine.getInstance().setMainActivity(this);
		mScreenService = ((Engine)Engine.getInstance()).getScreenService();
	}
	RelativeLayout top,bottom;
	ViewFlipper center;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		top = (RelativeLayout) findViewById(R.id.main_top_layout);
		center = (ViewFlipper) findViewById(R.id.main_center_layout);
		bottom = (RelativeLayout) findViewById(R.id.main_bottom_layout);
		mScreenService.setViewLayout(top, center, bottom);
		mScreenService.show(HomeActivity.class);
		mScreenService.showTop(TopActivity.class, null, null, null);
		mScreenService.showBottom(BottomActivity.class, null, null, null);
	}
}
