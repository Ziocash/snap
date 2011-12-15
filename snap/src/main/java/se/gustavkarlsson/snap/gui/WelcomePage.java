package se.gustavkarlsson.snap.gui;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import se.gustavkarlsson.snap.resources.Strings;

public class WelcomePage extends WizardPage {
	private Button btnSend;
	private Button btnReceive;

	public WelcomePage() {
		super(WelcomePage.class.getName());
		setTitle(Strings.WELCOME_PAGE_TITLE);
		setDescription(Strings.WELCOME_PAGE_DESCRIPTION);
	}

	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new GridLayout(1, false));

		Label lblQuestion = new Label(container, SWT.NONE);
		lblQuestion.setText("Do you want to send or receive files?");

		btnSend = new Button(container, SWT.RADIO);
		btnSend.addSelectionListener(new RadioButtonSelectedAdapter());
		btnSend.setText("&Send");

		btnReceive = new Button(container, SWT.RADIO);
		btnReceive.addSelectionListener(new RadioButtonSelectedAdapter());
		btnReceive.setText("&Receive");
	}
	
	public Class<Strings> getMessages() {
		return Strings.class;
	}
	
	@Override
	public boolean canFlipToNextPage() {
		return (btnSend.getSelection() || btnReceive.getSelection());
	}

	
	@Override
	public IWizardPage getNextPage() {
		if (btnSend.getSelection()) {
			return getWizard().getPage(ChooseFilesPage.class.getName());
		} else {
			return null; // TODO return Receive page
		}
	}
	
	private class RadioButtonSelectedAdapter extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent e) {
			getWizard().getContainer().updateButtons();
		}
	}
}
