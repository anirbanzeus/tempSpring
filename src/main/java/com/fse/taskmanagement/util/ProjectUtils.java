package com.fse.taskmanagement.util;

import java.util.Calendar;
import java.util.Date;

public class ProjectUtils {
	
public Date getNextDay(Date currentDate) {
		
		// get a calendar instance, which defaults to "now"
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(currentDate);
	 
	    // add one day to the date/calendar
	    calendar.add(Calendar.DAY_OF_YEAR, 1);
	    
	    // now get "tomorrow"
	    Date tomorrow = calendar.getTime();
		return tomorrow;
		
	}

}
