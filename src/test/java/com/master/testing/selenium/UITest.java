package com.master.testing.selenium;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.*;

public class UITest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UITest.class);

    public static final String MAIN_PAGE = "https://the-internet.herokuapp.com";
    public static final String ADD_REMOVE_ELEMENT_PAGE = "https://the-internet.herokuapp.com/add_remove_elements";
    public static final String BROKEN_IMAGES_PAGE = "https://the-internet.herokuapp.com/broken_images";

    WebDriver driver;

    @BeforeClass
    public static void beforeClass() {
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
    }

    @Before
    public void init() {
        driver = new ChromeDriver();
        driver.get(MAIN_PAGE);
        wait(5);
    }

    @Test
    public void testClick() {
        WebElement link = driver.findElement(By.linkText("Add/Remove Elements"));
        link.click();

        wait(2);

        WebElement addBtn = driver.findElement(By.xpath("button[text()='Add Element']"));
        addBtn.click();

        wait(2);

        WebElement added = driver.findElement(By.className("added-manually"));
        assertNotNull("The element added-manually should be added", added);

        wait(2);

        WebElement deleteBtn = driver.findElement(By.xpath("button[text()='Delete']"));
        deleteBtn.click();

        wait(2);

        WebElement removed = driver.findElement(By.className("added-manually"));
        assertNull("The element added-manually should be hidden", removed);

        assertEquals(ADD_REMOVE_ELEMENT_PAGE + " is the expected url", ADD_REMOVE_ELEMENT_PAGE, driver.getCurrentUrl());
    }

    @Test
    public void testCheckboxes() {
        WebElement checkboxesHolder = driver.findElement(By.xpath("//a[contains(text(),'Checkboxes')]"));
        checkboxesHolder.click();

        List<WebElement> checkboxElements = driver.findElements(By.cssSelector("#checkboxes input[type='checkbox']"));

        for (WebElement checkbox : checkboxElements) {
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
        }
    }

    @Test
    public void testBrokenImage() {
        driver.navigate().to(BROKEN_IMAGES_PAGE);
        driver.manage().window().maximize();

        wait(5);

        int count = 0;

        try {
            List<WebElement> images = driver.findElements(By.tagName("img"));
            for (WebElement image : images) {
                if (image != null) {
                    HttpClient client = HttpClientBuilder.create().build();
                    HttpGet request = new HttpGet(image.getAttribute("src"));
                    HttpResponse response = client.execute(request);
                    if (response.getStatusLine().getStatusCode() != 200) {
                        count++;
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("An error occurred while iterating over images -> ", e);
        }

        assertTrue("There are broken images on the page", count > 0);
    }

    @Test
    public void testContextMenu() {
        try {
            driver.findElement(By.xpath("//a[contains(text(),'Drag and Drop')]")).click();
            WebElement contextBox = driver.findElement(By.cssSelector("div[contextmenu='menu']"));
            Actions actions = new Actions(driver);
            actions.moveToElement(contextBox);
            actions.contextClick(contextBox).build().perform();

            sleep(2000);

            WebElement menuItem = driver.findElement(By.cssSelector("menuitem[label='the-internet']"));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click()", menuItem);

            sleep(2000);

            driver.switchTo().alert().accept();

            sleep(200);
        } catch (InterruptedException e) {
            LOGGER.error("Thread exception -> ", e);
        }
    }

    @Test
    public void testDragAndDrop() {
        try {
            driver.findElement(By.xpath("//a[contains(text(),'Drag and Drop')]")).click();

            WebElement columnA = driver.findElement(By.cssSelector("div#column-a"));
            WebElement columnB = driver.findElement(By.cssSelector("div#column-b"));

            Actions actions = new Actions(driver);
            actions
                    .dragAndDrop(columnA, columnB)
                    .build()
                    .perform();

            sleep(6000);

            driver.quit();
        } catch (InterruptedException e) {
            LOGGER.error("Thread exception -> ", e);
        }
    }

    @Test
    public void testDropdown() {
        try {
            driver.findElement(By.xpath("//a[contains(text(),'Dropdown')]")).click();
            WebElement dropdown = driver.findElement(By.id("dropdown"));
            Select select = new Select(dropdown);
            select.selectByValue("2");
            sleep(6000);
        } catch (InterruptedException e) {
            LOGGER.error("Thread exception -> ", e);
        }
    }

    @Test
    public void testHover() {
        try {
            driver.findElement(By.xpath("//a[contains(text(),'Infinite Scroll')]")).click();
            List<WebElement> images = driver.findElements(By.cssSelector(".figure"));
            Actions action = new Actions(driver);
            for (WebElement image : images)
                action.moveToElement(image).perform();

            sleep(3000);
            driver.quit();
        } catch (InterruptedException e) {
            LOGGER.error("Thread exception -> ", e);
        }
    }

    public void wait(int seconds) {
        driver.manage()
                .timeouts()
                .implicitlyWait(Duration.ofSeconds(seconds));
    }

    private void sleep(int ms) throws InterruptedException {
        Thread.sleep(ms);
    }
}
