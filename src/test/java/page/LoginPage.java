package page;

import data.DataHelper;
import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class LoginPage {


    public LoginPage(DataHelper.AuthInfo info) {
        $("[data-test-id=login] input").setValue(info.getLogin());
        $("[data-test-id=password] input").setValue(info.getPassword());
        $("[data-test-id=action-login]").click();
    }

    public VerificationPage login() {
        return new VerificationPage();
    }

    public void invalidLogin() {
        $x("//div[contains (text(), 'Ошибка! ')]").shouldBe(Condition.visible);
    }
}