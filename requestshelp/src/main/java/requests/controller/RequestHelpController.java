package requests.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import requests.model.Category;
import requests.model.Material;
import requests.model.RequestHelp;
import requests.model.User;
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
	public RequestHelp createRequest(@RequestBody RequestHelp request){
		System.out.println("post: " + request);

		RequestHelp fin = new RequestHelp(request.getDescription(), request.getDay(), request.getPlace());
		fin.setMaterials(request.getMaterials());
		fin.setPublishedUser(request.getPublishedUser());
		fin.setCategory(request.getCategory());
		System.out.println(fin);


		return repository.save(fin);
	}

	@PutMapping("/requests/{id}/state")
	public ResponseEntity<RequestHelp> updateRequestState(@PathVariable(value = "id") long requestId, @RequestBody String newStateRequest) {
		Optional <RequestHelp> request = repository.findById(requestId);
		System.out.println(request);
		System.out.println("/////////////////");
		System.out.println(newStateRequest);
		System.out.println("////////////////");

		if (request.isPresent()) {
			RequestHelp req = request.get();
			System.out.println(req);
			req.setState(newStateRequest);

			return new ResponseEntity<>(repository.save(req), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/requests/{id}/materials")
	public ResponseEntity<RequestHelp> updateRequestMaterials(@PathVariable(value = "id") long requestId, @RequestBody List<Material> materials) {
		Optional <RequestHelp> request = repository.findById(requestId);

		if (request.isPresent()) {
			RequestHelp req = request.get();
			req.addMaterials(materials);

			return new ResponseEntity<>(repository.save(req), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// non so se servirà eliminare una richiesta, ma nel dubbio l'ho inserita
	@DeleteMapping("/requests/{id}")
	public Map<String, Boolean> deleteUser(@PathVariable(value = "id") long requestId) throws Exception {
		RequestHelp request = repository.findById(requestId).orElseThrow(() -> new ResourceNotFoundException("Request not found with id: " + requestId));

		repository.delete(request);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
