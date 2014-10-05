package apps.window.reportwindow;

import java.rmi.RemoteException;
import java.util.Vector;

import javax.swing.JPanel;

import util.commonUTIL;

import dsServices.RemoteBOProcess;
import dsServices.RemoteReferenceData;
import dsServices.RemoteTask;
import dsServices.RemoteTrade;
import dsServices.ServerConnectionUtil;

import apps.window.operationwindow.jobpanl.FilterValues;
import beans.FilterBean;
import beans.UserJobsDetails;

public abstract class SearchCriteriaType extends JPanel
{
	public static ServerConnectionUtil de = null;
	RemoteReferenceData remoteBORef;
	RemoteTask remoteTask;
	RemoteTrade remoteTrade;
	RemoteBOProcess remoteBo;
	FilterValues filterValues = null;
     /**
	 * @return the filterValues
	 */
	public FilterValues getFilterValues() {
		return filterValues;
	}
	/**
	 * @param filterValues the filterValues to set
	 */
	
	public abstract Vector<FilterBean>  searchCriteria();
     public abstract  void clearllCriterial();
     public abstract void loadFilters(Vector<UserJobsDetails> jobdetails);
     
     public SearchCriteriaType() {
    	 de = ServerConnectionUtil.connect("localhost", 1099,
 				commonUTIL.getServerIP());

 		try {
 			remoteBORef = (RemoteReferenceData) de
 					.getRMIService("ReferenceData");
 			remoteTask = (RemoteTask) de.getRMIService("Task");
 			remoteTrade = (RemoteTrade) de.getRMIService("Trade");
 			remoteBo = (RemoteBOProcess) de.getRMIService("BOProcess");
 			filterValues = new FilterValues(remoteBORef, remoteTrade,remoteTask, remoteBo);
 		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			commonUTIL.displayError("JFrameNewReport ", " Constructor ", e);
		}
    	 
     }
     
     
}
