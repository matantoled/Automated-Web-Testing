package pages.util_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages._pages_mngt.MainPageManager;
import pages.super_pages.MenusPage;

public class CategoryContentPage extends MenusPage {

	public CategoryContentPage(MainPageManager pages) {
		super(pages);
	}
	
	public CategoryContentPage ensurePageLoaded() {
		super.ensurePageLoaded();
		waitBig.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".page-title"))));
		return this;
	}
	

	public ItemsListPage clickCellPhones() {
		log.info("Click Cell phones button");
	    driver.findElement(By.cssSelector(".item-box:nth-child(2) .title > a")).click();
		return pages.itemsListPage.ensurePageLoaded();		
	}
}
