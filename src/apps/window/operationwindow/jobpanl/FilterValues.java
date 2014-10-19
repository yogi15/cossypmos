package apps.window.operationwindow.jobpanl;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JComboBox;

import beans.Book;
import beans.FilterBean;
import beans.LegalEntity;
import beans.StartUPData;
import beans.Task;
import beans.Trade;
import beans.Transfer;
import beans.UserJobsDetails;

import util.commonUTIL;

import dsServices.RemoteBOProcess;
import dsServices.RemoteReferenceData;
import dsServices.RemoteTask;
import dsServices.RemoteTrade;

public class FilterValues {

	Hashtable<String, Vector> dataValues = null;
	String starupData[] = { "SearchCriteria", "TaskColumn", "Status",
			"ProductType", "Currency", "EventType", "WFType", "BUY/SELL",
			"TransferType", "FEEType", "accEvent", "TaskType",
			"TradeAttribute", "QuotingCurr", "PrimaryCurr", "LEAttributes",
			"BookAttributes" };
	String referenceData[] = { "Book" };
	String datesSearch[] = { "between", ">=", ">", "<=", "<", "=" };
	Hashtable<Integer, Book> bookValues = new Hashtable<Integer, Book>();
	static Hashtable<String, String> attributesTableName = new Hashtable<String, String>();
	static Hashtable<String, String> attributesTableWhereClause = new Hashtable<String, String>();
	Hashtable<Integer, LegalEntity> counterPary = new Hashtable<Integer, LegalEntity>();
	static Hashtable<String, String> columnNames = new Hashtable<String, String>();
	static Hashtable<String, String> numberDataTypes = new Hashtable<String, String>();
	static Hashtable<String, String> matachingColumns = new Hashtable<String, String>();
	static Hashtable<String, String> tableNames = new Hashtable<String, String>();
	static Hashtable<String, String> forwardColumnMaps = new Hashtable<String, String>();
	static Hashtable<String, String> replaceColumnNameOnSQL = new Hashtable<String, String>();
	static Hashtable<String, String> attributesWhereClause = new Hashtable<String, String>();
	String attributesObject[] = { "tradeattribute", "legalentityattribute",
			"bookattribute", "Book.Attributes", "Transfer.attributes" }; // used
																			// for
																			// attributes
																			// only.
	RemoteTrade remoteTrade = null;
	RemoteBOProcess remoteBO = null;
	RemoteTask remoteTask = null;
	RemoteReferenceData remote = null;
	static {
		// add attributseTables
		attributesTableName.put("tradeattribute", "attribute tradeattribute");
		attributesTableName.put("legalentityattribute",
				"leattribute legalentityattribute ");
		attributesTableName.put("bookattribute",
				"bookattributes Bookattributes ");
		attributesTableName.put("Book.Attributes", "Book book");
		attributesTableName.put("Transfer.attributes", "Transfer transer");

		attributesTableWhereClause.put("LegalEntity.Attributes",
				"t.cpid = leattribute.le_id and leattribute.attributeValue =");

		attributesWhereClause.put("TradeKeyword",
				"trade.id = tradeattribute.id  ");
		attributesWhereClause.put("LeKeyword",
				" legalentity.id = legalentityattribute.le_id  ");

		/*replaceColumnNameOnSQL.put("Trade.DeliveryDate",
				"to_char(Trade.DeliveryDate,'dd/mm/yyyy') DeliveryDate");
		replaceColumnNameOnSQL.put("Trade.EffectiveDate",
		"to_char(Trade.DeliveryDate,'dd/mm/yyyy') EffectiveDate");*/
		replaceColumnNameOnSQL.put("Trade.TradeDate",
				"to_char(Trade.TradeDate,'dd/mm/yyyy HH:MM:ss') TradeDate");
		replaceColumnNameOnSQL.put("Trade.BaseCurrency",
				"substr(Trade.tradedesc,0,3) BaseCurrency");
		replaceColumnNameOnSQL.put("Trade.Type", "Trade.Type Direction ");
		replaceColumnNameOnSQL.put("Trade.ProductType",
				"Trade.tradedesc1 ProductType");
		replaceColumnNameOnSQL.put("Trade.FX.NEAR_AMT1", "Trade.quantity FX_NEAR_AMT1");
		replaceColumnNameOnSQL.put("Trade.FX.NEAR_AMT2", "Trade.nominal FX_NEAR_AMT2");
		replaceColumnNameOnSQL.put("Trade.FX_FAR_AMT1", "Trade.amount FX_FAR_AMT1");
		replaceColumnNameOnSQL.put("Trade.FX_FAR_AMT2", "Trade.yield FX_FAR_AMT2");
		replaceColumnNameOnSQL.put("Trade.Price", "Trade.Price Rate ");
		replaceColumnNameOnSQL.put("Trade.Bond.Amount", "Trade.Nominal Bond_Amount");
		replaceColumnNameOnSQL.put("Trade.Broker", "(select NVL(NAME,'') from LE where id= Trade.BrokerId)Broker_Name");
		replaceColumnNameOnSQL.put("Trade.Bond.Description","Trade.TradeDesc Bond_Description");
		replaceColumnNameOnSQL.put("Trade.FX.CurrencyPair","Trade.TradeDesc FX_CUrrencyPair");
		replaceColumnNameOnSQL.put("Trade.Bond.Nominal","Trade.Nominal Bond_Quantity");
		replaceColumnNameOnSQL.put("Trade.Bond.Quantity","Trade.Quantity Bond_Quantity");
		replaceColumnNameOnSQL.put("Trade.FX.FAR_EndDate","to_char(Trade.EffectiveDate,'dd/mm/yyyy') FX_FAR_EndDate");
		replaceColumnNameOnSQL.put("Trade.FX.FarRate","Trade.SECONDETRADEPRICE FX_FAR_Rate"); 
		replaceColumnNameOnSQL.put("Trade.FX.NearRate","Trade.price FX_NEAR_Rate");
		replaceColumnNameOnSQL.put("Trade.FX.QuoteCCY","Trade.currency FX_QuoteCCY");
		
		replaceColumnNameOnSQL
				.put("Posting.CreditAccId",
						" (select accountname from ACCOUNT where id = Posting.CreditAccId) CreditAcc");
		replaceColumnNameOnSQL
				.put("Posting.DebitAccId",
						" (select accountname from ACCOUNT where id = Posting.DebitAccId) DebitAcc");

		forwardColumnMaps.put("Monthly", "TO_CHAR(settledate,'MON')");
		forwardColumnMaps.put("Daily", "TO_CHAR(settledate,'DD')");
		forwardColumnMaps.put("Weekly", "TO_CHAR(settledate,'WW')");
		forwardColumnMaps.put("Yearly", "TO_CHAR(settledate,'YYYY')");
		forwardColumnMaps.put("Quarterly", "TO_CHAR(settledate,'Q')");

		forwardColumnMaps
				.put("TO_CHAR(settledate,'Q')",
						" decode(TO_CHAR(settledate,'Q'),'1','31 MARCH','2','30 JUNE', '3','30 SEPT','4','31 DEC') Quarterly ");
		forwardColumnMaps.put("TO_CHAR(settledate,'MON')",
				"TO_CHAR(settledate,'MON') Monthly");
		forwardColumnMaps.put("TO_CHAR(settledate,'WW')",
				"TO_CHAR(settledate,'WW') Weekly");

		columnNames.put("Book", "Bookid");
		columnNames.put("Type", "Type");
		columnNames.put("LegalEntity", "id");
		columnNames.put("cpid", "cpid");
		columnNames.put("Currency", "Currency");
		columnNames.put("SettleCurrency", "Currency");
		columnNames.put("Action", "action");
		columnNames.put("Status", "Status");
		columnNames.put("ProductType", "ProductType");
		columnNames.put("EventType", "EventType");
		columnNames.put("WFConfig", "EventType");
		columnNames.put("WFType", "WFType");
		columnNames.put("BUY/SELL", "Type");
		columnNames.put("TradeID", "tradeid");
		columnNames.put("Amount", "amount");
		columnNames.put("TaskType", "taskType");
		columnNames.put("TaskType", "taskType");
		columnNames.put("TradeDate", "tradeDate");
		columnNames.put("EffectiveDate", "effectiveDate");
		columnNames.put("DeliveryDate", "deliveryDate");
		columnNames.put("CounterParty", "cpid");
		columnNames.put("SettleDate", "SettleDate");
		columnNames.put("PrimaryCurr", "PrimaryCurr");
		columnNames.put("QuotingCurr", "QuotingCurr");
		columnNames.put("Quantity", "Quantity");
		columnNames.put("TaskDate", "TaskDate");
		columnNames.put("Monthly", "TO_CHAR(settledate,'MON')");
		columnNames.put("TradeID", "ID");
		numberDataTypes.put("Book", "Bookid");
		numberDataTypes.put("LegalEntity", "id");
		numberDataTypes.put("Task", "id");
		numberDataTypes.put("Trade", "id");
		numberDataTypes.put("Transfer", "id");
		numberDataTypes.put("Posting", "id");
		numberDataTypes.put("TradeID", "tradeid");
		numberDataTypes.put("Amount", "amount");
		numberDataTypes.put("Quantity", "Quantity");
		numberDataTypes.put("cpid", "cpid");

		matachingColumns.put("PrimaryCurr", "Currency");
		matachingColumns.put("QuotingCurr", "Currency");

		tableNames.put("Trade", "Trade");
		tableNames.put("Transfer", "Transfer");
		tableNames.put("CashPosition", "Cashposition");
		tableNames.put("ForwardLadder", "Cashposition");
		tableNames.put("OpenPos", "OpenPos");
		tableNames.put("Posting", "Posting");
		tableNames.put("Product", "Product");
		tableNames.put("Coupon", "Coupon");
		tableNames.put("Book", "Book");
		tableNames.put("Le", "Le");
		tableNames.put("Trader", "Le");
		tableNames.put("PNL", "Liquidpos");

		// this used when where statement is having table alias but table string
		// don't have table name
		tableNames.put("legalEntity", "Le"); // this to be removed.
		tableNames.put("LegalEntity", "Le");
		// tableNames.put("Cashposition", "Cashposition");

	}

	public String getTableName(String tableName) {
		return tableNames.get(tableName);
	}

	public FilterValues(RemoteReferenceData remote, RemoteTrade remoteTrade,
			RemoteTask remoteTask, RemoteBOProcess remoteBO) {
		this.remote = remote;
		this.remoteTask = remoteTask;
		this.remoteTrade = remoteTrade;
		this.remoteBO = remoteBO;
		dataValues = new Hashtable<String, Vector>();
		for (int i = 0; i < starupData.length; i++) {
			getValuesonColumn(starupData[i], remote);
		}
		dataValues.put("Book", getBooks(remote));
		dataValues.put("CounterParty", getCounterParty(remote));
		dataValues.put("QuotingCurr", dataValues.get("Currency"));
		dataValues.put("PrimaryCurr", dataValues.get("Currency"));
		dataValues.put("Book.Attributes", dataValues.get("BookAttributes"));
		dataValues
				.put("LegalEntity.Attributes", dataValues.get("LEAttributes"));

	}

	public String[] getDateSearchCriteria() {
		return datesSearch;
	}

	public Vector getDomainValues(String domainName) {
		Vector domainData = null;
		domainData = dataValues.get(domainName);
		if (domainData == null) {

			try {
				domainData = (Vector) remote.getStartUPData(domainName);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				commonUTIL.display("FilterValues", "getDomainValues");
			}
			if (domainData != null) {
				dataValues.put(domainName, domainData);
			}
		}
		return domainData;
	}

	public Vector getValuesonColumn(String name, RemoteReferenceData remote1) {
		Vector data = null;
		boolean duplicateColumn = false;
		String duplicateColumnName = "";
		if (remote == null) {

			return data;
		}

		if (dataValues.containsKey(name)) {
			data = dataValues.get(name);
		} else {
			duplicateColumnName = matachingColumns.get(name);
			if (!commonUTIL.isEmpty(duplicateColumnName))
				duplicateColumn = true;
		}

		if (data == null) {
			try {
				if (duplicateColumn) {
					data = (Vector) dataValues.get(duplicateColumnName);
					if (data == null)
						data = (Vector) remote
								.getStartUPData(duplicateColumnName);
				} else {
					data = (Vector) remote.getStartUPData(name);
				}
				synchronized (dataValues) {
					dataValues.put(name, data);
				}
			} catch (Exception e) {
				commonUTIL.displayError("JobsPanel ", "FilterValues", e);
			}

		}

		convertVectortoSringArray(data, name);

		return data;

	}

	public String[] convertVectortoSringArrayForBook(Vector v, String objectType) {

		String name[] = null;
		int i = 0;
		name = stringarray.get("Book");
		if (name != null)
			return name;
		else if (v != null) {
			name = new String[v.size() + 1];
			Iterator its = v.iterator();
			name[i] = "Seleced Item";
			i = 1;
			while (its.hasNext()) {

				name[i] = ((Book) its.next()).getBook_name();
				i++;
			}
		}
		stringarray.put("Book", name);
		return name;
		// TODO add your handling code here:
	}

	public String[] convertVectortoSringArrayForCounterParty(Vector v,
			String objectType) {

		String name[] = null;
		int i = 0;
		name = stringarray.get("CounterParty");
		if (name != null)
			return name;
		else if (v != null) {
			name = new String[v.size() + 1];
			Iterator its = v.iterator();
			name[i] = "Seleced Item";
			i = 1;
			while (its.hasNext()) {

				name[i] = ((LegalEntity) its.next()).getName();
				i++;
			}
		}
		stringarray.put("CounterParty", name);
		return name;
		// TODO add your handling code here:
	}

	public String[] convertVectortoSringArray(Vector v, String sname,
			int firstItem) {

		String name[] = null;
		int i = 0;
		name = stringarray.get(sname);
		if (name != null)
			return name;
		else if (v != null) {
			name = new String[v.size() + 1];
			Iterator its = v.iterator();
			name[i] = "Seleced Item";
			i = 1;
			while (its.hasNext()) {

				name[i] = ((StartUPData) its.next()).getName();
				i++;
			}
		}
		stringarray.put(sname, name);
		return name;
		// TODO add your handling code here:
	}

	public String[] convertVectortoSringArray(Vector v, String sname) {

		String name[] = null;
		int i = 0;
		name = stringarray.get(sname);
		if (name != null)
			return name;
		else if (v != null) {
			name = new String[v.size() + 1];
			name[i] = "Selected Values";
			i = 1;
			Iterator its = v.iterator();
			while (its.hasNext()) {

				name[i] = ((StartUPData) its.next()).getName();
				i++;
			}
		}
		stringarray.put(sname, name);
		return name;
		// TODO add your handling code here:
	}

	public void fillStartUPData(Vector data, JComboBox comb) {
		Iterator<StartUPData> its = data.iterator();
		while (its.hasNext()) {
			comb.addItem(((StartUPData) its.next()).getName());

		}

	}

	public Book getBook(int bookId) {
		Vector books = dataValues.get("Book");
		Book book = (Book) books.get(bookId);
		return book;
	}

	public LegalEntity geCounterParty(int cpID) {
		Vector counterParty = dataValues.get("CounterParty");
		LegalEntity le = (LegalEntity) counterParty.get(cpID);
		return le;
	}

	public Vector getActionOnTransfer(int transferID) {
		Vector actions = null;
		try {
			actions = (Vector) remoteBO.getOnlyAction(transferID);
			return actions;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public Transfer getTransfer(int transferID) {
		Transfer transfer = new Transfer();
		transfer.setId(transferID);
		try {
			return remoteBO.selectTransfer(transfer);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public Trade getTrade(int tradeId) {

		try {
			return remoteTrade.selectTrade(tradeId);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public Vector getActions(Trade trade) {
		Vector actions = null;
		try {
			actions = (Vector) remoteTrade.getOnlyAction(trade);
			return actions;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public void fillBookData(Vector data, JComboBox comb) {
		Iterator<Book> its = data.iterator();
		while (its.hasNext()) {
			comb.addItem(((Book) its.next()).getBook_name());

		}

	}

	public void fillCounterParty(Vector data, JComboBox comb) {
		Iterator<Book> its = data.iterator();
		while (its.hasNext()) {
			comb.addItem(((Book) its.next()).getBook_name());

		}

	}

	private Vector getCounterParty(RemoteReferenceData remote) {
		Vector counterParty = null;
		try {
			if (dataValues.containsKey("counterPary")) {
				counterParty = dataValues.get("counterPary");
			}
			if (counterParty == null) {
				counterParty = (Vector) remote.selectAllLs();
				dataValues.put("CounterParty", counterParty);
				convertVectortoSringArrayForCounterParty(counterParty,
						"CounterParty");
			}

			return counterParty;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			commonUTIL.displayError("FilterValues ", " getCounterParty", e);
			return null;
		}

	}

	private Vector getBooks(RemoteReferenceData remote) {
		Vector books = null;
		try {
			if (dataValues.containsKey("Book")) {
				books = dataValues.get("Book");
			}
			if (books == null) {
				books = (Vector) remote.selectALLBooks();
				dataValues.put("Book", books);
				convertVectortoSringArrayForBook(books, "Book");
			}

			return books;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			commonUTIL.displayError("FilterValues ", " getBooks", e);
			return null;
		}

	}

	public Vector getBooks() {
		Vector books = null;

		if (dataValues.containsKey("Book"))
			books = dataValues.get("Book");
		return books;
	}

	public Vector getLegalEntitys() {
		return dataValues.get("CounterParty");

	}

	Hashtable<String, String[]> stringarray = new Hashtable<String, String[]>();

	public String createWhere(Vector<FilterBean> jobdetails) {
		// TODO Auto-generated method stub
		String where = "";
		if (jobdetails == null || jobdetails.size() == 0
				|| jobdetails.isEmpty())
			return null;
		for (int i = 0; i < jobdetails.size(); i++) {
			FilterBean bean = (FilterBean) jobdetails.get(i);
			boolean isStaticRefData = true;
			if (bean.getColumnName().equalsIgnoreCase("Book")) {
				isStaticRefData = false;
				where = where
						+ createCriteriaOnBook(dataValues.get("Book"), bean);

			}
			if (bean.getColumnName().equalsIgnoreCase("CounterParty")) {
				isStaticRefData = false;
				where = where
						+ createCriteriaOnCounterParty(
								dataValues.get("CounterParty"), bean);

			} else if ((isStaticRefData)) {
				where = where + createCriteria(bean, "");
			}
			if (i != jobdetails.size() - 1) {
				if (commonUTIL.isEmpty(bean.getAnd_or()))
					where = where + " and ";
				else
					where = where + " " + bean.getAnd_or() + "  ";
			}

		}
		where = where + " and task_status not in ('2')";
		return where;
	}

	public String createWhereForAttribute(FilterBean bean, int count) {
		String where = "";
		if (count == 0) {
			where = where + " attribute" + count + ".attributename = '"
					+ bean.getColumnValues() + "' and attribute" + count
					+ ".attributeValue " + bean.getSearchCriteria() + " '"
					+ bean.getAnd_or() + "'";
			if (bean.getColumnName().contains("Trade")) {
				where = where + " and attribute" + count
						+ ".type = 'Trade' and attribute" + count
						+ ".id = Trade.id";
			}
		} else {
			where = where + " attribute" + count + ".attributename = '"
					+ bean.getColumnValues() + "' and attribute" + count
					+ ".attributeValue " + bean.getSearchCriteria() + " '"
					+ bean.getAnd_or() + "'";
			if (bean.getColumnName().contains("Trade")) {
				where = where + " and attribute" + count
						+ ".type = 'Trade'  and attribute" + count
						+ ".id = Trade.id";
			}
		}
		return where.trim();
	}

	public boolean isObjectAttribute(String objectAndAttributeName) {
		boolean flag = false;
		for (int i = 0; i < attributesObject.length; i++) {
			String objAttributeNme = (String) attributesObject[i];
			if (objAttributeNme.equalsIgnoreCase(objectAndAttributeName)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	public String createWhere(Vector<FilterBean> jobdetails, String tableName1) {
		// TODO Auto-generated method stub
		String where = "";
		Vector<FilterBean> attributesFilterBeans = new Vector<FilterBean>();
		boolean isForwardLadder = false;
		boolean isAttribute = false;
		int att = 0;
		if (jobdetails == null || jobdetails.size() == 0
				|| jobdetails.isEmpty())
			return where;
		for (int i = 0; i < jobdetails.size(); i++) {
			FilterBean bean = (FilterBean) jobdetails.get(i);
			if (bean.getColumnName().endsWith("Keyword")) {
				attributesFilterBeans.add(bean);
			} else {
				if (bean.getColumnName().equalsIgnoreCase("Ladder"))
					isForwardLadder = true;
				boolean isStaticRefData = true;
				if (isObjectAttribute(bean.getColumnName())) {
					where = where + " " + bean.getColumnName() + " "
							+ bean.getSearchCriteria() + " '%"
							+ bean.getColumnValues() + "=" + bean.getAnd_or()
							+ "%'";
				} else if (bean.getColumnName().equalsIgnoreCase("Book")) {
					where = where
							+ " "
							+ tableNames.get(tableName1).toLowerCase()
							+ "."
							+ createCriteriaOnBook(dataValues.get("Book"), bean);

				} else if (bean.getColumnName()
						.equalsIgnoreCase("CounterParty")) {
					isStaticRefData = false;
					where = where
							+ " "
							+ tableNames.get(tableName1).toLowerCase()
							+ "."
							+ createCriteriaOnCounterParty(
									dataValues.get("CounterParty"), bean);

				} else if (bean.getColumnName().equalsIgnoreCase(
						"TradeAttribute")) {
					where = where + " " + createWhereForAttribute(bean, att);
					att++;
					if (i != jobdetails.size() - 1) {
						where = where + " and ";
					}
				} else if (bean.getColumnName().endsWith("Date")) {

					where = where + "  trunc("
							+ tableNames.get(tableName1).toLowerCase() + "."
							+ createCriteria(bean, tableName1) ;

				} else {
					where = where + " "
							+ tableNames.get(tableName1).toLowerCase() + "."
							+ createCriteria(bean, tableName1);
				}
				if (i != jobdetails.size() - 1) {
					if (bean.getAnd_or() == null || bean.getAnd_or().isEmpty())
						where = where + " and ";
					else if (bean.getColumnName().endsWith("Date")) {
						where = where + " and ";
					} else {
						where = where + " " + bean.getAnd_or() + "  ";
					}
				}
			}

		}
		where = addAttributesFilters(attributesFilterBeans, where);
		if (tableName1.equalsIgnoreCase("trade"))
			where = where + " and trade.version >= 0 ";
		if (isForwardLadder) {
			where = genearteGroupClauseForForwardLadder(where);
		}
		return where;
	}

	private String addAttributesFilters(
			Vector<FilterBean> attributesFilterBeans, String where) {
		// TODO Auto-generated method stub attributesWhereClause
		for (int i = 0; i < attributesFilterBeans.size(); i++) {
			FilterBean bean = attributesFilterBeans.get(i);
			String whereClause = attributesWhereClause
					.get(bean.getColumnName());
			if (!where.isEmpty())
				where = where + " and " + bean.getColumnValues();
			else
				where = where + "  " + bean.getColumnValues();
			where = whereClause + " and " + where;
		}
		return where;
	}

	private String genearteGroupClauseForForwardLadder(String where) {
		// TODO Auto-generated method stub
		String whereC = where;
		String[] whereSplit = null;
		if (where.contains(";")) {
			whereSplit = whereC.split(";");
			whereC = whereSplit[0];
		}
		String sql = whereC;
		String groupClause = "";
		String[] whereConditions = sql.split("cashposition.");
		String whereClause = "";
		for (int i = 0; i < whereConditions.length; i++) {
			String w = whereConditions[i];
			if (!w.trim().isEmpty()) {
				if (w.contains("TO_CHAR")) {
					groupClause = " group by currency , " + w;
				} else {
					whereClause = whereClause + w;
				}
			}

		}
		whereClause = whereClause.trim();
		String wClause = "";
		if (whereSplit == null) {
			whereClause = whereClause.substring(0, whereClause.length() - 3)
					+ "  " + groupClause;
		} else {
			wClause = whereClause.substring(0, whereClause.length() - 3) + "  "
					+ groupClause + ";";
			for (int ws = 1; ws < whereSplit.length; ws++) {
				wClause = wClause
						+ whereClause.substring(0, whereClause.length() - 3)
						+ "  " + " group by currency , " + whereSplit[ws] + ";";
			}
			whereClause = wClause.substring(0, wClause.length() - 1);
		}
		return whereClause;
	}

	private String createCriteriaOnBook(Vector<Book> books, FilterBean bean) {
		String bookCriteria = "";
		String ids = bean.getColumnValues();
		if (books == null || books.size() == 0 || books.isEmpty())
			return null;
		if (ids == null)
			return null;
		if (ids.contains(",")) {
			String values[] = ids.split(",");
			for (int i = 0; i < values.length; i++) {
				String id = values[i].trim();
				Book book = books.elementAt(new Integer(id).intValue() - 1);
				bookCriteria = bookCriteria + book.getBookno() + ",";
			}
			bookCriteria = bookCriteria.substring(0, bookCriteria.length() - 1);
		} else {
			Book book = books.elementAt(new Integer(ids).intValue() - 1);
			bookCriteria = new Integer(book.getBookno()).toString();
		}
		ids = columnNames.get(bean.getColumnName())
				+ " "
				+ " "
				+ attachsqlTypeCrietria(bean.getSearchCriteria().trim(),
						bookCriteria, bean.getColumnName());
		return ids;

	}

	private String createCriteriaOnCounterParty(
			Vector<LegalEntity> counterPartys, FilterBean bean) {
		String leCriteria = "";
		String ids = bean.getIdSelected();
		if (counterPartys == null || counterPartys.size() == 0
				|| counterPartys.isEmpty())
			return null;
		if (ids == null)
			return null;
		if (ids.contains(",")) {
			String values[] = ids.split(",");
			for (int i = 0; i < values.length; i++) {
				String id = values[i].trim();
				LegalEntity le = counterPartys.elementAt(new Integer(id)
						.intValue() - 1);
				leCriteria = leCriteria + le.getId() + ",";
			}
			leCriteria = leCriteria.substring(0, leCriteria.length() - 1);
		} else {
			LegalEntity le = counterPartys.elementAt(new Integer(ids)
					.intValue() - 1);
			leCriteria = new Integer(le.getId()).toString();
		}
		ids = columnNames.get(bean.getColumnName())
				+ " "
				+ " "
				+ attachsqlTypeCrietria(bean.getSearchCriteria().trim(),
						leCriteria, bean.getColumnName());
		return ids;

	}

	private String attachsqlTypeCrietria(String criteriaType, String values,
			String columnName) {

		String typeC = "";
		if (criteriaType.equalsIgnoreCase("in")
				|| criteriaType.equalsIgnoreCase("not in")) {
			typeC = criteriaType + " ('";
			if (values.contains(",")) {
				// values =getFilterValues(values,"Books");
				String value[] = values.split(",");
				for (int i = 0; i < value.length; i++) {
					String id = value[i].trim();
					typeC = typeC + id + "','";
				}
				typeC = typeC.substring(0, typeC.length() - 2) + ")";
			} else {
				typeC = typeC + values + "')";
			}
		}
		if (criteriaType.equalsIgnoreCase("=")
				|| criteriaType.equalsIgnoreCase("!=")) {
			typeC = criteriaType + " '" + values + "'";
		}
		if (criteriaType.equalsIgnoreCase(">")
				|| criteriaType.equalsIgnoreCase("<")
				|| criteriaType.equalsIgnoreCase(">=")
				|| criteriaType.equalsIgnoreCase("<=")) {

			if (numberDataTypes.containsKey(columnName.trim())) {
				typeC = criteriaType + values;
			} else
				typeC = criteriaType + " '" + values + "'";
		}
		return typeC;

	}

	private String getForwardLadderWhereClause(String values) {

		// TODO Auto-generated method stub
		if (values.contains(",")) {
			String v[] = values.split(",");
			String value = "";

			for (int i = 0; i < v.length; i++)
				value = value + forwardColumnMaps.get(v[i]) + ";";
			value = value.substring(0, value.length() - 1);
			return value;
		}
		return forwardColumnMaps.get(values.trim());
	}

	private String createCriteria(FilterBean bean, String tableName1) {
		String criteria = "";
		if (bean.getColumnName().endsWith("Date")) {
			criteria = columnNames.get(bean.getColumnName())
					+ ") "
					+ " "
					+ getDatesWhereClause(
							bean.getColumnValues(),
							bean.getAnd_or(),
							bean.getSearchCriteria(),
							tableNames.get(tableName1) + "."
									+ columnNames.get(bean.getColumnName()));
		} else if (bean.getColumnName().equalsIgnoreCase("Ladder")) {
			criteria = getForwardLadderWhereClause(bean.getColumnValues());
		} else {
			criteria = columnNames.get(bean.getColumnName())
					+ " "
					+ " "
					+ attachsqlTypeCrietria(bean.getSearchCriteria().trim(),
							bean.getColumnValues().toUpperCase(),
							bean.getColumnName());
		}
		return criteria;

	}

	public String getDatesWhereClause(String startDate, String endDate,
			String searchCriteria, String columnName) {
		String dateWhereClause = "";
		if (searchCriteria.equalsIgnoreCase("between")) {
			dateWhereClause = " between  to_date('" + startDate.trim()
					+ "', 'dd/MM/YYYY')";
			dateWhereClause = dateWhereClause + " and to_date('"
					+ endDate.trim() + "', 'dd/MM/YYYY')";
		} else {
			dateWhereClause = searchCriteria + "  to_date('" + startDate.trim()
					+ "', 'dd/MM/YYYY')";
			if (commonUTIL.isEmpty(endDate))
				return dateWhereClause;
			if (searchCriteria.equalsIgnoreCase(">")) {
				dateWhereClause = dateWhereClause + " and " + columnName
						+ "   <" + "  to_date('" + endDate.trim()
						+ "', 'dd/MM/YYYY')";

			} else {
				dateWhereClause = dateWhereClause + " and " + columnName
						+ "  >" + "  to_date('" + endDate.trim()
						+ "', 'dd/MM/YYYY')";
			}
		}

		return dateWhereClause;

	}

	public String getids(String columnValues, String values) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getFilterValues(String ids, String columnName) {
		// TODO Auto-generated method stub
		String bookIds = "";
		if (ids == null || ids.isEmpty())
			return null;
		int start = ids.indexOf("[") + 1;
		int end = ids.indexOf("]");
		String id = ids.substring(start, end);

		return id;

	}

	public void updateTransferAndPublissh(Transfer transfer, int userID) {
		// TODO Auto-generated method stub
		try {
			transfer.setUserid(userID);
			remoteBO.updateTransferAndPublish(transfer, userID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int updateTraderAndPublissh(Trade trade, int userID) {
		// TODO Auto-generated method stub
		try {
			trade.setUserID(userID);
			return remoteTrade.saveTrade(trade);

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}

	}

	public Collection getNettedTransfers(int transferID) {
		Vector nettedTransfer = null;
		try {
			nettedTransfer = (Vector) remoteBO.getNettedTransfers(transferID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nettedTransfer;
	}

	public void savePublishTask(Task task, String status) {
		// TODO Auto-generated method stub
		try {
			remoteTask.updateTaskStatus(task, task.getUserid(),
					task.getUser_name(), status);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			commonUTIL.displayError("FilterValues", "savePublishTask", e);
		}

	}

	private String addTableNameIfrequired(String columnSQL) {

		int selectIndex = columnSQL.indexOf("select");
		String sql = "Select ";
		int fromIndex = columnSQL.indexOf("from");
		int whereIndex = columnSQL.indexOf("where");
		String sqlColumn = columnSQL.substring(selectIndex + 6, fromIndex);
		String[] tablename = getArrayofRepeatedString(
				columnSQL.substring(fromIndex + 4, whereIndex), ",");
		String whereClause[] = getArrayofRepeatedString(
				columnSQL.substring(whereIndex + 5, columnSQL.length()), "and");
		if (whereClause == null)
			return columnSQL;
		if (tablename == null) {
			String table = columnSQL.substring(fromIndex + 4, whereIndex);
			String newtables = table;
			for (int i = 0; i < whereClause.length; i++) {
				String where = whereClause[i];
				String tableinwhere = where.substring(0, where.indexOf("."));
				if (!table.trim().contains(tableinwhere.trim())) {
					newtables = newtables + ", "
							+ getTableName(tableinwhere.trim()) + " "
							+ tableinwhere;
				}

			}
			sql = sql + columnSQL.substring(selectIndex + 6, fromIndex)
					+ " from  " + newtables + " where "
					+ columnSQL.substring(whereIndex + 5, columnSQL.length());
		}

		// String

		return "";
	}

	private String[] getArrayofRepeatedString(String name, String regex) {
		if (!name.contains(regex))
			return null;
		String[] names = name.split(regex);
		return names;
	}

	// tradeAttribute.ISIN
	public String createWhereOnAttributes(String columnSQL, String where) {
		String querySQL = "";

		if (!columnSQL.contains("tradeAttribute")) {
			if (columnSQL.contains("where")) {
				querySQL = addTableNameIfrequired(columnSQL);
			} else {
				querySQL = addTableNameIfrequired(columnSQL + " where " + where);
			}
			return querySQL;
		}
		int counter = 0;
		String tablename = "";
		String whereClause = where;
		String sql = "Select ";
		String type = "Trade";
		int selectIndex = columnSQL.indexOf("select");
		int fromIndex = columnSQL.indexOf("from");
		int whereIndex = columnSQL.indexOf("where");
		String sqlColumn = columnSQL.substring(selectIndex + 6, fromIndex);
		if (whereIndex > 0) {
			tablename = columnSQL.substring(fromIndex, whereIndex);
			whereClause = " where " + whereClause + " and "
					+ columnSQL.substring(whereIndex + 5, columnSQL.length())
					+ " and ";
		} else {
			tablename = columnSQL.substring(fromIndex, columnSQL.length());
			if (commonUTIL.isEmpty(where)) {
				whereClause = " where "; // whereClause +
											// columnSQL.substring(whereIndex,
											// columnSQL.length());
			} else {
				whereClause = " where " + where + " and ";
			}
		}
		String columns[] = sqlColumn.split(",");
		for (int i = 0; i < columns.length; i++) {
			if (columns[i].contains("tradeAttribute")) {
				String attributeCol = columns[i].trim();
				String attributeName = attributeCol.replace("tradeAttribute.",
						"");

				attributeCol = attributeCol.replace("tradeAttribute.",
						"attribute" + counter + ".attributevalue ");
				tablename = tablename + ", Attribute attribute" + counter + " ";
				if (!whereClause.contains(attributeName)) {
					String whereC = getAttributeWhereAlias(counter, whereClause);
					tablename = tablename
							+ addNestedTableName(counter, tablename);
					whereClause = whereClause + " " + whereC + ".id = " + type
							+ ".id and  " + whereC + ".type='" + type
							+ "' and  " + whereC + ".attributename ='"
							+ attributeName + "'  and ";

				}
				counter = counter + 1;

				sql = sql + attributeCol + ",";
			} else {
				sql = sql + columns[i] + ",";
			}

		}
		sql = sql.trim().substring(0, sql.length() - 1);
		// TODO Auto-generated method stub
		whereClause = whereClause.substring(0, whereClause.lastIndexOf("and"));
		sql = sql + " " + tablename + " " + whereClause;
		return sql;
	}

	public String addNestedTableName(int counter, String tablenames) {
		String tablealias = "attribute" + counter;
		if (tablenames.contains(tablealias)) {
			tablealias = "";
		} else {
			tablealias = ", Attribute " + "attribute" + counter;
		}
		return tablealias;
	}

	public String getAttributeWhereAlias(int counter, String where) {
		int count = counter;
		String attributeAlias = "attribute" + count;
		if (checkAttribute(attributeAlias, where)) {
			count = count + 1;
			attributeAlias = getAttributeWhereAlias(count, where);
		}
		return attributeAlias;
	}

	public boolean checkAttribute(String alias, String where) {
		boolean flag = false;
		String attributeAlias = alias;
		if (where.contains(attributeAlias)) {
			return true;
		}
		return flag;

	}

	public String getColumnsForForwardLadder(Vector<FilterBean> filterBeanData) {
		// TODO Auto-generated method stub
		String sql = " select sum(actualamt) Total, Currency ,";
		String cols[] = null;
		for (int i = 0; i < filterBeanData.size(); i++) {
			FilterBean bean = (FilterBean) filterBeanData.get(i);
			if (bean.getColumnName().equalsIgnoreCase("Ladder")) {
				if (bean.getColumnValues().contains(",")) {
					cols = bean.getColumnValues().split(",");
					sql = sql + forwardColumnMaps.get(cols[0]);
				} else {
					sql = sql + forwardColumnMaps.get(bean.getColumnValues())
							+ ",";
				}
			} else if (bean.getColumnName().equalsIgnoreCase("SettleDate")) {

			} else if (bean.getColumnName().equalsIgnoreCase("Currency")) {

			} else {
				sql = sql + bean.getColumnName() + ",";
			}

		}

		if (cols == null) {
			sql = sql.substring(0, sql.length() - 1);
			sql = sql + " from cashposition ";
		} else {
			sql = sql + " from cashposition ";
			for (int c = 1; c < cols.length; c++)
				sql = sql + ";" + " select sum(actualamt) Total, Currency ,"
						+ forwardColumnMaps.get(cols[c])
						+ "  from cashposition ;";
		}

		return sql;
	}

	// this is to add attribute table name in sql if any attribute table name is
	// existing in whereclause.

	public String checkTableAliasForAttributes(String sql) {
		String where = sql.substring(sql.indexOf("where"), sql.length());
		String sqlwithoutWhere = sql.substring(0, sql.indexOf("where"));
		boolean containLegalEntityTable = false;
		boolean containBookTable = false;
		if (sqlwithoutWhere.contains("Le legalEntity"))
			containLegalEntityTable = true;
		if (sqlwithoutWhere.contains("Book book"))
			containBookTable = true;
		for (int i = 0; i < attributesObject.length; i++) {

			if (where.contains(attributesObject[i])) {
				String attributesTable = attributesTableName
						.get(attributesObject[i]);
				if (!sqlwithoutWhere.contains(attributesTable)) {
					if (containLegalEntityTable) {
						sqlwithoutWhere = sqlwithoutWhere + " , "
								+ attributesTable;
					} else {
						sqlwithoutWhere = sqlwithoutWhere
								+ " , le legalentity " + " , "
								+ attributesTable;
						where = where + " and trade.cpid = legalentity.id ";
					}
					if (containBookTable) {
						sqlwithoutWhere = sqlwithoutWhere + " , "
								+ attributesTable;
					} else {
						sqlwithoutWhere = sqlwithoutWhere + " , Book book "
								+ " , " + attributesTable;
						where = where + " and trade.bookid = book.bookno";
					}

				}
			}
		}
		return sqlwithoutWhere + " " + where;
	}

	public String changeColumnNameForForwoardReport(String sqlW) {
		// TODO Auto-generated method stub
		String sql = sqlW;
		Enumeration keys = forwardColumnMaps.keys();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			if (sqlW.contains(key)) {
				String value = (String) forwardColumnMaps.get(key);
				sql = sqlW.replace(key, value);
				break;

			}
		}
		return sql;

	}

	public String changeColumnNameForNormalReport(String sqlW) {
		// TODO Auto-generated method stub
		String sql = sqlW;
		Enumeration keys = replaceColumnNameOnSQL.keys();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			if (sql.contains(key)) {
				String value = (String) replaceColumnNameOnSQL.get(key);
				sql = sql.replace(key, value);
				// break;

			}
		}	
		return sql;
	}

}
