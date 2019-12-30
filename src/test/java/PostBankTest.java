import org.junit.Assert;
import org.junit.Test;
import org.junit.platform.commons.util.AnnotationUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.zaychikov.pageobjects.yandex.market.CompTechnology.CompTechologyMainPage;
import ru.zaychikov.pageobjects.yandex.market.CompTechnology.Tablet;
import ru.zaychikov.pageobjects.yandex.market.CompTechnology.TabletPage;
import ru.zaychikov.pageobjects.yandex.market.YandexMarketPage;
import ru.zaychikov.pageobjects.yandex.YandexStartPage;
import ru.zaychikov.util.Init;
import ru.zaychikov.exceptions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PostBankTest extends Init {

    private static final Logger LOG = Logger.getLogger(PostBankTest.class.getName());

    @Test
    public void postTest() throws InterruptedException {
        Init.openStartUpPage("https://yandex.ru");
        //1) Перейти на https://market.yandex.ru
        YandexStartPage startPage  = new YandexStartPage();
        startPage.clickButton("Маркет");

        //2) Выбрать категорию: Компьютерная техника -> Компьютеры -> Планшеты
        YandexMarketPage marketPage = new YandexMarketPage();
        marketPage.clickButton("Компьютерная техника");

        CompTechologyMainPage compTechologyMainPage = new CompTechologyMainPage();
        compTechologyMainPage.clickButton("Планшеты (ссылка)");

        //3) Выбрать производителя: Samsung
        TabletPage tabletPage = new TabletPage();
        tabletPage.clickButton("samsung");

        // 4) Установить сортировку: по цене
        tabletPage.clickButton("по цене");

        // 5) Вывести в лог первые 5
        // 6) Запомнить вторую позицию из списка продукт (наименование и цена)
        Thread.sleep(5000); //ждем пока загрузится список товаров
        List<WebElement> tabletElementList = tabletPage.getTabletListElements();
        List<Tablet> tabletList = new ArrayList<>();

        for(int i = 0; i < 5; i++) {
            String productId = tabletElementList.get(i).getAttribute("id");
            String id = null;
            String tabletName;
            String tabletPrice;
            LOG.info(productId);
            Pattern pattern = Pattern.compile("\\d{8}");
            Matcher matcher = pattern.matcher(productId);
            while (matcher.find()){
                id = matcher.group(0);
            }
            tabletName = Init.getWebDriver().findElement(By.xpath("//*[contains(@id, 'product-" + id + "')]//*[contains(@class, 'title')]/a")).getText();
            tabletPrice = Init.getWebDriver().findElement(By.xpath("//*[contains(@id, 'product-" + id + "')]//*[contains(@class, 'price-wrapper')]//div")).getText();
            tabletList.add(new Tablet(tabletName, tabletPrice));
        }

        for(Tablet tablet : tabletList) {
            LOG.info(tablet.getName() + " " + tablet.getPrice());
        }

        //7) Ввести в строке поиска запомненный продукт (наименование) и нажать «Поиск»
        tabletPage.sendKeys("Поле поиска", tabletList.get(1).getName());
        tabletPage.clickButton("найти");

        Thread.sleep(5000);

        String targetTabletName = Init.getWebDriver().findElement(By.xpath("//*[contains(@id, 'product-')]//*[contains(@class, 'title')]/a")).getText();
        String targetTabletPrice = Init.getWebDriver().findElement(By.xpath("//*[contains(@id, 'product-')]//*[contains(@class, 'price-wrapper')]//div")).getText();

        Tablet targetTablet = new Tablet(targetTabletName, targetTabletPrice);

        if(!targetTablet.getName().equals(tabletList.get(1).getName())) {
            throw new AutotestError("Планшет " + targetTablet.getName() + " НЕ соответствует планшету "+ tabletList.get(1).getName());
        }
        LOG.info("Планшет " + targetTablet.getName() + " соответствует планшету "+ tabletList.get(1).getName());
    }
}
