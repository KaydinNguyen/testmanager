package resources;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class base {

	public static WebDriver driver;

	public static void startBrowser() {

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://app-vm30qa.dealtap.ca/login/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.findElement(By.name("email")).sendKeys("tester@test.com");
		driver.findElement(By.name("password")).sendKeys("Abc12345");
		driver.findElement(By.xpath("//div[text()='Log In']")).click();
		driver.findElement(By.xpath("//div[contains(text(),'Test: Manager')]")).click();

	}

	public static WebElement runTest(By element) {
		return driver.findElement(element);
	}

	public static void getReport() {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(60, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

		WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(By.xpath("//div[@class='testReport-caption']"));

			}
		});

		WebElement testreport = driver.findElement(By.xpath("//div[@class='testReport-caption']"));
		List<WebElement> pr = driver.findElements(By.xpath("//div[@class='testReportFlow-tests']"));

		if (testreport.isDisplayed()) {

			System.out.println("Test case finished");
			for (int k = 0; k < pr.size(); k++) {

				System.out.println(pr.get(k).getText());

			}
		}

		else {
			System.out.println("Test case failed");
		}

		driver.close();
		
	}

}
