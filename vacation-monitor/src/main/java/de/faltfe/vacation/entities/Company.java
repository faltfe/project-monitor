package de.faltfe.vacation.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Company {

    @Id
    @GeneratedValue
    public Long id;

    @Column(nullable = false, unique = true)
    public String name;

    @Column
    @Enumerated(EnumType.STRING)
    public FederalState federalState;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @Setter(AccessLevel.NONE)
    public Set<Person> persons = new HashSet<>();

    public void addPerson(Person person) {
        persons.add(person);
        person.setCompany(this);
    }

    public void removePerson(Person person) {
        persons.remove(person);
        person.setCompany(null);
    }
}
