package de.faltfe.vacation.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class VacationQuota {

    @Id
    @GeneratedValue
    public Long id;

    @Column(nullable = false)
    public int year;

    @Column(nullable = false)
    public int total;

    @Column(nullable = false)
    public int carryOver = 0;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    public Person person;
}
