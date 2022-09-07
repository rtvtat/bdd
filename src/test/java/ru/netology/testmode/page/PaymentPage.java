package ru.netology.testmode.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.testmode.data.DataHelper;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import static com.codeborne.selenide.Selenide.$;

public class PaymentPage {

    private SelenideElement amountField = $("span[data-test-id='amount'] .input__control");
    private SelenideElement fromField = $("span[data-test-id='from'] .input__control");
    private SelenideElement transferButton = $("button[data-test-id='action-transfer'");
    private SelenideElement cancelButton = $("button[data-test-id='action-cancel'");
    NumberFormat formatter = new DecimalFormat("#0");
    public PaymentPage() {
    }

    public DashboardPage transferMoney(DataHelper.Card card, Integer sum) {
        amountField.setValue(String.valueOf(sum));
        fromField.setValue(card.getNumber());
        transferButton.click();
        return new DashboardPage();
    }
}
