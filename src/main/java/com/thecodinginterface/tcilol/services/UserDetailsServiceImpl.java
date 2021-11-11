package com.thecodinginterface.tcilol.services;

import com.thecodinginterface.tcilol.models.User;
import com.thecodinginterface.tcilol.models.UserDetailsDto;
import com.thecodinginterface.tcilol.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " does not exist"));
        return new UserDetailsDto(user);
    }
}
