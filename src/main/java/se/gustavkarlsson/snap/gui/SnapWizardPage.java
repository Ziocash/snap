package se.gustavkarlsson.snap.gui;

import java.awt.Component;

import javax.swing.AbstractButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTree;
import javax.swing.text.JTextComponent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ciscavate.cjwizard.CustomWizardComponent;
import org.ciscavate.cjwizard.WizardPage;
import org.ciscavate.cjwizard.WizardSettings;

public abstract class SnapWizardPage extends WizardPage {
	private static final long serialVersionUID = 1L;

	/**
	 * Commons logging log instance
	 */
	private static Log log = LogFactory.getLog(SnapWizardPage.class);

	/**
	 * Constructor. Sets the title and description for this wizard panel.
	 * 
	 * @param title
	 *            The short (1-3 word) name of this page.
	 * @param description
	 *            A possibly longer description (but still under 1 sentence)
	 */
	public SnapWizardPage(String title, String description) {
		super(title, description);
	}

	/**
	 * Updates the settings map after this page has been used by the user.
	 * 
	 * This method should update the WizardSettings Map so that it contains the
	 * new key/value pairs from this page.
	 * 
	 */
	@Override
	public void updateSettings(WizardSettings settings) {
		for (Component c : _namedComponents) {
			settings.put(c.getName(), getValue(c));
		}
	}

	/**
	 * Gets the value from a component.
	 * 
	 * @param c
	 *            The component.
	 * @return The value.
	 */
	private Object getValue(Component c) {
		Object val = null;

		if (c instanceof CustomWizardComponent) {
			val = ((CustomWizardComponent) c).getValue();
		} else if (c instanceof JTextComponent) {
			val = ((JTextComponent) c).getText();
		} else if (c instanceof AbstractButton) {
			val = ((AbstractButton) c).isSelected();
		} else if (c instanceof JComboBox) {
			val = ((JComboBox) c).getSelectedItem();
		} else if (c instanceof JList) {
			val = ((JList) c).getSelectedValues();
		} else if (c instanceof JTree) {
			val = ((JTree) c).getModel();
		} else {
			log.warn("Unknown component: " + c);
		}

		return val;
	}
}
