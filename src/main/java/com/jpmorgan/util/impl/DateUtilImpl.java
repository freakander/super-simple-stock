package com.jpmorgan.util.impl;

import java.util.Calendar;
import java.util.Date;

import com.jpmorgan.util.DateUtil;

public class DateUtilImpl implements DateUtil {

	public DateUtilImpl(){		
	}
	
	public Date getMinutesBack(int minutes){
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MINUTE, minutes);
		return now.getTime();
	}
}
