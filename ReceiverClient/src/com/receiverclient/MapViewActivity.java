package com.receiverclient;
import java.util.List;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.receiverclient.houzhi.tools.HandlerConstant;

public class MapViewActivity extends MapActivity {

	private MapView mMapView;
	private MapController mMapController;
	private GeoPoint mGeoPoint;
	private Button button;
	private double x;
	private double y;
//	private Handler handler;
	private NotificationManager m_notificationManager;
	private Notification m_notification;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapview);
//		m_notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//		m_notification = new Notification();
		mMapView = (MapView) findViewById(R.id.MapView01);
		// mMapView.setTraffic(true);//����Ϊ��ͨģʽ
		// mMapView.setSatellite(true);//����Ϊ����ģʽ
		mMapView.setStreetView(true);// ����Ϊ�־�ģʽ

		mMapController = mMapView.getController();
		// ���õ�ͼ����
		mMapView.setEnabled(true);
		mMapView.setClickable(true);

		mMapView.setBuiltInZoomControls(true);
		setPoint(30.659259, 104.065762);

//		handler = new Handler() {
//			public void handleMessage(Message msg) {
//				myPoint mypoint = (myPoint) msg.obj;
//				setPoint(mypoint.getY(), mypoint.getX());
//			}
//		};

		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				View view = LayoutInflater.from(MapViewActivity.this).inflate(
						R.layout.input_layout, null);
				AlertDialog.Builder builder = new AlertDialog.Builder(
						MapViewActivity.this);
				builder.setCancelable(false);
				builder.setTitle("����Ŀ�����...");
				builder.setView(view);
				builder.setIcon(R.drawable.mylocation);
				final EditText et1 = (EditText) view
						.findViewById(R.id.edittext1);
				final EditText et2 = (EditText) view
						.findViewById(R.id.edittext2);
				builder.setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
//								m_notification.icon = R.drawable.mylocation;
//								m_notification.tickerText = "�����...";
//								m_notification.defaults = Notification.DEFAULT_SOUND;
//								m_notification.setLatestEventInfo(MainActivity.this, "888", "***", null);
//								m_notification.notify();
								new Thread() {
									public void run() {
										Message message = new Message();
										 x = Float.parseFloat(et1.getText()
												.toString());
										 y = Float.parseFloat(et2.getText()
												.toString());
										 MsgFromServer p = new MsgFromServer("", x, y, 0);
										message.obj = p;
										message.what = HandlerConstant.SET_POSITION;
										handler.sendMessage(message);
									}
								}.start();
							}
						});
				builder.setNegativeButton("ȡ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

							}
						});
				builder.show();
			}
		});
		//��־ �޸��˵�
		initHandler();
		startService();
	}
	public static final int SET = 1;
	public static final int RECEIVE = 2;
	private void setPoint(double d, double e) {
		// TODO Auto-generated method stub
		// ��ʾ��������
		mMapView.setStreetView(true);// ����Ϊ�־�ģʽ

		mMapController = mMapView.getController();
		// ���õ�ͼ����
		mMapView.setEnabled(true);
		mMapView.setClickable(true);

		mMapView.setBuiltInZoomControls(true);
		mGeoPoint = new GeoPoint((int) (d * 1000000), (int) (e * 1000000));
		mMapController.animateTo(mGeoPoint);
		// �Ŵ���Ϊ18
		mMapController.setZoom(18);
		// ʹ�û���������ʾĿ��Ķ�����Ϣ
		MyLocationOverlay mylocationoverlay = new MyLocationOverlay();
		List<Overlay> list = mMapView.getOverlays();
		list.add(mylocationoverlay);
		// mMapView.refreshDrawableState();
		mMapView.invalidate();
	}

	// ��Ŀ���ַ�������Լ���Ҫ��ʾ����Ϣ
	class MyLocationOverlay extends Overlay {
		public boolean draw(Canvas canvas, MapView mapView, boolean shadow,
				long when) {
			super.draw(canvas, mapView, shadow);
			// �Ѿ������ת������ʾ���ϵľ���λ��
			Paint paint = new Paint();
			Point myScreenCoords = new Point();
			mapView.getProjection().toPixels(mGeoPoint, myScreenCoords);
			paint.setStrokeWidth(1);
			paint.setARGB(255, 255, 0, 0);
			paint.setTextSize(23);
			paint.setStyle(Paint.Style.STROKE);
			Bitmap bmp = BitmapFactory.decodeResource(getResources(),
					R.drawable.mylocation);
			canvas.drawBitmap(bmp, myScreenCoords.x, myScreenCoords.y, paint);
			canvas.drawText("", myScreenCoords.x, myScreenCoords.y, paint);
			return true;
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private ReceiverApplication application; //��������
    //����UI
    private Handler handler;
    private void initHandler(){

        application = (ReceiverApplication)getApplication();
    	handler = new Handler(){
    		
			/* (non-Javadoc)
			 * @see android.os.Handler#handleMessage(android.os.Message)
			 */
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				MsgFromServer m;
				switch(msg.what){
				case HandlerConstant.SYNC_MSG:
					m = (MsgFromServer)msg.obj;
					updateUI(m);
					break;
				case HandlerConstant.SET_POSITION:
					m = (MsgFromServer)msg.obj;
					updateUI(m);
					break;
				}
			}
    		
    	};
    	application.setUIHandler(handler);
    }
    /**
     * ����UI�߳�����
     * @param msg
     */
    public void updateUI(MsgFromServer msg){
    	setPoint(msg.getLatitude(), msg.getLongitude());
    }
    
    private void startService(){
    	Intent intent = new Intent("mainservice");
		
		startService(intent);
		Log.i("start service","start");
    }
}
