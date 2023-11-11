package com.example.HILLOGY_library_exam;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;

import com.example.HILLOGY_library_exam.entities.User;
import com.example.HILLOGY_library_exam.repositories.UserRepository;

@Configuration
class InitUsers {

	private static final Logger log = LoggerFactory.getLogger(InitUsers.class);
	@Autowired
	DataSourceProperties dataSourceProperties;

	// changes database settings to avoid recognizing "user" as a keyword
	@Bean
	public DataSource dataSource() {
		DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.url("jdbc:h2:mem:mydb;NON_KEYWORDS=user");
		return dataSourceBuilder.build();
	}

	// creates several test users
	@Bean
	CommandLineRunner createDefaultUsers(UserRepository userRepository) {

		return args -> {
			log.info("Preloading " + userRepository.save(new User("Bob")));
			log.info("Preloading " + userRepository.save(new User("Bobbert")));
			log.info("Preloading " + userRepository.save(new User("Sarah")));
		};
	}
}