package se.gustavkarlsson.snap.gui.pages;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import net.miginfocom.swing.MigLayout;
import se.gustavkarlsson.gwiz.AbstractWizardPage;

@SuppressWarnings("serial")
public abstract class SnapWizardPage extends AbstractWizardPage {

	private final String title;
	private final String description;

	private JPanel headerPanel = null;
	private JPanel contentPanel = null;

	private JLabel titleLabel = null;
	private JLabel descriptionLabel = null;

	public SnapWizardPage(String title, String description) {
		this.title = title;
		this.description = description;

		setupControls();
		layoutControls();
	}

	private void setupControls() {
		headerPanel = new JPanel();
		headerPanel.setBackground(Color.WHITE);

		contentPanel = new JPanel();

		titleLabel = new JLabel(title);
		titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));

		descriptionLabel = new JLabel(description);
		descriptionLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
	}

	private void layoutControls() {
		setLayout(new MigLayout("insets 0"));

		headerPanel.setLayout(new MigLayout());

		headerPanel.add(titleLabel, "wrap");
		headerPanel.add(descriptionLabel);

		add(headerPanel, "width 100%, height 60p, wrap 0");

		add(new JSeparator(), "width 100%, wrap 0");

		add(contentPanel, "width 100%, height 100%");
	}

	protected final JPanel getContentPanel() {
		return contentPanel;
	}

}
