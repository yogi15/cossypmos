package apps.window.reportwindow.util;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import beans.StartUPData;

import util.ReferenceDataCache;
import util.commonUTIL;

public class ReportColumns {

	static DefaultMutableTreeNode parentTradeColumns = new DefaultMutableTreeNode("Trade");
	static DefaultMutableTreeNode parentProductColumns = new DefaultMutableTreeNode("Product");
	static DefaultMutableTreeNode parentCouponColumns = new DefaultMutableTreeNode("Coupon");
	
	static DefaultMutableTreeNode parentBookColumns = new DefaultMutableTreeNode("Book");
	static DefaultMutableTreeNode parentLEColumns = new DefaultMutableTreeNode("LegalEntity");
	static DefaultMutableTreeNode parentTransferColumns = new DefaultMutableTreeNode("Transfer");
	static DefaultMutableTreeNode parentPostingColumns = new DefaultMutableTreeNode("Posting");
	static DefaultMutableTreeNode parentOpenPosColumns = new DefaultMutableTreeNode("OpenPos");
	static DefaultMutableTreeNode parentCashpositionColumns = new DefaultMutableTreeNode("Cashposition");
	static DefaultMutableTreeNode parentTraderColumns = new DefaultMutableTreeNode("Trader");
	static DefaultMutableTreeNode parentPNLColumns = new DefaultMutableTreeNode("PNL");
	static Hashtable<String,String> columnsMaps = new Hashtable<String, String>();
	static Hashtable<String,Vector> childcolumnsMaps = new Hashtable<String, Vector>();
	static Hashtable<String,String> attributecolumnsMaps = new Hashtable<String, String>();
	static Hashtable<String,String> reversecolumnsMaps = new Hashtable<String, String>();
	
	static {
		
		
		Vector tradeAttribute = ReferenceDataCache.getStarupData("TradeAttribute");
		childcolumnsMaps.put("Trade.Attributes", tradeAttribute);
		
		for(int i=0;i<tradeAttribute.size();i++) {
			StartUPData startD = (StartUPData) tradeAttribute.get(i);
			attributecolumnsMaps.put("Trade.Attributes."+startD.getName(),"tradeAttribute."+startD.getName());
		}
		columnsMaps.put("Posting.PostingID","Id"); // to be changed
		reversecolumnsMaps.put("Posting.Id","Posting.PostingID");
		columnsMaps.put("Posting.TRADEID","tradeID");
		columnsMaps.put("Posting.TRANSFERID","Id"); // to be changed
		columnsMaps.put("Posting.CreditAmount","CreditAmount");
		columnsMaps.put("Posting.DebitAmount","DebitAmount");
		columnsMaps.put("Posting.AccEventType","AccEventType");
		columnsMaps.put("Posting.LinkId","LinkId");
		columnsMaps.put("Posting.EventType","EventType");
		columnsMaps.put("Posting.DebitAccId","(select accountname from account where id =Posting.DebitAccId) debitACC");
		columnsMaps.put("Posting.CreditAccId","CreditAccId");
		columnsMaps.put("Posting.RuleName","RuleName");
		columnsMaps.put("Posting.Currency","Currency");
		columnsMaps.put("PNL.RealisedPNL","RealisedPNL");
		
		
		
		columnsMaps.put("Posting.Type","Type");
		columnsMaps.put("Posting.Status","status");
		columnsMaps.put("Posting.BookingDate","BookingDate");
		columnsMaps.put("Posting.CreationDate","CreationDate");
		columnsMaps.put("Posting.EffectiveDate","EffectiveDate");
		columnsMaps.put("Posting.AccEventConfigID","AccEventConfigID");

		columnsMaps.put("Openpos.ProductSubType","ProductSubType");
		columnsMaps.put("Openpos.PositionID","PositionID");
		columnsMaps.put("Openpos.Settledate","Settledate");
		columnsMaps.put("Openpos.Price","Price");
		columnsMaps.put("Openpos.Sign","Sign");
		columnsMaps.put("Openpos.Quantity","Quantity");
		//columnsMaps.put("OpenPos.Quantity","Quantity");
		columnsMaps.put("Openpos.ProductType","ProductType");
		columnsMaps.put("Openpos.PrimaryCurr","PrimaryCurr");
		columnsMaps.put("Openpos.QuotingCurr","QuotingCurr");
		columnsMaps.put("Openpos.QuotingAmt","QuotingAmt");
			
		
		columnsMaps.put("Cashposition.ProductSubType","ProductSubType");
		columnsMaps.put("Cashposition.PositionID","PositionID");
		columnsMaps.put("Cashposition.Settledate","Settledate");
		columnsMaps.put("Cashposition.Price","Price");
		columnsMaps.put("Cashposition.Sign","Sign");
		columnsMaps.put("Cashposition.Quantity","Quantity");
		//columnsMaps.put("OpenPos.Quantity","Quantity");
		columnsMaps.put("Cashposition.ProductType","ProductType");
		columnsMaps.put("Cashposition.PrimaryCurr","PrimaryCurr");
		columnsMaps.put("Cashposition.QuotingCurr","QuotingCurr");
		columnsMaps.put("Cashposition.QuotingAmt","QuotingAmt");
		columnsMaps.put("Cashposition.Currency","Currency");
		columnsMaps.put("Cashposition.Type","Type");
		columnsMaps.put("Cashposition.ActualAmt","ActualAmt");
		
		
		columnsMaps.put("Transfer.TransferId","Id");// to be changed
		columnsMaps.put("Transfer.Amount","Amount");
		columnsMaps.put("Transfer.EventType","EventType");
		columnsMaps.put("Transfer.TransferType","TransferType");
		columnsMaps.put("Transfer.TransferStatus","TransferStatus");
		columnsMaps.put("Transfer.SettleCurrency","SettleCurrency");
		columnsMaps.put("Transfer.PayerCode","PayerCode");
		columnsMaps.put("Transfer.PayerRole","PayerRole");
		columnsMaps.put("Transfer.ReceiverCode","ReceiverCode");
		columnsMaps.put("Transfer.ReceiverRole","ReceiverRole");
		columnsMaps.put("Transfer.PaymentStatus","PaymentStatus");
		columnsMaps.put("Transfer.DeliveryDate","DeliveryDate");
		columnsMaps.put("Transfer.ValueDate","ValueDate");
		columnsMaps.put("Transfer.Method","Method");
		columnsMaps.put("Transfer.ReceiverInst","ReceiverInst");
		columnsMaps.put("Transfer.Action","Action");
		columnsMaps.put("Transfer.Status","Status");
		columnsMaps.put("Transfer.PayerInst","PayerInst");
		columnsMaps.put("Transfer.LinkID","LinkID");
	//	columnsMaps.put("Transfer.ATTRIBUTES","Attributes");
		columnsMaps.put("Transfer.UserID","UserID"); // to be changed
		columnsMaps.put("Transfer.cpid","cpid");// to be changed
		columnsMaps.put("Transfer.ProdcutType","ProdcutType");
		columnsMaps.put("Transfer.NetterdTransferID","NetterdTransferID");
		columnsMaps.put("Transfer.NettedConfigID","NettedConfigID");
		columnsMaps.put("Transfer.SettleAmount","SettleAmount");
		columnsMaps.put("Transfer.ProductID","ProductID");
	//	columnsMaps.put("Transfer.TransferId","id");
		columnsMaps.put("Transfer.TradeID","TradeID");
		
		
		
		columnsMaps.put("Trade.TradeId","id"); // to be changed
		 reversecolumnsMaps.put("Trade.id", "Trade.TradeId");
		columnsMaps.put("Trade.Currency","Currency");
		//columnsMaps.put("Trade.Currency","productId "); 
	//	columnsMaps.put("Trade.CounterParty","cpID");
		columnsMaps.put("Trade.Status","Status");
	//	columnsMaps.put("Trade.Trader","traderid");
		columnsMaps.put("Trade.Type","Type");
		columnsMaps.put("Trade.TradeDate","TradeDate"); 
	    columnsMaps.put("Trade.Broker","BrokerID");
		columnsMaps.put("Trade.TradeAmount","TradeAmount"); 
		columnsMaps.put("Trade.EffectiveDate","EffectiveDate");
		columnsMaps.put("Trade.DeliveryDate","DeliveryDate");
	//	columnsMaps.put("Trade.Book","bookId ");
        columnsMaps.put("Trade.Nominal","Nominal ");
	    columnsMaps.put("Trade.Quantity","Quantity"); 
	    columnsMaps.put("Trade.Amount2 ","Nominal");
	    columnsMaps.put("Trade.Amount1","Quantity"); 
		columnsMaps.put("Trade.User","userid ");  // to be changed
		columnsMaps.put("Trade.Price","Price");
		columnsMaps.put("Trade.Version","Version"); 
		columnsMaps.put("Trade.Attributes","Attributes");
		columnsMaps.put("Trade.Yield","Yield ");
		reversecolumnsMaps.put("Trade.tradedesc", "Trade.TradeProductName1");
		columnsMaps.put("Trade.TradeProductName1","tradedesc"); // to be changed
		columnsMaps.put("Trade.TradeProductName2","tradedesc1");// to be changed
		reversecolumnsMaps.put("Trade.tradedesc1", "Trade.TradeProductName2");
	//	columnsMaps.put("Trade.Trader","traderID ");
		columnsMaps.put("Trade.Action","Action ");
		columnsMaps.put("Trade.ProductType","ProductType");
	//	columnsMaps.put("Product.ID","id");
				columnsMaps.put("Product.productType ","productType");
						columnsMaps.put("Product.productname","productname");
		columnsMaps.put("Product.productShortName ","productShortName");
		columnsMaps.put("Product.quantity","quantity");
		columnsMaps.put("Product.issueDate","issueDate");
		columnsMaps.put("Product.marturityDate","marturityDate");
		columnsMaps.put("Product.IssuerId","IssuerId");
		columnsMaps.put("Product.Country","Country");
		columnsMaps.put("Product.IssuePrice ","IssuePrice");
		columnsMaps.put("Product.IssueCurrency","IssueCurrency");
		columnsMaps.put("Product.RedemptionPrice","RedemptionPrice");
		columnsMaps.put("Product.RedemptionCurrency","RedemptionCurrency");
		columnsMaps.put("Product.FaceValue","FaceValue");
		columnsMaps.put("Product.Code","code");
		columnsMaps.put("Product.Dateddate","dateddate");
		columnsMaps.put("Product.Tenor","Tenor");
		columnsMaps.put("Product.Attributes","Attributes");
	//	columnsMaps.put("Product.id number(30),
	//	columnsMaps.put("Product.productId number(30),
		columnsMaps.put("Coupon.CouponType","couponType");
		columnsMaps.put("Coupon.FixedRate","FixedRate");
		columnsMaps.put("Coupon.CCY","CCY");
		columnsMaps.put("Coupon.DayCount ","DayCount");
		columnsMaps.put("Coupon.CouponAdjustMentMethod ","CouponAdjustMentMethod");
		columnsMaps.put("Coupon.CouponFrequency","CouponFrequency");
		columnsMaps.put("Coupon.BusinessDayConvention","BusinessDayConvention");
		columnsMaps.put("Coupon.CouponDate","CouponDate");
		columnsMaps.put("Coupon.Rating","Rating");
		columnsMaps.put("Coupon.Ex_dividend","Ex_dividend");
		columnsMaps.put("Coupon.RecordDays","recordDays");
		columnsMaps.put("Coupon.ShutDays","shutDays");
		columnsMaps.put("Coupon.AccrualDigits","AccrualDigits");
		columnsMaps.put("Coupon.PriceDecimals","PriceDecimals");
		columnsMaps.put("Coupon.YieldDecimals","YieldDecimals");
		columnsMaps.put("Coupon.NominalDecimals ","NominalDecimals");
		columnsMaps.put("Coupon.AnnounceDate","AnnounceDate");
		columnsMaps.put("Coupon.AuctionDate","AuctionDate");
		columnsMaps.put("Coupon.WithholdingTax","WithholdingTax");
		columnsMaps.put("Coupon.ApplyWithholdingTax","ApplyWithholdingTax");
		columnsMaps.put("Coupon.WhenIssueBond","WhenIssueBond");
		columnsMaps.put("Coupon.TickSize ","TickSize");
		columnsMaps.put("Coupon.YieldMethod","YieldMethod");
		columnsMaps.put("Coupon.QuoteType","QuoteType");
		columnsMaps.put("Coupon.Activefrom","Activefrom");
	//	columnsMaps.put("Book.No","bookno");
	    columnsMaps.put("Book.BookName","book_name"); // to be changed
	    reversecolumnsMaps.put("Book.book_name", "Book.BookName");
	//	columnsMaps.put("LegalEntity.id","id");
		columnsMaps.put("LegalEntity.Name","Name");
		columnsMaps.put("LegalEntity.Role","Role");
													
		columnsMaps.put("Trader.Name","Name");
		columnsMaps.put("Trader.Role","Role");
		
	}
	
	public static String getKey(String value) {
		 
		  String val=  reversecolumnsMaps.get(value.trim());
		
		  return val;
		  
	}
	
	public static void addChildLeaf(DefaultMutableTreeNode leaf,String name) {
		
		Vector childData = childcolumnsMaps.get(name);
		if(!commonUTIL.isEmpty(childData)) {
		for(int i=0;i<childData.size();i++) {
			StartUPData data = (StartUPData) childData.get(i);
			DefaultMutableTreeNode childleaf = new DefaultMutableTreeNode(name+"."+data.getName());
			leaf.add(childleaf);
			
		}
		}
	
		

	}
	
	public void addChildNode(TreeNode node, Vector<StartUPData> childs,String name) {
		
	//	DefaultMutableTreeNode leaf = new DefaultMutableTreeNode(key.substring("Trade.".length(),key.length()));;
		
	}
	public  DefaultMutableTreeNode getTreeNodes(boolean showOpenPosition) {
		buildTreeNodes(showOpenPosition);
		 DefaultMutableTreeNode _root = new  DefaultMutableTreeNode("Columns");
		
		 System.out.println(parentTradeColumns.getChildCount());
		 _root.add(parentTradeColumns);
		 _root.add(parentProductColumns);
		 _root.add(parentCouponColumns);
		 _root.add(parentBookColumns);
		 _root.add(parentTraderColumns);
		 _root.add(parentLEColumns);
		 if(!showOpenPosition) {
		 _root.add(parentTransferColumns);
		 _root.add(parentPostingColumns);
		 }
		 if(showOpenPosition)
		 _root.add(parentCashpositionColumns);
		 _root.add(parentOpenPosColumns);
		 _root.add(parentPNLColumns);
		
		 
		 return _root;
	}
	
	public static void buildTreeNodes(boolean showOpenPosition) {
		DefaultMutableTreeNode leaf = null;
	  Set set = columnsMaps.entrySet();
	  Iterator enu = set.iterator();
	  while(enu.hasNext()) {
		  Entry k = (Entry)enu.next(); 
		String key = (String) k.getKey();
		  if(key.contains("Trade.")) {
			   leaf = new DefaultMutableTreeNode(key.substring("Trade.".length(),key.length()));
			    addChildLeaf(leaf,"Trade."+leaf.toString());
			 
			   parentTradeColumns.add(leaf);
		  } else if (key.contains("Product.")) {
			   leaf = new DefaultMutableTreeNode(key.substring("Product.".length(),key.length()));
			   parentProductColumns.add(leaf);
		  } else if (key.contains("Coupon.")) {
			   leaf = new DefaultMutableTreeNode(key.substring("Coupon.".length(),key.length()));
			   parentCouponColumns.add(leaf);
		  } else if (key.contains("Book.")) {
			   leaf = new DefaultMutableTreeNode(key.substring("Book.".length(),key.length()));
			   parentBookColumns.add(leaf);
		  }
		  else if (key.contains("LegalEntity.")) {
			   leaf = new DefaultMutableTreeNode(key.substring("LegalEntity.".length(),key.length()));
			   parentLEColumns.add(leaf);
		  }
		  else if (key.contains("Trader.")) {
			   leaf = new DefaultMutableTreeNode(key.substring("Trader.".length(),key.length()));
			   parentTraderColumns.add(leaf);
		  }
		  else if (key.contains("Transfer.")) {
			  if(!showOpenPosition) {
			   leaf = new DefaultMutableTreeNode(key.substring("Transfer.".length(),key.length()));
			   parentTransferColumns.add(leaf);
			  }
		  }
		  else if (key.contains("Posting.")) {
			  if(!showOpenPosition) {
			   leaf = new DefaultMutableTreeNode(key.substring("Posting.".length(),key.length()));
			   parentPostingColumns.add(leaf);
			  }
		  }
		
		  else if (key.contains("Cashposition.")) {
			  if(showOpenPosition) {
			   leaf = new DefaultMutableTreeNode(key.substring("Cashposition.".length(),key.length()));
			   parentCashpositionColumns.add(leaf);
			  }
		  }
		  else if (key.contains("OpenPos.")) {
			  if(showOpenPosition) {
			   leaf = new DefaultMutableTreeNode(key.substring("OpenPos.".length(),key.length()));
			   parentOpenPosColumns.add(leaf);
			  }
		  }
		  else if (key.contains("PNL.")) {
			  if(showOpenPosition) {
			   leaf = new DefaultMutableTreeNode(key.substring("PNL.".length(),key.length()));
			   parentPNLColumns.add(leaf);
			  }
		  }
	  }
	  
	  
	  
	}
//	Trade.Attributes.Genspec
	public static String getColumnName(String columnFromHash) {
		String columnName = "";
		String returnValue ="";
		if(columnFromHash.contains("Attribute")) {
			columnName = attributecolumnsMaps.get(columnFromHash.trim());
			returnValue= columnName;
		} else {
		if(columnsMaps.containsKey(columnFromHash.trim())) {
			columnName = columnsMaps.get(columnFromHash.trim());
			if(commonUTIL.isEmpty(columnName)) 
				columnName = reversecolumnsMaps.get(columnFromHash.trim());
		}
		returnValue= columnFromHash.substring(0,columnFromHash.indexOf(".")+1) + columnName;
	}
	
		return returnValue;
	}
	
}
