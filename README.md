# Statistics REST API

This project calculates real time statistics from transactions received in the last 60 seconds. There's two APIs, one called every time a transaction is made, and other to return the statistics of transactions of the last 60 seconds.

`POST /transactions` for each new transaction with body:

```$json
{
  "amount": 123.45,
  "timestamp": 1529208512
}
```

Where:

- `amount` is the transaction amount
- `timestamp` is the transaction time in Unix Timestamp on UTC time zone

Returns an empty body with either:

- `201` - in case of success
- `204` - if transaction is older than 60 seconds

`GET /​statistics`

Returns:

```$json
{
  "sum": 0,
  "avg": 0,
  "max": 0,
  "min": 0,
  "count": 0
}
```

Where:

- `sum` is the total sum of transaction value in the last 60 seconds
- `avg` is the average amount of transaction value in the last 60 seconds
- `max` is the single highest transaction value in the last 60 seconds
- `min` is the single lowest transaction value in the last 60 seconds
- `count` is the total number of transactions happened in the last 60 seconds

## Build and test

To build and execute unit and integration tests, execute:

`mvn clean install`

