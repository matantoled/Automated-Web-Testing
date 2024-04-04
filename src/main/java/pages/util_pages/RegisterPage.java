package pages.util_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import pages._pages_mngt.MainPageManager;
import pages.super_pages.MenusPage;

public class RegisterPage extends MenusPage {

	public RegisterPage(MainPageManager pages) {
		super(pages);
	}

	@FindBy(id = "register-button")
	private WebElement registerButton;

	public RegisterPage ensurePageLoaded() {
		super.ensurePageLoaded();
		waitBig.until(ExpectedConditions.visibilityOf(registerButton));
		return this;
	}

	public RegisterPage chooseGender(String gender) {
		log.info("Select gender " + gender);
		if (gender.equals("male"))
			driver.findElement(By.id("gender-male")).click();
		else
			driver.findElement(By.id("gender-female")).click();
		return this;
	}

	public RegisterPage setFirstName(String firstName) {
		log.info("Enter first name");
		driver.findElement(By.id("FirstName")).sendKeys(firstName);
		return this;
	}

	public RegisterPage setLastName(String lastName) {
		log.info("Enter last name");
		driver.findElement(By.id("LastName")).sendKeys(lastName);
		return this;
	}

	public RegisterPage selectBirthdayDay(String day) {
		log.info("Select birthday day.");
		Select dayDropdown = new Select(driver.findElement(By.name("DateOfBirthDay")));
		dayDropdown.selectByVisibleText(day);
		return this;
	}


	public RegisterPage selectBirthdayMonth(String month) {
		log.info("Select birthday month.");
		Select monthDropdown = new Select(driver.findElement(By.name("DateOfBirthMonth")));
		monthDropdown.selectByVisibleText(month);

		return this.ensurePageLoaded();
	}

	public RegisterPage selectBirthdayYear(String year) {
		log.info("Select birthday year.");
		Select yearDropdown = new Select(driver.findElement(By.name("DateOfBirthYear")));
		yearDropdown.selectByVisibleText(year);
		return this;
	}

	public RegisterPage setMail(String email) {
		log.info("Set email " + email);
		driver.findElement(By.id("Email")).sendKeys(email);
		return this;
	}

	public RegisterPage setPassword(String password) {
		log.info("Type password " + password);
		driver.findElement(By.id("Password")).sendKeys(password);
		return this;
	}

	public RegisterPage setPasswordVerification(String password) {
		log.info("Type password verification.");
		driver.findElement(By.id("ConfirmPassword")).sendKeys(password);
		return this;
	}

	public RegisterSuccessPage clickRegisterButton() {
		log.info("Click register button");
		driver.findElement(By.id("register-button")).click();
		return pages.registerSuccessPage.ensurePageLoaded();
	}

}
