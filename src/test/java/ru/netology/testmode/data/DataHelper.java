package ru.netology.testmode.data;

import lombok.Value;
import ru.netology.testmode.page.DashboardPage;

public class DataHelper {

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class  VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor (AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    @Value
    public static class Card {
        String number;
    }

    public static Card getFirstCard() {
        return new Card("5559 0000 0000 0001");
    }

    public static Card getSecondCard() {
        return new Card("5559 0000 0000 0002");
    }

    public static Card getCardByMask(DashboardPage.Card from) {
        if (from.getNumber().contains("0001")) {
            return getFirstCard();
        } else if (from.getNumber().contains("0002")) {
            return getSecondCard();
        }
        return null;
    }

}
