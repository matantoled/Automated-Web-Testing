package pages.util_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import pages._pages_mngt.MainPageManager;
import pages.super_pages.MenusPage;
import util.GenUtils;

public class CheckoutPage extends MenusPage{

		public CheckoutPage(MainPageManager pages) {
			super(pages);
		}

		public CheckoutPage ensurePageLoaded() {
			super.ensurePageLoaded();
			waitBig.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("checkout-steps"))));
			return this;
		}
		
		public CheckoutPage setCountry(String country) {
			log.info("Select Country");
			Select selectCountry = new Select(driver.findElement(By.id("BillingNewAddress_CountryId")));
			selectCountry.selectByVisibleText(country);
			return this.ensurePageLoaded();
		}
		
		public CheckoutPage setCity(String city) {
			log.info("Type city");
			driver.findElement(By.id("BillingNewAddress_City")).sendKeys(city);
			return this.ensurePageLoaded();
		}
		
		public CheckoutPage setAddress1(String address) {
			log.info("Type street address");
		    driver.findElement(By.id("BillingNewAddress_Address1")).sendKeys(address);
		    return this.ensurePageLoaded();
		}
		
		public CheckoutPage setZip(String zip) {
			log.info("Type zip postal code");
		    driver.findElement(By.id("BillingNewAddress_ZipPostalCode")).sendKeys(zip);
		    return this.ensurePageLoaded();
		}
		
		public CheckoutPage setPhone(String phone) {
			log.info("Type phone number");
		    driver.findElement(By.id("BillingNewAddress_PhoneNumber")).sendKeys(phone);
		    return this.ensurePageLoaded();
		}
		
		public CheckoutPage clickContinueToShippingMethod() {
			log.info("Click continue");
		    driver.findElement(By.name("save")).click();
		    return this.ensurePageLoaded();
		}
		
		public CheckoutPage clickContinueToPaymentMethod() {
			GenUtils.sleepSeconds(1);
		    log.info("Click continue");
		    driver.findElement(By.cssSelector(".shipping-method-next-step-button")).click();
		    return this.ensurePageLoaded();
		}
		
		public CheckoutPage	clickContinueToPaymentInfo() {
			GenUtils.sleepSeconds(1);
		    log.info("Click continue");
		    driver.findElement(By.cssSelector(".payment-method-next-step-button")).click();
		    return this.ensurePageLoaded();
		}
		
		public CheckoutPage clickContinueToConfirmOrder() {
			 GenUtils.sleepSeconds(1);
			 log.info("Click continue");
		     driver.findElement(By.cssSelector(".payment-info-next-step-button")).click();
		     return this.ensurePageLoaded();
		}
		
		public CheckoutPage verifyOrderPrice(String expectedPrice) {
			 GenUtils.sleepSeconds(1);
			 String actualPrice = driver.findElement(By.cssSelector(".product-subtotal")).getText();
			 Assert.assertTrue(actualPrice.equals(expectedPrice),
						"Expected price: '" + expectedPrice + "', but actual price is '" + actualPrice + "'");
			 return this.ensurePageLoaded();
		}
		
		public OrderSuccessPage clickConfirmOrder() {
			GenUtils.sleepSeconds(2);
		    log.info("Click confirm");
		    driver.findElement(By.cssSelector(".confirm-order-next-step-button")).click();
		    return pages.orderSuccessPage.ensurePageLoaded();
		}
		
		
		

}
