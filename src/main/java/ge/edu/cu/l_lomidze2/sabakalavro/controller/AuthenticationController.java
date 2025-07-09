package ge.edu.cu.l_lomidze2.sabakalavro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ge.edu.cu.l_lomidze2.sabakalavro.dto.LoginResponseDTO;
import ge.edu.cu.l_lomidze2.sabakalavro.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.constraints.NotBlank;


@RestController
@RequestMapping("/user")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/login")
    @Operation(description = "log in")
    @SecurityRequirements({})
    public LoginResponseDTO login(
        @NotBlank @RequestHeader String username,
        @NotBlank @RequestHeader String password
    ) {
        try {
            var auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
            );

            return LoginResponseDTO.builder()
                .token(jwtService.encode(auth))
            .build();
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
}
