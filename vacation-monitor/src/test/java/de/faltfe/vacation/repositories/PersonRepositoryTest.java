package de.faltfe.vacation.repositories;

import de.faltfe.vacation.config.EnableVacationJpaTest;
import de.faltfe.vacation.entities.Person;
import de.faltfe.vacation.entities.VacationEntry;
import de.faltfe.vacation.entities.VacationQuota;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@EnableVacationJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
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

}
