package com.example.HILLOGY_library_exam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.HILLOGY_library_exam.entities.Book;
import com.example.HILLOGY_library_exam.repositories.Library;

@Configuration
class InitLibrary {

	private static final Logger log = LoggerFactory.getLogger(InitLibrary.class);

	@Bean
	CommandLineRunner createDefaultLibrary(Library library) {

		// creates several test books
		return args -> {
			log.info("Preloading "
					+ library.save(new Book("9992158107", "404 messages under the sea", "Confused Coder")));
			log.info("Preloading " + library.save(new Book("097522980X", "How 2 Springboot", "Keyboard Masher")));
			log.info("Preloading "
					+ library.save(new Book("1843560283", "I really don't know what I'm doing", "Keyboard Masher")));
			log.info("Preloading " + library.save(new Book("0851310419", "Do you know this text?", "Mr Confusion")));
		};
	}
}