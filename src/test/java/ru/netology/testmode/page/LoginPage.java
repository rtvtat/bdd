package ru.netology.testmode.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.testmode.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private SelenideElement loginField = $("span[data-test-id='login'] .input__control");
    private SelenideElement passwordField = $("span[data-test-id='password'] .input__control");
    private SelenideElement loginButton = $("button[data-test-id='action-login']");

    public VerificationPage validLogin(DataHelper.AuthInfo authInfo) {
        loginField.setValue(authInfo.getLogin());
        passwordField.setValue(authInfo.getPassword());
        loginButton.click();
        return new VerificationPage();

    }
}
