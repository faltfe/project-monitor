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
public class Project {

    @Id
    @GeneratedValue
    public Long id;

    @Column(nullable = false, unique = true)
    public String title;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "project_person",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    @EqualsAndHashCode.Exclude
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
