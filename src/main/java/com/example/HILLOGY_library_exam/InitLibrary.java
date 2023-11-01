package com.example.HILLOGY_library_exam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class InitLibrary {

  private static final Logger log = LoggerFactory.getLogger(InitLibrary.class);

  @Bean
  CommandLineRunner  createDefaultLibrary(Library library) {

    return args -> {
      log.info("Preloading " + library.save(new Book("404 messages under the sea", "Confused Coder", "9992158107", true)));
      log.info("Preloading " + library.save(new Book("How 2 Springboot", "Help please", "097522980X", true)));
    };
  }
}