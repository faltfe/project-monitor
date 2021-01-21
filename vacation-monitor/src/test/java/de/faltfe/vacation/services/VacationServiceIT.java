package de.faltfe.vacation.services;

import de.faltfe.vacation.entities.Person;
import de.faltfe.vacation.entities.VacationEntry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DirtiesContext
class VacationServiceIT {

    @Autowired
    private VacationService vacationService;

    @Autowired(required = false)
    private PersonService personService;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    void vacationServiceTest() {
        Person person = personService.getPersons()
                                     .stream()
                                     .filter(p -> p.getId() == 1L)
                                     .findFirst()
                                     .orElseThrow(AssertionError::new);
        assertNotNull(person);

        VacationEntry vacation = new VacationEntry();
        vacation.setStartDate(LocalDate.now());
        vacation.setEndDate(LocalDate.now());

        vacationService.addVacation(person.getId(), vacation);

        Person person2 = personService.getPersons()
                                      .stream()
                                      .filter(p -> p.getId() == 1L)
                                      .findFirst()
                                      .orElseThrow(AssertionError::new);
        assertTrue(person2.getVacations().stream().map(VacationEntry::getId).noneMatch(Objects::isNull));

//        Set<VacationEntry> vacations = vacationService.getVacations(person.getId());
//        VacationEntry vacationToDelete = vacations.stream()
//                                                  .filter(v -> v.getPerson().getId().equals(person.getId()))
//                                                  .findFirst()
//                                                  .orElseThrow(AssertionError::new);
//        vacationService.removeVacation(person.getId(), vacationToDelete);
//
//        TypedQuery<Person> personQuery = entityManager.createQuery("Select p from Person p where p.id = 1", Person.class);
//        assertTrue(personQuery.getResultList().isEmpty());
//
//        TypedQuery<VacationEntry> query2 = entityManager.createQuery("Select v from VacationEntry v",
//                                                                     VacationEntry.class
//        );
//        assertTrue(query2.getResultList().isEmpty());
    }
}
