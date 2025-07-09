package ge.edu.cu.l_lomidze2.sabakalavro.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import ge.edu.cu.l_lomidze2.sabakalavro.dto.UserDTO;
import ge.edu.cu.l_lomidze2.sabakalavro.model.User;
import ge.edu.cu.l_lomidze2.sabakalavro.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/vulnerable")
public class VulnerableController {
    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper mapper;

    @GetMapping("/bola/user/{id}")
    @Tag(name = "1. BOLA (Broken Object Level Authentication)")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/excessive/user/{id}")
    @Tag(name = "2. Excessive Data Exposure")
    @SecurityRequirements({})
    public User excessiveExposure(Principal principal) {
        return userService.getUser(principal.getName());
    }

    @GetMapping("/unrestricted/user/{id}")
    @Tag(name = "3. Unrestricted API Consumption")
    @SecurityRequirements({})
    public UserDTO unrestrictedConsumption(Principal principal, HttpServletRequest request) {
        return mapper.convertValue(userService.getUser(principal.getName()), UserDTO.class);
    }
}
