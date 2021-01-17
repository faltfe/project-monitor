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
public class Person {

    @Id
    @GeneratedValue
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
    public Company company;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "persons")
    @EqualsAndHashCode.Exclude
    @Setter(AccessLevel.NONE)
    public Set<Project> projects = new HashSet<>();

    @OneToOne(mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public VacationQuota vacationQuota;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    @Setter(AccessLevel.NONE)
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
