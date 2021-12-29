package de.faltfe.vacation.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "public_holiday", uniqueConstraints = @UniqueConstraint(columnNames = {"date", "description"}))
@Data
public class PublicHoliday implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String description;

    @Column
    @Enumerated(EnumType.STRING)
    private FederalState federalState;
}
