package selenium.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;
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
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

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
		WebElement agree_button = driver.findElement(By.id("introAgreeButton"));
		agree_button.click();

		// In the result, open the website www.aodocs.com
		WebElement result = driver.findElement(By.xpath("//a[@href ='https://www.aodocs.com/']"));
		result.click();

	}

	@Test
    @Order(2)
	public void requestDemo() throws Exception {
		// To wait for element visible

	
		// Into the website click on the "Request a demo" button
		WebElement demo_button = driver
				.findElement(By.xpath("//a[@href ='https://www.aodocs.com/contact?request_type=request_demo']"));
		demo_button.click();

		// Fill the form with a first name
		WebElement firstname_input = driver.findElement(By.name("firstname"));
		firstname_input.sendKeys("khouloud");

		// set empty in the "Last Name" field
		WebElement lastname_input = driver.findElement(By.name("lastname"));
		lastname_input.sendKeys("");

		// Fill a random string in the "Your Email" field
		WebElement email_input = driver.findElement(By.name("email"));
		email_input.sendKeys(RandomStringUtils.randomAlphabetic(6));

		// Choose a value in Company Size
		WebElement companySize_value = driver.findElement(By.xpath("//select[@name='company_size__c']//option[3]"));
		companySize_value.click();
	}

	@Test
    @Order(3)
	public void checkTheErrorMessages() throws Exception {


		// Check the error messages of the empty fields

		WebElement errorMesgEmpty = driver.findElement(By.xpath("//div[contains(@class,'hs_lastname')]//label[@class='hs-error-msg']"));
		String errorMesgEmpty_text = errorMesgEmpty.getText();
		assertEquals(errorMesgEmpty_text, "Please complete this required field.");

		// Check the error messages of the email format
		
		WebElement errorMesgEmail = driver.findElement(By.xpath("//div[contains(@class,'hs_email')]//label[@class='hs-error-msg']"));
		String errorMesgEmail_text = errorMesgEmail.getText();
		assertEquals(errorMesgEmail_text, "Email must be formatted correctly.");

	}
	
	 @AfterAll
	    public void closeBrowser() {

	        driver.quit();
	    }
}
