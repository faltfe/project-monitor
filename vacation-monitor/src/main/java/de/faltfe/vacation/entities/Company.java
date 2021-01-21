package de.faltfe.vacation.entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private FederalState federalState;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    private Set<Person> persons = new HashSet<>();

    public void addPerson(Person person) {
        persons.add(person);
        person.setCompany(this);
    }

    public void removePerson(Person person) {
        persons.removeIf(p -> p.getId().equals(person.getId()));
        person.setCompany(null);
    }
}
