package de.faltfe.vacation.services;

import de.faltfe.vacation.entities.Person;
import de.faltfe.vacation.entities.VacationEntry;
import de.faltfe.vacation.repositories.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VacationService {

    private final PersonRepository personRepository;

    public VacationService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Set<VacationEntry> getVacations(Long personId) {
        return personRepository.findById(personId)
                               .map(Person::getVacations)
                               .orElseGet(Collections::emptySet);
    }

    public void addVacation(Long personId, VacationEntry vacationEntry) {
        // TODO Remove or extent vacation entry if dates collide
        addVacations(personId, List.of(vacationEntry));
    }

    public void addVacations(Long personId, Collection<VacationEntry> vacationEntries) {
        personRepository.findById(personId)
                        .ifPresent(person -> {
                            vacationEntries.stream()
                                           .filter(vacation -> vacation.getPerson().getId().equals(personId))
                                           .forEach(person::addVacation);
                            personRepository.save(person);
                        });
    }

    public void removeVacation(Long personId, VacationEntry vacationEntry) {
        removeVcations(personId, List.of(vacationEntry));
    }

    public void removeVcations(Long personId, Collection<VacationEntry> vacationEntries) {
        personRepository.findById(personId)
                        .ifPresent(person -> {
                            vacationEntries.stream()
                                           .filter(vacation -> vacation.getPerson().getId().equals(personId))
                                           .forEach(person::removeVacation);
                            personRepository.save(person);
                        });
    }

    public void updateVacation(Long personId, VacationEntry updatedEntry) {
        updateVacations(personId, List.of(updatedEntry));
    }

    public void updateVacations(Long personId, Collection<VacationEntry> vacationEntries) {
        personRepository.findById(personId)
                        .ifPresent(person -> {
                            vacationEntries.stream()
                                           .filter(vacation -> Objects.nonNull(vacation.getId()))
                                           .filter(vacation -> vacation.getPerson().getId().equals(personId))
                                           .forEach(vacation -> {
                                               person.removeVacation(vacation);
                                               person.addVacation(vacation);
                                           });
                            personRepository.save(person);
                        });
    }
}
