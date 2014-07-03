package dbSQL;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;

import util.commonUTIL;
import beans.Event;
import beans.Event;



public class EventSQL implements Serializable {

	final static private String tableName = "Event";
	final static private String DELETE =
		"DELETE FROM " + tableName + "   where name =? ";
	final static private String INSERT =
		"INSERT into " + tableName + "(eventID,eventType,type,tradeID,transferID,consumedFlag,sqlType) values(?,?,?,?,?,?,?)";
	final static private String UPDATE =
		"UPDATE " + tableName + " set type=?,typename=?,typevalue=?   where userid = ? ";
	final static private String SELECT_MAX =
		"SELECT MAX(id) DESC_ID FROM " + tableName + " ";
	final static private String SELECTALL =
		"SELECT eventID,eventType,type,tradeID,transferID,consumedFlag,sqlType " + tableName + " ";
	final static private String SELECT =
		"SELECT eventID,eventType,type,tradeID,transferID,consumedFlag,sqlType  FROM " + tableName + " where userid =  ? and type = ?";
	 static private String SELECTONE =
		"SELECT eventID,eventType,type,tradeID,transferID,consumedFlag,sqlType  FROM " + tableName + " where   userid =  ?  order by type ";
	 static private String CHECKINSERT  =
				"SELECT  eventID,eventType,type,tradeID,transferID,consumedFlag,sqlType   FROM " + tableName + " where userid =   ? and type = ? and typename = ?  order by type ";
	 static private String SELECTWHERE  =
				"SELECT eventID,eventType,type,tradeID,transferID,consumedFlag,sqlType   FROM " + tableName + " where " ;
	 
	 
	 public static boolean save(Event insertEvent, Connection con) {
		 try {
             return insert(insertEvent, con);
         }catch(Exception e) {
        	 commonUTIL.displayError("EventSQL","save",e);
        	 return false;
         }
	 }
	 public static boolean update(Event updateEvent, Connection con) {
		 try {
             return edit(updateEvent, con);
         }catch(Exception e) {
        	 commonUTIL.displayError("EventSQL","update",e);
        	 return false;
         }
	 }
	 
	 public static boolean delete(Event deleteEvent, Connection con) {
		 try {
             return remove(deleteEvent, con);
         }catch(Exception e) {
        	 commonUTIL.displayError("EventSQL","update",e);
        	 return false;
         }
	 }
	 
	 protected static boolean remove(Event deleteEvent, Connection con ) {
			
	        PreparedStatement stmt = null;
		 try {
			 int j = 1;
			 con.setAutoCommit(false);
			String  sql = " DELETE FROM Event   where id =" + deleteEvent.getEventID() ;
			    stmt = con.prepareStatement(sql);
			            stmt.execute(sql);
			  
			            
			             
			             con.commit();
			             commonUTIL.display("EventSQL ::  remove", sql);
			    
		 } catch (Exception e) {
			 commonUTIL.displayError("EventSQL","remove",e);
			 return false;
	           
	        }
	        finally {
	           try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				commonUTIL.displayError("EventSQL ","remove",e);
			}
	        }
	        return true;
	 }
	 public static Collection selectEvent(Event favorities, Connection con) {
		 try {
             return  select(favorities, con);
         }catch(Exception e) {
        	 commonUTIL.displayError("EventSQL ","select",e);
        	 return null;
         }
	 }
	 public static Collection selectALL(Connection con) {
		 try {
             return select(con);
         }catch(Exception e) {
        	 commonUTIL.displayError("EventSQL ","selectALL",e);
        	 return null;
         }
	 }
	 
	 protected static  boolean edit(Event updateEvent, Connection con ) {
		 
	        PreparedStatement stmt = null;
		 try {
			 int j = 1;
			 con.setAutoCommit(false);
			 stmt = dsSQL.newPreparedStatement(con, UPDATE);
	            
			
	           
	            
	       //     stmt.setString(1, updateEvent.getType());
	         //   stmt.setString(2, updateEvent.getTypeName());
	           // stmt.setString(3, updateEvent.getTypeValue());
	            //stmt.setInt(4, updateEvent.getUserId());
	            
	            
	            stmt.executeUpdate();
	            con.commit();
			 
		 } catch (Exception e) {
			 commonUTIL.displayError("EventSQL ",UPDATE,e);
			 return false;
	           
	        }
	        finally {
	           try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				commonUTIL.displayError("EventSQL",UPDATE,e);
			}
	        }
	        return true;
	 }
	
	 protected static boolean insert(Event inserEvent, Connection con ) {
			
	        PreparedStatement stmt = null;
		 try {
			 con.setAutoCommit(false);
		
			 int j = 1;
			 stmt = dsSQL.newPreparedStatement(con, INSERT);
			// eventID, eventType,type,tradeID,transferID,consumedFlag,sqlType	
			 stmt.setInt(1, inserEvent.getEventID());
			  stmt.setString(2, inserEvent.getEventType());
	            stmt.setString(3,inserEvent.getType());
	            stmt.setInt(4, inserEvent.getTradeID());
	            stmt.setInt(5, inserEvent.getTransferID());
	            if(!inserEvent.isConsumedFlag())
	               stmt.setInt(6,1);
	            stmt.setString(7, inserEvent.getSqlType());
	            commonUTIL.display(" EventSQL insert ",INSERT);
			 
	            stmt.executeUpdate();
	            con.commit();
			// }	
		 } catch (Exception e) {
			 commonUTIL.displayError("EventSQL",INSERT,e);
			 return false;
	           
	        }
	        finally {
	           try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				commonUTIL.displayError("EventSQL "," insert",e);
			}
	        }
	        return true;
	 }
	 
	 protected static Collection select(Event event,Connection con ) {
		 
		 int j = 0;
	        PreparedStatement stmt = null;
	       Vector<Event> values = new Vector<Event>();
	       String sql  = "";
		 try {
			 sql = SELECTONE + "'" + event.getEventID() + "'";
			con.setAutoCommit(false);
			 stmt = dsSQL.newPreparedStatement(con,sql) ;
	         
	         ResultSet rs = stmt.executeQuery();
	         
	         while(rs.next()) {
	        	// eventID, eventType,type,tradeID,transferID,consumedFlag,sqlType		
	        	 Event evt = new Event();
	        	 evt.setEventID(rs.getInt(1));
	        	 evt.setEventType(rs.getString(1));
	        	 evt.setType(rs.getString(1));
	        	 evt.setTradeID(rs.getInt(1));
	        	 evt.setTransferID(rs.getInt(1));
	        	 
	        
	        values.add(evt);
	        
	      
	       
	         
	         }
		 } catch (Exception e) {
			 commonUTIL.displayError("EventSQL",sql,e);
			 return values;
	           
	        }
	        finally {
	           try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				commonUTIL.displayError("EventSQL", sql,e);
			}
	        }
	        return values;
	 }

public static Collection selectWhere(String sqlw,Connection con ) {
		 
		 int j = 0;
	        PreparedStatement stmt = null;
	       Vector values = new Vector();
	       String sql  = "";
		 try {
			 sql = SELECTWHERE + sqlw + " order by type ";
			con.setAutoCommit(false);
			 stmt = dsSQL.newPreparedStatement(con,sql) ;
	         
	         ResultSet rs = stmt.executeQuery();
	         
	         while(rs.next()) {
	        	
	        	 Event evt = new Event();
	        	 evt.setEventID(rs.getInt(1));
	        	 evt.setEventType(rs.getString(1));
	        	 evt.setType(rs.getString(1));
	        	 evt.setTradeID(rs.getInt(1));
	        	 evt.setTransferID(rs.getInt(1));
	        	 
	        
	        values.add(evt);
	        
	      
	       
	         
	         }
	         commonUTIL.display("EventSQL selectWhere ",sql);
		 } catch (Exception e) {
			 commonUTIL.displayError("EventSQL selectWhere ",sql,e);
			 return values;
	           
	        }
	        finally {
	           try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				commonUTIL.displayError("EventSQL", sql,e);
			}
	        }
	        return values;
	 }

	 protected static Collection select(Connection con) { 
		 int j = 0;
	     PreparedStatement stmt = null;
	     Vector Events = new Vector();
	     
		 try {
			 con.setAutoCommit(false);
			 stmt = dsSQL.newPreparedStatement(con, SELECTALL );
	      
	      ResultSet rs = stmt.executeQuery();
	      
	      while(rs.next()) {
	    	  Event Event = new Event();
	    	
	    	  Event evt = new Event();
	        	 evt.setEventID(rs.getInt(1));
	        	 evt.setEventType(rs.getString(1));
	        	 evt.setType(rs.getString(1));
	        	 evt.setTradeID(rs.getInt(1));
	        	 evt.setTransferID(rs.getInt(1));
	        	 
	        
	        	 Events.add(evt);
	        
	      
	      }  commonUTIL.display("EventSQL ",SELECTALL);
	      
		 } catch (Exception e) {
			 commonUTIL.displayError("EventSQL",SELECTALL,e);
			 return Events;
	        
	     }
	     finally {
	        try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				commonUTIL.displayError("EventSQL ",SELECTALL,e);
			}
	     }
	     return Events;
	 }
	public static Collection getEventName(Connection con) {
		// TODO Auto-generated method stub
		int j = 0;
	     PreparedStatement stmt = null;
	     Vector Events = new Vector();
	     String sql = "select value from Event where name = "+ "'InitialData'";
		 try {
			 con.setAutoCommit(false);
			 stmt = dsSQL.newPreparedStatement(con, sql );
	      
	      ResultSet rs = stmt.executeQuery();
	      
	      while(rs.next()) {
	    	  Event Event = new Event();
	    	//  Event.setName(rs.getString(1));
		       
		        Events.add(Event);
	      
	      }
	      
	      commonUTIL.display("getEventName",sql);
		 } catch (Exception e) {
			 commonUTIL.displayError("getEventName",sql,e);
			 return Events;
	        
	     }
	     finally {
	        try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				commonUTIL.displayError("getEventName",sql,e);
			}
	     }
	     return Events;
	 
	}
	




}
