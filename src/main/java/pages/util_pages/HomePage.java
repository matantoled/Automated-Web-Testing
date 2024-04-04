package pages.util_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import pages._pages_mngt.MainPageManager;
import pages.super_pages.MenusPage;

public class HomePage extends MenusPage {

	public HomePage(MainPageManager pages) {
		super(pages);
	}

	private final static String HOME_MESSAGE = "Welcome to our store";
	private String welcome=".topic-block-title > h2";

	public HomePage ensurePageLoaded() {
		super.ensurePageLoaded();
		waitBig.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(welcome))));
		return this;
	}

	public HomePage verifyHomeText() {
		log.info("Verify home text is "+HOME_MESSAGE);
		String actualMessage = driver.findElement(By.cssSelector(welcome)).getText();

		Assert.assertTrue(actualMessage.equals(HOME_MESSAGE),
				"Expected value: '" + HOME_MESSAGE + "', but actual is '" + actualMessage + "'");
		return this;
	}


}
