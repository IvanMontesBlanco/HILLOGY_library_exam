package com.example.HILLOGY_library_exam;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.HILLOGY_library_exam.commands.BookCommand;
import com.example.HILLOGY_library_exam.commands.BookListAllCommand;

import picocli.CommandLine;

@SpringBootApplication
public class HillogyLibraryExamApplication implements CommandLineRunner {

	private BookCommand bookCommand;
	private BookListAllCommand bookListAllCommand;

	public static void main(String[] args) {
		SpringApplication.run(HillogyLibraryExamApplication.class, args);
	}

	public HillogyLibraryExamApplication(BookCommand bookCommand, BookListAllCommand bookListAllCommand) {
		super();
		this.bookCommand = bookCommand;
		this.bookListAllCommand = bookListAllCommand;
	}

	@SuppressWarnings("deprecation")
	public void run(String... args) throws Exception {
		CommandLine commandLine = new CommandLine(bookCommand);
		commandLine.addSubcommand("list_all", bookListAllCommand);
		commandLine.parseWithHandler(new CommandLine.RunLast(), args);
	}

}
