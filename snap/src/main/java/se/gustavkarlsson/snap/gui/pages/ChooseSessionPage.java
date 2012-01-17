package se.gustavkarlsson.snap.gui.pages;

import java.io.File;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import se.gustavkarlsson.snap.gui.util.filetree.FileNameComparator;
import se.gustavkarlsson.snap.gui.util.sessionlist.FileLabelProvider;
import se.gustavkarlsson.snap.resources.Strings;
import se.gustavkarlsson.snap.session.SessionManager;

public class ChooseSessionPage extends WizardPage {

	private final SessionManager sessionManager;
	private ListViewer sessionListViewer;

	/**
	 * Create the wizard.
	 */
	public ChooseSessionPage(SessionManager sessionManager) {
		super(ChooseSessionPage.class.getName());
		setTitle(Strings.CHOOSE_SESSION_PAGE_TITLE);
		setDescription(Strings.CHOOSE_SESSION_PAGE_DESCRIPTION);

		this.sessionManager = sessionManager;
	}

	/**
	 * Create contents of the wizard.
	 * 
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		container.setLayout(new GridLayout(1, false));

		sessionListViewer = new ListViewer(container, SWT.BORDER | SWT.V_SCROLL);
		org.eclipse.swt.widgets.List sessionList = sessionListViewer.getList();
		sessionList.addSelectionListener(new SessionSelectedListener());
		sessionList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,
				1, 1));
		sessionListViewer.setContentProvider(new ArrayContentProvider());
		sessionListViewer.setLabelProvider(new FileLabelProvider());
		sessionListViewer.setComparator(new ViewerComparator(
				new FileNameComparator()));

		Button refreshButton = new Button(container, SWT.NONE);
		refreshButton.addSelectionListener(new RefreshSessionsListener());
		refreshButton.setText("&Refresh");
	}

	@Override
	public void setVisible(boolean visible) {
		if (visible) {
			sessionManager.update();
			sessionListViewer.setInput(sessionManager.getSessions());
		}
		super.setVisible(visible);
	}

	@Override
	public boolean isPageComplete() {
		return (!sessionListViewer.getSelection().isEmpty());
	}
	
	@Override
	public boolean canFlipToNextPage() {
		return isPageComplete() && getNextPage() != null;
	}

	private class RefreshSessionsListener extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent e) {
			sessionManager.update();
			List<File> newSessions = sessionManager.getSessions();
			if (!newSessions.equals(sessionListViewer.getInput())) {
				sessionListViewer.setInput(newSessions);
			}
			
			getWizard().getContainer().updateButtons();
		}
	}
	
	private class SessionSelectedListener extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent e) {
			getWizard().getContainer().updateButtons();
		}
	}
}
