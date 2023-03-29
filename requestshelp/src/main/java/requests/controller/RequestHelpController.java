package requests.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import requests.model.RequestHelp;
import requests.repository.RequestHelpRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class RequestHelpController {

    @Autowired
    RequestHelpRepository repository;

    @GetMapping("/request/{id}")
    public RequestHelp getRequestById(@PathVariable(value = "id") long id) {
        Optional<RequestHelp> request = repository.findById(id);
        return request.orElse(null);
    }

    @GetMapping("/requests")
    public List<RequestHelp> getAllRequests() {
        List <RequestHelp> requests = new ArrayList<>();
        repository.findAll().forEach(requests::add);
        return requests;
    }
}
