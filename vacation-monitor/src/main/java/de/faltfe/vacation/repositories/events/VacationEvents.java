package de.faltfe.vacation.repositories.events;

import de.faltfe.vacation.entities.VacationEntry;
import de.faltfe.vacation.repositories.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

@RepositoryEventHandler
@Slf4j
public class VacationEvents {

    @Autowired
    private PersonRepository personRepository;

    @HandleAfterCreate
    public void handleVacationSave(VacationEntry vacationEntry) {
        log.debug("New vacation entry was saved");
    }
}
