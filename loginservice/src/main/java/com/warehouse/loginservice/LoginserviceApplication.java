package com.warehouse.loginservice;

import com.warehouse.loginservice.entity.Role;
import com.warehouse.loginservice.entity.User;
import com.warehouse.loginservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class LoginserviceApplication implements CommandLineRunner {


    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(LoginserviceApplication.class, args);
    }

    public void run(String... args) {
        User adminAccount = userRepository.findByRole(Role.ADMIN);

        if (adminAccount == null) {
            User user = new User();

            user.setEmail("admin@gmail.com");
            user.setFirstname("Trong");
            user.setLastname("Nguyen");
            user.setRole(Role.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);
        }
    }

}
