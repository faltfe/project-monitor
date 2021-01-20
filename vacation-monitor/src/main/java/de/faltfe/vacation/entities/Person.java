package de.faltfe.vacation.entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    public Long id;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false, unique = true)
    public String email;

    @JoinColumn(name = "company_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    public Company company;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "persons")
    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    public Set<Project> projects = new HashSet<>();

    @OneToOne(mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    public VacationQuota vacationQuota;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    public Set<VacationEntry> vacations = new HashSet<>();

    public void addProject(Project project) {
        projects.add(project);
        project.getPersons().add(this);
    }

    public void removeProject(Project project) {
        projects.remove(project);
        project.getPersons().remove(this);
    }

    public void addVacation(VacationEntry vacationEntry) {
        vacations.add(vacationEntry);
        vacationEntry.setPerson(this);
    }

    public void removeVacation(VacationEntry vacationEntry) {
        vacations.remove(vacationEntry);
        vacationEntry.setPerson(null);
    }
}
