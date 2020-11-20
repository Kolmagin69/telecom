## Telecom

## Cервис сообщений Pub-Sub
### Структура 
* Описание
* Модуль Publisher 
* Модуль Subscriber 
* Развертывание сервиса  
    * В докер контейнере
     * На локальной машине

### Описание 

Сервис состоит из 2 приложений. Первое приложение Publisher отвечает за генерацию и многопоточную отправку 
сообщений подписчику (subscriber) по HTTP протоколу. Subscriber разбирает сообщения и записывает его
в базу данных.

Стек технологий:
* Сервис написан на базе Spring Framework
  * Spring Boot, Spring Boot Data JPA
 * Hibernate в качестве реализации JPA
 * PostgreSQL в качестве базы данных 
 * Jackson для работы .json файлами
 

### Publisher
Сервис Publisher генерирует сообщения, записывает в базу данных и осуществляет их отправку в пять потоков 
сервису subscriber каждые 15 секунд по протоколу HTTP в виде JSON файла
##### Пример сообщения:
```json
{
    "id": 1,
    "msisdn": 123456789,
    "action": "PURCHASE",
    "timestamp": 1589464122
}
```
, где 
1.	ID. Идентификатор отправленного сообщения.
2.   Msisdn. Уникальный номер абонента. 
3.   Action. Тип сообщения (бывает двух типов PURCHASE и SUBSCRIPTION)
4.   Timestamp. Время и дата отправки сообщения

После запуска приложений для начала сессии(генерация и отправка сообщений) необходимо отправить POST запрос:
```
POST /publisher/start HTTP/1.1
Host: localhost:8089
```
Сервис должен вернуть статус запроса 200 и строку такого вида:
```
Start Threads : [ 1 - Thread-165, 2 - Thread-163, 3 - Thread-166, 4 - Thread-167, 5 - Thread-164 ]
```
причем повторный запрос вернет сообщение об ошибки, так как стоит ограничение 
```json
{
    "dateTime": "2020-11-20T10:20:48.047+03:00[UTC+03:00]",
    "message": "Threads are already running, only 5 threads can be run at a time!",
    "httpStatus": "400 BAD_REQUEST"
}
```
Чтобы остановить многопоточную отправку сообщений subscriber необходимо отправить POST запрос:
```
POST /publisher/stop HTTP/1.1
Host: localhost:8089
```
Сервис так же должен вернуть статус запорса 200 и строку такого вида:
```
Stop Threads : [ 1 - Thread-165, 2 - Thread-163, 3 - Thread-166, 4 - Thread-167, 5 - Thread-164 ]
```
После чего можно будет вновь запустить генерацию и отправку сообщений по средствам HTTP запроса

### Subscriber 
Сервис Subscriber принимает сообщения по POST HTTP запросу в виде JSON файла:
```json
{
  "id":239, 
  "msisdn":1393528971, 
  "action":"SUBSCRIPTION", 
  "timestamp":"1605862347042"
}
```
парсит сообщение по значению ключа action(бывает двух типов PURCHASE и SUBSCRIPTION) и сохраняет в соответствующую 
таблицу базы данных, ниже привидена структура при создании таблиц
```
CREATE TABLE subscriptions (
    id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    msisdn int NOT NULL,
    timestamp timestamp NOT NULL
);

CREATE TABLE purchases (
    id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    msisdn int NOT NULL,
    timestamp timestamp NOT NULL
);
```
Для оправки сообщения необходимо сделать POST запрос:
```
POST /subscriber/post/message HTTP/1.1
Host: subscriber:8086
Content-Type: application/json

{
    "id":258, 
    "msisdn":1393525412, 
    "action":"SUBSCRIPTION", 
    "timestamp":"1605862341035"
}
```

### Развертывание сервиса 
 #### В докер контейнере
Требования:
* наличие установленного [docker](https://www.docker.com/products/docker-desktop) и 
[docker-compose](https://docs.docker.com/compose/install/) 

1 Для запуска сервиса необходимо прописать в командной строке из текущей директории:
 ```
 mvn package
 ```
>сборка занимает в районе 5 минут (это как то связано с spotify плагином для сборки docker image)

2 После удачной сборки необходимо прописать в командной строке: 
 ```
docker-compose up 
 ```
После того как docker развернет модули и базы данных (работают на портах 5406, 5409 соответственно) сервисом можно 
пользоваться

> причем чтобы подключиться к одной из баз данных необходимо прописать в командной строке
 ```
psql -h localhost -p 5406 -U postgres
 ```
#### На локальной машине 
1 Для запуска сервиса необходимо сначала откорректировать несколько файлов:
* в файлах application.properties в директории publisher и subscriber надо подменить строки 4, 5, 6(либо выставить свои настройки)
 ```
# publisher/src/main/src/main/resources/application.properties
spring.datasource.username=publisher
spring.datasource.password=pas123
spring.datasource.url=jdbc:postgresql://localhost:5432/publisher

# subscriber/src/main/resources/application.properties
spring.datasource.username=subscriber
spring.datasource.password=pas123
spring.datasource.url=jdbc:postgresql://localhost:5432/subscriber
 ```
* class intech.com.controller.FiveThreadController находящийся в директории publisher/src/main/java/intech/com/controller строчку 40 меняем на:
 ```
final URI localhostURI = URI.create("http://subscriber:8086/subscriber/post/message");

 ```
* в файле pom.xml сервисов необходимо удалить spotify plugin

2 После этого нужно создать USER, DATABASE, TABLE в postgresql:
>файл с SQL запросами для каждого сервиса находиться в соответствующей папке \src\main\resources\db.sql

3 Далее из главной директории в командной строке надо прописать:
 ```
 mvn package
 ```
4 На последнем этапе нужно запустить собранные артефакты *.jar для этого в командной строке:

```
java -Djava.util.logging.config.file=/subscriber/resourse/logback.xml -jar subscriber/target/subscriber-1.jar

java -Djava.util.logging.config.file=/publisher/resourse/logback.xml -jar publisher/target/publisher-1.jar
 ```
> причем логи помимо вывода на экран будут записываться в файл .log лежащий в publisher/logging и subscriber/logging соответственно

> При желании можно пропустить 3 и 4 пункт и запустить приложение через среду разработки (например Intellij IDEA)

Сервис запущен и им можно пользоваться


