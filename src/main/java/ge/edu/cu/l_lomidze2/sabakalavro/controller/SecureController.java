package ge.edu.cu.l_lomidze2.sabakalavro.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;

import ge.edu.cu.l_lomidze2.sabakalavro.dto.UserDTO;
import ge.edu.cu.l_lomidze2.sabakalavro.model.User;
import ge.edu.cu.l_lomidze2.sabakalavro.service.BruteForceService;
import ge.edu.cu.l_lomidze2.sabakalavro.service.UserService;
import ge.edu.cu.l_lomidze2.sabakalavro.util.IpUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/secure")
public class SecureController {
    @Autowired
    private UserService userService;

    @Autowired
    private BruteForceService bruteForceService;

    @Autowired
    private ObjectMapper mapper;

    @GetMapping("/bola/user/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Tag(name = "1. BOLA (Broken Object Level Authentication)")
    public User bola(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/bola/user")
    @Tag(name = "1. BOLA (Broken Object Level Authentication)")
    public User bolaSelf(Principal principal) {
        return userService.getUser(principal.getName());
    }

    @GetMapping("/excessive/user/{id}")
    @Tag(name = "2. Excessive Data Exposure")
    @SecurityRequirements({})
    public UserDTO excessiveExposure(@PathVariable Long id) {
        return mapper.convertValue(userService.getUser(id), UserDTO.class);
    }

    @GetMapping("/unrestricted/user/{id}")
    @Tag(name = "3. Unrestricted API Consumption")
    @SecurityRequirements({})
    public User unrestrictedConsumption(@PathVariable Long id, HttpServletRequest request) {
        var ip = IpUtil.getIp(request);

        if (bruteForceService.isLockedOut(ip)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return userService.getUser(id);
    }
}
