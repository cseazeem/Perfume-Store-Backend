package com.perfume.store.security;
import com.perfume.store.model.User;
import com.perfume.store.repository.UserRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.server.ResponseStatusException;

@Configuration
public class ApplicationConfiguration {
    private final UserRepo userRepository;

    public ApplicationConfiguration(UserRepo userRepository) {
        this.userRepository = userRepository;
    }


    @Bean
    public UserDetailsService userDetailsService(UserRepo userDao) {
        return username -> {
            if (username == null || username.trim().isEmpty()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Username must not be null or empty");
            }

            User user = userDao.findByEmail(username);
            if (user == null) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "User not found for email: " + username);
            }

            // Assuming `User` implements `UserDetails` or can be converted to it.
            return user;
        };
    }
}
