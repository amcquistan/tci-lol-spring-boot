package com.thecodinginterface.tcilol.services;

import com.thecodinginterface.tcilol.exceptions.UnauthorizedResourceException;
import com.thecodinginterface.tcilol.models.ListItem;
import com.thecodinginterface.tcilol.repositories.ListItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ListItemService {

    @Autowired
    private ListItemRepository listItemRepository;

    @Autowired
    private UserListService userListService;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(ListItemService.class);

    public ListItem findById(Long id) throws EntityNotFoundException {
        return listItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ListItem not found by id " + id));
    }

    public List<ListItem> findByListIdForUsername(Long listId, String requestingUsername)
            throws EntityNotFoundException, UnauthorizedResourceException {

        var userList = userListService.findById(listId);

        userService.validateResourceForRequestingUsername(userList.getUser().getId(), requestingUsername);

        return listItemRepository.findByUserList(userList);
    }

    public ListItem findByIdForUsername(Long id, String requestingUsername)
            throws EntityNotFoundException, UnauthorizedResourceException {

        var item = findById(id);

        userService.validateResourceForRequestingUsername(item.getUserList().getUser().getId(), requestingUsername);

        return item;
    }

    public ListItem createForUsername(Long listId, String requestingUsername, ListItem item)
            throws EntityNotFoundException, UnauthorizedResourceException {

        var userList = userListService.findById(listId);
        item.setUserList(userList);

        userService.validateResourceForRequestingUsername(userList.getUser().getId(), requestingUsername);

        return listItemRepository.saveAndFlush(item);
    }

    public ListItem updateForUsername(Long id, String requestingUsername, ListItem modifiedItem)
            throws EntityNotFoundException, UnauthorizedResourceException  {

        var item = findById(id);

        userService.validateResourceForRequestingUsername(item.getUserList().getUser().getId(), requestingUsername);

        BeanUtils.copyProperties(modifiedItem, item, "id", "created");

        return listItemRepository.saveAndFlush(item);
    }

    public void deleteForUsername(Long id, String requestingUsername)
            throws EntityNotFoundException, UnauthorizedResourceException {

        var item = findById(id);

        userService.validateResourceForRequestingUsername(item.getUserList().getUser().getId(), requestingUsername);

        listItemRepository.delete(item);
    }
}
