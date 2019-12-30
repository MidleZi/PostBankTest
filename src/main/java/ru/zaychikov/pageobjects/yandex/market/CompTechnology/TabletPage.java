package ru.zaychikov.pageobjects.yandex.market.CompTechnology;

import org.openqa.selenium.By;
import ru.zaychikov.util.AllPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.zaychikov.annotations.*;
import ru.zaychikov.util.Init;

import java.util.List;

public class TabletPage extends AllPage {

    @FindBy(xpath = "//*[@id='header-search']")
    @ElementTitle(value = "Поле поиска")
    private WebElement searchField;

    @FindBy(xpath = "//*[*[contains(text(), 'Найти')]]")
    @ElementTitle(value = "найти")
    private WebElement searchBtn;

    @FindBy(xpath = "//*[input[@name='Производитель Samsung']]")
    @ElementTitle(value = "samsung")
    private WebElement samsungCheckBox;

    @FindBy(xpath = "//*[*[contains(text(), 'по цене')]]")
    @ElementTitle(value = "по цене")
    private WebElement sortOnPriceBtn;

    private String pathToTabletList = "//*[contains(@id, 'product')]";

    public List<WebElement> getTabletListElements() {
        return Init.getWebDriver().findElements(By.xpath(pathToTabletList));
    }

    public TabletPage() {
        PageFactory.initElements(Init.getWebDriver(), this);
        findElement("Поле поиска");
    }

    public void createTabletFromSearchList() {

    }
}
