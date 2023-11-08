package com.example.HILLOGY_library_exam.commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import com.example.HILLOGY_library_exam.entities.Book;
import com.example.HILLOGY_library_exam.entities.User;
import com.example.HILLOGY_library_exam.exceptions.BookCheckedOutException;
import com.example.HILLOGY_library_exam.exceptions.BookDuplicatedException;
import com.example.HILLOGY_library_exam.exceptions.BookNotFoundException;
import com.example.HILLOGY_library_exam.exceptions.UserNotFoundException;
import com.example.HILLOGY_library_exam.repositories.Library;
import com.example.HILLOGY_library_exam.services.LibraryService;
import com.example.HILLOGY_library_exam.services.UserService;

@Component
public class UserCommands implements CommandMarker {

	@Autowired
	private UserService userService;
	
	@Autowired
	private LibraryService libraryService;

	public UserCommands(UserService userService, LibraryService libraryService) {
		super();
		this.userService = userService;
		this.libraryService = libraryService;
	}



	@CliCommand(value = { "list_users", "listUsers" }, help = "Lists all users in the library.")
	public String list_books() {
		return userCollection2String(userService.listAll());
	}
	
	@CliCommand(value = { "add_user", "addUser" }, help = "Adds an user with the given name. Id is auto-generated.")
	public String addUser(@CliOption(key = { "", "name" }, mandatory = true) String name) {
		return userService.newUser(new User(name)).toString();
	}
	
	@CliCommand(value = { "delete_user", "deleteUser" }, help = "Deletes an user with the given id. Book checked out by the user will be returned.")
	public String deleteUser(@CliOption(key = { "", "id" }, mandatory = true) long id) {
		try{
			userService.deleteUser(id);
			return "User with id (" + String.valueOf(id) + ") has been successfully deleted.";
		}
		catch(UserNotFoundException e) {
			return e.getMessage();
		}
	}
	
	@CliCommand(value = { "check_out", "checkOut" }, help = "Makes the given user check out the given book. Uses the format --BOOK ISBN --USER ID")
	public String checkOut(@CliOption(key = { "is", "ISBN", "book", "b" }, mandatory = true) String ISBN,
			@CliOption(key = { "id", "user", "u" }, mandatory = true) Long id) {
		try {
			userService.checkOutBook(id, ISBN);
		}
		catch(BookNotFoundException e) {
			return e.getMessage();
		}
		catch(UserNotFoundException e) {
			return e.getMessage();
		}
		catch(BookCheckedOutException e) {
			return e.getMessage();
		}
		return "User with id (" + String.valueOf(id) + ") has checked out the book with ISBN (" + ISBN + ").";
	}
	
	@CliCommand(value = { "return", "returnBook", "return_book" }, help = "Makes the given user return the given book. Uses the format --BOOK ISBN --USER ID")
	public String returnOut(@CliOption(key = { "is", "ISBN", "book", "b" }, mandatory = true) String ISBN,
			@CliOption(key = { "id", "user", "u" }, mandatory = true) Long id) {
		try {
			userService.returnBook(id, ISBN);
		}
		catch(BookNotFoundException e) {
			return e.getMessage();
		}
		return "User with id (" + String.valueOf(id) + ") has returned the book with ISBN (" + ISBN + ").";
	}
	
	// returns a given user collection in text form
		private String userCollection2String(CollectionModel<EntityModel<User>> userCollection) {
			StringBuilder toret = new StringBuilder();
			Iterator<EntityModel<User>> itr = userCollection.iterator();

			while (itr.hasNext()) {
				toret.append(userEntity2String(itr.next()));
			}

			return toret.toString();
		}

		// returns a given user entity model in text form
		private String userEntity2String(EntityModel<User> userEntity) {
			return userEntity.getContent().toString() + "\n";
		}

}