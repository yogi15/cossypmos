package dsServices;

import java.rmi.RemoteException;
import java.util.Collection;

import dbSQL.EventSQL;
import dbSQL.dsSQL;
import dsEventProcessor.EventProcessor;

import beans.Event;

public class EventServiceImp implements RemoteEvent {

	/*@Override
	public boolean saveEvent(EventProcessor event) throws RemoteException {
		// TODO Auto-generated method stub
		return EventSQL.save(event, dsSQL.getConn());
	} */

	@Override
	public EventProcessor updateEvent(EventProcessor event) throws RemoteException {
		// TODO Auto-generated method stub
		return event;
	}

	@Override
	public EventProcessor getEventOnType(String type) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	
	private Collection getEventOnWhere(String type) throws RemoteException {
		// TODO Auto-generated method stub
		String sqlw= " subscriberlist not like  '%"+type+"%'  order by id asc "; // imp to get order by in asc to prcoess the old event first. 
		return EventSQL.selectWhere(sqlw, dsSQL.getConn());
	}

	@Override
	public Collection getEventNotProcessed(String managerName)
			throws RemoteException {
		// TODO Auto-generated method stub
		String sqlw= " subscriberlist not like  '%"+managerName+"%'  and adminclearMark not like  '%"+managerName+"%' order by eventID asc "; // imp to get order by in asc to prcoess the old event first. 
		
		return EventSQL.selectWhere(sqlw, dsSQL.getConn());
	}
	@Override
	public Collection getEventProcessed(String managerName)
			throws RemoteException {
		// TODO Auto-generated method stub
		String sqlw= " subscriberlist like  '%"+managerName+"%'  and adminclearMark not like  '%"+managerName+"%'  order by eventID asc "; // imp to get order by in asc to prcoess the old event first. 
		
		return EventSQL.selectWhere(sqlw, dsSQL.getConn());
	}

	@Override
	public void updateClearEvents(EventProcessor eventp) throws RemoteException {
		// TODO Auto-generated method stub
		EventSQL.update(eventp, dsSQL.getConn());
	}
}
