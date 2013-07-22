package com.receiverclient;

import com.receiverclient.R;
import com.receiverclient.houzhi.tools.HandlerConstant;
import com.receiverclient.houzhi.tools.StaticFinalVariable;
 
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	private Button button;
	private Button ipSure;
	private TextView ip;
	private ReceiverApplication application;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
//        initHandler();  			//初始化handler
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button1);
        
        button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent1 = new Intent();
				intent1.setClass(MainActivity.this, MapViewActivity.class);
				startActivity(intent1);
			}
		});
        ipSure = (Button)findViewById(R.id.sure_ip);
        ip = (TextView)findViewById(R.id.ip);
        final SharedPreferences sp = getSharedPreferences("ip", Context.MODE_PRIVATE);
        String s = sp.getString("ip", "");
        ip.setText(s);
        StaticFinalVariable.IP=s;
        ipSure.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String s = ip.getText().toString();
				SharedPreferences.Editor ed = sp.edit();
				StaticFinalVariable.IP=s;
				ed.putString("ip", s);
				ed.commit();
			}
		});
        
        
//        application = (ReceiverApplication)getApplication();
    }
    
    //处理UI
    private Handler handler;
    private void initHandler(){
    	handler = new Handler(){
    		
			/* (non-Javadoc)
			 * @see android.os.Handler#handleMessage(android.os.Message)
			 */
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch(msg.what){
				case HandlerConstant.SYNC_MSG:
					MsgFromServer m = (MsgFromServer)msg.obj;
					updateUI(m);
					break;
				}
			}
    		
    	};
    	application.setUIHandler(handler);
    }
    /**
     * 更新UI线程内容
     * @param msg
     */
    public void updateUI(MsgFromServer msg){
    	
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    
}
