package bo.transfer.rule;

import java.rmi.RemoteException;
import java.util.Vector;

import dsServices.RemoteBOProcess;
import dsServices.RemoteProduct;
import dsServices.RemoteReferenceData;
import dsServices.RemoteTrade;

import beans.Book;
import beans.LegalEntity;
import beans.Sdi;
import beans.Trade;
import beans.TransferRule;

public abstract class ProductTransferRule {
	
	public RemoteTrade remoteTrade = null;
	public RemoteProduct remoteProduct = null;
	

	public RemoteBOProcess remoteBOProcess = null;
	public RemoteReferenceData refData = null;
	public Vector<Sdi> sdis = null;
	public Sdi agentSdi = null;
	public Sdi getAgentSdi() {
		Sdi po = getSdi("PO");
	    Sdi cp = getSdi("CounterParty");
	    try {
	    	agentSdi = 	refData.selectAgentSdi(cp.getCpId(),po.getPoId(),po.getsdiformat(),po.getCurrency(),po.getProducts());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return agentSdi;
	}

	public void setAgentID( Vector<Sdi> sdis ) {
		
	}


	public void setAgentSdi(Sdi agentSdi) {
		this.agentSdi = agentSdi;
	}

	public Sdi poSdi =null;
	
	 public Vector<Sdi> getSdis() {
		return sdis;
	}
	 
	
	 
	 
	public void setSdis(Vector<Sdi> sdis) {
		this.sdis = sdis;
	}
	 public Sdi getSdiOnEntity(int leid) {
		 return null;
	 }
	 
	 public Sdi getSdi(String role) {
		 Sdi sd = null;
		if(sdis ==  null || sdis.isEmpty()) 
			return null;
	
		 if((sdis != null) && (!sdis.isEmpty())) {
			 for(int i=0;i<sdis.size();i++) {
				 Sdi s = (Sdi) sdis.elementAt(i);
				 if(s.getRole().equalsIgnoreCase(role)) {
					 sd =s;
				     break;
				 }
			 }
			 
			 
			 
		 }
		 return sd;
			
	 }
	 public Sdi getSdi(int sdiID) {
		 Sdi sd = null;
		if(sdis ==  null || sdis.isEmpty()) 
			return null;
	
		 if((sdis != null) && (!sdis.isEmpty())) {
			 for(int i=0;i<sdis.size();i++) {
				 Sdi s = (Sdi) sdis.elementAt(i);
				 if(s.getId() == sdiID) {
					 sd =s;
				     break;
				 }
			 }
			 
			 
			 
		 }
		 return sd;
			
	 }
	
	public final String tradeTypeBUY = "BUY";
	 public final String tradeTypeSELL = "SELL";
	  public final String PAY = "PAY";
	 public final String RECEIVE = "RECEIVE";
	 public final String transerTYPEPRINCIPAL = "PRINCIPAL";
	 public final String transerTYPESECURITY = "SECURITY";
	 public final String transerTYPEINTEREST = "INTEREST";
	 public final String transerTYPEFEES = "_FEES";
	
	
	
	
	
	 public  abstract String getProductType();
	 public abstract Vector<TransferRule> getTransferRules(Vector v1);
	 public abstract Vector<TransferRule>  generateRules(Trade trade);
	 
	 
	 public RemoteProduct getRemoteProduct() {
			return remoteProduct;
		}
		public void setRemoteProduct(RemoteProduct remoteProduct) {
			this.remoteProduct = remoteProduct;
		}
	 
	 public RemoteTrade getRemoteTrade() {
			return remoteTrade;
		}
		public void setRemoteTrade(RemoteTrade remoteTrade) {
			this.remoteTrade = remoteTrade;
		}
		public RemoteBOProcess getRemoteBOProcess() {
			return remoteBOProcess;
		}
		public void setRemoteBOProcess(RemoteBOProcess remoteBOProcess) {
			this.remoteBOProcess = remoteBOProcess;
		}
		public RemoteReferenceData getRefDate() {
			return refData;
		}
		public void setRefDate(RemoteReferenceData refData) {
			this.refData = refData;
		}
		
		public LegalEntity getLegalEntity(int id) {
			LegalEntity le = null; 
			try {
				le =  (LegalEntity)  refData.selectLE(id);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return le;
		}
		
		public Book getBook(int id) {
			Book book = new Book(); 
			book.setBookno(id);
			try {
				book =  (Book)  refData.selectBook(book);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return book;
		}
		
}
