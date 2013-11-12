package com.test.du;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TestDU {
	//尽量避免使用static声明
	static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	static String s1="发表于 前天 09:24";
	static String s2="发表于 昨天 15:54";
	static String s3="发表于 7天前";
	
 public static void main(String[] args) {
//	能使用if else就不要使用多个if，可以提高效率   	
	if (s1.substring(4,6).equals("前天") ) {  
		gettimes1(2); 
	}
	if (s2.substring(4,6).equals("昨天")){
		gettimes2(1);
	}  
//	不能处理数字为两位数或更多的情况，此外matches判断有些多余
	if (s3.substring(4,5).matches("^[1-9]d*$")){
		gettimes3(Integer.parseInt(s3.substring(4,5)));
	}
 }
//以下三个方法作用相同，建议写成一个方法
public static void gettimes3(int day) {
	  Calendar calendar = Calendar.getInstance();
	  calendar.add(Calendar.DAY_OF_MONTH, -day);
	  String resultDay = format.format(calendar.getTime());
	  System.out.println(resultDay);
	     }

public static void gettimes2(int day) {
	 
	  Calendar calendar = Calendar.getInstance();
	  calendar.add(Calendar.DAY_OF_MONTH, -day);
	  String resultDay = format.format(calendar.getTime()) + " " + s2.substring(6, 12);
	  System.out.println(resultDay);
	     }

public static void gettimes1(int day) {
	  
	  Calendar calendar = Calendar.getInstance();
	  calendar.add(Calendar.DAY_OF_MONTH, -day);
	  String resultDay = format.format(calendar.getTime()) + " " + s1.substring(6, 12);
	  System.out.println(resultDay);
	     }


}



 

