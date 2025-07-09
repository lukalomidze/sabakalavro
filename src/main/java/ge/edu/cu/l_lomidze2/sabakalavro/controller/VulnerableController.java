package ge.edu.cu.l_lomidze2.sabakalavro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ge.edu.cu.l_lomidze2.sabakalavro.model.User;
import ge.edu.cu.l_lomidze2.sabakalavro.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/vulnerable")
public class VulnerableController {
    @Autowired
    private UserService userService;

    @GetMapping("/bola/user/{id}")
    @Tag(name = "1. BOLA (Broken Object Level Authentication)")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }
}
