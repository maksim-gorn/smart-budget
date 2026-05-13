package com.tpu.itr.smart_budget.authentication;



import com.tpu.itr.smart_budget.authentication.Utils.PhoneValidator;
import com.tpu.itr.smart_budget.authentication.dto.JwtResponse;
import com.tpu.itr.smart_budget.authentication.dto.LoginRequest;
import com.tpu.itr.smart_budget.authentication.dto.RegisterRequest;
import com.tpu.itr.smart_budget.authentication.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(
        @RequestBody RegisterRequest registerRequest
    ) {
        log.info(String.format(
                "called AuthController.register, phone: %s",
                registerRequest.phone_number())
        );


        String jwtString = userService.register(registerRequest);
        JwtResponse jwtResponse = new JwtResponse(jwtString);
        return ResponseEntity.ok().body(jwtResponse);

    }


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(
            @RequestBody LoginRequest loginRequest
    ) {

        log.info(String.format(
                "called AuthController.login, phone: %s",
                 loginRequest.phone_number())
        );

        String jwtString = userService.login(loginRequest);
        JwtResponse jwtResponse = new JwtResponse(jwtString);
        return ResponseEntity.ok().body(jwtResponse);
    }

    //@PostMapping("/logout")

}
