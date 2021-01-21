package de.faltfe.vacation.services;

import de.faltfe.vacation.entities.Person;
import de.faltfe.vacation.repositories.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getPersons() {
        return personRepository.findAll();
    }

    public Person save(Person person) {
        return personRepository.save(person);
    };
}
