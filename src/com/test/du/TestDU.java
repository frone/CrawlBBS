package com.test.du;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TestDU {
	//��������ʹ��static����
	static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	static String s1="������ ǰ�� 09:24";
	static String s2="������ ���� 15:54";
	static String s3="������ 7��ǰ";
	
 public static void main(String[] args) {
//	��ʹ��if else�Ͳ�Ҫʹ�ö��if���������Ч��   	
	if (s1.substring(4,6).equals("ǰ��") ) {  
		gettimes1(2); 
	}
	if (s2.substring(4,6).equals("����")){
		gettimes2(1);
	}  
//	���ܴ�������Ϊ��λ�����������������matches�ж���Щ����
	if (s3.substring(4,5).matches("^[1-9]d*$")){
		gettimes3(Integer.parseInt(s3.substring(4,5)));
	}
 }
//������������������ͬ������д��һ������
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



 

