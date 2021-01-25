package de.faltfe.vacation.services;

import de.faltfe.vacation.entities.FederalState;
import de.faltfe.vacation.entities.OfficialHoliday;
import de.faltfe.vacation.entities.Person;
import de.faltfe.vacation.entities.VacationEntry;
import de.faltfe.vacation.exceptions.PersonNotFoundException;
import de.faltfe.vacation.repositories.OfficialHolidayRepository;
import de.faltfe.vacation.repositories.PersonRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

@Service
public class VacationService {

    private final PersonRepository personRepository;

    private final OfficialHolidayRepository officialHolidayRepository;

    public VacationService(PersonRepository personRepository, OfficialHolidayRepository officialHolidayRepository) {
        this.personRepository = personRepository;
        this.officialHolidayRepository = officialHolidayRepository;
    }

    public Set<VacationEntry> getVacations(Long personId) {
        return personRepository.findById(personId).map(Person::getVacations).orElseGet(Collections::emptySet);
    }

    public void addVacation(Long personId, VacationEntry vacationEntry) {
        // TODO Remove or extent vacation entry if dates collide
        addVacations(personId, List.of(vacationEntry));
    }

    public void addVacations(Long personId, Collection<VacationEntry> vacationEntries) {
        personRepository.findById(personId).ifPresentOrElse(person -> {
            vacationEntries.stream()
                           .filter(vacation -> vacation.getPerson().getId().equals(personId))
                           .forEach(person::addVacation);
            personRepository.save(person);
        }, PersonNotFoundException::new);
    }

    public void removeVacation(Long personId, VacationEntry vacationEntry) {
        removeVacations(personId, List.of(vacationEntry));
    }

    public void removeVacations(Long personId, Collection<VacationEntry> vacationEntries) {
        personRepository.findById(personId).ifPresent(person -> {
            vacationEntries.stream()
                           .filter(vacation -> vacation.getPerson().getId().equals(personId))
                           .forEach(person::removeVacation);
            personRepository.save(person);
        });
    }

    public void updateVacation(Long personId, VacationEntry updatedEntry) {
        updateVacations(personId, List.of(updatedEntry));
    }

    public void updateVacations(Long personId, Collection<VacationEntry> vacationEntries) {
        personRepository.findById(personId).ifPresentOrElse(person -> {
            vacationEntries.stream()
                           .filter(vacation -> Objects.nonNull(vacation.getId()))
                           .filter(vacation -> vacation.getPerson().getId().equals(personId))
                           .forEach(vacation -> {
                               person.removeVacation(vacation);
                               person.addVacation(vacation);
                           });
            personRepository.save(person);
        }, PersonNotFoundException::new);
    }

    public long getVacationDays(Long personId, LocalDate from, LocalDate to) throws PersonNotFoundException {
        Person person = personRepository.findById(personId).orElseThrow(PersonNotFoundException::new);
        List<LocalDate> vacationEntries = new ArrayList<>();
        person.getVacations().forEach(vacation -> {
            if (vacation.getStartDate().equals(vacation.getEndDate())) {
                vacationEntries.add(vacation.getStartDate());
                return;
            }
            vacation.getStartDate()
                    .datesUntil(vacation.getEndDate().plusDays(1))
                    .collect(Collectors.toCollection(() -> vacationEntries));

        });
        List<LocalDate> officialHolidays = getOfficialHolidays(person.getCompany().getFederalState(), from, to);
        return vacationEntries.stream()
                              .filter(date -> date.isAfter(from) || date.isEqual(from))
                              .filter(date -> date.isBefore(to) || date.isEqual(to))
                              .filter(date -> date.getDayOfWeek() != DayOfWeek.SATURDAY)
                              .filter(date -> date.getDayOfWeek() != DayOfWeek.SUNDAY)
                              .filter(date -> !officialHolidays.contains(date))
                              .count();
    }

    public long getVacationDays(Long personId) throws PersonNotFoundException {
        LocalDate date = LocalDate.now();
        return getVacationDays(personId, date.with(firstDayOfYear()), date.with(lastDayOfYear()));
    }

    private List<LocalDate> getOfficialHolidays(FederalState federalState, LocalDate from, LocalDate to) {
        return officialHolidayRepository.findAllByDateBetweenAndFederalState(from, to, federalState)
                                        .stream()
                                        .map(OfficialHoliday::getDate)
                                        .collect(Collectors.toList());
    }
}
