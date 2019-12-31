package net.smiguel.sample.app.sampleapp.service;

import net.smiguel.sample.app.sampleapp.model.Person;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    /**
     * Sample Method
     *
     * @return
     */
    public Person getPerson() {
        return Person
                .builder()
                .build();
    }
}
