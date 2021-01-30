package de.faltfe.vacation.repositories;


import de.faltfe.vacation.entities.FederalState;
import de.faltfe.vacation.entities.PublicHoliday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PublicHolidayRepository extends JpaRepository<PublicHoliday, Long> {

    List<PublicHoliday> findAllByDateBetweenAndFederalStateIsNull(LocalDate from, LocalDate to);

    List<PublicHoliday> findAllByDateBetweenAndFederalState(LocalDate from, LocalDate to, FederalState federalState);

    Optional<PublicHoliday> findByDate(@NonNull LocalDate date);
}
