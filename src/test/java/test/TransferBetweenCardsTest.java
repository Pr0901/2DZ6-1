package test;

import data.DataHelper;
import org.junit.jupiter.api.BeforeEach;
import page.CardBalancePage;
import page.CardTransfersPage;
import page.LoginPage;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferBetweenCardsTest {

    @Test
    void invalidAuth() {
        open("http://localhost:9999");
        var rightAuthInfo = DataHelper.getAuthInfo();
        var authInfo = DataHelper.getWrongInfo(rightAuthInfo);
        var loginPage = new LoginPage(authInfo);
        loginPage.invalidLogin();
    }

    @Test
    void transferMoneyFromFirstToSecondCard() {
        open("http://localhost:9999");
        var authInfo = DataHelper.getAuthInfo();
        var loginPage = new LoginPage(authInfo);
        var verificationPage = loginPage.login();
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = DataHelper.getFirstCard();
        var secondCardInfo = DataHelper.getSecondCard();
        var firstCardBalance = dashboardPage.getFirstCardBalance();
        var secondCardBalance = dashboardPage.getSecondCardBalance();
        var amount = DataHelper.validAmount(firstCardBalance);
        var expectedBalanceFirstCard = firstCardBalance - amount;
        var expectedBalanceSecondCard = secondCardBalance + amount;
        var cardTransfersPage = CardBalancePage.transferFromFirstToSecond();
        cardTransfersPage.transferBetweenCards(String.valueOf(amount), firstCardInfo);
        var actualBalanceFirstCard = dashboardPage.getFirstCardBalance();
        var actualBalanceSecondCard = dashboardPage.getSecondCardBalance();
        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
    }


    @Test
    void transferMoneyFromSecondToFirstCard() {
        open("http://localhost:9999");
        var authInfo = DataHelper.getAuthInfo();
        var loginPage = new LoginPage(authInfo);
        var verificationPage = loginPage.login();
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = DataHelper.getFirstCard();
        var secondCardInfo = DataHelper.getSecondCard();
        var firstCardBalance = dashboardPage.getFirstCardBalance();
        var secondCardBalance = dashboardPage.getSecondCardBalance();
        var amount = DataHelper.validAmount(secondCardBalance);
        var expectedBalanceFirstCard = firstCardBalance + amount;
        var expectedBalanceSecondCard = secondCardBalance - amount;
        var cardTransfersPage = CardBalancePage.transferFromSecondToFirst();
        cardTransfersPage.transferBetweenCards(String.valueOf(amount), secondCardInfo);
        var actualBalanceFirstCard = dashboardPage.getFirstCardBalance();
        var actualBalanceSecondCard = dashboardPage.getSecondCardBalance();
        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
    }


    @Test
    void transferMoneyFromFirstToFirstCard() {
        open("http://localhost:9999");
        var authInfo = DataHelper.getAuthInfo();
        var loginPage = new LoginPage(authInfo);
        var verificationPage = loginPage.login();
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = DataHelper.getFirstCard();
        var secondCardInfo = DataHelper.getSecondCard();
        var firstCardBalance = dashboardPage.getFirstCardBalance();
        var secondCardBalance = dashboardPage.getSecondCardBalance();
        var amount = DataHelper.validAmount(secondCardBalance);
        var cardTransfersPage = CardBalancePage.transferFromSecondToFirst();
        cardTransfersPage.transferBetweenCards(String.valueOf(amount), firstCardInfo);
        var actualBalanceFirstCard = dashboardPage.getFirstCardBalance();
        var actualBalanceSecondCard = dashboardPage.getSecondCardBalance();
        assertEquals(firstCardBalance, actualBalanceFirstCard);
        assertEquals(secondCardBalance, actualBalanceSecondCard);
    }


    @Test
    void transferMoneyFromInvalidCard() {
        open("http://localhost:9999");
        var authInfo = DataHelper.getAuthInfo();
        var loginPage = new LoginPage(authInfo);
        var verificationPage = loginPage.login();
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = DataHelper.getFirstCard();
        var secondCardInfo = DataHelper.getSecondCard();
        var invalidCardInfo = DataHelper.getInvalidCard();
        var firstCardBalance = dashboardPage.getFirstCardBalance();
        var secondCardBalance = dashboardPage.getSecondCardBalance();
        var amount = DataHelper.validAmount(secondCardBalance);
        var cardTransfersPage = CardBalancePage.transferFromSecondToFirst();
        cardTransfersPage.transferBetweenCards(String.valueOf(amount), invalidCardInfo);
        cardTransfersPage.errorMessage("Ошибка! Произошла ошибка");
        var actualBalanceFirstCard = dashboardPage.getFirstCardBalance();
        var actualBalanceSecondCard = dashboardPage.getSecondCardBalance();
        assertEquals(firstCardBalance, actualBalanceFirstCard);
        assertEquals(secondCardBalance, actualBalanceSecondCard);
    }

    @Test
    void transferMoneyMoreThenBalance() {
        open("http://localhost:9999");
        var authInfo = DataHelper.getAuthInfo();
        var loginPage = new LoginPage(authInfo);
        var verificationPage = loginPage.login();
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = DataHelper.getFirstCard();
        var secondCardInfo = DataHelper.getSecondCard();
        var firstCardBalance = dashboardPage.getFirstCardBalance();
        var secondCardBalance = dashboardPage.getSecondCardBalance();
        var amount = DataHelper.invalidAmount(firstCardBalance);
        var cardTransfersPage = CardBalancePage.transferFromFirstToSecond();
        cardTransfersPage.transferBetweenCards(String.valueOf(amount), secondCardInfo);
        cardTransfersPage.errorMessage("Выполнена попытка перевода суммы, превышающей остаток на карте списания");
        var actualBalanceFirstCard = dashboardPage.getFirstCardBalance();
        var actualBalanceSecondCard = dashboardPage.getSecondCardBalance();
        assertEquals(firstCardBalance, actualBalanceFirstCard);
        assertEquals(secondCardBalance, actualBalanceSecondCard);
    }
}
