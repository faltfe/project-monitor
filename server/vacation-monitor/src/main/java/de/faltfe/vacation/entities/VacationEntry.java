package de.faltfe.vacation.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "vacation_entry")
@Data
public class VacationEntry implements Serializable {

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_id", updatable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Person person;
}
