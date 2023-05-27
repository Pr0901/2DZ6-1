package Test;

import Data.DataHelper;
import Page.CardBalancePage;
import Page.CardTransfersPage;
import Page.LoginPage;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferBetweenCardsTest {

    @Test
    void invalidAuth() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var rightAuthInfo = DataHelper.getAuthInfo();
        var authInfo = DataHelper.getWrongInfo(rightAuthInfo);
    }

    @Test
    void transferMoneyFromFirstToSecondCard() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = DataHelper.getFirstCard();
        var secondCardInfo = DataHelper.getSecondCard();
        var firstCardBalance = dashboardPage.getFirstCardBalance();
        var secondCardBalance = dashboardPage.getSecondCardBalance();
        var amount = DataHelper.validAmount(firstCardBalance);
        var expectedBalanceFirstCard = firstCardBalance - amount;
        var expectedBalanceSecondCard = secondCardBalance + amount;
        var cardBalancePage = new CardBalancePage();
        cardBalancePage.transferFromFirstToSecond();
        var cardTransfersPage = new CardTransfersPage();
        cardTransfersPage.transferBetweenCards(String.valueOf(amount), firstCardInfo);
        var actualBalanceFirstCard = dashboardPage.getFirstCardBalance();
        var actualBalanceSecondCard = dashboardPage.getSecondCardBalance();
        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        assertEquals(expectedBalanceSecondCard,actualBalanceSecondCard);
    }
    @Test
    void transferMoneyFromSecondToFirstCard() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = DataHelper.getFirstCard();
        var secondCardInfo = DataHelper.getSecondCard();
        var firstCardBalance = dashboardPage.getFirstCardBalance();
        var secondCardBalance = dashboardPage.getSecondCardBalance();
        var amount = DataHelper.validAmount(secondCardBalance);
        var expectedBalanceFirstCard = firstCardBalance + amount;
        var expectedBalanceSecondCard = secondCardBalance - amount;
        var cardBalancePage = new CardBalancePage();
        cardBalancePage.transferFromSecondToFirst();
        var cardTransfersPage = new CardTransfersPage();
        cardTransfersPage.transferBetweenCards(String.valueOf(amount), secondCardInfo);
        var actualBalanceFirstCard = dashboardPage.getFirstCardBalance();
        var actualBalanceSecondCard = dashboardPage.getSecondCardBalance();
        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        assertEquals(expectedBalanceSecondCard,actualBalanceSecondCard);
    }


    @Test
    void transferMoneyFromFirstToFirstCard() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = DataHelper.getFirstCard();
        var secondCardInfo = DataHelper.getSecondCard();
        var firstCardBalance = dashboardPage.getFirstCardBalance();
        var secondCardBalance = dashboardPage.getSecondCardBalance();
        var amount = DataHelper.validAmount(secondCardBalance);
        var cardBalancePage = new CardBalancePage();
        cardBalancePage.transferFromSecondToFirst();
        var cardTransfersPage = new CardTransfersPage();
        cardTransfersPage.transferBetweenCards(String.valueOf(amount), firstCardInfo);
        var actualBalanceFirstCard = dashboardPage.getFirstCardBalance();
        var actualBalanceSecondCard = dashboardPage.getSecondCardBalance();
        assertEquals(firstCardBalance, actualBalanceFirstCard);
        assertEquals(secondCardBalance,actualBalanceSecondCard);
    }

    @Test
    void transferMoneyFromInvalidCard() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = DataHelper.getFirstCard();
        var secondCardInfo = DataHelper.getSecondCard();
        var invalidCardInfo = DataHelper.getInvalidCard();
        var firstCardBalance = dashboardPage.getFirstCardBalance();
        var secondCardBalance = dashboardPage.getSecondCardBalance();
        var amount = DataHelper.validAmount(secondCardBalance);
        var cardBalancePage = new CardBalancePage();
        cardBalancePage.transferFromSecondToFirst();
        var cardTransfersPage = new CardTransfersPage();
        cardTransfersPage.invalidTransferBetweenCards(String.valueOf(amount), invalidCardInfo, "Ошибка! Произошла ошибка");
        $("[data-test-id='action-cancel'] .button__content").click();
        var actualBalanceFirstCard = dashboardPage.getFirstCardBalance();
        var actualBalanceSecondCard = dashboardPage.getSecondCardBalance();
        assertEquals(firstCardBalance, actualBalanceFirstCard);
        assertEquals(secondCardBalance,actualBalanceSecondCard);
    }
    @Test
    void transferMoneyMoreThenBalance() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = DataHelper.getFirstCard();
        var secondCardInfo = DataHelper.getSecondCard();
        var firstCardBalance = dashboardPage.getFirstCardBalance();
        var secondCardBalance = dashboardPage.getSecondCardBalance();
        var amount = DataHelper.invalidAmount(firstCardBalance);
        var cardBalancePage = new CardBalancePage();
        cardBalancePage.transferFromSecondToFirst();
        var cardTransfersPage = new CardTransfersPage();
        cardTransfersPage.invalidTransferBetweenCards(String.valueOf(amount), secondCardInfo, "Выполнена попытка перевода суммы, превышающей остаток на карте списания");
        var actualBalanceFirstCard = dashboardPage.getFirstCardBalance();
        var actualBalanceSecondCard = dashboardPage.getSecondCardBalance();
        assertEquals(firstCardBalance, actualBalanceFirstCard);
        assertEquals(secondCardBalance, actualBalanceSecondCard);
    }
}
