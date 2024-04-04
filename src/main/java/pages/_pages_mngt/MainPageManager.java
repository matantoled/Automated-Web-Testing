package pages._pages_mngt;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import applogic.ApplicationManager1;
import pages._pages_mngt.page_factory.DisplayedElementLocatorFactory;
import pages.super_pages.MenusPage;
import pages.super_pages.Page;
import pages.util_pages.CategoryContentPage;
import pages.util_pages.CheckoutPage;
import pages.util_pages.HomePage;
import pages.util_pages.ItemsListPage;
import pages.util_pages.LoginPage;
import pages.util_pages.OrderSuccessPage;
import pages.util_pages.RegisterPage;
import pages.util_pages.RegisterSuccessPage;
import pages.util_pages.ShoppingCartPage;
import util.ParamsUtils;
import util.SelUtils;

public class MainPageManager {

	private WebDriver driver;
	public SelUtils su;
	private Logger log;
	private ParamsUtils sessionParams;

	//Pages
	public MenusPage menusPage;
	public RegisterPage registerPage;
	public RegisterSuccessPage registerSuccessPage;
	public HomePage homePage;
	public LoginPage loginPage;
	public CategoryContentPage categoryContentPage;
	public ItemsListPage itemsListPage;
	public ShoppingCartPage shoppingCartPage;
	public CheckoutPage checkoutPage;
	public OrderSuccessPage orderSuccessPage;

	public MainPageManager(ApplicationManager1 app) {
		driver = app.getDriver();
		su = app.su;
		log = app.getLogger();
		sessionParams = app.getParamsUtils();	
		

		//Pages initialization
		menusPage = initElements(new MenusPage(this));
		homePage=initElements(new HomePage(this));
		registerPage = initElements(new RegisterPage(this));
		registerSuccessPage=initElements(new RegisterSuccessPage(this));
		loginPage = initElements(new LoginPage(this));
		categoryContentPage = initElements(new CategoryContentPage(this));
		itemsListPage = initElements(new ItemsListPage(this));
		shoppingCartPage = initElements(new ShoppingCartPage(this)); 
		checkoutPage = initElements(new CheckoutPage(this)); 
		orderSuccessPage = initElements(new OrderSuccessPage(this)); 
		
	}

	public <T extends Page> T initElements(T page) {
		// PageFactory.initElements(driver, page);
		// PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10),
		// page);
		PageFactory.initElements(new DisplayedElementLocatorFactory(driver, 10), page);
		return page;
	}

	public WebDriver getWebDriver() {
		return driver;
	}

	public Logger gerLogger() {
		return log;
	}

	public ParamsUtils getSessionParams() {
		return sessionParams;
	}
}
