package se.gustavkarlsson.snap.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import net.miginfocom.swing.MigLayout;
import se.gustavkarlsson.gwiz.Wizard;

@SuppressWarnings("serial")
public class SnapWizard extends JFrame implements Wizard {

	private JPanel wizardPageContainer;
	private JButton cancelButton;
	private JButton previousButton;
	private JButton nextButton;
	private JButton finishButton;

	public SnapWizard(String title) {
		super(title);
		setupWizard();
	}

	private void setupWizard() {
		setupComponents();
		layoutComponents();

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	/**
	 * Sets up the components of the wizard with listeners and mnemonics.
	 */
	private void setupComponents() {
		wizardPageContainer = new JPanel();
		cancelButton = new JButton("Cancel");
		previousButton = new JButton("Previous");
		nextButton = new JButton("Next");
		finishButton = new JButton("Finish");

		cancelButton.setMnemonic(KeyEvent.VK_C);
		previousButton.setMnemonic(KeyEvent.VK_P);
		nextButton.setMnemonic(KeyEvent.VK_N);
		finishButton.setMnemonic(KeyEvent.VK_F);

		wizardPageContainer.addContainerListener(new SizeAdjuster());
	}

	/**
	 * Lays out the components in the wizards content pane.
	 */
	private void layoutComponents() {
		getContentPane().setLayout(new MigLayout("fill, insets 0", "[grow][][][][][]", "[][][][]"));

		getContentPane().add(wizardPageContainer, "spanx, grow");

		getContentPane().add(new JSeparator(), "spanx, growx");

		getContentPane().add(cancelButton, "skip");

		getContentPane().add(previousButton);

		getContentPane().add(nextButton);

		getContentPane().add(finishButton);

		wizardPageContainer.setLayout(new GridLayout(1, 1));
	}

	@Override
	public JPanel getWizardPageContainer() {
		return wizardPageContainer;
	}

	@Override
	public AbstractButton getCancelButton() {
		return cancelButton;
	}

	@Override
	public JButton getPreviousButton() {
		return previousButton;
	}

	@Override
	public JButton getNextButton() {
		return nextButton;
	}

	@Override
	public JButton getFinishButton() {
		return finishButton;
	}

	private class SizeAdjuster implements ContainerListener {

		@Override
		public void componentAdded(ContainerEvent e) {
			Dimension currentSize = getSize();
			Dimension preferredSize = getPreferredSize();

			Dimension newSize = new Dimension(currentSize);
			newSize.width = Math.max(currentSize.width, preferredSize.width);
			newSize.height = Math.max(currentSize.height, preferredSize.height);

			setSize(newSize);
		}

		@Override
		public void componentRemoved(ContainerEvent e) {
		}

	}
}
