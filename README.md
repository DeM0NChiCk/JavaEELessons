# JavaEE
### Формат сдачи 
- #### Cоздать приватный репозиторий и добавить [@DeM0NChiCk](https://github.com/DeM0NChiCk) в collaborator.
- #### Создать отдельную ветку. Сделать pull request с кратким описанием commit'ов и подробным description о том, что вы сделали в рамках домашнего задания. Прикрепить скринкаст с выполненной работой. Добавить меня в Reviewers.
- #### Первое домашнее задание можно сделать в ветке master/main, для следующих требуется создавать новые ветки.

## Пример работы с Servlet
### Домашнее задание 15.03.2025
-  Создать 6 сервлетов. 2 с помощью файла конфигурации web.xml и 3 с помощью аннотаций и классов. 
-   1. Первый сервлет ознакомительный. На нём вывести ваше имя и кнопку "начать работу", которая будет вести на второй сервлет. (path URL: /)
    2. На втором сервлете краткая информация о чём сервлет. На странице должны быть два поля вводу и 4 кнопки (сумма, вычитание, умножение, деление) (path URL: /calculator)
    3. Оставшиеся сервлеты выводят результат вычисления или текст, что данные введены не корректно. (path URL: /calculator/sum, /calculator/sub, /calculator/div, /calculator/multi)
    4. Требуется вынести логику вычисления из сервлета, то есть реализация вычисления происходит внутри пакета service

## Базы данных

### Домашнее задание 18.03.2025
- Теория: Изучать связи базы данных One to One, One to Mane, Many to Many в postgresql. Подумать над моделью данных для вашего сайт. Например: онлайн магазин (users, products, shop), онлайн газета (users, publications).
- Требования: Поднять базу данных, создать таблицу с сущностями, заполнить данными.

## JDBS и ServletContextListener

### Домашнее задание 22.03.2025
- Повторить всё что писали на занятии. Написать свой ServletContextListener, написать модели для своих сущностей в БД в model/

## Repository, Mappers, DTO, Service

### Домашнее задание 25.03.2025
- Реализовать Repository для работы со своей базой данных. Добавить mapper, dto, service. Написать servlet для обработки запроса и ответа с сервисом. 

## Фильтрация и связи таблиц

### Домашнее задание 29.03.2025
- Реализовать связи m2m в рамках своего проекта. Добавить возможность фильтрации каталога (можно на стороне клиента через js, можно через service и forward в servlets)

## Авторизация и фильтры

### Домашнее задание 01.04.2025
- Реализовать авторизацию и аутентификацию на сайт с помощью фильтров.

### Домашнее задание 05.04.2025
- Продолжить реализацию вашей бизнес-логики

## Spring

### Домашнее заадание 08.04.2025
- Задать beans мапперов через spring-context.xml, все остальные через аннотации. Рекомендуется изучить аннотации спринга.
