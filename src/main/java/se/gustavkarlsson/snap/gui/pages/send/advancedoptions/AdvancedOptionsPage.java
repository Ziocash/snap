package se.gustavkarlsson.snap.gui.pages.send.advancedoptions;

import java.awt.Color;
import java.net.InetAddress;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import se.gustavkarlsson.gwiz.AbstractWizardPage;
import se.gustavkarlsson.snap.domain.SenderSettings;
import se.gustavkarlsson.snap.gui.pages.SnapWizardPage;
import se.gustavkarlsson.snap.resources.PropertyManager;
import se.gustavkarlsson.snap.util.NetworkUtils;
import se.gustavkarlsson.snap.util.PasswordUtils;
import se.gustavkarlsson.snap.util.PasswordUtils.Strength;

public class AdvancedOptionsPage extends SnapWizardPage {
	private static final long serialVersionUID = 1L;

	private static final String TITLE = "Advanced Options";
	private static final String DESCRIPTION = "Set advanced options for the transfer.";

	private JLabel listeningAddressLabel = null;
	private JComboBox listeningAddressComboBox = null;

	private JLabel portLabel = null;
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
	 * @param settings
	 */
	public AdvancedOptionsPage(SenderSettings settings) {
		super(TITLE, DESCRIPTION);
		createControls();
		setInitialData();
		layoutControls();
	}

	private void createControls() {
		listeningAddressLabel = new JLabel("Listening address: ");
		listeningAddressComboBox = new JComboBox();

		portLabel = new JLabel("Port: ");
		portTextField = new JTextField();

		enableUpnpPortMappingCheckBox = new JCheckBox("Enable UPnP port mapping");
		enableUpnpPortMappingCheckBox.setEnabled(PropertyManager.isUsingUpnp());

		enableNatPmpPortMappingCheckBox = new JCheckBox("Enable NAT-PMP port mapping");
		enableNatPmpPortMappingCheckBox.setEnabled(PropertyManager.isUsingNatPmp());

		enableCompressionCheckBox = new JCheckBox("Enable");
		compressionRateLabel = new JLabel("Rate: ");
		compressionRateComboBox = new JComboBox();

		enableEncryptionCheckBox = new JCheckBox("Enable");
		encryptionKeyLabel = new JLabel("Encryption key: "); // TODO: Add some kind of model
		encryptionKeyTextField = new JTextField();
	}

	private void setInitialData() {
		Vector<InetAddress> networkAddresses = new Vector<InetAddress>();
		networkAddresses.addAll(NetworkUtils.listInetAddresses());
		ComboBoxModel model = new DefaultComboBoxModel(networkAddresses);
		listeningAddressComboBox.setModel(model);
		listeningAddressComboBox.setRenderer(new NetworkAddressComboBoxRenderer());

		portTextField.setInputVerifier(new PortInputVerifier());
	}

	private void layoutControls() {
		getPageContentPanel().setLayout(new MigLayout("", "[grow]"));

		// Network
		JPanel networkPanel = new JPanel(new MigLayout("", "[][right, grow]"));
		networkPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Network"));
		getPageContentPanel().add(networkPanel, "growx, wrap");

		networkPanel.add(listeningAddressLabel);
		networkPanel.add(listeningAddressComboBox, "wrap");
		networkPanel.add(portLabel);
		networkPanel.add(portTextField, "wrap, width 50");
		networkPanel.add(enableUpnpPortMappingCheckBox, "wrap, span 2");
		networkPanel.add(enableNatPmpPortMappingCheckBox, "span 2");


		// Compression
		JPanel compressionPanel = new JPanel(new MigLayout("", "[][right, grow][right]"));
		compressionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Compression"));
		getPageContentPanel().add(compressionPanel, "growx, wrap");

		compressionPanel.add(enableCompressionCheckBox);
		compressionPanel.add(compressionRateLabel);
		compressionPanel.add(compressionRateComboBox);


		// Encryption
		JPanel encryptionPanel = new JPanel(new MigLayout("", "[grow][][]"));
		encryptionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Encryption"));
		getPageContentPanel().add(encryptionPanel, "growx, wrap");

		encryptionPanel.add(enableEncryptionCheckBox);
		encryptionPanel.add(encryptionKeyLabel);
		encryptionPanel.add(encryptionKeyLabel);
		encryptionPanel.add(encryptionKeyTextField, "width 150");
	}

	private class EncryptionKeyToColorConverter implements IConverter {
		@Override
		public Object convert(Object encryptionKey) {
			Strength encryptionKeyStrength = PasswordUtils.checkStrength((String) encryptionKey);

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

		private final ToolTip balloon = new ToolTip(getShell(), SWT.BALLOON | SWT.ICON_ERROR);

		public PortVerifyListener() {
			balloon.setMessage("Valid ports range from 0 to 65535");
		}

		@Override
		public void verifyText(VerifyEvent event) {
			event.doit = false;
			boolean valid = false;

			String newText = portText.getText().substring(0, event.start) + event.text
					+ portText.getText().substring(event.end);

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

		private final ToolTip balloon = new ToolTip(getShell(), SWT.BALLOON | SWT.ICON_ERROR);

		public EncryptionKeyVerifyListener() {
			balloon.setMessage("Invalid character input");
		}

		@Override
		public void verifyText(VerifyEvent event) {
			String newText = encryptionKeyText.getText().substring(0, event.start) + event.text
					+ encryptionKeyText.getText().substring(event.end);

			event.doit = PasswordUtils.checkValidity(newText);

			balloon.setLocation(encryptionKeyText.toDisplay(encryptionKeyText.getSize()));
			balloon.setVisible(!event.doit);
		}
	}

	@Override
	protected AbstractWizardPage getNextPage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean isCancelAllowed() {
		return true;
	}

	@Override
	protected boolean isPreviousAllowed() {
		return true;
	}

	@Override
	protected boolean isNextAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean isFinishAllowed() {
		return false;
	}
}
