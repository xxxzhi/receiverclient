package com.receiverclient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.receiverclient.houzhi.tools.StaticFinalVariable;

public class SetActivity extends Activity {
	private Button button;
	private Button ipSure;
	private TextView ip;
	private ReceiverApplication application;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
//        initHandler();  			//��ʼ��handler
        setContentView(R.layout.set);
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
        
    }
}
