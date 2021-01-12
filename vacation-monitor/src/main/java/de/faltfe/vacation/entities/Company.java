package de.faltfe.vacation.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Company {

    @Id
    @GeneratedValue
    public Long id;

    @Column
    public String name;

    @Column
    @Enumerated(EnumType.STRING)
    public FederalState federalState;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    public Set<Person> person = new HashSet<>();
}
