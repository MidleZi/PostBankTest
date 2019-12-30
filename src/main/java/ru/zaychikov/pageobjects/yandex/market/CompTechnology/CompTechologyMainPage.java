package ru.zaychikov.pageobjects.yandex.market.CompTechnology;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.zaychikov.annotations.*;
import ru.zaychikov.util.AllPage;
import ru.zaychikov.util.Init;

public class CompTechologyMainPage extends AllPage {

    @FindBy(xpath = "//*[a[contains(text(), 'Планшеты')]]")
    @ElementTitle(value = "Планшеты (ссылка)")
    private WebElement tabletHrefBtn;

    @FindBy(xpath = "//*[p[contains(text(), 'Планшеты')]]")
    @ElementTitle(value = "Планшеты (плитка)")
    private WebElement tabletTileBtn;

    public CompTechologyMainPage() {
        PageFactory.initElements(Init.getWebDriver(), this);
        findElement("Планшеты (ссылка)");
    }

}
