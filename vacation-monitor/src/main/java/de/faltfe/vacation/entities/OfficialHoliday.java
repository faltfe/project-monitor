package de.faltfe.vacation.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "official_holiday", uniqueConstraints = @UniqueConstraint(columnNames = {"date", "description"}))
@Data
public class OfficialHoliday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String description;
}
