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
package ru.bcs.at.library.core.cucumber.api;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.util.Arrays;
import java.util.Collection;

public final class Spectators {

    private Spectators() {

    }

    /**
     * @param selenideCondition Selenide.Condition
     * @param timeout           максимальное время ожидания в миллисекундах для перехода элементов в заданное состояние
     * @param selenideElements  произвольное количество selenide-элементов
     * @author Anton Pavlov
     * Обертка над Selenide waitUntil для произвольного числа элементов
     * @see SelenideElement#waitUntil(Condition, long)
     */
    public static void waitElementsUntil(Condition selenideCondition, int timeout, SelenideElement... selenideElements) {
        Arrays.stream(selenideElements).forEach(e -> e.waitUntil(selenideCondition, timeout));
    }

    /**
     * @param selenideCondition Selenide.Condition
     * @param timeout           максимальное время ожидания в миллисекундах для перехода элементов в заданное состояние
     * @param selenideElements  коллекция selenide-элементов
     * @author Anton Pavlov
     * Обертка над Selenide waitUntil для работы с колекцией элементов
     * @see SelenideElement#waitUntil(Condition, long)
     */
    public static void waitElementsUntil(Condition selenideCondition, int timeout, Collection<SelenideElement> selenideElements) {
        selenideElements.forEach(e -> e.waitUntil(selenideCondition, timeout));
    }

}
