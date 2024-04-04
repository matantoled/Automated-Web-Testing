package pages.super_pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages._pages_mngt.MainPageManager;
import util.SelUtils;

/*
 * Abstract class representation of a Page in the UI. Page object pattern
 * @author Sebastiano Armeli-Battana
 */
public abstract class Page {

	protected WebDriver driver;
	protected SelUtils su;
	protected WebDriverWait waitZero;
	protected WebDriverWait waitSmall;
	protected WebDriverWait waitMedium;
	protected WebDriverWait waitBig;
	protected WebDriverWait waitVeryBig;
	protected MainPageManager pages;
	protected Logger log;
	protected boolean ux;

	/*
	 * Constructor injecting the WebDriver interface
	 * @param webDriver
	 */
	public Page(MainPageManager pages) {
		this.su = pages.su;
		this.pages = pages;
		driver = pages.getWebDriver();
		log = pages.gerLogger();

		waitZero = new WebDriverWait(pages.getWebDriver(), 0);
		waitSmall = new WebDriverWait(pages.getWebDriver(), 4);
		waitMedium = new WebDriverWait(pages.getWebDriver(), 15);
		waitBig = new WebDriverWait(pages.getWebDriver(), 30);
		waitVeryBig = new WebDriverWait(pages.getWebDriver(), 60 * 3);
	}

	public Page ensurePageLoaded() {
		return this;
	}

}
