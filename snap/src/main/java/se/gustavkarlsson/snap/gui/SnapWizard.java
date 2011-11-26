package se.gustavkarlsson.snap.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SpringLayout;

public class SnapWizard implements Wizard {

	private JFrame frame;
	private JButton cancelButton;
	private JButton nextButton;
	private JButton backButton;
	private JLabel pageTitle;
	private JLabel pageSubTitle;
	private JPanel centerPanel;
	private JButton finishButton;
	private JLabel infoLabel;

	/**
	 * Create the application.
	 */
	public SnapWizard() {
		initialize(); // TODO Tidy up init of SnapWizard
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setMinimumSize(new Dimension(420, 450));
		frame.setBounds(100, 100, 400, 400);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.WHITE);
		frame.getContentPane().add(topPanel, BorderLayout.NORTH);
		SpringLayout sl_topPanel = new SpringLayout();
		topPanel.setLayout(sl_topPanel);

		pageTitle = new JLabel(" ");
		pageTitle.setFont(new Font("Tahoma", Font.PLAIN, 18));
		sl_topPanel.putConstraint(SpringLayout.NORTH, pageTitle, 10,
				SpringLayout.NORTH, topPanel);
		sl_topPanel.putConstraint(SpringLayout.WEST, pageTitle, 15,
				SpringLayout.WEST, topPanel);
		topPanel.add(pageTitle);

		pageSubTitle = new JLabel(" ");
		sl_topPanel.putConstraint(SpringLayout.NORTH, pageSubTitle, 5,
				SpringLayout.SOUTH, pageTitle);
		sl_topPanel.putConstraint(SpringLayout.WEST, pageSubTitle, 10,
				SpringLayout.WEST, pageTitle);
		topPanel.add(pageSubTitle);

		sl_topPanel.putConstraint(SpringLayout.SOUTH, topPanel, 10,
				SpringLayout.SOUTH, pageSubTitle);

		infoLabel = new JLabel("");
		infoLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		infoLabel.setVisible(false);
		sl_topPanel.putConstraint(SpringLayout.NORTH, infoLabel, 15,
				SpringLayout.NORTH, topPanel);
		sl_topPanel.putConstraint(SpringLayout.EAST, infoLabel, -10,
				SpringLayout.EAST, topPanel);
		infoLabel.setIcon(new ImageIcon(SnapWizard.class
				.getResource("/javax/swing/plaf/metal/icons/ocean/info.png")));
		topPanel.add(infoLabel);

		JPanel bottomPanel = new JPanel();
		frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		backButton = new JButton("< Back");
		backButton.setPreferredSize(new Dimension(85, 23));
		backButton.setEnabled(false);
		backButton.setMnemonic('B');
		backButton.setDisplayedMnemonicIndex(2);
		bottomPanel.add(backButton);

		nextButton = new JButton("Next >");
		nextButton.setEnabled(false);
		nextButton.setMnemonic('N');
		nextButton.setDisplayedMnemonicIndex(0);
		nextButton.setPreferredSize(new Dimension(85, 23));
		bottomPanel.add(nextButton);

		finishButton = new JButton("Finish");
		finishButton.setEnabled(false);
		finishButton.setPreferredSize(new Dimension(85, 23));
		finishButton.setMinimumSize(new Dimension(85, 23));
		finishButton.setMnemonic('F');
		finishButton.setDisplayedMnemonicIndex(0);
		bottomPanel.add(finishButton);

		cancelButton = new JButton("Cancel");
		cancelButton.setEnabled(false);
		cancelButton.setMnemonic('A');
		cancelButton.setDisplayedMnemonicIndex(1);
		cancelButton.setPreferredSize(new Dimension(85, 23));
		bottomPanel.add(cancelButton);

		centerPanel = new JPanel();
		frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout(0, 0));

		JSeparator topSeparator = new JSeparator();
		centerPanel.add(topSeparator, BorderLayout.NORTH);

		JSeparator bottomSeparator = new JSeparator();
		centerPanel.add(bottomSeparator, BorderLayout.SOUTH);
	}

	@Override
	public void show() {
		frame.setVisible(true);
	}

	@Override
	public void setPage(WizardPage page) {
		Component[] components = centerPanel.getComponents();
		for (Component c : components) {
			if (c instanceof JPanel) {
				centerPanel.remove(c);
			}
		}
		centerPanel.add(page, BorderLayout.CENTER);
		page.setWizard(this);
	}

	@Override
	public void setTitle(String newTitle) {
		frame.setTitle(newTitle);
	}

	@Override
	public void setPageTitle(String newPageTitle) {
		pageTitle.setText(newPageTitle);
	}

	@Override
	public void setPageSubTitle(String newPageSubTitle) {
		pageSubTitle.setText(newPageSubTitle);
	}

	@Override
	public void setBackButtonEnabled(boolean enabled) {
		backButton.setEnabled(enabled);
	}

	@Override
	public void setNextButtonEnabled(boolean enabled) {
		nextButton.setEnabled(enabled);
	}

	@Override
	public void setFinishButtonEnabled(boolean enabled) {
		finishButton.setEnabled(enabled);
	}

	@Override
	public void setCancelButtonEnabled(boolean enabled) {
		cancelButton.setEnabled(enabled);
	}

	@Override
	public void setBackButtonVisible(boolean visible) {
		setVisible(backButton, visible);
	}

	@Override
	public void setNextButtonVisible(boolean visible) {
		setVisible(nextButton, visible);
	}

	@Override
	public void setFinishButtonVisible(boolean visible) {
		setVisible(finishButton, visible);
	}

	@Override
	public void setCancelButtonVisible(boolean visible) {
		setVisible(cancelButton, visible);
	}

	private void setVisible(Component component, boolean visible) {
		component.setVisible(visible);
	}

	@Override
	public void setBackButtonActionListener(ActionListener listener) {
		setButtonActionListener(backButton, listener);
	}

	@Override
	public void setNextButtonActionListener(ActionListener listener) {
		setButtonActionListener(nextButton, listener);
	}

	@Override
	public void setFinishButtonActionListener(ActionListener listener) {
		setButtonActionListener(finishButton, listener);
	}

	@Override
	public void setCancelButtonActionListener(ActionListener listener) {
		setButtonActionListener(cancelButton, listener);
	}

	@Override
	public void setFrameWindowListener(WindowListener listener) {
		WindowListener[] oldListeners = frame.getWindowListeners();
		for (WindowListener wl : oldListeners) {
			frame.removeWindowListener(wl);
		}
		if (listener != null) {
			frame.addWindowListener(listener);
		}
	}

	@Override
	public void setInfoLabelMouseListener(MouseListener listener) {
		MouseListener[] oldListeners = infoLabel.getMouseListeners();
		for (MouseListener wl : oldListeners) {
			infoLabel.removeMouseListener(wl);
		}
		if (listener != null) {
			infoLabel.addMouseListener(listener);
		}
	}

	private void setButtonActionListener(JButton button, ActionListener listener) {
		ActionListener[] oldListeners = button.getActionListeners();
		for (ActionListener al : oldListeners) {
			button.removeActionListener(al);
		}
		if (listener != null) {
			button.addActionListener(listener);
		}
	}
}
