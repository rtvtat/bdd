package ru.netology.testmode.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.testmode.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement codeField = $(".input__control");
    private SelenideElement verifyButton = $("button[data-test-id='action-verify'");

    public VerificationPage() {
        codeField.shouldBe(Condition.visible);
        verifyButton.shouldBe(Condition.visible);
    }

    public DashboardPage validVerify(DataHelper.VerificationCode verificationCode) {
        codeField.setValue(verificationCode.getCode());
        verifyButton.click();
        return new DashboardPage();
    }
}
