package com.thecodinginterface.tcilol.controllers;

import com.thecodinginterface.tcilol.exceptions.UnauthorizedResourceException;
import com.thecodinginterface.tcilol.models.User;
import com.thecodinginterface.tcilol.repositories.UserRepository;
import com.thecodinginterface.tcilol.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> list() {
        return userRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public User get(@PathVariable Long id, Principal principal) {
        try {
            return userService.findByIdForUsername(id, principal.getName());
        } catch(EntityNotFoundException | UnauthorizedResourceException e) {
            logger.error("Bad Request", e);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id, Principal principal) {
        var successful = false;
        try {
            successful = userService.deleteByIdForUsername(id, principal.getName());
        } catch(UnauthorizedResourceException e) {
            logger.error("Bad Request", e);
        }

        if (!successful) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public User update(@PathVariable Long id, @RequestBody User user, Principal principal) {
        try {
            return userService.updateForUsername(id, user, principal.getName());
        } catch(EntityNotFoundException | UnauthorizedResourceException e) {
            logger.error("Bad Request", e);
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
