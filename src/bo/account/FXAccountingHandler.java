package bo.account;

import java.util.Collections;
import java.util.Vector;

import constants.FXConstants;
import constants.TradeConstants;

import productPricing.Pricer;
import dsEventProcessor.EventProcessor;
import dsEventProcessor.LiquidationEventProcessor;
import beans.AccConfigRule;
import beans.AccEventConfig;
import beans.Trade;

public class FXAccountingHandler extends AccountingHandler {

	private Vector<String> splitCurrency(String splitCurrency) {

		Vector<String> currency = new Vector<String>();

		String[] currencyArr = splitCurrency.split("/");

		Collections.addAll(currency, currencyArr);

		return currency;
	}

	public void getCONT(Trade trade, EventProcessor event,
			AccEventConfig eventConfig, Vector accountingEvents,
			AccConfigRule rule, Pricer pricer) {

		String fxType = trade.getTradedesc1();

		if (fxType.equals(FXConstants.FXFORWARD)
				|| fxType.equals(FXConstants.FXSWAP) || fxType.equals(FXConstants.FXFORWARDOPTION)) {

			Vector<String> splittedCurrency = splitCurrency(trade
					.getTradedesc());
			
			String tradeDate = trade.getTradeDate().substring(0, 10);
		
			BOPosting accEventBuy = new BOPosting(eventConfig);

			accEventBuy.setAmount(trade.getQuantity());
			accEventBuy.setEffectiveDate(tradeDate);
			accEventBuy.setBookingDate(tradeDate);
			accEventBuy.setCurrency(splittedCurrency.get(0));
			accountingEvents.addElement(accEventBuy);

			BOPosting accEventSell = new BOPosting(eventConfig);

			accEventSell.setAmount(trade.getNominal());
			accEventSell.setEffectiveDate(tradeDate);
			accEventSell.setBookingDate(tradeDate);
			accEventSell.setCurrency(splittedCurrency.get(1));
			accountingEvents.addElement(accEventSell);

		}

	}

	public void getREV_CONT(Trade trade, EventProcessor event,
			AccEventConfig eventConfig, Vector accountingEvents,
			AccConfigRule rule, Pricer pricer) {

		String fxType = trade.getTradedesc1();
		if (fxType.equals(FXConstants.FXFORWARD)
				|| fxType.equals(FXConstants.FXSWAP)
				|| fxType.equals(FXConstants.FXTAKEUP)) {

			Vector<String> splittedCurrency = splitCurrency(trade
					.getTradedesc());
			
			String tradeDate = trade.getTradeDate().substring(0, 10);
			String deliveryDate = trade.getDelivertyDate().substring(0, 10);
			
			BOPosting accEvent = new BOPosting(eventConfig);

			accEvent.setAmount(trade.getQuantity());
			accEvent.setEffectiveDate(deliveryDate);
			accEvent.setBookingDate(tradeDate);
			accEvent.setCurrency(splittedCurrency.get(0));
			accountingEvents.addElement(accEvent);
			BOPosting accEventSell = new BOPosting(eventConfig);

			accEventSell.setAmount(trade.getNominal());
			accEventSell.setEffectiveDate(deliveryDate);
			accEventSell.setBookingDate(tradeDate);
			accEventSell.setCurrency(splittedCurrency.get(1));
			accountingEvents.addElement(accEventSell);

		}

	}

	public void getTRADE_AMOUNT(Trade trade, EventProcessor event,
			AccEventConfig eventConfig, Vector accountingEvents,
			AccConfigRule rule, Pricer pricer) {
		
		if ( !trade.getTradedesc1().equals(FXConstants.FXFORWARDOPTION) ) {
			
			String tradeDate = trade.getTradeDate().substring(0, 10);
			BOPosting accEvent = new BOPosting(eventConfig);

			accEvent.setAmount(trade.getQuantity());
			accEvent.setEffectiveDate(tradeDate);
			accEvent.setBookingDate(tradeDate);
			accEvent.setCurrency(trade.getCurrency());

			accountingEvents.addElement(accEvent);

			
		}
		
	}

	public void getREALISED_CLEAN_PL(Trade trade, EventProcessor event,
			AccEventConfig eventConfig, Vector accountingEvents,
			AccConfigRule rule, Pricer pricer) {

		BOPosting accEvent = new BOPosting(eventConfig);
	
		LiquidationEventProcessor liquidationEvent = (LiquidationEventProcessor)event;

		accEvent.setAmount(liquidationEvent.getLiquidation().getRealisedPNL());
		accEvent.setEffectiveDate(trade.getDelivertyDate());
		accEvent.setBookingDate(trade.getTradeDate().substring(0, 10));
		accEvent.setCurrency(trade.getCurrency());

		accountingEvents.addElement(accEvent);

	}
}
