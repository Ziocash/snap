package se.gustavkarlsson.snap.gui.pages;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import se.gustavkarlsson.snap.resources.Strings;

public class AdvancedOptionsPage extends WizardPage {

	/**
	 * Create the wizard.
	 */
	public AdvancedOptionsPage() {
		super(AdvancedOptionsPage.class.getName());
		setTitle(Strings.ADVANCED_OPTIONS_PAGE_TITLE);
		setDescription(Strings.ADVANCED_OPTIONS_PAGE_DESCRIPTION);
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
	}

}
