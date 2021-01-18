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
    public Long id;

    @Column(nullable = false, unique = true)
    public String title;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "project_person",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    public Set<Person> persons = new HashSet<>();

    public void addPerson(Person person) {
        persons.add(person);
        person.getProjects().add(this);
    }

    public void removePerson(Person person) {
        persons.remove(person);
        person.getProjects().remove(this);
    }
}
