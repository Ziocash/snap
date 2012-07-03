package se.gustavkarlsson.snap.gui;

import java.util.List;

import javax.swing.tree.TreeModel;

import org.ciscavate.cjwizard.PageFactory;
import org.ciscavate.cjwizard.WizardPage;
import org.ciscavate.cjwizard.WizardSettings;

import se.gustavkarlsson.snap.gui.pages.general.welcome.WelcomePage;
import se.gustavkarlsson.snap.gui.pages.send.advancedoptions.AdvancedOptionsPage;
import se.gustavkarlsson.snap.gui.pages.send.choosefiles.ChooseFilesPage;

public class SnapPageFactory implements PageFactory {

	@Override
	public WizardPage createPage(List<WizardPage> path, WizardSettings settings) {
		WizardPage chosenPage = null;

		final WizardPage previousPage = path.isEmpty() ? null : path.get(path
				.size() - 1);
		if (previousPage == null) {
			chosenPage = new WelcomePage();
		} else if (previousPage instanceof WelcomePage) {
			if (sendChosen(settings)) {
				chosenPage = new ChooseFilesPage();
			} else if (receiveChosen(settings)) {
				//
			}
		} else if (previousPage instanceof ChooseFilesPage) {
			if (hasAddedFiles(settings)) {
				if (enabledAdvancedOptions(settings)) {
					chosenPage = new AdvancedOptionsPage();
				} else {
					//
				}
			}
		}

		return chosenPage;
	}

	private boolean enabledAdvancedOptions(WizardSettings settings) {
		return (Boolean) settings
				.get(ChooseFilesPage.ENABLE_ADVANCED_OPTIONS_BUTTON_NAME);
	}

	private boolean sendChosen(WizardSettings settings) {
		return (Boolean) settings.get(WelcomePage.SEND_BUTTON_NAME);
	}

	private boolean receiveChosen(WizardSettings settings) {
		return (Boolean) settings.get(WelcomePage.RECEIVE_BUTTON_NAME);
	}

	private boolean hasAddedFiles(WizardSettings settings) {
		TreeModel model = (TreeModel) settings
				.get(ChooseFilesPage.FILE_TREE_NAME);
		Object root = model.getRoot();

		if (root == null) {
			return false;
		}

		return model.getChildCount(root) > 0;
	}

}
