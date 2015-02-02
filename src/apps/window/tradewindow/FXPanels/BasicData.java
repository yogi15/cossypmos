package apps.window.tradewindow.FXPanels;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;


//VS4E -- DO NOT REMOVE THIS LINE!
public class BasicData extends JPanel {

	private static final long serialVersionUID = 1L;
	public JLabel jLabel1;
	public JTextField currencyPair;
	public JLabel jLabel2;
	public JRadioButton jRadioButton1;
	public JLabel jLabel3;
	public JTextField book;
	public JTextField buysell;
	public JRadioButton jRadioButton2;
	public JRadioButton jRadioButton3;
	public JLabel jLabel4;
	public JTextField counterPary;
	public JRadioButton jRadioButton4;
	public JRadioButton jRadioButton5;
	public JRadioButton jRadioButton6;
	public JRadioButton jRadioButton7;
	public JRadioButton jRadioButton0;
	public JTextField jTextField7;
	private JLabel jLabel0;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	public BasicData() {
		initComponents();
	}

	private void initComponents() {
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
		setLayout(new GroupLayout());
		add(getCurrencyPair(), new Constraints(new Leading(12, 65, 12, 12), new Leading(40, 23, 12, 12)));
		add(getJLabel1(), new Constraints(new Leading(14, 75, 10, 10), new Leading(12, 18, 12, 12)));
		add(getJLabel2(), new Constraints(new Leading(99, 57, 10, 10), new Leading(12, 18, 12, 12)));
		add(getBook(), new Constraints(new Leading(99, 147, 12, 12), new Leading(40, 23, 12, 12)));
		add(getJLabel3(), new Constraints(new Leading(256, 64, 10, 10), new Leading(12, 12, 12)));
		add(getBuysell(), new Constraints(new Leading(258, 65, 12, 12), new Leading(40, 23, 12, 12)));
		add(getJLabel4(), new Constraints(new Leading(338, 121, 12, 12), new Leading(12, 12, 12)));
		add(getCounterPary(), new Constraints(new Leading(337, 178, 10, 10), new Leading(40, 23, 12, 12)));
		add(getJTextField7(), new Constraints(new Leading(523, 152, 10, 10), new Leading(40, 23, 12, 12)));
		add(getJLabel0(), new Constraints(new Leading(523, 121, 89, 96), new Leading(12, 12, 12)));
		add(getJRadioButton7(), new Constraints(new Leading(756, 12, 12), new Leading(32, 12, 12)));
		add(getJRadioButton6(), new Constraints(new Leading(758, 64, 12, 12), new Leading(5, 12, 12)));
		add(getJRadioButton1(), new Constraints(new Leading(677, 10, 10), new Leading(3, 17, 12, 12)));
		add(getJRadioButton2(), new Constraints(new Leading(677, 10, 10), new Leading(24, 16, 10, 10)));
		add(getJRadioButton0(), new Constraints(new Leading(677, 12, 12), new Leading(44, 19, 12, 12)));
		add(getJRadioButton5(), new Constraints(new Leading(677, 12, 12), new Leading(65, 15, 10, 10)));
		setSize(864, 106);
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("Trader");
		}
		return jLabel0;
	}

	private JTextField getJTextField7() {
		if (jTextField7 == null) {
			jTextField7 = new JTextField();
			//jTextField7.setText("jTextField0");
		}
		return jTextField7;
	}

	private JRadioButton getJRadioButton0() {
		if (jRadioButton0 == null) {
			jRadioButton0 = new JRadioButton();
			jRadioButton0.setSelected(true);
			jRadioButton0.setText("TakeUP");
			jRadioButton0.setEnabled(false);
		}
		return jRadioButton0;
	}

	private JRadioButton getJRadioButton7() {
		if (jRadioButton7 == null) {
			jRadioButton7 = new JRadioButton();
			jRadioButton7.setSelected(true);
			jRadioButton7.setText("RollOver");
			jRadioButton7.setEnabled(false);
		}
		return jRadioButton7;
	}

	private JRadioButton getJRadioButton6() {
		if (jRadioButton6 == null) {
			jRadioButton6 = new JRadioButton();
			jRadioButton6.setSelected(true);
			jRadioButton6.setText("RollBack");
			jRadioButton6.setEnabled(false);
		}
		return jRadioButton6;
	}

	private JRadioButton getJRadioButton4() {
		if (jRadioButton4 == null) {
			jRadioButton4 = new JRadioButton();
			jRadioButton4.setSelected(true);
			jRadioButton4.setText("Non Deliverable");
		}
		return jRadioButton4;
	}

	private JRadioButton getJRadioButton5() {
		if (jRadioButton5 == null) {
			jRadioButton5 = new JRadioButton();
			jRadioButton5.setSelected(true);
			jRadioButton5.setText("Merchant");
		}
		return jRadioButton5;
	}

	private JTextField getCounterPary() {
		if (counterPary == null) {
			counterPary = new JTextField();
		//	counterPary.setText("Counterparty");
		}
		return counterPary;
	}

	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("CounterParty");
		}
		return jLabel4;
	}

	private JRadioButton getJRadioButton3() {
		if (jRadioButton3 == null) {
			jRadioButton3 = new JRadioButton();
			jRadioButton3.setSelected(true);
			jRadioButton3.setText("Deliverable");
		}
		return jRadioButton3;
	}

	private JRadioButton getJRadioButton2() {
		if (jRadioButton2 == null) {
			jRadioButton2 = new JRadioButton();
			jRadioButton2.setSelected(true);
			jRadioButton2.setText("Swap");
		}
		return jRadioButton2;
	}

	private JTextField getBuysell() {
		if (buysell == null) {
			buysell = new JTextField();
			buysell.setText("BUY");
			buysell.setBackground(Color.green);
			
		}
		return buysell;
	}

	private JTextField getBook() {
		if (book == null) {
			book = new JTextField();
		//	book.setText("Book");
		}
		return book;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("BUY/SELL");
			 jLabel3.addMouseListener(new MouseListener() {
				
				
				@Override
				public void mouseClicked(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					String buysellt = buysell.getText().toString();
					if(buysellt.equalsIgnoreCase("BUY") || buysellt.equalsIgnoreCase("BUY/SELL")) 
						buysell.setBackground(Color.red);
						if(buysellt.equalsIgnoreCase("SELL") || buysellt.equalsIgnoreCase("SELL/BUY")) 
							buysell.setBackground(Color.green);
						
						
					
				}
			});

				
		} 
		
		return jLabel3;
	}

	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setSelected(true);
			jRadioButton1.setText("OutRight");
		}
		return jRadioButton1;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("Book");
		}
		return jLabel2;
	}

	private JTextField getCurrencyPair() {
		if (currencyPair == null) {
			currencyPair = new JTextField();
		//	currencyPair.setText("CurrencyPair");
		}
		return currencyPair;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("Currency Pair");
		}
		return jLabel1;
	}

}
