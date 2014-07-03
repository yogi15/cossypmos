package util.common;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;



public class NumberFormatUtil {
	
	static Map<Locale, NumberFormat> _numberFormat            = new HashMap<Locale, NumberFormat>();
	static Map<Locale, NumberFormat> _numberFormatNoGrouping  = new HashMap<Locale, NumberFormat>();
	static Map<String, DecimalFormat> _decimalFormat           = new HashMap<String, DecimalFormat>();
	static Map<String, DecimalFormat> _decimalFormatNoGrouping = new HashMap<String, DecimalFormat>();
	static Map<String, DecimalFormat> _patternDecimalFormat    = new HashMap<String, DecimalFormat>();
	static public final int DECIMALS =  15;
	
	synchronized static NumberFormat getNumberFormat(Locale loc,
			boolean useGrouping) {
		NumberFormat f =
			useGrouping ? (NumberFormat)_numberFormat.get(loc) :
				(NumberFormat)_numberFormatNoGrouping.get(loc);
			if (f == null) {
				f = NumberFormat.getInstance(loc);
				f.setMaximumFractionDigits(DECIMALS);
				if (useGrouping) {
					_numberFormat.put(loc, f);
				}
				else {
					f.setGroupingUsed(false);
					_numberFormatNoGrouping.put(loc, f);
				}
			}
			return f;
	}
	
	 /**
	  * The maximum number of digits JVM can handle is limited to 15 (MAX_DEC_PLACES).
	  * The following code examines the total number of digits in
	  * the number and returns the appropriate number of decimals that
	  * JVM can reliably handle for this number.
	  *
	  * @param number
	  * @return the maximum number of decimals the JVM can handle for the given number.
	  */
	 static public int getMaxDecimals(double number) {
		 double absValue = Math.abs(number);
		 int decimals = 0;
		 if (absValue < 1 ) {
			 decimals = NumberRoundingMethod.MAX_DEC_PLACES;
		 } else if (absValue < 10 ) {
			 decimals = NumberRoundingMethod.MAX_DEC_PLACES - 1;
		 } else if (absValue < 100 ) {
			 decimals = NumberRoundingMethod.MAX_DEC_PLACES - 2;
		 } else if (absValue < 1000 ) {
			 decimals = NumberRoundingMethod.MAX_DEC_PLACES - 3;
		 } else if (absValue < 10000 ) {
			 decimals = NumberRoundingMethod.MAX_DEC_PLACES - 4;
		 } else if (absValue < 100000 ) {
			 decimals = NumberRoundingMethod.MAX_DEC_PLACES - 5;
		 } else if (absValue < 1000000 ) {
			 decimals = NumberRoundingMethod.MAX_DEC_PLACES - 6;
		 } else if (absValue < 10000000 ) {
			 decimals = NumberRoundingMethod.MAX_DEC_PLACES - 7;
		 } else if (absValue < 100000000 ) {
			 decimals = NumberRoundingMethod.MAX_DEC_PLACES - 8;
		 } else if (absValue < 1000000000 ) {
			 decimals = NumberRoundingMethod.MAX_DEC_PLACES - 9;
		 } else if (absValue < 10000000000.0 ) {
			 decimals = NumberRoundingMethod.MAX_DEC_PLACES - 10;
		 } else if (absValue < 100000000000.0 ) {
			 decimals = NumberRoundingMethod.MAX_DEC_PLACES - 11;
		 } else if (absValue < 1000000000000.0 ) {
			 decimals = NumberRoundingMethod.MAX_DEC_PLACES - 12;
		 } else if (absValue < 10000000000000.0 ) {
			 decimals = NumberRoundingMethod.MAX_DEC_PLACES - 13;
		 } else if (absValue < 100000000000000.0 ) {
			 decimals = NumberRoundingMethod.MAX_DEC_PLACES - 14;
		 }

		 if (decimals < 0) {
			 decimals = 0;
		 }
		 return decimals;
	 }
	 synchronized static DecimalFormat getDecimalFormat(int numberOfdec,
			 Locale loc,
			 boolean useGrouping) {

		 if (loc == null)
			 loc = Locale.getDefault();
		 DecimalFormat f=
			 useGrouping ?
					 (DecimalFormat)_decimalFormat.get(loc.toString()+numberOfdec) :
						 (DecimalFormat)_decimalFormatNoGrouping.get(loc.toString()+numberOfdec);
					 if (f == null) {
						 NumberFormat numberFormat = getNumberFormat(loc, useGrouping);
						 if (numberFormat instanceof DecimalFormat)
							 f = (DecimalFormat)numberFormat.clone();
						 else
							 f=new DecimalFormat();
						 f.setGroupingUsed(useGrouping);
						 f.setMinimumFractionDigits(numberOfdec);
						 f.setMaximumFractionDigits(numberOfdec);
						 if (useGrouping) {
							 _decimalFormat.put(loc.toString()+numberOfdec, f);
						 }
						 else {
							 _decimalFormatNoGrouping.put(loc.toString()+numberOfdec, f);
						 }
					 }
					 return f;
	 }

	 /**
		 * Returns the String representation (with 10 decimals) of a given double.
		 *
		 * @param rate a double to convert
		 */
		 static public String numberToString(double rate) {
			 return numberToString(rate, Locale.getDefault());
		 }
		 /**
		  * Returns the String representation (with a given
		  * number of decimals) of a given double.
		  *
		  * @param amt         a double to convert
		  * @param numberOfdec a number of decimals
		  */
		 static public String numberToString(double amt, int numberOfDec) {
			 return numberToString(amt, numberOfDec, null);
		 }
		 static public String numberToString(double amt, int numberOfdec, Locale locale) {
			 return numberToString(amt, numberOfdec, locale, true);
		 }
		 static public String numberToString(double amt, int numberOfdec,
				 Locale locale, boolean useGrouping) {
			 if( Double.isNaN(amt) ) {
				 return NULL_NUMBER_STRING;
			 }

			 if(numberOfdec < 0) return numberToString(amt);
			 DecimalFormat df = getDecimalFormat(numberOfdec, locale, useGrouping);
			 synchronized(df) {
				 return df.format(amt);
			 }
		 }

	 static public String numberToString(double rate, Locale locale) {
		 return numberToString(rate, locale, true);
	 }
	 static final String NULL_NUMBER_STRING = "";
	 static public String numberToString(double rate,
			 Locale locale,
			 boolean useGrouping) {

		 if( Double.isNaN(rate) ) {
			 return NULL_NUMBER_STRING;
		 }
		 NumberFormat f=getNumberFormat(locale, useGrouping);
		 synchronized(f) {
			 f.setMaximumFractionDigits(getMaxDecimals(rate));
			 return f.format(rate);
		 }
	 }
}
