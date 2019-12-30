package ru.zaychikov.util;

import ru.zaychikov.exceptions.*;
import ru.zaychikov.annotations.*;
import java.util.logging.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class AllPage {

    private static final Logger LOG = Logger.getLogger(AllPage.class.getName());

    public static void waitForWindow() {
        waitForWindow(Init.getTimeOut());
    }

    public static void waitForWindow(int timeout) {
        WebDriverWait wait = new WebDriverWait(Init.getWebDriver(), timeout);
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                Boolean active = false;
                try {
                    JavascriptExecutor js = ((JavascriptExecutor) driver);
                    active = (Boolean) js.executeScript("return document.readyState == 'complete'");
                } catch (WebDriverException ex) {
                    LOG.info("Error: " + ex);
                }
                return active;
            }
        });
    }

    protected WebElement find(By xpath) throws NullPointerException {
        try {
            waitForWindow();
        } catch (WebDriverException ex) {
            LOG.info(Init.getWebDriver().getPageSource());
            throw new AutotestError("DOM не сформирован! Ошибка при ожидании загрузки страницы");
        }
        try {
            Init.getWait().until(ExpectedConditions.visibilityOfElementLocated(xpath));
            WebElement element = Init.getWebDriver().findElement(xpath);
            if (element.isDisplayed()) {
                return element;
            }
        } catch (TimeoutException | NoSuchElementException ex) {
            LOG.info(ex.toString());
        }
        throw new ElementNotVisibleException("Элемент не отобразился. xPath= " + xpath);
    }

    public void checkVisibleElement(By xpath, int timeout) {
        long deadline = Calendar.getInstance().getTimeInMillis() + (timeout * 1000);
        while (deadline < Calendar.getInstance().getTimeInMillis()) {
            try {
                find(xpath);
                return;
            } catch (Exception ex) {
                LOG.info("Элемент " + xpath.toString() + " не виден");
            }
            find(xpath);
            throw new AutotestError("Элемент " + xpath.toString() + " не виден. Ожидание составило" + timeout + " сек.");
        }
    }

    public boolean elementVisible(String title) {
        By xpath = getXpathFindBy(title);
        return elementVisible(xpath, Init.getTimeOut());
    }

    public WebElement findElement(String title) {
        By xpath = getXpathFindBy(title);
        return findElement(xpath, Init.getTimeOut());
    }

    public WebElement findElement(By xpath, int timeout) {
        long deadline = Calendar.getInstance().getTimeInMillis() + (timeout * 1000);
        while (deadline > Calendar.getInstance().getTimeInMillis()) {
            try {
                WebElement element = find(xpath);
                return element;
            } catch (ElementNotVisibleException ex) {
                LOG.info("Элемент " + xpath.toString() + " не виден");
            }
        }
        if (elementVisible(xpath, timeout)) {
            WebElement element = find(xpath);
            return element;
        }
        throw new AutotestError("Элемент " + xpath.toString() + " не виден. жидание составило" + timeout + " сек.");
    }

    public void userOnPage() {
        waitForWindow();
    }

    public boolean elementVisible(By xpath, int timeout) {
        try {
            checkVisibleElement(xpath, timeout);
            return true;
        } catch (Exception ex) {
            LOG.info("Элемент " + xpath.toString() + " не виден");
        }
        return false;
    }

    public By getXpathFindBy(String title) {
        List<Field> fieldList = getDeclaredFieldsWithInheritance(this.getClass());
        String x = "";
        for (Field field : fieldList) {
            for (Annotation annotation : field.getAnnotations()) {
                if (annotation instanceof ElementTitle && ((ElementTitle) annotation).value().equals(title)) {
                    if (!field.getAnnotation(FindBy.class).xpath().equals(x)) {
                        return By.xpath(field.getAnnotation(FindBy.class).xpath());
                    } else if (!field.getAnnotation(FindBy.class).id().equals(x)) {
                        return By.xpath(field.getAnnotation(FindBy.class).id());
                    } else if (!field.getAnnotation(FindBy.class).name().equals(x)) {
                        return By.xpath(field.getAnnotation(FindBy.class).name());
                    } else if (!field.getAnnotation(FindBy.class).className().equals(x)) {
                        return By.xpath(field.getAnnotation(FindBy.class).className());
                    } else if (!field.getAnnotation(FindBy.class).css().equals(x)) {
                        return By.xpath(field.getAnnotation(FindBy.class).css());
                    } else if (!field.getAnnotation(FindBy.class).linkText().equals(x)) {
                        return By.xpath(field.getAnnotation(FindBy.class).linkText());
                    }
                    throw new java.util.NoSuchElementException("Не найден элемент " + title);
                }
            }
        }
        for (Field field : fieldList) {
            for (Annotation annotation : field.getAnnotations()) {
                if (annotation instanceof ElementTitle) {
                    LOG.info("((ElementTitle annotation.value() = " + ((ElementTitle) annotation).value().toString());
                }
            }
        }
        throw new NoSuchElementException("Не найден элемент " + title + " в pageObject классе");
    }

    public static List<Field> getDeclaredFieldsWithInheritance(Class clazz) {
        List<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        for (Class supp = clazz.getSuperclass(); !supp.getName().equals("java.lang.Object"); supp = supp.getSuperclass()) {
            fields.addAll(Arrays.asList(supp.getDeclaredFields()));
        }
        return fields;
    }

    public void clickButton(String title) {
        if (elementVisible(title)) {
            long deadline = Calendar.getInstance().getTimeInMillis() + (Init.getTimeOut() * 1000);
            while (deadline > Calendar.getInstance().getTimeInMillis()) {
                try {
                    findElement(title).click();
                    LOG.info("Пользователь жмет " + title);
                    break;
                } catch (WebDriverException ex) {
                    LOG.info(ex.toString());
                }
            }
        }
    }

    public void sendKeys(String title, String value) {
        if (elementVisible(title)) {
            findElement(title).sendKeys(value);
            LOG.info("Пользователь заполняет " + title + " значением " + value);
        }
    }

//    public String getTitle() {
//        return ((PageEntry) this.getClass().getAnnotation(PageEntry.class)).title();
//    }
}
