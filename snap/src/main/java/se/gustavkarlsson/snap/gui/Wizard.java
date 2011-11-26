package se.gustavkarlsson.snap.gui;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowListener;

public interface Wizard {

	public void show();

	public void setPage(WizardPage page);

	public void setTitle(String newTitle);

	public void setPageTitle(String newPageTitle); // TODO Tidy up page changing

	public void setPageSubTitle(String newPageSubTitle);

	public void setBackButtonEnabled(boolean enabled);

	public void setNextButtonEnabled(boolean enabled);

	public void setFinishButtonEnabled(boolean enabled);

	public void setCancelButtonEnabled(boolean enabled);

	public void setBackButtonVisible(boolean visible);

	public void setNextButtonVisible(boolean visible);

	public void setFinishButtonVisible(boolean visible);

	public void setCancelButtonVisible(boolean visible);

	public void setBackButtonActionListener(ActionListener listener);

	public void setNextButtonActionListener(ActionListener listener);

	public void setFinishButtonActionListener(ActionListener listener);

	public void setCancelButtonActionListener(ActionListener listener);

	public void setFrameWindowListener(WindowListener listener);

	public void setInfoLabelMouseListener(MouseListener listener);
}
