# account-manager

## Environment

- java 11
- gradle 7 (with gradlew)
- intellij

## Datasource

- `src/main/resources/`
    - `schema.sql`
    - `data.sql`

H2 DB auto create on start. Remove `h2_db.mv.db` if fresh start needed.

## Run/Test

- Port 8088

`.\gradlew bootRun` or IDE

## Sample request

Get all accounts
```shell
$ http localhost:8088/accounts
HTTP/1.1 200
Connection: keep-alive
Content-Type: application/json
Date: Fri, 11 Mar 2022 09:16:19 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

[
    {
        "id": 12345678,
        "name": "Eric Chan"
    },
    {
        "id": 88888888,
        "name": "Jason Ma"
    }
]
``` 

Get account by ID
```shell
$ http localhost:8088/accounts/12345678
HTTP/1.1 200
Connection: keep-alive
Content-Type: application/json
Date: Fri, 11 Mar 2022 09:16:47 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

{
    "id": 12345678,
    "name": "Eric Chan"
}
```

Transfer money
```shell
$ http POST localhost:8088/transactions destination=12345678 source=88888888 amount=100.0
HTTP/1.1 200
Connection: keep-alive
Content-Type: application/json
Date: Fri, 11 Mar 2022 09:17:29 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

{
    "amount": 100.0,
    "destination": 12345678,
    "source": 88888888,
    "timestamp": "2022-03-11T09:17:29.498195500Z"
}
```

Get balance
```shell
$ http localhost:8088/accounts/12345678/balance
HTTP/1.1 200
Connection: keep-alive
Content-Type: application/json
Date: Fri, 11 Mar 2022 09:17:49 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

{
    "accountId": 12345678,
    "value": 1000100.0
}
```

Get transactions
```shell
$ http localhost:8088/accounts/12345678/transactions
HTTP/1.1 200
Connection: keep-alive
Content-Type: application/json
Date: Fri, 11 Mar 2022 09:18:10 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

[
    {
        "amount": 100.0,
        "destination": 12345678,
        "source": 88888888,
        "timestamp": "2022-03-11T09:17:29.512205Z"
    },
    {
        "amount": 1000000.0,
        "destination": 12345678,
        "source": null,
        "timestamp": "2022-03-11T09:13:46.685775Z"
    }
]
```
