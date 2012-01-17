package se.gustavkarlsson.snap.gui.pages;

import java.awt.Container;

import org.apache.log4j.Logger;
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
import se.gustavkarlsson.snap.session.SessionManager;
import se.gustavkarlsson.snap.util.LoggerHelper;

public class WelcomePage extends WizardPage {
	
	private static final Logger LOG = LoggerHelper.getLogger();
	
	private final SessionManager sessionManager;
	
	private Button btnSend;
	private Button btnSendFromSession;
	private Button btnReceive;
	

	public WelcomePage(SessionManager sessionManager) {
		super(WelcomePage.class.getName());
		this.sessionManager = sessionManager;
		setTitle(Strings.WELCOME_PAGE_TITLE);
		setDescription(Strings.WELCOME_PAGE_DESCRIPTION);
	}

	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new GridLayout(1, false));

		Label lblQuestion = new Label(container, SWT.NONE);
		lblQuestion.setText("Do you want to send or receive files?");
		
		RadioButtonSelectedListener radioButtonSelectedListener = new RadioButtonSelectedListener();

		btnSend = new Button(container, SWT.RADIO);
		btnSend.addSelectionListener(radioButtonSelectedListener);
		btnSend.setText("&Send files");

		btnReceive = new Button(container, SWT.RADIO);
		btnReceive.addSelectionListener(radioButtonSelectedListener);
		btnReceive.setText("&Receive files");
		
		btnSendFromSession = new Button(container, SWT.RADIO);
		btnSendFromSession.addSelectionListener(radioButtonSelectedListener);
		btnSendFromSession.setText("Send files from previous s&ession");
	}
	
	@Override
	public boolean canFlipToNextPage() {
		return (btnSend.getSelection()
				|| btnReceive.getSelection()
				|| btnSendFromSession.getSelection());
	}

	
	@Override
	public IWizardPage getNextPage() {
		if (btnSend.getSelection()) {
			return getWizard().getPage(ChooseFilesPage.class.getName());
		} else if (btnReceive.getSelection()) {
			return null; // TODO return Select session page
		} else if (btnSendFromSession.getSelection()) {
			return null; // TODO return Receive page
		}
		LOG.error("No button selected for next page");
		return null;
	}
	
	private class RadioButtonSelectedListener extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent e) {
			getWizard().getContainer().updateButtons();
		}
	}
	
	@Override
	public void setVisible(boolean visible) {
		sessionManager.update();
		btnSendFromSession.setVisible(!sessionManager.getSessions().isEmpty());
		super.setVisible(visible);
	}
}
