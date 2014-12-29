package bo.util;


import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Vector;

import constants.SDIConstants;

import util.commonUTIL;

import dbSQL.BookSQL;
import dbSQL.LegalEntitySQL;
import dbSQL.dsSQL;
import dsServices.RemoteReferenceData;

import apps.window.operationwindow.jobpanl.FilterValues;
import beans.Book;
import beans.LegalEntity;
import beans.Sdi;
import beans.Trade;

public class SDISelectorUtil {
	Vector<Sdi> poSDIs = null;
	FilterValues filterValues = null;
	static Hashtable <String,Vector<Sdi>> preferredSdis = new Hashtable<String,Vector<Sdi>> ();
	
	
	public static Vector<Sdi> selectSdi(int Leid,String role,RemoteReferenceData remoteRef) {
		Vector<Sdi> leSDIs = null;
		try {
				leSDIs = remoteRef.getSDIONLegalEntityRole(role,Leid);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return leSDIs;
		
	}
	
	public static Vector<Sdi> selectSdi(String Currency,String ProductType,int Leid,String role,RemoteReferenceData remoteRef) {
		Vector<Sdi> leSDIs = new Vector<Sdi>();
		try {
			String cpkey = createKey(role,Currency,ProductType,Leid);
			Vector<Sdi>	cpSDI = preferredSdis.get(cpkey);
			if(commonUTIL.isEmpty(cpSDI)) {
		      	cpSDI = remoteRef.getPreferredSDIONLegalEntityRoleCurrencyProduct(role,ProductType,Currency,Leid);
		      	if(!commonUTIL.isEmpty(cpSDI)) {
					preferredSdis.put(cpkey, cpSDI);
					
			}
			}
		      	leSDIs = 	getLeastPrioritySDIs(cpSDI);
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			commonUTIL.displayError("SDISelectionUtil", "selectSdiOntrade", e);
		}
		return leSDIs;
		
	}
	
	public static Vector<Vector<Sdi>> selectSdiOntrade(Trade trade,RemoteReferenceData remoteRef) {
		Vector<Vector<Sdi>>  tradeSDIs = new Vector<Vector<Sdi>>();
		
		if(remoteRef != null) {
		
			Book book =(Book)  BookSQL.selectBook(trade.getBookId(), dsSQL.getConn());
			try {
				String pokey = createKey(SDIConstants.PO,trade.getCurrency(),trade.getProductType(),book.getLe_id());
				Vector<Sdi>	poSDI = preferredSdis.get(pokey);
				if(commonUTIL.isEmpty(poSDI)) {
						poSDI = remoteRef.getPreferredSDIONLegalEntityRoleCurrencyProduct(SDIConstants.PO,trade.getProductType(),trade.getCurrency(),book.getLe_id());
							if(!commonUTIL.isEmpty(poSDI)) {
									preferredSdis.put(pokey, poSDI);
							}
				}
			String cpkey = createKey(SDIConstants.COUNTERPARY,trade.getCurrency(),trade.getProductType(),trade.getCpID());
			Vector<Sdi>	cpSDI = preferredSdis.get(cpkey);
			if(commonUTIL.isEmpty(cpSDI)) {
		      	cpSDI = remoteRef.getPreferredSDIONLegalEntityRoleCurrencyProduct(SDIConstants.COUNTERPARY,trade.getProductType(),trade.getCurrency(),trade.getCpID());
		      	if(!commonUTIL.isEmpty(cpSDI)) {
					preferredSdis.put(cpkey, cpSDI);
			}
			}
			
			tradeSDIs.add(0,getLeastPrioritySDIs(poSDI));
			tradeSDIs.add(1, getLeastPrioritySDIs(cpSDI));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
			    commonUTIL.displayError("SDISelectionUtil", "selectSdiOntrade", e);
			}
			
			
		}
		
		
		return tradeSDIs;
		
	}
	
	private static Vector<Sdi> getLeastPrioritySDIs(Vector<Sdi> preferredSdis) {
		int leastpriority = 0;
		Vector<Sdi> prioritySDIs = new Vector<Sdi>();
		for(int i=0;i<preferredSdis.size();i++) {
			Sdi sdi = preferredSdis.get(i);
			if(i == 0) {
				leastpriority   = sdi.getPriority();
			} 
			if(sdi.getPriority() < leastpriority) {
				leastpriority = sdi.getPriority();
			}
			
		}
		
		for(int i=0;i<preferredSdis.size();i++) {
			Sdi sdi = preferredSdis.get(i);
			if(sdi.getPriority() == leastpriority) 
				prioritySDIs.add(sdi);
			
		}
		return prioritySDIs;
	}
	
	
	public static Vector<Sdi> getPreferredSdisOnKey(String key) {
		return preferredSdis.get(key);
	}
	
	private static String createKey(String role,String currency,String productType,int leID) {
		return role+"|"+currency+"|"+productType+"|"+String.valueOf(leID);
	}
    private static void replaceExistingKeySDI(String key,RemoteReferenceData remoteref) {
       Sdi sdi = null;
       int indexID = 0;
    	Vector<Sdi>	sdis = preferredSdis.get(key);
		
		if(sdis != null) {	
			
			for(int i=0;i<sdis.size();i++) {
				Sdi s = sdis.get(i);
				indexID = i;
				if(!commonUTIL.isEmpty(s.getkey())) {
					sdi = s;
				    sdi.setkey("");
				    break;
				}
			}
			
			try {
				if(sdi != null) {
				Sdi existKeySDI = remoteref.updateSDI(sdi);
				sdis.remove(indexID);
				sdis.add(existKeySDI);
				preferredSdis.put(key,sdis);
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
    }
	public static void updateSDIPreferredKeys(Sdi sdi,RemoteReferenceData remoteref,String key) {
		// TODO Auto-generated method stub
		try {
			Sdi sdiupdate = remoteref.updateSDI(sdi);
			replaceExistingKeySDI(key,remoteref);
			
			int indextoUpdated = 0;
			int checkExistingKey = 0;
			
		Vector<Sdi>	sdis = preferredSdis.get(key);
		
		if(sdis != null) {
			
			for(int i=0;i<sdis.size();i++) {
				Sdi s = sdis.get(i);
				if(s.getId() == sdiupdate.getId()) {
					indextoUpdated = i;
					break;
				}
		   }
			sdis.remove(indextoUpdated);
			sdis.add(sdiupdate);
			preferredSdis.put(key, sdis);
		}
		
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
