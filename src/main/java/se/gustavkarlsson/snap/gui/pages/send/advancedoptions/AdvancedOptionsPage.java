package se.gustavkarlsson.snap.gui.pages.send.advancedoptions;

import java.awt.Button;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Label;
import java.security.acl.Group;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import se.gustavkarlsson.gwiz.AbstractWizardPage;
import se.gustavkarlsson.snap.gui.pages.SnapWizardPage;
import se.gustavkarlsson.snap.gui.pages.send.advancedoptions.old.CompressionRateLabelProvider;
import se.gustavkarlsson.snap.resources.PropertyManager;
import se.gustavkarlsson.snap.util.PasswordUtils;
import se.gustavkarlsson.snap.util.PasswordUtils.Strength;

public class AdvancedOptionsPage extends SnapWizardPage {
	private static final long serialVersionUID = 1L;

	private static final String CANONICAL_NAME = AdvancedOptionsPage.class.getCanonicalName();

	private static final String TITLE = "Advanced Options";
	private static final String DESCRIPTION = "Set advanced options.";

	private JComboBox listeningAddressComboBox = null;
	private JTextField portTextField = null;

	private JCheckBox enableUpnpPortMappingCheckBox = null;
	private JCheckBox enableNatPmpPortMappingCheckBox = null;

	private JCheckBox enableCompressionCheckBox = null;
	private JLabel compressionRateLabel = null;
	private JComboBox compressionRateComboBox = null;

	private JCheckBox enableEncryptionCheckBox = null;
	private JLabel encryptionKeyLabel = null;
	private JTextField encryptionKeyTextField = null;

	/**
	 * Create the wizard.
	 */
	public AdvancedOptionsPage() {
		super(TITLE, DESCRIPTION);
		createControls();
		layoutControls();
	}

	private void createControls() {
		listeningAddressComboBox = new JComboBox(); // TODO: Add some kind of model
		// TODO listeningAddressComboBox.setValue(PropertyManager.getListeningAddress());

		portTextField = new JTextField(); // TODO: Add validation
		portTextField.setText(String.valueOf(PropertyManager.getListeningPort()));

		enableUpnpPortMappingCheckBox = new JCheckBox("Enable UPnP port mapping");
		enableUpnpPortMappingCheckBox.setEnabled(PropertyManager.isUsingUpnp());

		enableNatPmpPortMappingCheckBox = new JCheckBox("Enable NAT-PMP port mapping");
		enableNatPmpPortMappingCheckBox.setEnabled(PropertyManager.isUsingNatPmp());

		enableCompressionCheckBox = new JCheckBox("Enable compression");
		// TODO enableCompressionCheckBox.setEnabled(PropertyManager.isUsingCompression());

		compressionRateLabel = new JLabel("Rate:"); // TODO: Add some kind of model

		compressionRateComboBox = new JComboBox(); // TODO: Add some kind of model
		// TODO compressionRateComboBox.setValue(PropertyManager.getCompressionRate());

		enableEncryptionCheckBox = new JCheckBox("Enable encryption");
		// TODO enableEncryptionCheckBox.setEnabled(PropertyManager.isUsingEncryption());

		encryptionKeyLabel = new JLabel("Encryption key"); // TODO: Add some kind of model

		encryptionKeyTextField = new JTextField();
	}

	private void layoutControls() {
		getContentPanel().setLayout(new MigLayout("", "[grow]"));

		// Network
		JPanel networkPanel = new JPanel(new MigLayout("", "[][right, grow]"));
		networkPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Network"));
		getContentPanel().add(networkPanel, "growx, wrap");

		networkPanel.add(new JLabel("Listening address:"));
		networkPanel.add(listeningAddressComboBox, "wrap");

		networkPanel.add(new JLabel("Port:"));
		networkPanel.add(portTextField, "wrap, width 50");

		networkPanel.add(enableUpnpPortMappingCheckBox, "wrap, span 2");

		networkPanel.add(enableNatPmpPortMappingCheckBox, "span 2");

		//createCompressionGroup();
		//createEncryptionGroup();
	}

	private void createCompressionGroup(Composite container) {
		Group compressionGroup = new Group(container, SWT.NONE);
		compressionGroup.setLayoutData("growx, wrap");
		compressionGroup.setLayout(new MigLayout("", "[grow][][]"));
		compressionGroup.setText("Compression");

		enableCompressionButton = new Button(compressionGroup, SWT.CHECK);
		enableCompressionButton.setText("Enable");

		compressionRateLabel = new Label(compressionGroup, SWT.NONE);
		compressionRateLabel.setText("Rate:");

		compressionRateComboViewer = new ComboViewer(compressionGroup, SWT.READ_ONLY);
		compressionRateComboViewer.setLabelProvider(new CompressionRateLabelProvider());
		compressionRateComboViewer.setContentProvider(new ArrayContentProvider());
		compressionRateComboViewer.setInput(new Integer[] { 9, 5, 1 });
		compressionRateCombo = compressionRateComboViewer.getCombo();
		compressionRateCombo.select(1);
	}

	private void createEncryptionGroup(Composite container) {
		encryptionGroup = new Group(container, SWT.NONE);
		encryptionGroup.setLayoutData("growx");
		encryptionGroup.setLayout(new MigLayout("", "[grow][][]"));
		encryptionGroup.setText("Encryption");

		enableEncryptionButton = new Button(encryptionGroup, SWT.CHECK);
		enableEncryptionButton.setText("Enable");

		encryptionKeyLabel = new Label(encryptionGroup, SWT.NONE);
		encryptionKeyLabel.setText("Encryption key:");

		encryptionKeyText = new Text(encryptionGroup, SWT.BORDER | SWT.PASSWORD);
		encryptionKeyText.setLayoutData("width 150");
		encryptionKeyText
		.addVerifyListener(new EncryptionKeyVerifyListener());
	}

	private void bindComponents() {
		DataBindingContext bindingContext = new DataBindingContext(
				SWTObservables.getRealm(Display.getCurrent()));

		// Compression widgets enabled
		IObservableValue enableCompressionButtonSelection = WidgetProperties.selection().observe(enableCompressionButton);
		IObservableValue compressionRateLabelEnabled = WidgetProperties.enabled().observe(compressionRateLabel);
		IObservableValue compressionRateComboEnabled = WidgetProperties.enabled().observe(compressionRateCombo);
		bindingContext.bindValue(enableCompressionButtonSelection, compressionRateLabelEnabled);
		bindingContext.bindValue(enableCompressionButtonSelection, compressionRateComboEnabled);

		// Encryption widgets enabled
		IObservableValue enableEncryptionButtonSelection = WidgetProperties.selection().observe(enableEncryptionButton);
		IObservableValue encryptionKeyLabelEnabled = WidgetProperties.enabled().observe(encryptionKeyLabel);
		IObservableValue encryptionKeyTextEnabled = WidgetProperties.enabled().observe(encryptionKeyText);
		bindingContext.bindValue(enableEncryptionButtonSelection, encryptionKeyLabelEnabled);
		bindingContext.bindValue(enableEncryptionButtonSelection, encryptionKeyTextEnabled);

		// Encryption key color
		IObservableValue encryptionKeyTextModify = WidgetProperties.text(SWT.Modify).observe(encryptionKeyText);
		IObservableValue encryptionKeyTextForeground = WidgetProperties.foreground().observe(encryptionKeyText);
		UpdateValueStrategy encryptionKeyToColorConverter = new UpdateValueStrategy().setConverter(new EncryptionKeyToColorConverter());
		bindingContext.bindValue(encryptionKeyTextModify, encryptionKeyTextForeground, encryptionKeyToColorConverter, null);
	}

	private class EncryptionKeyToColorConverter implements IConverter {
		@Override
		public Object convert(Object encryptionKey) {
			Strength encryptionKeyStrength = PasswordUtils
					.checkStrength((String) encryptionKey);

			Color color;
			switch (encryptionKeyStrength) {
			case EXCEPTIONAL:
				color = new Color(Display.getCurrent(), 0, 230, 230);
				break;
			case STRONG:
				color = new Color(Display.getCurrent(), 115, 230, 115);
				break;
			case MODERATE:
				color = new Color(Display.getCurrent(), 230, 230, 0);
				break;
			case WEAK:
				color = new Color(Display.getCurrent(), 230, 115, 0);
				break;
			case INADEQUATE:
				color = new Color(Display.getCurrent(), 230, 0, 0);
				break;
			default:
				color = null;
				// TODO log error
				break;
			}
			return color;
		}

		@Override
		public Object getFromType() {
			return String.class;
		}

		@Override
		public Object getToType() {
			return Color.class;
		}
	}

	private class PortVerifyListener implements VerifyListener {

		private final ToolTip balloon = new ToolTip(getShell(), SWT.BALLOON
				| SWT.ICON_ERROR);

		public PortVerifyListener() {
			balloon.setMessage("Valid ports range from 0 to 65535");
		}

		@Override
		public void verifyText(VerifyEvent event) {
			event.doit = false;
			boolean valid = false;

			String newText = portText.getText().substring(0, event.start)
					+ event.text + portText.getText().substring(event.end);

			if (newText.equals("")) {
				// Let user empty the field, but don't accept it as valid
				event.doit = true;
			} else {
				try {
					int portNumber = Integer.parseInt(newText);
					if ((0 <= portNumber) && (portNumber <= 65535)) {
						event.doit = true;
						valid = true;
					}
				} catch (NumberFormatException e) {
				}
			}

			balloon.setLocation(portText.toDisplay(portText.getSize()));
			balloon.setVisible(!valid);
		}
	}

	private class EncryptionKeyVerifyListener implements VerifyListener {

		private final ToolTip balloon = new ToolTip(getShell(), SWT.BALLOON
				| SWT.ICON_ERROR);

		public EncryptionKeyVerifyListener() {
			balloon.setMessage("Invalid character input");
		}

		@Override
		public void verifyText(VerifyEvent event) {
			String newText = encryptionKeyText.getText().substring(0,
					event.start)
					+ event.text
					+ encryptionKeyText.getText().substring(event.end);

			event.doit = PasswordUtils.checkValidity(newText);

			balloon.setLocation(encryptionKeyText
					.toDisplay(encryptionKeyText.getSize()));
			balloon.setVisible(!event.doit);
		}
	}

	@Override
	protected AbstractWizardPage getNextPage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean isCompleted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean canFinish() {
		// TODO Auto-generated method stub
		return false;
	}
}
