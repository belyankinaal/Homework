# language: ru

Функционал: Автотесты на Edujira

  Сценарий: Проверка статуса и Fix Version задачи TestSeleniumATHomework
    Дано пользователь авторизован и находится в проекте Test
    Когда он вводит в поиск задачу "TestSeleniumATHomework"
    И открывает задачу
    Тогда видит статус задачи "Сделать"
    И Fix Version "Version 2.0"