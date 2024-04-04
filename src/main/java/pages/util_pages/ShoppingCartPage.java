package pages.util_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import pages._pages_mngt.MainPageManager;
import pages.super_pages.MenusPage;

public class ShoppingCartPage extends MenusPage{

		public ShoppingCartPage(MainPageManager pages) {
			super(pages);
		}

		public ShoppingCartPage ensurePageLoaded() {
			super.ensurePageLoaded();
			waitBig.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("termsofservice"))));
			return this;
		}
		
		public ShoppingCartPage verifyPrice(String expectedPrice) {
			String actualPrice = driver.findElement(By.cssSelector(".value-summary > strong")).getText();
			Assert.assertTrue(expectedPrice.equals(actualPrice),
					"Expected price: '" + expectedPrice + "', but actual price is '" + actualPrice + "'");
			return this;
		}

		public ShoppingCartPage clickTermsOfServiceCheckbox() {
			log.info("Click terms of service checkbox");	 
		    driver.findElement(By.id("termsofservice")).click();		
			return this;
		}

		public CheckoutPage clickCheckoutButton() {
			log.info("Click CHECKOUT button");	 
		    driver.findElement(By.id("checkout")).click();
		    return pages.checkoutPage.ensurePageLoaded();
		}
		
}
