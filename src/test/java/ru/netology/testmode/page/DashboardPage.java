package ru.netology.testmode.page;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.Value;
import ru.netology.testmode.data.DataHelper;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

    @Value
    public static class Card {
        String uuid;
        String number;
        Integer sum;
        SelenideElement button;
    }
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private SelenideElement h2 = $("h2[data-test-id='dashboard']");
    private List<SelenideElement> listElement = $$("li.list__item");


    public DashboardPage() {
        h2.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public List<Card> getCards() {
        List<Card> cards = new ArrayList<>();
        for (SelenideElement item : listElement) {
            Card card = extractCardData(item);
            cards.add(card);
        }
        return cards;
    }

    public PaymentPage transferMoney(Card to) {
        to.getButton().click();
        return new PaymentPage();
    }

    private Card extractCardData(SelenideElement item) {
        String text = item.getText();
        String number = extractNumber(text);
        Integer sum = extractSum(text);
        SelenideElement div = item.$("div[data-test-id]");
        SelenideElement button = item.$("button[data-test-id='action-deposit']");
        String uuid = div.getAttribute("data-test-id");
        return new Card(uuid, number, sum, button);
    }

    private String extractNumber(String text) {
        return text.substring(0, text.indexOf(","));
    }

    private Integer extractSum(String text) {
        String balance = text.substring(text.indexOf(balanceStart)+balanceStart.length(), text.indexOf(balanceFinish));
        return Integer.parseInt(balance);
    }

}
