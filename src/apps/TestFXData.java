package apps;

import java.io.IOException;
import java.util.Collection;
import java.util.Properties;
import java.util.Vector;

import util.commonUTIL;
import util.csvReader.CSVFileHandler;
import beans.Attribute;
import beans.Deal;
import beans.DealBean;
import beans.DealFXBean;
import beans.HelperDealBean;
import beans.HelperFXBean;
import beans.Trade;
import dsServices.RemoteProduct;
import dsServices.RemoteReferenceData;
import dsServices.RemoteTrade;
import dsServices.ServerConnectionUtil;

public class TestFXData {

	public static void main(String args[]) {
		ServerConnectionUtil de = null;
		String CFG_FILE = "application.properties";
		RemoteReferenceData remoteReference = null;
		RemoteTrade remoteTrade = null;
		RemoteProduct remoteproduct = null;
		de = ServerConnectionUtil.connect("localhost", 1099, "127.0.0.1");
		try {

			remoteReference = (RemoteReferenceData) de
					.getRMIService("ReferenceData");
			remoteTrade = (RemoteTrade) de.getRMIService("Trade");
			remoteproduct = (RemoteProduct) de.getRMIService("Product");

			DealSender dealSender = new DealSender();
			dealSender.setRemoteTrade(remoteTrade);
			dealSender.setRemoteProduct(remoteproduct);
			dealSender.setRemoteRefernce(remoteReference);
			Properties prop = new Properties();

			prop.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(CFG_FILE));
			String path = prop.getProperty("pathFXfile");
			java.util.Vector<Deal> beans = CSVFileHandler.read(path,
					new HelperFXBean());

			DealFXBean fxBean = null;
			for (int i = 0; i < beans.size(); i++) {

				fxBean = (DealFXBean) beans.get(i);
				
				Trade trade = dealSender.buildFXTrade(fxBean);
				
				java.util.Vector message = new java.util.Vector();

				if (trade.getTradedesc1().equals("FXTAKEUP")) {

					String whereString = " ATTRIBUTENAME = 'External ID' and ATTRIBUTEVALUE = '" + fxBean.getExternalParentID() +"'";
					Vector<Attribute> attribute = (Vector<Attribute>) remoteReference
							.selectWhereAttribute(whereString);

					if (attribute.size() == 1) {
						
						Attribute attr = attribute.get(0);
						
						int parentId = attr.getId();
						
						String attributeString = trade.getAttributes()
								+ "ParentID=" + parentId + ";";
						
						trade.setAttributes(attributeString);
						trade.setParentID(parentId);
						
					} else if (attribute.size() > 1) { 

						commonUTIL.display("Trade not saved with Id ", ""
								+ ">>>> More than one Parent Trade found");
					} else {
						
						commonUTIL.display("Trade not saved with Id ", ""
								+ ">>>> Problem with External Id");
						
					}
				} 
				message = remoteTrade.saveTrade(trade, message);

				int tradeid = ((Integer) message.elementAt(1)).intValue();

				System.out.println("Trade saved with id " + tradeid);
				trade = remoteTrade.selectTrade(tradeid);
				trade.setAction("EXECUTE");
				message = remoteTrade.saveTrade(trade, message);
				System.out.println("Trade saved with id " + tradeid);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
