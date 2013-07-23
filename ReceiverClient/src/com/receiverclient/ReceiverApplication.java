package com.receiverclient;

import com.receiverclient.houzhi.tools.StaticFinalVariable;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

public class ReceiverApplication extends Application {
	private Handler handlerUI;
	public Handler getUIHandler(){
		return handlerUI;
	}
	public void setUIHandler(Handler handler){
		handlerUI = handler;
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		final SharedPreferences sp = getSharedPreferences("ip", Context.MODE_PRIVATE);
        String s = sp.getString("ip", "");
        StaticFinalVariable.IP=s;
	}
	
	
}
