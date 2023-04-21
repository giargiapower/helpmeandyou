# Magazzino: microservizio

### Esecuzione
Run della classe MagazzinoApplication

### Test con Postman: comandi

#### GET
Mettere su postman get e:
* Per prendere tutti magazzini inserire l'indirizzo: http://localhost:5434/api/magazzini/allMagazzini
---
Fare in un primo momento questa query sul database PgAdmin:
```
INSERT INTO magazzino(id, luogo) VALUES (1, 'Torino')
```
* Per prendere tutte le informazioni di un magazzino inserire l'indirizzo: http://localhost:5434/api/magazzini/1 (1 è l'id della richiesta)


#### POST
Mettere su postman post e all'indirizzo http://localhost:8080/api/magazzini/create
andare su body, settare raw in formato json e inserire ad esempio:

```
{
"luogo": "Verona"
}
```

---

Mettere su postman post e all'indirizzo http://localhost:8080/api/magazzini/create/1/tagliaerba


#### DELETE
Mettere su postman post e all'indirizzo http://localhost:8080/api/magazzini/delete/1/tagliaerba

