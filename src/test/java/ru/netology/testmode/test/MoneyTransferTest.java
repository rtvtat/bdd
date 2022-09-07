package ru.netology.testmode.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.testmode.data.DataHelper;
import ru.netology.testmode.page.DashboardPage;
import ru.netology.testmode.page.LoginPage;
import ru.netology.testmode.page.PaymentPage;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }


    @Test
    @DisplayName("checking the transfer of funds")
    void moneyTransferTestSuccess() {
        LoginPage loginPage = new LoginPage();
        DataHelper.AuthInfo authInfo = DataHelper.getAuthInfo();
        DashboardPage dashboardPage = loginPage.validLogin(authInfo)
                .validVerify(DataHelper.getVerificationCodeFor(authInfo));

        List<DashboardPage.Card> cards = dashboardPage.getCards();
        // переводим 1 рубль
        DashboardPage.Card to = cards.get(0);
        DashboardPage.Card from = cards.get(1);
        PaymentPage paymentPage = dashboardPage.transferMoney(to);
        Integer sum = 1;
        Map<String, Integer> excepted = getMapCards(cards);
        excepted.put(to.getUuid(), excepted.get(to.getUuid())+sum);
        excepted.put(from.getUuid(), excepted.get(from.getUuid())-sum);

        paymentPage.transferMoney(DataHelper.getCardByMask(from), sum);
        // переводим средства с одной карты на другую
        List<DashboardPage.Card> resultCards = dashboardPage.getCards();
        // проверяем баланс карт
        Map<String, Integer> result = getMapCards(resultCards);
        assertEquals(excepted, result);
    }

    //проверяем перевод суммы, большей чем доступно на карте
    @Test
    @DisplayName("checking the transfer of funds")
    void moneyTransferTestWithIncorrectSum() {
        LoginPage loginPage = new LoginPage();
        DataHelper.AuthInfo authInfo = DataHelper.getAuthInfo();
        DashboardPage dashboardPage = loginPage.validLogin(authInfo)
                .validVerify(DataHelper.getVerificationCodeFor(authInfo));

        List<DashboardPage.Card> cards = dashboardPage.getCards();
        // переводим сумму, большую чем баланс карты
        DashboardPage.Card to = cards.get(0);
        DashboardPage.Card from = cards.get(1);
        PaymentPage paymentPage = dashboardPage.transferMoney(to);
        Integer sum = from.getSum()+1000;
        Map<String, Integer> excepted = getExceptedSums(cards, to, from, sum);

        paymentPage.transferMoney(DataHelper.getCardByMask(from), sum);
        // переводим средства с одной карты на другую
        List<DashboardPage.Card> resultCards = dashboardPage.getCards();
        // проверяем баланс карт
        Map<String, Integer> result = getMapCards(resultCards);
        assertEquals(excepted, result);
    }

    private Map<String, Integer> getExceptedSums(
            List<DashboardPage.Card> cards,
            DashboardPage.Card to,
            DashboardPage.Card from,
            Integer sum) {
        Map<String, Integer> excepted = getMapCards(cards);
        if (sum <= excepted.get(from.getUuid())) {
            excepted.put(to.getUuid(), excepted.get(to.getUuid()) + sum);
            excepted.put(from.getUuid(), excepted.get(from.getUuid()) - sum);
        }
        return excepted;
    }

    private Map<String, Integer> getMapCards(List<DashboardPage.Card> resultCards) {
        Map<String, Integer> result = new HashMap<>();
        for (DashboardPage.Card card : resultCards) {
            result.put(card.getUuid(), card.getSum());
        }
        return result;
    }


}
