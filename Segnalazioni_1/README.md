# Segnalazioni: microservizio
Per eseguire con Kubernetes: stesse chiamate di sotto, ma sulla porta 30000.

---

### Esecuzione
Entrare da terminale nella directory Segnalazioni_1 ed eseguire:
```
./mvnw -DskipTests=true clean package
docker-compose build
docker-compose up
```

---

### Test con Postman: comandi

#### GET
Mettere su postman get e:
* Per prendere tutte le segnalazioni inserire l'indirizzo: http://localhost:8080/api/segnalazioni/segnalazione/list
* Per prendere una singola segnalazione inserire l'indirizzo: http://localhost:8080/api/segnalazioni/segnalazione/1 (1 è l'id della segnalazione, ma dipende dal vostro db, guardate il vostro caso)

#### POST
Mettere su postman post e all'indirizzo http://localhost:8080/api/segnalazioni/segnalazione/create
andare su body, settare raw in formato json e inserire ad esempio:
```
{
	"titolo": "prova",
	"descrizione": "prova descrizione" ,
	"tipologia": "prova tipologia" ,
	"creatore" :"1" ,
	"segnalato"  : "2"
}
```

#### DELETE
Mettere su postman delete e:
* Per eliminare una segnalazione inserire l'indirizzo: http://localhost:8080/api/segnalazioni/delete/1 (1 è l'id della richiesta)

A questo punto la richiesta con id 1 non ci sarà più nel database.
