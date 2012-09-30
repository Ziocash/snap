package se.gustavkarlsson.snap.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import net.miginfocom.swing.MigLayout;
import se.gustavkarlsson.gwiz.Wizard;

@SuppressWarnings("serial")
public class SnapWizard extends JFrame implements Wizard {
	private final JPanel wizardPageContainer = new JPanel();
	private final JButton cancelButton = new JButton("Cancel");
	private final JButton previousButton = new JButton("Previous");
	private final JButton nextButton = new JButton("Next");
	private final JButton finishButton = new JButton("Finish");

	/**
	 * Creates a <code>DefaultWizard</code> with a title.
	 * 
	 * @param title
	 *            the title of the frame
	 * @see JFrame
	 */
	public SnapWizard(String title) {
		super(title);
		setupWizard();
	}

	/**
	 * Sets up wizard upon construction.
	 */
	private void setupWizard() {
		setupComponents();
		layoutComponents();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	/**
	 * Sets up the components of the wizard with listeners and mnemonics.
	 */
	private void setupComponents() {
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		finishButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(getContentPane(), "Wizard finished!");
				dispose();
			}
		});

		cancelButton.setMnemonic(KeyEvent.VK_C);
		previousButton.setMnemonic(KeyEvent.VK_P);
		nextButton.setMnemonic(KeyEvent.VK_N);
		finishButton.setMnemonic(KeyEvent.VK_F);

		wizardPageContainer.addContainerListener(new GrowFrameOnComponentAddedListener());
	}

	/**
	 * Lays out the components in the wizards content pane.
	 */
	private void layoutComponents() {
		setLayout(new MigLayout("insets 0", "[grow]", "[grow]"));

		// Make WizardPages inside container fill all available space
		wizardPageContainer.setLayout(new GridLayout(1, 1));
		add(wizardPageContainer, "grow, wrap");

		// Make container grow automatically
		JPanel buttonPanel = new JPanel(new MigLayout());
		buttonPanel.add(cancelButton);
		buttonPanel.add(previousButton);
		buttonPanel.add(nextButton);
		buttonPanel.add(finishButton);

		add(new JSeparator(), "grow, wrap");
		add(buttonPanel, "right");
	}

	@Override
	public JPanel getWizardPageContainer() {
		return wizardPageContainer;
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

	private class GrowFrameOnComponentAddedListener implements ContainerListener {

		@Override
		public void componentAdded(ContainerEvent e) {
			Dimension preferredSize = getPreferredSize();
			Dimension currentSize = getSize();
			Dimension newSize = new Dimension(currentSize);
			newSize.width = (preferredSize.width > currentSize.width ? preferredSize.width : currentSize.width);
			newSize.height = (preferredSize.height > currentSize.height ? preferredSize.height : currentSize.height);
			setSize(newSize);
		}

		@Override
		public void componentRemoved(ContainerEvent e) {
		}

	}
}
