package com.warehouse.loginservice;

import com.warehouse.loginservice.entity.UserRole;
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
        User adminAccount = userRepository.findByRole(UserRole.ADMIN);

        if (adminAccount == null) {
            User user = new User();

            user.setEmail("admin@gmail.com");
            user.setName("Nguyen Duc Trong");
            user.setUsername("ductrong");
            user.setRole(UserRole.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);
        }
    }

}
