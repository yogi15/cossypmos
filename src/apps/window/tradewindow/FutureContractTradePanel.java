package apps.window.tradewindow;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;


import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;

import util.RemoteServiceUtil;

import FutureContract.ContractSelectorComboBox;
import FutureContract.FutureContractPropertyTable;
import FutureContract.FuturesConstants;

import beans.Book;
import beans.LegalEntity;

import com.jidesoft.dialog.ButtonPanel;
import com.jidesoft.grid.PropertyTable;
import com.jidesoft.swing.AutoCompletionComboBox;
import com.jidesoft.swing.JideBoxLayout;

import dsServices.RemoteProduct;
import dsServices.RemoteReferenceData;

public class FutureContractTradePanel extends JFrame {
	
	private JPanel contractStatusBar = new JPanel();
	private final String DISPLAYABLEOBJECT = "FutureContract";
	//private JPanel ContractStatusBar = new JPanel();
	private final int WINDOW_WIDTH = 1200;// 900
	private final int WINDOW_DEPTH = 600;
	//private final int CONTRACT_PANEL_SPLIT_LOCATION = 105;
	private final int PANEL_SPLIT_LOCATION = 100;
	private final int BOTTOM_PANE_SPLIT_LOCATION = 400;
	
	private JPanel topEntityPanel = new JPanel(new BorderLayout());
	
	private JPanel splitLeftPanel = new JPanel(new BorderLayout());
	private JPanel splitRightPanel = new JPanel(new BorderLayout());
	private JLabel contractLabel = new JLabel("FutureContract");
	
	private JSplitPane splitBottomPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
			splitLeftPanel, splitRightPanel);
	
	private JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
			topEntityPanel, splitBottomPane);
	
	
	private ContractSelectorComboBox contractSelectorComboBox = null;
	//ContractSelectorComboBoxListener contractSelectorComboBoxActionListener = null;
	private JPanel oldPropertyTablePanel = null;
	//private JPanel _futureContractDetailPropertyTable = null;
	//private JPanel _futureUnderlyingPanel = null;
	private JPanel futureContractTablePanel = new JPanel(new BorderLayout());
	//private final String CONTRACTPROPERTIESPANELNAME = "Coontract Details";
	private JPanel contractPropertiesPanel = new JPanel(new BorderLayout());

	protected JButton loadButton = new JButton("Load");
	protected JButton newButton = new JButton("New");
	protected JButton deleteButton = new JButton("Delete");
	protected JButton saveButton = new JButton("Save");
	protected JButton saveAsNewButton = new JButton("Save As New");
	protected JButton closeButton = new JButton("Close");

	private JPanel buttonsPanel = new JPanel();

	private PropertyTable propertyTable = null;
	
	public FutureContractTradePanel() {
		try {
			jbInit();
		} catch (Exception e) {
			// Log.error(this, e);
		}
		// initDomains();
	}
	
	protected void jbInit() throws Exception {
		// super();
		createLayout();
		//setupMainComponents();
	}
	
	private void createLayout() {
		
		Container contentPane = getContentPane();
		setSize(WINDOW_WIDTH, WINDOW_DEPTH);

		contentPane.setLayout(new BorderLayout());
		splitLeftPanel.setLayout(new BorderLayout());
		contentPane.add(splitPane, BorderLayout.CENTER);

		splitPane.setDividerLocation(PANEL_SPLIT_LOCATION);
		//splitPane.add(topEntityPanel, BorderLayout.NORTH);
		
		//splitLeftPanel.add(contractStatusBar, BorderLayout.NORTH);
		splitBottomPane.setDividerLocation(BOTTOM_PANE_SPLIT_LOCATION);
		splitBottomPane.setAutoscrolls(true);
		splitLeftPanel.add(contractPropertiesPanel, BorderLayout.CENTER);
		splitLeftPanel.add(buttonsPanel, BorderLayout.SOUTH);
		// splitPane.add(splitLeftPanel);
		
		createTopPanel();
		createButtonsPanel();
		createPropertyTablesForFutureContractTrade();
	
	}
	
	private void createTopPanel() {
		
		String[] _fontNames = {"A", "B"};
		
		FlowLayout layout = new FlowLayout();
    	layout.setAlignment(FlowLayout.LEFT);
    	topEntityPanel.setLayout(layout);
    	
    	RemoteReferenceData referenceData = RemoteServiceUtil.getRemoteReferenceDataService();
    	Collection<Book> booksVec = null;
    	Collection<LegalEntity> cpVec = null;
    	Collection<LegalEntity> traderVec = null;
    	Collection<Book> statusVec = null;
    	try {
    		cpVec  = referenceData.selectLEonWhereClause(" role like 'CounterParty'");
    		traderVec  = referenceData.selectLEonWhereClause(" role like 'Trader'");
    		booksVec = referenceData.selectALLBooks();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   	
    	String cp[] = new String[cpVec.size()];
    	String trader[] = new String[traderVec.size()];
    	String book[] = new String[booksVec.size()];
    	int i =0;
    	for (Iterator<LegalEntity> iterator = cpVec.iterator(); iterator.hasNext();) {
    		LegalEntity type = (LegalEntity) iterator.next();
    		cp[i++] = type.getName();
        }
    	
    	i =0;
    	for (Iterator<LegalEntity> iterator = traderVec.iterator(); iterator.hasNext();) {
    		LegalEntity type = (LegalEntity) iterator.next();
    		trader[i++] = type.getName();
        }
    	
    	i =0;
    	for (Iterator<Book> iterator = booksVec.iterator(); iterator.hasNext();) {
    		Book type = (Book) iterator.next();
    		book[i++] = type.getBook_name();
        }
		AutoCompletionComboBox counterPartyComBoBox = new AutoCompletionComboBox(cp);
		counterPartyComBoBox.setStrict(false);
		counterPartyComBoBox.setName("AutoCompletion JComboBox (Not strict)");
        topEntityPanel.add(new JLabel("Counter Party"));
		topEntityPanel.add(Box.createVerticalStrut(50), JideBoxLayout.FIX);
        topEntityPanel.add(counterPartyComBoBox);
        topEntityPanel.add(Box.createVerticalStrut(20), JideBoxLayout.FIX);
        topEntityPanel.add(Box.createVerticalStrut(12), JideBoxLayout.FIX);
        
        AutoCompletionComboBox bookComBoBox = new AutoCompletionComboBox(book);
        bookComBoBox.setStrict(false);
        bookComBoBox.setName("AutoCompletion JComboBox (Not strict)");
        topEntityPanel.add(new JLabel("Book"));
		topEntityPanel.add(Box.createHorizontalStrut(3), JideBoxLayout.FIX);
        topEntityPanel.add(bookComBoBox);
        topEntityPanel.add(Box.createHorizontalStrut(12), JideBoxLayout.FIX);
        
        AutoCompletionComboBox traderComBoBox = new AutoCompletionComboBox(trader);
        traderComBoBox.setStrict(false);
        traderComBoBox.setName("AutoCompletion JComboBox (Not strict)");
        topEntityPanel.add(new JLabel("Trader"));
		topEntityPanel.add(Box.createHorizontalStrut(3), JideBoxLayout.FIX);
        topEntityPanel.add(traderComBoBox);
        topEntityPanel.add(Box.createHorizontalStrut(12), JideBoxLayout.FIX);
        
        AutoCompletionComboBox statusComBoBox = new AutoCompletionComboBox(cp);
        statusComBoBox.setStrict(false);
        topEntityPanel.add(new JLabel("Status"));
		topEntityPanel.add(Box.createHorizontalStrut(3), JideBoxLayout.FIX);
        topEntityPanel.add(statusComBoBox);
        topEntityPanel.add(Box.createHorizontalStrut(50), JideBoxLayout.FIX);
        
        AutoCompletionComboBox newStatusComBoBox = new AutoCompletionComboBox(cp);
        newStatusComBoBox.setStrict(false);
        topEntityPanel.add(new JLabel("Status"));
		topEntityPanel.add(Box.createHorizontalStrut(3), JideBoxLayout.FIX);
        topEntityPanel.add(newStatusComBoBox);
        topEntityPanel.add(Box.createHorizontalStrut(12), JideBoxLayout.FIX);
              
     
	}
	private void createButtonsPanel() {

		// setButtonDetails();
		JPanel buttonPanel = new ButtonPanel(SwingConstants.RIGHT);

		buttonPanel.add(buttons2Column(loadButton, newButton));
		buttonPanel.add(buttons2Column(saveAsNewButton, saveButton));
		buttonPanel.add(buttons2Column(deleteButton, closeButton));

		initBottomButtonsActionListeners();

		getRootPane().setDefaultButton(loadButton);
		buttonsPanel.add(buttonPanel, JideBoxLayout.FLEXIBLE);

	}

	private ButtonPanel buttons2Column(JButton topButton, JButton botButton) {

		ButtonPanel buttonPanel = new ButtonPanel(SwingConstants.TOP);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		buttonPanel.add(topButton, ButtonPanel.AFFIRMATIVE_BUTTON);
		buttonPanel.add(botButton, ButtonPanel.AFFIRMATIVE_BUTTON);
		return buttonPanel;

	}

	private void initBottomButtonsActionListeners() {

		saveButton.setToolTipText(saveButton.getName());
		saveButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				System.out.println("Save");

			}

		});

		closeButton.setToolTipText(closeButton.getName());
		closeButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				System.out.println("Close");
			}

		});

		saveAsNewButton.setToolTipText(saveAsNewButton.getName());
		saveAsNewButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// System.out.println("Save As New");
				/*System.out.println(propertyTable.getValueAt(1, 1) + "++++++");
				System.out.println(propertyTable.getValueAt(2, 1) + "----");

				propertyTable.getValueAt(1, 1);*/
			}

		});

		deleteButton.setToolTipText(deleteButton.getName());
		deleteButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				System.out.println("delete");
			}

		});

		newButton.setToolTipText(newButton.getName());
		newButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				System.out.println("New");
			}

		});

		loadButton.setToolTipText(loadButton.getName());
		loadButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				System.out.println("Load");
			}

		});

	}
	
	private void createPropertyTablesForFutureContractTrade() {

		JPanel propertyTablePanel = getFutureContractTradePropertyTablePanel();
		if (oldPropertyTablePanel != null) {
			futureContractTablePanel.remove(oldPropertyTablePanel);
		}
		oldPropertyTablePanel = propertyTablePanel;
		contractPropertiesPanel.add(oldPropertyTablePanel, BorderLayout.CENTER);
	}

	public JPanel getFutureContractTradePropertyTablePanel() {

		// FutureContractPropertyTable contractPropertyTable = new
		// FutureContractPropertyTable("FUTURES_FX");

		propertyTable = new FutureContractPropertyTable(FuturesConstants.FUTURES_FX_TRADE).getFutureProductPropertyTable();

		JPanel basePanel = new JPanel();
		JScrollPane tableScrollPane = new JScrollPane();
		tableScrollPane.getViewport().add(propertyTable);
		basePanel.setLayout(new BorderLayout());
		basePanel.add(tableScrollPane, BorderLayout.CENTER);

		return basePanel;

	}
	public static void main(String args[]) {
		FutureContractTradePanel c1 = new FutureContractTradePanel();
		c1.setSize(900, 900);
		c1.setVisible(true);
	}
}
