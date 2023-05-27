package Page;

import Data.DataHelper;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class CardTransfersPage {

    private SelenideElement amountLine = $("[data-test-id='amount'] input");
    private SelenideElement cardFromLine = $("[data-test-id='from'] input");
    private SelenideElement continueButton = $("[data-test-id='action-transfer']");
    private SelenideElement errorMessage = $("[data-test-id='error-notification'] .notification__content");
    private SelenideElement codeField = $x("//h1[contains(@class, 'heading heading_size_xl heading_theme_alfa-on-white')]");

    public CardTransfersPage() {
        codeField.shouldBe(visible);
    }

    public void transferBetweenCards(String amount, DataHelper.CardInfo cardInfo) {

        amountLine.setValue(amount);
        cardFromLine.setValue(cardInfo.getCardNumber());
        continueButton.click();
    }

    public void invalidTransferBetweenCards(String amount, DataHelper.CardInfo cardInfo, String expectedText) {

        amountLine.setValue(amount);
        cardFromLine.setValue(cardInfo.getCardNumber());
        continueButton.click();
        errorMessage.shouldHave(Condition.exactText(expectedText), Duration.ofSeconds(15));
    }
}
