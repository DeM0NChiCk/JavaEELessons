import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.itis.semestr.config.TestConfig;

import java.util.*;

@SpringJUnitConfig(classes = TestConfig.class)
public class FullFlowTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;
    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }
    @AfterEach
    public void tearDown() {
        driver.quit();
    }
    @Test
    public void goldflowapp() {
        driver.get("http://localhost:8080/");
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.findElement(By.cssSelector(".register")).click();
        driver.findElement(By.id("email")).click();
        driver.findElement(By.id("email")).sendKeys("Tester4@test.itis.ru");
        driver.findElement(By.id("username")).click();
        driver.findElement(By.id("username")).sendKeys("Tester4");
        driver.findElement(By.id("password")).click();
        driver.findElement(By.id("password")).click();
        driver.findElement(By.id("password")).click();
        driver.findElement(By.id("password")).sendKeys("Tester1234");
        driver.findElement(By.cssSelector("button")).click();
        driver.findElement(By.id("email")).click();
        driver.findElement(By.id("email")).sendKeys("Tester4@test.itis.ru");
        driver.findElement(By.id("password")).click();
        driver.findElement(By.id("password")).sendKeys("Tester1234");
        driver.findElement(By.cssSelector("button")).click();
        driver.findElement(By.cssSelector(".news-item:nth-child(1) > button")).click();
        driver.findElement(By.cssSelector(".news-item:nth-child(4) > button")).click();
        driver.findElement(By.linkText("Каталог")).click();
        driver.findElement(By.cssSelector("label:nth-child(1) > .category-filter")).click();
        driver.findElement(By.cssSelector("label:nth-child(9) > .category-filter")).click();
        driver.findElement(By.cssSelector("label:nth-child(9)")).click();
        driver.findElement(By.cssSelector("label:nth-child(1)")).click();
        driver.findElement(By.cssSelector(".product-item:nth-child(1) button:nth-child(1)")).click();
        {
            WebElement element = driver.findElement(By.cssSelector(".product-item:nth-child(1) button:nth-child(1)"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        {
            WebElement element = driver.findElement(By.tagName("body"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element, 0, 0).perform();
        }
        driver.findElement(By.cssSelector(".close")).click();
        driver.findElement(By.cssSelector(".product-item:nth-child(1) .toggle-favorite")).click();
        driver.findElement(By.cssSelector(".product-item:nth-child(2) .toggle-favorite")).click();
        driver.findElement(By.cssSelector(".right-buttons > button")).click();
        driver.findElement(By.cssSelector(".product-item:nth-child(2) .toggle-favorite")).click();
        driver.findElement(By.cssSelector(".buttons > button:nth-child(1)")).click();
        Assertions.assertEquals("Товар добавлен в корзину!", driver.switchTo().alert().getText());
        driver.findElement(By.cssSelector(".cart")).click();
        driver.findElement(By.cssSelector("input")).click();
        driver.findElement(By.cssSelector("input")).sendKeys("15");
        driver.findElement(By.cssSelector(".modal-content")).click();
        driver.findElement(By.cssSelector(".close")).click();
        driver.findElement(By.cssSelector(".buttons > button:nth-child(1)")).click();
        Assertions.assertEquals("Товар добавлен в корзину!", driver.switchTo().alert().getText());
        driver.findElement(By.cssSelector(".cart")).click();
        driver.findElement(By.cssSelector(".close")).click();
        driver.findElement(By.cssSelector(".right-buttons > button:nth-child(1)")).click();
        driver.findElement(By.cssSelector(".product-item:nth-child(3) .toggle-favorite")).click();
        driver.findElement(By.cssSelector(".right-buttons > button")).click();
        driver.findElement(By.cssSelector(".product-item:nth-child(2) button:nth-child(1)")).click();
        Assertions.assertEquals("Товар добавлен в корзину!", driver.switchTo().alert().getText());
        driver.findElement(By.cssSelector(".cart")).click();
        driver.findElement(By.cssSelector(".close")).click();
        driver.findElement(By.cssSelector(".product-item:nth-child(1) button:nth-child(1)")).click();
        Assertions.assertEquals("Товар добавлен в корзину!", driver.switchTo().alert().getText());
        driver.findElement(By.cssSelector(".cart")).click();
        {
            WebElement element = driver.findElement(By.cssSelector(".cart"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        {
            WebElement element = driver.findElement(By.tagName("body"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element, 0, 0).perform();
        }
        driver.findElement(By.cssSelector("tr:nth-child(2) input")).click();
        driver.findElement(By.cssSelector("tr:nth-child(2) input")).sendKeys("15");
        driver.findElement(By.cssSelector("tr:nth-child(2) input")).sendKeys(Keys.ENTER);
        driver.findElement(By.cssSelector("tr:nth-child(2) > td:nth-child(3)")).click();
        driver.findElement(By.cssSelector("tr:nth-child(2) > td:nth-child(3)")).click();
        {
            WebElement element = driver.findElement(By.cssSelector("tr:nth-child(2) > td:nth-child(3)"));
            Actions builder = new Actions(driver);
            builder.doubleClick(element).perform();
        }
        driver.findElement(By.cssSelector(".modal-content")).click();
        driver.findElement(By.cssSelector("tr:nth-child(2) button")).click();
        driver.findElement(By.cssSelector(".close")).click();
        driver.findElement(By.cssSelector(".product-item:nth-child(1) button:nth-child(1)")).click();
        Assertions.assertEquals("Товар добавлен в корзину!", driver.switchTo().alert().getText());
        driver.findElement(By.cssSelector(".cart")).click();
        {
            WebElement element = driver.findElement(By.cssSelector(".cart"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        {
            WebElement element = driver.findElement(By.tagName("body"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element, 0, 0).perform();
        }
        driver.findElement(By.cssSelector("tr:nth-child(2) input")).click();
        driver.findElement(By.cssSelector("tr:nth-child(2) input")).sendKeys("15");
        driver.findElement(By.cssSelector(".modal-content")).click();
        driver.findElement(By.cssSelector(".cart-buttons > button")).click();

        Assertions.assertEquals("Заказ оформлен!", driver.switchTo().alert().getText());
        driver.findElement(By.cssSelector(".left-buttons > button")).click();
        driver.findElement(By.linkText("Заказы")).click();
        driver.findElement(By.cssSelector(".home-button:nth-child(1)")).click();
        driver.findElement(By.cssSelector("button:nth-child(2)")).click();
    }
}
