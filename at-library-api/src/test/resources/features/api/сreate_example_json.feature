# language: ru
@all
@api
Функционал: Тесты платформы

  Сценарий: провреки методов
    И заполняю json-шаблон "/src/test/resources/restBodies/example.json" данными из таблицы и сохраняю в переменную "готовый-json"
      | example glossary | test_test |

    И в json "готовый-json" значения равны значениям из таблицы
      | glossary.title | test_test |


    * установлено значение переменной "дата_текущая_минус_1_час" с текущей датой минус 1 час в формате "dd.MM.yyyy hh:mm"
    * установлено значение переменной "дата_текущая_плюс_1_час" с текущей датой плюс 1 час в формате "dd.MM.yyyy hh:mm"
    * установлено значение переменной "дата_текущая_плюс_1_час" с текущей датой в формате "dd.MM.yyyy hh:mm"
