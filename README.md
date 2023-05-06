# HelpMeAndYou
Progetto di Tecniche Avanzate di Sviluppo Software 2022/2023 di 
Anna Fontana, Gianni Molinari e Fernando Serrano.

--Breve descrizione progetto--

## Esecuzione
- Aprire Docker

- Eseguire da terminale il comando:
	```
	docker run -d --hostname my-test-rabbit --name test-rabbit -p 15672:15672 -p 5672:5672 rabbitmq:3-management
	```

- Poi entrare da terminale nella directory gateway/EurekaServer ed eseguire:
	```
	./mvnw -DskipTests=true clean package
	docker-compose build
	docker-compose up
	```

- Poi entrare da un altro terminale nella directory gateway/ApiGateway ed eseguire:
	```
	./mvnw -DskipTests=true clean package
	docker-compose build
	docker-compose up
	```

- Infine entrare da un altro terminale nelle varie directory dei microservizi e per ognuno eseguire:
	```
	./mvnw -DskipTests=true clean package
	docker-compose build
	docker-compose up
	```
