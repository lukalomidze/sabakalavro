package ge.edu.cu.l_lomidze2.sabakalavro.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import ge.edu.cu.l_lomidze2.sabakalavro.dto.UserDTO;
import ge.edu.cu.l_lomidze2.sabakalavro.model.User;
import ge.edu.cu.l_lomidze2.sabakalavro.service.UserService;
import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("/secure")
public class SecureController {
    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper mapper;

    @GetMapping("/bola/user/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "BOLA (Broken Object Level Authentication)")
    public User bola(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/bola/user")
    @Operation(summary = "BOLA (Broken Object Level Authentication)")
    public User bolaSelf(Principal principal) {
        return userService.getUser(principal.getName());
    }

    @GetMapping("/excessive/user/{id}")
    public UserDTO excessiveExposure(@PathVariable Long id) {
        return mapper.convertValue(userService.getUser(id), UserDTO.class);
    }
}
