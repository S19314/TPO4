/**
 *
 *  @author Domariev Vladyslav S19314
 *
 */

package zad1;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.StringTokenizer;

public class Time {

    public static String passed(String d1, String d2){
    	boolean isContainsTime = false;
    	if(d1.contains("T") || d1.contains("T")) isContainsTime = true; 
    	
    	
    	String message = "";
    	try {
//	        System.out.print("d1 " + d1 );
//	        System.out.print(" d2 " + d2  + "\n");
	    	
	    	String newFormatData1 = parseFirstLine_V3(d1),
	    			newFormatData2 = parseFirstLine_V3(d2);
	        Duration duration = duration(d1, d2);
	    	
	    	Period period = period(d1, d2);
	    	
	        // int dni = period.getDays();
	        // float tygodni = period.getDays()/7.0F;
	        // ChronoUnit.DAYS.between();
	        // duration.get()
	        // Formar
	    	
	    	LocalDate[] localeDates	= createLocalDateTime(d1, d2);
	    	
	    	
	    	int hours = period.getDays()*24;
	    	// long days_v2 = period.getYears() * 365 + 
	    	ZonedDateTime[] zonedDateTimes = createZondeDateTime(d1, d2);
	    	
	    	long dni =  ChronoUnit.DAYS.between(localeDates[0], localeDates[1]);
	    	double weeks = dni/7.0; //ChronoUnit.WEEKS.between(zonedDateTimes[0], zonedDateTimes[1]);
	    	// zonedDateTimes[0].minu
	    	// zonedDateTimes[0].GETdA
	    	message = "Od "+ newFormatData1 +" do " + newFormatData2 + "\r\n"  + 
	    			"- mija: " + dni +" dni, ";
	    	
	    	
	    	if(weeks == (long)weeks) {
	    		message = message + " tygodni " +  (long)weeks + "\r\n";  
	    	}else {
	    		message = message + String.format(Locale.ENGLISH ,"tygodni %.2f", weeks) + "\r\n"; 
	    	}
	    			//	"- mija: " + dni +" dni, tygodni " + weeks + "\r\n" ;//  +
			// %.2f
	    	
	    	// System.out.println(String.format(Locale.ENGLISH ,"tygodni %.2f", weeks));
///	    	int godzin = period.get(ChronoUnit.HOURS);
	    	if(isContainsTime ) {
	    		// LocalTime[] localTimes = createLocalTime(d1, d2);	
	    		
	    		
	    		long godziny = ChronoUnit.HOURS.between(zonedDateTimes[0], zonedDateTimes[1]), //localTimes[0], localTimes[1]),
	    	    		minuty = ChronoUnit.MINUTES.between(zonedDateTimes[0], zonedDateTimes[1]);//localTimes[0], localTimes[1]);
	    	    
	    		message = message + "- godzin: " + godziny + ", minut: "+minuty+"  \r\n"; 
	    		// "[- godzin: g, minut: m ] \r\n";
	    	}
	
	    	
	    	String yearForCalendarData1, monthForCalendarData1, dayForCalendarData1, hoursForCalendarData1 = "", minutesForCalendarData1 = "",
	    		   yearForCalendarData2, monthForCalendarData2, dayForCalendarData2, hoursForCalendarData2 = "", minutesForCalendarData2 = ""; 
	    	StringTokenizer stringTokenizerData1 = new StringTokenizer(d1, "T");
	    	StringTokenizer stringTokenizerData2 = new StringTokenizer(d2, "T");
	    	String tmpFirstPartData1 = stringTokenizerData1.nextToken();
	    	String tmpFirstPartData2 = stringTokenizerData2.nextToken(); 
	    	
	    	yearForCalendarData1 = tmpFirstPartData1.trim().split("-")[0];
	    	monthForCalendarData1 = tmpFirstPartData1.trim().split("-")[1];
	    	dayForCalendarData1 = tmpFirstPartData1.trim().split("-")[2];
	    	
	    	yearForCalendarData2 = tmpFirstPartData2.trim().split("-")[0];
	    	monthForCalendarData2 = tmpFirstPartData2.trim().split("-")[1];
	    	dayForCalendarData2 = tmpFirstPartData2.trim().split("-")[2];
	    	
	    	
	    	if(isContainsTime) {
	    		 String secondPart;
	    			secondPart = stringTokenizerData1.nextToken().trim();
	    			hoursForCalendarData1 = secondPart.split(":")[0];
	    			minutesForCalendarData1 = secondPart.split(":")[1];
	    			
	    			secondPart = stringTokenizerData2.nextToken().trim();
	    			hoursForCalendarData2 = secondPart.split(":")[0];
	    			minutesForCalendarData2 = secondPart.split(":")[1];
	    	 
	    	 }
	    	
	    	
	    	Calendar calendarData1 = Calendar.getInstance();
	    	try {
	    		
	    		calendarData1.set(Integer.parseInt(yearForCalendarData1), Integer.parseInt(monthForCalendarData1)-1, Integer.parseInt(dayForCalendarData1));
	    		if(isContainsTime) {
		    		calendarData1.add(Calendar.HOUR, Integer.parseInt(hoursForCalendarData1));
		    		calendarData1.add(Calendar.MINUTE, Integer.parseInt(minutesForCalendarData1));
	    		}
	    	}catch(Exception exc) {
	    		// exc.printStackTrace();
	    	}
	    	
	    	Calendar calendarData2 = Calendar.getInstance();
	    	try {
	    		calendarData2.set(Integer.parseInt(yearForCalendarData2), Integer.parseInt(monthForCalendarData2)-1, Integer.parseInt(dayForCalendarData2));
	    		if(isContainsTime) {
		    		calendarData2.add(Calendar.HOUR, Integer.parseInt(hoursForCalendarData2));
		    		calendarData2.add(Calendar.MINUTE, Integer.parseInt(minutesForCalendarData2));
	    		}
	    	}catch(Exception exc) {
	    		// exc.printStackTrace();
	    	}
	    	
	    	
	    	
	    	

	    	String infoKalendarzowo = "";
	    	
//	    	long years = ChronoUnit.YEARS.between(zonedDateTimes[0], zonedDateTimes[1]);
	    	int yearrrr = -Integer.parseInt(yearForCalendarData1),
	    		monthhhh = -Integer.parseInt(monthForCalendarData1),
	    		dayyyy = -Integer.parseInt(dayForCalendarData1);
	    	
//	    	if(dayyyy < 0) dayyyy++;
	    	if(monthhhh < 0) monthhhh++;
	    	  if(yearrrr < 0) yearrrr++;
	    	

	    	if(isContainsTime) {
	    		calendarData2.add(Calendar.MINUTE, -Integer.parseInt(minutesForCalendarData1));
	    		calendarData2.add(Calendar.HOUR, -Integer.parseInt(hoursForCalendarData1));
		    	
		    	// calendarData2.add(Calendar.YEAR, -Integer.parseInt(yearForCalendarData1));
	    	}
	    	calendarData2.add(Calendar.DAY_OF_MONTH, dayyyy);
	    	calendarData2.add(Calendar.MONTH, monthhhh);
	    	calendarData2.add(Calendar.YEAR, yearrrr);
	    	


	    	
	    	long years = calendarData2.get(Calendar.YEAR); 
	    	if(years > 0) {
	    		infoKalendarzowo = infoKalendarzowo + years;
	    		int ostacza = (int)years%10;
	    		if(ostacza > 1 && ostacza < 5 ) {
	    			infoKalendarzowo = infoKalendarzowo + " lata";
	    		}else if(ostacza == 1) {
	    			infoKalendarzowo = infoKalendarzowo + " rok";
	    		}else {
	    			infoKalendarzowo = infoKalendarzowo + " lat";
	    		}
	    	}
	    	
	    	long month = 1 + calendarData2.get(Calendar.MONTH);//ChronoUnit.MONTHS.between(zonedDateTimes[0],zonedDateTimes[1]);
	    	if(month > 0) {
	    		
	    		if(years > 0) infoKalendarzowo = infoKalendarzowo + ",";
	    		
	    		infoKalendarzowo = infoKalendarzowo + " "+ month;
	    		int ostacza = (int)month%10;
	    		if(month > 1 && month < 5 ) { 
	    			infoKalendarzowo = infoKalendarzowo + " miesiące";
	    		}else if(ostacza == 1) {
	    			infoKalendarzowo = infoKalendarzowo + " miesiąc";
	    		}else {
	    			infoKalendarzowo = infoKalendarzowo + " miesięcy";
	    		}
	    	}
	    	
	    	long daysCalendar = calendarData2.get(Calendar.DAY_OF_MONTH); // ChronoUnit.MONTHS.between(zonedDateTimes[0],zonedDateTimes[1]);
	    	
	    	if(daysCalendar > 0) {
	    		
	    		if(month > 0) infoKalendarzowo = infoKalendarzowo + ",";
	    		
	    		infoKalendarzowo = infoKalendarzowo + " "+ daysCalendar;
	    		int ostacza = (int)daysCalendar%10;
	    		if(daysCalendar > 1 && daysCalendar < 5 ) { 
	    			infoKalendarzowo = infoKalendarzowo + " dni";
	    		}else if(ostacza == 1) {
	    			infoKalendarzowo = infoKalendarzowo + " dzień";
	    		}else {
	    			infoKalendarzowo = infoKalendarzowo + " dni";
	    		}
	    	}
	    	/*
	    	Calendar ccc3 = Calendar.getInstance();
	    	ccc3.set(2000, 0, 1);
	    	System.out.println("ccc3 " + ccc3.get(Calendar.YEAR));
	    	System.out.println("ccc3 " + ccc3.get(Calendar.MONTH));
	    	System.out.println("ccc3 " + ccc3.get(Calendar.DATE));
	    	*/
//	    	for(int i = 0; i < 11; i++ ) {
//	    		System.out.println("I " + i + "... Modul " + (int)i%10);
//	    	}
	    	
	    	
	    	
	    	
	    	
	    	
	    	if(!infoKalendarzowo.equals("")) message = message + "- kalendarzowo: " + infoKalendarzowo;
	    	// if(dni > 0) {
	    	
	    	//}
	    	
	    	
	    	// "- mija: " + dni ; 
	    	/*
	    	+ " dni, tygodni t\r\n" + 
	        		"[- godzin: g, minut: m ] \r\n" + 
	        		"[- kalendarzowo: [ r (lat|lata|rok}, ] [ m (miesięcy|miesiące|miesiąc ),]  [d  (dzień|dni)]  ]";
	        */
	       // System.out.println("\n"+message+"\n");
    	}catch(DateTimeParseException dateTimeParseException) {
    		// dateTimeParseException.printStackTrace();
    		// System.out.println("*** " + dateTimeParseException.toString() + " " + dateTimeParseException.getMessage());
    		return "*** " + dateTimeParseException.toString();// + " " + dateTimeParseException.getMessage();	
    		
    	}
        
        return message;
    }
    
    

    private static String parseFirstLine_V2(String data) {
    	if(false) { //data.contains("T")) {
    		// System.out.println("Data before  " + data);
	    	data = data.replace('T', ' ');
	    	// System.out.println("Data after " + data);
    	}
    	String datePattern  = "d MMMM yyyy (EEEE)",
    			newFormatData1  = ""; //,


    	LocalDateTime data1 = LocalDateTime.parse(data); 
		newFormatData1 = data1.format(
	    			DateTimeFormatter.ofPattern(datePattern, new Locale("pl"))
		);
		
		   
		if(data.contains("T")) {
    		// String time = stringTokenizer.nextToken();
//    		 1 kwietnia 2020 (środa) godz. 10:00
    		// System.out.print(" Date ___" + date + "___");
    		// System.out.println(" Time ____" + time + "_____");
    		
    		String timePattern = "HH:mm";
    		/*
    		LocalTime localeTime = LocalTime.parse(time);
    		String formattedDate = localeTime.format(
					 DateTimeFormatter.ofPattern(timePattern, new Locale("pl"))
			 ); 
    		newFormatData1 = newFormatData1 + " godz. " + formattedDate;
    			*/			 
    		// 	System.out.println("Local " + localeTime + "__ " + formattedDate);
    	}
	
    	return newFormatData1;
    }
    
    
    private static String parseFirstLine_V3(String data) {
    	
    	
    	if(data.contains("T")) DateTimeFormatter.ISO_LOCAL_DATE_TIME.parse(data);
    	
    	StringTokenizer stringTokenizer = new StringTokenizer(data, "T");
    	
    	String datePattern  = "d MMMM yyyy (EEEE)",
    			newFormatData1  = "",
    			date = stringTokenizer.nextToken().trim();
    	
    	LocalDate data1 = LocalDate.parse(date);
		newFormatData1 = data1.format(
	    			DateTimeFormatter.ofPattern(datePattern, new Locale("pl"))
	        		);
    	if(data.contains("T")) {
    		String time = stringTokenizer.nextToken();
//    		 1 kwietnia 2020 (środa) godz. 10:00
    		// System.out.print(" Date ___" + date + "___");
    		// System.out.println(" Time ____" + time + "_____");
    		
    		String timePattern = "HH:mm";
    		LocalTime localeTime = LocalTime.parse(time);
    		String formattedDate = localeTime.format(
					 DateTimeFormatter.ofPattern(timePattern, new Locale("pl"))
			 ); 
    		newFormatData1 = newFormatData1 + " godz. " + formattedDate;
    						 
    		// 	System.out.println("Local " + localeTime + "__ " + formattedDate);
    	}
	
    	return newFormatData1;
    }
    
    private static Period period(String data1, String data2) {
    	/*
    	boolean haveTime1 = false,
    			haveTime2 = false;
    	*/
    	String[] argumentsData1 = new String[] {data1},
    			argumentsData2 = new String[] {data2}; 
    	if(data1.contains("T")) {
    		argumentsData1 = parseLongDate(data1);
    		// haveTime1 = true;
    	}
    	
    	if(data2.contains("T")) {
    		argumentsData2 = parseLongDate(data2);
    		// haveTime2 = true;
    	}
    	
    	LocalDate localData1 = LocalDate.parse(argumentsData1[0]),
    		localData2 = LocalDate.parse(argumentsData2[0]);
    	/*
    	LocalTime localTime1 = LocalTime.of(0,0),
    			localTime2 = LocalTime.of(0,0);
    	if(haveTime1) {
    		localTime1 = LocalTime.parse(argumentsData1[1]);
    	}
    	
    	if(haveTime2) {
    		localTime2 = LocalTime.parse(argumentsData2[1]);
    	}
    	
    	
    	localDateTime1.plusHours(localTime1.getHour());
    	localDateTime1.plusMinutes(localTime1.getMinute());
    	
    	localDateTime2.plusHours(localTime2.getHour());
    	localDateTime2.plusMinutes(localTime2.getMinute());
    	*/
    	LocalDate localDate1 = LocalDate.from(localData1);
    	LocalDate localDate2 = LocalDate.from(localData2);
    	
    	 Period period  = Period.between(localDate1, localDate2);
    	
    	return period;
    }	
    
    private static Duration duration(String data1, String data2) {
    	boolean haveTime1 = false,
    			haveTime2 = false;
    	String[] argumentsData1 = new String[] {data1},
    			argumentsData2 = new String[] {data2}; 
    	if(data1.contains("T")) {
    		argumentsData1 = parseLongDate(data1);
    		haveTime1 = true;
    	}
    	
    	if(data2.contains("T")) {
    		argumentsData2 = parseLongDate(data2);
    		haveTime2 = true;
    	}
    	
    	LocalDate localData1 = LocalDate.parse(argumentsData1[0]),
    		localData2 = LocalDate.parse(argumentsData2[0]);
    	LocalTime localTime1 = LocalTime.of(0,0),
    			localTime2 = LocalTime.of(0,0);
    	if(haveTime1) {
    		localTime1 = LocalTime.parse(argumentsData1[1]);
    	}
    	
    	if(haveTime2) {
    		localTime2 = LocalTime.parse(argumentsData2[1]);
    	}
    	
    	LocalDateTime localDateTime1 = LocalDateTime.of(localData1, localTime1);
    	LocalDateTime localDateTime2 = LocalDateTime.of(localData2, localTime2);
    	/*
    	localDateTime1.plusHours(localTime1.getHour());
    	localDateTime1.plusMinutes(localTime1.getMinute());
    	
    	localDateTime2.plusHours(localTime2.getHour());
    	localDateTime2.plusMinutes(localTime2.getMinute());
    	*/
    	// Europe/Warsaw
    	ZonedDateTime zonedDateTime1 = ZonedDateTime.of(localDateTime1, ZoneId.of("Europe/Warsaw")),
    			zonedDateTime2 = ZonedDateTime.of(localDateTime2, ZoneId.of("Europe/Warsaw"));
    	
    	// Period period  = Period.between(localDateTime1, localDateTime2);
//    	Duration duration = Duration.between(localDateTime1, localDateTime2);
    	Duration duration = Duration.between(zonedDateTime1, zonedDateTime2);
    	return duration;
    }
    
    
    
    private static ZonedDateTime[] createZondeDateTime(String data1, String data2) {
    	ZonedDateTime[] result  = new ZonedDateTime[2];
    	boolean haveTime1 = false,
    			haveTime2 = false;
    	String[] argumentsData1 = new String[] {data1},
    			argumentsData2 = new String[] {data2}; 
    	if(data1.contains("T")) {
    		argumentsData1 = parseLongDate(data1);
    		haveTime1 = true;
    	}
    	
    	if(data2.contains("T")) {
    		argumentsData2 = parseLongDate(data2);
    		haveTime2 = true;
    	}
    	
    	LocalDate localData1 = LocalDate.parse(argumentsData1[0]),
    		localData2 = LocalDate.parse(argumentsData2[0]);
    	LocalTime localTime1 = LocalTime.of(0,0),
    			localTime2 = LocalTime.of(0,0);
    	if(haveTime1) {
    		localTime1 = LocalTime.parse(argumentsData1[1]);
    	}
    	
    	if(haveTime2) {
    		localTime2 = LocalTime.parse(argumentsData2[1]);
    	}
    	
    	LocalDateTime localDateTime1 = LocalDateTime.of(localData1, localTime1);
    	LocalDateTime localDateTime2 = LocalDateTime.of(localData2, localTime2);
    	/*
    	localDateTime1.plusHours(localTime1.getHour());
    	localDateTime1.plusMinutes(localTime1.getMinute());
    	
    	localDateTime2.plusHours(localTime2.getHour());
    	localDateTime2.plusMinutes(localTime2.getMinute());
    	*/
    	// Europe/Warsaw
    	ZonedDateTime zonedDateTime1 = ZonedDateTime.of(localDateTime1, ZoneId.of("Europe/Warsaw")),
    			zonedDateTime2 = ZonedDateTime.of(localDateTime2, ZoneId.of("Europe/Warsaw"));
    	result[0] = zonedDateTime1;
    	result[1] = zonedDateTime2;
    	return result;
    }
    
    private static LocalDate[] createLocalDateTime(String data1, String data2){
    	
    	LocalDate[] result  = new LocalDate[2];
    	boolean haveTime1 = false,
    			haveTime2 = false;
    	String[] argumentsData1 = new String[] {data1},
    			argumentsData2 = new String[] {data2}; 
    	if(data1.contains("T")) {
    		argumentsData1 = parseLongDate(data1);
    		// haveTime1 = true;
    	}
    	
    	if(data2.contains("T")) {
    		argumentsData2 = parseLongDate(data2);
    	//	haveTime2 = true;
    	}
    	
    	LocalDate localData1 = LocalDate.parse(argumentsData1[0]),
    		localData2 = LocalDate.parse(argumentsData2[0]);
    	/*
    	LocalTime localTime1 = LocalTime.of(0,0),
    			localTime2 = LocalTime.of(0,0);
    	
    	if(haveTime1) {
    		localTime1 = LocalTime.parse(argumentsData1[1]);
    	}
    	
    	if(haveTime2) {
    		localTime2 = LocalTime.parse(argumentsData2[1]);
    	}
    
    	LocalDateTime localDateTime1 = LocalDateTime.of(localData1, localTime1);
    	LocalDateTime localDateTime2 = LocalDateTime.of(localData2, localTime2);
    	*/
    	/*
    	localDateTime1.plusHours(localTime1.getHour());
    	localDateTime1.plusMinutes(localTime1.getMinute());
    	
    	localDateTime2.plusHours(localTime2.getHour());
    	localDateTime2.plusMinutes(localTime2.getMinute());
    	*/
    	// Europe/Warsaw
    	/*
    	ZonedDateTime zonedDateTime1 = ZonedDateTime.of(localDateTime1, ZoneId.of("Europe/Warsaw")),
    			zonedDateTime2 = ZonedDateTime.of(localDateTime2, ZoneId.of("Europe/Warsaw"));
    	*/
    	result[0] = localData1;
    	result[1] = localData2;
    	return result;
    	
    }

    private static LocalTime[] createLocalTime(String data1, String data2){
    	
    	LocalTime[] result  = new LocalTime[2];
    	boolean haveTime1 = false,
    			haveTime2 = false;
    	String[] argumentsData1 = new String[] {data1},
    			argumentsData2 = new String[] {data2}; 
    	if(data1.contains("T")) {
    		argumentsData1 = parseLongDate(data1);
    	 haveTime1 = true;
    	}
    	
    	if(data2.contains("T")) {
    		argumentsData2 = parseLongDate(data2);
    		haveTime2 = true;
    	}
    	
    	
    	LocalTime localTime1 = LocalTime.of(0,0),
    			localTime2 = LocalTime.of(0,0);
    	
    	if(haveTime1) {
    		localTime1 = LocalTime.parse(argumentsData1[1]);
    	}
    	
    	if(haveTime2) {
    		localTime2 = LocalTime.parse(argumentsData2[1]);
    	}
    
    	result[0] = localTime1;
    	result[1] = localTime2;
    	return result;
    	
    }

    
    private static String[] parseLongDate(String data) {
    	String[] tabl = new String[2];
    	tabl[0] = data;
    	if(data.contains("T")) {
    		StringTokenizer stringTokenizer = new StringTokenizer(data, "T");
    		tabl[0] = stringTokenizer.nextToken();
    		tabl[1] = stringTokenizer.nextToken();
    	}
    	return tabl;
    }
    
      
    
}
