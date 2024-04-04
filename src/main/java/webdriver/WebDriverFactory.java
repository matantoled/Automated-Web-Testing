package webdriver;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.safari.SafariDriver;

import applogic.ApplicationManager1;
import util.ParamsUtils;

/*
 * Factory to instantiate a WebDriver object. It returns an instance of the
 * driver (local invocation) or an instance of RemoteWebDriver
 * @author Sebastiano Armeli-Battana
 */
public class WebDriverFactory {

	/* Browsers constants */
	public final String CHROME = "chrome";
	public final String FIREFOX = "firefox";
	public final String OPERA = "opera";
	public final String INTERNET_EXPLORER = "ie";
	public final String PHANTOMJS = "phantomjs";
	public final String HTML_UNIT = "htmlunit";
	public final String SAFARI = "safari";
	public final String IPHONE = "iphone";
	public final String EDGE = "edge";

	private WebDriver webDriver = null;

	/*
	 * Factory method to return a WebDriver instance given the browser to hit
	 * @param browser : String representing the local browser to hit
	 * @param username : username for BASIC authentication on the page to test
	 * @param password : password for BASIC authentication on the page to test
	 * @return WebDriver instance
	 */
	public WebDriver getInstance(String browser, String downloadsFolder, ApplicationManager1 app) {

		if (webDriver != null) {
			return webDriver;
		}

		if (CHROME.equals(browser)) {
			System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Jdk14Logger");

			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("download.default_directory", downloadsFolder);
			prefs.put("safebrowsing.enabled", "true");
			prefs.put("credentials_enable_service", false);

			ChromeOptions options = new ChromeOptions();
			if (!System.getProperty("os.name").toLowerCase().matches(".*windows.*")) {
				//here is the code for Linux
				options.addArguments("--no-sandbox");
				options.addArguments("--disable-dev-shm-usage");
			}
			options.setExperimentalOption("useAutomationExtension", false);
			options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
			options.setExperimentalOption("prefs", prefs);
			options.addArguments("disable-infobars");
			options.addArguments("safebrowsing-disable-download-protection");
			options.setCapability(CapabilityType.ELEMENT_SCROLL_BEHAVIOR, 1);
			options.setExperimentalOption("useAutomationExtension", false);
			options.setExperimentalOption("w3c", false);

			if (app.paramsUtils.getPropValue(ParamsUtils.HEADLESS_MODE).equals("true"))
				options.addArguments("headless");

			webDriver = new ChromeDriver(options);


		} else if (FIREFOX.equals(browser)) {

			String profileDirectory = System.getProperty("user.dir");
			profileDirectory += File.separator + "Profile" + File.separator + "FF";
			Logger.getLogger("SeleniumLog").debug("profileDirectory: " + profileDirectory);
			FirefoxProfile ffProfile = new FirefoxProfile(new File(profileDirectory));

			ffProfile.setPreference("browser.download.folderList", 2);
			ffProfile.setPreference("browser.download.manager.showWhenStarting", false);
			ffProfile.setPreference("browser.download.manager.alertOnEXEOpen", false);
			ffProfile.setPreference("browser.download.manager.focusWhenStarting", false);
			ffProfile.setPreference("browser.download.useDownloadDir", true);
			ffProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
			ffProfile.setPreference("browser.download.manager.closeWhenDone", true);
			ffProfile.setPreference("browser.download.manager.showAlertOnComplete", false);
			ffProfile.setPreference("browser.download.manager.useWindow", false);
			ffProfile.setPreference("services.sync.prefs.sync.browser.download.manager.showWhenStarting", false);
			ffProfile.setPreference("browser.helperApps.neverAsk.saveToDisk",
					"text/csv,application/xml,text/xml,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,"
							+ "application/pdf,application/octet-stream,images/jpeg,text/html,application/xhtml+xml,application/atom+xml,"
							+ "application/xslt+xml,image/svg+xml,application/mathml+xml,application/rss+xml,application/msword,text/plain,"
							+ "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,text/comma-separated-values,application/csv,"
							+ "application/vnd.msexcel,text/anytext");
			ffProfile.setPreference("browser.helperApps.neverAsk.openFile", "");
			ffProfile.setPreference("browser.download.dir", downloadsFolder);

			ffProfile.setAcceptUntrustedCertificates(true);
			ffProfile.setAssumeUntrustedCertificateIssuer(true);

			String path = "";
			if (System.getProperty("os.name").toLowerCase().matches(".*windows.*")) {
				path="C:\\Program Files\\Mozilla Firefox\\firefox.exe";
				//path = "C:\\Local\\Mozilla Firefox\\firefox.exe";
			} else {
				path = "/linuxpathto/firefox";
			}

			System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Jdk14Logger");

			FirefoxOptions options = new FirefoxOptions();
			options.setProfile(ffProfile);
			options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
			options.setCapability("elementScrollBehavior", 1);

			options.setBinary(path); // ONLY ON LINUX - AFTER UPGRADE TO NEW FIREFOX

			System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");

			webDriver = new FirefoxDriver(options);

			Logger.getLogger("SeleniumLog").info("Firefox profile was initialized");

		} else if (OPERA.equals(browser)) {
			
			System.setProperty("webdriver.opera.driver", "Profile\\Windows\\operadriver.exe");
			webDriver = new OperaDriver();

		} else if (INTERNET_EXPLORER.equals(browser)) {
			webDriver = new InternetExplorerDriver();

		} else if (EDGE.equals(browser)) {
			webDriver = new EdgeDriver();
		}

		else if (SAFARI.equals(browser)) {
			webDriver = new SafariDriver();

		} else {

			// HTMLunit Check
			// if (username != null && password != null) {
			// webDriver = (HtmlUnitDriver)
			// AuthenticatedHtmlUnitDriver.create(username, password);
			// } else {
			webDriver = new HtmlUnitDriver(true);
			// }
		}

		return webDriver;
	}

	public void removeWebDriver() {

		if (webDriver != null) {
			try {
				try {
					webDriver.close();
				} catch (WebDriverException ex) {
					ex.printStackTrace();
					Logger.getLogger("SeleniumLog").error("Error close() web driver: " + ex.getMessage());
				}
				try {
					webDriver.quit();
				} catch (WebDriverException ex) {
			      ex.printStackTrace();
					Logger.getLogger("SeleniumLog").error("Error quit() web driver: " + ex.getMessage());
				}
			} catch (Throwable t) {
				Logger.getLogger("SeleniumLog").error("Error quit web driver: " + t.getMessage());
			}
			webDriver = null;
		}
	}


	/*
	 * Factory method to return a RemoteWebDriver instance given the url of the
	 * Grid hub and a Browser instance.
	 * @param gridHubUrl : grid hub URI
	 * @param browser : Browser object containing info around the browser to hit
	 * @param username : username for BASIC authentication on the page to test
	 * @param password : password for BASIC authentication on the page to test
	 * @return RemoteWebDriver
	 */
	// public static WebDriver getInstance(String gridHubUrl, Browser browser,
	// String username, String password) {
	//
	// WebDriver webDriver = null;
	//
	// DesiredCapabilities capability = new DesiredCapabilities();
	// String browserName = browser.getName();
	// capability.setJavascriptEnabled(true);
	//
	// // In case there is no Hub
	// if (gridHubUrl == null || gridHubUrl.length() == 0) {
	// return getInstance(browserName, username, password);
	// }
	//
	// if (CHROME.equals(browserName)) {
	// capability = DesiredCapabilities.chrome();
	// } else if (FIREFOX.equals(browserName)) {
	// capability = DesiredCapabilities.firefox();
	//
	// FirefoxProfile ffProfile = new FirefoxProfile();
	//
	// // Authenication Hack for Firefox
	// if (username != null && password != null) {
	// ffProfile.setPreference("network.http.phishy-userpass-length", 255);
	// capability.setCapability(FirefoxDriver.PROFILE, ffProfile);
	// }
	//
	// capability.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
	// } else if (INTERNET_EXPLORER.equals(browserName)) {
	//
	// capability = DesiredCapabilities.internetExplorer();
	// capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
	// true);
	// } else if (PHANTOMJS.equals(browserName)) {
	// capability = DesiredCapabilities.phantomjs();
	// } else if (OPERA.equals(browserName)) {
	// capability = DesiredCapabilities.opera();
	// } else if (SAFARI.equals(browserName)) {
	// capability = DesiredCapabilities.safari();
	// } else {
	//
	// capability = DesiredCapabilities.htmlUnit();
	// // HTMLunit Check
	// if (username != null && password != null) {
	// webDriver = (HtmlUnitDriver) AuthenticatedHtmlUnitDriver.create(username,
	// password);
	// } else {
	// webDriver = new HtmlUnitDriver(true);
	// }
	//
	// return webDriver;
	// }
	//
	// capability = setVersionAndPlatform(capability, browser.getVersion(),
	// browser.getPlatform());
	//
	// // Create Remote WebDriver
	// try {
	// webDriver = new RemoteWebDriver(new URL(gridHubUrl), capability);
	// } catch (MalformedURLException e) {
	// e.printStackTrace();
	// }
	//
	// return webDriver;
	// }

	/*
	 * Helper method to set version and platform for a specific browser
	 * @param capability : DesiredCapabilities object coming from the selected
	 * browser
	 * @param version : browser version
	 * @param platform : browser platform
	 * @return DesiredCapabilities
	 */
	//private DesiredCapabilities setVersionAndPlatform(DesiredCapabilities capability, String version, String platform) {
	// if (MAC.equalsIgnoreCase(platform)) {
	// capability.setPlatform(Platform.MAC);
	// } else if (LINUX.equalsIgnoreCase(platform)) {
	// capability.setPlatform(Platform.LINUX);
	// } else if (XP.equalsIgnoreCase(platform)) {
	// capability.setPlatform(Platform.XP);
	// } else if (VISTA.equalsIgnoreCase(platform)) {
	// capability.setPlatform(Platform.VISTA);
	// } else if (WINDOWS.equalsIgnoreCase(platform)) {
	// capability.setPlatform(Platform.WINDOWS);
	// } else if (ANDROID.equalsIgnoreCase(platform)) {
	// capability.setPlatform(Platform.ANDROID);
	// } else {
	// capability.setPlatform(Platform.ANY);
	// }

	// if (version != null) {
	// capability.setVersion(version);
	// }
	//	return capability;
	//}


}