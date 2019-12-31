package net.smiguel.sample.app.sampleapp.controller;

import lombok.AllArgsConstructor;
import net.smiguel.sample.app.sampleapp.model.Person;
import net.smiguel.sample.app.sampleapp.service.PersonService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class HomeController {

    private PersonService personService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> getPerson() {
        return ResponseEntity.ok(personService.getPerson());
    }

}
