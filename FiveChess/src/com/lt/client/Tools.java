package com.lt.client;

import java.awt.Point;
/***
 * 工具类
 * @author liaot
 *
 */
public  class Tools {
	//点转化成String
	public static String pointToString( int color, Point point) {
		StringBuffer result = new StringBuffer();
		result.append(color).append("-");
		result.append(point.x).append("-");
		result.append(point.y);
		return result.toString();
	}
	//点转化成String
		public static Point  stringToPoint (String message) {
			String[] result = message.split("-",3);
			Point point = new Point(Integer.valueOf(result[1]), Integer.valueOf(result[2]));
			return point;
 		}
}
