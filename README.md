# Hogwarts5 - Test

## Service test
Der er tilføjet test til alle nye service metoder.

## Controller
Controllerne testes via postman.

### `POST /prefects`
- Gør en student til prefect
- Request body
```json
{
  "id": 10
}
```

### `GET /prefects/:id`
- Returnerer en prefect udfra student id.
- Ellers returneres `status 404` med fejlbesked.

### `GET /prefects`
- Returnerer en liste over alle prefects.

### `GET /prefects/house/{house}`
- Returnerer en liste over alle prefects for et specifikt house.

### ´DELETE /prefects/:id´
- Fjener prefect fra en student og returnerer et student objekt

### `PATCH /students/{id}`
- Tilføjer/fjerner prefect fra en student
- Request body
```json
{
  "prefect": true
}
```
