package de.faltfe.vacation.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "vacation_quota", uniqueConstraints = @UniqueConstraint(columnNames = {"person_id", "year"}))
@Data
public class VacationQuota implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private int total;

    @Column(nullable = false)
    private int carryover = 0;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    private Person person;
}
