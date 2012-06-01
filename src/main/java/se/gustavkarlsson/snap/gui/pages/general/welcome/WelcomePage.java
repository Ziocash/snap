package se.gustavkarlsson.snap.gui.pages.general.welcome;

import net.miginfocom.swt.MigLayout;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import se.gustavkarlsson.snap.gui.pages.send.choosefiles.ChooseFilesPage;
import se.gustavkarlsson.snap.main.Snap;

public class WelcomePage extends WizardPage {

	private Button sendButton;
	private Button receiveButton;

	public WelcomePage() {
		super(WelcomePage.class.getName());
		setTitle("Welcome to " + Snap.APP_NAME);
		setDescription("Do you want to save or receive files?");
	}

	@Override
	public void createControl(Composite parent) {
		RadioButtonSelectedListener radioButtonSelectedListener = new RadioButtonSelectedListener();

		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new MigLayout());
		setControl(container);
		
		container.setBackground(new org.eclipse.swt.graphics.Color(getShell().getDisplay(), 100, 255, 100));
		parent.setBackground(new org.eclipse.swt.graphics.Color(getShell().getDisplay(), 255, 100, 100));

		sendButton = new Button(container, SWT.RADIO);
		sendButton.addSelectionListener(radioButtonSelectedListener);
		sendButton.setText("&Send files");
		sendButton.setLayoutData("wrap");

		receiveButton = new Button(container, SWT.RADIO);
		receiveButton.addSelectionListener(radioButtonSelectedListener);
		receiveButton.setText("&Receive files");
	}

	@Override
	public boolean isPageComplete() {
		return getNextPage() != null;
	}

	@Override
	public IWizardPage getNextPage() {
		if (sendButton.getSelection()) {
			return getWizard().getPage(ChooseFilesPage.class.getName());
		} else if (receiveButton.getSelection()) {
			// TODO return receive page
		}
		return null;
	}

	private class RadioButtonSelectedListener extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent e) {
			getWizard().getContainer().updateButtons();
		}
	}
}
