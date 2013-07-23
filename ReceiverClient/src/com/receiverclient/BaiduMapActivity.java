package com.receiverclient;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.receiverclient.houzhi.tools.HandlerConstant;

public class BaiduMapActivity extends Activity {
	BMapManager mBMapMan = null;
	MapView mMapView = null;

	// 搜索相关
	MKSearch mSearch = null; // 搜索模块，也可去掉地图模块独立使用

	public static final String KEY = "AF6AEF9A44D45C9A4DC521F348C58C4FFCC689F0";

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		mBMapMan = new BMapManager(getApplication());
		mBMapMan.init(KEY, null);
		// 注意：请在试用setContentView前初始化BMapManager对象，否则会报错
		setContentView(R.layout.baidu_mapview);
		mMapView = (MapView) findViewById(R.id.bmapsView);
		mMapView.setBuiltInZoomControls(true);
		// 设置启用内置的缩放控件
		MapController mMapController = mMapView.getController();
		// 得到mMapView的控制权,可以用它控制和驱动平移和缩放
		GeoPoint point = new GeoPoint((int) (39.915 * 1E6),
				(int) (116.404 * 1E6));
		// 用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
		mMapController.setCenter(point);// 设置地图中心点
		mMapController.setZoom(12);// 设置地图zoom级别

		mMapView.getController().enableClick(true);
		mMapView.getController().setZoom(12);

		// 初始化搜索模块，注册事件监听
		mSearch = new MKSearch();
		mSearch.init(mBMapMan, new MKSearchListener() {
			@Override
			public void onGetPoiDetailSearchResult(int type, int error) {
			}

			public void onGetAddrResult(MKAddrInfo res, int error) {
				if (error != 0) {
					String str = String.format("错误号：%d", error);
					Toast.makeText(BaiduMapActivity.this, str,
							Toast.LENGTH_LONG).show();
					return;
				}
				// 地图移动到该点
				mMapView.getController().animateTo(res.geoPt);
				if (res.type == MKAddrInfo.MK_GEOCODE) {
					// 地理编码：通过地址检索坐标点
					String strInfo = String.format("纬度：%f 经度：%f",
							res.geoPt.getLatitudeE6() / 1e6,
							res.geoPt.getLongitudeE6() / 1e6);
					Toast.makeText(BaiduMapActivity.this, strInfo,
							Toast.LENGTH_LONG).show();
				}
				if (res.type == MKAddrInfo.MK_REVERSEGEOCODE) {
					// 反地理编码：通过坐标点检索详细地址及周边poi
					String strInfo = res.strAddr;
					Toast.makeText(BaiduMapActivity.this, strInfo,
							Toast.LENGTH_LONG).show();

				}
				// 生成ItemizedOverlay图层用来标注结果点
				ItemizedOverlay<OverlayItem> itemOverlay = new ItemizedOverlay<OverlayItem>(
						null, mMapView);
				// 生成Item
				OverlayItem item = new OverlayItem(res.geoPt, "", null);
				// 得到需要标在地图上的资源
				Drawable marker = getResources().getDrawable(
						R.drawable.icon_markf);
				// 为maker定义位置和边界
				marker.setBounds(0, 0, marker.getIntrinsicWidth(),
						marker.getIntrinsicHeight());
				// 给item设置marker
				item.setMarker(marker);
				// 在图层上添加item
				itemOverlay.addItem(item);

				// 清除地图其他图层
				mMapView.getOverlays().clear();
				// 添加一个标注ItemizedOverlay图层
				mMapView.getOverlays().add(itemOverlay);
				// 执行刷新使生效
				mMapView.refresh();
			}

			public void onGetPoiResult(MKPoiResult res, int type, int error) {

			}

			public void onGetDrivingRouteResult(MKDrivingRouteResult res,
					int error) {
			}

			public void onGetTransitRouteResult(MKTransitRouteResult res,
					int error) {
			}

			public void onGetWalkingRouteResult(MKWalkingRouteResult res,
					int error) {
			}

			public void onGetBusDetailResult(MKBusLineResult result, int iError) {
			}

			@Override
			public void onGetSuggestionResult(MKSuggestionResult res, int arg1) {
			}

		});
		
		result = (TextView) findViewById(R.id.result);
		
		initHandler();
		startService();
	}
	private TextView result ;
	private void startService() {
		Intent intent = new Intent("mainservice");

		startService(intent);
		Log.i("start service", "start");
	}


	void search(int lat, int lon) {
		GeoPoint ptCenter = new GeoPoint(lat, lon);
		// 反Geo搜索
		mSearch.reverseGeocode(ptCenter);
	}

	private ReceiverApplication application;
	private Handler handler;

	private void initHandler() {

		application = (ReceiverApplication) getApplication();
		handler = new Handler() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see android.os.Handler#handleMessage(android.os.Message)
			 */
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				MsgFromServer m;
				switch (msg.what) {
				case HandlerConstant.SYNC_MSG:
					m = (MsgFromServer) msg.obj;
					System.out.println("get msg");
					result.setText(m.getStatus());
					search((int) (m.getLatitude()* 1e6), (int)(m.getLongitude()*1e6));
					break;
				case HandlerConstant.SET_POSITION:
					m = (MsgFromServer) msg.obj;
					break;
				}
			}

		};
		application.setUIHandler(handler);
	}

	@Override
	protected void onDestroy() {
		mMapView.destroy();
		if (mBMapMan != null) {
			mBMapMan.destroy();
			mBMapMan = null;
		}
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		if (mBMapMan != null) {
			mBMapMan.stop();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		if (mBMapMan != null) {
			mBMapMan.start();
		}
		super.onResume();
	}
}
