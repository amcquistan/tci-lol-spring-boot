package com.thecodinginterface.tcilol.controllers;

import com.thecodinginterface.tcilol.exceptions.UnauthorizedResourceException;
import com.thecodinginterface.tcilol.models.UserList;
import com.thecodinginterface.tcilol.services.UserListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users/{userid}/lists")
public class UserListController {

    @Autowired
    private UserListService userListService;

    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);

    @GetMapping
    public List<UserList> list(@PathVariable Long userid, Principal principal) {
        try {
            return userListService.findByUserIdForUsername(userid, principal.getName());
        } catch(EntityNotFoundException e) {
            logger.error("Not Found Error", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not Found", e);
        } catch (UnauthorizedResourceException e) {
            logger.error("Forbidden", e);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Requester Unauthorized", e);
        } catch(Exception e) {
            logger.error("Unsuccessful", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsuccessful", e);
        }
    }

    @GetMapping("{listid}")
    public UserList get(@PathVariable Long userid, @PathVariable Long listid, Principal principal) {
        try {
            return userListService.findByIdForUsername(listid, principal.getName());
        } catch(EntityNotFoundException e) {
            logger.error("Not Found Error", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not Found", e);
        } catch (UnauthorizedResourceException e) {
            logger.error("Forbidden", e);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Requester Unauthorized", e);
        } catch(Exception e) {
            logger.error("Unsuccessful", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsuccessful", e);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserList create(@PathVariable Long userid, @RequestBody UserList userList,
                           Principal principal) {
        try {
            return userListService.createForUsername(userid, principal.getName(), userList);
        } catch(EntityNotFoundException e) {
            logger.error("Not Found Error", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Found", e);
        } catch (UnauthorizedResourceException e) {
            logger.error("Forbidden", e);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Requester Unauthorized", e);
        } catch(Exception e) {
            logger.error("Unsuccessful", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsuccessful", e);
        }
    }

    @PutMapping("{listid}")
    public UserList update(@PathVariable Long userid, @PathVariable Long listid,
                           @RequestBody UserList userList, Principal principal) {

        try {
            return userListService.updateForUsername(listid, principal.getName(), userList);
        } catch(EntityNotFoundException e) {
            logger.error("Not Found Error", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not Found", e);
        } catch (UnauthorizedResourceException e) {
            logger.error("Forbidden", e);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Requester Unauthorized", e);
        } catch(Exception e) {
            logger.error("Unsuccessful", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsuccessful", e);
        }
    }

    @DeleteMapping("{listid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long userid, @PathVariable Long listid, Principal principal) {
        try {
            userListService.deleteForUsername(listid, principal.getName());
        } catch(EntityNotFoundException e) {
            logger.error("Not Found Error", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not Found", e);
        } catch (UnauthorizedResourceException e) {
            logger.error("Forbidden", e);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Requester Unauthorized", e);
        } catch(Exception e) {
            logger.error("Forbidden", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsuccessful", e);
        }
    }
}
