package com.example.HILLOGY_library_exam.commands;

import org.springframework.boot.SpringApplication;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.stereotype.Component;

import com.example.HILLOGY_library_exam.HillogyLibraryExamApplication;

@Component
public class InitCommand implements CommandMarker {

    @CliCommand(value = { "init", "init_library", "initLibrary" })
    public void list_books() {
		SpringApplication.run(HillogyLibraryExamApplication.class);
    }
}