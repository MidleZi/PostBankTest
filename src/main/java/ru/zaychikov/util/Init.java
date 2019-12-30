package ru.zaychikov.util;


import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.logging.Logger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Init {


    private static WebDriver webDriver;
    private static PageFactory pageFactory;
    private static final String pagesPackage = "ru.pages";
    private static WebDriverWait wait;
    private static int timeout = 10;
    private static final Logger LOG = Logger.getLogger(Init.class.getName());

    @Before
    public void startWebDriver(){
        LOG.info("Запускаю webDriver");
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        webDriver = new ChromeDriver();
        wait = new WebDriverWait(webDriver, timeout);
        webDriver.manage().window().maximize();
    }

    @After
    public void closeWebDriver() {
        LOG.info("Закрываю webDriver");
        webDriver.quit();
    }

    public static int getTimeOut() {
        return timeout;
    }

    public static void openStartUpPage(String url) {
        LOG.info("Открываю стартовую страницу");
        webDriver.get(url);
    }

    public static WebDriver getWebDriver() {
        return webDriver;
    }

//    public static libs.PageFactory getPageFactory() {
//        if(pageFactory == null) {
//            pageFactory = new PageFactory(getPagePackage());
//        }
//        return pageFactory;
//    }

    public static String getCurrentDate() {
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("d.MM.yyyy");
        return formatForDateNow.format(dateNow);
    }

    public static String getCurrentDate(int days) {
        Date dateNow = new Date();
        Calendar instance = Calendar.getInstance();
        instance.setTime(dateNow);
        instance.add(Calendar.DAY_OF_MONTH, days);
        Date newDate = instance.getTime();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy");
        return formatForDateNow.format(newDate);
    }

    public static String getPagePackage() {
        return pagesPackage;
    }

    public static WebDriverWait getWait(){
        return wait;
    }

}
