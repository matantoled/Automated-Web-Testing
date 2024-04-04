package pages.util_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages._pages_mngt.MainPageManager;
import pages.super_pages.MenusPage;

public class LoginPage extends MenusPage{

	public LoginPage(MainPageManager pages) {
		super(pages);
	}

	public LoginPage ensurePageLoaded() {
		super.ensurePageLoaded();
		waitBig.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".login-button"))));
		return this;
	}
	
	public LoginPage enterEmail(final String email ) {
		log.info("Type email " + email);
		driver.findElement(By.id("Email")).sendKeys(email);
		return this;
	}
	
	public LoginPage enterPassword(final String password) {
		log.info("Type password " + password);
		driver.findElement(By.id("Password")).sendKeys(password);
		return this;
	}
	
	public HomePage clickLoginButton() {
		log.info("Click login button");
		driver.findElement(By.cssSelector(".login-button")).click();
		return pages.homePage.ensurePageLoaded();
	}
}
