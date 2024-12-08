package com.perfume.store.service;

//import com.perfume.store.database.UserDAO;
import com.perfume.store.dto.AuthRequest;
import com.perfume.store.exceptions.InvalidArgumentException;
import com.perfume.store.model.User;
import com.perfume.store.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class AuthenticationService {
    private final UserRepo userJdbiDao;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepo userJdbiDao, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userJdbiDao = userJdbiDao;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }


    public User authenticate(AuthRequest input) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getEmail(),
                            input.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new InvalidArgumentException("Invalid email or password");
        }

        User user = userJdbiDao.findByEmail(input.getEmail());
//        if (Objects.isNull(user)){
//                throw new InvalidArgumentException("User not found");
//        }
        return user;
    }
}

