# Installation

**docker-compose**

Use docker_compose to up database with default data from create.sql, all settings can be found in docker-compose.yml
```
docker-compose up -d
```

**jooq**

I use JOOQ, so for entities codegen you should launch
```
maven jooq-codegen:generate -Dspring.datasource.url=jdbc:postgresql://localhost:5432/warrobots -Dspring.datasource.username=user -Dspring.datasource.password=password -f pom.xml
```

**Launch**

Override if needed
```
-Dspring.datasource.url=jdbc:postgresql://localhost:5432/warrobots -Dspring.datasource.username=user -Dspring.datasource.password=password
```

# API
Without swagger configuration.

Test examples can be found in ./requests

server.port=9831 by default

**API for getting current state of database**

Users list: `GET /api/reference/list/users`
Items list: `GET /api/reference/list/items`

**Business logic**

Buy item: `POST /api/buy`
```
{
  "userGuid": "",
  "itemId": 0
}
```

Gift item: `POST /api/gift`
```
{
  "senderUserGuid": "",
  "acceptorUserGuid": "",
  "itemId": 0
}
```
