package beans;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import dsServices.RemoteReferenceData;

import util.RemoteServiceUtil;
import util.commonUTIL;

public class HelperFXBean implements HelperBean {

	ArrayList<String> columnNames = new ArrayList<String>();
	ArrayList<String> tradeAttributes = new ArrayList<String>();
	
	@Override
	public Deal getBean(String[] record) {
		
		String attribute = "";
		DealFXBean dealBean = new DealFXBean();
		
		dealBean.setMemberName(record[0]);
		dealBean.setMember(record[1]);
		dealBean.setDEALERname(record[2]);
		dealBean.setDealer(record[3]);
		dealBean.setTradeNumber(record[4]);
		dealBean.setTradeDate(record[5]);
		dealBean.setTradeTime(record[6]);
		dealBean.setTradeType(record[7]);
		dealBean.setMaturityDate(record[8]);
		dealBean.setCurrency(record[9]);
		dealBean.setType(record[10]);
		dealBean.setCurrencyPair(record[11]);
		dealBean.setSpotPrice(record[12]);
		dealBean.setQuantity(record[13]);
		String ispositionB = record[15];
		if(ispositionB.equalsIgnoreCase("YES"))
		      dealBean.setisPositionBased(true);
		else 
			 dealBean.setisPositionBased(false);
		
		String feesType = "";
		if(!commonUTIL.isEmpty(record[16])) {
			feesType = record[16];
			FeesUploader fee = new FeesUploader();
			fee.setFeeType(feesType);
			fee.setCurrency(record[17]);
			fee.setAmount(commonUTIL.converStringToDouble(record[18]));
			fee.setFeeDate(record[20]);
			fee.setEndDate(record[20]);
			fee.setStartDate(record[20]);
			fee.setLeCode(record[23]);
			fee.setLeRole("BROKER");
			dealBean.setFees(fee);
		
		}
		
		if (record[7].equals("FXSWAP")) {
			
			dealBean.setFarLegAmt1(record[26]);
			dealBean.setFarLegAmt2(record[27]);
			dealBean.setFarLegRate(record[28]);
			dealBean.setFarDate(record[29]);
			
		}
		
		if (dealBean.TradeType.equals("FXTAKEUP")) {
			
			dealBean.setExternalParentID(record[25]);
		}
		
		for ( int i = 0; i < record.length; i++) {
			
			if (tradeAttributes.contains(columnNames.get(i))) {
								
				if ( attribute.length() != 0 ) {
					
					if (!record[i].equals("")) {
					
						attribute = new StringBuffer(attribute)
						   .append(columnNames.get(i))
						  .append("=")
						  .append(record[i])
						  .append(";")
						  .toString();
						
					}
									
				} else {
					
					if (!record[i].equals("")) {
						
						attribute =  new StringBuffer(columnNames.get(i))
						  .append("=")
						  .append(record[i])
						  .append(";")
						  .toString();
						
					}
					
						
				}
			
			}
		}
		
		dealBean.setAttributes(attribute);
		
		return dealBean;
		
	}


	@Override
	public void setColumnNames(ArrayList<String> columnNames) {
		
		Collection startUPData = null;
		
		this.columnNames = columnNames;
		
		if ( tradeAttributes.isEmpty() ) {
			
			RemoteReferenceData remoteRef = RemoteServiceUtil.getRemoteReferenceDataService();
			
			try {
				
				startUPData = remoteRef.getStartUPData("TradeAttribute");
			
			} catch (RemoteException e) {
				
				e.printStackTrace();
				
			}
			
			Iterator<StartUPData> it = startUPData.iterator();
			
			StartUPData data = null;
			
			while(it.hasNext()) {
			
				data = (StartUPData) it.next();
				tradeAttributes.add(data.getName());
			}
			
		}
		
		
		
	}

}
