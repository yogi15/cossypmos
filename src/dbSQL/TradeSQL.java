package dbSQL;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;


import beans.Trade;
import util.commonUTIL;

public class TradeSQL {
 
 
 
 final static private String tableName = "Trade";
 final static private String DELETE =
  "update  " + tableName + " set version = -1  where id =? ";
 final static private String INSERT =
  "INSERT into " + tableName + "(id,productId,cpID,status,type,tradeDate,brokerID,TradeAmount,effectiveDate,deliverydate ,bookId,quantity,price,userid,version,currency,yield,attributes,tradedesc,traderID,nominal,action,tradedesc1,productType,amortization,mirrorID,parentid,autotype,secondeTradeprice,rollOverTo,rollOverFrom,rollBackTo,rollBackFrom,outstanding,isparitial,offsetid,xccySPlitid,mirrorBookid,b2bid,ispositionbased) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
 final static private String UPDATE =
  "UPDATE " + tableName + " set productId=?,cpID=?,status=?,type=?,tradeDate=?,brokerID=?,TradeAmount=?,effectiveDate=?,deliverydate=?,bookId=?,quantity=?  where id = ? ";
  
 final static private String SELECT_MAX =
  "SELECT TRADE_SEQ.NEXTVAL  DESC_ID FROM  dual";
 final static private String SELECTALL =
  "SELECT id,productId,cpID,status,type,tradeDate,brokerID,TradeAmount,effectiveDate, to_char(deliverydate, 'DD/MM/YYYY'),bookId,quantity,price,userid,version,currency,yield,attributes,tradedesc,traderID ,nominal,action,tradedesc1,productType,amortization,mirrorID,parentid,autotype,secondeTradeprice,rollOverTo,rollOverFrom,rollBackTo,rollBackFrom,outstanding,isparitial,offsetid,xccySPlitid,mirrorBookid,b2bid,ispositionbased FROM " + tableName + " where  version >= 0 ";
 final static private String SELECT =
  "SELECT id,productId,cpID,status,type,tradeDate,brokerID,TradeAmount,effectiveDate,to_char(deliverydate, 'DD/MM/YYYY') ,bookId,quantity,price,userid,version,currency,yield,attributes,tradedesc,traderID ,nominal,action,tradedesc1,productType,amortization,mirrorID,parentid,autotype,secondeTradeprice,rollOverTo,rollOverFrom,rollBackTo,rollBackFrom ,outstanding,isparitial,offsetid,xccySPlitid,mirrorBookid,b2bid,ispositionbased  FROM " + tableName + " where  version >= 0  and id =  ?";
  static private String SELECTONE =
  "SELECT id,productId,cpID,status,type,tradeDate,brokerID,TradeAmount,effectiveDate,to_char(deliverydate, 'DD/MM/YYYY') ,bookId,quantity,price,userid,version,currency,yield,attributes,tradedesc,traderID,nominal ,action,tradedesc1,productType,amortization,mirrorID,parentid,autotype,secondeTradeprice,rollOverTo,rollOverFrom,rollBackTo,rollBackFrom,outstanding,isparitial,offsetid,xccySPlitid ,mirrorBookid,b2bid,ispositionbased  FROM " + tableName + " where  version >= 0 and id =  ";

  final static private String ROLLOVERHIERARCHIES =
   "select id,productId,cpID,status,type,tradeDate,brokerID,TradeAmount,effectiveDate,to_char(deliverydate, 'DD/MM/YYYY') ,bookId,quantity,price,userid,version,currency,yield,attributes,tradedesc,traderID,nominal ,action,tradedesc1,productType,amortization,mirrorID,parentid,autotype,secondeTradeprice,rollOverTo,rollOverFrom,rollBackTo,rollBackFrom,outstanding,isparitial,offsetid,xccySPlitid,mirrorBookid,b2bid,ispositionbased  from trade start with id = ";
  final static private String ROLLBACKHIERARCHIES =
		   "select id,productId,cpID,status,type,tradeDate,brokerID,TradeAmount,effectiveDate,deliverydate ,bookId,quantity,price,userid,version,currency,yield,attributes,tradedesc,traderID,nominal ,action,tradedesc1,productType,amortization,mirrorID,parentid,autotype,secondeTradeprice,rollOverTo,rollOverFrom,rollBackTo,,outstanding,isparitial,offsetid,xccySPlitid,mirrorBookid,b2bid,ispositionbased  from trade start with id = ? connect by nocycle prior   id = rollbackfrom ";

  
  final static private String SELECTWHERE =
	  "SELECT id,productId,cpID,status,type,tradeDate,brokerID,TradeAmount,effectiveDate,to_char(deliverydate, 'DD/MM/YYYY') ,bookId,quantity,price,userid,version,currency,yield,attributes,tradedesc ,traderID,nominal,action,tradedesc1,productType,amortization,mirrorID,parentid,autotype,secondeTradeprice,rollOverTo,rollOverFrom,rollBackTo,rollBackFrom,outstanding,isparitial,offsetid,xccySPlitid ,mirrorBookid,b2bid,ispositionbased  FROM " + tableName + " where version >= 0 and ";
  final static private String SELECTXCCYSPLIT =
		  "SELECT id,productId,cpID,status,type,tradeDate,brokerID,TradeAmount,effectiveDate,to_char(deliverydate, 'DD/MM/YYYY') ,bookId,quantity,price,userid,version,currency,yield,attributes,tradedesc ,traderID,nominal,action,tradedesc1,productType,amortization,mirrorID,parentid,autotype,secondeTradeprice,rollOverTo,rollOverFrom,rollBackTo,rollBackFrom,outstanding,isparitial,offsetid,xccySPlitid,mirrorBookid,b2bid,ispositionbased   FROM " + tableName + " where version >= 0 and ";
	 
  final static private String SELECTOPEN = 
	 "select id,tradedesc from trade where version >= 0 and productType ='" ;
  
  private static String getUpdateSQL(Trade trade) {
      String updateSQL = "UPDATE  trade set  productId=" +trade.getProductId()+ ",type='"+ trade.getType().trim() +
       "',tradeDate=to_timestamp('" + trade.getTradeDate() +"', 'DD/MM/YYYY hh24:mi:ss'), status='" + trade.getStatus().trim() +"' ,cpID= " + trade.getCpID() + ",TradeAmount= " + trade.getTradeAmount() + 
       ",bookId=" +  trade.getBookId() + " ,quantity= " + trade.getQuantity() + " ,nominal= " + trade.getNominal() + " ,price=" + trade.getPrice() + 
       " ,effectiveDate='" + trade.getEffectiveDate() +
       "',currency='" + trade.getCurrency() +
       "',deliverydate =to_date('" + trade.getDelivertyDate() +"', 'DD/MM/YYYY hh24:mi:ss')"+
       " ,userID=" + trade.getUserID() +
       " ,version=" + (trade.getVersion() + 1) +
       " ,yield = " + trade.getYield() + 
       ",attributes  = '" + trade.getAttributes () + 
       "',tradedesc  = '" + trade.getTradedesc() + 
       "',tradedesc1  = '" + trade.getTradedesc1() + 
       "',action  = '" + trade.getAction() + 
       "',productType  = '" + trade.getProductType().trim() + 
       "',amortization  = '" + trade.getAmoritizationData() + 
       "',traderID = " + trade.getTraderID() + 
        ",parentID = " + trade.getParentID() + 
         ",mirrorID = " + trade.getMirrorID() + 
          ",autotype = '" + trade.getAutoType() + 
           "',secondeTradeprice = " + trade.getSecondPrice() + 
           ",rollOverTo = " + trade.getRollOverTo() + 
           ",rollOverFrom = " + trade.getRollOverFrom() + 
           ",rollBackTo = " + trade.getRollBackTo() + 
           ",rollBackfrom = " + trade.getRollBackFrom() + 
           ",outstanding = " + trade.getOutstanding() + 
      ",xccySPlitid = " + trade.getXccySPlitid() + 
      ",offsetid = " + trade.getOffsetid() +
      ",mirrorBookid = " + trade.getMirrorBookid() + 
      ",b2bid = " + trade.getMirrorBookid();
      
     if(trade.isParitial())
    	 updateSQL = updateSQL + "  ,isparitial = 1 ";
     else    
    	 updateSQL = updateSQL + "  ,isparitial = 0 ";
     updateSQL = updateSQL + "  where id = "+ trade.getId();
      return updateSQL;
     }
 
  public static int save(Trade insertTrade, Connection con) {
   try {
             return insert(insertTrade, con);
         }catch(Exception e) {
          commonUTIL.displayError("TradeSQL","save",e);
          return 0;
         }
  }
  public static boolean update(Trade updateTrade, Connection con) {
   try {
             return edit(updateTrade, con);
         }catch(Exception e) {
          commonUTIL.displayError("TradeSQL","update",e);
          return false;
         }
  }
 
  public static boolean delete(Trade deleteTrade, Connection con) {
   try {
             return remove(deleteTrade, con);
         }catch(Exception e) {
          commonUTIL.displayError("TradeSQL","update",e);
          return false;
         }
  }
 
  protected static boolean remove(Trade deleteTrade, Connection con ) {
   
         PreparedStatement stmt = null;
   try {
    int j = 1;
    con.setAutoCommit(false);
    stmt = dsSQL.newPreparedStatement(con, DELETE);
             stmt.setInt(j++, deleteTrade.getId());
           
             stmt.executeUpdate();
   con.commit();
   commonUTIL.display("TradeSQL","remove " +DELETE);
   } catch (Exception e) {
    commonUTIL.displayError("TradeSQL","remove",e);
    return false;
           
         }
         finally {
            try {
    stmt.close();
   } catch (SQLException e) {
    // TODO Auto-generated catch block
    commonUTIL.displayError("TradeSQL","remove",e);
   }
         }
         return true;
  }
 
 
  public static Trade select(int TradeId, Connection con) {
   try {
          return (Trade) selectTrade(TradeId, con);
      }catch(Exception e) {
       commonUTIL.displayError("TradeSQL","select",e);
       return null;
      }
  }
  public static Collection selectALL(Connection con) {
   try {
          return select(con);
      }catch(Exception e) {
       commonUTIL.displayError("TradeSQL","selectALL",e);
       return null;
      }
  }
  public static Collection selectwhere(String sql,Connection con) {
	   try {
	          return selectOnWherecClause(sql, con);
	      }catch(Exception e) {
	       commonUTIL.displayError("TradeSQL","selectwhere",e);
	       return null;
	      }
	  }
  public static Collection selectwhereforReports(String sql,Connection con) {
	   try {
	          return selectOnWherecClauseReports(sql, con);
	      }catch(Exception e) {
	       commonUTIL.displayError("TradeSQL","selectwhere",e);
	       return null;
	      }
	  }
  
public static int selectMaxID(Connection con) {
   try {
          return selectMax(con);
      }catch(Exception e) {
       commonUTIL.displayError("TradeSQL","selectMaxID",e);
       return 0;
      }
  }
 
  protected static  boolean edit(Trade updateTrade, Connection con ) {
  
         PreparedStatement stmt = null;
         String sql = "";
   try {
    
    int j = 1;
    con.setAutoCommit(false);
   
    sql = getUpdateSQL(updateTrade);
    stmt = con.prepareStatement(sql);
            stmt.executeUpdate(sql);
  
            
             
             con.commit();
             commonUTIL.display("TradeSQL ::  edit", sql);
    
            
   } catch (Exception e) {
    commonUTIL.displayError("TradeSQL","edit  "+sql,e);
    return false;
           
         }
         finally {
            try {
    stmt.close();
   } catch (SQLException e) {
    // TODO Auto-generated catch block
    commonUTIL.displayError("TradeSQL","edit",e);
   }
         }
         return true;
  }
 
 
protected static int selectMax(Connection con ) {
  
   int j = 0;
         PreparedStatement stmt = null;
   try {
    con.setAutoCommit(false);
    System.out.println(con.getAutoCommit());
    stmt = dsSQL.newPreparedStatement(con, SELECT_MAX);
    commonUTIL.display("TradeSQL ::  selectMax", SELECT_MAX);
          ResultSet rs = stmt.executeQuery();
          while(rs.next())
          j = rs.getInt("DESC_ID");
   
   } catch (Exception e) {
    commonUTIL.displayError("TradeSQL",SELECT_MAX,e);
    return j;
           
         }
         finally {
            try {
    stmt.close();
   } catch (SQLException e) {
    // TODO Auto-generated catch block
    commonUTIL.displayError("TradeSQL","selectMax",e);
   }
         }
         return j;
  }
     
      protected static int insert(Trade inserTrade, Connection con ) {
     
         PreparedStatement stmt = null;
         int tradeId=0;
   try {
    con.setAutoCommit(false);
   // System.out.println(con.getAutoCommit());
    Trade trade = selectTrade(inserTrade.getId(), con);
    if( (trade != null) && (trade.getId()) > 0) {
      update(inserTrade, con);
      tradeId = inserTrade.getId();
    } else {
     con.setAutoCommit(false);
    
     tradeId = selectMax(con) +1;
    
   //  System.out.println(con.getAutoCommit());
    int j = 1;
    stmt = dsSQL.newPreparedStatement(con, INSERT);
   
    stmt.setInt(1,tradeId);
    
    stmt.setInt(2, inserTrade.getProductId());
   
    stmt.setInt(3, inserTrade.getCpID());
    stmt.setString(4, inserTrade.getStatus());
    stmt.setString(5, inserTrade.getType());
    stmt.setTimestamp(6, commonUTIL.getStringToTimestamp(inserTrade.getTradeDate()));
    stmt.setInt(7, inserTrade.getBrokerID());
    stmt.setDouble(8, inserTrade.getTradeAmount());
    stmt.setString(9, inserTrade.getEffectiveDate());
    stmt.setTimestamp(10, commonUTIL.convertStringtoSQLTimeStamp(inserTrade.getDelivertyDate()));
    stmt.setInt(11, inserTrade.getBookId());
    stmt.setDouble(12, inserTrade.getQuantity());
    stmt.setDouble(13, inserTrade.getPrice());
    
    stmt.setInt(14, inserTrade.getUserID());
    stmt.setInt(15, 1);
    stmt.setString(16, inserTrade.getCurrency());
    stmt.setDouble(17, inserTrade.getYield());
    stmt.setString(18, inserTrade.getAttributes());
    stmt.setString(19, inserTrade.getTradedesc());
    stmt.setDouble(20, inserTrade.getTraderID());
    stmt.setDouble(21, inserTrade.getNominal());
    stmt.setString(22, inserTrade.getAction());
    stmt.setString(23, inserTrade.getTradedesc1());
    stmt.setString(24, inserTrade.getProductType().trim());
    stmt.setString(25, inserTrade.getAmoritizationData());
    stmt.setInt(26, inserTrade.getMirrorID());
    stmt.setInt(27, inserTrade.getParentID());
    stmt.setString(28, inserTrade.getAutoType());
    stmt.setDouble(29, inserTrade.getSecondPrice());
   
    stmt.setDouble(30, inserTrade.getRollOverTo());
    stmt.setDouble(31, inserTrade.getRollOverFrom());
    stmt.setDouble(32, inserTrade.getRollBackTo());
    stmt.setDouble(33, inserTrade.getRollBackFrom());
    stmt.setDouble(34, inserTrade.getOutstanding());
    if(inserTrade.isParitial())
      stmt.setDouble(35, 1);
    else 
   	 stmt.setDouble(35, 0);
    stmt.setInt(36, inserTrade.getOffsetid());
    stmt.setInt(37, inserTrade.getXccySPlitid());
    stmt.setInt(38, inserTrade.getMirrorBookid());
    stmt.setInt(39, inserTrade.getB2bid());
    if(inserTrade.isPositionBased()) {
    	 stmt.setString(40, "Y");
    } else {
    	 stmt.setString(40, "N");
    }

             stmt.executeUpdate();
             commonUTIL.display("TradeSQL ::  insert", INSERT);
             System.out.println(" trade " + tradeId + " status " + inserTrade.getStatus());
             con.commit();
    }
   
   } catch (Exception e) {
    commonUTIL.displayError("TradeSQL "," insert",e);
    return 0;
           
         }
         finally {
            try {
             if(stmt != null)
        stmt.close();
             
    
   } catch (SQLException e) {
    // TODO Auto-generated catch block
    commonUTIL.displayError("TradeSQL "," insert",e);
   }
         }
         return tradeId;
  }
 
  protected static Collection selectTradec(int TradeID,Connection con ) {
  
   int j = 0;
         PreparedStatement stmt = null;
         Vector Trades = new Vector();
        
   try {
    con.setAutoCommit(false);
  //  System.out.println(con.getAutoCommit());
    stmt = dsSQL.newPreparedStatement(con, SELECTONE + TradeID);
         
          ResultSet rs = stmt.executeQuery();
         
          while(rs.next()) {
           Trade Trade = new Trade();
           Trade.setId(rs.getInt(1));
           
           Trade.setProductId(rs.getInt(2));
           Trade.setCpID(rs.getInt(3));
           Trade.setStatus(rs.getString(4));
           Trade.setType(rs.getString(5));
         
           Trade.setTradeDate(commonUTIL.convertSQLDatetoString(rs.getTimestamp((6))));
           Trade.setBrokerID(rs.getInt(7))   ;
           Trade.setTradeAmount(rs.getDouble(8))  ;
           Trade.setEffectiveDate(rs.getString(9))  ;
           Trade.setDelivertyDate(rs.getString(10)) ;
           Trade.setBookId(rs.getInt(11))  ;
           Trade.setQuantity(rs.getDouble(12))  ;
           Trade.setPrice(rs.getDouble(13))  ;
           Trade.setUserID(rs.getInt(14))  ;
           Trade.setVersionID(rs.getInt(15))  ;
           Trade.setCurrency(rs.getString(16));
           Trade.setYield(rs.getDouble(17));
           Trade.setAttributes(rs.getString(18));
           Trade.setTradedesc(rs.getString(19));
           Trade.setTraderID(rs.getInt(20))  ;
           Trade.setNominal(rs.getDouble(21));
           Trade.setAction(rs.getString(22));
           Trade.setTradedesc1(rs.getString(23));
           Trade.setProductType(rs.getString(24));
           Trade.setAmoritizationData(rs.getString(25));
           Trade.setMirrorID(rs.getInt(26));
           Trade.setParentID(rs.getInt(27));
           Trade.setAutoType(rs.getString(28));
           Trade.setSecondPrice(rs.getDouble(29));
           Trade.setRollOverTo(rs.getInt(30));
           Trade.setRollOverFrom(rs.getInt(31));
           Trade.setRollBackTo(rs.getInt(32));
           Trade.setRollBackFrom(rs.getInt(33));
           Trade.setOutstanding(rs.getDouble(34));
          if( rs.getInt( 35) == 1)
        	  Trade.setParitial(true);
          
          Trade.setOffsetid(rs.getInt(36));
          Trade.setXccySPlitid(rs.getInt(37));
          Trade.setMirrorBookid(rs.getInt(38));
          Trade.setB2bid(rs.getInt(39));
          if(rs.getString(40).equalsIgnoreCase("N")) {
        	  Trade.setPositionBased(false);
         }
           Trades.add(Trade);
         
          }
        
          commonUTIL.display("TradeSQL  ",SELECTONE + TradeID);
   } catch (Exception e) {
    commonUTIL.displayError("TradeSQL",SELECTONE,e);
    return Trades;
           
         }
         finally {
            try {
    stmt.close();
   } catch (SQLException e) {
    // TODO Auto-generated catch block
    commonUTIL.displayError("TradeSQL ","selectMax",e);
   }
         }
         return Trades;
  }
 
  protected static Collection select(Connection con) {
   int j = 0;
      PreparedStatement stmt = null;
      Vector Trades = new Vector();
     
   try {
    con.setAutoCommit(false);
    System.out.println(con.getAutoCommit());
    stmt = dsSQL.newPreparedStatement(con, SELECTALL );
      
       ResultSet rs = stmt.executeQuery();
      
       while(rs.next()) {
        Trade Trade = new Trade();
      
        Trade.setId(rs.getInt(1));
         
          Trade.setProductId(rs.getInt(2));
          Trade.setCpID(rs.getInt(3));
          Trade.setStatus(rs.getString(4));
          Trade.setType(rs.getString(5));
        
          Trade.setTradeDate(commonUTIL.convertSQLDatetoString(rs.getTimestamp((6))));
          Trade.setBrokerID(rs.getInt(7))   ;
          Trade.setTradeAmount(rs.getDouble(8))  ;
          Trade.setEffectiveDate(rs.getString(9))  ;
          Trade.setDelivertyDate(rs.getString(10)) ;
          Trade.setBookId(rs.getInt(11))  ;
          Trade.setQuantity(rs.getDouble(12))  ;
          Trade.setPrice(rs.getDouble(13))  ;
          Trade.setUserID(rs.getInt(14))  ;
          Trade.setVersionID(rs.getInt(15))  ;
          Trade.setCurrency(rs.getString(16));
          Trade.setYield(rs.getDouble(17));
          Trade.setAttributes(rs.getString(18));
          Trade.setTradedesc(rs.getString(19));
         
          Trade.setTraderID(rs.getInt(20))  ;
          Trade.setNominal(rs.getDouble(21));
          Trade.setAction(rs.getString(22));
          Trade.setTradedesc1(rs.getString(23));
          Trade.setProductType(rs.getString(24));
          Trade.setAmoritizationData(rs.getString(25));
          Trade.setMirrorID(rs.getInt(26));
          Trade.setParentID(rs.getInt(27));
          Trade.setAutoType(rs.getString(28));
          Trade.setSecondPrice(rs.getDouble(29));
          Trade.setRollOverTo(rs.getInt(30));
          Trade.setRollOverFrom(rs.getInt(31));
          Trade.setRollBackTo(rs.getInt(32));
          Trade.setRollBackFrom(rs.getInt(33));
          Trade.setOutstanding(rs.getDouble(34));
          if( rs.getInt( 35) == 1)
        	  Trade.setParitial(true);
          Trade.setOffsetid(rs.getInt(36));
          Trade.setXccySPlitid(rs.getInt(37));
          Trade.setMirrorBookid(rs.getInt(38));
          Trade.setB2bid(rs.getInt(39));
          if(rs.getString(40).equalsIgnoreCase("N")) {
        	  Trade.setPositionBased(false);
         }
          Trades.add(Trade);
      
      
       }
   } catch (Exception e) {
    commonUTIL.displayError("TradeSQL",SELECTALL,e);
    return Trades;
        
      }
      finally {
         try {
    stmt.close();
   } catch (SQLException e) {
    // TODO Auto-generated catch block
    commonUTIL.displayError("TradeSQL","selectMax",e);
   }
      }
      return Trades;
  }
  

 
  protected static Trade selectTrade(int tradeId,Connection con ) {
   int j = 0;
      PreparedStatement stmt = null;
      Vector Trades = new Vector();
      Trade trade = new Trade();
      boolean recordExist = false;
   try {
    con.setAutoCommit(false);
    System.out.println(con.getAutoCommit());
   String sql = SELECTONE +tradeId;
    stmt = dsSQL.newPreparedStatement(con, sql);
      
       ResultSet rs = stmt.executeQuery();
      
       while(rs.next()) {
    	   recordExist = true;
    	   trade.setId(rs.getInt(1));
           
           trade.setProductId(rs.getInt(2));
           trade.setCpID(rs.getInt(3));
           trade.setStatus(rs.getString(4));
           trade.setType(rs.getString(5));
         
           trade.setTradeDate(commonUTIL.convertSQLDatetoString(rs.getTimestamp((6))));
           trade.setBrokerID(rs.getInt(7))   ;
         //  System.out.println(rs.getBigDecimal(8).floatValue());
           trade.setTradeAmount(rs.getBigDecimal(8).floatValue())  ;
           trade.setEffectiveDate(rs.getString(9))  ;
           trade.setDelivertyDate(rs.getString(10)) ;
           trade.setBookId(rs.getInt(11))  ;
           trade.setQuantity(rs.getDouble(12))  ;
           trade.setPrice(rs.getDouble(13))  ;
           trade.setUserID(rs.getInt(14))  ;
           trade.setVersionID(rs.getInt(15))  ;
           trade.setCurrency(rs.getString(16));
           trade.setYield(rs.getDouble(17));
           trade.setAttributes(rs.getString(18));
           trade.setTradedesc(rs.getString(19));
           trade.setTraderID(rs.getInt(20))  ;
           trade.setNominal(rs.getDouble(21));
           trade.setAction(rs.getString(22));
           trade.setTradedesc1(rs.getString(23));
           trade.setProductType(rs.getString(24));
           trade.setAmoritizationData(rs.getString(25));
           trade.setMirrorID(rs.getInt(26));
           trade.setParentID(rs.getInt(27));
           trade.setAutoType(rs.getString(28));
           trade.setSecondPrice(rs.getDouble(29));
           trade.setRollOverTo(rs.getInt(30));
           trade.setRollOverFrom(rs.getInt(31));
           trade.setRollBackTo(rs.getInt(32));
           trade.setRollBackFrom(rs.getInt(33));
           trade.setOutstanding(rs.getDouble(34));
           if( rs.getInt( 35) == 1)
        	   trade.setParitial(true);
           trade.setOffsetid(rs.getInt(36));
           trade.setXccySPlitid(rs.getInt(37));
           trade.setMirrorBookid(rs.getInt(38));
           trade.setB2bid(rs.getInt(39));
           if(rs.getString(40).trim().equalsIgnoreCase("N")) {
        	   trade.setPositionBased(false);
          }
         // Trades.add(Trade);
      
       }
       commonUTIL.display("TradeSQL",SELECTONE + " " + sql);
       if(!recordExist)
    	    trade = null;
   } catch (Exception e) {
    commonUTIL.displayError("TradeSQL",SELECTONE,e);
    return trade;
        
      }
      finally {
         try {
    stmt.close();
   } catch (SQLException e) {
    // TODO Auto-generated catch block
    commonUTIL.displayError("TradeSQL",SELECTONE,e);
   }
      }
      return trade;
  }

  public static Collection getRollBackHierarchies(int parentID,Connection con ) {
	   int j = 0;
	      PreparedStatement stmt = null;
	      Vector Trades = new Vector();
	      String sql = ROLLBACKHIERARCHIES;
	   try {
	    con.setAutoCommit(false);
	   
	 
	    stmt = dsSQL.newPreparedStatement(con, sql);
	    stmt.setInt(1,parentID);
	       ResultSet rs = stmt.executeQuery();
	      
	       while(rs.next()) {
	    	   Trade trade = new Trade();
	    	   trade.setId(rs.getInt(1));
	           
	           trade.setProductId(rs.getInt(2));
	           trade.setCpID(rs.getInt(3));
	           trade.setStatus(rs.getString(4));
	           trade.setType(rs.getString(5));
	         
	           trade.setTradeDate(commonUTIL.convertSQLDatetoString(rs.getTimestamp((6))));
	           trade.setBrokerID(rs.getInt(7))   ;
	           trade.setTradeAmount(rs.getDouble(8))  ;
	           trade.setEffectiveDate(rs.getString(9))  ;
	           trade.setDelivertyDate(rs.getString(10)) ;
	           trade.setBookId(rs.getInt(11))  ;
	           trade.setQuantity(rs.getDouble(12))  ;
	           trade.setPrice(rs.getDouble(13))  ;
	           trade.setUserID(rs.getInt(14))  ;
	           trade.setVersionID(rs.getInt(15))  ;
	           trade.setCurrency(rs.getString(16));
	           trade.setYield(rs.getDouble(17));
	           trade.setAttributes(rs.getString(18));
	           trade.setTradedesc(rs.getString(19));
	           trade.setTraderID(rs.getInt(20))  ;
	           trade.setNominal(rs.getDouble(21));
	           trade.setAction(rs.getString(22));
	           trade.setTradedesc1(rs.getString(23));
	           trade.setProductType(rs.getString(24));
	           trade.setAmoritizationData(rs.getString(25));
	           trade.setMirrorID(rs.getInt(26));
	           trade.setParentID(rs.getInt(27));
	           trade.setAutoType(rs.getString(28));
	           trade.setSecondPrice(rs.getDouble(29));
	           trade.setRollOverTo(rs.getInt(30));
	           trade.setRollOverFrom(rs.getInt(31));
	           trade.setRollBackTo(rs.getInt(32));
	           trade.setRollBackFrom(rs.getInt(33));
	           trade.setOutstanding(rs.getDouble(34));
	           if( rs.getInt( 35) == 1)
	        	   trade.setParitial(true);
	           trade.setOffsetid(rs.getInt(36));
	           trade.setXccySPlitid(rs.getInt(37));
	           trade.setMirrorBookid(rs.getInt(38));
	           trade.setB2bid(rs.getInt(39));
	           if(rs.getString(40).trim().equalsIgnoreCase("N")) {
	        	   trade.setPositionBased(false);
	          }
	         Trades.add(trade);
	        
	       }
	       commonUTIL.display("TradeSQL",sql);
	   } catch (Exception e) {
	    commonUTIL.displayError("TradeSQL"," getRollOverHierarchies " + sql,e);
	    return Trades;
	        
	      }
	      finally {
	         try {
	    stmt.close();
	   } catch (SQLException e) {
	    // TODO Auto-generated catch block
	    commonUTIL.displayError("TradeSQL"," getRollOverHierarchies  " + sql,e);
	   }
	      }
	      return Trades;
	  }
  
  public static Collection getRollOverHierarchies(int parentID,Connection con ) {
	   int j = 0;
	      PreparedStatement stmt = null;
	      Vector Trades = new Vector();
	      String sql = ROLLOVERHIERARCHIES;
	   try {
		   sql = sql + " " + parentID+" connect by nocycle prior   id = rolloverfrom ";
	    con.setAutoCommit(false);
	   
	 
	    stmt = dsSQL.newPreparedStatement(con, sql);
	 //   stmt.setInt(1,parentID);
	       ResultSet rs = stmt.executeQuery();
	      
	       while(rs.next()) {
	    	   Trade trade = new Trade();
	    	   trade.setId(rs.getInt(1));
	           
	           trade.setProductId(rs.getInt(2));
	           trade.setCpID(rs.getInt(3));
	           trade.setStatus(rs.getString(4));
	           trade.setType(rs.getString(5));
	         
	           trade.setTradeDate(commonUTIL.convertSQLDatetoString(rs.getTimestamp((6))));
	           trade.setBrokerID(rs.getInt(7))   ;
	           trade.setTradeAmount(rs.getDouble(8))  ;
	           trade.setEffectiveDate(rs.getString(9))  ;
	           trade.setDelivertyDate(rs.getString(10)) ;
	           trade.setBookId(rs.getInt(11))  ;
	           trade.setQuantity(rs.getDouble(12))  ;
	           trade.setPrice(rs.getDouble(13))  ;
	           trade.setUserID(rs.getInt(14))  ;
	           trade.setVersionID(rs.getInt(15))  ;
	           trade.setCurrency(rs.getString(16));
	           trade.setYield(rs.getDouble(17));
	           trade.setAttributes(rs.getString(18));
	           trade.setTradedesc(rs.getString(19));
	           trade.setTraderID(rs.getInt(20))  ;
	           trade.setNominal(rs.getDouble(21));
	           trade.setAction(rs.getString(22));
	           trade.setTradedesc1(rs.getString(23));
	           trade.setProductType(rs.getString(24));
	           trade.setAmoritizationData(rs.getString(25));
	           trade.setMirrorID(rs.getInt(26));
	           trade.setParentID(rs.getInt(27));
	           trade.setAutoType(rs.getString(28));
	           trade.setSecondPrice(rs.getDouble(29));
	           trade.setRollOverTo(rs.getInt(30));
	           trade.setRollOverFrom(rs.getInt(31));
	           trade.setRollBackTo(rs.getInt(32));
	           trade.setRollBackFrom(rs.getInt(33));
	           trade.setOutstanding(rs.getDouble(34));
	           if( rs.getInt( 35) == 1)
	        	   trade.setParitial(true);
	           trade.setOffsetid(rs.getInt(36));
	           trade.setXccySPlitid(rs.getInt(37));
	           trade.setMirrorBookid(rs.getInt(38));
	           trade.setB2bid(rs.getInt(39));
	           if(rs.getString(40).trim().equalsIgnoreCase("N")) {
	        	   trade.setPositionBased(false);
	          }
	         Trades.add(trade);
	        
	       }
	       commonUTIL.display("TradeSQL",sql);
	   } catch (Exception e) {
	    commonUTIL.displayError("TradeSQL"," getRollOverHierarchies " + sql,e);
	    return Trades;
	        
	      }
	      finally {
	         try {
	    stmt.close();
	   } catch (SQLException e) {
	    // TODO Auto-generated catch block
	    commonUTIL.displayError("TradeSQL"," getRollOverHierarchies  " + sql,e);
	   }
	      }
	      return Trades;
	  }

public static Vector getXccySplitOnOffset(int tradeid, Connection con) {
	// TODO Auto-generated method stub
	int j = 0;
    PreparedStatement stmt = null;
    Vector Trades = new Vector();
    String sql = SELECTXCCYSPLIT;
    
 try {
	   sql = sql + "   id = " + tradeid +" or xccySPlitid= " + tradeid;
  con.setAutoCommit(false);
 

  stmt = dsSQL.newPreparedStatement(con, sql);
//   stmt.setInt(1,parentID);
     ResultSet rs = stmt.executeQuery();
    
     while(rs.next()) {
  	   Trade trade = new Trade();
  	   trade.setId(rs.getInt(1));
         
         trade.setProductId(rs.getInt(2));
         trade.setCpID(rs.getInt(3));
         trade.setStatus(rs.getString(4));
         trade.setType(rs.getString(5));
       
         trade.setTradeDate(commonUTIL.convertSQLDatetoString(rs.getTimestamp((6))));
         trade.setBrokerID(rs.getInt(7))   ;
         trade.setTradeAmount(rs.getDouble(8))  ;
         trade.setEffectiveDate(rs.getString(9))  ;
         trade.setDelivertyDate(rs.getString(10)) ;
         trade.setBookId(rs.getInt(11))  ;
         trade.setQuantity(rs.getDouble(12))  ;
         trade.setPrice(rs.getDouble(13))  ;
         trade.setUserID(rs.getInt(14))  ;
         trade.setVersionID(rs.getInt(15))  ;
         trade.setCurrency(rs.getString(16));
         trade.setYield(rs.getDouble(17));
         trade.setAttributes(rs.getString(18));
         trade.setTradedesc(rs.getString(19));
         trade.setTraderID(rs.getInt(20))  ;
         trade.setNominal(rs.getDouble(21));
         trade.setAction(rs.getString(22));
         trade.setTradedesc1(rs.getString(23));
         trade.setProductType(rs.getString(24));
         trade.setAmoritizationData(rs.getString(25));
         trade.setMirrorID(rs.getInt(26));
         trade.setParentID(rs.getInt(27));
         trade.setAutoType(rs.getString(28));
         trade.setSecondPrice(rs.getDouble(29));
         trade.setRollOverTo(rs.getInt(30));
         trade.setRollOverFrom(rs.getInt(31));
         trade.setRollBackTo(rs.getInt(32));
         trade.setRollBackFrom(rs.getInt(33));
         trade.setOutstanding(rs.getDouble(34));
         if( rs.getInt( 35) == 1)
      	   trade.setParitial(true);
         trade.setOffsetid(rs.getInt(36));
         trade.setXccySPlitid(rs.getInt(37));
         trade.setMirrorBookid(rs.getInt(38));
         trade.setB2bid(rs.getInt(39));
         if(rs.getString(40).trim().equalsIgnoreCase("N")) {
      	   trade.setPositionBased(false);
        }
       Trades.add(trade);
      
     }
     commonUTIL.display("TradeSQL",sql);
 } catch (Exception e) {
  commonUTIL.displayError("TradeSQL"," getRollOverHierarchies " + sql,e);
  return Trades;
      
    }
    finally {
       try {
  stmt.close();
 } catch (SQLException e) {
  // TODO Auto-generated catch block
  commonUTIL.displayError("TradeSQL"," getRollOverHierarchies  " + sql,e);
 }
    }
    return Trades;
}

public static Vector getXccySplitOnChild(int tradeID, Connection con) {
	// TODO Auto-generated method stub
	 int j = 0;
     PreparedStatement stmt = null;
     Vector Trades = new Vector();
     String sql = SELECTXCCYSPLIT;
     
  try {
	   sql = sql + "   offsetid = " + tradeID +" or xccySPlitid= " + tradeID + " or id = " + tradeID;
   con.setAutoCommit(false);
  

   stmt = dsSQL.newPreparedStatement(con, sql);
//   stmt.setInt(1,parentID);
      ResultSet rs = stmt.executeQuery();
     
      while(rs.next()) {
   	   Trade trade = new Trade();
   	   trade.setId(rs.getInt(1));
          
          trade.setProductId(rs.getInt(2));
          trade.setCpID(rs.getInt(3));
          trade.setStatus(rs.getString(4));
          trade.setType(rs.getString(5));
        
          trade.setTradeDate(commonUTIL.convertSQLDatetoString(rs.getTimestamp((6))));
          trade.setBrokerID(rs.getInt(7))   ;
          trade.setTradeAmount(rs.getDouble(8))  ;
          trade.setEffectiveDate(rs.getString(9))  ;
          trade.setDelivertyDate(rs.getString(10)) ;
          trade.setBookId(rs.getInt(11))  ;
          trade.setQuantity(rs.getDouble(12))  ;
          trade.setPrice(rs.getDouble(13))  ;
          trade.setUserID(rs.getInt(14))  ;
          trade.setVersionID(rs.getInt(15))  ;
          trade.setCurrency(rs.getString(16));
          trade.setYield(rs.getDouble(17));
          trade.setAttributes(rs.getString(18));
          trade.setTradedesc(rs.getString(19));
          trade.setTraderID(rs.getInt(20))  ;
          trade.setNominal(rs.getDouble(21));
          trade.setAction(rs.getString(22));
          trade.setTradedesc1(rs.getString(23));
          trade.setProductType(rs.getString(24));
          trade.setAmoritizationData(rs.getString(25));
          trade.setMirrorID(rs.getInt(26));
          trade.setParentID(rs.getInt(27));
          trade.setAutoType(rs.getString(28));
          trade.setSecondPrice(rs.getDouble(29));
          trade.setRollOverTo(rs.getInt(30));
          trade.setRollOverFrom(rs.getInt(31));
          trade.setRollBackTo(rs.getInt(32));
          trade.setRollBackFrom(rs.getInt(33));
          trade.setOutstanding(rs.getDouble(34));
          if( rs.getInt( 35) == 1)
       	   trade.setParitial(true);
          trade.setOffsetid(rs.getInt(36));
          trade.setXccySPlitid(rs.getInt(37));
          trade.setMirrorBookid(rs.getInt(38));
          trade.setB2bid(rs.getInt(39));
          if(rs.getString(40).trim().equalsIgnoreCase("N")) {
       	   trade.setPositionBased(false);
         }
        Trades.add(trade);
       
      }
      commonUTIL.display("TradeSQL"," getXccySplitOnChild " + sql);
  } catch (Exception e) {
   commonUTIL.displayError("TradeSQL"," getXccySplitOnChild " + sql,e);
   return Trades;
       
     }
     finally {
        try {
   stmt.close();
  } catch (SQLException e) {
   // TODO Auto-generated catch block
   commonUTIL.displayError("TradeSQL"," getRollOverHierarchies  " + sql,e);
  }
     }
     return Trades;
}
  public static Collection getXccySplit(int tradeID,Connection con ) {
	   int j = 0;
	      PreparedStatement stmt = null;
	      Vector Trades = new Vector();
	      String sql = SELECTXCCYSPLIT;
	      
	   try {
		   sql = sql + "   offsetid = " + tradeID +" or xccySPlitid= " + tradeID;
	    con.setAutoCommit(false);
	   
	 
	    stmt = dsSQL.newPreparedStatement(con, sql);
	 //   stmt.setInt(1,parentID);
	       ResultSet rs = stmt.executeQuery();
	      
	       while(rs.next()) {
	    	   Trade trade = new Trade();
	    	   trade.setId(rs.getInt(1));
	           
	           trade.setProductId(rs.getInt(2));
	           trade.setCpID(rs.getInt(3));
	           trade.setStatus(rs.getString(4));
	           trade.setType(rs.getString(5));
	         
	           trade.setTradeDate(commonUTIL.convertSQLDatetoString(rs.getTimestamp((6))));
	           trade.setBrokerID(rs.getInt(7))   ;
	           trade.setTradeAmount(rs.getDouble(8))  ;
	           trade.setEffectiveDate(rs.getString(9))  ;
	           trade.setDelivertyDate(rs.getString(10)) ;
	           trade.setBookId(rs.getInt(11))  ;
	           trade.setQuantity(rs.getDouble(12))  ;
	           trade.setPrice(rs.getDouble(13))  ;
	           trade.setUserID(rs.getInt(14))  ;
	           trade.setVersionID(rs.getInt(15))  ;
	           trade.setCurrency(rs.getString(16));
	           trade.setYield(rs.getDouble(17));
	           trade.setAttributes(rs.getString(18));
	           trade.setTradedesc(rs.getString(19));
	           trade.setTraderID(rs.getInt(20))  ;
	           trade.setNominal(rs.getDouble(21));
	           trade.setAction(rs.getString(22));
	           trade.setTradedesc1(rs.getString(23));
	           trade.setProductType(rs.getString(24));
	           trade.setAmoritizationData(rs.getString(25));
	           trade.setMirrorID(rs.getInt(26));
	           trade.setParentID(rs.getInt(27));
	           trade.setAutoType(rs.getString(28));
	           trade.setSecondPrice(rs.getDouble(29));
	           trade.setRollOverTo(rs.getInt(30));
	           trade.setRollOverFrom(rs.getInt(31));
	           trade.setRollBackTo(rs.getInt(32));
	           trade.setRollBackFrom(rs.getInt(33));
	           trade.setOutstanding(rs.getDouble(34));
	           if( rs.getInt( 35) == 1)
	        	   trade.setParitial(true);
	           trade.setOffsetid(rs.getInt(36));
	           trade.setXccySPlitid(rs.getInt(37));
	           trade.setMirrorBookid(rs.getInt(38));
	           trade.setB2bid(rs.getInt(39));
	           if(rs.getString(40).trim().equalsIgnoreCase("N")) {
	           	   trade.setPositionBased(false);
	             }
	         Trades.add(trade);
	        
	       }
	       commonUTIL.display("TradeSQL",sql);
	   } catch (Exception e) {
	    commonUTIL.displayError("TradeSQL"," getXccySplit " + sql,e);
	    return Trades;
	        
	      }
	      finally {
	         try {
	    stmt.close();
	   } catch (SQLException e) {
	    // TODO Auto-generated catch block
	    commonUTIL.displayError("TradeSQL"," getRollOverHierarchies  " + sql,e);
	   }
	      }
	      return Trades;
	  }
 
  protected static Collection selectOnWherecClause(String wsql,Connection con ) {
	   int j = 0;
	      PreparedStatement stmt = null;
	      Vector Trades = new Vector();
	      String sql = "";
	   try {
	    con.setAutoCommit(false);
	   
	  sql = SELECTWHERE + wsql + "  ";
	    stmt = dsSQL.newPreparedStatement(con, sql);
	      
	       ResultSet rs = stmt.executeQuery();
	      
	       while(rs.next()) {
	    	   Trade trade = new Trade();
	    	   trade.setId(rs.getInt(1));
	           
	           trade.setProductId(rs.getInt(2));
	           trade.setCpID(rs.getInt(3));
	           trade.setStatus(rs.getString(4));
	           trade.setType(rs.getString(5));
	         
	           trade.setTradeDate(commonUTIL.convertSQLDatetoString(rs.getTimestamp((6))));
	           trade.setBrokerID(rs.getInt(7))   ;
	           trade.setTradeAmount(rs.getDouble(8))  ;
	           trade.setEffectiveDate(rs.getString(9))  ;
	           trade.setDelivertyDate(rs.getString(10)) ;
	           trade.setBookId(rs.getInt(11))  ;
	           trade.setQuantity(rs.getDouble(12))  ;
	           trade.setPrice(rs.getDouble(13))  ;
	           trade.setUserID(rs.getInt(14))  ;
	           trade.setVersionID(rs.getInt(15))  ;
	           trade.setCurrency(rs.getString(16));
	           trade.setYield(rs.getDouble(17));
	           trade.setAttributes(rs.getString(18));
	           trade.setTradedesc(rs.getString(19));
	           trade.setTraderID(rs.getInt(20))  ;
	           trade.setNominal(rs.getDouble(21));
	           trade.setAction(rs.getString(22));
	           trade.setTradedesc1(rs.getString(23));
	           trade.setProductType(rs.getString(24));
	           trade.setAmoritizationData(rs.getString(25));
	           trade.setMirrorID(rs.getInt(26));
	           trade.setParentID(rs.getInt(27));
	           trade.setAutoType(rs.getString(28));
	           trade.setSecondPrice(rs.getDouble(29));
	           trade.setRollOverTo(rs.getInt(30));
	           trade.setRollOverFrom(rs.getInt(31));
	           trade.setRollBackTo(rs.getInt(32));
	           trade.setRollBackFrom(rs.getInt(33));
	           trade.setOutstanding(rs.getDouble(34));
	           if( rs.getInt( 35) == 1)
	        	   trade.setParitial(true);
	           trade.setOffsetid(rs.getInt(36));
	           trade.setXccySPlitid(rs.getInt(37));
	           trade.setMirrorBookid(rs.getInt(38));
	           trade.setB2bid(rs.getInt(39));
	           if(rs.getString(40).trim().equalsIgnoreCase("N")) {
	           	   trade.setPositionBased(false);
	             }
	         Trades.add(trade);
	        
	       }
	       commonUTIL.display("TradeSQL",sql);
	   } catch (Exception e) {
	    commonUTIL.displayError("TradeSQL","selectWhere " + sql,e);
	    return Trades;
	        
	      }
	      finally {
	         try {
	    stmt.close();
	   } catch (SQLException e) {
	    // TODO Auto-generated catch block
	    commonUTIL.displayError("TradeSQL"," selectWhere " + sql,e);
	   }
	      }
	      return Trades;
	  }
  
  
  
  public static Trade getOutStandingBalanceonFWDOption(int FWDOptionID, Connection con) {
	  String sql = " select  sum(quantity) primaryCurr, sum(nominal)  quotingCurr from trade where parentid =" +  FWDOptionID + "  group by parentid ";
	  PreparedStatement stmt = null;
     
      Trade trade = null;
      try {
    	    con.setAutoCommit(false);
    	   
    	     commonUTIL.display("TradeSQL", "getOutStandingBalanceonFWDOption  " + sql);
    	    stmt = dsSQL.newPreparedStatement(con, sql);
    	    
    	       ResultSet rs = stmt.executeQuery();
    	       while(rs.next()) {
    	    	  trade  = new Trade();
    	    	  trade.setQuantity(rs.getDouble(1));
    	    	  trade.setNominal(rs.getDouble(2));
    	       }
      } catch (Exception e) {
    	    commonUTIL.displayError("TradeSQL","selectOnWherecClauseReports " + sql,e);
    	    return trade;
    	        
    	      }
    	      finally {
    	         try {
    	    stmt.close();
    	   } catch (SQLException e) {
    	    // TODO Auto-generated catch block
    	    commonUTIL.displayError("TradeSQL"," selectOnWherecClauseReports " + sql,e);
    	   }
    	      }
    	      return trade;
    		}
     
  public static Trade getOutStandingBalanceonFWDOption(int FWDOptionID,int tradeid, Connection con) {
	  String sql = " select  sum(quantity) primaryCurr, sum(nominal)  quotingCurr from trade where parentid =" +  FWDOptionID + " and id not in ("+tradeid+")  group by parentid ";
	  PreparedStatement stmt = null;
     
      Trade trade = null;
      try {
    	    con.setAutoCommit(false);
    	   
    	     commonUTIL.display("TradeSQL", "getOutStandingBalanceonFWDOption  " + sql);
    	    stmt = dsSQL.newPreparedStatement(con, sql);
    	    
    	       ResultSet rs = stmt.executeQuery();
    	       while(rs.next()) {
    	    	  trade  = new Trade();
    	    	  trade.setQuantity(rs.getDouble(1));
    	    	  trade.setNominal(rs.getDouble(2));
    	       }
      } catch (Exception e) {
    	    commonUTIL.displayError("TradeSQL","selectOnWherecClauseReports " + sql,e);
    	    return trade;
    	        
    	      }
    	      finally {
    	         try {
    	    stmt.close();
    	   } catch (SQLException e) {
    	    // TODO Auto-generated catch block
    	    commonUTIL.displayError("TradeSQL"," selectOnWherecClauseReports " + sql,e);
    	   }
    	      }
    	      return trade;
    		}
     
	  
  private static PreparedStatement selectOnwhereForwardLadder(String sqlw, Connection con) {
	  PreparedStatement Trades = null;
	  PreparedStatement stmt = null;
	  try {
		  con.setAutoCommit(false);
		  Trades = dsSQL.newPreparedStatement(con, sqlw);
		    
		// = stmt.executeQuery();
		  
	  } catch (Exception e) {
		    commonUTIL.displayError("TradeSQL","selectOnWherecClauseReports " + sqlw,e);
		    return Trades;
		        
		      }
		      finally {
		       //  try {
		 //   stmt.close();
		 //  } catch (SQLException e) {
		    // TODO Auto-generated catch block
		 //   commonUTIL.displayError("TradeSQL"," selectOnWherecClauseReports " + sqlw,e);
		 //  }
		      }
		      return Trades;
			}
  
  private static Collection selectOnWherecClauseReports(String sqlw, Connection con) {
		// TODO Auto-generated method stub
	  int j = 0;
      PreparedStatement stmt = null;
      Vector<Object> Trades = new Vector();
      String sql = sqlw;
      ResultSet rs = null;
   try {
    con.setAutoCommit(false);
   if(sqlw.contains(";")) {
	  String s [] = sqlw.split(";"); 
	  for(int i=0;i<s.length;i++)
		  Trades.add( selectOnwhereForwardLadder(s[i],con));
	  ReportGenerator generateReport = new ReportGenerator();
	    Trades = (Vector) generateReport.generateReportOnSQLOnStatement(Trades);
	  
   } else {
	   commonUTIL.display("TradeSQL", "selectOnWherecClauseReports == reports SQL " + sql);
	    stmt = dsSQL.newPreparedStatement(con, sql);
	  rs = stmt.executeQuery();
	  if(rs != null)  {
 	     Trades.add(rs);
    ReportGenerator generateReport = new ReportGenerator();
    Trades = (Vector) generateReport.generateReportOnSQL(Trades);
    }
   }
    
    
       
       
     /*  ResultSetMetaData mdata = null;
       int columnDatacount = 0;
       String typeclass [] = null; 
       String headers [] = null;
       int count = 22;
      if(rs != null) {
    	 
    	  mdata = rs.getMetaData();
    	  
    	  columnDatacount = mdata.getColumnCount();
    	  headers = new  String [columnDatacount];
    	 typeclass = new String [columnDatacount];
    	  
    	  for(int i=0;i<mdata.getColumnCount();i++) {
    		  typeclass[i] = mdata.getColumnClassName(i+1);
    		  System.out.println(typeclass[i]);
    	   headers[i] = mdata.getColumnName(i+1);
    	   System.out.println(headers[i]);
    	  }
      }
      
      String [][] data = new String[count][columnDatacount];
      int r = 0;
      
  
      
       while(rs.next()) {
    	   System.out.print("Row number==  " + r );
    	   for(int c=0;c<mdata.getColumnCount();c++) {
    	         data[r][c] = rs.getString(c+1);
    	         System.out.print("  columns no == " + c + " " +  data[r][c] + " ");
    	         
    	   }
    	   System.out.println();
    	   r++;
       }
       Trades.add(0, headers);
       Trades.add(1,typeclass);
       Trades.add(2,data); */
  
   } catch (Exception e) {
    commonUTIL.displayError("TradeSQL","selectOnWherecClauseReports " + sql,e);
    return Trades;
        
      }
      finally {
         try {
        	 if(stmt != null)
    stmt.close();
   } catch (SQLException e) {
    // TODO Auto-generated catch block
    commonUTIL.displayError("TradeSQL"," selectOnWherecClauseReports " + sql,e);
   }
      }
      return Trades;
	}

  public static Collection selectforOpen(String productType, Connection con ) {
	   int j = 0;
	      PreparedStatement stmt = null;
	      Vector Trades = new Vector();
	      String sql = SELECTOPEN + productType.toUpperCase().trim() +"' order by id desc" ;
	   try {
	    con.setAutoCommit(false);
	    
	 
	    stmt = dsSQL.newPreparedStatement(con, sql);
	
	       ResultSet rs = stmt.executeQuery();
	      
	       while(rs.next()) {
	    	   Trade trade = new Trade();
	    	   trade.setId(rs.getInt(1));
	           
	          
	           trade.setTradedesc(rs.getString(2));
	            Trades.add(trade);
	       
	       }
	       commonUTIL.display("TradeSQL",sql);
	   } catch (Exception e) {
	    commonUTIL.displayError("TradeSQL","selectforOpen " +  sql ,e);
	    return Trades;
	        
	      }
	      finally {
	         try {
	    stmt.close();
	   } catch (SQLException e) {
	    // TODO Auto-generated catch block
	    commonUTIL.displayError("TradeSQL"," selectWhere " + sql,e);
	   }
	      }
	      return Trades;
	  }

public static Vector getMirrorTrade(int mirrorID,Connection con) {
	String sql = " autotype = 'Original' and id ="+ mirrorID;
	// TODO Auto-generated method stub
	return (Vector) selectOnWherecClause(sql,con);
}
public static Vector getMirrorTradeonB2B(int mirrorID,Connection con) {
	String sql = " autotype = 'MIRROR' and id ="+ mirrorID;
	// TODO Auto-generated method stub
	return (Vector) selectOnWherecClause(sql,con);
}
public static Vector geB2BTradeOnOriginalID(int b2bID,Connection con) {
	String sql = " autotype = 'BackToBack' and id ="+ b2bID;
	// TODO Auto-generated method stub
	return (Vector) selectOnWherecClause(sql,con);
}
public static Vector getB2BTradeonMirrorID(int mirrorTradeID,Connection con) {
	String sql = " autotype = 'BackToBack' and mirrorid ="+ mirrorTradeID;
	// TODO Auto-generated method stub
	return (Vector) selectOnWherecClause(sql,con);
}
public static Vector getOrginalTradeOnB2bID(int b2bID,Connection con) {
	String sql = " autotype = 'Original' and b2bid ="+ b2bID;
	// TODO Auto-generated method stub
	return (Vector) selectOnWherecClause(sql,con);
}

public static Vector getTradesOnB2bTrade(Trade b2bTrade,Connection con) {
	String sql = " b2bid = " +  b2bTrade.getId() + " or  id  ="+b2bTrade.getMirrorID();
	// TODO Auto-generated method stub
	return (Vector) selectOnWherecClause(sql,con);
}


    

}