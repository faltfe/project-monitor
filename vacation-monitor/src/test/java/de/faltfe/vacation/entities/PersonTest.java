package de.faltfe.vacation.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    private Person person;
    private Project project;
    private VacationEntry vacationEntry;

    @BeforeEach
    public void initTest() {
        person = new Person();
        project = new Project();
        vacationEntry = new VacationEntry();
    }

    @Test
    void addProject() {
        person.addProject(project);
        assertFalse(person.getProjects().isEmpty());
        assertFalse(project.getPersons().isEmpty());
    }

    @Test
    void removeProject() {
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
        person.addVacation(vacationEntry);
        person.removeVacation(vacationEntry);
        assertTrue(person.getVacations().isEmpty());
        assertNull(vacationEntry.getPerson());
    }
}
