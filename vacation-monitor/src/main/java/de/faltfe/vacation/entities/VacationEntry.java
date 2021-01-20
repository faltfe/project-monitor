package de.faltfe.vacation.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "vacation_entry")
@Data
public class VacationEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column
    @Enumerated(EnumType.STRING)
    private VacationStatus status = VacationStatus.REQUESTED;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    public Person person;
}
