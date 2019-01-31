/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.bcs.at.library.core.steps;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.google.common.base.Strings;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.qameta.allure.restassured.AllureRestAssured;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import lombok.experimental.Delegate;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import ru.bcs.at.library.core.cucumber.api.CoreEnvironment;
import ru.bcs.at.library.core.cucumber.api.CoreScenario;
import ru.bcs.at.library.core.log.Log4jRestAssuredFilter;

import java.net.MalformedURLException;
import java.net.URI;

import static com.codeborne.selenide.Configuration.browser;
import static com.codeborne.selenide.WebDriverRunner.*;
import static ru.bcs.at.library.core.core.helpers.PropertyLoader.loadProperty;

@Log4j2
public class InitialSetupSteps {

    @Delegate
    CoreScenario coreScenario = CoreScenario.getInstance();

    private static boolean turnOnAllureListener = false;

    /**
     * @author Anton Pavlov
     * Действия выполняемые перед каждым сценарием
     */
    @Before
    public void beforeEachTest(Scenario scenario) throws MalformedURLException {

        RestAssured.baseURI = System.getProperty("baseURI", loadProperty("baseURI"));
        Configuration.baseUrl = System.getProperty("baseURI", loadProperty("baseURI"));

        if (!turnOnAllureListener) {
            SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(false));
            RestAssured.filters(
                    new AllureRestAssured(),
                    new Log4jRestAssuredFilter()
            );
            turnOnAllureListener = true;
        }

        /**
         * @author Anton Pavlov
         * Если сценарий содержит тег @web" то будет создан WebDriver
         */
        boolean webTest = scenario.getSourceTagNames().contains("@web");
        if (webTest) {
            startWebTest(scenario);
        }

        /**
         * @author Anton Pavlov
         * Создает окружение(среду) для запуска сценария
         *
         * @param scenario сценарий
         * @throws Exception
         */
        coreScenario.setEnvironment(new CoreEnvironment(scenario, webTest));
    }


    /**
     * @author Anton Pavlov
     * По завершению теста удаляет все куки и закрывает веб-браузер
     */
    @After
    public void afterEachTest(Scenario scenario) {
        boolean webTest = scenario.getSourceTagNames().contains("@web");
        if (webTest) {
            Selenide.clearBrowserLocalStorage();
            Selenide.clearBrowserCookies();
            Selenide.close();
        }
    }

    /**
     * @author Anton Pavlov
     * Создание WebDriver
     */
    private void startWebTest(Scenario scenario) throws MalformedURLException {
        /**
         * @author Anton Pavlov
         * Создает настойки прокси для запуска драйвера
         */
        if (!Strings.isNullOrEmpty(System.getProperty("proxy"))) {
            Proxy proxy = new Proxy().setHttpProxy(System.getProperty("proxy"));
            setProxy(proxy);
            log.info("Проставлена прокси: " + proxy);
        }

        /**
         * @author Anton Pavlov
         * Уведомление о месте запуска тестов
         */
        if (Strings.isNullOrEmpty(Configuration.remote)) {
            log.info("Тесты будут запущены локально в браузере: " + browser);
        } else {
            log.info("Тесты запущены на удаленной машине: " + Configuration.remote);

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName(browser);
            capabilities.setCapability("enableVNC", true);
            capabilities.setCapability("enableVideo", false);
            capabilities.setCapability("screenResolution", "1920x1080");
            capabilities.setCapability("width", "1920");
            capabilities.setCapability("height", "1080");
            capabilities.setCapability("name", scenario.getName());

            setWebDriver(
                    new RemoteWebDriver(
                            URI.create(Configuration.remote).toURL(),
                            capabilities));
        }

        /**
         * @author Anton Pavlov
         * Устанавливает разрешения экрана
         */
        getWebDriver().manage().window().setSize(new Dimension(1920, 1080));
        /**
         * @author Anton Pavlov
         * Удаляет все cookies
         */
        getWebDriver().manage().deleteAllCookies();
    }

}
