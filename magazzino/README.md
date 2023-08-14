# Magazzino: microservizio
Per eseguire con Kubernetes: stesse chiamate di sotto, ma sulla porta 30000.

---

### Esecuzione
Entrare da terminale nella directory magazzino ed eseguire:
```
./mvnw -DskipTests=true clean package
docker-compose build
docker-compose up
```

---

### Test con Postman: comandi

#### GET
Mettere su postman get e:
- Per prendere tutti magazzini: mettere su postman get e inserire l'indirizzo http://localhost:8080/api/magazzini/magazzini
---
Fare in un primo momento questa query sul database PgAdmin:
```
INSERT INTO magazzino(id, citta, indirizzo, provincia, regione) VALUES (1, 'Torino', 'Via Pessinetto 12', 'Torino', 'Piemonte')
```
- Per prendere tutte le informazioni di un magazzino: mettere su postman get e inserire l'indirizzo http://localhost:8080/api/magazzini/1 (1 è l'id della richiesta)

- Per prendere tutti i materiali di un magazzino dato il suo id: mettere su postman get e inserire l'indirizzo http://localhost:8080/api/magazzini/1/materiali (1 è l'id del magazzino)

- Per prendere tutti i materiali disponibili data una provincia e un id: mettere su postman get e inserire l'indirizzo http://localhost:8080/api/magazzini/materiali/Torino/2024-04-29


#### POST
Mettere su postman post e all'indirizzo http://localhost:8080/api/magazzini/create
andare su body, settare raw in formato json e inserire ad esempio:

```
{
    "regione": "Veneto",
    "provincia": "Verona",
    "citta": "Verona",
    "indirizzo": "Via Gigi 25"
}
```


#### PUT
- Mettere su postman put all'indirizzo http://localhost:8080/api/magazzini/create/1/tutore

---
Fare in un primo momento questa query sul database PgAdmin:
```
INSERT INTO materiale(id, nome, magazzino_id) VALUES (1, 'stampelle', 1)
```
- Mettere su postman put e all'indirizzo http://localhost:8080/api/magazzini/aggiungi/1/richiesta
  andare su body, settare raw in formato json e inserire ad esempio:

```
{
    "giorno": "2024-04-30",
    "provincia": "Firenze"
}
```


#### DELETE
- Per eliminare un materiale da un magazzino: mettere su postman delete e all'indirizzo http://localhost:8080/api/magazzini/elimina/1/2 (1: id magazzino, 2: id materiale)

- Per eliminare un magazzino: mettere su postman delete e all'indirizzo http://localhost:8080/api/magazzini/elimina/1

- Per eliminare tutti i magazzini: mettere su postman delete e all'indirizzo http://localhost:8080/api/magazzini/elimina

- Per eliminare una richiesta di aiuto: mettere su postman delete e all'indirizzo http://localhost:8080/api/magazzini/elimina/richiesta/1
