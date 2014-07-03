package util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import apps.newDealInvoker;
import beans.TransferRule;
import util.common.DateU;





import org.joda.time.DateTime;
import org.joda.time.Days;

import constants.CommonConstants;

//import oracle.sql.DATE;



public class commonUTIL {
	   public final static String ENCODING="UTF-8";
	
	static protected String USER_HOME=null;
    static protected String USER_NAME=null;
	static public void displayError(String name,String methodName, Exception e) {
      System.out.println("Classname : " + name + " : MethodName : " +methodName + " :: "+ e);
       
    }
	
	
	static public  Color getColors() {
		// TODO Auto-generated method stub
		return new Color(232, 230, 215);
	}
	/**
	 * Returns current date in the passed format
	 *	@author yogesh
	 *	@overloaded
	 * @param dateFormatStr
	 * @return date
	 */
	static public String getCurrentDateTime( String dateFormatStr ) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
		Date date = new Date();
			
		return  dateFormat.format(date);
	}

	 /**
	   * return the contents of this file as a byte[]
	   *
	   * @param is
	   * @return
	   * @throws IOException
	   */
	  public static byte[] readBinaryFile(InputStream is) throws IOException {

		  if( is == null ) {
			  throw new IllegalArgumentException("InputStream is null");
		  }

		  byte[] buf = new byte[2048];
		  ByteArrayOutputStream tmp = new ByteArrayOutputStream();


		  for(int read; (read = is.read(buf, 0, buf.length)) != -1; ) {
			  tmp.write(buf, 0, read);
		  }

		  return tmp.toByteArray();
	  }

	  /**
	     * Returns the <code>String</code> pathname of the user's home directory
	     * where this <code>Defaults</code> object will be saved.
	     *
	     * @return the <code>String</code> pathname of the user's home directory
	     */
	  
	  static public String getUserHome() {
	        if(USER_HOME == null)
	            USER_HOME=System.getProperty("user.home");
	        return USER_HOME;
	    }

	
	 static public String replaceString(String str, String oldString, String newString) {
		  if (str == null || oldString == null) return str;
		  int index = str.indexOf(oldString);
		  if (index < 0) return str;
		  String newStr = "";
		  String currentStr = str;
		  while (index >= 0) {
			  String before = "";
			  if (index > 0) {
				  before = currentStr.substring(0, index);
			  }
			  String after = currentStr.substring(index + oldString.length());
			  newStr += before + newString;
			  currentStr = after;
			  index = currentStr.indexOf(oldString);
			  if (index < 0)
				  newStr += currentStr;
		  }
		  return newStr;
	  }
	
	static public double converStringToDouble(String doubleValue) {
		Double doub = new Double(doubleValue);
		return doub.doubleValue();
	}
	static public int converStringToInteger(String intValue) {
		Integer doub = new Integer(intValue);
		return doub.intValue();
	}
	static public String converDoubleToString(double doubleValue) {
		Double doub = new Double(doubleValue);
		return doub.toString();
		
	}
	
	static public boolean validatePhoneNumberField(String value) {
		 String sPhoneNumber = "605-8889999";
		 boolean flag = false;
		 Pattern pattern = Pattern.compile("\\d{3}-\\d{7}");
	      Matcher matcher = pattern.matcher(sPhoneNumber);
	 
	      if (matcher.matches()) {
	    	  flag = true;
	      } 
	      return flag;
 }
	
	
	static public Timestamp getTimeStamp (String date) {
	 String NEW_FORMAT = CommonConstants.SDF_DATE_TIME_FORMAT;
	
	SimpleDateFormat formats = new SimpleDateFormat(NEW_FORMAT);
	

	return Timestamp.valueOf( formats.format(stringToDate(date, true)));
}
	static public String getTimeStampToString (Timestamp date) {
	if(date == null) {
		commonUTIL.display("CommonUtil", "getTimeStampToString  timestamp getting null <<<<<<<<<<<<<<<<<<<<<<");
		return "";
	}
		 String NEW_FORMAT = CommonConstants.SDF_DATE_TIME_FORMAT;
		SimpleDateFormat formats = new SimpleDateFormat(NEW_FORMAT);
		String dateF = formats.format(date.getTime());
		return dateF.toString();
	} 
	
	

	static public void setLabelFont(JComponent label) {
		label.setFont(new Font("Arial", Font.BOLD, 13));
	
		
	}
	static public void setBackGroundColor(JComponent label) {
		//label.setBackground(getColors());
	
		
	}
	static public DateU getDate(Date date) {
		
		return DateU.valueOf(date);
	}
	
	public static String doubleFormat(double value) {
		DecimalFormat df = new DecimalFormat("############.##");
		return df.format(value);

	}
	
	
	public static double roundToZero(double value) {
    	if(Math.abs(value) < 1E-100) {
    		return 0.0;
    	}

    	return value;
    }
	
	 static public StringBuffer unzipBytes(byte bytes[])
	  throws IOException,InterruptedException {
		  try {
			  ByteArrayInputStream is= new ByteArrayInputStream(bytes);
			  GZIPInputStream gzin= new GZIPInputStream(is);
			  InputStreamReader reader= new  InputStreamReader(gzin);
			  BufferedReader buf=new BufferedReader(reader);
			  String line=null;
			  StringBuffer log = new StringBuffer(1024);
			  while((line=buf.readLine()) != null) {
               log.append(line).append(System.getProperty("line.separator"));
			  }
			  return log;
		  } catch (Exception e) {
			 commonUTIL.displayError("CommonUtil", "unzipBytes", e);
			  return null;
		  }
	  }
	 static public int parseInt(String s) {
	        try{
	            return isEmpty(s) ? Integer.MIN_VALUE : Integer.parseInt(s.replaceAll("\\+|,|\\s", ""));
	        } catch (NumberFormatException e) {
	            return Integer.MAX_VALUE;
	        }
	    }
	 static public boolean isEmpty(String s) {
		  return ((s == null) || (s.trim().length() == 0));
	  }
	 /**
	   * @param collection
	   * @return true if map is not null and size not equal to zero
	   * @see java.util.Map#size()
	   */
	  public static boolean isEmpty(Map<?, ?> map){
		  return (map == null || map.size() == 0);
	  }

	 static public java.sql.Date convertStringtoSQLDate(String date) {
		 if(isEmpty(date))
			 return null;
		return convertSQLDate(stringToDate(date,true));
		 
	 }
	 
	 static public void showAlertMessage(String msg) {
		 JOptionPane.showMessageDialog(null,msg,null,
	      				JOptionPane.INFORMATION_MESSAGE);
	 }
	 
	 
	 static public void showPopuUP(Component comp, String msg) {
		 
		 JOptionPane.showInternalInputDialog(comp,new JTextField());
	 }
	 
	 public static boolean isNumeric(String str)  
	 {  
	   try  
	   {  
	     double d = Double.parseDouble(str);  
	   }  
	   catch(NumberFormatException nfe)  
	   {  
	     return false;  
	   }  
	   return true;  
	 }
	 
	 static public boolean isStringDate(String date) {
		 boolean  isdate = true;
		 if(!isEmpty(date)) {
			 java.util.Date date1 =  stringToDate(date,false);
			 if(date1 == null)
				 isdate =  false;
		 } else {
			 isdate = false;
		 }
		 return isdate;
		
	 }
	 static public java.sql.Timestamp getSQLTimeStamp() {
		 Calendar currentDate = Calendar.getInstance();
			return new java.sql.Timestamp(currentDate.getTimeInMillis());
			 
		 }
	 static public java.sql.Timestamp convertStringtoSQLTimeStamp(String date) {
			return new java.sql.Timestamp( stringToDate(date,true).getTime());
			 
		 }
	/* static public String convertSQLDatetoString(java.sql.Timestamp ts) {
		 long timeValue = ts.getTime();
	        long fractionSeconds = timeValue % 1000; // The millisecond component
	        if (fractionSeconds == 0) {
	          
	            timeValue += ts.getNanos() / 1000000; // convert to milliseconds
	        }
	        Date d = new Date(timeValue);
	        return dateToString(d);
	 } */
	 static public String convertSQLDatetoString(java.sql.Timestamp ts) {
		return getTimeStampToString(ts);
	       
	 } 
	static public java.sql.Date convertSQLDate(java.util.Date date) {
		return new java.sql.Date(date.getTime());
	}
	 static public String getCurrentDate(java.util.Date date) {
		 Calendar currentDate = Calendar.getInstance();
		  SimpleDateFormat formatter= 
		  new SimpleDateFormat(CommonConstants.SDF_DATE_FORMAT);
		  String dateNow = formatter.format(currentDate.getTime());
		  
	        return dateNow;
	    }
	 
	 static public String getDateDefaultFormat(java.util.Date date) {
		
		  SimpleDateFormat formatter= 
		  new SimpleDateFormat(CommonConstants.SDF_DATE_FORMAT);
		  String dateNow = formatter.format(date);
		  
	        return dateNow;
	    }
	 static public java.util.Date getCurrentDate() {
		 Calendar currentDate = Calendar.getInstance();
		 SimpleDateFormat formatter= getDateFormat();
				  new SimpleDateFormat(CommonConstants.SDF_DATE_FORMAT);
		 return (java.util.Date) currentDate.getTime();
	    }
	 
	 static public String getCurrentDateTime() {
		 Calendar currentDate = Calendar.getInstance();
		  SimpleDateFormat formatter= 
		  new SimpleDateFormat(CommonConstants.SDF_DATE_TIME_FORMAT);
		  String dateNow = formatter.format(currentDate.getTime());
		  
	        return dateNow;
	    }
	 static public Date getCurrentTimeDate() {
		 Calendar currentDate = Calendar.getInstance();
		 SimpleDateFormat formatter= 	 new SimpleDateFormat(CommonConstants.SDF_DATE_TIME_FORMAT);
		  String dateNow = formatter.format(currentDate.getTime());
		  return formatter.getCalendar().getTime();
	 }
	 
	 static public Date getCurrentTimeDate(String date) {
		
		 SimpleDateFormat formatter= 	 new SimpleDateFormat(CommonConstants.SDF_DATE_TIME_FORMAT);
		 Date parsedDate;
			try {
				parsedDate = formatter.parse(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
				
		    formatter.format(parsedDate.getTime());
		  return formatter.getCalendar().getTime();
	 }
	 static public java.sql.Timestamp getStringToTimestamp(String dateTime) {
		 if(isEmpty(dateTime))
				 return null;
		 if(dateTime.contains("-")) 
			 dateTime = dateTime.replace("-", "/");
		 SimpleDateFormat dateFormat = new SimpleDateFormat(CommonConstants.SDF_DATE_TIME_FORMAT);
		  Timestamp timestamp = null;
		    Date parsedDate;
			try {
				parsedDate = dateFormat.parse(dateTime);
				timestamp = new java.sql.Timestamp(parsedDate.getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}

		

		  
	        return timestamp;
	    }
	 static public String getAddDays(int days) {
		 Calendar currentDate = Calendar.getInstance();
		 currentDate.add(Calendar.DATE, days);
		 SimpleDateFormat formatter= 
		 new SimpleDateFormat(CommonConstants.SDF_DATE_FORMAT);
		  String dateNow = formatter.format(currentDate.getTime());
		  
	        return dateNow;
	    }
	 static public java.util.Date stringToDate(String dateStr,boolean isLenient) {
		   if(dateStr.contains("-")) 
			   dateStr = dateStr.replace("-", "/");
	        SimpleDateFormat format = new SimpleDateFormat(CommonConstants.SDF_DATE_FORMAT);
	        if(dateStr.contains("-")) {
	        	format = new SimpleDateFormat(CommonConstants.SDF_DATE_FORMAT);
	        }
	        format.setLenient(isLenient);
	        java.util.Date date = null;
	        boolean flag = false;
	        try {
	           date = format.parse(dateStr);
	            if (date != null)
	               return date;
	            else
	                return null;
	        } catch (Exception e) {
	        	displayError("CommonUTIL","stringTOdate",e);
	            return date;
	        }
	      
	    }
	 static public void display(String name,String message) {
	      System.out.println("Classname : " + name + " : MethodName : " +message + " :: ");
	       
	    }
	 static public String  dateToString(java.util.Date date) {
	        SimpleDateFormat formator = new SimpleDateFormat(CommonConstants.SDF_DATE_FORMAT);
	        formator.setLenient(true);
	        String datetostring = "";
	        String nowYYYYMMDD = new String(formator.format(date));
	        datetostring = formator.format(date).toString();
	      return nowYYYYMMDD.toString();
	    }
	 
	 /**
	   * Returns the IP address of the host we're running on.
	   */
	  public static String getLocalHost() {
		  InetAddress addr = null;
		  String strAddr=null;
		  try {
			  addr = InetAddress.getLocalHost();
			  strAddr = addr.getHostAddress();
		  }catch (Exception e) {
			  commonUTIL.displayError("CommonUTIL", "getLocalHost() ", e); }
		  return strAddr;
	  }

	  public static String getLocalHostName() {
		  InetAddress addr = null;
		  String strAddr=null;
		  try {
			  addr = InetAddress.getLocalHost();
			  
		  }catch (Exception e) {
			  commonUTIL.displayError("CommonUTIL", "getLocalHost() ", e); }
		  return addr.getHostName();
	  }
	  
	  
	  public static String getServerIP() {
		  return getLocalHost();
	  }
	  /**
	   * Answer true if the specified host names the one we're running on.
	   * @param hostNameOrIPAddr The host to test:  either it's string name, or
	   *                 the string form of its IP address (e.g. "166.24.219.1").
	   */
	  
	  
	  public static boolean isLocalHost(String hostNameOrIPAddr)
	  {
		  InetAddress localAddr, argAddr;
		  // Following InetAddress calls may throw UnknownHostException
		  try {
			  localAddr = InetAddress.getLocalHost();
			  argAddr = InetAddress.getByName(hostNameOrIPAddr);
		  } catch (UnknownHostException ex) {
			  commonUTIL.displayError("CommonUTIL", "isLocalHost() ", ex);
			  // Either we can't even this this host's IP address (!!), or
			  // we can't get the IP address for the given host
			  return false;        // Either way, best guess is that this host is not the given host
		  }
		  return localAddr.equals(argAddr);
	  }
	  
	  public boolean checkDateValidation(Date startDate,Date endDate) {
	    	if(startDate.after(endDate)) 
	    		return false;
	    	else
	    		return true;
	    	
	    }
	  
	  public static String getDateFormat(Date date) {
	    	//System.out.println(date.getDay() + " " + date.getYear() + " " + date.getMonth());
	    //	System.out.println(date.get);
		  String dd = "";
		  if(date == null)
			  return dd;
	    	SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.SDF_DATE_FORMAT); 
	    	 dd = sdf.format(date); 
	    	return dd;


	    }
	  
	  public static SimpleDateFormat getDateFormat() {
	    	//System.out.println(date.getDay() + " " + date.getYear() + " " + date.getMonth());
	    //	System.out.println(date.get);
		  String dd = "";
		
	    	SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.SDF_DATE_FORMAT); 
	    	return sdf;


	    }
	  public static SimpleDateFormat getDateTimeFormat() {
	    	//System.out.println(date.getDay() + " " + date.getYear() + " " + date.getMonth());
	    //	System.out.println(date.get);
		  String dd = "";
		
	    	SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.SDF_DATE_TIME_FORMAT); 
	    	return sdf;


	    }
	  
	  
	  public static String getOnlyDate(Date fromDate1) {
		  SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.SDF_DATE_FORMAT); 
			 String sfromValue =  sdf.format(fromDate1);
			return sfromValue;
	  }
	  public static String getOnlyDateFromStringDate(String fromDate1) {
		 
		  SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.SDF_DATE_FORMAT); 
			 String sfromValue =  sdf.format( commonUTIL.stringToDate(fromDate1, true));
			return sfromValue;
	  }
	  public static boolean between2dates(Date fromDate1,Date toDate2,Date valueDate) {
		  SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.SDF_DATE_FORMAT); 
		 String sfromValue =  sdf.format(fromDate1);
		 String svalueDate = sdf.format(valueDate);
		 String stoDate2 = sdf.format(toDate2);
		 
		  if(valueDate.compareTo(fromDate1) ==0)
			  return true;
	  if(valueDate.after(fromDate1) && valueDate.before(toDate2)) {
		  return true;
	  } else  {
		  boolean flag = false;
		  if(sfromValue.equalsIgnoreCase(svalueDate) &&  valueDate.before(toDate2)) {
				 return true;
			 }
		  if( stoDate2.equalsIgnoreCase(svalueDate) &&  valueDate.after(fromDate1) ) {
				 return true;
			 }
	  }
		  return false;
	  }

  

	public static void setPath(String path) {
	//	this.path = path;
		// TODO Auto-generated method stub
		
	}
	//String path = "";
	/**
	 * 
	 * @author yogesh
	 * @return current date in string format
	 */
	static public String getCurrentDateInString() {
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat(CommonConstants.SDF_DATE_TIME_FORMAT);
		return formatter.format(currentDate.getTime());
	}
	public static String convertDateTimeTOString(Date date) {
		// TODO Auto-generated method stub
		SimpleDateFormat formatter = new SimpleDateFormat(CommonConstants.SDF_DATE_TIME_FORMAT);
		return formatter.format(date.getTime());
	}

	
	/**
	 * Returns true if date1 is greater than date2
	 * 
	 * @author yogesh
	 * @param date1
	 * @param date2
	 * @return
	 */
	static public boolean checkGreaterDate(Date date1, Date date2) {
		
		if(date1.after(date2)) {
			
			return true;
			
		} else {
			
			return false;
		}
		
		
	}
	
	/**
	 * Returns a date object for a day, month and year provided
	 * 
	 * @author yogesh
	 * @param day
	 * @param month
	 * @param year
	 * @return Date
	 */

	static public Date setDate(int day, int month, int year) {

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.YEAR, year);

		return cal.getTime();

	}

	/**
	 * Returns true if the year is a leap year
	 * 
	 * @author yogesh
	 * @param year
	 * @return boolean
	 */
	static public boolean isLeapYear(int year) {

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		return cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;

	}

	/**
	 * Returns number of months between two dates
	 * 
	 * @author yogesh
	 * @param startDate
	 * @param endDate
	 * @return integer
	 */
	static public int diffInMonths(Date startDate, Date endDate) {

		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(startDate);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDate);

		int diffYear = endCalendar.get(Calendar.YEAR)
				- startCalendar.get(Calendar.YEAR);
		return (diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar
				.get(Calendar.MONTH));

	}

	/**
	 * Returns number of days between two dates
	 * 
	 * @author yogesh
	 * @param startDate
	 * @param endDate
	 * @return long
	 */
	static public long diffInDays(Date startDate, Date endDate) {

		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(startDate);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDate);

		long miliSecondForStartCalendar = startCalendar.getTimeInMillis();
		long miliSecondEndCalendar = endCalendar.getTimeInMillis();

		long diffInMilis = miliSecondEndCalendar - miliSecondForStartCalendar;
		
		
		return diffInMilis / (24 * 60 * 60 * 1000);

	}
	
	/**
	 *  Returns number of days between two dates using Joda DateTime Library
	 * 
	 * @author yogesh
	 * @param startDate
	 * @param endDate
	 * @return long
	 */
	
	static public long jodaDiffInDays(Date startDate, Date endDate) {

		DateTime startTime = null;
		DateTime endTime= null;
		
		startTime = new DateTime(startDate);
		
		endTime = new DateTime(endDate);
				
		long diffInDays = Days.daysBetween(startTime, endTime).getDays();
		
		return diffInDays;

	}
	
	
	/**
	 * Adds/Substract the no. of days to a date
	 * 
	 * @author yogesh
	 * @param updateDate
	 * @param noOfDays
	 * @return Date
	 */

	static public Date addSubtractDate(Date updateDate, int noOfDays) {

		Calendar newDate = Calendar.getInstance();
		newDate.setTime(updateDate);
		newDate.add(Calendar.DATE, noOfDays);

		return newDate.getTime();

	}
	
	/**
	 * Adds/Substract the no. of days to a date which is in string format
	 * 
	 * @author yogesh
	 * @param dateInString
	 * @param noOfDays
	 * @return date in string format
	 */
	static public String addSubtractDate(String dateInString, int noOfDays) {

		Calendar newDate = Calendar.getInstance();
		newDate.setTime(stringToDate(dateInString, true));
		newDate.add(Calendar.DATE, noOfDays);

		SimpleDateFormat formatter = new SimpleDateFormat(CommonConstants.SDF_DATE_FORMAT);
		String dateNow = formatter.format(newDate.getTime());

		return dateNow;

	}
	
	/**
	 * Returns a date after subtracting any one of the following: Day, Month,
	 * Quarter, SemiAnnual or a Year
	 * 
	 * @author yogesh
	 * @param updateDate
	 * @param deductionNo
	 * @param criteria
	 * @return Date
	 */
	static public Date addSubtractDate(Date updateDate, int addSubtratcNo,
			String criteria) {

		Calendar newDate = Calendar.getInstance();
		newDate.setTime(updateDate);
		int lastDayOfMonth = newDate.getActualMaximum(Calendar.DAY_OF_MONTH);
		int year = newDate.get(Calendar.YEAR);
		int month = newDate.get(Calendar.MONTH);
		int day = newDate.get(Calendar.DAY_OF_MONTH);

		/*
		 * if ( ( month == 1 && ( lastDayOfMonth == 29 && isLeapYear(year) ) ||
		 * ( lastDayOfMonth == 28 && !isLeapYear(year) ) ) ) {
		 * 
		 * criteria = "skip"; newDate.set(Calendar.MONTH, 0); int lastDay =
		 * newDate.getActualMaximum(Calendar.DAY_OF_MONTH);
		 * newDate.set(Calendar.DAY_OF_MONTH, lastDay);
		 * 
		 * 
		 * }
		 */

		if (criteria.equals("day")) {

			newDate.add(Calendar.DATE, addSubtratcNo);

		} else if (criteria.equals("week")) {

			newDate.add(Calendar.DATE, addSubtratcNo);

		} else if (criteria.equals("month")) {

			if (lastDayOfMonth == day) {

				newDate.add(Calendar.DATE,
						-newDate.getActualMaximum(Calendar.DAY_OF_MONTH));

			} else {

				newDate.add(Calendar.MONTH, addSubtratcNo);

			}

		} else if (criteria.equals("quarter")) {

			if (lastDayOfMonth == day) {

				newDate.add(Calendar.MONTH, addSubtratcNo);
				newDate.set(Calendar.DATE,
						newDate.getActualMaximum(Calendar.DAY_OF_MONTH));

			} else {

				newDate.add(Calendar.MONTH, addSubtratcNo);

			}

		} else if (criteria.equals("semiAnnual")) {

			if (lastDayOfMonth == day) {

				newDate.add(Calendar.MONTH, addSubtratcNo);
				newDate.set(Calendar.DATE,
						newDate.getActualMaximum(Calendar.DAY_OF_MONTH));

			} else {

				newDate.add(Calendar.MONTH, addSubtratcNo);

			}

		} else if (criteria.equals("year")) {

			if (lastDayOfMonth == day) {

				newDate.add(Calendar.MONTH, addSubtratcNo);
				newDate.set(Calendar.DATE,
						newDate.getActualMaximum(Calendar.DAY_OF_MONTH));

			} else {

				newDate.add(Calendar.MONTH, addSubtratcNo);

			}

		}

		return newDate.getTime();

	}

	/**
	 * Returns an integer indicating day in a week. Sunday is 1 whereas Saturday
	 * has integer value of 7
	 * 
	 * @param dateInString
	 * @param dateFormat
	 * @return int
	 */
	static int getWeekDay(String dateInString, String dateFormat) {

		Calendar c = Calendar.getInstance();
		c.setTime(stringToDate(dateInString, true));

		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

		return dayOfWeek;
	}
	
	
	/**
	 * Returns the last day of a month for a given date of String format
	 * 
	 * @author yogesh
	 * @param dateInString
	 * @return int
	 */

	static public int getLastDayofMonth(String dateInString) {

		Calendar c = Calendar.getInstance();
		c.setTime(stringToDate(dateInString, true));
		int lastDayofMonth = c.getActualMaximum(c.DAY_OF_MONTH);

		return lastDayofMonth;

	}

	/**
	 * Returns the last day of a month for a given date of Date format
	 * 
	 * @author yogesh
	 * @param checkDate
	 * @return
	 */

	static public int getLastDayofMonth(Date checkDate) {

		Calendar c = Calendar.getInstance();
		c.setTime(checkDate);
		int lastDayofMonth = c.getActualMaximum(c.DAY_OF_MONTH);

		return lastDayofMonth;

	}


	/*public static boolean isEmpty(Vector  rules) {
		// TODO Auto-generated method stub
		if(rules == null || rules.isEmpty())
		return true;
		return false;
	} */


	/**
	   * Compares two given objects.
	   * <p>
	   * If both are null, returns true. If one of them is null,
	   * but not the other one, returns false.
	   * Otherwise, it uses the <code>equals()</code>
	   * method of the first Object.
	   *
	   * @param v1 an Object
	   * @param v2 an Object
	   *
	   * @return A boolean, true if both objects are identical, or false
	   * otherwise
	   */
	  static public boolean isSame(Object v1,Object v2) {
		  if(v1==null && v2==null) return true;
		  if(v1==null && v2 != null) return false;
		  if(v2==null && v1 != null) return false;
		  if (v1.getClass().isArray() && v2.getClass().isArray()) {
			  return Arrays.deepEquals((Object[])v1, (Object[])v2);
		  }
		  return v1.equals(v2);
	  }

	  public static String lpad(String str,int len, char padChar) {
		  return pad(str, len, padChar, false);
	  }
	  public static String rpad(String str,int len, char padChar) {
		  return pad(str, len, padChar, true);
	  }
	  protected static String pad(String str,int len, char padChar, boolean rpad) {
		  if (str.length() == len)
			  return str;
		  else if (str.length() > len)
			  return str.substring(0,len);
		  else {
			  int size = len - str.length();
			  char [] chars = new char[size];
			  for (int i=0;i<size;i++)
				  chars[i]=padChar;
			  if (rpad)
				  return str.concat(String.valueOf(chars));
			  else
				  return String.valueOf(chars).concat(str);
		  }
	  }
	  
	  static public final String DEFAULT_LIST_SEPARATOR_CHAR = ",";
	  public static Collection<String> stringToCollection(Collection<String> c, String s, String separator, boolean trim) {
	        return stringToCollection(c, s, separator, trim, false);
	    }
	  public static Vector<String> string2Vector(String s, String separator, boolean trim) {
		  return (Vector<String>)stringToCollection(new Vector<String>(), s, separator, trim);
	  }
	  public static Vector<String> string2Vector(String s) {
		  return (Vector<String>) stringToCollection(new Vector<String>(), s, DEFAULT_LIST_SEPARATOR_CHAR, false);
	  }

	  /**
	     * Add the components of the string <code>s</code> to the collection
	     * <code>c</code>. Each component of the string is separated by the
	     * string <code>separator</code>.
	     *
	     * @param c
	     *            The collection to add the components to.
	     * @param s
	     *            The string that contains the components
	     * @param separator
	     *            The separator between components
	     * @param trim
	     *            True indicates that the components of the string should be
	     *            trimmed of leading and trailing whitespace.
	     * @param skipEmptyStrings
	     *            Do not include any empty strings in the resulting collection.
	     *             
	     * @return The collection <code>c</code> or null if there were some error
	     *         parsing the initial string <code>s</code>.
	     */
	    public static Collection<String> stringToCollection(Collection<String> c, String s, String separator, boolean trim, boolean skipEmptyStrings) {
	        try {
	            if(isEmpty(s)) {
	                return c;
	            }
	            int index;
	            int prevIndex=0;
	            String str;
	            while((index=s.indexOf(separator,prevIndex)) != -1) {
	                str = s.substring(prevIndex,index);
	                if (trim) {
	                    str =  str.trim();
	                }
	                prevIndex = index+separator.length();
	                
	                if(skipEmptyStrings && commonUTIL.isEmpty(str)) {
	                    continue;
	                }
	                c.add(str);
	            }
	            str = s.substring(prevIndex,s.length());
	                        
	            if (trim) {
	                str = str.trim();
	            }
	            
	            if(skipEmptyStrings && commonUTIL.isEmpty(str)) {
	                return c;
	            }
	            
	            c.add(str);
	            
	            return c;
	        } catch(Exception x) { 
	           commonUTIL.displayError("", "", x);
	        }
	        return null;
	    }
	    
	    
	public static String getOnlyDate(String settleDate) {
		// TODO Auto-generated method stub
		return settleDate.substring(0, 10).trim();
	}
	
	  /**
	   * Returns the String representation of a JDate using a given locale.
	   * <p>
	   * The DateFormat is LONG.
	   *
	   * @param jdate a JDate to convert
	   * @param loc   a Locale
	   */
	  public static String dateToLongString(Date date,Locale loc) {
		  if(date == null) return "";
		 
		  DateFormat dateformat=DateFormat.getDateInstance(DateFormat.LONG,
				  loc);
		  dateformat.setTimeZone(TimeZone.getDefault());
		  return dateformat.format(date);
	  }

	  /**
	   * Returns the String representation of a Date using a given Locale.
	   * <p>
	   * The DateFormat is SHORT for Date, and
	   * the DateFormat is MEDIUM for Time.
	   *
	   * @param date a Date to convert
	   * @param loc  a Locale
	   */
	  public static String timestampToString(Date date,Locale loc) {
		  if(date == null) return "";
		  DateFormat dateformat=
			  DateFormat.getDateTimeInstance(DateFormat.SHORT,
					  DateFormat.MEDIUM,
					  loc);
		  dateformat.setTimeZone(TimeZone.getDefault());
		  String s = dateformat.format(date);
		  //Now replace the characters which are non-
		  //standard to calypso
		  if (s != null && s.indexOf(".")>0 && s.indexOf(".")<10){
			  s = s.replace('.','/');
		  }
		  if (s != null && s.indexOf("-")>0 && s.indexOf("-")<10){
			  s = s.replace('-','/');
		  }
		  return s;
	  }


	 /**
	   * @param collection
	   * @return true if collection is not null and not empty
	   * @see java.util.Collection#isEmpty()
	   */
	  public static boolean isEmpty(Collection<?> collection) {
		  return (collection == null || collection.isEmpty());
	  }
/*
	 * Replaces a common from a string format number
	 * 
	 * @author yogesh
	 * @param replaceCommaInNumber
	 * @return double
	 */
	
	static public double replaceCommaInNumber( String stringNumber ) {
		
		//stringNumber.replaceAll("^\"|\"$", "");
		//stringNumber.replaceAll(",","");
		stringNumber.replaceAll(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)","");
		stringNumber.replaceAll("[^0-9.]", "");
		stringNumber.replaceAll(",","");
		System.out.println(stringNumber);
		return Double.parseDouble(stringNumber.trim());
	}
	/**
	 * <p>
	 * Escapes the HTML characters in {@code s} so that the string will be
	 * presented as is in an HTML renderer such as a web browser. The characters
	 * &amp;, &lt;, &gt; and &quot; are replaced by their HTML escape character
	 * equivalents of &amp;amp;, &amp;lt;, &amp;gt; and &amp;quot;.
	 * </p>
	 * <p>
	 * If s is null then null will be returned.
	 *
	 * @param s
	 *            the string to convert
	 * @return the string argument with the HTML specific characters replaced
	 *         with their HTML escape equivalents.
	 */
	  public static String htmlEncode(String s){
        return replaceCharacters(s, Arrays.asList('&', '<', '>', '"'), Arrays.asList("&amp;", "&lt;","&gt;", "&quot;"));
	  }

	  /**
	 * <p>
	 * Replace each occurrence of a character in {@code source} that is
	 * currently in {@code search} and replace the character with string in the
	 * same position in the {@code replacement} list.
	 * </p>
	 * <p>
	 * {@code source} may be null, in which case null will be returned. {@code
	 * search} and {@code replacement} must not be null and must be of the same
	 * length. The characters in the {@code source} should not contain
	 * duplicates. If there is a duplicate then the last occurrence will be
	 * used.
	 * </p>
	 * <p>
	 * The substituted strings are not re-searched for replacements. Only the
	 * original string, {@code source}, is used to finding characters to be
	 * replaced.
	 * </p>
	 * <p>
	 * The original string is untouched.
	 * </p>
	 *
	 * @param source
	 *            the string to replace characters in.
	 * @param search
	 *            the list of characters that should be replaced.
	 * @param replacement
	 *            the list of strings to replace characters contained in {@code
	 *            search}.
	 * @return The original string with each character that is contained in
	 *         {@code search} replaced by the text in {@code replacement} at the
	 *         corresponding position.
	 * @throws NullPointerException
	 *             if {@code search} or {@replacement} are null.
	 * @throws IllegalArgumentException
	 *             if the length of {@code search} is not the same as the length
	 *             of {@code replacement}.
	   */
    public static String replaceCharacters(String source, List<Character> search, List<String> replacement) {
    	if(search == null) {
    		throw new NullPointerException("Search set is null");
    	}
    	if(replacement == null) {
    		throw new NullPointerException("replacement set is null");
    	}
    	if(search.size() != replacement.size()) {
    		throw new IllegalArgumentException("Lengths of search and replacement sets are different");
    	}
    	if(source == null) {
			  return null;
    	}
    	Map<Character, Integer> candidates = new HashMap<Character, Integer>();
    	int index = 0;
    	for(Character c: search) {
    		candidates.put(c, Integer.valueOf(index++));
    	}
    	String[] replacementStrings = replacement.toArray(new String[replacement.size()]);
    	StringBuilder finalString = new StringBuilder();
    	for(char c: source.toCharArray()) {
//    		Character character = c;
    		Integer candidateIndex = candidates.get(c);
    		if(candidateIndex == null) {
    			finalString.append(c);
    		} else {
    			finalString.append(replacementStrings[candidateIndex.intValue()]);
    		}
    	}
    	return finalString.toString();
    }
    
    static public Properties getPropertiesFromFile(String fileName) {
    	return baseGetPropertiesFromFile(fileName);
    }
    /**
     * Load the properties in the property file attached to the Stream into
     * props. If any massaging has to happen then this will be done as well. For
     * instance only allowing Client Password when that is approved.
     *
     * @param stream Stream that is connected to a Property stream.
     * @param props The properties instance to add the properties in the stream
     *            to.
     * @throws IOException Thrown when there is an exception loading the
     *         properties from the stream.
     */
	 static private void loadPropertiesFromStream(InputStream stream,
                                                 Properties props) throws IOException {
        props.load(stream);

    }
    /**
     * Returns the login default settings that are stored in the specified Env
     * file.
     *
     * @param fileName the <code>String</code> name of the saved Env file
     * @return the requested <code>Properties</code> object
     */
	 static  public Properties baseGetPropertiesFromFile(String fileName) {
        Properties props =  new Properties();
        try {
            File file = new File(fileName);
            if(file.exists()) {
                InputStream stream= new FileInputStream(file);
                if(stream != null) {
                    loadPropertiesFromStream(stream, props);
                    try {
                        stream.close();
                    } catch(Exception ee){
                    }
                }
            }
        } catch (Exception ae) {
         //   Log.error("Defaults", ae);
        }
        return props;
    }
    /**
     * Saves the passed <code>Properties</code> object as a file, giving it
     * the specified name and recording a comment on it.
     *
     * @param p The <code>Properties</code> object to be saved as a file.
     * @param fileName The <code>String</code> name of the file to be saved.
     * @param comment The <code>String</code> comment to be saved in this
     *        file.
     */
    static public void saveToFile(Properties p, String fileName, String comment) {
        try {
         //   cipherDBPassword(p);
          //  cipherClientPassword(p);
         //   cipherCLSIOSwapPassword(p);
            File file = new File(fileName);
            FileOutputStream stream =new FileOutputStream(file);
            p.store(stream,comment);
        } catch (Exception e) {
         //   Log.error("Defaults", e);
        }
    }
	
	 static public String getUserDirName() {
		 createDir();
	        return getUserHome() + File.separator + "Cosmos";
	    }
	 static public void createDir() {
	        try{
	            File file = new File(getUserHome() + File.separator + "Cosmos");
	            if (file.exists())
	                return;
	            file.mkdir();
	            //Copy everything from Calypso dir to Calypso/releaseNumber
	            //Copy appstarter.* calypso.resources.*
	            //calypso_app_starter.*
	            //calypsouser.*
	        } catch (Exception e) {
	          //  Log.error("Defaults", e);
	        }
	    }

	
}
