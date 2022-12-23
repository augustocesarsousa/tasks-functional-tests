package com.acsousa.tasks.functional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TasksTest {

    public WebDriver accessApplication() throws MalformedURLException {
        // WebDriver driver = new ChromeDriver();
        DesiredCapabilities cap = DesiredCapabilities.chrome();
        WebDriver driver = new RemoteWebDriver(new URL("http://172.17.8.187:4444/wd/hub"), cap);
        driver.navigate().to("http://172.17.8.187:8001/tasks");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    @Test
    public void shouldSaveTaskWhenSendingCorrectDatas() throws MalformedURLException {
        WebDriver driver = accessApplication();
        try {
            driver.findElement(By.id("addTodo")).click();
            driver.findElement(By.id("task")).sendKeys("Test Selenium");
            driver.findElement(By.id("dueDate")).sendKeys("22/12/2030");
            driver.findElement(By.id("saveButton")).click();
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Success!", message);            
        } finally {
            driver.quit();
        }
    }

    @Test
    public void shouldNotSaveTasksWhithoutDescription() throws MalformedURLException {
        WebDriver driver = accessApplication();
        try {
            driver.findElement(By.id("addTodo")).click();
            driver.findElement(By.id("dueDate")).sendKeys("22/12/2030");
            driver.findElement(By.id("saveButton")).click();
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Fill the task description", message);
        } finally {
            driver.quit();
        }
    }

    @Test
    public void shouldNotSaveTasksWhithoutDate() throws MalformedURLException {
        WebDriver driver = accessApplication();
        try {
            driver.findElement(By.id("addTodo")).click();
            driver.findElement(By.id("task")).sendKeys("Test Selenium");
            driver.findElement(By.id("saveButton")).click();
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Fill the due date", message);
        } finally {
            driver.quit();
        }
    }

    @Test
    public void shouldNotSaveTasksWhithPastDate() throws MalformedURLException {
        WebDriver driver = accessApplication();
        try {
            driver.findElement(By.id("addTodo")).click();
            driver.findElement(By.id("task")).sendKeys("Test Selenium");
            driver.findElement(By.id("dueDate")).sendKeys("22/12/2010");
            driver.findElement(By.id("saveButton")).click();
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Due date must not be in past", message);            
        } finally {
            driver.quit();
        }
    }
}
