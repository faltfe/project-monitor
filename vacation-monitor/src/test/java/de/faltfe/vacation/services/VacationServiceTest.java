package de.faltfe.vacation.services;

import de.faltfe.vacation.entities.Person;
import de.faltfe.vacation.entities.VacationEntry;
import de.faltfe.vacation.repositories.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VacationServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Spy
    private Person person;

    @Spy
    private VacationEntry vacationEntry;

    @Captor
    private ArgumentCaptor<Person> personCaptor;

    @Captor
    private ArgumentCaptor<VacationEntry> vacationCaptor;

    @InjectMocks
    private VacationService vacationService;

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
        verify(person, never()).addVacation(any());
        verifyNoMoreInteractions(personRepository);
    }

    @Test
    void removeVacation() {
    }

    @Test
    void updateVacation() {
    }
}