package de.faltfe.vacation.repositories;

import de.faltfe.vacation.entities.Person;
import de.faltfe.vacation.entities.VacationEntry;
import de.faltfe.vacation.entities.VacationQuota;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void testSaveVacation() {
        Person person = personRepository.findByName("plainperson").orElseThrow(AssertionError::new);
        VacationEntry vacation = new VacationEntry();
        vacation.setStartDate(LocalDate.now());
        vacation.setEndDate(vacation.getStartDate().plusDays(1));
        person.addVacation(vacation);
        Person savedPerson = personRepository.save(person);
        assertNotNull(savedPerson.getId());
        assertTrue(savedPerson.getVacations().stream().noneMatch(vacationEntry -> vacationEntry.getId() == null));
    }

    @Test
    void testVacationQuota() {
        Person person = personRepository.findByName("fullperson").orElseThrow(AssertionError::new);
        assertNotNull(person.getVacationQuota());

        personRepository.delete(person);
        assertTrue(personRepository.findById(person.getId()).isEmpty());

        TypedQuery<VacationQuota> quotaQuery = entityManager.createQuery(
                "SELECT vq from VacationQuota vq where vq.person.id = ?1",
                VacationQuota.class
        );
        quotaQuery.setParameter(1, person.getId());
        assertThrows(NoResultException.class, quotaQuery::getSingleResult);
    }

    @Test
    void testSaveAndDeleteVacationOrphaned() {
        VacationEntry vacation = new VacationEntry();
        Person person = personRepository.findById(1L).orElseThrow(AssertionError::new);
        person.addVacation(vacation);

        vacation.setStartDate(LocalDate.now());
        vacation.setEndDate(LocalDate.now());

        Person savedPerson = personRepository.saveAndFlush(person);

        Person personWithVacation = personRepository.findById(savedPerson.getId()).orElseThrow(AssertionError::new);
        assertEquals(1, personWithVacation.getVacations().size());
        assertTrue(personWithVacation.getVacations().stream().map(VacationEntry::getId).noneMatch(Objects::isNull));
        personWithVacation.getVacations().forEach(personWithVacation::removeVacation);
        personRepository.save(personWithVacation);

        Person personWithoutVacation = personRepository.findById(savedPerson.getId()).orElseThrow(AssertionError::new);
        assertTrue(personWithoutVacation.getVacations().isEmpty());

        TypedQuery<VacationEntry> query = entityManager.createQuery("Select v from VacationEntry v", VacationEntry.class);
        assertTrue(query.getResultList().isEmpty());
    }

}
