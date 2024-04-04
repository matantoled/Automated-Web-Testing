package tests.basic;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import tests.supers.TestBase;
import util.GenUtils;

public class LinearOldCode extends TestBase {

	String timestamp, email, password;

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
	}

	private void registerNewUser() {

		WebDriver driver = app.getDriver();

		log.info("Click Register");
		driver.findElement(By.linkText("Register")).click();

		log.info("Select male");
		driver.findElement(By.id("gender-male")).click();

		log.info("Enter first name");
		driver.findElement(By.id("FirstName")).sendKeys("Name1");

		log.info("Enter last name");
		driver.findElement(By.id("LastName")).sendKeys("Name2");

		log.info("Select Birthday");
		Select day = new Select(driver.findElement(By.name("DateOfBirthDay")));
		day.selectByVisibleText("1");

		Select month = new Select(driver.findElement(By.name("DateOfBirthMonth")));
		month.selectByVisibleText("January");

		Select year = new Select(driver.findElement(By.name("DateOfBirthYear")));
		year.selectByVisibleText("1920");

		log.info("Type randomized email");

		log.info("Randomized email is: " + email);
		driver.findElement(By.id("Email")).sendKeys(email);

		log.info("Type password");
		driver.findElement(By.id("Password")).sendKeys(password);
		driver.findElement(By.id("ConfirmPassword")).sendKeys(password);

		log.info("Click register button");
		driver.findElement(By.id("register-button")).click();

		String actualTextRegistration = driver.findElement(By.cssSelector(".result")).getText();
		String expectedTextRegistration = "Your registration completed";
		Assert.assertTrue(actualTextRegistration.equals(expectedTextRegistration),
				"Expected value: '" + expectedTextRegistration + "', but actual is '" + actualTextRegistration + "'");

		driver.findElement(By.cssSelector(".register-continue-button")).click();
		String firstScreenText = driver.findElement(By.cssSelector(".topic-block-title > h2")).getText();
		String expectedTextFirstScreen = "Welcome to our store";
		Assert.assertTrue(firstScreenText.equals(expectedTextFirstScreen),
				"Expected value: '" + expectedTextFirstScreen + "', but actual is '" + actualTextRegistration + "'");

		log.info("Click logout button");
		driver.findElement(By.xpath("//a[@class='ico-logout']")).click();

	}

	private void shoppingProcessEndToEnd() {
		
	    
	}

}

