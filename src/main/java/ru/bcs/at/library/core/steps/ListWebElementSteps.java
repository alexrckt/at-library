/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p style="color: green; font-size: 1.5em">
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p style="color: green; font-size: 1.5em">
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.bcs.at.library.core.steps;

import com.codeborne.selenide.SelenideElement;
import cucumber.api.java.ru.И;
import lombok.extern.log4j.Log4j2;
import ru.bcs.at.library.core.cucumber.api.CoreScenario;

import java.util.stream.Collectors;

/**
 * <h1 style="color: green; font-size: 2.2em">Шаги для работы с списком элементов</h1>
 *
 * @author Anton Pavlov
 */
@Log4j2
public class ListWebElementSteps {

    private CoreScenario coreScenario = CoreScenario.getInstance();

    /**
     * <p style="color: green; font-size: 1.5em">Клик по заданному элементу в блоке
     * @param elementName имя элемента
     * @param blockName имя блока
     * </p>
     */
    @И("^выполнено нажатие на (?:кнопку|поле) \"([^\"]*)\" в блоке \"([^\"]*)\"$")
    public void clickOnElementInBlock(String elementName, String blockName) {
        coreScenario.getCurrentPage().getBlock(blockName).getElement(elementName).click();
    }

    @И("^в блоке \"([^\"]*)\" найден список элементов\"([^\"]*)\" и сохранен в переменную \"([^\"]*)\"$")
    public void getElementsList(String blockName, String listName, String varName) {
        coreScenario.setVar(varName, coreScenario.getCurrentPage().getBlock(blockName).getElementsList(listName));
    }

    @И("^в блоке \"([^\"]*)\" найден список элементов\"([^\"]*)\" и сохранен текст в переменную \"([^\"]*)\"$")
    public void getListElementsText(String blockName, String listName, String varName) {
        coreScenario.setVar(varName,
                coreScenario.getCurrentPage()
                        .getBlock(blockName)
                        .getElementsList(listName)
                        .stream()
                        .map(SelenideElement::getText)
                        .collect(Collectors.toList()));
    }
}
