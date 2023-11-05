package com.example.HILLOGY_library_exam.commands;

import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

@Component
public class BookCommands implements CommandMarker {

    @CliCommand(value = { "list_books", "listBooks", "l_b" })
    public String list_books() {
        return "http://localhost:8080/api/library";
    }
    
    @CliCommand(value = { "list_available", "listAvailalbe", "l_a" })
    public String list_available() {
        return "http://localhost:8080/api/library/listAvailalbe";
    }
}