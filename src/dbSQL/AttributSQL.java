package dbSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;

import beans.Attribute;
import beans.Book;
import util.commonUTIL;


public class AttributSQL {
	
	final static private String DELETE_FROM_ATTRIBUTE =
		"DELETE FROM ATTRIBUTE where id =? ";
	final static private String INSERT_FROM_ATTRIBUTE =
		"INSERT into ATTRIBUTE(id,attributeName ,type,attributeValue) values(?,?,?,?)";
	final static private String UPDATE_FROM_ATTRIBUTE =
		"UPDATE ATTRIBUTE set attributeName=?,type=?,attributeValue=? where id = ? ";
	final static private String SELECT_MAX =
		"SELECT MAX(id) DESC_ID FROM ATTRIBUTE ";
	final static private String SELECTALL =
		"SELECT id,attributeName ,type,attributeValue FROM ATTRIBUTE order by id";
	final static private String SELECT =
		"SELECT id,attributeName ,type,attributeValue FROM ATTRIBUTE where id =  ?";
	final static private String SELECTONE =
		"SELECT id,attributeName ,type,attributeValue FROM ATTRIBUTE where id =  " ;
	 
	final static private String SELECTWHERE =
		"SELECT id,attributeName ,type,attributeValue FROM ATTRIBUTE where    " ;
	 private static String getUpdateSQL(Attribute attribute) {
	      String updateSQL = "UPDATE ATTRIBUTE  set " +
	      		" id= " + attribute.getId() + 
	      		" ,type= '" + attribute.getType() + 	      		
	      		"' ,attributeName= '" + attribute.getName() + 	      		
	      		"' ,attributeValue= '" + attribute.getValue() + 	      		
	      		"'  where id= " + attribute.getId() +" and type = '"+attribute.getType()+"' and attributeName= '"+attribute.getName()+"'";
	      return updateSQL;
	     }
	 
	 public static boolean save(Attribute insertAttribute, Connection con) {
		 try {
             return insert(insertAttribute, con);
         }catch(Exception e) {
        	 commonUTIL.displayError("insertAttributeSQL","save",e);
        	 return false;
         }
	 }
	 public static boolean update(Attribute updateAttribute, Connection con) {
		 try {
             return edit(updateAttribute, con);
         }catch(Exception e) {
        	 commonUTIL.displayError("AttributeSQL","update",e);
        	 return false;
         }
	 }
	 
	 public static boolean delete(Attribute deleteAttribute, Connection con) {
		 try {
             return remove(deleteAttribute, con);
         }catch(Exception e) {
        	 commonUTIL.displayError("AttributeSQL","update",e);
        	 return false;
         }
	 }
	 public static Collection selectAttribute(int AttributeId, Connection con) {
		 try {
             return  select(AttributeId, con);
         }catch(Exception e) {
        	 commonUTIL.displayError("AttributeSQL","select",e);
        	 return null;
         }
	 }
	 public static Collection selectALL(Connection con) {
		 try {
             return select(con);
         }catch(Exception e) {
        	 commonUTIL.displayError("AttributeSQL","selectALL",e);
        	 return null;
         }
	 }
	 
	 public static int selectMaxID(Connection con) {
		 try {
             return selectMax(con);
         }catch(Exception e) {
        	 commonUTIL.displayError("AttributeSQL","selectMaxID",e);
        	 return 0;
         }
	 }
	 
	 protected static  boolean edit(Attribute updateAttribute, Connection con ) {
		 
		 PreparedStatement stmt = null;
	        String sql = getUpdateSQL(updateAttribute);
		 try {
			 con.setAutoCommit(false);
			 int j = 1;
			 stmt = dsSQL.newPreparedStatement(con, sql);
	            stmt.executeUpdate(sql);
			 con.commit();
			 commonUTIL.display("AttributeSQL ::  edit", sql);
		 } catch (Exception e) {
			 commonUTIL.displayError("AttributeSQL","edit  " + sql,e);
			 return false;
	           
	        }
	        finally {
	           try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				commonUTIL.displayError("AttributeSQL","edit",e);
			}
	        }
	        return true;
	 }
	
	protected static boolean remove(Attribute deleteAttribute, Connection con ) {
	
	        PreparedStatement stmt = null;
		 try {
			 int j = 1;
			 stmt = dsSQL.newPreparedStatement(con, DELETE_FROM_ATTRIBUTE);
	            stmt.setInt(j++, deleteAttribute.getId());
	           
	            stmt.executeUpdate();
			 
		 } catch (Exception e) {
			 commonUTIL.displayError("AttributeSQL","remove",e);
			 return false;
	           
	        }
	        finally {
	           try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				commonUTIL.displayError("AttributeSQL","remove",e);
			}
	        }
	        return true;
	 }

protected static int selectMax(Connection con ) {
		 
		 int j = 0;
	        PreparedStatement stmt = null;
		 try {
			
			 stmt = dsSQL.newPreparedStatement(con, SELECT_MAX);
	         
	         ResultSet rs = stmt.executeQuery();
	         while(rs.next())
	         j = rs.getInt("DESC_ID");
			 
		 } catch (Exception e) {
			 commonUTIL.displayError("AttributeSQL",SELECT_MAX,e);
			 return j;
	           
	        }
	        finally {
	           try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				commonUTIL.displayError("AttributeSQL",SELECT_MAX,e);
			}
	        }
	        return j;
	 }
	 
	 protected static boolean insert(Attribute inserAttribute, Connection con ) {
			
	        PreparedStatement stmt = null;
		 try {
			// int id = selectMax(con);
			 int j = 1;
			 con.setAutoCommit(false);
			 stmt = dsSQL.newPreparedStatement(con, INSERT_FROM_ATTRIBUTE);
			
			 stmt.setInt(1,inserAttribute.getId());
			 stmt.setString(2,inserAttribute.getName());
			 
	            stmt.setString(3, inserAttribute.getType());
	            stmt.setString(4,inserAttribute.getValue());
	            
	            stmt.executeUpdate();
	            con.commit();
	            commonUTIL.display("AttributeSQL","insert " + INSERT_FROM_ATTRIBUTE);
		 } catch (Exception e) {
			 commonUTIL.displayError("AttributeSQL",INSERT_FROM_ATTRIBUTE,e);
			 return false;
	           
	        }
	        finally {
	           try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				commonUTIL.displayError("bookSQL",INSERT_FROM_ATTRIBUTE,e);
			}
	        }
	        return true;
	 }
	 
	 protected static Collection select(int AttributeIn,Connection con ) {
		 
		 int j = 0;
	        PreparedStatement stmt = null;
	        Vector Attributes = new Vector();
	        Attribute Attribute = new Attribute();
		 try {
			
			 stmt = dsSQL.newPreparedStatement(con, SELECTONE + AttributeIn);
	         
	         ResultSet rs = stmt.executeQuery();
	        
	         while(rs.next()) {
	        
	        	 Attribute.setId(rs.getInt(1));
	        	 Attribute.setName(rs.getString(2));
	        	 Attribute.setType(rs.getString(3));
	        	 
	        	 Attribute.setValue(rs.getString(4));
	        
	      
	        Attributes.add(Attribute);
	         
	         }
	         commonUTIL.display("AttributeSQL","selectWhereClause "+SELECTONE);
	         return  Attributes;
	        
		 } catch (Exception e) {
			 commonUTIL.displayError("AttributeSQL","select",e);
			 return Attributes;
	           
	        }
	        finally {
	           try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				commonUTIL.displayError("AttributeSQL","selectMax",e);
			}
	        }
	       
	 }

	 protected static Collection select(Connection con) { 
		 int j = 0;
	     PreparedStatement stmt = null;
	     Vector Attributes = new Vector();
	     
		 try {
			
			 stmt = dsSQL.newPreparedStatement(con, SELECTALL );
	      
	      ResultSet rs = stmt.executeQuery();
	      
	      while(rs.next()) {
	   
	    	  Attribute Attribute = new Attribute();
	    	  Attribute.setId(rs.getInt(1));
	        	 Attribute.setName(rs.getString(2));
	        	 Attribute.setType(rs.getString(3));
	        	 
	        	 Attribute.setValue(rs.getString(4));
	        
	    	  Attributes.add(Attribute);
	      
	      }
	      commonUTIL.display("AttributeSQL","selectWhereClause ");
		 } catch (Exception e) {
			 commonUTIL.displayError("AttributeSQL",SELECTALL,e);
			 return Attributes;
	        
	     }
	     finally {
	        try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				commonUTIL.displayError("AttributeSQL",SELECTALL,e);
			}
	     }
	     return Attributes;
	 }
	 public static Collection selectWhereClaus(String sqlw,Connection con ) {
		 int j = 0;
	     PreparedStatement stmt = null;
	     Vector Attributes = new Vector();
	     String sql = "";
		 try {
			 sql = SELECTWHERE + sqlw;
			 stmt = dsSQL.newPreparedStatement(con, sql );
	      
	      ResultSet rs = stmt.executeQuery();
	      
	      while(rs.next()) {
	    	  Attribute Attribute = new Attribute();
	    	  Attribute.setId(rs.getInt(1));
	        	 Attribute.setName(rs.getString(2));
	        	 Attribute.setType(rs.getString(3));
	        	 
	        	 Attribute.setValue(rs.getString(4));
	        
	    	  Attributes.add(Attribute);
	    	  commonUTIL.display("AttributeSQL","selectWhereClause ");
	      }
		 } catch (Exception e) {
			 commonUTIL.displayError("AttributeSQL","selectWhereClause " + SELECTWHERE ,e);
			 return Attributes;
	        
	     }
	     finally {
	        try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				commonUTIL.displayError("AttributeSQL","selectMax",e);
			}
	     }
	     return Attributes;
	 }

}

