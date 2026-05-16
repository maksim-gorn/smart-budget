package com.tpu.itr.smart_budget.authentication.user;

//import com.tpu.itr.smart_budget.user.JWT.JWTRepository;
import com.tpu.itr.smart_budget.authentication.JWT.JWTService;
//import com.tpu.itr.smart_budget.user.dto.JWT;
//import com.tpu.itr.smart_budget.user.dto.JWTEntity;
import com.tpu.itr.smart_budget.authentication.Utils.PhoneValidator;
import com.tpu.itr.smart_budget.authentication.dto.LoginRequest;
import com.tpu.itr.smart_budget.authentication.dto.RegisterRequest;
import com.tpu.itr.smart_budget.common.BadRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;


    public UserService(
         UserRepository userRepository,
         JWTService jwtService,
         PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        //this.jwtRepository = jwtRepository;
        this.jwtService =jwtService;
        this.passwordEncoder = passwordEncoder;
    }


    public String register(RegisterRequest registerRequest)
    {

        if (registerRequest.phone_number().isEmpty()) {
            throw new BadRequestException("No phone number presented");
        }

        if (registerRequest.password().isEmpty()) {
            throw new BadRequestException("No password presented");
        }

        if (!registerRequest.phone_number().matches("^\\+?[0-9]{7,15}$")) {
            throw new BadRequestException("Phone number contains invalid characters");
        }

        if (!PhoneValidator.isValid(registerRequest.phone_number(), null)) {
            throw new BadRequestException("Phone number is incorrect");
        }

        Optional<UserEntity> userOptional = userRepository.findByPhoneNumber(registerRequest.phone_number());

        if (userOptional.isPresent()){
            throw new BadRequestException("Phone number is already present");
        }

        String hashedPassword = passwordEncoder.encode(registerRequest.password());
        var userEntity = new UserEntity(
                null,
                null,
                registerRequest.phone_number(),
                hashedPassword
        );

        var savedUserEntity = userRepository.save(userEntity);

        return jwtService.generateToken(savedUserEntity.getId());

    }

    public String login(LoginRequest loginRequest)
    {
        if (loginRequest.phone_number().isEmpty())
            throw new BadRequestException("No phone number presented");

        if (loginRequest.password().isEmpty())
            throw new BadRequestException("No password presented");

        Optional<UserEntity> userOptional = userRepository.findByPhoneNumber(loginRequest.phone_number());


        if (userOptional.isEmpty())
            throw new BadRequestException("User not found by credentials");

        UserEntity userFromDB = userOptional.get();

        if (!passwordEncoder.matches(loginRequest.password(), userFromDB.getPassword_hash())) {
            throw new BadRequestException("Password is incorrect");
        }

        return jwtService.generateToken(userFromDB.getId());
    }

}
