# HaveTimeRouting
`REST API` для построения кратчайших по расстоянию маршрутов по дорожной сети `OpenStreetMap`.

## Стек
Java 21, Spring Boot 4, Maven, GraphHopper 11, Swagger/OpenAPI.

## Возможности
- Пешие (`FOOT`) и автомобильные (`CAR`) маршруты.
- Расстояние в метрах и приблизительное время в секундах.
- Координаты маршрута для отображения на карте.
- Валидация входных данных.

## Запуск
Для запуска необходимы `JDK 21` и установленный `Maven`.

1. Скачайте карту Центрального федерального округа Российской Федерации в формате `.osm.pbf` с [Geofabrik](https://download.geofabrik.de/russia/central-fed-district.html).
2. Поместите файл в папку `data` в корне проекта.
3. Укажите его имя в `src/main/resources/application.properties`:

```properties
routing.osm-file=${ROUTING_OSM_FILE:data/central-fed-district-261024.osm.pbf}
routing.graph-location=${ROUTING_GRAPH_LOCATION:graph-cache}
```
**Замените `central-fed-district-261024.osm.pbf` на имя скачанного файла.**

4. Откройте терминал в корне проекта и соберите JAR:

```shell
mvn clean package
```

5. Запустите приложение из той же папки:

```shell
java -jar target/havetimerouting-0.0.1-SNAPSHOT.jar
```

Также можно запустить главный класс приложения в `IDE` или выполнить:

```shell
mvn spring-boot:run
```

Карта и подготовленный граф хранятся отдельно от `JAR`. При первом запуске `GraphHopper` создаёт граф 
в `graph-cache`. Это может занять время. Последующие запуски используют сохранённый граф.

## API
`POST /api/v1/routes`

Тело запроса (`Content-Type: application/json`):

```json
{
  "startLat": 55.751244,
  "startLon": 37.618423,
  "endLat": 55.760186,
  "endLon": 37.618711,
  "profile": "FOOT"
}
```

Если профиль не указан, используется `FOOT`. Время пути оценивается без учёта актуальных пробок.

## Документация
После запуска доступен [Swagger UI](http://localhost:8080/swagger-ui/index.html), где можно посмотреть описание `API` и отправить тестовый запрос.