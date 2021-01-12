package de.faltfe.vacation.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Person {

    @Id
    @GeneratedValue
    public Long id;

    @Column
    public String name;

    @Column
    public String email;

    @JoinColumn(name = "company_id")
    @ManyToOne(fetch = FetchType.LAZY)
    public Company company;
}
