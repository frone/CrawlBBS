package com.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface jiekou {
	public int equals(String text);
}

class Equals implements jiekou {
	int x = 9;
	String m = "I love you.";

	public int equals(String text) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(text);
		if (isNum.matches() && x > Integer.parseInt(text)) {
			return 1;
		} else if (text.equals(m)) {
			return 0;
		} else {
			return -1;
		}
	}
}
