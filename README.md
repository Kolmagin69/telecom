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
сервису subscriber каждые 15 секунд по протоколу HTTP в формате JSON файла
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
    "dateTime": "1605862347042",
    "message": "Threads are already running, only 5 threads can be run at a time!",
    "httpStatus": "400 BAD_REQUEST"
}
```
Чтобы остановить многопоточную отправку сообщений subscriber, необходимо отправить POST запрос:
```
POST /publisher/stop HTTP/1.1
Host: localhost:8089
```
Сервис также должен вернуть статус запроса 200 и строку такого вида:
```
Stop Threads : [ 1 - Thread-165, 2 - Thread-163, 3 - Thread-166, 4 - Thread-167, 5 - Thread-164 ]
```
После чего можно будет вновь запустить генерацию и отправку сообщений посредством HTTP запроса

### Subscriber 
Сервис Subscriber принимает сообщения по POST HTTP запросу в формате JSON файла:
```json
{
  "id":239, 
  "msisdn":1393528971, 
  "action":"SUBSCRIPTION", 
  "timestamp":"1605862347042"
}
```
парсит сообщение по значению ключа action(бывает двух типов PURCHASE и SUBSCRIPTION) и сохраняет в соответствующую 
таблицу базы данных, ниже приведена структура при создании таблиц
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
Для отправки сообщения необходимо сделать POST запрос:
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
> сборка занимает около 5 минут

2 После удачной сборки артефактов и docker образов необходимо прописать в командной строке из текущей директории: 
 ```
docker-compose up 
 ```
После того как docker развернет модули и базы данных (работают на портах 5406, 5409 соответственно) сервисом можно 
пользоваться

> причем чтобы подключиться к одной из баз данных необходимо прописать в командной строке:
 ```
# connect to publisher db
docker exec -it database_pub psql -U postgres

# connect to subscriber db
docker exec -it database_sub psql -U postgres
 ```
#### На локальной машине 
1 Для запуска сервиса необходимо сначала сконфигурировать настройки:
* application.properties в директории publisher надо поменять строки 4, 6, 7, 8(либо выставить свои настройки)
 ```
# publisher/src/main/src/main/resources/application.properties

subscriber.port=localhost:8086

spring.datasource.username=publisher
spring.datasource.password=pas123
spring.datasource.url=jdbc:postgresql://localhost:5432/publisher
```

* application.properties в директории subscriber надо  строки 3, 4, 5
```
# subscriber/src/main/resources/application.properties

spring.datasource.username=subscriber
spring.datasource.password=pas123
spring.datasource.url=jdbc:postgresql://localhost:5432/subscriber
 ```
* (не обязательно) в файле pom.xml сервисов удалить spotify plugin (чтобы немного уменьшить время сборки приложения)

2 После этого нужно создать USER и DATABASE в postgresql. Для этого необходимо подключиться к базе дынных:
 ```
psql -U postgres
 ```
подключившись к базе выполнить SQL запрос:
 ```
postgres=# 
    
SQL query to creation publisher =#
    CREATE USER publisher WITH PASSWORD 'pas123';
    CREATE DATABASE publisher OWNER publisher;

SQL query to creation subscriber =#
    CREATE USER subscriber WITH PASSWORD 'pas123';
    CREATE DATABASE subscriber OWNER subscriber;    

 ```

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


