/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.at.library.core.setup;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import lombok.experimental.Delegate;
import lombok.extern.log4j.Log4j2;
import ru.at.library.core.core.helpers.LogReportListener;
import ru.at.library.core.cucumber.api.CoreEnvironment;
import ru.at.library.core.cucumber.api.CoreScenario;

import static ru.at.library.core.core.helpers.PropertyLoader.tryLoadProperty;

/**
 * Начальная настройка
 */
@Log4j2
public class InitialSetupSteps {

    private static int scenarioNumber = 0;

    @Delegate
    CoreScenario coreScenario = CoreScenario.getInstance();

//    @Attachment(value = "Web Driver Logs", type = "text/plain", fileExtension = ".log")
//    private static String attachmentWebDriverLogs() {
//        /**
//         * Чтоб все логи консоли успели загрузится
//         */
//        Selenide.sleep(1000);
//        List<String> webDriverLogs = Selenide.getWebDriverLogs(LogType.BROWSER, Level.ALL);
//        StringBuilder stringBuilder = new StringBuilder();
//        for (String logText : webDriverLogs) {
//            stringBuilder.append(logText);
//            stringBuilder.append("\n\n");
//            log.trace(logText);
//        }
//
//        return stringBuilder.toString();
//    }

    /**
     * Действия выполняемые перед каждым сценарием
     * Включение слушателей Allure
     * Если сценарий содержит тег @web" то будет создан WebDriver
     * Создает окружение(среду) для запуска сценария
     */
    @Before(order = 0)
    public void startUITestInBrowser(Scenario scenario) {
        scenarioNumber++;

        log.info(String.format("%s: старт сценария %d с именем [%s]", scenario.getId(), scenarioNumber, scenario.getName()));

        RestAssured.baseURI = System.getProperty("baseURI", tryLoadProperty("baseURI"));
        Configuration.baseUrl = System.getProperty("baseURI", tryLoadProperty("baseURI"));

        /**
         * Если сценарий содержит тег @web" то будет создан WebDriver
         */
        new InitialDriver().startUITest(scenario);

        /**
         * Создает окружение(среду) для запуска сценария
         *
         * @param scenario сценарий
         * @throws Exception
         */
        coreScenario.setEnvironment(new CoreEnvironment(scenario));

        LogReportListener.turnOn();
    }

    @After(order = 0)
    public void endOfTest(Scenario scenario) {
        log.info(String.format("%s: завершение сценария с именем [%s]", scenario.getId(), scenario.getName()));

        boolean quitDriver = doNeedToCloseTheBrowser(tryLoadProperty("ENVIRONMENT"));
        tryingToCloseTheBrowser(scenario, quitDriver);

    }

    @Step("В зависимости от стенда принимаете решение о закрытии браузера")
    private boolean doNeedToCloseTheBrowser(String environment) {
        boolean quitDriver = true;
        switch (environment) {
            case "dev": {
                break;
            }
            case "integrative": {
                break;
            }
            case "prod": {
                quitDriver = false;
                break;
            }
        }
        return quitDriver;
    }



    @Step("Попытка закрытия браузера")
    private void tryingToCloseTheBrowser(Scenario scenario, boolean quitDriver) {
        if (quitDriver) {
            try {
                Selenide.close();
                log.info(String.format("%s: драйвер успешно остановлен", scenario.getId()));
            } catch (IllegalStateException ex) {
                log.warn(String.format("%s: Использовался метод getWebDriver().quit(), но браузер не был запущен", scenario.getId()));
            }
        }
    }
}
