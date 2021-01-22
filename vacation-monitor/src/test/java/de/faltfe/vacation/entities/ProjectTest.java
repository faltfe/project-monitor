package de.faltfe.vacation.entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectTest {

    @Spy
    private Person person;

    @Spy
    private Project project;

    @Test
    void addPerson() {
        project.addPerson(person);
        assertFalse(project.getPersons().isEmpty());
        assertFalse(person.getProjects().isEmpty());
    }

    @Test
    void removePerson() {
        when(person.getId()).thenReturn(1L);
        project.addPerson(person);
        project.removePerson(person);
        assertTrue(project.getPersons().isEmpty());
        assertTrue(person.getProjects().isEmpty());
    }
}