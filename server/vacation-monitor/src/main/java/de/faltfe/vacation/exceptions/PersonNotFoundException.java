package de.faltfe.vacation.exceptions;

public class PersonNotFoundException extends Exception{

    public PersonNotFoundException() {
        super("The requested user with id was not found!");
    }
}
