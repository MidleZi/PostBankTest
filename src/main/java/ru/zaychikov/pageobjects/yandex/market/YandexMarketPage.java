package ru.zaychikov.pageobjects.yandex.market;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.zaychikov.annotations.*;
import ru.zaychikov.util.AllPage;
import ru.zaychikov.util.Init;

public class YandexMarketPage extends AllPage {

    @FindBy(xpath = "//*[@id='header-search']")
    @ElementTitle(value = "Поле поиска")
    private WebElement searchField;

    @FindBy(xpath = "//*[*[contains(text(), 'Компьютерная техника')]]")
    @ElementTitle(value = "Компьютерная техника")
    private WebElement compTechBtn;

    public YandexMarketPage() {
        PageFactory.initElements(Init.getWebDriver(), this);
        findElement("Компьютерная техника");
    }

}
