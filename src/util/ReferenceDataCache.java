package util;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import constants.SDIConstants;
import dsServices.RemoteReferenceData;
import dsServices.ServerConnectionUtil;
import beans.Attribute;
import beans.Book;
import beans.Country;
import beans.LeContacts;
import beans.LegalEntity;
import beans.Message;
import beans.Sdi;
import beans.StartUPData;
import beans.Trade;
import beans.Transfer;

public class ReferenceDataCache {
	
	static public Hashtable <Integer,LegalEntity> leCache = new Hashtable <Integer,LegalEntity>();
	static public  Hashtable <Integer,Book> bookCache = new Hashtable <Integer,Book>();
	static public  Hashtable <String,Country> countryCache = new Hashtable  <String,Country>();
	static public Hashtable <Integer,Vector<LeContacts>> Lecontacts = new Hashtable <Integer,Vector<LeContacts>>();
	static public Hashtable <String,Vector<StartUPData>> startupData = new Hashtable <String,Vector<StartUPData>>();
	static public Hashtable <String,LegalEntity> leAliasCache = new Hashtable <String,LegalEntity>();
	static public Hashtable <String,LegalEntity> exchangeAliasCache = new Hashtable <String,LegalEntity>();
	static public Hashtable <Integer,Vector<Book>> POBookCache = new Hashtable <Integer,Vector<Book>>();

	static Hashtable<Integer,Vector<Sdi>> LegalEntitySdis  = new Hashtable<Integer,Vector<Sdi>>();
	public  static  ServerConnectionUtil de = null;
	static RemoteReferenceData  remoteBORef;

static public  ReferenceDataCache singleTonInstance;
		 
		
		
	


	public static ReferenceDataCache getSingleInstatnce() {
		if(singleTonInstance == null) {
			singleTonInstance = new  ReferenceDataCache();
			init();
		}
		return singleTonInstance;
	}
	
	
	public static void init() {
	
		try {
			de =ServerConnectionUtil.connect("localhost", 1099,commonUTIL.getServerIP() );
			remoteBORef = (RemoteReferenceData) de.getRMIService("ReferenceData");
			Vector LegalEntity = (Vector) remoteBORef.selectAllLs();
			Vector books = (Vector) remoteBORef.selectALLBooks();
			Vector countrys = (Vector) remoteBORef.selectALLCountry();
			Vector exchangeData = (Vector) remoteBORef.getALLExchanges();
	    		
	    	    cacheLE(LegalEntity,leCache);
	    	    cacheBooks(books,bookCache);
	    	    cacheCountrys(countrys,countryCache);
	    	    cacheExchangeData(exchangeData,exchangeAliasCache);
	    	    cacheLeContacts();
		 }catch(Exception e) {
	    		commonUTIL.displayError("ReferenceDataCache", "init", e);
    	 }
	    	//	remoteaccount = (RemoteAccount) de.getRMIService("Account");
	    	 
	}
	private static void cacheCountrys(Vector countrys,
			Hashtable<String, Country> countryCache2) {
		// TODO Auto-generated method stub
		if(!commonUTIL.isEmpty(countrys)) {
			for(int i =0;i<countrys.size();i++) {
				Country country = (Country) countrys.get(i);
				countryCache.put(country.getName(), country);
			}
		}
	}
	private static void cacheExchangeData(Vector exchangeLE,
			Hashtable<String, LegalEntity> exchange) {
		// TODO Auto-generated method stub
		if(!commonUTIL.isEmpty(exchangeLE)) {
			for(int i =0;i<exchangeLE.size();i++) {
				LegalEntity ex = (LegalEntity) exchangeLE.get(i);
				exchange.put(ex.getName().trim(), ex);
			}
		}
	}
    public static Country getCountry(String countryName) {
    	return countryCache.get(countryName);
    }
	
	
	private static void cacheLeContacts() {
		Enumeration<LegalEntity> enu = leCache.elements();
		while(enu.hasMoreElements()) {
			LegalEntity le =  enu.nextElement();
			try {
				Vector<LeContacts> leContacts = (Vector) remoteBORef.getLeContacts(le.getId());
				if(!commonUTIL.isEmpty(leContacts))
				Lecontacts.put(le.getId(),leContacts);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
	}
	private static void cacheBooks(Vector books, Hashtable<Integer, Book> bookCache2) {
		// TODO Auto-generated method stub
		if(!commonUTIL.isEmpty(books)) {
			for(int i =0;i<books.size();i++) {
				Book book = (Book) books.get(i);
				bookCache.put(book.getBookno(), book);
			}
		}
			
		
	}
	
	
	private static void cacheLE(Vector legalEntity,
			Hashtable<Integer, LegalEntity> leCache2) {
		// TODO Auto-generated method stub
		if(!commonUTIL.isEmpty(legalEntity)) {
			for(int i =0;i<legalEntity.size();i++) {
				LegalEntity le = (LegalEntity) legalEntity.get(i);
				//le.setAttributes(attributes)
				leCache.put(le.getId(), le);
				if(le.getAlias() != null)
				leAliasCache.put(le.getAlias(), le);
			}
		}
		
	}
	
	public static Vector<LeContacts> getLecContacts(int leid) {
		return Lecontacts.get(leid);
	}
	
	
	public static LegalEntity getExchangeLE(String exchangeName) {
		if(singleTonInstance == null) {
			singleTonInstance = new  ReferenceDataCache();
			init();
		}
		return exchangeAliasCache.get(exchangeName.trim());
	}
	public static  Book getBook(int bookID) {
		if(singleTonInstance == null) 
			   singleTonInstance = getSingleInstatnce();
		return bookCache.get(bookID);
		  
	}
	
	public  static LegalEntity  getParty(int leID) {
		return  leCache.get(leID);
		  
	}
	public static LegalEntity  getPO(int bookID) {
		if(singleTonInstance == null) 
			   singleTonInstance = getSingleInstatnce();
		
		Book book = bookCache.get(bookID);
		return  getLegalEntity(book.getLe_id());
		
	}
	public static Vector<Sdi> getSdi(int leid) {
		if(singleTonInstance == null) 
			   singleTonInstance = getSingleInstatnce();
		 Vector<Sdi> sdis = null;
		 sdis = LegalEntitySdis.get(leid);
		 if(sdis == null) {
			 try {
				sdis =  remoteBORef.getSDIONLegalEntity(leid);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				commonUTIL.displayError("StaticDataCache", "getSdi", e);
			}
			 LegalEntitySdis.put(leid, sdis);
		 }
		return sdis;
		
	}
	public static Vector<Sdi> getSdisonLegelEntityRole(String role,int leID,String currency,String productType) {
		Vector<Sdi> sdis = getSdi(leID);
		Vector<Sdi> sdifound = new Vector<Sdi>();
		  
		boolean pType = false;
		boolean curr = false;
		if(sdis == null)
			return null;
		for(int i=0;i<sdis.size();i++) {
			Sdi sdi = sdis.elementAt(i);
			if(sdi.getRole().equalsIgnoreCase(role)) {
				if(sdi.getProducts().contains(productType)) {
					pType = true;
				}
				if(sdi.getCurrency().contains(currency)) {
					curr = true;
				}
				if(pType && curr) {
					sdifound.add(sdi);
					pType = false;
					curr = false;
					continue;
					 
				} else if (pType && sdi.getCurrency().equalsIgnoreCase(SDIConstants.ANY)) {
					sdifound.add(sdi);
					pType = false;
					curr = false;
					continue;
					 
				}else if (curr && sdi.getProducts().equalsIgnoreCase(SDIConstants.ANY)) {
					sdifound.add(sdi);
					pType = false;
					curr = false;
					continue;
					 
				}else if (sdi.getCurrency().equalsIgnoreCase(SDIConstants.ANY) && sdi.getProducts().equalsIgnoreCase(SDIConstants.ANY)) {
					sdifound.add(sdi);
					 
				}
			}
		}
		return sdifound;
		
	}
	public static Vector<Sdi> getSdisonLegelEntityRole(String role,int leID,String currency,String messageType,String productType) {
		Vector<Sdi> sdis = getSdi(leID);
		Vector<Sdi> sdifound = new Vector<Sdi>();
		  
		boolean pType = false;
		boolean curr = false;
		boolean mess = false;
		if(sdis == null)
			return null;
		for(int i=0;i<sdis.size();i++) {
			Sdi sdi = sdis.elementAt(i);
			if(sdi.getRole().equalsIgnoreCase(role)) {
				if(sdi.getProducts().contains(productType)) {
					pType = true;
				}
				if(sdi.getCurrency().contains(currency)) {
					curr = true;
				}
				if(sdi.getMessageType().equalsIgnoreCase(messageType)) {
					mess = true;
				}
				if(pType && curr && mess) {
					sdifound.add(sdi);
					pType = false;
					curr = false;
					mess = false;
					continue;
					 
				} else if (pType && sdi.getCurrency().equalsIgnoreCase(SDIConstants.ANY) && mess) {
					sdifound.add(sdi);
					pType = false;
					curr = false;
					mess = false;
					continue;
					 
				}else if (curr && sdi.getProducts().equalsIgnoreCase(SDIConstants.ANY) && mess) {
					sdifound.add(sdi);
					pType = false;
					curr = false;
					mess = false;
					continue;
					 
				}else if (mess && sdi.getCurrency().equalsIgnoreCase(SDIConstants.ANY) && sdi.getProducts().equalsIgnoreCase(SDIConstants.ANY)) {
					sdifound.add(sdi);
					 
				}
			}
		}
		return sdifound;
		
	}
	public static Vector<StartUPData>  getStarupData(String name) {
		if(singleTonInstance == null) 
			   singleTonInstance = getSingleInstatnce();
		
		Vector<StartUPData> data = startupData.get(name);
		if(data == null) {
			try {
				data = (Vector) remoteBORef.getStartUPData(name);
				startupData.put(name,data);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return data;
		
	}
	         
	public static LegalEntity   getLegalEntity(int LeID) {
		if(singleTonInstance == null) 
			   singleTonInstance = getSingleInstatnce();
		return leCache.get(LeID);
	
	}
	public static LegalEntity   getLegalEntity(String LeAlias) {
		if(singleTonInstance == null) 
			   singleTonInstance = getSingleInstatnce();
		return leAliasCache.get(LeAlias);
	
	}
	
	
	public static Vector<Book> getALLPOBooks(int poId) {
		if(singleTonInstance == null) 
			   singleTonInstance = getSingleInstatnce();
		Vector<Book> poBooks = new Vector<Book>();
		poBooks = POBookCache.get(poId);
		if(commonUTIL.isEmpty(poBooks)) {
			String sql = " le_id = "+poId;
			try {
				poBooks = (Vector) remoteBORef.getBookWhere(sql);
				POBookCache.put(poId,poBooks);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return poBooks;
		
		
	}
	public static String getAttribute(int poid,int cpid,String attributeType,String cpRole) {
		return null;
	}
	
	public static LeContacts getLEContact(String role,String date,int leid,String productType,String contactType) {
		if(singleTonInstance == null)
			 singleTonInstance = getSingleInstatnce();
		Vector<LeContacts> lecontacts =  Lecontacts.get(leid);
		LeContacts contacts = null;
		if(lecontacts == null)
			return contacts;
		for(int i=0;i<lecontacts.size();i++) {
			LeContacts lecont = lecontacts.get(i);
			if(lecont.getLeRole().equalsIgnoreCase(role) && lecont.getProductType().equalsIgnoreCase(productType)&&lecont.getContactCategory().equalsIgnoreCase(contactType)) {
				
				contacts = lecont;
				break;
			}
			
		}
		return contacts;
	}

	public static Vector getAttributes(int leid) {
		// TODO Auto-generated method stub
		Vector<Attribute> attribuess= new Vector<Attribute>();
		LegalEntity le = getLegalEntity(leid);
		String attributes = le.getAttributes();
	
		if(attributes != null && attributes.length() > 0) {
    		String atttoken [] = attributes.trim().split(";"); 
    		
    		
    		
    		for(int i =0;i<atttoken.length;i++) {
				String att = (String) atttoken[i];
				if(att.contains("=")) {
						String attvalue = att.substring(att.indexOf('=')+1, att.length());
						String attnameName = att.substring(0, att.indexOf('='));
						Attribute attribues = new 	Attribute();
						attribues.setName(attnameName);
						attribues.setValue(attvalue);
						attribuess.add(attribues);
				  }
				}
		}
		return attribuess;
	}

	

	
	public static String getSenderMessageCode(String role,int leid,String productType,String addressType,String contactType) {
		if(singleTonInstance == null)
			 singleTonInstance = getSingleInstatnce();
		Vector<LeContacts> lecontacts =  Lecontacts.get(leid);
		if(lecontacts == null)
			return null;
		LeContacts contacts = null;
		
		for(int i=0;i<lecontacts.size();i++) {
			LeContacts lecont = lecontacts.get(i);
			if(lecont.getLeRole().equalsIgnoreCase(role) && lecont.getProductType().equalsIgnoreCase(productType) && lecont.getContactCategory().equalsIgnoreCase(contactType) ) {
				contacts = lecont;
				break;
			 }
		}
		if(contacts == null)
			 return null;
		return contacts.getAddressCode(addressType);
	}
	
	public static String getReceiverMessageCode(String role,int leid,String productType,String addressType,String contactType) {
		if(singleTonInstance == null)
			 singleTonInstance = getSingleInstatnce();
		Vector<LeContacts> lecontacts =  Lecontacts.get(leid);
		LeContacts contacts = null;
		for(int i=0;i<lecontacts.size();i++) {
			LeContacts lecont = lecontacts.get(i);
			if(lecont.getLeRole().equalsIgnoreCase(role) && lecont.getProductType().equalsIgnoreCase(productType) && lecont.getContactCategory().equalsIgnoreCase(contactType) ) {
				contacts = lecont;
				break;
			 }
		}
		return contacts.getAddressCode(addressType);
	}
	
	public static LeContacts getLEContact(String role, LegalEntity le,
			String leContacts, String productType, int poId, Trade trade,
			Transfer transfer, Message message, Connection ds, Object dbCon) {
		if(singleTonInstance == null)
			 singleTonInstance = getSingleInstatnce();
		// TODO Auto-generated method stub
		Vector<LeContacts> lecontacts =  Lecontacts.get(le.getId());
		LeContacts contacts = null;
		if(leContacts == null)
			return contacts;
		for(int i=0;i<lecontacts.size();i++) {
			LeContacts lecont = lecontacts.get(i);
			if(lecont.getLeRole().equalsIgnoreCase(role) && lecont.getProductType().equalsIgnoreCase(productType)&&lecont.getContactCategory().equalsIgnoreCase(leContacts)) {
				
				contacts = lecont;
				break;
			}
			
		}
		return contacts;
	}


	public static LeContacts getPartyContact(String role, String contactType,String messageType,String productType, int cpId) {
		// TODO Auto-generated method stub
		if(singleTonInstance == null)
			 singleTonInstance = getSingleInstatnce();
		Vector<LeContacts> lecontacts =  Lecontacts.get(cpId);
		LeContacts contacts = null;
		if(lecontacts == null)
			return contacts;
		for(int i=0;i<lecontacts.size();i++) {
			LeContacts lecont = lecontacts.get(i);
			if(lecont.getLeRole().equalsIgnoreCase(role) && lecont.getProductType().equalsIgnoreCase(productType) && lecont.getContactCategory().equalsIgnoreCase(contactType) ) {
				contacts = lecont;
				break;
			 }
		}
		return contacts;
	}


	public static String getSwiftValue(int cpId, String productType,
			String role, String messageType, String agentContact) {
		if(singleTonInstance == null)
			 singleTonInstance = getSingleInstatnce();
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				Vector<LeContacts> lecontacts =  Lecontacts.get(cpId);
				LeContacts contacts = null;
				if(lecontacts == null)
					return null;
				for(int i=0;i<lecontacts.size();i++) {
					LeContacts lecont = lecontacts.get(i);
					if(lecont.getLeRole().equalsIgnoreCase(role) && lecont.getProductType().equalsIgnoreCase(productType) && lecont.getContactCategory().equalsIgnoreCase(agentContact) ) {
						
						contacts = lecont;
						break;
					}
				}
				if(contacts == null)
					return null;
		return contacts.getSwift();
	}


	

	
}
