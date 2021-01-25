package de.faltfe.vacation.repositories;


import de.faltfe.vacation.entities.FederalState;
import de.faltfe.vacation.entities.OfficialHoliday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OfficialHolidayRepository extends JpaRepository<OfficialHoliday, Long> {

    List<OfficialHoliday> findAllByDateBetweenAndFederalStateIsNull(LocalDate from, LocalDate to);

    List<OfficialHoliday> findAllByDateBetweenAndFederalState(LocalDate from, LocalDate to, FederalState federalState);

    Optional<OfficialHoliday> findByDate(@NonNull LocalDate date);
}
