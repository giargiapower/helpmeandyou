# Richieste di aiuto: microservizio

### Esecuzione
Run della classe RequestsHelp

### Test con Postman: comandi

#### GET
Mettere su postman get e:
* Per prendere tutte le richieste di aiuto inserire l'indirizzo: http://localhost:8080/api/requests

* Per prendere una singola richiesta di aiuto inserire l'indirizzo: http://localhost:8080/api/requests/1 (1 è l'id della richiesta, ma dipende dal vostro db, guardate il vostro caso)



#### POST
Fare in un primo momento queste query sul database PgAdmin:
```
insert into material (id, name)
values (1, 'carrozzina')
```
```
insert into material (id, name)
values (2, 'stampelle')
```
```
insert into users (id, name)
values (1, 'gigi')
```
```
insert into category (type)
values ('infermieristica')
```

Mettere su postman post e all'indirizzo http://localhost:8080/api/requests
andare su body , settare raw in formato json e inserire ad esempio:
```
{
    "description" : "aiuto per andare a fare una visita medica per nonna pina",
    "day" : "2023-05-15",
    "place" : "via amati 15, Torino",
    "materials" : [
        {
            "id" : 1
        },
        {
            "id" : 2
        }
    ],    
    "publishedUser" : { "id" : 1 },
    "category" : { "type" : "infermieristica" }
}
```

#### PUT
Mettere su postman put e all'indirizzo http://localhost:8080/api/requests/1/state (1 è l'id della richiesta)
andare su body , settare raw in formato json e inserire ad esempio:
```
{  
    "state": "accepted"
}
```
A questo punto la richiesta con id 1 avrà lo stato modificato a "accepted"


#### DELETE
Mettere su postman delete e:
* Per eliminare una richiesta di aiuto inserire l'indirizzo: http://localhost:8080/api/requests/1 (1 è l'id della richiesta)

A questo punto la richiesta con id 1 non ci sarà più nel database.
