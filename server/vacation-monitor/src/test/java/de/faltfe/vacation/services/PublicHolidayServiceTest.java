package de.faltfe.vacation.services;

import de.faltfe.vacation.entities.FederalState;
import de.faltfe.vacation.entities.PublicHoliday;
import de.faltfe.vacation.repositories.PublicHolidayRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PublicHolidayServiceTest {

    @InjectMocks
    private PublicHolidayService publicHolidayService;

    @Mock
    private PublicHolidayRepository publicHolidayRepository;

    @Test
    void getPublicHolidays() {
        LocalDate localDate = LocalDate.now();
        publicHolidayService.getPublicHolidays(localDate, localDate);
        verify(publicHolidayRepository).findAllByDateBetweenAndFederalStateIsNull(localDate, localDate);
    }

    @Test
    void getPublicHolidaysWithFederalState() {
        LocalDate from = LocalDate.now().with(TemporalAdjusters.lastDayOfYear());
        LocalDate to = from.with(TemporalAdjusters.lastDayOfYear());
        when(publicHolidayRepository.findAllByDateBetweenAndFederalState(from, to, FederalState.NI)).thenReturn(prepareHolidays());
        List<PublicHoliday> holidays = publicHolidayService.getPublicHolidays(from, to, FederalState.NI);
        assertEquals(prepareHolidays().size(), holidays.size());
    }

    @Test
    void getFederalStateHolidays() {
        LocalDate from = LocalDate.now().with(TemporalAdjusters.lastDayOfYear());
        LocalDate to = from.with(TemporalAdjusters.lastDayOfYear());
        when(publicHolidayRepository.findAllByDateBetweenAndFederalState(from, to, FederalState.NI)).thenReturn(prepareHolidays());
        List<PublicHoliday> holidays = publicHolidayService.getFederalStateHolidays(from, to, FederalState.NI);
        assertTrue(holidays.stream().map(PublicHoliday::getFederalState).anyMatch(Objects::isNull));
    }

    private List<PublicHoliday> prepareHolidays() {
        PublicHoliday noFederalState = new PublicHoliday();
        noFederalState.setDate(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()));

        PublicHoliday withFederalState = new PublicHoliday();
        withFederalState.setDate(LocalDate.now().minusDays(1));
        withFederalState.setFederalState(FederalState.NI);

        return List.of(noFederalState, withFederalState);
    }


}