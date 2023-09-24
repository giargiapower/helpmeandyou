# HelpMeAndYou: backend

Progetto di Tecniche Avanzate di Sviluppo Software 2022/2023 di
Anna Fontana, Gianni Molinari e Fernando Serrano.

--Breve descrizione progetto--

---

## TODO:

- disponibilità materiali / richieste da sistemare
+ blocca utente + notifica in tempo reale da sistemare
+ settaggio documenti su restart da sistemare

---

## Esecuzione con Kubernetes

- Installare kubectl (https://kubernetes.io/docs/tasks/tools/install-kubectl/)
- Aprire **Docker Desktop**
- Andare nelle impostazioni -> **Kubernetes** -> spuntare **Enable Kubernetes**
- Aprire terminale ed entrare nella directory /helpmeandyou
- **Lanciare lo script** col comando:
  ```
  kubectl apply -f .\deployment.yaml
  ```
- Aprire il terminare ed entrare nella directory /helpmeandyou/demo
- **Lanciare lo script** col comando:
  ```
  kubectl apply -f .\kubernetes-manifests.yaml
  ```
- Verranno fatti partire tutti i pod e i servizi necessari.
	- Per verificare che i pod siano partiti correttamente lanciare il comando:
	  ```
		kubectl get pods
	  ```
	  oppure ```watch 'kubectl get pods'``` per vederli in tempo reale
	- Per verificare che i servizi siano partiti correttamente lanciare il comando:
	  ```
		kubectl get services
	  ```
- Per **terminare**:
	- **i pod** usare il comando:
	  ```
	  kubectl delete deployment --all
	  ```
	  oppure:```kubectl scale deployment --all --replicas=0```
	- **i servizi** usare il comando:
	  ```
	  kubectl delete services --all
	  ```

---

- Per **testare RabbitMQ**:
	- esporre la porta 15672 del pod di RabbitMQ con il comando:
	  ```
	  kubectl port-forward <nome_pod_rabbitmq> 15672:15672
	  ```
	  (Per sapere il nome del pod di RabbitMQ lanciare il comando ```kubectl get pods```)
	- andare su http://localhost:15672/ e loggarsi con le credenziali di default (user: guest, password: guest)
	- si dovrebbe vedere una coda (richieste - account)


- Per accedere al **servizio di Eureka Server**:
	- esporre la porta 8761 del pod di Eureka Server con il comando:
  ```
    kubectl port-forward <nome_pod_eurekaserver> 8761:8761
    ```
  (Per sapere il nome del pod dell'EurekaServer lanciare il comando ```kubectl get pods```)
	- andare su http://localhost:8761/
	- si dovrebbe vedere la pagina di Eureka Server con i vari microservizi registrati (API-Gateway,
	  Gestore-Credenziali, Magazzino, Richieste_aiuto, Segnalazioni)

---

- Per **testare il contenuto dei database su kubernetes**:
	- da terminale scrivere il comando:
  ```
  kubectl exec -it <nome_pod_dbpostgresql...> -- psql -U postgres
  ```
  (Per sapere il nome del pod del db lanciare il comando ```kubectl get pods```)
	- fare la query desiderata (es. ```SELECT * FROM public.segnalazione WHERE id = 1;```)
	- per vedere tutte le tabelle del db fare ```\dt```
	- per vedere i tipi di campi di una tabella fare ```\d <nome_tabella>```

---

## NB!!!

- ***Tutte le chiamate vanno fatte sulla porta 30000*** (
  es. http://localhost:30000/api/amministratore/da_approvare/list)
- *Per vedere i pod in tempo reale eseguire su UBUNTU il comando:*
  ```
  watch 'kubectl get pods'
  ```
- *Se compaiono errori strani eseguire il comando seguente:*
  ```
  kubectl delete pvc --all
  ```
- Noi abbiamo utilizzato il node docker-desktop per far partire i pod, non dovrebbero esserci problemi nel caso si usi
  minikube
- Se si vuole usare docker-desktop o eliminare minikube o cambiare il node in cui far partire i pod
- ***SE SI FANNO MODIFICHE AL CODICE BISOGNA RICOMPILARE I DOCKERFILE E RICREARE LE IMMAGINI DOCKER***
	- Per ricompilare i dockerfile e ricreare le immagini docker:
		- entrare nella directory che vogliamo buildare
        - eseguire il comando:
          ```
          ./mvnw -DskipTests=true clean package
          
          docker build -t giargiapower/helpmeandyou:nome_immagine .
          
          docker push giargiapower/helpmeandyou:nome_immagine
		  ```
    - ***Per gli altri ricordarsi poi di fare le PULL delle nuove immagini docker (anche da Docker Desktop volendo)***

---

## Esecuzione senza Kubernetes

- Aprire **Docker Desktop**
- Inizialmente **per avviare RabbitMQ**: la prima volta eseguire da terminale il comando:
  ```
  docker run -d --hostname my-test-rabbit --name test-rabbit -p 15672:15672 -p 5672:5672 rabbitmq:3-management
  ```
  In alternativa, quando il container "my-test-rabbit" già esiste, per avviarlo basta eseguire:
  ```
  docker start test-rabbit
  ```

- Poi **per avviare l'Eureka Server** entrare da terminale nella directory gateway/EurekaServer ed eseguire:
  ```
  ./mvnw -DskipTests=true clean package
  docker-compose build
  docker-compose up
  ```

- Poi **per avviare l'Api Gateway** entrare da un altro terminale nella directory gateway/ApiGateway ed eseguire:
  ```
  ./mvnw -DskipTests=true clean package
  docker-compose build
  docker-compose up
  ```

- Infine **per avviare i vari microservizi** entrare da un altro terminale nelle varie directory dei microservizi e per
  ognuno eseguire:
  ```
  ./mvnw -DskipTests=true clean package
  docker-compose build
  docker-compose up
  ```

### Terminare l'esecuzione

- Per arrestare RabbitMQ scrivere a terminale:
  ```
  docker stop test-rabbit
  ```
- Per arrestare i microservizi, l'Api Gateway e l'Eureka Server basta premere Ctrl+C nei rispettivi terminali.

In alternativa, è possibile arrestare tutti i container Docker dall'applicazione **Docker Desktop**.

### **NB!!!**

Per eliminare tutte le immagini Docker che sono null, eseguire da terminale:

```
docker rmi $(docker images --filter "dangling=true" -q --no-trunc)
```
