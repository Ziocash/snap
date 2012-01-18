package se.gustavkarlsson.snap.gui.pages;

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

public class WelcomePage extends WizardPage {

	private final SessionManager sessionManager;
	
	private Button sendButton;
	private Button sendFromSessionButton;
	private Button receiveButton;

	public WelcomePage(SessionManager sessionManager ) {
		super(WelcomePage.class.getName());
		setTitle(Strings.WELCOME_PAGE_TITLE);
		setDescription(Strings.WELCOME_PAGE_DESCRIPTION);
		
		this.sessionManager = sessionManager;
	}

	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new GridLayout(1, false));

		Label questionLabel = new Label(container, SWT.NONE);
		questionLabel.setText("Do you want to send or receive files?");

		RadioButtonSelectedListener radioButtonSelectedListener = new RadioButtonSelectedListener();

		sendButton = new Button(container, SWT.RADIO);
		sendButton.addSelectionListener(radioButtonSelectedListener);
		sendButton.setText("&Send files");

		sendFromSessionButton = new Button(container, SWT.RADIO);
		sendFromSessionButton.addSelectionListener(radioButtonSelectedListener);
		sendFromSessionButton.setText("Send files from previous s&ession");

		receiveButton = new Button(container, SWT.RADIO);
		receiveButton.addSelectionListener(radioButtonSelectedListener);
		receiveButton.setText("&Receive files");
	}
	
	@Override
	public void setVisible(boolean visible) {
		if (visible) {
			sessionManager.setCurrentSession(null);
		}
		super.setVisible(visible);
	}

	@Override
	public boolean isPageComplete() {
		return (sendButton.getSelection()
				|| sendFromSessionButton.getSelection() || receiveButton
					.getSelection());
	}

	@Override
	public boolean canFlipToNextPage() {
		return isPageComplete() && getNextPage() != null;
	}

	@Override
	public IWizardPage getNextPage() {
		if (sendButton.getSelection()) {
			return getWizard().getPage(ChooseFilesPage.class.getName());
		} else if (sendFromSessionButton.getSelection()) {
			return getWizard().getPage(ChooseSessionPage.class.getName());
		} else if (receiveButton.getSelection()) {
			// TODO return Select session page
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
