package apps.window.positionwindow;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import util.ClassInstantiateUtil;
import util.commonUTIL;
import beans.StartUPData;
import beans.UserJob;
import beans.Users;
import dsServices.RemoteReferenceData;
import dsServices.RemoteTask;
import dsServices.RemoteTrade;
import dsServices.ServerConnectionUtil;
import dsServices.RemoteMO;

public class JFramePositionOLD extends JFrame  {
	
	 Users user = null;
	 PositionPanel positionPanel;
	 public static  ServerConnectionUtil de = null;
	 RemoteTrade remoteTrade;
	 RemoteMO remoteMO;
	 PositionFilterValues filterValues = null;
		Vector<StartUPData> searchCriteria;
		Vector<StartUPData> searchColumn;
		Vector<UserJob> jobs = null;
		String reportType = "Position";
		String columnSQL;
		RemoteTask remoteTask;
		RemoteReferenceData remoteBORef;
		
		
		public JFramePositionOLD(String name,Users user)  {
			super(name);
			setUser(user);
			de =ServerConnectionUtil.connect("localhost", 1099,commonUTIL.getServerIP() );
			
			try {
				remoteBORef = (RemoteReferenceData) de.getRMIService("ReferenceData");
				remoteTrade = (RemoteTrade)  de.getRMIService("Trade");
				remoteMO = (RemoteMO) de.getRMIService("MO");
				remoteTask = (RemoteTask) de.getRMIService("Task");
				searchCriteria = (Vector) remoteBORef.getStartUPData("SearchCriteria");
				 searchColumn = (Vector)  remoteBORef.getStartUPData("Position" + "Column");// understand which column to display on selected report
				 filterValues = new PositionFilterValues(remoteBORef,remoteTrade,remoteTask,null,remoteMO);
				 initComponents();
			}catch (RemoteException e) {
				// TODO Auto-generated catch block
				commonUTIL.displayError("JFramePosition ", " Constructor " , e);
			}
		}
		
		
		private void initComponents() {
			getUserTempleates(user.getId());
			UserJob job = jobs.elementAt(0);
			  Vector detailsJob = job.getDetailsJobs();
			  positionPanel = new PositionPanel("Position", searchCriteria, searchColumn, filterValues, job, detailsJob, remoteTask, remoteTrade,remoteMO, getUser());
			  add(positionPanel);
		}
		
		private Users getUser() {
			// TODO Auto-generated method stub
			return user;
		}


		private void saveJobs(Vector<UserJob> jobs2) {
			// TODO Auto-generated method stub
			if(jobs2 != null || jobs2.size() > 0) {
				for(int i=0;i<jobs2.size();i++) {
					UserJob job = jobs2.elementAt(i);
					
					try {
						job = (UserJob) remoteTask.saveUserJob(job);
						if(jobs == null) 
							this.jobs = new Vector<UserJob>();
						jobs.add(job);
						
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
				setJobs(jobs);
			}
			
		}
		private void setJobs(Vector<UserJob> jobs2) {
			// TODO Auto-generated method stub
			this.jobs = jobs2;
		}
		




		private void setUser(Users user2) {
			// TODO Auto-generated method stub
			this.user = user2;
			
		}
		
		private Vector<UserJob> getUserTempleates(int userid) {
			Vector<UserJob> jobs = null;
			try {
				jobs =	remoteTask.getUserJob(userid,"POSITION"); 
				if((jobs == null) || jobs.isEmpty()) {
					UserJob job = new UserJob();
					job.setId(0);
					job.setTabid(0);
					job.setUserID(user.getId());
					job.setType(reportType.toUpperCase());
					job.setTreeNodeName("DefaultTemplate");
					jobs = new Vector<UserJob>();
					jobs.add(job);
					saveJobs(jobs);
					
				}else {
					setJobs(jobs);
					}
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					commonUTIL.displayError("JFrameNewReport ", " getUserTempleates " , e);
					
				}
				return jobs;
			}

}