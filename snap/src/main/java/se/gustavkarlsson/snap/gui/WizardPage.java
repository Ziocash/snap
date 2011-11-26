package se.gustavkarlsson.snap.gui;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class WizardPage extends JPanel {

	protected Wizard wizard;

	public abstract void setWizard(Wizard wizard);
}
