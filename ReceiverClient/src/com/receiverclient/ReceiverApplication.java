package com.receiverclient;

import android.app.Application;
import android.os.Handler;

public class ReceiverApplication extends Application {
	private Handler handlerUI;
	public Handler getUIHandler(){
		return handlerUI;
	}
	public void setUIHandler(Handler handler){
		handlerUI = handler;
	}
}
