import java.awt.event.KeyEvent;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*; 

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import apps.window.utilwindow.ListSearchUtil;

import com.jidesoft.swing.ListSearchable;


//VS4E -- DO NOT REMOVE THIS LINE!
public class testsearch extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField jTextField0;
	private JList jList0;
	private JScrollPane jScrollPane0;
	private JPanel jPanel0;

	public testsearch() {
		initComponents();
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getJPanel0(), new Constraints(new Leading(58, 220, 10, 10), new Leading(31, 193, 10, 10)));
		setSize(320, 240);
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJTextField0(), new Constraints(new Leading(23, 145, 10, 10), new Leading(24, 30, 10, 10)));
			jPanel0.add(getJScrollPane0(), new Constraints(new Leading(22, 142, 10, 10), new Leading(55, 66, 10, 10)));
			DefaultListModel model = (DefaultListModel) jList0.getModel();
			ListSearchUtil search = new ListSearchUtil(model,jList0,jTextField0,jScrollPane0);
		}
		return jPanel0;
	}

	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setViewportView(getJList0());
			jScrollPane0.setAutoscrolls(false);
			jList0.setAutoscrolls(false);
			jScrollPane0.setVisible(false);
			jScrollPane0.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			jScrollPane0.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		}
		return jScrollPane0;
	}

	
	
	public static void main(String args[]) {
		testsearch e33= new testsearch();
		e33.setVisible(true);
	}
	private JList getJList0() {
		if (jList0 == null) {
			jList0 = new JList();
			
			DefaultListModel listModel = new DefaultListModel();
			
			listModel.addElement("item0");
			listModel.addElement("item1");
			listModel.addElement("item2");
			listModel.addElement("item3");
			listModel.addElement( "Bob");
					listModel.addElement( "Ted");
							listModel.addElement( "Carol");
									listModel.addElement( "Alice");
											listModel.addElement("pppo");
						listModel.addElement("jay");
								listModel.addElement("pank");
										listModel.addElement("king3");
												listModel.addElement("ling");
														listModel.addElement("linong");
																listModel.addElement("jayu");
																		listModel.addElement("second");
																				listModel.addElement("firt");
																						listModel.addElement("hart");
																								listModel.addElement("kin");
			jList0.setModel(listModel);
			jList0.setVisible(false);
		}
		return jList0;
	}
String test = "";
private void keypres(java.awt.event.KeyEvent evt) {
	
}
	private void keyPressHandler(java.awt.event.KeyEvent evt) {    
		char ch = evt.getKeyChar(); 
		long m_time;
		String m_key ="";
		//ignore searches for non alpha-numeric characters   
		if (!Character.isLetterOrDigit(ch)) {       
			return;    
			}     // reset string if too much time has elapsed   
		//if (m_time+CHAR_DELTA < System.currentTimeMillis()) {  
		//	m_key = "";   
			//}   
	//	System.out.println("pppp"+evt.getKeyChar());
		    m_time = System.currentTimeMillis();  
		    test  = test + Character.toLowerCase(ch);     
		    
		    
		    System.out.println("key++++ " + test);
		    // Iterate through items in the list until a matching prefix is found.    
		    // This technique is fine for small lists, however, doing a linear   
		    // search over a very large list with additional string manipulation    
		    // (eg: toLowerCase) within the tight loop would be quite slow.    // In that case, pre-processing the case-conversions, and storing the  
		    // strings in a more search-efficient data structure such as a Trie    // or a Ternary Search Tree would lead to much faster find.   
		    for (int i=0; i < jList0.getModel().getSize(); i++) {    
		    	String str = ((String)jList0.getModel().getElementAt(i)).toLowerCase();       
		    	if (str.startsWith(test)) {       
		    		jList0.setSelectedIndex(i);
		    	//	System.out.println(str);
		    		//break;//change selected item in list          
		    		jList0.ensureIndexIsVisible(i);
		    		//change listbox scroll-position           
		    		break;       
		    		}    
		    	}
		    
		    
		    
	}
	
	
	private JTextField getJTextField0() {
		if (jTextField0 == null) {
			jTextField0 = new JTextField();
		//	jTextField0.setText("jTextField0");
		}/*jTextField0.addKeyListener(new KeyAdapter() {
			

		    @Override
		    public void keyTyped(KeyEvent e) {
		    	
		    	
		        if (e.getKeyChar() == KeyEvent.VK_ENTER) {
		            // Do stuff
		        	if(jList0.getSelectedIndex() == -1) {
		        		jTextField0.setText("");
		        		return;
		        	}
		        	jTextField0.setText(jList0.getSelectedValue().toString());
		        	jList0.setVisible(false);
		        	jScrollPane0.setVisible(false);
		        	test = "";
		        } else {
		        	jScrollPane0.setVisible(true);
		        	
		        	jList0.setVisible(true);
		        	keyPressHandler(e);
		        	//search(jTextField0.getText());
		        }  
		        
		    }
		    @Override
		    public void keyPressed(KeyEvent key) {
		    	boolean found = false;
		    	if(key.getKeyCode()==8)
			    {
		    		
			        if(test.length() > 0)
			    		test = test.substring(0, test.length()-1);
			       
			        for (int i=0; i < jList0.getModel().getSize(); i++) {    
				    	String str = ((String)jList0.getModel().getElementAt(i)).toLowerCase();  
				    	
				    	if(str.trim().equalsIgnoreCase(test.trim())) {
				    		
				    		jList0.setSelectedIndex(i);
				    		jList0.ensureIndexIsVisible(i);
				    		found = true;
				    		break;
				    	} 
			        }
			        if(!found) {
			        	for (int i=0; i < jList0.getModel().getSize(); i++) {    
					    	String str = ((String)jList0.getModel().getElementAt(i)).toLowerCase();  
					    	if(str.startsWith(test)) {  
					    		jList0.setSelectedIndex(i);
					    		jList0.ensureIndexIsVisible(i);
					    		break;  
					    	}
			        	}
			        }
				    	
			    
			    	
			    }
		    	
		    	
		    }
		    
		}); */
		return jTextField0;
	}
	private void search(String text) {  
		DefaultListModel model = (DefaultListModel)jList0.getModel();     
		// Case–sensitive.    
		
		if(model.contains(text)) {   
			jList0.setSelectedValue(text, true);
			int index = model.indexOf(text);   
			
			jList0.setSelectedIndex(index);       
		//	label.setText(text + " found at index " + index);    
			} else {         
				jList0.clearSelection();    
				//jList0.setText(text + " not found");     
				}   
		}
}
