# language: ru
@unit
@web
@web_list_steps.feature
Функционал: WebListSteps

  Предыстория: Предыстрория
    Когда совершен переход на страницу "BCS demo аккаунт" по ссылке "url.broker.demo"

####################################################################################

  Сценарий: список из переменной "([^"]*)" содержит значение (?:поля|элемента) "([^"]*)"
    И написание автотеста в работе
    Когда список из переменной "ааа" содержит значение поля "ббб"

####################################################################################

  Сценарий: список "([^"]*)" отображается на странице
    И написание автотеста в работе
    Когда список "аааа" отображается на странице

####################################################################################