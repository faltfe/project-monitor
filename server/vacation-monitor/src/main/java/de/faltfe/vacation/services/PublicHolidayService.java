package de.faltfe.vacation.services;

import de.faltfe.vacation.entities.FederalState;
import de.faltfe.vacation.entities.PublicHoliday;
import de.faltfe.vacation.repositories.PublicHolidayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PublicHolidayService {

    private final PublicHolidayRepository publicHolidayRepository;

    public List<PublicHoliday> getFederalStateHolidays(LocalDate from, LocalDate to, FederalState federalState) {
        return getPublicHolidays(from, to, federalState, true);
    }

    public List<PublicHoliday> getPublicHolidays(LocalDate from, LocalDate to, FederalState federalState) {
        return getPublicHolidays(from, to, federalState, false);
    }

    public List<PublicHoliday> getPublicHolidays(LocalDate from, LocalDate to) {
        return publicHolidayRepository.findAllByDateBetweenAndFederalStateIsNull(from, to);
    }

    private List<PublicHoliday> getPublicHolidays(LocalDate from, LocalDate to, FederalState federalState, boolean onlyFederalState) {
        List<PublicHoliday> federalStateHolidays = publicHolidayRepository.findAllByDateBetweenAndFederalState(from, to, federalState);
        if (onlyFederalState) {
            return federalStateHolidays;
        }

        List<PublicHoliday> genericHolidays = getPublicHolidays(from, to);

        return Stream.concat(federalStateHolidays.stream(), genericHolidays.stream())
                     .distinct()
                     .collect(Collectors.toList());
    }

}
