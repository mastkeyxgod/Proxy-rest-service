# REST-API

Проект написан на Java 17 с использованием Spring Framework и его модулей.

## Требования:

## 1. Реализовать обработчики, которые проксируют запросы к [JsonPlaceHolder](rhttps://jsonplaceholder.typicode.com/)
Реализованы обработчики (**GET, POST, PUT, PATCH, DELETE**) на эндпоинтах:
* **/api/v1/jph/users/**** (проксирует к jsonplaceholder.typicode.com/users)
* **/api/v1/jph/posts/**** (проксирует к jsonplaceholder.typicode.com/posts)
* **/api/v1/jph/albums/**** (проксирует к jsonplaceholder.typicode.com/albums)

Подробнее на [swagger](https://app.swaggerhub.com/apis/ilyafetyukhin/VkJavaTest/1.0.0).

## 2. Реализовать базовую авторизацию.

Базовая авторизация и роли реализованы с помощью модуля Spring Security.

## 3. Проработать ролевую модель.

Проработана гибкая ролевая модель, в которой роли включают в себя *привилегии*.
Доступ к эндпоинтам может ограничиваться как ролями, так и привилегиями для более гибкой настройки.
Также присутствует кастомная иерархия ролей, в которой к ролям, стоящим более высоко в иерархии, будут при 
первом запуске будут подкачиваться привилегии более низших, указанных нами, ролей.
То есть при добавлении новой роли, нам нужно присвоить ей только новые привилегии, которые будут относиться 
**только к этой роли**, а если она должна еще и иметь привилегии других ролей, мы можем поставить ее более выше в 
иерархии и эти привилегии автоматически подкачаются к ней.

Чтобы создать нувую привилегию, нужно воспользоваться методом `createPrivilegeIfNotFound(String name)` в классе `ru/mastkey/vkbackendtest/setup/SetupDataLoader.java`, например:
```java
Privilege postCreatePrivilege = createPrivilegeIfNotFound("CREATE_POSTS_PRIVILEGE");
```
Это создаст привилегию CREATE_POSTS_PRIVILEGE.

Создание ролей также происходит в этом классе, но методом `createRoleIfNotFound(String name, Set<Privilege> privileges)`, например:
```java
createRoleIfNotFound("ROLE_USERS_EDITOR", Set.of(userCreatePrivilege, userUpdatePrivilege, userDeletePrivilege));
```

Для создания роли нам нужно передать **Сет** привилегий, чтобы они стали доступны этой роли. Но мы также можем передать пустой сет, и добавить привилегии через настройку иерархии.

- Иерархия ролей настраивается в `java/ru/mastkey/vkbackendtest/security/SecurityFilterConfigurer.java`, в Бине RoleHierarchy:
```java  
            String hierarchy =
                        "ROLE_ADMIN > ROLE_USERS_EDITOR\n" +
                        "ROLE_ADMIN > ROLE_POSTS_EDITOR\n" +
                        "ROLE_ADMIN > ROLE_ALBUMS_EDITOR\n" +
                        "ROLE_ADMIN > ROLE_WEBSOCKETS\n" +
                        "ROLE_USERS_EDITOR > ROLE_USERS_VIEWER\n" +
                        "ROLE_POSTS_EDITOR > ROLE_POSTS_VIEWER\n" +
                        "ROLE_ALBUMS_EDITOR > ROLE_ALBUMS_VIEWER\n" +
                        "ROLE_VIEWER > ROLE_USERS_VIEWER\n" +
                        "ROLE_VIEWER > ROLE_POSTS_VIEWER\n" +
                        "ROLE_VIEWER > ROLE_ALBUMS_VIEWER";
```

Чтобы указать, что роль A стоит выше в иерархии чем роль B, то нужно с новой строки указать: **"A > B"**:

```java
  "ROLE_A > ROLE_B";
```
И после этого все привилегии, имеющиеся у роли B, станут доступны роли A, если при запуске приложения у нас указан параметр [настройки ролей](#настройка-ролей-и-привилегий).


Вот так выглядит иерархия роллей с их привилегиями в проекте:

![roles](https://github.com/mastkeyxgod/Vk-Java-Internship-Test/assets/126360234/d64dd9e3-0a9b-4583-b99d-20bf416c3113)

* **ROLE_ADMIN** - имеет доступ ко всем обработчикам и админскому обработчику
* **ROLE_POSTS_EDITOR** - имеет доступ ко всем обработчикам /api/v1/jph/posts/**
* **ROLE_USERS_EDITOR** - имеет доступ ко всем обработчикам /api/v1/jph/users/**
* **ROLE_ALBUMS_EDITOR** - имеет доступ ко всем обработчикам /api/v1/jph/users/**
* **ROLE_VIEWER** - имеет доступ к GET методам следующих обработчиков:
    * /api/v1/jph/posts/**
    * /api/v1/jph/users/**
    * /api/v1/jph/users/**
* **ROLE_WEBSOCKET** - имеет доступ к подключению по вебсокетам по /ws
* **ROLE_POSTS_VIEWER** - имеет доступ к GET методам обработчика /api/v1/jph/posts/**
* **ROLE_USERS_VIEWER** - имеет доступ к GET методам обработчика /api/v1/jph/users/**
* **ROLE_ALBUMS_VIEWER** - имеет доступ к GET методам обработчика /api/v1/jph/albums/**


## 4. Реализовать ведение аудита действий.

Аудит реализовон посредством создания CustomAuthorizationEventPublisher'а, который отлавливает все попытки авторизации, и публикует AuthorizationEvent. Этот ивент отлавливется в AuthorizationEventListener и уже записыватся как новая запись в таблице аудит.

Поля Аудита:
* **method** - метод действия
* **endpoint** - эндпоинт, по которому совершалось действие
* **status** - *GRANTED* - если пользователь имеет доступ, *DENIED* - если доступа нет
* **user** - username пользователя, который совершал действие(если пользователь заходит без авторизации, то он будет записан как **anonymousUser**)
* **ip** - ip адрес, с которого было произведено действие
* **timestamp** - время, в которое было произведено действие

Таблица аудита в БД:

![audit](https://github.com/mastkeyxgod/Vk-Java-Internship-Test/assets/126360234/2d0f2dad-288c-45cd-86fe-74d3e48b2de7)


## 5. Реализовать inmemory кэш.

Кэш реализован с помощью Spring Cache. При GET запросах на всех обработчиках проксирования 
на [JsonPlaceHolder](rhttps://jsonplaceholder.typicode.com/) объект 
ответа заносится в кэш, при PUT и 
PATCH - обновляется в кэше, а при DELETE - удаляется.

Кэш настроен так, что его максимальный размер - 100, а запись удаляется через 5 минут после попадания в кэш.

Максимальный размер контроллируется переменной `cache.max-size`, а время жизни в минутах - переменной `cache.life-time-in-minutes`. Обе переменные находятся в `application.yml`.

## Дополнительно:

## 0. Простота запуска приложения.

**Запуск приложения**:
  - Настройте конфигурацию базы данных
  - Настройте тестовые данные и роли с привилегиями
  - Выполните команду `./gradlew bootRun` для запуска приложения.

**Конфигурация базы данных находится в `application.yml`**:
  - URL базы данных: `jdbc:mysql://localhost:3306/security`
  - Имя пользователя: `root`
  - Пароль: `root`

#### **Настройка ролей и привилегий**: 
  - Создание ролей и привилегий находится в классе `java/ru/mastkey/vkbackendtest/setup/SetupDataLoader.java`. 
Там создаются все новые роли и привилегии, и если они не записаны в бд - то записываются.
Установите переменную `setup.roles-and-privileges` в `true` в файле `application.yml`, чтобы активировать настройку.

**Тестовые данные**:
  - Приложение может создавать тестовых пользователей и назначать им роли. Установите переменную `setup.test-users` в `true`, чтобы активировать эту функцию.


  Если эта функция активированна, то при запуске приложения будут созданы следующие пользователи:


  - Admin (username: "admin", password: "admin", roles: {ROLE_ADMIN})
  - WebSocketUser (username: "websocket", password: "test", roles: {ROLE_WEBSOCKETS})
  - UsersEditor (username: "user_editor", password: "test", roles: {ROLE_USERS_EDITOR})
  - PostsEditor (username: "post_editor", password: "test", roles: {ROLE_POSTS_EDITOR})
  - AlbumsEditor (username: "album_editor", password: "test", roles: {ROLE_ALBUMS_EDITOR})
  - UsersViewer (username: "user_viewer", password: "test", roles: {ROLE_USERS_VIEWER})
  - Viewer (username: "viewer", password: "test", roles: {ROLE_VIEWER})

  После запуска с этой (активированной) функией, стоит **отключить** ее, чтобы избежать дублирования данных в БД.

## 1. Использование базы данных.

В проекте использовалась реляционная база данных MySQL:
* **Для ведения аудита**
* **Для хранения данных пользователей, зарегестрированных в системе**
* **Для хранения данных ролей и привилегий**

Структура базы данных:

![db](https://github.com/mastkeyxgod/Vk-Java-Internship-Test/assets/126360234/fea42a4d-89fb-4a92-af07-598847a37781)

## 2. Добавление REST Api для создания пользователей.

Было добавлено 2 обработчика - **/api/v1/admin** и **/api/v1/registry**.

* **admin** обработчик позваляет добавить или удалить роль у пользователя.

* **registry** - позволяет зарегестрироваться новому пользователю по имени и паролю. По дефолту выдается роль ROLL_VIEWER.

## 3. Расширенная ролевая модель.

Расширенная ролевая модель была описана в **Требованиях, п. 3**.

## 4. Написать тесты

Написаны тесты для всех обработчиков.

## 5. Реализовать конечную точку для запросов по websocket

Точка реализована по адресу /ws

Все запросы к ней перенаправляются к [echo-server](https://websocket.org/tools/websocket-echo-server/)

Эхо ответы также принимаются обратно.

Для подключения к этой точке требуется наличие привилегии WEBSOCKET_PRIVILEGE у роли.

Подключение к этой точке также отслеживается аудитом.

## 6. Валидация

Так же была добавлена валидация на **POST, PUT и PATCH** запросы на всех проксирующих обработчикиах и обработчик регистрации.

Если какое то поле не проходит валидацию, то в теле ответа на такой запрос будет Map, в **которой ключ - поле, значение - ошибка, связанная с этим полем**.


