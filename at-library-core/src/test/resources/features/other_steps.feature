# language: ru
@all
Функция: Проверка шагов OtherSteps

  Сценарий: Установлено значение переменной "однострочная" из application.properties
    Когда установлено значение переменной "однострочная" равным "key.text"
    Тогда значение переменной "однострочная" равно "простая многострочная переменная"

  Сценарий: Установлено значение переменной "однострочная"
    Когда установлено значение переменной "однострочная" равным "простая многострочная переменная"
    Тогда значение переменной "однострочная" равно "key.text"

  Сценарий: Установлено значение переменной "многострочная"
    Когда установлено значение переменной "многострочная" равным
  """
  сложная
  многострочная
  перменная
  """
    Тогда значение переменной "многострочная" равно
  """
  сложная
  многострочная
  перменная
  """

  Сценарий: Значения в переменных совпадают из application.properties
    Когда установлено значение переменной "первая" равным "key.text"
    И установлено значение переменной "вторая" равным "простая многострочная переменная"
    Тогда значения в переменных "первая" и "вторая" совпадают

  Сценарий: Значения в переменных совпадают
    Когда установлено значение переменной "первая" равным "простая многострочная переменная"
    И установлено значение переменной "вторая" равным "простая многострочная переменная"
    Тогда значения в переменных "первая" и "вторая" совпадают


  Сценарий: Значения в переменных НЕ совпадают
    Когда установлено значение переменной "первая" равным "простая многострочная переменная"
    И установлено значение переменной "вторая" равным "сложная однострочная перменная"
    Тогда значения в переменных "первая" и "вторая" не совпадают


  Сценарий: Верно, что
    Когда верно, что "'простая многострочная переменная'.equals('простая многострочная переменная')"


  Сценарий: Сохранено значение из property файла в переменную
    Когда сохранено значение "key.text" из property файла в переменную "переменная"
    Тогда значение переменной "переменная" равно "простая многострочная переменная"


  Сценарий: Значения из переменной и из property файла совпадают
    Когда установлено значение переменной "переменная" равным "простая многострочная переменная"
    Когда сохранено значение "key.text" из property файла в переменную "переменная_из_property"
    Тогда значения в переменных "переменная" и "переменная_из_property" совпадают


  Сценарий: шаблон "([^"]*)" заполнен данными из таблицы и сохранён в переменную "([^"]*)"$
    И шаблон "example.txt" заполнен данными из таблицы и сохранён в переменную "текст_для_проверки"
      | @varible@ | A meta-markup language, used to create markup languages such as DocBook. |
    Тогда значение переменной "текст_для_проверки" равно "Шаблон с текстом: A meta-markup language, used to create markup languages such as DocBook."

  Сценарий: Значение переменной является email-ом
    Когда генерация случайного email и сохранение в переменную "сгенерированный_email"
    Тогда значение переменной "сгенерированный_email" является email-ом


  Сценарий: Длина строки переменной равна
    Когда установлено значение переменной "однострочная" равным "простая многострочная переменная"
    Тогда длина строки переменной "однострочная" равна 32

  Сценарий: Длина строки переменной равна
    Когда установлено значение переменной "однострочная" равным "простая многострочная переменная"
    Тогда длина строки переменной "однострочная" меньше 33

  Сценарий: Длина строки переменной равна
    Когда установлено значение переменной "однострочная" равным "простая многострочная переменная"
    Тогда длина строки переменной "однострочная" больше 31


  Сценарий: выполнено ожидание в течение
    И выполнено ожидание в течение 1 секунды


  Сценарий: перемещещие файла из "([^"]*)" в "([^"]*)"
    И перемещещие файла из "aaa" в "bbbb"


  Сценарий: установлено значение переменной "([^"]*)" с текущей датой в формате "([^"]*)"
    И установлено значение переменной "текущая_дата_минус_час" с текущей датой в формате "yyyy.MM.dd G 'at' HH:mm:ss z"


  Сценарий: установлено значение переменной "([^"]*)" с текущей датой минус (\d+) (?:час|часов) в формате "([^"]*)"
    И установлено значение переменной "текущая_дата_минус_час" с текущей датой минус 1 час в формате "yyyy.MM.dd G 'at' HH:mm:ss z"


  Сценарий: установлено значение переменной "([^"]*)" с текущей датой плюс (\d+) (?:час|часов) в формате "([^"]*)"$
    И установлено значение переменной "текущая_дата_минус_час" с текущей датой плюс 1 час в формате "yyyy.MM.dd G 'at' HH:mm:ss z"


  Сценарий: ручной тест
    И ручной тест


  Сценарий: написание автотеста в работе
    И написание автотеста в работе


  Сценарий: написание автотеста в работе. Планируемая дата:
    И написание автотеста в работе. Планируемая дата: "2100 год"


  Сценарий: автотест реализован на старом фреймворке
    И автотест реализован на старом фреймворке