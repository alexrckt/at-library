# language: ru
  @vtb
Функционал: Вход в ВТБ-Онлайн
  Сценарий: Проверка отображения сообщения о ошибке
    Когда совершен переход на страницу "ВТБ" по ссылке "https://vtb.ru"
    И выполнено нажатие на кнопку "ВТБ-Онлайн"
    Тогда страница "ВТБ-Онлайн" загрузилась
    Когда в поле "Номер карты" введено значение "4444 3333 2222 1111"
    И выполнено нажатие на кнопку "Далее"
    Тогда текст "Вход в систему доступен только клиентам ВТБ" отображается на странице