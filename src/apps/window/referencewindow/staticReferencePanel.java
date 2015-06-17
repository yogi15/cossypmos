package apps.window.referencewindow;

import java.awt.Component;
import java.util.ArrayList;
 
import javax.swing.ActionMap;
import javax.swing.JPanel;
 
public abstract class   staticReferencePanel extends JPanel {
 
       public abstract ActionMap getHotKeysActionMapper();
       public abstract JPanel getHotKeysPanel();
       public abstract ArrayList<Component> getFocusOrderList();
      
}
