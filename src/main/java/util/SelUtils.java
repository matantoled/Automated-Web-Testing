package util;

import java.text.MessageFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class SelUtils {

	public SelUtils(WebDriver driverSU, Logger log) {
		this.log = log;
		driver = driverSU;
		waitSmall = new WebDriverWait(driver, 5);
		waitBig = new WebDriverWait(driver, 30);
	}

	Logger log = Logger.getLogger("SeleniumLog");

	private WebDriver driver;
	private WebDriverWait waitSmall;
	private WebDriverWait waitBig;

	public enum LocatorType {
		Id, Xpath, LinkText, PartialLinkText, CSS, Name, ClassName
	}

	public String getValue(WebElement el) {
		return el.getAttribute("value");
	}


	public void clearAndSendKeys(String text, LocatorType type, String locator, Object... placeholders) {
		WebElement e = getElement(type, locator, placeholders);
		clearAndSendKeys(e, text);
	}

	public void clearAndSendKeys(WebElement we, String text) {
		log.debug("Clear and sendKeys: '" + text + "'");
		we.clear();
		we.sendKeys(text);
	}

	public boolean isElementExist(LocatorType type, String locator, Object... placeholders) {
		return getListOfElements(type, locator, placeholders).size() > 0;
	}

	public boolean isElementExist(WebElement e) {
		try {
			e.getText();
		} catch (Exception exp) {
			return false;
		}
		return true;
	}

	public boolean isEnabled(LocatorType type, String locator, Object... placeholders) {
		if (isElementExist(type, locator, placeholders)) {
			WebElement element = getElement(type, locator, placeholders);
			return element.isEnabled();
		}
		return false;
	}

	public String getSelectedValueTextFromSelect(WebElement selectTextBox) {
		log.debug("Get selected value text from Select");
		return new Select(selectTextBox).getFirstSelectedOption().getText();
	}

	public void selectByTextFromSelect(WebElement selectTextBox, String textToSelect) {
		log.debug("Select by text: " + textToSelect + " from Select");
		new Select(selectTextBox).selectByVisibleText(textToSelect);
	}

	public void selectByValueFromSelect(WebElement selectTextBox, String textToSelect) {
		log.debug("Select by value: " + textToSelect + " from Select");
		new Select(selectTextBox).selectByValue(textToSelect);
	}

	public void selectByIndexFromSelect(WebElement selectTextBox, int index) {
		log.debug("Select by index: " + index + " from Select");
		new Select(selectTextBox).selectByIndex(index);
	}

	public void waitPageLoaded(WebElement elementOnPage, String pageName) {
		try {
			waitBig.until(ExpectedConditions.visibilityOf(elementOnPage));
		} catch (Exception e) {
			Assert.fail("Expected page '" + pageName.replace("class ", "") + "' wasn't loaded!!!");
		}

	}

	public boolean waitNoFailure(WebDriverWait wait, By by) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}


	public void switchToFrame(LocatorType locatorType, String framePath, Object... index) {
		driver.switchTo().frame(getElement(locatorType, framePath, index));
	}

	public void switchToDefaultFrame() {
		driver.switchTo().defaultContent();
	}


	public void closeCurrentBrowserTab() {
		log.debug("Close current tab in browser");
		getElement(LocatorType.CSS, "body").sendKeys(Keys.CONTROL + "w");
	}

	public void switchBrowserTab() {
		log.debug("Switch tab in browser");
		String current = driver.getWindowHandle();
		for (String winHandle : driver.getWindowHandles())
			if (!current.equals(winHandle)) {
				driver.switchTo().window(winHandle);
				break;
			}
	}


	public void moveToElementAndClick(WebElement we) {
		moveToElement(we);
		we.click();
	}

	public void moveToElementAndClick(LocatorType type, String locator, Object... placeholders) {
		moveToElementAndClick(getElement(type, locator, placeholders));
	}

	public void moveToElementAndRightClick(WebElement we) {
		moveToElement(we);
		rightClick(we);
	}

	public void moveToElementAndRightClick(LocatorType type, String locator, Object... placeholders) {
		moveToElementAndRightClick(getElement(type, locator, placeholders));
	}

	public void moveToElement(WebElement e) {
		Actions actions = new Actions(driver);
		actions.moveToElement(e).perform();
	}

	public void moveToElement(LocatorType type, String locator, Object... placeholders) {
		moveToElement(getElement(type, locator, placeholders));
	}

	public void dragAndDropElement(WebElement source, WebElement destination) {
		Actions actions = new Actions(driver);
		actions.dragAndDrop(source, destination).build().perform();
	}

	public void rightClick(WebElement e) {
		try {
			Actions action = new Actions(driver).contextClick(e);
			action.build().perform();
		} catch (Exception ex) {
			rightClickJS(e);
		}
	}

	public void rightClick(LocatorType type, String locator, Object... placeholders) {
		rightClick(getElement(type, locator, placeholders));
	}

	public void verifyElementExist(LocatorType type, String locator, Object... placeholders) {
		Assert.assertTrue(isElementExist(type, locator, placeholders), "The Element doesn't exist!");
	}

	public void verifyElementDoesNotExist(LocatorType type, String locator, Object... placeholders) {
		Assert.assertFalse(isElementExist(type, locator, placeholders), "The Element does exist!");
	}

	public void verifyElementExist(WebElement e) {
		Assert.assertTrue(isElementExist(e), "The Element doesn't exist!");
	}

	public void waitElementVisibleAndSendKeys(String text, LocatorType type, String locator, Object... placeholders) {
		WebElement el = waitSmall.until(ExpectedConditions.visibilityOfElementLocated(getByLocator(type, locator, placeholders)));
		clearAndSendKeys(el, text);
	}

	public void waitElementVisibleAndSendKeys(String text, LocatorType type, String locator) {
		WebElement el = waitSmall.until(ExpectedConditions.visibilityOfElementLocated(getByLocator(type, locator)));
		clearAndSendKeys(el, text);
	}

	public void waitElementVisibleAndSendKeys(String text, WebElement e) {
		clearAndSendKeys(waitSmall.until(ExpectedConditions.visibilityOf(e)), text);
	}

	public void waitElementVisibleAndClick(LocatorType type, String locator, Object... placeholders) {
		waitSmall.until(ExpectedConditions.visibilityOfElementLocated(getByLocator(type, locator, placeholders))).click();
	}

	public void waitElementVisibleAndClick(LocatorType type, String locator) {
		waitSmall.until(ExpectedConditions.visibilityOfElementLocated(getByLocator(type, locator))).click();
	}

	public void waitElementVisibleAndClick(WebElement e) {
		waitSmall.until(ExpectedConditions.visibilityOf(e)).click();
	}

	public void waitElementVisible(LocatorType type, String locator, Object... placeholders) {
		waitSmall.until(ExpectedConditions.visibilityOfElementLocated(getByLocator(type, locator, placeholders)));
	}

	public void waitBigElementVisible(LocatorType type, String locator, Object... placeholders) {
		waitBig.until(ExpectedConditions.visibilityOfElementLocated(getByLocator(type, locator, placeholders)));
	}

	public void waitElementVisible(WebElement e) {
		waitSmall.until(ExpectedConditions.visibilityOf(e));
	}

	public void waitElementInvisible(LocatorType type, String locator, Object... placeholders) {
		waitBig.until(ExpectedConditions.invisibilityOfElementLocated(getByLocator(type, locator, placeholders)));
	}

	public void waitElementClickableAndClick(LocatorType type, String locator, Object... placeholders) {
		waitSmall.until(ExpectedConditions.elementToBeClickable(getByLocator(type, locator, placeholders))).click();
	}

	public void waitElementClickableAndClick(WebElement e) {
		waitSmall.until(ExpectedConditions.elementToBeClickable(e)).click();
	}

	public void waitElementClickableAndClickJS(WebElement e) {
		waitSmall.until(ExpectedConditions.elementToBeClickable(e));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", e);
	}

	public void waitElementClickableAndClickJS(LocatorType type, String locator, Object... placeholders) {
		waitSmall.until(ExpectedConditions.elementToBeClickable(getByLocator(type, locator, placeholders)));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", getElement(type, locator, placeholders));
	}

	public void waitElementClickable(LocatorType type, String locator, Object... placeholders) {
		waitSmall.until(ExpectedConditions.elementToBeClickable(getByLocator(type, locator, placeholders)));
	}

	public void waitBigElementClickable(LocatorType type, String locator, Object... placeholders) {
		waitBig.until(ExpectedConditions.elementToBeClickable(getByLocator(type, locator, placeholders)));
	}

	public void waitElementClickable(WebElement e) {
		waitSmall.until(ExpectedConditions.elementToBeClickable(e));
	}

	public By getByLocator(LocatorType type, String locator, Object... placeholders) {
		locator = createDynamicLocator(locator, placeholders);
		return getByLocator(type, locator);
	}

	public By getByLocator(LocatorType type, String locator) {
		switch (type) {
			case CSS:
				return By.cssSelector(locator);
			case ClassName:
				return By.className(locator);
			case Id:
				return By.id(locator);
			case LinkText:
				return By.linkText(locator);
			case Name:
				return By.name(locator);
			case PartialLinkText:
				return By.partialLinkText(locator);
			case Xpath:
				return By.xpath(locator);
		}
		return By.id(locator);
	}


	public String createDynamicLocator(String locator, Object... placeholders) {
		if (placeholders.length < 1)
			return MessageFormat.format(locator.replaceAll("(?<!')'(?!')", "''"), 0);
		else {
			return MessageFormat.format(locator.replaceAll("(?<!')'(?!')", "''"), placeholders);
		}
	}

	public void getElementAndClick(LocatorType type, String locator, Object... placeholders) {
		getElement(type, locator, placeholders).click();
	}

	public void getElementAndClickIfExists(LocatorType type, String locator, Object... placeholders) {
		if (isElementExist(type, locator, placeholders)) {
			getElementAndClick(type, locator, placeholders);
		}
	}

	public void getElementAndClickJSIfExists(LocatorType type, String locator, Object... placeholders) {
		if (isElementExist(type, locator, placeholders)) {
			clickJS(type, locator, placeholders);
		}
	}

	public void getElementAndClickIfVisible(LocatorType type, String locator, Object... placeholders) {
		if (isElementVisible(type, locator, placeholders)) {
			getElementAndClick(type, locator, placeholders);
		}
	}

	public boolean isElementVisible(LocatorType type, String locator, Object... placeholders) {
		if (isElementExist(type, locator, placeholders))
			return getElement(type, locator, placeholders).isDisplayed();
		return false;
	}

	public String getElementAndGetText(LocatorType type, String locator, Object... placeholders) {
		return getElement(type, locator, placeholders).getText();
	}

	public String getElementAndGetValue(LocatorType type, String locator, Object... placeholders) {
		return getElement(type, locator, placeholders).getAttribute("value");
	}

	public String getElementAndGetTitle(LocatorType type, String locator, Object... placeholders) {
		return getElement(type, locator, placeholders).getAttribute("title");
	}

	public String getElementAndGetAlt(LocatorType type, String locator, Object... placeholders) {
		return getElement(type, locator, placeholders).getAttribute("alt");
	}


	public WebElement getElement(LocatorType type, String locator, Object... placeholders) {
		locator = createDynamicLocator(locator, placeholders);

		switch (type) {
			case CSS:
				return driver.findElement(By.cssSelector(locator));
			case Id:
				return driver.findElement(By.id(locator));
			case LinkText:
				return driver.findElement(By.linkText(locator));
			case PartialLinkText:
				return driver.findElement(By.partialLinkText(locator));
			case Xpath:
				return driver.findElement(By.xpath(locator));
			case Name:
				return driver.findElement(By.name(locator));
			case ClassName:
				return driver.findElement(By.className(locator));
			default:
				log.debug("No locator type!");
				return null;
		}
	}

	public List<WebElement> getListOfElements(LocatorType type, String locator, Object... placeholders) {
		locator = createDynamicLocator(locator, placeholders);
		switch (type) {
			case CSS:
				return driver.findElements(By.cssSelector(locator));
			case Id:
				return driver.findElements(By.id(locator));
			case LinkText:
				return driver.findElements(By.linkText(locator));
			case PartialLinkText:
				return driver.findElements(By.partialLinkText(locator));
			case Xpath:
				return driver.findElements(By.xpath(locator));
			case Name:
				return driver.findElements(By.name(locator));
			case ClassName:
				return driver.findElements(By.className(locator));
			default:
				return null;
		}
	}


	public boolean waitForJStoLoad() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// wait for jQuery to load
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return (int) js.executeScript("return $.active") == 0;
				} catch (Exception e) {
					return true;
				}
			}
		};

		// wait for Javascript to load
		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver driver) {
				return js.executeScript("return document.readyState").toString().equals("complete");
			}
		};
		return waitBig.until(jQueryLoad) && waitBig.until(jsLoad);
	}




	public void clickJS(LocatorType type, String locator, Object... placeholders) {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", getElement(type, locator, placeholders));
	}

	public void clickJS(WebElement e) {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", e);
	}

	public String getTextJS(LocatorType type, String locator, Object... placeholders) {
		return (String) ((JavascriptExecutor) driver).executeScript("return $(arguments[0]).text();", getElement(type, locator, placeholders));
	}

	public String getInnerTextJS(WebElement e) {
		return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].innerHTML", e);
	}

	public void rightClickJS(LocatorType type, String locator, Object... placeholders) {
		((JavascriptExecutor) driver).executeScript("$(arguments[0]).contextmenu();", getElement(type, locator, placeholders));
	}

	public void rightClickJS(WebElement e) {
		((JavascriptExecutor) driver).executeScript("$(arguments[0]).contextmenu();", e);
	}

	public void scrollPageUp() {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0)");
	}

	public void scrollPageDown() {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	public void scrollToElement(WebElement e) {
		log.debug("Scroll to element");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		if (js.executeScript("return navigator.userAgent;").toString().contains("Chrome"))
			js.executeScript("arguments[0].scrollIntoView({block: 'center'});", e);
		else //firefox
			js.executeScript("arguments[0].scrollIntoView({block: 'end'});", e);
	}

	public void scrollToElement(LocatorType type, String locator, Object... placeholders) {
		scrollToElement(getElement(type, locator, placeholders));
	}


	public void clickKeyboard(CharSequence key) {
		Keyboard keyBoard = ((HasInputDevices) driver).getKeyboard();
		keyBoard.pressKey(key);
		keyBoard.releaseKey(key);
	}


	public boolean isElementVisible(WebElement element) {
		return element.isDisplayed();
	}
}
