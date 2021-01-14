package de.faltfe.vacation.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompanyTest {

    private Company company;
    private Person person;

    @BeforeEach
    public void init() {
        company = new Company();
        person = new Person();
    }

    @Test
    void addPerson() {
        company.addPerson(person);
        assertFalse(company.getPersons().isEmpty());
        assertEquals(company, person.getCompany());
    }

    @Test
    void removePerson() {
        company.addPerson(person);
        company.removePerson(person);
        assertTrue(company.getPersons().isEmpty());
        assertNull(person.getCompany());
    }
}