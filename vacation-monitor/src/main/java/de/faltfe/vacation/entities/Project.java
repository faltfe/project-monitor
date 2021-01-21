package de.faltfe.vacation.entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @ManyToMany(fetch = FetchType.LAZY,
        cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "project_person",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "person_id"),
        uniqueConstraints = @UniqueConstraint(columnNames = {"project_id", "person_id"})
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    private Set<Person> persons = new HashSet<>();

    public void addPerson(Person person) {
        persons.add(person);
        person.getProjects().add(this);
    }

    public void removePerson(Person person) {
        persons.removeIf(p -> p.getId().equals(person.getId()));
        person.getProjects().remove(this);
    }
}
