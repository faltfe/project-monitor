package de.faltfe.vacation.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class VacationEntry {

    @Id
    @GeneratedValue
    @Column(unique = true, nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    public Long id;

    @Column(nullable = false)
    public LocalDate startDate;

    @Column(nullable = false)
    public LocalDate endDate;

    @Column
    @Enumerated(EnumType.STRING)
    private VacationStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    @EqualsAndHashCode.Exclude
    public Person person;
}
