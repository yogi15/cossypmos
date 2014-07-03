package beans;

public class HelperFXBean implements HelperBean {

	@Override
	public Deal getBean(String[] record) {
		// TODO Auto-generated method stub
		DealFXBean dealBean = new DealFXBean();
		dealBean.setMemberName(record[0]);
		dealBean.setMember(record[1]);
		dealBean.setDEALERname(record[2]);
		dealBean.setDealer(record[3]);
		dealBean.setTradeNumber(record[4]);
		dealBean.setTradeDate(record[5]);
		dealBean.setTradeTime(record[6]);
		dealBean.setTradeType(record[7]);
		dealBean.setMaturityDate(record[8]);
		dealBean.setCurrency(record[9]);
		dealBean.setType(record[10]);
		dealBean.setCurrencyPair(record[11]);
		dealBean.setSpotPrice(record[12]);
		dealBean.setQuantity(record[13]);
	//	dealBean.setType(record[10]);
		
		return dealBean;
	}

}
