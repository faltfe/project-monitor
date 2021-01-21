package de.faltfe.vacation.entities;

import org.junit.jupiter.api.BeforeEach;

class ProjectTest {

    private Person person;
    private Project project;

    @BeforeEach
    public void init() {
        person = new Person();
        project = new Project();
    }

//    @Test
//    void addPerson() {
//        project.addPerson(person);
//        assertFalse(project.getPersons().isEmpty());
//        assertFalse(person.getProjects().isEmpty());
//    }
//
//    @Test
//    void removePerson() {
//        project.addPerson(person);
//        project.removePerson(person);
//        assertTrue(project.getPersons().isEmpty());
//        assertTrue(person.getProjects().isEmpty());
//    }
}