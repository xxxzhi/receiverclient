package com.receiverclient;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.receiverclient.houzhi.tools.HandlerConstant;
import com.receiverclient.houzhi.tools.StaticFinalVariable;

public class MainService extends Service {
	private ReceiverApplication application;
	private Handler handler; 		//�޸�UI�߳�
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.e("end","end1");
		application = (ReceiverApplication)this.getApplication();
		handler = application.getUIHandler();
		
		new Thread(){

			/* (non-Javadoc)
			 * @see java.lang.Thread#run()
			 */
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Log.i("hello","hello run");
				while(true){
					syncMsg();
					try {
						TimeUnit.SECONDS.sleep(15);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}.start();
		Log.i("start","hello run");
		return super.onStartCommand(intent, flags, startId);
	}
	private void syncMsg(){
		HttpGet get = new HttpGet("http://"+StaticFinalVariable.IP+StaticFinalVariable.URL_Str);
    	
		try {
		
			HttpResponse response = new DefaultHttpClient().execute(get);
			Log.e("error","response ok");
			if(response.getStatusLine().getStatusCode()==200){
				
				HttpEntity httpEntity= response.getEntity();
//				BufferedReader read = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
//				read.readLine();
				
		 		DataInputStream data = new DataInputStream(new BufferedInputStream(httpEntity.getContent()));
				String m  = data.readUTF();
				System.out.println(response.toString());
				
				Log.i("get msg",m);
				double a1=data.readDouble();
				System.out.println(a1);
				double a2 = data.readDouble();
				double a3 = data.readDouble();
				MsgFromServer msg = new MsgFromServer(m,a1, a2, a3);
				Message message = new Message();
				message.what =HandlerConstant.SYNC_MSG;
				message.obj = msg;
				handler.sendMessage(message);
				Log.e("error",response.getStatusLine()+"");
				Log.i("get msg",m +","+a1+","+a2+","+a3);				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		
	}
	
}
