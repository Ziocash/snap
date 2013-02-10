package se.gustavkarlsson.snap.domain;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SenderSettings {

	private List<File> filesToSend = new ArrayList<File>();

	public List<File> getFilesToSend() {
		return filesToSend;
	}

	public void setFilesToSend(List<File> filesToSend) {
		this.filesToSend = filesToSend;
	}
}
