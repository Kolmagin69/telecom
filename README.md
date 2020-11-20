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

Сервис состоит из 2 приложений. Первое приложение Publisher отвечает за генерацию и отправку 
сообщений подписчику (subscriber) по HTTP протоколу. Subscriber разбирает сообщения и записывает его
в базу данных.

Протокол
Publisher и subscriber общаются между собой методом отправки JSON сообщений по следующему протоколу:

*	ID. Идентификатор отправленного сообщения. Автоинкрементится в каждом сообщении
*   Msisdn. Уникальный номер абонента. В рамках этого задания рандомно сгенерированное цифровое
 значние.
*   Action. Тип сообщения. Сообщения могут быть 2 типов – PURCHASE или SUBSCRIPTION. Значение 
выбирается рандомно 
при генерации сообщения.
*   Timestamp. UNIX timestamp.

### Пример сообщения:
```json
{
    "id": 1,
    "msisdn": 123456789,
    "action": "PURCHASE",
    "timestamp": 1589464122
}
```
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
Что остановить многопоточную отправку сообщений subscriber необходимо отправить POST запрос:
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

Для запуска сервиса необходимо прописать в командной строке из текущей директории:
 ```
 mvn package
 ```
>сборка занимает в районе 5 минут (это как то связано с spotify плагином для сборки docker image)

После удачной сборки необходимо прописать в командной строке: 
 ```
docker-compose up
 ```
После того как docker развернет модули и базы данных (работают на портах 5406, 5409 соответственно) сервисом можно 
пользоваться

> причем чтобы подключиться к одной из баз данных необходимо прописать в командной строке
 ```
psql -h localhost -p 5406 -U postgres
 ```
 



