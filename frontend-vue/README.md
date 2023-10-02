# HelpMeAndYou: frontend
Progetto di Tecniche Avanzate di Sviluppo Software 2022/2023 di
Anna Fontana, Gianni Molinari e Fernando Serrano.

Frontend sviluppato con Vue.js.

---
## Note
Da settare per far funzionare il tutto:
- inserire le stesse categorie nel backend sia per requests help che per gestore credenziali. In entrambe inserire anche una categoria chiamata "Nessuna categoria"
- inserire almeno un magazzino con id=1 nel backend (città Torino meglio -- vd TODO in frontend)
- inserire almeno un conto con id=1 nel backend

---
## Esecuzione con Docker
Dopo aver eseguito il backend, per eseguire il frontend:
- **Aprire terminale ed entrare nella directory /frontend-vue**
- La prima volta lanciare il comando:
  ```
  docker-compose up
  ```
- Per eseguire il progetto lanciare il comando:
  ```
  docker-compose up
  ```
- Aprire il browser e andare su http://localhost:8080/

---
## Esecuzione senza docker
Dopo aver eseguito il backend, per eseguire il frontend:
- **Aprire terminale ed entrare nella directory /frontend-vue**
- La prima volta lanciare il comando:
  ```
  npm install
  ```
  (NB!!! Installare prima **Node.js** (versione 19.2)).
- Per compilare il progetto lanciare il comando:
  ```
  npm run build
  ```
- Per eseguire il progetto lanciare il comando:
  ```
  npm run serve
  ```
- Aprire il browser e andare su http://localhost:8080/

---
#### Prendere cose da bootstrap
https://getbootstrap.com/docs/5.3/getting-started/introduction/


### Note
In passato ho installato anche:
```
npm install vue bootstrap bootstrap-vue

npm install typed.js

npm i bootstrap-icons

npm install bootstrap

npm install vuex --save
```

---
Per il problema di login con Google, è un issue aperto su GitHub

https://github.com/devbaji/vue3-google-login/issues/40
