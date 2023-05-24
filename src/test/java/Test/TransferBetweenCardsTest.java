package Test;

import Data.DataHelper;
import Page.CardBalancePage;
import Page.CardTransfersPage;
import Page.LoginPage;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferBetweenCardsTest {

    @Test
    void transferMoneyBetweenCards() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        verificationPage.validVerify(verificationCode);
        var cardBalancePage = new CardBalancePage();
        cardBalancePage.getCardBalance("92df3f1c-a033-48e6-8390-206f6b1f56c0");
//        assertEquals("10000", startBalanceFirstCard);
//        var cardTransfersPage = new CardTransfersPage();

    }

    @Test
    void invalidAuth() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var rightAuthInfo = DataHelper.getAuthInfo();
        var authInfo = DataHelper.getWrongInfo(rightAuthInfo);
    }


}
