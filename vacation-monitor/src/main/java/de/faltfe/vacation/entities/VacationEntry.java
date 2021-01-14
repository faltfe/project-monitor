package de.faltfe.vacation.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class VacationEntry {

    @Id
    @GeneratedValue
    public Long id;

    @Column(nullable = false)
    public LocalDate startDate;

    @Column(nullable = false)
    public LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    @EqualsAndHashCode.Exclude
    public Person person;
}
