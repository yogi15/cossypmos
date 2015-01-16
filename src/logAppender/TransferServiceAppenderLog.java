package logAppender;

import org.apache.log4j.Logger;

import constants.logConstants;

public class TransferServiceAppenderLog {
	
	static Logger logger = Logger.getLogger(
			"TransferServiceAppenderLogger");
	
	public static void printLog(String logType, String msg) {

		switch (logConstants.logTypes.get(logType)) {

		case 1:
			logger.info(msg);
			return;
		case 2:
			logger.debug(msg);			
			return;		
		case 3:
			logger.error(msg);			
			return;

		}

	}
	


}
