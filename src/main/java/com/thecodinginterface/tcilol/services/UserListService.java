package com.thecodinginterface.tcilol.services;

import com.thecodinginterface.tcilol.exceptions.UnauthorizedResourceException;
import com.thecodinginterface.tcilol.models.User;
import com.thecodinginterface.tcilol.models.UserList;
import com.thecodinginterface.tcilol.repositories.UserListRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UserListService {

    @Autowired
    private UserListRepository userListRepository;

    @Autowired
    private UserService userService;

    public List<UserList> findByUserIdForUsername(Long userId, String requestingUsername)
            throws EntityNotFoundException, UnauthorizedResourceException {

        userService.validateResourceForRequestingUsername(userId, requestingUsername);

        var user = new User();
        user.setId(userId);
        return userListRepository.findByUser(user);
    }

    public UserList findByIdForUsername(Long id, String requestingUsername)
            throws EntityNotFoundException, UnauthorizedResourceException {

        var userList = findById(id);

        userService.validateResourceForRequestingUsername(userList.getUser().getId(), requestingUsername);

        return userList;
    }

    public UserList createForUsername(Long userId, String requestingUsername, UserList userList)
            throws EntityNotFoundException, UnauthorizedResourceException {

        userService.validateResourceForRequestingUsername(userId, requestingUsername);

        var user = userService.findById(userId);
        userList.setUser(user);

        return userListRepository.saveAndFlush(userList);
    }

    public UserList updateForUsername(Long listId, String requestingUsername, UserList modifiedUserList)
            throws EntityNotFoundException, UnauthorizedResourceException {

        var userList = findById(listId);

        userService.validateResourceForRequestingUsername(userList.getUser().getId(), requestingUsername);

        BeanUtils.copyProperties(modifiedUserList, userList, "id", "created");

        return userListRepository.saveAndFlush(userList);
    }

    public void deleteForUsername(Long listId, String requestingUsername)
            throws EntityNotFoundException, UnauthorizedResourceException {

        var userList = findById(listId);

        userService.validateResourceForRequestingUsername(userList.getUser().getId(), requestingUsername);

        userListRepository.delete(userList);
    }

    public UserList findById(Long id) throws EntityNotFoundException {
        return userListRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("UserList not found by id " + id));
    }
}
