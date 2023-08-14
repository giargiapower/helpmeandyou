# Richieste di aiuto: microservizio
Per eseguire con Kubernetes: stesse chiamate di sotto, ma sulla porta 30000.

---

### Esecuzione
Entrare da terminale nella directory requesthelp ed eseguire:
```
./mvnw -DskipTests=true clean package
docker-compose build
docker-compose up
```

---

### Test con Postman: comandi

#### GET
Mettere su postman get e:
- Per prendere tutte le richieste di aiuto inserire l'indirizzo: http://localhost:8080/api/richiesteaiuto/richieste

- Per prendere una singola richiesta di aiuto inserire l'indirizzo: http://localhost:8080/api/richiesteaiuto/richiesta/1 (1 è l'id della richiesta, ma dipende dal vostro db, guardate il vostro caso)

- ***TODO:*** Per prendere tutte le categorie inserire l'indirizzo: http://localhost:8080/api/richiesteaiuto/categorie

#### POST
In un primo momento assicurarsi che ci siano almeno un account e una categoria nel database:
- Per creare una categoria mettere su postman POST e all'indirizzo: http://localhost:8080/api/richiesteaiuto/categoria/crea andare su body, settare raw in formato json e inserire ad esempio:
```
{
	"tipo" : "infermieristica"
}
```
- Per l'account invece farlo dall'amministratore_controller

A questo punto mettere su postman post e all'indirizzo http://localhost:8080/api/richiesteaiuto/richiesta/crea
andare su body, settare raw in formato json e inserire ad esempio:
```
{
    "descrizione" : "aiuto per andare a fare una visita medica per nonna pina",
    "giorno" : "2023-05-15",
    "indirizzo" : {
        "regione" : "Piemonte",
        "provincia" : "Torino",
        "citta" : "Venaria Reale",
        "indirizzo" : "Via Pessinetto 12"
    },
    "idMateriale" : 1,
    "pubAccount" : { "id" : 1 },
    "categoria" : { "tipo" : "infermieristica" }
}
```


#### PUT
- Mettere su postman put con indirizzo http://localhost:8080/api/richiesteaiuto/richiesta/accetta/1/1 (id della richiesta, id account che accetta la richiesta).
A questo punto la richiesta con id 1 avrà lo stato modificato a "accettata"

- Mettere su postman put con indirizzo http://localhost:8080/api/richiesteaiuto/richiesta/termina/1 (1 è l'id della richiesta).
A questo punto la richiesta con id 1 avrà lo stato modificato a "terminata".


#### DELETE
Mettere su postman delete e:
* Per eliminare una richiesta di aiuto inserire l'indirizzo: http://localhost:8080/api/richiesteaiuto/richiesta/elimina/1 (1 è l'id della richiesta)

A questo punto la richiesta con id 1 non ci sarà più nel database.
