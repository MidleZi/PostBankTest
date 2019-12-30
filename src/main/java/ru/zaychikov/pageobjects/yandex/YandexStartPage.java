package ru.zaychikov.pageobjects.yandex;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.zaychikov.annotations.*;
import ru.zaychikov.util.AllPage;
import ru.zaychikov.util.Init;

public class YandexStartPage extends AllPage {

    @FindBy(xpath = "//*[@data-id='market']")
    @ElementTitle(value = "Маркет")
    public WebElement yandexMarketButton;

    public YandexStartPage() {
        PageFactory.initElements(Init.getWebDriver(), this);
        findElement("Маркет");
    }
}
