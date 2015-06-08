package apps.window.utilwindow;

import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import beans.Book;
import beans.LegalEntity;

import com.jidesoft.combobox.TableExComboBox;
import com.jidesoft.combobox.TableExComboBoxSearchable;
import com.jidesoft.grid.SortableTable;

public class ComponentUtil {
	
	private static TableBookModelUtil getBookModelUtil(Vector<Book> bookData) {
		String cols[] = { "Bookid", "Name "  };
		return new TableBookModelUtil(bookData,cols);
	}
	private static TableLegalEntityModelUtil getTraderModelUtil(Vector<LegalEntity> traderData) {
		String traderCol[] = { "ID", "TraderName "  };
		return new TableLegalEntityModelUtil(traderData,traderCol);
	}
	private static TableLegalEntityModelUtil getCPModelUtil(Vector<LegalEntity> traderData) {
		String col[] = { "Id", "Name " };
		return new TableLegalEntityModelUtil(traderData,col);
	}
	private static TableBookModelUtil getBookModelUtil(Vector<Book> bookData, String col[]) {
		return new TableBookModelUtil(bookData,col);
	}
	private static TableLegalEntityModelUtil getTraderModelUtil(Vector<LegalEntity> traderData, String col[]) {
		return new TableLegalEntityModelUtil(traderData,col);
	}
	private static TableLegalEntityModelUtil getCPModelUtil(Vector<LegalEntity> traderData, String col[]) {
		return new TableLegalEntityModelUtil(traderData,col);
	}
	public static TableExComboBox getCounterPartyComboBox(Vector<LegalEntity> leData) {
		TableExComboBox counterParty =  new TableExComboBox(
					getCPModelUtil(leData)) {
				@Override
				public JTable createTable(TableModel model) {
					return new SortableTable(model);
				}
				
				
			};
			counterParty.setEditable(false);
			counterParty.setBorder(null);
			counterParty.setValueColumnIndex(1);
			new TableExComboBoxSearchable(counterParty);
			return counterParty;
	}
	public static TableExComboBox getTraderComboBox(Vector<LegalEntity> leData) {
		TableExComboBox trader =  new TableExComboBox(
					getCPModelUtil(leData)) {
				@Override
				public JTable createTable(TableModel model) {
					return new SortableTable(model);
				}				
				
			};
			 
			trader.setEditable(false);
			trader.setBorder(null);
			trader.setValueColumnIndex(1);
			new TableExComboBoxSearchable(trader);
			return trader;
	}
	public static TableExComboBox getBookComboBox(Vector<Book> bookData) {
		TableExComboBox book =  new TableExComboBox(
				getBookModelUtil(bookData)) {
				@Override
				public JTable createTable(TableModel model) {
					return new SortableTable(model);
				}
				
				
			};
			book.setEditable(false);
			book.setBorder(null);
			book.setValueColumnIndex(1);
			new TableExComboBoxSearchable(book);
			return book;
	}
	
	public static TableExComboBox getCounterPartyComboBox(Vector<LegalEntity> leData, String col[]) {
		TableExComboBox counterParty =  new TableExComboBox(
					getCPModelUtil(leData, col)) {
				@Override
				public JTable createTable(TableModel model) {
					return new SortableTable(model);
				}
				
				
			};
			counterParty.setEditable(false);
			counterParty.setBorder(null);
			counterParty.setValueColumnIndex(1);
			new TableExComboBoxSearchable(counterParty);
			return counterParty;
	}
	public static TableExComboBox getTraderComboBox(Vector<LegalEntity> leData, String col[]) {
		TableExComboBox trader =  new TableExComboBox(
					getCPModelUtil(leData, col)) {
				@Override
				public JTable createTable(TableModel model) {
					return new SortableTable(model);
				}
				
				
			};
			 
			trader.setEditable(false);
			trader.setBorder(null);
			trader.setValueColumnIndex(1);
			new TableExComboBoxSearchable(trader);
			return trader;
	}
	public static TableExComboBox getBookComboBox(Vector<Book> bookData, String col[]) {
		TableExComboBox book =  new TableExComboBox(
				getBookModelUtil(bookData, col)) {
				@Override
				public JTable createTable(TableModel model) {
					return new SortableTable(model);
				}
				
				
			};
			book.setEditable(false);
			book.setBorder(null);
			book.setValueColumnIndex(1);
			new TableExComboBoxSearchable(book);
			return book;
	}
	
}
