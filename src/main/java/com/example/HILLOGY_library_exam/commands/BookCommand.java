package com.example.HILLOGY_library_exam.commands;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(
		  name = "book",
		  description = "Commands for managing books",
				  subcommands = {
					      BookListAllCommand.class
					  }
		)
public class BookCommand implements Runnable{
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
        CommandLine.run(new BookCommand(), args);
    }
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
