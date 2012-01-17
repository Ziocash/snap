package se.gustavkarlsson.snap.resources;

import se.gustavkarlsson.snap.main.Snap;

public abstract class Strings {

	// Error messages
	public static final String ARGUMENT_IS_NULL = "Argument is null";
	public static final String ARGUMENT_IS_NEGATIVE = "Argument is negative";
	public static final String PORT_OUT_OF_RANGE = "Port out of range";
	public static final String COMPRESSION_RATE_OUT_OF_RANGE = "Compression rate out of range";
	public static final String NOT_A_FOLDER = "Not a folder";
	public static final String NOT_A_FILE = "Not a file";
	public static final String CANT_ADD_CHILDREN_TO_FILE = "Can't add children to node";
	public static final String ILLEGAL_ARGUMENT_TYPE = "Illegal argument type";
	
	public static final String WELCOME_PAGE_TITLE = "Welcome to " + Snap.APP_NAME;
	public static final String WELCOME_PAGE_DESCRIPTION = "Send or Receive?";
	
	public static final String CHOOSE_FILES_PAGE_TITLE = "Choose Files";
	public static final String CHOOSE_FILES_PAGE_DESCRIPTION = "Use the \"Browse\" button or by drag and drop from your OS.";
	
	
	public static final String ADVANCED_OPTIONS_PAGE_TITLE = "Advanced Options";
	public static final String ADVANCED_OPTIONS_PAGE_DESCRIPTION = "Set advanced options.";
	
	public static final String SESSION_FILE_FILTER_DESCRIPTION = Snap.APP_NAME + " session files";
}
