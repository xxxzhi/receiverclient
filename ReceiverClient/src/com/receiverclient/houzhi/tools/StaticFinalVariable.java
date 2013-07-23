package com.receiverclient.houzhi.tools;

public final class StaticFinalVariable {
	
	/**
	 * 默认服务器地址
	 */
	public static final String URL_Str = ":8080/Server/ReturnMsg";
	
	public static String IP = "125.216.245.215";
	/**
	 * 转换成字节时，总共的字节数 的数量占用的位数
	 */
	public static final int  TRANS_DATA_BYTES = 2;
	/**
	 * 最小压缩大小
	 */
	public static final long MIN_LENGTH=500;
	
	/**
	 * id 整数长度
	 */
	public static final int ID_BYTES= 4;
}
