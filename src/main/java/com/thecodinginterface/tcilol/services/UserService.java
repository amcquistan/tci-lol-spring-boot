package com.thecodinginterface.tcilol.services;

import com.thecodinginterface.tcilol.exceptions.UnauthorizedResourceException;
import com.thecodinginterface.tcilol.models.AvailableRoles;
import com.thecodinginterface.tcilol.models.User;
import com.thecodinginterface.tcilol.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public User findByEmail(String email) throws UsernameNotFoundException {
         try {
             return userRepository.findByEmail(email)
                     .orElseThrow(() -> new UsernameNotFoundException("User not found for email " + email));
         } catch(UsernameNotFoundException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    public User findById(Long id) throws EntityNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    public User findByIdForUsername(Long id, String requestingUsername)
            throws EntityNotFoundException, UnauthorizedResourceException {

        validateResourceForRequestingUsername(id, requestingUsername);

        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found for id " + id));
    }

    public boolean deleteByIdForUsername(Long id, String requestingUsername)
            throws UnauthorizedResourceException {

        validateResourceForRequestingUsername(id, requestingUsername);

        userRepository.deleteById(id);

        return true;
    }

    public User updateForUsername(Long id, User modifiedUser, String requestingUsername)
            throws EntityNotFoundException, UnauthorizedResourceException {

        User requestingUser = null;
        try {
            requestingUser = findByEmail(requestingUsername);
        } catch(UsernameNotFoundException e) {
            throw new UnauthorizedResourceException("User not found by email " + requestingUsername);
        }

        validateResourceForRequesterUser(id, requestingUser);

        var existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found by id " + id));

        BeanUtils.copyProperties(modifiedUser, existingUser, "id", "created", "password");

        return userRepository.saveAndFlush(existingUser);
    }

    public boolean isAdminOrSameUser(Long id, User user) {
        return Objects.equals(id, user.getId()) || user.getRoles().contains(AvailableRoles.ROLE_ADMIN);
    }

    public void validateResourceForRequestingUsername(Long resourceUserId, String requestingUsername)
            throws UnauthorizedResourceException {

        User requestingUser = null;
        try {
            requestingUser = findByEmail(requestingUsername);
        } catch(UsernameNotFoundException e) {
            throw new UnauthorizedResourceException(e.getMessage());
        }

        validateResourceForRequesterUser(resourceUserId, requestingUser);
    }

    public void validateResourceForRequesterUser(Long resourceUserId, User requestingUser)
            throws UnauthorizedResourceException {

        if (!isAdminOrSameUser(resourceUserId, requestingUser)) {
            logger.error("Requesting user {} not authorized to access resources for user Id {}", requestingUser, resourceUserId);
            throw new UnauthorizedResourceException("Unauthorized access by " + requestingUser);
        }
    }
}
