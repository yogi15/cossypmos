package apps.window.tradewindow.util;

import java.util.Vector;

import beans.CurrencySplitConfig;
import beans.Trade;

public class FXSplitUtil {
	
	  
	
	
	
	   public static Vector<Trade> splitTrade(Trade splitTrade1,Trade splitTrade2,Trade orginalTrade,CurrencySplitConfig sconfig,double firstRate,double secondRate)  {
		  
		   Vector<Trade> splitsTrade = new Vector<Trade>();
		   String primaryCurrecy = getPrimaryCurrency(orginalTrade.getTradedesc());
		   String quotingCurrecy = getQuotingCurrency(orginalTrade.getTradedesc());
		   splitTrade1.setPrice(firstRate);
		   splitTrade2.setPrice(secondRate);
		   splitTrade1.setCurrency(splitTrade1.getTradedesc().substring(4, 7));
		   splitTrade2.setCurrency(splitTrade2.getTradedesc().substring(4, 7));
		   splitTrade1.setAttribute("XccySplitFrom",  Integer.valueOf(orginalTrade.getId()).toString());
		   splitTrade2.setAttribute("XccySplitFrom",  Integer.valueOf(orginalTrade.getId()).toString());
		   splitTrade1.setAutoType("XccySplit");
		   splitTrade2.setAutoType("XccySplit");
		   splitTrade1.setXccySPlitid(orginalTrade.getId());
		   splitTrade2.setXccySPlitid(orginalTrade.getId());
		   double splitCurrencyValue = 0.0;
		   if(!isPrimaryCurrency(splitTrade1.getTradedesc(),primaryCurrecy)) {
			   if(orginalTrade.getType().equalsIgnoreCase("BUY")) { 
				   splitTrade1.setQuantity(orginalTrade.getQuantity() / firstRate);    // split Curr will be positive quantity 
				   splitCurrencyValue = splitTrade1.getQuantity();
				   splitTrade1.setNominal(orginalTrade.getQuantity() * -1);           // primary curre will be negitave 
				   splitTrade1.setType("BUY");
				   
			   } else {
				   splitTrade1.setQuantity(orginalTrade.getQuantity() / firstRate );  // 
				   splitCurrencyValue = splitTrade1.getQuantity();
				   splitTrade1.setNominal(orginalTrade.getQuantity() *-1 );          // primary currency will be positive as trade type is sell 
				   splitTrade1.setType("SELL");
			   }
		   }  else {
			   	if(orginalTrade.getType().equalsIgnoreCase("BUY")) { 
			   	 splitTrade1.setType("SELL");
			   	 splitTrade1.setQuantity(orginalTrade.getQuantity() *-1);
			  
				 splitTrade1.setNominal(orginalTrade.getQuantity() / firstRate  );
				 	splitCurrencyValue = splitTrade1.getNominal();
			   	
			   	} else {
			   		splitTrade1.setQuantity(orginalTrade.getQuantity() *  -1);
			   	
					   splitTrade1.setNominal(orginalTrade.getQuantity() / firstRate);
					   splitCurrencyValue = splitTrade1.getNominal();
					   splitTrade1.setType("BUY");
				   
			   			}
			   
		   }
		   
		   if(!isPrimaryCurrency(splitTrade2.getTradedesc(),quotingCurrecy)) {
			   if(orginalTrade.getType().equalsIgnoreCase("BUY")) { 
				   splitTrade2.setQuantity(splitCurrencyValue * -1);
				   splitTrade2.setNominal(orginalTrade.getQuantity() * secondRate );
				   splitTrade2.setType("SELL");
				   
			   } else {
				   splitTrade2.setQuantity(splitCurrencyValue * -1);
				   splitTrade2.setNominal(orginalTrade.getQuantity() * secondRate  );
				   splitTrade2.setType("BUY");
			   }
		   }  else {
			   	if(orginalTrade.getType().equalsIgnoreCase("BUY")) { 
			   	 splitTrade2.setType("SELL");
			   	 splitTrade2.setQuantity(orginalTrade.getQuantity() / firstRate *-1);
				 splitTrade2.setNominal(orginalTrade.getQuantity() );
			   	
			   	} else {
			   		splitTrade2.setQuantity(orginalTrade.getQuantity() / firstRate);
					   splitTrade2.setNominal(orginalTrade.getQuantity() * -1);
					   splitTrade2.setType("BUY");
				   
			   			}
			   
		   }
		   
		   splitsTrade.add(splitTrade1);
		   splitsTrade.add(splitTrade2);
		   return  splitsTrade;
		 
		   
	   }
	   
	   
	   public static boolean  isPrimaryCurrency(String currencyPair,String currency) {
		   if(currencyPair.substring(0, 3).equalsIgnoreCase(currency))
				   return true;
		   else 
			   return false;
		   
	   }
	   
	   public static String getPrimaryCurrency(String currencyPair) {
		   return currencyPair.substring(0, 3);
	   }
	   
	   public static String getQuotingCurrency(String currencyPair) {
		   return currencyPair.substring(4, 7);
	   }
}
