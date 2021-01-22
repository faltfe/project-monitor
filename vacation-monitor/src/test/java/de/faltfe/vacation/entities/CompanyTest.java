package de.faltfe.vacation.entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompanyTest {

    @Spy
    private Company company;
    @Spy
    private Person person;

    @Test
    void addPerson() {
        company.addPerson(person);
        assertFalse(company.getPersons().isEmpty());
        assertEquals(company, person.getCompany());
    }

    @Test
    void removePerson() {
        when(person.getId()).thenReturn(1L);
        company.addPerson(person);
        company.removePerson(person);
        assertTrue(company.getPersons().isEmpty());
        assertNull(person.getCompany());
    }
}