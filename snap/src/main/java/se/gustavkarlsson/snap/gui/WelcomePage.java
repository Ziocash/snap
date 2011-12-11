package se.gustavkarlsson.snap.gui;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import se.gustavkarlsson.snap.constants.Messages;

public class WelcomePage extends WizardPage {
	private Button btnSend;
	private Button btnReceive;

	public WelcomePage() {
		super("welcomePage");
		setTitle(Messages.WELCOME_PAGE_TITLE);
		setDescription(Messages.WELCOME_PAGE_DESCRIPTION);
	}

	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		RowLayout rl_container = new RowLayout(SWT.VERTICAL);
		rl_container.marginWidth = 10;
		rl_container.marginHeight = 10;
		rl_container.marginBottom = 0;
		rl_container.marginRight = 0;
		rl_container.marginLeft = 0;
		rl_container.marginTop = 0;
		container.setLayout(rl_container);

		Label lblQuestion = new Label(container, SWT.NONE);
		lblQuestion.setText("Do you want to send or receive files?");

		btnSend = new Button(container, SWT.RADIO);
		btnSend.setSelection(true);
		btnSend.setText("Send");

		btnReceive = new Button(container, SWT.RADIO);
		btnReceive.setText("Receive");
	}

	// @Override
	// public IWizardPage getNextPage() {
	// if (btnSend.getSelection()) {
	// // TODO return Send page
	// } else {
	// // TODO return Receive page
	// }
	// return null;
	// }

}
