package com.example.HILLOGY_library_exam.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.HILLOGY_library_exam.entities.Book;
import com.example.HILLOGY_library_exam.entities.User;
import com.example.HILLOGY_library_exam.exceptions.UserNotFoundException;
import com.example.HILLOGY_library_exam.repositories.UserRepository;

//tag::hateoas-imports[]
//end::hateoas-imports[]

@RestController
@RequestMapping("api/users/")
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LibraryService libraryService;

	UserService(UserRepository userRepository, LibraryService libraryService) {
		this.userRepository = userRepository;
		this.libraryService = libraryService;
	}

	// Aggregate root
	// tag::get-aggregate-root[]
	@GetMapping
	CollectionModel<EntityModel<User>> listAll() {
		List<EntityModel<User>> users = userRepository.findAll().stream()
				.map(user -> EntityModel.of(user,
						linkTo(methodOn(UserService.class).findById(user.getId())).withSelfRel(),
						linkTo(methodOn(UserService.class).listAll()).withRel("users")))
				.collect(Collectors.toList());

		return CollectionModel.of(users, linkTo(methodOn(LibraryService.class).listAll()).withSelfRel());
	}
	// end::get-aggregate-root[]

	// find user by id
	// tag::get-single-item[]
	@GetMapping("/{id}")
	EntityModel<User> findById(@PathVariable Long id) {
		User user = userRepository.findById(id)//
				.orElseThrow(() -> new UserNotFoundException(id));

		return EntityModel.of(user, //
				linkTo(methodOn(UserService.class).findById(id)).withSelfRel(),
				linkTo(methodOn(UserService.class).listAll()).withRel("books"));
	}
	// end::get-single-item[]

	// check out book by ISBN
	@GetMapping("/checkout/{ISBN}")
	EntityModel<Book> checkOutBook(@PathVariable String ISBN) {
		libraryService.checkOutBook(ISBN);
		return libraryService.findByISBN(ISBN);
	}

	// return book by ISBN
	@GetMapping("/return/{ISBN}")
	EntityModel<Book> returnBook(@PathVariable String ISBN) {
		libraryService.returnBook(ISBN);
		return libraryService.findByISBN(ISBN);
	}
}
