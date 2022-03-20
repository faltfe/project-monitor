package de.faltfe.vacation.repositories;

import de.faltfe.vacation.entities.FederalState;
import de.faltfe.vacation.entities.PublicHoliday;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PublicHolidayRepositoryTest {

    @Autowired
    private PublicHolidayRepository publicHolidayRepository;

    @Test
    void findAllByDateBetweenAndFederalStateIsNull() {
        LocalDate from = LocalDate.of(2019, Month.JANUARY, 1);
        LocalDate to = from.with(TemporalAdjusters.lastDayOfYear());
        List<PublicHoliday> publicHolidays = publicHolidayRepository.findAllByDateBetweenAndFederalStateIsNull(from, to);
        assertTrue(publicHolidays.stream().map(PublicHoliday::getFederalState).anyMatch(Objects::isNull));
    }

    @Test
    void findAllByDateBetweenAndFederalState() {
        LocalDate from = LocalDate.of(2019, Month.JANUARY, 1);
        LocalDate to = from.with(TemporalAdjusters.lastDayOfYear());
        assertEquals(1, publicHolidayRepository.findAllByDateBetweenAndFederalState(from, to, FederalState.NI).size());
    }

}