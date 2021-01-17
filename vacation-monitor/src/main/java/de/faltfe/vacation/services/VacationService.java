package de.faltfe.vacation.services;

import de.faltfe.vacation.entities.Person;
import de.faltfe.vacation.entities.VacationEntry;
import de.faltfe.vacation.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
public class VacationService {

    @Autowired
    private PersonRepository personRepository;

    public Set<VacationEntry> getVacations(Long personId) {
        return personRepository.findById(personId)
                               .map(Person::getVacations)
                               .orElseGet(Collections::emptySet);
    }

    public void addVacation(Long personId, VacationEntry vacationEntry) {
        // TODO Remove or extent vacation entry if dates collide
        personRepository.findById(personId)
                        .ifPresent(person -> {
                            person.addVacation(vacationEntry);
                            personRepository.save(person);
                        });
    }

    public void removeVacation(Long personId, VacationEntry vacationEntry) {
        personRepository.findById(personId)
                        .ifPresent(person -> {
                            person.removeVacation(vacationEntry);
                            personRepository.save(person);
                        });
    }

    public void updateVacation(Long personId, VacationEntry updatedEntry) {
        personRepository.findById(personId)
                        .ifPresent(person -> person.getVacations()
                                                   .stream()
                                                   .filter(vacation -> vacation.getId().equals(updatedEntry.getId()))
                                                   .findFirst()
                                                   .ifPresent(vacation -> {
                                                       person.removeVacation(vacation);
                                                       person.addVacation(updatedEntry);
                                                       personRepository.save(person);
                                                   }));
    }
}
