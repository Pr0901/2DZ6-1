package Page;

import Data.DataHelper;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.impl.Html.text;


public class CardBalancePage {

    private ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    private SelenideElement heading = $("[data-test-id=dashboard]");

    public CardBalancePage() {
        heading.shouldBe(visible);
    }

    public int getCardBalance(String id) {
        return extractBalance(text.toString());
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }




//    public int getFirstCardBalance() {
//        val text = cards.first().text();
//        return extractBalance(text);
//    }
//
//    private int extractBalance(String text) {
//        val start = text.indexOf(balanceStart);
//        val finish = text.indexOf(balanceFinish);
//        val value = text.substring(start + balanceStart.length(), finish);
//        return Integer.parseInt(value);
//    }
//    public CardTransfersPage balance() {
//        $("")
//        return new CardTransfersPage();
//    }
}

