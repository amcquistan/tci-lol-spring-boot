package com.thecodinginterface.tcilol.controllers;

import com.thecodinginterface.tcilol.models.AvailableRoles;
import com.thecodinginterface.tcilol.models.Role;
import com.thecodinginterface.tcilol.models.User;
import com.thecodinginterface.tcilol.models.UserDetailsDto;
import com.thecodinginterface.tcilol.repositories.RoleRepository;
import com.thecodinginterface.tcilol.repositories.UserRepository;
import com.thecodinginterface.tcilol.security.JwtResponse;
import com.thecodinginterface.tcilol.security.JwtUtils;
import com.thecodinginterface.tcilol.security.LoginRequest;
import com.thecodinginterface.tcilol.security.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.stream.Collectors;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Value("${tcilol.jwt.secret}")
    private String jwtSecret;

    @Value("${tcilol.jwt.expiration.ms}")
    private int jwtExpiryMs;

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        var userDetails = (UserDetailsDto) auth.getPrincipal();
        var jwt = JwtUtils.generateJwtToken(jwtSecret, jwtExpiryMs, userDetails);

        return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),
                userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()));
    }

    @PostMapping("/signup")
    public User signup(@RequestBody SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists");
        }

        if (request.getPassword().length() < 8) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must be at least 8 characters");
        }

        if (!request.getPassword().equals(request.getPasswordCheck())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords do not match");
        }

        var user = new User(null,
                            request.getEmail(),
                            encoder.encode(request.getPassword()),
                            request.getFirstName(),
                            request.getLastName(),
                            Set.of(new Role(AvailableRoles.ROLE_USER)));
        userRepository.saveAndFlush(user);

        return user;
    }
}
