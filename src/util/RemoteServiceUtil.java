package util;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dsServices.Remote;
import dsServices.RemoteAccount;
import dsServices.RemoteBOProcess;
import dsServices.RemoteProduct;
import dsServices.RemoteReferenceData;
import dsServices.RemoteTask;
import dsServices.RemoteTrade;
import dsServices.ServerConnectionUtil;

public class RemoteServiceUtil {

	private static ServerConnectionUtil getServerConnection() {

		return ServerConnectionObjectUtil.getServerConnection();
	}

	public static RemoteReferenceData getRemoteReferenceDataService() {

		RemoteReferenceData referenceData = null;

		try {

			ServerConnectionUtil de = getServerConnection();

			referenceData = (RemoteReferenceData) de.getRMIService("ReferenceData");

		} catch (RemoteException e) {

			e.printStackTrace();

		}

		return referenceData;

	}
	
	
	public static RemoteTask getRemoteTaskService() {

		RemoteTask remoteTask = null;

		try {

			ServerConnectionUtil de = getServerConnection();

			remoteTask = (RemoteTask) de.getRMIService("Task");

		} catch (RemoteException e) {

			e.printStackTrace();

		}

		return remoteTask;

	}
	public static RemoteBOProcess getRemoteBOProcessService() {

		RemoteBOProcess remoteBOProcess = null;

		try {

			ServerConnectionUtil de = getServerConnection();

			remoteBOProcess = (RemoteBOProcess) de.getRMIService("BOProcess");

		} catch (RemoteException e) {

			e.printStackTrace();

		}

		return remoteBOProcess;

	}
	public static RemoteTrade getRemoteTradeService() {

		RemoteTrade remoteTrade = null;

		try {

			ServerConnectionUtil de = getServerConnection();

			remoteTrade = (RemoteTrade) de.getRMIService("Trade");

		} catch (RemoteException e) {

			e.printStackTrace();

		}

		return remoteTrade;

	}

	public static RemoteProduct getRemoteProductService() {

		RemoteProduct remoteProduct = null;

		try {

			ServerConnectionUtil de = getServerConnection();

			remoteProduct = (RemoteProduct) de.getRMIService("Product");

		} catch (RemoteException e) {

			e.printStackTrace();

		}

		return remoteProduct;

	}
	
	public static RemoteAccount getRemoteAccountService() {

		RemoteAccount remoteAccount = null;

		try {

			ServerConnectionUtil de = getServerConnection();

			remoteAccount = (RemoteAccount) de.getRMIService("Account");

		} catch (RemoteException e) {

			e.printStackTrace();

		}

		return remoteAccount;

	}
}

