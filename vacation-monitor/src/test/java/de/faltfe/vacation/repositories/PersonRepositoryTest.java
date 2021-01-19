package de.faltfe.vacation.repositories;

import de.faltfe.vacation.config.EnableVacationJpaTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@EnableVacationJpaTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    void testRepository() {
        assertNotNull(personRepository);
    }

}
