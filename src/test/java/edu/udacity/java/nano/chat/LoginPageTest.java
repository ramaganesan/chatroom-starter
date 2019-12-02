package edu.udacity.java.nano.chat;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
public class LoginPageTest {

    @LocalServerPort
    private int port;

    private WebDriver webDriver;

    @Before
    public void setUp() throws MalformedURLException{
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();
    }


    @Test
    public void getLoginPage(){
        webDriver.get("http://localhost:"+port);
        WebElement element = webDriver.findElement(By.id("username"));
        assertNotNull(element);
        element.sendKeys("test-login");
        WebElement button = webDriver.findElement(By.id("login-button"));
        button.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement username = webDriver.findElement(By.id("username"));
        assertEquals("test-login", username.getText());
        webDriver.close();
    }


}
