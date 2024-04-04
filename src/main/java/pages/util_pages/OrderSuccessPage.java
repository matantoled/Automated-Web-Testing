package pages.util_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import pages._pages_mngt.MainPageManager;
import pages.super_pages.MenusPage;
import util.GenUtils;

public class OrderSuccessPage extends MenusPage{

	public OrderSuccessPage(MainPageManager pages) {
		super(pages);
	}

	public OrderSuccessPage ensurePageLoaded() {
		super.ensurePageLoaded();
		waitBig.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".page-title h1"))));
		return this;
	}
	
	public OrderSuccessPage verifySuccessMessage() {
		GenUtils.sleepSeconds(1);
	    String actualMainTitle = driver.findElement(By.cssSelector(".page-title h1")).getText();
	    String MAIN_TITLE = "Thank you";
		Assert.assertTrue(MAIN_TITLE.equals(actualMainTitle),
				"Expected text title is " + MAIN_TITLE + " but the text title is " + actualMainTitle);
		
		String actualSubtitle = driver.findElement(By.cssSelector(".section > .title > strong")).getText();
		String SUBTITLE = "Your order has been successfully processed!";
		Assert.assertTrue(SUBTITLE.equals(actualSubtitle),
				"Expected subtitle is " + SUBTITLE + " but the subtitle is " + actualSubtitle);
		return this;
	}
	
	public HomePage clickContinue() {
		log.info("Click continue");
	    driver.findElement(By.cssSelector(".order-completed-continue-button")).click();
	    return pages.homePage.ensurePageLoaded();
	}
	
}