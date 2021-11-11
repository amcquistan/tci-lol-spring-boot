package com.thecodinginterface.tcilol.controllers;

import com.thecodinginterface.tcilol.exceptions.UnauthorizedResourceException;
import com.thecodinginterface.tcilol.models.ListItem;
import com.thecodinginterface.tcilol.services.ListItemService;
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
@RequestMapping("/api/v1/users/{userid}/lists/{listid}/items")
public class ListItemController {

    @Autowired
    private ListItemService listItemService;

    private static final Logger logger = LoggerFactory.getLogger(ListItemController.class);

    @GetMapping
    public List<ListItem> list(@PathVariable Long listid, Principal principal) {
        try {
            return listItemService.findByListIdForUsername(listid, principal.getName());
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

    @GetMapping("{itemid}")
    public ListItem get(@PathVariable Long listid, @PathVariable Long itemid, Principal principal) {

        try {
            return listItemService.findByIdForUsername(itemid, principal.getName());
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
    public ListItem create(@PathVariable Long listid,
                           @RequestBody ListItem item, Principal principal) {
        try {
            return listItemService.createForUsername(listid, principal.getName(), item);
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

    @RequestMapping(value = "{itemid}", method = RequestMethod.PUT)
    public ListItem update(@PathVariable Long listid, @PathVariable Long itemid,
                           @RequestBody ListItem modifiedItem, Principal principal) {
        try {
            return listItemService.updateForUsername(listid, principal.getName(), modifiedItem);
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

    @DeleteMapping("{itemid}")
    public void delete(@PathVariable Long itemid, Principal principal) {
        try {
            listItemService.deleteForUsername(itemid, principal.getName());
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
}
