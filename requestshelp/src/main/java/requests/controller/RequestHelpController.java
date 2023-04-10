package requests.controller;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import requests.model.RequestHelp;
import requests.repository.RequestHelpRepository;
import java.util.*;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class RequestHelpController {

	@Autowired
	RequestHelpRepository repository;

	@GetMapping("/requests/{id}")
	public RequestHelp getRequestById(@PathVariable(value = "id") long requestId) {
		Optional <RequestHelp> request = repository.findById(requestId);
		return request.orElse(null);
	}

	@GetMapping("/requests")
	public List<RequestHelp> getAllRequests() {
		List <RequestHelp> requests = new ArrayList<>();
		repository.findAll().forEach(requests::add);
		return requests;
	}

	@PostMapping("/requests")
	public RequestHelp createRequest(@RequestBody RequestHelp requestHelp){
		RequestHelp fin = new RequestHelp(requestHelp.getDescription(), requestHelp.getDay(), requestHelp.getPlace());
		fin.setPublishedUser(requestHelp.getPublishedUser());
		fin.setCategory(requestHelp.getCategory());
		return repository.save(fin);
	}

	@PutMapping("/requests/accept/{id}")
	public ResponseEntity<RequestHelp> acceptRequest(@PathVariable(value = "id") long requestId, @RequestBody RequestHelp requestHelp) {
		Optional <RequestHelp> request = repository.findById(requestId);

		if (request.isPresent()) {
			RequestHelp existingRequest = request.get();
			existingRequest.setAcceptedUser(requestHelp.getAcceptedUser());
			existingRequest.setMaterials(requestHelp.getMaterials());
			return new ResponseEntity<>(repository.save(existingRequest), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/requests/terminate/{id}")
	public ResponseEntity<RequestHelp> terminateRequest(@PathVariable(value = "id") long requestId) {
		Optional <RequestHelp> request = repository.findById(requestId);

		if (request.isPresent()) {
			RequestHelp existingRequest = request.get();
			existingRequest.setState("terminated");
			return new ResponseEntity<>(repository.save(existingRequest), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// non so se servirà eliminare una richiesta, ma nel dubbio l'ho inserita
	// SERVE NEL CASO VENGA BLOCCATO UN UTENTE A FRONTE DI SEGNALAZIONE
	@DeleteMapping("/requests/{id}")
	public Map<String, Boolean> deleteUser(@PathVariable(value = "id") long requestId) throws Exception {
		RequestHelp request = repository.findById(requestId).orElseThrow(() -> new ResourceNotFoundException("Request not found with id: " + requestId));

		repository.delete(request);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
