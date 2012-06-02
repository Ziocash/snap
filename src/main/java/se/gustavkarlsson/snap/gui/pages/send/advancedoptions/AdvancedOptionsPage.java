package se.gustavkarlsson.snap.gui.pages.send.advancedoptions;

import net.miginfocom.swt.MigLayout;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolTip;

import se.gustavkarlsson.snap.resources.PropertyManager;
import se.gustavkarlsson.snap.util.NetworkUtils;
import se.gustavkarlsson.snap.util.PasswordUtils;
import se.gustavkarlsson.snap.util.PasswordUtils.Strength;

public class AdvancedOptionsPage extends WizardPage {
	private ComboViewer listeningAddressComboViewer;
	private ComboViewer compressionRateComboViewer;
	private Button enableCompressionButton;
	private Label compressionRateLabel;
	private Combo compressionRateCombo;
	private Combo listeningAddressCombo;
	private Group encryptionGroup;
	private Button enableEncryptionButton;
	private Text encryptionKeyText;
	private Label encryptionKeyLabel;
	private Button enableUpnpPortMappingButton;
	private Button enableNatpmpPortMappingButton;
	private Text portText;

	/**
	 * Create the wizard.
	 */
	public AdvancedOptionsPage() {
		super(AdvancedOptionsPage.class.getName());
		setTitle("Advanced Options");
		setDescription("Set advanced options.");
	}

	/**
	 * Create contents of the wizard.
	 * 
	 * @param parent
	 */
	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new MigLayout("", "[grow]"));
		setControl(container);

		createNetworkGroup(container);
		createCompressionGroup(container);
		createEncryptionGroup(container);

		bindComponents();
	}

	private void createNetworkGroup(Composite container) {
		Group networkGroup = new Group(container, SWT.NONE);
		networkGroup.setLayoutData("growx, wrap");
		networkGroup.setLayout(new MigLayout("", "[][right, grow]"));
		networkGroup.setText("Network");

		Label listeningAddressLabel = new Label(networkGroup, SWT.NONE);
		listeningAddressLabel.setText("Listening address:");

		listeningAddressComboViewer = new ComboViewer(networkGroup, SWT.READ_ONLY);
		listeningAddressComboViewer
				.setContentProvider(new ArrayContentProvider());
		listeningAddressComboViewer
				.setLabelProvider(new Inet4AddressLabelProvider());

		listeningAddressComboViewer.setInput(NetworkUtils.listInetAddresses());
		listeningAddressCombo = listeningAddressComboViewer.getCombo();
		listeningAddressCombo.setLayoutData("wrap");
		listeningAddressCombo.select(0);

		Label portLabel = new Label(networkGroup, SWT.NONE);
		portLabel.setText("Port:");

		portText = new Text(networkGroup, SWT.BORDER);
		portText.setLayoutData("wrap, width 50");
		portText.addVerifyListener(new PortVerifyListener());
		portText.setText(Integer.toString(PropertyManager.getListeningPort()));

		enableUpnpPortMappingButton = new Button(networkGroup, SWT.CHECK);
		enableUpnpPortMappingButton.setLayoutData("wrap, span 2");
		enableUpnpPortMappingButton.setText("Enable UPnP port mapping");
		enableUpnpPortMappingButton.setSelection(PropertyManager.isUsingUpnp());

		enableNatpmpPortMappingButton = new Button(networkGroup, SWT.CHECK);
		enableNatpmpPortMappingButton.setLayoutData("span 2");
		enableNatpmpPortMappingButton.setText("Enable NAT-PMP port mapping");
		enableNatpmpPortMappingButton.setSelection(PropertyManager.isUsingNatPmp());
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
					if (0 <= portNumber && portNumber <= 65535) {
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
}
