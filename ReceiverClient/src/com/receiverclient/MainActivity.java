package com.receiverclient;

import com.receiverclient.houzhi.tools.StaticFinalVariable;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends ActivityGroup implements View.OnClickListener {
	RelativeLayout content;
	private TextView title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new MenuSlideSetLeft(this);

		content = (RelativeLayout) findViewById(R.id.contentview);
		title = (TextView) findViewById(R.id.titlename);
		
		View v;
		v = findViewById(R.id.set);
		v.setOnClickListener(this);

		v = findViewById(R.id.run);
		v.setOnClickListener(this);

		v = findViewById(R.id.exit);
		v.setOnClickListener(this);

        
		
		launchActivity("run", BaiduMapActivity.class, R.string.title_run);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.set:
			launchActivity("set", SetActivity.class, R.string.title_set);
			break;
		case R.id.run:
			launchActivity("run", BaiduMapActivity.class, R.string.title_run);
			break;
		case R.id.exit:
			android.os.Process.killProcess(android.os.Process.myPid());
			break;
		}
	}

	private void launchActivity(String id, Class<?> activityClass, int titleId) {
		content.removeAllViews();
		title.setText(titleId);
		Intent intent = new Intent(MainActivity.this, activityClass);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

		Window window = getLocalActivityManager().startActivity(id, intent);
		View view = window.getDecorView();
		content.addView(view);
	}


	public static final String TAG_TEST = "test";
}
