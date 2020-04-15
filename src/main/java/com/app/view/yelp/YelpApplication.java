package com.app.view.yelp;

import com.app.view.yelp.model.User;
import com.app.view.yelp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class YelpApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		//SpringApplication.run(YelpApplication.class, args);
		SpringApplication app = new SpringApplication(YelpApplication.class);
		app.setDefaultProperties(Collections
				.singletonMap("server.port", "8083"));
		app.run(args);
	}

	/**
	 * Callback used to run the bean.
	 *
	 * @param args incoming main method arguments
	 * @throws Exception on error
	 */
	@Override
	public void run(String... args) throws Exception {
		clearData();
		saveData();
	}

	private void saveData() {
		User user1 = new User(1, "User", "User");
		User user2 = new User(2, "Admin", "Admin");

		userRepository.save(user1);
		userRepository.save(user2);

	}

	private void clearData() {
		userRepository.deleteAll();
	}
}
