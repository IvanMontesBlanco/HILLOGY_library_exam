package com.example.HILLOGY_library_exam.commands;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(
		  name = "list_all",
		  description = "Lists all books in the library"
		)
public class BookListAllCommand implements Runnable{
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
        CommandLine.run(new BookListAllCommand(), args);
    }
	
	@Override
	public void run() {
		System.out.println("Test command: list all books (TODO)");
		
	}

}
