# Banca: microservizio
Per eseguire con Kubernetes: stesse chiamate di sotto, ma sulla porta 32000.

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
- Per prendere un singolo conto inserire l'indirizzo: http://localhost:8080/api/v1/conto/1 (id conto)

- Per prendere tutti i conti inserire l'indirizzo: http://localhost:8080/api/v1/conti


#### POST
- Per creare un conto: mettere su postman post e all'indirizzo http://localhost:8080/api/v1/conto/create
andare su body, settare raw in formato json e inserire ad esempio:
```
{  
	"saldo": 123.33
}
```


#### PUT
- Per aggiungere saldo al conto: mettere su postman put con indirizzo http://localhost:8080/api/v1/conto/add/1 (id conto)
andare su body, settare raw in formato json e inserire ad esempio:
```
{  
	"saldo": 123.33
}
```
a quel punto il saldo del conto con id 1 sarà aumentato a 246.66 (123.33 + 123.33).
