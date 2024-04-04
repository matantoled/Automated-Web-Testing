package applogic;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pages._pages_mngt.MainPageManager;
import util.SelUtils;

public class DriverBasedHelper {

	protected WebDriver driver;
	protected SelUtils su;
	protected MainPageManager pages;
	protected ApplicationManager1 app;
	protected Logger log;

	public DriverBasedHelper(ApplicationManager1 app) {
		this.app = app;
		driver = app.getDriver();
		su = app.su;
		log = app.getLogger();
		pages = app.pages();
	}

}
