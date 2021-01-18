package de.faltfe.vacation.services;

import de.faltfe.vacation.VacationTestConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = VacationTestConfiguration.class)
@DirtiesContext
class VacationServiceIT {

    @Autowired
    private VacationService vacationService;

    @Test
    void vacationServiceTest() {
        assertNotNull(vacationService);
    }
}
