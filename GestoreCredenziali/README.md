# GestoreCredenziali: microservizio
Per eseguire con Kubernetes: stesse chiamate di sotto, ma sulla porta 30000.

---

### Esecuzione
Entrare da terminale nella directory GestoreCredenziali ed eseguire:
```
./mvnw -DskipTests=true clean package
docker-compose build
docker-compose up
```

---

### Test con Postman: comandi

- ### AccountController

#### GET
Mettere su postman get e:
- Per fare il login: mettere su postman get e all'indirizzo http://localhost:8085/api/utenti/login
andare su body, settare raw in formato json e inserire ad esempio:
	```
	{
		"email":"prova@prova",
		"password":123
	}
	```

#### POST
Mettere su postman post e:
- Per creare un account: all'indirizzo http://localhost:8085/api/utenti/registrazione/create/account
andare su body, settare raw in formato json e inserire ad esempio:

	```
	{
		"email":"prova@prova",
		"password":123,
		"nome":"prova",
		"cognome":"prova",
		"telefono":12344,
		"indirizzo":"viale prova",
		"stato":"da_approvare",
		"cv": null,
		"documento_identita": null
	}
	```

- Per settare "Documento d'identità" e "Cv" data un'email: all'indirizzo http://localhost:8085/api/utenti/registrazione/create/Documents
andare su body --> form-data --> inserire tra le Key:
  - "email" con Value una email già registrata
  - "doc" e inserire il file per il documento
  - "cv" con rispettivo file di curriculum per il curriculum



- ### AmministratoreController

#### GET
Mettere su postman get e:
- Per ritornare tutti gli utenti da approvare: inserire l'indirizzo http://localhost:8085/api/amministratore/da_approvare/list

- Per ritornare il cv di un utente: inserire l'indirizzo http://localhost:8085/api/amministratore/cv
  andare su Body -> inserire come parametro in form-data (alto sinistra): fileName (Key) e il nome del file salvato. Sappiamo il nome
  perchè quando viene salvato viene castato come emailaccount.estensione del file. noi accettiamo solo i pdf
  che blocchiamo da frontend. ad esempio se l'account è prova@prova allora il file sarà prova@prova.estensione

- Per ritornare il cv di un utente: inserire su postman l'indirizzo http://localhost:8085/api/amministratore/id
  e poi stessa cosa come per cv


#### PUT
Mettere su postman put e:
- Per approvare o bloccare un utente: all'indirizzo http://localhost:8085/api/amministratore/da_approvare/valuta/QUA_INSERISCI_ID_ACCOUNT_DA_APPROVARE
andare su body, settare raw in formato json e inserire ad esempio:
	```
	{
		"decisione": "approva"
	}
	```
	oppure
	
	```
	{
		"decisione": "blocca"
	}
 	```

- Per aggiornare la categoria di un utente: all'indirizzo http://localhost:8085/api/amministratore/aggiorna_categoria/INSERISCI_ID_ACCOUNT
andare su body, settare raw in formato json e inserire ad esempio:
	```
	{
		"categoria": "QUA_INSERISCI_CATEGORIA"
	}
	```

- Per bloccare un utente: inserire l'indirizzo http://localhost:8085/api/amministratore/blocca/INSERISCI_ID_ACCOUNT


---
- ### LoginController
L'ho lasciato in un controller a parte perché mi sembra più ordinato così, ma la porta a cui si interfaccia è la stessa/simile ad AccountController.

#### GET
- Per fare il login con Google: mettere su google indirizzo http://localhost:8085/api/utenti/oauth_login per restituire un Account
con le informazioni lette dal profilo Google dell'utente (http://localhost:8085/api/utenti/loginSuccess).

NB!!! No schermata consensi poiché l'app NON accede a contenuti sensibili.
