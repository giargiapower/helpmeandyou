# Segnalazioni: microservizio

### Esecuzione
Entrare da terminale nella directory Segnalazioni_1 ed eseguire:
```
./mvnw -DskipTests=true clean package
docker-compose build
docker-compose up
```