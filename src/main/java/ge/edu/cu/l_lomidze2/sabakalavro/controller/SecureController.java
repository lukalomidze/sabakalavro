package ge.edu.cu.l_lomidze2.sabakalavro.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ge.edu.cu.l_lomidze2.sabakalavro.model.User;
import ge.edu.cu.l_lomidze2.sabakalavro.service.UserService;


@RestController
@RequestMapping("/api/secure")
public class SecureController {
    private static Logger logger = LogManager.getLogger();

    private UserService userService;

    public SecureController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        logger.info("retrieved user");

        return userService.getUser(id);
    }
}
