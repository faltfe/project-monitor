package de.faltfe.vacation.entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonTest {

    @Spy
    private Person person;

    @Spy
    private Project project;

    @Spy
    private VacationEntry vacationEntry;

    @Test
    void addProject() {
        person.addProject(project);
        assertFalse(person.getProjects().isEmpty());
        assertFalse(project.getPersons().isEmpty());
    }

    @Test
    void removeProject() {
        when(project.getId()).thenReturn(1L);
        person.addProject(project);
        person.removeProject(project);
        assertTrue(person.getProjects().isEmpty());
        assertTrue(project.getPersons().isEmpty());
    }

    @Test
    void addVacation() {
        person.addVacation(vacationEntry);
        assertFalse(person.getVacations().isEmpty());
        assertEquals(person, vacationEntry.getPerson());
    }

    @Test
    void removeVacation() {
        when(vacationEntry.getId()).thenReturn(1L);
        person.addVacation(vacationEntry);
        person.removeVacation(vacationEntry);
        assertTrue(person.getVacations().isEmpty());
        assertNull(vacationEntry.getPerson());
    }
}
