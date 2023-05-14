# HelpMeAndYou
Progetto di Tecniche Avanzate di Sviluppo Software 2022/2023 di 
Anna Fontana, Gianni Molinari e Fernando Serrano.

--Breve descrizione progetto--

## Esecuzione con Kubernetes
- installare kubectl (https://kubernetes.io/docs/tasks/tools/install-kubectl/)
- Aprire **Docker Desktop**
- Attivare nelle impostazioni **Kubernetes**
- Aprire terminale ed entrare nella directory /helpmeandyou
- lanciare lo script col comando :
  ```
  kubectl apply -f .\deployment.yaml
  ```
- verranno fatti partire tutti i pod e i servizi necessari
- per verificare che i pod siano partiti correttamente lanciare il comando:
```
  kubectl get pods
```

- per terminare i pod usare il comando : 
  ```
   kubectl scale deployment --all --replicas=0
  
  ```

## NB!!!
- tutte le chiamate vanno fatte sulla porta 30000
- io ho utilizzato il node docker-desktop per far partire i pod, non dovrebbero esserci problemi nel caso usaste minikube
- se volete mettere usare docker-desktop o eliminate miikube o cambiate il node in cui far partire i pod

## per anna
consentire il login di google con le porte 8085, 30000 e 9000



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

- Infine **per avviare i vari microservizi** entrare da un altro terminale nelle varie directory dei microservizi e per ognuno eseguire:
	```
	./mvnw -DskipTests=true clean package
	docker-compose build
	docker-compose up
	```

## Terminare l'esecuzione
- Per arrestare RabbitMQ scrivere a terminale:
	```
	docker stop test-rabbit
	```
- Per arrestare i microservizi, l'Api Gateway e l'Eureka Server basta premere Ctrl+C nei rispettivi terminali.

In alternativa, è possibile arrestare tutti i container Docker dall'applicazione **Docker Desktop**.


----
**NB!!!**
Per eliminare tutte le immagini Docker che sono null, eseguire da terminale:
```
docker rmi $(docker images --filter "dangling=true" -q --no-trunc)
```
