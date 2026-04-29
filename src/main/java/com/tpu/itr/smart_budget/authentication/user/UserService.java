package com.tpu.itr.smart_budget.authentication.user;

//import com.tpu.itr.smart_budget.user.JWT.JWTRepository;
import com.tpu.itr.smart_budget.authentication.JWT.JWTService;
//import com.tpu.itr.smart_budget.user.dto.JWT;
//import com.tpu.itr.smart_budget.user.dto.JWTEntity;
import com.tpu.itr.smart_budget.authentication.dto.LoginRequest;
import com.tpu.itr.smart_budget.authentication.dto.RegisterRequest;
import com.tpu.itr.smart_budget.authentication.dto.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    //private final JWTRepository jwtRepository;
    private final JWTService jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public UserService(
         UserRepository userRepository,
         //JWTRepository jwtRepository,
         JWTService jwtUtils,
         PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        //this.jwtRepository = jwtRepository;
        this.jwtUtils=jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }


    public String register(RegisterRequest registerRequest)
    {
        //нужно будет сделать проверку номера и почты по другому
        //для регистрации нужен либо телефон либо почта
        if (registerRequest.email().isEmpty() && registerRequest.phone_number().isEmpty())
            throw new IllegalArgumentException("No email or phone number presented");

        if (registerRequest.password().isEmpty())
            throw new IllegalArgumentException("No password presented");

        String hashedPassword = passwordEncoder.encode(registerRequest.password());
        var userEntity = new UserEntity(
                null,
                registerRequest.email(),
                registerRequest.phone_number(),
                hashedPassword
        );

        var savedUserEntity = userRepository.save(userEntity);

        String jwtString = jwtUtils.generateToken(savedUserEntity.getId());
        return jwtString;

    }

    public String login(LoginRequest loginRequest)
    {
        if (loginRequest.email().isEmpty() && loginRequest.phone_number().isEmpty())
            throw new IllegalArgumentException("No email or phone number presented");

        if (loginRequest.password().isEmpty())
            throw new IllegalArgumentException("No password presented");

        Optional<UserEntity> userOptional = userRepository.findByEmailOrPhoneNumber
                (loginRequest.email(), loginRequest.phone_number());

        if (userOptional.isEmpty())
            throw new EntityNotFoundException("User not found by credentials");

        UserEntity userFromDB = userOptional.get();

        if (!passwordEncoder.matches(loginRequest.password(), userFromDB.getPassword_hash())) {
            throw new BadCredentialsException("Credentials are incorrect");
        }

        return jwtUtils.generateToken(userFromDB.getId());
    }

}
