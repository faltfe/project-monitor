package de.faltfe.vacation.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @JoinColumn(name = "company_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Company company;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "persons")
    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Project> projects = new HashSet<>();

    @OneToOne(mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private VacationQuota vacationQuota;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "person")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    private Set<VacationEntry> vacations = new HashSet<>();

    public void addProject(Project project) {
        projects.add(project);
        project.getPersons().add(this);
    }

    public void removeProject(Project project) {
        projects.removeIf(p -> p.getId().equals(project.getId()));
        project.getPersons().remove(this);
    }

    public void addVacation(VacationEntry vacationEntry) {
        vacations.add(vacationEntry);
        vacationEntry.setPerson(this);
    }

    public void removeVacation(VacationEntry vacationEntry) {
        vacations.removeIf(element -> element.getId().equals(vacationEntry.getId()));
        vacationEntry.setPerson(null);
    }
}
