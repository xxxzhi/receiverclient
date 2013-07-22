package com.receiverclient;

import java.io.Serializable;

import android.util.Log;

public class MsgFromServer implements Serializable{
	private String status ; 	//����״̬��Ϣ
	
	private double altitude;	//�߶�
	
	private double latitude;	//γ��
	
	private double longitude;	//����
	
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
	 * @return �߶�
	 */
	public String getAltitude(){
		return status;
	}
	
	public void setLatitude(double s){
		latitude =s;
	}
	/**
	 * 
	 * @return γ��
	 */
	public double getLatitude(){
		return latitude;
	}
	public void setLongitude(double s){
		longitude=s;
	}
	/**
	 * 
	 * @return  ����
	 */
	public double getLongitude(){
		return longitude;
	}
}
