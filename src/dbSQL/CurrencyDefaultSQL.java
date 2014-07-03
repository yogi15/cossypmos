package dbSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;

import beans.CurrencyDefault;
import util.commonUTIL;

public class CurrencyDefaultSQL {
	
	
	
	final static private String DELETE_FROM_CurrrencyD =
			"DELETE FROM CURRENCYDEFAULT where currency_code =? ";
		final static private String INSERT_FROM_currencyDefault =
			"INSERT into CURRENCYDEFAULT(currencyDefaultno,le_id,currencyDefault_name) values(?,?,?)";
		final static private String UPDATE_FROM_currencyDefault =
				//currency_code,rounding,iso_code,country,default_holiday,spot_days,is_precious_metal_b,non_deliverable_b,rounding
			"UPDATE CURRENCY_DEFAULT set currency_code=?,rounding=?,iso_code=?,country=?,default_holidays=?,spot_days=?,is_precious_metal_b=?,non_deliverable_b=? where currency_code = ? ";
		final static private String SELECT_MAX =
			"SELECT MAX(currencyDefaultno) DESC_ID FROM CURRENCYDEFAULT ";
		final static private String SELECTALL =
			"SELECT currency_code,rounding,iso_code,country,default_holidays,spot_days,is_precious_metal_b,non_deliverable_b  FROM CURRENCYDEFAULT order by currency_code";
		final static private String SELECT =
			"SELECT currency_code,rounding,iso_code,country,default_holidays,spot_days,is_precious_metal_b,non_deliverable_b  FROM CURRENCYDEFAULT where currency_code =  ?";
		 static private String SELECTONE =
			"SELECT currency_code,rounding,iso_code,country,default_holidays,spot_days,is_precious_metal_b,non_deliverable_b FROM CURRENCYDEFAULT where currency_code =  " ;
		 

		 
		 
		 
		 public static boolean save(CurrencyDefault insertcurrencyDefault, Connection con) {
			 try {
	             return insert(insertcurrencyDefault, con);
	         }catch(Exception e) {
	        	 commonUTIL.displayError("currencyDefaultSQL","save",e);
	        	 return false;
	         }
		 }
		 public static boolean update(CurrencyDefault updatecurrencyDefault, Connection con) {
			 try {
	             return edit(updatecurrencyDefault, con);
	         }catch(Exception e) {
	        	 commonUTIL.displayError("currencyDefaultSQL","update",e);
	        	 return false;
	         }
		 }
		 
		 public static boolean delete(CurrencyDefault deletecurrencyDefault, Connection con) {
			 try {
	             return remove(deletecurrencyDefault, con);
	         }catch(Exception e) {
	        	 commonUTIL.displayError("currencyDefaultSQL","update",e);
	        	 return false;
	         }
		 }
		 public static CurrencyDefault selectcurrencyDefault(String  currency_code, Connection con) {
			 try {
	             return  select(currency_code, con);
	         }catch(Exception e) {
	        	 commonUTIL.displayError("currencyDefaultSQL","select",e);
	        	 return null;
	         }
		 }
		 public static Collection selectALL(Connection con) {
			 try {
	             return select(con);
	         }catch(Exception e) {
	        	 commonUTIL.displayError("currencyDefaultSQL","selectALL",e);
	        	 return null;
	         }
		 }
		 
		 public static int selectMaxID(Connection con) {
			 try {
	             return selectMax(con);
	         }catch(Exception e) {
	        	 commonUTIL.displayError("currencyDefaultSQL","selectMaxID",e);
	        	 return 0;
	         }
		 }
		 
		 protected static  boolean edit(CurrencyDefault updatecurrencyDefault, Connection con ) {
			 
		        PreparedStatement stmt = null;
			 try {
				 con.setAutoCommit(false);
				 int j = 1;
				 stmt = dsSQL.newPreparedStatement(con, UPDATE_FROM_currencyDefault);
		            
				
		           
				
			        
			        
		            stmt.setString(1, updatecurrencyDefault.getCurrency_code());
		            stmt.setInt(2, updatecurrencyDefault.getRounding());
		            stmt.setString(3, updatecurrencyDefault.getIso_code());
		            stmt.setString(4, updatecurrencyDefault.getCountry());
		            stmt.setString(5, updatecurrencyDefault.getDefault_holiday());
		            stmt.setInt(6, updatecurrencyDefault.getSpot_days());
		            stmt.setInt(7, updatecurrencyDefault.getIs_precious_metal_b());
		            stmt.setInt(8, updatecurrencyDefault.getNon_deliverable_b());
		            stmt.setString(9, updatecurrencyDefault.getCurrency_code());
		            
		            
		            stmt.executeUpdate();
				 con.commit();
			 } catch (Exception e) {
				 commonUTIL.displayError("currencyDefaultSQL","edit",e);
				 return false;
		           
		        }
		        finally {
		           try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					commonUTIL.displayError("currencyDefaultSQL","edit",e);
				}
		        }
		        return true;
		 }
		
		protected static boolean remove(CurrencyDefault deletecurrencyDefault, Connection con ) {
		
		        PreparedStatement stmt = null;
			 try {
				 int j = 1;
				 con.setAutoCommit(false);
				 stmt = dsSQL.newPreparedStatement(con, DELETE_FROM_CurrrencyD);
		            stmt.setString(j++, deletecurrencyDefault.getCurrency_code());
		           
		            stmt.executeUpdate();
				 con.commit();
			 } catch (Exception e) {
				 commonUTIL.displayError("currencyDefaultSQL","remove",e);
				 return false;
		           
		        }
		        finally {
		           try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					commonUTIL.displayError("currencyDefaultSQL","remove",e);
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
				 commonUTIL.displayError("currencyDefaultSQL",SELECT_MAX,e);
				 return j;
		           
		        }
		        finally {
		           try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					commonUTIL.displayError("currencyDefaultSQL",SELECT_MAX,e);
				}
		        }
		        return j;
		 }
		 
		 protected static boolean insert(CurrencyDefault insercurrencyDefault, Connection con ) {
				
		        PreparedStatement stmt = null;
			 try {
				 con.setAutoCommit(false);
				 int id = selectMax(con);
				 int j = 1;
		/*		 stmt = dsSQL.newPreparedStatement(con, INSERT_FROM_currencyDefault);
				 stmt.setInt(1,id+1);
		            stmt.setString(3, insercurrencyDefault.getcurrencyDefault_name());
		            stmt.setInt(2,insercurrencyDefault.getLe_id() );
		            
		            stmt.executeUpdate(); */
		            con.commit();
			 } catch (Exception e) {
				 commonUTIL.displayError("currencyDefaultSQL",INSERT_FROM_currencyDefault,e);
				 return false;
		           
		        }
		        finally {
		           try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					commonUTIL.displayError("currencyDefaultSQL",INSERT_FROM_currencyDefault,e);
				}
		        }
		        return true;
		 }
		 
		 protected static CurrencyDefault select(String currency_code,Connection con ) {
			 
			 int j = 0;
		        PreparedStatement stmt = null;
		        Vector currencyDefaults = new Vector();
		        CurrencyDefault CurrencyDefault = new CurrencyDefault();
			 try {
				 con.setAutoCommit(false);
				 stmt = dsSQL.newPreparedStatement(con, SELECTONE + "'"+ currency_code +"'");
		         
		         ResultSet rs = stmt.executeQuery();
		         
		         while(rs.next()) {
		        
		        CurrencyDefault.setCurrency_code(rs.getString(1));
		        CurrencyDefault.setRounding(rs.getInt(2));
		        CurrencyDefault.setIso_code(rs.getString(3));
		        CurrencyDefault.setCountry(rs.getString(4));
		        CurrencyDefault.setDefault_holiday(rs.getString(5));
		        CurrencyDefault.setSpot_days(rs.getInt(6));
		        CurrencyDefault.setIs_precious_metal_b(rs.getInt(7));
		        CurrencyDefault.setNon_deliverable_b(rs.getInt(8));
		       
		        
		      
		       return CurrencyDefault;
		         		         }
			 } catch (Exception e) {
				 commonUTIL.displayError("currencyDefaultSQL","select",e);
				 return CurrencyDefault;
		           
		        }
		        finally {
		           try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					commonUTIL.displayError("currencyDefaultSQL","selectMax",e);
				}
		        }
		        return CurrencyDefault;
		 }

		 protected static Collection select(Connection con) { 
			 int j = 0;
		     PreparedStatement stmt = null;
		     Vector currencyDefaults = new Vector();
		     
			 try {
				 con.setAutoCommit(false);
				 stmt = dsSQL.newPreparedStatement(con, SELECTALL );
		      
		      ResultSet rs = stmt.executeQuery();
		      
		      while(rs.next()) {
		   
		     CurrencyDefault CurrencyDefault = new CurrencyDefault();
		     CurrencyDefault.setCurrency_code(rs.getString(1));
		        CurrencyDefault.setRounding(rs.getInt(2));
		        CurrencyDefault.setIso_code(rs.getString(3));
		        CurrencyDefault.setCountry(rs.getString(4));
		        CurrencyDefault.setDefault_holiday(rs.getString(5));
		        CurrencyDefault.setSpot_days(rs.getInt(6));
		        CurrencyDefault.setIs_precious_metal_b(rs.getInt(7));
		        CurrencyDefault.setNon_deliverable_b(rs.getInt(8));
		        currencyDefaults.add(CurrencyDefault);
		      
		      }
			 } catch (Exception e) {
				 commonUTIL.displayError("currencyDefaultSQL",SELECTALL,e);
				 return currencyDefaults;
		        
		     }
		     finally {
		        try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					commonUTIL.displayError("currencyDefaultSQL",SELECTALL,e);
				}
		     }
		     return currencyDefaults;
		 }
		 protected static Collection selectcurrencyDefault(int currencyDefaultId,Connection con ) {
			 int j = 0;
		     PreparedStatement stmt = null;
		     Vector currencyDefaults = new Vector();
		     
			 try {
				 con.setAutoCommit(false);
				 SELECTONE = SELECTONE + "'" + currencyDefaultId +"'";
				 stmt = dsSQL.newPreparedStatement(con, SELECTONE );
		      
		      ResultSet rs = stmt.executeQuery();
		      
		      while(rs.next()) {
		    	  CurrencyDefault CurrencyDefault = new CurrencyDefault();
		    	  CurrencyDefault.setCurrency_code(rs.getString(1));
			        CurrencyDefault.setRounding(rs.getInt(2));
			        CurrencyDefault.setIso_code(rs.getString(3));
			        CurrencyDefault.setCountry(rs.getString(4));
			        CurrencyDefault.setDefault_holiday(rs.getString(5));
			        CurrencyDefault.setSpot_days(rs.getInt(6));
			        CurrencyDefault.setIs_precious_metal_b(rs.getInt(7));
			        CurrencyDefault.setNon_deliverable_b(rs.getInt(8));
			       
		      
		      }
			 } catch (Exception e) {
				 commonUTIL.displayError("currencyDefaultSQL","selectcurrencyDefault",e);
				 return currencyDefaults;
		        
		     }
		     finally {
		        try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					commonUTIL.displayError("currencyDefaultSQL","selectMax",e);
				}
		     }
		     return currencyDefaults;
		 }

		 
}
