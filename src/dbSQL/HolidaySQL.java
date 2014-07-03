package dbSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;

import util.commonUTIL;
import beans.Holiday;

public class HolidaySQL {
	
	
	
	
	
	
	final static private String DELETE_FROM_HOLIDAY =
	"DELETE FROM HOLIDAY where Holidayno =? ";
final static private String INSERT_FROM_HOLIDAY =
	"INSERT into HOLIDAY(currency,country,hdate,fweekday,sweekday) values(?,?,?,?,?)";
final static private String UPDATE_FROM_HOLIDAY =
	"UPDATE HOLIDAY set currency=?,country=?,hdate=?,fweekday=?,sweekday=?  where currency = ? and country=?";
final static private String SELECT_MAX =
	"SELECT MAX(Holidayno) DESC_ID FROM HOLIDAY";
final static private String SELECTALL =
	"SELECT currency,country,hdate,fweekday,sweekday FROM HOLIDAY order by currency";
final static private String SELECT =
	"SELECT currency,country,hdate,fweekday,sweekday FROM HOLIDAY where currency = ? and country=?";
 static private String SELECTONE =
	"SELECT currency,country,hdate,fweekday,sweekday FROM HOLIDAY where  " ;
 
 
 
 
 public static boolean save(Holiday insertHoliday, Connection con) {
	 try {
         return insert(insertHoliday, con);
     }catch(Exception e) {
    	 commonUTIL.displayError("HolidaySQL","save",e);
    	 return false;
     }
 }
 public static boolean update(Holiday updateHoliday, Connection con) {
	 try {
         return edit(updateHoliday, con);
     }catch(Exception e) {
    	 commonUTIL.displayError("HolidaySQL","update",e);
    	 return false;
     }
 }
 
 public static boolean delete(Holiday deleteHoliday, Connection con) {
	 try {
         return remove(deleteHoliday, con);
     }catch(Exception e) {
    	 commonUTIL.displayError("HolidaySQL","update",e);
    	 return false;
     }
 }
 public static Holiday selectHoliday(String currency, Connection con) {
	 try {
         return  select(currency, con);
     }catch(Exception e) {
    	 commonUTIL.displayError("HolidaySQL","select",e);
    	 return null;
     }
 }
 public static Collection selectALL(Connection con) {
	 try {
         return select(con);
     }catch(Exception e) {
    	 commonUTIL.displayError("HolidaySQL","selectALL",e);
    	 return null;
     }
 }
 
 public static int selectMaxID(Connection con) {
	 try {
         return selectMax(con);
     }catch(Exception e) {
    	 commonUTIL.displayError("HolidaySQL","selectMaxID",e);
    	 return 0;
     }
 }
 
 protected static  boolean edit(Holiday updateHoliday, Connection con ) {
	 
        PreparedStatement stmt = null;
	 try {
		 con.setAutoCommit(false);
		 int j = 1;
		 stmt = dsSQL.newPreparedStatement(con, UPDATE_FROM_HOLIDAY);
            
		
           
            
		 stmt.setString(1,updateHoliday.getCurrency());
         stmt.setString(2, updateHoliday.getCountry());
         stmt.setString(3, updateHoliday.getHdate());
         stmt.setInt(4, updateHoliday.getFweekday());
         stmt.setInt(5, updateHoliday.getSweekdday());
         stmt.setString(6,updateHoliday.getCurrency());
         stmt.setString(7, updateHoliday.getCountry());
         
            stmt.executeUpdate();
		 con.commit();
	 } catch (Exception e) {
		 commonUTIL.displayError("HolidaySQL","edit  " +UPDATE_FROM_HOLIDAY,e);
		 return false;
           
        }
        finally {
           try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			commonUTIL.displayError("HolidaySQL","edit",e);
		}
        }
        return true;
 }

protected static boolean remove(Holiday deleteHoliday, Connection con ) {

        PreparedStatement stmt = null;
	 try {
		 int j = 1;
		 con.setAutoCommit(false);
		 //stmt = dsSQL.newPreparedStatement(con, DELETE_FROM_Holiday);
         //   stmt.setInt(j++, deleteHoliday.getHolidayno());
           
            stmt.executeUpdate();
		 con.commit();
	 } catch (Exception e) {
		 commonUTIL.displayError("HolidaySQL","remove",e);
		 return false;
           
        }
        finally {
           try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			commonUTIL.displayError("HolidaySQL","remove",e);
		}
        }
        return true;
 }

protected static int selectMax(Connection con ) {
	 
	 int j = 0;
        PreparedStatement stmt = null;
	 try {
		 con.setAutoCommit(false);
		 stmt = dsSQL.newPreparedStatement(con, SELECT_MAX);
         
         ResultSet rs = stmt.executeQuery();
         while(rs.next())
         j = rs.getInt("DESC_ID");
		 
	 } catch (Exception e) {
		 commonUTIL.displayError("HolidaySQL",SELECT_MAX,e);
		 return j;
           
        }
        finally {
           try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			commonUTIL.displayError("HolidaySQL",SELECT_MAX,e);
		}
        }
        return j;
 }
 
 protected static boolean insert(Holiday inserHoliday, Connection con ) {
		
        PreparedStatement stmt = null;
	 try {
		 con.setAutoCommit(false);
		 int id = selectMax(con);
		 int j = 1;
		 stmt = dsSQL.newPreparedStatement(con, INSERT_FROM_HOLIDAY);
		 stmt.setString(1,inserHoliday.getCurrency());
            stmt.setString(2, inserHoliday.getCountry());
            stmt.setString(3, inserHoliday.getHdate());
            stmt.setInt(4, inserHoliday.getFweekday());
            stmt.setInt(5, inserHoliday.getSweekdday());
            
           
            stmt.executeUpdate();
            con.commit();
	 } catch (Exception e) {
		 commonUTIL.displayError("HolidaySQL",INSERT_FROM_HOLIDAY,e);
		 return false;
           
        }
        finally {
           try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			commonUTIL.displayError("HolidaySQL",INSERT_FROM_HOLIDAY,e);
		}
        }
        return true;
 }
 
 protected static Holiday select(String currency,Connection con ) {
	 
	 int j = 0;
        PreparedStatement stmt = null;
        Vector Holidays = new Vector();
        Holiday Holiday = new Holiday();
        String sql = "";
	 try {
		 con.setAutoCommit(false);
		  sql = SELECTONE + " currency = '" + currency +"'";
		 stmt = dsSQL.newPreparedStatement(con,  sql);
         
         ResultSet rs = stmt.executeQuery();
         
         while(rs.next()) {
        
        	 Holiday.setCurrency(rs.getString(1));
 	        Holiday.setCountry(rs.getString(2));
 	        Holiday.setHdate(rs.getString(3));
 	        Holiday.setFweekday(rs.getInt(4));
 	       Holiday.setSweekdday(rs.getInt(5));
        
            return Holiday;
         
         }
	 } catch (Exception e) {
		 commonUTIL.displayError("HolidaySQL","select :: " + sql,e);
		 return Holiday;
           
        }
        finally {
           try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			commonUTIL.displayError("HolidaySQL","selectMax",e);
		}
        }
        return Holiday;
 }

 protected static Collection select(Connection con) { 
	 int j = 0;
     PreparedStatement stmt = null;
     Vector Holidays = new Vector();
     
	 try {
		 con.setAutoCommit(false);
		 stmt = dsSQL.newPreparedStatement(con, SELECTALL );
      
      ResultSet rs = stmt.executeQuery();
      
      while(rs.next()) {
   
     Holiday Holiday = new Holiday();

	 Holiday.setCurrency(rs.getString(1));
     Holiday.setCountry(rs.getString(2));
     Holiday.setHdate(rs.getString(3));
     Holiday.setFweekday(rs.getInt(4));
    Holiday.setSweekdday(rs.getInt(5));
        Holidays.add(Holiday);
      
      }
	 } catch (Exception e) {
		 commonUTIL.displayError("HolidaySQL",SELECTALL,e);
		 return Holidays;
        
     }
     finally {
        try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			commonUTIL.displayError("HolidaySQL",SELECTALL,e);
		}
     }
     return Holidays;
 }
 protected static Collection selectHoliday(int HolidayId,Connection con ) {
	 int j = 0;
     PreparedStatement stmt = null;
     Vector Holidays = new Vector();
     
	 try {
		 con.setAutoCommit(false);
		 SELECTONE = SELECTONE + HolidayId;
		 stmt = dsSQL.newPreparedStatement(con, SELECTONE );
      
      ResultSet rs = stmt.executeQuery();
      
      while(rs.next()) {
    	  Holiday Holiday = new Holiday();
    	

     	 Holiday.setCurrency(rs.getString(1));
	        Holiday.setCountry(rs.getString(2));
	        Holiday.setHdate(rs.getString(3));
	        Holiday.setFweekday(rs.getInt(4));
	       Holiday.setSweekdday(rs.getInt(5));
     Holidays.add(Holiday);
      
      }
	 } catch (Exception e) {
		 commonUTIL.displayError("HolidaySQL","selectHoliday",e);
		 return Holidays;
        
     }
     finally {
        try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			commonUTIL.displayError("HolidaySQL","selectMax",e);
		}
     }
     return Holidays;
 }

}



