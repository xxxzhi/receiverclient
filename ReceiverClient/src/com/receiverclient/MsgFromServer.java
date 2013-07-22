package com.receiverclient;

import java.io.Serializable;

import android.util.Log;

public class MsgFromServer implements Serializable{
	private String status ; 	//老人状态信息
	
	private double altitude;	//高度
	
	private double latitude;	//纬度
	
	private double longitude;	//经度
	
	public MsgFromServer(String s,double a,double la,double lo){
		status =s;
		altitude = a;
		latitude = la;
		longitude = lo;
	}
	public void setStatus(String s){
		status = s;
	}
	public String getStatus(){
		return status;
	}
	public void setAltitude(double s){
		altitude=s;
	}
	/**
	 * 
	 * @return 高度
	 */
	public String getAltitude(){
		return status;
	}
	
	public void setLatitude(double s){
		latitude =s;
	}
	/**
	 * 
	 * @return 纬度
	 */
	public double getLatitude(){
		return latitude;
	}
	public void setLongitude(double s){
		longitude=s;
	}
	/**
	 * 
	 * @return  经度
	 */
	public double getLongitude(){
		return longitude;
	}
}
