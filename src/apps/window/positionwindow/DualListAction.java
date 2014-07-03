package apps.window.positionwindow;

import com.jidesoft.icons.IconsFactory;
import com.jidesoft.list.DualList;
import com.jidesoft.swing.JideBoxLayout;
import com.jidesoft.swing.JideButton;
import com.jidesoft.swing.JideSwingUtilities;
import com.jidesoft.swing.NullPanel;

import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public enum DualListAction {
	 /**
     * @see DualList#COMMAND_MOVE_LEFT
     */
	
    MOVE_LEFT(DualList.COMMAND_MOVE_LEFT),
    /**
     * @see DualList#COMMAND_MOVE_RIGHT
     */
    MOVE_RIGHT(DualList.COMMAND_MOVE_RIGHT),
    /**
     * @see DualList#COMMAND_MOVE_ALL_LEFT
     */
    MOVE_ALL_LEFT(DualList.COMMAND_MOVE_ALL_LEFT),
    /**
     * @see DualList#COMMAND_MOVE_ALL_RIGHT
     */
    MOVE_ALL_RIGHT(DualList.COMMAND_MOVE_ALL_RIGHT),
    /**
     * @see DualList#COMMAND_MOVE_UP
     */
    MOVE_UP(DualList.COMMAND_MOVE_UP),
    /**
     * @see DualList#COMMAND_MOVE_DOWN
     */
    MOVE_DOWN(DualList.COMMAND_MOVE_DOWN),
    /**
     * @see DualList#COMMAND_MOVE_TO_TOP
     */
    MOVE_TO_TOP(DualList.COMMAND_MOVE_TO_TOP),
    /**
     * @see DualList#COMMAND_MOVE_TO_BOTTOM
     */
    MOVE_TO_BOTTOM(DualList.COMMAND_MOVE_TO_BOTTOM);

    private static final String LOG_CATEGORY = DualListAction.class.getSimpleName();

    private final String command;

    private DualListAction(String command) {
        this.command = command;
    }

    /**
     * Returns the command string
     * 
     * @return the command string
     */
    public String getCommand() {
        return command;
    }

    @Override
    public String toString() {
        return getCommand();
    }

    /**
     * Creates a button from the given action listener
     * 
     * @param l
     *            the action listener; cannot be null
     * @return a new button object
     */
    public AbstractButton createButton(ActionListener l) {
        Action action = createAction(l);
        return createButton(action);
    }

    /**
     * Creates a button from the given action listener
     * 
     * @param action
     *            the action object; cannot be null
     * @return a new button object
     */
    public AbstractButton createButton(final Action action) {
        final AbstractButton button = new JideButton(action);
        action.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if ("disabledIcon".equals(evt.getPropertyName())) {
                    button.setDisabledIcon((Icon) action.getValue("disabledIcon"));
                }
            }
        });
        button.setName(""
                + action.getValue(Action.ACTION_COMMAND_KEY));
        button.setDisabledIcon((Icon) action.getValue("disabledIcon"));
        return button;
    }

    /**
     * Creates a new action object from the given action listener. This action
     * can be used for buttons.
     * 
     * @param l
     *            the action listener, never null
     * @return a new action object
     */
    public Action createAction(final ActionListener l) {
        assert l != null;
        Action action = new AbstractAction() {
            /**
             * 
             */
            private static final long serialVersionUID = 2220415275495693110L;

            @Override
            public void actionPerformed(ActionEvent e) {
                l.actionPerformed(e);
            }
        };
        action.putValue(Action.ACTION_COMMAND_KEY, command);
        updateAction(action);
        return action;
    }

    /**
     * Loads the properties and resources into the action object.
     * 
     * @param action
     *            the action
     */
    private void updateAction(Action action) {
        String prefix = "dualList."
                + command + ".";

        try {
            String text = getResourceString(prefix
                    + "text");
            if (text != null
                    && text.trim().length() > 0) {
                action.putValue(Action.NAME, text);
            }
        } catch (Exception e) {
           // Log.debug(LOG_CATEGORY, e.getMessage());
        }

        String url = null;
        try {
            url = getResourceString(prefix
                    + "icon");
        } catch (Exception e) {
          //  Log.debug(LOG_CATEGORY, e.getMessage());
        }
        if (url == null
                || url.trim().length() == 0) {
            url = String.format("icons/%s.png", command);
        }
        Icon icon = IconsFactory.getImageIcon(DualList.class, url);
        action.putValue(Action.SMALL_ICON, icon);

        url = null;
        try {
            url = getResourceString(prefix
                    + "disabledIcon");
        } catch (Exception e) {
          //  Log.debug(LOG_CATEGORY, e.getMessage());
        }
        if (url == null
                || url.trim().length() == 0) {
            url = String.format("icons/%s_disabled.png", command);
        }
        icon = IconsFactory.getImageIcon(DualList.class, url);
        action.putValue("disabledIcon", icon);

        try {
            String tooltip = getResourceString(prefix
                    + "tooltip");
            if (tooltip != null
                    && tooltip.trim().length() > 0) {
                action.putValue(Action.SHORT_DESCRIPTION, tooltip);
            }
        } catch (Exception e) {
        //    Log.debug(LOG_CATEGORY, e.getMessage());
        }

        try {
            String mnemonic = getResourceString(prefix
                    + "mnemonic");
            if (mnemonic != null
                    && mnemonic.trim().length() > 1) {
                action.putValue(Action.MNEMONIC_KEY, mnemonic.charAt(0));
            }
        } catch (Exception e) {
          //  Log.debug(LOG_CATEGORY, e.getMessage());
        }
    }

    /**
     * Gets the localized string from resource bundle. Subclass can override it
     * to provide its own string. For example, you can customize the icons by
     * overriding this method. The key for the icons will be in the format of
     * "dualList.moveLeft.icon" and "dualList.moveLeft.disabledIcon" for the
     * move left button. There are a total of eight buttons. Once you override,
     * you can return a full qualified path to the icon resource such as
     * "/com/yourcompany/icons/moveLeft.png". Note that the icon must be in the
     * class path so that we can access it as resource.
     * 
     * @param key
     *            the key
     * @return the localized string.
     */
    private String getResourceString(String key) {
        return ResourceBundle.getBundle("com.jidesoft.list.dualList",
                                        Locale.getDefault()).getString(key);
    }

    /**
     * Convenience method for creating a panel with proper spacing around a
     * button. Useful for adding a button into a {@link DualList} style button
     * panel.
     * 
     * @param c
     *            the component to surround with space
     * @return a new panel
     * @see #createButtonPanel()
     */
    public static JComponent createButtonSpacePanel(Component c) {
        JPanel p = JideSwingUtilities.createCenterPanel(c);
        p.setCursor(Cursor.getDefaultCursor());
        return p;
    }

    /**
     * Creates a {@link DualList} style button panel with a vertical alignment,
     * using a {@link JideBoxLayout}. Buttons can be added into the panel using
     * {@link #createButtonSpacePanel(Component)}
     * 
     * @return a new panel
     */
    public static Container createButtonPanel() {
        JPanel panel = new NullPanel();
        panel.setOpaque(false);
        panel.setLayout(new JideBoxLayout(panel, JideBoxLayout.Y_AXIS, 4));
        panel.setBorder(new EmptyBorder(0, 2, 0, 2));
        return panel;
    }
}
