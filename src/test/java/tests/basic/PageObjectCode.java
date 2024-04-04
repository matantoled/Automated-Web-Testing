package tests.basic;

import org.testng.annotations.Test;

import tests.supers.TestBase;
import util.GenUtils;

public class PageObjectCode extends TestBase {

	String timestamp, email, password, price;

	@Test
	public void test() {
		try {

			initParameter();
			app.getDriver().get("https://demo.nopcommerce.com/");
			
			registerNewUser();
			shoppingProcessEndToEnd();
			
			endTestSuccess();
		} catch (Throwable t) {
			onTestFailure(t);
		}
	}

	private void initParameter() {
        timestamp = GenUtils.getCurrentTimestamp(GenUtils.TIME_FORMAT_ddMMyyHHmmSSS);
        email = "randomEmail" + timestamp + "@gmail.com";
        password = "123456";
        price = "";
	}

	
	private void registerNewUser() {

		app.pages().menusPage.clickRegister().chooseGender("male")
				.setFirstName("Name1").setLastName("Name2")
				.selectBirthdayDay("1").selectBirthdayMonth("January").selectBirthdayYear("2000")
				.setMail(email)
				.setPassword(password).setPasswordVerification(password)
				.clickRegisterButton()
				.verifySuccessMessage().clickContinue().verifyHomeText().logout();
	}

	private void shoppingProcessEndToEnd() {
	
		String expectedPrice = app.pages().menusPage.clickLogin().enterEmail(email).enterPassword(password)
								  .clickLoginButton().clickElectronics().clickCellPhones().getPrice();
		app.pages().itemsListPage.addItemToCart();
		log.info("Sleep 5 seconds");
	    GenUtils.sleepSeconds(5);
		app.pages().menusPage.checkAmountInCart(1).clickShoppingCart()
		.verifyPrice(expectedPrice).clickTermsOfServiceCheckbox()
		.clickCheckoutButton().setCountry("Israel").setCity("Jerusalem")
		.setAddress1("Rabin 12").setZip("9123456").setPhone("0501234567")
		.clickContinueToShippingMethod().clickContinueToPaymentMethod()
		.clickContinueToPaymentInfo().clickContinueToConfirmOrder()
		.verifyOrderPrice(expectedPrice).clickConfirmOrder()
		.verifySuccessMessage().clickContinue().checkAmountInCart(0).logout();

	}
}
