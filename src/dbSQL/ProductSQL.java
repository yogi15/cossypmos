package dbSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;

import beans.Book;
import beans.Coupon;
import beans.Product;
import util.commonUTIL;


public class ProductSQL {
	
	
	final static private String tableName = "product";
	final static private String DELETE =
		"DELETE FROM " + tableName + "   where id =? ";
	final static private String INSERT =
		"INSERT into " + tableName + "(id,productType,productName,productShortName ,quantity,issueDate,marturityDate,IssuerId,Country,IssuePrice,IssueCurrency,RedemptionPrice,RedemptionCurrency,FaceValue,code,dateddate,tenor,attributes,ispositionBased,collateralId,callableType,repoType,UNDERLYINGTYPE,currency,productsubtype) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				//"issueDate,marturityDate,IssuerId,Country,IssuePrice,IssueCurrency,RedemptionPrice,RedemptionCurrency,FaceValue,code,dateddate,tenor ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	final static private String UPDATE =
		"UPDATE " + tableName +
		" set 	productType=?, " +
		"		productName=?," +
		"		productShortName=?," +
		"		quantity=?," +
		"		issueDate=?," +
		"		marturityDate=?," +
		"		IssuerId=?," +
		"		Country=?," +
		"		IssuePrice=?," +
		"		IssueCurrency=?," +
		"		RedemptionPrice=?," +
		"		RedemptionCurrency=?," +
		"		FaceValue=?," +
		"		code=?, " +
		"		Dateddate=?," +
		"		Tenor=?,  " +
		"		attributes=?,  " +
		"		isPositionBased=?,  " +
		"		collateralId=?,  " +
		"		callableType=?,  " +
		"		repoType=?  " +
		"where 	id = ? ";
	
	 private static String getUpdateSQL(Product product) {
	      String updateSQL = new StringBuffer("UPDATE ")
	      	.append(tableName)
	      	.append(" set productType= '").append(product.getProductType()).append("',")
	      	.append(" productName='").append(product.getProductname()).append("',")
			.append(" productShortName='").append(product.getProdcutShortName()).append("',")
			.append(" quantity=").append(product.getQuantity()).append(",")
			.append(" issueDate='").append(product.getIssueDate()).append("',")
			.append(" marturityDate='").append(product.getMarturityDate()).append("',")
			.append(" IssuerId=").append(product.getIssuerId()).append(",")
			.append(" Country='").append(product.getCountry()).append("',")
			.append(" IssuePrice=").append(product.getIssuePrice()).append(",")
			.append(" IssueCurrency='").append(product.getIssueCurrency()).append("',")
			.append(" RedemptionPrice=").append(product.getRedemptionPrice()).append(",")
			.append(" RedemptionCurrency='").append(product.getRedemptionCurrency()).append("',")
			.append(" FaceValue=").append(product.getFaceValue()).append(",")
			.append(" code='").append(product.getCode()).append("',")
			.append(" Dateddate='").append(product.getDatedDate()).append("',")
			.append(" Tenor='").append(product.getTenor()).append("',")
			.append(" attributes='").append(product.getAttributes()).append("',")
			.append(" isPositionBased=").append(product.getIsPosition()).append(",")
			.append(" collateralId=").append(product.getCollateralID()).append(",")
			.append(" callableType='").append(product.getCallType()).append("',")
			.append(" repoType='").append(product.getRepoType()).append("',")
			.append(" currency='").append(product.getCurrency()).append("',")
			.append(" productSubType='").append(product.getProductSubType()).append("',")
			.append(" UNDERLYINGTYPE='").append(product.getUnderlyingProductType()).append("'")
			.append("where 	id = ").append(product.getId()).toString();
	      //System.out.println(updateSQL);
	      return updateSQL;
	     }
	 
	final static private String SELECT_MAX =
		" SELECT PRODUCT_SEQ.NEXTVAL DESC_ID FROM DUAL";
	final static private String SELECTALL =
		"SELECT id,productType,productName,productShortName ,quantity,issueDate,marturityDate,IssuerId,Country,IssuePrice,IssueCurrency,RedemptionPrice,RedemptionCurrency,FaceValue,code,dateddate,tenor,attributes,ispositionBased,collateralId,callableType,repoType,UNDERLYINGTYPE,currency,productSubType FROM " + tableName + " order by id ";
	final static private String SELECT =
		"SELECT id,productType,productName,productShortName ,quantity,issueDate,marturityDate,IssuerId,Country,IssuePrice,IssueCurrency,RedemptionPrice,RedemptionCurrency,FaceValue,code,dateddate,tenor,attributes,ispositionBased,collateralId,callableType,repoType,UNDERLYINGTYPE,currency,productSubType FROM " + tableName + " where id =  ?";
	 static private String SELECTONE =
		"SELECT id,productType,productName,productShortName ,quantity,issueDate,marturityDate,IssuerId,Country,IssuePrice,IssueCurrency,RedemptionPrice,RedemptionCurrency,FaceValue,code,dateddate,tenor,attributes,ispositionBased,collateralId,callableType,repoType,UNDERLYINGTYPE,currency,productSubType FROM " + tableName + " where id =  ";
	 final static private String SELECTWHRE = 
		 "SELECT id,productType,productName,productShortName ,quantity,issueDate,marturityDate,IssuerId,Country,IssuePrice,IssueCurrency,RedemptionPrice,RedemptionCurrency,FaceValue,code,dateddate,tenor,attributes,ispositionBased ,collateralId,callableType,repoType,UNDERLYINGTYPE,currency,productSubType FROM " + tableName + " where   ";
	 final static private String SELECTFROMATTRIBUTES = " select id,attributes FROM " + tableName + " where attributes = '%like" ; // this working only with oralce..  
	final static private String selectExisitingName = " select id,producttype,productname,productshortname,productSubType from product ";
	final static private String selectFutureProduct = " select id,productType,productName,productShortName ,quantity,issueDate,marturityDate,IssuerId,Country,IssuePrice,IssueCurrency,RedemptionPrice,RedemptionCurrency,FaceValue,code,dateddate,tenor,attributes,ispositionBased,collateralId,callableType,repoType,UNDERLYINGTYPE  from product where id = (select underlying_productid from FUTURECONTRACT_PRODUCT where id = (select PRODUCTID from futurecontract where id = ";
	 
	 
	 public static int save(Product insertProduct, Connection con) {
		 try {
			 if(insertProduct.getProductType().equalsIgnoreCase("MM") || insertProduct.getProductType().equalsIgnoreCase("REPO")) {
				  return insert(insertProduct, con);
			 }
			 Product product = selectProduct(insertProduct.getProductname(),con);
			 if(product.getId() > 0) 
				 return -1;
             return insert(insertProduct, con);
         }catch(Exception e) {
        	 commonUTIL.displayError("ProductSQL","save",e);
        	 return 0;
         }
	 }
	 public static boolean update(Product updateProduct, Connection con) {
		 try {
             return edit(updateProduct, con);
         }catch(Exception e) {
        	 commonUTIL.displayError("ProductSQL","update",e);
        	 return false;
         }
	 }
	 
	 public static boolean delete(Product deleteProduct, Connection con) {
		 try {
             return remove(deleteProduct, con);
         }catch(Exception e) {
        	 commonUTIL.displayError("ProductSQL","update",e);
        	 return false;
         }
	 }
	 
	 protected static boolean remove(Product deleteProduct, Connection con ) {
			
	        PreparedStatement stmt = null;
		 try {
			 int j = 1;
			 stmt = dsSQL.newPreparedStatement(con, DELETE);
	            stmt.setInt(j++, deleteProduct.getId());
	           
	            stmt.executeUpdate();
			 
		 } catch (Exception e) {
			 commonUTIL.displayError("productSQL","remove",e);
			 return false;
	           
	        }
	        finally {
	           try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				commonUTIL.displayError("productSQL","remove",e);
			}
	        }
	        return true;
	 }
	 
	 public static Product selectProduct(int ProductId, Connection con) {
		 try {
             return  select(ProductId, con);
         }catch(Exception e) {
        	 commonUTIL.displayError("ProductSQL","select",e);
        	 return null;
         }
	 }
	 public static Product selectProductWithCoupons(int ProductId, Connection con) {
		 try {
            Product product =   select(ProductId, con);
            product.setCoupon(getCoupon(ProductId, con));
            return product;
         }catch(Exception e) {
        	 commonUTIL.displayError("ProductSQL","select",e);
        	 return null;
         }
	 }
	 
	 public static Product selectProduct(String productName, Connection con) {
		 try {
             return  select(productName, con);
         }catch(Exception e) {
        	 commonUTIL.displayError("ProductSQL","select",e);
        	 return null;
         }
	 }
	 
	 public static Collection selectALL(Connection con) {
		 try {
             return select(con);
         }catch(Exception e) {
        	 commonUTIL.displayError("ProductSQL","selectALL",e);
        	 return null;
         }
	 }
	 
	 public static int selectMaxID(Connection con) {
		 try {
             return selectMax(con);
         }catch(Exception e) {
        	 commonUTIL.displayError("ProductSQL","selectMaxID",e);
        	 return 0;
         }
	 }
	 
	 protected static  boolean edit(Product updateProduct, Connection con ) {
		 
	        PreparedStatement stmt = null;
		 try {
			
	            
			
	           con.setAutoCommit(false);
	            
			 int j = 1;
			 stmt = dsSQL.newPreparedStatement(con, getUpdateSQL(updateProduct));
  
            /*stmt.setString(1, updateProduct.getProductType());
            stmt.setString(2, updateProduct.getProductname());
            stmt.setString(3, updateProduct.getProdcutShortName());
            stmt.setDouble(4, updateProduct.getQuantity());
            stmt.setString(5, updateProduct.getIssueDate());
            stmt.setString(6, updateProduct.getMarturityDate());
            stmt.setInt(7,    updateProduct.getIssuerId());
            stmt.setString(8, updateProduct.getCountry());
            stmt.setDouble(9, updateProduct.getIssuePrice());
            stmt.setString(10, updateProduct.getIssueCurrency());
            stmt.setDouble(11, updateProduct.getRedemptionPrice());
            stmt.setString(12, updateProduct.getRedemptionCurrency());
            stmt.setDouble(13, updateProduct.getFaceValue());
            stmt.setString(14, updateProduct.getCode());
            stmt.setString(15, updateProduct.getDatedDate());
            stmt.setString(16, updateProduct.getTenor());
            stmt.setString(17, updateProduct.getAttributes());
            stmt.setInt(18, updateProduct.getIsPosition());
            stmt.setInt(19, updateProduct.getId());  
            stmt.setInt(20, updateProduct.getCollateralID());  
            stmt.setString(21, updateProduct.getCallType());  
            stmt.setString(22, updateProduct.getRepoType());  
            */
            stmt.executeUpdate();
            con.commit();
            commonUTIL.display("productSQL",UPDATE);
	        //stmt.executeUpdate();
			 
		 } catch (Exception e) {
			 commonUTIL.displayError("ProductSQL","edit",e);
			 return false;
	           
	        }
	        finally {
	           try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				commonUTIL.displayError("bookSQL","edit",e);
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
			 commonUTIL.displayError("ProductSQL","selectMax",e);
			 return j;
	           
	        }
	        finally {
	           try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				commonUTIL.displayError("ProductSQL","selectMax",e);
			}
	        }
	        return j;
	 }
      
      protected static int insert(Product inserProduct, Connection con ) {
			int id = 0;
	        PreparedStatement stmt = null;
		 try {
			 id = selectMax(con);
			 id = id+1;
			 int j = 1;
			 con.setAutoCommit(false);
			 stmt = dsSQL.newPreparedStatement(con, INSERT);
			 
			 stmt.setInt(1,id);
		
	            stmt.setString(2, inserProduct.getProductType());
	            stmt.setString(3,  inserProduct.getProductname());
	            stmt.setString(4, inserProduct.getProdcutShortName());
	            stmt.setDouble(5, inserProduct.getQuantity());
	           stmt.setString(6, inserProduct.getIssueDate());
   	            stmt.setString(7, inserProduct.getMarturityDate());
   	            stmt.setInt(8, inserProduct.getIssuerId());
   	           stmt.setString(9, inserProduct.getCountry());
   	            stmt.setDouble(10, inserProduct.getIssuePrice());
   	            stmt.setString(11, inserProduct.getIssueCurrency());
   	            stmt.setDouble(12, inserProduct.getRedemptionPrice());
   	            stmt.setString(13, inserProduct.getRedemptionCurrency());
   	            stmt.setDouble(14, inserProduct.getFaceValue());
   	            stmt.setString(15, inserProduct.getCode());
   	         stmt.setString(16, inserProduct.getDatedDate()); 
   	      stmt.setString(17, inserProduct.getTenor());
   	   stmt.setString(18, inserProduct.getAttributes());
   	stmt.setInt(19, inserProduct.getIsPosition());
 	stmt.setInt(20, inserProduct.getCollateralID());
 	stmt.setString(21, inserProduct.getCallType());
 	stmt.setString(22, inserProduct.getRepoType());
 	stmt.setString(23, inserProduct.getUnderlyingProductType());
 	stmt.setString(24, inserProduct.getCurrency());
 	stmt.setString(25, inserProduct.getProductSubType());
   	      
	            
	            stmt.executeUpdate();
	            con.commit();
	            commonUTIL.display("productSQL",INSERT);
	            return id;
			 
		 } catch (Exception e) {
			 commonUTIL.displayError("PrdocutSQL","insert",e);
			 return 0;
	           
	        }
	        finally {
	           try {
				stmt.close();
				return id;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				commonUTIL.displayError("PrdocutSQL","insert",e);
			}
	        }
	        
	 }
	 
	 protected static Product select(int productID,Connection con ) {
		 
		 int j = 0;
	        PreparedStatement stmt = null;
	        Product product = new Product();
	        
		 try {
			
			 stmt = dsSQL.newPreparedStatement(con, SELECTONE + productID);
	         
	         ResultSet rs = stmt.executeQuery();
	         
	         while(rs.next()) {
	        
	     	    product.setId(rs.getInt(1));
		        product.setProductType(rs.getString(2));
		        product.setProductname(rs.getString(3));
		        product.setProdcutShortName(rs.getString(4));
		        product.setQuantity(rs.getInt(5));
		        product.setIssueDate(rs.getString(6));
		        product.setMarturityDate(rs.getString(7));
		        product.setIssuerId(rs.getInt(8));
		        product.setCountry(rs.getString(9));
		        product.setIssuePrice(rs.getDouble(10));
		        product.setIssueCurrency(rs.getString(11));
		        product.setRedemptionPrice(rs.getDouble(12));
		        product.setRedemptionCurrency(rs.getString(13));
		        product.setFaceValue(rs.getDouble(14));
		        product.setCode(rs.getString(15));
		       
		        product.setDatedDate(rs.getString(16));
		        product.setTenor(rs.getString(17));
		        product.setAttributes(rs.getString(18));
		        product.setIsPosition(rs.getInt(19));
		        product.setCollateralID(rs.getInt(20));
		        product.setCallType(rs.getString(21));
		        product.setRepoType(rs.getString(22));
		        product.setUnderlyingProductType(rs.getString(23));
		        product.setCurrency(rs.getString(24));
		        product.setProductSubType(rs.getString(25));
		        
	        commonUTIL.display("productSQL-----",SELECTONE + productID);
	        
	      
	       
	         
	         }
		 } catch (Exception e) {
			 commonUTIL.displayError("ProductSQL",SELECTONE + productID,e);
			 return product;
	           
	        }
	        finally {
	           try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				commonUTIL.displayError("ProductSQL",SELECTONE + productID,e);
			}
	        }
	        return product;
	 }

protected static Product selectProductWithCoupon(int productID,Connection con ) {
		 
		 int j = 0;
	        PreparedStatement stmt = null;
	        Product product = new Product();
	        
		 try {
			
			 stmt = dsSQL.newPreparedStatement(con, SELECTONE + productID);
	         
	         ResultSet rs = stmt.executeQuery();
	         
	         while(rs.next()) {
	        
	     	    product.setId(rs.getInt(1));
		        product.setProductType(rs.getString(2));
		        product.setProductname(rs.getString(3));
		        product.setProdcutShortName(rs.getString(4));
		        product.setQuantity(rs.getInt(5));
		        product.setIssueDate(rs.getString(6));
		        product.setMarturityDate(rs.getString(7));
		        product.setIssuerId(rs.getInt(8));
		        product.setCountry(rs.getString(9));
		        product.setIssuePrice(rs.getDouble(10));
		        product.setIssueCurrency(rs.getString(11));
		        product.setRedemptionPrice(rs.getDouble(12));
		        product.setRedemptionCurrency(rs.getString(13));
		        product.setFaceValue(rs.getDouble(14));
		        product.setCode(rs.getString(15));
		       
		        product.setDatedDate(rs.getString(16));
		        product.setTenor(rs.getString(17));
		        product.setAttributes(rs.getString(18));
		        product.setIsPosition(rs.getInt(19));
		        product.setCollateralID(rs.getInt(20));
		        product.setCallType(rs.getString(21));
		        product.setRepoType(rs.getString(22));
		        product.setUnderlyingProductType(rs.getString(23));
		        product.setCurrency(rs.getString(24));
		        product.setProductSubType(rs.getString(25));
	        commonUTIL.display("productSQL-----",SELECTONE + productID);
	        
	      
	       
	         
	         }
		 } catch (Exception e) {
			 commonUTIL.displayError("ProductSQL",SELECTONE + productID,e);
			 return product;
	           
	        }
	        finally {
	           try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				commonUTIL.displayError("ProductSQL",SELECTONE + productID,e);
			}
	        }
	        return product;
	 }

	
	 
protected static Product select(String productName,Connection con ) {
		 
		 int j = 0;
	        PreparedStatement stmt = null;
	        Product product = new Product();
	        String sql = "";
		 try {
			 sql = selectExisitingName + " where productName = '"+productName.trim() + "' order by PRODUCTSHORTNAME, id " ;
			
			 stmt = dsSQL.newPreparedStatement(con, sql);
	         
	         ResultSet rs = stmt.executeQuery();
	         
	         while(rs.next()) {
	        
	        product.setId(rs.getInt(1));
	        product.setProductType(rs.getString(3));
	        product.setProductname(rs.getString(2));
	        product.setProdcutShortName(rs.getString(4));
	        product.setProductSubType(rs.getString(5));
	        
	         }
		 } catch (Exception e) {
			 commonUTIL.displayError("ProductSQL"," select " + sql,e);
			 return product;
	           
	        }
	        finally {
	           try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				commonUTIL.displayError("ProductSQL"," select " + sql,e);
			}
	        }
	        return product;
	 }

	 
	 protected static Collection select(Connection con) { 
		 int j = 0;
	     PreparedStatement stmt = null;
	     Vector products = new Vector();
	     
		 try {
			
			 stmt = dsSQL.newPreparedStatement(con, SELECTALL );
	      
	      ResultSet rs = stmt.executeQuery();
	      System.out.println(rs.getMetaData());
	      while(rs.next()) {
	    	  
	    	  Product product = new Product();
	    	  
	    	    product.setId(rs.getInt(1));
		        product.setProductType(rs.getString(2));
		        product.setProductname(rs.getString(3));
		        product.setProdcutShortName(rs.getString(4));
		        product.setQuantity(rs.getInt(5));
		        product.setIssueDate(rs.getString(6));
		        product.setMarturityDate(rs.getString(7));
		        product.setIssuerId(rs.getInt(8));
		        product.setCountry(rs.getString(9));
		        product.setIssuePrice(rs.getDouble(10));
		        product.setIssueCurrency(rs.getString(11));
		        product.setRedemptionPrice(rs.getDouble(12));
		        product.setRedemptionCurrency(rs.getString(13));
		        product.setFaceValue(rs.getDouble(14));
		        product.setCode(rs.getString(15));
		       
		        product.setDatedDate(rs.getString(16));
		        product.setTenor(rs.getString(17));
		        product.setAttributes(rs.getString(18));
		        product.setIsPosition(rs.getInt(19));
		        product.setCollateralID(rs.getInt(20));
		        product.setCallType(rs.getString(21));
		        product.setRepoType(rs.getString(22));
		        product.setUnderlyingProductType(rs.getString(23));
		        product.setCurrency(rs.getString(24));
		        product.setProductSubType(rs.getString(25));
		        products.add(product);
		       
	      }
	      commonUTIL.display("productSQL",SELECTALL);
		 } catch (Exception e) {
			 commonUTIL.displayError("productSQL","select",e);
			 return products;
	        
	     }
	     finally {
	        try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				commonUTIL.displayError("productSQL","selectMax",e);
			}
	     }
	     return products;
	 }
	 
	 public static Collection selectonWhereClause(String sqlw,Connection con) { 
		 int j = 0;
	     PreparedStatement stmt = null;
	     Vector products = new Vector();
	     String sql= SELECTWHRE + sqlw;
		 try {
			commonUTIL.display("ProdcutSQL ", "selectonWhereClause"+sql);
			 stmt = dsSQL.newPreparedStatement(con, sql );
	      
	      ResultSet rs = stmt.executeQuery();
	   //   System.out.println(rs.getMetaData());
	      while(rs.next()) {
	    	  
	    	  Product product = new Product();
	    	  
	    	  product.setId(rs.getInt(1));
		        product.setProductType(rs.getString(2));
		        product.setProductname(rs.getString(3));
		        product.setProdcutShortName(rs.getString(4));
		        product.setQuantity(rs.getInt(5));
		        product.setIssueDate(rs.getString(6));
		        product.setMarturityDate(rs.getString(7));
		        product.setIssuerId(rs.getInt(8));
		        product.setCountry(rs.getString(9));
		        product.setIssuePrice(rs.getDouble(10));
		        product.setIssueCurrency(rs.getString(11));
		        product.setRedemptionPrice(rs.getDouble(12));
		        product.setRedemptionCurrency(rs.getString(13));
		        product.setFaceValue(rs.getDouble(14));
		        product.setCode(rs.getString(15));
		       
		        product.setDatedDate(rs.getString(16));
		        product.setTenor(rs.getString(17));
		        product.setAttributes(rs.getString(18));
		        product.setIsPosition(rs.getInt(19));
		        product.setCollateralID(rs.getInt(20));
		        product.setCallType(rs.getString(21));
		        product.setRepoType(rs.getString(22));
		        product.setUnderlyingProductType(rs.getString(23));
		        product.setCurrency(rs.getString(24));
		        product.setProductSubType(rs.getString(25));
		        product.setCoupon(CouponSQL.getcouponOnProduct(product.getId(), dsSQL.getConn()));
		        products.add(product);
		       
	      }
	      commonUTIL.display("productSQL",sql);
		 } catch (Exception e) {
			 commonUTIL.displayError("productSQL","select",e);
			 return products;
	        
	     }
	     finally {
	        try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				commonUTIL.displayError("productSQL","selectMax",e);
			}
	     }
	     return products;
	 }
	 
	 public static Product selectonWhereClauseOnProductSubType(String productType,String productSubType,Connection con) { 
		 int j = 0;
	     PreparedStatement stmt = null;
	     Product product = new Product();
	     String sql= SELECTWHRE + " producttype ='"+productType+"' and productname like '"+productSubType+"'";
		 try {
			commonUTIL.display("ProdcutSQL ", "selectonWhereClause  "+sql);
			 stmt = dsSQL.newPreparedStatement(con, sql );
	      
	      ResultSet rs = stmt.executeQuery();
	   //   System.out.println(rs.getMetaData());
	      while(rs.next()) {
	    	  
	    	
	    	  product.setId(rs.getInt(1));
		        product.setProductType(rs.getString(2));
		        product.setProductname(rs.getString(3));
		        product.setProdcutShortName(rs.getString(4));
		        product.setQuantity(rs.getInt(5));
		        product.setIssueDate(rs.getString(6));
		        product.setMarturityDate(rs.getString(7));
		        product.setIssuerId(rs.getInt(8));
		        product.setCountry(rs.getString(9));
		        product.setIssuePrice(rs.getDouble(10));
		        product.setIssueCurrency(rs.getString(11));
		        product.setRedemptionPrice(rs.getDouble(12));
		        product.setRedemptionCurrency(rs.getString(13));
		        product.setFaceValue(rs.getDouble(14));
		        product.setCode(rs.getString(15));
		       
		        product.setDatedDate(rs.getString(16));
		        product.setTenor(rs.getString(17));
		        product.setAttributes(rs.getString(18));
		        product.setIsPosition(rs.getInt(19));
		        product.setCollateralID(rs.getInt(20));
		        product.setCallType(rs.getString(21));
		        product.setRepoType(rs.getString(22));
		        product.setUnderlyingProductType(rs.getString(23));
		        product.setCurrency(rs.getString(24));
		        product.setProductSubType(rs.getString(25));

	      }
	      commonUTIL.display("productSQL selectonWhereClauseOnProductSubType ",sql);
		 } catch (Exception e) {
			 commonUTIL.displayError("productSQL","selectonWhereClauseOnProductSubType",e);
			 return product;
	        
	     }
	     finally {
	        try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				commonUTIL.displayError("productSQL","selectonWhereClauseOnProductSubType",e);
			}
	     }
	     return product;
	 }
	 public static Product getFutureProduct(int productID,Connection con) { 
		 int j = 0;
	     PreparedStatement stmt = null;
	     Product product = new Product();
	     String sql= selectFutureProduct + productID +  "))";
		 try {
		//	commonUTIL.display("ProdcutSQL ", "getFutureProduct  "+sql);
			 stmt = dsSQL.newPreparedStatement(con, sql );
	      
	      ResultSet rs = stmt.executeQuery();
	   //   System.out.println(rs.getMetaData());
	      while(rs.next()) {
	    	  
	    	
	    	  product.setId(rs.getInt(1));
		        product.setProductType(rs.getString(2));
		        product.setProductname(rs.getString(3));
		        product.setProdcutShortName(rs.getString(4));
		        product.setQuantity(rs.getInt(5));
		        product.setIssueDate(rs.getString(6));
		        product.setMarturityDate(rs.getString(7));
		        product.setIssuerId(rs.getInt(8));
		        product.setCountry(rs.getString(9));
		        product.setIssuePrice(rs.getDouble(10));
		        product.setIssueCurrency(rs.getString(11));
		        product.setRedemptionPrice(rs.getDouble(12));
		        product.setRedemptionCurrency(rs.getString(13));
		        product.setFaceValue(rs.getDouble(14));
		        product.setCode(rs.getString(15));
		       
		        product.setDatedDate(rs.getString(16));
		        product.setTenor(rs.getString(17));
		        product.setAttributes(rs.getString(18));
		        product.setIsPosition(rs.getInt(19));
		        product.setCollateralID(rs.getInt(20));
		        product.setCallType(rs.getString(21));
		        product.setRepoType(rs.getString(22));
		        product.setUnderlyingProductType(rs.getString(23));
		        product.setCurrency(rs.getString(24));
		        product.setProductSubType(rs.getString(25));

	      }
	      commonUTIL.display("productSQL getFutureProduct ",sql);
		 } catch (Exception e) {
			 commonUTIL.displayError("productSQL","getFutureProduct",e);
			 return product;
	        
	     }
	     finally {
	        try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				commonUTIL.displayError("productSQL","getFutureProduct",e);
			}
	     }
	     return product;
	 }
	 private static Coupon getCoupon(int productID,Connection con) {
		 return (Coupon) CouponSQL.getcouponOnProduct(productID, con);
	 }
}
