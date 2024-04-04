package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import applogic.ApplicationManager1;

public class ParamsUtils {

	private Properties prop;
	private ApplicationManager1 app;

	private static final String PROP_FILE = "/application.properties";

	public final static String WORKSPACE = "workspace";

	public final static String BROWSER_NAME = "browser.name";
	public final static String HEADLESS_MODE = "headless_mode";
	public final static String RECORDING = "recording";



	public ParamsUtils(ApplicationManager1 app) {

		prop = new Properties();
		this.app = app;
		try {
			prop.load(new FileInputStream(new File("src/main/resources", PROP_FILE)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		storeValue(BROWSER_NAME, "chrome");
		storeValue(HEADLESS_MODE, "false");
		storeValue(WORKSPACE, "C:\\Selenium Workspace");
		storeValue(RECORDING, "false");

	}



	private void storeValue(String name, String defaultValue) {
		/**
		 * Priorities: 1. System argument (-D) 2. application.properties 3.
		 * default params above
		 */
		String value = System.getProperty(name);
		if (value != null) {
			prop.setProperty(name, value);
		} else if (!prop.containsKey(name)) {
			prop.setProperty(name, defaultValue);
		}
	}


	public String getPropValue(String name) {
		return prop.getProperty(name);
	}


}