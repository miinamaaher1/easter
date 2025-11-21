package com.mina.easter;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.mina.easter.entities.Course;
import com.mina.easter.repositories.CourseRepository;

import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class EasterApplication {

	@Autowired
	private CourseRepository courseRepository;

	public static void main(String[] args) {
		SpringApplication.run(EasterApplication.class, args);
	}

	@Bean
	public CommandLineRunner seedCourses() {
	    return args -> {
	        if (courseRepository.count() == 0) {
	            Course english = new Course();
	            english.setName("English");
	            courseRepository.save(english);

	            Course math = new Course();
	            math.setName("Math");
	            courseRepository.save(math);

	            Course science = new Course();
	            science.setName("Science");
	            courseRepository.save(science);
	        }
	    };
	}
}
