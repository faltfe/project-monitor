package de.faltfe.vacation.services;

import de.faltfe.vacation.entities.*;
import de.faltfe.vacation.exceptions.PersonNotFoundException;
import de.faltfe.vacation.repositories.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VacationServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PublicHolidayService publicHolidayService;

    @Spy
    private Person person;

    @Spy
    private Company company;

    @Spy
    private VacationEntry vacationEntry;

    @Captor
    private ArgumentCaptor<Person> personCaptor;

    @Captor
    private ArgumentCaptor<VacationEntry> vacationCaptor;

    @Spy
    @InjectMocks
    private VacationService vacationService;

    @BeforeEach
    void setUp() {
        company.setFederalState(FederalState.NI);
    }

    @Test
    void getVacationsEmptySet() {
        when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));
        assertTrue(vacationService.getVacations(person.getId()).isEmpty());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = 1)
    void getVacationsPersonNotFound(Long personId) {
        when(personRepository.findById(personId)).thenReturn(Optional.empty());
        assertTrue(vacationService.getVacations(personId).isEmpty());
    }

    @Test
    void getVacations() {
        person.addVacation(vacationEntry);
        when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));
        Set<VacationEntry> vacationEntries = vacationService.getVacations(person.getId());
        assertTrue(vacationEntries.contains(vacationEntry));
    }

    @Test
    void addVacation() {
        when(person.getId()).thenReturn(1L);
        when(personRepository.findById(anyLong())).thenReturn(Optional.of(person));
        vacationEntry.setPerson(person);
        vacationService.addVacation(person.getId(), vacationEntry);
        verify(person).addVacation(vacationCaptor.capture());
        verify(personRepository).save(personCaptor.capture());
        assertNotNull(vacationCaptor.getValue().getPerson());
        assertFalse(personCaptor.getValue().getVacations().isEmpty());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = 1)
    void addVacationNoPersonFound(Long personId) {
        when(person.getId()).thenReturn(personId);
        when(personRepository.findById(any())).thenReturn(Optional.empty());
        vacationService.addVacation(person.getId(), vacationEntry);
        verify(person, never()).addVacation(any(VacationEntry.class));
        verifyNoMoreInteractions(personRepository);
    }

    @Test
    void removeVacation() {
        when(person.getId()).thenReturn(1L);
        when(personRepository.findById(anyLong())).thenReturn(Optional.of(person));
        vacationEntry.setPerson(person);
        vacationService.removeVacation(person.getId(), vacationEntry);
        verify(person).removeVacation(vacationCaptor.capture());
        verify(personRepository).save(personCaptor.capture());
        assertNull(vacationCaptor.getValue().getPerson());
        assertTrue(personCaptor.getValue().getVacations().isEmpty());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = 1)
    void removeVacationNoPersonFound(Long personId) {
        when(person.getId()).thenReturn(personId);
        when(personRepository.findById(any())).thenReturn(Optional.empty());
        vacationService.removeVacation(person.getId(), vacationEntry);
        verify(person, never()).removeVacation(any(VacationEntry.class));
        verifyNoMoreInteractions(personRepository);
    }

    @Test
    void updateVacation() {
        when(person.getId()).thenReturn(1L);
        when(personRepository.findById(anyLong())).thenReturn(Optional.of(person));

        when(vacationEntry.getId()).thenReturn(1L);
        vacationEntry.setStatus(VacationStatus.REQUESTED);
        person.addVacation(vacationEntry);

        VacationEntry updatedEntry = spy(VacationEntry.class);
        when(updatedEntry.getId()).thenReturn(1L);
        updatedEntry.setStatus(VacationStatus.APPROVED);
        updatedEntry.setPerson(person);

        vacationService.updateVacation(person.getId(), updatedEntry);
        verify(person).removeVacation(any(VacationEntry.class));
        verify(person, times(2)).addVacation(any(VacationEntry.class));
        verify(personRepository).save(personCaptor.capture());

        assertEquals(1, personCaptor.getValue().getVacations().size());
        assertTrue(personCaptor.getValue()
                               .getVacations()
                               .stream()
                               .allMatch(entry -> entry.getStatus() == VacationStatus.APPROVED));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = 1)
    void updateVacationNoPersonFound(Long personId) {
        when(person.getId()).thenReturn(personId);
        when(personRepository.findById(any())).thenReturn(Optional.empty());
        vacationService.updateVacation(person.getId(), vacationEntry);
        verify(person, never()).removeVacation(any(VacationEntry.class));
        verify(person, never()).addVacation(any(VacationEntry.class));
        verifyNoMoreInteractions(personRepository);
    }

    @Test
    void updateVacationNewVacation() {
        when(personRepository.findById(anyLong())).thenReturn(Optional.of(person));

        vacationService.updateVacation(1L, vacationEntry);
        verify(person, never()).removeVacation(any(VacationEntry.class));
        verify(person, never()).addVacation(any(VacationEntry.class));
    }

    @Test
    void updateVacationMismatchPersonId() {
        when(person.getId()).thenReturn(1L);
        when(vacationEntry.getId()).thenReturn(1L);
        vacationEntry.setPerson(person);

        when(personRepository.findById(anyLong())).thenReturn(Optional.of(person));

        vacationService.updateVacation(person.getId() + 1, vacationEntry);
        verify(person, never()).removeVacation(any(VacationEntry.class));
        verify(person, never()).addVacation(any(VacationEntry.class));
    }

    @ParameterizedTest
    @MethodSource("provideVacationParameter")
    void getVacationDaysWithoutPublicHolidays(LocalDate from, LocalDate to, long expectedHolidays) throws PersonNotFoundException {
        when(personRepository.findById(anyLong())).thenReturn(Optional.of(person));
        person.setCompany(company);

        VacationEntry vacationRange = new VacationEntry();
        vacationRange.setStartDate(LocalDate.of(2019, Month.MARCH, 1));
        vacationRange.setEndDate(vacationRange.getStartDate().with(TemporalAdjusters.lastDayOfMonth())); // 21 days

        VacationEntry vacationDay = new VacationEntry();
        vacationDay.setStartDate(LocalDate.of(2019, Month.AUGUST, 1)); // 1 day
        vacationDay.setEndDate(vacationDay.getStartDate());

        when(person.getVacations()).thenReturn(Set.of(vacationRange, vacationDay));
        when(publicHolidayService.getPublicHolidays(eq(from), eq(to), any(FederalState.class)))
                .thenReturn(List.of());

        assertEquals(expectedHolidays, vacationService.getVacationDays(1L, from, to));
    }

    @Test
    void getVacationDaysWithPublicHoliday() throws PersonNotFoundException {
        when(personRepository.findById(anyLong())).thenReturn(Optional.of(person));
        person.setCompany(company);

        VacationEntry vacationRange = new VacationEntry();
        vacationRange.setStartDate(LocalDate.of(2019, Month.MARCH, 1)); // Friday
        vacationRange.setEndDate(vacationRange.getStartDate().with(TemporalAdjusters.lastDayOfMonth())); // 21 days

        VacationEntry vacationDay = new VacationEntry();
        vacationDay.setStartDate(LocalDate.of(2019, Month.AUGUST, 1)); // 1 day
        vacationDay.setEndDate(vacationDay.getStartDate());

        PublicHoliday publicHoliday = new PublicHoliday();
        publicHoliday.setDate(vacationRange.getStartDate());

        when(person.getVacations()).thenReturn(Set.of(vacationRange, vacationDay));
        when(publicHolidayService.getPublicHolidays(any(LocalDate.class), any(LocalDate.class), any(FederalState.class)))
                .thenReturn(List.of(publicHoliday));

        LocalDate from = LocalDate.of(2019, Month.JANUARY, 1);
        LocalDate to = from.with(TemporalAdjusters.lastDayOfYear());
        assertEquals(21, vacationService.getVacationDays(1L, from, to));
    }

    @Test
    void getVacationDaysCurrentYear() throws PersonNotFoundException {
        when(personRepository.findById(anyLong())).thenReturn(Optional.of(person));
        person.setCompany(company);

        when(person.getVacations()).thenReturn(Set.of());
        when(publicHolidayService.getPublicHolidays(any(LocalDate.class), any(LocalDate.class), any(FederalState.class)))
                .thenReturn(List.of());

        vacationService.getVacationDays(1L);
        ArgumentCaptor<LocalDate> fromCapture = ArgumentCaptor.forClass(LocalDate.class);
        ArgumentCaptor<LocalDate> toCapture = ArgumentCaptor.forClass(LocalDate.class);
        verify(vacationService).getVacationDays(anyLong(), fromCapture.capture(), toCapture.capture());

        assertEquals(LocalDate.now().with(TemporalAdjusters.firstDayOfYear()), fromCapture.getValue());
        assertEquals(LocalDate.now().with(TemporalAdjusters.lastDayOfYear()), toCapture.getValue());
    }

    private static Stream<Arguments> provideVacationParameter() {
        return Stream.of(
                Arguments.of(LocalDate.of(2019, Month.JANUARY, 1), LocalDate.of(2019, Month.DECEMBER, 31), 22),
                Arguments.of(LocalDate.of(2019, Month.MARCH, 11), LocalDate.of(2019, Month.DECEMBER, 31), 16),
                Arguments.of(LocalDate.of(2019, Month.APRIL, 1), LocalDate.of(2019, Month.DECEMBER, 31), 1),
                Arguments.of(LocalDate.of(2019, Month.JANUARY, 1), LocalDate.of(2019, Month.MARCH, 5), 3),
                Arguments.of(LocalDate.of(2019, Month.APRIL, 1), LocalDate.of(2019, Month.MAY, 1), 0)
        );
    }
}
