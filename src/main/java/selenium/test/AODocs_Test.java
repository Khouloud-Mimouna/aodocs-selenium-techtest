package selenium.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import selenium.driver.Browser;
import selenium.driver.WebDriverUtility;

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class AODocs_Test {

	private WebDriver driver;
	private String baseUrl;

	@BeforeAll
	public void setUp() throws Exception {
		// On instancie notre driver, et on configure notre temps d'attente
		driver = WebDriverUtility.getWebDriver(Browser.CHROME);
		baseUrl = "https://www.google.com";
	}

	@Test
	@Order(1)
	public void seachAODocsInGoogle() throws Exception {

		// On se connecte au google
		driver.get(baseUrl);

		// Search AODocs in Google
		WebElement search = driver.findElement(By.name("q"));
		search.sendKeys("AODocs");
		search.submit();

		// BEFORE YOU CONTINUE GOOGLE POPUP
		driver.switchTo().frame(0);
		WebElement agreeButton = driver.findElement(By.id("introAgreeButton"));
		agreeButton.click();

		// In the result, open the website www.aodocs.com
		WebElement result = driver.findElement(By.xpath("//a[@href ='https://www.aodocs.com/']"));
		result.click();

	}

	@Test
	@Order(2)
	public void requestDemo() throws Exception {
		// To wait for element visible

		// Into the website click on the "Request a demo" button
		WebElement demoButton = driver
				.findElement(By.xpath("//a[@href ='https://www.aodocs.com/contact?request_type=request_demo']"));
		demoButton.click();

		// Fill the form with a "first name"
		WebElement firstnameInput = driver.findElement(By.name("firstname"));
		firstnameInput.sendKeys("khouloud");

		// Set empty in the "last name" field
		WebElement lastnameInput = driver.findElement(By.name("lastname"));
		lastnameInput.sendKeys("");

		// Fill a random string in the "Your Email" field
		WebElement emailInput = driver.findElement(By.name("email"));
		emailInput.sendKeys(RandomStringUtils.randomAlphabetic(6));

		// Choose a value in Company Size
		WebElement companySizeValue = driver.findElement(By.xpath("//select[@name='company_size__c']//option[3]"));
		companySizeValue.click();
	}

	@Test
	@Order(3)
	public void checkTheErrorMessages() throws Exception {

		// Check the error messages of the empty fields

		WebElement errorMesgEmpty = driver
				.findElement(By.xpath("//div[contains(@class,'hs_lastname')]//label[@class='hs-error-msg']"));
		String errorMesgEmptyText = errorMesgEmpty.getText();
		assertEquals(errorMesgEmptyText, "Please complete this required field.");

		// Check the error messages of the email format

		WebElement errorMesgEmail = driver
				.findElement(By.xpath("//div[contains(@class,'hs_email')]//label[@class='hs-error-msg']"));
		String errorMesgEmailText = errorMesgEmail.getText();
		assertEquals(errorMesgEmailText, "Email must be formatted correctly.");

	}

	@AfterAll
	public void closeBrowser() {
		driver.quit();
	}
}
