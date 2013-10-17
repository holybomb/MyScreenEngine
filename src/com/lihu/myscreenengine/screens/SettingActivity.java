package com.lihu.myscreenengine.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.lihu.myscreenengine.R;
import com.lihu.myscreenengine.enums.SCREEN_TYPE;

public class SettingActivity extends BaseScreen {
	public static final String TAG = SettingActivity.class.getCanonicalName();
	public SettingActivity() {
		super(SCREEN_TYPE.SETTING,TAG);
	}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
    	View view = inflater.inflate(R.layout.activity_setting,container);
        TextView txt = (TextView)view.findViewById(R.id.set_text);
        txt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mScreenService.back();
			}
		});
        return view;
    }
}
